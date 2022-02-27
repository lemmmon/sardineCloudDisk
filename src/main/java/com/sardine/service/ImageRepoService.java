package com.sardine.service;

import com.sardine.bean.User;
import com.sardine.common.Constants;
import com.sardine.common.CustomException;
import com.sardine.common.config.SardineConfig;
import com.sardine.common.context.UserContext;
import com.sardine.manager.daoManager.UserDaoManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Optional;

@Slf4j
@Service
public class ImageRepoService implements RepoService {
    @Autowired
    private SardineConfig sardineConfig;

    @Autowired
    private UserDaoManager userDaoManager;

    @Override
    public void uploadToCache(MultipartFile multipartFile) throws CustomException {
        String cachePath = getUserCacheDir();
        log.info("save to [{}], filename: {}", cachePath, multipartFile.getOriginalFilename());
        File destFile = new File(cachePath + File.separator + multipartFile.getOriginalFilename());
        try {
            multipartFile.transferTo(destFile.getAbsoluteFile());
        } catch (Exception ex) {
            log.error("unknown exception when upload to cache: ", ex);
            throw new CustomException(Constants.INTERNAL_ERROR);
        }
    }

    private String getUserCacheDir() throws CustomException {
        Optional<User> optional = userDaoManager.getUserByToken(UserContext.getToken());
        if (!optional.isPresent()) {
            log.error("cannot find user info by token");
            throw new CustomException(Constants.USER_INFO_INVALID);
        }
        String cachePath =
                sardineConfig.getCachePath() + File.separator + optional.get().getName() + File.separator + Constants.IMAGE;
        File destDir = new File(cachePath);
        if (!destDir.exists() && !destDir.mkdirs()) {
            log.error("failed to mkdir {}", destDir);
            throw new CustomException(Constants.INTERNAL_ERROR);
        }
        return cachePath;
    }

    @Override
    public void uploadToCloud(MultipartFile multipartFile) throws CustomException {

    }

    @Override
    public void download(String name) {

    }

    @Override
    public void delete(String name) {

    }
}
