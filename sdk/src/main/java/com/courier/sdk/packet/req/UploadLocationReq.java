package com.courier.sdk.packet.req;


import com.courier.sdk.common.IdEntity;

/**
 * Created by Administrator on 2015/10/26.
 */
public class UploadLocationReq extends IdEntity {
    private static final long serialVersionUID = 4204657378826319522L;
    private Double  lat;    //经度
    private Double  lng;    //纬度


    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

}
