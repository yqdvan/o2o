package com.dyq.o2o.dao;

import com.dyq.o2o.entity.Shop;

public interface ShopDao{
    /**
     * add new shop
     * @param shop
     * @return 1 success , -1 failed
     */
    int insertShop(Shop shop);

    /**
     *
     * @param shop
     * @return
     */
    int updateShop(Shop shop);

    /**
     *
     * @param shopId
     * @return
     */
    Shop queryByShopId(long shopId);
}
