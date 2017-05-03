package cn.sczhckj.kitchen.data.bean;

import java.util.List;

import cn.sczhckj.kitchen.data.bean.kitchen.DebookBean;

/**
 * @ describe:  WebSocket通用Bean
 * @ author: Like on 2016/12/21.
 * @ email: 572919350@qq.com
 */

public class PushCommonBean {

    private Integer id;//菜品ID
    private Integer cateId;//分类ID
    private Integer arriveCount;//上菜数量

    private String message;//消息内容

    private DebookBean debook;//退订

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCateId() {
        return cateId;
    }

    public void setCateId(Integer cateId) {
        this.cateId = cateId;
    }

    public Integer getArriveCount() {
        return arriveCount;
    }

    public void setArriveCount(Integer arriveCount) {
        this.arriveCount = arriveCount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DebookBean getDebook() {
        return debook;
    }

    public void setDebook(DebookBean debook) {
        this.debook = debook;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", cateId=" + cateId +
                ", arriveCount=" + arriveCount +
                ", message='" + message + '\'' +
                ", debook=" + debook +
                '}';
    }
}
