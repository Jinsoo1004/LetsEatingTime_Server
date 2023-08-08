package com.example.let.service.impl;

import com.example.let.domain.Access;
import com.example.let.domain.UploadedFile;
import com.example.let.domain.User;
import com.example.let.domain.req.PasswordChangeRequest;
import com.example.let.exception.GlobalException;
import com.example.let.mapper.FileMapper;
import com.example.let.mapper.UserMapper;
import com.example.let.service.AccessService;
import com.example.let.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Service
@Log4j2
@RequiredArgsConstructor
public class FileUploadServiceImpl implements FileUploadService {
    private final FileMapper fileMapper;
    private final UserMapper userMapper;
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
    public List<User> setMealBundle(MultipartFile multipartFile) throws IOException {
        File file = new File(multipartFile.getOriginalFilename());
        multipartFile.transferTo(file);
        FileInputStream inputStream = new FileInputStream(file);

        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = workbook.getSheetAt(0);
        String grantor = sheet.getRow(4).getCell(5).getStringCellValue();

        int rowIndex = 4;
        XSSFRow row = sheet.getRow(rowIndex++);
        List<User> users = new ArrayList<>();
        while (row.getCell(0) == null) {
            User user = userMapper.getById(row.getCell(2).getStringCellValue());
            accessService.register(user.getId(), grantor, "breakfast_weekend");
            accessService.register(user.getId(), grantor, "lunch_weekend");
            accessService.register(user.getId(), grantor, "dinner_weekend");

            users.add(user);
            log.info(user.toString());
            row = sheet.getRow(rowIndex++);
        }
        //todo 등록
        return users;
    }
    @Override
    public File getMealBundleForm() throws IOException {
        FileOutputStream fos = new FileOutputStream(new File("bundle/output.xlsx"));
        File file = new File("bundle/meal.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(file));
        XSSFSheet sheet = workbook.getSheetAt(0);
        workbook.write(fos);
        fos.close();
        return new ClassPathResource("bundle/meal.xlsx").getFile();
    }
}