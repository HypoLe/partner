package com.boco.eoms.infmanage.controller;

import org.apache.struts.action.ActionForm;
import java.util.Collection;

public class TawInfInfoForm
    extends ActionForm
{
    // ��¼ID
    private int id;

    // ���ϱ��
    private String infInfoId;

    // ��������
    private String infInfoName;

    // ���������
    private int infSortId;

    // �����������
    private String infSortName;

    // �����������ű��
    private int deptId;

    // ����������������
    private String deptName;

    // �ϴ���ID
    private String infUpId;

    // �ϴ�������
    private String infUpName;

    // �ϴ��ļ�����
    private String infUpfileName;

    // �ϴ�ʱ��
    private String infUpTime;

    private java.util.Collection collectionSort;

    public TawInfInfoForm()
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

    public String getInfInfoId()
    {
        return infInfoId;
    }

    public void setInfInfoId(String infInfoId)
    {
        this.infInfoId = infInfoId;
    }

    public String getInfInfoName()
    {
        return infInfoName;
    }

    public void setInfInfoName(String infInfoName)
    {
        this.infInfoName = infInfoName;
    }

    public int getInfSortId()
    {
        return infSortId;
    }

    public void setInfSortId(int infSortId)
    {
        this.infSortId = infSortId;
    }

    public int getDeptId()
    {
        return deptId;
    }

    public void setDeptId(int deptId)
    {
        this.deptId = deptId;
    }

    public String getInfUpId()
    {
        return infUpId;
    }

    public void setInfUpId(String infUpId)
    {
        this.infUpId = infUpId;
    }

    public String getInfUpTime()
    {
        return infUpTime;
    }

    public void setInfUpTime(String infUpTime)
    {
        this.infUpTime = infUpTime;
    }

    public String getDeptName()
    {
        return deptName;
    }

    public void setDeptName(String deptName)
    {
        this.deptName = deptName;
    }

    public String getInfSortName()
    {
        return infSortName;
    }

    public void setInfSortName(String infSortName)
    {
        this.infSortName = infSortName;
    }

    public String getInfUpName()
    {
        return infUpName;
    }

    public void setInfUpName(String infUpName)
    {
        this.infUpName = infUpName;
    }

    public java.util.Collection getCollectionSort()
    {
        return collectionSort;
    }

    public void setCollectionSort(java.util.Collection collectionSort)
    {
        this.collectionSort = collectionSort;
    }

    public String getInfUpfileName()
    {
        return infUpfileName;
    }

    public void setInfUpfileName(String infUpfileName)
    {
        this.infUpfileName = infUpfileName;
    }

}