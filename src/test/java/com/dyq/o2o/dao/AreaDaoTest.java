package com.dyq.o2o.dao;

import com.dyq.o2o.BaseTest;
import com.dyq.o2o.entity.Area;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class AreaDaoTest extends BaseTest{

    @Autowired
    private AreaDao areaDao;

    @Test
    public void testQueryArea() throws IOException {
        List<Area> areaList = areaDao.queryArea();

        for (Area area : areaList) {
            System.out.println("dyq : " + area.getAreaName());
        }
        System.out.println("dyq : "+areaList.size());

    }
}
