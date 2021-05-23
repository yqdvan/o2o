package com.dyq.o2o.service.iml;

import com.dyq.o2o.dao.ShopCategoryDao;
import com.dyq.o2o.dao.ShopDao;
import com.dyq.o2o.dto.ShopExecution;
import com.dyq.o2o.dto.ShopStateEnum;
import com.dyq.o2o.entity.Shop;
import com.dyq.o2o.entity.ShopCategory;
import com.dyq.o2o.exception.ShopOperationException;
import com.dyq.o2o.service.ShopService;
import com.dyq.o2o.util.FileUtil;
import com.dyq.o2o.util.ImageUtil;
import com.dyq.o2o.util.PageCalculator;
import com.dyq.o2o.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

@Service
public class ShopServiceImpl implements ShopService{
    @Autowired
    ShopDao shopDao;

    @Autowired
    private ShopCategoryDao shopCategoryDao;

    @Override
    public ShopExecution getShopList(Shop shopCondition, int pageIndex,
                                     int pageSize) {
        int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
        List<Shop> shopList = shopDao.queryShopList(shopCondition, rowIndex,
                pageSize);
        int count = shopDao.queryShopCount(shopCondition);
        ShopExecution se = new ShopExecution();
        if (shopList != null) {
            se.setShopList(shopList);
            se.setCount(count);
        } else {
            se.setState(ShopStateEnum.INNER_ERROR.getState());
        }
        return se;
    }

    @Override
    public Shop getByShopId(long shopId) {
        if(shopId < 0){
            return  null;
        }else {
            Shop shop = shopDao.queryByShopId(shopId);
            return shop;
        }
    }

    @Override
    @Transactional
    public ShopExecution addShop(Shop shop, CommonsMultipartFile shopImg)throws ShopOperationException {
        // null value check
        if(shop == null){
            return  new ShopExecution(ShopStateEnum.NULL_SHOP);
        }

        try{
            // set ini value
            shop.setEnableStatus(0);
            shop.setCreateTime(new Date());
            shop.setLastEditTime(new Date());

            // add shop inf
            int effectiveNum = shopDao.insertShop(shop);

            if(effectiveNum <= 0){
                throw new ShopOperationException("店铺创建失败!");
            }else{
                if (shopImg!=null){
                    // save img
                    try{
                        addShopImg(shop,shopImg);
                    }catch (Exception e){
                        throw new ShopOperationException("add shop img 失败" + e.getMessage());
                    }
                    // update shop img
                    effectiveNum = shopDao.updateShop(shop);
                    if(effectiveNum<=0){
                        throw new ShopOperationException("update img addr 失败!");
                    }
                }
            }

        }catch (Exception e){
            throw new ShopOperationException("addShop error: "+e.getMessage());
        }
        return new ShopExecution(ShopStateEnum.CHECK,shop);
    }
//    @Override
//    @Transactional
//    public ShopExecution addShop(Shop shop, CommonsMultipartFile shopImg)
//            throws RuntimeException {
//        if (shop == null) {
//            return new ShopExecution(ShopStateEnum.NULL_SHOP);
//        }
//        try {
//            shop.setEnableStatus(0);
//            shop.setCreateTime(new Date());
//            shop.setLastEditTime(new Date());
//            if (shop.getShopCategory() != null) {
//                Long shopCategoryId = shop.getShopCategory()
//                        .getShopCategoryId();
//                ShopCategory sc = new ShopCategory();
//                sc = shopCategoryDao.queryShopCategory(shopCategoryId);
//                ShopCategory parentCategory = new ShopCategory();
//                parentCategory.setShopCategoryId(sc.getParentId());
//                shop.setParentCategory(parentCategory);
//            }
//            int effectedNum = shopDao.insertShop(shop);
//            if (effectedNum <= 0) {
//                throw new RuntimeException("店铺创建失败");
//            } else {
//                try {
//                    if (shopImg != null) {
//                        addShopImg(shop, shopImg);
//                        effectedNum = shopDao.updateShop(shop);
//                        if (effectedNum <= 0) {
//                            throw new RuntimeException("创建图片地址失败");
//                        }
//                    }
//                } catch (Exception e) {
//                    throw new RuntimeException("addShopImg error: "
//                            + e.getMessage());
//                }
//                // 执行增加shopAuthMap操作
//                ShopAuthMap shopAuthMap = new ShopAuthMap();
//                shopAuthMap.setEmployeeId(shop.getOwnerId());
//                shopAuthMap.setShopId(shop.getShopId());
//                shopAuthMap.setName("");
//                shopAuthMap.setTitle("Owner");
//                shopAuthMap.setTitleFlag(1);
//                shopAuthMap.setCreateTime(new Date());
//                shopAuthMap.setLastEditTime(new Date());
//                shopAuthMap.setEnableStatus(1);
//                try {
//                    effectedNum = shopAuthMapDao.insertShopAuthMap(shopAuthMap);
//                    if (effectedNum <= 0) {
//                        throw new RuntimeException("授权创建失败");
//                    } else {// 创建成功
//                        return new ShopExecution(ShopStateEnum.CHECK, shop);
//                    }
//                } catch (Exception e) {
//                    throw new RuntimeException("insertShopAuthMap error: "
//                            + e.getMessage());
//                }
//
//            }
//        } catch (Exception e) {
//            throw new RuntimeException("insertShop error: " + e.getMessage());
//        }
//    }


    //    @Override
//    public ShopExecution modifyShop(Shop shop, InputStream shopImgIputStream,String fileName) throws ShopOperationException {
//        if(shop == null || shop.getShopId() == null){
//            return new ShopExecution(ShopStateEnum.NULL_SHOP);
//        }else {
//            try {
//                if (shopImgIputStream != null && !fileName.equals("")) {
//                    Shop shopTemp = shopDao.queryByShopId(shop.getShopId());
//                    if (shopTemp.getShopImg() != null) {
//                        ImageUtil.deleteFileOrPath(shopTemp.getShopImg());
//                    }
//                    addShopImg(shop, shopImgIputStream, fileName);
//                }
//
//                shop.setLastEditTime(new Date());
//                int effectiveNum = shopDao.updateShop(shop);
//                if (effectiveNum <= 0) {
//                    return new ShopExecution(ShopStateEnum.INNER_ERROR);
//                } else {
//                    shop = shopDao.queryByShopId(shop.getShopId());
//                    return new ShopExecution(ShopStateEnum.SUCCESS, shop);
//                }
//            } catch (Exception e) {
//                throw new ShopOperationException("modify error! " + e.getMessage());
//            }
//        }
//    }
    @Override
    @Transactional
    public ShopExecution modifyShop(Shop shop, CommonsMultipartFile shopImg)
            throws RuntimeException {
        if (shop == null || shop.getShopId() == null) {
            return new ShopExecution(ShopStateEnum.NULL_SHOPID);
        } else {
            try {
                if (shopImg != null) {
                    Shop tempShop = shopDao.queryByShopId(shop.getShopId());
                    if (tempShop.getShopImg() != null) {
                        FileUtil.deleteFile(tempShop.getShopImg());
                    }
                    addShopImg(shop, shopImg);
                }
                shop.setLastEditTime(new Date());
                int effectedNum = shopDao.updateShop(shop);
                if (effectedNum <= 0) {
                    return new ShopExecution(ShopStateEnum.INNER_ERROR);
                } else {// 创建成功
                    shop = shopDao.queryByShopId(shop.getShopId());
                    return new ShopExecution(ShopStateEnum.SUCCESS, shop);
                }
            } catch (Exception e) {
                throw new RuntimeException("modifyShop error: "
                        + e.getMessage());
            }
        }
    }

//    private void addShopImg(Shop shop, InputStream shopImgInputStream,String fileName) {
//        // get shop img dir relative addr
//        String dest = PathUtil.getShopImagePath(shop.getShopId());
//        String shopImgAddr = ImageUtil.generateThumbnail(shopImgInputStream,fileName,dest);
//        System.out.println("dyq-debug :"+"shopImgAddr: " + shopImgAddr);
//        shop.setShopImg(shopImgAddr);
//    }
    private void addShopImg(Shop shop, CommonsMultipartFile shopImg) {
        String dest = FileUtil.getShopImagePath(shop.getShopId());
        String shopImgAddr = ImageUtil.generateThumbnail(shopImg, dest);
        shop.setShopImg(shopImgAddr);
    }
}
