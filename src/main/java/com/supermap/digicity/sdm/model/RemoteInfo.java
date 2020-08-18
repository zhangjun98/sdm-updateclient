/**
 * Project Name: sdm-upgradetools
 * File Name: RemoteInfo
 * Package Name: com.supermap.digicity.sdm.model
 * Date: 2020/4/30 12:07
 * Copyright (c) 2020,All Rights Reserved.
 */
package com.supermap.digicity.sdm.model;

/**
 * @Author: zhangjun
 * @Description: 远程的信息
 * @Date: Create in 12:07 2020/4/30 
 */
public class RemoteInfo {
    private String HTTPURL;

    public String getHTTPURL() {
        return HTTPURL;
    }

    public void setHTTPURL(String HTTPURL) {
        this.HTTPURL = HTTPURL;
    }

    @Override
    public String toString() {
        return "RemoteInfo{" +
                "HTTPURL='" + HTTPURL + '\'' +

                '}';
    }
}
