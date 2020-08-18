/**
 * Project Name: sdm-upgradetools
 * File Name: test
 * Package Name: com.supermap.digicty.sdm
 * Date: 2020/4/29 9:40
 * Copyright (c) 2020,All Rights Reserved.
 */
package com.supermap.digicty.sdm;

import com.google.gson.Gson;
import com.supermap.digicity.sdm.model.VersionInfo;
import com.supermap.digicity.sdm.utils.loadxml.LoadVersionInfo;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @Author: zhangjun
 * @Description:
 * @Date: Create in 9:40 2020/4/29 
 */
public class test {
     static long getZipTrueSize(String filePath) {
        long size = 0;
        ZipFile f;
        try {
            f = new ZipFile(filePath, Charset.forName("GBK"));
            Enumeration<? extends ZipEntry> en = f.entries();
            while (en.hasMoreElements()) {
                size += en.nextElement().getSize();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return size;
    }
    public static void main(String[] args) throws JSONException {
        LoadVersionInfo infos = new LoadVersionInfo("src/main/resources/");
        //LoadProductStartupUtils.setLocalpath("src/main/resources/");
        VersionInfo versionInfo = infos.getVersionInfo();
        Gson gosn = new Gson();
        String json = gosn.toJson(versionInfo);
        JSONObject param = null;
        try {
            param = new JSONObject(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println(param);
        //========创建json======
        JSONArray featuresarray = null;
        JSONArray enhancesarray = null;
        JSONArray compatibilitysarray = null;
        JSONArray othersarray = null;
        try {
            featuresarray = param.getJSONArray("features");
            enhancesarray = param.getJSONArray("enhances");
            compatibilitysarray = param.getJSONArray("compatibilitys");
            othersarray = param.getJSONArray("others");
        //    System.out.println(featuresarray);
        //    System.out.println(enhancesarray);
        //    System.out.println(compatibilitysarray);
        //    System.out.println(othersarray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ArrayList<String> featuresarraylist = new ArrayList<>();
        ArrayList<String> enhancesarraylist = new ArrayList<>();
        ArrayList<String> compatibilitysarraylist = new ArrayList<>();
        ArrayList<String> othersarraylist = new ArrayList<>();
        for(int i=0;i<featuresarray.length();i++){
            featuresarraylist.add(featuresarray.get(i).toString());
        }
        for(int i=0;i<enhancesarray.length();i++){
            enhancesarraylist.add(enhancesarray.get(i).toString());
        }
        for(int i=0;i<compatibilitysarray.length();i++){
            compatibilitysarraylist.add(compatibilitysarray.get(i).toString());
        }
        for(int i=0;i<othersarray.length();i++){
            othersarraylist.add(othersarray.get(i).toString());
        }
        String productname = param.getString("productname");  //简单的直接获取值
        String productversion = param.getString("productversion");  //简单的直接获取值
        System.out.println(productname);
        System.out.println(productversion);
        System.out.println(featuresarraylist);
        System.out.println(enhancesarraylist);
        System.out.println(compatibilitysarraylist);
        System.out.println(othersarraylist);

    }
}
