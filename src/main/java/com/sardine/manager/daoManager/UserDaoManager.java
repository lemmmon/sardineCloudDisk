package com.sardine.manager.daoManager;

import com.sardine.bean.User;
import com.sardine.dao.UserDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
public class UserDaoManager {
    @Autowired
    private UserDao userDao;

    public void add(User user) {
    }

    public void delete(String userName) {

    }

    public void set(User user) {

    }

    public void setToken(String userName, String token) {
        userDao.setToken(userName, token);
    }

    public List<User> get(String userName) {
        return userDao.getByName(userName);
    }

    public List<User> getAll() {
        return userDao.get();
    }

    public Optional<User> getUserByToken(String token) {
        List<User> allUser = userDao.get();
        if (CollectionUtils.isEmpty(allUser)) {
            log.warn("cannot find user from db");
            return Optional.empty();
        }
        List<User> userList =
                allUser.stream().filter(user -> token.equals(user.getToken())).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(userList)) {
            return Optional.empty();
        }
        return Optional.of(userList.get(0));
    }
}
