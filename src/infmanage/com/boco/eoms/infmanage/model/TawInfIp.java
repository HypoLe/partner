package com.boco.eoms.infmanage.model;

public class TawInfIp
{

    // �û�����
    private String userName;

    // �û�IP��ַ
    private String userAddress;

    // ��ϵ�绰
    private String userTel;

    // �û�����
    private String userType;

    // ��ע
    private String remark;

    // ��¼ID
    private int id;

    // �豸���
    private String devId;

    // �����豸�˿�
    private String devPort;

    // �߼��˿�
    private String logicPort;

    // ����ID
    private int deptId;

    // �û����
    private String userId;

    // �û��߼���·��
    private String userLogic;

    // ��������
    private String deptName;

    public TawInfIp()
    {
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

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public int getDeptId()
    {
        return deptId;
    }

    public void setDeptId(int deptId)
    {
        this.deptId = deptId;
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