package com.dyq.o2o.service;

import com.dyq.o2o.entity.ShopCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShopCategoryService{
    List<ShopCategory> getShopCategoryList(@Param("shopCategoryCondition")ShopCategory shopCategoryCondition);
}
