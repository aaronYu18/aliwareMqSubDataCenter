package com.courier.db.dao;

import com.courier.db.entity.DeliveryOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by bin on 2015/11/14.
 */
@ContextConfiguration(locations = {"classpath:applicationDBContext.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class DeliveryOrderMapperTest {

    @Autowired
    DeliveryOrderMapper deliveryOrderMapper;



    @Test
    public void testBatchInsert() throws Exception{
        List<DeliveryOrder> orders = new ArrayList<>();
        DeliveryOrder test1 = new DeliveryOrder();
        DeliveryOrder test2 = new DeliveryOrder();

//        test1.setId(1000001l);
        test1.setUserId(1l);
        test1.setMailNo("0000000005");
        test1.setCollectionMoney(23.5);
        test1.setFreightMoney(23.5);
        test1.setReceiverName("高云峰");
        test1.setReceiverPhone("18717997634");
        test1.setReceiverProvince("310000");
        test1.setReceiverProvinceName("上海");
        test1.setReceiverCity("310100");
        test1.setReceiverCityName("上海");
        test1.setReceiverArea("310105");
        test1.setReceiverAreaName("浦东新区");
        test1.setReceiverAddress("益江路396弄33号");
        test1.setReceiverLat(23.23);
        test1.setReceiverLng(34.45);
        test1.setPaymentType((byte) 0);
        test1.setOrderType((byte)1);
        test1.setSignTime(new Date());
        test1.setAppSignTime(new Date());
        test1.setSignPersonName("高云峰");
        test1.setSignPersonType((byte)0);
//        test1.setHasPicture(false);
        test1.setDeviceType("0");
        test1.setOrderStatus((byte)1);
//        test1.setFlag(true);
        test1.setjGCreateTime(new Date());
        test1.setCreateTime(new Date());

//        test2.setId(1000002l);
        test2.setUserId(1l);
        test2.setMailNo("00000000006");
        test2.setCollectionMoney(23.5);
        test2.setFreightMoney(23.5);
        test2.setReceiverName("高云峰");
        test2.setReceiverPhone("18717997634");
        test2.setReceiverProvince("310000");
        test2.setReceiverProvinceName("上海");
        test2.setReceiverCity("310100");
        test2.setReceiverCityName("上海");
        test2.setReceiverArea("310105");
        test2.setReceiverAreaName("浦东新区");
        test2.setReceiverAddress("益江路396弄33号");
        test2.setReceiverLat(23.23);
        test2.setReceiverLng(34.45);
        test2.setPaymentType((byte) 0);
        test2.setOrderType((byte)1);
        test2.setSignTime(new Date());
        test2.setAppSignTime(new Date());
        test2.setSignPersonName("高云峰");
        test2.setSignPersonType((byte)0);
//        test2.setHasPicture(false);
        test2.setDeviceType("0");
        test2.setOrderStatus((byte)1);
//        test2.setFlag(true);
        test2.setjGCreateTime(new Date());
        test2.setCreateTime(new Date());

        orders.add(test1);
        orders.add(test2);
        deliveryOrderMapper.batchInsert(orders);
    }

}