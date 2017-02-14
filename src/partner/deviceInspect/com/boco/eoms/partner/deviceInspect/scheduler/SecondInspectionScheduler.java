package com.boco.eoms.partner.deviceInspect.scheduler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.boco.activiti.partner.process.po.SecondInspectionReportModel;
import com.boco.activiti.partner.process.service.IPnrTransferOfficeService;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.partner.deviceInspect.util.getJDBCconn;
import com.boco.eoms.partner.netresource.dao.IEomsDao;

/**
 * 
 	* @author WangJun
 	* @ClassName: FirstVerifyScheduler
 	* @Version 1.0
 	* @ModifiedBy WangJun
 	* @Copyright 亿阳
 	* @date Nov 27, 2015 9:49:09 AM
 	* @description 抢修工单审核 生成一次核验报表 每天0点生成，前一天0点到24点之间的公客工单施工队回单的工单数据
 */
public class SecondInspectionScheduler implements Job{

	public void execute(JobExecutionContext context) throws JobExecutionException {
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) ApplicationContextHolder.getInstance().getBean("pnrTransferOfficeService");
		Map<String,String> param =new HashMap<String,String>();
		Map<String, Object> resultMap =pnrTransferOfficeService.collectSecondInspectionData(param);
		int total = (Integer)resultMap.get("total");
		System.out.println(new Date()+":生成二次抽查报表数据完毕！共入库成功"+total+"条数据！");
	}
	public static void main(String[] args){
		String process_instance_id="382124";
		List<SecondInspectionReportModel> list= new ArrayList<SecondInspectionReportModel>();
		Connection con=null;
	    PreparedStatement ps=null;
	    PreparedStatement ps2=null;
	    ResultSet rs=null;
	    int total=0;
	    String sql ="select r.process_instance_id, r.county, r.process_type" +
			    	"  from pnr_first_verify_report r, pnr_act_transfer_office_main m" + 
			    	" where r.process_instance_id = m.process_instance_id" + 
			    	"   and m.task_def_key = 'twoSpotChecks'" + 
			    	"   and r.approve_flag = '1'" + 
			    	"   and m.job_order_type is null" + 
			    	"   and m.process_instance_id='"+process_instance_id+"'";

	  //  System.out.println("---------查询前一天的0点到24点的一次核验通过的工单数据 (一次核验提交审批人签字的工单)="+sql);
	    try {
			con = getJDBCconn.getConn();
			ps=con.prepareStatement(sql);     
			rs=ps.executeQuery();
			while(rs.next()){
				SecondInspectionReportModel secondInspectionReportModel= new SecondInspectionReportModel();
				secondInspectionReportModel.setProcessInstanceId(rs.getString("PROCESS_INSTANCE_ID"));
				secondInspectionReportModel.setCounty(rs.getString("county"));
				secondInspectionReportModel.setProcessType(rs.getString("process_type"));
		        list.add(secondInspectionReportModel);
		    }
			if(list.size()>0){
				int listSize=list.size();
				String sql3 ="insert into pnr_second_inspection_report(rise_time,report_date,approve_flag,process_instance_id,county,process_type) values (sysdate,trunc(sysdate-1),'0',?,?,?)";
				String sql2 ="update pnr_act_transfer_office_main m set m.job_order_type = 'Y' where m.process_instance_id = ?";//先存在m.job_order_type的字段里，存Y值代表已经生成过了
				ps = con.prepareStatement(sql3);
				ps2 = con.prepareStatement(sql2);
				for (int i = 0; i <listSize; i++) {
					ps.setString(1,list.get(i).getProcessInstanceId());
					ps.setString(2,list.get(i).getCounty());
					ps.setString(3,list.get(i).getProcessType());
					ps.addBatch();
					
					//批量更新主表
					ps2.setString(1,list.get(i).getProcessInstanceId());
					ps2.addBatch();
					
					if (i% 100 == 0) {
						int[] executeBatch = ps.executeBatch();
						ps2.executeBatch();
						con.commit();
						total=total+executeBatch.length;
						ps.clearBatch();
						ps2.clearBatch();
					}
				}
				// 最后插入不足100条的数据
				int[] executeBatch2=ps.executeBatch();
				ps2.executeBatch();
				con.commit();	
				total=total+executeBatch2.length;
			}
		}  catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
}
