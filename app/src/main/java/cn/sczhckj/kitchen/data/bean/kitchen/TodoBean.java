package cn.sczhckj.kitchen.data.bean.kitchen;

import java.util.List;

/**
 * @ describe:  菜品
 * @ author: Like on 2017/1/5.
 * @ email: 572919350@qq.com
 */

public class TodoBean {

    private Integer foodId;//菜品ID
    private Integer cateId;//分类ID
    private String name;//菜品名称
    private Integer count;//总共份数
    private List<DetailBean> details;//单个菜品信息

    private boolean isSelect;//本地字段，Item是否选中

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

    public Integer getCount() {
        if (details == null){
            return 0;
        }else {
            return details.size();
        }
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<DetailBean> getDetails() {
        return details;
    }

    public void setDetails(List<DetailBean> details) {
        this.details = details;
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
                ", count=" + count +
                ", details=" + details +
                '}';
    }
}
