/**
 * Project Name: sdm-upgradetools
 * File Name: HttpClientUnils
 * Package Name: com.supermap.digicity.sdm.utils
 * Date: 2020/5/7 11:26
 * Copyright (c) 2020,All Rights Reserved.
 */
package com.supermap.digicity.sdm.utils;

import com.supermap.digicity.sdm.model.ProduceInfo;
import com.supermap.digicity.sdm.model.VersionInfo;
import com.supermap.digicity.sdm.utils.loadxml.LoadProductStartupUtils;
import com.supermap.digicity.sdm.utils.loadxml.LoadStartupInfoUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.dom4j.DocumentException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zhangjun
 * @Description:
 * @Date: Create in 11:26 2020/5/7 
 */
public class HttpClientUtils {

   static String uri;
    static {
        try {
            uri = LoadProductStartupUtils.getProductStartup().get("HTTPURL");
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    public static void setUri(String uri) {
        HttpClientUtils.uri = uri;
    }

    /**
     * 获取到服务器上面的所有产品
     * @return
     */
  public static List<ProduceInfo> getproductlist()  {
      String getproductlisturl=uri+"/getProduce";
      List<ProduceInfo> productinfolist= new ArrayList<>();
    // 创建client,放入try()中自动释放,不需要finally
    try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
      // 执行得到response
      try (CloseableHttpResponse response = client.execute(new HttpGet(getproductlisturl))) {
        HttpEntity entity = response.getEntity();
        if (entity != null) {
          // body
          String bodyAsString = EntityUtils.toString(entity);
          JSONArray param = new JSONArray(bodyAsString);

            for(int i=0;i<param.length();i++){
                ProduceInfo info =new ProduceInfo();
                info.setProduceName((String)param.get(i));
                info.setProduceVersion(getProductVersionList((String)param.get(i)));
                productinfolist.add(info);
            }


        }
      } catch (JSONException e) {
          e.printStackTrace();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return productinfolist;
  }

    /**
     * 获取产品名称对应的所有产品信息
     * @param productname
     * @return
     */
  public static ArrayList<String> getProductVersionList(String productname ){
      ArrayList<String> versionlist = new ArrayList<>();
      try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
         String getProductVersionListURL = uri+"/"+productname;
          try (CloseableHttpResponse response = client.execute(new HttpGet(getProductVersionListURL))) {
              HttpEntity entity = response.getEntity();
              if (entity != null) {
                  String bodyAsString = EntityUtils.toString(entity);
                  JSONArray param=new JSONArray();
                  if(bodyAsString.equals("")){
                      param.put("");
                  }else{
                       param = new JSONArray(bodyAsString);
                  }

                  for(int i=0;i<param.length();i++){
                      versionlist.add(param.get(i).toString());
                  }
              }
          } catch (JSONException e) {
              e.printStackTrace();
          }
      } catch (IOException e) {
          e.printStackTrace();
      }
     return versionlist;
  }

    /**
     * 获取最新的产品更新包的名称
     * @param productname
     * @param version
     * @return
     */
  public static String getNewProductName(String productname,String version ){
      String newproductname="";
      try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
          String getProductnameURL = uri+"/"+productname+"/"+version;
          try (CloseableHttpResponse response = client.execute(new HttpGet(getProductnameURL))) {
              HttpEntity entity = response.getEntity();
              if (entity != null) {
                 newproductname = EntityUtils.toString(entity);
              }
          }
      } catch (IOException e) {
          e.printStackTrace();
      }
     // System.out.println(newproductname);
      return  newproductname;

  }

    /**
     * 获取版本信息
     * @param productname
     * @param version
     * @return
     */
    public static VersionInfo getOneProductInfo(String productname,String version ){
      String getOneProductInfoUrl=uri+"getTxt/"+productname+"/"+version;
      VersionInfo info = null;
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
            try (CloseableHttpResponse response = client.execute(new HttpGet(getOneProductInfoUrl))) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String bodyAsString = EntityUtils.toString(entity);
                    info=JSONToversionInfo(bodyAsString);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return info;
    }

    public static long getRemoteZipSize(String productname,String version ){
        String getremotezipsizeUrl=uri+"getZipSize/"+productname+"/"+version;
        Long size=0L;
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
            try (CloseableHttpResponse response = client.execute(new HttpGet(getremotezipsizeUrl))) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String bodyAsString = EntityUtils.toString(entity);
                    size=  Long.parseLong(bodyAsString);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return size;
    }



    /**
     * 通过产品名称获取到最新的产品版本
     * @param productname
     * @return
     */
    public static String getNewProductVersionForProductName(String productname){
       String productversion="";
        String getNewProductVersionForProductNameUrl=uri+"/"+productname+"/getNewest";
        try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
            try (CloseableHttpResponse response = client.execute(new HttpGet(getNewProductVersionForProductNameUrl))) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String bodyAsString = EntityUtils.toString(entity);
                    productversion=bodyAsString;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return productversion;
    }

    /**
     * 将获得的json转换成 versioninfo对象
     * @param JSONString
     * @return
     * @throws JSONException
     */
    private static VersionInfo JSONToversionInfo(String JSONString) throws JSONException {
        VersionInfo info = new VersionInfo();
        JSONObject param = null;
        try {
            param = new JSONObject(JSONString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONArray featuresarray = null;
        JSONArray enhancesarray = null;
        JSONArray compatibilitysarray = null;
        JSONArray othersarray = null;
        try {
            featuresarray = param.getJSONArray("features");
            enhancesarray = param.getJSONArray("enhances");
            compatibilitysarray = param.getJSONArray("compatibilitys");
            othersarray = param.getJSONArray("others");
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
        String productname = param.getString("productname");
        String productversion = param.getString("productversion");
        info.setProductname(productname);
        info.setProductversion(productversion);
        info.setFeatures(featuresarraylist);
        info.setEnhances(enhancesarraylist);
        info.setCompatibilitys(compatibilitysarraylist);
        info.setOthers(othersarraylist);
        return info;

    }
    public static void main(String[] args) throws JSONException {
        System.out.println( HttpClientUtils.getRemoteZipSize("iserver","10.1"));
    }
}
