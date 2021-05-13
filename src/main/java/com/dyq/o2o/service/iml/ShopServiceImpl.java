package com.dyq.o2o.service.iml;

import com.dyq.o2o.dao.ShopDao;
import com.dyq.o2o.dto.ShopExecution;
import com.dyq.o2o.dto.ShopStateEnum;
import com.dyq.o2o.entity.Shop;
import com.dyq.o2o.exception.ShopOperationException;
import com.dyq.o2o.service.ShopService;
import com.dyq.o2o.util.ImageUtil;
import com.dyq.o2o.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.util.Date;

@Service
public class ShopServiceImpl implements ShopService{
    @Autowired
    ShopDao shopDao;

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
    public ShopExecution addShop(Shop shop, InputStream shopImgIputStream,String fileName)throws ShopOperationException {
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
                if (shopImgIputStream!=null){
                    // save img
                    try{
                        addShopImg(shop,shopImgIputStream,fileName);
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

    @Override
    public ShopExecution modifyShop(Shop shop, InputStream shopImgIputStream,String fileName) throws ShopOperationException {
        if(shop == null || shop.getShopId() == null){
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }else {
            try {
                if (shopImgIputStream != null && !fileName.equals("")) {
                    Shop shopTemp = shopDao.queryByShopId(shop.getShopId());
                    if (shopTemp.getShopImg() != null) {
                        ImageUtil.deleteFileOrPath(shopTemp.getShopImg());
                    }
                    addShopImg(shop, shopImgIputStream, fileName);
                }

                shop.setLastEditTime(new Date());
                int effectiveNum = shopDao.updateShop(shop);
                if (effectiveNum <= 0) {
                    return new ShopExecution(ShopStateEnum.INNER_ERROR);
                } else {
                    shop = shopDao.queryByShopId(shop.getShopId());
                    return new ShopExecution(ShopStateEnum.SUCCESS, shop);
                }
            } catch (Exception e) {
                throw new ShopOperationException("modify error! " + e.getMessage());
            }
        }
    }

    private void addShopImg(Shop shop, InputStream shopImgInputStream,String fileName) {
        // get shop img dir relative addr
        String dest = PathUtil.getShopImagePath(shop.getShopId());
        String shopImgAddr = ImageUtil.generateThumbnail(shopImgInputStream,fileName,dest);
        System.out.println("dyq-debug :"+"shopImgAddr: " + shopImgAddr);
        shop.setShopImg(shopImgAddr);
    }
}
