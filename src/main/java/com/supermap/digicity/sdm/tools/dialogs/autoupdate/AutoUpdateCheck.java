/**
 * Project Name: sdm-upgradetools
 * File Name: AutoUpdateCheck
 * Package Name: com.supermap.digicity.sdm.tools.dialogs.autoupdate
 * Date: 2020/5/8 12:17
 * Copyright (c) 2020,All Rights Reserved.
 */
package com.supermap.digicity.sdm.tools.dialogs.autoupdate;

import com.supermap.digicity.sdm.utils.CmdExec;
import com.supermap.digicity.sdm.utils.HttpClientUtils;
import com.supermap.digicity.sdm.utils.loadxml.LoadStartupInfoUtils;

/**
 * @Author: zhangjun
 * @Description:
 * @Date: Create in 12:17 2020/5/8 
 */
public class AutoUpdateCheck {

    public void checkProductAndVersion(String productname,String productversion,String setuplocalpath){

       String newversion= HttpClientUtils.getNewProductVersionForProductName(productname);
       if(!newversion.equals("[]")){
           System.out.println("产品最新版本为："+newversion);
       if(!newversion.equals(productversion)){
           UpdateAutoProcessDialog dig = new UpdateAutoProcessDialog(productname,newversion,setuplocalpath);
       }else{
           //启动目录下的项目
              CmdExec.startApp();
       }
    }else{
           //启动目录下的项目
           CmdExec.startApp();
       }
    }

}
