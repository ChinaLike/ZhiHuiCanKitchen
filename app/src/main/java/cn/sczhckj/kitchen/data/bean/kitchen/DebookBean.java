package cn.sczhckj.kitchen.data.bean.kitchen;

/**
 * @ describe: 退单信息
 * @ author: Like on 2017-04-28.
 * @ email: 572919350@qq.com
 */

public class DebookBean {

    private Integer foodId;
    private Integer cateId;
    private String name;//菜品名称
    private Integer debookCount;//退订数量
    private String debookUnit;//退订单位
    private String tableName;//桌号
    private String debookTime;//退定时间

    private boolean isSelect;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDebookCount() {
        return debookCount;
    }

    public void setDebookCount(Integer debookCount) {
        this.debookCount = debookCount;
    }

    public String getDebookUnit() {
        return debookUnit;
    }

    public void setDebookUnit(String debookUnit) {
        this.debookUnit = debookUnit;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getDebookTime() {
        return debookTime;
    }

    public void setDebookTime(String debookTime) {
        this.debookTime = debookTime;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    @Override
    public String toString() {
        return "{" +
                "foodId=" + foodId +
                ", cateId=" + cateId +
                ", name='" + name + '\'' +
                ", debookCount=" + debookCount +
                ", debookUnit='" + debookUnit + '\'' +
                ", tableName='" + tableName + '\'' +
                ", debookTime='" + debookTime + '\'' +
                '}';
    }
}
