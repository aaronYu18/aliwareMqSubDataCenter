package com.courier.commons.model.xml;

/**
 * Created by beyond on 2016/6/2.
 */
public class RequestOrderCModel extends RequestOrder {


    public RequestOrderCModel(String clientID, String txLogisticID, String customerId, Sender sender, Receiver receiver, String acceptCanvassEmpCode, String orderChannelCode) {
        super(clientID, txLogisticID, customerId, sender, receiver);
        this.acceptCanvassEmpCode = acceptCanvassEmpCode;
        this.orderChannelCode = orderChannelCode;
    }

    private static final long serialVersionUID = -8662728373741276249L;
    private String acceptCanvassEmpCode;
    private String orderChannelCode;

    public String getAcceptCanvassEmpCode() {
        return acceptCanvassEmpCode;
    }

    public void setAcceptCanvassEmpCode(String acceptCanvassEmpCode) {
        this.acceptCanvassEmpCode = acceptCanvassEmpCode;
    }

    public String getOrderChannelCode() {
        return orderChannelCode;
    }

    public void setOrderChannelCode(String orderChannelCode) {
        this.orderChannelCode = orderChannelCode;
    }
}
