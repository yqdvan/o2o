package com.dyq.o2o.dto;

import com.dyq.o2o.entity.Shop;

import java.util.List;

public class ShopExecution{
    //结果状态
    private int state;

    //状态标识
    private String stateInfo;

    //shop
    private int count;

    //operated shop
    private Shop shop;

    private List<Shop> shopList;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public List<Shop> getShopList() {
        return shopList;
    }

    public void setShopList(List<Shop> shopList) {
        this.shopList = shopList;
    }

    public ShopExecution(){

    }

    //operate shop when failed constructor
    public ShopExecution(ShopStateEnum shopStateEnum){
        this.state = shopStateEnum.getState();
        this.stateInfo = shopStateEnum.getStateInfo();
    }

    //operate shop when success constructor
    public ShopExecution(ShopStateEnum shopStateEnum,Shop shop){
        this.state = shopStateEnum.getState();
        this.stateInfo = shopStateEnum.getStateInfo();
        this.shop = shop;
    }

    //operate shop when success constructor
    public ShopExecution(ShopStateEnum shopStateEnum,List<Shop> shopList){
        this.state = shopStateEnum.getState();
        this.stateInfo = shopStateEnum.getStateInfo();
        this.shopList = shopList;
    }
}
