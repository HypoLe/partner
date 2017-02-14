package com.boco.eoms.message.dao.hibernate;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.commons.loging.BocoLog;
import com.boco.eoms.message.dao.ISchedulerDao;
import com.boco.eoms.message.mgr.impl.VoiceMonitorManagerImpl;

/**
 * 
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Date:2009-7-21 下午03:40:02
 * </p>
 * 
 * @author 孙圣泰
 * @version 3.5.1
 * 
 */
public class SchedulerDaoHibernate_HeiLongJiang extends BaseDaoHibernate implements
		ISchedulerDao {

	public boolean mmsMonitorScheduler(String mobiles, String subject, List contentList) {
		if(mobiles != null && !mobiles.equals("")) {
			return true;
		}
		return false;
	}

	public boolean smsMonitorScheduler(String tel, String msg) {
		if(tel != null && !tel.equals("")) {
			System.out.println("++++++++++++++++++++++++++++++++HeiLongJiang++++++++++++++++++++++++++");
			return true;
		}
		return false;
	}

	public boolean voiceMonitorScheduler(String t_no, String t_alloc_time, String t_finish_time, String t_content, String t_tel_num, String t_tel_num2, String dispatch_tel) {
		DataSource db = (DataSource)ApplicationContextHolder.getInstance().getBean("voiceSource");
		Statement stmt = null;
		Connection conn = null;
		boolean status = true;
		try {			
			conn = db.getConnection();
			stmt = conn.createStatement();
			String sql = "insert into t_info (t_no,t_alloc_time,t_finish_time,t_content,t_tel_num,t_tel_num2,dispatch_tel,t_allocer_no, t_dealer_no) values ('"+ t_no + "','" + t_alloc_time + "','" + t_finish_time + "','" + t_content + "','" + t_tel_num + "','" + t_tel_num2 + "','" + dispatch_tel + "',0,0)";
			
			stmt.execute(sql);
			
		} catch (Exception e) {
			status = false;			
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				status = false;
				BocoLog.error(VoiceMonitorManagerImpl.class, e.getMessage());
			}
		}
		return status;
	}
	
}