package com.zheng.business.service;

import com.zheng.business.bean.CityCode;
import com.zheng.business.bean.RrException;
import com.zheng.business.dao.CityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 给服务调用，查看城市码对应城市名称
 * Date:2022/1/2718:19
 **/
@Service
public class CitycodeService {
    @Autowired
    CityMapper CityMapper;

    public CityCode findCityname(int citycode){
        CityCode city= CityMapper.findCityname(citycode);
        if(null==city)
            throw new RrException(30006, "不存在该城市");
        else
            return city;
    }
}
