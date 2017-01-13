package cn.sczhckj.kitchen.data.bean.kitchen;

/**
 * @ describe:  菜品详情
 * @ author: Like on 2017/1/6.
 * @ email: 572919350@qq.com
 */

public class DetailBean {

    private Integer tableId;//台桌ID
    private String tableName;//桌号
    private Integer recordId;//消费记录ID
    private Integer detailId;//明细记录ID
    private Integer taskSubId;//子任务ID

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

    public Integer getTaskSubId() {
        return taskSubId;
    }

    public void setTaskSubId(Integer taskSubId) {
        this.taskSubId = taskSubId;
    }

    @Override
    public String toString() {
        return "{" +
                "tableId=" + tableId +
                ", tableName='" + tableName + '\'' +
                ", recordId=" + recordId +
                ", detailId=" + detailId +
                ", taskSubId=" + taskSubId +
                '}';
    }
}
