package com.supermap.digicity.sdm.utils.loadxml;

import com.supermap.digicity.sdm.model.RemoteInfo;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.*;

/**
 * 加载本地的xml
 */
public  class LoadStartupInfoUtils {

    private static Document document;

    static{
        LoadStartupInfoUtils utils=new LoadStartupInfoUtils();

        SAXReader reader = new SAXReader();
        InputStream is=utils.getClass().getResourceAsStream("/startup.xml");
        try {
            document = reader.read(is);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取远程信息
     * @return
     */
    public static RemoteInfo getRemoteInfo(){
        RemoteInfo info =new RemoteInfo();
        Element rootElement = document.getRootElement();
        Iterator iterator = rootElement.elementIterator();
        //获取到节点元素
            Element httpURL = (Element) iterator.next();
            info.setHTTPURL(httpURL.getStringValue());
         return info;
    }
    public static void main(String[] args) throws Exception {


    }


}