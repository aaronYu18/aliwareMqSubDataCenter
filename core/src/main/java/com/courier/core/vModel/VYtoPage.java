package com.courier.core.vModel;

import com.courier.db.vModel.VYtoData;
import com.courier.db.entity.Report;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2015/11/4.
 */
public class VYtoPage implements Serializable {
    private static final long serialVersionUID = -4009086234924941571L;
    private VYtoData ytoData;
    private List<Report> todayReports;  // 当天收派件情况


    public VYtoPage() {
    }

    public VYtoPage(List<Report> todayReports, VYtoData ytoData) {
        this.todayReports = todayReports;
        this.ytoData = ytoData;
    }

    public VYtoPage(VYtoData ytoData) {
        this.ytoData = ytoData;
    }

    public List<Report> getTodayReports() {
        return todayReports;
    }

    public void setTodayReports(List<Report> todayReports) {
        this.todayReports = todayReports;
    }

    public VYtoData getYtoData() {
        return ytoData;
    }

    public void setYtoData(VYtoData ytoData) {
        this.ytoData = ytoData;
    }
}
