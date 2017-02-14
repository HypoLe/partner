package com.boco.eoms.worksheet;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.FormService;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.TaskFormData;

import com.boco.activiti.partner.process.model.TransferMaleGuestInform;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.common.log.BocoLog;
import com.boco.eoms.util.InterfaceUtil;

public class TaskFlowsService {
	
	private FormService formService ;
	
	/**
	 * 统一的入口：提交到下一环节
	 * 代码先整体写这，待测试通过在整理 流程api类
	 * @param msg
	 * @return
	 */
	public String submitTaskFormData(String msg){
	    BocoLog.info(this,27,"TaskFlowsService-submitTaskFormData-接收的报文:"+msg);
	
		Map<String,Object> keyMap = getValueBymsg(msg);
		String taskId = (String)keyMap.get("taskId");
		
		formService= (FormService) ApplicationContextHolder
		.getInstance().getBean("formService");
		TaskFormData taskFormData = formService.getTaskFormData(taskId);
		Map<String, String> map = new HashMap<String, String>();
		for (FormProperty formProperty : taskFormData.getFormProperties()) {
			if (formProperty.isWritable()) {
				String name = formProperty.getId();
				map.put(name, (String) keyMap.get(name));
				BocoLog.info(this,38,"TaskFlowsService-submitTaskFormData-传递的参数:流程引擎-"+name+":"+keyMap.get(name));
			}
		}
		formService.submitTaskFormData(taskId, map);
		BocoLog.info(this,42,"TaskFlowsService-submitTaskFormData-流程引擎提交结果:提交完成@！");
		
		return null;
	}
	/**
	 * 把接口发过来的报文串，存储到Map中，待用
	 * @param msg
	 * @return
	 */
	private Map<String,Object> getValueBymsg(String msg){
		Map<String,Object> map = new HashMap<String,Object>();
		InterfaceUtil doc = new InterfaceUtil();
		HashMap sheetMapOpDetail = new HashMap();
		String workOrderNo="";
		
		//解析成xml形式 
		msg =doc.changeCharaterOpposite(msg);
		String xpathOpDetail="//opDetail/recordInfo/fieldInfo";
		try {
			sheetMapOpDetail = doc.xmlElements(msg, sheetMapOpDetail, "FieldContent",xpathOpDetail);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return sheetMapOpDetail;
	}

}
