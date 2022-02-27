package com.sardine.bean;

import lombok.*;

import java.util.List;

/**
 * 节点磁盘信息
 *
 * @author hao ying
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NodeDetailInfo {
    private long id;
    private String ip;
    private String disk;
    private long diskCapacity;
    private String nodeOwner;
    private int healthStatus;
}
