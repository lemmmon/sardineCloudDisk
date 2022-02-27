package com.sardine.dao;

import com.sardine.bean.NodeDetailInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface NodeDetailInfoDao {
//    int add(NodeDetailInfo nodeDetailInfo);

    @Delete("DELETE FROM NODE_DETAIL_INFO WHERE IP=#{ip}")
    int delete(String ip);


    @Insert("INSERT INTO NODE_DETAIL_INFO (ip, disk, disk_capacity, owner, health_status) VALUES (#{ip}, #{disk}, " +
            "#{diskCapacity}, #{nodeOwner}, #{healthStatus})")
    int add(NodeDetailInfo nodeDetailInfo);

    @Update("UPDATE NODE_DETAIL_INFO SET disk = #{disk},disk_capacity=#{diskCapacity},owner=#{nodeOwner}," +
            "health_status=#{healthStatus} WHERE (IP = #{ip} AND DISK = #{disk})")
    int update(NodeDetailInfo nodeDetailInfo);

    @Select("SELECT * FROM NODE_DETAIL_INFO WHERE IP = #{ip}")
    List<NodeDetailInfo> getByIp(String ip);

    @Update("UPDATE NODE_DETAIL_INFO SET HEALTH_STATUS = #{status} WHERE IP = #{ip}")
    void setStatus(String ip, int status);

    @Select("SELECT * FROM NODE_DETAIL_INFO")
    List<NodeDetailInfo> get();
}
