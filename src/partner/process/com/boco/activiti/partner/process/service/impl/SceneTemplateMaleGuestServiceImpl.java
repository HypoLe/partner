package com.boco.activiti.partner.process.service.impl;


import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.boco.activiti.partner.process.po.scene.ChildSceneModel;
import com.boco.activiti.partner.process.po.scene.ChildSceneWholeModel;
import com.boco.activiti.partner.process.po.scene.MainSceneModel;
import com.boco.activiti.partner.process.po.scene.MaterialModel;
import com.boco.activiti.partner.process.po.scene.SceneDetailModel;
import com.boco.activiti.partner.process.po.scene.SceneDetailWholeModel;
import com.boco.activiti.partner.process.po.scene.UtilizeModel;
import com.boco.activiti.partner.process.service.ISceneTemplateService;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.deviceManagement.common.service.CommonSpringJdbcService;
import com.boco.eoms.partner.process.util.CommonUtils;

/**
 * 场景模板 服务类 实现
 * 
 * 涉及到场景模板的方法，统一写在这个方法里，以便日后复用
 * 
 * @author Jun
 *
 */

public class SceneTemplateMaleGuestServiceImpl extends JdbcDaoSupport implements ISceneTemplateService {
	
	private CommonSpringJdbcService commonSpringJdbcService;
	
	public CommonSpringJdbcService getCommonSpringJdbcService() {
		return commonSpringJdbcService;
	}
	public void setCommonSpringJdbcService(
			CommonSpringJdbcService commonSpringJdbcService) {
		this.commonSpringJdbcService = commonSpringJdbcService;
	}
	@Override
	public Map<String, Object> checkChildSceneForDetails(
			String processInstanceId, String linkType, Map<String, String> param) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Map<String, Object> checkMaterialForDetails(
			String processInstanceId, String linkType, Map<String, String> param) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int countSceneTemplateNum(String processInstanceId, String linkType,
			Map<String, String> param) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public Map<String, Object> deleteSceneTemplate(String processInstanceId,
			String linkType, Map<String, String> param) {
		String processType = ""; //流程类型
		if(param != null){
			String tempProcessType = param.get("processType");
			if(tempProcessType != null){
				processType = tempProcessType;
			}
		}
		this.deleteSelRelTasks(processInstanceId,linkType,processType, param); //删除表maste_male_sel_rel_task中的数据
		this.deleteItemTask(processInstanceId, linkType, processType, param); //删除表maste_male_item_rel_task中的数据
		this.deleteDataTask(processInstanceId, linkType, processType, param); //删除表maste_male_data_rel_task中的数据	
		return null;
	}
	
	//获取场景模板的条数
	public int getNumberOfSceneTemplate(String processInstanceId,String linkType, Map<String, String> param){
		int total = 0;
		CommonSpringJdbcService jdbcService = (CommonSpringJdbcService) ApplicationContextHolder.getInstance().getBean("commonSpringJdbcService");
		String dataIfsql ="select count(mk.id) num from maste_male_sel_rel_task mk where mk.task_key='"+linkType+"' and mk.process_instance_id='"+processInstanceId+"'";
		total = jdbcService.queryForInt(dataIfsql);
		return total;
	}
	
	@Override
	public Map<String, Object> echoChildScene(String processInstanceId,String linkType, Map<String, String> param) {
		Map<String, Object> result = new HashMap<String, Object>();
		String auditorSceneExistFlag = "N"; // N为施工队没有场景模板数据
		List<MainSceneModel> mainSceneList = null; //主场景
		List<ChildSceneWholeModel> childSceneWholeModelList = null; //子场景
		Double totalAmount = 0d; //总金额
		List<SceneDetailWholeModel> sceneDetailWholeModelList = null; //子场景明细

		String task_key = "auditor"; // 默认施工队
		// 1.判断施工队是否有场景模板数据
		int auditorNumber = this.getNumberOfSceneTemplate(processInstanceId,
				task_key, param);
		if (auditorNumber > 0) {
			auditorSceneExistFlag = "Y"; // N为施工队有场景模板数据
			if ("acheck".equals(linkType)) { // 一次核验环节
				int acheckNumber = this.getNumberOfSceneTemplate(
						processInstanceId, "acheck", param);
				if (acheckNumber > 0) {
					task_key = "acheck"; // 一次核验有数据查询一次核验的，没有查询施工队的
				}
			} else if ("twoSpotChecks".equals(linkType)
					|| "auditReport".equals(linkType)) {
				int twoSpotChecksNumber = this.getNumberOfSceneTemplate(
						processInstanceId, "twoSpotChecks", param);
				if (twoSpotChecksNumber > 0) {
					task_key = "twoSpotChecks";// 二次抽检有数据查询二次抽检的
				} else {
					int acheckNumber = this.getNumberOfSceneTemplate(
							processInstanceId, "acheck", param);
					if (acheckNumber > 0) {
						task_key = "acheck"; // 二次抽检没数据显示一次核验的，没有显示施工队的
					}
				}
			}

			// 取对应的场景模板数据
			CommonSpringJdbcService jdbcService = (CommonSpringJdbcService) ApplicationContextHolder
					.getInstance().getBean("commonSpringJdbcService");
			String searchSql = " select m.scene_no,m.scene_name,case when nvl(mk.id,'0')='0' then 'false' else 'true' end flag "
					+ " from master_male_scene m"
					+ " left join maste_male_sel_rel_task mk on m.scene_no = mk.scene_no_str "
					+ "and mk.task_key='"
					+ task_key
					+ "' and mk.process_instance_id='"
					+ processInstanceId
					+ "'";

			List<ListOrderedMap> resultList = jdbcService
					.queryForList(searchSql);
			// 取主场景 开始----数据目前只是支持 单条选择
			mainSceneList = new ArrayList<MainSceneModel>();
			// 取子场景多选开始
			childSceneWholeModelList = new ArrayList<ChildSceneWholeModel>();
			String scene_no = "";
			String scene_name = "";
			String copy_no = "";
			String copy_name = "";
			String dispose = "";
			String unit = "";
			String isHave = "0";
			ChildSceneWholeModel childSceneWholeModel = new ChildSceneWholeModel();

			for (ListOrderedMap listOrderedMap : resultList) {

				MainSceneModel model1 = new MainSceneModel();
				model1.setMainSceneValue(listOrderedMap.get("scene_no") + "");
				model1.setMainSceneName(listOrderedMap.get("scene_name") + "");
				model1.setChecked(listOrderedMap.get("flag") + "");
				mainSceneList.add(model1);
				if ("true".equals(listOrderedMap.get("flag"))) {

					scene_no = listOrderedMap.get("scene_no") + "";
					scene_name = listOrderedMap.get("scene_name") + "";
					childSceneWholeModel.setMainSceneValue(scene_no);// 选中的主场景id值
					childSceneWholeModel.setMainSceneName(scene_name);// 选中的主场景name值

				}

			}
			
			//result.put("mainSceneList", mainSceneList);
			
			// 取主场景 结束
			List<ChildSceneModel> childSceneList = new ArrayList<ChildSceneModel>();
			// ChildSceneWholeModel childSceneWholeModel=new
			// ChildSceneWholeModel();

			String childSceneSearchSql = " select m.copy_no,m.copy_name,"
					+ "case when nvl(mk.id,'0')='0' then 'false' else 'true' end flag"
					+ ",mk.dispose,mk.unit,nvl(m.is_have,'0') ishave"
					+ " from maste_male_copy_scene m"
					+ " left join maste_male_item_rel_task mk on m.copy_no = mk.copy_no and mk.task_key='"
					+ task_key + "' and mk.process_instance_id='"
					+ processInstanceId + "'" + " where m.scene_no='"
					+ scene_no + "'";

			List<ListOrderedMap> childSceneResultList = jdbcService
					.queryForList(childSceneSearchSql);

			for (ListOrderedMap childSceneResult : childSceneResultList) {

				ChildSceneModel childSceneModel1 = new ChildSceneModel();
				childSceneModel1.setChildSceneValue(childSceneResult
						.get("copy_no")
						+ "");
				childSceneModel1.setChildSceneName(childSceneResult
						.get("copy_name")
						+ "");
				childSceneModel1.setChecked(childSceneResult.get("flag") + "");
				childSceneList.add(childSceneModel1);

				if ("true".equals(childSceneResult.get("flag"))) {
					copy_no = childSceneResult.get("copy_no") + "";
					copy_name = childSceneResult.get("copy_name") + "";
					dispose = childSceneResult.get("dispose") + "";
					unit = childSceneResult.get("unit") + "";
					isHave = childSceneResult.get("ishave") + "";

				}

			}

			childSceneWholeModel.setChildSceneList(childSceneList);
			childSceneWholeModelList.add(childSceneWholeModel);
		
			//result.put("childSceneWholeModelList", childSceneWholeModelList);
			
			// 取子场景多选结束

			// 取场景明细 开始
			sceneDetailWholeModelList = new ArrayList<SceneDetailWholeModel>();
			SceneDetailWholeModel sceneDetailWholeModel = new SceneDetailWholeModel();
			sceneDetailWholeModel.setMainSceneValue(scene_no);
			sceneDetailWholeModel.setMainSceneName(scene_name);
			sceneDetailWholeModel.setChildSceneValue(copy_no);
			sceneDetailWholeModel.setChildSceneName(copy_name);
			sceneDetailWholeModel.setIsHave(isHave);
			// 表头
			List<String> properties = new ArrayList<String>();
			properties.add("主场景");
			properties.add("子场景");
			properties.add("处理措施");
			properties.add("单位");
			properties.add("材料");
			properties.add("数量");
			properties.add("单位");
			properties.add("单价");
			properties.add("总额");
			properties.add("是否利旧");// 是否利旧
			sceneDetailWholeModel.setProperties(properties);

			// 除去表头的行数（真实数据的行数）
			String copySceneSql = " select tm.item_no from maste_male_item tm where tm.copy_no='"
					+ copy_no + "'";
			List<ListOrderedMap> copySceneSqlList = jdbcService
					.queryForList(copySceneSql);
			int itemSize = copySceneSqlList.size();
			sceneDetailWholeModel.setSceneDetailListSize(itemSize);
			// 明细
			// 第一行
			SceneDetailModel sceneDetailModel1 = new SceneDetailModel();
			sceneDetailModel1.setMainSceneName(scene_name);
			sceneDetailModel1.setChildSceneName(copy_name);
			sceneDetailModel1.setTreatmentMeasures(dispose);
			sceneDetailModel1.setTotalUnit(unit);

			String dataSelSql = "select md.data_no,md.data_name,dk.unit as cunit,\n"
					+ "decode(dk.num,null,mt.limit_num,dk.num) num,\n"
					+ "decode(dk.old_num,null,mt.limit_num,dk.old_num) old_num,\n"
					+ "decode(dk.quota_num,null,mt.limit_num,dk.quota_num) quota_num,\n"
					+ "decode(dk.old_quota_num,null,mt.limit_num,dk.old_quota_num) old_quota_num,\n"
					+ "decode(tk.unit,null,mt.unit,tk.unit) unit,\n"
					+ "decode(tk.num,null,'1',tk.num) unit_num,\n"
					+ "decode(tk.old_unit,null,'1',tk.old_unit) old_unit,\n"
					+ "decode(dk.price,null,md.per_price,dk.price) per_price,\n"
					+ "decode(dk.old_price,null,md.per_price,dk.old_price) old_price,\n"
					+ "md.per_price initial_price,\n"
					+ "decode(dk.total_price,null,md.per_price*mt.limit_num,dk.total_price) total_price,\n"
					+ "case when nvl(dk.id,'0')='0' then 'false' else 'true' end flag,"
					+ "tk.task_key,"
					+ "dt.item_no, "
					+ "dk.is_utilize "
					+ // 是否利旧
					" from maste_male_item_rel_data dt\n"
					+ "left join maste_male_data md on dt.data_no = md.data_no\n"
					+ "left join maste_male_item mt on dt.item_no = mt.item_no\n"
					+ "left join maste_male_data_rel_task dk on dt.item_no=dk.item_no and dt.data_no = dk.data_no\n"
					+ "and dk.process_instance_id='"
					+ processInstanceId
					+ "' and dk.task_key='"
					+ task_key
					+ "'\n"
					+ "left join maste_male_item_rel_task tk on tk.process_instance_id = dk.process_instance_id\n"
					+ "and tk.process_instance_id='" + processInstanceId
					+ "' and tk.task_key='" + task_key + "'\n"
					+ "where dt.scene_no ='" + scene_no
					+ "' and dt.copy_no = '" + copy_no + "'";

			List<SceneDetailModel> sceneDetailList = new ArrayList<SceneDetailModel>();
			System.out.println("----------------dataSelSql:" + dataSelSql);
			//Double totalAmount = 0d;//
			List<ListOrderedMap> dataSelList = jdbcService
					.queryForList(dataSelSql);
			int i = 0;
			for (ListOrderedMap copySceneSqlResult : copySceneSqlList) {
				String item_no = copySceneSqlResult.get("item_no") + "";
				List<MaterialModel> materialList = new ArrayList<MaterialModel>();
				// 其余的行
				SceneDetailModel sceneDetailModel = null;

				if (i != 0) {
					sceneDetailModel = new SceneDetailModel();// 其余行的数据
				}

				for (ListOrderedMap dataSelResult : dataSelList) {

					String md_no = dataSelResult.get("data_no") + "";
					String md_name = dataSelResult.get("data_name") + "";
					String md_flag = dataSelResult.get("flag") + "";

					if (i == 0 && item_no.equals(dataSelResult.get("item_no"))) {// 子场景数据：第一行数据

						MaterialModel materialModel = new MaterialModel(md_no,
								md_name, md_flag);
						materialList.add(materialModel);
						sceneDetailModel1.setMaterialList(materialList);
						sceneDetailModel1.setItemNo(item_no);// 设置材料标示-用于取值

						if ("true".equals(md_flag)) {
							// 数量
							sceneDetailModel1.setNum(dataSelResult.get("num")
									+ "");
							sceneDetailModel1.setOldNum(dataSelResult
									.get("old_num")
									+ "");
							sceneDetailModel1.setQuotaNum(dataSelResult
									.get("quota_num")
									+ "");
							sceneDetailModel1.setOldQuotaNum(dataSelResult
									.get("old_quota_num")
									+ "");

							// 总的单位
							sceneDetailModel1.setTotalUnit(dataSelResult.get("unit")
									+ "");
							//每一行的单位
							sceneDetailModel1.setUnit(dataSelResult.get("cunit")
									+ "");
							sceneDetailModel1.setUnitNum(dataSelResult
									.get("unit_num")
									+ "");
							sceneDetailModel1.setOldUnitNum(dataSelResult
									.get("old_unit")
									+ "");

							// 单价
							sceneDetailModel1.setPrice(dataSelResult
									.get("per_price")
									+ "");
							sceneDetailModel1.setOldPrice(dataSelResult
									.get("old_price")
									+ "");
							sceneDetailModel1.setInitialPrice(dataSelResult
									.get("initial_price")
									+ "");

							sceneDetailModel1.setTotal(dataSelResult
									.get("total_price")
									+ "");
							totalAmount += Double.parseDouble(dataSelResult
									.get("total_price") == null ? "0"
									: dataSelResult.get("total_price") + "");

							// 是否利旧
							List<UtilizeModel> utilizeList = new ArrayList<UtilizeModel>();
							UtilizeModel xz = null;
							UtilizeModel wxz = null;
							if ("1"
									.equals(dataSelResult.get("is_utilize")
											+ "")) {
								xz = new UtilizeModel("1", "是", "true");
								wxz = new UtilizeModel("0", "否", "false");
							} else {
								xz = new UtilizeModel("0", "否", "true");
								wxz = new UtilizeModel("1", "是", "false");
							}
							utilizeList.add(xz);
							utilizeList.add(wxz);
							sceneDetailModel1.setUtilizeList(utilizeList);

						}
					} else if (item_no.equals(dataSelResult.get("item_no"))) {// 子场景数据：其余行的数据

						// 材料
						MaterialModel materialModel = new MaterialModel(md_no,
								md_name, md_flag);

						materialList.add(materialModel);
						sceneDetailModel.setMaterialList(materialList);

						sceneDetailModel.setItemNo(item_no);
						if ("true".equals(md_flag)) {
							// 数量
							sceneDetailModel.setNum(dataSelResult.get("num")
									+ "");
							sceneDetailModel.setOldNum(dataSelResult
									.get("old_num")
									+ "");
							sceneDetailModel.setQuotaNum(dataSelResult
									.get("quota_num")
									+ "");
							sceneDetailModel.setOldQuotaNum(dataSelResult
									.get("old_quota_num")
									+ "");

							sceneDetailModel.setUnit(dataSelResult.get("cunit")
									+ "");
							sceneDetailModel.setUnitNum(dataSelResult
									.get("unit_num")
									+ "");
							sceneDetailModel.setOldUnitNum(dataSelResult
									.get("old_unit")
									+ "");

							// 单价
							sceneDetailModel.setPrice(dataSelResult
									.get("per_price")
									+ "");
							sceneDetailModel.setOldPrice(dataSelResult
									.get("old_price")
									+ "");
							sceneDetailModel.setInitialPrice(dataSelResult
									.get("initial_price")
									+ "");

							sceneDetailModel.setTotal(dataSelResult
									.get("total_price")
									+ "");
							totalAmount += Double.parseDouble(dataSelResult
									.get("total_price") == null ? "0"
									: dataSelResult.get("total_price") + "");

							// 是否利旧
							List<UtilizeModel> utilizeList = new ArrayList<UtilizeModel>();
							UtilizeModel xz = null;
							UtilizeModel wxz = null;
							if ("1"
									.equals(dataSelResult.get("is_utilize")
											+ "")) {
								xz = new UtilizeModel("1", "是", "true");
								wxz = new UtilizeModel("0", "否", "false");
							} else {
								xz = new UtilizeModel("0", "否", "true");
								wxz = new UtilizeModel("1", "是", "false");
							}
							utilizeList.add(xz);
							utilizeList.add(wxz);
							sceneDetailModel.setUtilizeList(utilizeList);

						}
					}
				}
				if (i != 0) {// 其余行的数据

					sceneDetailList.add(sceneDetailModel);
				}
				i++;

			}
			// 材料

			sceneDetailWholeModel.setFirstLineSceneDetail(sceneDetailModel1);

			sceneDetailWholeModel.setSceneDetailList(sceneDetailList);
			sceneDetailWholeModelList.add(sceneDetailWholeModel);
			// 取场景明细 结束

			// 取项目总金额 开始
			//result.put("totalAmount", totalAmount);// 没有传0
			// 取项目总金额 结束
			result.put("sceneDetailWholeModelList", sceneDetailWholeModelList);
		}

		result.put("auditorSceneExistFlag", auditorSceneExistFlag);
		result.put("mainSceneList", mainSceneList);
		result.put("childSceneWholeModelList", childSceneWholeModelList);
		result.put("totalAmount", totalAmount);
		result.put("sceneDetailWholeModelList", sceneDetailWholeModelList);
		result.put("task_key", task_key);
		System.out.println("------------task_key="+task_key);

		return result;
	}
	@Override
	public Map<String, Object> loadNotChosenOtherCostsSubScene(
			String processInstanceId, String linkType, Map<String, String> param) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Map<String, Object> saveSceneTemplate(HttpServletRequest request,
			String processInstanceId, String linkType, Map<String, String> param) {
		
	/*
	 * 	select * from maste_male_sel_rel_task ; ----工单-记录选择的主场景，及子场景。
		select * from maste_male_item_rel_task; ----工单-记录条目详情。
		select * from maste_male_data_rel_task; ----工单-记录材料。
	 */	
		

		String mainSceneNo = "";
		String copySceneNo = "";
		String idSign = "";
		 String  sceneName="";        
		 String  copyName="";         
		 String  dispose ="";           
		 String  unit ="";            
		 String  num ="";  
		 final List<Map> vmaps = new ArrayList();
		
		 //计算施工队环节项目金额估算
		 double totalAmount = 0L;
		//手机回传的信息
		if(param!=null && "android".equals(param.get("operateType"))){
			
			String sceneTemplate = StaticMethod.nullObject2String(request.getParameter("sceneTemplate"));
			//使用json解析传过来的数据
			JSONObject json= new JSONObject(sceneTemplate); 
			mainSceneNo= json.getString("sceneTemplateId");
			copySceneNo= json.getString("sceneId");
			idSign = mainSceneNo+"-"+copySceneNo;
			sceneName= json.getString("sceneTemplateName");
			copyName= json.getString("sceneName");
			dispose= json.getString("sceneMeasure");
			unit= json.getString("sceneUnitName");
			num= json.getString("sceneUnit");

		    JSONArray jsonArray=json.getJSONArray("materialData"); 
			    for(int i=0;i<jsonArray.length();i++){ 
				    JSONObject material=(JSONObject) jsonArray.get(i); 				    
				       Map  vmap = new HashMap();			
					   vmap.put("dataNo", material.getString("materialVersionId"));
					   vmap.put("dataNum", material.getString("materialSum"));
					   vmap.put("dataOldNum", material.getString("materialSum"));
					   vmap.put("quotaNum", material.getString("originalMaterialSum"));
					   vmap.put("oldquotanum", material.getString("originalMaterialSum"));
					   vmap.put("dataUnit",material.getString("materialUnitName"));
					   vmap.put("dataPrice", material.getString("materialPrice"));
					   vmap.put("oldPrice", material.getString("materialPrice"));
					   vmap.put("initialPrice", material.getString("originalMaterialPrice"));
					   vmap.put("totalPrice", material.getString("materialRental"));
					   
					   if(material.getString("materialRental") != null && !"".equals(material.getString("materialRental"))){
						   totalAmount+= Double.parseDouble(material.getString("materialRental"));
					   }
					   
					   vmap.put("isUtilize", StaticMethod.nullObject2String(request.getParameter("isUtilize"),"0"));
					   vmap.put("itemNo", material.getString("materialId"));
					   
					   vmaps.add(vmap);
				  } 
		
		}else{
			String[] mainScenes=request.getParameterValues("mainScene");
			if(mainScenes != null && mainScenes.length > 0 ){
				String[] copyScenes=request.getParameterValues(mainScenes[0]+"-check");	
				if(copyScenes !=null && copyScenes.length > 0){
					mainSceneNo = StaticMethod.nullObject2String(mainScenes[0], "");
					copySceneNo = StaticMethod.nullObject2String(copyScenes[0], "");
					idSign = mainSceneNo+"-"+copySceneNo;
					 sceneName=StaticMethod.nullObject2String(request.getParameter(idSign+"-sceneName"));        
					  copyName=StaticMethod.nullObject2String(request.getParameter(idSign+"-copyName"));         
					   dispose =StaticMethod.nullObject2String(request.getParameter(idSign+"-dispose"));           
					   unit =StaticMethod.nullObject2String(request.getParameter(idSign+"-unitname"));            
					   num =StaticMethod.nullObject2String(request.getParameter(idSign+"-unit"),"1");  
					
					   //工单-记录材料, 根据item字符串获取材料的
					   String copySceneSql = " select tm.item_no from maste_male_item tm where tm.copy_no='"+copySceneNo+"'";
					   final List<ListOrderedMap> copySceneSqlList = commonSpringJdbcService.queryForList(copySceneSql);
					   int copySceneSqlListSize = copySceneSqlList.size();
					   
					   for(ListOrderedMap map:copySceneSqlList){
						   Map  vmap = new HashMap();			
						   String idflag = idSign+"-"+map.get("item_no");			
						   vmap.put("dataNo", StaticMethod.nullObject2String(request.getParameter(idflag+"-material")));
						   vmap.put("dataNum", StaticMethod.nullObject2String(request.getParameter(idflag+"-num")));
						   vmap.put("dataOldNum", StaticMethod.nullObject2String(request.getParameter(idflag+"-oldnum")));
						   vmap.put("quotaNum", StaticMethod.nullObject2String(request.getParameter(idflag+"-quotanum")));
						   vmap.put("oldquotanum", StaticMethod.nullObject2String(request.getParameter(idflag+"-oldquotanum")));
						   //vmap.put("dataUnit", StaticMethod.nullObject2String(request.getParameter(idflag+"-unit")));
						   vmap.put("dataUnit", StaticMethod.nullObject2String(request.getParameter(idflag+"-cunit")));
						   vmap.put("dataPrice", StaticMethod.nullObject2String(request.getParameter(idflag+"-price")));
						   vmap.put("oldPrice", StaticMethod.nullObject2String(request.getParameter(idflag+"-oldprice")));
						   vmap.put("initialPrice", StaticMethod.nullObject2String(request.getParameter(idflag+"-initialprice")));
						   vmap.put("totalPrice", StaticMethod.nullObject2String(request.getParameter(idflag+"-ctotal")));
						   vmap.put("isUtilize", StaticMethod.nullObject2String(request.getParameter(idflag+"-utilize")));
						   vmap.put("itemNo", StaticMethod.nullObject2String(map.get("item_no")+""));
						   vmaps.add(vmap);
					   }
				}
			}
		}
		
		//流程类型标识
		String processType = "";
		if(param.get("processType") != null && !"".equals(param.get("processType"))){
			processType = param.get("processType");
		}
		final String fprocessType = processType;
		
		final String flinkType = linkType;
		final String fprocessInstanceId = processInstanceId;
		
		final String fmainSceneNo = mainSceneNo;
		final String fcopySceneNo = copySceneNo;
		//根据子场景id到数据查询 ，获取变量名到前台页面获取值。	               
		final String  fsceneName=sceneName;        
		final String  fcopyName=copyName;         
		final String  fdispose =dispose;           
		final String  funit =unit;            
		final String  fnum =num;  
		
		//先删除
		this.deleteSelRelTasks(fprocessInstanceId, flinkType, fprocessType,null); //先删除maste_male_sel_rel_task表中的数据
		this.deleteItemTask(fprocessInstanceId, flinkType,fprocessType,null);
		this.deleteDataTask(processInstanceId, linkType, fprocessType, null);
		
		//在插入
		if(!"".equals(mainSceneNo) && !"".equals(copySceneNo)){
			insertSelRelTasks(flinkType,fprocessInstanceId,mainSceneNo,copySceneNo,fprocessType);
		    insertItemTask(flinkType,fprocessInstanceId,mainSceneNo,copySceneNo,fsceneName,fcopyName,fdispose,funit,fnum,fprocessType);
			insertDataTask(fprocessInstanceId,flinkType,vmaps,fprocessType);
		}
		
		if(param!=null && "android".equals(param.get("operateType"))){
			Map<String, Object> resultMap = new HashMap<String, Object>();
			resultMap.put("totalAmount", Double.toString(CommonUtils.reservedDecimalPlaces(totalAmount,2)));
			return resultMap;
		}
		
		return null;
	}
	
	//删除 maste_male_sel_rel_task表中数据 t1
	private void deleteSelRelTasks(String processInstanceId,String linkType,String processType,Map<String,String> param){
		String delselRelTasksql = "delete from maste_male_sel_rel_task k where k.process_instance_id='"+processInstanceId+"' and k.task_key='"+linkType+"'";
	    this.getJdbcTemplate().execute(delselRelTasksql);
	}
	
	/**
	
	 * 插入选择的主、子场景信息
	
	 * @param flinkType
	 * @param fprocessInstanceId
	 * @param mainSceneNo
	 * @param copySceneNo
	 * @return
	 */
	private boolean insertSelRelTasks(final String flinkType,final String fprocessInstanceId,final String mainSceneNo,final String copySceneNo,final String fprocessType){
		
		//this.deleteSelRelTasks(fprocessInstanceId, flinkType, fprocessType,null); //先删除maste_male_sel_rel_task表中的数据
//		String delselRelTasksql = "delete from maste_male_sel_rel_task k where k.process_instance_id='"+fprocessInstanceId+"' and k.task_key='"+flinkType+"'";
//	    this.getJdbcTemplate().execute(delselRelTasksql);
		
	    String selRelTasksql = "insert into maste_male_sel_rel_task(ID,SCENE_NO_STR,COPY_NO_STR,TASK_KEY,PROCESS_INSTANCE_ID,INSERT_TIME,PROCESS_TYPE) values(sys_guid(),?,?,?,?,sysdate,?)";
        
   
        this.getJdbcTemplate().execute(selRelTasksql, new PreparedStatementCallback(){
			@Override
			public Object doInPreparedStatement(PreparedStatement ps)
					throws SQLException, DataAccessException {
				ps.setString(1, mainSceneNo);
				ps.setString(2, copySceneNo);
				ps.setString(3, flinkType);
				ps.setString(4, fprocessInstanceId);
				ps.setString(5, fprocessType);
				return ps.execute();
			}
			
		});
		return true;
	}
	
	//删除maste_male_item_rel_task表中的数据 t2
	private void deleteItemTask(String processInstanceId,String linkType,String processType,Map<String,String> param){
		String delitemRelTasksql = "delete from maste_male_item_rel_task k where k.process_instance_id='"+processInstanceId+"' and k.task_key='"+linkType+"'";
	    this.getJdbcTemplate().execute(delitemRelTasksql);
	}
	
	/**
	 * 插入工单的条目信息，只有一条哦
	 * @param flinkType
	 * @param fprocessInstanceId
	 * @param mainSceneNo
	 * @param copySceneNo
	 * @param sceneName
	 * @param copyName
	 * @param dispose
	 * @param unit
	 * @param num
	 * @return
	 */
	private boolean insertItemTask(final String flinkType,final String fprocessInstanceId,final String mainSceneNo,final String copySceneNo,final String sceneName,final String copyName,final String dispose,final String unit,final String num,final String fprocessType){
		
		//this.deleteItemTask(fprocessInstanceId, flinkType,fprocessType,null);
//		String delitemRelTasksql = "delete from maste_male_item_rel_task k where k.process_instance_id='"+fprocessInstanceId+"' and k.task_key='"+flinkType+"'";
//	    this.getJdbcTemplate().execute(delitemRelTasksql);
		
	    String itemRelTasksql = "insert into maste_male_item_rel_task(ID,SCENE_NO,SCENE_NAME,COPY_NO,COPY_NAME,DISPOSE,UNIT,NUM,TASK_KEY,PROCESS_INSTANCE_ID,INSERT_TIME,PROCESS_TYPE) values(sys_guid(),?,?,?,?,?,?,?,?,?,sysdate,?)";
        this.getJdbcTemplate().execute(itemRelTasksql, new PreparedStatementCallback(){
			@Override
			public Object doInPreparedStatement(PreparedStatement ps)
					throws SQLException, DataAccessException {
				ps.setString(1, mainSceneNo);
				ps.setString(2, sceneName);
				ps.setString(3, copySceneNo);
				ps.setString(4, copyName);
				ps.setString(5, dispose);
				ps.setString(6, unit);
				ps.setString(7, num);
				ps.setString(8, flinkType);
				ps.setString(9, fprocessInstanceId);
				ps.setString(10, fprocessType);
				
				return ps.execute();
			}
			
		});
		
		return true;
		
	}
	
	//删除maste_male_data_rel_task表中的数据 t3
	private void deleteDataTask(String processInstanceId,String linkType,String processType,Map<String,String> param){
		String deldataRelTasksql = "delete from maste_male_data_rel_task k where k.process_instance_id='"+processInstanceId+"' and k.task_key='"+linkType+"'";
	    this.getJdbcTemplate().execute(deldataRelTasksql);
	}
	
	
	/**
	
	 * 插入材料
	 
	 * @param processInstanceId
	 * @param linkType
	 * @param vmaps
	 * @return
	 */
	private boolean insertDataTask(final String processInstanceId,final String linkType,final List<Map> vmaps,final String fprocessType){
		
		//this.deleteDataTask(processInstanceId, linkType, fprocessType, null);
//		String deldataRelTasksql = "delete from maste_male_data_rel_task k where k.process_instance_id='"+processInstanceId+"' and k.task_key='"+linkType+"'";
//	    this.getJdbcTemplate().execute(deldataRelTasksql);
	    
	    String dataRelTasksql = "insert into maste_male_data_rel_task(ID,DATA_NO,NUM,UNIT,PRICE,TOTAL_PRICE,TASK_KEY,PROCESS_INSTANCE_ID,ITEM_NO,OLD_NUM,QUOTA_NUM,OLD_QUOTA_NUM,OLD_PRICE,INITIAL_PRICE,IS_UTILIZE,INSERT_TIME,PROCESS_TYPE) values(sys_guid(),?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate,?)";
	    this.getJdbcTemplate().batchUpdate(dataRelTasksql,new BatchPreparedStatementSetter(){
		      
		      public int getBatchSize(){
//		       return copySceneSqlList.size();
		       return vmaps.size();
		      }
		      public void setValues(PreparedStatement ps,int i){
		       
		    	Map obj = vmaps.get(i);
			       try{
				     ps.setString(1, obj.get("dataNo").toString());
				     ps.setString(2, obj.get("dataNum").toString());
				     ps.setString(3, obj.get("dataUnit").toString());
				     ps.setString(4, obj.get("dataPrice").toString());
				     ps.setString(5, obj.get("totalPrice").toString());
				     ps.setString(6, linkType);
				     ps.setString(7, processInstanceId);
				     ps.setString(8, obj.get("itemNo").toString());
				     ps.setString(9, obj.get("dataOldNum").toString());
				     ps.setString(10, obj.get("quotaNum").toString());
				     ps.setString(11, obj.get("oldquotanum").toString());
				     ps.setString(12, obj.get("oldPrice").toString());
				     ps.setString(13, obj.get("initialPrice").toString());
				     ps.setString(14, obj.get("isUtilize").toString());
				     ps.setString(15, fprocessType);
			       }catch(Exception e){
				        e.printStackTrace();
				   }
		       } 
		     });
		
		
		return true;
		
	}
	@Override
	public HSSFWorkbook scenarioTemplateExcel(String processInstanceId,
			String linkType, Map<String, String> param) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
