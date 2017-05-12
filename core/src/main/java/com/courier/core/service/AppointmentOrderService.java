package com.courier.core.service;

import com.courier.db.dao.AppointmentOrderMapper;
import com.courier.db.dao.crud.BaseManager;
import com.courier.db.dao.crud.BaseMapper;
import com.courier.db.entity.AppointmentOrder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Created by beyond on 2016/12/26.
 */
@Service
public class AppointmentOrderService extends BaseManager<AppointmentOrder> {

    private static final Logger logger = LoggerFactory.getLogger(AppointmentOrderService.class);
    @Autowired
    private AppointmentOrderMapper appointmentOrderMapper;

    @Override
    public AppointmentOrder getEntity() {
        return new AppointmentOrder();
    }

    @Override
    public BaseMapper<AppointmentOrder> getBaseMapper() {
        return appointmentOrderMapper;
    }

    public int batchUpdateByIds(ArrayList<AppointmentOrder> orders) {
        if (orders == null || orders.size() <= 0) return 1;
        return appointmentOrderMapper.batchUpdateByIds(orders);
    }
}
