package com.example.let.service.impl;

import com.example.let.domain.UploadedFile;
import com.example.let.exception.GlobalException;
import com.example.let.mapper.FileMapper;
import com.example.let.mapper.UserMapper;
import com.example.let.service.FileUploadService;
import com.example.let.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class FileUploadServiceImpl implements FileUploadService {
    private final FileMapper fileMapper;
    private final UserMapper userMapper;

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

}
