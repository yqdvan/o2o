package com.dyq.o2o.dao;

import com.dyq.o2o.BaseTest;
import com.dyq.o2o.entity.Area;
import com.dyq.o2o.entity.ShopCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class ShopCategoryDaoTest extends BaseTest{
    @Autowired
    private ShopCategoryDao shopCategoryDao;
    @Test
    public void testQueryShopCategory() throws IOException {
        List<ShopCategory> shopCategoryList = shopCategoryDao.queryShopCategory(new ShopCategory());
        System.out.println("dyq- shopCategoryDao test : "+shopCategoryList.size());
        assertEquals(3,shopCategoryList.size());
    }
}
