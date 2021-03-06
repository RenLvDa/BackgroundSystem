package com.project.PizzaExpress.service.FactoryRes;

import com.alibaba.fastjson.JSONObject;
import com.project.PizzaExpress.dao.FactoryResDAO;
import com.project.PizzaExpress.dao.FactoryResourceDAO;
import com.project.PizzaExpress.dao.ResourceDAO;
import com.project.PizzaExpress.entity.FactoryResEntity;
import com.project.PizzaExpress.entity.FactoryResourceEntity;
import com.project.PizzaExpress.entity.ResourceEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class FactoryResServiceImpl implements IFactoryResService{

    @Autowired
    private ResourceDAO resourceDao;
    @Autowired
    private FactoryResourceDAO factoryResourceDAO;
    @Autowired
    private FactoryResDAO factoryResDAO;

    //查
    @Override
    public JSONObject getAllFactoryRes() {
        JSONObject result = new JSONObject();
        List<FactoryResEntity> query =  factoryResDAO.queryAll();
        result.put("errorCode",0);
        result.put("list",query);
        result.put("total",query.size());
        return result;
    }

    @Override
    public JSONObject getAllFactoryResByPage(Integer pno, Integer pageSize){
        JSONObject result = new JSONObject();
        int startIndex = (pno - 1) * pageSize;
        List<FactoryResEntity> query = factoryResDAO.queryAllByPage(startIndex,pageSize);
        String total = factoryResDAO.queryFactoryResSize();
        result.put("errorCode",0);
        result.put("list",query);
        result.put("total",total);
        return result;
    }

    @Override
    public JSONObject getFactoryResByFIdLike(String f_id){
        JSONObject result = new JSONObject();
        List<FactoryResEntity> query = factoryResDAO.queryLike("%" + f_id + "%");
        if(ObjectUtils.isEmpty(query)){
            result.put("errorCode",1);
            result.put("errorMsg","no such factory ID");
        }else{
            result.put("errorCode",0);
            result.put("SuccessQuery",query);
            result.put("total",query.size());
        }
        return result;
    }

    @Override
    public JSONObject getFactoryResByFId(String f_id){
        JSONObject result = new JSONObject();
        List<FactoryResEntity> query = factoryResDAO.query(f_id);
        if(ObjectUtils.isEmpty(query)){
            result.put("errorCode",1);
            result.put("errorMsg","no such factory ID");
        }else{
            result.put("errorCode",0);
            result.put("SuccessQuery",query);
            result.put("total",query.size());
        }
        return result;
    }

    @Override
    public JSONObject getFactoryResByRNameLike(String r_name){
        JSONObject result = new JSONObject();
        List<FactoryResEntity> query = factoryResDAO.queryByNameLike("%" + r_name + "%");
        if(ObjectUtils.isEmpty(query)){
            result.put("errorCode",1);
            result.put("errorMsg","no such resource name");
        }else{
            result.put("errorCode",0);
            result.put("SuccessQuery",query);
            result.put("total",query.size());
        }
        return result;
    }

    @Override
    public JSONObject getFactoryResByRName(String r_name){
        JSONObject result = new JSONObject();
        List<FactoryResEntity> query = factoryResDAO.queryByName(r_name);
        if(ObjectUtils.isEmpty(query)){
            result.put("errorCode",1);
            result.put("errorMsg","no such resource name");
        }else{
            result.put("errorCode",0);
            result.put("SuccessQuery",query);
            result.put("total",query.size());
        }
        return result;
    }

    @Override
    public JSONObject getFactoryResByRId(String r_id){
        JSONObject result = new JSONObject();
        List<FactoryResEntity> query = factoryResDAO.queryByRId(r_id);
        if(ObjectUtils.isEmpty(query)){
            result.put("errorCode",1);
            result.put("errorMsg","no such resource ID");
        }else{
            result.put("errorCode",0);
            result.put("SuccessQuery",query);
            result.put("total",query.size());
        }
        return result;
    }

    @Override
    public JSONObject getFactoryResByFIdAndRId(String f_id,String r_id){
        JSONObject result = new JSONObject();
        List<FactoryResEntity> query = factoryResDAO.queryByFidAndRid(f_id,r_id);
        if(ObjectUtils.isEmpty(query)){
            result.put("errorCode",1);
            result.put("errorMsg","no such data");
        }else{
            result.put("errorCode",0);
            result.put("SuccessQuery",query.get(0));
        }
        return result;
    }

    //改
    public JSONObject updateFactoryResNum(String f_id,String r_id,int num){
        JSONObject result = new JSONObject();
        factoryResourceDAO.updateResourceNum(f_id,r_id,num);
        List<FactoryResEntity> query = factoryResDAO.queryByFidAndRid(f_id,r_id);
        result.put("errorCode",0);
        result.put("SuccessUpdate",query.get(0));
        return result;
    }

    //增
    public JSONObject addFactoryResItem(String FacResInfo){
        JSONObject result = new JSONObject();
        if (FacResInfo == null || FacResInfo.equals(""))
        {
            result.put("errorCode", 3);
            result.put("errorMsg", "No FacResInfo information");
        }else {
            FactoryResEntity factoryResEntity = FactoryResEntity.fromJsonString(FacResInfo);
            FactoryResourceEntity factoryResourceEntity = FactoryResourceEntity.fromJsonString(FacResInfo);
            ResourceEntity resourceEntity = ResourceEntity.fromJsonString(FacResInfo);
            if(factoryResEntity == null){
                result.put("errorCode", 4);
                result.put("errorMsg", "Lack of necessary factory Resource information");
            }else if(factoryResDAO.queryByFidAndRid(factoryResEntity.getF_id(),factoryResEntity.getR_id()).size() != 0){
                result.put("errorCode", 1);
                result.put("errorMsg", "Factory resource item exists");
            }else{
                if(resourceDao.queryByRid(factoryResEntity.getR_id()).size() == 0){
                    resourceDao.insert(resourceEntity);
                }
                factoryResourceDAO.insert(factoryResourceEntity);
                if(factoryResourceDAO.queryByFidAndRid(factoryResEntity.getF_id(),factoryResEntity.getR_id()).size() != 0
                        && resourceDao.queryByRid(factoryResEntity.getR_id()).size() != 0){
                    result.put("errorCode",0);
                    result.put("successInsert",factoryResEntity);
                }else{
                    result.put("errorCode", 2);
                    result.put("errorMsg", "System Error : Insert Error");
                }
            }
        }
        return result;
    }
}
