package com.boco.eoms.infmanage.model;

public class TawInfUpfile
{
    // �ϴ��ļ����
    private int id;

    // ��������Ϣ��ļ�¼ID����
    private int infoId;

    // �ϴ��ļ�����
    private String infUpfileName;
    private String encodename;

    public TawInfUpfile()
    {
    }
    public int getInfoId()
    {
        return infoId;
    }
    public void setInfoId(int infoId)
    {
        this.infoId = infoId;
    }
    public String getInfUpfileName()
    {
        return infUpfileName;
    }
    public void setInfUpfileName(String infUpfileName)
    {
        this.infUpfileName = infUpfileName;
    }
    public int getId()
    {
        return id;
    }
    public void setId(int id)
    {
        this.id = id;
    }
    public String getEncodename()
    {
        return encodename;
    }
    public void setEncodename(String encodename)
    {
        this.encodename = encodename;
    }

}