package com.boco.eoms.partner.assess.AssAlgorithm.webapp.action;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.ui.util.JSONUtil;
import com.boco.eoms.partner.assess.AssAlgorithm.mgr.IAssKpiConfigMgr;
import com.boco.eoms.partner.assess.AssAlgorithm.mgr.IAssSelectedInstanceMgr;
import com.boco.eoms.partner.assess.AssAlgorithm.model.AssKpiConfig;
import com.boco.eoms.partner.assess.AssAlgorithm.model.AssSelectedInstance;
import com.boco.eoms.partner.assess.AssTree.mgr.IAssTreeMgr;
import com.boco.eoms.partner.assess.AssTree.model.AssTree;
import com.boco.eoms.partner.assess.util.AssStaticMethod;

/**
 * <p>
 * Title:指标打分实例
 * </p>
 * <p>
 * Description:指标打分实例
 * </p>
 * <p>
 * Tue Nov 16 09:08:10 CST 2010
 * </p>
 * 
 * @moudle.getAuthor() 戴志刚
 * @moudle.getVersion() 1.0
 * 
 */
 
 public abstract class AssSelectedInstanceAction extends BaseAction {
	 
	 protected String beenNameForSelectedInstanceMgr = "";
	 protected String beenNameForKpiConfigMgr = "";
	 protected String beenNameForTreeMgr = "";
 
	/**
	 * 保存指标打分配置
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IAssSelectedInstanceMgr instanceMgr = (IAssSelectedInstanceMgr) getBean(beenNameForSelectedInstanceMgr);
		IAssKpiConfigMgr assKpiConfigMgr = (IAssKpiConfigMgr) getBean(beenNameForKpiConfigMgr);
		IAssTreeMgr assTreeMgr = (IAssTreeMgr) getBean(beenNameForTreeMgr);
		String nodeId = StaticMethod.null2String(request.getParameter("nodeId"));
		String kpiId = StaticMethod.null2String(request.getParameter("kpiId"));
		String taskId = StaticMethod.null2String(request.getParameter("taskId"));
		String partnerId = StaticMethod.null2String(request.getParameter("partnerId"));
		String time = StaticMethod.null2String(request.getParameter("time"));
		String areaId = StaticMethod.null2String(request.getParameter("areaId"));
		String parNum = StaticMethod.null2String(request.getParameter("parNum"));
		
		AssTree assTree = assTreeMgr.getTreeNodeByNodeId(nodeId);
		try{	
		String configId = "";
		String algorithm = "";
		float weight = 0;
		float allWeight = 0;
		float kpiWeight = assTree.getWeight();
		//删除原有该指标的打分实例
		instanceMgr.removeAssSelectedInstances(kpiId,taskId,areaId,time,partnerId);
		List list = assKpiConfigMgr.getConfigsByKpiId(kpiId,"group");
		

	    JSONObject timeItem = new JSONObject();
		
		for(int i=0;i<list.size();i++){
			AssKpiConfig config = (AssKpiConfig)list.get(i);
			//判断填写值是否在值域范围内
			boolean showGroup = AssStaticMethod.isInBound(parNum,config.getNumRelation(),config.getNumConfig());
			if(showGroup||config.getNumConfig()==null){
			
			
			if("1".equals(config.getAlgorithm())){
				String numValue = request.getParameter(config.getId());
				if(!"".equals(numValue)){
				AssSelectedInstance instance = new AssSelectedInstance();
				instance.setKpiId(kpiId);
				instance.setTaskId(taskId);
				instance.setPartnerId(partnerId);
				instance.setTime(time);
				instance.setConfigId(config.getId());
				instance.setWeight(Float.parseFloat(numValue));
				instance.setAreaId(areaId);
				instanceMgr.saveAssSelectedInstance(instance);
				//
				allWeight = allWeight + Float.parseFloat(numValue);
				}
			}else if("2".equals(config.getAlgorithm())||"5".equals(config.getAlgorithm())){//直接折算，设备折算
				String numValue = StaticMethod.nullObject2String(request.getParameter(config.getId()),"0");
				if("".equals(numValue)){
					numValue = "0";
				}
				if("".equals(parNum)){
					timeItem.put("failMsg", "填写对应的网组设备量有误");
					timeItem.put("failure", "true");
					JSONUtil.print(response, timeItem.toString());
					return null;
				}
				//如果是直接折算则不除以设备数，如果是设备折算则除以设备数
				float rate = Float.parseFloat(numValue);
				if("2".equals(config.getAlgorithm())){
					rate = Float.parseFloat(numValue)/Float.parseFloat(parNum);
				}
				if(!"".equals(numValue)){
				AssSelectedInstance instance = new AssSelectedInstance();
				instance.setKpiId(kpiId);
				instance.setTaskId(taskId);
				instance.setPartnerId(partnerId);
				instance.setTime(time);
				instance.setConfigId(config.getId());
				//根据填写的数值得到不同折算后的得分
				float lastWeight = assKpiConfigMgr.getLastWeightByRange(config.getNodeId(), rate);
				instance.setWeight(Float.parseFloat(numValue));
				instance.setAreaId(areaId);
				instanceMgr.saveAssSelectedInstance(instance);
				//
				allWeight = allWeight + lastWeight*kpiWeight/100;
				}
			}else if("3".equals(config.getAlgorithm())){
				String radioValue = request.getParameter(config.getId());
				if(radioValue!=null){
				configId = radioValue.split("_")[0];
				weight = Float.parseFloat(radioValue.split("_")[1]);
				AssSelectedInstance instance = new AssSelectedInstance();
				instance.setKpiId(kpiId);
				instance.setTaskId(taskId);
				instance.setPartnerId(partnerId);
				instance.setTime(time);
				instance.setConfigId(configId);
				instance.setWeight(weight);
				instance.setAreaId(areaId);
				instanceMgr.saveAssSelectedInstance(instance);
				//
				allWeight = allWeight + weight*kpiWeight/100;
				}
			}else if("4".equals(config.getAlgorithm())){
				String[] checkboxValue = request.getParameterValues(config.getId());
				for(int j=0;checkboxValue!=null&&j<checkboxValue.length;j++){
					AssSelectedInstance instance = new AssSelectedInstance();
					configId = checkboxValue[j].split("_")[0];
					weight = Float.parseFloat(checkboxValue[j].split("_")[1]);
					instance.setKpiId(kpiId);
					instance.setTaskId(taskId);
					instance.setPartnerId(partnerId);
					instance.setTime(time);
					instance.setConfigId(configId);
					instance.setWeight(weight);
					instance.setAreaId(areaId);
					instanceMgr.saveAssSelectedInstance(instance);
					//
					allWeight = allWeight + weight*kpiWeight/100;
				}
			}else if("6".equals(config.getAlgorithm())){
				String radioValue = request.getParameter(config.getId());
				if(radioValue!=null){
				configId = radioValue.split("_")[0];
				weight = Float.parseFloat(radioValue.split("_")[1]);
				AssSelectedInstance instance = new AssSelectedInstance();
				instance.setKpiId(kpiId);
				instance.setTaskId(taskId);
				instance.setPartnerId(partnerId);
				instance.setTime(time);
				instance.setConfigId(configId);
				instance.setWeight(weight);
				instance.setAreaId(areaId);
				instanceMgr.saveAssSelectedInstance(instance);
				//
				allWeight = allWeight + weight/100;
				algorithm="deductScore";
				}
			}else if("7".equals(config.getAlgorithm())){
				String radioValue = request.getParameter(config.getId());
				if(radioValue!=null){
				configId = radioValue.split("_")[0];
				weight = Float.parseFloat(radioValue.split("_")[1]);
				AssSelectedInstance instance = new AssSelectedInstance();
				instance.setKpiId(kpiId);
				instance.setTaskId(taskId);
				instance.setPartnerId(partnerId);
				instance.setTime(time);
				instance.setConfigId(configId);
				instance.setWeight(weight);
				instance.setAreaId(areaId);
				instanceMgr.saveAssSelectedInstance(instance);
				//
				allWeight = allWeight + weight;
				}
			}else if("8".equals(config.getAlgorithm())){
				String[] checkboxValue = request.getParameterValues(config.getId());
				for(int j=0;checkboxValue!=null&&j<checkboxValue.length;j++){
					AssSelectedInstance instance = new AssSelectedInstance();
					configId = checkboxValue[j].split("_")[0];
					weight = Float.parseFloat(checkboxValue[j].split("_")[1]);
					instance.setKpiId(kpiId);
					instance.setTaskId(taskId);
					instance.setPartnerId(partnerId);
					instance.setTime(time);
					instance.setConfigId(configId);
					instance.setWeight(weight);
					instance.setAreaId(areaId);
					instanceMgr.saveAssSelectedInstance(instance);
					//
					allWeight = allWeight + weight;
				}
			}
		  }
		}
		timeItem.put("algorithm", algorithm);
		timeItem.put("success", "true");
		timeItem.put("nodeId", nodeId);
		timeItem.put("partnerId", partnerId);
		if((allWeight/kpiWeight)<=0.25){
			timeItem.put("color", "\'red\'");
		}else{
			timeItem.put("color", "\'black\'");
		}
		//
		timeItem.put("value", AssStaticMethod.floatFormat(allWeight));
		JSONUtil.print(response, timeItem.toString());
		}catch(Exception e){
			e.printStackTrace();
			JSONObject timeItem = new JSONObject();
			timeItem.put("failure", "true");
			JSONUtil.print(response, timeItem.toString());
		}
		return null;
	}
	
	/**
	 * 删除指标打分配置
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 
	public ActionForward remove(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String kpiId = StaticMethod.null2String(request.getParameter("kpiId"));
		String taskId = StaticMethod.null2String(request.getParameter("taskId"));
		String partnerId = StaticMethod.null2String(request.getParameter("partnerId"));
		String time = StaticMethod.null2String(request.getParameter("time"));
		
		
		return mapping.findForward("success");
	}
	*/
	/**
	 * 得到某指标对应的指标实例集合
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 
	public ActionForward getInstanceByKpiId(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IAssSelectedInstanceMgr instanceMgr = (IAssSelectedInstanceMgr) getBean(beenNameForSelectedInstanceMgr);
		String kpiId = StaticMethod.null2String(request.getParameter("kpiId"));
		String taskId = StaticMethod.null2String(request.getParameter("taskId"));
		String partnerId = StaticMethod.null2String(request.getParameter("partnerId"));
		String time = StaticMethod.null2String(request.getParameter("time"));

		return null;
	}
	*/
}