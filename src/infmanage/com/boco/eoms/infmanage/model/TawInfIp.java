package com.boco.eoms.infmanage.model;

public class TawInfIp
{

    // 用户名称
    private String userName;

    // 用户IP地址
    private String userAddress;

    // 联系电话
    private String userTel;

    // 用户类型
    private String userType;

    // 备注
    private String remark;

    // 记录ID
    private int id;

    // 设备编号
    private String devId;

    // 数据设备端口
    private String devPort;

    // 逻辑端口
    private String logicPort;

    // 部门ID
    private int deptId;

    // 用户编号
    private String userId;

    // 用户逻辑电路号
    private String userLogic;

    // 部门名称
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