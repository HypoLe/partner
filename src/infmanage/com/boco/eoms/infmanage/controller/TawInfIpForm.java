package com.boco.eoms.infmanage.controller;

import org.apache.struts.action.ActionForm;
import java.util.Collection;

public class TawInfIpForm
    extends ActionForm
{
    // ��¼ID
    private int id;

    // �û����
    private String userId;

    // �û�����
    private String userName;

    // ����ID
    private int deptId;

    // �û�IP��ַ
    private String userAddress;

    // ��ϵ�绰
    private String userTel;

    // �û�����
    private String userType;

    // �����豸�˿�
    private String devPort;

    // �豸���
    private String devId;

    // �û��߼���·��
    private String userLogic;

    // �߼��˿�
    private String logicPort;

    // ��ע
    private String remark;

    // ��������
    private String deptName;

    public TawInfIpForm()
    {
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public int getDeptId()
    {
        return deptId;
    }

    public void setDeptId(int deptId)
    {
        this.deptId = deptId;
    }

    public String getUserAddress()
    {
        return userAddress;
    }

    public void setUserAddress(String userAddress)
    {
        this.userAddress = userAddress;
    }

    public String getUserTel()
    {
        return userTel;
    }

    public void setUserTel(String userTel)
    {
        this.userTel = userTel;
    }

    public String getUserType()
    {
        return userType;
    }

    public void setUserType(String userType)
    {
        this.userType = userType;
    }

    public String getDevPort()
    {
        return devPort;
    }

    public void setDevPort(String devPort)
    {
        this.devPort = devPort;
    }

    public String getDevId()
    {
        return devId;
    }

    public void setDevId(String devId)
    {
        this.devId = devId;
    }

    public String getUserLogic()
    {
        return userLogic;
    }

    public void setUserLogic(String userLogic)
    {
        this.userLogic = userLogic;
    }

    public String getLogicPort()
    {
        return logicPort;
    }

    public void setLogicPort(String logicPort)
    {
        this.logicPort = logicPort;
    }

    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    public String getDeptName()
    {
        return deptName;
    }

    public void setDeptName(String deptName)
    {
        this.deptName = deptName;
    }
}