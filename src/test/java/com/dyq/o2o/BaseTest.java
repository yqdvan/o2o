package com.dyq.o2o;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;



/**
 * 用来配置spring 和junit 整合
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉junit spring配置文件在的位置
@ContextConfiguration({"classpath:spring/spring-dao.xml","classpath:spring/spring-service.xml"})
public class BaseTest{

}
