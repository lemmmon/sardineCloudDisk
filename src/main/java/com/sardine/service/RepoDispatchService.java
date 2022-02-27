package com.sardine.service;

import com.sardine.bean.User;
import com.sardine.common.Constants;
import com.sardine.common.CustomException;
import com.sardine.common.context.UserContext;
import com.sardine.manager.daoManager.UserDaoManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Slf4j
@Service
public class RepoDispatchService {
    @Autowired
    private ImageRepoService imageRepoService;

    @Autowired
    private UserDaoManager userDaoManager;

    public void uploadDispatch(MultipartFile multipartFile) throws CustomException {
        imageRepoService.uploadToCache(multipartFile);
    }


}
