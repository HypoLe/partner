package com.boco.eoms.partner.taskManager.dao.hibernate;

import java.util.ArrayList;
import java.util.List;

import com.boco.eoms.deviceManagement.common.dao.CommonGenericDaoImpl;
import com.boco.eoms.deviceManagement.common.utils.CommonSqlHelper;
import com.boco.eoms.partner.taskManager.dao.ICarApproveDao;
import com.boco.eoms.partner.taskManager.model.CarApprove;

public class CarApproveDaoHibernate extends CommonGenericDaoImpl<CarApprove, String> implements ICarApproveDao {

	public Object commitCarApprove(Object o){
		this.getSession().saveOrUpdate(o);
		return o;
	}

	public void updateCarStatue(String carNum, String staue) {
		String sql = "update pnr_carinfo set DISPATCH_STATUS='"+staue+"' where CAR_NUMBER='"+carNum+"' ";
		this.getJdbcTemplate().execute(sql);
	}

	@SuppressWarnings("unchecked")
	public List getAllCarApproveOrApply(int offset, int pagesize,String whereStr,String joinStr) {
		List list = new ArrayList();
		String sql ="select approve.ID as id,APPLY_TIME as apply_time,APPLY_USER as apply_user,APPLY_USER_DEPT as apply_user_dept, " +
				"APPROVE_STATUE as approve_statue,BACK_TIME as back_time,BACK_USER as back_user,approve.CAR_NUM as car_num,REMARK as remark, " +
				"approvetask.CAR_APPROVE_ID as car_approve_id,APPROVE_USER_DEPT as approve_user_dept,TASK_ID as task_id,TASK_TYPE as task_type,TASK_NAME as task_name, " +
				"TASK_STATUE as task_statue from  PNR_CAR_APPROVE approve left join Car_Approve_Task  approvetask on approvetask.CAR_APPROVE_ID=approve.ID " +
				"left join PNR_CAR_TASK task on approve.ID = task.CAR_APPROVE_ID "+joinStr+ " "+ whereStr;
		sql = CommonSqlHelper.formatPageSql(sql,offset,pagesize);
		String sql2 = "select count(*) from PNR_CAR_APPROVE approve left join Car_Approve_Task  approvetask on approvetask.CAR_APPROVE_ID=approve.ID" +
				" left join PNR_CAR_TASK task on approve.ID = task.CAR_APPROVE_ID "+joinStr+ " "+ whereStr;

		List approveList = this.getJdbcTemplate().queryForList(sql);
		int total = this.getJdbcTemplate().queryForInt(sql2);
		list.add(approveList);
		list.add(total);
		return list;
	}
	
	/**
	 * 由我申请的列表
	 * @param offset
	 * @param pagesize
	 * @param whereStr
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getByMeApply(int offset, int pagesize,String whereStr){
		List list = new ArrayList();
		String sql ="select approve.ID as id,APPLY_TIME as apply_time,APPLY_USER as apply_user,APPLY_USER_DEPT " +
				"as apply_user_dept,APPROVE_STATUE as approve_statue,BACK_USER as " +
				"back_user,approve.CAR_NUM as car_num,BACK_TIME as back_time,APPROVE_USER as approve_user, " +
				"APPROVE_USER_DEPT as approve_user_dept,TASK_TYPE as task_type,TASK_NAME as task_name,TASK_ID as " +
				"task_id from PNR_CAR_APPROVE approve left join PNR_CAR_TASK task on  approve.ID = task.CAR_APPROVE_ID "+whereStr;
		sql = CommonSqlHelper.formatPageSql(sql,offset,pagesize);
		String sql2 = "select count(*) from PNR_CAR_APPROVE approve left join PNR_CAR_TASK task on  approve.ID = task.CAR_APPROVE_ID "+whereStr;

		List approveList = this.getJdbcTemplate().queryForList(sql);
		int total = this.getJdbcTemplate().queryForInt(sql2);
		list.add(approveList);
		list.add(total);
		return list;
	}
	/**
	 * 
	 *@Description:更新车辆调度状态和车辆的申请单号。
	 *@date May 15, 2013 6:14:31 PM
	 *@author Fengguangping fengguangping@boco.com.cn
	 *@param carNumber：车牌号
	 *@param dispatchStatus：当前调度状态
	 *@param applyid：申请主键id
	 */
	public void updateCarDispatchStatusAndApplyId(String carNumber,String dispatchStatus, String applyid) {
		String sql = "update pnr_carinfo set DISPATCH_STATUS='"+dispatchStatus+"',apply_id='"+applyid+"'  where CAR_NUMBER='"+carNumber+"' ";
		this.getJdbcTemplate().execute(sql);
	}

}
