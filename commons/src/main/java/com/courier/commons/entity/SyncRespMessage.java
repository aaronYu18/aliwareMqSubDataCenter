package com.courier.commons.entity;

import com.courier.commons.enums.SyncEnum;

import java.util.List;

/**
 * Created by beyond on 2016/3/29.
 */
public class SyncRespMessage extends RespMessage {
    private int code;

    public SyncRespMessage(Status status, int code) {
        super(status);
        this.code = code;
    }
    public SyncRespMessage(Status status, SyncEnum.MessageTipEnum tipEnum, List list){
        super(status,tipEnum.getDesc(),list);
        this.code = tipEnum.getCode();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
