package com.dyq.o2o.service.iml;

import com.dyq.o2o.dao.ProductCategoryDao;
import com.dyq.o2o.dto.ProductCategoryExecution;
import com.dyq.o2o.entity.ProductCategory;
import com.dyq.o2o.enums.ProductCategoryStateEnum;
import com.dyq.o2o.exception.ProductCategoryOperationException;
import com.dyq.o2o.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService{
    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Override
    public List<ProductCategory> getProductCategoryList(long shopId) {
        return productCategoryDao.queryProductCategory(shopId);
    }

    @Override
    public ProductCategoryExecution batchAddProductCategory(List<ProductCategory> productCategoryList) throws RuntimeException {
        if(productCategoryList != null && productCategoryList.size() > 0){
            try{
                int effectNum = productCategoryDao.batchInsertProductCategory(productCategoryList);
                if(effectNum <= 0){
                    throw new ProductCategoryOperationException("店铺创建失败!");
                }else {
                    return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
                }
            }catch (Exception e){
                throw new ProductCategoryOperationException("batchAddProductCategory error:" + e.getMessage());
            }

        }else {
            return new ProductCategoryExecution(ProductCategoryStateEnum.EMPTY_LIST);
        }

    }

    @Override
    @Transactional
    public ProductCategoryExecution deleteProductCategory(
            long productCategoryId, long shopId) throws RuntimeException {
        try {
            int effectedNum = productCategoryDao.deleteProductCategory(
                    productCategoryId, shopId);
            if (effectedNum <= 0) {
                throw new RuntimeException("店铺类别删除失败");
            } else {
                return new ProductCategoryExecution(
                        ProductCategoryStateEnum.SUCCESS);
            }

        } catch (Exception e) {
            throw new RuntimeException("deleteProductCategory error: "
                    + e.getMessage());
        }
    }
}
