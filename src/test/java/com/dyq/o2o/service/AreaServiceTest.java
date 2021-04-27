package com.dyq.o2o.service;

import com.dyq.o2o.BaseTest;
import com.dyq.o2o.entity.Area;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class AreaServiceTest extends BaseTest{
    @Autowired
    private AreaService areaService;
    @Test
    public void testGetAreaList(){
        System.out.println("--- dyq Debug start! ---");
        List<Area> areaList = areaService.getAreaList();
        assertEquals("丁香 ",areaList.get(0).getAreaName());
        System.out.println("dyq Debug  : "+areaList.get(0).getAreaName());
    }
}
