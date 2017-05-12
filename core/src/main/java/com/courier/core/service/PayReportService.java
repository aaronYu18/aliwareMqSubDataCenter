package com.courier.core.service;

import com.courier.db.dao.PayReportMapper;
import com.courier.db.dao.crud.BaseManager;
import com.courier.db.dao.crud.BaseMapper;
import com.courier.db.entity.PayReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2015/11/2.
 */
@Service
@Transactional
public class PayReportService extends BaseManager<PayReport> {
    private static final Logger logger = LoggerFactory.getLogger(PayReportService.class);

    @Autowired
    PayReportMapper payReportMapper;

    @Override
    public PayReport getEntity() {
        return new PayReport();
    }

    @Override
    public BaseMapper<PayReport> getBaseMapper() {
        return payReportMapper;
    }


    public int batchInsert(List<PayReport> reports) {
        if (reports == null || reports.size() <= 0) return 1;
        return payReportMapper.batchInsert(reports);
    }

    public List<PayReport> buildByFilters(Map<String, Object> filters) {
        return payReportMapper.buildByFilters(filters);
    }


    public int delDataForInsert() {
        return payReportMapper.delDataForInsert();
    }


    public PayReport findMonthAvg(Map<String, Object> filters) {
        return payReportMapper.findMonthAvg(filters);
    }

}
