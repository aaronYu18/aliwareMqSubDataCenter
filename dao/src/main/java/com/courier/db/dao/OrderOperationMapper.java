package com.courier.db.dao;

import com.courier.commons.vModel.VManagerSubCurrentData;
import com.courier.db.dao.crud.BaseMapper;
import com.courier.db.entity.OrderOperation;
import com.courier.db.entity.Report;
import com.courier.db.vModel.VOperationDayCount;
import com.courier.db.vModel.VOperationReport;
import com.courier.db.vModel.VYtoData;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by vincent on 15/11/3.
 */
public interface OrderOperationMapper extends BaseMapper<OrderOperation>{
    int countByFilters(Map<String, Object> filters);

    List<OrderOperation> findByFilters(Map<String, Object> filters);

    List<VOperationDayCount> dayCountByFilters(Map<String, Object> filters);

    Map<String, Object> countTodaySignAndCollect(Map<String, Object> filters);

    VYtoData countYtoData(Map<String, Object> filters);

    List<Report> findReportsByTime(Map<String, Object> filters);

    Report findAvgReportByTime(Map<String, Object> filters);

    void  insertBatch(List<OrderOperation> list);

    int backup(@Param("sign_fail_date") Date sign_fail_date,@Param("other_date") Date other_date);

    int deleteHistory(@Param("sign_fail_date") Date sign_fail_date,@Param("other_date") Date other_date);

    int backupHistory(@Param("sign_fail_date") Date sign_fail_date,@Param("other_date") Date other_date);

    int deleteOperation(@Param("sign_fail_date") Date sign_fail_date,@Param("other_date") Date other_date);

    Report countAllByTime(Map<String, Object> filters);

    List<VOperationReport> getVOperationReports(@Param("beginT") String beginT, @Param("endT") String endT);

    VOperationReport countForManage(Map<String, Object> filters);

    int countCollectingNoForManage(Map<String, Object> filters);

    int countSendingNoForManage(Map<String, Object> filters);

    List<Long> countUsersForManage(Map<String, Object> filters);

    List<VManagerSubCurrentData> buildForManageList(Map<String, Object> filters);
}
