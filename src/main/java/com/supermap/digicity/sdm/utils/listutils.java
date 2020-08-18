/**
 * Project Name: sdm-upgradetools
 * File Name: listutils
 * Package Name: com.supermap.digicity.sdm.utils
 * Date: 2020/4/30 10:15
 * Copyright (c) 2020,All Rights Reserved.
 */
package com.supermap.digicity.sdm.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: zhangjun
 * @Description:
 * @Date: Create in 10:15 2020/4/30 
 */
public class listutils {


    public static List<Integer> getDiffrent(List<Integer> oldlist, List<Integer> newlist){
        List<Integer>  temp=new ArrayList<>(newlist);
        for(int i=0;i< newlist.size();i++)
        {
            if(oldlist.contains(newlist.get(i))){
                temp.remove(newlist.get(i));
            }
        }
        return temp;
    }
}
