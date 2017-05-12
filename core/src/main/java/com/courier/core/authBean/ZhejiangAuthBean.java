package com.courier.core.authBean;

/**
 * Created by vincent on 16/5/12.
 */
public class ZhejiangAuthBean extends BaseAuthBean {

    private static final long serialVersionUID = 8790043725534384541L;
    private byte[] picture;     //开包照片
    private Long pictureSize;   //图片大小

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public Long getPictureSize() {
        return pictureSize;
    }

    public void setPictureSize(Long pictureSize) {
        this.pictureSize = pictureSize;
    }
}