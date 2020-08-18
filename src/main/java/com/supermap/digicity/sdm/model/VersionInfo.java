/**
 * Project Name: sdm-upgradetools
 * File Name: VersionInfo
 * Package Name: com.supermap.digicity.sdm.model
 * Date: 2020/5/9 17:59
 * Copyright (c) 2020,All Rights Reserved.
 */
package com.supermap.digicity.sdm.model;

import java.util.List;

/**
 * @Author: zhangjun
 * @Description:
 * @Date: Create in 17:59 2020/5/9 
 */
public class VersionInfo {
    String productname;
    String productversion;
    List<String> features;
    List<String> enhances;
    List<String> compatibilitys;
    List<String> others;

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getProductversion() {
        return productversion;
    }

    public void setProductversion(String productversion) {
        this.productversion = productversion;
    }

    public List<String> getFeatures() {
        return features;
    }

    public void setFeatures(List<String> features) {
        this.features = features;
    }

    public List<String> getEnhances() {
        return enhances;
    }

    public void setEnhances(List<String> enhances) {
        this.enhances = enhances;
    }

    public List<String> getCompatibilitys() {
        return compatibilitys;
    }

    public void setCompatibilitys(List<String> compatibilitys) {
        this.compatibilitys = compatibilitys;
    }

    public List<String> getOthers() {
        return others;
    }

    public void setOthers(List<String> others) {
        this.others = others;
    }
}
