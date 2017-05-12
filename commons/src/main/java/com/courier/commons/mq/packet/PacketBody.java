package com.courier.commons.mq.packet;

import com.courier.commons.entity.BaseEntity;

import java.util.Map;

/**
 * Created by ryan on 15/5/18.
 */
public class PacketBody extends BaseEntity {

    private static final long serialVersionUID = 6040615369166928832L;
    /**
     * 对象体
     */
    private Object content;

    /**
     * 扩展信息
     */
    private Map<String, String> ext;


    public PacketBody() {
    }

    public PacketBody(Object content) {
        this.content = content;
    }

    public PacketBody(Object content, Map<String, String> ext) {
        this.content = content;
        this.ext = ext;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public Map<String, String> getExt() {
        return ext;
    }

    public void setExt(Map<String, String> ext) {
        this.ext = ext;
    }

}
