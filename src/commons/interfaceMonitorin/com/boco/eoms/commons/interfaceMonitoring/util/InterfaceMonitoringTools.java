package com.boco.eoms.commons.interfaceMonitoring.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.EOMSAttributes;
import com.boco.eoms.base.util.StaticMethod;

import com.boco.eoms.commons.interfaceMonitoring.dao.hibernate.InterfaceMonitoringDaoHibernate;
import com.boco.eoms.commons.interfaceMonitoring.mgr.InterfaceMonitoringMgr;
import com.boco.eoms.commons.interfaceMonitoring.model.InterfaceMonitoring;
import com.boco.eoms.sequence.ISequenceFacade;
import com.boco.eoms.sequence.Sequence;
import com.boco.eoms.sequence.exception.SequenceNotFoundException;
import com.boco.eoms.sequence.util.SequenceLocator;

public class InterfaceMonitoringTools {

	/**
	 * @see 接口监控日志打印方法
	 * @return void
	 * @param 参数名称 Map map 
	 *           
	 *            
	 *            
	 *            
	 *            
		 */
	public void interfaceMonitoringLlogout(Map map) {
	    for(Iterator it = map.entrySet().iterator(); it.hasNext();){   

            System.out.println(it.next());

        } 
	}
	/**
	 * @see 接口监控日志入库
	 * @return void
	 * @param 参数名称 Map map 
	 *           
	 *            
	 *            
	 *            
	 *            
		 */
	public void interfaceMonitoringLlog(Map map,String result,String interFaceType,String interFaceMethod)throws Exception {
//		this.interfaceMonitoringLlogout(map);
//		try{
		String text="";
	    for(Iterator it = map.entrySet().iterator(); it.hasNext();){
	    	if(!"".equals(StaticMethod.nullObject2String(it.next()))){
	    		if(it.next().toString().length()>1000){
	    			text+=it.next().toString().substring(0,30)+"...更多"+"&#13";
	    			
	    		}else{
	    			text+=it.next().toString()+"&#13";	
	    		}
	    	}
	    	
	    
            System.out.println(it.next());

        } 
	  
	    System.out.println(text);
	    InterfaceMonitoring interfaceMonitoring = new InterfaceMonitoring();
	    interfaceMonitoring.setInterFaceType(interFaceType);
	    interfaceMonitoring.setMethod(interFaceMethod);
	    String serSupplier=StaticMethod.nullObject2String(map.get("serSupplier"));
	    interfaceMonitoring.setSerSupplier(serSupplier);
	    interfaceMonitoring.setSerCaller(StaticMethod.nullObject2String(map.get("serCaller")));
	    interfaceMonitoring.setSheetKey(StaticMethod.nullObject2String(map.get("sheetkey")));
	    interfaceMonitoring.setCallingSide(StaticMethod.nullObject2String(map.get("serCaller")));
	    interfaceMonitoring.setProvider(serSupplier);
//	    interfaceMonitoring.set
		if(serSupplier.equals("EOMS")){
			interfaceMonitoring.setCallDirection("Horizontal");	
		}
		else{
		interfaceMonitoring.setCallDirection("Longitudinal");
		}
		interfaceMonitoring.setSuccess(result);
		interfaceMonitoring.setExceptionLog(result);
		interfaceMonitoring.setInterFaceMethod("Web Service");
	    interfaceMonitoring.setCallingTime(StaticMethod.getLocalString());
	    interfaceMonitoring.setText(text);
		this.saveInterfaceMonitoring(interfaceMonitoring);//接口调用将接口调用操作任务加入队列
	}
	
	public void saveInterfaceMonitoring(InterfaceMonitoring interfaceMonitoring) {
		String sequenceOpen = StaticMethod		
		.null2String(((EOMSAttributes) ApplicationContextHolder
				.getInstance().getBean("eomsAttributes"))
				.getSequenceOpen());
		InterfaceMonitoringMgr mgr = (InterfaceMonitoringMgr) ApplicationContextHolder
		.getInstance().getBean("InterfaceMonitoringMgr");
		if ("true".equals(sequenceOpen)) {
			// 初始化队列
			ISequenceFacade sequenceFacade = SequenceLocator
					.getSequenceFacade();
			Sequence closemsgSequence = null;
			try {
				closemsgSequence = sequenceFacade.getSequence("Interface");
			} catch (SequenceNotFoundException e) {
				e.printStackTrace();
			}			
			// 把mgr撇队列里
			sequenceFacade.put(mgr, "saveInterfaceMonitoring", 
					new Class[] {java.lang.Object.class},
					new Object[] {interfaceMonitoring}, null,
					closemsgSequence);
			closemsgSequence.setChanged();
			sequenceFacade.doJob(closemsgSequence);
		} else {
//			mMgr.closeMsg(interfaceMonitoring);
			mgr.saveInterfaceMonitoring(interfaceMonitoring);
		}
		
	}
	public static void main(String[] args) {
		 Map test = new HashMap();
		 InterfaceMonitoringTools interfaceMonitoringTools =new InterfaceMonitoringTools();
		 test.put("test1", "111");
		 test.put("test2", "222");
		 interfaceMonitoringTools.interfaceMonitoringLlogout(test);

	}

}
