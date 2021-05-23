package com.dyq.o2o.service;

import com.dyq.o2o.BaseTest;
import com.dyq.o2o.dto.ShopExecution;
import com.dyq.o2o.dto.ShopStateEnum;
import com.dyq.o2o.entity.Area;
import com.dyq.o2o.entity.PersonInfo;
import com.dyq.o2o.entity.Shop;
import com.dyq.o2o.entity.ShopCategory;
import com.dyq.o2o.exception.ShopOperationException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestExecutionListeners;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ShopServiceTest extends BaseTest{
    @Autowired
    private ShopService shopService;

    @Test
    public void testGetShopList(){
        PersonInfo user = new PersonInfo();
        user.setUserId(1L);
        long employeeId = user.getUserId();
        List<Shop> list = new ArrayList<Shop>();
        Shop shopCondition = new Shop();
        shopCondition.setOwner(user);
        ShopExecution shopExecution = shopService.getShopList(shopCondition,0,100);
        list = shopExecution.getShopList();
        System.out.println("dyq ------------ :"+list.get(0).getOwner().getName());
    }

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

//    @Test
//    public void testModifyShop() throws ShopOperationException ,FileNotFoundException {
//        Shop shop = new Shop();
//        shop.setShopId(24L);
//        shop.setShopName("水果铺子V2改");
//        shop.setPhone("1320167753");
//        File shopImg = new File("/home/yqduan/Pictures/Wallpapers/abc-123.jpg");
//
//        ShopExecution shopExecution = shopService.modifyShop(shop, new FileInputStream(shopImg), shopImg.getName());
//        System.out.println("dyq test modify shop :"+shopExecution.getShop().getShopName());
//    }

}
