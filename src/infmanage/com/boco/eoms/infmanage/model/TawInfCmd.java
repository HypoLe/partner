package com.boco.eoms.infmanage.model;

public class TawInfCmd
{
    // ��������������
    private String cmdSwich;

    // ��������
    private String cmdName;

    // �������
    private String cmdParam;

    // ������ȡֵ��Χ
    private String paramScope;

    // ����Ļ�������
    private String cmdDes;

    // ��¼ID
    private int id;

    // ������
    private String cmdId;

    // ����ID
    private int deptId;

    public TawInfCmd()
    {
    }

    public String getCmdSwich()
    {
        return cmdSwich;
    }

    public void setCmdSwich(String cmdSwich)
    {
        this.cmdSwich = cmdSwich;
    }

    public String getCmdId()
    {
        return cmdId;
    }

    public void setCmdId(String cmdId)
    {
        this.cmdId = cmdId;
    }

    public String getCmdName()
    {
        return cmdName;
    }

    public void setCmdName(String cmdName)
    {
        this.cmdName = cmdName;
    }

    public String getCmdParam()
    {
        return cmdParam;
    }

    public void setCmdParam(String cmdParam)
    {
        this.cmdParam = cmdParam;
    }

    public String getParamScope()
    {
        return paramScope;
    }

    public void setParamScope(String paramScope)
    {
        this.paramScope = paramScope;
    }

    public String getCmdDes()
    {
        return cmdDes;
    }

    public void setCmdDes(String cmdDes)
    {
        this.cmdDes = cmdDes;
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

}