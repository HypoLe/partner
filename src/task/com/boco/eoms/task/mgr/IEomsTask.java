package com.boco.eoms.task.mgr;

import java.util.List;

import com.boco.eoms.base.service.Manager;

public interface IEomsTask extends Manager{

	public void  savaEomsTaskUser(List eomstaskuserlist,String userid);
	
	public List getEomsTaskUser(String userid);
	
	public List listTaskDrafter(String principal);
}
