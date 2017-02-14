package com.boco.eoms.commons.statistic.logstat.vo;

import com.boco.eoms.commons.statistic.commonstat.vo.StatDetailVO;

public class LogstatDetailVO extends StatDetailVO {
	
    private String opername;
	private String modelname;
	private String username;
	private String beginnotetime;
	public String getOpername() {
		return opername;
	}
	public void setOpername(String opername) {
		this.opername = opername;
	}
	public String getModelname() {
		return modelname;
	}
	public void setModelname(String modelname) {
		this.modelname = modelname;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getBeginnotetime() {
		return beginnotetime;
	}
	public void setBeginnotetime(String beginnotetime) {
		this.beginnotetime = beginnotetime;
	}

	


	

}
