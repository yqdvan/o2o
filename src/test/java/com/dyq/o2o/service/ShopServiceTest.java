package com.dyq.o2o.service;

import com.dyq.o2o.BaseTest;
import com.dyq.o2o.dto.ShopExecution;
import com.dyq.o2o.dto.ShopStateEnum;
import com.dyq.o2o.entity.Area;
import com.dyq.o2o.entity.PersonInfo;
import com.dyq.o2o.entity.Shop;
import com.dyq.o2o.entity.ShopCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

import static org.junit.Assert.assertEquals;

public class ShopServiceTest extends BaseTest{
    @Autowired
    private ShopService shopService;

    @Test
    public void testAddShop() throws FileNotFoundException {
        Shop shop = new Shop();
        PersonInfo owner = new PersonInfo();
        Area area = new Area();
        ShopCategory shopCategory = new ShopCategory();

        owner.setUserId(1L);
        area.setAreaId(2);
        shopCategory.setShopCategoryId(1L);

        shop.setOwner(owner);
        shop.setArea(area);
        shop.setShopCategory(shopCategory);

        shop.setShopName("测试店铺-改造");
        shop.setShopDesc("test desc");
        shop.setPhone("test123456");
        shop.setShopAddr("test addr");
        shop.setAdvice("in checking test!");
        shop.setCreateTime(new Date());
        shop.setEnableStatus(ShopStateEnum.CHECK.getState());

        File shopImg = new File("/home/yqduan/Pictures/pubg.jpg");
        InputStream inputStream = new FileInputStream(shopImg);
        ShopExecution se = shopService.addShop(shop,inputStream,shopImg.getName());
        assertEquals(ShopStateEnum.CHECK.getState(),se.getState());

    }

}
