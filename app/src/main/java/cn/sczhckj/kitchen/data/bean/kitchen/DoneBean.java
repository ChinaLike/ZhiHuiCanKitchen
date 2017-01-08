package cn.sczhckj.kitchen.data.bean.kitchen;

/**
 * @ describe:  补打
 * @ author: Like on 2017/1/5.
 * @ email: 572919350@qq.com
 */

public class DoneBean {

    private Integer foodId;//菜品ID
    private Integer cateId;//分类ID
    private String name;//菜品名称
    private Integer tableId;//台桌ID
    private String tableName;//桌号
    private Integer printCount;//打印此数
    private String lastPrint;//最后打印时间
    private Integer recordId;//消费记录ID
    private Integer detailId;//明细记录ID

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

    public Integer getPrintCount() {
        return printCount;
    }

    public void setPrintCount(Integer printCount) {
        this.printCount = printCount;
    }

    public String getLastPrint() {
        return lastPrint;
    }

    public void setLastPrint(String lastPrint) {
        this.lastPrint = lastPrint;
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
                ", tableId=" + tableId +
                ", tableName='" + tableName + '\'' +
                ", printCount=" + printCount +
                ", lastPrint='" + lastPrint + '\'' +
                ", recordId=" + recordId +
                ", detailId=" + detailId +
                '}';
    }
}
