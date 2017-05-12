package com.courier.sdk.common.resp;
import com.courier.sdk.common.IdEntity;

/**
 * Created by Administrator on 2015/10/26.
 */
public class AppConfigResp extends IdEntity {

    private static final long serialVersionUID = 5409467465439138354L;
    private Integer uploadInterval;  //上传位置间隔
    private String  locationVersion; //位置信息版本号


    public Integer getUploadInterval() {
        return uploadInterval;
    }

    public void setUploadInterval(Integer uploadInterval) {
        this.uploadInterval = uploadInterval;
    }

    public String getLocationVersion() {
        return locationVersion;
    }

    public void setLocationVersion(String locationVersion) {
        this.locationVersion = locationVersion;
    }
}
