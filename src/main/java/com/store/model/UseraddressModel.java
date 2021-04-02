package com.store.model;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
public class UseraddressModel extends PageModel {

    public Map<String, String> excelHead() {
        Map<String, String> excelHead = new HashMap<>();
        excelHead.put("userid", "用户ID");
        excelHead.put("addressid", "主键");
        excelHead.put("provincename", "省份名称");
        excelHead.put("cityname", "城市名称");
        excelHead.put("areaname", "区域名称");
        excelHead.put("receivename", "收货人姓名");
        excelHead.put("receivephone", "收货人电话");
        excelHead.put("addressdetail", "详细地址");
        excelHead.put("isdefault", "是否默认");
        excelHead.put("lat", "维度");
        excelHead.put("lng", "经度");
        excelHead.put("createtime", "创建时间");
        excelHead.put("valid", "1显示");
        return excelHead;
    }

    @ApiModelProperty(value = "用户ID", required = true)
    private String userid;
    @ApiModelProperty(value = "地址ID", required = false)
    private String addressid;
    @ApiModelProperty(value = "省份名称", required = false)
    private String provincename;
    @ApiModelProperty(value = "城市名称", required = false)
    private String cityname;
    @ApiModelProperty(value = "区域名称", required = false)
    private String areaname;
    @ApiModelProperty(value = "收货人姓名", required = true)
    private String receivename;
    @ApiModelProperty(value = "收货人电话", required = true)
    private String receivephone;
    @ApiModelProperty(value = "详细地址", required = true)
    private String addressdetail;
    @ApiModelProperty(value = "门牌号", required = true)
    private String housenumber;

    @ApiModelProperty(value = "是否默认", required = true)
    private String isdefault;
    @ApiModelProperty(value = "维度", required = true)
    private String lat;
    @ApiModelProperty(value = "经度", required = true)
    private String lng;
    @ApiModelProperty(hidden = true)
    private Date createtime;
    @ApiModelProperty(hidden = true)
    private String valid;

    public String getHousenumber() {
        return housenumber;
    }

    public void setHousenumber(String housenumber) {
        this.housenumber = housenumber;
    }

    /**
     * 获取用户ID
     *
     * @return
     */
    public String getUserid() {
        return userid;
    }

    /**
     * 设置用户ID
     *
     * @param userid
     */
    public void setUserid(String userid) {
        this.userid = userid;
    }

    /**
     * 获取主键
     *
     * @return
     */
    public String getAddressid() {
        return addressid;
    }

    /**
     * 设置主键
     *
     * @param addressid
     */
    public void setAddressid(String addressid) {
        this.addressid = addressid;
    }

    /**
     * 获取省份名称
     *
     * @return
     */
    public String getProvincename() {
        return provincename;
    }

    /**
     * 设置省份名称
     *
     * @param provincename
     */
    public void setProvincename(String provincename) {
        this.provincename = provincename;
    }

    /**
     * 获取城市名称
     *
     * @return
     */
    public String getCityname() {
        return cityname;
    }

    /**
     * 设置城市名称
     *
     * @param cityname
     */
    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    /**
     * 获取区域名称
     *
     * @return
     */
    public String getAreaname() {
        return areaname;
    }

    /**
     * 设置区域名称
     *
     * @param areaname
     */
    public void setAreaname(String areaname) {
        this.areaname = areaname;
    }

    /**
     * 获取收货人姓名
     *
     * @return
     */
    public String getReceivename() {
        return receivename;
    }

    /**
     * 设置收货人姓名
     *
     * @param receivename
     */
    public void setReceivename(String receivename) {
        this.receivename = receivename;
    }

    /**
     * 获取收货人电话
     *
     * @return
     */
    public String getReceivephone() {
        return receivephone;
    }

    /**
     * 设置收货人电话
     *
     * @param receivephone
     */
    public void setReceivephone(String receivephone) {
        this.receivephone = receivephone;
    }

    /**
     * 获取详细地址
     *
     * @return
     */
    public String getAddressdetail() {
        return addressdetail;
    }

    /**
     * 设置详细地址
     *
     * @param addressdetail
     */
    public void setAddressdetail(String addressdetail) {
        this.addressdetail = addressdetail;
    }

    /**
     * 获取是否默认
     *
     * @return
     */
    public String getIsdefault() {
        return isdefault;
    }

    /**
     * 设置是否默认
     *
     * @param isdefault
     */
    public void setIsdefault(String isdefault) {
        this.isdefault = isdefault;
    }

    /**
     * 获取维度
     *
     * @return
     */
    public String getLat() {
        return lat;
    }

    /**
     * 设置维度
     *
     * @param lat
     */
    public void setLat(String lat) {
        this.lat = lat;
    }

    /**
     * 获取经度
     *
     * @return
     */
    public String getLng() {
        return lng;
    }

    /**
     * 设置经度
     *
     * @param lng
     */
    public void setLng(String lng) {
        this.lng = lng;
    }

    /**
     * 获取创建时间
     *
     * @return
     */
    public Date getCreatetime() {
        return createtime;
    }

    /**
     * 设置创建时间
     *
     * @param createtime
     */
    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    /**
     * 获取1显示
     *
     * @return
     */
    public String getValid() {
        return valid;
    }

    /**
     * 设置1显示
     *
     * @param valid
     */
    public void setValid(String valid) {
        this.valid = valid;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public String getFullAddress() {
        StringBuffer sb = new StringBuffer();
        sb.append(provincename).append(" ");
        sb.append(cityname).append(" ");
        sb.append(areaname).append(" ");
        sb.append(addressdetail).append(" ");
        sb.append(housenumber);
        return sb.toString();
    }
}