package com.boco.activiti.partner.process.dao.hibernate;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.Task;
import org.hibernate.dialect.IngresDialect;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.boco.activiti.partner.process.dao.IPnrTaskTicketJDBCDao;
import com.boco.activiti.partner.process.po.TaskModel;
import com.boco.activiti.partner.process.po.WorkOrderStatisticsModel;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.commons.accessories.dao.TawCommonsAccessoriesDao;
import com.boco.eoms.commons.accessories.model.TawCommonsAccessories;
import com.boco.eoms.commons.interfaceMonitoring.util.interfaceMonitorin;
import com.boco.eoms.partner.process.util.CommonUtils;

/**

 */
public class PnrTaskTicketDaoJDBC extends JdbcDaoSupport implements IPnrTaskTicketJDBCDao {
    private TaskService taskService;
    private HistoryService historyService;
    
    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }
    
    public HistoryService getHistoryService() {
		return historyService;
	}

	public void setHistoryService(HistoryService historyService) {
		this.historyService = historyService;
	}

	@Override
    public List<WorkOrderStatisticsModel> workOrderStatistics(String beginTime, String endTime, String subType) {
        List<WorkOrderStatisticsModel> r = new ArrayList<WorkOrderStatisticsModel>();
        String whereSql = " where create_time > to_date('" + beginTime +
                "','yyyy-MM-dd HH24:mi:ss') and create_time<  to_date('" + endTime + "','yyyy-MM-dd HH24:mi:ss') ";
        if (subType != null && !subType.equals("")) {
            whereSql = whereSql + " and sub_type=  '" + subType + "'";
        }
        String sumNumSql = "select city,count(id)as sumNum from pnr_act_task_ticket_main";
        String unfiledNumSql = "select city,sum(decode(archiving_time,'',1,null,0,0))as unfiledNum from pnr_act_task_ticket_main";
        String overtimeNumSql = "select city,sum(decode(sign(nvl(last_reply_time,sysdate)-end_time),1,1,-1,0,0))as overtimeNum from pnr_act_task_ticket_main";
        String endSelectSql = "group by city";
        String sql = "select area.areaid,area.areaname,a.sumNum,b.unfiledNum,c.overtimeNum  from " +
                " (select areaid,areaname from taw_system_area where parentareaid=28) area left join" +
                " (" + sumNumSql + whereSql + endSelectSql + ") a on area.areaid=a.city left join" +
                " (" + unfiledNumSql + whereSql + endSelectSql + ") b on area.areaid=b.city left join" +
                " (" + overtimeNumSql + whereSql + "" + endSelectSql + ") c on area.areaid=c.city";
        List<Map> list = this.getJdbcTemplate().queryForList(sql);
        for (Map map : list) {
            WorkOrderStatisticsModel workOrderStatisticsModel = new WorkOrderStatisticsModel();
            workOrderStatisticsModel.setCity(map.get("areaid").toString());
            workOrderStatisticsModel.setCityName(map.get("areaname").toString());
            if (map.get("sumNum") == null) {
                workOrderStatisticsModel.setSumNum(0);
            } else {
                workOrderStatisticsModel.setSumNum(Integer.parseInt(map
                        .get("sumNum").toString()));
            }
            if (map.get("overtimeNum") == null) {
                workOrderStatisticsModel.setOvertimeNum(0);
            } else {
                workOrderStatisticsModel.setOvertimeNum(Integer.parseInt(map
                        .get("overtimeNum").toString()));
            }
            if (map.get("unfiledNum") == null) {
                workOrderStatisticsModel.setUnfiledNumber(0);
            } else {
                workOrderStatisticsModel.setUnfiledNumber(Integer.parseInt(map
                    .get("unfiledNum").toString()));
            }

            if(workOrderStatisticsModel.getSumNum()==0){
                workOrderStatisticsModel.setArchiveNumber(0);
            } else{
                if(workOrderStatisticsModel.getUnfiledNumber()==0){
                    workOrderStatisticsModel.setArchiveNumber(workOrderStatisticsModel.getSumNum());
                }else{
                    workOrderStatisticsModel.setArchiveNumber(workOrderStatisticsModel.getSumNum() - workOrderStatisticsModel.getUnfiledNumber());
                }
            }
            if(workOrderStatisticsModel.getSumNum()!=0&&workOrderStatisticsModel.getOvertimeNum()!=0){
                workOrderStatisticsModel.setOvertimeRate(this.calculateThePercentage(new Double(workOrderStatisticsModel.getOvertimeNum()),new Double(workOrderStatisticsModel.getSumNum())));
            }else{
                workOrderStatisticsModel.setOvertimeRate("0%");
            }

            r.add(workOrderStatisticsModel);
        }
        return r;
    }
    private String calculateThePercentage(Double a, Double b) {
        NumberFormat nf = NumberFormat.getPercentInstance();
        nf.setMinimumFractionDigits(2);
        NumberFormat nf1 = NumberFormat.getPercentInstance();
        nf.setMinimumFractionDigits(0);
        if (a == null || a.equals(0) || a.equals(0.0)|| a.equals(0.00)) {
            return nf1.format(0);
        }
        if (b == null || b.equals(0) || b.equals(0.0)|| a.equals(0.00)) {
            return nf1.format(0);
        }
        Double d = new Double(0);
        d = a / b;
        if (d == null || d.equals(100) || d.equals(100.0)|| a.equals(100.00)) {
            return nf1.format(100);
        }
        String r = nf.format(d);
        return r;
    }
    @Override
    public List<TaskModel> workOrderQuery(String deptId,String workerid,String beginTime, String endTime, String subType, String theme, String city, int beginNum, int endNum) {
       
    	List<TaskModel> r = new ArrayList<TaskModel>();
        String whereSql = " where tm.state=5  ";
        if (beginTime != null && !beginTime.equals("")) {
            whereSql = whereSql + " and tm.create_time > to_date('" + beginTime +
                    "','yyyy-MM-dd HH24:mi:ss')";
        }
        if (endTime != null && !endTime.equals("")) {
            whereSql = whereSql + " and tm.create_time < to_date('" + endTime +
                    "','yyyy-MM-dd HH24:mi:ss')";
        }
        if (subType != null && !subType.equals("")) {
            whereSql = whereSql + " and tm.sub_type=  '" + subType + "'";
        }
        if (theme != null && !theme.equals("")) {
            whereSql = whereSql + " and tm.theme like  '%" + theme + "%'";
        }
        if (city != null && !city.equals("")) {
            whereSql = whereSql + " and tm.city =  '" + city + "'";
        }
        if(deptId!=null&&!deptId.equals("")){
        	
        	/*if(deptId.equals("-1")){
        		
        		whereSql=whereSql+" and tm.candidate_group like '%'";
        	}else{*/
        		whereSql=whereSql+" and tm.candidate_group like '"+deptId+"%'";
        		
//        	}
        }
        if(!"".equals(workerid)&&null != workerid){
        	String process_instance_id="\'\'";
        	
            List<HistoricTaskInstance> listw = historyService.createHistoricTaskInstanceQuery().taskAssignee(workerid).list();
           
            for(int j=0;j<listw.size();j++){
            	
                List<HistoricTaskInstance> historicTasks = historyService.createHistoricTaskInstanceQuery().processInstanceId(listw.get(j).getProcessInstanceId()).orderByTaskId().desc().listPage(0, 2);
                
                if("completed".equals(historicTasks.get(0).getDeleteReason())){
               	    if(historicTasks.get(0).getAssignee().equals(workerid)){
               	    	
               	    	
               	    	if("".equals(process_instance_id)){
               	    		
               	    		process_instance_id=listw.get(j).getProcessInstanceId();
               	    	}else{
               	    		
               	    		process_instance_id +=","+listw.get(j).getProcessInstanceId();
               	    	}
               	    }
                }else{
                	if(historicTasks.get(1).getAssignee().equals(workerid)){
               	    	
                		
                		if("".equals(process_instance_id)){
                			
                			process_instance_id=listw.get(j).getProcessInstanceId();
                		}else{
                			
                			process_instance_id +=","+listw.get(j).getProcessInstanceId();
                		}
               	    }
                }
                
             
               
        		
        	}
            
           if(!process_instance_id.equals("")){
        	   
        	   whereSql = whereSql + " and tm.process_instance_id in (" + process_instance_id + ")";
           }
        }
        String sql = "select process_instance_id,theme,initiator,deptid,create_time from (select tm.process_instance_id,tm.theme,tm.initiator,u2.deptid,tm.create_time, rownum rwn    from pnr_act_task_ticket_main tm left join taw_system_user u2 on tm.initiator = u2.userid " + whereSql + " and rownum <=" + endNum + " order by tm.create_time desc) where rwn>" + beginNum;
        System.out.println("PnrTaskTicketDao--203---"+sql);
        List<Map> list = this.getJdbcTemplate().queryForList(sql);
        for (Map map : list) {
            TaskModel taskModel = new TaskModel();
            taskModel.setDeptId(map
            		.get("DEPTID").toString());
            taskModel.setProcessInstanceId(map
                    .get("process_instance_id").toString());
            taskModel.setTheme(map
                    .get("theme").toString());
            taskModel.setInitiator(map
            		.get("INITIATOR").toString());
            try {
        		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            	taskModel.setSendTime(dateFormat.parse(map.get("CREATE_TIME").toString()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
          
            List<Task> taskList = taskService.createTaskQuery().processInstanceId(taskModel.getProcessInstanceId()).active().list();

          
            List<HistoricTaskInstance> historicTasks = historyService.createHistoricTaskInstanceQuery().processInstanceId(taskModel.getProcessInstanceId()).orderByTaskId().desc().listPage(0, 2);
           
            if (taskList != null && taskList.size() == 1) {
                Task task = taskList.get(0);
                taskModel.setStatusName(task.getName());
                taskModel.setTaskId(task.getId());

            }
            
            
           if("delete".equals(historicTasks.get(0).getDeleteReason())){
           	    taskModel.setExecutor(historicTasks.get(1).getAssignee());
            	taskModel.setStatusName(CommonUtils.taskDetele);
            }else if("completed".equals(historicTasks.get(0).getDeleteReason())){
            	
            	taskModel.setExecutor(historicTasks.get(0).getAssignee());
            	taskModel.setStatusName(CommonUtils.taskComplete);
            }else{
            	taskModel.setExecutor(historicTasks.get(1).getAssignee());
            }
            
           
            r.add(taskModel);
            
        }
        return r;
    }

    @Override
    public int workOrderCount(String deptId,String workerid,String beginTime, String endTime, String subType, String theme, String city) {
        List<TaskModel> r = new ArrayList<TaskModel>();
    	String whereSql = " where state=5 ";
        if (beginTime != null && !beginTime.equals("")) {
            whereSql = whereSql + " and create_time > to_date('" + beginTime +
                    "','yyyy-MM-dd HH24:mi:ss')";
        }
        if (endTime != null && !endTime.equals("")) {
            whereSql = whereSql + " and create_time < to_date('" + endTime +
                    "','yyyy-MM-dd HH24:mi:ss')";
        }
        if (subType != null && !subType.equals("")) {
            whereSql = whereSql + " and sub_type=  '" + subType + "'";
        }
        if (theme != null && !theme.equals("")) {
            whereSql = whereSql + " and theme like  '%" + theme + "%'";
        }
        if (city != null && !city.equals("")) {
            whereSql = whereSql + " and city =  '" + city + "'";
        }
        if(deptId!=null&&!deptId.equals("")){
        	
        /*	if(deptId.equals("-1")){
        		
        		whereSql=whereSql+" and candidate_group like '%'";
        	}else{*/
        		whereSql=whereSql+" and candidate_group like '"+deptId+"%'";
        		
//        	}
        }
        if(!"".equals(workerid)&&null != workerid){
        	String process_instance_id="";
        	
            List<HistoricTaskInstance> listw = historyService.createHistoricTaskInstanceQuery().taskAssignee(workerid).list();
           
            for(int j=0;j<listw.size();j++){
            	
                List<HistoricTaskInstance> historicTasks = historyService.createHistoricTaskInstanceQuery().processInstanceId(listw.get(j).getProcessInstanceId()).orderByTaskId().desc().listPage(0, 2);
                
                if("completed".equals(historicTasks.get(0).getDeleteReason())){
               	    if(historicTasks.get(0).getAssignee().equals(workerid)){
               	    	
               	    	
               	    	if("".equals(process_instance_id)){
               	    		
               	    		process_instance_id=listw.get(j).getProcessInstanceId();
               	    	}else{
               	    		
               	    		process_instance_id +=","+listw.get(j).getProcessInstanceId();
               	    	}
               	    }
                }else{
                	if(historicTasks.get(1).getAssignee().equals(workerid)){
               	    	
                		
                		if("".equals(process_instance_id)){
                			
                			process_instance_id=listw.get(j).getProcessInstanceId();
                		}else{
                			
                			process_instance_id +=","+listw.get(j).getProcessInstanceId();
                		}
               	    }
                }
                
             
               
        		
        	}
            
            if(!process_instance_id.equals("")){

        	whereSql = whereSql + " and process_instance_id in (" + process_instance_id + ")";
            }
        }
        String sql = "select process_instance_id from pnr_act_task_ticket_main " + whereSql;
        System.out.println("PnrTaskTicketDao--321---"+sql);
        List<Map> list = this.getJdbcTemplate().queryForList(sql);
        
        return list.size();
    }

	@Override
	public void saveOrUpatePerson(String processInstanceId,
			String[] personStrings) {
		// TODO Auto-generated method stub
		final String[] personS =personStrings;
		final String process = processInstanceId;
//		先删除已处理的前一批
		String delSql = "delete from pnr_act_task_person_relate where process_instance_id =?";
		this.getJdbcTemplate().execute(delSql, new PreparedStatementCallback(){
			  public Object doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
			   	 	ps.setString(1, process);			     
			    	ps.addBatch();
			      return ps.executeBatch();
			     }
		});
//		保存数据
	
		String sql = "insert into pnr_act_task_person_relate values(?,?)";
		this.getJdbcTemplate().execute(sql, new PreparedStatementCallback(){
			  public Object doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
			    
				  for(int i =0 ; i< personS.length ; i++){
			    	ps.setString(1, process);			     
			    	ps.setString(2, personS[i]);
			    	ps.addBatch();
				  }
			      return ps.executeBatch();
			     }
		});
//		return 0;
	}

	@Override
	public void saveOrUpateSpecialty(String processInstanceId,
			String[] specialtyStrings) {
		// TODO Auto-generated method stub
		final String[] personS =specialtyStrings;
		final String process = processInstanceId;
//		先删除已处理的前一批
		String delSql = "delete from pnr_act_task_spec_relate where process_instance_id =?";
		this.getJdbcTemplate().execute(delSql, new PreparedStatementCallback(){
			  public Object doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
			   	 	ps.setString(1, process);			     
			    	ps.addBatch();
			      return ps.executeBatch();
			     }
		});
//		保存数据
	
		String sql = "insert into pnr_act_task_spec_relate values(?,?)";
		this.getJdbcTemplate().execute(sql, new PreparedStatementCallback(){
			  public Object doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
			    
				  for(int i =0 ; i< personS.length ; i++){
			    	ps.setString(1, process);			     
			    	ps.setString(2, personS[i]);
			    	ps.addBatch();
				  }
			      return ps.executeBatch();
			     }
		});
	}
	@Override
	public void saveOrUpateAttachment(String processInstanceId,
			String accessoriesNames) {
		// TODO Auto-generated method stub
		final String accessorieS =accessoriesNames;
		final String process = processInstanceId;
//		先删除已存在的前一批
		String delSql = "delete from pnr_act_task_attachment where process_instance_id =?";
		this.getJdbcTemplate().execute(delSql, new PreparedStatementCallback(){
			  public Object doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
			   	 	ps.setString(1, process);			     
			    	ps.addBatch();
			      return ps.executeBatch();
			     }
		});
//	    保存数据
		String sql = "insert into pnr_act_task_attachment(process_instance_id,accessories_id,accessoriesname) values(?,?,?)";
//	according to accessoriesNames to find the primary key of table(taw_commons_accessories)
		TawCommonsAccessoriesDao tawCommonsAccessoriesDao = (TawCommonsAccessoriesDao)ApplicationContextHolder.getInstance().getBean("tawCommonsAccessoriesDao");
		final List<TawCommonsAccessories> list = tawCommonsAccessoriesDao.getAllFileByName(accessorieS);
		final int size = list.size();
		
		this.getJdbcTemplate().execute(sql, new PreparedStatementCallback(){
			public Object doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				
				for(int i =0 ; i<size ; i++){
					ps.setString(1, process);			     
					ps.setString(2, list.get(i).getId());
					ps.setString(3, list.get(i).getAccessoriesName());
					ps.addBatch();
				}
				return ps.executeBatch();
			}
		});
	}

	@Override
	public String getAttachmentNamesByProcessInstanceId(
			String processInstanceId) {
		// TODO Auto-generated method stub
		   String sql="select att.accessoriesname NAME from pnr_act_task_attachment att where att.process_instance_id='"+processInstanceId+"'" ;
	        List<Map> list =  this.getJdbcTemplate().queryForList(sql);
	        int size = list.size();
	        String attachmentNames ="";
	        if(size>0){
	        	for(int i=0 ; i< size;i++){	
	        		if("".equals(attachmentNames)){		        			
	        			attachmentNames+="'"+list.get(i).get("NAME")+"'";
	        		}else{		        			
	        			attachmentNames+=",'"+list.get(i).get("NAME")+"'";
	        		}
	        	}
	        	
	        }
		return attachmentNames;
	}
}
