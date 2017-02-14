package com.boco.eoms.infmanage.bo;

import java.io.*;
import java.sql.*;
import java.util.*;
import javax.sql.*;
import javax.servlet.http.*;
import javax.servlet.*;
import java.lang.IllegalAccessException;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.*;
import org.apache.struts.util.*;

import com.boco.eoms.common.bo.*;
import com.boco.eoms.common.util.*;
import com.boco.eoms.infmanage.model.*;
import com.boco.eoms.infmanage.controller.*;
import com.boco.eoms.infmanage.dao.*;

public class TawInfAddressBookBO
    extends BO {

  /**
   * @see   ���췽��
   * @param ds  DataSource ����Դ����Struts������ṩ��
   */
  public TawInfAddressBookBO(com.boco.eoms.db.util.ConnectionPool ds) {
    super(ds);
  }

  /**
   * @see   ���ݲ�����ѯ��Ϣ�����ط�����������Ϣ���ϣ�
   * @param offset  int  �Ӹ�����¼����ʾ
   * @param length  int  ÿҳ�ļ�¼����
   * @param tawAPMform  TawMemoForm  ������ѯ��Ϣ��Form
   * @return List����ֵ��
   * @throws SQLException
   */

  public List getlist(int offset, int length, String con) throws SQLException {
    TawInfAddressBook taw = null;
    List tawAPMs = null;
    TawInfAddressBookDAO tawInfAddressBookDAO = null;

    try {
      tawInfAddressBookDAO = new TawInfAddressBookDAO(ds);
      tawAPMs =
          tawInfAddressBookDAO.selectByCondition(offset, length, con);
    }
    catch (SQLException e) {
    }
    finally {
      taw = null;
      tawInfAddressBookDAO = null;
    }

    return tawAPMs;
  }

  /**
   * @see   ��������ѯ��Ϣƴ��SQL����е�"WHERE ..."��ѯ������
   * @param tawAPM  TawMemoForm  ������ѯ��Ϣ��Form
   * @return String����ֵ��
   * @throws SQLException
   */

  public String selectByConditions(TawInfAddressBookForm form, String sdomIds) throws
      SQLException {

    String condition = "";
    Vector vTemp = new Vector();
    int groupId = form.getGroupId();
    String deptName = StaticMethod.null2String(form.getDeptName());
    String company = StaticMethod.null2String(form.getCompany());
    String specialty = StaticMethod.null2String(form.getSpecialty());
    String duty = StaticMethod.null2String(form.getDuty());
    String name = StaticMethod.null2String(form.getName());
    String userId = StaticMethod.null2String(form.getUserId());
    String mobile = StaticMethod.null2String(form.getMobile());
    String officeTel = StaticMethod.null2String(form.getOfficeTel());
    String smart = StaticMethod.null2String(form.getSmart());
    String email = StaticMethod.null2String(form.getEmail());
    String remark = StaticMethod.null2String(form.getRemark());
    String recType = StaticMethod.null2String(form.getRecType());

    try {
      condition =
          " WHERE a.group_id=b.group_id and ";

      if (!sdomIds.equals("")) {
        vTemp.add(" a.dept_id in (" + sdomIds +
                  ") ");
      }
      if (groupId != 0) {
        vTemp.add(" a.group_id = " + groupId + "");
      }
      if (!deptName.trim().equals("")) {
        vTemp.add(" a.dept_name like " + "\'%" + deptName +
                  "%\'  ");
      }

      if (!company.trim().equals("")) {
        vTemp.add(" a.company like " + "\'%" + company +
                  "%\'  ");
      }
      if (!specialty.trim().equals("")) {
        vTemp.add(" a.specialty like  " + "\'%" + specialty +
                  "%\'  ");
      }

      if (!duty.trim().equals("")) {
        vTemp.add(" a.duty like  " + "\'%" + duty +
                  "%\'  ");
      }
      if (!name.trim().equals("")) {
        vTemp.add(" a.name like  " + "\'%" + name +
                  "%\'  ");
      }
      if (!userId.trim().equals("")) {
        vTemp.add(" a.user_id like  " + "\'%" + userId +
                  "%\'  ");
      }
      if (!mobile.trim().equals("")) {
        vTemp.add(" a.mobile like  " + "\'%" + mobile +
                  "%\'  ");
      }

      if (!officeTel.trim().equals("")) {
        vTemp.add(" a.office_tel like  " + "\'%" + officeTel +
                  "%\'  ");
      }
      if (!smart.trim().equals("")) {
        vTemp.add(" a.smart like  " + "\'%" + smart +
                  "%\'  ");
      }
      if (!email.trim().equals("")) {
        vTemp.add(" a.email like " + "\'%" + email +
                  "%\'  ");
      }
      if (!remark.trim().equals("")) {
        vTemp.add(" a.remark like  " + "\'%" + remark +
                  "%\'  ");
      }
      if (!recType.trim().equals("")) {
        vTemp.add(" a.rec_type like  " + "\'%" + recType +
                  "%\'  ");
      }

      for (int i = 0; i < vTemp.size(); i++) {
        condition += vTemp.get(i).toString() + "   and ";
      }
      condition = condition.substring(0, (condition.length() - 5));
      condition +=" order by count desc ";

    }
    catch (Exception e) {
    }
    finally {
      vTemp = null;
      company = null;
      specialty = null;
      duty = null;
      name = null;
      userId = null;
      mobile = null;
      officeTel = null;
      smart = null;
      email = null;
      remark = null;
      recType = null;
      deptName = null;
    }
    return condition;
  }
}
