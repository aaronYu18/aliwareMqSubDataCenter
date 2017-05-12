package com.courier.db.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import com.courier.db.entity.BaseEntity;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

/**
 * Created by admin on 2015/10/20.
 */
@Table(name = "appVersion")
public class AppVersion extends BaseEntity {

    private static final long serialVersionUID = -1158087247169872671L;
    private Long id;
    @NotBlank(message = "{app.version.code.required}")
    private String code;                //     版本号
    @NotBlank(message = "{app.version.name.required}")
    private String name;                //     版本名称
    private String description;         //     版本说明
    @NotBlank(message = "{app.version.download.url.required}")
    @URL(message = "{app.version.download.url.error}")
    private String downloadUrl;         //     下载地址
    @NotNull(message = "{app.version.forceUpdate.required}")
    private Boolean forceUpdate;        //     强制更新：1 是 0 否
    @NotNull(message = "{app.version.type.required}")
    private Byte type;                  //     类型：1 ios 0 android 2 android定制

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
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    @Column
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    @Column
    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
    @Column
    public Boolean getForceUpdate() {
        return forceUpdate;
    }

    public void setForceUpdate(Boolean forceUpdate) {
        this.forceUpdate = forceUpdate;
    }
    @Column
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Column
    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }
}
