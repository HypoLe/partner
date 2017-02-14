package com.boco.eoms.infmanage.bo;

import java.sql.*;
import javax.sql.*;
import java.io.*;
import com.boco.eoms.common.bo.BO;

import com.boco.eoms.infmanage.controller.*;
import com.boco.eoms.common.util.StaticMethod;

public class TawInfMaintainerBO
    extends BO
{
    public TawInfMaintainerBO(com.boco.eoms.db.util.ConnectionPool ds)
    {
        super(ds);
    }

    public String getQueryCondition(TawInfMaintainerForm form, String sdomIds)
    {
        // taw_inf_maintainer a
        // taw_dept b
        String condition = "";
        boolean markWhere = false;

        // ����ID
        if (form.getDeptId() != 0)
        {
            condition = condition + " Where dept_id= " +
                form.getDeptId();
            markWhere = true;
        }

        // ����
        if (!StaticMethod.null2String(form.getMaintainerName()).trim().equals(
            ""))
        {
            if (!markWhere)
            {
                condition = condition + " Where maintainer_name LIKE '%" +
                    form.getMaintainerName().trim() + "%'";
                markWhere = true;
            }
            else
            {
                condition = condition + " AND maintainer_name LIKE '%" +
                    form.getMaintainerName().trim() + "%'";
            }
        }

        // �Ա�
        if (!StaticMethod.null2String(form.getMaintainerSex()).trim().equals(
            ""))
        {
            if (!markWhere)
            {
                condition = condition + " where maintainer_sex LIKE '%" +
                    form.getMaintainerSex().trim() + "%'";
                markWhere = true;
            }
            else
            {
                condition = condition + " AND maintainer_sex LIKE '%" +
                    form.getMaintainerSex().trim() + "%'";
            }
        }

        // ��ϵ�绰
        if (!StaticMethod.null2String(form.getTele()).trim().equals(""))
        {
            if (!markWhere)
            {
                condition = condition + " where tele LIKE '%" +
                    form.getTele().trim() + "%'";
                markWhere = true;
            }
            else
            {
                condition = condition + " AND tele LIKE '%" +
                    form.getTele().trim() + "%'";
            }
        }

        // �ƶ��绰
        if (!StaticMethod.null2String(form.getTeleMobile()).trim().equals(""))
        {
            if (!markWhere)
            {
                condition = condition + " where tele_mobile LIKE '%" +
                    form.getTeleMobile().trim() + "%'";
                markWhere = true;
            }
            else
            {
                condition = condition + " AND tele_mobile LIKE '%" +
                    form.getTeleMobile().trim() + "%'";
            }
        }

        if (!sdomIds.equals(""))
        {
            if (!markWhere)
            {
                condition = condition + " where dept_id in (" + sdomIds + ") ";
                markWhere = true;
            }
            else
            {
                condition = condition + " AND dept_id in (" + sdomIds + ") ";
            }
        }

        return condition;
    }
}