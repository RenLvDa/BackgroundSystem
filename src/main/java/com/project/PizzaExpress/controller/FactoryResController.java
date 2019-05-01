package com.project.PizzaExpress.controller;

import com.alibaba.fastjson.JSON;
import com.project.PizzaExpress.service.FactoryRes.FactoryResServiceImpl;
import com.project.PizzaExpress.service.FactoryRes.IFactoryResService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/FactoryRes")
@CrossOrigin
public class FactoryResController {
    @Resource
    private IFactoryResService factoryResService = new FactoryResServiceImpl();

    //查询所有工厂库存信息
    @RequestMapping("/list")
    public String getAllFactoryRes() {
        return JSON.toJSONString(factoryResService.getAllFactoryRes());
    }

    //根据工厂id 查询工厂库存信息（模糊）
    @RequestMapping("/getByIdLike")
    public String getFactoryResByIdLike(@RequestParam String f_id) {
        return JSON.toJSONString(factoryResService.getFactoryResByIdLike(f_id));
    }

    //根据工厂id 查询工厂库存信息（精准）
    @RequestMapping("/getById")
    public String getFactoryResById(@RequestParam String f_id) {
        return JSON.toJSONString(factoryResService.getFactoryResById(f_id));
    }

    //根据原料名 查询工厂库存信息（模糊）
    @RequestMapping("/getByNameLike")
    public String getFactoryResByNameLike(@RequestParam String r_name) {
        return JSON.toJSONString(factoryResService.getFactoryResByNameLike(r_name));
    }

    //根据原料名 查询工厂库存信息（精准）
    @RequestMapping("/getByNameLike")
    public String getFactoryResByName(@RequestParam String r_name) {
        return JSON.toJSONString(factoryResService.getFactoryResByName(r_name));
    }

    //修改工厂库存数量
    @RequestMapping("/updateResNum")
    public String updateFactoryResNum(@RequestParam String f_id,@RequestParam String r_id,@RequestParam int num) {
        return JSON.toJSONString(factoryResService.updateFactoryResNum(f_id,r_id,num));
    }
    
    //修改工厂库存信息

    //增加工厂库存信息条目
    @RequestMapping("/addFacResItem")
    public String addFactoryResItem(@RequestParam String FacResInfo) {
        return JSON.toJSONString(factoryResService.addFactoryResItem(FacResInfo));
    }

    //删除工厂库存信息条目


}
