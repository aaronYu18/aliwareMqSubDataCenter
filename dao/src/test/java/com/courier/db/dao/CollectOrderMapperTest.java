package com.courier.db.dao;

import com.courier.commons.util.Uuid;
import com.courier.db.entity.CollectOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 * Created by bin on 2015/11/7.
 */
@ContextConfiguration(locations = {"classpath:applicationDBContext.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class CollectOrderMapperTest {

    @Autowired
    CollectOrderMapper collectOrderMapper;

    @Test
    public void testSave() throws Exception {
        CollectOrder collectOrder = new CollectOrder();
        collectOrder.setSenderProvince("上海市");
        collectOrder.setSenderCity("上海市");
        collectOrder.setSenderAddress("华徐公路999号");
        Uuid uuid  = new Uuid();
        System.out.println(uuid.toString().length());
        collectOrder.setSerialNo(uuid.toString());
        collectOrder.setReceiverAddress("涞寅路658弄45号");
        collectOrder.setReceiverProvince("上海市");
        collectOrder.setReceiverCity("上海市");
        collectOrder.setReceiverArea("松江区");
        collectOrder.setSource((byte) 1);
        collectOrder.setSourceKey("A123");
        collectOrder.setSenderPhone("15821160427");
        collectOrder.setSenderName("zhangs");
        collectOrder.setSenderArea("青浦区");
        collectOrder.setReceiverName("李斯");
        collectOrder.setReceiverPhone("18678898909");
//        collectOrder.setOrderType(Enumerate.CollectOrderType.GRAB.getCode());
//        collectOrder.setOrderStatus(Enumerate.CollectOrderStatus.WAIT_ACCEPT.getCode());
        collectOrderMapper.insert(collectOrder);

    }

    @Test
    public void testGet() throws Exception {

    }

    @Test
    public void testFindByFilters() throws Exception {

    }

    @Test
    public void testFindBySourceKey() throws Exception {

    }

    @Test
    public void testBatchList() throws Exception {
        List<CollectOrder> orders = new ArrayList<>();
        CollectOrder order = new CollectOrder();
        order.setId(10007l);
        order.setOrderStatus((byte)5);
        CollectOrder order1 = new CollectOrder();
        order1.setId(11252l);
        order1.setOrderStatus((byte)3);
//        order = collectOrderMapper.get(order);
//        order1 = collectOrderMapper.get(order1);
        orders.add(order);orders.add(order1);
        collectOrderMapper.updateBatch(orders);
    }
}