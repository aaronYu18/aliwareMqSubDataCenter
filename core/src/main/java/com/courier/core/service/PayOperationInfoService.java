package com.courier.core.service;

import com.courier.commons.util.DateUtil;
import com.courier.db.dao.PayOperationInfoMapper;
import com.courier.db.dao.crud.BaseManager;
import com.courier.db.dao.crud.BaseMapper;
import com.courier.db.entity.PayOperationInfo;
import com.courier.db.entity.PayReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vincent on 15/11/4.
 */
@Service
@Transactional
public class PayOperationInfoService extends BaseManager<PayOperationInfo> {
    @Override
    public PayOperationInfo getEntity() {
        return new PayOperationInfo();
    }

    @Autowired
    private PayOperationInfoMapper payOperationInfoMapper;

    @Override
    public BaseMapper<PayOperationInfo> getBaseMapper() {
        return payOperationInfoMapper;
    }


    public List<PayReport> findReportsByTime(Byte type, Date beginDate, Date endDate, Date countDate) {
        Map<String, Object> filters = new HashMap<String, Object>();

        filters.put("type", type);
        if (beginDate != null)
            filters.put("beginT", DateUtil.toSeconds(beginDate));
        if (endDate != null)
            filters.put("endT", DateUtil.toSeconds(endDate));

        if(countDate == null)countDate = new Date();
        filters.put("countT", DateUtil.toSeconds(countDate));

        return payOperationInfoMapper.findReportsByTime(filters);
    }

}
