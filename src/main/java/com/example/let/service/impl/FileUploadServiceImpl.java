package com.example.let.service.impl;

import com.example.let.domain.Access;
import com.example.let.domain.UploadedFile;
import com.example.let.domain.User;
import com.example.let.domain.UserForAccess;
import com.example.let.exception.GlobalException;
import com.example.let.mapper.AccessMapper;
import com.example.let.mapper.FileMapper;
import com.example.let.mapper.UserMapper;
import com.example.let.service.AccessService;
import com.example.let.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.usermodel.*;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
@Log4j2
@RequiredArgsConstructor
public class FileUploadServiceImpl implements FileUploadService {
    private final FileMapper fileMapper;
    private final UserMapper userMapper;
    private final AccessMapper accessMapper;
    private final AccessService accessService;

    @Value("${resource.path}")
    private String location;
    @Value("${dateFormat.yyyyMM}")
    private String dateFormatMonth;

    @Override
    public UploadedFile registerFile(MultipartFile multipartFile, String id) {
        if (multipartFile == null) {
            return null;
        }

        String originalFileName = multipartFile.getOriginalFilename();
        String contentType = multipartFile.getContentType();
        Long size = multipartFile.getSize();

        File file = new File(getDirectory(), UUID.randomUUID() + "_" + originalFileName);
        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            LoggerFactory.getLogger(getClass()).error(e.getMessage(), e);
            throw new GlobalException(HttpStatus.BAD_REQUEST, "Cannot save file");
        }

        UploadedFile uploadedFile = UploadedFile.builder()
                .originalFileName(originalFileName)
                .fileName(file.getName())
                .size(size)
                .contentType(contentType)
                .build();

        fileMapper.insertFile(uploadedFile);
        userMapper.setImage(id, uploadedFile.getFileIdx());

        return getFile(uploadedFile.getFileIdx());
    }

    @Override
    public UploadedFile getFile(Long idx) {
        return fileMapper.selectFile(idx);
    }

    @Override
    public File getPhysicalFile(UploadedFile uploadedFile) {
        if (uploadedFile == null) {
            throw new NullPointerException("File not found");
        }

        if (uploadedFile.getRegisterTimeDate() == null) {
            throw new GlobalException(HttpStatus.BAD_REQUEST, "Invalid file data");
        }

        String directory = getDirectory(uploadedFile.getRegisterTimeDate());

        return new File(directory, uploadedFile.getFileName());
    }

    @Override
    public void deleteFile(Long fileIdx) {
        fileMapper.deleteFile(fileIdx);

        UploadedFile uploadedFile = getFile(fileIdx);

        if (uploadedFile != null) {
            File file = getPhysicalFile(uploadedFile);
            file.delete();
        }
    }

    private String getDirectory() {
        return getDirectory(new Date());
    }

    private String getDirectory(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatMonth);

        File directory = new File(location, dateFormat.format(date));
        if (!directory.exists()) {
            directory.mkdirs();
        }

        return directory.getAbsolutePath();
    }

    @Override
    @Transactional
    public List<User> setMealBundle(MultipartFile multipartFile) throws IOException {
        InputStream inputStream = multipartFile.getInputStream();

        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = workbook.getSheetAt(0);
        String grantor = sheet.getRow(4).getCell(7).getStringCellValue();

        int rowIndex = 4;
        XSSFRow row = sheet.getRow(rowIndex++);
        List<User> users = new ArrayList<>();
        while (! row.getCell(0).getStringCellValue().equals("EndPoint")) {
            User user = userMapper.getById(row.getCell(2).getStringCellValue());
            accessMapper.delete(user.getId());
            if (row.getCell(4).getStringCellValue().equals("O")) {
                accessService.register(user.getId(), grantor, "breakfast");
                accessService.register(user.getId(), grantor, "lunch");
                accessService.register(user.getId(), grantor, "dinner");
            } else {
                accessMapper.deleteByUserIdAndType(user.getId(), "breakfast");
                accessMapper.deleteByUserIdAndType(user.getId(), "lunch");
                accessMapper.deleteByUserIdAndType(user.getId(), "dinner");
            }
            if (row.getCell(5).getStringCellValue().equals("O")) {
                accessService.register(user.getId(), grantor, "breakfast_weekend");
                accessService.register(user.getId(), grantor, "lunch_weekend");
                accessService.register(user.getId(), grantor, "dinner_weekend");
            } else {
                accessMapper.deleteByUserIdAndType(user.getId(), "breakfast_weekend");
                accessMapper.deleteByUserIdAndType(user.getId(), "lunch_weekend");
                accessMapper.deleteByUserIdAndType(user.getId(), "dinner_weekend");
            }

            users.add(user);
            row = sheet.getRow(rowIndex++);
        }
        //todo 등록
        return users;
    }
    @Override
    public byte[] getMealBundleForm() throws IOException {
        List<User> user = userMapper.getStudent();
        List<UserForAccess> userForAccess = new ArrayList<>();
        for (User target : user) {
            List<Access> access = accessMapper.getById(target.getId());
            userForAccess.add(UserForAccess.builder().user(target).access(access).build());
        }
        InputStream stream = new ClassPathResource("bundle/meal.xlsx").getInputStream();
        XSSFWorkbook workbook = new XSSFWorkbook(stream);
        XSSFSheet sheet = workbook.getSheetAt(0);
        XSSFRow row = null;
        XSSFCell cell = null;

        int rowIndex;
        for(rowIndex = 4; rowIndex < userForAccess.size() + 4; rowIndex++) {
            row = sheet.createRow((short) rowIndex);

            Map<String, String> map = new HashMap<>();
            UserForAccess target = userForAccess.get(rowIndex - 4);
            User targetUser = target.getUser();
            List<Access> targetAccess = target.getAccess();
            DecimalFormat df = new DecimalFormat("00");
            map.put("0", targetUser.getGrade()
                    + targetUser.getClassName()
                    + df.format(targetUser.getClassNo()));
            map.put("1", targetUser.getName());
            map.put("2", targetUser.getId());

            for (Access access : targetAccess) {
                if (access.getType().equals("breakfast")) map.put("4", "O");
                if (access.getType().equals("breakfast_weekend")) map.put("5", "O");
            }
            if(! map.containsKey("4")) map.put("4", "X");
            if(! map.containsKey("5")) map.put("5", "X");
            for(int j = 0; j < 6; j++) {
                cell = row.createCell(j);
                cell.setCellValue(map.get(Integer.toString(j)));
            }

            sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 2, 3));
        }
        cell = sheet.createRow(rowIndex).createCell(0);
        cell.setCellValue("EndPoint");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            workbook.write(bos);
        } finally {
            bos.close();
        }
        return bos.toByteArray();
    }
}