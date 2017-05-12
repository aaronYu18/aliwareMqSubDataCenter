package com.courier.sdk.packet.resp;
import com.courier.sdk.common.IdEntity;

/**
 * Created by Administrator on 2015/10/26.
 */
public class FaqVersionResp extends IdEntity {

    private static final long serialVersionUID = 8036501701850174742L;
    private String  name;              //版本名称
    private String  description;       //描述
    private Integer version;           //版本号
    private Boolean isForceUpdate;     //是否强制

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

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }
}
