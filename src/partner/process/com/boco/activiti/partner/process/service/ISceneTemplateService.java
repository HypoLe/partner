package com.boco.activiti.partner.process.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * 场景模板 服务类
 * 
 * 涉及到场景模板的方法，统一写在这个方法里，以便日后复用
 * 
 * @author Jun
 *
 */
public interface ISceneTemplateService  {
	
	//删除流程涉及到的场景模板的数据
	//流程号，涉及到的场景模板的数据的类型，其他参数（待定）
	//先清除原来存储的场景相关存储数据
	public Map<String,Object> deleteSceneTemplate(String processInstanceId,String linkType,Map<String,String> param);
	
	//保存场景模板的数据
	//流程号，涉及到的场景模板的数据的类型，其他参数（待定）
	public Map<String,Object> saveSceneTemplate(HttpServletRequest request,String processInstanceId,String linkType,Map<String,String> param);
	
	//回显
	public Map<String,Object> echoChildScene(String processInstanceId,String linkType,Map<String,String> param);
	
	
	
	//查看详情
	//流程号，涉及到的场景模板的数据的类型，其他参数（待定）
	
	//导出场景模板数据
	//流程号，涉及到的场景模板的数据的类型，其他参数（待定）
	
	//获取未被选择的"其他费用（手工填写）"子场景
	public Map<String,Object> loadNotChosenOtherCostsSubScene(String processInstanceId,String linkType,Map<String,String> param); 
	
	//查看子场景详情
	public Map<String,Object> checkChildSceneForDetails(String processInstanceId,String linkType,Map<String,String> param);
	
	//查看材料（主材或者辅材）
	public Map<String,Object> checkMaterialForDetails(String processInstanceId,String linkType,Map<String,String> param);
	
	//导出场景模板
	public HSSFWorkbook scenarioTemplateExcel(String processInstanceId,String linkType,Map<String,String> param);
	
	//查看场景模板是否有数据
	public int countSceneTemplateNum(String processInstanceId,String linkType,Map<String,String> param);
	
}
