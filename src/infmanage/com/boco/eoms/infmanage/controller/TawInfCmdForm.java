package com.boco.eoms.infmanage.controller;

import org.apache.struts.action.ActionForm;
import java.util.Collection;

public class TawInfCmdForm
    extends ActionForm
{
    // ��¼ID
    private int id;

    // ��������������
    private String cmdSwich;

    // ������
    private String cmdId;

    // ��������
    private String cmdName;

    // �������
    private String cmdParam;

    // ������ȡֵ��Χ
    private String paramScope;

    // ����Ļ�������
    private String cmdDes;

    private java.util.Collection collectionSwich;

    // ����ID
    private int deptId;

    public TawInfCmdForm()
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

    public java.util.Collection getCollectionSwich()
    {
        return collectionSwich;
    }

    public void setCollectionSwich(java.util.Collection collectionSwich)
    {
        this.collectionSwich = collectionSwich;
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