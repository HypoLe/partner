package com.boco.eoms.commons.system.user.util;

import java.util.ArrayList;
import java.util.List;

import com.boco.eoms.commons.system.user.model.TawSystemUser;

public class Accounts {
	
	private List accountList = new ArrayList();

	public void addAccount(TawSystemUser tawSystemUser) {
		accountList.add(tawSystemUser);
	}

	public List getAccountList() {
		return accountList;
	}

	public void setAccountList(List accountList) {
		this.accountList = accountList;
	}


}
