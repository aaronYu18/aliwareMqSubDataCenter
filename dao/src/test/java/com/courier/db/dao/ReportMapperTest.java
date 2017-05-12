package com.courier.db.dao;

import com.courier.commons.util.DateUtil;
import com.courier.db.entity.Report;
import com.courier.db.vModel.VReportTotalNum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

/**
 * Created by david on 16-3-8.
 */
@ContextConfiguration(locations = {"classpath:applicationDBContext.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class ReportMapperTest {

    @Autowired
    ReportMapper reportMapper;
    @Test
    public void testFindThisMonth() throws Exception {
        List<Report> thisMonth = reportMapper.findThisMonth(Long.parseLong("100"),new Date());
        System.out.println(thisMonth);
    }

    @Test
    public void testFindStatByMonths() throws Exception {
        List<String> list = new ArrayList<>();
        for (int i=0;i<9;i++){
            Date date = DateUtil.lastNumMonth(i);
            list.add(DateUtil.toYearMonth(date));
        }
        List<Report> stats = reportMapper.findStatByMonths(Long.parseLong("1846"),list);
        System.out.println(stats);
    }
    @Test
    public void testFindTotalByRegionRole(){
        Set<Long> userIds = new HashSet<>();
        userIds.add(7062l);
        userIds.add(9019l);
        userIds.add(3648l);
        VReportTotalNum totalByRegionRole = reportMapper.findTotalByRegionRole("2016-04-09", "2016-04-18", userIds);
        System.out.println(totalByRegionRole);
    }

    @Test
    public void testFindTotalByUserIds(){
        Set<Long> userIds = new HashSet<>();
        userIds.add(7062l);
        userIds.add(9019l);
        userIds.add(3648l);
        List<VReportTotalNum> totalByUserIds = reportMapper.findReportGroupByUserId("2016-04-09", "2016-04-18", userIds);
        System.out.println(totalByUserIds);
    }
    @Test
    public void testFindReportGroupByTime(){
        Set<Long> userIds = new HashSet<>();
        userIds.add(7062l);
        userIds.add(9019l);
        userIds.add(3648l);
        List<VReportTotalNum> totalByUserIds = reportMapper.findReportGroupByTime("2016-04-01", "2016-04-18", userIds);
        System.out.println(totalByUserIds);
    }
}