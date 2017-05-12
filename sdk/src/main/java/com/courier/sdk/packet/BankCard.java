package com.courier.sdk.packet;

import com.courier.sdk.common.IdEntity;

/**
 * Created by vincent on 15/10/29.
 */
public class BankCard extends IdEntity {
    private static final long serialVersionUID = 7129389586572456013L;
    private String bankNumber;          //银行卡号
    private String bankName;            //开户行名称
    private String bankBranchName;      //开户行分支

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
    }

    public String getBankBranchName() {
        return bankBranchName;
    }

    public void setBankBranchName(String bankBranchName) {
        this.bankBranchName = bankBranchName;
    }
}
