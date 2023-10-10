package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class FileService {
    private final FileMapper fileMapper;
    private final UserMapper userMapper;

    public FileService(FileMapper fileMapper, UserMapper userMapper) {
        this.fileMapper = fileMapper;
        this.userMapper = userMapper;
    }

    public String[] getFileList(Integer userId) {
        return fileMapper.getFileList(userId);
    }

    public File getFile(String fileName) {
        return fileMapper.getFile(fileName);
    }

    public void addFile(MultipartFile multipartFile, String username) throws IOException {
        InputStream fis = multipartFile.getInputStream();
        ByteArrayOutputStream fileBuffer = new ByteArrayOutputStream();
        int readCount;
        byte[] size = new byte[1024];
        while ((readCount = fis.read(size, 0, size.length)) != -1) {
            fileBuffer.write(size, 0, readCount);
        }

        fileBuffer.flush();
        byte[] fileData = fileBuffer.toByteArray();
        String fileName = multipartFile.getOriginalFilename();
        String contentType = multipartFile.getContentType();
        String fileSize = String.valueOf(multipartFile.getSize());
        Integer userId = userMapper.getUser(username).getUserId();
        File file = new File(0, fileName, contentType, fileSize, userId, fileData);
        fileMapper.insert(file);
    }

    public void deleteFile(String fileName) {
        fileMapper.deleteFile(fileName);
    }
}
