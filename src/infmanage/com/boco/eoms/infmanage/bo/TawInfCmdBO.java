package com.boco.eoms.infmanage.bo;

import java.sql.*;
import javax.sql.*;
import java.io.*;
import com.boco.eoms.common.bo.BO;

import com.boco.eoms.infmanage.controller.*;
import com.boco.eoms.common.util.StaticMethod;

public class TawInfCmdBO
    extends BO
{
    public TawInfCmdBO(com.boco.eoms.db.util.ConnectionPool ds)
    {
        super(ds);
    }

    public String getQueryCondition(TawInfCmdForm form)
    {
        String condition = "";
        boolean markWhere = false;

        // ��¼ID
        if (form.getId() != 0)
        {
            if (!markWhere)
            {
                condition = condition + " Where id= " +
                    form.getId();
                markWhere = true;
            }
            else
            {
                condition = condition + " AND id= " + form.getId();
            }
        }

        // ��������������
        if (!StaticMethod.null2String(form.getCmdSwich()).trim().equals(
            ""))
        {
            if (!markWhere)
            {
                condition = condition + " Where cmd_swich LIKE '%" +
                    form.getCmdSwich().trim() + "%'";
                markWhere = true;
            }
            else
            {
                condition = condition + " AND cmd_swich LIKE '%" +
                    form.getCmdSwich().trim() + "%'";
            }
        }

        // ������
        if (!StaticMethod.null2String(form.getCmdId()).trim().equals(
            ""))
        {
            if (!markWhere)
            {
                condition = condition + " where cmd_id LIKE '%" +
                    form.getCmdId().trim() + "%'";
                markWhere = true;
            }
            else
            {
                condition = condition + " AND cmd_id LIKE '%" +
                    form.getCmdId().trim() + "%'";
            }
        }

        // ��������
        if (!StaticMethod.null2String(form.getCmdName()).trim().equals(""))
        {
            if (!markWhere)
            {
                condition = condition + " where cmd_name LIKE '%" +
                    form.getCmdName().trim() + "%'";
                markWhere = true;
            }
            else
            {
                condition = condition + " AND cmd_name LIKE '%" +
                    form.getCmdName().trim() + "%'";
            }
        }

        // �������
        if (!StaticMethod.null2String(form.getCmdParam()).trim().equals(""))
        {
            if (!markWhere)
            {
                condition = condition + " where cmd_param LIKE '%" +
                    form.getCmdParam().trim() + "%'";
                markWhere = true;
            }
            else
            {
                condition = condition + " AND cmd_param LIKE '%" +
                    form.getCmdParam().trim() + "%'";
            }
        }

        // ������ȡֵ��Χ
        if (!StaticMethod.null2String(form.getParamScope()).trim().equals(""))
        {
            if (!markWhere)
            {
                condition = condition + " where param_scope LIKE '%" +
                    form.getParamScope().trim() + "%'";
                markWhere = true;
            }
            else
            {
                condition = condition + " AND param_scope LIKE '%" +
                    form.getParamScope().trim() + "%'";
            }
        }
        return condition;
    }
}