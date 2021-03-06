package com.boco.eoms.workplan.vo;

/**
 * <p>Title: 月度作业计划审批信息数据显示类</p>
 * <p>Description: 提供页面的所需的数据封装</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: boco</p>
 * @author eoms
 * @version 1.0
 */

public class TawwpMonthCheckVO
    implements Comparable {

  private String id; //标识
  private String deptId; //审批部门编号
  private String deptName; //审批部门名称
  private String checkUser; //审批人用户名
  private String checkUserName; //审批人姓名
  private String state; //审批状态编号  0：新建 1：通过 2：驳回
  private String stateName; //审批状态名称
  private String content; //审批信息
  private String crtime; //创建时间 (按创建时间排序)
  private String checktime; //审批时间
  private String flowSerial; //流程标识
  private String stepSerial; //步骤标识

  //以下为各属性set、get方法
  public String getChecktime() {
    if (checktime == null) {
      checktime = "";
    }

    return checktime;
  }

  public String getCheckUser() {
    if (checkUser == null) {
      checkUser = "";
    }

    return checkUser;
  }

  public String getContent() {
    if (content == null) {
      content = "";
    }

    return content;
  }

  public String getCrtime() {
    if (crtime == null) {
      crtime = "";
    }

    return crtime;
  }

  public String getDeptId() {
    if (deptId == null) {
      deptId = "";
    }

    return deptId;
  }

  public String getDeptName() {
    if (deptName == null) {
      deptName = "";
    }

    return deptName;
  }

  public String getId() {
    if (id == null) {
      id = "";
    }

    return id;
  }

  public String getState() {
    if (state == null) {
      state = "";
    }

    return state;
  }

  public String getStateName() {
    if (stateName == null) {
      stateName = "";
    }

    return stateName;
  }

  public void setChecktime(String checktime) {
    this.checktime = checktime;
  }

  public void setCheckUser(String checkUser) {
    this.checkUser = checkUser;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public void setCrtime(String crtime) {
    this.crtime = crtime;
  }

  public void setDeptId(String deptId) {
    this.deptId = deptId;
  }

  public void setDeptName(String deptName) {
    this.deptName = deptName;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setState(String state) {
    this.state = state;
  }

  public void setStateName(String stateName) {
    this.stateName = stateName;
  }

  public String getCheckUserName() {
    return checkUserName;
  }

  public void setCheckUserName(String checkUserName) {
    this.checkUserName = checkUserName;
  }

  public String getFlowSerial() {

    return flowSerial;
  }

  public void setFlowSerial(String flowSerial) {
    this.flowSerial = flowSerial;
  }

  public String getStepSerial() {
    return stepSerial;
  }

  public void setStepSerial(String stepSerial) {
    this.stepSerial = stepSerial;
  }

  //进行排序处理，按创建时间进行排序
  public int compareTo(Object obj) {
    TawwpMonthCheckVO tawwpMonthCheckVO = (TawwpMonthCheckVO) obj;
    String crtime1 = null;
    String crtime2 = null;

    int iRet = 0;

    try {
      crtime1 = this.getChecktime();
      crtime2 = tawwpMonthCheckVO.getChecktime();

      iRet = crtime1.compareTo(crtime2);
    }
    catch (Exception ex) {
      ex.printStackTrace();
    }
    return iRet;
  }

}
