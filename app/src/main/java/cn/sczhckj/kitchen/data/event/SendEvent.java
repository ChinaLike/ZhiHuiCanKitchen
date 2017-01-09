package cn.sczhckj.kitchen.data.event;

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

    private int type;//类型

    private String name;//菜品名字

    private String table;//台桌名字

    private int count;//数量

    public SendEvent(int type) {
        this.type = type;
    }

    public SendEvent(int type, String name, String table) {
        this.type = type;
        this.name = name;
        this.table = table;
    }

    public SendEvent(int type, String name, int count) {
        this.type = type;
        this.name = name;
        this.count = count;
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
}
