package com.boco.activiti.partner.process.service.impl;

import java.util.Date;
import java.util.Map;

import org.acegisecurity.providers.encoding.Md5PasswordEncoder;

import com.boco.activiti.partner.process.dao.INetResInspectUserDao;
import com.boco.activiti.partner.process.dao.INetResInspectUserJDBCDao;
import com.boco.activiti.partner.process.model.NetResInspectUser;
import com.boco.activiti.partner.process.service.INetResInspectUserService;
import com.boco.eoms.commons.system.user.model.TawSystemUser;
import com.boco.eoms.commons.system.user.service.ITawSystemUserManager;
import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.partner.process.util.CommonUtils;

/**
 * 
 * @author WANGJUN
 * @ClassName: INetResInspectUserService
 * @Version 1.0
 * @ModifiedBy
 * @Copyright BOCO
 * @date Sep 7, 2016 2:39:53 PM
 * @description 巡检众筹新注册用户
 */
public class NetResInspectUserServiceImpl extends
		CommonGenericServiceImpl<NetResInspectUser> implements
		INetResInspectUserService {
	private INetResInspectUserDao netResInspectUserDao;
	private INetResInspectUserJDBCDao netResInspectUserJDBCDao;
	private ITawSystemUserManager tawSystemUserSaveManagerFlush;

	public INetResInspectUserDao getNetResInspectUserDao() {
		return netResInspectUserDao;
	}

	public void setNetResInspectUserDao(
			INetResInspectUserDao netResInspectUserDao) {
		this.netResInspectUserDao = netResInspectUserDao;
		this.setCommonGenericDao(netResInspectUserDao);
	}

	public INetResInspectUserJDBCDao getNetResInspectUserJDBCDao() {
		return netResInspectUserJDBCDao;
	}

	public void setNetResInspectUserJDBCDao(
			INetResInspectUserJDBCDao netResInspectUserJDBCDao) {
		this.netResInspectUserJDBCDao = netResInspectUserJDBCDao;
	}
	
	public ITawSystemUserManager getTawSystemUserSaveManagerFlush() {
		return tawSystemUserSaveManagerFlush;
	}

	public void setTawSystemUserSaveManagerFlush(
			ITawSystemUserManager tawSystemUserSaveManagerFlush) {
		this.tawSystemUserSaveManagerFlush = tawSystemUserSaveManagerFlush;
	}

	/**
	 * 	 通过地市ID获取到联通的区县ID
	 	 * @author WANGJUN
	 	 * @title: getDeptIdByAreaId
	 	 * @date Sep 7, 2016 4:07:45 PM
	 	 * @return String
	 */
	public String getDeptIdByAreaId(String areaId){
		return netResInspectUserJDBCDao.getDeptIdByAreaId(areaId);
	}
	
	/**
	 * 	 保存巡检众筹用户
	 	 * @author WANGJUN
	 	 * @title: saveNetResInspectUser
	 	 * @date Sep 8, 2016 8:42:09 AM
	 	 * @param param
	 	 * @return String
	 */
	public String saveNetResInspectUser(Map<String,String> param){
		String returnJson = "";
		boolean userExist = tawSystemUserSaveManagerFlush.isUserExist(param.get("phone"));
		if(!userExist){
			try{
				TawSystemUser tawSystemUser = new TawSystemUser();
				tawSystemUser.setUserid(param.get("phone"));
				tawSystemUser.setUsername(param.get("userName"));
				tawSystemUser.setMobile(param.get("phone"));
				tawSystemUser.setPassword(new Md5PasswordEncoder().encodePassword(param.get("password"),new String()));//密码 
				tawSystemUser.setDeleted("0");
				tawSystemUser.setSavetime(new Date());
				tawSystemUser.setEnabled(true);
				tawSystemUser.setAccountLocked(false);
				tawSystemUser.setId("");
				tawSystemUser.setIsPartners("0");
				tawSystemUser.setDeptid(this.getDeptIdByAreaId(param.get("cityId")));
				
				//往taw_system_user保存数据
				tawSystemUserSaveManagerFlush.saveTawSystemUser(tawSystemUser,"");
				
				//往pnr_act_netresinspect_user表中保存所属组织,地市ID,工作属性
				NetResInspectUser netResInspectUser = new NetResInspectUser();
				netResInspectUser.setUserid(param.get("phone"));
				netResInspectUser.setGroupname(param.get("group"));
				netResInspectUser.setCityId(param.get("cityId"));
				netResInspectUser.setJobAttributesId(param.get("jobAttributesId"));
				this.save(netResInspectUser);
				
				//发短信
				String msg = param.get("userName")+"，您好，您已成功注册现场综合维护平台账号，账户名"+param.get("phone")+"，密码"+param.get("password")+"，您可登陆手机端通过巡检众筹功能上报您发现的网络资源隐患，请保存好您的账户密码";
				CommonUtils.sendMsg(msg, param.get("phone"));
				
				//给手机端返回结果
				returnJson ="success";
			}catch(Exception e){
				e.printStackTrace();
				returnJson ="error";
			}
		}else{
			returnJson ="userExist";
		}
		return returnJson;
	}
}
