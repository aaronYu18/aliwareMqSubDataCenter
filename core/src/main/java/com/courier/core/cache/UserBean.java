package com.courier.core.cache;

import com.courier.commons.entity.BaseEntity;
import com.courier.db.entity.Branch;
import com.courier.db.entity.User;
import com.courier.db.entity.UserLoginRecord;

/**
 * Created by bin on 2015/7/23.
 */
public class UserBean extends BaseEntity {
    private static final long serialVersionUID = -6084435477105719907L;
    private User user;
    private Branch branch;
    private UserLoginRecord userLoginRecord;
    private Byte collectPattern;

    public UserBean() {
    }

    public UserBean(User user, Branch branch, UserLoginRecord userLoginRecord, Byte collectPattern) {
        this.user = user;
        this.branch = branch;
        this.userLoginRecord = userLoginRecord;
        this.collectPattern = collectPattern;
    }

    public UserBean(Branch branch, User user) {
        this.branch = branch;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserLoginRecord getUserLoginRecord() {
        return userLoginRecord;
    }

    public void setUserLoginRecord(UserLoginRecord userLoginRecord) {
        this.userLoginRecord = userLoginRecord;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public Byte getCollectPattern() {
        return collectPattern;
    }

    public void setCollectPattern(Byte collectPattern) {
        this.collectPattern = collectPattern;
    }
}
