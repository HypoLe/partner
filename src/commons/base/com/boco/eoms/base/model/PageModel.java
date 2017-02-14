package com.boco.eoms.base.model;

import java.util.ArrayList;
import java.util.List;

/** 
 * Description:  
 * Copyright:   Copyright (c)2011
 * Company:     BOCO
 * @author:     Liuchang 
 * @version:    1.0 
 * Create at:   Nov 3, 2011 12:00:19 AM 
 */
public class PageModel {
	/**
	 * 总记录数
	 */
	private int total;

	/**
	 * 当前页结果集
	 */
	private List datas = new ArrayList();

	public List getDatas() {
		return datas;
	}

	public void setDatas(List datas) {
		this.datas = datas;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
}
