package cn.sczhckj.kitchen.data.bean;

import cn.sczhckj.kitchen.data.bean.kitchen.DetailBean;

/**
 * @describe: 数据请求通用的Bean
 * @author: Like on 2016/11/25.
 * @Email: 572919350@qq.com
 */

public class RequestCommonBean {

    private String deviceId;//用于开桌设备ID
    private Integer foodId;//菜品ID
    private Integer cateId;//菜品分类ID
    private Integer recordId;//消费记录ID
    private Integer detailId;//明细记录ID
    private String name;//用户姓名，在后厨端作为菜品名称
    private Integer type;//类型，用于版本检测0-点菜端 1-后厨端

    private DetailBean detail;//后厨单个菜品信息
    private Integer tableId;//台桌ID
    private String tableName;//桌号

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getFoodId() {
        return foodId;
    }

    public void setFoodId(Integer foodId) {
        this.foodId = foodId;
    }

    public Integer getCateId() {
        return cateId;
    }

    public void setCateId(Integer cateId) {
        this.cateId = cateId;
    }

    public Integer getRecordId() {
        return recordId;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public Integer getDetailId() {
        return detailId;
    }

    public void setDetailId(Integer detailId) {
        this.detailId = detailId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DetailBean getDetail() {
        return detail;
    }

    public void setDetail(DetailBean detail) {
        this.detail = detail;
    }

    public Integer getTableId() {
        return tableId;
    }

    public void setTableId(Integer tableId) {
        this.tableId = tableId;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "{" +
                "deviceId='" + deviceId + '\'' +
                ", foodId=" + foodId +
                ", cateId=" + cateId +
                ", recordId=" + recordId +
                ", detailId=" + detailId +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", detail=" + detail +
                ", tableId=" + tableId +
                ", tableName='" + tableName + '\'' +
                '}';
    }
}
