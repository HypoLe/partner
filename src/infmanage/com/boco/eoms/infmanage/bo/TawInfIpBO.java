package com.boco.eoms.infmanage.bo;

import java.sql.*;
import javax.sql.*;
import java.io.*;
import com.boco.eoms.common.bo.BO;

import com.boco.eoms.infmanage.controller.*;
import com.boco.eoms.common.util.StaticMethod;

public class TawInfIpBO
    extends BO
{
    public TawInfIpBO(com.boco.eoms.db.util.ConnectionPool ds)
    {
        super(ds);
    }

    public String getQueryCondition(TawInfIpForm form,String sdomIds)
    {
        String condition = "";
        boolean markWhere = false;

        // ��¼ID
        if (form.getId() != 0)
        {
            condition = condition + " Where id= " +
                form.getId();
            markWhere = true;
        }

        // �û����
        if (!StaticMethod.null2String(form.getUserId()).trim().equals(
            ""))
        {
            if (!markWhere)
            {
                condition = condition + " Where user_id LIKE '%" +
                    form.getUserId().trim() + "%'";
                markWhere = true;
            }
            else
            {
                condition = condition + " AND user_id LIKE '%" +
                    form.getUserId().trim() + "%'";
            }
        }

        // �û�����
        if (!StaticMethod.null2String(form.getUserName()).trim().equals(
            ""))
        {
            if (!markWhere)
            {
                condition = condition + " where user_name LIKE '%" +
                    form.getUserName().trim() + "%'";
                markWhere = true;
            }
            else
            {
                condition = condition + " AND user_name LIKE '%" +
                    form.getUserName().trim() + "%'";
            }
        }

        // ��������
        if (!StaticMethod.null2String(form.getDeptName()).trim().equals(""))
        {
            if (!markWhere)
            {
                condition = condition + " where dept_name LIKE '%" +
                    form.getDeptName().trim() + "%'";
                markWhere = true;
            }
            else
            {
                condition = condition + " AND dept_name LIKE '%" +
                    form.getDeptName().trim() + "%'";
            }
        }

        // �û�IP��ַ
        if (!StaticMethod.null2String(form.getUserAddress()).trim().equals(""))
        {
            if (!markWhere)
            {
                condition = condition + " where user_address LIKE '%" +
                    form.getUserAddress().trim() + "%'";
                markWhere = true;
            }
            else
            {
                condition = condition + " AND user_address LIKE '%" +
                    form.getUserAddress().trim() + "%'";
            }
        }

        // ��ϵ�绰
        if (!StaticMethod.null2String(form.getUserTel()).trim().equals(""))
        {
            if (!markWhere)
            {
                condition = condition + " where user_tel LIKE '%" +
                    form.getUserTel().trim() + "%'";
                markWhere = true;
            }
            else
            {
                condition = condition + " AND user_tel LIKE '%" +
                    form.getUserTel().trim() + "%'";
            }
        }

        // �û�����
        if (!StaticMethod.null2String(form.getUserType()).trim().equals(""))
        {
            if (!markWhere)
            {
                condition = condition + " where user_type LIKE '%" +
                    form.getUserType().trim() + "%'";
                markWhere = true;
            }
            else
            {
                condition = condition + " AND user_type LIKE '%" +
                    form.getUserType().trim() + "%'";
            }
        }

        // �����豸�˿�
        if (!StaticMethod.null2String(form.getDevPort()).trim().equals(""))
        {
            if (!markWhere)
            {
                condition = condition + " where dev_port LIKE '%" +
                    form.getDevPort().trim() + "%'";
                markWhere = true;
            }
            else
            {
                condition = condition + " AND dev_port LIKE '%" +
                    form.getDevPort().trim() + "%'";
            }
        }

        // �豸���
        if (!StaticMethod.null2String(form.getDevId()).trim().equals(""))
        {
            if (!markWhere)
            {
                condition = condition + " where dev_id LIKE '%" +
                    form.getDevId().trim() + "%'";
                markWhere = true;
            }
            else
            {
                condition = condition + " AND dev_id LIKE '%" +
                    form.getDevId().trim() + "%'";
            }
        }

        // �û��߼���·��
        if (!StaticMethod.null2String(form.getUserLogic()).trim().equals(""))
        {
            if (!markWhere)
            {
                condition = condition + " where user_logic LIKE '%" +
                    form.getUserLogic().trim() + "%'";
                markWhere = true;
            }
            else
            {
                condition = condition + " AND user_logic LIKE '%" +
                    form.getUserLogic().trim() + "%'";
            }
        }

        // �߼��˿�
        if (!StaticMethod.null2String(form.getLogicPort()).trim().equals(""))
        {
            if (!markWhere)
            {
                condition = condition + " where logicport LIKE '%" +
                    form.getLogicPort().trim() + "%'";
                markWhere = true;
            }
            else
            {
                condition = condition + " AND logicport LIKE '%" +
                    form.getLogicPort().trim() + "%'";
            }
        }

        // ��ע
        if (!StaticMethod.null2String(form.getRemark()).trim().equals(""))
        {
            if (!markWhere)
            {
                condition = condition + " where remark LIKE '%" +
                    form.getRemark().trim() + "%'";
                markWhere = true;
            }
            else
            {
                condition = condition + " AND remark LIKE '%" +
                    form.getRemark().trim() + "%'";
            }
        }

        //
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