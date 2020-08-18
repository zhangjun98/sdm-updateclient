/**
 * Project Name: sdm-upgradetools
 * File Name: Registery
 * Package Name: com.supermap.digicity.sdm.tools
 * Date: 2020/4/27 11:53
 * Copyright (c) 2020,All Rights Reserved.
 */
package com.supermap.digicity.sdm.utils;


import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 * @Author: zhangjun
 * @Description:  注册表路径为HKEY_LOCAL_MACHINE\Software\JavaSoft\prefs\supermap/S/G/S
 * 节点不要出现大写字母,不然在注册表中的项前就加了一个“/”;
 * @Date: Create in 11:53 2020/4/27
 */
public class OperateRegistryUtils {

    /**
     * 把相应的值储存到变量中去 如果没有对应的节点，会自己创造一个对应的节点
     */
    public static void writeValue(String productparentnode,String productname,String keys ,String values) {
        // HKEY_LOCAL_MACHINE\Software\JavaSoft\prefs下写入注册表值.
        Preferences pre = Preferences.userRoot().node("/"+productparentnode+"/"+productname);
            pre.put(keys, values);
    }
    public static void writeValue(String productparentnode,String keys ,String values) {
        // HKEY_LOCAL_MACHINE\Software\JavaSoft\prefs下写入注册表值.
        Preferences pre = Preferences.userRoot().node("/"+productparentnode);
        pre.put(keys, values);
    }

    /***
     * 根据key获取value
     *
     */
    public static String getValue(String productparentnode,String productname,String key) {
        Preferences pre = Preferences.userRoot().node("/"+productparentnode+"/"+productname);
        return pre.get(key, "");
    }

    public static String getValue(String productparentnode,String key) {
        Preferences pre = Preferences.userRoot().node("/"+productparentnode);
        return pre.get(key, "");
    }

    /***
     * 清除注册表
     *
     * @throws BackingStoreException
     */
    public static void clearValue(String productparentnode,String productname) throws BackingStoreException {
        Preferences pre = Preferences.userRoot().node("/"+productparentnode+"/"+productname);
        pre.clear();
    }

    /**
     * 谨慎使用，不然会删除该注册表目录的所有子目录结构
     * @param productparentnode
     * @throws BackingStoreException
     */
    public static void clearValue(String productparentnode) throws BackingStoreException {
        Preferences pre = Preferences.userRoot().node("/"+productparentnode);
        pre.clear();
    }

    public static void main(String[] args) {

        try {
           OperateRegistryUtils.writeValue("supermapSGS","iserver","setupVersion","9.1");
           OperateRegistryUtils.writeValue("supermapSGS","test","setupVersion","2.1");
           OperateRegistryUtils.writeValue("supermapSGS","iserver","setupLocal","E:\\");
           OperateRegistryUtils.writeValue("supermapSGS","test","setupLocal","E:\\");
           OperateRegistryUtils.writeValue("supermapSGS","datamanger","setupVersion","1");
           OperateRegistryUtils.writeValue("supermapSGS","datamanger","setupLocal","E:\\");


          //OperateRegistryUtils.writeValue("supermapSGS","datamanger","setupVersion","1.3");
          // OperateRegistryUtils.writeValue("supermapSGS","datamanger","setupLocal","E:\\CTCode");
        }catch (Exception e){

        }
        System.out.println(OperateRegistryUtils.getValue("supermapSGS","datamanger","setupLocal"));
     try {
        // OperateRegistryUtils.clearValue("datamanger");
        } catch (Exception e) {
            e.printStackTrace();
       }

    }

}
