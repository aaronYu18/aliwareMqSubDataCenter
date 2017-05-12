package com.courier.sdk.packet.req;

import java.util.Date;
import com.courier.sdk.common.IdEntity;
/**
 * Created by vincent on 15/10/23.
 */
public class CollectQueryReq extends IdEntity{
    private static final long serialVersionUID = -1820624521255821085L;
    private Byte[] status;           //状态 0:待接单;1:接单待揽收;2:已揽收;3:待接单超时)
    private Byte[] types;            //类型
    private Date beginT;             //开始时间
    private Date endT;               //结束时间
    private String timeField;        //时间字段名称(下单时间，揽件时间等)
    private Byte sortType = 1;       //排序方式 0.asc 1.desc
    private String expressNo;        //快递单号
    private Long orderId;            //订单id

    public Date getBeginT() {
        return beginT;
    }

    public void setBeginT(Date beginT) {
        this.beginT = beginT;
    }

    public Date getEndT() {
        return endT;
    }

    public void setEndT(Date endT) {
        this.endT = endT;
    }

    public String getExpressNo() {
        return expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }

    public Byte getSortType() {
        return sortType;
    }

    public Byte[] getStatus() {
        return status;
    }

    public void setStatus(Byte[] status) {
        this.status = status;
    }

    public String getTimeField() {
        return timeField;
    }

    public void setTimeField(String timeField) {
        this.timeField = timeField;
    }

    public Byte[] getTypes() {
        return types;
    }

    public void setTypes(Byte[] types) {
        this.types = types;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
