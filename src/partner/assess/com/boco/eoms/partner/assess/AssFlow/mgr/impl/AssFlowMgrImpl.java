/**
 * 
 */
package com.boco.eoms.partner.assess.AssFlow.mgr.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.lang.reflect.Field;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.common.util.StaticMethod;
import com.boco.eoms.commons.system.role.model.TawSystemSubRole;
import com.boco.eoms.commons.system.role.service.ITawSystemSubRoleManager;
import com.boco.eoms.partner.assess.AssFlow.mgr.IAssFlowMgr;
import com.boco.eoms.partner.assess.AssFlow.model.AssFlow;
import com.boco.eoms.partner.assess.AssRight.util.AssRoleIdList;

/**
 * <p>
 * Title:后评估模块流程管理业务类
 * </p>
 * <p>
 * Description:后评估模块流程管理业务类
 * </p>
 * <p>
 * Date:Nov 24, 2010 9:12:59 AM
 * </p>
 * 
 * @author 戴志刚
 * @version 1.0
 * 
 */
public abstract class AssFlowMgrImpl implements IAssFlowMgr{

	protected String beenNameForRoleService = "";
	protected String beenNameForRoleIdList = "";
	/*
	 * 流程配置文件路径
	 */
	protected String FLOW_XML_PATH = "com/boco/eoms/assess/AssFlow/config/AssFlow.xml";

	/**
	 * 根据当前状态得到对应的AssFlow对象（包含流转信息）
	 * 
	 * @param state 当前状态
	 * @return AssFlow 审核流程流转配置对应类对象
	 */
	public AssFlow getAssFlowByXml(String state) {
		AssFlow assFlow = new AssFlow();
		try {
			SAXReader reader = new SAXReader();
			String path = getFilePathForUrl(FLOW_XML_PATH);
			Document document = reader.read(new File(path));
			Element root = document.getRootElement();
			List nodes = root.elements("cell");
			Element elm = null;
			String cellId = null;
			for (Iterator it = nodes.iterator(); it.hasNext();) {
				elm = (Element) it.next();
				cellId = elm.attribute("id").getText();
				if (cellId.equals(state)) {
					assFlow.setId(cellId);
					assFlow.setRoleName(elm.attribute("roleName").getText());
					assFlow.setOperater(elm.attribute("operater").getText());
					assFlow.setDescription(elm.attribute("description")
							.getText());
					assFlow.setRejectCell(elm.attribute("rejectCell")
							.getText());
					assFlow.setGotoCell(elm.attribute("gotoCell").getText());
					assFlow.setPageType(elm.attribute("pageType").getText());
					break;
				}
				// do something
			}

		} catch (Exception e) {
			e.printStackTrace();
			assFlow = null;
		}
		return assFlow;
	}

	/**
	 * 读java包时返回的路径
	 * 
	 * @param filePath
	 *            文件路径
	 * @return
	 * @throws FileNotFoundException
	 */
	public String getFilePathForUrl(String filePath)
			throws FileNotFoundException {
		URL url = getFileUrl(filePath);
		return url.getFile();
	}

	/**
	 * 获取filePath的url
	 * 
	 * @param filePath
	 * @return
	 * @throws FileNotFoundException
	 *             创建url失败将抛出MalformedURLException
	 */
	public URL getFileUrl(String filePath) throws FileNotFoundException {
		URL url = null;
		if (filePath != null) {
			try {
				url = AssFlowMgrImpl.class.getClassLoader().getResource(filePath);
			} catch (Exception e) {
				throw new FileNotFoundException(filePath + "is not found.");
			}
		} else {
			// TODO 有问题，需修改
		}
		return url;
	}
	

	 /**    
	  * @将POJO对象转成Map    
	  */     
	     
	 public Map pojo2Map(Object obj) {      
	  Map hashMap = new HashMap();      
	  try {      
	   Class c = obj.getClass();      
	   Field f[] = c.getDeclaredFields();

	   for (int i = 0; i < f.length; i++) {              
	     hashMap.put(f[i].getName(), f[i].get(obj));      
	    }      
	  } catch (Throwable e) {      
	   System.err.println(e);      
	  }      
	  return hashMap;      
	 } 

	/**
	 * 根据地域id得到执行角色对应的角色id
	 * 
	 * @param state 当前状态
	 * @param areaId 地域
	 * @return subRoleId 执行角色对应的角色id
	 */
	public String getSubRoleIdByArea(String state,String areaId) {
		String subRoleId = "";
		AssRoleIdList roleIdList = (AssRoleIdList)ApplicationContextHolder.getInstance().getBean(beenNameForRoleIdList);
		Map map = pojo2Map(roleIdList);
		AssFlow assFlow = getAssFlowByXml(state); 
		String operater = assFlow.getOperater();
		String roleId = StaticMethod.nullObject2String(map.get(operater));
		ITawSystemSubRoleManager subRoleMgr = (ITawSystemSubRoleManager)ApplicationContextHolder
		.getInstance().getBean("ItawSystemSubRoleManager");
		List subRoleList = subRoleMgr.getSubRolesByDeptId(areaId,"0",roleId);
		if(subRoleList.size()==1){
			TawSystemSubRole subRole = (TawSystemSubRole)subRoleList.get(0);
			subRoleId = subRole.getId();
		}
		//得到区公司角色
		if(subRoleList.size()==0){
			subRoleList = subRoleMgr.getSubRolesByDeptId(areaId.substring(0, areaId.length()-2),"0",roleId);
			TawSystemSubRole subRole = (TawSystemSubRole)subRoleList.get(0);
			subRoleId = subRole.getId();
		}
		return subRoleId;
	}
	
}
