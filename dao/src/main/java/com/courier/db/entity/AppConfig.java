package com.courier.db.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import com.courier.db.entity.BaseEntity;

/**
 * Created by admin on 2015/10/20.
 */
@Table(name = "appConfig")
public class AppConfig extends BaseEntity {

    private static final long serialVersionUID = -3948506108981607801L;
    private Long id;
    @NotNull(message = "{app.version.appConfigKey.required}")
    private String appConfigKey;             //     app配置记录key值 (服务器端比对，一旦表内值发生变化key值加1)
    @NotNull(message = "{app.version.gpsUploadInterval.required}")
    private Integer gpsUploadInterval;         //     gps上传频率
    private Integer gpsRecordInterval; //gps记录频率 暂时不用

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
    public String getAppConfigKey() {
        return appConfigKey;
    }

    public void setAppConfigKey(String appConfigKey) {
        this.appConfigKey = appConfigKey;
    }

    @Column
    public Integer getGpsUploadInterval() {
        return gpsUploadInterval;
    }

    public void setGpsUploadInterval(Integer gpsUploadInterval) {
        this.gpsUploadInterval = gpsUploadInterval;
    }

    @Column
    public Integer getGpsRecordInterval() {
        return gpsRecordInterval;
    }

    public void setGpsRecordInterval(Integer gpsRecordInterval) {
        this.gpsRecordInterval = gpsRecordInterval;
    }
}
