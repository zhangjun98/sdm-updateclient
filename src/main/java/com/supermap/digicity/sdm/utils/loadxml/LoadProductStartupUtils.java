/**
 * Project Name: sdm-upgradetools
 * File Name: LoadProductStartupUtils
 * Package Name: com.supermap.digicity.sdm.utils
 * Date: 2020/5/10 10:28
 * Copyright (c) 2020,All Rights Reserved.
 */
package com.supermap.digicity.sdm.utils.loadxml;

import com.supermap.digicity.sdm.utils.CmdExec;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
/**
 * @Author: zhangjun
 * @Description:  读取产品中的startup.xml文件
 * @Date: Create in 10:28 2020/5/10 
 */
public  class LoadProductStartupUtils {
    static String localpath;

    public static String getLocalpath() {
        return localpath;
    }

    public static void setLocalpath(String localpath) {
        LoadProductStartupUtils.localpath = localpath;
    }

    public static Map<String,String> getProductStartup() throws DocumentException {
        Document document;
        File VersionInfo = new File(localpath+"/start.xml");
        SAXReader reader = new SAXReader();
        document = reader.read(VersionInfo);
        Map<String,String> startupinfomap = new HashMap<>();
        Element rootElement = document.getRootElement();
        Iterator iterator = rootElement.elementIterator();
        Element pidName = (Element) iterator.next();
        Element filepath = (Element) iterator.next();
        Element HTTPURL = (Element) iterator.next();
       if(filepath.getStringValue().length()==0){
           startupinfomap.put("filepath",localpath);
       }else{
           startupinfomap.put("filepath",localpath+"\\"+filepath.getStringValue());
       }
        startupinfomap.put("pidName",pidName.getStringValue());
        startupinfomap.put("HTTPURL",HTTPURL.getStringValue());
        return startupinfomap;
    }

    public static void main(String[] args) throws DocumentException {

    }
}
