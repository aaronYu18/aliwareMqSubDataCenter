package com.courier.db.dao;

import com.courier.db.entity.PayOperationInfo;
import com.courier.db.entity.PayOrderInfo;
import com.courier.db.entity.PayReport;
import com.courier.db.entity.Report;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by bin on 2015/11/7.
 */
@ContextConfiguration(locations = {"classpath:applicationDBContext.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class PayMapperTest {

    @Autowired
    PayOrderInfoMapper payOrderInfoMapper;
    @Autowired
    PayOperationInfoMapper payOperationInfoMapper;
    @Autowired
    PayReportMapper payReportMapper;

    @Test
    public void testSave() throws Exception {
        PayOrderInfo info = new PayOrderInfo();
        info.setSerialNo("sdgg");
        info.setPaymentTime(new Date());
        payOrderInfoMapper.insert(info);

    }
    @Test
    public void testSavePay() throws Exception {
        PayOperationInfo info = new PayOperationInfo();
        info.setSerialNo("sdgg");
        info.setPayOrderInfoId(1l);
        payOperationInfoMapper.insert(info);

    }
    @Test
    public void batchInsert() throws Exception {
        List<PayReport> reports = new ArrayList<>();
        PayReport report = new PayReport(23.4d,23.4d,new Date(),23.4d,23.4d,(byte)1,100l);
        PayReport report1 = new PayReport(23.4d,23.4d,new Date(),23.4d,23.4d,(byte)1,100l);
        reports.add(report);
        reports.add(report1);
        payReportMapper.batchInsert(reports);
        System.out.println("sdg");
    }

}