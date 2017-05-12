package com.courier.db.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by admin on 2015/10/20.
 */
@Table(name = "sourceClientIdRelation")
public class SourceClientIdRelation extends BaseEntity {

    private static final long serialVersionUID = -4448494474993724762L;
    private Long id;
    @NotNull(message = "{sourceclientid.relation.source.required}")
    private Byte source;                   //  渠道（0:移动官网使用,1:微信使用,2:安卓App使用,3:C5使用,4:支付宝使用,5:会员平台使用,6:官网使用,7:IOSApp使用,8:百度轻应用使用）
    @NotNull(message = "{sourceclientid.relation.clientId.required}")
    private String clientId;


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

    @Column
    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
    @Column
    public Byte getSource() {
        return source;
    }

    public void setSource(Byte source) {
        this.source = source;
    }
}
