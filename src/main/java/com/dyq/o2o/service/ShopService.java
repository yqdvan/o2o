package com.dyq.o2o.service;

import com.dyq.o2o.dto.ShopExecution;
import com.dyq.o2o.entity.Shop;

import java.io.File;

public interface ShopService{
    ShopExecution addShop(Shop shop, File shopImg);
}
