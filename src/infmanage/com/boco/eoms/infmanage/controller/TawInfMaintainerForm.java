package com.boco.eoms.infmanage.controller;

import org.apache.struts.action.*;
import javax.servlet.http.*;
import org.apache.struts.validator.*;
import java.util.Collection;

public class TawInfMaintainerForm extends ActionForm
{
    // ά����Ա���
    private int maintainerId;

    // ά����Ա����
    private String maintainerName;

    // ά����Ա�Ա�
    private String maintainerSex;

    // �������ű��
    private int deptId;

    // ������������
    private String deptName;

    // ��ϵ�绰
    private String tele;

    // �ƶ��绰
    private String teleMobile;

    // ��ͥ�绰
    private String teleHome;

    // E-Mail
    private String email;

    // ����ר��
    private String special;

    private java.util.Collection collectionSex;

    public TawInfMaintainerForm()
    {
    }
    public int getMaintainerId()
    {
        return maintainerId;
    }
    public void setMaintainerId(int maintainerId)
    {
        this.maintainerId = maintainerId;
    }
    public String getMaintainerName()
    {
        return maintainerName;
    }
    public void setMaintainerName(String maintainerName)
    {
        this.maintainerName = maintainerName;
    }
    public String getMaintainerSex()
    {
        return maintainerSex;
    }
    public void setMaintainerSex(String maintainerSex)
    {
        this.maintainerSex = maintainerSex;
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
    public String getTele()
    {
        return tele;
    }
    public void setTele(String tele)
    {
        this.tele = tele;
    }
    public String getTeleMobile()
    {
        return teleMobile;
    }
    public void setTeleMobile(String teleMobile)
    {
        this.teleMobile = teleMobile;
    }
    public String getTeleHome()
    {
        return teleHome;
    }
    public void setTeleHome(String teleHome)
    {
        this.teleHome = teleHome;
    }
    public String getEmail()
    {
        return email;
    }
    public void setEmail(String email)
    {
        this.email = email;
    }
    public String getSpecial()
    {
        return special;
    }
    public void setSpecial(String special)
    {
        this.special = special;
    }
    public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest)
    {
        /**@todo: finish this method, this is just the skeleton.*/
        return null;
    }
    public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest)
    {
    }
    public java.util.Collection getCollectionSex()
    {
        return collectionSex;
    }
    public void setCollectionSex(java.util.Collection collectionSex)
    {
        this.collectionSex = collectionSex;
    }

}