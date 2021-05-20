package com.dyq.o2o.dao;

import com.dyq.o2o.entity.Shop;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShopDao{

    /**
     * 分页查询店铺,可输入的条件有：店铺名（模糊），店铺状态，店铺Id,店铺类别,区域ID
     *
     * @param shopCondition
     * @param rowIndex
     * @param pageSize
     * @return
     */
    List<Shop> queryShopList(@Param("shopCondition") Shop shopCondition,
                             @Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);

    /**
     * 返回queryShopList总数
     *
     * @param shopCondition
     * @return
     */
    int queryShopCount(@Param("shopCondition") Shop shopCondition);


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
