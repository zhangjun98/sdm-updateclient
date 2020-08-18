package com.supermap.digicity.sdm.model;

import java.util.ArrayList;

/**
 * @Description 产品实体类
 * @Auther zhoujinqiao
 * @Data 2020/4/27 16:12
 **/
public class ProduceInfo {
    //产品名称
    private String produceName;
    //远程产品版本
    private ArrayList<String> produceVersion;
    //待下载的版本
    private String downloadProcessVersion;
    //本地安装版本
    private String loaclVersion;
    //本地安装目录
    private String setupLocal;

    //下载产品的安装目录
    private String downloadProduceSetupLocal;



    public String getDownloadProcessVersion() {
        return downloadProcessVersion;
    }

    public void setDownloadProcessVersion(String downloadProcessVersion) {
        this.downloadProcessVersion = downloadProcessVersion;
    }
    public String getDownloadProduceSetupLocal() {
        return downloadProduceSetupLocal;
    }

    public void setDownloadProduceSetupLocal(String downloadProduceSetupLocal) {
        this.downloadProduceSetupLocal = downloadProduceSetupLocal;
    }
    public String getProduceName() {
        return produceName;
    }

    public void setProduceName(String produceName) {
        this.produceName = produceName;
    }

    public ArrayList<String> getProduceVersion() {
        return produceVersion;
    }

    public void setProduceVersion(ArrayList<String> produceVersion) {
        this.produceVersion = produceVersion;
    }

    public String getLoaclVersion() {
        return loaclVersion;
    }

    public void setLoaclVersion(String loaclVersion) {
        this.loaclVersion = loaclVersion;
    }

    public String getSetupLocal() {
        return setupLocal;
    }

    public void setSetupLocal(String setupLocal) {
        this.setupLocal = setupLocal;
    }

    @Override
    public String toString() {
        return "ProduceInfo{" +
                "produceName='" + produceName + '\'' +
                ", produceVersion=" + produceVersion +
                ", downloadProcessVersion='" + downloadProcessVersion + '\'' +
                ", loaclVersion='" + loaclVersion + '\'' +
                ", setupLocal='" + setupLocal + '\'' +
                ", downloadProduceSetupLocal='" + downloadProduceSetupLocal + '\'' +
                '}';
    }
}
