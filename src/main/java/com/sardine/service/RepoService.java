package com.sardine.service;

import com.sardine.common.CustomException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface RepoService {
    void uploadToCache(MultipartFile multipartFile) throws CustomException;

    void uploadToCloud(MultipartFile multipartFile) throws CustomException;

    void download(String name) throws CustomException;

    void delete(String name) throws CustomException;
}
