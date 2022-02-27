package com.sardine.manager.daoManager;

import com.sardine.bean.NodeDetailInfo;
import com.sardine.common.Constants;
import com.sardine.common.CustomException;
import com.sardine.dao.NodeDetailInfoDao;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Component
public class NodeDetailInfoManager {
    @Autowired
    private NodeDetailInfoDao nodeDetailInfoDao;

    public void delete(String ip) {
        nodeDetailInfoDao.delete(ip);
    }

    public void add(NodeDetailInfo nodeDetailInfo) throws CustomException {
        int res = nodeDetailInfoDao.add(nodeDetailInfo);
        if (res == 0) {
            log.error("insert {} into db failed", nodeDetailInfo.getIp());
            throw new CustomException(Constants.INTERNAL_ERROR);
        }
    }

    public void setIfNotExist(NodeDetailInfo nodeDetailInfo) throws CustomException {
        Optional<NodeDetailInfo> optionalNodeDetailInfo = getInfoByIp(nodeDetailInfo.getIp(), nodeDetailInfo.getDisk());
        if (optionalNodeDetailInfo.isPresent()) {
            update(nodeDetailInfo);
        } else {
            add(nodeDetailInfo);
        }
    }

    public void update(NodeDetailInfo nodeDetailInfo) throws CustomException {
        int res = nodeDetailInfoDao.update(nodeDetailInfo);
        if (res == 0) {
            log.error("update node info failed: {}", nodeDetailInfo.getIp());
            throw new CustomException(Constants.INTERNAL_ERROR);
        }
    }

    public Optional<NodeDetailInfo> getInfoByIp(String ip, String disk) {
        List<NodeDetailInfo> nodeDetailInfo = nodeDetailInfoDao.getByIp(ip);
        if (CollectionUtils.isEmpty(nodeDetailInfo)) {
            return Optional.empty();
        }
        List<NodeDetailInfo> targetNodeInfo =
                nodeDetailInfo.stream().filter(e -> disk.equals(e.getDisk())).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(targetNodeInfo)) {
            return Optional.empty();
        }
        return Optional.of(targetNodeInfo.get(0));
    }

    public List<NodeDetailInfo> getAll() {
        List<NodeDetailInfo> allNode = nodeDetailInfoDao.get();
        if (CollectionUtils.isEmpty(allNode)) {
            return new ArrayList<>();
        }
        return allNode;
    }

    public void updateHeathStatusByIp(String ip, int heathStatus) {
        try {
            nodeDetailInfoDao.setStatus(ip, heathStatus);
        } catch (Exception ex) {
            log.error("failed to update heartbeat info:", ex);
        }
    }
}
