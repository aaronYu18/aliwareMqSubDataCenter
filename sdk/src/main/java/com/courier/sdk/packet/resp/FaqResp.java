package com.courier.sdk.packet.resp;
import com.courier.sdk.common.IdEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/10/26.
 */
public class FaqResp extends IdEntity {

    private static final long serialVersionUID = 8036501701850174742L;
    private String  name;              //问题或分类名称
    private String  description;       //回答
    private String path;               //编号

    List<FaqResp> children = new ArrayList<>();

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public List<FaqResp> getChildren() {
        return children;
    }

    public void setChildren(List<FaqResp> children) {
        this.children = children;
    }
}
