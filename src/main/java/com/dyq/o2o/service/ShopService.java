package com.dyq.o2o.service;

import com.dyq.o2o.dto.ShopExecution;
import com.dyq.o2o.entity.Shop;
import com.dyq.o2o.exception.ShopOperationException;

import java.io.File;
import java.io.InputStream;

public interface ShopService{
//    ShopExecution addShop(Shop shop, File shopImg);
    ShopExecution addShop(Shop shop, InputStream shopImgIputStream,String fileName) throws ShopOperationException;
}
