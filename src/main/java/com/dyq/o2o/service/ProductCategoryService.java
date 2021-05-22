package com.dyq.o2o.service;

import com.dyq.o2o.entity.ProductCategory;

import java.util.List;

public interface ProductCategoryService{
    List<ProductCategory> getProductCategoryList(long shopId);
}
