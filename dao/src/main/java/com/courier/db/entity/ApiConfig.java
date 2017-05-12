package com.courier.db.entity;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by admin on 2015/10/20.
 */
@Table(name = "apiConfig")
public class ApiConfig extends BaseEntity {

    private static final long serialVersionUID = 6348378426171675496L;
    private Long id;
    @NotBlank(message = "{api.config.name.required}")
    private String name;                   //     三方系统名称
    private String description;            //     描述
    @NotNull(message = "{api.config.source.required}")
    private Byte source;                   //  渠道（0:移动官网使用,1:微信使用,2:安卓App使用,3:C5使用,4:支付宝使用,5:会员平台使用,6:官网使用,7:IOSApp使用,8:百度轻应用使用）
    @NotBlank(message = "{api.config.token.required}")
    private String token;                  //     公开给三方系统的key
    @NotNull(message = "{api.config.permission.required}")
    private Byte permission;               //  权限（0:抢单通知（取件）,1:系统分配（取件）,2:取消取件订单,3:发送push,4:获取用户gps）


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "updateTime")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getUpdateTime() {
        return super.getUpdateTime();
    }

    @Column(name = "createTime")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreateTime() {
        return super.getCreateTime();
    }
    @Column
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    @Column
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Column
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    @Column
    public Byte getPermission() {
        return permission;
    }

    public void setPermission(Byte permission) {
        this.permission = permission;
    }
    @Column
    public Byte getSource() {
        return source;
    }

    public void setSource(Byte source) {
        this.source = source;
    }
}
