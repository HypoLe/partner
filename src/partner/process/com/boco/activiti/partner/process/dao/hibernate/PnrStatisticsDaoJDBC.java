package com.boco.activiti.partner.process.dao.hibernate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.activiti.engine.TaskService;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.boco.activiti.partner.process.dao.IPnrStatisticsJDBCDao;
import com.boco.activiti.partner.process.po.PreflightDetailStatisticPartnerModel;
import com.boco.activiti.partner.process.po.PreflightStatisticPartnerModel;
import com.boco.activiti.partner.process.po.WorkOrderStatisticsDrillModel;
import com.boco.activiti.partner.process.po.WorkOrderStatisticsModel;
import com.boco.activiti.partner.process.po.WorkOrderStatisticsModel2;
import com.boco.eoms.partner.inspect.model.InspectStatisticArea;
import com.boco.eoms.partner.inspect.model.InspectStatisticPartner;

/**

 */
public class PnrStatisticsDaoJDBC extends JdbcDaoSupport implements IPnrStatisticsJDBCDao {
    private TaskService taskService;

    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public List<WorkOrderStatisticsModel> taskTicketWorkOrderStatistics(String beginTime, String endTime, String subType) {
        List<WorkOrderStatisticsModel> r = new ArrayList<WorkOrderStatisticsModel>();
        String whereSql = " where to_char(create_time,'yyyy-mm-dd') >= '"+ beginTime +
                "' and to_char(create_time,'yyyy-mm-dd')<= '" + endTime+"'";
        if (subType != null && !subType.equals("")) {
            whereSql = whereSql + " and sub_type in   (" + subType + ")";
        }
        String sumNumSql = "select city,count(id)as sumNum from pnr_act_task_ticket_main";
        String unfiledNumSql = "select city,sum(decode(archiving_time,'',1,null,1,0))as unfiledNum from pnr_act_task_ticket_main";
        String overtimeNumSql = "select city,sum(decode(sign(nvl(last_reply_time,sysdate)-end_time),1,1,-1,0,0))as overtimeNum from pnr_act_task_ticket_main";
        String endSelectSql = "group by city";
        String sql = "select area.areaid,area.areaname,a.sumNum,b.unfiledNum,c.overtimeNum  from " +
                " (select areaid,areaname from taw_system_area where parentareaid=28) area left join" +
                " (" + sumNumSql + whereSql + endSelectSql + ") a on area.areaid=a.city left join" +
                " (" + unfiledNumSql + whereSql + endSelectSql + ") b on area.areaid=b.city left join" +
                " (" + overtimeNumSql + whereSql + "" + endSelectSql + ") c on area.areaid=c.city   order by nlssort(area.areaname,'NLS_SORT=SCHINESE_PINYIN_M')";
        System.out.println(sql);
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

            if (workOrderStatisticsModel.getSumNum() == 0) {
                workOrderStatisticsModel.setArchiveNumber(0);
            } else {
                if (workOrderStatisticsModel.getUnfiledNumber() == 0) {
                    workOrderStatisticsModel.setArchiveNumber(workOrderStatisticsModel.getSumNum());
                } else {
                    workOrderStatisticsModel.setArchiveNumber(workOrderStatisticsModel.getSumNum() - workOrderStatisticsModel.getUnfiledNumber());
                }
            }
            if (workOrderStatisticsModel.getSumNum() != 0 && workOrderStatisticsModel.getOvertimeNum() != 0) {
                workOrderStatisticsModel.setOvertimeRate(this.calculateThePercentage(new Double(workOrderStatisticsModel.getOvertimeNum()), new Double(workOrderStatisticsModel.getSumNum())));
            } else {
                workOrderStatisticsModel.setOvertimeRate("0%");
            }

            r.add(workOrderStatisticsModel);
        }
        return r;
    }

    public List<WorkOrderStatisticsModel> troubleTicketWorkOrderStatistics(String beginTime, String endTime, String subType) {
        List<WorkOrderStatisticsModel> r = new ArrayList<WorkOrderStatisticsModel>();
        String whereSql = " where to_char(create_time,'yyyy-mm-dd') >= '"+ beginTime +
        "' and to_char(create_time,'yyyy-mm-dd')<= '" + endTime+"'";
        if (subType != null && !subType.equals("")) {
            whereSql = whereSql + " and sub_type in   (" + subType + ")";
        }
        String sumNumSql = "select city,count(id)as sumNum from pnr_act_trouble_ticket_main";
        String unfiledNumSql = "select city,sum(decode(archiving_time,'',1,null,1,0))as unfiledNum from pnr_act_trouble_ticket_main";
        String overtimeNumSql = "select city,sum(decode(sign(nvl(last_reply_time,sysdate)-end_time),1,1,-1,0,0))as overtimeNum from pnr_act_trouble_ticket_main";
        String endSelectSql = "group by city";
        String sql = "select area.areaid,area.areaname,a.sumNum,b.unfiledNum,c.overtimeNum  from " +
                " (select areaid,areaname from taw_system_area where parentareaid=28) area left join" +
                " (" + sumNumSql + whereSql + endSelectSql + ") a on area.areaid=a.city left join" +
                " (" + unfiledNumSql + whereSql + endSelectSql + ") b on area.areaid=b.city left join" +
                " (" + overtimeNumSql + whereSql + "" + endSelectSql + ") c on area.areaid=c.city   order by nlssort(area.areaname,'NLS_SORT=SCHINESE_PINYIN_M')";
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

            if (workOrderStatisticsModel.getSumNum() == 0) {
                workOrderStatisticsModel.setArchiveNumber(0);
            } else {
                if (workOrderStatisticsModel.getUnfiledNumber() == 0) {
                    workOrderStatisticsModel.setArchiveNumber(workOrderStatisticsModel.getSumNum());
                } else {
                    workOrderStatisticsModel.setArchiveNumber(workOrderStatisticsModel.getSumNum() - workOrderStatisticsModel.getUnfiledNumber());
                }
            }
            if (workOrderStatisticsModel.getSumNum() != 0 && workOrderStatisticsModel.getOvertimeNum() != 0) {
                workOrderStatisticsModel.setOvertimeRate(this.calculateThePercentage(new Double(workOrderStatisticsModel.getOvertimeNum()), new Double(workOrderStatisticsModel.getSumNum())));
            } else {
                workOrderStatisticsModel.setOvertimeRate("0%");
            }

            r.add(workOrderStatisticsModel);
        }
        return r;
    }
    public List<WorkOrderStatisticsModel> transferOfficeTicketWorkOrderStatistics(String beginTime, String endTime, String subType) {
    	List<WorkOrderStatisticsModel> r = new ArrayList<WorkOrderStatisticsModel>();
    	String whereSql = " where to_char(create_time,'yyyy-mm-dd') >= '"+ beginTime +
    	"' and to_char(create_time,'yyyy-mm-dd')<= '" + endTime+"' and m1.themeinterface='transferOffice' and m1.state!=1";
    	if (subType != null && !subType.equals("")) {
    		whereSql = whereSql + " and m1.sub_type in   (" + subType + ")";
    	}
    	String sumNumSql = "select a1.parentareaid, count(m1.id) as sumNum from pnr_act_transfer_office_main  m1  left join taw_system_user u on u.userid = m1.task_assignee left join taw_system_dept d1  on u.deptid = d1.deptid left join taw_system_area a1 on d1.areaid = a1.areaid";
    	String unfiledNumSql = "select a1.parentareaid,sum(decode(archiving_time, '', 1, null, 1, 0)) as unfiledNum from  pnr_act_transfer_office_main  m1  left join taw_system_user u on u.userid = m1.task_assignee left join taw_system_dept d1  on u.deptid = d1.deptid left join taw_system_area a1 on d1.areaid = a1.areaid";
    	String overtimeNumSql = "select a1.parentareaid,sum(decode(sign(nvl(last_reply_time, sysdate) - end_time),1,1,-1,0,0)) as overtimeNum  from pnr_act_transfer_office_main  m1  left join taw_system_user u on u.userid = m1.task_assignee left join taw_system_dept d1  on u.deptid = d1.deptid left join taw_system_area a1 on d1.areaid = a1.areaid";
    	String endSelectSql1 = " group by a1.parentareaid";
    	//String deptIdSql = "select uu.deptid from taw_system_user uu where uu.userid='"+userId+"'";
    	String sql = "select area.areaid,area.areaname,a.sumNum,b.unfiledNum,c.overtimeNum  from " +
    	" (select areaid,areaname from taw_system_area where parentareaid=28) area left join" +
    	" (" + sumNumSql + whereSql + endSelectSql1 + ") a on area.areaid=a.parentareaid left join" +
    	" (" + unfiledNumSql + whereSql + endSelectSql1 + ") b on area.areaid=b.parentareaid left join" +
    	" (" + overtimeNumSql + whereSql + "" + endSelectSql1 + ") c on area.areaid=c.parentareaid   order by nlssort(area.areaname,'NLS_SORT=SCHINESE_PINYIN_M')";
    	System.out.println(sql);
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
    		
    		if (workOrderStatisticsModel.getSumNum() == 0) {
    			workOrderStatisticsModel.setArchiveNumber(0);
    		} else {
    			if (workOrderStatisticsModel.getUnfiledNumber() == 0) {
    				workOrderStatisticsModel.setArchiveNumber(workOrderStatisticsModel.getSumNum());
    			} else {
    				workOrderStatisticsModel.setArchiveNumber(workOrderStatisticsModel.getSumNum() - workOrderStatisticsModel.getUnfiledNumber());
    			}
    		}
    		if (workOrderStatisticsModel.getSumNum() != 0 && workOrderStatisticsModel.getOvertimeNum() != 0) {
    			workOrderStatisticsModel.setOvertimeRate(this.calculateThePercentage(new Double(workOrderStatisticsModel.getOvertimeNum()), new Double(workOrderStatisticsModel.getSumNum())));
    		} else {
    			workOrderStatisticsModel.setOvertimeRate("0%");
    		}
    		
    		r.add(workOrderStatisticsModel);
    	}
    	return r;
    }
    public List<WorkOrderStatisticsModel> transferInterfaceWorkOrderStatistics(String transferType,String beginTime, String endTime, String subType){
    	
    	List<WorkOrderStatisticsModel> r = new ArrayList<WorkOrderStatisticsModel>();
    	String whereSql="";
    	if("transferOffice".equals(transferType)){
    		whereSql = " where to_char(create_time,'yyyy-mm-dd') >= '"+ beginTime +
    		"' and to_char(create_time,'yyyy-mm-dd')<= '" + endTime+"' and m1.themeinterface='transferOffice' and m1.state!=1";
    	}else if("interface".equals(transferType)){
    		whereSql = " where to_char(create_time,'yyyy-mm-dd') >= '"+ beginTime +
    		"' and to_char(create_time,'yyyy-mm-dd')<= '" + endTime+"' and m1.themeinterface='interface' and m1.state!=1";
    	}else if("all".equals(transferType)){
    		whereSql = " where to_char(create_time,'yyyy-mm-dd') >= '"+ beginTime +
    		"' and to_char(create_time,'yyyy-mm-dd')<= '" + endTime+"' and m1.themeinterface in ('transferOffice','interface') and m1.state!=1";
    	}
    	if (subType != null && !subType.equals("")) {
    		whereSql = whereSql + " and m1.sub_type in   ('" + subType + "')";
    	}
    	String sumNumSql = "select a1.parentareaid, count(m1.id) as sumNum from pnr_act_transfer_office_main  m1  left join taw_system_user u on u.userid = m1.task_assignee left join taw_system_dept d1  on u.deptid = d1.deptid left join taw_system_area a1 on d1.areaid = a1.areaid";
    	String unfiledNumSql = "select a1.parentareaid,sum(decode(archiving_time, '', 1, null, 1, 0)) as unfiledNum from  pnr_act_transfer_office_main  m1  left join taw_system_user u on u.userid = m1.task_assignee left join taw_system_dept d1  on u.deptid = d1.deptid left join taw_system_area a1 on d1.areaid = a1.areaid";
    	String overtimeNumSql = "select a1.parentareaid,sum(decode(sign(nvl(last_reply_time, sysdate) - end_time),1,1,-1,0,0)) as overtimeNum  from pnr_act_transfer_office_main  m1  left join taw_system_user u on u.userid = m1.task_assignee left join taw_system_dept d1  on u.deptid = d1.deptid left join taw_system_area a1 on d1.areaid = a1.areaid";
    	String endSelectSql1 = " group by a1.parentareaid";
    	//String deptIdSql = "select uu.deptid from taw_system_user uu where uu.userid='"+userId+"'";
    	String sql = "select area.areaid,area.areaname,a.sumNum,b.unfiledNum,c.overtimeNum  from " +
    	" (select areaid,areaname from taw_system_area where parentareaid=28) area left join" +
    	" (" + sumNumSql + whereSql + endSelectSql1 + ") a on area.areaid=a.parentareaid left join" +
    	" (" + unfiledNumSql + whereSql + endSelectSql1 + ") b on area.areaid=b.parentareaid left join" +
    	" (" + overtimeNumSql + whereSql + "" + endSelectSql1 + ") c on area.areaid=c.parentareaid   order by nlssort(area.areaname,'NLS_SORT=SCHINESE_PINYIN_M')";
    	System.out.println(sql);
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
    		
    		if (workOrderStatisticsModel.getSumNum() == 0) {
    			workOrderStatisticsModel.setArchiveNumber(0);
    		} else {
    			if (workOrderStatisticsModel.getUnfiledNumber() == 0) {
    				workOrderStatisticsModel.setArchiveNumber(workOrderStatisticsModel.getSumNum());
    			} else {
    				workOrderStatisticsModel.setArchiveNumber(workOrderStatisticsModel.getSumNum() - workOrderStatisticsModel.getUnfiledNumber());
    			}
    		}
    		if (workOrderStatisticsModel.getSumNum() != 0 && workOrderStatisticsModel.getOvertimeNum() != 0) {
    			workOrderStatisticsModel.setOvertimeRate(this.calculateThePercentage(new Double(workOrderStatisticsModel.getOvertimeNum()), new Double(workOrderStatisticsModel.getSumNum())));
    		} else {
    			workOrderStatisticsModel.setOvertimeRate("0%");
    		}
    		
    		r.add(workOrderStatisticsModel);
    	}
    	return r;
    }
    
    @Override
    public List<WorkOrderStatisticsModel> taskTicketWorkOrderStatisticsbycity(String city,String beginTime, String endTime, String subType) {
        List<WorkOrderStatisticsModel> r = new ArrayList<WorkOrderStatisticsModel>();
        String whereSql = " where to_char(create_time,'yyyy-mm-dd') >= '"+ beginTime +
                "' and to_char(create_time,'yyyy-mm-dd')<= '" + endTime+"'";
        if (subType != null && !subType.equals("")) {
            whereSql = whereSql + " and sub_type in   (" + subType + ")";
        }
        String sumNumSql = "select country,count(id)as sumNum from pnr_act_task_ticket_main";
        String unfiledNumSql = "select country,sum(decode(archiving_time,'',1,null,1,0))as unfiledNum from pnr_act_task_ticket_main";
        String overtimeNumSql = "select country,sum(decode(sign(nvl(last_reply_time,sysdate)-end_time),1,1,-1,0,0))as overtimeNum from pnr_act_task_ticket_main";
        String endSelectSql = "group by country";
        String sql = "select area.areaid,area.areaname,a.sumNum,b.unfiledNum,c.overtimeNum  from " +
                " (select areaid,areaname from taw_system_area where parentareaid=" + city + ") area left join" +
                " (" + sumNumSql + whereSql + endSelectSql + ") a on area.areaid=a.country left join" +
                " (" + unfiledNumSql + whereSql + endSelectSql + ") b on area.areaid=b.country left join" +
                " (" + overtimeNumSql + whereSql + "" + endSelectSql + ") c on area.areaid=c.country   order by nlssort(area.areaname,'NLS_SORT=SCHINESE_PINYIN_M')";
        System.out.println(sql);
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

            if (workOrderStatisticsModel.getSumNum() == 0) {
                workOrderStatisticsModel.setArchiveNumber(0);
            } else {
                if (workOrderStatisticsModel.getUnfiledNumber() == 0) {
                    workOrderStatisticsModel.setArchiveNumber(workOrderStatisticsModel.getSumNum());
                } else {
                    workOrderStatisticsModel.setArchiveNumber(workOrderStatisticsModel.getSumNum() - workOrderStatisticsModel.getUnfiledNumber());
                }
            }
            if (workOrderStatisticsModel.getSumNum() != 0 && workOrderStatisticsModel.getOvertimeNum() != 0) {
                workOrderStatisticsModel.setOvertimeRate(this.calculateThePercentage(new Double(workOrderStatisticsModel.getOvertimeNum()), new Double(workOrderStatisticsModel.getSumNum())));
            } else {
                workOrderStatisticsModel.setOvertimeRate("0%");
            }

            r.add(workOrderStatisticsModel);
        }
        return r;
    }
    @Override
    public List<WorkOrderStatisticsModel> transferOfficeWorkOrderStatisticsbycity(String city,String beginTime, String endTime, String subType) {
    	List<WorkOrderStatisticsModel> r = new ArrayList<WorkOrderStatisticsModel>();
    	String whereSql = " where to_char(create_time,'yyyy-mm-dd') >= '"+ beginTime +
    	"' and to_char(create_time,'yyyy-mm-dd')<= '" + endTime+"' and m1.themeinterface='transferOffice' and m1.state!=1";
    	if (subType != null && !subType.equals("")) {
    		whereSql = whereSql + " and m1.sub_type in   (" + subType + ")";
    	}
    	String sumNumSql = "select a1.areaid, count(m1.id) as sumNum from pnr_act_transfer_office_main  m1  left join taw_system_user u on u.userid = m1.task_assignee left join taw_system_dept d1  on u.deptid = d1.deptid left join taw_system_area a1 on d1.areaid = a1.areaid";
    	String unfiledNumSql = "select a1.areaid,sum(decode(archiving_time, '', 1, null, 1, 0)) as unfiledNum from  pnr_act_transfer_office_main  m1  left join taw_system_user u on u.userid = m1.task_assignee left join taw_system_dept d1  on u.deptid = d1.deptid left join taw_system_area a1 on d1.areaid = a1.areaid";
    	String overtimeNumSql = "select a1.areaid,sum(decode(sign(nvl(last_reply_time, sysdate) - end_time),1,1,-1,0,0)) as overtimeNum  from pnr_act_transfer_office_main  m1  left join taw_system_user u on u.userid = m1.task_assignee left join taw_system_dept d1  on u.deptid = d1.deptid left join taw_system_area a1 on d1.areaid = a1.areaid";
    	String endSelectSql = "group by a1.areaid";
    	String sql = "select area.areaid,area.areaname,a.sumNum,b.unfiledNum,c.overtimeNum  from " +
    	" (select areaid,areaname from taw_system_area where parentareaid=" + city + ") area left join" +
    	" (" + sumNumSql + whereSql + endSelectSql + ") a on area.areaid=a.areaid left join" +
    	" (" + unfiledNumSql + whereSql + endSelectSql + ") b on area.areaid=b.areaid left join" +
    	" (" + overtimeNumSql + whereSql + "" + endSelectSql + ") c on area.areaid=c.areaid   order by nlssort(area.areaname,'NLS_SORT=SCHINESE_PINYIN_M')";
    	System.out.println(sql);
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
    		
    		if (workOrderStatisticsModel.getSumNum() == 0) {
    			workOrderStatisticsModel.setArchiveNumber(0);
    		} else {
    			if (workOrderStatisticsModel.getUnfiledNumber() == 0) {
    				workOrderStatisticsModel.setArchiveNumber(workOrderStatisticsModel.getSumNum());
    			} else {
    				workOrderStatisticsModel.setArchiveNumber(workOrderStatisticsModel.getSumNum() - workOrderStatisticsModel.getUnfiledNumber());
    			}
    		}
    		if (workOrderStatisticsModel.getSumNum() != 0 && workOrderStatisticsModel.getOvertimeNum() != 0) {
    			workOrderStatisticsModel.setOvertimeRate(this.calculateThePercentage(new Double(workOrderStatisticsModel.getOvertimeNum()), new Double(workOrderStatisticsModel.getSumNum())));
    		} else {
    			workOrderStatisticsModel.setOvertimeRate("0%");
    		}
    		
    		r.add(workOrderStatisticsModel);
    	}
    	return r;
    }
    public List<WorkOrderStatisticsModel> transferOfficeInterfaceStatisticsbycity(String type,String city,String beginTime, String endTime, String subType){
    	List<WorkOrderStatisticsModel> r = new ArrayList<WorkOrderStatisticsModel>();
    	String whereSql = "";
    	if("transferOffice".equals(type)){
    		whereSql = " where to_char(create_time,'yyyy-mm-dd') >= '"+ beginTime +
        	"' and to_char(create_time,'yyyy-mm-dd')<= '" + endTime+"' and m1.themeinterface='transferOffice' and m1.state!=1";
    	}else if("interface".equals(type)){
    		whereSql = " where to_char(create_time,'yyyy-mm-dd') >= '"+ beginTime +
        	"' and to_char(create_time,'yyyy-mm-dd')<= '" + endTime+"' and m1.themeinterface='interface' and m1.state!=1";
    	}else if("all".equals(type)){
    		whereSql = " where to_char(create_time,'yyyy-mm-dd') >= '"+ beginTime +
        	"' and to_char(create_time,'yyyy-mm-dd')<= '" + endTime+"' and m1.themeinterface in('interface','transferOffice') and m1.state!=1";
    	}
    	if (subType != null && !subType.equals("")) {
    		whereSql = whereSql + " and m1.sub_type in   ('" + subType + "')";
    	}
    	String sumNumSql = "select a1.areaid, count(m1.id) as sumNum from pnr_act_transfer_office_main  m1  left join taw_system_user u on u.userid = m1.task_assignee left join taw_system_dept d1  on u.deptid = d1.deptid left join taw_system_area a1 on d1.areaid = a1.areaid";
    	String unfiledNumSql = "select a1.areaid,sum(decode(archiving_time, '', 1, null, 1, 0)) as unfiledNum from  pnr_act_transfer_office_main  m1  left join taw_system_user u on u.userid = m1.task_assignee left join taw_system_dept d1  on u.deptid = d1.deptid left join taw_system_area a1 on d1.areaid = a1.areaid";
    	String overtimeNumSql = "select a1.areaid,sum(decode(sign(nvl(last_reply_time, sysdate) - end_time),1,1,-1,0,0)) as overtimeNum  from pnr_act_transfer_office_main  m1  left join taw_system_user u on u.userid = m1.task_assignee left join taw_system_dept d1  on u.deptid = d1.deptid left join taw_system_area a1 on d1.areaid = a1.areaid";
    	String endSelectSql = "group by a1.areaid";
    	String sql = "select area.areaid,area.areaname,a.sumNum,b.unfiledNum,c.overtimeNum  from " +
    	" (select areaid,areaname from taw_system_area where parentareaid=" + city + ") area left join" +
    	" (" + sumNumSql + whereSql + endSelectSql + ") a on area.areaid=a.areaid left join" +
    	" (" + unfiledNumSql + whereSql + endSelectSql + ") b on area.areaid=b.areaid left join" +
    	" (" + overtimeNumSql + whereSql + "" + endSelectSql + ") c on area.areaid=c.areaid   order by nlssort(area.areaname,'NLS_SORT=SCHINESE_PINYIN_M')";
    	System.out.println(sql);
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
    		
    		if (workOrderStatisticsModel.getSumNum() == 0) {
    			workOrderStatisticsModel.setArchiveNumber(0);
    		} else {
    			if (workOrderStatisticsModel.getUnfiledNumber() == 0) {
    				workOrderStatisticsModel.setArchiveNumber(workOrderStatisticsModel.getSumNum());
    			} else {
    				workOrderStatisticsModel.setArchiveNumber(workOrderStatisticsModel.getSumNum() - workOrderStatisticsModel.getUnfiledNumber());
    			}
    		}
    		if (workOrderStatisticsModel.getSumNum() != 0 && workOrderStatisticsModel.getOvertimeNum() != 0) {
    			workOrderStatisticsModel.setOvertimeRate(this.calculateThePercentage(new Double(workOrderStatisticsModel.getOvertimeNum()), new Double(workOrderStatisticsModel.getSumNum())));
    		} else {
    			workOrderStatisticsModel.setOvertimeRate("0%");
    		}
    		
    		r.add(workOrderStatisticsModel);
    	}
    	return r;

    	
    }
    
    public List<WorkOrderStatisticsModel> troubleTicketWorkOrderStatisticsbycity(String city,String beginTime, String endTime, String subType) {
        List<WorkOrderStatisticsModel> r = new ArrayList<WorkOrderStatisticsModel>();
        String whereSql = " where to_char(create_time,'yyyy-mm-dd') >= '"+ beginTime +
        "' and to_char(create_time,'yyyy-mm-dd')<= '" + endTime+"'";
        if (subType != null && !subType.equals("")) {
            whereSql = whereSql + " and sub_type in   (" + subType + ")";
        }
        String sumNumSql = "select country,count(id)as sumNum from pnr_act_trouble_ticket_main";
        String unfiledNumSql = "select country,sum(decode(archiving_time,'',1,null,1,0))as unfiledNum from pnr_act_trouble_ticket_main";
        String overtimeNumSql = "select country,sum(decode(sign(nvl(last_reply_time,sysdate)-end_time),1,1,-1,0,0))as overtimeNum from pnr_act_trouble_ticket_main";
        String endSelectSql = "group by country";
        String sql = "select area.areaid,area.areaname,a.sumNum,b.unfiledNum,c.overtimeNum  from " +
                " (select areaid,areaname from taw_system_area where parentareaid=" + city + ") area left join" +
                " (" + sumNumSql + whereSql + endSelectSql + ") a on area.areaid=a.country left join" +
                " (" + unfiledNumSql + whereSql + endSelectSql + ") b on area.areaid=b.country left join" +
                " (" + overtimeNumSql + whereSql + "" + endSelectSql + ") c on area.areaid=c.country   order by nlssort(area.areaname,'NLS_SORT=SCHINESE_PINYIN_M')";
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

            if (workOrderStatisticsModel.getSumNum() == 0) {
                workOrderStatisticsModel.setArchiveNumber(0);
            } else {
                if (workOrderStatisticsModel.getUnfiledNumber() == 0) {
                    workOrderStatisticsModel.setArchiveNumber(workOrderStatisticsModel.getSumNum());
                } else {
                    workOrderStatisticsModel.setArchiveNumber(workOrderStatisticsModel.getSumNum() - workOrderStatisticsModel.getUnfiledNumber());
                }
            }
            if (workOrderStatisticsModel.getSumNum() != 0 && workOrderStatisticsModel.getOvertimeNum() != 0) {
                workOrderStatisticsModel.setOvertimeRate(this.calculateThePercentage(new Double(workOrderStatisticsModel.getOvertimeNum()), new Double(workOrderStatisticsModel.getSumNum())));
            } else {
                workOrderStatisticsModel.setOvertimeRate("0%");
            }

            r.add(workOrderStatisticsModel);
        }
        return r;
    }
    @Override
    public List<WorkOrderStatisticsModel> taskTicketWorkOrderStatisticsbycountry(String city,String beginTime, String endTime, String subType) {
        List<WorkOrderStatisticsModel> r = new ArrayList<WorkOrderStatisticsModel>();
        String whereSql = " where to_char(create_time,'yyyy-mm-dd') >= '"+ beginTime +
                "' and to_char(create_time,'yyyy-mm-dd')<= '" + endTime+"'" +
                " and m.process_instance_id = p.process_instance_id and m.country = " + city+" ";
        if (subType != null && !subType.equals("")) {
            whereSql = whereSql + " and sub_type in   (" + subType + ")";
        }
        String sumNumSql = "select p.person,count(id)as sumNum from pnr_act_task_ticket_main m,pnr_act_task_person_relate p";
        String unfiledNumSql = "select p.person,sum(decode(archiving_time,'',1,null,1,0))as unfiledNum from pnr_act_task_ticket_main m,pnr_act_task_person_relate p";
        String overtimeNumSql = "select p.person,sum(decode(sign(nvl(last_reply_time,sysdate)-end_time),1,1,-1,0,0))as overtimeNum from pnr_act_task_ticket_main m,pnr_act_task_person_relate p";
        String endSelectSql = "group by p.person";
        String sql = "select distinct area.person, a.sumNum, b.unfiledNum, c.overtimeNum  from " +
                " (select process_instance_id,person from pnr_act_task_person_relate) area left join" +
                " (" + sumNumSql + whereSql + endSelectSql + ") a on area.person = a.person left join" +
                " (" + unfiledNumSql + whereSql + endSelectSql + ") b on area.person = b.person left join" +
                " (" + overtimeNumSql + whereSql + "" + endSelectSql + ") c on area.person = c.person " +
                " where a.sumNum is not null or b.unfiledNum is not null or c.overtimeNum is not null";
        System.out.println(sql);
        List<Map> list = this.getJdbcTemplate().queryForList(sql);
        
        for (Map map : list) {
            WorkOrderStatisticsModel workOrderStatisticsModel = new WorkOrderStatisticsModel();
            workOrderStatisticsModel.setCity(city);
            workOrderStatisticsModel.setCityName(map.get("person").toString());
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

            if (workOrderStatisticsModel.getSumNum() == 0) {
                workOrderStatisticsModel.setArchiveNumber(0);
            } else {
                if (workOrderStatisticsModel.getUnfiledNumber() == 0) {
                    workOrderStatisticsModel.setArchiveNumber(workOrderStatisticsModel.getSumNum());
                } else {
                    workOrderStatisticsModel.setArchiveNumber(workOrderStatisticsModel.getSumNum() - workOrderStatisticsModel.getUnfiledNumber());
                }
            }
            if (workOrderStatisticsModel.getSumNum() != 0 && workOrderStatisticsModel.getOvertimeNum() != 0) {
                workOrderStatisticsModel.setOvertimeRate(this.calculateThePercentage(new Double(workOrderStatisticsModel.getOvertimeNum()), new Double(workOrderStatisticsModel.getSumNum())));
            } else {
                workOrderStatisticsModel.setOvertimeRate("0%");
            }

            r.add(workOrderStatisticsModel);
        }
        return r;
    }

    @Override
    public List<WorkOrderStatisticsModel> troubleTicketWorkOrderStatisticsbycountry(String city,String beginTime, String endTime, String subType) {
        List<WorkOrderStatisticsModel> r = new ArrayList<WorkOrderStatisticsModel>();
        String whereSql = " where to_char(create_time,'yyyy-mm-dd') >= '"+ beginTime +
                "' and to_char(create_time,'yyyy-mm-dd')<= '" + endTime+"'" +
                " and m.process_instance_id = p.process_instance_id and m.country = " + city+" ";
        if (subType != null && !subType.equals("")) {
            whereSql = whereSql + " and sub_type in   (" + subType + ")";
        }
        String sumNumSql = "select p.person,count(id)as sumNum from pnr_act_trouble_ticket_main m,pnr_act_trouble_person_relate p";
        String unfiledNumSql = "select p.person,sum(decode(archiving_time,'',1,null,1,0))as unfiledNum from pnr_act_trouble_ticket_main m,pnr_act_trouble_person_relate p";
        String overtimeNumSql = "select p.person,sum(decode(sign(nvl(last_reply_time,sysdate)-end_time),1,1,-1,0,0))as overtimeNum from pnr_act_trouble_ticket_main m,pnr_act_trouble_person_relate p";
        String endSelectSql = "group by p.person";
        String sql = "select distinct area.person person, a.sumNum, b.unfiledNum, c.overtimeNum  from " +
                " (select process_instance_id,person from pnr_act_trouble_person_relate) area left join" +
                " (" + sumNumSql + whereSql + endSelectSql + ") a on area.person = a.person left join" +
                " (" + unfiledNumSql + whereSql + endSelectSql + ") b on area.person = b.person left join" +
                " (" + overtimeNumSql + whereSql + "" + endSelectSql + ") c on area.person = c.person" +
                " where a.sumNum is not null or b.unfiledNum is not null or c.overtimeNum is not null";
        System.out.println(sql);
        List<Map> list = this.getJdbcTemplate().queryForList(sql);
        
        for (Map map : list) {
            WorkOrderStatisticsModel workOrderStatisticsModel = new WorkOrderStatisticsModel();
            //这个地方用了city的model
            workOrderStatisticsModel.setCity(city);
            workOrderStatisticsModel.setCityName(map.get("person").toString());
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

            if (workOrderStatisticsModel.getSumNum() == 0) {
                workOrderStatisticsModel.setArchiveNumber(0);
            } else {
                if (workOrderStatisticsModel.getUnfiledNumber() == 0) {
                    workOrderStatisticsModel.setArchiveNumber(workOrderStatisticsModel.getSumNum());
                } else {
                    workOrderStatisticsModel.setArchiveNumber(workOrderStatisticsModel.getSumNum() - workOrderStatisticsModel.getUnfiledNumber());
                }
            }
            if (workOrderStatisticsModel.getSumNum() != 0 && workOrderStatisticsModel.getOvertimeNum() != 0) {
                workOrderStatisticsModel.setOvertimeRate(this.calculateThePercentage(new Double(workOrderStatisticsModel.getOvertimeNum()), new Double(workOrderStatisticsModel.getSumNum())));
            } else {
                workOrderStatisticsModel.setOvertimeRate("0%");
            }

            r.add(workOrderStatisticsModel);
        }

        return r;
    }
    @Override
    public List<WorkOrderStatisticsModel> transferOfficeWorkOrderStatisticsbycountry(String city,String beginTime, String endTime, String subType) {
    	List<WorkOrderStatisticsModel> r = new ArrayList<WorkOrderStatisticsModel>();
    	String whereSql = " where to_char(create_time,'yyyy-mm-dd') >= '"+ beginTime +
    	"' and to_char(create_time,'yyyy-mm-dd')<= '" + endTime+"'" +
    	"  and d.areaid = " + city+" and m.themeinterface='transferOffice' and m.state!=1";
    	if (subType != null && !subType.equals("")) {
    		whereSql = whereSql + " and m.sub_type in   (" + subType + ")";
    	}
    	String sumNumSql = "select m.task_assignee, count(m.id) as sumNum from pnr_act_transfer_office_main   m   left join taw_system_user u on m.task_assignee = u.userid left join taw_system_dept d on u.deptid = d.deptid ";
    	String unfiledNumSql = "select m.task_assignee, sum(decode(archiving_time, '', 1, null, 1, 0)) as unfiledNum  from pnr_act_transfer_office_main   m  left join taw_system_user u on m.task_assignee = u.userid left join taw_system_dept d on u.deptid = d.deptid  ";
    	String overtimeNumSql = "select m.task_assignee, sum(decode(sign(nvl(last_reply_time, sysdate) - end_time), 1,  1,  -1, 0, 0)) as overtimeNum from pnr_act_transfer_office_main   m left join taw_system_user u on m.task_assignee = u.userid left join taw_system_dept d on u.deptid = d.deptid    ";
    	String endSelectSql = "group by m.task_assignee";
    	String sql = "select distinct area.task_assignee person, a.sumNum , b.unfiledNum, c.overtimeNum  from " +
    	" (select m1.task_assignee,u1.username from pnr_act_transfer_office_main m1,taw_system_user u1 where m1.task_assignee = u1.userid) area left join" +
    	" (" + sumNumSql + whereSql + endSelectSql + ") a on area.task_assignee = a.task_assignee left join" +
    	" (" + unfiledNumSql + whereSql + endSelectSql + ") b on area.task_assignee = b.task_assignee left join" +
    	" (" + overtimeNumSql + whereSql + "" + endSelectSql + ") c on area.task_assignee = c.task_assignee" +
    	" where a.sumNum is not null or b.unfiledNum is not null or c.overtimeNum is not null";
    	System.out.println(sql);
    	List<Map> list = this.getJdbcTemplate().queryForList(sql);
    	
    	for (Map map : list) {
    		WorkOrderStatisticsModel workOrderStatisticsModel = new WorkOrderStatisticsModel();
    		//这个地方用了city的model
    		workOrderStatisticsModel.setCity(city);
    		workOrderStatisticsModel.setCityName(map.get("person").toString());
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
    		
    		if (workOrderStatisticsModel.getSumNum() == 0) {
    			workOrderStatisticsModel.setArchiveNumber(0);
    		} else {
    			if (workOrderStatisticsModel.getUnfiledNumber() == 0) {
    				workOrderStatisticsModel.setArchiveNumber(workOrderStatisticsModel.getSumNum());
    			} else {
    				workOrderStatisticsModel.setArchiveNumber(workOrderStatisticsModel.getSumNum() - workOrderStatisticsModel.getUnfiledNumber());
    			}
    		}
    		if (workOrderStatisticsModel.getSumNum() != 0 && workOrderStatisticsModel.getOvertimeNum() != 0) {
    			workOrderStatisticsModel.setOvertimeRate(this.calculateThePercentage(new Double(workOrderStatisticsModel.getOvertimeNum()), new Double(workOrderStatisticsModel.getSumNum())));
    		} else {
    			workOrderStatisticsModel.setOvertimeRate("0%");
    		}
    		
    		r.add(workOrderStatisticsModel);
    	}
    	
    	return r;
    }
    public List<WorkOrderStatisticsModel> transferOfficeInterfaceStatisticsbycountry(String type,String city,String beginTime, String endTime, String subType){
    	List<WorkOrderStatisticsModel> r = new ArrayList<WorkOrderStatisticsModel>();
    	String whereSql="";
    	if("transferOffice".equals(type)){
    		
    		whereSql = " where to_char(create_time,'yyyy-mm-dd') >= '"+ beginTime +
    		"' and to_char(create_time,'yyyy-mm-dd')<= '" + endTime+"'" +
    		"  and d.areaid = " + city+" and m.themeinterface='transferOffice' and m.state!=1";
    	}else if("interface".equals(type)){
    		whereSql = " where to_char(create_time,'yyyy-mm-dd') >= '"+ beginTime +
    		"' and to_char(create_time,'yyyy-mm-dd')<= '" + endTime+"'" +
    		"  and d.areaid = " + city+" and m.themeinterface='interface' and m.state!=1";
    	}else if("all".equals(type)){
    		whereSql = " where to_char(create_time,'yyyy-mm-dd') >= '"+ beginTime +
    		"' and to_char(create_time,'yyyy-mm-dd')<= '" + endTime+"'" +
    		"  and d.areaid = " + city+" and m.themeinterface in ('interface','transferOffice') and m.state!=1";
    	}
    	if (subType != null && !subType.equals("")) {
    		whereSql = whereSql + " and m.sub_type in   ('" + subType + "')";
    	}
    	String sumNumSql = "select m.task_assignee, count(m.id) as sumNum from pnr_act_transfer_office_main   m   left join taw_system_user u on m.task_assignee = u.userid left join taw_system_dept d on u.deptid = d.deptid ";
    	String unfiledNumSql = "select m.task_assignee, sum(decode(archiving_time, '', 1, null, 1, 0)) as unfiledNum  from pnr_act_transfer_office_main   m  left join taw_system_user u on m.task_assignee = u.userid left join taw_system_dept d on u.deptid = d.deptid  ";
    	String overtimeNumSql = "select m.task_assignee, sum(decode(sign(nvl(last_reply_time, sysdate) - end_time), 1,  1,  -1, 0, 0)) as overtimeNum from pnr_act_transfer_office_main   m left join taw_system_user u on m.task_assignee = u.userid left join taw_system_dept d on u.deptid = d.deptid    ";
    	String endSelectSql = "group by m.task_assignee";
    	String sql = "select distinct area.task_assignee person, a.sumNum , b.unfiledNum, c.overtimeNum  from " +
    	" (select m1.task_assignee,u1.username from pnr_act_transfer_office_main m1,taw_system_user u1 where m1.task_assignee = u1.userid) area left join" +
    	" (" + sumNumSql + whereSql + endSelectSql + ") a on area.task_assignee = a.task_assignee left join" +
    	" (" + unfiledNumSql + whereSql + endSelectSql + ") b on area.task_assignee = b.task_assignee left join" +
    	" (" + overtimeNumSql + whereSql + "" + endSelectSql + ") c on area.task_assignee = c.task_assignee" +
    	" where a.sumNum is not null or b.unfiledNum is not null or c.overtimeNum is not null";
    	System.out.println(sql);
    	List<Map> list = this.getJdbcTemplate().queryForList(sql);
    	
    	for (Map map : list) {
    		WorkOrderStatisticsModel workOrderStatisticsModel = new WorkOrderStatisticsModel();
    		//这个地方用了city的model
    		workOrderStatisticsModel.setCity(city);
    		workOrderStatisticsModel.setCityName(map.get("person").toString());
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
    		
    		if (workOrderStatisticsModel.getSumNum() == 0) {
    			workOrderStatisticsModel.setArchiveNumber(0);
    		} else {
    			if (workOrderStatisticsModel.getUnfiledNumber() == 0) {
    				workOrderStatisticsModel.setArchiveNumber(workOrderStatisticsModel.getSumNum());
    			} else {
    				workOrderStatisticsModel.setArchiveNumber(workOrderStatisticsModel.getSumNum() - workOrderStatisticsModel.getUnfiledNumber());
    			}
    		}
    		if (workOrderStatisticsModel.getSumNum() != 0 && workOrderStatisticsModel.getOvertimeNum() != 0) {
    			workOrderStatisticsModel.setOvertimeRate(this.calculateThePercentage(new Double(workOrderStatisticsModel.getOvertimeNum()), new Double(workOrderStatisticsModel.getSumNum())));
    		} else {
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
        if (a == null || a.equals(0) || a.equals(0.0) || a.equals(0.00)) {
            return nf1.format(0);
        }
        if (b == null || b.equals(0) || b.equals(0.0) || a.equals(0.00)) {
            return nf1.format(0);
        }
        Double d = a / b;
        if (d == null || d.equals(100) || d.equals(100.0) || a.equals(100.00)) {
            return nf1.format(100);
        }
        String r = nf.format(d);
        return r;
    }

    @Override
    public List<WorkOrderStatisticsModel2> workOrderStatistics2(String beginTime, String endTime) {
        List<WorkOrderStatisticsModel2> r = new ArrayList<WorkOrderStatisticsModel2>();
        String whereSql = " where create_time >= to_date('" + beginTime +
                "','yyyy-MM-dd HH24:mi:ss') and create_time<=  to_date('" + endTime + "','yyyy-MM-dd HH24:mi:ss') and state=5 ";
        String faultSumNumSql = "select city,count(id)as faultSumNum from pnr_act_trouble_ticket_main" + whereSql + " and sub_Type='101220101'";
        String faultOvertimeNumSql = "select city,sum(decode(sign(nvl(last_reply_time,sysdate)-end_time),1,1,-1,0,0))as faultOvertimeNum from pnr_act_trouble_ticket_main" + whereSql + " and sub_Type='101220101'";
        String generateElectricitySumNumSql = "select city,count(id)as generateElectricitySumNum from pnr_act_trouble_ticket_main" + whereSql + " and sub_Type='101220102'";
        String generateElectricityOvertimeNumSql = "select city,sum(decode(sign(nvl(last_reply_time,sysdate)-end_time),1,1,-1,0,0))as generateElectricityOvertimeNum from pnr_act_trouble_ticket_main" + whereSql + " and sub_Type='101220102'";
        String businessOpenedSumNumSql = "select city,count(id)as businessOpenedSumNum from pnr_act_task_ticket_main" + whereSql + " and sub_Type='101110101'";
        String businessOpenedOvertimeNumSql = "select city,sum(decode(sign(nvl(last_reply_time,sysdate)-end_time),1,1,-1,0,0))as businessOpenedOvertimeNum from pnr_act_task_ticket_main" + whereSql + " and sub_Type='101110101'";
        String networkCutoverSumNumSql = "select city,count(id)as networkCutoverSumNum from pnr_act_task_ticket_main" + whereSql + " and sub_Type='101110102'";
        String networkCutoverOvertimeNumSql = "select city,sum(decode(sign(nvl(last_reply_time,sysdate)-end_time),1,1,-1,0,0))as networkCutoverOvertimeNum from pnr_act_task_ticket_main" + whereSql + " and sub_Type='101110102'";
        String operationalCoordinationSumNumSql = "select city,count(id)as operationalCSumNum from pnr_act_task_ticket_main" + whereSql + " and sub_Type='101110103'";
        String operationalCoordinationOvertimeNumSql = "select city,sum(decode(sign(nvl(last_reply_time,sysdate)-end_time),1,1,-1,0,0))as operationalCOvertimeNum from pnr_act_task_ticket_main" + whereSql + " and sub_Type='101110103'";
        String inspectionSumNumSql = "select city,count(id)as inspectionSumNum from pnr_act_task_ticket_main" + whereSql + " and sub_Type='101110105'";
        String inspectionOvertimeNumSql = "select city,sum(decode(sign(nvl(last_reply_time,sysdate)-end_time),1,1,-1,0,0))as inspectionOvertimeNum from pnr_act_task_ticket_main" + whereSql + " and sub_Type='101110105'";
        String projectAcceptanceSumNumSql = "select city,count(id)as projectAcceptanceSumNum from pnr_act_task_ticket_main" + whereSql + " and sub_Type='101110106'";
        String projectAcceptanceOvertimeNumSql = "select city,sum(decode(sign(nvl(last_reply_time,sysdate)-end_time),1,1,-1,0,0))as projectAcceptanceOvertimeNum from pnr_act_task_ticket_main" + whereSql + " and sub_Type='101110106'";
        String paymentSumNumSql = "select city,count(id)as paymentSumNum from pnr_act_task_ticket_main" + whereSql + " and sub_Type='101110104'";
        String paymentOvertimeNumSql = "select city,sum(decode(sign(nvl(last_reply_time,sysdate)-end_time),1,1,-1,0,0))as paymentOvertimeNum from pnr_act_task_ticket_main" + whereSql + " and sub_Type='101110104'";
        String otherSumNumSql = "select city,count(id)as otherSumNum from pnr_act_task_ticket_main" + whereSql + " and sub_Type='101110107'";
        String otherOvertimeNumSql = "select city,sum(decode(sign(nvl(last_reply_time,sysdate)-end_time),1,1,-1,0,0))as otherOvertimeNum from pnr_act_task_ticket_main" + whereSql + " and sub_Type='101110107'";


        String endSelectSql = "group by city";
        String sql = "select area.areaid,area.areaname," +
                "a.faultSumNum,b.faultOvertimeNum," +
                "c.generateElectricitySumNum,d.generateElectricityOvertimeNum, " +
                "e.businessOpenedSumNum,f.businessOpenedOvertimeNum," +
                "g.networkCutoverSumNum,h.networkCutoverOvertimeNum," +
                "i.operationalCSumNum,j.operationalCOvertimeNum," +
                "k.inspectionSumNum,l.inspectionOvertimeNum," +
                "m.projectAcceptanceSumNum,projectAcceptanceOvertimeNum, " +
                "o.otherSumNum,otherOvertimeNum, " +
                "q.paymentSumNum,paymentOvertimeNum " +
                " from " +
                " (select areaid,areaname from taw_system_area where parentareaid=28) area left join" +
                " (" + faultSumNumSql +  endSelectSql + ") a on area.areaid=a.city left join" +
                " (" + faultOvertimeNumSql + endSelectSql + ") b on area.areaid=b.city left join" +
                " (" + generateElectricitySumNumSql +  endSelectSql + ") c on area.areaid=c.city left join" +
                " (" + generateElectricityOvertimeNumSql + endSelectSql + ") d on area.areaid=d.city left join" +
                " (" + businessOpenedSumNumSql +  endSelectSql + ") e on area.areaid=e.city left join" +
                " (" + businessOpenedOvertimeNumSql + endSelectSql + ") f on area.areaid=f.city left join" +
                " (" + networkCutoverSumNumSql +  endSelectSql + ") g on area.areaid=g.city left join" +
                " (" + networkCutoverOvertimeNumSql + endSelectSql + ") h on area.areaid=h.city left join" +
                " (" + operationalCoordinationSumNumSql +  endSelectSql + ") i on area.areaid=i.city left join" +
                " (" + operationalCoordinationOvertimeNumSql + endSelectSql + ") j on area.areaid=j.city left join" +
                " (" + inspectionSumNumSql +  endSelectSql + ") k on area.areaid=k.city left join" +
                " (" + inspectionOvertimeNumSql + endSelectSql + ") l on area.areaid=l.city left join" +
                " (" + projectAcceptanceSumNumSql +  endSelectSql + ") m on area.areaid=m.city left join" +
                " (" + projectAcceptanceOvertimeNumSql + endSelectSql + ") n on area.areaid=n.city left join"+
                " (" + paymentSumNumSql +  endSelectSql + ") q on area.areaid=q.city left join" +
                " (" + paymentOvertimeNumSql + endSelectSql + ") r on area.areaid=r.city left join" +
                " (" + otherSumNumSql +  endSelectSql + ") o on area.areaid=o.city left join" +
                " (" + otherOvertimeNumSql + endSelectSql + ") p on area.areaid=p.city";
        System.out.println("555555555555555555555-------------------"+sql);
        List<Map> list = this.getJdbcTemplate().queryForList(sql);
        for (Map map : list) {
            WorkOrderStatisticsModel2 workOrderStatisticsModel2 = new WorkOrderStatisticsModel2();
            workOrderStatisticsModel2.setCity(map.get("areaid").toString());
            workOrderStatisticsModel2.setCityName(map.get("areaname").toString());
            if (map.get("faultSumNum") == null) {
                workOrderStatisticsModel2.setFaultSumNum(0);
            } else {
                workOrderStatisticsModel2.setFaultSumNum(Integer.parseInt(map
                        .get("faultSumNum").toString()));
            }
            if (map.get("faultOvertimeNum") == null) {
                workOrderStatisticsModel2.setFaultOvertimeNum(0);
            } else {
                workOrderStatisticsModel2.setFaultOvertimeNum(Integer.parseInt(map
                        .get("faultOvertimeNum").toString()));
            }
            if (workOrderStatisticsModel2.getFaultSumNum() != 0 && workOrderStatisticsModel2.getFaultOvertimeNum() != 0) {
                workOrderStatisticsModel2.setFaultOvertimeRate(this.calculateThePercentage(new Double(workOrderStatisticsModel2.getFaultOvertimeNum()), new Double(workOrderStatisticsModel2.getFaultSumNum())));
            } else {
                workOrderStatisticsModel2.setFaultOvertimeRate("0%");
            }
            if (map.get("generateElectricitySumNum") == null) {
                workOrderStatisticsModel2.setGenerateElectricitySumNum(0);
            } else {
                workOrderStatisticsModel2.setGenerateElectricitySumNum(Integer.parseInt(map
                        .get("generateElectricitySumNum").toString()));
            }
            if (map.get("generateElectricityOvertimeNum") == null) {
                workOrderStatisticsModel2.setGenerateElectricityOvertimeNum(0);
            } else {
                workOrderStatisticsModel2.setGenerateElectricityOvertimeNum(Integer.parseInt(map
                        .get("generateElectricityOvertimeNum").toString()));
            }
            if (workOrderStatisticsModel2.getGenerateElectricitySumNum() != 0 && workOrderStatisticsModel2.getGenerateElectricityOvertimeNum() != 0) {
                workOrderStatisticsModel2.setGenerateElectricityOvertimeRate(this.calculateThePercentage(new Double(workOrderStatisticsModel2.getGenerateElectricityOvertimeNum()), new Double(workOrderStatisticsModel2.getGenerateElectricitySumNum())));
            } else {
                workOrderStatisticsModel2.setGenerateElectricityOvertimeRate("0%");
            }
            if (map.get("businessOpenedSumNum") == null) {
                workOrderStatisticsModel2.setBusinessOpenedSumNum(0);
            } else {
                workOrderStatisticsModel2.setBusinessOpenedSumNum(Integer.parseInt(map
                        .get("businessOpenedSumNum").toString()));
            }
            if (map.get("businessOpenedOvertimeNum") == null) {
                workOrderStatisticsModel2.setBusinessOpenedOvertimeNum(0);
            } else {
                workOrderStatisticsModel2.setBusinessOpenedOvertimeNum(Integer.parseInt(map
                        .get("businessOpenedOvertimeNum").toString()));
            }
            if (workOrderStatisticsModel2.getBusinessOpenedSumNum() != 0 && workOrderStatisticsModel2.getBusinessOpenedOvertimeNum() != 0) {
                workOrderStatisticsModel2.setBusinessOpenedOvertimeRate(this.calculateThePercentage(new Double(workOrderStatisticsModel2.getBusinessOpenedOvertimeNum()), new Double(workOrderStatisticsModel2.getBusinessOpenedSumNum())));
            } else {
                workOrderStatisticsModel2.setBusinessOpenedOvertimeRate("0%");
            }
            if (map.get("networkCutoverSumNum") == null) {
                workOrderStatisticsModel2.setNetworkCutoverSumNum(0);
            } else {
                workOrderStatisticsModel2.setNetworkCutoverSumNum(Integer.parseInt(map
                        .get("networkCutoverSumNum").toString()));
            }
            if (map.get("networkCutoverOvertimeNum") == null) {
                workOrderStatisticsModel2.setNetworkCutoverOvertimeNum(0);
            } else {
                workOrderStatisticsModel2.setNetworkCutoverOvertimeNum(Integer.parseInt(map
                        .get("networkCutoverOvertimeNum").toString()));
            }
            if (workOrderStatisticsModel2.getNetworkCutoverSumNum() != 0 && workOrderStatisticsModel2.getNetworkCutoverOvertimeNum() != 0) {
                workOrderStatisticsModel2.setNetworkCutoverOvertimeRate(this.calculateThePercentage(new Double(workOrderStatisticsModel2.getNetworkCutoverOvertimeNum()), new Double(workOrderStatisticsModel2.getNetworkCutoverSumNum())));
            } else {
                workOrderStatisticsModel2.setNetworkCutoverOvertimeRate("0%");
            }
            if (map.get("operationalCSumNum") == null) {
                workOrderStatisticsModel2.setOperationalCoordinationSumNum(0);
            } else {
                workOrderStatisticsModel2.setOperationalCoordinationSumNum(Integer.parseInt(map
                        .get("operationalCSumNum").toString()));
            }
            if (map.get("operationalCOvertimeNum") == null) {
                workOrderStatisticsModel2.setOperationalCoordinationOvertimeNum(0);
            } else {
                workOrderStatisticsModel2.setOperationalCoordinationOvertimeNum(Integer.parseInt(map
                        .get("operationalCOvertimeNum").toString()));
            }
            if (workOrderStatisticsModel2.getOperationalCoordinationSumNum() != 0 && workOrderStatisticsModel2.getOperationalCoordinationOvertimeNum() != 0) {
                workOrderStatisticsModel2.setOperationalCoordinationOvertimeRate(this.calculateThePercentage(new Double(workOrderStatisticsModel2.getOperationalCoordinationOvertimeNum()), new Double(workOrderStatisticsModel2.getOperationalCoordinationSumNum())));
            } else {
                workOrderStatisticsModel2.setOperationalCoordinationOvertimeRate("0%");
            }
            if (map.get("inspectionSumNum") == null) {
                workOrderStatisticsModel2.setInspectionSumNum(0);
            } else {
                workOrderStatisticsModel2.setInspectionSumNum(Integer.parseInt(map
                        .get("inspectionSumNum").toString()));
            }
            if (map.get("inspectionOvertimeNum") == null) {
                workOrderStatisticsModel2.setInspectionOvertimeNum(0);
            } else {
                workOrderStatisticsModel2.setInspectionOvertimeNum(Integer.parseInt(map
                        .get("inspectionOvertimeNum").toString()));
            }
            if (workOrderStatisticsModel2.getInspectionSumNum() != 0 && workOrderStatisticsModel2.getInspectionOvertimeNum() != 0) {
                workOrderStatisticsModel2.setInspectionOvertimeRate(this.calculateThePercentage(new Double(workOrderStatisticsModel2.getInspectionOvertimeNum()), new Double(workOrderStatisticsModel2.getInspectionSumNum())));
            } else {
                workOrderStatisticsModel2.setInspectionOvertimeRate("0%");
            }
            if (map.get("projectAcceptanceSumNum") == null) {
                workOrderStatisticsModel2.setProjectAcceptanceSumNum(0);
            } else {
                workOrderStatisticsModel2.setProjectAcceptanceSumNum(Integer.parseInt(map
                        .get("projectAcceptanceSumNum").toString()));
            }
            if (map.get("projectAcceptanceOvertimeNum") == null) {
                workOrderStatisticsModel2.setProjectAcceptanceOvertimeNum(0);
            } else {
                workOrderStatisticsModel2.setProjectAcceptanceOvertimeNum(Integer.parseInt(map
                        .get("projectAcceptanceOvertimeNum").toString()));
            }
            if (workOrderStatisticsModel2.getProjectAcceptanceSumNum() != 0 && workOrderStatisticsModel2.getProjectAcceptanceOvertimeNum() != 0) {
                workOrderStatisticsModel2.setProjectAcceptanceOvertimeRate(this.calculateThePercentage(new Double(workOrderStatisticsModel2.getProjectAcceptanceOvertimeNum()), new Double(workOrderStatisticsModel2.getProjectAcceptanceSumNum())));
            } else {
                workOrderStatisticsModel2.setProjectAcceptanceOvertimeRate("0%");
            }
            if (map.get("otherSumNum") == null) {
                workOrderStatisticsModel2.setOtherSumNum(0);
            } else {
                workOrderStatisticsModel2.setOtherSumNum(Integer.parseInt(map
                        .get("otherSumNum").toString()));
            }
            if (map.get("otherOvertimeNum") == null) {
                workOrderStatisticsModel2.setOtherOvertimeNum(0);
            } else {
                workOrderStatisticsModel2.setOtherOvertimeNum(Integer.parseInt(map
                        .get("otherOvertimeNum").toString()));
            }
            if (workOrderStatisticsModel2.getOtherSumNum() != 0 && workOrderStatisticsModel2.getOtherOvertimeNum() != 0) {
                workOrderStatisticsModel2.setOtherOvertimeRate(this.calculateThePercentage(new Double(workOrderStatisticsModel2.getOtherOvertimeNum()), new Double(workOrderStatisticsModel2.getOtherSumNum())));
            } else {
                workOrderStatisticsModel2.setOtherOvertimeRate("0%");
            }
            if (map.get("paymentSumNum") == null) {
                workOrderStatisticsModel2.setPaymentSumNum(0);
            } else {
                workOrderStatisticsModel2.setPaymentSumNum(Integer.parseInt(map
                        .get("paymentSumNum").toString()));
            }
            if (map.get("paymentOvertimeNum") == null) {
                workOrderStatisticsModel2.setPaymentOvertimeNum(0);
            } else {
                workOrderStatisticsModel2.setPaymentOvertimeNum(Integer.parseInt(map
                        .get("paymentOvertimeNum").toString()));
            }
            if (workOrderStatisticsModel2.getPaymentSumNum() != 0 && workOrderStatisticsModel2.getPaymentOvertimeNum() != 0) {
                workOrderStatisticsModel2.setPaymentOvertimeRate(this.calculateThePercentage(new Double(workOrderStatisticsModel2.getPaymentOvertimeNum()), new Double(workOrderStatisticsModel2.getPaymentSumNum())));
            } else {
                workOrderStatisticsModel2.setPaymentOvertimeRate("0%");
            }
            r.add(workOrderStatisticsModel2);
        }
        return r;
    }
    @Override
    public List<WorkOrderStatisticsModel2> workOrderStatistics3(String beginTime, String endTime) {
        List<WorkOrderStatisticsModel2> r = new ArrayList<WorkOrderStatisticsModel2>();
        String whereSql = ",ACT_RU_TASK where PROC_INST_ID_=PROCESS_INSTANCE_ID and  create_time >= to_date('" + beginTime +
                "','yyyy-MM-dd HH24:mi:ss') and create_time<=  to_date('" + endTime + "','yyyy-MM-dd HH24:mi:ss') and TASK_DEF_KEY_!='extractWorkOrder'";
        String faultSumNumSql = "select city,count(id)as faultSumNum from pnr_act_trouble_ticket_main" + whereSql + " and sub_Type='101220101'";
        String faultOvertimeNumSql = "select city,sum(decode(sign(nvl(last_reply_time,sysdate)-end_time),1,1,-1,0,0))as faultOvertimeNum from pnr_act_trouble_ticket_main" + whereSql + " and sub_Type='101220101'";
        String generateElectricitySumNumSql = "select city,count(id)as generateElectricitySumNum from pnr_act_trouble_ticket_main" + whereSql + " and sub_Type='101220102'";
        String generateElectricityOvertimeNumSql = "select city,sum(decode(sign(nvl(last_reply_time,sysdate)-end_time),1,1,-1,0,0))as generateElectricityOvertimeNum from pnr_act_trouble_ticket_main" + whereSql + " and sub_Type='101220102'";
        String businessOpenedSumNumSql = "select city,count(id)as businessOpenedSumNum from pnr_act_task_ticket_main" + whereSql + " and sub_Type='101110101'";
        String businessOpenedOvertimeNumSql = "select city,sum(decode(sign(nvl(last_reply_time,sysdate)-end_time),1,1,-1,0,0))as businessOpenedOvertimeNum from pnr_act_task_ticket_main" + whereSql + " and sub_Type='101110101'";
        String networkCutoverSumNumSql = "select city,count(id)as networkCutoverSumNum from pnr_act_task_ticket_main" + whereSql + " and sub_Type='101110102'";
        String networkCutoverOvertimeNumSql = "select city,sum(decode(sign(nvl(last_reply_time,sysdate)-end_time),1,1,-1,0,0))as networkCutoverOvertimeNum from pnr_act_task_ticket_main" + whereSql + " and sub_Type='101110102'";
        String operationalCoordinationSumNumSql = "select city,count(id)as operationalCSumNum from pnr_act_task_ticket_main" + whereSql + " and sub_Type='101110103'";
        String operationalCoordinationOvertimeNumSql = "select city,sum(decode(sign(nvl(last_reply_time,sysdate)-end_time),1,1,-1,0,0))as operationalCOvertimeNum from pnr_act_task_ticket_main" + whereSql + " and sub_Type='101110103'";
        String paymentSumNumSql = "select city,count(id)as paymentSumNum from pnr_act_task_ticket_main" + whereSql + " and sub_Type='101110104'";
        String paymentOvertimeNumSql = "select city,sum(decode(sign(nvl(last_reply_time,sysdate)-end_time),1,1,-1,0,0))as paymentOvertimeNum from pnr_act_task_ticket_main" + whereSql + " and sub_Type='101110104'";
        String inspectionSumNumSql = "select city,count(id)as inspectionSumNum from pnr_act_task_ticket_main" + whereSql + " and sub_Type='101110105'";
        String inspectionOvertimeNumSql = "select city,sum(decode(sign(nvl(last_reply_time,sysdate)-end_time),1,1,-1,0,0))as inspectionOvertimeNum from pnr_act_task_ticket_main" + whereSql + " and sub_Type='101110105'";
        String projectAcceptanceSumNumSql = "select city,count(id)as projectAcceptanceSumNum from pnr_act_task_ticket_main" + whereSql + " and sub_Type='101110106'";
        String projectAcceptanceOvertimeNumSql = "select city,sum(decode(sign(nvl(last_reply_time,sysdate)-end_time),1,1,-1,0,0))as projectAcceptanceOvertimeNum from pnr_act_task_ticket_main" + whereSql + " and sub_Type='101110106'";
        String otherSumNumSql = "select city,count(id)as otherSumNum from pnr_act_task_ticket_main" + whereSql + " and sub_Type='101110107'";
        String otherOvertimeNumSql = "select city,sum(decode(sign(nvl(last_reply_time,sysdate)-end_time),1,1,-1,0,0))as otherOvertimeNum from pnr_act_task_ticket_main" + whereSql + " and sub_Type='101110107'";
        String endSelectSql = "group by city";
        String sql = "select area.areaid,area.areaname," +
                "a.faultSumNum,b.faultOvertimeNum," +
                "c.generateElectricitySumNum,d.generateElectricityOvertimeNum, " +
                "e.businessOpenedSumNum,f.businessOpenedOvertimeNum," +
                "g.networkCutoverSumNum,h.networkCutoverOvertimeNum," +
                "i.operationalCSumNum,j.operationalCOvertimeNum," +
                "k.inspectionSumNum,l.inspectionOvertimeNum," +
                "m.projectAcceptanceSumNum,projectAcceptanceOvertimeNum, " +
                "o.otherSumNum,otherOvertimeNum, " +
                "q.paymentSumNum,paymentOvertimeNum " +
                " from " +
                " (select areaid,areaname from taw_system_area where parentareaid=28) area left join" +
                " (" + faultSumNumSql +  endSelectSql + ") a on area.areaid=a.city left join" +
                " (" + faultOvertimeNumSql + endSelectSql + ") b on area.areaid=b.city left join" +
                " (" + generateElectricitySumNumSql +  endSelectSql + ") c on area.areaid=c.city left join" +
                " (" + generateElectricityOvertimeNumSql + endSelectSql + ") d on area.areaid=d.city left join" +
                " (" + businessOpenedSumNumSql +  endSelectSql + ") e on area.areaid=e.city left join" +
                " (" + businessOpenedOvertimeNumSql + endSelectSql + ") f on area.areaid=f.city left join" +
                " (" + networkCutoverSumNumSql +  endSelectSql + ") g on area.areaid=g.city left join" +
                " (" + networkCutoverOvertimeNumSql + endSelectSql + ") h on area.areaid=h.city left join" +
                " (" + operationalCoordinationSumNumSql +  endSelectSql + ") i on area.areaid=i.city left join" +
                " (" + operationalCoordinationOvertimeNumSql + endSelectSql + ") j on area.areaid=j.city left join" +
                " (" + inspectionSumNumSql +  endSelectSql + ") k on area.areaid=k.city left join" +
                " (" + inspectionOvertimeNumSql + endSelectSql + ") l on area.areaid=l.city left join" +
                " (" + projectAcceptanceSumNumSql +  endSelectSql + ") m on area.areaid=m.city left join" +
                " (" + projectAcceptanceOvertimeNumSql + endSelectSql + ") n on area.areaid=n.city left join"+
                " (" + paymentSumNumSql +  endSelectSql + ") q on area.areaid=q.city left join" +
                " (" + paymentOvertimeNumSql + endSelectSql + ") r on area.areaid=r.city left join" +
                " (" + otherSumNumSql +  endSelectSql + ") o on area.areaid=o.city left join" +
                " (" + otherOvertimeNumSql + endSelectSql + ") p on area.areaid=p.city";
        System.out.println("子啊图-------------------"+sql);
        List<Map> list = this.getJdbcTemplate().queryForList(sql);
        for (Map map : list) {
            WorkOrderStatisticsModel2 workOrderStatisticsModel2 = new WorkOrderStatisticsModel2();
            workOrderStatisticsModel2.setCity(map.get("areaid").toString());
            workOrderStatisticsModel2.setCityName(map.get("areaname").toString());
            if (map.get("faultSumNum") == null) {
                workOrderStatisticsModel2.setFaultSumNum(0);
            } else {
                workOrderStatisticsModel2.setFaultSumNum(Integer.parseInt(map
                        .get("faultSumNum").toString()));
            }
            if (map.get("faultOvertimeNum") == null) {
                workOrderStatisticsModel2.setFaultOvertimeNum(0);
            } else {
                workOrderStatisticsModel2.setFaultOvertimeNum(Integer.parseInt(map
                        .get("faultOvertimeNum").toString()));
            }
            if (workOrderStatisticsModel2.getFaultSumNum() != 0 && workOrderStatisticsModel2.getFaultOvertimeNum() != 0) {
                workOrderStatisticsModel2.setFaultOvertimeRate(this.calculateThePercentage(new Double(workOrderStatisticsModel2.getFaultOvertimeNum()), new Double(workOrderStatisticsModel2.getFaultSumNum())));
            } else {
                workOrderStatisticsModel2.setFaultOvertimeRate("0%");
            }
            if (map.get("generateElectricitySumNum") == null) {
                workOrderStatisticsModel2.setGenerateElectricitySumNum(0);
            } else {
                workOrderStatisticsModel2.setGenerateElectricitySumNum(Integer.parseInt(map
                        .get("generateElectricitySumNum").toString()));
            }
            if (map.get("generateElectricityOvertimeNum") == null) {
                workOrderStatisticsModel2.setGenerateElectricityOvertimeNum(0);
            } else {
                workOrderStatisticsModel2.setGenerateElectricityOvertimeNum(Integer.parseInt(map
                        .get("generateElectricityOvertimeNum").toString()));
            }
            if (workOrderStatisticsModel2.getGenerateElectricitySumNum() != 0 && workOrderStatisticsModel2.getGenerateElectricityOvertimeNum() != 0) {
                workOrderStatisticsModel2.setGenerateElectricityOvertimeRate(this.calculateThePercentage(new Double(workOrderStatisticsModel2.getGenerateElectricityOvertimeNum()), new Double(workOrderStatisticsModel2.getGenerateElectricitySumNum())));
            } else {
                workOrderStatisticsModel2.setGenerateElectricityOvertimeRate("0%");
            }
            if (map.get("businessOpenedSumNum") == null) {
                workOrderStatisticsModel2.setBusinessOpenedSumNum(0);
            } else {
                workOrderStatisticsModel2.setBusinessOpenedSumNum(Integer.parseInt(map
                        .get("businessOpenedSumNum").toString()));
            }
            if (map.get("businessOpenedOvertimeNum") == null) {
                workOrderStatisticsModel2.setBusinessOpenedOvertimeNum(0);
            } else {
                workOrderStatisticsModel2.setBusinessOpenedOvertimeNum(Integer.parseInt(map
                        .get("businessOpenedOvertimeNum").toString()));
            }
            if (workOrderStatisticsModel2.getBusinessOpenedSumNum() != 0 && workOrderStatisticsModel2.getBusinessOpenedOvertimeNum() != 0) {
                workOrderStatisticsModel2.setBusinessOpenedOvertimeRate(this.calculateThePercentage(new Double(workOrderStatisticsModel2.getBusinessOpenedOvertimeNum()), new Double(workOrderStatisticsModel2.getBusinessOpenedSumNum())));
            } else {
                workOrderStatisticsModel2.setBusinessOpenedOvertimeRate("0%");
            }
            if (map.get("networkCutoverSumNum") == null) {
                workOrderStatisticsModel2.setNetworkCutoverSumNum(0);
            } else {
                workOrderStatisticsModel2.setNetworkCutoverSumNum(Integer.parseInt(map
                        .get("networkCutoverSumNum").toString()));
            }
            if (map.get("networkCutoverOvertimeNum") == null) {
                workOrderStatisticsModel2.setNetworkCutoverOvertimeNum(0);
            } else {
                workOrderStatisticsModel2.setNetworkCutoverOvertimeNum(Integer.parseInt(map
                        .get("networkCutoverOvertimeNum").toString()));
            }
            if (workOrderStatisticsModel2.getNetworkCutoverSumNum() != 0 && workOrderStatisticsModel2.getNetworkCutoverOvertimeNum() != 0) {
                workOrderStatisticsModel2.setNetworkCutoverOvertimeRate(this.calculateThePercentage(new Double(workOrderStatisticsModel2.getNetworkCutoverOvertimeNum()), new Double(workOrderStatisticsModel2.getNetworkCutoverSumNum())));
            } else {
                workOrderStatisticsModel2.setNetworkCutoverOvertimeRate("0%");
            }
            if (map.get("operationalCSumNum") == null) {
                workOrderStatisticsModel2.setOperationalCoordinationSumNum(0);
            } else {
                workOrderStatisticsModel2.setOperationalCoordinationSumNum(Integer.parseInt(map
                        .get("operationalCSumNum").toString()));
            }
            if (map.get("operationalCOvertimeNum") == null) {
                workOrderStatisticsModel2.setOperationalCoordinationOvertimeNum(0);
            } else {
                workOrderStatisticsModel2.setOperationalCoordinationOvertimeNum(Integer.parseInt(map
                        .get("operationalCOvertimeNum").toString()));
            }
            if (workOrderStatisticsModel2.getOperationalCoordinationSumNum() != 0 && workOrderStatisticsModel2.getOperationalCoordinationOvertimeNum() != 0) {
                workOrderStatisticsModel2.setOperationalCoordinationOvertimeRate(this.calculateThePercentage(new Double(workOrderStatisticsModel2.getOperationalCoordinationOvertimeNum()), new Double(workOrderStatisticsModel2.getOperationalCoordinationSumNum())));
            } else {
                workOrderStatisticsModel2.setOperationalCoordinationOvertimeRate("0%");
            }
            if (map.get("inspectionSumNum") == null) {
                workOrderStatisticsModel2.setInspectionSumNum(0);
            } else {
                workOrderStatisticsModel2.setInspectionSumNum(Integer.parseInt(map
                        .get("inspectionSumNum").toString()));
            }
            if (map.get("inspectionOvertimeNum") == null) {
                workOrderStatisticsModel2.setInspectionOvertimeNum(0);
            } else {
                workOrderStatisticsModel2.setInspectionOvertimeNum(Integer.parseInt(map
                        .get("inspectionOvertimeNum").toString()));
            }
            if (workOrderStatisticsModel2.getInspectionSumNum() != 0 && workOrderStatisticsModel2.getInspectionOvertimeNum() != 0) {
                workOrderStatisticsModel2.setInspectionOvertimeRate(this.calculateThePercentage(new Double(workOrderStatisticsModel2.getInspectionOvertimeNum()), new Double(workOrderStatisticsModel2.getInspectionSumNum())));
            } else {
                workOrderStatisticsModel2.setInspectionOvertimeRate("0%");
            }
            if (map.get("projectAcceptanceSumNum") == null) {
                workOrderStatisticsModel2.setProjectAcceptanceSumNum(0);
            } else {
                workOrderStatisticsModel2.setProjectAcceptanceSumNum(Integer.parseInt(map
                        .get("projectAcceptanceSumNum").toString()));
            }
            if (map.get("projectAcceptanceOvertimeNum") == null) {
                workOrderStatisticsModel2.setProjectAcceptanceOvertimeNum(0);
            } else {
                workOrderStatisticsModel2.setProjectAcceptanceOvertimeNum(Integer.parseInt(map
                        .get("projectAcceptanceOvertimeNum").toString()));
            }
            if (workOrderStatisticsModel2.getProjectAcceptanceSumNum() != 0 && workOrderStatisticsModel2.getProjectAcceptanceOvertimeNum() != 0) {
                workOrderStatisticsModel2.setProjectAcceptanceOvertimeRate(this.calculateThePercentage(new Double(workOrderStatisticsModel2.getProjectAcceptanceOvertimeNum()), new Double(workOrderStatisticsModel2.getProjectAcceptanceSumNum())));
            } else {
                workOrderStatisticsModel2.setProjectAcceptanceOvertimeRate("0%");
            }
            if (map.get("otherSumNum") == null) {
                workOrderStatisticsModel2.setOtherSumNum(0);
            } else {
                workOrderStatisticsModel2.setOtherSumNum(Integer.parseInt(map
                        .get("otherSumNum").toString()));
            }
            if (map.get("otherOvertimeNum") == null) {
                workOrderStatisticsModel2.setOtherOvertimeNum(0);
            } else {
                workOrderStatisticsModel2.setOtherOvertimeNum(Integer.parseInt(map
                        .get("otherOvertimeNum").toString()));
            }
            if (workOrderStatisticsModel2.getOtherSumNum() != 0 && workOrderStatisticsModel2.getOtherOvertimeNum() != 0) {
                workOrderStatisticsModel2.setOtherOvertimeRate(this.calculateThePercentage(new Double(workOrderStatisticsModel2.getOtherOvertimeNum()), new Double(workOrderStatisticsModel2.getOtherSumNum())));
            } else {
                workOrderStatisticsModel2.setOtherOvertimeRate("0%");
            }
            if (map.get("paymentSumNum") == null) {
                workOrderStatisticsModel2.setPaymentSumNum(0);
            } else {
                workOrderStatisticsModel2.setPaymentSumNum(Integer.parseInt(map
                        .get("paymentSumNum").toString()));
            }
            if (map.get("paymentOvertimeNum") == null) {
                workOrderStatisticsModel2.setPaymentOvertimeNum(0);
            } else {
                workOrderStatisticsModel2.setPaymentOvertimeNum(Integer.parseInt(map
                        .get("paymentOvertimeNum").toString()));
            }
            if (workOrderStatisticsModel2.getPaymentSumNum() != 0 && workOrderStatisticsModel2.getPaymentOvertimeNum() != 0) {
                workOrderStatisticsModel2.setPaymentOvertimeRate(this.calculateThePercentage(new Double(workOrderStatisticsModel2.getPaymentOvertimeNum()), new Double(workOrderStatisticsModel2.getPaymentSumNum())));
            } else {
                workOrderStatisticsModel2.setPaymentOvertimeRate("0%");
            }
            r.add(workOrderStatisticsModel2);
        }
        return r;
    }
/**
 * flag ：1：总工单；2：超时工单； 3：未归档工单； 4：归档工单。
 */
	@Override
	public List<WorkOrderStatisticsDrillModel> taskTicketStatisticsDrill(
			String flag, String city, String beginTime, String endTime,
			String subType) {
		// TODO Auto-generated method stub
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 List<WorkOrderStatisticsDrillModel> r = new ArrayList<WorkOrderStatisticsDrillModel>();
	        StringBuffer sqlBuffer = new StringBuffer();
	        sqlBuffer.append(" select task.city,task.process_instance_id,task.id,task.theme,");
	        sqlBuffer.append(" task.create_time,task.initiator,u.deptid from pnr_act_task_ticket_main task left join taw_system_user u on task.initiator = u.userid where 1=1");
	        
	        if(null!=subType && !"".equals(subType)){
	        	sqlBuffer.append(" and task.sub_type in(").append(subType).append(")");
	        }
	        
	        if("1".equals(flag))
	        {
	        	
	        }else if("2".equals(flag))
	        {
	        	sqlBuffer.append(" and decode(sign(nvl(task.last_reply_time, sysdate) - task.end_time),1,1,-1,0,0)=1");
	        }else if("3".equals(flag))
	        {
	        	sqlBuffer.append(" and decode(task.archiving_time, '', 1, null, 1, 0) =1 ");
	        }else if("4".equals(flag))
	        {
	        	sqlBuffer.append(" and decode(task.archiving_time, '', 1, null, 1, 0) =0 ");
	        }
	        
	        
	        if(null!=beginTime && !"".equals(beginTime))
	        {
	        	sqlBuffer.append(" and to_char(task.create_time,'yyyy-mm-dd') >='").append(beginTime).append("'");
	        }
	        
	        if(null!=endTime && !"".equals(endTime)){
	        	sqlBuffer.append(" and to_char(task.create_time,'yyyy-mm-dd') <='").append(endTime).append("'");	        }

	        if(null!=city && !"".equals(city)){
	        	if(city.equals("28"))
	        	{
	        		sqlBuffer.append(" and task.city like'").append(city).append("%'");
	        	}else
	        	{
	        		sqlBuffer.append(" and task.city='").append(city).append("'");
					
				}
	        }
	        
	        System.out.println("PnrStatisticsDaoJDBC-taskTicketStatisticsDrill--657--"+sqlBuffer);

	        List<Map> list = this.getJdbcTemplate().queryForList(sqlBuffer.toString());
	        for (Map map : list) {
	        	WorkOrderStatisticsDrillModel workOrderStatisticsModel = new WorkOrderStatisticsDrillModel();
	            workOrderStatisticsModel.setCity(map.get("CITY").toString());
	            workOrderStatisticsModel.setProcessInstanceId(map.get("PROCESS_INSTANCE_ID").toString());
	            workOrderStatisticsModel.setThemeId(map.get("ID").toString());
	            workOrderStatisticsModel.setTheme(map.get("THEME").toString());
	            if(map.get("DEPTID")==null){
	            	
	            	workOrderStatisticsModel.setDeptId("");
	            }else{
	            	
	            	workOrderStatisticsModel.setDeptId(map.get("DEPTID").toString());
	            }
	            try {
					workOrderStatisticsModel.setCreateDate(dateFormat.parse(map.get("CREATE_TIME").toString()));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            workOrderStatisticsModel.setInitiator(map.get("INITIATOR").toString());
	            workOrderStatisticsModel.setFlag(2);
	           
	            r.add(workOrderStatisticsModel);
	        }
	        return r;
	}

	@Override
	public List<WorkOrderStatisticsDrillModel> troubleTicketStatisticsDrill(
			String flag, String city, String beginTime, String endTime,
			String subType) {
		// TODO Auto-generated method stub
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		 List<WorkOrderStatisticsDrillModel> r = new ArrayList<WorkOrderStatisticsDrillModel>();
	        StringBuffer sqlBuffer = new StringBuffer();
	        sqlBuffer.append(" select trouble.city,trouble.process_instance_id,trouble.id,trouble.theme,");
//	        sqlBuffer.append(" trouble.send_time,trouble.initiator from pnr_act_trouble_ticket_main trouble where 1=1 and trouble.state=5 ");
	        sqlBuffer.append(" trouble.send_time,trouble.initiator,u.deptid from pnr_act_trouble_ticket_main trouble left join taw_system_user u on trouble.initiator=u.userid where 1=1 ");
	        
	        if(null!=subType && !"".equals(subType)){
	        	sqlBuffer.append(" and trouble.sub_type in(").append(subType).append(")");
	        }
	        
	        if("1".equals(flag))
	        {
	        	
	        }else if("2".equals(flag))
	        {
	        	sqlBuffer.append(" and decode(sign(nvl(trouble.last_reply_time, sysdate) - trouble.end_time),1,1,-1,0,0)=1");
	        }else if("3".equals(flag))
	        {
	        	sqlBuffer.append(" and decode(trouble.archiving_time, '', 1, null, 1, 0) =1 ");
	        }else if("4".equals(flag))
	        {
	        	sqlBuffer.append(" and decode(trouble.archiving_time, '', 1, null, 1, 0) =0 ");
	        }
	        
	        
	        if(null!=beginTime && !"".equals(beginTime))
	        {
	        	sqlBuffer.append(" and to_char(trouble.create_time,'yyyy-mm-dd') >='").append(beginTime).append("'");
	        }
	        
	        if(null!=endTime && !"".equals(endTime)){
	        	sqlBuffer.append(" and to_char(trouble.create_time,'yyyy-mm-dd') <='").append(endTime).append("'");
	        }

	        if(null!=city && !"".equals(city)){
	        	if(city.equals("28"))
	        	{
	        		sqlBuffer.append(" and trouble.city like'").append(city).append("%'");
	        	}else
	        	{
	        		sqlBuffer.append(" and trouble.city='").append(city).append("'");
					
				}
	        }
	        
	        System.out.println("PnrStatisticsDaoJDBC-troubleTicketStatisticsDrill--732--"+sqlBuffer);
	        List<Map> list = this.getJdbcTemplate().queryForList(sqlBuffer.toString());
	        for (Map map : list) {
	        	WorkOrderStatisticsDrillModel workOrderStatisticsModel = new WorkOrderStatisticsDrillModel();
	            workOrderStatisticsModel.setCity(map.get("CITY").toString());
	            workOrderStatisticsModel.setProcessInstanceId(map.get("PROCESS_INSTANCE_ID").toString());
	            workOrderStatisticsModel.setThemeId(map.get("ID").toString());
	            workOrderStatisticsModel.setTheme(map.get("THEME").toString());
	            if(map.get("DEPTID")==null){
	            	
	            	workOrderStatisticsModel.setDeptId("");
	            }else{
	            	
	            	workOrderStatisticsModel.setDeptId(map.get("DEPTID").toString());
	            }
	            try {
					workOrderStatisticsModel.setCreateDate(dateFormat.parse(map.get("SEND_TIME").toString()));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            workOrderStatisticsModel.setInitiator(map.get("INITIATOR").toString());
	            workOrderStatisticsModel.setFlag(1);

	            r.add(workOrderStatisticsModel);
	        }
	        return r;
	}
	@Override
	public List<WorkOrderStatisticsDrillModel> transferOfficeTicketStatisticsDrill(
			String flag, String city, String beginTime, String endTime,
			String subType) {
		// TODO Auto-generated method stub
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		List<WorkOrderStatisticsDrillModel> r = new ArrayList<WorkOrderStatisticsDrillModel>();
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append(" select d.areaid,transferOffice.process_instance_id,transferOffice.id,transferOffice.theme,");
		sqlBuffer.append(" transferOffice.send_time,transferOffice.task_assignee,u.deptid from pnr_act_transfer_office_main transferOffice left join taw_system_user u on transferOffice.task_assignee=u.userid   left join taw_system_dept d on u.deptid = d.deptid  left join taw_system_area ta on d.areaid = ta.areaid where 1=1 and transferOffice.themeinterface='transferOffice' and transferOffice.state!=1");
		
		if(null!=subType && !"".equals(subType)){
			sqlBuffer.append(" and transferOffice.sub_type in(").append(subType).append(")");
		}
		
		if("1".equals(flag))
		{
			
		}else if("2".equals(flag))
		{
			sqlBuffer.append(" and decode(sign(nvl(transferOffice.last_reply_time, sysdate) - transferOffice.end_time),1,1,-1,0,0)=1");
		}else if("3".equals(flag))
		{
			sqlBuffer.append(" and decode(transferOffice.archiving_time, '', 1, null, 1, 0) =1 ");
		}else if("4".equals(flag))
		{
			sqlBuffer.append(" and decode(transferOffice.archiving_time, '', 1, null, 1, 0) =0 ");
		}
		
		
		if(null!=beginTime && !"".equals(beginTime))
		{
			sqlBuffer.append(" and to_char(transferOffice.create_time,'yyyy-mm-dd') >='").append(beginTime).append("'");
		}
		
		if(null!=endTime && !"".equals(endTime)){
			sqlBuffer.append(" and to_char(transferOffice.create_time,'yyyy-mm-dd') <='").append(endTime).append("'");
		}
		
		if(null!=city && !"".equals(city)){
			if(city.equals("28"))
			{
				sqlBuffer.append(" and ta.parentareaid like'").append(city).append("%'  and ta.parentareaid!=28");
			}else
			{
				sqlBuffer.append(" and ta.parentareaid='").append(city).append("'");
				
			}
		}
		
		System.out.println("PnrStatisticsDaoJDBC-troubleTicketStatisticsDrill--732--"+sqlBuffer);
		List<Map> list = this.getJdbcTemplate().queryForList(sqlBuffer.toString());
		for (Map map : list) {
			WorkOrderStatisticsDrillModel workOrderStatisticsModel = new WorkOrderStatisticsDrillModel();
			workOrderStatisticsModel.setCity(map.get("AREAID").toString());
			workOrderStatisticsModel.setProcessInstanceId(map.get("PROCESS_INSTANCE_ID").toString());
			workOrderStatisticsModel.setThemeId(map.get("ID").toString());
			workOrderStatisticsModel.setTheme(map.get("THEME").toString());
			if(map.get("DEPTID")==null){
				
				workOrderStatisticsModel.setDeptId("");
			}else{
				
				workOrderStatisticsModel.setDeptId(map.get("DEPTID").toString());
			}
			try {
				workOrderStatisticsModel.setCreateDate(dateFormat.parse(map.get("SEND_TIME").toString()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			workOrderStatisticsModel.setInitiator(map.get("TASK_ASSIGNEE").toString());
			workOrderStatisticsModel.setFlag(1);
			
			r.add(workOrderStatisticsModel);
		}
		return r;
	}
	public List<WorkOrderStatisticsDrillModel> transferOfficeInterfaceStatisticsDrill(String type,String flag,String city,String beginTime, String endTime, String subType){
DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		List<WorkOrderStatisticsDrillModel> r = new ArrayList<WorkOrderStatisticsDrillModel>();
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append(" select d.areaid,transferOffice.process_instance_id,transferOffice.id,transferOffice.theme,");
		if("transferOffice".equals(type)){
			sqlBuffer.append(" transferOffice.send_time,transferOffice.task_assignee,u.deptid from pnr_act_transfer_office_main transferOffice left join taw_system_user u on transferOffice.task_assignee=u.userid   left join taw_system_dept d on u.deptid = d.deptid  left join taw_system_area ta on d.areaid = ta.areaid where 1=1 and transferOffice.themeinterface='transferOffice' and transferOffice.state!=1");
		}else if("interface".equals(type)){
			sqlBuffer.append(" transferOffice.send_time,transferOffice.task_assignee,u.deptid from pnr_act_transfer_office_main transferOffice left join taw_system_user u on transferOffice.task_assignee=u.userid   left join taw_system_dept d on u.deptid = d.deptid  left join taw_system_area ta on d.areaid = ta.areaid where 1=1 and transferOffice.themeinterface='interface' and transferOffice.state!=1");
		}else if("all".equals(type)){
			sqlBuffer.append(" transferOffice.send_time,transferOffice.task_assignee,u.deptid from pnr_act_transfer_office_main transferOffice left join taw_system_user u on transferOffice.task_assignee=u.userid   left join taw_system_dept d on u.deptid = d.deptid  left join taw_system_area ta on d.areaid = ta.areaid where 1=1 and transferOffice.themeinterface in('transferOffice','interface') and transferOffice.state!=1");
		}
		
		
		
		if(null!=subType && !"".equals(subType)){
			sqlBuffer.append(" and transferOffice.sub_type in('").append(subType).append("')");
		}
		
		if("1".equals(flag))
		{
			
		}else if("2".equals(flag))
		{
			sqlBuffer.append(" and decode(sign(nvl(transferOffice.last_reply_time, sysdate) - transferOffice.end_time),1,1,-1,0,0)=1");
		}else if("3".equals(flag))
		{
			sqlBuffer.append(" and decode(transferOffice.archiving_time, '', 1, null, 1, 0) =1 ");
		}else if("4".equals(flag))
		{
			sqlBuffer.append(" and decode(transferOffice.archiving_time, '', 1, null, 1, 0) =0 ");
		}
		
		
		if(null!=beginTime && !"".equals(beginTime))
		{
			sqlBuffer.append(" and to_char(transferOffice.create_time,'yyyy-mm-dd') >='").append(beginTime).append("'");
		}
		
		if(null!=endTime && !"".equals(endTime)){
			sqlBuffer.append(" and to_char(transferOffice.create_time,'yyyy-mm-dd') <='").append(endTime).append("'");
		}
		
		if(null!=city && !"".equals(city)){
			if(city.equals("28"))
			{
				sqlBuffer.append(" and ta.parentareaid like'").append(city).append("%'  and ta.parentareaid!=28");
			}else
			{
				sqlBuffer.append(" and ta.parentareaid='").append(city).append("'");
				
			}
		}
		
		System.out.println("PnrStatisticsDaoJDBC-troubleTicketStatisticsDrill--732--"+sqlBuffer);
		List<Map> list = this.getJdbcTemplate().queryForList(sqlBuffer.toString());
		for (Map map : list) {
			WorkOrderStatisticsDrillModel workOrderStatisticsModel = new WorkOrderStatisticsDrillModel();
			workOrderStatisticsModel.setCity(map.get("AREAID").toString());
			workOrderStatisticsModel.setProcessInstanceId(map.get("PROCESS_INSTANCE_ID").toString());
			workOrderStatisticsModel.setThemeId(map.get("ID").toString());
			workOrderStatisticsModel.setTheme(map.get("THEME").toString());
			if(map.get("DEPTID")==null){
				
				workOrderStatisticsModel.setDeptId("");
			}else{
				
				workOrderStatisticsModel.setDeptId(map.get("DEPTID").toString());
			}
			try {
				workOrderStatisticsModel.setCreateDate(dateFormat.parse(map.get("SEND_TIME").toString()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			workOrderStatisticsModel.setInitiator(map.get("TASK_ASSIGNEE").toString());
			workOrderStatisticsModel.setFlag(1);
			
			r.add(workOrderStatisticsModel);
		}
		return r;
	}
	
	
	/**
	 * flag ：1：总工单；2：超时工单； 3：未归档工单； 4：归档工单。
	 */
		@Override
		public List<WorkOrderStatisticsDrillModel> taskTicketStatisticsDrillbycity(
				String flag, String city, String beginTime, String endTime,
				String subType) {
			// TODO Auto-generated method stub
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			 List<WorkOrderStatisticsDrillModel> r = new ArrayList<WorkOrderStatisticsDrillModel>();
		        StringBuffer sqlBuffer = new StringBuffer();
		        sqlBuffer.append(" select task.country,task.process_instance_id,task.id,task.theme,");
		        sqlBuffer.append(" task.create_time,task.initiator,u.deptid from pnr_act_task_ticket_main task left join taw_system_user u on task.initiator = u.userid where 1=1");
		        
		        if(null!=subType && !"".equals(subType)){
		        	sqlBuffer.append(" and task.sub_type in(").append(subType).append(")");
		        }
		        
		        if("1".equals(flag))
		        {
		        	
		        }else if("2".equals(flag))
		        {
		        	sqlBuffer.append(" and decode(sign(nvl(task.last_reply_time, sysdate) - task.end_time),1,1,-1,0,0)=1");
		        }else if("3".equals(flag))
		        {
		        	sqlBuffer.append(" and decode(task.archiving_time, '', 1, null, 1, 0) =1 ");
		        }else if("4".equals(flag))
		        {
		        	sqlBuffer.append(" and decode(task.archiving_time, '', 1, null, 1, 0) =0 ");
		        }
		        
		        
		        if(null!=beginTime && !"".equals(beginTime))
		        {
		        	sqlBuffer.append(" and to_char(task.create_time,'yyyy-mm-dd') >='").append(beginTime).append("'");
		        }
		        
		        if(null!=endTime && !"".equals(endTime)){
		        	sqlBuffer.append(" and to_char(task.create_time,'yyyy-mm-dd') <='").append(endTime).append("'");	        }

		        if(null!=city &&!"".equals(city)){
		        	if(city.equals("28"))
		        	{
		        		sqlBuffer.append(" and task.country like'").append(city).append("%'");
		        	}else
		        	{
		        		sqlBuffer.append(" and task.country='").append(city).append("'");
						
					}
		        }
		        
		        System.out.println("PnrStatisticsDaoJDBC-taskTicketStatisticsDrill--657--用于区县级"+sqlBuffer);

		        List<Map> list = this.getJdbcTemplate().queryForList(sqlBuffer.toString());
		        for (Map map : list) {
		        	WorkOrderStatisticsDrillModel workOrderStatisticsModel = new WorkOrderStatisticsDrillModel();
		            workOrderStatisticsModel.setCity(map.get("COUNTRY").toString());
		            workOrderStatisticsModel.setProcessInstanceId(map.get("PROCESS_INSTANCE_ID").toString());
		            workOrderStatisticsModel.setThemeId(map.get("ID").toString());
		            workOrderStatisticsModel.setTheme(map.get("THEME").toString());
		            if(map.get("DEPTID")==null){
		            	
		            	workOrderStatisticsModel.setDeptId("");
		            }else{
		            	
		            	workOrderStatisticsModel.setDeptId(map.get("DEPTID").toString());
		            }
		            try {
						workOrderStatisticsModel.setCreateDate(dateFormat.parse(map.get("CREATE_TIME").toString()));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		            workOrderStatisticsModel.setInitiator(map.get("INITIATOR").toString());
		            workOrderStatisticsModel.setFlag(2);
		           
		            r.add(workOrderStatisticsModel);
		        }
		        return r;
		}

		@Override
		public List<WorkOrderStatisticsDrillModel> troubleTicketStatisticsDrillbycity(
				String flag, String city, String beginTime, String endTime,
				String subType) {
			// TODO Auto-generated method stub
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

			 List<WorkOrderStatisticsDrillModel> r = new ArrayList<WorkOrderStatisticsDrillModel>();
		        StringBuffer sqlBuffer = new StringBuffer();
		        sqlBuffer.append(" select trouble.country,trouble.process_instance_id,trouble.id,trouble.theme,");
//		        sqlBuffer.append(" trouble.send_time,trouble.initiator from pnr_act_trouble_ticket_main trouble where 1=1 and trouble.state=5 ");
		        sqlBuffer.append(" trouble.send_time,trouble.initiator,u.deptid from pnr_act_trouble_ticket_main trouble left join taw_system_user u on trouble.initiator=u.userid where 1=1 ");
		        
		        if(null!=subType && !"".equals(subType)){
		        	sqlBuffer.append(" and trouble.sub_type in(").append(subType).append(")");
		        }
		        
		        if("1".equals(flag))
		        {
		        	
		        }else if("2".equals(flag))
		        {
		        	sqlBuffer.append(" and decode(sign(nvl(trouble.last_reply_time, sysdate) - trouble.end_time),1,1,-1,0,0)=1");
		        }else if("3".equals(flag))
		        {
		        	sqlBuffer.append(" and decode(trouble.archiving_time, '', 1, null, 1, 0) =1 ");
		        }else if("4".equals(flag))
		        {
		        	sqlBuffer.append(" and decode(trouble.archiving_time, '', 1, null, 1, 0) =0 ");
		        }
		        
		        
		        if(null!=beginTime && !"".equals(beginTime))
		        {
		        	sqlBuffer.append(" and to_char(trouble.create_time,'yyyy-mm-dd') >='").append(beginTime).append("'");
		        }
		        
		        if(null!=endTime&&!"".equals(endTime)){
		        	sqlBuffer.append(" and to_char(trouble.create_time,'yyyy-mm-dd') <='").append(endTime).append("'");
		        }

		        if(null!=city && !"".equals(city)){
		        	if(city.equals("28"))
		        	{
		        		sqlBuffer.append(" and trouble.country like'").append(city).append("%'");
		        	}else
		        	{
		        		sqlBuffer.append(" and trouble.country='").append(city).append("'");
						
					}
		        }
		        
		        System.out.println("PnrStatisticsDaoJDBC-troubleTicketStatisticsDrill--732--用于区县级"+sqlBuffer);
		        List<Map> list = this.getJdbcTemplate().queryForList(sqlBuffer.toString());
		        for (Map map : list) {
		        	WorkOrderStatisticsDrillModel workOrderStatisticsModel = new WorkOrderStatisticsDrillModel();
		            workOrderStatisticsModel.setCity(map.get("COUNTRY").toString());
		            workOrderStatisticsModel.setProcessInstanceId(map.get("PROCESS_INSTANCE_ID").toString());
		            workOrderStatisticsModel.setThemeId(map.get("ID").toString());
		            workOrderStatisticsModel.setTheme(map.get("THEME").toString());
		            if(map.get("DEPTID")==null){
		            	
		            	workOrderStatisticsModel.setDeptId("");
		            }else{
		            	
		            	workOrderStatisticsModel.setDeptId(map.get("DEPTID").toString());
		            }
		            try {
						workOrderStatisticsModel.setCreateDate(dateFormat.parse(map.get("SEND_TIME").toString()));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		            workOrderStatisticsModel.setInitiator(map.get("INITIATOR").toString());
		            workOrderStatisticsModel.setFlag(1);

		            r.add(workOrderStatisticsModel);
		        }
		        return r;
		}
		
		@Override
		public List<WorkOrderStatisticsDrillModel> transferOfficeTicketStatisticsDrillbycity(
				String flag, String city, String beginTime, String endTime,
				String subType) {
			// TODO Auto-generated method stub
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			List<WorkOrderStatisticsDrillModel> r = new ArrayList<WorkOrderStatisticsDrillModel>();
			StringBuffer sqlBuffer = new StringBuffer();
			sqlBuffer.append(" select d.areaid,transferOffice.process_instance_id,transferOffice.id,transferOffice.theme,");
			sqlBuffer.append(" transferOffice.send_time,transferOffice.task_assignee,u.deptid from pnr_act_transfer_office_main transferOffice left join taw_system_user u on transferOffice.task_assignee=u.userid   left join taw_system_dept d on u.deptid = d.deptid left join taw_system_area ta on d.areaid = ta.areaid where 1=1  and transferOffice.themeinterface='transferOffice'  and transferOffice.state=5");
			
			if(null!=subType && !"".equals(subType)){
				sqlBuffer.append(" and transferOffice.sub_type in(").append(subType).append(")");
			}
			
			if("1".equals(flag))
			{
				
			}else if("2".equals(flag))
			{
				sqlBuffer.append(" and decode(sign(nvl(transferOffice.last_reply_time, sysdate) - transferOffice.end_time),1,1,-1,0,0)=1");
			}else if("3".equals(flag))
			{
				sqlBuffer.append(" and decode(transferOffice.archiving_time, '', 1, null, 1, 0) =1 ");
			}else if("4".equals(flag))
			{
				sqlBuffer.append(" and decode(transferOffice.archiving_time, '', 1, null, 1, 0) =0 ");
			}
			
			
			if(null!=beginTime && !"".equals(beginTime))
			{
				sqlBuffer.append(" and to_char(transferOffice.create_time,'yyyy-mm-dd') >='").append(beginTime).append("'");
			}
			
			if(null!=endTime && !"".equals(endTime)){
				sqlBuffer.append(" and to_char(transferOffice.create_time,'yyyy-mm-dd') <='").append(endTime).append("'");
			}
			
			if(null!=city && !"".equals(city)){
				if(city.equals("28"))
				{
					sqlBuffer.append(" and d.areaid like'").append(city).append("%'  and ta.parentareaid!=28");
				}else if(city.length()==4)
				{
					sqlBuffer.append(" and ta.parentareaid='").append(city).append("'");
					
				}else{
					sqlBuffer.append(" and d.areaid='").append(city).append("'");
				}
			}
			
			System.out.println("PnrStatisticsDaoJDBC-troubleTicketStatisticsDrill--732--用于区县级"+sqlBuffer);
			List<Map> list = this.getJdbcTemplate().queryForList(sqlBuffer.toString());
			for (Map map : list) {
				WorkOrderStatisticsDrillModel workOrderStatisticsModel = new WorkOrderStatisticsDrillModel();
				workOrderStatisticsModel.setCity(map.get("AREAID").toString());
				workOrderStatisticsModel.setProcessInstanceId(map.get("PROCESS_INSTANCE_ID").toString());
				workOrderStatisticsModel.setThemeId(map.get("ID").toString());
				workOrderStatisticsModel.setTheme(map.get("THEME").toString());
				if(map.get("DEPTID")==null){
					
					workOrderStatisticsModel.setDeptId("");
				}else{
					
					workOrderStatisticsModel.setDeptId(map.get("DEPTID").toString());
				}
				try {
					workOrderStatisticsModel.setCreateDate(dateFormat.parse(map.get("SEND_TIME").toString()));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				workOrderStatisticsModel.setInitiator(map.get("TASK_ASSIGNEE").toString());
				workOrderStatisticsModel.setFlag(1);
				
				r.add(workOrderStatisticsModel);
			}
			return r;
		}
		
		public List<WorkOrderStatisticsDrillModel> transferOfficeInterfaceStatisticsDrillbycity(String type,String flag,String city,String beginTime, String endTime, String subType){
			// TODO Auto-generated method stub
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			List<WorkOrderStatisticsDrillModel> r = new ArrayList<WorkOrderStatisticsDrillModel>();
			StringBuffer sqlBuffer = new StringBuffer();
			sqlBuffer.append(" select d.areaid,transferOffice.process_instance_id,transferOffice.id,transferOffice.theme,");
			if("transferOffice".equals(type)){
				sqlBuffer.append(" transferOffice.send_time,transferOffice.task_assignee,u.deptid from pnr_act_transfer_office_main transferOffice left join taw_system_user u on transferOffice.task_assignee=u.userid   left join taw_system_dept d on u.deptid = d.deptid left join taw_system_area ta on d.areaid = ta.areaid where 1=1  and transferOffice.themeinterface='transferOffice'  and transferOffice.state!=1");
			}else if("interface".equals(type)){
				sqlBuffer.append(" transferOffice.send_time,transferOffice.task_assignee,u.deptid from pnr_act_transfer_office_main transferOffice left join taw_system_user u on transferOffice.task_assignee=u.userid   left join taw_system_dept d on u.deptid = d.deptid left join taw_system_area ta on d.areaid = ta.areaid where 1=1  and transferOffice.themeinterface='interface'  and transferOffice.state!=1");
			}else if("all".equals(type)){
				sqlBuffer.append(" transferOffice.send_time,transferOffice.task_assignee,u.deptid from pnr_act_transfer_office_main transferOffice left join taw_system_user u on transferOffice.task_assignee=u.userid   left join taw_system_dept d on u.deptid = d.deptid left join taw_system_area ta on d.areaid = ta.areaid where 1=1  and transferOffice.themeinterface in('transferOffice','interface')  and transferOffice.state!=1");
			}
			
			
			
			if(null!=subType && !"".equals(subType)){
				sqlBuffer.append(" and transferOffice.sub_type in('").append(subType).append("')");
			}
			
			if("1".equals(flag))
			{
				
			}else if("2".equals(flag))
			{
				sqlBuffer.append(" and decode(sign(nvl(transferOffice.last_reply_time, sysdate) - transferOffice.end_time),1,1,-1,0,0)=1");
			}else if("3".equals(flag))
			{
				sqlBuffer.append(" and decode(transferOffice.archiving_time, '', 1, null, 1, 0) =1 ");
			}else if("4".equals(flag))
			{
				sqlBuffer.append(" and decode(transferOffice.archiving_time, '', 1, null, 1, 0) =0 ");
			}
			
			
			if(null!=beginTime && !"".equals(beginTime))
			{
				sqlBuffer.append(" and to_char(transferOffice.create_time,'yyyy-mm-dd') >='").append(beginTime).append("'");
			}
			
			if(null!=endTime && !"".equals(endTime)){
				sqlBuffer.append(" and to_char(transferOffice.create_time,'yyyy-mm-dd') <='").append(endTime).append("'");
			}
			
			if(null!=city && !"".equals(city)){
				if(city.equals("28"))
				{
					sqlBuffer.append(" and d.areaid like'").append(city).append("%'  and ta.parentareaid!=28");
				}else if(city.length()==4)
				{
					sqlBuffer.append(" and ta.parentareaid='").append(city).append("'");
					
				}else{
					sqlBuffer.append(" and d.areaid='").append(city).append("'");
				}
			}
			
			System.out.println("PnrStatisticsDaoJDBC-troubleTicketStatisticsDrill--732--用于区县级"+sqlBuffer);
			List<Map> list = this.getJdbcTemplate().queryForList(sqlBuffer.toString());
			for (Map map : list) {
				WorkOrderStatisticsDrillModel workOrderStatisticsModel = new WorkOrderStatisticsDrillModel();
				workOrderStatisticsModel.setCity(map.get("AREAID").toString());
				workOrderStatisticsModel.setProcessInstanceId(map.get("PROCESS_INSTANCE_ID").toString());
				workOrderStatisticsModel.setThemeId(map.get("ID").toString());
				workOrderStatisticsModel.setTheme(map.get("THEME").toString());
				if(map.get("DEPTID")==null){
					
					workOrderStatisticsModel.setDeptId("");
				}else{
					
					workOrderStatisticsModel.setDeptId(map.get("DEPTID").toString());
				}
				try {
					workOrderStatisticsModel.setCreateDate(dateFormat.parse(map.get("SEND_TIME").toString()));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				workOrderStatisticsModel.setInitiator(map.get("TASK_ASSIGNEE").toString());
				workOrderStatisticsModel.setFlag(1);
				
				r.add(workOrderStatisticsModel);
			}
			return r;
		}
		
		
		/**
		 * flag ：1：总工单；2：超时工单； 3：未归档工单； 4：归档工单。
		 */
			@Override
			public List<WorkOrderStatisticsDrillModel> taskTicketStatisticsDrillbyperson(String person,
					String flag, String city, String beginTime, String endTime,
					String subType) {
				// TODO Auto-generated method stub
					DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				 List<WorkOrderStatisticsDrillModel> r = new ArrayList<WorkOrderStatisticsDrillModel>();
			        StringBuffer sqlBuffer = new StringBuffer();
			        
			        sqlBuffer.append(" select a.*,u.userid from (select task.city, p.person,task.process_instance_id,task.id,task.theme, task.create_time,task.initiator ");
			        sqlBuffer.append(" from pnr_act_task_ticket_main task,pnr_act_task_person_relate p where 1 = 1 and task.process_instance_id = p.process_instance_id ");
			        
			        if(null!=subType && !"".equals(subType)){
			        	sqlBuffer.append(" and task.sub_type in(").append(subType).append(")");
			        }
			        
			        if("1".equals(flag))
			        {
			        	
			        }else if("2".equals(flag))
			        {
			        	sqlBuffer.append(" and decode(sign(nvl(task.last_reply_time, sysdate) - task.end_time),1,1,-1,0,0)=1");
			        }else if("3".equals(flag))
			        {
			        	sqlBuffer.append(" and decode(task.archiving_time, '', 1, null, 1, 0) =1 ");
			        }else if("4".equals(flag))
			        {
			        	sqlBuffer.append(" and decode(task.archiving_time, '', 1, null, 1, 0) =0 ");
			        }
			        
			        
			        if(null!=beginTime && !"".equals(beginTime))
			        {
			        	sqlBuffer.append(" and to_char(task.create_time,'yyyy-mm-dd') >='").append(beginTime).append("'");
			        }
			        
			        if(null!=endTime && !"".equals(endTime)){
			        	sqlBuffer.append(" and to_char(task.create_time,'yyyy-mm-dd') <='").append(endTime).append("'");	        }

			        if(null!=city && !"".equals(city)){
			        	if(city.equals("28"))
			        	{
			        		sqlBuffer.append(" and task.country like'").append(city).append("%'");
			        	}else
			        	{
			        		sqlBuffer.append(" and task.country='").append(city).append("'");
							
						}
			        }
			        sqlBuffer.append(" and p.person = '"+person+"'");
			        sqlBuffer.append(" and task.country = '"+city+"'");
			        sqlBuffer.append(" ) a left join taw_system_user u on a.initiator = u.userid ");
			        
			        System.out.println("PnrStatisticsDaoJDBC-taskTicketStatisticsDrill--657--用于处理人级"+sqlBuffer);

			        List<Map> list = this.getJdbcTemplate().queryForList(sqlBuffer.toString());
			        for (Map map : list) {
			        	WorkOrderStatisticsDrillModel workOrderStatisticsModel = new WorkOrderStatisticsDrillModel();
			            workOrderStatisticsModel.setCity(map.get("CITY").toString());
			            workOrderStatisticsModel.setProcessInstanceId(map.get("PROCESS_INSTANCE_ID").toString());
			            workOrderStatisticsModel.setThemeId(map.get("ID").toString());
			            workOrderStatisticsModel.setTheme(map.get("THEME").toString());
			            if(map.get("DEPTID")==null){
			            	
			            	workOrderStatisticsModel.setDeptId("");
			            }else{
			            	
			            	workOrderStatisticsModel.setDeptId(map.get("DEPTID").toString());
			            }
			            try {
							workOrderStatisticsModel.setCreateDate(dateFormat.parse(map.get("CREATE_TIME").toString()));
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			            workOrderStatisticsModel.setInitiator(map.get("INITIATOR").toString());
			            workOrderStatisticsModel.setFlag(2);
			           
			            r.add(workOrderStatisticsModel);
			        }
			        return r;
			}

			@Override
			public List<WorkOrderStatisticsDrillModel> troubleTicketStatisticsDrillbyperson(String person,
					String flag, String city, String beginTime, String endTime,
					String subType) {
				// TODO Auto-generated method stub
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

				 List<WorkOrderStatisticsDrillModel> r = new ArrayList<WorkOrderStatisticsDrillModel>();
			        StringBuffer sqlBuffer = new StringBuffer();
			        sqlBuffer.append(" select a.*,u.userid from (select trouble.city, p.person,trouble.process_instance_id,trouble.id,trouble.theme, trouble.send_time,trouble.initiator ");
			        sqlBuffer.append(" from pnr_act_trouble_ticket_main trouble,pnr_act_trouble_person_relate p where 1 = 1 and trouble.process_instance_id = p.process_instance_id ");
			         
			        if(null!=subType && !"".equals(subType)){
			        	sqlBuffer.append(" and trouble.sub_type in(").append(subType).append(")");
			        }
			        
			        if("1".equals(flag))
			        {
			        	
			        }else if("2".equals(flag))
			        {
			        	sqlBuffer.append(" and decode(sign(nvl(trouble.last_reply_time, sysdate) - trouble.end_time),1,1,-1,0,0)=1");
			        }else if("3".equals(flag))
			        {
			        	sqlBuffer.append(" and decode(trouble.archiving_time, '', 1, null, 1, 0) =1 ");
			        }else if("4".equals(flag))
			        {
			        	sqlBuffer.append(" and decode(trouble.archiving_time, '', 1, null, 1, 0) =0 ");
			        }
			        
			        
			        if(null!=beginTime && !"".equals(beginTime))
			        {
			        	sqlBuffer.append(" and to_char(trouble.create_time,'yyyy-mm-dd') >='").append(beginTime).append("'");
			        }
			        
			        if(null!=endTime && !"".equals(endTime)){
			        	sqlBuffer.append(" and to_char(trouble.create_time,'yyyy-mm-dd') <='").append(endTime).append("'");
			        }

			        if(null!=city && !"".equals(city)){
			        	if(city.equals("28"))
			        	{
			        		sqlBuffer.append(" and trouble.country like'").append(city).append("%'");
			        	}else
			        	{
			        		sqlBuffer.append(" and trouble.country='").append(city).append("'");
							
						}
			        }
			        sqlBuffer.append(" and p.person = '"+person+"'");
			        sqlBuffer.append(" and trouble.country = '"+city+"'");
			        sqlBuffer.append(" ) a left join taw_system_user u on a.initiator = u.userid ");
			        
			        System.out.println("PnrStatisticsDaoJDBC-troubleTicketStatisticsDrill--732--用于处理人级"+sqlBuffer);
			        List<Map> list = this.getJdbcTemplate().queryForList(sqlBuffer.toString());
			        for (Map map : list) {
			        	WorkOrderStatisticsDrillModel workOrderStatisticsModel = new WorkOrderStatisticsDrillModel();
			            workOrderStatisticsModel.setCity(map.get("CITY").toString());
			            workOrderStatisticsModel.setProcessInstanceId(map.get("PROCESS_INSTANCE_ID").toString());
			            workOrderStatisticsModel.setThemeId(map.get("ID").toString());
			            workOrderStatisticsModel.setTheme(map.get("THEME").toString());
			            if(map.get("DEPTID")==null){
			            	
			            	workOrderStatisticsModel.setDeptId("");
			            }else{
			            	
			            	workOrderStatisticsModel.setDeptId(map.get("DEPTID").toString());
			            }
			            try {
							workOrderStatisticsModel.setCreateDate(dateFormat.parse(map.get("SEND_TIME").toString()));
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			            workOrderStatisticsModel.setInitiator(map.get("INITIATOR").toString());
			            workOrderStatisticsModel.setFlag(1);

			            r.add(workOrderStatisticsModel);
			        }
			        return r;
			}
			@Override
			public List<WorkOrderStatisticsDrillModel> transferOfficeTicketStatisticsDrillbyperson(String person,
					String flag, String city, String beginTime, String endTime,
					String subType) {
				// TODO Auto-generated method stub
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				
				List<WorkOrderStatisticsDrillModel> r = new ArrayList<WorkOrderStatisticsDrillModel>();
				StringBuffer sqlBuffer = new StringBuffer();
				sqlBuffer.append(" select d.areaid,transferOffice.process_instance_id,transferOffice.id,transferOffice.theme,");
				sqlBuffer.append(" transferOffice.send_time,transferOffice.task_assignee,u.deptid from pnr_act_transfer_office_main transferOffice left join taw_system_user u on transferOffice.task_assignee=u.userid   left join taw_system_dept d on u.deptid = d.deptid left join taw_system_area ta on d.areaid = ta.areaid where 1=1  and transferOffice.themeinterface='transferOffice'");
				
				if(null!=subType && !"".equals(subType)){
					sqlBuffer.append(" and transferOffice.sub_type in(").append(subType).append(")");
				}
				
				if("1".equals(flag))
				{
					
				}else if("2".equals(flag))
				{
					sqlBuffer.append(" and decode(sign(nvl(transferOffice.last_reply_time, sysdate) - transferOffice.end_time),1,1,-1,0,0)=1");
				}else if("3".equals(flag))
				{
					sqlBuffer.append(" and decode(transferOffice.archiving_time, '', 1, null, 1, 0) =1 ");
				}else if("4".equals(flag))
				{
					sqlBuffer.append(" and decode(transferOffice.archiving_time, '', 1, null, 1, 0) =0 ");
				}
				
				
				if(null!=beginTime && !"".equals(beginTime))
				{
					sqlBuffer.append(" and to_char(transferOffice.create_time,'yyyy-mm-dd') >='").append(beginTime).append("'");
				}
				
				if(null!=endTime && !"".equals(endTime)){
					sqlBuffer.append(" and to_char(transferOffice.create_time,'yyyy-mm-dd') <='").append(endTime).append("'");
				}
				
				if(null!=city && !"".equals(city)){
					if(city.equals("28"))
					{
						sqlBuffer.append(" and ta.areaid like'").append(city).append("%' ta.parentareaid!=28");
					}else
					{
						sqlBuffer.append(" and ta.areaid='").append(city).append("'");
						
					}
				}
				sqlBuffer.append(" and transferOffice.task_assignee = '"+person+"'");
				sqlBuffer.append(" and ta.areaid = '"+city+"' and transferOffice.state != '4'");
				//sqlBuffer.append(" ) a left join taw_system_user u on a.task_assignee = u.userid ");
				
				System.out.println("PnrStatisticsDaoJDBC-troubleTicketStatisticsDrill--732--用于处理人级"+sqlBuffer);
				List<Map> list = this.getJdbcTemplate().queryForList(sqlBuffer.toString());
				for (Map map : list) {
					WorkOrderStatisticsDrillModel workOrderStatisticsModel = new WorkOrderStatisticsDrillModel();
					workOrderStatisticsModel.setCity(map.get("AREAID").toString());
					workOrderStatisticsModel.setProcessInstanceId(map.get("PROCESS_INSTANCE_ID").toString());
					workOrderStatisticsModel.setThemeId(map.get("ID").toString());
					workOrderStatisticsModel.setTheme(map.get("THEME").toString());
					if(map.get("DEPTID")==null){
						
						workOrderStatisticsModel.setDeptId("");
					}else{
						
						workOrderStatisticsModel.setDeptId(map.get("DEPTID").toString());
					}
					try {
						workOrderStatisticsModel.setCreateDate(dateFormat.parse(map.get("SEND_TIME").toString()));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					workOrderStatisticsModel.setInitiator(map.get("TASK_ASSIGNEE").toString());
					workOrderStatisticsModel.setFlag(1);
					
					r.add(workOrderStatisticsModel);
				}
				return r;
			}

			public List<WorkOrderStatisticsDrillModel> transferOfficeInterfaceStatisticsDrillbyperson(String type,String person,String flag,String city,String beginTime, String endTime, String subType){
				// TODO Auto-generated method stub
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				
				List<WorkOrderStatisticsDrillModel> r = new ArrayList<WorkOrderStatisticsDrillModel>();
				StringBuffer sqlBuffer = new StringBuffer();
				sqlBuffer.append(" select d.areaid,transferOffice.process_instance_id,transferOffice.id,transferOffice.theme,");
				if("transferOffice".equals(type)){
					sqlBuffer.append(" transferOffice.send_time,transferOffice.task_assignee,u.deptid from pnr_act_transfer_office_main transferOffice left join taw_system_user u on transferOffice.task_assignee=u.userid   left join taw_system_dept d on u.deptid = d.deptid left join taw_system_area ta on d.areaid = ta.areaid where 1=1  and transferOffice.themeinterface='transferOffice'");
				}else if("interface".equals(type)){
					sqlBuffer.append(" transferOffice.send_time,transferOffice.task_assignee,u.deptid from pnr_act_transfer_office_main transferOffice left join taw_system_user u on transferOffice.task_assignee=u.userid   left join taw_system_dept d on u.deptid = d.deptid left join taw_system_area ta on d.areaid = ta.areaid where 1=1  and transferOffice.themeinterface='interface'");
				}else if("all".equals(type)){
					sqlBuffer.append(" transferOffice.send_time,transferOffice.task_assignee,u.deptid from pnr_act_transfer_office_main transferOffice left join taw_system_user u on transferOffice.task_assignee=u.userid   left join taw_system_dept d on u.deptid = d.deptid left join taw_system_area ta on d.areaid = ta.areaid where 1=1  and transferOffice.themeinterface in('transferOffice','interface')");
				}
				
				
				
				if(null!=subType && !"".equals(subType)){
					sqlBuffer.append(" and transferOffice.sub_type in('").append(subType).append("')");
				}
				
				if("1".equals(flag))
				{
					
				}else if("2".equals(flag))
				{
					sqlBuffer.append(" and decode(sign(nvl(transferOffice.last_reply_time, sysdate) - transferOffice.end_time),1,1,-1,0,0)=1");
				}else if("3".equals(flag))
				{
					sqlBuffer.append(" and decode(transferOffice.archiving_time, '', 1, null, 1, 0) =1 ");
				}else if("4".equals(flag))
				{
					sqlBuffer.append(" and decode(transferOffice.archiving_time, '', 1, null, 1, 0) =0 ");
				}
				
				
				if(null!=beginTime && !"".equals(beginTime))
				{
					sqlBuffer.append(" and to_char(transferOffice.create_time,'yyyy-mm-dd') >='").append(beginTime).append("'");
				}
				
				if(null!=endTime && !"".equals(endTime)){
					sqlBuffer.append(" and to_char(transferOffice.create_time,'yyyy-mm-dd') <='").append(endTime).append("'");
				}
				
				if(null!=city && !"".equals(city)){
					if(city.equals("28"))
					{
						sqlBuffer.append(" and ta.areaid like'").append(city).append("%' ta.parentareaid!=28");
					}else
					{
						sqlBuffer.append(" and ta.areaid='").append(city).append("'");
						
					}
				}
				sqlBuffer.append(" and transferOffice.task_assignee = '"+person+"'");
				sqlBuffer.append(" and ta.areaid = '"+city+"' and transferOffice.state != '1'");
				//sqlBuffer.append(" ) a left join taw_system_user u on a.task_assignee = u.userid ");
				
				System.out.println("PnrStatisticsDaoJDBC-troubleTicketStatisticsDrill--732--用于处理人级"+sqlBuffer);
				List<Map> list = this.getJdbcTemplate().queryForList(sqlBuffer.toString());
				for (Map map : list) {
					WorkOrderStatisticsDrillModel workOrderStatisticsModel = new WorkOrderStatisticsDrillModel();
					workOrderStatisticsModel.setCity(map.get("AREAID").toString());
					workOrderStatisticsModel.setProcessInstanceId(map.get("PROCESS_INSTANCE_ID").toString());
					workOrderStatisticsModel.setThemeId(map.get("ID").toString());
					workOrderStatisticsModel.setTheme(map.get("THEME").toString());
					if(map.get("DEPTID")==null){
						
						workOrderStatisticsModel.setDeptId("");
					}else{
						
						workOrderStatisticsModel.setDeptId(map.get("DEPTID").toString());
					}
					try {
						workOrderStatisticsModel.setCreateDate(dateFormat.parse(map.get("SEND_TIME").toString()));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					workOrderStatisticsModel.setInitiator(map.get("TASK_ASSIGNEE").toString());
					workOrderStatisticsModel.setFlag(1);
					
					r.add(workOrderStatisticsModel);
				}
				return r;
			}
			
			
			
	@Override
	public List<WorkOrderStatisticsDrillModel> TroubleStatisticsPartnerIndexDrill(
			String city, String beginTime, String endTime, String subType,String level,String person) {
		// TODO Auto-generated method stub
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		 List<WorkOrderStatisticsDrillModel> r = new ArrayList<WorkOrderStatisticsDrillModel>();
	        StringBuffer sqlBuffer = new StringBuffer();
	        Boolean flag = false;
	        if("city".equals(level))
	        {
	        	sqlBuffer.append(" select trouble.city,");
	       
	        }else if("country".equals(level)||"person".equals(level))
	        {
	        	sqlBuffer.append(" select trouble.country as city,");
	        }
	        
	        sqlBuffer.append(" trouble.process_instance_id,trouble.id,trouble.theme,");
	        sqlBuffer.append(" trouble.send_time,trouble.initiator,");
	        sqlBuffer.append(" decode(sign(nvl(trouble.last_reply_time, sysdate) - trouble.end_time),1,1,-1,0,0) as over_time ");
	       
	        flag=true;
	        sqlBuffer.append(",u.deptid ");
	        sqlBuffer.append(" from pnr_act_trouble_ticket_main trouble left join taw_system_user u on trouble.initiator = u.userid ");
	        if("person".equals(level))
	        {
	        	sqlBuffer.append(" ,pnr_act_trouble_person_relate relate");
	        	sqlBuffer.append(" where trouble.process_instance_id = relate.process_instance_id");
	        	
	        }else {
	        	sqlBuffer.append("  where 1=1");
			}
			sqlBuffer.append(" and trouble.state=5");
	        if(null!=subType && !"".equals(subType)){
	        	sqlBuffer.append(" and trouble.sub_type ='").append(subType).append("'");
	        }
	        	        
	        
	        if(null!=beginTime && !"".equals(beginTime))
	        {
	        	sqlBuffer.append(" and trouble.create_time >= to_date('").append(beginTime).append("','yyyy-MM-dd HH24:mi:ss')");
	        }
	        
	        if(null!=endTime &&!"".equals(endTime)){
	        	sqlBuffer.append(" and trouble.create_time <= to_date('").append(endTime).append("','yyyy-MM-dd HH24:mi:ss')");
	        }

	        if(null!=city && !"".equals(city)){
	        	if("city".equals(level))
	        	{
	        		if(city.equals("28"))
	        		{
	        			sqlBuffer.append(" and trouble.city like'").append(city).append("%'");
	        		}else
	        		{
	        			sqlBuffer.append(" and trouble.city='").append(city).append("'");
	        			
	        		}
	        		
	        	}
	        	else if("country".equals(level))
	        	{
	        		sqlBuffer.append(" and trouble.country='").append(city).append("'");
	        	}else if("person".equals(level))
	        	{
	        		sqlBuffer.append(" and trouble.country='").append(city).append("'");
	        		sqlBuffer.append(" and relate.person='").append(person).append("'");
	        	}
	        		
	        }
	        sqlBuffer.append(" order by trouble.create_time desc");

	        System.out.println("PnrStaticsDaoJDBC--TroubleStatisticsPartnerIndexDrill-824-"+sqlBuffer);
	        List<Map> list = this.getJdbcTemplate().queryForList(sqlBuffer.toString());
	        for (Map map : list) {
	        	WorkOrderStatisticsDrillModel workOrderStatisticsModel = new WorkOrderStatisticsDrillModel();
	            workOrderStatisticsModel.setCity(map.get("CITY").toString());
	            workOrderStatisticsModel.setProcessInstanceId(map.get("PROCESS_INSTANCE_ID").toString());
	            workOrderStatisticsModel.setThemeId(map.get("ID").toString());
	            workOrderStatisticsModel.setTheme(map.get("THEME").toString());
	            try {
					workOrderStatisticsModel.setCreateDate(dateFormat.parse(map.get("SEND_TIME").toString()));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            workOrderStatisticsModel.setInitiator(map.get("INITIATOR").toString());
	            workOrderStatisticsModel.setIsOverTime(Integer.parseInt(map.get("OVER_TIME").toString()));
	            workOrderStatisticsModel.setFlag(1);
	            if(flag){
	            	if(map.get("DEPTID")==null){
	            		
	            		workOrderStatisticsModel.setDeptId("");
	            	}else{
	            		workOrderStatisticsModel.setDeptId(map.get("DEPTID").toString());
	            		
	            	}
	            }

	            r.add(workOrderStatisticsModel);
	        }
	        return r;
	}
    @Override
    public List<WorkOrderStatisticsDrillModel> TroubleStatisticsPartnerIndexDrill3(
            String city, String beginTime, String endTime, String subType,String level,String person) {
        // TODO Auto-generated method stub
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Boolean flag = false;
        List<WorkOrderStatisticsDrillModel> r = new ArrayList<WorkOrderStatisticsDrillModel>();
        StringBuffer sqlBuffer = new StringBuffer();
        if("city".equals(level))
        {
            sqlBuffer.append(" select trouble.city,");

        }else if("country".equals(level)||"person".equals(level))
        {
            sqlBuffer.append(" select trouble.country as city,");
        }

        sqlBuffer.append(" trouble.process_instance_id,trouble.id,trouble.theme,");
        sqlBuffer.append(" trouble.send_time,trouble.initiator,");
        sqlBuffer.append(" decode(sign(nvl(trouble.last_reply_time, sysdate) - trouble.end_time),1,1,-1,0,0) as over_time ");

        flag=true;
        sqlBuffer.append(" ,u.deptid ");
        sqlBuffer.append(" from pnr_act_trouble_ticket_main trouble left join taw_system_user u on trouble.initiator = u.userid ");
       
        if("person".equals(level))
        {
            sqlBuffer.append(" ,pnr_act_trouble_person_relate relate,act_ru_task ats");
            sqlBuffer.append(" where trouble.process_instance_id = relate.process_instance_id");
            sqlBuffer.append(" and trouble.process_instance_id = ats.proc_inst_id_");

        }else {
            sqlBuffer.append("  ,act_ru_task ats");
            sqlBuffer.append(" where ats.proc_inst_id_ = trouble.process_instance_id");
        }
        
        if(null!=subType && !"".equals(subType)){
            sqlBuffer.append(" and trouble.sub_type ='").append(subType).append("'");
        }


        if(null!=beginTime && !"".equals(beginTime))
        {
            sqlBuffer.append(" and trouble.create_time >= to_date('").append(beginTime).append("','yyyy-MM-dd HH24:mi:ss')");
        }

        if(null!=endTime && !"".equals(endTime)){
            sqlBuffer.append(" and trouble.create_time <= to_date('").append(endTime).append("','yyyy-MM-dd HH24:mi:ss')");
        }

        if(null!=city && !"".equals(city)){
            if("city".equals(level))
            {
                if(city.equals("28"))
                {
                    sqlBuffer.append(" and trouble.city like'").append(city).append("%'");
                }else
                {
                    sqlBuffer.append(" and trouble.city='").append(city).append("'");

                }

            }
            else if("country".equals(level))
            {
                sqlBuffer.append(" and trouble.country='").append(city).append("'");
            }else if("person".equals(level))
            {
                sqlBuffer.append(" and trouble.country='").append(city).append("'");
                sqlBuffer.append(" and relate.person='").append(person).append("'");
            }

        }
        sqlBuffer.append(" order by trouble.create_time desc");
        System.out.println("PnrStatisticsDaoJDBC--TroubleStatisticsPartnerIndexDrill3-925-"+sqlBuffer);
        List<Map> list = this.getJdbcTemplate().queryForList(sqlBuffer.toString());
        for (Map map : list) {
            WorkOrderStatisticsDrillModel workOrderStatisticsModel = new WorkOrderStatisticsDrillModel();
            workOrderStatisticsModel.setCity(map.get("CITY").toString());
            workOrderStatisticsModel.setProcessInstanceId(map.get("PROCESS_INSTANCE_ID").toString());
            workOrderStatisticsModel.setThemeId(map.get("ID").toString());
            workOrderStatisticsModel.setTheme(map.get("THEME").toString());
            try {
                workOrderStatisticsModel.setCreateDate(dateFormat.parse(map.get("SEND_TIME").toString()));
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            workOrderStatisticsModel.setInitiator(map.get("INITIATOR").toString());
            workOrderStatisticsModel.setIsOverTime(Integer.parseInt(map.get("OVER_TIME").toString()));
            workOrderStatisticsModel.setFlag(1);
            if(flag)
            {
            	if(map.get("DEPTID")==null){
            		
            		workOrderStatisticsModel.setDeptId("");
            	}else{
            		
            		workOrderStatisticsModel.setDeptId(map.get("DEPTID").toString());
            	}
            	
            }
            r.add(workOrderStatisticsModel);
        }
        return r;
    }

    @Override
	public List<WorkOrderStatisticsDrillModel> TaskStatisticsPartnerIndexDrill(
			String city, String beginTime, String endTime, String subType,String level ,String person) {
		// TODO Auto-generated method stub
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		 List<WorkOrderStatisticsDrillModel> r = new ArrayList<WorkOrderStatisticsDrillModel>();
	        StringBuffer sqlBuffer = new StringBuffer();
	        Boolean flag = false;
	        if("city".equals(level))
	        {
	        	sqlBuffer.append(" select task.city,");
	       
	        }else if("country".equals(level)||"person".equals(level))
	        {
	        	sqlBuffer.append(" select task.country as city,");
	        }
	        sqlBuffer.append(" task.process_instance_id,task.id,task.theme,");
	        sqlBuffer.append(" task.create_time,task.initiator,");
	        sqlBuffer.append(" decode(sign(nvl(task.last_reply_time, sysdate) - task.end_time),1,1,-1,0,0) as over_time ");
	        flag=true;
	        sqlBuffer.append(",u.deptid ");
	        sqlBuffer.append(" from pnr_act_task_ticket_main task left join taw_system_user u on task.initiator = u.userid ");
	        if("person".equals(level))
	        {
	        	sqlBuffer.append(" ,pnr_act_task_person_relate relate ");
	        	sqlBuffer.append(" where task.process_instance_id = relate.process_instance_id");
	        	
	        }else {
	        	sqlBuffer.append("  where 1=1 ");
			}
	        sqlBuffer.append(" and task.state=5 ");
	        if(null!=subType && !"".equals(subType)){
	        	sqlBuffer.append(" and task.sub_type ='").append(subType).append("'");
	        }
	        	        
	        
	        if(null!=beginTime && !"".equals(beginTime))
	        {
	        	sqlBuffer.append(" and task.create_time >= to_date('").append(beginTime).append("','yyyy-MM-dd HH24:mi:ss')");
	        }
	        
	        if(null!=endTime && !"".equals(endTime)){
	        	sqlBuffer.append(" and task.create_time <= to_date('").append(endTime).append("','yyyy-MM-dd HH24:mi:ss')");
	        }

	        if(null!=city && !"".equals(city)){
	        	if("city".equals(level))
	        	{
	        		if(city.equals("28"))
	        		{
	        			sqlBuffer.append(" and task.city like'").append(city).append("%'");
	        		}else
	        		{
	        			sqlBuffer.append(" and task.city='").append(city).append("'");
	        		}
	        		
	        	}
	        	else if("country".equals(level))
	        	{
	        		sqlBuffer.append(" and task.country='").append(city).append("'");
	        	}else if("person".equals(level))
	        	{
	        		sqlBuffer.append(" and task.country='").append(city).append("'");
	        		sqlBuffer.append(" and relate.person='").append(person).append("'");
	        	}
	        }
	        sqlBuffer.append(" order by task.create_time desc");

	        List<Map> list = this.getJdbcTemplate().queryForList(sqlBuffer.toString());
	        for (Map map : list) {
	        	WorkOrderStatisticsDrillModel workOrderStatisticsModel = new WorkOrderStatisticsDrillModel();
	            workOrderStatisticsModel.setCity(map.get("CITY").toString());
	            workOrderStatisticsModel.setProcessInstanceId(map.get("PROCESS_INSTANCE_ID").toString());
	            workOrderStatisticsModel.setThemeId(map.get("ID").toString());
	            workOrderStatisticsModel.setTheme(map.get("THEME").toString());
	            try {
					workOrderStatisticsModel.setCreateDate(dateFormat.parse(map.get("CREATE_TIME").toString()));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            workOrderStatisticsModel.setInitiator(map.get("INITIATOR").toString());
	            workOrderStatisticsModel.setIsOverTime(Integer.parseInt(map.get("OVER_TIME").toString()));
	            workOrderStatisticsModel.setFlag(2);
	            if(flag){
	            	if(map.get("DEPTID")==null){
	            		workOrderStatisticsModel.setDeptId("");	            	
	            		
	            	}else{
	            		workOrderStatisticsModel.setDeptId(map.get("DEPTID").toString());	            	
	            	}
	            }
	            r.add(workOrderStatisticsModel);
	        }
	        return r;
	}
    @Override
    public List<WorkOrderStatisticsDrillModel> TaskStatisticsPartnerIndexDrill3(
            String city, String beginTime, String endTime, String subType,String level ,String person) {
        // TODO Auto-generated method stub 在途统计
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Boolean flag = false;
        List<WorkOrderStatisticsDrillModel> r = new ArrayList<WorkOrderStatisticsDrillModel>();
        StringBuffer sqlBuffer = new StringBuffer();

        if("city".equals(level))
        {
            sqlBuffer.append(" select task.city,");

        }else if("country".equals(level)||"person".equals(level))
        {
            sqlBuffer.append(" select task.country as city,");
        }
        sqlBuffer.append(" task.process_instance_id,task.id,task.theme,");
        sqlBuffer.append(" task.create_time,task.initiator,");
        sqlBuffer.append(" decode(sign(nvl(task.last_reply_time, sysdate) - task.end_time),1,1,-1,0,0) as over_time ");
        flag=true;
        sqlBuffer.append(" ,u.deptid");
        sqlBuffer.append(" from pnr_act_task_ticket_main task left join taw_system_user u on task.initiator = u.userid ");
        if("person".equals(level))
        {
            sqlBuffer.append(" ,pnr_act_task_person_relate relate,act_ru_task ats");
            sqlBuffer.append(" where task.process_instance_id = relate.process_instance_id");
            sqlBuffer.append(" and task.process_instance_id = ats.proc_inst_id_");

        }else {
        	sqlBuffer.append(" ,act_ru_task ats");
            sqlBuffer.append(" where task.process_instance_id = ats.proc_inst_id_");
        }
        sqlBuffer.append(" and ats.task_def_key_ != 'extractWorkOrder'");
        if(null!=subType && !"".equals(subType)){
            sqlBuffer.append(" and task.sub_type ='").append(subType).append("'");
        }


        if(null!=beginTime && !"".equals(beginTime))
        {
            sqlBuffer.append(" and task.create_time >= to_date('").append(beginTime).append("','yyyy-MM-dd HH24:mi:ss')");
        }

        if(null!=endTime && !"".equals(endTime)){
            sqlBuffer.append(" and task.create_time <= to_date('").append(endTime).append("','yyyy-MM-dd HH24:mi:ss')");
        }

        if(null!=city && !"".equals(city)){
            if("city".equals(level))
            {
                if(city.equals("28"))
                {
                    sqlBuffer.append(" and task.city like'").append(city).append("%'");
                }else
                {
                    sqlBuffer.append(" and task.city='").append(city).append("'");
                }

            }
            else if("country".equals(level))
            {
                sqlBuffer.append(" and task.country='").append(city).append("'");
            }else if("person".equals(level))
            {
                sqlBuffer.append(" and task.country='").append(city).append("'");
                sqlBuffer.append(" and relate.person='").append(person).append("'");
            }
        }
        sqlBuffer.append(" order by task.create_time desc");
        List<Map> list = this.getJdbcTemplate().queryForList(sqlBuffer.toString());
        for (Map map : list) {
            WorkOrderStatisticsDrillModel workOrderStatisticsModel = new WorkOrderStatisticsDrillModel();
            workOrderStatisticsModel.setCity(map.get("CITY").toString());
            workOrderStatisticsModel.setProcessInstanceId(map.get("PROCESS_INSTANCE_ID").toString());
            workOrderStatisticsModel.setThemeId(map.get("ID").toString());
            workOrderStatisticsModel.setTheme(map.get("THEME").toString());
            try {
                workOrderStatisticsModel.setCreateDate(dateFormat.parse(map.get("CREATE_TIME").toString()));
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            workOrderStatisticsModel.setInitiator(map.get("INITIATOR").toString());
            workOrderStatisticsModel.setIsOverTime(Integer.parseInt(map.get("OVER_TIME").toString()));
            workOrderStatisticsModel.setFlag(2);
            if(flag)
            {
            	if(map.get("DEPTID")==null){
            		workOrderStatisticsModel.setDeptId("");
            		
            	}else{
            		
            		workOrderStatisticsModel.setDeptId(map.get("DEPTID").toString());
            	}
            	
            }
            r.add(workOrderStatisticsModel);
        }
        return r;
    }
	@Override
	public List<WorkOrderStatisticsModel2> workOrderStatistics2(String city,
			String beginTime, String endTime) {
		// TODO Auto-generated method stub 统计点击地市时，该地市下所有县级结构的工单
		List<WorkOrderStatisticsModel2> r = new ArrayList<WorkOrderStatisticsModel2>();
        String whereSql = " where create_time >= to_date('" + beginTime +
                "','yyyy-MM-dd HH24:mi:ss') and create_time<=  to_date('" + endTime + "','yyyy-MM-dd HH24:mi:ss') and state=5 and city='"+city+"' ";
        String faultSumNumSql = "select country,count(id)as faultSumNum from pnr_act_trouble_ticket_main" + whereSql + " and sub_Type='101220101'";
        String faultOvertimeNumSql = "select country,sum(decode(sign(nvl(last_reply_time,sysdate)-end_time),1,1,-1,0,0))as faultOvertimeNum from pnr_act_trouble_ticket_main" + whereSql + " and sub_Type='101220101'";
        String generateElectricitySumNumSql = "select country,count(id)as generateElectricitySumNum from pnr_act_trouble_ticket_main" + whereSql + " and sub_Type='101220102'";
        String generateElectricityOvertimeNumSql = "select country,sum(decode(sign(nvl(last_reply_time,sysdate)-end_time),1,1,-1,0,0))as generateElectricityOvertimeNum from pnr_act_trouble_ticket_main" + whereSql + " and sub_Type='101220102'";
        String businessOpenedSumNumSql = "select country,count(id)as businessOpenedSumNum from pnr_act_task_ticket_main" + whereSql + " and sub_Type='101110101'";
        String businessOpenedOvertimeNumSql = "select country,sum(decode(sign(nvl(last_reply_time,sysdate)-end_time),1,1,-1,0,0))as businessOpenedOvertimeNum from pnr_act_task_ticket_main" + whereSql + " and sub_Type='101110101'";
        String networkCutoverSumNumSql = "select country,count(id)as networkCutoverSumNum from pnr_act_task_ticket_main" + whereSql + " and sub_Type='101110102'";
        String networkCutoverOvertimeNumSql = "select country,sum(decode(sign(nvl(last_reply_time,sysdate)-end_time),1,1,-1,0,0))as networkCutoverOvertimeNum from pnr_act_task_ticket_main" + whereSql + " and sub_Type='101110102'";
        String operationalCoordinationSumNumSql = "select country,count(id)as operationalCSumNum from pnr_act_task_ticket_main" + whereSql + " and sub_Type='101110103'";
        String operationalCoordinationOvertimeNumSql = "select country,sum(decode(sign(nvl(last_reply_time,sysdate)-end_time),1,1,-1,0,0))as operationalCOvertimeNum from pnr_act_task_ticket_main" + whereSql + " and sub_Type='101110103'";
        String inspectionSumNumSql = "select country,count(id)as inspectionSumNum from pnr_act_task_ticket_main" + whereSql + " and sub_Type='101110105'";
        String inspectionOvertimeNumSql = "select country,sum(decode(sign(nvl(last_reply_time,sysdate)-end_time),1,1,-1,0,0))as inspectionOvertimeNum from pnr_act_task_ticket_main" + whereSql + " and sub_Type='101110105'";
        String projectAcceptanceSumNumSql = "select country,count(id)as projectAcceptanceSumNum from pnr_act_task_ticket_main" + whereSql + " and sub_Type='101110106'";
        String projectAcceptanceOvertimeNumSql = "select country,sum(decode(sign(nvl(last_reply_time,sysdate)-end_time),1,1,-1,0,0))as projectAcceptanceOvertimeNum from pnr_act_task_ticket_main" + whereSql + " and sub_Type='101110106'";
        String paymentSumNumSql = "select country,count(id)as paymentSumNum from pnr_act_task_ticket_main" + whereSql + " and sub_Type='101110104'";
        String paymentOvertimeNumSql = "select country,sum(decode(sign(nvl(last_reply_time,sysdate)-end_time),1,1,-1,0,0))as paymentOvertimeNum from pnr_act_task_ticket_main" + whereSql + " and sub_Type='101110104'";
        String otherSumNumSql = "select country,count(id)as otherSumNum from pnr_act_task_ticket_main" + whereSql + " and sub_Type='101110107'";
        String otherOvertimeNumSql = "select country,sum(decode(sign(nvl(last_reply_time,sysdate)-end_time),1,1,-1,0,0))as otherOvertimeNum from pnr_act_task_ticket_main" + whereSql + " and sub_Type='101110107'";
        String endSelectSql = "group by country";
        String sql = "select area.areaid,area.areaname," +
                "a.faultSumNum,b.faultOvertimeNum," +
                "c.generateElectricitySumNum,d.generateElectricityOvertimeNum, " +
                "e.businessOpenedSumNum,f.businessOpenedOvertimeNum," +
                "g.networkCutoverSumNum,h.networkCutoverOvertimeNum," +
                "i.operationalCSumNum,j.operationalCOvertimeNum," +
                "k.inspectionSumNum,l.inspectionOvertimeNum," +
                "m.projectAcceptanceSumNum,n.projectAcceptanceOvertimeNum, " +
                "p1.paymentSumNum,p2.paymentOvertimeNum, " +
                "q1.otherSumNum,q2.otherOvertimeNum " +
                " from " +
                " (select areaid,areaname from taw_system_area where parentareaid="+city+") area left join" +
                " (" + faultSumNumSql +  endSelectSql + ") a on area.areaid=a.country left join" +
                " (" + faultOvertimeNumSql + endSelectSql + ") b on area.areaid=b.country left join" +
                " (" + generateElectricitySumNumSql +  endSelectSql + ") c on area.areaid=c.country left join" +
                " (" + generateElectricityOvertimeNumSql + endSelectSql + ") d on area.areaid=d.country left join" +
                " (" + businessOpenedSumNumSql +  endSelectSql + ") e on area.areaid=e.country left join" +
                " (" + businessOpenedOvertimeNumSql + endSelectSql + ") f on area.areaid=f.country left join" +
                " (" + networkCutoverSumNumSql +  endSelectSql + ") g on area.areaid=g.country left join" +
                " (" + networkCutoverOvertimeNumSql + endSelectSql + ") h on area.areaid=h.country left join" +
                " (" + operationalCoordinationSumNumSql +  endSelectSql + ") i on area.areaid=i.country left join" +
                " (" + operationalCoordinationOvertimeNumSql + endSelectSql + ") j on area.areaid=j.country left join" +
                " (" + inspectionSumNumSql +  endSelectSql + ") k on area.areaid=k.country left join" +
                " (" + inspectionOvertimeNumSql + endSelectSql + ") l on area.areaid=l.country left join" +
                " (" + projectAcceptanceSumNumSql +  endSelectSql + ") m on area.areaid=m.country left join" +
                " (" + projectAcceptanceOvertimeNumSql + endSelectSql + ") n on area.areaid=n.country left join" +
		        " (" + paymentSumNumSql +  endSelectSql + ") p1 on area.areaid=p1.country left join" +
		        " (" + paymentOvertimeNumSql + endSelectSql + ") p2 on area.areaid=p2.country left join"+
		        " (" + otherSumNumSql +  endSelectSql + ") q1 on area.areaid=q1.country left join" +
		        " (" + otherOvertimeNumSql + endSelectSql + ")q2 on area.areaid=q2.country";
//		System.out.println("--------是对方受到-------"+sql);
        List<Map> list = this.getJdbcTemplate().queryForList(sql);
        for (Map map : list) {
            WorkOrderStatisticsModel2 workOrderStatisticsModel2 = new WorkOrderStatisticsModel2();
            workOrderStatisticsModel2.setCity(map.get("areaid").toString());
            workOrderStatisticsModel2.setCityName(map.get("areaname").toString());
            if (map.get("faultSumNum") == null) {
                workOrderStatisticsModel2.setFaultSumNum(0);
            } else {
                workOrderStatisticsModel2.setFaultSumNum(Integer.parseInt(map
                        .get("faultSumNum").toString()));
            }
            if (map.get("faultOvertimeNum") == null) {
                workOrderStatisticsModel2.setFaultOvertimeNum(0);
            } else {
                workOrderStatisticsModel2.setFaultOvertimeNum(Integer.parseInt(map
                        .get("faultOvertimeNum").toString()));
            }
            if (workOrderStatisticsModel2.getFaultSumNum() != 0 && workOrderStatisticsModel2.getFaultOvertimeNum() != 0) {
                workOrderStatisticsModel2.setFaultOvertimeRate(this.calculateThePercentage(new Double(workOrderStatisticsModel2.getFaultOvertimeNum()), new Double(workOrderStatisticsModel2.getFaultSumNum())));
            } else {
                workOrderStatisticsModel2.setFaultOvertimeRate("0%");
            }
            if (map.get("generateElectricitySumNum") == null) {
                workOrderStatisticsModel2.setGenerateElectricitySumNum(0);
            } else {
                workOrderStatisticsModel2.setGenerateElectricitySumNum(Integer.parseInt(map
                        .get("generateElectricitySumNum").toString()));
            }
            if (map.get("generateElectricityOvertimeNum") == null) {
                workOrderStatisticsModel2.setGenerateElectricityOvertimeNum(0);
            } else {
                workOrderStatisticsModel2.setGenerateElectricityOvertimeNum(Integer.parseInt(map
                        .get("generateElectricityOvertimeNum").toString()));
            }
            if (workOrderStatisticsModel2.getGenerateElectricitySumNum() != 0 && workOrderStatisticsModel2.getGenerateElectricityOvertimeNum() != 0) {
                workOrderStatisticsModel2.setGenerateElectricityOvertimeRate(this.calculateThePercentage(new Double(workOrderStatisticsModel2.getGenerateElectricityOvertimeNum()), new Double(workOrderStatisticsModel2.getGenerateElectricitySumNum())));
            } else {
                workOrderStatisticsModel2.setGenerateElectricityOvertimeRate("0%");
            }
            if (map.get("businessOpenedSumNum") == null) {
                workOrderStatisticsModel2.setBusinessOpenedSumNum(0);
            } else {
                workOrderStatisticsModel2.setBusinessOpenedSumNum(Integer.parseInt(map
                        .get("businessOpenedSumNum").toString()));
            }
            if (map.get("businessOpenedOvertimeNum") == null) {
                workOrderStatisticsModel2.setBusinessOpenedOvertimeNum(0);
            } else {
                workOrderStatisticsModel2.setBusinessOpenedOvertimeNum(Integer.parseInt(map
                        .get("businessOpenedOvertimeNum").toString()));
            }
            if (workOrderStatisticsModel2.getBusinessOpenedSumNum() != 0 && workOrderStatisticsModel2.getBusinessOpenedOvertimeNum() != 0) {
                workOrderStatisticsModel2.setBusinessOpenedOvertimeRate(this.calculateThePercentage(new Double(workOrderStatisticsModel2.getBusinessOpenedOvertimeNum()), new Double(workOrderStatisticsModel2.getBusinessOpenedSumNum())));
            } else {
                workOrderStatisticsModel2.setBusinessOpenedOvertimeRate("0%");
            }
            if (map.get("networkCutoverSumNum") == null) {
                workOrderStatisticsModel2.setNetworkCutoverSumNum(0);
            } else {
                workOrderStatisticsModel2.setNetworkCutoverSumNum(Integer.parseInt(map
                        .get("networkCutoverSumNum").toString()));
            }
            if (map.get("networkCutoverOvertimeNum") == null) {
                workOrderStatisticsModel2.setNetworkCutoverOvertimeNum(0);
            } else {
                workOrderStatisticsModel2.setNetworkCutoverOvertimeNum(Integer.parseInt(map
                        .get("networkCutoverOvertimeNum").toString()));
            }
            if (workOrderStatisticsModel2.getNetworkCutoverSumNum() != 0 && workOrderStatisticsModel2.getNetworkCutoverOvertimeNum() != 0) {
                workOrderStatisticsModel2.setNetworkCutoverOvertimeRate(this.calculateThePercentage(new Double(workOrderStatisticsModel2.getNetworkCutoverOvertimeNum()), new Double(workOrderStatisticsModel2.getNetworkCutoverSumNum())));
            } else {
                workOrderStatisticsModel2.setNetworkCutoverOvertimeRate("0%");
            }
            if (map.get("operationalCSumNum") == null) {
                workOrderStatisticsModel2.setOperationalCoordinationSumNum(0);
            } else {
                workOrderStatisticsModel2.setOperationalCoordinationSumNum(Integer.parseInt(map
                        .get("operationalCSumNum").toString()));
            }
            if (map.get("operationalCOvertimeNum") == null) {
                workOrderStatisticsModel2.setOperationalCoordinationOvertimeNum(0);
            } else {
                workOrderStatisticsModel2.setOperationalCoordinationOvertimeNum(Integer.parseInt(map
                        .get("operationalCOvertimeNum").toString()));
            }
            if (workOrderStatisticsModel2.getOperationalCoordinationSumNum() != 0 && workOrderStatisticsModel2.getOperationalCoordinationOvertimeNum() != 0) {
                workOrderStatisticsModel2.setOperationalCoordinationOvertimeRate(this.calculateThePercentage(new Double(workOrderStatisticsModel2.getOperationalCoordinationOvertimeNum()), new Double(workOrderStatisticsModel2.getOperationalCoordinationSumNum())));
            } else {
                workOrderStatisticsModel2.setOperationalCoordinationOvertimeRate("0%");
            }
            if (map.get("inspectionSumNum") == null) {
                workOrderStatisticsModel2.setInspectionSumNum(0);
            } else {
                workOrderStatisticsModel2.setInspectionSumNum(Integer.parseInt(map
                        .get("inspectionSumNum").toString()));
            }
            if (map.get("inspectionOvertimeNum") == null) {
                workOrderStatisticsModel2.setInspectionOvertimeNum(0);
            } else {
                workOrderStatisticsModel2.setInspectionOvertimeNum(Integer.parseInt(map
                        .get("inspectionOvertimeNum").toString()));
            }
            if (workOrderStatisticsModel2.getInspectionSumNum() != 0 && workOrderStatisticsModel2.getInspectionOvertimeNum() != 0) {
                workOrderStatisticsModel2.setInspectionOvertimeRate(this.calculateThePercentage(new Double(workOrderStatisticsModel2.getInspectionOvertimeNum()), new Double(workOrderStatisticsModel2.getInspectionSumNum())));
            } else {
                workOrderStatisticsModel2.setInspectionOvertimeRate("0%");
            }
            if (map.get("projectAcceptanceSumNum") == null) {
                workOrderStatisticsModel2.setProjectAcceptanceSumNum(0);
            } else {
                workOrderStatisticsModel2.setProjectAcceptanceSumNum(Integer.parseInt(map
                        .get("projectAcceptanceSumNum").toString()));
            }
            if (map.get("projectAcceptanceOvertimeNum") == null) {
                workOrderStatisticsModel2.setProjectAcceptanceOvertimeNum(0);
            } else {
                workOrderStatisticsModel2.setProjectAcceptanceOvertimeNum(Integer.parseInt(map
                        .get("projectAcceptanceOvertimeNum").toString()));
            }
            if (workOrderStatisticsModel2.getProjectAcceptanceSumNum() != 0 && workOrderStatisticsModel2.getProjectAcceptanceOvertimeNum() != 0) {
                workOrderStatisticsModel2.setProjectAcceptanceOvertimeRate(this.calculateThePercentage(new Double(workOrderStatisticsModel2.getProjectAcceptanceOvertimeNum()), new Double(workOrderStatisticsModel2.getProjectAcceptanceSumNum())));
            } else {
                workOrderStatisticsModel2.setProjectAcceptanceOvertimeRate("0%");
            }
            if (map.get("paymentSumNum") == null) {
            	workOrderStatisticsModel2.setPaymentSumNum(0);
            } else {
            	workOrderStatisticsModel2.setPaymentSumNum(Integer.parseInt(map
            			.get("paymentSumNum").toString()));
            }
            if (map.get("paymentOvertimeNum") == null) {
            	workOrderStatisticsModel2.setPaymentOvertimeNum(0);
            } else {
            	workOrderStatisticsModel2.setPaymentOvertimeNum(Integer.parseInt(map
            			.get("paymentOvertimeNum").toString()));
            }
            if (workOrderStatisticsModel2.getPaymentSumNum() != 0 && workOrderStatisticsModel2.getPaymentOvertimeNum() != 0) {
            	workOrderStatisticsModel2.setPaymentOvertimeRate(this.calculateThePercentage(new Double(workOrderStatisticsModel2.getPaymentOvertimeNum()), new Double(workOrderStatisticsModel2.getPaymentSumNum())));
            } else {
            	workOrderStatisticsModel2.setPaymentOvertimeRate("0%");
            }
            if (map.get("otherSumNum") == null) {
            	workOrderStatisticsModel2.setOtherSumNum(0);
            } else {
            	workOrderStatisticsModel2.setOtherSumNum(Integer.parseInt(map
            			.get("otherSumNum").toString()));
            }
            if (map.get("otherOvertimeNum") == null) {
            	workOrderStatisticsModel2.setOtherOvertimeNum(0);
            } else {
            	workOrderStatisticsModel2.setOtherOvertimeNum(Integer.parseInt(map
            			.get("otherOvertimeNum").toString()));
            }
            if (workOrderStatisticsModel2.getOtherSumNum() != 0 && workOrderStatisticsModel2.getOtherOvertimeNum() != 0) {
            	workOrderStatisticsModel2.setOtherOvertimeRate(this.calculateThePercentage(new Double(workOrderStatisticsModel2.getOtherOvertimeNum()), new Double(workOrderStatisticsModel2.getOtherSumNum())));
            } else {
            	workOrderStatisticsModel2.setOtherOvertimeRate("0%");
            }
            r.add(workOrderStatisticsModel2);
        }
        return r;
	}
	@Override
	public List<WorkOrderStatisticsModel2> workOrderStatistics3(String city,
			String beginTime, String endTime) {
		// TODO Auto-generated method stub 统计点击地市时，该地市下所有县级结构的工单
		List<WorkOrderStatisticsModel2> r = new ArrayList<WorkOrderStatisticsModel2>();
		String whereSql = " ,act_ru_task where proc_inst_id_=process_instance_id and create_time >= to_date('" + beginTime +
		"','yyyy-MM-dd HH24:mi:ss') and create_time<=  to_date('" + endTime + "','yyyy-MM-dd HH24:mi:ss') and city='"+city+"' and task_def_key_!='extractWorkOrder'";
		String faultSumNumSql = "select country,count(id)as faultSumNum from pnr_act_trouble_ticket_main" + whereSql + " and sub_Type='101220101'";
		String faultOvertimeNumSql = "select country,sum(decode(sign(nvl(last_reply_time,sysdate)-end_time),1,1,-1,0,0))as faultOvertimeNum from pnr_act_trouble_ticket_main" + whereSql + " and sub_Type='101220101'";
		String generateElectricitySumNumSql = "select country,count(id)as generateElectricitySumNum from pnr_act_trouble_ticket_main" + whereSql + " and sub_Type='101220102'";
		String generateElectricityOvertimeNumSql = "select country,sum(decode(sign(nvl(last_reply_time,sysdate)-end_time),1,1,-1,0,0))as generateElectricityOvertimeNum from pnr_act_trouble_ticket_main" + whereSql + " and sub_Type='101220102'";
		String businessOpenedSumNumSql = "select country,count(id)as businessOpenedSumNum from pnr_act_task_ticket_main" + whereSql + " and sub_Type='101110101'";
		String businessOpenedOvertimeNumSql = "select country,sum(decode(sign(nvl(last_reply_time,sysdate)-end_time),1,1,-1,0,0))as businessOpenedOvertimeNum from pnr_act_task_ticket_main" + whereSql + " and sub_Type='101110101'";
		String networkCutoverSumNumSql = "select country,count(id)as networkCutoverSumNum from pnr_act_task_ticket_main" + whereSql + " and sub_Type='101110102'";
		String networkCutoverOvertimeNumSql = "select country,sum(decode(sign(nvl(last_reply_time,sysdate)-end_time),1,1,-1,0,0))as networkCutoverOvertimeNum from pnr_act_task_ticket_main" + whereSql + " and sub_Type='101110102'";
		String operationalCoordinationSumNumSql = "select country,count(id)as operationalCSumNum from pnr_act_task_ticket_main" + whereSql + " and sub_Type='101110103'";
		String operationalCoordinationOvertimeNumSql = "select country,sum(decode(sign(nvl(last_reply_time,sysdate)-end_time),1,1,-1,0,0))as operationalCOvertimeNum from pnr_act_task_ticket_main" + whereSql + " and sub_Type='101110103'";
		String inspectionSumNumSql = "select country,count(id)as inspectionSumNum from pnr_act_task_ticket_main" + whereSql + " and sub_Type='101110105'";
		String inspectionOvertimeNumSql = "select country,sum(decode(sign(nvl(last_reply_time,sysdate)-end_time),1,1,-1,0,0))as inspectionOvertimeNum from pnr_act_task_ticket_main" + whereSql + " and sub_Type='101110105'";
		String projectAcceptanceSumNumSql = "select country,count(id)as projectAcceptanceSumNum from pnr_act_task_ticket_main" + whereSql + " and sub_Type='101110106'";
		String projectAcceptanceOvertimeNumSql = "select country,sum(decode(sign(nvl(last_reply_time,sysdate)-end_time),1,1,-1,0,0))as projectAcceptanceOvertimeNum from pnr_act_task_ticket_main" + whereSql + " and sub_Type='101110106'";
		String paymentSumNumSql = "select country,count(id)as paymentSumNum from pnr_act_task_ticket_main" + whereSql + " and sub_Type='101110104'";
		String paymentOvertimeNumSql = "select country,sum(decode(sign(nvl(last_reply_time,sysdate)-end_time),1,1,-1,0,0))as paymentOvertimeNum from pnr_act_task_ticket_main" + whereSql + " and sub_Type='101110104'";
		String otherSumNumSql = "select country,count(id)as otherSumNum from pnr_act_task_ticket_main" + whereSql + " and sub_Type='101110107'";
		String otherOvertimeNumSql = "select country,sum(decode(sign(nvl(last_reply_time,sysdate)-end_time),1,1,-1,0,0))as otherOvertimeNum from pnr_act_task_ticket_main" + whereSql + " and sub_Type='101110107'";
		String endSelectSql = "group by country";
		String sql = "select area.areaid,area.areaname," +
		"a.faultSumNum,b.faultOvertimeNum," +
		"c.generateElectricitySumNum,d.generateElectricityOvertimeNum, " +
		"e.businessOpenedSumNum,f.businessOpenedOvertimeNum," +
		"g.networkCutoverSumNum,h.networkCutoverOvertimeNum," +
		"i.operationalCSumNum,j.operationalCOvertimeNum," +
		"k.inspectionSumNum,l.inspectionOvertimeNum," +
		"m.projectAcceptanceSumNum,n.projectAcceptanceOvertimeNum, " +
		"p1.paymentSumNum,p2.paymentOvertimeNum, " +
		"q1.otherSumNum,q2.otherOvertimeNum " +
		" from " +
		" (select areaid,areaname from taw_system_area where parentareaid="+city+") area left join" +
		" (" + faultSumNumSql +  endSelectSql + ") a on area.areaid=a.country left join" +
		" (" + faultOvertimeNumSql + endSelectSql + ") b on area.areaid=b.country left join" +
		" (" + generateElectricitySumNumSql +  endSelectSql + ") c on area.areaid=c.country left join" +
		" (" + generateElectricityOvertimeNumSql + endSelectSql + ") d on area.areaid=d.country left join" +
		" (" + businessOpenedSumNumSql +  endSelectSql + ") e on area.areaid=e.country left join" +
		" (" + businessOpenedOvertimeNumSql + endSelectSql + ") f on area.areaid=f.country left join" +
		" (" + networkCutoverSumNumSql +  endSelectSql + ") g on area.areaid=g.country left join" +
		" (" + networkCutoverOvertimeNumSql + endSelectSql + ") h on area.areaid=h.country left join" +
		" (" + operationalCoordinationSumNumSql +  endSelectSql + ") i on area.areaid=i.country left join" +
		" (" + operationalCoordinationOvertimeNumSql + endSelectSql + ") j on area.areaid=j.country left join" +
		" (" + inspectionSumNumSql +  endSelectSql + ") k on area.areaid=k.country left join" +
		" (" + inspectionOvertimeNumSql + endSelectSql + ") l on area.areaid=l.country left join" +
		" (" + projectAcceptanceSumNumSql +  endSelectSql + ") m on area.areaid=m.country left join" +
		" (" + projectAcceptanceOvertimeNumSql + endSelectSql + ") n on area.areaid=n.country left join" +
		" (" + paymentSumNumSql +  endSelectSql + ") p1 on area.areaid=p1.country left join" +
		" (" + paymentOvertimeNumSql + endSelectSql + ") p2 on area.areaid=p2.country left join"+
		" (" + otherSumNumSql +  endSelectSql + ") q1 on area.areaid=q1.country left join" +
		" (" + otherOvertimeNumSql + endSelectSql + ")q2 on area.areaid=q2.country";
		List<Map> list = this.getJdbcTemplate().queryForList(sql);
		for (Map map : list) {
			WorkOrderStatisticsModel2 workOrderStatisticsModel2 = new WorkOrderStatisticsModel2();
			workOrderStatisticsModel2.setCity(map.get("areaid").toString());
			workOrderStatisticsModel2.setCityName(map.get("areaname").toString());
			if (map.get("faultSumNum") == null) {
				workOrderStatisticsModel2.setFaultSumNum(0);
			} else {
				workOrderStatisticsModel2.setFaultSumNum(Integer.parseInt(map
						.get("faultSumNum").toString()));
			}
			if (map.get("faultOvertimeNum") == null) {
				workOrderStatisticsModel2.setFaultOvertimeNum(0);
			} else {
				workOrderStatisticsModel2.setFaultOvertimeNum(Integer.parseInt(map
						.get("faultOvertimeNum").toString()));
			}
			if (workOrderStatisticsModel2.getFaultSumNum() != 0 && workOrderStatisticsModel2.getFaultOvertimeNum() != 0) {
				workOrderStatisticsModel2.setFaultOvertimeRate(this.calculateThePercentage(new Double(workOrderStatisticsModel2.getFaultOvertimeNum()), new Double(workOrderStatisticsModel2.getFaultSumNum())));
			} else {
				workOrderStatisticsModel2.setFaultOvertimeRate("0%");
			}
			if (map.get("generateElectricitySumNum") == null) {
				workOrderStatisticsModel2.setGenerateElectricitySumNum(0);
			} else {
				workOrderStatisticsModel2.setGenerateElectricitySumNum(Integer.parseInt(map
						.get("generateElectricitySumNum").toString()));
			}
			if (map.get("generateElectricityOvertimeNum") == null) {
				workOrderStatisticsModel2.setGenerateElectricityOvertimeNum(0);
			} else {
				workOrderStatisticsModel2.setGenerateElectricityOvertimeNum(Integer.parseInt(map
						.get("generateElectricityOvertimeNum").toString()));
			}
			if (workOrderStatisticsModel2.getGenerateElectricitySumNum() != 0 && workOrderStatisticsModel2.getGenerateElectricityOvertimeNum() != 0) {
				workOrderStatisticsModel2.setGenerateElectricityOvertimeRate(this.calculateThePercentage(new Double(workOrderStatisticsModel2.getGenerateElectricityOvertimeNum()), new Double(workOrderStatisticsModel2.getGenerateElectricitySumNum())));
			} else {
				workOrderStatisticsModel2.setGenerateElectricityOvertimeRate("0%");
			}
			if (map.get("businessOpenedSumNum") == null) {
				workOrderStatisticsModel2.setBusinessOpenedSumNum(0);
			} else {
				workOrderStatisticsModel2.setBusinessOpenedSumNum(Integer.parseInt(map
						.get("businessOpenedSumNum").toString()));
			}
			if (map.get("businessOpenedOvertimeNum") == null) {
				workOrderStatisticsModel2.setBusinessOpenedOvertimeNum(0);
			} else {
				workOrderStatisticsModel2.setBusinessOpenedOvertimeNum(Integer.parseInt(map
						.get("businessOpenedOvertimeNum").toString()));
			}
			if (workOrderStatisticsModel2.getBusinessOpenedSumNum() != 0 && workOrderStatisticsModel2.getBusinessOpenedOvertimeNum() != 0) {
				workOrderStatisticsModel2.setBusinessOpenedOvertimeRate(this.calculateThePercentage(new Double(workOrderStatisticsModel2.getBusinessOpenedOvertimeNum()), new Double(workOrderStatisticsModel2.getBusinessOpenedSumNum())));
			} else {
				workOrderStatisticsModel2.setBusinessOpenedOvertimeRate("0%");
			}
			if (map.get("networkCutoverSumNum") == null) {
				workOrderStatisticsModel2.setNetworkCutoverSumNum(0);
			} else {
				workOrderStatisticsModel2.setNetworkCutoverSumNum(Integer.parseInt(map
						.get("networkCutoverSumNum").toString()));
			}
			if (map.get("networkCutoverOvertimeNum") == null) {
				workOrderStatisticsModel2.setNetworkCutoverOvertimeNum(0);
			} else {
				workOrderStatisticsModel2.setNetworkCutoverOvertimeNum(Integer.parseInt(map
						.get("networkCutoverOvertimeNum").toString()));
			}
			if (workOrderStatisticsModel2.getNetworkCutoverSumNum() != 0 && workOrderStatisticsModel2.getNetworkCutoverOvertimeNum() != 0) {
				workOrderStatisticsModel2.setNetworkCutoverOvertimeRate(this.calculateThePercentage(new Double(workOrderStatisticsModel2.getNetworkCutoverOvertimeNum()), new Double(workOrderStatisticsModel2.getNetworkCutoverSumNum())));
			} else {
				workOrderStatisticsModel2.setNetworkCutoverOvertimeRate("0%");
			}
			if (map.get("operationalCSumNum") == null) {
				workOrderStatisticsModel2.setOperationalCoordinationSumNum(0);
			} else {
				workOrderStatisticsModel2.setOperationalCoordinationSumNum(Integer.parseInt(map
						.get("operationalCSumNum").toString()));
			}
			if (map.get("operationalCOvertimeNum") == null) {
				workOrderStatisticsModel2.setOperationalCoordinationOvertimeNum(0);
			} else {
				workOrderStatisticsModel2.setOperationalCoordinationOvertimeNum(Integer.parseInt(map
						.get("operationalCOvertimeNum").toString()));
			}
			if (workOrderStatisticsModel2.getOperationalCoordinationSumNum() != 0 && workOrderStatisticsModel2.getOperationalCoordinationOvertimeNum() != 0) {
				workOrderStatisticsModel2.setOperationalCoordinationOvertimeRate(this.calculateThePercentage(new Double(workOrderStatisticsModel2.getOperationalCoordinationOvertimeNum()), new Double(workOrderStatisticsModel2.getOperationalCoordinationSumNum())));
			} else {
				workOrderStatisticsModel2.setOperationalCoordinationOvertimeRate("0%");
			}
			if (map.get("inspectionSumNum") == null) {
				workOrderStatisticsModel2.setInspectionSumNum(0);
			} else {
				workOrderStatisticsModel2.setInspectionSumNum(Integer.parseInt(map
						.get("inspectionSumNum").toString()));
			}
			if (map.get("inspectionOvertimeNum") == null) {
				workOrderStatisticsModel2.setInspectionOvertimeNum(0);
			} else {
				workOrderStatisticsModel2.setInspectionOvertimeNum(Integer.parseInt(map
						.get("inspectionOvertimeNum").toString()));
			}
			if (workOrderStatisticsModel2.getInspectionSumNum() != 0 && workOrderStatisticsModel2.getInspectionOvertimeNum() != 0) {
				workOrderStatisticsModel2.setInspectionOvertimeRate(this.calculateThePercentage(new Double(workOrderStatisticsModel2.getInspectionOvertimeNum()), new Double(workOrderStatisticsModel2.getInspectionSumNum())));
			} else {
				workOrderStatisticsModel2.setInspectionOvertimeRate("0%");
			}
			if (map.get("projectAcceptanceSumNum") == null) {
				workOrderStatisticsModel2.setProjectAcceptanceSumNum(0);
			} else {
				workOrderStatisticsModel2.setProjectAcceptanceSumNum(Integer.parseInt(map
						.get("projectAcceptanceSumNum").toString()));
			}
			if (map.get("projectAcceptanceOvertimeNum") == null) {
				workOrderStatisticsModel2.setProjectAcceptanceOvertimeNum(0);
			} else {
				workOrderStatisticsModel2.setProjectAcceptanceOvertimeNum(Integer.parseInt(map
						.get("projectAcceptanceOvertimeNum").toString()));
			}
			if (workOrderStatisticsModel2.getProjectAcceptanceSumNum() != 0 && workOrderStatisticsModel2.getProjectAcceptanceOvertimeNum() != 0) {
				workOrderStatisticsModel2.setProjectAcceptanceOvertimeRate(this.calculateThePercentage(new Double(workOrderStatisticsModel2.getProjectAcceptanceOvertimeNum()), new Double(workOrderStatisticsModel2.getProjectAcceptanceSumNum())));
			} else {
				workOrderStatisticsModel2.setProjectAcceptanceOvertimeRate("0%");
			}
			if (map.get("paymentSumNum") == null) {
				workOrderStatisticsModel2.setPaymentSumNum(0);
			} else {
				workOrderStatisticsModel2.setPaymentSumNum(Integer.parseInt(map
						.get("paymentSumNum").toString()));
			}
			if (map.get("paymentOvertimeNum") == null) {
				workOrderStatisticsModel2.setPaymentOvertimeNum(0);
			} else {
				workOrderStatisticsModel2.setPaymentOvertimeNum(Integer.parseInt(map
						.get("paymentOvertimeNum").toString()));
			}
			if (workOrderStatisticsModel2.getPaymentSumNum() != 0 && workOrderStatisticsModel2.getPaymentOvertimeNum() != 0) {
				workOrderStatisticsModel2.setPaymentOvertimeRate(this.calculateThePercentage(new Double(workOrderStatisticsModel2.getPaymentOvertimeNum()), new Double(workOrderStatisticsModel2.getPaymentSumNum())));
			} else {
				workOrderStatisticsModel2.setPaymentOvertimeRate("0%");
			}
			if (map.get("otherSumNum") == null) {
				workOrderStatisticsModel2.setOtherSumNum(0);
			} else {
				workOrderStatisticsModel2.setOtherSumNum(Integer.parseInt(map
						.get("otherSumNum").toString()));
			}
			if (map.get("otherOvertimeNum") == null) {
				workOrderStatisticsModel2.setOtherOvertimeNum(0);
			} else {
				workOrderStatisticsModel2.setOtherOvertimeNum(Integer.parseInt(map
						.get("otherOvertimeNum").toString()));
			}
			if (workOrderStatisticsModel2.getOtherSumNum() != 0 && workOrderStatisticsModel2.getOtherOvertimeNum() != 0) {
				workOrderStatisticsModel2.setOtherOvertimeRate(this.calculateThePercentage(new Double(workOrderStatisticsModel2.getOtherOvertimeNum()), new Double(workOrderStatisticsModel2.getOtherSumNum())));
			} else {
				workOrderStatisticsModel2.setOtherOvertimeRate("0%");
			}
			r.add(workOrderStatisticsModel2);
		}
		return r;
	}

	@Override
	public List<WorkOrderStatisticsModel2> workOrderStatistics2Person3(
			String country, String beginTime, String endTime) {
		// TODO Auto-generated method stub
		List<WorkOrderStatisticsModel2> r = new ArrayList<WorkOrderStatisticsModel2>();
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append(" select sm.person,sum(faultSumNum) faultSumNum, sum(faultOvertimeNum) faultOvertimeNum,");
		sqlBuffer.append(" sum(generateElectricitySumNum) generateElectricitySumNum, sum(generateElectricityOvertimeNum) generateElectricityOvertimeNum,");
		sqlBuffer.append(" sum(businessOpenedSumNum) businessOpenedSumNum,sum(businessOpenedOvertimeNum) businessOpenedOvertimeNum,");
		sqlBuffer.append(" sum(networkCutoverSumNum) networkCutoverSumNum,sum(networkCutoverOvertimeNum) networkCutoverOvertimeNum,");
		sqlBuffer.append(" sum(operationalCSumNum) operationalCSumNum,sum(operationalCOvertimeNum) operationalCOvertimeNum,");
		sqlBuffer.append(" sum(inspectionSumNum) inspectionSumNum,sum(inspectionOvertimeNum) inspectionOvertimeNum,");
		sqlBuffer.append(" sum(projectAcceptanceSumNum) projectAcceptanceSumNum,sum(projectAcceptanceOvertimeNum) projectAcceptanceOvertimeNum,");
		sqlBuffer.append(" sum(paymentSumNum) paymentSumNum,sum(paymentOvertimeNum) paymentOvertimeNum,");
		sqlBuffer.append(" sum(otherSumNum) otherSumNum,sum(otherOvertimeNum) otherOvertimeNum");
		sqlBuffer.append("  from(");
		sqlBuffer.append(" select relate.person,sum(case when trouble.sub_Type = '101220101' then 1 else 0  end) faultSumNum,");
		sqlBuffer.append(" sum(case when trouble.sub_Type = '101220101' then decode(sign(nvl(trouble.last_reply_time, sysdate) -trouble.end_time),1,1,-1,0,0) else 0 end) faultOvertimeNum,");
		sqlBuffer.append(" sum(case when trouble.sub_Type = '101220102' then 1 else 0  end) generateElectricitySumNum,");
		sqlBuffer.append(" sum(case when trouble.sub_Type = '101220102' then decode(sign(nvl(trouble.last_reply_time, sysdate) -trouble.end_time),1,1,-1,0,0) else 0 end) generateElectricityOvertimeNum,");
		sqlBuffer.append(" 0 businessOpenedSumNum,0 businessOpenedOvertimeNum,");
		sqlBuffer.append(" 0 networkCutoverSumNum,0 networkCutoverOvertimeNum,");
		sqlBuffer.append(" 0 operationalCSumNum,0 operationalCOvertimeNum,");
		sqlBuffer.append(" 0 inspectionSumNum,0 inspectionOvertimeNum,");
		sqlBuffer.append(" 0 projectAcceptanceSumNum,0 projectAcceptanceOvertimeNum,");
		sqlBuffer.append(" 0 paymentSumNum,0 paymentOvertimeNum,");
		sqlBuffer.append(" 0 otherSumNum,0 otherOvertimeNum");
		sqlBuffer.append(" from pnr_act_trouble_ticket_main   trouble,pnr_act_trouble_person_relate relate,act_ru_task ats");
		sqlBuffer.append(" where trouble.process_instance_id = relate.process_instance_id");
		sqlBuffer.append(" and trouble.process_instance_id = ats.proc_inst_id_");
		sqlBuffer.append(" and trouble.create_time >=");
		sqlBuffer.append("  to_date('").append(beginTime).append("', 'yyyy-MM-dd HH24:mi:ss')");
		sqlBuffer.append(" and trouble.create_time <=");
		sqlBuffer.append("  to_date('").append(endTime).append("', 'yyyy-MM-dd HH24:mi:ss')");
		sqlBuffer.append(" and trouble.country = '").append(country).append("'");
		sqlBuffer.append("  group by relate.person");
		sqlBuffer.append(" union all");
		sqlBuffer.append(" select relate.person, 0 faultSumNum,0 faultOvertimeNum,");
		sqlBuffer.append(" 0 generateElectricitySumNum,0 generateElectricityOvertimeNum,");
		sqlBuffer.append(" sum(case when task.sub_Type = '101110101' then 1 else 0  end) businessOpenedSumNum,");
		sqlBuffer.append(" sum(case when task.sub_Type = '101110101' then decode(sign(nvl(task.last_reply_time, sysdate) -task.end_time),1,1,-1,0,0) else 0 end) businessOpenedOvertimeNum,");
		sqlBuffer.append(" sum(case when task.sub_Type = '101110102' then 1 else 0  end) networkCutoverSumNum,");
		sqlBuffer.append(" sum(case when task.sub_Type = '101110102' then decode(sign(nvl(task.last_reply_time, sysdate) -task.end_time),1,1,-1,0,0) else 0 end) networkCutoverOvertimeNum,");
		sqlBuffer.append(" sum(case when task.sub_Type = '101110103' then 1 else 0  end) operationalCSumNum,");
		sqlBuffer.append(" sum(case when task.sub_Type = '101110103' then decode(sign(nvl(task.last_reply_time, sysdate) -task.end_time),1,1,-1,0,0) else 0 end) operationalCOvertimeNum,");
		sqlBuffer.append(" sum(case when task.sub_Type = '101110105' then 1 else 0  end) inspectionSumNum,");
		sqlBuffer.append(" sum(case when task.sub_Type = '101110105' then decode(sign(nvl(task.last_reply_time, sysdate) -task.end_time),1,1,-1,0,0) else 0 end) inspectionOvertimeNum,");
		sqlBuffer.append(" sum(case when task.sub_Type = '101110106' then 1 else 0  end) projectAcceptanceSumNum,");
		sqlBuffer.append(" sum(case when task.sub_Type = '101110106' then decode(sign(nvl(task.last_reply_time, sysdate) -task.end_time),1,1,-1,0,0) else 0 end) projectAcceptanceOvertimeNum,");
		sqlBuffer.append(" sum(case when task.sub_Type = '101110104' then 1 else 0  end) paymentSumNum,");
		sqlBuffer.append(" sum(case when task.sub_Type = '101110104' then decode(sign(nvl(task.last_reply_time, sysdate) -task.end_time),1,1,-1,0,0) else 0 end) paymentOvertimeNum,");
		sqlBuffer.append(" sum(case when task.sub_Type = '101110107' then 1 else 0  end) otherSumNum,");
		sqlBuffer.append(" sum(case when task.sub_Type = '101110107' then decode(sign(nvl(task.last_reply_time, sysdate) -task.end_time),1,1,-1,0,0) else 0 end) otherOvertimeNum");
		sqlBuffer.append(" from pnr_act_task_ticket_main   task, pnr_act_task_person_relate relate,act_ru_task ats");
		sqlBuffer.append(" where task.process_instance_id = relate.process_instance_id");
		sqlBuffer.append(" and task.process_instance_id = ats.proc_inst_id_");
		sqlBuffer.append(" and task.create_time >=");
		sqlBuffer.append("  to_date('").append(beginTime).append("', 'yyyy-MM-dd HH24:mi:ss')");
		sqlBuffer.append(" and task.create_time <=");
		sqlBuffer.append("  to_date('").append(endTime).append("', 'yyyy-MM-dd HH24:mi:ss')");
		sqlBuffer.append(" and ats.task_def_key_ != 'extractWorkOrder'");
		sqlBuffer.append(" and task.country = '").append(country).append("'");;
		sqlBuffer.append(" group by relate.person");
		sqlBuffer.append(" )sm");
		sqlBuffer.append(" group by sm. person");
		
//	System.out.println("坑人啊啊啊啊啊啊"+sqlBuffer);
        List<Map> list = this.getJdbcTemplate().queryForList(sqlBuffer.toString());
        for (Map map : list) {
            WorkOrderStatisticsModel2 workOrderStatisticsModel2 = new WorkOrderStatisticsModel2();
            workOrderStatisticsModel2.setCity(map.get("PERSON").toString());
            if (map.get("faultSumNum") == null) {
                workOrderStatisticsModel2.setFaultSumNum(0);
            } else {
                workOrderStatisticsModel2.setFaultSumNum(Integer.parseInt(map
                        .get("faultSumNum").toString()));
            }
            if (map.get("faultOvertimeNum") == null) {
                workOrderStatisticsModel2.setFaultOvertimeNum(0);
            } else {
                workOrderStatisticsModel2.setFaultOvertimeNum(Integer.parseInt(map
                        .get("faultOvertimeNum").toString()));
            }
            if (workOrderStatisticsModel2.getFaultSumNum() != 0 && workOrderStatisticsModel2.getFaultOvertimeNum() != 0) {
                workOrderStatisticsModel2.setFaultOvertimeRate(this.calculateThePercentage(new Double(workOrderStatisticsModel2.getFaultOvertimeNum()), new Double(workOrderStatisticsModel2.getFaultSumNum())));
            } else {
                workOrderStatisticsModel2.setFaultOvertimeRate("0%");
            }
            if (map.get("generateElectricitySumNum") == null) {
                workOrderStatisticsModel2.setGenerateElectricitySumNum(0);
            } else {
                workOrderStatisticsModel2.setGenerateElectricitySumNum(Integer.parseInt(map
                        .get("generateElectricitySumNum").toString()));
            }
            if (map.get("generateElectricityOvertimeNum") == null) {
                workOrderStatisticsModel2.setGenerateElectricityOvertimeNum(0);
            } else {
                workOrderStatisticsModel2.setGenerateElectricityOvertimeNum(Integer.parseInt(map
                        .get("generateElectricityOvertimeNum").toString()));
            }
            if (workOrderStatisticsModel2.getGenerateElectricitySumNum() != 0 && workOrderStatisticsModel2.getGenerateElectricityOvertimeNum() != 0) {
                workOrderStatisticsModel2.setGenerateElectricityOvertimeRate(this.calculateThePercentage(new Double(workOrderStatisticsModel2.getGenerateElectricityOvertimeNum()), new Double(workOrderStatisticsModel2.getGenerateElectricitySumNum())));
            } else {
                workOrderStatisticsModel2.setGenerateElectricityOvertimeRate("0%");
            }
            if (map.get("businessOpenedSumNum") == null) {
                workOrderStatisticsModel2.setBusinessOpenedSumNum(0);
            } else {
                workOrderStatisticsModel2.setBusinessOpenedSumNum(Integer.parseInt(map
                        .get("businessOpenedSumNum").toString()));
            }
            if (map.get("businessOpenedOvertimeNum") == null) {
                workOrderStatisticsModel2.setBusinessOpenedOvertimeNum(0);
            } else {
                workOrderStatisticsModel2.setBusinessOpenedOvertimeNum(Integer.parseInt(map
                        .get("businessOpenedOvertimeNum").toString()));
            }
            if (workOrderStatisticsModel2.getBusinessOpenedSumNum() != 0 && workOrderStatisticsModel2.getBusinessOpenedOvertimeNum() != 0) {
                workOrderStatisticsModel2.setBusinessOpenedOvertimeRate(this.calculateThePercentage(new Double(workOrderStatisticsModel2.getBusinessOpenedOvertimeNum()), new Double(workOrderStatisticsModel2.getBusinessOpenedSumNum())));
            } else {
                workOrderStatisticsModel2.setBusinessOpenedOvertimeRate("0%");
            }
            if (map.get("networkCutoverSumNum") == null) {
                workOrderStatisticsModel2.setNetworkCutoverSumNum(0);
            } else {
                workOrderStatisticsModel2.setNetworkCutoverSumNum(Integer.parseInt(map
                        .get("networkCutoverSumNum").toString()));
            }
            if (map.get("networkCutoverOvertimeNum") == null) {
                workOrderStatisticsModel2.setNetworkCutoverOvertimeNum(0);
            } else {
                workOrderStatisticsModel2.setNetworkCutoverOvertimeNum(Integer.parseInt(map
                        .get("networkCutoverOvertimeNum").toString()));
            }
            if (workOrderStatisticsModel2.getNetworkCutoverSumNum() != 0 && workOrderStatisticsModel2.getNetworkCutoverOvertimeNum() != 0) {
                workOrderStatisticsModel2.setNetworkCutoverOvertimeRate(this.calculateThePercentage(new Double(workOrderStatisticsModel2.getNetworkCutoverOvertimeNum()), new Double(workOrderStatisticsModel2.getNetworkCutoverSumNum())));
            } else {
                workOrderStatisticsModel2.setNetworkCutoverOvertimeRate("0%");
            }
            if (map.get("operationalCSumNum") == null) {
                workOrderStatisticsModel2.setOperationalCoordinationSumNum(0);
            } else {
                workOrderStatisticsModel2.setOperationalCoordinationSumNum(Integer.parseInt(map
                        .get("operationalCSumNum").toString()));
            }
            if (map.get("operationalCOvertimeNum") == null) {
                workOrderStatisticsModel2.setOperationalCoordinationOvertimeNum(0);
            } else {
                workOrderStatisticsModel2.setOperationalCoordinationOvertimeNum(Integer.parseInt(map
                        .get("operationalCOvertimeNum").toString()));
            }
            if (workOrderStatisticsModel2.getOperationalCoordinationSumNum() != 0 && workOrderStatisticsModel2.getOperationalCoordinationOvertimeNum() != 0) {
                workOrderStatisticsModel2.setOperationalCoordinationOvertimeRate(this.calculateThePercentage(new Double(workOrderStatisticsModel2.getOperationalCoordinationOvertimeNum()), new Double(workOrderStatisticsModel2.getOperationalCoordinationSumNum())));
            } else {
                workOrderStatisticsModel2.setOperationalCoordinationOvertimeRate("0%");
            }
            if (map.get("inspectionSumNum") == null) {
                workOrderStatisticsModel2.setInspectionSumNum(0);
            } else {
                workOrderStatisticsModel2.setInspectionSumNum(Integer.parseInt(map
                        .get("inspectionSumNum").toString()));
            }
            if (map.get("inspectionOvertimeNum") == null) {
                workOrderStatisticsModel2.setInspectionOvertimeNum(0);
            } else {
                workOrderStatisticsModel2.setInspectionOvertimeNum(Integer.parseInt(map
                        .get("inspectionOvertimeNum").toString()));
            }
            if (workOrderStatisticsModel2.getInspectionSumNum() != 0 && workOrderStatisticsModel2.getInspectionOvertimeNum() != 0) {
                workOrderStatisticsModel2.setInspectionOvertimeRate(this.calculateThePercentage(new Double(workOrderStatisticsModel2.getInspectionOvertimeNum()), new Double(workOrderStatisticsModel2.getInspectionSumNum())));
            } else {
                workOrderStatisticsModel2.setInspectionOvertimeRate("0%");
            }
            if (map.get("projectAcceptanceSumNum") == null) {
                workOrderStatisticsModel2.setProjectAcceptanceSumNum(0);
            } else {
                workOrderStatisticsModel2.setProjectAcceptanceSumNum(Integer.parseInt(map
                        .get("projectAcceptanceSumNum").toString()));
            }
            if (map.get("projectAcceptanceOvertimeNum") == null) {
                workOrderStatisticsModel2.setProjectAcceptanceOvertimeNum(0);
            } else {
                workOrderStatisticsModel2.setProjectAcceptanceOvertimeNum(Integer.parseInt(map
                        .get("projectAcceptanceOvertimeNum").toString()));
            }
            if (workOrderStatisticsModel2.getProjectAcceptanceSumNum() != 0 && workOrderStatisticsModel2.getProjectAcceptanceOvertimeNum() != 0) {
                workOrderStatisticsModel2.setProjectAcceptanceOvertimeRate(this.calculateThePercentage(new Double(workOrderStatisticsModel2.getProjectAcceptanceOvertimeNum()), new Double(workOrderStatisticsModel2.getProjectAcceptanceSumNum())));
            } else {
                workOrderStatisticsModel2.setProjectAcceptanceOvertimeRate("0%");
            }
            if (map.get("paymentSumNum") == null) {
            	workOrderStatisticsModel2.setPaymentSumNum(0);
            } else {
            	workOrderStatisticsModel2.setPaymentSumNum(Integer.parseInt(map
            			.get("paymentSumNum").toString()));
            }
            if (map.get("paymentOvertimeNum") == null) {
            	workOrderStatisticsModel2.setPaymentOvertimeNum(0);
            } else {
            	workOrderStatisticsModel2.setPaymentOvertimeNum(Integer.parseInt(map
            			.get("paymentOvertimeNum").toString()));
            }
            if (workOrderStatisticsModel2.getPaymentSumNum() != 0 && workOrderStatisticsModel2.getPaymentOvertimeNum() != 0) {
            	workOrderStatisticsModel2.setPaymentOvertimeRate(this.calculateThePercentage(new Double(workOrderStatisticsModel2.getPaymentOvertimeNum()), new Double(workOrderStatisticsModel2.getPaymentSumNum())));
            } else {
            	workOrderStatisticsModel2.setPaymentOvertimeRate("0%");
            }
            if (map.get("otherSumNum") == null) {
            	workOrderStatisticsModel2.setOtherSumNum(0);
            } else {
            	workOrderStatisticsModel2.setOtherSumNum(Integer.parseInt(map
            			.get("otherSumNum").toString()));
            }
            if (map.get("otherOvertimeNum") == null) {
            	workOrderStatisticsModel2.setOtherOvertimeNum(0);
            } else {
            	workOrderStatisticsModel2.setOtherOvertimeNum(Integer.parseInt(map
            			.get("otherOvertimeNum").toString()));
            }
            if (workOrderStatisticsModel2.getOtherSumNum() != 0 && workOrderStatisticsModel2.getOtherOvertimeNum() != 0) {
            	workOrderStatisticsModel2.setOtherOvertimeRate(this.calculateThePercentage(new Double(workOrderStatisticsModel2.getOtherOvertimeNum()), new Double(workOrderStatisticsModel2.getOtherSumNum())));
            } else {
            	workOrderStatisticsModel2.setOtherOvertimeRate("0%");
            }
            r.add(workOrderStatisticsModel2);
        }
        return r;
	}
	@Override
	public List<WorkOrderStatisticsModel2> workOrderStatistics2Person(
			String country, String beginTime, String endTime) {
		// TODO Auto-generated method stub
		List<WorkOrderStatisticsModel2> r = new ArrayList<WorkOrderStatisticsModel2>();
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append(" select sm.person,sum(faultSumNum) faultSumNum, sum(faultOvertimeNum) faultOvertimeNum,");
		sqlBuffer.append(" sum(generateElectricitySumNum) generateElectricitySumNum, sum(generateElectricityOvertimeNum) generateElectricityOvertimeNum,");
		sqlBuffer.append(" sum(businessOpenedSumNum) businessOpenedSumNum,sum(businessOpenedOvertimeNum) businessOpenedOvertimeNum,");
		sqlBuffer.append(" sum(networkCutoverSumNum) networkCutoverSumNum,sum(networkCutoverOvertimeNum) networkCutoverOvertimeNum,");
		sqlBuffer.append(" sum(operationalCSumNum) operationalCSumNum,sum(operationalCOvertimeNum) operationalCOvertimeNum,");
		sqlBuffer.append(" sum(inspectionSumNum) inspectionSumNum,sum(inspectionOvertimeNum) inspectionOvertimeNum,");
		sqlBuffer.append(" sum(projectAcceptanceSumNum) projectAcceptanceSumNum,sum(projectAcceptanceOvertimeNum) projectAcceptanceOvertimeNum,");
		sqlBuffer.append(" sum(paymentSumNum) paymentSumNum,sum(paymentOvertimeNum) paymentOvertimeNum,");
		sqlBuffer.append(" sum(otherSumNum) otherSumNum,sum(otherOvertimeNum) otherOvertimeNum");
		sqlBuffer.append("  from(");
		sqlBuffer.append(" select relate.person,sum(case when trouble.sub_Type = '101220101' then 1 else 0  end) faultSumNum,");
		sqlBuffer.append(" sum(case when trouble.sub_Type = '101220101' then decode(sign(nvl(trouble.last_reply_time, sysdate) -trouble.end_time),1,1,-1,0,0) else 0 end) faultOvertimeNum,");
		sqlBuffer.append(" sum(case when trouble.sub_Type = '101220102' then 1 else 0  end) generateElectricitySumNum,");
		sqlBuffer.append(" sum(case when trouble.sub_Type = '101220102' then decode(sign(nvl(trouble.last_reply_time, sysdate) -trouble.end_time),1,1,-1,0,0) else 0 end) generateElectricityOvertimeNum,");
		sqlBuffer.append(" 0 businessOpenedSumNum,0 businessOpenedOvertimeNum,");
		sqlBuffer.append(" 0 networkCutoverSumNum,0 networkCutoverOvertimeNum,");
		sqlBuffer.append(" 0 operationalCSumNum,0 operationalCOvertimeNum,");
		sqlBuffer.append(" 0 inspectionSumNum,0 inspectionOvertimeNum,");
		sqlBuffer.append(" 0 projectAcceptanceSumNum,0 projectAcceptanceOvertimeNum,");
		sqlBuffer.append(" 0 paymentSumNum,0 paymentOvertimeNum,");
		sqlBuffer.append(" 0 otherSumNum,0 otherOvertimeNum");
		sqlBuffer.append(" from pnr_act_trouble_ticket_main   trouble,pnr_act_trouble_person_relate relate");
		sqlBuffer.append(" where trouble.process_instance_id = relate.process_instance_id");
		sqlBuffer.append(" and trouble.create_time >=");
		sqlBuffer.append("  to_date('").append(beginTime).append("', 'yyyy-MM-dd HH24:mi:ss')");
		sqlBuffer.append(" and trouble.create_time <=");
		sqlBuffer.append("  to_date('").append(endTime).append("', 'yyyy-MM-dd HH24:mi:ss')");
		sqlBuffer.append(" and trouble.state=5 and trouble.country = '").append(country).append("'");
		sqlBuffer.append("  group by relate.person");
		sqlBuffer.append(" union all");
		sqlBuffer.append(" select relate.person, 0 faultSumNum,0 faultOvertimeNum,");
		sqlBuffer.append(" 0 generateElectricitySumNum,0 generateElectricityOvertimeNum,");
		sqlBuffer.append(" sum(case when task.sub_Type = '101110101' then 1 else 0  end) businessOpenedSumNum,");
		sqlBuffer.append(" sum(case when task.sub_Type = '101110101' then decode(sign(nvl(task.last_reply_time, sysdate) -task.end_time),1,1,-1,0,0) else 0 end) businessOpenedOvertimeNum,");
		sqlBuffer.append(" sum(case when task.sub_Type = '101110102' then 1 else 0  end) networkCutoverSumNum,");
		sqlBuffer.append(" sum(case when task.sub_Type = '101110102' then decode(sign(nvl(task.last_reply_time, sysdate) -task.end_time),1,1,-1,0,0) else 0 end) networkCutoverOvertimeNum,");
		sqlBuffer.append(" sum(case when task.sub_Type = '101110103' then 1 else 0  end) operationalCSumNum,");
		sqlBuffer.append(" sum(case when task.sub_Type = '101110103' then decode(sign(nvl(task.last_reply_time, sysdate) -task.end_time),1,1,-1,0,0) else 0 end) operationalCOvertimeNum,");
		sqlBuffer.append(" sum(case when task.sub_Type = '101110105' then 1 else 0  end) inspectionSumNum,");
		sqlBuffer.append(" sum(case when task.sub_Type = '101110105' then decode(sign(nvl(task.last_reply_time, sysdate) -task.end_time),1,1,-1,0,0) else 0 end) inspectionOvertimeNum,");
		sqlBuffer.append(" sum(case when task.sub_Type = '101110106' then 1 else 0  end) projectAcceptanceSumNum,");
		sqlBuffer.append(" sum(case when task.sub_Type = '101110106' then decode(sign(nvl(task.last_reply_time, sysdate) -task.end_time),1,1,-1,0,0) else 0 end) projectAcceptanceOvertimeNum,");
		sqlBuffer.append(" sum(case when task.sub_Type = '101110104' then 1 else 0  end) paymentSumNum,");
		sqlBuffer.append(" sum(case when task.sub_Type = '101110104' then decode(sign(nvl(task.last_reply_time, sysdate) -task.end_time),1,1,-1,0,0) else 0 end) paymentOvertimeNum,");
		sqlBuffer.append(" sum(case when task.sub_Type = '101110107' then 1 else 0  end) otherSumNum,");
		sqlBuffer.append(" sum(case when task.sub_Type = '101110107' then decode(sign(nvl(task.last_reply_time, sysdate) -task.end_time),1,1,-1,0,0) else 0 end) otherOvertimeNum");
		sqlBuffer.append(" from pnr_act_task_ticket_main   task, pnr_act_task_person_relate relate");
		sqlBuffer.append(" where task.process_instance_id = relate.process_instance_id");
		sqlBuffer.append(" and task.create_time >=");
		sqlBuffer.append("  to_date('").append(beginTime).append("', 'yyyy-MM-dd HH24:mi:ss')");
		sqlBuffer.append(" and task.create_time <=");
		sqlBuffer.append("  to_date('").append(endTime).append("', 'yyyy-MM-dd HH24:mi:ss')");
		sqlBuffer.append(" and task.state=5");
		sqlBuffer.append(" and task.country = '").append(country).append("'");;
		sqlBuffer.append(" group by relate.person");
		sqlBuffer.append(" )sm");
		sqlBuffer.append(" group by sm. person");
		
//		System.out.println("坑人啊啊啊啊啊啊"+sqlBuffer);
		List<Map> list = this.getJdbcTemplate().queryForList(sqlBuffer.toString());
		for (Map map : list) {
			WorkOrderStatisticsModel2 workOrderStatisticsModel2 = new WorkOrderStatisticsModel2();
			workOrderStatisticsModel2.setCity(map.get("PERSON").toString());
			if (map.get("faultSumNum") == null) {
				workOrderStatisticsModel2.setFaultSumNum(0);
			} else {
				workOrderStatisticsModel2.setFaultSumNum(Integer.parseInt(map
						.get("faultSumNum").toString()));
			}
			if (map.get("faultOvertimeNum") == null) {
				workOrderStatisticsModel2.setFaultOvertimeNum(0);
			} else {
				workOrderStatisticsModel2.setFaultOvertimeNum(Integer.parseInt(map
						.get("faultOvertimeNum").toString()));
			}
			if (workOrderStatisticsModel2.getFaultSumNum() != 0 && workOrderStatisticsModel2.getFaultOvertimeNum() != 0) {
				workOrderStatisticsModel2.setFaultOvertimeRate(this.calculateThePercentage(new Double(workOrderStatisticsModel2.getFaultOvertimeNum()), new Double(workOrderStatisticsModel2.getFaultSumNum())));
			} else {
				workOrderStatisticsModel2.setFaultOvertimeRate("0%");
			}
			if (map.get("generateElectricitySumNum") == null) {
				workOrderStatisticsModel2.setGenerateElectricitySumNum(0);
			} else {
				workOrderStatisticsModel2.setGenerateElectricitySumNum(Integer.parseInt(map
						.get("generateElectricitySumNum").toString()));
			}
			if (map.get("generateElectricityOvertimeNum") == null) {
				workOrderStatisticsModel2.setGenerateElectricityOvertimeNum(0);
			} else {
				workOrderStatisticsModel2.setGenerateElectricityOvertimeNum(Integer.parseInt(map
						.get("generateElectricityOvertimeNum").toString()));
			}
			if (workOrderStatisticsModel2.getGenerateElectricitySumNum() != 0 && workOrderStatisticsModel2.getGenerateElectricityOvertimeNum() != 0) {
				workOrderStatisticsModel2.setGenerateElectricityOvertimeRate(this.calculateThePercentage(new Double(workOrderStatisticsModel2.getGenerateElectricityOvertimeNum()), new Double(workOrderStatisticsModel2.getGenerateElectricitySumNum())));
			} else {
				workOrderStatisticsModel2.setGenerateElectricityOvertimeRate("0%");
			}
			if (map.get("businessOpenedSumNum") == null) {
				workOrderStatisticsModel2.setBusinessOpenedSumNum(0);
			} else {
				workOrderStatisticsModel2.setBusinessOpenedSumNum(Integer.parseInt(map
						.get("businessOpenedSumNum").toString()));
			}
			if (map.get("businessOpenedOvertimeNum") == null) {
				workOrderStatisticsModel2.setBusinessOpenedOvertimeNum(0);
			} else {
				workOrderStatisticsModel2.setBusinessOpenedOvertimeNum(Integer.parseInt(map
						.get("businessOpenedOvertimeNum").toString()));
			}
			if (workOrderStatisticsModel2.getBusinessOpenedSumNum() != 0 && workOrderStatisticsModel2.getBusinessOpenedOvertimeNum() != 0) {
				workOrderStatisticsModel2.setBusinessOpenedOvertimeRate(this.calculateThePercentage(new Double(workOrderStatisticsModel2.getBusinessOpenedOvertimeNum()), new Double(workOrderStatisticsModel2.getBusinessOpenedSumNum())));
			} else {
				workOrderStatisticsModel2.setBusinessOpenedOvertimeRate("0%");
			}
			if (map.get("networkCutoverSumNum") == null) {
				workOrderStatisticsModel2.setNetworkCutoverSumNum(0);
			} else {
				workOrderStatisticsModel2.setNetworkCutoverSumNum(Integer.parseInt(map
						.get("networkCutoverSumNum").toString()));
			}
			if (map.get("networkCutoverOvertimeNum") == null) {
				workOrderStatisticsModel2.setNetworkCutoverOvertimeNum(0);
			} else {
				workOrderStatisticsModel2.setNetworkCutoverOvertimeNum(Integer.parseInt(map
						.get("networkCutoverOvertimeNum").toString()));
			}
			if (workOrderStatisticsModel2.getNetworkCutoverSumNum() != 0 && workOrderStatisticsModel2.getNetworkCutoverOvertimeNum() != 0) {
				workOrderStatisticsModel2.setNetworkCutoverOvertimeRate(this.calculateThePercentage(new Double(workOrderStatisticsModel2.getNetworkCutoverOvertimeNum()), new Double(workOrderStatisticsModel2.getNetworkCutoverSumNum())));
			} else {
				workOrderStatisticsModel2.setNetworkCutoverOvertimeRate("0%");
			}
			if (map.get("operationalCSumNum") == null) {
				workOrderStatisticsModel2.setOperationalCoordinationSumNum(0);
			} else {
				workOrderStatisticsModel2.setOperationalCoordinationSumNum(Integer.parseInt(map
						.get("operationalCSumNum").toString()));
			}
			if (map.get("operationalCOvertimeNum") == null) {
				workOrderStatisticsModel2.setOperationalCoordinationOvertimeNum(0);
			} else {
				workOrderStatisticsModel2.setOperationalCoordinationOvertimeNum(Integer.parseInt(map
						.get("operationalCOvertimeNum").toString()));
			}
			if (workOrderStatisticsModel2.getOperationalCoordinationSumNum() != 0 && workOrderStatisticsModel2.getOperationalCoordinationOvertimeNum() != 0) {
				workOrderStatisticsModel2.setOperationalCoordinationOvertimeRate(this.calculateThePercentage(new Double(workOrderStatisticsModel2.getOperationalCoordinationOvertimeNum()), new Double(workOrderStatisticsModel2.getOperationalCoordinationSumNum())));
			} else {
				workOrderStatisticsModel2.setOperationalCoordinationOvertimeRate("0%");
			}
			if (map.get("inspectionSumNum") == null) {
				workOrderStatisticsModel2.setInspectionSumNum(0);
			} else {
				workOrderStatisticsModel2.setInspectionSumNum(Integer.parseInt(map
						.get("inspectionSumNum").toString()));
			}
			if (map.get("inspectionOvertimeNum") == null) {
				workOrderStatisticsModel2.setInspectionOvertimeNum(0);
			} else {
				workOrderStatisticsModel2.setInspectionOvertimeNum(Integer.parseInt(map
						.get("inspectionOvertimeNum").toString()));
			}
			if (workOrderStatisticsModel2.getInspectionSumNum() != 0 && workOrderStatisticsModel2.getInspectionOvertimeNum() != 0) {
				workOrderStatisticsModel2.setInspectionOvertimeRate(this.calculateThePercentage(new Double(workOrderStatisticsModel2.getInspectionOvertimeNum()), new Double(workOrderStatisticsModel2.getInspectionSumNum())));
			} else {
				workOrderStatisticsModel2.setInspectionOvertimeRate("0%");
			}
			if (map.get("projectAcceptanceSumNum") == null) {
				workOrderStatisticsModel2.setProjectAcceptanceSumNum(0);
			} else {
				workOrderStatisticsModel2.setProjectAcceptanceSumNum(Integer.parseInt(map
						.get("projectAcceptanceSumNum").toString()));
			}
			if (map.get("projectAcceptanceOvertimeNum") == null) {
				workOrderStatisticsModel2.setProjectAcceptanceOvertimeNum(0);
			} else {
				workOrderStatisticsModel2.setProjectAcceptanceOvertimeNum(Integer.parseInt(map
						.get("projectAcceptanceOvertimeNum").toString()));
			}
			if (workOrderStatisticsModel2.getProjectAcceptanceSumNum() != 0 && workOrderStatisticsModel2.getProjectAcceptanceOvertimeNum() != 0) {
				workOrderStatisticsModel2.setProjectAcceptanceOvertimeRate(this.calculateThePercentage(new Double(workOrderStatisticsModel2.getProjectAcceptanceOvertimeNum()), new Double(workOrderStatisticsModel2.getProjectAcceptanceSumNum())));
			} else {
				workOrderStatisticsModel2.setProjectAcceptanceOvertimeRate("0%");
			}
			if (map.get("paymentSumNum") == null) {
				workOrderStatisticsModel2.setPaymentSumNum(0);
			} else {
				workOrderStatisticsModel2.setPaymentSumNum(Integer.parseInt(map
						.get("paymentSumNum").toString()));
			}
			if (map.get("paymentOvertimeNum") == null) {
				workOrderStatisticsModel2.setPaymentOvertimeNum(0);
			} else {
				workOrderStatisticsModel2.setPaymentOvertimeNum(Integer.parseInt(map
						.get("paymentOvertimeNum").toString()));
			}
			if (workOrderStatisticsModel2.getPaymentSumNum() != 0 && workOrderStatisticsModel2.getPaymentOvertimeNum() != 0) {
				workOrderStatisticsModel2.setPaymentOvertimeRate(this.calculateThePercentage(new Double(workOrderStatisticsModel2.getPaymentOvertimeNum()), new Double(workOrderStatisticsModel2.getPaymentSumNum())));
			} else {
				workOrderStatisticsModel2.setPaymentOvertimeRate("0%");
			}
			if (map.get("otherSumNum") == null) {
				workOrderStatisticsModel2.setOtherSumNum(0);
			} else {
				workOrderStatisticsModel2.setOtherSumNum(Integer.parseInt(map
						.get("otherSumNum").toString()));
			}
			if (map.get("otherOvertimeNum") == null) {
				workOrderStatisticsModel2.setOtherOvertimeNum(0);
			} else {
				workOrderStatisticsModel2.setOtherOvertimeNum(Integer.parseInt(map
						.get("otherOvertimeNum").toString()));
			}
			if (workOrderStatisticsModel2.getOtherSumNum() != 0 && workOrderStatisticsModel2.getOtherOvertimeNum() != 0) {
				workOrderStatisticsModel2.setOtherOvertimeRate(this.calculateThePercentage(new Double(workOrderStatisticsModel2.getOtherOvertimeNum()), new Double(workOrderStatisticsModel2.getOtherSumNum())));
			} else {
				workOrderStatisticsModel2.setOtherOvertimeRate("0%");
			}
			r.add(workOrderStatisticsModel2);
		}
		return r;
	}
	
	/**
	 * 统计历史区县巡检情况
	 * @param  themeinterface
	 * @param  taskdefkey
	 * @param  quarter
	 * 地市
	 * @return
	 */
	public List<PreflightStatisticPartnerModel> findPreflightStatisticCityHis(String themeinterface,String taskdefkey,String quarter){
		String sql = "select rt.*,bt.budget_amount from ("
		  +"  select r.city ,count(r.city) audit_num ,"
		  +"      sum(r.project_amount) audit_money , "
		  +"      sum(case when r.review_result='YES' then 1 else 0 end) pass_num ,"
		  +"      sum(case when r.review_result='YES' then r.project_amount else 0 end) pass_money,"
		  +"          sum(case when r.review_result='NO' then 1 else 0 end) no_pass_num ,"
		  +"      sum(case when r.review_result='NO' then r.project_amount else 0 end) no_pass_money ,"
		  +"         sum(case when r.review_result='YES' then 1 else 0 end) audit_pass_num ,"
		  +"      sum(case when r.review_result='YES' then r.project_amount else 0 end) audit_pass_money "
		  +"        from ("
		  +"    select pm.process_instance_id,"
		  +"    pm.project_amount,"
		  +"    pm.city,pm.country,pt.review_result"
		  +"     from pnr_act_transfer_office_main pm "
		  +"    left join pnr_review_results pt"
		  +"      on pm.process_instance_id = pt.process_instance_id "
		  +"     left join act_ru_task rk on pm.process_instance_id = rk.proc_inst_id_"
		  +"     where 1=1   " ;
		 if(!"".equals(themeinterface) && themeinterface!=null){
			   sql+= "and pm.themeinterface in ("+themeinterface+")";
		   }
		  sql+="       and pm.state <>1"
		  +"       and length(pm.city)=4"
		  +"       and rk.task_def_key_ not in ('need','workOrderCheck','cityLineExamine',"
		  +"      'cityLineDirectorAudit','cityManageExamine','cityManageDirectorAudit','cityViceAudit')   "
		  +"       and exists("
		  +"            select kst.id_ from act_hi_taskinst kst "
		  +"            where  kst.proc_inst_id_ =pm.process_instance_id"  ;
		  if(!"".equals(taskdefkey) && taskdefkey!=null){
		       sql+="    and   kst.task_def_key_ in ("+taskdefkey+")";
		  }
		  sql+="            )"
		  +"  ) r"
		  +"  group by r.city"
		  +"  )rt left join pnr_act_city_budget_amount bt "
		  +"  on rt.city = bt.city_id and bt.budget_year='2015' ";
		   if(!"".equals(quarter) && quarter!=null){
			   sql+= "and bt.budget_quarter="+quarter;
		   }
		   sql+=" left join taw_system_area t on rt.city = t.areaid order by t.sort asc";
		System.out.print("------------------"+sql);
		return this.getJdbcTemplate().query(sql,new InspectStatisticPartnerMapper());
	}
	
	private static final class InspectStatisticPartnerMapper implements RowMapper{
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			PreflightStatisticPartnerModel partner = new PreflightStatisticPartnerModel();
			partner.setCity(rs.getString("city"));  
			partner.setAuditMoney(rs.getString("audit_money"));
			partner.setAuditNum(rs.getInt("audit_num"));
			//除数是否为0
			if(rs.getInt("budget_amount")!=0){
				partner.setAuditPercent(this.calculateThePercentage1(new Double(rs.getString("audit_money")), new Double(rs.getString("budget_amount"))));
			}else{
				partner.setAuditPercent("0%");
			}
			partner.setAuditPassMoney(rs.getString("audit_pass_money"));
			partner.setAuditPassNum(rs.getInt("audit_pass_num"));
			if(rs.getInt("audit_num")!=0){
			partner.setAuditPassPercent(this.calculateThePercentage1(new Double(rs.getInt("audit_pass_num")), new Double(rs.getInt("audit_num"))));
			}else{
				partner.setAuditPassPercent("0%");
			}
			partner.setNoPassNum(rs.getInt("no_pass_num"));
			partner.setNoPassNumMoney(rs.getString("no_pass_money"));
			partner.setPassMoney(rs.getString("pass_money"));
			partner.setPassNum(rs.getInt("pass_num"));
			if(rs.getInt("audit_num")!=0){
			partner.setPassPercent(this.calculateThePercentage1(new Double(rs.getInt("pass_num")), new Double(rs.getInt("audit_num"))));
			}else{
				partner.setPassPercent("0%");
			}
			partner.setBudgetAmount(rs.getString("budget_amount"));
            return partner;
		}
		
		 private static final String calculateThePercentage1(Double a, Double b) {
		        NumberFormat nf = NumberFormat.getPercentInstance();
		        nf.setMinimumFractionDigits(2);
		        NumberFormat nf1 = NumberFormat.getPercentInstance();
		        nf.setMinimumFractionDigits(0);
		        if (a == null || a.equals(0) || a.equals(0.0) || a.equals(0.00)) {
		            return nf1.format(0);
		        }
		        if (b == null || b.equals(0) || b.equals(0.0) || a.equals(0.00)) {
		            return nf1.format(0);
		        }
		        Double d = a / b;
		        if (d == null || d.equals(100) || d.equals(100.0) || a.equals(100.00)) {
		            return nf1.format(100);
		        }
		        String r = nf.format(d);
		        return r;
		    }
	}
	 /**
	 * 统计历史区县巡检情况
	 * @param year
	 * @param month
	 * @param excelType 报表类型
	 * 区县
	 * @return
	 */
	public List<PreflightStatisticPartnerModel> findPreflightStatisticCountryHis(String themeinterface,String taskdefkey,String quarter,String city){
		
		String sql = "select rt.*,rt.country,rt.audit_num as auditNum,rt.audit_money as auditMoney,rt.pass_num as passNum,rt.pass_money as  passMoney ,"
			  +"  rt.no_pass_num as noPassNum, rt.no_pass_money as  noPassNumMoney, rt.audit_pass_num as auditPassNum, rt.audit_pass_money as auditPassMoney " 
			  +	"  ,trunc(decode(budget_amount,0,0,audit_money/budget_amount*100),2) as auditPercent ,trunc(decode(audit_num,0,0,audit_pass_num/audit_num*100),2) as passPercent ,trunc(decode(audit_num,0,0,pass_num/audit_num*100),2) as auditPassPercent from ("
			  +"  select r.city ,r.country,count(r.city) audit_num ,"
			  +"      sum(r.project_amount) audit_money , "
			  +"      sum(case when r.review_result='YES' then 1 else 0 end) pass_num ,"
			  +"      sum(case when r.review_result='YES' then r.project_amount else 0 end) pass_money,"
			  +"          sum(case when r.review_result='NO' then 1 else 0 end) no_pass_num ,"
			  +"      sum(case when r.review_result='NO' then r.project_amount else 0 end) no_pass_money ,"
			  +"         sum(case when r.review_result='YES' then 1 else 0 end) audit_pass_num ,"
			  +"      sum(case when r.review_result='YES' then r.project_amount else 0 end) audit_pass_money "
			  +"        from ("
			  +"    select pm.process_instance_id,"
			  +"    pm.project_amount,"
			  +"    pm.city,pm.country,pt.review_result"
			  +"     from pnr_act_transfer_office_main pm "
			  +"    left join pnr_review_results pt"
			  +"      on pm.process_instance_id = pt.process_instance_id "
			  +"     left join act_ru_task rk on pm.process_instance_id = rk.proc_inst_id_"
			  +"     where 1=1   " ;
			 if(!"".equals(themeinterface) && themeinterface!=null){
				   sql+= "and pm.themeinterface in ("+themeinterface+")";
			   }
			  sql+="       and pm.state <>1"
			  +"       and length(pm.city)=4"
			  +"       and pm.city='"+city+"'"
			  +"       and rk.task_def_key_ not in ('need','workOrderCheck','cityLineExamine',"
			  +"      'cityLineDirectorAudit','cityManageExamine','cityManageDirectorAudit','cityViceAudit')   "
			  +"       and exists("
			  +"            select kst.id_ from act_hi_taskinst kst "
			  +"            where  kst.proc_inst_id_ =pm.process_instance_id"  ;
			  if(!"".equals(taskdefkey) && taskdefkey!=null){
			       sql+="    and   kst.task_def_key_ in ("+taskdefkey+")";
			  }
			  sql+="            )"
			  +"  ) r"
			  +"  group by r.city,r.country"
			  +"  )rt left join pnr_act_city_budget_amount bt "
			  +"  on rt.city = bt.city_id and bt.budget_year='2015' ";
			   if(!"".equals(quarter) && quarter!=null){
				   sql+= "and bt.budget_quarter="+quarter;
			   }
			   sql+=" left join taw_system_area t on rt.city = t.areaid order by t.sort asc";
			System.out.print("------------------"+sql);
			return this.getJdbcTemplate().queryForList(sql);
	}
	
	 /**
	 * 预检预修详情
	 * @param year
	 * @param month
	 * @param excelType 报表类型
	 * @return
	 */
	public List<PreflightDetailStatisticPartnerModel> findPreflightDatilStatisticCityHis(String year,String month,String excelType, int firstResult,
			int endResult, int pageSize){
		String sql = "select temp2.* from (select temp1.*, rownum num from (";
		String selectSql="select * from ("
				+" select pm.city,pm.country,pm.process_instance_id,"
				+" pm.sheet_id,pm.theme,pm.bear_service,"
				+" pm.workorder_type_name,"
				+" pm.sub_type_name,pm.key_word,pm.work_order_degree,pm.fault_description,pm.project_amount,"
				+" pm.submit_application_time,nvl(k.name_,'归档') name_ ,"
				+" kt.end_time_ ,"
				+" row_number() over(partition by kt.proc_inst_id_ order by kt.end_time_ desc) row_num,"
				+" pr.expert_opinion,"
				+" decode(pr.review_result,'YES','合格','NO','不合格','-') review_result,"
				+" decode(pr.IS_AGREE_STRIKING,'YES','同意','NO','不同意','-') practice,"
				+" pm.distributed_interface_time"

				+" from pnr_act_transfer_office_main pm"
				+" left join pnr_review_results  pr on pm.process_instance_id = pr.process_instance_id"
				+" and to_char(nvl(pr.review_time,pr.import_time) ,'yyyy-mm')='"+year+"-"+month+"'"//--条件
				+" left join act_ru_task k on pm.process_instance_id = k.proc_inst_id_"
				+" left join  act_hi_taskinst kt "
				+" on  pm.process_instance_id = kt.proc_inst_id_ and kt.task_def_key_='cityViceAudit'"

				+" where pm.state <>1 "
				+"      and pm.themeinterface in ('interface','arteryPrecheck')"

				+"      and to_char(pm.submit_application_time,'yyyy-mm')='"+year+"-"+month+"'";//--条件
				
				if("1".equals(excelType)){//合格同意
					selectSql+=" and pr.review_result='YES' and pr.IS_AGREE_STRIKING='YES'";
				}else if("2".equals(excelType)){//合格不同意
					selectSql+=" and pr.review_result='YES' and pr.IS_AGREE_STRIKING='NO'";
				}else if("3".equals(excelType)){//不合格
					selectSql+=" and pr.review_result='NO'";
				}
				
				selectSql+=" ) where row_num =1 ";
				
				sql=sql+selectSql
				+ " order by to_number(process_instance_id)) temp1 where rownum <="
				+ endResult * pageSize + ") temp2 where temp2.num > "
				+ firstResult * pageSize;
				
			System.out.println("--------------------"+sql);
		return this.getJdbcTemplate().query(sql,new PreflightDetailStatisticPartnerModelMapper());
	}
	private static final class PreflightDetailStatisticPartnerModelMapper implements RowMapper{
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			PreflightDetailStatisticPartnerModel partner=new PreflightDetailStatisticPartnerModel();
			partner.setCity(rs.getString("city"));
			partner.setCountry(rs.getString("country"));
			partner.setProcessinstanceid(rs.getString("process_instance_id"));
			partner.setSheetid(rs.getString("sheet_id"));
			partner.setTheme(rs.getString("theme"));
			partner.setBearService(rs.getString("bear_service"));
			partner.setWorkorderTypeName(rs.getString("workorder_type_name"));
			partner.setSubTypeName(rs.getString("sub_type_name"));
			partner.setKeyWord(rs.getString("key_word"));
			partner.setWorkOrderDegree(rs.getString("work_order_degree"));
			partner.setFaultDescription(rs.getString("fault_description"));
			partner.setProjectAmount(rs.getString("project_amount"));
			partner.setSubmitApplicationTime(rs.getDate("submit_application_time"));
			partner.setName(rs.getString("name_"));
			partner.setEndTime(rs.getDate("end_time_"));
			partner.setExpertOpinion(rs.getString("expert_opinion"));
			partner.setReviewResult(rs.getString("review_result"));
			partner.setPractice(rs.getString("practice"));
			partner.setDistributedInterfaceTime(rs.getDate("distributed_interface_time"));
            return partner;
		}
	}
	
	/**
	 * 预检预修详情数量
	 * @param year
	 * @param month
	 * @param excelType 报表类型
	 * @return
	 */
	public int findPreflightDatilStatisticCityHisCount(String year,String month,String excelType){
		String sql="select count(*) total from ("
			+" select pm.city,pm.country,pm.process_instance_id,"
			+" pm.sheet_id,pm.theme,pm.bear_service,"
			+" pm.workorder_type_name,"
			+" pm.sub_type_name,pm.key_word,pm.work_order_degree,pm.fault_description,pm.project_amount,"
			+" pm.submit_application_time,nvl(k.name_,'归档') name_ ,"
			+" kt.end_time_ ,"
			+" row_number() over(partition by kt.proc_inst_id_ order by kt.end_time_ desc) row_num,"
			+" pr.expert_opinion,"
			+" decode(pr.review_result,'YES','合格','NO','不合格','-') review_result,"
			+" decode(pr.IS_AGREE_STRIKING,'YES','同意','NO','不同意','-') practice,"
			+" pm.distributed_interface_time"
			+" from pnr_act_transfer_office_main pm"
			+" left join pnr_review_results  pr on pm.process_instance_id = pr.process_instance_id"
			+" and to_char(nvl(pr.review_time,pr.import_time) ,'yyyy-mm')='"+year+"-"+month+"'"//--条件
			+" left join act_ru_task k on pm.process_instance_id = k.proc_inst_id_"
			+" left join  act_hi_taskinst kt "
			+" on  pm.process_instance_id = kt.proc_inst_id_ and kt.task_def_key_='cityViceAudit'"

			+" where pm.state <>1 "
			+"      and pm.themeinterface in ('interface','arteryPrecheck')"

			+"      and to_char(pm.submit_application_time,'yyyy-mm')='"+year+"-"+month+"'";//--条件
			
			if("1".equals(excelType)){//合格同意
				sql+=" and pr.review_result='YES' and pr.IS_AGREE_STRIKING='YES'";
			}else if("2".equals(excelType)){//合格不同意
				sql+=" and pr.review_result='YES' and pr.IS_AGREE_STRIKING='NO'";
			}else if("3".equals(excelType)){//不合格
				sql+=" and pr.review_result='NO'";
			}
			
			sql+=" ) where row_num =1 ";
			List<Map> list = this.getJdbcTemplate().queryForList(sql);
			System.out.print("==================="+sql);
			return Integer.parseInt(list.get(0).get("total").toString());
	}
	
	/**
	 * 机房优化周报
	 * @param 
	 * @param 
	 * @param 
	 * @return
	 */
	public List<PreflightDetailStatisticPartnerModel>  findWeeklyStatisticHis(String sendStartTime,String sendEndTime,String region,String country,String themeinterface,String taskdefkey ,int firstResult,
			int endResult, int pageSize){
		return null;
	}
	
}
