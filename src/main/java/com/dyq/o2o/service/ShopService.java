package com.dyq.o2o.service;

import com.dyq.o2o.dto.ShopExecution;
import com.dyq.o2o.entity.Shop;
import com.dyq.o2o.exception.ShopOperationException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;

public interface ShopService{
    ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize);
//    ShopExecution addShop(Shop shop, File shopImg);
    ShopExecution addShop(Shop shop, InputStream shopImgIputStream,String fileName) throws ShopOperationException;

    /**
     * 修改店铺
     *
     * @param shop
     * @param
     * @return
     */
    ShopExecution modifyShop(Shop shop, InputStream shopImgIputStream,String fileName) throws ShopOperationException;

    Shop getByShopId(long shopId);
}
