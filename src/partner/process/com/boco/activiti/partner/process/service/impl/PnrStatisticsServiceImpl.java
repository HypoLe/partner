package com.boco.activiti.partner.process.service.impl;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.boco.activiti.partner.process.dao.IPnrStatisticsJDBCDao;
import com.boco.activiti.partner.process.po.PreflightDetailStatisticPartnerModel;
import com.boco.activiti.partner.process.po.PreflightStatisticPartnerModel;
import com.boco.activiti.partner.process.po.WorkOrderStatisticsDrillModel;
import com.boco.activiti.partner.process.po.WorkOrderStatisticsModel;
import com.boco.activiti.partner.process.po.WorkOrderStatisticsModel2;
import com.boco.activiti.partner.process.service.IPnrStatisticsService;
import com.boco.eoms.partner.inspect.model.InspectStatisticArea;

/**

 */
public class PnrStatisticsServiceImpl implements IPnrStatisticsService {

    private IPnrStatisticsJDBCDao pnrStatisticsJDBCDao;

    public void setPnrStatisticsJDBCDao(IPnrStatisticsJDBCDao pnrStatisticsJDBCDao) {
        this.pnrStatisticsJDBCDao = pnrStatisticsJDBCDao;
    }

    public List<WorkOrderStatisticsModel> workOrderStatistics(String type,String beginTime,String endTime,String subType){
        List<WorkOrderStatisticsModel> r=null;
        if(type.equals("trouble")){
        r=pnrStatisticsJDBCDao.troubleTicketWorkOrderStatistics(beginTime, endTime, subType);
        }
        if(type.equals("task")){
            r=pnrStatisticsJDBCDao.taskTicketWorkOrderStatistics(beginTime,endTime,subType);
        }
        if(type.equals("all")){
            List<WorkOrderStatisticsModel> task=pnrStatisticsJDBCDao.taskTicketWorkOrderStatistics(beginTime,endTime,subType);
            List<WorkOrderStatisticsModel> trouble=  pnrStatisticsJDBCDao.troubleTicketWorkOrderStatistics(beginTime, endTime, subType);
            r=this.add(task,trouble);
        }if(type.equals("transferOffice")){
        	 r=pnrStatisticsJDBCDao.transferOfficeTicketWorkOrderStatistics(beginTime,endTime,subType);
        }if(type.equals("transferInterface")){
        	
        }
        WorkOrderStatisticsModel all=new WorkOrderStatisticsModel();
        all.setCityName("全省");
        all.setCity("28");
          for(WorkOrderStatisticsModel workOrderStatisticsModel:r){
              all.setSumNum(all.getSumNum()+workOrderStatisticsModel.getSumNum());
              all.setOvertimeNum(all.getOvertimeNum() + workOrderStatisticsModel.getOvertimeNum());
              all.setUnfiledNumber(all.getUnfiledNumber() + workOrderStatisticsModel.getUnfiledNumber());
              all.setArchiveNumber(all.getArchiveNumber() + workOrderStatisticsModel.getArchiveNumber());
          }
        if(all.getSumNum()!=0&&all.getOvertimeNum()!=0){
            all.setOvertimeRate(this.calculateThePercentage(new Double(all.getOvertimeNum()),new Double(all.getSumNum())));
        }else{
            all.setOvertimeRate("0%");
        }
        r.add(all);
        return r;
    }
    public List<WorkOrderStatisticsModel> transferOfficeStatistics(String type,String beginTime, String endTime, String subType){
    	List<WorkOrderStatisticsModel> r=null;
        	 r=pnrStatisticsJDBCDao.transferInterfaceWorkOrderStatistics(type,beginTime,endTime,subType);
        WorkOrderStatisticsModel all=new WorkOrderStatisticsModel();
        all.setCityName("全省");
        all.setCity("28");
          for(WorkOrderStatisticsModel workOrderStatisticsModel:r){
              all.setSumNum(all.getSumNum()+workOrderStatisticsModel.getSumNum());
              all.setOvertimeNum(all.getOvertimeNum() + workOrderStatisticsModel.getOvertimeNum());
              all.setUnfiledNumber(all.getUnfiledNumber() + workOrderStatisticsModel.getUnfiledNumber());
              all.setArchiveNumber(all.getArchiveNumber() + workOrderStatisticsModel.getArchiveNumber());
          }
        if(all.getSumNum()!=0&&all.getOvertimeNum()!=0){
            all.setOvertimeRate(this.calculateThePercentage(new Double(all.getOvertimeNum()),new Double(all.getSumNum())));
        }else{
            all.setOvertimeRate("0%");
        }
        r.add(all);
        return r;
    }
    @Override
    public List<WorkOrderStatisticsModel2> workOrderStatistics2( String beginTime, String endTime) {
        return pnrStatisticsJDBCDao.workOrderStatistics2(beginTime, endTime);
    }
    @Override
    public List<WorkOrderStatisticsModel2> workOrderStatistics3( String beginTime, String endTime) {
        return pnrStatisticsJDBCDao.workOrderStatistics3(beginTime, endTime);
    }
    private  List<WorkOrderStatisticsModel> add( List<WorkOrderStatisticsModel> a, List<WorkOrderStatisticsModel> b){
        List<WorkOrderStatisticsModel> r=new ArrayList<WorkOrderStatisticsModel>();
           for(WorkOrderStatisticsModel workOrderStatisticsModelA:a){
               for(WorkOrderStatisticsModel workOrderStatisticsModelB:b){
                       if(workOrderStatisticsModelA.getCity().equals(workOrderStatisticsModelB.getCity())){
                           WorkOrderStatisticsModel workOrderStatisticsModel=new WorkOrderStatisticsModel();
                           workOrderStatisticsModel.setCity(workOrderStatisticsModelA.getCity());
                           workOrderStatisticsModel.setCityName(workOrderStatisticsModelA.getCityName());
                           workOrderStatisticsModel.setSumNum(workOrderStatisticsModelA.getSumNum()+workOrderStatisticsModelB.getSumNum());
                           workOrderStatisticsModel.setOvertimeNum(workOrderStatisticsModelA.getOvertimeNum() + workOrderStatisticsModelB.getOvertimeNum());
                           workOrderStatisticsModel.setUnfiledNumber(workOrderStatisticsModelA.getUnfiledNumber() + workOrderStatisticsModelB.getUnfiledNumber());
                           workOrderStatisticsModel.setArchiveNumber(workOrderStatisticsModelA.getArchiveNumber() + workOrderStatisticsModelB.getArchiveNumber());
                           if(workOrderStatisticsModel.getSumNum()!=0&&workOrderStatisticsModel.getOvertimeNum()!=0){
                               workOrderStatisticsModel.setOvertimeRate(this.calculateThePercentage(new Double(workOrderStatisticsModel.getOvertimeNum()),new Double(workOrderStatisticsModel.getSumNum())));
                           }else{
                               workOrderStatisticsModel.setOvertimeRate("0%");
                           }
                           r.add(workOrderStatisticsModel);
                       }
               }
           }
        return r;
    }
    //用于处理人级
    private  List<WorkOrderStatisticsModel> add1( List<WorkOrderStatisticsModel> a, List<WorkOrderStatisticsModel> b){
        List<WorkOrderStatisticsModel> r=new ArrayList<WorkOrderStatisticsModel>();
        //C 用于判断不相同的人名不相同的
        List<WorkOrderStatisticsModel> c=new ArrayList<WorkOrderStatisticsModel>();
           for(WorkOrderStatisticsModel workOrderStatisticsModelA:a){
               for(WorkOrderStatisticsModel workOrderStatisticsModelB:b){

                       if(workOrderStatisticsModelA.getCityName().equals(workOrderStatisticsModelB.getCityName())){
                           WorkOrderStatisticsModel workOrderStatisticsModel=new WorkOrderStatisticsModel();
                           workOrderStatisticsModel.setCity(workOrderStatisticsModelA.getCity());
                           workOrderStatisticsModel.setCityName(workOrderStatisticsModelA.getCityName());
                           workOrderStatisticsModel.setSumNum(workOrderStatisticsModelA.getSumNum()+workOrderStatisticsModelB.getSumNum());
                           workOrderStatisticsModel.setOvertimeNum(workOrderStatisticsModelA.getOvertimeNum() + workOrderStatisticsModelB.getOvertimeNum());
                           workOrderStatisticsModel.setUnfiledNumber(workOrderStatisticsModelA.getUnfiledNumber() + workOrderStatisticsModelB.getUnfiledNumber());
                           workOrderStatisticsModel.setArchiveNumber(workOrderStatisticsModelA.getArchiveNumber() + workOrderStatisticsModelB.getArchiveNumber());
                           if(workOrderStatisticsModel.getSumNum()!=0&&workOrderStatisticsModel.getOvertimeNum()!=0){
                               workOrderStatisticsModel.setOvertimeRate(this.calculateThePercentage(new Double(workOrderStatisticsModel.getOvertimeNum()),new Double(workOrderStatisticsModel.getSumNum())));
                           }else{
                               workOrderStatisticsModel.setOvertimeRate("0%");
                           }
                           r.add(workOrderStatisticsModel);
                           c.add(workOrderStatisticsModel);
                       }
               }
               
           }
           //对于不相交的数据单独遍历
           for(WorkOrderStatisticsModel workOrderStatisticsModelA:a){
        	   
        	   int i = 1;
               for(WorkOrderStatisticsModel workOrderStatisticsModelC:c){
                       if(workOrderStatisticsModelA.getCityName().equals(workOrderStatisticsModelC.getCityName())){
                           i++;
                       }
               }
               if(i==1){
            	   WorkOrderStatisticsModel workOrderStatisticsModel=new WorkOrderStatisticsModel();
                   workOrderStatisticsModel.setCity(workOrderStatisticsModelA.getCity());
                   workOrderStatisticsModel.setCityName(workOrderStatisticsModelA.getCityName());
                   workOrderStatisticsModel.setSumNum(workOrderStatisticsModelA.getSumNum());
                   workOrderStatisticsModel.setOvertimeNum(workOrderStatisticsModelA.getOvertimeNum());
                   workOrderStatisticsModel.setUnfiledNumber(workOrderStatisticsModelA.getUnfiledNumber());
                   workOrderStatisticsModel.setArchiveNumber(workOrderStatisticsModelA.getArchiveNumber());
                   if(workOrderStatisticsModel.getSumNum()!=0&&workOrderStatisticsModel.getOvertimeNum()!=0){
                       workOrderStatisticsModel.setOvertimeRate(this.calculateThePercentage(new Double(workOrderStatisticsModel.getOvertimeNum()),new Double(workOrderStatisticsModel.getSumNum())));
                   }else{
                       workOrderStatisticsModel.setOvertimeRate("0%");
                   }
                   r.add(workOrderStatisticsModel);
               }
           }
           
           for(WorkOrderStatisticsModel workOrderStatisticsModelB:b){
        	   
        	   int i = 1;
               for(WorkOrderStatisticsModel workOrderStatisticsModelC:c){
                       if(workOrderStatisticsModelB.getCityName().equals(workOrderStatisticsModelC.getCityName())){
                           i++;
                       }
               }
               if(i==1){
            	   WorkOrderStatisticsModel workOrderStatisticsModel=new WorkOrderStatisticsModel();
                   workOrderStatisticsModel.setCity(workOrderStatisticsModelB.getCity());
                   workOrderStatisticsModel.setCityName(workOrderStatisticsModelB.getCityName());
                   workOrderStatisticsModel.setSumNum(workOrderStatisticsModelB.getSumNum());
                   workOrderStatisticsModel.setOvertimeNum(workOrderStatisticsModelB.getOvertimeNum());
                   workOrderStatisticsModel.setUnfiledNumber(workOrderStatisticsModelB.getUnfiledNumber());
                   workOrderStatisticsModel.setArchiveNumber(workOrderStatisticsModelB.getArchiveNumber());
                   if(workOrderStatisticsModel.getSumNum()!=0&&workOrderStatisticsModel.getOvertimeNum()!=0){
                       workOrderStatisticsModel.setOvertimeRate(this.calculateThePercentage(new Double(workOrderStatisticsModel.getOvertimeNum()),new Double(workOrderStatisticsModel.getSumNum())));
                   }else{
                       workOrderStatisticsModel.setOvertimeRate("0%");
                   }
                   r.add(workOrderStatisticsModel);
               }
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
        Double d = a / b;
        if (d == null || d.equals(100) || d.equals(100.0)|| a.equals(100.00)) {
            return nf1.format(100);
        }
        String r = nf.format(d);
        return r;
    }

	@Override
	public Map<String, Object> workOrderStatisticsDrill(String type,
			String flag, String city, String beginTime, String endTime,
			String subType,int firstIndex,int lastIndex) 
	{
		// TODO Auto-generated method stub
		
		Map<String, Object>  map = new HashMap<String, Object>();
		
		 List<WorkOrderStatisticsDrillModel> r = new ArrayList<WorkOrderStatisticsDrillModel>();
		 List<WorkOrderStatisticsDrillModel> rlist = new ArrayList<WorkOrderStatisticsDrillModel>();
	        if(type.equals("trouble"))
	        {
	        	r=pnrStatisticsJDBCDao.troubleTicketStatisticsDrill(flag,city,beginTime, endTime, subType);
	        }else if(type.equals("task"))
	        {
	            r=pnrStatisticsJDBCDao.taskTicketStatisticsDrill(flag,city,beginTime, endTime, subType);
	        }else if(type.equals("all"))
	        {
	            List<WorkOrderStatisticsDrillModel> task=pnrStatisticsJDBCDao.taskTicketStatisticsDrill(flag,city,beginTime, endTime, subType);
	            for(WorkOrderStatisticsDrillModel a:task)
	            {
	            	r.add(a);
	            }
	            List<WorkOrderStatisticsDrillModel> trouble=  pnrStatisticsJDBCDao.troubleTicketStatisticsDrill(flag,city,beginTime, endTime, subType);
	            for(WorkOrderStatisticsDrillModel b:trouble)
	            {
	            	r.add(b);
	            }
	        }if(type.equals("transferOffice")){
	        	r=pnrStatisticsJDBCDao.transferOfficeTicketStatisticsDrill(flag,city,beginTime, endTime, subType);
	        }
	       
//	        System.out.println("firstIndex:"+firstIndex+"--lastIndex:"+lastIndex);
	    
	        int size = r.size();
	        int maxSize =0;
	        int begin = firstIndex;
	        //当取的值，为最大时
	        if(size>=lastIndex){
	        	maxSize =lastIndex;
	        }else{
	        	maxSize=size;
	        }
//	        System.out.println("size:"+size+"--maxSize:"+maxSize+"--begin:"+begin);
	       
	        for(int index=begin;index<maxSize;index++){
	    	   rlist.add(r.get(index));
	        } 
	        map.put("size",size);
//	        System.out.println("size:"+size+"--maxSize:"+maxSize+"--begin:"+begin);
	       	        
	        map.put("list",rlist);
	 
	     return map;
	}
	public Map<String, Object>transferOfficeStatisticsDrill(String type,String flag,String city,String beginTime, String endTime, String subType,int firstIndex,int lastIndex){
		Map<String, Object>  map = new HashMap<String, Object>();
		
		 List<WorkOrderStatisticsDrillModel> r = new ArrayList<WorkOrderStatisticsDrillModel>();
		 List<WorkOrderStatisticsDrillModel> rlist = new ArrayList<WorkOrderStatisticsDrillModel>();
	       
	        	r=pnrStatisticsJDBCDao.transferOfficeInterfaceStatisticsDrill(type,flag,city,beginTime, endTime, subType);
	        
	        int size = r.size();
	        int maxSize =0;
	        int begin = firstIndex;
	        //当取的值，为最大时
	        if(size>=lastIndex){
	        	maxSize =lastIndex;
	        }else{
	        	maxSize=size;
	        }
	       
	        for(int index=begin;index<maxSize;index++){
	    	   rlist.add(r.get(index));
	        } 
	        map.put("size",size);
	       	        
	        map.put("list",rlist);
	 
	     return map;
	}
	
	
	@Override
	public Map<String, Object> workOrderStatisticsDrillbycity(String type,
			String flag, String city, String beginTime, String endTime,
			String subType,int firstIndex,int lastIndex) 
	{
		// TODO Auto-generated method stub
		
		System.out.println(type);
		Map<String, Object>  map = new HashMap<String, Object>();
		
		 List<WorkOrderStatisticsDrillModel> r = new ArrayList<WorkOrderStatisticsDrillModel>();
		 List<WorkOrderStatisticsDrillModel> rlist = new ArrayList<WorkOrderStatisticsDrillModel>();
	        if(type.equals("trouble"))
	        {
	        	r=pnrStatisticsJDBCDao.troubleTicketStatisticsDrillbycity(flag,city,beginTime, endTime, subType);
	        }else if(type.equals("task"))
	        {
	            r=pnrStatisticsJDBCDao.taskTicketStatisticsDrillbycity(flag,city,beginTime, endTime, subType);
	        }else if(type.equals("all"))
	        {
	            List<WorkOrderStatisticsDrillModel> task=pnrStatisticsJDBCDao.taskTicketStatisticsDrillbycity(flag,city,beginTime, endTime, subType);
	            for(WorkOrderStatisticsDrillModel a:task)
	            {
	            	r.add(a);
	            }
	            List<WorkOrderStatisticsDrillModel> trouble=  pnrStatisticsJDBCDao.troubleTicketStatisticsDrillbycity(flag,city,beginTime, endTime, subType);
	            for(WorkOrderStatisticsDrillModel b:trouble)
	            {
	            	r.add(b);
	            }
	        }if(type.equals("transferOffice")){
	        	r=pnrStatisticsJDBCDao.transferOfficeTicketStatisticsDrillbycity(flag,city,beginTime, endTime, subType);
	        }
	       
//	        System.out.println("firstIndex:"+firstIndex+"--lastIndex:"+lastIndex);
	    
	        int size = r.size();
	        int maxSize =0;
	        int begin = firstIndex;
	        //当取的值，为最大时
	        if(size>=lastIndex){
	        	maxSize =lastIndex;
	        }else{
	        	maxSize=size;
	        }
//	        System.out.println("size:"+size+"--maxSize:"+maxSize+"--begin:"+begin);
	       
	        for(int index=begin;index<maxSize;index++){
	    	   rlist.add(r.get(index));
	        } 
	        map.put("size",size);
//	        System.out.println("size:"+size+"--maxSize:"+maxSize+"--begin:"+begin);
	       	        
	        map.put("list",rlist);
	 
	     return map;
	}
	public Map<String, Object> transferOfficeStatisticsDrillbycity(String type,String flag,String city,String beginTime, String endTime, String subType,int firstIndex,int lastIndex){
		System.out.println(type);
		Map<String, Object>  map = new HashMap<String, Object>();
		
		 List<WorkOrderStatisticsDrillModel> r = new ArrayList<WorkOrderStatisticsDrillModel>();
		 List<WorkOrderStatisticsDrillModel> rlist = new ArrayList<WorkOrderStatisticsDrillModel>();
	        	r=pnrStatisticsJDBCDao.transferOfficeInterfaceStatisticsDrillbycity(type,flag,city,beginTime, endTime, subType);
	       
//	        System.out.println("firstIndex:"+firstIndex+"--lastIndex:"+lastIndex);
	    
	        int size = r.size();
	        int maxSize =0;
	        int begin = firstIndex;
	        //当取的值，为最大时
	        if(size>=lastIndex){
	        	maxSize =lastIndex;
	        }else{
	        	maxSize=size;
	        }
//	        System.out.println("size:"+size+"--maxSize:"+maxSize+"--begin:"+begin);
	       
	        for(int index=begin;index<maxSize;index++){
	    	   rlist.add(r.get(index));
	        } 
	        map.put("size",size);
//	        System.out.println("size:"+size+"--maxSize:"+maxSize+"--begin:"+begin);
	       	        
	        map.put("list",rlist);
	 
	     return map;
	}
	
	
	
	@Override
	public Map<String, Object> workOrderStatisticsDrillbyperson(String person,String type,
			String flag, String city, String beginTime, String endTime,
			String subType,int firstIndex,int lastIndex) 
	{
		// TODO Auto-generated method stub
		
		System.out.println(type);
		Map<String, Object>  map = new HashMap<String, Object>();
		
		 List<WorkOrderStatisticsDrillModel> r = new ArrayList<WorkOrderStatisticsDrillModel>();
		 List<WorkOrderStatisticsDrillModel> rlist = new ArrayList<WorkOrderStatisticsDrillModel>();
	        if(type.equals("trouble"))
	        {
	        	r=pnrStatisticsJDBCDao.troubleTicketStatisticsDrillbyperson(person,flag,city,beginTime, endTime, subType);
	        }else if(type.equals("task"))
	        {
	            r=pnrStatisticsJDBCDao.taskTicketStatisticsDrillbyperson(person,flag,city,beginTime, endTime, subType);
	        }else if(type.equals("all"))
	        {
	            List<WorkOrderStatisticsDrillModel> task=pnrStatisticsJDBCDao.taskTicketStatisticsDrillbyperson(person,flag,city,beginTime, endTime, subType);
	            for(WorkOrderStatisticsDrillModel a:task)
	            {
	            	r.add(a);
	            }
	            List<WorkOrderStatisticsDrillModel> trouble=  pnrStatisticsJDBCDao.troubleTicketStatisticsDrillbyperson(person,flag,city,beginTime, endTime, subType);
	            for(WorkOrderStatisticsDrillModel b:trouble)
	            {
	            	r.add(b);
	            }
	        }else if(type.equals("transferOffice")){
	        	r=pnrStatisticsJDBCDao.transferOfficeTicketStatisticsDrillbyperson(person,flag,city,beginTime, endTime, subType);
	        }
	       
	    
	        int size = r.size();
	        int maxSize =0;
	        int begin = firstIndex;
	        //当取的值，为最大时
	        if(size>=lastIndex){
	        	maxSize =lastIndex;
	        }else{
	        	maxSize=size;
	        }
//	        System.out.println("size:"+size+"--maxSize:"+maxSize+"--begin:"+begin);
	       
	        for(int index=begin;index<maxSize;index++){
	    	   rlist.add(r.get(index));
	        } 
	        map.put("size",size);
//	        System.out.println("size:"+size+"--maxSize:"+maxSize+"--begin:"+begin);
	       	        
	        map.put("list",rlist);
	 
	     return map;
	}
	public Map<String, Object> transferOfficeStatisticsDrillbyperson(String person,String type,String flag,String city,String beginTime, String endTime, String subType,int firstIndex,int lastIndex){
		System.out.println(type);
		Map<String, Object>  map = new HashMap<String, Object>();
		
		 List<WorkOrderStatisticsDrillModel> r = new ArrayList<WorkOrderStatisticsDrillModel>();
		 List<WorkOrderStatisticsDrillModel> rlist = new ArrayList<WorkOrderStatisticsDrillModel>();
	        	r=pnrStatisticsJDBCDao.transferOfficeInterfaceStatisticsDrillbyperson(type,person,flag,city,beginTime, endTime, subType);
	       
	    
	        int size = r.size();
	        int maxSize =0;
	        int begin = firstIndex;
	        //当取的值，为最大时
	        if(size>=lastIndex){
	        	maxSize =lastIndex;
	        }else{
	        	maxSize=size;
	        }
//	        System.out.println("size:"+size+"--maxSize:"+maxSize+"--begin:"+begin);
	       
	        for(int index=begin;index<maxSize;index++){
	    	   rlist.add(r.get(index));
	        } 
	        map.put("size",size);
//	        System.out.println("size:"+size+"--maxSize:"+maxSize+"--begin:"+begin);
	       	        
	        map.put("list",rlist);
	 
	     return map;
	}
	
	
	
	
	@Override
	public Map<String,Object> statisticsPartnerIndexDrill(
			String flag, String city, String beginTime, String endTime,
			String subType,String level,String person,int firstIndex,int lastIndex) {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String, Object>();
		List<WorkOrderStatisticsDrillModel> r = new ArrayList<WorkOrderStatisticsDrillModel>();
		List<WorkOrderStatisticsDrillModel> rlist = new ArrayList<WorkOrderStatisticsDrillModel>();
       
		if(flag.equals("1"))
        {
        	r=pnrStatisticsJDBCDao.TroubleStatisticsPartnerIndexDrill(city,beginTime, endTime, subType,level,person);
        }else if(flag.equals("2"))
        {
            r=pnrStatisticsJDBCDao.TaskStatisticsPartnerIndexDrill(city,beginTime, endTime, subType,level,person);
        }
		
//      分页获取数据
        int size = r.size();
        int maxSize =0;
        int begin = firstIndex;
        //当取的值，为最大时
        if(size>=lastIndex){
        	maxSize =lastIndex;
        }else{
        	maxSize=size;
        }
       
        for(int index=begin;index<maxSize;index++){
    	   rlist.add(r.get(index));
        } 
        map.put("size",size);
       	        
        map.put("list",rlist);
   return map;
	}
    @Override
    public Map<String,Object> statisticsPartnerIndexDrill3(
            String flag, String city, String beginTime, String endTime,
            String subType,String level,String person,int firstIndex,int lastIndex) {
        // TODO Auto-generated method stub  在途统计查看工单详情
        
    	Map<String,Object> map = new HashMap<String,Object>();
    	
    	List<WorkOrderStatisticsDrillModel> r = new ArrayList<WorkOrderStatisticsDrillModel>();
    	List<WorkOrderStatisticsDrillModel> rlist = new ArrayList<WorkOrderStatisticsDrillModel>();

        if(flag.equals("1"))
        {
            r=pnrStatisticsJDBCDao.TroubleStatisticsPartnerIndexDrill3(city,beginTime, endTime, subType,level,person);
        }else if(flag.equals("2"))
        {
            r=pnrStatisticsJDBCDao.TaskStatisticsPartnerIndexDrill3(city,beginTime, endTime, subType,level,person);
        }
//      分页获取数据
        int size = r.size();
        int maxSize =0;
        int begin = firstIndex;
        //当取的值，为最大时
        if(size>=lastIndex){
        	maxSize =lastIndex;
        }else{
        	maxSize=size;
        }
       
        for(int index=begin;index<maxSize;index++){
    	   rlist.add(r.get(index));
        } 
        map.put("size",size);
       	        
        map.put("list",rlist);
        
        
        return map;
    }

    @Override
	public List<WorkOrderStatisticsModel2> workOrderStatistics2(String city,
			String beginTime, String endTime) {
		// TODO Auto-generated method stub
		return pnrStatisticsJDBCDao.workOrderStatistics2(city,beginTime, endTime);
	}

	@Override
	public List<WorkOrderStatisticsModel2> workOrderStatistics2Person(
			String country, String beginTime, String endTime) {
		// TODO Auto-generated method stub
		return pnrStatisticsJDBCDao.workOrderStatistics2Person(country,beginTime, endTime);

	}
	@Override
	public List<WorkOrderStatisticsModel2> workOrderStatistics3(String city,
			String beginTime, String endTime) {
		// TODO Auto-generated method stub
		return pnrStatisticsJDBCDao.workOrderStatistics3(city,beginTime, endTime);
	}
	
	@Override
	public List<WorkOrderStatisticsModel2> workOrderStatistics2Person3(
			String country, String beginTime, String endTime) {
		// TODO Auto-generated method stub
		return pnrStatisticsJDBCDao.workOrderStatistics2Person3(country,beginTime, endTime);
		
	}

	@Override
	public List<WorkOrderStatisticsModel> workOrderStatisticsbyCity(
			String city,String cityname,String type,String beginTime,String endTime,String subType) {
		// TODO Auto-generated method stub
		List<WorkOrderStatisticsModel> r=null;
        if(type.equals("trouble")){
        r=pnrStatisticsJDBCDao.troubleTicketWorkOrderStatisticsbycity(city,beginTime, endTime, subType);
        }
        if(type.equals("task")){
            r=pnrStatisticsJDBCDao.taskTicketWorkOrderStatisticsbycity(city,beginTime,endTime,subType);
        }
        if(type.equals("all")){
            List<WorkOrderStatisticsModel> task=pnrStatisticsJDBCDao.taskTicketWorkOrderStatisticsbycity(city,beginTime,endTime,subType);
            List<WorkOrderStatisticsModel> trouble=  pnrStatisticsJDBCDao.troubleTicketWorkOrderStatisticsbycity(city,beginTime, endTime, subType);
            r=this.add(task,trouble);
        }if(type.equals("transferOffice")){
        	r=pnrStatisticsJDBCDao.transferOfficeWorkOrderStatisticsbycity(city,beginTime,endTime,subType);
        }
        WorkOrderStatisticsModel all=new WorkOrderStatisticsModel();
        all.setCityName(cityname);
        all.setCity(city);
        all.setCitylength(4);

          for(WorkOrderStatisticsModel workOrderStatisticsModel:r){
              all.setSumNum(all.getSumNum()+workOrderStatisticsModel.getSumNum());
              all.setOvertimeNum(all.getOvertimeNum() + workOrderStatisticsModel.getOvertimeNum());
              all.setUnfiledNumber(all.getUnfiledNumber() + workOrderStatisticsModel.getUnfiledNumber());
              all.setArchiveNumber(all.getArchiveNumber() + workOrderStatisticsModel.getArchiveNumber());
          }
        if(all.getSumNum()!=0&&all.getOvertimeNum()!=0){
            all.setOvertimeRate(this.calculateThePercentage(new Double(all.getOvertimeNum()),new Double(all.getSumNum())));
        }else{
            all.setOvertimeRate("0%");
        }
        r.add(all);
        return r;
	}
	public List<WorkOrderStatisticsModel> transferOfficeStatisticsbyCity(String city,String cityname,String type,String beginTime, String endTime, String subType){
		List<WorkOrderStatisticsModel> r=null;
                	r=pnrStatisticsJDBCDao.transferOfficeInterfaceStatisticsbycity(type,city,beginTime,endTime,subType);
        
        WorkOrderStatisticsModel all=new WorkOrderStatisticsModel();
        all.setCityName(cityname);
        all.setCity(city);
        all.setCitylength(4);

          for(WorkOrderStatisticsModel workOrderStatisticsModel:r){
              all.setSumNum(all.getSumNum()+workOrderStatisticsModel.getSumNum());
              all.setOvertimeNum(all.getOvertimeNum() + workOrderStatisticsModel.getOvertimeNum());
              all.setUnfiledNumber(all.getUnfiledNumber() + workOrderStatisticsModel.getUnfiledNumber());
              all.setArchiveNumber(all.getArchiveNumber() + workOrderStatisticsModel.getArchiveNumber());
          }
        if(all.getSumNum()!=0&&all.getOvertimeNum()!=0){
            all.setOvertimeRate(this.calculateThePercentage(new Double(all.getOvertimeNum()),new Double(all.getSumNum())));
        }else{
            all.setOvertimeRate("0%");
        }
        r.add(all);
        return r;
	}
	@Override
	public Map<String,Object> workOrderStatisticsbyCountry(
			String city,String type,String beginTime,String endTime,String subType,int firstIndex,int lastIndex) {
		// TODO Auto-generated method stub
        Map<String,Object> map = new HashMap<String,Object>();
    	List<WorkOrderStatisticsModel> r = new ArrayList<WorkOrderStatisticsModel>();
    	List<WorkOrderStatisticsModel> rlist = new ArrayList<WorkOrderStatisticsModel>();
        if(type.equals("trouble")){
        r=pnrStatisticsJDBCDao.troubleTicketWorkOrderStatisticsbycountry(city,beginTime, endTime, subType);
        }
        if(type.equals("task")){
            r=pnrStatisticsJDBCDao.taskTicketWorkOrderStatisticsbycountry(city,beginTime,endTime,subType);
        }
        if(type.equals("all")){
            List<WorkOrderStatisticsModel> task=pnrStatisticsJDBCDao.taskTicketWorkOrderStatisticsbycountry(city,beginTime,endTime,subType);
            List<WorkOrderStatisticsModel> trouble=  pnrStatisticsJDBCDao.troubleTicketWorkOrderStatisticsbycountry(city,beginTime, endTime, subType);
            r=this.add1(task,trouble);
            
        }if(type.equals("transferOffice")){
        	 r=pnrStatisticsJDBCDao.transferOfficeWorkOrderStatisticsbycountry(city,beginTime,endTime,subType);
        }
        
//      分页获取数据
        int size = r.size();
        int maxSize =0;
        int begin = firstIndex;
        //当取的值，为最大时
        if(size>=lastIndex){
        	maxSize =lastIndex;
        }else{
        	maxSize=size;
        }
       
        for(int index=begin;index<maxSize;index++){
    	   rlist.add(r.get(index));
        } 
        map.put("size",size);
       	        
        map.put("list",rlist);
        
        
        return map;
	}
	public Map<String, Object> transferOfficeStatisticsbyCountry(String city,String type,String beginTime, String endTime, String subType,int firstIndex,int lastIndex){
		Map<String,Object> map = new HashMap<String,Object>();
    	List<WorkOrderStatisticsModel> r = new ArrayList<WorkOrderStatisticsModel>();
    	List<WorkOrderStatisticsModel> rlist = new ArrayList<WorkOrderStatisticsModel>();
       
        	 r=pnrStatisticsJDBCDao.transferOfficeInterfaceStatisticsbycountry(type,city,beginTime,endTime,subType);
        
        
//      分页获取数据
        int size = r.size();
        int maxSize =0;
        int begin = firstIndex;
        //当取的值，为最大时
        if(size>=lastIndex){
        	maxSize =lastIndex;
        }else{
        	maxSize=size;
        }
       
        for(int index=begin;index<maxSize;index++){
    	   rlist.add(r.get(index));
        } 
        map.put("size",size);
       	        
        map.put("list",rlist);
        
        
        return map;
	}
	
	/**
	 * 统计预检预修项目详情表
	 * @param  themeinterface
	 * @param  taskdefkey
	 * @param  quarter
	 * 地市
	 * @return
	 */
	public List<PreflightStatisticPartnerModel> findPreflightStatisticCityHis(String themeinterface,String taskdefkey,String quarter){
		return pnrStatisticsJDBCDao.findPreflightStatisticCityHis( themeinterface, taskdefkey,quarter);
	}
	
	/**
	 * 统计预检预修项目详情表
	 * @param year
	 * @param month
	 * @param excelType 报表类型
	 * 区县
	 * @return
	 */
	public List<PreflightStatisticPartnerModel> findPreflightStatisticCountryHis(String themeinterface,String taskdefkey,String quarter,String city){
		return pnrStatisticsJDBCDao.findPreflightStatisticCountryHis(themeinterface,taskdefkey,quarter,city);
	}
	
	 /**
	 * 统计预检预修项目详情表
	 * @param year
	 * @param month
	 * @param excelType 报表类型
	 * @return
	 */
	public List<PreflightDetailStatisticPartnerModel> findPreflightDatilStatisticCityHis(String year,String month,String excelType, int firstResult,
			int endResult, int pageSize){
		return pnrStatisticsJDBCDao.findPreflightDatilStatisticCityHis(year, month,excelType,firstResult,endResult,pageSize);
		
	}
	
	/**
	 * 统计预检预修项目详情表数量
	 * @param year
	 * @param month
	 * @param excelType 报表类型
	 * @return
	 */
	public int findPreflightDatilStatisticCityHisCount(String year,String month,String excelType){
		return pnrStatisticsJDBCDao.findPreflightDatilStatisticCityHisCount(year, month,excelType);
		
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
		return pnrStatisticsJDBCDao.findWeeklyStatisticHis(sendStartTime,sendEndTime,region,country,themeinterface,taskdefkey,firstResult,endResult,pageSize);
		
	}
}
