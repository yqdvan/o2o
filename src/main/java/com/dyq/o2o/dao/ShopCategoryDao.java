package com.dyq.o2o.dao;

import com.dyq.o2o.entity.ProductCategory;
import com.dyq.o2o.entity.ShopCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShopCategoryDao{
    /**
     * 新增商品类别
     *
     * @param productCategoryList
     *
     * @return effectedNum
     */
    int batchInsertProductCategory(List<ProductCategory> productCategoryList);

    List<ShopCategory> queryShopCategory(@Param("shopCategoryCondition") ShopCategory shopCategory);
}
