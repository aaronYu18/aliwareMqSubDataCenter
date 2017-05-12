package com.courier.core.service;

import com.courier.core.resp.courier.ResponseDTO;
import com.courier.db.dao.BackendUserMapper;
import com.courier.db.dao.crud.BaseManager;
import com.courier.db.dao.crud.BaseMapper;
import com.courier.db.dao.crud.SearchFilter;
import com.courier.db.entity.BackendUser;
import com.courier.sdk.constant.CodeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aaron_yu on 15/5/10.
 */
@Service
@Transactional
public class BackendUserService extends BaseManager<BackendUser> {
    private static final Logger logger = LoggerFactory.getLogger(BackendUserService.class);

    @Autowired private BackendUserMapper backendUserMapper;
    @Override public BackendUser getEntity() { return new BackendUser(); }
    @Override public BaseMapper<BackendUser> getBaseMapper() { return backendUserMapper; }



    public BackendUser findByUsername(String username) {
        if (StringUtils.isEmpty(username)) return null;

        List<SearchFilter> searchFilterList = new ArrayList<SearchFilter>();
        searchFilterList.add(new SearchFilter("username", SearchFilter.Operator.EQ, username));
        List<BackendUser> list = findAll(searchFilterList, null);

        if(list != null && !list.isEmpty()) return list.get(0);
        return null;
    }
    public ResponseDTO loginDeal(String jobNo, String password, Byte deviceType, String deviceNo) throws Exception {

        return new ResponseDTO(CodeEnum.C1000);
    }
}
