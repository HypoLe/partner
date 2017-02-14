package com.boco.eoms.partner.contact.scheduler;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.deviceManagement.common.utils.CommonSqlHelper;

public class ContactScheduler implements Job{
	public void execute(JobExecutionContext context) throws JobExecutionException {
		JdbcTemplate jdbcTemplate = (JdbcTemplate) ApplicationContextHolder.getInstance().getBean("jdbcTemplate");
	    String sql="";
		if(CommonSqlHelper.isInformixDialect()){
	    	sql="ALTER SEQUENCE SEQ_CONTACT restart  WITH 1";
	    }else{//如oracle，不能重置sequence,破而立
	    	try {
				jdbcTemplate.execute("drop sequence SEQ_CONTACT ");  
			} catch (DataAccessException e) {
				System.out.println("首次，无需先drop");
			}
	    	sql="create sequence SEQ_CONTACT minvalue 1 maxvalue 99999 start with 1 increment by 1 NOCACHE";
	    }
		jdbcTemplate.execute(sql);
		System.out.println("------业务联系函，重置SEQUENCE SEQ_CONTACT------"+sql);
	}
}
