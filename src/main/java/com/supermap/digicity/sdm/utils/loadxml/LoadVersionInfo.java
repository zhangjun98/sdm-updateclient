/**
 * Project Name: sdm-upgradetools
 * File Name: LoadVersionInfoUtils
 * Package Name: com.supermap.digicity.sdm.utils
 * Date: 2020/5/9 17:57
 * Copyright (c) 2020,All Rights Reserved.
 */
package com.supermap.digicity.sdm.utils.loadxml;

import com.supermap.digicity.sdm.model.VersionInfo;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.*;

/**
 * @Author: zhangjun
 * @Description: 获取产品信息的通用类
 * @Date: Create in 17:57 2020/5/9 
 */
public class LoadVersionInfo{
    private static Document document;
    private String localpath;

   public  LoadVersionInfo(String localpath) {
       this.localpath=localpath;
       File VersionInfo = new File(localpath+"/versioninfo.xml");
       SAXReader reader = new SAXReader();
       try {
           document = reader.read(VersionInfo);
       } catch (DocumentException e) {
           e.getMessage();
       }
   }

    public VersionInfo getVersionInfo(){
        VersionInfo versionInfo = new VersionInfo();
        Element rootElement = document.getRootElement();
        Iterator iterator = rootElement.elementIterator();
        Element productname = (Element) iterator.next();
        Element productversion = (Element) iterator.next();
        versionInfo.setProductname(productname.getStringValue());
        versionInfo.setProductversion(productversion.getStringValue());
        //遍历features中的feature
        ArrayList<ArrayList<String>> tmplist=new ArrayList<>();
       for(int i=0;iterator.hasNext();i++){
           ArrayList<String> elementlist =new ArrayList<>();
           Element elementchild=(Element)iterator.next();
           Iterator childiterator = elementchild.elementIterator();
           while(childiterator.hasNext()){
               Element elementchildlist=(Element)childiterator.next();
               elementlist.add(elementchildlist.getStringValue());
           }
           tmplist.add(elementlist);
       }
        versionInfo.setFeatures(tmplist.get(0));
        versionInfo.setEnhances(tmplist.get(1));
        versionInfo.setCompatibilitys(tmplist.get(2));
        versionInfo.setOthers(tmplist.get(3));
        return versionInfo;
    }

    public static void main(String[] args) throws DocumentException {
        LoadVersionInfo infos = new LoadVersionInfo("src/main/resources/");
        VersionInfo info= infos.getVersionInfo();
    }
}
