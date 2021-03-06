//---------------------------------------------------------
// Application: Application Name
// Author     : Author
// File       : TawRmChangedutyForm.java
//
// Copyright 2003 Company
//
// Generated at Thu Mar 27 10:17:32 CST 2003
// using Karapan Sapi Struts Generator
// Visit http://www.javanovic.com
//---------------------------------------------------------

package com.boco.eoms.duty.controller;

import org.apache.struts.action.*;
import org.apache.struts.validator.*;

import com.boco.eoms.duty.model.*;

public class TawRmChangedutyForm extends ValidatorForm {
  public final static int ADD = 1;
  public final static int EDIT = 2;
  private int strutsAction;
  private String strutsButton = "";
  private int roomId = 0;
  private String inputdate = "";
  private int workserial1 = 0;
  private int workserial2 = 0;
  private String expireddate = "";
  private String hander = "";
  private String receiver = "";
  private int flag = 0;
  private String reason="";
  private int id = 0;

  public int getStrutsAction() {
    return strutsAction;
  }
  public String getStrutsButton() {
    return strutsButton;
  }
  public int getRoomId() {
    return roomId;
  }
  public String getInputdate() {
    return inputdate;
  }
  public int getWorkserial1() {
    return workserial1;
  }
  public int getWorkserial2() {
    return workserial2;
  }
  public String getExpireddate() {
    return expireddate;
  }
  public String getHander() {
    return hander;
  }
  public String getReceiver() {
    return receiver;
  }
  public int getFlag() {
    return flag;
  }
  public int getId() {
    return id;
  }

  public void setStrutsAction(int strutsAction) {
    this.strutsAction = strutsAction;
  }
  public void setStrutsButton(String strutsButton) {
    this.strutsButton = strutsButton;
  }
  public void setRoomId(int roomId) {
    this.roomId = roomId;
  }
  public void setInputdate(String inputdate) {
    this.inputdate = inputdate;
  }
  public void setWorkserial1(int workserial1) {
    this.workserial1 = workserial1;
  }
  public void setWorkserial2(int workserial2) {
    this.workserial2 = workserial2;
  }
  public void setExpireddate(String expireddate) {
    this.expireddate = expireddate;
  }
  public void setHander(String hander) {
    this.hander = hander;
  }
  public void setReceiver(String receiver) {
    this.receiver = receiver;
  }
  public void setFlag(int flag) {
    this.flag = flag;
  }
  public void setId(int id) {
    this.id = id;
  }
  public String getReason() {
    return reason;
  }
  public void setReason(String reason) {
    this.reason = reason;
  }

}
