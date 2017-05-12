package com.courier.db.dao;

import com.courier.db.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by bin on 2015/11/4.
 */
@ContextConfiguration(locations = {"classpath:applicationDBContext.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class UserMapperTest {

    @Autowired
    UserMapper userMapper;

    @Test
    public void testGet() throws Exception{
        User user = new User();
        user.setId(100l);
        User user1 = userMapper.get(user);
        System.out.println(user1);
    }
}