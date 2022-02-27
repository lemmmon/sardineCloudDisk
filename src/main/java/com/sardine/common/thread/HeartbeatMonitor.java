package com.sardine.common.thread;

import com.alibaba.fastjson.JSONObject;
import com.sardine.bean.NodeDetailInfo;
import com.sardine.common.Constants;
import com.sardine.common.HealthStatus;
import com.sardine.common.Util;
import com.sardine.manager.daoManager.NodeDetailInfoManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@EnableScheduling
public class HeartbeatMonitor {
    @Autowired
    private NodeDetailInfoManager nodeDetailInfoManager;

    private Map<String, Integer> heartBeatInfo = new ConcurrentHashMap<>();

    /**
     * 启动springboot时加载
     */
    @Bean
    public void init() {
        log.info("start init heartbeat info");
        waitDbNormal();
        List<NodeDetailInfo> allNode = nodeDetailInfoManager.getAll();
        for (NodeDetailInfo nodeDetailInfo : allNode) {
            heartBeatInfo.put(nodeDetailInfo.getIp(), Constants.MAX_LOSE_HEARTBEAT_TIMES);
        }
    }

    private void waitDbNormal() {
        while (true) {
            Util.sleep(10);
            try {
                nodeDetailInfoManager.getAll();
            } catch (Exception ex) {
                System.out.println("db is not ok");
                continue;
            }
            break;
        }
    }

    public void resetTimes(String message) {
        try {
            String ip = message.split("@")[0];
            // 无效IP则丢弃
            List<NodeDetailInfo> nodeDetailInfoList = parseDiskMessage(ip, message.replace(ip + "@", ""));
            heartBeatInfo.put(ip, Constants.MAX_LOSE_HEARTBEAT_TIMES);
            for (NodeDetailInfo nodeDetailInfo : nodeDetailInfoList) {
                nodeDetailInfoManager.setIfNotExist(nodeDetailInfo);
            }
        } catch (Exception ex) {
            log.error("failed to rest heartbeat info:", ex);
        }
    }

    /**
     * msg: 本机IP@盘符1@容量,盘符2:@容量
     *
     * @param ip          ip
     * @param diskMessage disk info
     * @return 硬盘信息
     */
    private List<NodeDetailInfo> parseDiskMessage(String ip, String diskMessage) {
        JSONObject jsonObject = JSONObject.parseObject(diskMessage);
        List<NodeDetailInfo> result = new ArrayList<>();
        for (String key : jsonObject.keySet()) {
            JSONObject diskJsonObject = jsonObject.getJSONObject(key);
            NodeDetailInfo nodeDetailInfo = NodeDetailInfo.builder()
                    .disk(diskJsonObject.getString("drive_letter"))
                    .diskCapacity(diskJsonObject.getLong("avaliable_capacity"))
                    .ip(ip)
                    .build();
            result.add(nodeDetailInfo);
        }
        return result;
    }


    /**
     * 监测心跳定时任务
     * // todo 定时任务间隔要调整
     */
    @Scheduled(fixedDelay = 5000)
    public void detectHeartBeat() {
        log.error("start check heart beat");
        waitDbNormal();
        List<String> ipSet = new ArrayList<>(heartBeatInfo.keySet());
        for (String ip : ipSet) {
            int count = heartBeatInfo.get(ip);
            --count;
            if (count <= 0) {
                //  服务器状态异常
                log.error("host {} status is abnormal", ip);
                nodeDetailInfoManager.updateHeathStatusByIp(ip, HealthStatus.ABNORMAL.getValue());
                nodeDetailInfoManager.delete(ip);
                heartBeatInfo.remove(ip);
            } else {
                heartBeatInfo.put(ip, count);
                nodeDetailInfoManager.updateHeathStatusByIp(ip, HealthStatus.NORMAL.getValue());
            }
        }
    }
}
