package com.dyq.o2o.dao;

import com.dyq.o2o.entity.Area;

import java.util.List;

public interface AreaDao{
    /**
     * 列出区域劣币啊
     * @return areaList
     */
    List<Area> queryArea();
}
