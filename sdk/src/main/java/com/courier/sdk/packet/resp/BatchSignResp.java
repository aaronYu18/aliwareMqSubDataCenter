package com.courier.sdk.packet.resp;

import com.courier.sdk.constant.CodeEnum;
import com.courier.sdk.common.IdEntity;

/**
 * Created by vincent on 15/11/12.
 */
public class BatchSignResp extends IdEntity{
    private static final long serialVersionUID = 6517865909595448427L;
    private CodeEnum codeEnum;
    private Long orderId;

    public CodeEnum getCodeEnum() {
        return codeEnum;
    }

    public void setCodeEnum(CodeEnum codeEnum) {
        this.codeEnum = codeEnum;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
