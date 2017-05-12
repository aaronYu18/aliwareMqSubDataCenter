package com.courier.core.cache;

import com.courier.commons.entity.BaseEntity;
import com.courier.db.entity.*;

/**
 * Created by bin on 2015/7/23.
 */
public class ManagerBean extends BaseEntity {
    private static final long serialVersionUID = -8228410518850664023L;

    private Manager manager;
    private ManagerLoginRecord managerLoginRecord;

    public ManagerBean() {
    }

    public ManagerBean(Manager manager, ManagerLoginRecord managerLoginRecord) {
        this.manager = manager;
        this.managerLoginRecord = managerLoginRecord;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public ManagerLoginRecord getManagerLoginRecord() {
        return managerLoginRecord;
    }

    public void setManagerLoginRecord(ManagerLoginRecord managerLoginRecord) {
        this.managerLoginRecord = managerLoginRecord;
    }
}
