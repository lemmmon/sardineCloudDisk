package com.sardine.service;

import com.sardine.bean.User;
import com.sardine.common.Constants;
import com.sardine.common.CustomException;
import com.sardine.common.Util;
import com.sardine.manager.daoManager.UserDaoManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class LoginService {

    @Autowired
    private UserDaoManager userDaoManager;

    /**
     * 校验用户信息是否有效，如果有效则返回token
     *
     * @param user
     * @return
     */
    public User createToken(User user) throws CustomException {
        if (StringUtils.isEmpty(user.getName()) || StringUtils.isEmpty(user.getPassword())) {
            log.error("input param error");
            throw new CustomException("input param error");
        }
        // 合法用户则生成一个token
        if (checkUserInfoValid(user)) {
            user.setToken(Util.randomSequence(Constants.tokenLength) + user.getMac());
        } else {
            throw new CustomException("login failed");
        }
        // token写入数据库中
        userDaoManager.setToken(user.getName(), user.getToken());
        return user;
    }

    private boolean checkUserInfoValid(User user) {
        List<User> optionalUser = userDaoManager.get(user.getName());
        if (CollectionUtils.isEmpty(optionalUser)) {
            log.error("cannot find user info from db");
            return false;
        }
        User userInDao = optionalUser.get(0);
        return userInDao.getPassword().equals(user.getPassword());
    }

    /**
     * 检查token是否有效
     *
     * @return true/false
     */
    public boolean checkTokenValid(String token) {
        if (StringUtils.isEmpty(token)) {
            log.warn("input token is empty");
            return false;
        }
        Optional<User> userOptional = userDaoManager.getUserByToken(token);
        return userOptional.isPresent();
    }

    /**
     * 注销token
     *
     * @param name
     */
    public void deleteToken(String name) {
        List<User> userList = userDaoManager.get(name);
        if (CollectionUtils.isEmpty(userList)) {
            log.warn("cannot find user by token");
            return;
        }
        User user = userList.get(0);
        log.info("delete user info: {}", user);
        userDaoManager.setToken(user.getName(), null);
    }
}
