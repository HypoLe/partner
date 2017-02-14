package com.boco.eoms.partner.keymanagement.form;

import com.boco.eoms.base.webapp.form.BaseForm;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: huangpeng
 * Date: 13-6-28
 * Time: 下午8:27
 * To change this template use File | Settings | File Templates.
 */
public class KeyBorrowRecordForm  extends BaseForm {
    private String id;
    private String borrower;
    private String borrowerPhone;
    private String remand;
    private String borrowTime;
    private String remandTime;
    private String accessCardNum;
    private String subject;
    private String approver;
    private String approverPhone;
    private String remark;
    private Integer keyStatus;
    private String btsName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBorrower() {
        return borrower;
    }

    public void setBorrower(String borrower) {
        this.borrower = borrower;
    }

    public String getBorrowerPhone() {
        return borrowerPhone;
    }

    public void setBorrowerPhone(String borrowerPhone) {
        this.borrowerPhone = borrowerPhone;
    }

    public String getRemand() {
        return remand;
    }

    public void setRemand(String remand) {
        this.remand = remand;
    }

    public String getBorrowTime() {
        return borrowTime;
    }

    public void setBorrowTime(String borrowTime) {
        this.borrowTime = borrowTime;
    }

    public String getRemandTime() {
        return remandTime;
    }

    public void setRemandTime(String remandTime) {
        this.remandTime = remandTime;
    }

    public String getAccessCardNum() {
        return accessCardNum;
    }

    public void setAccessCardNum(String accessCardNum) {
        this.accessCardNum = accessCardNum;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getApprover() {
        return approver;
    }

    public void setApprover(String approver) {
        this.approver = approver;
    }

    public String getApproverPhone() {
        return approverPhone;
    }

    public void setApproverPhone(String approverPhone) {
        this.approverPhone = approverPhone;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getKeyStatus() {
        return keyStatus;
    }

    public void setKeyStatus(Integer keyStatus) {
        this.keyStatus = keyStatus;
    }

    public String getBtsName() {
        return btsName;
    }

    public void setBtsName(String btsName) {
        this.btsName = btsName;
    }
}
