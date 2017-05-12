package com.courier.commons;

import com.courier.commons.constant.Global;
import com.courier.commons.util.ValidationUtil;
import com.courier.commons.util.DateUtil;
import org.junit.Test;

import java.util.Date;
import java.util.regex.Pattern;

/**
 * Created by bin on 2015/6/23.
 */
public class PatternTest {

    @Test
    public void dateTest(){
        System.out.println("result:"+DateUtil.toSeconds(new Date()));
        Date result = DateUtil.getDateSecondBeofre(new Date(), 60);
        System.out.println("result:"+DateUtil.toSeconds(result));
    }

    @org.junit.Test
    public void mobileTest(){
        System.out.println(ValidationUtil.isTimeNo("dddd"));
        System.out.println(ValidationUtil.isTimeNo("300090"));
        System.out.println(ValidationUtil.isTimeNo("201921"));
        System.out.println(ValidationUtil.isTimeNo("201001"));
        System.out.println(ValidationUtil.isTimeNo("201801"));
        System.out.println(ValidationUtil.isTimeNo("101801"));
        System.out.println(ValidationUtil.isTimeNo("0201801"));
    }
}
