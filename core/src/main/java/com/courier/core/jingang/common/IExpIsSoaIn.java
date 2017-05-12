package com.courier.core.jingang.common;

/**
 * Created by beyond on 2016/5/31.
 */
public interface IExpIsSoaIn {
    /**
     * 问题件上报
     * @param issueJson
     *        问题件实体类Json串
     * @param clientId
     *        客户端ID
     */
    public Long saveIssue(String issueJson, String clientId);
}
