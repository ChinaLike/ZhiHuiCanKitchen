package cn.sczhckj.kitchen.data.event;

import java.util.List;

import cn.sczhckj.kitchen.data.bean.kitchen.DebookBean;
import cn.sczhckj.kitchen.data.bean.kitchen.DetailBean;
import cn.sczhckj.kitchen.data.bean.kitchen.TodoBean;

/**
 * @ describe:  事件发布
 * @ author: Like on 2017/1/6.
 * @ email: 572919350@qq.com
 */

public class SendEvent {

    /**
     * 通知代加工菜品刷新
     */
    public final static int FOOD_REFRESH = 0;
    /**
     * 菜品
     */
    public final static int FOOD_LABLE = 1;
    /**
     * 小票
     */
    public final static int PRINT_LABLE = 2;
    /**
     * 下一个
     */
    public final static int KEY_NEXT = 3;
    /**
     * 上一个
     */
    public final static int KEY_PRE = 4;
    /**
     * 确认
     */
    public final static int KEY_AFFIRM = 5;
    /**
     * 出菜成功
     */
    public final static int FOOD_FINISH = 6;
    /**
     * 手动点击菜品完成
     */
    public final static int NON_AUTO_FOOD_FINISH = 7;
    /**
     * 手动点击补打
     */
    public final static int NON_AUTO_PRINT = 8;
    /**
     * 退单
     */
    public final static int PUSH_DEBOOK = 9;

    private int type;//类型

    private String name;//菜品名字

    private String table;//台桌名字

    private int count;//数量

    private int postion;//第几项

    private boolean isHeader;//是否是头部数据

    private TodoBean bean;//加工菜品

    private DebookBean debook;//退单菜品

    private String unit;//商品单位

    public SendEvent(int type) {
        this.type = type;
    }

    public SendEvent(int type, boolean isHeader) {
        this.type = type;
        this.isHeader = isHeader;
    }

    public SendEvent(int type, DebookBean debook) {
        this.type = type;
        this.debook = debook;
    }

    public SendEvent(int type, String name, String table) {
        this.type = type;
        this.name = name;
        this.table = table;
    }

    public SendEvent(int type, String name, int count,String unit) {
        this.type = type;
        this.name = name;
        this.count = count;
        this.unit = unit;
    }

    public SendEvent(int type, int postion, boolean isHeader) {
        this.type = type;
        this.postion = postion;
        this.isHeader = isHeader;
    }

    public SendEvent(int type, TodoBean bean, boolean isHeader) {
        this.type = type;
        this.bean = bean;
        this.isHeader = isHeader;
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getTable() {
        return table;
    }

    public int getCount() {
        return count;
    }

    public int getPostion() {
        return postion;
    }

    public void setPostion(int postion) {
        this.postion = postion;
    }

    public boolean isHeader() {
        return isHeader;
    }

    public void setHeader(boolean header) {
        isHeader = header;
    }

    public TodoBean getBean() {
        return bean;
    }

    public void setBean(TodoBean bean) {
        this.bean = bean;
    }

    public DebookBean getDebook() {
        return debook;
    }

    public void setDebook(DebookBean debook) {
        this.debook = debook;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
