package com.boco.activiti.partner.process.dao;

import java.util.List;

import com.boco.activiti.partner.process.model.NetResInspect;
import com.boco.activiti.partner.process.model.NetResInspectTurnToSendModel;
import com.boco.activiti.partner.process.model.PnrAndroidPhotoFile;
import com.boco.activiti.partner.process.model.PnrPrecheckPhoto;
import com.boco.eoms.deviceManagement.common.dao.CommonGenericDao;

/**
 
 */
public interface INetResInspectInfoDao{
	/**
	 * 	 保存转派信息
	 	 * @author zhoukeqing
	 	 * @title: saveTurnToSendInfo
	 	 * @date Jul 11, 2016 2:42:35 PM
	 	 * @param param
	 	 * @return String
	 */
	public void saveTurnToSendInfo(NetResInspectTurnToSendModel netResInspectTurnToSendModel);
	/**
	 * 	 手机工单代办列表查询
	 	 * @author zhoukeqing
	 	 * @title: searchListsendundo
	 	 * @date Jul 13, 2016 2:42:35 PM
	 	 * @param param
	 	 * @return String
	 */
	public List<NetResInspect> searchListsendundo(String userid,int pageSize, int pageIndex);
	/**
	 * 	 手机工单代办列表总数查询
	 	 * @author zhoukeqing
	 	 * @title: searchListsendundoTotal
	 	 * @date Jul 13, 2016 2:42:35 PM
	 	 * @param param
	 	 * @return String
	 */
	public int searchListsendundoTotal(String userid);
	/**
	 * 	 手机工单详情页面查询
	 	 * @author zhoukeqing
	 	 * @title: serchDetailAndroid
	 	 * @date Jul 13, 2016 2:42:35 PM
	 	 * @param param
	 	 * @return String
	 */
	public NetResInspect serchDetailAndroid(String processInstanceId);
	/**
	 * 	 查询pnr_android_photo
	 	 * @author zhoukeqing
	 	 * @title: serchAndroidPhoto
	 	 * @date Jul 13, 2016 2:42:35 PM
	 	 * @param param
	 	 * @return String
	 */
	public List<PnrAndroidPhotoFile> serchAndroidPhoto(String processInstanceId);
	/**
	 * 	 将手机拍摄的照片和流程关联
	 	 * @author zhoukeqing
	 	 * @title: saveFlowPhone
	 	 * @date Jul 15, 2016 2:42:35 PM
	 	 * @param param
	 	 * @return String
	 */
	public void saveFlowPhone(String processInstanceId,String type);
	/**
	 * 	 将手机拍摄的照片和流程关联  新建工单用
	 	 * @author zhoukeqing
	 	 * @title: saveFlowPhone
	 	 * @date Jul 15, 2016 2:42:35 PM
	 	 * @param param
	 	 * @return String
	 */
	public void savePhoneStarFlow(List<PnrAndroidPhotoFile> list,String processInstanceId);
	/**
	 * 	 将手机拍摄的照片和流程关联  派发子流程用
	 	 * @author zhoukeqing
	 	 * @title: saveFlowPhone
	 	 * @date Jul 15, 2016 2:42:35 PM
	 	 * @param param
	 	 * @return String
	 */
	public void savePhoneSubFlow(List<PnrAndroidPhotoFile> list,String processInstanceId);
}
