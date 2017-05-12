package com.courier.core.util;


import com.courier.db.entity.DeliveryOrder;
import com.courier.sdk.constant.CodeEnum;

/**
 * Created by bin on 2015/10/31.
 */
public class SignResultResp extends com.courier.sdk.common.IdEntity {

    private CodeEnum codeEnum;
    private DeliveryOrder deliveryOrder;

    public SignResultResp() {
    }

    public SignResultResp(CodeEnum codeEnum, DeliveryOrder deliveryOrder) {
        this.codeEnum = codeEnum;
        this.deliveryOrder = deliveryOrder;
    }

    public CodeEnum getCodeEnum() {
        return codeEnum;
    }

    public void setCodeEnum(CodeEnum codeEnum) {
        this.codeEnum = codeEnum;
    }

    public DeliveryOrder getDeliveryOrder() {
        return deliveryOrder;
    }

    public void setDeliveryOrder(DeliveryOrder deliveryOrder) {
        this.deliveryOrder = deliveryOrder;
    }
}
