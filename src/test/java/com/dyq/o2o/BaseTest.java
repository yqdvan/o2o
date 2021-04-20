package com.dyq.o2o;

import com.dyq.o2o.dao.AreaDao;
import com.dyq.o2o.entity.Area;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * 用来配置spring 和junit 整合
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件在的位置
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class BaseTest{

}
