package com.boco.eoms.partner.taskManager.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.commons.system.user.model.TawSystemUser;
import com.boco.eoms.commons.system.user.service.ITawSystemUserManager;
import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.partner.baseinfo.mgr.PartnerDeptMgr;
import com.boco.eoms.partner.baseinfo.model.PartnerDept;
import com.boco.eoms.partner.taskManager.dao.ICarApproveDao;
import com.boco.eoms.partner.taskManager.model.CarApprove;
import com.boco.eoms.partner.taskManager.model.CarApproveLink;
import com.boco.eoms.partner.taskManager.model.CarApproveTask;
import com.boco.eoms.partner.taskManager.model.CarTask;
import com.boco.eoms.partner.taskManager.service.ICarApproveService;

public class CarApproveServiceImpl extends CommonGenericServiceImpl<CarApprove> implements ICarApproveService {

	private ICarApproveDao carApproveDao;

	public ICarApproveDao getCarApproveDao() {
		return carApproveDao;
	}

	public void setCarApproveDao(ICarApproveDao carApproveDao) {
		this.setCommonGenericDao(carApproveDao);
		this.carApproveDao = carApproveDao;
	}
	
	@SuppressWarnings("unchecked")
	public String getDeptId(TawSystemSessionForm sessionForm) {
		String deptId = "";
		try {
//			ITawSystemDeptManager deptMgr = (ITawSystemDeptManager) ApplicationContextHolder
//			.getInstance().getBean("ItawSystemDeptManager");
			ITawSystemUserManager userMgr = (ITawSystemUserManager) ApplicationContextHolder
			.getInstance().getBean("itawSystemUserManager");
			PartnerDeptMgr partnerDeptMgr = (PartnerDeptMgr)
			ApplicationContextHolder.getInstance().getBean("partnerDeptMgr");
			String deptid=sessionForm.getDeptid();
			String userid=sessionForm.getUserid();
			List<PartnerDept>  list0=new ArrayList<PartnerDept>();
			TawSystemUser user = userMgr.getTawSystemUserByuserid(userid);
			if (user != null) {
				if (!"admin".equals(sessionForm.getUserid())) {
					list0=partnerDeptMgr.getPartnerDepts("and deptMagId='"+deptid+"'");
					if (list0.size()>0) {//表示人员是代维管理人员
						deptId = StaticMethod.null2String(list0.get(0).getDeptMagId());
					}else {//移动管理人员
//						TawSystemDept dept1=deptMgr.getDeptinfobydeptid(deptid,"0");
//						deptId = dept1.getDeptId();
//						if(dept1!=null){
//							String areaid=StaticMethod.null2String(dept1.getAreaid());
//						}else {
//							throw new Exception("没有权限，请联系管理员");
//						}
					}
				}else {
				}
			}else {
				throw new Exception("没有权限，请联系管理员");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return deptId;
	}

	public void commitCarApprove(CarApprove carApprove,
			CarApproveLink carApproveLink, CarApproveTask carApproveTask,
			CarTask carTask) {
		carApprove = (CarApprove) carApproveDao.commitCarApprove(carApprove);
		String approveId = carApprove.getId();
		carApproveTask.setCarApproveId(approveId);
		carApproveLink.setCarApproveId(approveId);
		carTask.setCarApproveId(approveId);
		carApproveDao.commitCarApprove(carApproveLink);
		carApproveDao.commitCarApprove(carApproveTask);
		carApproveDao.commitCarApprove(carTask);
		updateCarStatue(carApprove.getCarNum(), "1");
		
	}

	public void updateCarStatue(String carNum, String staue) {
		carApproveDao.updateCarStatue(carNum, staue);
	}

	@SuppressWarnings("unchecked")
	public List getAllCarApproveOrApply(int offset, int pagesize,String whereStr,String joinStr) {
		
		return carApproveDao.getAllCarApproveOrApply(offset, pagesize, whereStr,joinStr);
	}
	
	public void saveAllObject(List<Object> list,String approveStatus,String carNum){
		//add by fengguangping 2013-05-15
//		for(int i=0;i<list.size();i++){
//			carApproveDao.commitCarApprove(list.get(i));
//		}
//		if("1".equals(approveStatus)){//审批通过
//			carApproveDao.updateCarStatue(carNum, 2+"");
//		}else{
//			carApproveDao.updateCarStatue(carNum, 0+"");
//		}
		String applyid="";
		for(int i=0;i<list.size();i++){
			Object obj=carApproveDao.commitCarApprove(list.get(i));
			if (obj instanceof CarApprove) {
				CarApprove carApprove=(CarApprove)obj;
				applyid=carApprove.getId();
			}
		}
		if("1".equals(approveStatus)){//审批通过
			carApproveDao.updateCarDispatchStatusAndApplyId(carNum, 2+"", applyid);
		}else{
			carApproveDao.updateCarStatue(carNum, 0+"");
		}
	}
	
	/**
	 * 车辆归还
	 * @param list
	 * @param carNum
	 */
	public void backApplyCar(List<Object> list,String carNum){
		for(int i=0;i<list.size();i++){
			carApproveDao.commitCarApprove(list.get(i));
		}
		carApproveDao.updateCarStatue(carNum, 0+"");
	}
	
	/**
	 * 终止车辆的使用
	 * @param list
	 * @param carNum
	 */
	public void endApplyCar(List<Object> list,String carNum){
		for(int i=0;i<list.size();i++){
			carApproveDao.commitCarApprove(list.get(i));
		}
		carApproveDao.updateCarStatue(carNum, 0+"");
	}
	
	/**
	 * 由我申请的列表
	 * @param offset
	 * @param pagesize
	 * @param whereStr
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getByMeApply(int offset, int pagesize,String whereStr){
		return carApproveDao.getByMeApply(offset, pagesize, whereStr);
	}
}
