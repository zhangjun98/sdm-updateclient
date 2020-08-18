/**
 * Project Name: sdm-upgradetools
 * File Name: test2
 * Package Name: com.supermap.digicty.sdm
 * Date: 2020/5/11 12:37
 * Copyright (c) 2020,All Rights Reserved.
 */
package com.supermap.digicty.sdm;

import com.supermap.digicity.sdm.Thread.AutoDownloadBackTask;
import com.supermap.digicity.sdm.model.ProduceInfo;
import com.supermap.digicity.sdm.upgrade;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.swing.*;
import java.io.File;
import java.net.URLEncoder;

/**
 * @Author: zhangjun
 * @Description:
 * @Date: Create in 12:37 2020/5/11 
 */
public class test2 {
    public static void main(String[] args) throws Exception {
        ProduceInfo info = new ProduceInfo();
        info.setDownloadProduceSetupLocal("E:\\a整体功能测试\\断点下载测试\\测试");
        info.setProduceName("数据处理系统");
        info.setDownloadProcessVersion("10.0");
        AutoDownloadBackTask test = new AutoDownloadBackTask(info);
        test.run();
    }

}
