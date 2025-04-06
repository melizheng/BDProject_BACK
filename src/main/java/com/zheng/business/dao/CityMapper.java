package com.zheng.business.dao;

import com.zheng.business.bean.CityCode;
import org.apache.ibatis.annotations.Param;

/**
 * 得到城市的name
 * Date:2022/1/2718:12
 **/
@org.apache.ibatis.annotations.Mapper
public interface CityMapper {
    //已知citycode
    CityCode findCityname(@Param("city_code")int city_code);

}
