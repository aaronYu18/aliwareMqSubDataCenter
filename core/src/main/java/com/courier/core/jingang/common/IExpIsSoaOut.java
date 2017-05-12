package com.courier.core.jingang.common;

/**
 * Created by beyond on 2016/5/31.
 */
public interface IExpIsSoaOut {
    /**
     * 问题件查询
     * @param dealOrMedia
     *  		是否显示处理信息和媒体信息(0/1)
     * @param waybillNo
     * 			运单号(支持多个)
     * @return
     */
    public String issue(int dealOrMedia, String... waybillNo);
}
