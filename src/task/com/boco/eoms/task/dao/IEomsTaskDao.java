package com.boco.eoms.task.dao;

import java.util.List;
import com.boco.eoms.base.dao.Dao;


public interface IEomsTaskDao extends Dao {

	
	public  void saveEomsTask(List eomstaskuserlist,String userid);
	
	public List getEomsTaskUserByUserid(String userid);
	
	public List listTaskDrafter(String principal) ;
}
