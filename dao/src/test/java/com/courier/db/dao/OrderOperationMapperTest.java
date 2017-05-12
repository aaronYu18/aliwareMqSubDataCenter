package com.courier.db.dao;

import com.courier.db.entity.OrderOperation;
import com.courier.sdk.constant.Enumerate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by bin on 2015/11/14.
 */
@ContextConfiguration(locations = {"classpath:applicationDBContext.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class OrderOperationMapperTest {

    @Autowired
    OrderOperationMapper orderOperationMapper;
    @Test
    public void testInsertBatch() throws Exception {

        OrderOperation orderOperation = new OrderOperation();
        orderOperation.setUserId(1l);
        orderOperation.setOperationType(Enumerate.OperationType.GRAB.getCode());
        orderOperation.setDcType(Enumerate.DCType.DELIVERY.getCode());

        OrderOperation orderOperation1 = new OrderOperation();
        orderOperation1.setUserId(1l);
        orderOperation1.setOrderId(1l);
        orderOperation1.setOperationType(Enumerate.OperationType.GRAB.getCode());
        orderOperation1.setDcType(Enumerate.DCType.DELIVERY.getCode());
        OrderOperation orderOperation2 = new OrderOperation();
        orderOperation2.setUserId(1l);
        orderOperation2.setOrderId(2l);
        orderOperation2.setOperationType(Enumerate.OperationType.GRAB.getCode());
        orderOperation2.setDcType(Enumerate.DCType.DELIVERY.getCode());
        OrderOperation orderOperation3 = new OrderOperation();
        orderOperation3.setUserId(1l);
        orderOperation3.setOrderId(3l);
        orderOperation3.setOperationType(Enumerate.OperationType.GRAB.getCode());
        orderOperation3.setDcType(Enumerate.DCType.DELIVERY.getCode());
        List<OrderOperation> list = new ArrayList<>();
        list.add(orderOperation1);
        list.add(orderOperation3);
        list.add(orderOperation2);
        orderOperationMapper.insertBatch(list);
    }
}