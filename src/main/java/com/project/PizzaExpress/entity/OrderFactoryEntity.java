package com.project.PizzaExpress.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class OrderFactoryEntity {

    private String o_id;
    private String f_list;

    public OrderFactoryEntity(String o_id, String f_list)
    {
        this.o_id = o_id;
        this.f_list = f_list;
    }

    public String getO_id() {
        return o_id;
    }

    public void setO_id(String o_id) {
        this.o_id = o_id;
    }

    public String getF_list() {
        return f_list;
    }

    public void setF_list(String f_list) {
        this.f_list = f_list;
    }
}
