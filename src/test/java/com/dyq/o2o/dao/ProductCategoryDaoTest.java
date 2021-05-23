package com.dyq.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import com.dyq.o2o.BaseTest;
import com.dyq.o2o.entity.ProductCategory;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ProductCategoryDaoTest extends BaseTest {
	@Autowired
	private ProductCategoryDao productCategoryDao;

	@Test
	public void testAInsertProductCategory() throws Exception {
		ProductCategory productCategory = new ProductCategory();
		productCategory.setProductCategoryName("商品类别1");
		productCategory.setProductCategoryDesc("测试商品类别");
		productCategory.setPriority(1);
		productCategory.setCreateTime(new Date());
		productCategory.setLastEditTime(new Date());
		productCategory.setShopId(23L);
		ProductCategory productCategory2 = new ProductCategory();
		productCategory2.setProductCategoryName("商品类别2");
		productCategory2.setProductCategoryDesc("测试商品类别2");
		productCategory2.setPriority(2);
		productCategory2.setCreateTime(new Date());
		productCategory2.setLastEditTime(new Date());
		productCategory2.setShopId(23L);
		List<ProductCategory> productCategoryList = new ArrayList<ProductCategory>();

		productCategoryList.add(productCategory);
		productCategoryList.add(productCategory2);

		int effectedNum = productCategoryDao
				.batchInsertProductCategory(productCategoryList);

		assertEquals(2, effectedNum);
	}

	@Test
	public void testQueryByShopId() throws Exception {
		Long shopId = 24L;
		List<ProductCategory> productCategoryList = productCategoryDao.queryProductCategory(24L);
		assertEquals(1, productCategoryList.size());
		System.out.println(" dya -------- " + productCategoryList.size());
		System.out.println(productCategoryList.get(0).toString());

	}

	@Test
	public void testCDeleteProductCategory() throws Exception {
		long shopId = 24;
		List<ProductCategory> productCategoryList = productCategoryDao
				.queryProductCategory(shopId);
		int effectedNum = productCategoryDao.deleteProductCategory(
				productCategoryList.get(0).getProductCategoryId(), shopId);
		assertEquals(1, effectedNum);
		effectedNum = productCategoryDao.deleteProductCategory(
				productCategoryList.get(1).getProductCategoryId(), shopId);
		assertEquals(1, effectedNum);
	}
}
