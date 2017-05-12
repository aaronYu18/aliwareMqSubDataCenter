package com.courier.sdk.common.resp;
import com.courier.sdk.common.IdEntity;

/**
 * Created by Administrator on 2015/10/26.
 */
public class CheckVersionResp extends IdEntity {

    private static final long serialVersionUID = 5647535421033746258L;
    private String  downloadUrl;       //下载地址
    private String  description;       //描述
    private Boolean isForceUpdate;     //是否强制
    private String  name;              //版本名称
    private String version;           //版本号
    private LoginStatusResp loginStatusResp;       //登陆状态

    private AppConfigResp appConfigResp;  //位置数据


    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsForceUpdate() {
        return isForceUpdate;
    }

    public void setIsForceUpdate(Boolean isForceUpdate) {
        this.isForceUpdate = isForceUpdate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public LoginStatusResp getLoginStatusResp() {
        return loginStatusResp;
    }

    public void setLoginStatusResp(LoginStatusResp loginStatusResp) {
        this.loginStatusResp = loginStatusResp;
    }

    public AppConfigResp getAppConfigResp() {
        return appConfigResp;
    }

    public void setAppConfigResp(AppConfigResp appConfigResp) {
        this.appConfigResp = appConfigResp;
    }
}
