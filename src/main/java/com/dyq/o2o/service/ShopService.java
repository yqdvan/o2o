package com.dyq.o2o.service;

import com.dyq.o2o.dto.ShopExecution;
import com.dyq.o2o.entity.Shop;
import com.dyq.o2o.exception.ShopOperationException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;

public interface ShopService{
//    ShopExecution addShop(Shop shop, File shopImg);
    ShopExecution addShop(Shop shop, InputStream shopImgIputStream,String fileName) throws ShopOperationException;

    /**
     * 修改店铺
     *
     * @param shop
     * @param shopImg
     * @return
     */
    ShopExecution modifyShop(Shop shop, InputStream shopImgIputStream,String fileName) throws ShopOperationException;
}
