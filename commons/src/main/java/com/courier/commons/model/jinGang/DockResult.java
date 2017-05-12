package com.courier.commons.model.jinGang;

import com.courier.commons.entity.BaseEntity;

/**
 * Created by bin on 2015/12/14.
 */
public class DockResult extends BaseEntity {
    private String Waybill_No;
    private String Upload_Time;
    private String ProcessInfo;

    public String getWaybill_No() {
        return Waybill_No;
    }

    public void setWaybill_No(String waybill_No) {
        Waybill_No = waybill_No;
    }

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
}
