package com.courier.commons.vModel;

import com.courier.commons.entity.BaseEntity;

/**
 * Created by david on 16-3-8.
 */
public class VJqPlotData extends BaseEntity {
    private Integer[][] datas ;
    private Integer dataMax;
    private String[] xLabels;

    public VJqPlotData() {
    }

    public VJqPlotData(Integer[][] datas, String[] xLabels, Integer dataMax) {
        this.datas = datas;
        this.xLabels = xLabels;
        this.dataMax = dataMax;
    }

    public Integer[][] getDatas() {
        return datas;
    }

    public void setDatas(Integer[][] datas) {
        this.datas = datas;
    }

    public Integer getDataMax() {
        return dataMax;
    }

    public void setDataMax(Integer dataMax) {
        this.dataMax = dataMax;
    }

    public String[] getxLabels() {
        return xLabels;
    }

    public void setxLabels(String[] xLabels) {
        this.xLabels = xLabels;
    }

}
