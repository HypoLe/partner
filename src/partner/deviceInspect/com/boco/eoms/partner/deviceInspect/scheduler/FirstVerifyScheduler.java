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

import com.boco.activiti.partner.process.po.FirstVerifyReportModel;
import com.boco.activiti.partner.process.service.IPnrTransferOfficeService;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.partner.deviceInspect.util.getJDBCconn;

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
public class FirstVerifyScheduler implements Job{

	public void execute(JobExecutionContext context) throws JobExecutionException {
		IPnrTransferOfficeService pnrTransferOfficeService = (IPnrTransferOfficeService) ApplicationContextHolder.getInstance().getBean("pnrTransferOfficeService");
		//取公客
		Map<String,String> param =new HashMap<String,String>();
		param.put("processType", "maleGuest");//公客
		Map<String, Object> resultMap =pnrTransferOfficeService.collectFirstVerifyData(param);
		int total = (Integer)resultMap.get("total");
		System.out.println(new Date()+":生成一次核验报表 公客 数据完毕！共入库成功"+total+"条数据！");
		
		//取抢修
		Map<String,String> param2 =new HashMap<String,String>();
		param2.put("processType", "transferOffice");//抢修
		Map<String, Object> resultMap2 =pnrTransferOfficeService.collectFirstVerifyData(param2);
		int total2 = (Integer)resultMap2.get("total");
		System.out.println(new Date()+":生成一次核验报表 抢修 数据完毕！共入库成功"+total2+"条数据！");
	}
	
	public static void main(String[] args){
		String processType="transferOffice";
		String process_instance_id="382124";
		Connection con=null;
	    PreparedStatement ps=null;
	    PreparedStatement ps2=null;
	    ResultSet rs=null;
	    int total=0;
	    List<FirstVerifyReportModel> list= new ArrayList<FirstVerifyReportModel>();
	    String sql= "select m.process_instance_id,m.country,m.themeinterface" +
			    	"  from pnr_act_transfer_office_main m" + 
			    	" where m.task_def_key = 'acheck'" + 
			    	"   and m.themeinterface = '"+processType+"'" + 
			    	"   and m.country is not null" + 
			    	"   and m.batch is null" + 
			    	"   and m.process_instance_id='"+process_instance_id+"'";
	   
	    try {
			con = getJDBCconn.getConn();
			ps=con.prepareStatement(sql);     
			rs=ps.executeQuery();
			while(rs.next()){
				FirstVerifyReportModel firstVerifyReportModel= new FirstVerifyReportModel();
				firstVerifyReportModel.setProcessInstanceId(rs.getString("PROCESS_INSTANCE_ID"));
				firstVerifyReportModel.setCounty(rs.getString("COUNTRY"));
				firstVerifyReportModel.setProcessType(rs.getString("themeinterface"));//流程类型
		        list.add(firstVerifyReportModel);
		        System.out.println("---JDBC--:"+rs.getString("PROCESS_INSTANCE_ID"));
		    }
			if(list.size()>0){
				int listSize=list.size();
				con.setAutoCommit(false);// 关闭自动提交
				String sql3 ="insert into pnr_first_verify_report(rise_time,report_date,approve_flag,process_instance_id,county,process_type) values (sysdate,trunc(sysdate-1),'0',?,?,?)";
				String sql2 ="update pnr_act_transfer_office_main m set m.batch = 'Y' where m.process_instance_id = ?";//先存在批次的字段里，存Y值代表已经生成过了
				ps = con.prepareStatement(sql3);
				ps2 = con.prepareStatement(sql2);
				for (int i = 0; i <listSize; i++) {
					//插入一次核验数据
					ps.setString(1,list.get(i).getProcessInstanceId());
					ps.setString(2,list.get(i).getCounty());
					ps.setString(3,list.get(i).getProcessType());//流程类型
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
				System.out.println("---total--:"+total);
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
