package com.courier.core.jingang.convert;

import com.courier.commons.model.jinGang.JGUser;
import com.courier.db.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2015/10/23.
 */
public class JGUserConvert {
    public static final Logger logger = LoggerFactory.getLogger(JGUserConvert.class);
    /**
     * list转化
     * @param objs
     * @return
     */
    public static List<User> convertList(List<JGUser> objs){
        if(objs == null || objs.size() == 0) return null;

        List<User> result = new ArrayList<User>();

        for(JGUser obj : objs)
            result.add(convertObj(obj));

        return result;
    }
    /**
     * api对象转化为service对象
     * @param obj
     * @return
     */
    public static User convertObj(JGUser obj){
        if(obj == null) return null;

        User result = new User();

        String displayName = obj.getDisplayName();
        String orgCode = obj.getOrgCode();
        String userId = obj.getUserId();
        String userName = obj.getUserName();


        result.setNickname(displayName);
        result.setJobNo(userId);
        result.setOrgCode(orgCode);
        result.setUsername(userName);


        return result;
    }
}
