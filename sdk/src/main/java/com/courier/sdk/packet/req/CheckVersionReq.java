package com.courier.sdk.packet.req;
import com.courier.sdk.common.IdEntity;
/**
 * Created by Administrator on 2015/10/26.
 */
public class CheckVersionReq extends IdEntity {

    private static final long serialVersionUID = 7777568955512261497L;
    private String version;          //版本号
    private Byte type;              //类型
    private String locationVersion;  //位置信息版本号

    public String getLocationVersion() {
        return locationVersion;
    }

    public void setLocationVersion(String locationVersion) {
        this.locationVersion = locationVersion;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }
}
