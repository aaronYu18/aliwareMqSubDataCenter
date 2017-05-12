package com.courier.sdk.packet.resp;

import com.courier.sdk.common.IdEntity;

import java.util.Date;

/**
 * Created by vincent on 15/11/4.
 */
public class TrajectoryResp extends IdEntity{
    private static final long serialVersionUID = 8441647052447378828L;
    private String Waybill_No;
    private String Upload_Time;
    private String ProcessInfo;

    public String getProcessInfo() {
        return ProcessInfo;
    }

    public void setProcessInfo(String processInfo) {
        ProcessInfo = processInfo;
    }

    public String getUpload_Time() {
        return Upload_Time;
    }

    public void setUpload_Time(String upload_Time) {
        Upload_Time = upload_Time;
    }

    public String getWaybill_No() {
        return Waybill_No;
    }

    public void setWaybill_No(String waybill_No) {
        Waybill_No = waybill_No;
    }
}
