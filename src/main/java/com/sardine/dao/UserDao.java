package com.sardine.dao;

import com.sardine.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserDao {
//    void add(User user);
//
//    void delete(String userName);
//
//    @Update("UPDATE USER_INFO")
//    void set(User user);

    @Update("UPDATE USER_INFO SET TOKEN = #{token} WHERE NAME = #{userName}")
    int setToken(String userName, String token);

    @Select("SELECT * FROM USER_INFO WHERE NAME = #{userName}")
    List<User> getByName(String userName);

    @Select("SELECT * FROM USER_INFO")
    List<User> get();
}
