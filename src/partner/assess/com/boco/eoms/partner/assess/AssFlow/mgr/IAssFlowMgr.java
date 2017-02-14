/**
 * 
 */
package com.boco.eoms.partner.assess.AssFlow.mgr;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.common.util.StaticMethod;
import com.boco.eoms.commons.system.role.model.TawSystemSubRole;
import com.boco.eoms.commons.system.role.service.ITawSystemSubRoleManager;
import com.boco.eoms.partner.assess.AssFlow.mgr.impl.AssFlowMgrImpl;
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
 * Date:Nov 24, 2010 9:11:06 AM
 * </p>
 * 
 * @author 戴志刚
 * @version 1.0
 * 
 */
public interface IAssFlowMgr {

	/**
	 * 根据当前状态得到对应的AssFlow对象（包含流转信息）
	 * 
	 * @param state 当前状态
	 * @return AssFlow 审核流程流转配置对应类对象
	 */
	public AssFlow getAssFlowByXml(String state);

	/**
	 * 读java包时返回的路径
	 * 
	 * @param filePath
	 *            文件路径
	 * @return
	 * @throws FileNotFoundException
	 */
	public String getFilePathForUrl(String filePath)
			throws FileNotFoundException;

	/**
	 * 获取filePath的url
	 * 
	 * @param filePath
	 * @return
	 * @throws FileNotFoundException
	 *             创建url失败将抛出MalformedURLException
	 */
	public URL getFileUrl(String filePath) throws FileNotFoundException;
	

	 /**    
	  * @将POJO对象转成Map    
	  */     
	     
	 public Map pojo2Map(Object obj);

	/**
	 * 根据地域id得到执行角色对应的角色id
	 * 
	 * @param state 当前状态
	 * @param areaId 地域
	 * @return subRoleId 执行角色对应的角色id
	 */
	public String getSubRoleIdByArea(String state,String areaId);
}
