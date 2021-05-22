package com.dyq.o2o.web.shopadmin;

import com.dyq.o2o.dto.ShopExecution;
import com.dyq.o2o.dto.ShopStateEnum;
import com.dyq.o2o.entity.*;
import com.dyq.o2o.service.AreaService;
import com.dyq.o2o.service.ProductCategoryService;
import com.dyq.o2o.service.ShopCategoryService;
import com.dyq.o2o.service.ShopService;
import com.dyq.o2o.util.CodeUtil;
import com.dyq.o2o.util.HttpServletRequestUtil;
import com.dyq.o2o.util.ImageUtil;
import com.dyq.o2o.util.PathUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("shopadmin")
public class ShopManagementController{
    @Autowired
    private ShopService shopService;
    @Autowired
    private ShopCategoryService shopCategoryService;
    @Autowired
    private AreaService areaService;
    @Autowired
    private ProductCategoryService productCategoryService;



    @RequestMapping(value = "/getshoplist", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getShopList(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        PersonInfo user = new PersonInfo();
        user.setUserId(1L);
        request.getSession().setAttribute("user",user);
        //user = (PersonInfo) request.getSession().getAttribute("user");
        long employeeId = user.getUserId();

        List<Shop> list = new ArrayList<Shop>();
        try {
            Shop shopCondition = new Shop();
            shopCondition.setOwner(user);
            ShopExecution shopExecution = shopService
                    .getShopList(shopCondition,0,100);
            list = shopExecution.getShopList();
            modelMap.put("shopList", list);
            modelMap.put("user", user);
            modelMap.put("success", true);
            // 列出店铺成功之后，将店铺放入session中作为权限验证依据，即该帐号只能操作它自己的店铺
            request.getSession().setAttribute("shopList", list);
        } catch (Exception e) {
            e.printStackTrace();
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
        }
        return modelMap;
    }


    @RequestMapping(value = "/modifyshop",method = RequestMethod.POST)
    @ResponseBody
    private Map<String,Object> modifyShop(HttpServletRequest request){
        System.out.println("dyq debug registerShop is running!");
        Map<String, Object> modelMap = new HashMap<String, Object>();
        // 校验验证码
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "OperationStatusEnum.VERIFYCODE_ERROR.getStateInfo()");
            return modelMap;
        }
        // 1.receive arg , include shop inf img
        ObjectMapper mapper = new ObjectMapper();
        Shop shop = null;
        String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
        try{
            shop = mapper.readValue(shopStr, Shop.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }
        CommonsMultipartFile shopImg = null;
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        if(commonsMultipartResolver.isMultipart(request)){
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
        }

        // 2.edit shop
        if(shop!=null && shop.getShopId()!=null){
            // session todo
            PersonInfo owner = (PersonInfo)request.getSession().getAttribute("user");
            shop.setOwner(owner);
            ShopExecution se = null;
            try {
                if(shopImg == null){
                    se = shopService.modifyShop(shop,null,null);
                }else {
                    se = shopService.modifyShop(shop,shopImg.getInputStream(),shopImg.getOriginalFilename());
                }
                List<Shop> shopList = (List<Shop>) request.getSession().getAttribute("shopList");
                if(shopList == null || shopList.size()==0){
                    shopList = new ArrayList<Shop>();
                }

                // sesion 里有数据库的临时的关于owner的信息
                shopList.add(se.getShop());
                request.getSession().setAttribute("shopList",shopList);

            } catch (IOException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
                return modelMap;
            }
            if(se.getState() == ShopStateEnum.CHECK.getState()){
                modelMap.put("success", true);
            }else {
                modelMap.put("success", false);
                modelMap.put("errMsg", se.getStateInfo());
            }
            return modelMap;
        }else{
            modelMap.put("success", false);
            modelMap.put("errMsg", "please input shop Id!");
            return modelMap;
        }
    }



    @RequestMapping(value = "/getshopbyid",method = RequestMethod.GET)
    // ResponseBody :return json
    @ResponseBody
    private Map<String ,Object>getShopById(HttpServletRequest request){
        Map<String ,Object> modeMap = new HashMap<String ,Object>();
        Long shopId = HttpServletRequestUtil.getLong(request,"shopId");
        if(shopId > -1){
            try{
                Shop shop = shopService.getByShopId(shopId);
                List<Area> areaList = areaService.getAreaList();
                modeMap.put("shop",shop);
                modeMap.put("areaList",areaList);
                modeMap.put("success",true);
            }catch (Exception e){
                modeMap.put("success",false);
                modeMap.put("errMsg",e.getMessage());
            }

        }else {
            modeMap.put("success",false);
            modeMap.put("errMsg","empty shopId");
        }
        return modeMap;
    }

    @RequestMapping(value = "/getshopinitinfo",method = RequestMethod.GET)
    @ResponseBody
    private Map<String ,Object>getShopInitInfo(){
        Map<String ,Object> modeMap = new HashMap<String ,Object>();
        List<ShopCategory> shopCategoryList = new ArrayList<ShopCategory>();
        List<Area> areaList = new ArrayList<Area>();
        try{
            shopCategoryList = shopCategoryService.getShopCategoryList(new ShopCategory());
            areaList = areaService.getAreaList();
            modeMap.put("shopCategoryList",shopCategoryList);
            modeMap.put("areaList",areaList);
            modeMap.put("success",true);
        }catch (Exception e){
            modeMap.put("success",false);
            modeMap.put("errMsg",e.getMessage());
        }
        return modeMap;
    }

    @RequestMapping(value = "/registershop",method = RequestMethod.POST)
    @ResponseBody
    private Map<String,Object> registerShop(HttpServletRequest request){
        System.out.println("dyq debug registerShop is running!");
        Map<String, Object> modelMap = new HashMap<String, Object>();
        // 校验验证码
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "OperationStatusEnum.VERIFYCODE_ERROR.getStateInfo()");
            return modelMap;
        }
        // 1.receive arg , include shop inf img
        ObjectMapper mapper = new ObjectMapper();
        Shop shop = null;
        String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
        try{
            shop = mapper.readValue(shopStr, Shop.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }
        CommonsMultipartFile shopImg = null;
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        if(commonsMultipartResolver.isMultipart(request)){
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
        }else{
            modelMap.put("success", false);
            modelMap.put("errMsg", "upload img cannot be null!");
            return modelMap;
        }
        // 2.registry shop
        if(shop!=null && shopImg!=null){
            PersonInfo owner = new PersonInfo();
            // session todo
            owner.setUserId(1L);
            shop.setOwner(owner);
            ShopExecution se = null;
            try {
                se = shopService.addShop(shop,shopImg.getInputStream(),shopImg.getOriginalFilename());
            } catch (IOException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
                return modelMap;
            }
            if(se.getState() == ShopStateEnum.CHECK.getState()){
                modelMap.put("success", true);
            }else {
                modelMap.put("success", false);
                modelMap.put("errMsg", se.getStateInfo());
            }
            return modelMap;
        }else{
            modelMap.put("success", false);
            modelMap.put("errMsg", "please input shop info!");
            return modelMap;
        }
    }
//    private static void inputStreamToFile(InputStream ins, File file){
//        FileOutputStream os = null;
//        try{
//            os = new FileOutputStream(file);
//            int bytesRead = 0;
//            byte[]buffer = new byte[1024];
//            while ((bytesRead = ins.read(buffer)) != -1){
//                os.write(buffer,0,bytesRead);
//            }
//        } catch (IOException e) {
//            throw  new RuntimeException("调用inputStreamToFile 异常" + e.getMessage());
//        }finally {
//            try{
//                if(os!=null){
//                    os.close();
//                }
//                if(ins!=null){
//                    ins.close();
//                }
//            } catch (IOException e) {
//                throw  new RuntimeException("调用inputStreamToFile 关闭IO异常" + e.getMessage());
//            }
//        }
//    }
}
