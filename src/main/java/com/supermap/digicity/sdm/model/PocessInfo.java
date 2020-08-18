/**
 * Project Name: sdm-upgradetools
 * File Name: PocessInfo
 * Package Name: com.supermap.digicity.sdm.model
 * Date: 2020/4/30 14:43
 * Copyright (c) 2020,All Rights Reserved.
 */
package com.supermap.digicity.sdm.model;

/**
 * @Author: zhangjun
 * @Description: 进程任务的实体类
 * @Date: Create in 14:43 2020/4/30 
 */
public class PocessInfo {
    //该进程所在的行号
    private int rowKey;
    //该进程现在的状态，用于更新表格第三列
    private String pocessStatue;

    public int getRowKey() {
        return rowKey;
    }

    public void setRowKey(int rowKey) {
        this.rowKey = rowKey;
    }

    public String getPocessStatue() {
        return pocessStatue;
    }

    public void setPocessStatue(String pocessStatue) {
        this.pocessStatue = pocessStatue;
    }
}
