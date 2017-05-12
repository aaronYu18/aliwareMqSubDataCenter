package com.courier.sdk.packet.req;

import com.courier.sdk.common.IdEntity;

import java.util.Date;

/**
 * Created by vincent on 15/11/3.
 */
public class OperationReq extends IdEntity {
    private static final long serialVersionUID = 1882445440659261119L;
    private Byte[] dcTypes;  //收派件类型(0:取件;1:派件)
    private Byte[] operations; //操作类型(0:接单;1:取件;2:签收;3:异常签收)
    private Date beginT;  //创建时间开始
    private Date endT;  //创建时间结束

    public Byte[] getDcTypes() {
        return dcTypes;
    }

    public void setDcTypes(Byte[] dcTypes) {
        this.dcTypes = dcTypes;
    }

    public Byte[] getOperations() {
        return operations;
    }

    public void setOperations(Byte[] operations) {
        this.operations = operations;
    }

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
}
