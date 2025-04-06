package com.zheng.business.bean;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * author:
 * Date:2022/2/1320:33
 **/
public class ListAndCount {
    ArrayList<LinkedHashMap> data;
    Integer count;

    public ListAndCount() {
    }

    public ListAndCount(ArrayList<LinkedHashMap> data) {
        this.data = data;
    }

    public ListAndCount(ArrayList<LinkedHashMap> data, Integer count) {
        this.data = data;
        this.count = count;
    }

    public ArrayList<LinkedHashMap> getData() {
        return data;
    }

    public void setData(ArrayList<LinkedHashMap> data) {
        this.data = data;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
