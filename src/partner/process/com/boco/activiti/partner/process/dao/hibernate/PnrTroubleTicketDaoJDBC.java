package com.boco.activiti.partner.process.dao.hibernate;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.task.Task;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.boco.activiti.partner.process.dao.IPnrTroubleTicketJDBCDao;
import com.boco.activiti.partner.process.model.PnrSmsSendEntity;
import com.boco.activiti.partner.process.po.TaskModel;
import com.boco.activiti.partner.process.po.WorkOrderStatisticsModel;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.commons.accessories.dao.TawCommonsAccessoriesDao;
import com.boco.eoms.commons.accessories.model.TawCommonsAccessories;
import com.boco.eoms.partner.process.util.CommonUtils;

/**
 
 */
public class PnrTroubleTicketDaoJDBC extends JdbcDaoSupport implements IPnrTroubleTicketJDBCDao {
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
        String whereSql = "where create_time > '" + beginTime+
                "' and create_time< '" +endTime+"'";
        if(subType!=null&&!subType.equals("")){
            whereSql=whereSql+" and sub_type=  '"+subType+"'";
        }
        String sumNumSql = "select city,count(id)as sumNum from pnr_act_trouble_ticket_main";
        String unfiledNumSql = "select city,sum(decode(archiving_time,'',1,null,0,0))as unfiledNumber from pnr_act_trouble_ticket_main";
        String overtimeNumSql = "select city,sum(decode(sign(nvl(last_reply_time,sysdate)-end_time),1,1,-1,0,0))as overtimeNum from pnr_act_trouble_ticket_main";
        String endSelectSql = "group by p.city";
        String sql="select area.areaid,area.areaname,a.sumNum,b.unfiledNum,c.overtimeNum  from " +
              " (select areaid,areaname from taw_system_area where parentareaid=28) area," +
              " ("+sumNumSql+  whereSql +endSelectSql+") s," +
              " ("+unfiledNumSql+  whereSql +endSelectSql+") s," +
              " ("+overtimeNumSql+  whereSql +""+endSelectSql+") s," +
              " where area.areaid=a.city and  area.areaid=b.city and area.areaid=c.city";

        return null;
    }
    @Override
    public List<TaskModel> workOrderQuery(String deptId,String workerid,String beginTime, String endTime, String subType,String theme,String city,int beginNum,int endNum) {
       
    	HistoryService historyService = (HistoryService)ApplicationContextHolder.getInstance().getBean("historyService");

    	List<TaskModel> r=new ArrayList<TaskModel>();
        String whereSql = " where m.state=5  ";
        if(beginTime!=null&&!beginTime.equals("")){
            whereSql=whereSql+" and m.create_time > to_date('" + beginTime+
                    "','yyyy-MM-dd HH24:mi:ss')";
        }
        if(endTime!=null&&!endTime.equals("")){
            whereSql=whereSql+" and m.create_time < to_date('" + endTime+
                    "','yyyy-MM-dd HH24:mi:ss')";
        }
        if(subType!=null&&!subType.equals("")){
            whereSql=whereSql+" and m.sub_type=  '"+subType+"'";
        }
        if(theme!=null&&!theme.equals("")){
            whereSql=whereSql+" and m.theme like  '%"+theme+"%'";
        }
        if(city!=null&&!city.equals("")){
            whereSql=whereSql+" and m.city =  '"+city+"'";
        }
        if(deptId!=null&&!deptId.equals("")){
        	
        	/*if(deptId.equals("-1")){
        		
        		whereSql=whereSql+" and u.deptid like '%'";
        	}else{*/
        		whereSql=whereSql+" and u.deptid like '"+deptId+"%'";
        		
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
            	
            	whereSql = whereSql + " and m.process_instance_id in (" + process_instance_id + ")";
            }
        }
        String sql="select process_instance_id,theme,initiator,deptid,send_time from (select m.process_instance_id,m.theme,m.initiator,u2.deptid,m.send_time, rownum rwn    from pnr_act_trouble_ticket_main m left join taw_system_user u on m.task_assignee = u.userid left join taw_system_user u2 on m.initiator = u2.userid " +whereSql+" and rownum <="+endNum+" order by m.create_time desc) where rwn>"+beginNum;
        System.out.println("pnrTroubleTicketDao-146---"+sql);

        List<Map> list = this.getJdbcTemplate().queryForList(sql);

        for(Map map:list){
            TaskModel taskModel=new TaskModel();
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
            	taskModel.setSendTime(dateFormat.parse(map.get("SEND_TIME").toString()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            List<HistoricTaskInstance> historicTasks = historyService.createHistoricTaskInstanceQuery().processInstanceId(taskModel.getProcessInstanceId()).orderByTaskId().desc().listPage(0, 2);

            List<Task> taskList = taskService.createTaskQuery().processInstanceId(taskModel.getProcessInstanceId()).active().list();
            
            if(taskList!=null&&taskList.size()==1){
                Task task=taskList.get(0);
                taskModel.setStatusName(task.getName());
                taskModel.setTaskId(task.getId());
                taskModel.setExecutor(task.getAssignee());
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
    public int workOrderCount(String deptId,String workerid,String beginTime, String endTime, String subType,String theme,String city) {
        String whereSql = " where m.state = 5 ";
        if(beginTime!=null&&!beginTime.equals("")){
            whereSql=whereSql+" and m.create_time > to_date('" + beginTime+
                    "','yyyy-MM-dd HH24:mi:ss')";
        }
        if(endTime!=null&&!endTime.equals("")){
            whereSql=whereSql+" and m.create_time < to_date('" + endTime+
                    "','yyyy-MM-dd HH24:mi:ss')";
        }
        if(subType!=null&&!subType.equals("")){
            whereSql=whereSql+" and m.sub_type=  '"+subType+"'";
        }
        if(theme!=null&&!theme.equals("")){
            whereSql=whereSql+" and m.theme like  '%"+theme+"%'";
        }
        if(city!=null&&!city.equals("")){
            whereSql=whereSql+" and m.city =  '"+city+"'";
        }
        if(deptId!=null&&!deptId.equals("")){
        	
        	/*if(deptId.equals("-1")){
        		
        		whereSql=whereSql+" and u.deptid like '%'";
        	}else{*/
        		whereSql=whereSql+" and u.deptid like '"+deptId+"%'";
        		
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
        	   
        	   whereSql = whereSql + " and m.process_instance_id in (" + process_instance_id + ")";
           }
        }
        
        String sql="select count(*) as total from pnr_act_trouble_ticket_main m left join taw_system_user u on m.task_assignee = u.userid " +whereSql;
        System.out.println("pnrTroubleTicketDao-259---"+sql);
        List<Map> list =  this.getJdbcTemplate().queryForList(sql);
        return Integer.parseInt(list.get(0).get("total").toString());
    }

	@Override
	public void saveOrUpatePerson(String processInstanceId,
			String[] personStrings) {
		// TODO Auto-generated method stub
		final String[] personS =personStrings;
		final String process = processInstanceId;
//		先删除已处理的前一批
		String delSql = "delete from pnr_act_trouble_person_relate where process_instance_id =?";
		this.getJdbcTemplate().execute(delSql, new PreparedStatementCallback(){
			  public Object doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
			   	 	ps.setString(1, process);			     
			    	ps.addBatch();
			      return ps.executeBatch();
			     }
		});
//		保存数据
	
		String sql = "insert into pnr_act_trouble_person_relate values(?,?)";
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
	public void saveOrUpateSpecialty(String processInstanceId,
			String[] specialtyStrings) {
		// TODO Auto-generated method stub
		final String[] personS =specialtyStrings;
		final String process = processInstanceId;
//		先删除已处理的前一批
		String delSql = "delete from pnr_act_trouble_spec_relate where process_instance_id =?";
		this.getJdbcTemplate().execute(delSql, new PreparedStatementCallback(){
			  public Object doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
			   	 	ps.setString(1, process);			     
			    	ps.addBatch();
			      return ps.executeBatch();
			     }
		});
//		保存数据
	
		String sql = "insert into pnr_act_trouble_spec_relate values(?,?)";
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
	public synchronized void saveSendContext(PnrSmsSendEntity p) {
		// TODO Auto-generated method stub
		final PnrSmsSendEntity personS =p;
		final String[] mobiles  = p.getMobile()==null?null:p.getMobile().split(";");
		
		int getSize = 0;
		if(mobiles !=null){			
			getSize =mobiles.length;
		}
		final int size = getSize;
		
//		保存数据
	
		String sql = "insert into SENDSMS(Mobile,Content,Flag,Inserttime) values(?,?,?,sysdate)";
		this.getJdbcTemplate().execute(sql, new PreparedStatementCallback(){
			  public Object doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
			       for(int i=0;i<size ;i++){			    	   
			    	   ps.setString(1, mobiles[i]);			     
			    	   ps.setString(2, personS.getContext());
			    	   ps.setInt(3, 0);
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
//			先删除已存在的前一批
			String delSql = "delete from pnr_act_trouble_attachment where process_instance_id =?";
			this.getJdbcTemplate().execute(delSql, new PreparedStatementCallback(){
				  public Object doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
				   	 	ps.setString(1, process);			     
				    	ps.addBatch();
				      return ps.executeBatch();
				     }
			});
//		保存数据
			String sql = "insert into pnr_act_trouble_attachment(process_instance_id,accessories_id,accessoriesname) values(?,?,?)";
//		according to accessoriesNames to find the primary key of table(taw_commons_accessories)
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
			   String sql="select att.accessoriesname NAME from pnr_act_trouble_attachment att where att.process_instance_id='"+processInstanceId+"'" ;
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

		@Override
		public Map<String, String> getCityOrCoruntryIdByGkCountryName(
				String gkCountryName) {
			
			 Map<String, String> map = new HashMap<String, String>();

			   String sql="select g.gk_city_name,g.gk_country_name,g.conuntry_name,g.city_id,g.country_id from  pnr_gk_city_match g where g.gk_country_name='"+gkCountryName+"' order by rowid asc" ;
		        
			   List<Map> list =  this.getJdbcTemplate().queryForList(sql);
		       int size = list.size();		      
		       
		       if(size>0)
		       {
		        	for(int i=0 ; i< size;i++){	
		        		map.putAll(list.get(i));
		        	}
		        	
		       }
			return map;
			
		}
}
