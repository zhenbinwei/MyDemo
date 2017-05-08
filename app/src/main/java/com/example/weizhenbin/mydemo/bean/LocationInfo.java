package com.example.weizhenbin.mydemo.bean;

import java.io.Serializable;

/**
 * @author zhenbinwei   (作者)
 * @version V1.0
 * @Title: LocationInfo
 * @ProjectName Show
 * @Package com.weizhenbin.show.bean
 * @date 2016/2/25 15:46
 */
public class LocationInfo implements Serializable {

    //纬度
    public double latitude;

    //经度
    public double longitude;

    //国家
    public String country;
    public String countryCode;
    //省份
    public String province;
    //市
    public String city;
    public String cityCode;
    //区
    public String district;
    //街道
    public String street;
    //门牌号
    public String streetNumber;
    //地址
    public String addrStr;



    @Override
    public String toString() {
        return "LocationInfo{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", country='" + country + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", cityCode='" + cityCode + '\'' +
                ", district='" + district + '\'' +
                ", street='" + street + '\'' +
                ", streetNumber='" + streetNumber + '\'' +
                ", addrStr='" + addrStr + '\'' +
                '}';
    }
}
