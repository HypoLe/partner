/**
 * Copyright ? 2003  boco Co.,Ltd
 * All right reserved.
 * Version    Author          Date(YYYY-MM-DD)   Action
 * V1.0.0.0   Wang Zhuo Wei   2003-08-15         created
 */

package com.boco.common.security.exception;

/**
 * <p>Title: BOCO Authentication and Authorization System</p>
 * <p>Description: The Exception of Object Already Exist</p>
 * <p>Copyright: Copyright (c) 2003 boco Co.,Ltd</p>
 * <p>Company: BOCO</p>
 * @author Wang Zhuo Wei
 * @version 1.0
 */

public class ObjectAlreadyExistException
    extends Exception {

  public ObjectAlreadyExistException() {
    super();
  }

  public ObjectAlreadyExistException(String desc) {
    super(desc);
  }
}
