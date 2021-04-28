package com.dyq.o2o.dao;

import com.dyq.o2o.BaseTest;
import com.dyq.o2o.entity.Area;
import com.dyq.o2o.entity.PersonInfo;
import com.dyq.o2o.entity.Shop;
import com.dyq.o2o.entity.ShopCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static junit.framework.TestCase.assertEquals;

public class ShopDaoTest extends BaseTest{
    @Autowired
    private ShopDao shopDao;
    @Test
    public void testInsertShop(){
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

        shop.setShopName("测试店铺4");
        shop.setShopDesc("test desc");
        shop.setPhone("test123456");
        shop.setShopAddr("test addr");
        shop.setAdvice("in checking test!");
        shop.setCreateTime(new Date());
        shop.setShopImg("test img");
        shop.setEnableStatus(1);
        shop.setPriority(66);

        int effectedNum = shopDao.insertShop(shop);
        assertEquals(1,effectedNum);
        System.out.println("##dyq-- : " + shop.getShopId());
    }

    @Test
    public void testUpdateShop(){
        Shop shop = new Shop();
        shop.setShopId(8L);

        shop.setShopName("更改店铺名字2");
        shop.setShopDesc("描述一下desc");
        shop.setShopAddr("更新的地址addr");
        shop.setLastEditTime(new Date());


        int effectedNum = shopDao.updateShop(shop);
        assertEquals(1,effectedNum);
        System.out.println("##dyq-- : " + shop.getShopId());
    }
}
