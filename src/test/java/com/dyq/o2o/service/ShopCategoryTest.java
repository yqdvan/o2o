package com.dyq.o2o.service;

import com.dyq.o2o.BaseTest;
import com.dyq.o2o.entity.Area;
import com.dyq.o2o.entity.ShopCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ShopCategoryTest extends BaseTest{
    @Autowired
    private ShopCategoryService shopCategoryService;
    @Test
    public void testGetShopCategoryList(){
        System.out.println("--- dyq Debug start! ---");
        List<ShopCategory> shopCategoryList = shopCategoryService.getShopCategoryList(new ShopCategory());
        assertEquals(3,shopCategoryList.size());
        System.out.println("dyq Debug ShopCategoryService  : "+shopCategoryList.get(0).getShopCategoryName());
    }
}
