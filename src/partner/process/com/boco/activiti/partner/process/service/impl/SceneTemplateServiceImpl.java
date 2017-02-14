package com.boco.activiti.partner.process.service.impl;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFHyperlink;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import com.boco.activiti.partner.process.model.MasteDataTask;
import com.boco.activiti.partner.process.model.MasteRelTaskItem;
import com.boco.activiti.partner.process.model.MasteSelCopyTask;
import com.boco.activiti.partner.process.po.SceneTableModel;
import com.boco.activiti.partner.process.service.IMasteDataTaskService;
import com.boco.activiti.partner.process.service.IMasteRelTaskItemService;
import com.boco.activiti.partner.process.service.IMasteSelCopyTaskService;
import com.boco.activiti.partner.process.service.ISceneTemplateService;
import com.boco.activiti.partner.process.webapp.action.ExcelUtils;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.deviceManagement.common.service.CommonSpringJdbcService;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

/**
 * 场景模板 服务类 实现
 * 
 * 涉及到场景模板的方法，统一写在这个方法里，以便日后复用
 * 
 * @author Jun
 *
 */

public class SceneTemplateServiceImpl implements ISceneTemplateService {
	
	private IMasteRelTaskItemService masteRelTaskItemService;
	private IMasteSelCopyTaskService masteSelCopyTaskService;
	private IMasteDataTaskService masteDataTaskService;
	private CommonSpringJdbcService commonSpringJdbcService;
	
	public IMasteRelTaskItemService getMasteRelTaskItemService() {
		return masteRelTaskItemService;
	}
	public void setMasteRelTaskItemService(
			IMasteRelTaskItemService masteRelTaskItemService) {
		this.masteRelTaskItemService = masteRelTaskItemService;
	}
	public IMasteSelCopyTaskService getMasteSelCopyTaskService() {
		return masteSelCopyTaskService;
	}
	public void setMasteSelCopyTaskService(
			IMasteSelCopyTaskService masteSelCopyTaskService) {
		this.masteSelCopyTaskService = masteSelCopyTaskService;
	}
	public IMasteDataTaskService getMasteDataTaskService() {
		return masteDataTaskService;
	}
	public void setMasteDataTaskService(IMasteDataTaskService masteDataTaskService) {
		this.masteDataTaskService = masteDataTaskService;
	}
	public CommonSpringJdbcService getCommonSpringJdbcService() {
		return commonSpringJdbcService;
	}
	public void setCommonSpringJdbcService(
			CommonSpringJdbcService commonSpringJdbcService) {
		this.commonSpringJdbcService = commonSpringJdbcService;
	}
	
	
	public Map<String,Object> deleteSceneTemplate(String processInstanceId,String linkType,Map<String,String> param){
		String whereSql="";
		if(linkType!=null&&!"".equals(linkType)){	//涉及到的场景模板是那个类型：新建、回单、抽查
			whereSql+=" and k.link_type='"+linkType+"'";
		}
		
		if(param!=null){//添加其他参数
			
		}
		
		String delsql1 ="delete from maste_sel_copy_task k where k.process_instance_id='"+processInstanceId+"'"+whereSql;
		System.out.println("------delsql1="+delsql1);
		commonSpringJdbcService.executeProcedure(delsql1);
		String delsql2 ="delete from maste_rel_task_item k where k.process_instance_id='"+processInstanceId+"'"+whereSql;
		System.out.println("------delsql2="+delsql2);
		commonSpringJdbcService.executeProcedure(delsql2);
		String delsql3 ="delete from maste_data_task k where k.process_instance_id='"+processInstanceId+"'"+whereSql;
		System.out.println("------delsql3="+delsql3);
		commonSpringJdbcService.executeProcedure(delsql3);
		
		return null;
	}
	
	public Map<String,Object> saveSceneTemplate(HttpServletRequest request,String processInstanceId,String linkType,Map<String,String> param){
		
		/* 0、针对二次界面问题
	     * 1、将场景模板的获取所有子场景id获取到
	     * 2、根据子场景id，获取属性字段
	     * 3、通过子场景id和属性对应的编码组成的名字获取一组值；（定额存储）
	     * 4、存储选择的主材、辅材信息；
	     * 5、完毕 
	     */
		
		String childSceneIds = request.getParameter("childSceneIds");
		String[] childSenceIdStr=childSceneIds.split(",");
		int idsSize = childSenceIdStr.length;
		
		String childSceneId = "";
		String copy_thread_strs="";
		String task_rel_strs_m="";
		String task_rel_strs="";
		
		//存储工单选择的子场景信息
		String quotaField ="";
		String existQuotaValue ="";
		String assistExistQuotaValue ="";
		
		//存储创建和拆除字段
		//定义新建和拆除字符串（用来拼接属于各类型的子场景）
		String createStr="";//新建字符串
		String deleteStr="";//拆除字符串
		
		if (childSceneIds != null && !"".equals(childSceneIds)) {//当子场景ids串存在时，才保存场景信息
			
			for(int i=0;i<idsSize;i++){
				MasteSelCopyTask masteSelCopyTask = new MasteSelCopyTask();
				childSceneId = childSenceIdStr[i];
				quotaField =  request.getParameter(childSceneId+"_quotaField");
				existQuotaValue = request.getParameter(childSceneId+"_existQuotaValue");
				assistExistQuotaValue = request.getParameter(childSceneId+"_assistExistQuotaValue");
				masteSelCopyTask.setChildSceneId(childSceneId);
				masteSelCopyTask.setQuotaField(quotaField);
				masteSelCopyTask.setExistquotaValue(existQuotaValue);
				masteSelCopyTask.setAssistExistquotaValue(assistExistQuotaValue);
				masteSelCopyTask.setProcessInstanceId(processInstanceId);
				masteSelCopyTask.setSeq(i);
				masteSelCopyTask.setLinkType(linkType);//环节类型
				masteSelCopyTaskService.save(masteSelCopyTask);
			}
			
//			存储定额信息
			
			for(int i=0;i<idsSize;i++){
				childSceneId = childSenceIdStr[i];
				
				String tabFieldSql = "select qt.task_rel_strs,qt.copy_thread_strs,qt.task_rel_strs_m from maste_condition_rel_quota qt where qt.copy_no='"+childSceneId+"'";
				List<ListOrderedMap> resultList = commonSpringJdbcService.queryForList(tabFieldSql);			
				
				for (ListOrderedMap listOrderedMap : resultList) {
					
					 copy_thread_strs =((String)listOrderedMap.get("copy_thread_strs"));
					 task_rel_strs_m =((String)listOrderedMap.get("task_rel_strs_m"));
					 task_rel_strs =((String)listOrderedMap.get("task_rel_strs"));
					
				}
				
				String[] copy_thread_array = copy_thread_strs.split("#");
				String[] task_rel_array = task_rel_strs_m.split("#");
				int threadArraySize = copy_thread_array.length;

				//计算需要多少定额个对象--当前子场景
				String[] parameterArray = request.getParameterValues(childSceneId+"_"+copy_thread_array[0]);
				int objectSize = parameterArray==null?0:parameterArray.length;
				
//				主材、辅材存储
				String[] main = request.getParameterValues(childSceneId+"_main");
				String[] assist = request.getParameterValues(childSceneId+"_assist");
				String[] quotaValue = request.getParameterValues(childSceneId+"_quotaValue");
				String[] inforMain = request.getParameterValues(childSceneId+"_inforMain");
				String[] inforAssist = request.getParameterValues(childSceneId+"_inforAssist");
				String[] totalCostMain = request.getParameterValues(childSceneId+"_totalCostMain");
				String[] totalCostAssist = request.getParameterValues(childSceneId+"_totalCostAssist");
				
				String[] itemNo = request.getParameterValues(childSceneId+"_itemNo");
				String[] uniqueId = request.getParameterValues(childSceneId+"_uniqueId");

//				List<MaterialQuotaItem> list = new ArrayList<MaterialQuotaItem>();
				
				int seq = 1;
				for(int j=0;j<objectSize;j++ ){//当前子场景-对象的个数循环
					
					MasteRelTaskItem tm = new MasteRelTaskItem();
					//动态的设置值--当前子场景
					for(int k=0;k<threadArraySize;k++){//当前子场景-1个对象的各个属性赋值
						String[] cpStrings = request.getParameterValues(childSceneId+"_"+copy_thread_array[k]);
						Field field;
						try {
							field = tm.getClass().getDeclaredField(task_rel_array[k]);
							field.setAccessible(true);
							field.set(tm,cpStrings==null?null:cpStrings[j]);
						} catch (SecurityException e) {
							e.printStackTrace();
						} catch (NoSuchFieldException e) {
							e.printStackTrace();
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						}
						 
					}
					
					tm.setProcessInstanceId(processInstanceId);
					tm.setCopyNo(childSceneId);
					tm.setChildSceneId(childSceneId);
					tm.setAssistHid(assist[j]);
					tm.setMainHid(main[j]);
					tm.setQuotavalueHid(quotaValue[j]);
					tm.setInforassistHid(inforAssist[j]);
					tm.setInformainHid(inforMain[j]);
					tm.setTotalcostassistHid(totalCostAssist[j]);
					tm.setTotalcostmainHid(totalCostMain[j]);
					
					tm.setItemNo(itemNo[j]);
					tm.setUniqueId(uniqueId[j]);
					
					//主材、辅材
					
					int seqMain = 1;//主材排序
					String[] inforMainOneStr =inforMain[j].split(";");
					for(int m=0;m<inforMainOneStr.length;m++){
						String[] msgStrings = inforMainOneStr[m].split(",");
						
					
						if(msgStrings.length==6){
							MasteDataTask dataTask = new MasteDataTask();
							dataTask.setDataId(msgStrings[0]);
							dataTask.setNum(msgStrings[1]==null?0:Double.parseDouble(msgStrings[1]));
							dataTask.setPerPrice(msgStrings[2]==null?0:Double.parseDouble(msgStrings[2]));
							dataTask.setTotalPrice(msgStrings[3]==null?0:Double.parseDouble(msgStrings[3]));
							dataTask.setIsWhetherOld(msgStrings[4]);
							dataTask.setItemNo(msgStrings[5]);
							dataTask.setProcessInstanceId(processInstanceId);
							dataTask.setUniqueId(uniqueId[j]);
							dataTask.setSeq(seqMain++);//排序
							dataTask.setType("1");	
							dataTask.setLinkType(linkType);//环节类型
							masteDataTaskService.save(dataTask);
						}
						
					}
					
					int seqAss = 1;//辅材排序
					String[] inforassistOneStr =inforAssist[j].split(";");
					for(int n=0;n<inforassistOneStr.length;n++){
						String[] msgStrs = inforassistOneStr[n].split(",");
						
						if(msgStrs.length==6){
							MasteDataTask dataTask = new MasteDataTask();
							dataTask.setDataId(msgStrs[0]);
							dataTask.setNum(msgStrs[1]==null?0:Double.parseDouble(msgStrs[1]));
							dataTask.setPerPrice(msgStrs[2]==null?0:Double.parseDouble(msgStrs[2]));
							dataTask.setTotalPrice(msgStrs[3]==null?0:Double.parseDouble(msgStrs[3]));
							dataTask.setIsWhetherOld(msgStrs[4]);
							dataTask.setItemNo(msgStrs[5]);
							dataTask.setProcessInstanceId(processInstanceId);
							dataTask.setUniqueId(uniqueId[j]);
							dataTask.setSeq(seqAss++);//排序
							dataTask.setType("0");	
							dataTask.setLinkType(linkType);//环节类型
							masteDataTaskService.save(dataTask);

						}
						
					}
	                
					tm.setSeq(seq++);//排序
					tm.setLinkType(linkType);
					masteRelTaskItemService.save(tm);	
				}
			
				//统计新建和拆除--start
				//1.循环体外定义新建字符串和拆除字符串
				//2.根据子场景id判断该子场景属于新建还是拆除
				//3.拼串入库
				System.out.println("这个子场景的第个"+i+"对象");
				if(childSceneId!=null && !"".equals(childSceneId)){
					String flag="";
					String flagSql="select mcs.is_new from MASTE_COPY_SCENE mcs where mcs.copy_no='"+childSceneId+"'";
					flag = commonSpringJdbcService.selChildFlag(flagSql);
					double allSum = 0;
					String unit="";
					if(flag!=null && !"0".equals(flag.trim())){
						String[] danwei = request.getParameterValues(childSceneId+"_rsvu");
						String[] sum = request.getParameterValues(childSceneId+"_rfgz");
						for(int k=0;k<danwei.length;k++){
							allSum+=Double.parseDouble(sum[k]==null?"0":sum[k].toString());
							unit = danwei[k];
						}
					}
					String childSql = "select mcs.copy_name from MASTE_COPY_SCENE mcs where mcs.copy_no='"+childSceneId+"'";
					String unitSql="select mct.tbody_name from MASTE_COPY_TBODY mct where mct.tbody_no='"+unit+"'";
					
					if(flag!=null && "1".equals(flag.trim())){//新建
						createStr = commonSpringJdbcService.addChildStr(createStr, childSql, unitSql, allSum);
					}else if(flag!=null && "2".equals(flag.trim())){//拆除
						deleteStr = commonSpringJdbcService.addChildStr(deleteStr, childSql, unitSql, allSum);
					}
				}
				
				//统计新建和拆除--end
				
				
			}
		}
		
		//返回结果
		Map<String,Object> result=new HashMap<String,Object>();
		result.put("createStr", createStr);
		result.put("deleteStr", deleteStr);
		
		return result;
	}
	
	//回显
	public Map<String,Object> echoChildScene(String processInstanceId,String linkType,Map<String,String> param){
				
		List<SceneTableModel> echoChildSceneTableList=new ArrayList<SceneTableModel>();

		Search search = new Search();
		search.addFilterEqual("processInstanceId", processInstanceId);
		search.addFilterEqual("linkType", linkType);
		search.addSortAsc("seq");
		SearchResult<MasteSelCopyTask> resultTask=masteSelCopyTaskService.searchAndCount(search);
		List<MasteSelCopyTask> resultTaskList =resultTask.getResult();
		
		int num = resultTaskList.size();
		
		String childSceneIds="";
		String oneChildSceneId="";
		String oneChildSceneName="";//子场景中文值
		String quotaField ="";
		String type =""; //子场景表头-各个的类型
		String theadNo ="";//子场景表头-编号
		String className ="";//子场景表头-
		String isrequire ="";//子场景表头-
		String twoTr="";//子场景第一行数据
		
		SceneTableModel sceneTableModel =null;
		MasteSelCopyTask  entity=null;
		
		for(int i=0;i<num;i++){//子场景循环
		
			sceneTableModel=new SceneTableModel();

			entity= resultTaskList.get(i);
			//设置子场景id值
			oneChildSceneId=entity.getChildSceneId();
			if(oneChildSceneId!=null&&!"".equals(oneChildSceneId)){//判断获取到的子场景id是否为空
				
				sceneTableModel.setChildSceneId(oneChildSceneId);
				childSceneIds+=oneChildSceneId+",";
				//定额字段 用子场景id和字段名以"_"拼接
				quotaField=entity.getQuotaField();
				sceneTableModel.setQuotaField(quotaField);
				//主材已选择的定额值 //已选的定额--主材
				String existQuotaValue=entity.getExistquotaValue();
				sceneTableModel.setExistQuotaValue(existQuotaValue);
				//辅材已选择的定额值 //已选的定额--辅材
				String assistExistQuotaValue=entity.getAssistExistquotaValue();
				sceneTableModel.setAssistExistQuotaValue(assistExistQuotaValue);
				
				// 表头-数据
				//CommonSpringJdbcService jdbcService = (CommonSpringJdbcService) ApplicationContextHolder.getInstance().getBean("commonSpringJdbcService");
				String searchSql ="select d.class_name,d.thead_name,d.copy_name,d.thead_no,d.type,d.is_quota,d.is_require,d.class_name from  maste_copy_thead d  " +
						"where d.copy_no='" +oneChildSceneId+"' order by d.seq";
				List<ListOrderedMap> resultList = commonSpringJdbcService.queryForList(searchSql);
				
				System.out.println("------------searchSql="+searchSql);
				
				int tableHeaderLength=resultList.size();
				String[] tableHeader=new String[tableHeaderLength+1];  
				  
		
				List  list = new ArrayList();
				int j =0;
				List<String[]> tableData=new ArrayList<String[]>();
				String[] tr=null;//存储现有数据的
				String[] tr1=new String[tableHeaderLength];//存标
				
				for (ListOrderedMap listOrderedMap : resultList) {
					
					tableHeader[j]=(String)listOrderedMap.get("thead_name");
				
					oneChildSceneName =(String)listOrderedMap.get("copy_name");
					
					theadNo = (String)listOrderedMap.get("thead_no");
					type=(String)listOrderedMap.get("type");
					className = (String)listOrderedMap.get("class_name");
					isrequire = (String)listOrderedMap.get("is_require");
					
					if("select".equals(type)){
								twoTr="<select onchange=\"getOtherIndex(this,&quot;"+
								oneChildSceneId+"_quotaField&quot;,&quot;"+
								oneChildSceneId+"_table&quot;,&quot;"+
								oneChildSceneId+"&quot;)\" name=\""+
								oneChildSceneId+"_"+theadNo+"\">" ;
								
								String bodySql ="select tv.tbody_name,tv.tbody_no from maste_rel_thead_value tv " +
								"where tv.thead_no='" +theadNo+"' and tv.copy_no='" +oneChildSceneId+"'";
						         List<ListOrderedMap> bodyList = commonSpringJdbcService.queryForList(bodySql);
							        if(bodyList.size()>0){
								 		for (ListOrderedMap listMap : bodyList) {
								 			String tbody_no=(String)listMap.get("tbody_no");
								 			String tbody_name=(String)listMap.get("tbody_name");
								 			
											twoTr+="<option value=\""+tbody_no+"\">"+tbody_name+"</option>";
											
								         	}
						         twoTr+="</select>";
		 		
			                    }
					}else if("inputText".equals(type)){
						 twoTr="<input type=\"text\"";
						
						 if("1".equals(isrequire)){
							 twoTr+=" alt=\"allowBlank:false,maxLength:120,vtext:&quot;请输入值，不能为空！&quot;\"";
						 }
						 if(!"".equals(className)&&className!=null){
							if(className.equals("lengthen")){
								twoTr+=" class=\""+className+"\" value='XYZ' size='90' name=\""+oneChildSceneId+"_"+theadNo+"\" />";
							}else{
								twoTr+=" class=\""+className+"\" value='XYZ' size='4' name=\""+oneChildSceneId+"_"+theadNo+"\" />";
							}    					
	  			         }else{
	      					twoTr+=" value='XYZ' size='4' name=\""+oneChildSceneId+"_"+theadNo+"\" />";
	  			         }
					
					}else if("text".equals(type)){
						if("hsok".equals(theadNo)){
							twoTr="<div style='width:58px;'>XYZ</div><input type=\"hidden\" value=\"XYZ\" name=\""+oneChildSceneId+"_"+theadNo+"\" />";
						}else{
							twoTr="<div>XYZ</div><input type=\"hidden\" value=\"XYZ\" name=\""+oneChildSceneId+"_"+theadNo+"\" />";
						}
						
					}else if("mainButton".equals(type)){
						twoTr="<input type=\"button\" onclick=\"chooseMainMaterial(this,&quot;"+oneChildSceneId+"&quot;)\" value=\"主材选择\" name=\""+oneChildSceneId+"_"+theadNo+"\" />";
					}else if("assistButton".equals(type)){
						twoTr="<input type=\"button\" onclick=\"chooseAssistMaterial(this,&quot;"+oneChildSceneId+"&quot;)\" value=\"辅材选择\" name=\""+oneChildSceneId+"_"+theadNo+"\" />";
					}
					
					tr1[j]=twoTr;
					
					twoTr="";
					j++;
					
				}
				
				tableHeader[tableHeaderLength]="<input type=\"button\" onclick=\"addtr(this,&quot;"+oneChildSceneId+"&quot;)\" value=\"添加\">";
				
				j=0;
				
				//设置子场景中文值
				sceneTableModel.setChildSceneName(oneChildSceneName);
				
				//设置表头	
				sceneTableModel.setTableHeader(tableHeader);
				
				//设置数据
				//int tableDateLength=4;
				//设置数据--获取需要查找的sql
				String dataSqlConstr ="select r.con_strs,r.task_rel_strs from maste_condition_rel_quota r where r.copy_no='"+oneChildSceneId+"'";
				System.out.println("-------dataSqlConstr="+dataSqlConstr);
				List<ListOrderedMap> dataConstr = commonSpringJdbcService.queryForList(dataSqlConstr);
				
				String task_rel_strs ="";
				String con_strs ="";
				for(ListOrderedMap listOrderedMap : dataConstr){
					task_rel_strs = (String)listOrderedMap.get("task_rel_strs");
					con_strs = (String)listOrderedMap.get("con_strs");
				}
				
			   //设置数据--获取已经设置的定额信息
				String selDataSql = "select "+task_rel_strs.replace("#", ",")+",ITEM_NO,main_hid,assist_hid,quotavalue_hid,informain_hid,inforassist_hid,totalcostmain_hid,totalcostassist_hid,unique_id from maste_rel_task_item where CHILD_SCENE_ID='"+oneChildSceneId+"' and PROCESS_INSTANCE_ID='"+processInstanceId+"' and link_type = '"+linkType+"' order by seq";
					
				
				List<ListOrderedMap> dataList = commonSpringJdbcService.queryForList(selDataSql);
				System.out.println("----------selDataSql="+selDataSql);
				
				String[] dataFiled = task_rel_strs.split("#"); 

				String main_hid="";
				String assist_hid="";
				String quotavalue_hid="";
				String informain_hid="";
				String inforassist_hid="";
				String totalcostmain_hid="";
				String totalcostassist_hid="";
				String item_no="";
				String unique_id="";
				 
				for(ListOrderedMap listOrderedMap : dataList){
					main_hid = listOrderedMap.get("main_hid")==null?"":(String)listOrderedMap.get("main_hid");
					assist_hid = listOrderedMap.get("assist_hid")==null?"":(String)listOrderedMap.get("assist_hid");
					quotavalue_hid = listOrderedMap.get("quotavalue_hid")==null?"":(String)listOrderedMap.get("quotavalue_hid");
					informain_hid = listOrderedMap.get("informain_hid")==null?"":(String)listOrderedMap.get("informain_hid");
					inforassist_hid = listOrderedMap.get("inforassist_hid")==null?"":(String)listOrderedMap.get("inforassist_hid");
					totalcostmain_hid = listOrderedMap.get("totalcostmain_hid")==null?"":(String)listOrderedMap.get("totalcostmain_hid");
					totalcostassist_hid = listOrderedMap.get("totalcostassist_hid")==null?"":(String)listOrderedMap.get("totalcostassist_hid");
					item_no = listOrderedMap.get("item_no")==null?"":(String)listOrderedMap.get("item_no");
					unique_id = listOrderedMap.get("unique_id")==null?"":(String)listOrderedMap.get("unique_id");
					
					tr =new String[tableHeaderLength+1];
					
					
					for(int r=0;r<tableHeaderLength;r++){
						boolean flag = tr1[r].contains("XYZ");
						if(flag){
							
							tr[r] = tr1[r].replaceAll("XYZ", listOrderedMap.get(dataFiled[r])==null?"":(String)listOrderedMap.get(dataFiled[r])) ;
						}else{
							tr[r] = tr1[r].replaceAll("<option value=\""+(String)listOrderedMap.get(dataFiled[r])+"\"", "<option value=\""+(String)listOrderedMap.get(dataFiled[r])+"\" selected=\"selected\"") ;
						}
					}
//					删除-按钮，以及隐藏域的值
					tr[tableHeaderLength] ="<input type=\"hidden\" value=\""+main_hid+"\" name=\""+oneChildSceneId+"_main\" />" +
							"<input type=\"hidden\" value=\""+assist_hid+"\" name=\""+oneChildSceneId+"_assist\" />" +
							"<input type=\"hidden\" value=\""+quotavalue_hid+"\" name=\""+oneChildSceneId+"_quotaValue\" />" +
							"<input type=\"hidden\" value=\""+informain_hid+"\" name=\""+oneChildSceneId+"_inforMain\" />" +
							"<input type=\"hidden\" value=\""+inforassist_hid+"\" name=\""+oneChildSceneId+"_inforAssist\" />" +
							"<input type=\"hidden\" value=\""+totalcostmain_hid+"\" name=\""+oneChildSceneId+"_totalCostMain\" />" +
							"<input type=\"hidden\" value=\""+totalcostassist_hid+"\" name=\""+oneChildSceneId+"_totalCostAssist\" />" +
							"<input type=\"hidden\" value=\""+item_no+"\" name=\""+oneChildSceneId+"_itemNo\" />" +
							"<input type=\"hidden\" value=\""+unique_id+"\" name=\""+oneChildSceneId+"_uniqueId\" />"
							+"<input type=\"button\" onclick=\"deltr(this,&quot;"+oneChildSceneId+"&quot;)\" value=\"删除\" />";
					
					tableData.add(tr);
				}
							
				sceneTableModel.setTableData(tableData);
				echoChildSceneTableList.add(sceneTableModel);
			}
		}
		
		Map<String,Object> result=new HashMap<String,Object>();
		result.put("childSceneIds", childSceneIds);
		result.put("echoChildSceneTableList", echoChildSceneTableList);
		
		return result;
}
	//获取未被选择的"其他费用（手工填写）"子场景
	public Map<String,Object> loadNotChosenOtherCostsSubScene(String processInstanceId,String linkType,Map<String,String> param){
		//主场景IDS
		String mainSceneIds=param.get("mainSceneIds");
		
		String sql= "select sc.copy_no,sc.copy_name" +
					"     from maste_copy_scene sc" + 
					"    where sc.copy_no in" + 
					"          (select sc.copy_no" + 
					"             from maste_copy_scene sc" + 
					"            where sc.scene_no in ("+mainSceneIds+")" + 
					"              and sc.copy_name like '%其他费用（手工填写）'" + 
					"           minus" + 
					"           select distinct ite.child_scene_id" + 
					"             from maste_rel_task_item ite" + 
					"            where ite.process_instance_id = '"+processInstanceId+"'" +
					" 			       and ite.link_type='"+linkType+"')";

		List queryForList = commonSpringJdbcService.queryForList(sql);
		Map<String,Object> result=new HashMap<String,Object>();
		result.put("resultList", queryForList);
		
		return result;
	}
	
	//查看子场景详情
	public Map<String,Object> checkChildSceneForDetails(String processInstanceId,String linkType,Map<String,String> param){
		List<SceneTableModel> sceneTableList=new ArrayList<SceneTableModel>();
		Search search = new Search();
		search.addFilterEqual("processInstanceId", processInstanceId);
		search.addFilterEqual("linkType", linkType);
		search.addSortAsc("seq");
		SearchResult<MasteSelCopyTask> resultTask=masteSelCopyTaskService.searchAndCount(search);
		List<MasteSelCopyTask> resultTaskList =resultTask.getResult();
		
		int num = resultTaskList.size();
		
		String childSceneId="";
		SceneTableModel sceneTableModel =null;
		MasteSelCopyTask  entity=null;
		for(int i=0;i<num;i++){
		
			sceneTableModel=new SceneTableModel();

			entity= resultTaskList.get(i);
			//设置子场景id值
			childSceneId=entity.getChildSceneId();
			if (childSceneId != null && !"".equals(childSceneId)) {
				sceneTableModel.setChildSceneId(childSceneId);
				
				//CommonSpringJdbcService jdbcService = (CommonSpringJdbcService) ApplicationContextHolder.getInstance().getBean("commonSpringJdbcService");
				String searchSql ="select d.class_name,d.thead_name,d.copy_name,d.thead_no,d.type,decode(d.is_quota,'1','YES','NO') is_quota from  maste_copy_thead d  " +
						"where d.copy_no='" +childSceneId+"' order by d.seq";
				
				System.out.println("----------查看子场景详情-searchSql="+searchSql);
				
				List<ListOrderedMap> resultList = commonSpringJdbcService.queryForList(searchSql);
				
				
				String childSceneName="";
				int tableHeaderLength=resultList.size();
				String[] tableHeader=new String[tableHeaderLength];  
		
				List  list = new ArrayList();
				int j =0;
				
				for (ListOrderedMap listOrderedMap : resultList) {
					
					tableHeader[j]=(String)listOrderedMap.get("thead_name");
					j++;
					childSceneName =(String)listOrderedMap.get("copy_name");
				}
				j=0;
				
				//设置子场景中文值
				sceneTableModel.setChildSceneName(childSceneName);
				
				//设置表头	
				sceneTableModel.setTableHeader(tableHeader);
				
				//设置数据
				//int tableDateLength=4;
				List<String[]> tableData=new ArrayList<String[]>();
				//获取需要查找的sql
				String dataSqlConstr ="select r.con_strs,r.task_rel_strs from maste_condition_rel_quota r where r.copy_no='"+childSceneId+"'";
				
				System.out.println("-----------查看子场景详情-dataSqlConstr="+dataSqlConstr);
				
				List<ListOrderedMap> dataConstr = commonSpringJdbcService.queryForList(dataSqlConstr);
				
				String task_rel_strs ="";
				String con_strs ="";
				for(ListOrderedMap listOrderedMap : dataConstr){
					task_rel_strs = (String)listOrderedMap.get("task_rel_strs");
					con_strs = (String)listOrderedMap.get("con_strs");
				}
				
				String selDataSql = "select "+task_rel_strs.replace("#", ",")+",ITEM_NO,unique_id from maste_rel_task_item where CHILD_SCENE_ID='"+childSceneId+"' and PROCESS_INSTANCE_ID='"+processInstanceId+"' and link_type = '"+linkType+"' order by seq";
				
				String[] con = con_strs==null?null:con_strs.split("#");
				String newStr ="";
				String lowerStr = "";
				int csize= con==null?0: con.length;
				
				for(int x=0;x<csize;x++){
					//System.out.println("执行了:"+csize+"-000"+selDataSql);
					lowerStr = con[x].toLowerCase();
					newStr="(select tbody_name from maste_copy_tbody  where maste_copy_tbody.tbody_no=maste_rel_task_item."+lowerStr+") "+lowerStr;
					selDataSql = selDataSql.replace(lowerStr, newStr);
				}
				//System.out.println("000000000selDataSql:"+selDataSql);
				System.out.println("-----------查看子场景详情-selDataSql="+selDataSql);
				List<ListOrderedMap> dataList = commonSpringJdbcService.queryForList(selDataSql);
				
				String[] dataFiled = task_rel_strs.split("#"); 
				String[] tr=null;

				String item_no="";
				String unique_id="";
				for(ListOrderedMap listOrderedMap : dataList){
					tr =new String[tableHeaderLength];
					for(int r=0;r<tableHeaderLength;r++){
						if("MAIN_TYPE".equalsIgnoreCase(dataFiled[r])){
							item_no =(String)listOrderedMap.get("ITEM_NO");
							if(listOrderedMap.get("unique_id")!=null){
								unique_id =(String)listOrderedMap.get("unique_id");
							}
							tr[r] ="<a href=\"javascript:void(0);\" onclick=\"checkMainMaterialForDetails(&quot;"+item_no+"&quot;,&quot;"+processInstanceId+"&quot;,&quot;"+unique_id+"&quot;,&quot;"+linkType+"&quot;)\">查看</a>";
						}else if("AUXILIARY_TYPE".equalsIgnoreCase(dataFiled[r])){
							tr[r] ="<a href=\"javascript:void(0);\" onclick=\"checkAssistMaterialForDetails(&quot;"+item_no+"&quot;,&quot;"+processInstanceId+"&quot;,&quot;"+unique_id+"&quot;,&quot;"+linkType+"&quot;)\">查看</a>";
						}else {						
							tr[r] = (String)listOrderedMap.get(dataFiled[r]);
						}
						
					}
					//tr[tableHeaderLength] = (String)listOrderedMap.get("ITEM_NO");
					
					tableData.add(tr);
				}
				
				
				
				
			/*	String[] tr1=new String[tableHeaderLength];
				tr1[0]="22";
				tr1[1]="DAKDE993";
				tr1[2]="<a href=\"javascript:void(0);\" onclick=\"checkMainMaterialForDetails()\">查看</a>";
				tr1[3]="<a href=\"javascript:void(0);\" onclick=\"checkAssistMaterialForDetails()\">查看</a>";*/
				
			/*	String[] tr2=new String[tableHeaderLength];
				tr2[0]="4543";
				tr2[1]="DAKDE9E3IIF";
				tr2[2]="<a href=\"javascript:void(0);\" onclick=\"checkMainMaterialForDetails()\">查看</a>";;
				tr2[3]="<a href=\"javascript:void(0);\" onclick=\"checkAssistMaterialForDetails()\">查看</a>";
				tableData.add(tr2);*/
				
				sceneTableModel.setTableData(tableData);
			
			//返回集合
				sceneTableList.add(sceneTableModel);
			}
		}
		
		Map<String,Object> result=new HashMap<String,Object>();
		result.put("sceneTableList", sceneTableList);		
		return result;
	}
	
	//查看材料（主材或者辅材）
	public Map<String,Object> checkMaterialForDetails(String processInstanceId,String linkType,Map<String,String> param){
		
		String searchSql ="select d.sort_num,d.name,d.standard,d.unit,k.num," +
				"k.per_price,k.total_price,decode(k.is_whether_old,'NO','否','是') w from maste_data_task k " +
				"left join maste_data d on k.data_id = d.id " +
				"where k.process_instance_id='"+processInstanceId+"' and link_type='"+linkType+"' and k.item_no='"+param.get("itemNo")+"'" ;
		if("main".equals(param.get("materialType"))){//主材
			searchSql+=" and k.type='1'";
		}else if("assist".equals(param.get("materialType"))){
			searchSql+=" and k.type='0'";
		}
		if(!"".equals(param.get("uniqueId"))){
			searchSql+=" and k.unique_id='"+param.get("uniqueId")+"'";
			searchSql+=" order by k.seq asc";
		} else{
			searchSql+=" order by to_number(nvl(d.sort_num,'0')) asc";
		}
		
		System.out.println("-----查看材料="+searchSql);
		
 		List<ListOrderedMap> resultList = commonSpringJdbcService.queryForList(searchSql);
		List<SceneTableModel> materialTableList=new ArrayList<SceneTableModel>();
		SceneTableModel sceneTableModel=new SceneTableModel();
		//设置表头
		int tableHeaderLength=8;
		String[] tableHeader=new String[tableHeaderLength];  
		tableHeader[0]="序号";
		tableHeader[1]="名称";
		tableHeader[2]="规格程式";
		tableHeader[3]="单位";
		tableHeader[4]="数量";
		tableHeader[5]="单价";
		tableHeader[6]="合价";
		tableHeader[7]="材料是否利旧";
		sceneTableModel.setTableHeader(tableHeader);
		
		//设置数据
		List<String[]> tableData=new ArrayList<String[]>();
		String[] tr=null;
		
		int seqNum = 1;//序号
		for(ListOrderedMap listOrderedMap : resultList){
			tr=new String[tableHeaderLength];

			tr[0]= Integer.valueOf(seqNum++).toString();
			tr[1]= (String)listOrderedMap.get("name");
			tr[2]= (String)listOrderedMap.get("standard");
			tr[3]= (String)listOrderedMap.get("unit");
			
			tr[4]= listOrderedMap.get("num")==null?"0":Double.parseDouble(listOrderedMap.get("num").toString())+"";
			tr[5]= listOrderedMap.get("per_price")==null?"0":Double.parseDouble(listOrderedMap.get("per_price").toString())+"";
			tr[6]= listOrderedMap.get("total_price")==null?"0":Double.parseDouble(listOrderedMap.get("total_price").toString())+"";
			tr[7]= (String)listOrderedMap.get("w");

			tableData.add(tr);
		}
		
		sceneTableModel.setTableData(tableData);
		materialTableList.add(sceneTableModel);
		
		Map<String,Object> result=new HashMap<String,Object>();
		result.put("materialTableList", materialTableList);		
		return result;
	}
	
	//导出场景模板
	public HSSFWorkbook scenarioTemplateExcel(String processInstanceId,String linkType,Map<String,String> param){
		String sheetName="场景模板";
		if("need".equals(linkType)){//新建派发
			sheetName="新建工单-场景模板";
		}else if("worker".equals(linkType)){//工单处理
			sheetName="施工队回单-场景模板";
		}else if("orderAudit".equals(linkType)){//抽查
			sheetName="抽查-场景模板";
		}
		
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet(sheetName);
		ExcelUtils exportExcel = new ExcelUtils(wb, sheet);

		int y = 0;

		List<SceneTableModel> sceneTableList = new ArrayList<SceneTableModel>();

		//IMasteSelCopyTaskService masteSelCopyTaskService = (IMasteSelCopyTaskService) getBean("masteSelCopyTaskService");
		Search search = new Search();
		search.addFilterEqual("processInstanceId", processInstanceId);
		search.addFilterEqual("linkType", linkType);
		search.addSortAsc("seq");
		SearchResult<MasteSelCopyTask> resultTask = masteSelCopyTaskService.searchAndCount(search);
		List<MasteSelCopyTask> resultTaskList = resultTask.getResult();

		int num = resultTaskList.size();

		String childSceneId = "";
		SceneTableModel sceneTableModel = null;
		MasteSelCopyTask entity = null;
		for (int i = 0; i < num; i++) {
			sceneTableModel = new SceneTableModel();

			entity = resultTaskList.get(i);
			//设置子场景id值
			childSceneId = entity.getChildSceneId();
			sceneTableModel.setChildSceneId(childSceneId);

			String searchSql = "select d.class_name,d.thead_name,d.copy_name,d.thead_no,d.type,decode(d.is_quota,'1','YES','NO') is_quota from  maste_copy_thead d  "
					+ "where d.copy_no='" + childSceneId + "' order by d.seq";
			List<ListOrderedMap> resultList = commonSpringJdbcService
					.queryForList(searchSql);

			String childSceneName = "";
			int tableHeaderLength = resultList.size();
			String[] tableHeader = new String[tableHeaderLength];

			List list = new ArrayList();
			int j = 0;

			for (ListOrderedMap listOrderedMap : resultList) {

				tableHeader[j] = (String) listOrderedMap.get("thead_name");
				j++;
				childSceneName = (String) listOrderedMap.get("copy_name");
			}
			j = 0;

			//设置子场景中文值
			sceneTableModel.setChildSceneName(childSceneName);

			//设置表头	
			sceneTableModel.setTableHeader(tableHeader);

			//设置数据
			//int tableDateLength=4;
			List<String[]> tableData = new ArrayList<String[]>();

			//获取需要查找的sql
			String dataSqlConstr = "select r.con_strs,r.task_rel_strs from maste_condition_rel_quota r where r.copy_no='"
					+ childSceneId + "'";
			List<ListOrderedMap> dataConstr = commonSpringJdbcService
					.queryForList(dataSqlConstr);

			String task_rel_strs = "";
			String con_strs = "";
			for (ListOrderedMap listOrderedMap : dataConstr) {
				task_rel_strs = (String) listOrderedMap.get("task_rel_strs");
				con_strs = (String) listOrderedMap.get("con_strs");
			}

			String selDataSql = "select "
					+ task_rel_strs.replace("#", ",")
					+ ",ITEM_NO from maste_rel_task_item where CHILD_SCENE_ID='"
					+ childSceneId + "' and PROCESS_INSTANCE_ID='"
					+ processInstanceId + "' and link_type='"+linkType+"'";

			String[] con = con_strs == null ? null : con_strs.split("#");
			String newStr = "";
			String lowerStr = "";
			int csize = con == null ? 0 : con.length;

			for (int x = 0; x < csize; x++) {
				System.out.println("执行了:" + csize + "-000" + selDataSql);
				lowerStr = con[x].toLowerCase();
				newStr = "(select tbody_name from maste_copy_tbody  where maste_copy_tbody.tbody_no=maste_rel_task_item."
						+ lowerStr + ") " + lowerStr;
				selDataSql = selDataSql.replace(lowerStr, newStr);
			}
			System.out.println("000000000selDataSql:" + selDataSql);
			List<ListOrderedMap> dataList = commonSpringJdbcService
					.queryForList(selDataSql);

			String[] dataFiled = task_rel_strs.split("#");

			sceneTableModel.setTableData(tableData);

			//返回集合
			sceneTableList.add(sceneTableModel);

			/**------------------------------------------------------------------------------------------------------------------------------------------*/

			int titleCount = sceneTableList.get(i).getTableHeader().length;//子场景标题长度
			int dataCount = sceneTableList.get(i).getTableData().size();//子场景定额数据大小
			int assistMaterialTitleCount = 0;
			if (sceneTableList.get(i).getAssistMaterialTableHeader() != null
					&& !"".equals(sceneTableList.get(i)
							.getAssistMaterialTableHeader())) {
				assistMaterialTitleCount = sceneTableList.get(i)
						.getAssistMaterialTableHeader().length;//辅材列标题长度 
			}
			int manMaterialTitleCount = 0;
			if (sceneTableList.get(i).getManSceneTableHeader() != null
					&& !"".equals(sceneTableList.get(i)
							.getManSceneTableHeader())) {
				manMaterialTitleCount = sceneTableList.get(i)
						.getManSceneTableHeader().length;//主材列标题长度 
			}

			String worksheetTitle = sceneTableList.get(i).getChildSceneName();//子场景名称

			// 创建单元格样式   
			HSSFCellStyle cellStyleTitle = wb.createCellStyle();
			// 指定单元格居中对齐   
			cellStyleTitle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			// 指定单元格垂直居中对齐   
			cellStyleTitle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			// 指定当单元格内容显示不下时自动换行   
			cellStyleTitle.setWrapText(true);
			// ------------------------------------------------------------------   
			HSSFCellStyle cellStyle = wb.createCellStyle();
			// 指定单元格居中对齐   
			cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			// 指定单元格垂直居中对齐   
			cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			// 指定当单元格内容显示不下时自动换行   
			cellStyle.setWrapText(true);
			// ------------------------------------------------------------------   
			// 设置单元格字体   
			HSSFFont font = wb.createFont();
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			font.setFontName("宋体");
			font.setFontHeight((short) 200);
			cellStyleTitle.setFont(font);

			int p = 0;
			//获取主材  辅材数据 
			CommonSpringJdbcService jdbcService1 = (CommonSpringJdbcService) ApplicationContextHolder
					.getInstance().getBean("commonSpringJdbcService");
			String item_no1 = "";
			String[] tr1 = null;
			String[] manSheetName = new String[dataList.size()];
			String[] assistSheetName = new String[dataList.size()];
			int num1 = 0;
			int num2 = 0;
			for (ListOrderedMap listOrderedMap : dataList) {
				for (int r = 0; r < tableHeaderLength; r++) {
					if ("MAIN_TYPE".equalsIgnoreCase(dataFiled[r])) {
						item_no1 = (String) listOrderedMap.get("ITEM_NO");
						String searchSql3 = "select d.sort_num,d.name,d.standard,d.unit,k.num,"
								+ "k.per_price,k.total_price,decode(k.is_whether_old,'NO','否','是') w from maste_data_task k "
								+ "left join maste_data d on k.data_id = d.id "
								+ "where k.link_type='"+linkType+"' and k.type='1' and k.item_no='"
								+ item_no1
								+ "' and k.process_instance_id='"
								+ processInstanceId
								+ "'"
								+ " order by to_number(nvl(d.sort_num,'0')) asc";

						List<ListOrderedMap> resultList3 = commonSpringJdbcService
								.queryForList(searchSql3);
						List<SceneTableModel> mainMaterialTableList = new ArrayList<SceneTableModel>();

						//设置表头
						int mainMaterialTableHeaderLength = 8;
						String[] mainMaterialTableHeader = new String[mainMaterialTableHeaderLength];
						mainMaterialTableHeader[0] = "序号";
						mainMaterialTableHeader[1] = "名称";
						mainMaterialTableHeader[2] = "规格程式";
						mainMaterialTableHeader[3] = "单位";
						mainMaterialTableHeader[4] = "数量";
						mainMaterialTableHeader[5] = "单价";
						mainMaterialTableHeader[6] = "合价";
						mainMaterialTableHeader[7] = "材料是否利旧";
						sceneTableModel
								.setManSceneTableHeader(mainMaterialTableHeader);

						//设置数据
						List<String[]> mainMaterialTabletableData = new ArrayList<String[]>();
						String[] tr3 = null;
						int seqNum = 1;//序号
						for (ListOrderedMap listOrderedMap3 : resultList3) {
							tr3 = new String[mainMaterialTableHeaderLength];
							//tr3[0] = (String) listOrderedMap3.get("sort_num");
							tr3[0] = Integer.valueOf(seqNum++).toString();;
							tr3[1] = (String) listOrderedMap3.get("name");
							tr3[2] = (String) listOrderedMap3.get("standard");
							tr3[3] = (String) listOrderedMap3.get("unit");

							tr3[4] = listOrderedMap3.get("num") == null ? "0"
									: Double.parseDouble(listOrderedMap3.get(
											"num").toString())
											+ "";
							tr3[5] = listOrderedMap3.get("per_price") == null ? "0"
									: Double.parseDouble(listOrderedMap3.get(
											"per_price").toString())
											+ "";
							tr3[6] = listOrderedMap3.get("total_price") == null ? "0"
									: Double.parseDouble(listOrderedMap3.get(
											"total_price").toString())
											+ "";
							tr3[7] = (String) listOrderedMap3.get("w");

							mainMaterialTabletableData.add(tr3);
						}

						manSheetName[num1] = childSceneName + "主材表" + p;
						HSSFSheet sheet2 = wb.createSheet(manSheetName[num1]);

						ExcelUtils exportExcel2 = new ExcelUtils(wb, sheet2);
						num1 += 1;

						int jishuqi1 = 0;
						exportExcel2.createNormalHead("主材表",
								mainMaterialTableHeaderLength - 1, jishuqi1);
						jishuqi1 += 1;
						//主材列标题
						exportExcel2.createMaterialTitle(
								mainMaterialTableHeader,
								mainMaterialTableHeaderLength, jishuqi1);
						jishuqi1 += 1;

						//主材数据
						if (mainMaterialTabletableData != null
								&& !"".equals(mainMaterialTabletableData)) {
							for (int x = 0; x < mainMaterialTabletableData
									.size(); x++) {
								HSSFRow row4 = sheet2.createRow(jishuqi1);
								HSSFCell cell4 = null;
								for (int x1 = 0; x1 < mainMaterialTabletableData
										.get(x).length; x1++) {
									cell4 = row4.createCell((short) x1);
									cell4.setCellStyle(cellStyleTitle);
									cell4
											.setCellValue(new HSSFRichTextString(
													mainMaterialTabletableData
															.get(x)[x1]));
								}
								jishuqi1 += 1;
							}
						}
						jishuqi1 = 0;
						p += 1;

					} else if ("AUXILIARY_TYPE".equalsIgnoreCase(dataFiled[r])) {
						String searchSql1 = "select d.sort_num,d.name,d.standard,d.unit,k.num,"
								+ "k.per_price,k.total_price,decode(k.is_whether_old,'NO','否','是') w from maste_data_task k "
								+ "left join maste_data d on k.data_id = d.id "
								+ "where k.link_type='"+linkType+"' and  k.type='0' and k.item_no='"
								+ item_no1
								+ "' and k.process_instance_id='"
								+ processInstanceId
								+ "'"
								+ " order by to_number(nvl(d.sort_num,'0')) asc";

						List<ListOrderedMap> resultList1 = jdbcService1
								.queryForList(searchSql1);
						List<SceneTableModel> assistMaterialTableList = new ArrayList<SceneTableModel>();
						//设置表头
						int assistMaterialTableHeaderLength = 8;
						String[] tableHeader1 = new String[assistMaterialTableHeaderLength];
						tableHeader1[0] = "序号";
						tableHeader1[1] = "名称";
						tableHeader1[2] = "规格程式";
						tableHeader1[3] = "单位";
						tableHeader1[4] = "数量";
						tableHeader1[5] = "单价";
						tableHeader1[6] = "合价";
						tableHeader1[7] = "材料是否利旧";
						sceneTableModel
								.setAssistMaterialTableHeader(tableHeader1);

						//设置数据
						List<String[]> assistMaterialTableData = new ArrayList<String[]>();
						String[] tr2 = null;
						int seqNum1 = 1;//序号
						for (ListOrderedMap listOrderedMap1 : resultList1) {
							tr2 = new String[assistMaterialTableHeaderLength];
							//tr2[0] = (String) listOrderedMap1.get("sort_num");
							tr2[0] = Integer.valueOf(seqNum1++).toString();
							tr2[1] = (String) listOrderedMap1.get("name");
							tr2[2] = (String) listOrderedMap1.get("standard");
							tr2[3] = (String) listOrderedMap1.get("unit");

							tr2[4] = listOrderedMap1.get("num") == null ? "0"
									: Double.parseDouble(listOrderedMap1.get(
											"num").toString())
											+ "";
							tr2[5] = listOrderedMap1.get("per_price") == null ? "0"
									: Double.parseDouble(listOrderedMap1.get(
											"per_price").toString())
											+ "";
							tr2[6] = listOrderedMap1.get("total_price") == null ? "0"
									: Double.parseDouble(listOrderedMap1.get(
											"total_price").toString())
											+ "";
							tr2[7] = (String) listOrderedMap1.get("w");

							assistMaterialTableData.add(tr2);
						}
						assistSheetName[num2] = childSceneName + "辅材表" + p;
						HSSFSheet sheet1 = wb
								.createSheet(assistSheetName[num2]);
						ExcelUtils exportExcel1 = new ExcelUtils(wb, sheet1);
						num2 += 1;
						int jishuqi = 0;
						exportExcel1.createNormalHead("辅材表",
								assistMaterialTableHeaderLength - 1, jishuqi);
						jishuqi += 1;

						//辅材列标题
						exportExcel1.createMaterialTitle(tableHeader1,
								assistMaterialTableHeaderLength, jishuqi);
						jishuqi += 1;

						//辅材数据
						if (assistMaterialTableData != null
								&& !"".equals(assistMaterialTableData)) {
							for (int x = 0; x < assistMaterialTableData.size(); x++) {
								HSSFRow row3 = sheet1.createRow(jishuqi);
								HSSFCell cell4 = null;
								for (int x1 = 0; x1 < assistMaterialTableData
										.get(x).length; x1++) {
									cell4 = row3.createCell((short) x1);
									cell4.setCellStyle(cellStyleTitle);
									cell4
											.setCellValue(new HSSFRichTextString(
													assistMaterialTableData
															.get(x)[x1]));
								}
								jishuqi += 1;
							}
							p += 1;
							jishuqi = 0;
						}

					}

				}

			}

			//子场景名称   
			exportExcel.createNormalHead(worksheetTitle, titleCount - 1, y);
			y += 1;

			//子场景列标题
			exportExcel.createTitle(sceneTableList, titleCount, y, i);//参数：1.对象集合含子场景标题2、标题长度，3行数，4对象下标
			y += 1;

			//子场景定额数据行
			int x = 0;
			int n = 0;
			for (ListOrderedMap listOrderedMap : dataList) {
				HSSFRow row2 = sheet.createRow(y);
				HSSFCell cell2 = null;
				for (int r = 0; r < tableHeaderLength; r++) {
					if ("MAIN_TYPE".equalsIgnoreCase(dataFiled[r])) {
						cell2 = row2.createCell((short) r);
						cell2.setCellValue(new HSSFRichTextString("查看"));
						HSSFHyperlink link = cell2.getHyperlink();
						link = new HSSFHyperlink(HSSFHyperlink.LINK_DOCUMENT);
						link.setAddress("'" + manSheetName[x] + "'!A1");
						cell2.setHyperlink(link);
						//		                		 cell2.setCellType(HSSFCell.CELL_TYPE_FORMULA);
						//		                		 cell2.setCellFormula("HYPERLINK(\"["+downFilename+"]'"+manSheetName[x]+"'!A1\",\"查看\")"); 
						HSSFCellStyle linkStyle = wb.createCellStyle();
						HSSFFont cellFont = wb.createFont();
						cellFont.setUnderline((byte) 1);
						cellFont.setColor(HSSFColor.BLUE.index);
						linkStyle.setFont(cellFont);
						linkStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 指定单元格居中对齐
						linkStyle
								.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 指定单元格垂直居中对齐
						cell2.setCellStyle(linkStyle);

						x += 1;
					} else if ("AUXILIARY_TYPE".equalsIgnoreCase(dataFiled[r])) {
						cell2 = row2.createCell((short) r);
						cell2.setCellValue(new HSSFRichTextString("查看"));
						HSSFHyperlink link = cell2.getHyperlink();
						link = new HSSFHyperlink(HSSFHyperlink.LINK_DOCUMENT);
						link.setAddress("'" + assistSheetName[n] + "'!A1");
						cell2.setHyperlink(link);
						//								 cell2.setCellType(HSSFCell.CELL_TYPE_FORMULA);
						//		                		 cell2.setCellFormula("HYPERLINK(\"["+downFilename+"]'"+assistSheetName[n]+"'!A1\",\"查看\")"); 
						HSSFCellStyle linkStyle = wb.createCellStyle();
						HSSFFont cellFont = wb.createFont();
						cellFont.setUnderline((byte) 1);
						cellFont.setColor(HSSFColor.BLUE.index);
						linkStyle.setFont(cellFont);
						linkStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 指定单元格居中对齐
						linkStyle
								.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 指定单元格垂直居中对齐
						cell2.setCellStyle(linkStyle);
						n += 1;
					} else {
						cell2 = row2.createCell((short) r);
						cell2.setCellStyle(cellStyleTitle);
						cell2.setCellValue(new HSSFRichTextString(
								(String) listOrderedMap.get(dataFiled[r])));

					}

				}

				y += 1;
			}

		}	
		
		return wb;
	}
	
	//查看场景模板是否有数据
	public int countSceneTemplateNum(String processInstanceId,String linkType,Map<String,String> param){
		String sql="select count(t.id) as total from maste_sel_copy_task t where t.process_instance_id = '"+processInstanceId+"' and t.link_type = '"+linkType+"'";
		List<Map> list = commonSpringJdbcService.queryForList(sql);
		return Integer.parseInt(list.get(0).get("total").toString());
	}
}
