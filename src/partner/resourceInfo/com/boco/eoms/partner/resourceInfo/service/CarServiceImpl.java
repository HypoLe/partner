package com.boco.eoms.partner.resourceInfo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.struts.upload.FormFile;

import utils.PartnerPrivUtils;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.commons.system.dept.model.TawSystemDept;
import com.boco.eoms.commons.system.dept.service.ITawSystemDeptManager;
import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.deviceManagement.common.utils.CommonUtils;
import com.boco.eoms.partner.baseinfo.mgr.PartnerDeptMgr;
import com.boco.eoms.partner.baseinfo.model.PartnerDept;
import com.boco.eoms.partner.process.service.PnrProcessService;
import com.boco.eoms.partner.process.util.PnrProcessCach;
import com.boco.eoms.partner.process.util.XLSModel;
import com.boco.eoms.partner.resourceInfo.dao.ICarDao;
import com.boco.eoms.partner.resourceInfo.model.Apparatus;
import com.boco.eoms.partner.resourceInfo.model.Car;
import com.boco.eoms.partner.resourceInfo.util.ImportResult;
import com.boco.eoms.partner.resourceInfo.util.XLSCellValidateUtil;
import com.boco.eoms.partner.resourceInfo.util.XLSFileImport;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

public class CarServiceImpl extends CommonGenericServiceImpl<Car> implements CarService,PnrProcessService {
	private ICarDao carDao;
	public ICarDao getCarDao() {
		return carDao;
	}
	public void setCarDao(ICarDao carDao) {
		this.setCommonGenericDao(carDao);
		this.carDao=carDao;
	}
	public Search searchPrivFilter(Search search, String userId,String deptId,HttpServletRequest request) {
		search.addFilterNotEqual("deleted", "1");
		//获取区域id作为删选条件
		List<PartnerDept>  list0=new ArrayList<PartnerDept>();
		if (!"admin".equals(userId)) {
			PartnerDeptMgr partnerDeptMgr=(PartnerDeptMgr)com.boco.eoms.base.util.ApplicationContextHolder.getInstance().getBean("partnerDeptMgr");
			list0=partnerDeptMgr.getPartnerDepts("and deptMagId='"+deptId+"'");
			if (list0.size()!=0&&list0!=null) {//不等于0表示是代维公司的
				search.addFilterILike("maintainCompany", deptId+"%");//代维公司权限限定
			}else {
				ITawSystemDeptManager deptManager=(ITawSystemDeptManager)com.boco.eoms.base.util.ApplicationContextHolder.getInstance().getBean("ItawSystemDeptManager");
				TawSystemDept dept=deptManager.getDeptinfobydeptid(deptId,"0");
				if (dept!=null) {
					search.addFilterILike("area", dept.getAreaid()+"%");//区域权限限定
				}
			}
		}
		search.addFilterNotEqual("visible", "1");//1表示不可见，正在执行流程申请中
		search.addFilterNotEqual("deleted", "1");//1表示删除
		// TODO Auto-generated method stub
		return search;
	}

	/**
	 * 管理员批量导入
	 */
	public ImportResult importFromFile(FormFile formFile) throws Exception {
		XLSFileImport xlsFileImport=new XLSFileImport(){
			public XLSModel getXLSModel() {
				XLSModel xModel=new XLSModel(2,1,15,0,0,0,0,0,0);
				return xModel;
			}
			public boolean doSaveRow2Data(HSSFRow row) throws Exception {
				Car car=new Car();
				car.setArea(XLSCellValidateUtil.checkAndGetAreaId(row, 1));//获取区域
				car.setMaintainCompany(XLSCellValidateUtil.checkAndGetDeptId(row, 2));
				car.setCarNumber(XLSCellValidateUtil.checkCarNumberIsOnly(row, 3,"add",""));
				car.setCarType(XLSCellValidateUtil.checkIsNull(row, 4));
				car.setCarModel(XLSCellValidateUtil.checkIsNull(row, 5));
				car.setCarBrand(XLSCellValidateUtil.checkIsNull(row, 6));
				car.setFuleType(XLSCellValidateUtil.checkDictName(row,7, "1230203",false));
				car.setFuleConStandard(XLSCellValidateUtil.checkNumber(row,8));
				car.setCarGPSNumber(XLSCellValidateUtil.checkGPSNumberIsOnly(row, 9,"add",""));
				car.setCarGPSFactory(XLSCellValidateUtil.checkIsNull(row, 10));
				car.setCarGPSSimCardNumber(XLSCellValidateUtil.checkIsNull(row, 11));
				car.setDriver(XLSCellValidateUtil.checkAndGetUserId(row, 12, 2));
				car.setDriverContact(XLSCellValidateUtil.checkIsNull(row, 13));
				car.setCarProperty(XLSCellValidateUtil.checkDictName(row, 14,"1230201",false));
				car.setCarStatus(XLSCellValidateUtil.checkDictName(row, 15, "1230202",false));
				car.setAddTime(CommonUtils.toEomsStandardDate(new Date()));
				car.setDeleted("0");
				car.setVisible("0");
				car.setDispatchStatus("0");
				car.setApplyId("");
				car.setLocateTime(new Date());
				save(car);
				String id=car.getId()+"";
				PnrProcessCach.carnumAndGPSnumListCach.put(car.getCarGPSNumber(), id+","+car.getCarGPSNumber());//保存成功后把信息加入缓存中，供下条记录验证使用
				PnrProcessCach.carnumAndGPSnumListCach.put(car.getCarNumber(), id+","+car.getCarNumber());//保存成功后把信息加入缓存中，供下条记录验证使用
				return true;
			}
		};
		ImportResult result=xlsFileImport.xlsFileValidate(formFile);
		return result;
	}
	
	public Car getCarObject (HSSFRow row,Car car) throws Exception{
		car.setArea(XLSCellValidateUtil.checkAndGetAreaId(row, 1));//获取区域
		car.setMaintainCompany(XLSCellValidateUtil.checkAndGetDeptId(row, 2));
		car.setCarNumber(XLSCellValidateUtil.getCellStringValue(row, 3));
		car.setCarType(XLSCellValidateUtil.getCellStringValue(row, 4));
		car.setCarModel(XLSCellValidateUtil.getCellStringValue(row, 5));
		car.setCarBrand(XLSCellValidateUtil.getCellStringValue(row, 6));
		car.setFuleType(XLSCellValidateUtil.dictNameToDictId(row,7, "1230203",false));
		car.setFuleConStandard(XLSCellValidateUtil.getCellStringValue(row, 8));
		car.setCarGPSNumber(XLSCellValidateUtil.getCellStringValue(row, 9));
		car.setCarGPSFactory(XLSCellValidateUtil.getCellStringValue(row, 10));
		car.setCarGPSSimCardNumber(XLSCellValidateUtil.getCellStringValue(row,  11));
		car.setDriver(XLSCellValidateUtil.checkAndGetUserId(row, 12, 2));
		car.setDriverContact(XLSCellValidateUtil.getCellStringValue(row, 13));
		car.setCarProperty(XLSCellValidateUtil.dictNameToDictId(row, 14,"1230201",false));
		car.setCarStatus(XLSCellValidateUtil.dictNameToDictId(row, 15, "1230202",false));
		car.setAddTime(CommonUtils.toEomsStandardDate(new Date()));
		car.setDeleted("0");
		car.setVisible("0");
		return car;
	}
	/**
	 * 删除归档
	 */
	public boolean doDeleteXLSFileData(HSSFRow row, HttpServletRequest request) throws Exception {
		XLSModel xModel=getXLSModel();
		if (row.getRowNum()==xModel.getDeleteStartRowNum()) {
			//return false;//数据验证是从第三行开始，前二行不做归档
			PnrProcessCach.reloadCarnumAndGPSnumList();
		}
		XLSCellValidateUtil xlsUtil=new XLSCellValidateUtil();
		String id=xlsUtil.getCellStringValue(row, 0);
		List list=carDao.findByHql("from Car where id="+id);
		Car car=(Car)list.get(0);
		car.setDeleted("1");
		car.setVisible("0");
		save(car);
		String carNumber=StaticMethod.null2String(car.getCarNumber());
		String gpsNumber=StaticMethod.null2String(car.getCarGPSNumber());
		PnrProcessCach.carnumAndGPSnumListCach.remove(carNumber);
		PnrProcessCach.carnumAndGPSnumListCach.remove(gpsNumber);
		return true;
	}
	/**
	 * 新增数据归档
	 */
	public boolean doSaveXLSFileData(HSSFRow row, HttpServletRequest request) throws Exception {
		XLSModel xModel=getXLSModel();
		if (row.getRowNum()==xModel.getAddStartRowNum()) {
			//return false;//数据验证是从第四行开始，前三行不做归档
			PnrProcessCach.reloadCarnumAndGPSnumList();
		}
		Car car=new Car();
		car=getCarObject(row, car);
		car.setDispatchStatus("0");
		car.setApplyId("");
		car.setLocateTime(new Date());
		save(car);
		String carNumber=StaticMethod.null2String(car.getCarNumber());
		String gpsNumber=StaticMethod.null2String(car.getCarGPSNumber());
		int id=car.getId();
		PnrProcessCach.carnumAndGPSnumListCach.put(carNumber, id+","+carNumber);
		PnrProcessCach.carnumAndGPSnumListCach.put(gpsNumber, id+","+gpsNumber);
		return true;
	}
	/**
	 * 更新数据归档
	 */
	public boolean doUpdateXLSFileData(HSSFRow row, HttpServletRequest request)	throws Exception {
		Car car=new Car();
		XLSModel xModel=getXLSModel();
		if (row.getRowNum()==xModel.getUpdateStartRowNum()) {
			//重新加载车辆缓存数据;
			PnrProcessCach.reloadCarnumAndGPSnumList();
		}
		String id=XLSCellValidateUtil.getCellStringValue(row, 0);
		List list=carDao.findByHql("from Car where id="+id);
		car=(Car)list.get(0);
		PnrProcessCach.carnumAndGPSnumListCach.remove(car.getCarNumber());
		PnrProcessCach.carnumAndGPSnumListCach.remove(car.getCarGPSNumber());
		car=getCarObject(row, car);
		save(car);
		String carNumber=StaticMethod.null2String(car.getCarNumber());
		String gpsNumber=StaticMethod.null2String(car.getCarGPSNumber());
		PnrProcessCach.carnumAndGPSnumListCach.put(carNumber, id+","+carNumber);
		PnrProcessCach.carnumAndGPSnumListCach.put(gpsNumber, id+","+gpsNumber);
		return true;
	}
	/**
	 * 构造XLSModel
	 */
	public XLSModel getXLSModel() {
		XLSModel xModel=new XLSModel(2,1,15,2,0,1,2,0,15);
		return xModel;
	}
	/**
	 * 删除数据时校验
	 */
	public  boolean doDeleteXLSFileValidate(HSSFRow row) throws Exception {
		XLSCellValidateUtil xlsUtil=new XLSCellValidateUtil();
		XLSModel xModel=getXLSModel();
		if (row.getRowNum()==xModel.getDeleteStartRowNum()) {
			//重新加载车辆缓存数据;
			PnrProcessCach.reloadCarnumAndGPSnumList();
		}
		String id=xlsUtil.getCellStringValue(row, 0);
		List list=carDao.findByHql("from Car where id='"+id+"'   and deleted <>  1  and visible <> 1");
		if (list==null||list.size()<1) {
			//抛出异常该记录不存在
			throw new RuntimeException("序号为 "+id+" 的记录不存在请核实再传！");
		}
		Car car=(Car)list.get(0);
		car.setVisible("1");
		car.setDeleted("0");
		save(car);
		return true;
	}
	/**
	 * 更新数据时验证
	 */
	public boolean doUpdateXLSFileValidate(HSSFRow row) throws Exception{
		XLSCellValidateUtil xlsUtil=new XLSCellValidateUtil();
		XLSModel xModel=getXLSModel();
		if (row.getRowNum()==xModel.getUpdateStartRowNum()) {
			//重新加载车辆缓存数据;
			PnrProcessCach.reloadCarnumAndGPSnumList();
		}
		String id=xlsUtil.getCellStringValue(row, 0);
		List list=carDao.findByHql("from Car where id='"+id+"'   and deleted <> 1 and  visible <> 1 ");
		if (list==null||list.size()<1) {
			throw new RuntimeException("序号为 "+id+" 的记录不存在请核实再传！");
		}
		validateEachRow(row,"update",id);
		Car car=(Car)list.get(0);
		car.setVisible("1");
		car.setDeleted("0");
		save(car);
		return true;
	}
	/**
	 * 新增数据校验
	 */
	public boolean doSaveXLSFileValidate(HSSFRow row) throws Exception {
		XLSModel xModel=getXLSModel();
		if (row.getRowNum()==xModel.getAddStartRowNum()) {
			//重新加载车辆缓存数据;
			PnrProcessCach.reloadCarnumAndGPSnumList();
		}
		validateEachRow(row,"add","");
		return true;
	}
	/**
	 * 校验当前行的每一个单元格
	 * @param row
	 * @throws Exception
	 */
	private void validateEachRow( HSSFRow row,String type,String id) throws Exception{
		XLSCellValidateUtil.checkAndGetAreaId(row, 1);//获取区域
		XLSCellValidateUtil.checkAndGetDeptId(row, 2);
		XLSCellValidateUtil.checkCarNumberIsOnly(row, 3,type,id);
		XLSCellValidateUtil.checkIsNull(row, 4);
		XLSCellValidateUtil.checkIsNull(row, 5);
		XLSCellValidateUtil.checkIsNull(row, 6);
		XLSCellValidateUtil.checkDictName(row,7, "1230203",false);
		XLSCellValidateUtil.checkNumber(row,8);
		XLSCellValidateUtil.checkGPSNumberIsOnly(row, 9,type,id);
		XLSCellValidateUtil.checkIsNull(row, 10);
		XLSCellValidateUtil.checkIsNull(row, 11);
		XLSCellValidateUtil.checkAndGetUserId(row, 12, 2);
		XLSCellValidateUtil.checkIsNull(row, 13);
		XLSCellValidateUtil.checkDictName(row, 14,"1230201",false);
		XLSCellValidateUtil.checkDictName(row, 15, "1230202",false);
	}
	/**
	 * 通过车牌号和主键id查找车辆，验证车牌号的唯一性
	 */
	public List findByCarNumber(String carNumber,String id) throws Exception {
		List<Car> list=new ArrayList<Car>();
		if ("".equals(id)) {
			list=carDao.findByHql("from Car where carNumber='"+carNumber+"' and deleted <> '1' ");
		}else {
			int id2=Integer.parseInt(id);
			list=carDao.findByHql("from Car where carNumber='"+carNumber+"'  and id <> '"+id+"' and deleted <> '1' ");
		}
		return list;
	}
	/**
	 * 通过GPS编号和主键id查找车辆，验证GPS编号的唯一性
	 */
	public List findByCarGPSNumber(String carGPSNumber,String id) throws Exception {
		List<Car> list=new ArrayList<Car>();;
		if ("".equals(id)) {
			list=carDao.findByHql("from Car where carGPSNumber='"+carGPSNumber+"' and deleted <> '1' ");
		}else {
			int id2=Integer.parseInt(id);
			list=carDao.findByHql("from Car where carGPSNumber='"+carGPSNumber+"' and id <> '"+id+"'  and deleted <> '1' ");
		}
		return list;
	}
	
	public boolean doRestoreDeleteXLSFileData(HSSFRow row,HttpServletRequest request) {
		XLSModel xModel=getXLSModel();
		if (row.getRowNum()<xModel.getDeleteStartRowNum()) {
			return false;//数据验证是从第三行开始，前二行不做归档
		}
		XLSCellValidateUtil xlsUtil=new XLSCellValidateUtil();
		String id=xlsUtil.getCellStringValue(row, 0);
		List list=carDao.findByHql("from Car where id="+id);
		Car car=(Car)list.get(0);
		car.setDeleted("0");
		car.setVisible("0");
		save(car);
		return true;
	}
	public boolean doRestoreSaveXLSFileData(HSSFRow row,HttpServletRequest request) {
		return true;
	}
	public boolean doRestoreUpdateXLSFileData(HSSFRow row,HttpServletRequest request) {
		XLSModel xModel=getXLSModel();
		if (row.getRowNum()<xModel.getUpdateStartRowNum()) {
			return false;//数据验证是从第四行开始，前二行不做归档
		}
		XLSCellValidateUtil xlsUtil=new XLSCellValidateUtil();
		String id=xlsUtil.getCellStringValue(row, 0);
		List list=carDao.findByHql("from Car where id="+id);
		Car car=(Car)list.get(0);
		car.setDeleted("0");
		car.setVisible("0");
		save(car);
		return true;
	}
	@Override
	public Car find(String id2) {
		int id=Integer.parseInt(id2);
		Search search=new Search();
		Car car=null;
		search.addFilterEqual("id", id);
		SearchResult<Car> result=searchAndCount(search);
		List<Car> list=new ArrayList<Car>();
		list=result.getResult();
		if (list.size()<1) {
			car=new Car();
		}else {
			car=list.get(0);
		}
		return car;
	}
	public boolean doLoadStaticSource() {
		// XXX Auto-generated method stub
		return false;
	}
	/**
	 * 
	 *@Description 从登录的申请人员所在省公司开始搜索获取可以发起可申请调度(车辆的删除标志位‘0’,状态为‘123020201’-在用,调度状态为‘0’-空闲)的车辆;
	 *该方法还可以扩展作为应急调度时，移动人员可以查看他管辖区域的可调度车辆;
	 *@date May 3, 2013 5:14:02 PM
	 *@author Fengguangping fengguangping@boco.com.cn
	 *@return
	 *@throws Exception
	 */
	public List<Car> getDispatchCar(HttpServletRequest request) throws Exception {
		Map map=PartnerPrivUtils.userIsPersonnel(request);
		String identityFlag=map.get("isPersonnel").toString();
		List list=new ArrayList<Car>();
		if ("y".equals(identityFlag)) {
			String deptid=map.get("deptMagId").toString();
			list=carDao.findByHql("from Car where maintainCompany like '"+deptid.substring(0, PartnerPrivUtils.getProvinceDeptLength())+"%' and deleted='0' and carStatus='123020201' and dispatchStatus='0' ");
			return list;
		}else if ("n".equals(identityFlag)) {
			list=carDao.findByHql("from Car where area like '"+map.get("areaId").toString()+"%' and deleted='0' and carStatus='123020201' and dispatchStatus='0' ");
			return list;
		}else if ("admin".equals(identityFlag)) {
			list=carDao.findByHql("from Car where  deleted='0' and carStatus='123020201' and dispatchStatus='0' ");
			return list;
		}		
		return list;
	}
	/**
	 * 
	 *@Description 根据指定部门的deptid获取可申请调度(车辆的删除标志位‘0’,状态为‘123020201’-在用,调度状态为‘0’-空闲)的车辆;
	 *@date May 3, 2013 5:20:01 PM
	 *@author Fengguangping fengguangping@boco.com.cn
	 *@param deptid:部门的deptid
	 *@return
	 *@throws Exception
	 */
	public List<Car> getDispatchCar(String deptid) throws Exception {
		List  list=new ArrayList<Car>();
		list=carDao.findByHql("from Car where maintainCompany like '"+deptid+"%' and deleted='0' and carStatus='123020201' and dispatchStatus='0' ");
		return list;
	}
	/**
	 * 
	 *@Description:通过车牌号获取未删除的车辆实体
	 *@date May 6, 2013 10:08:14 AM
	 *@author Fengguangping fengguangping@boco.com.cn
	 *@param carNumber:车牌号
	 *@return
	 *@throws Exception
	 */
	public Car getCarByCarNumber(String carNumber)throws Exception{
		Car car=new Car();
		List list=carDao.findByHql("from Car where carNumber='"+carNumber+"' and deleted='0' ");
		if (list!=null&&list.size()==1) {
			car=(Car)list.get(0);
		}
		return car;
	}
	/**
	 * 
	 *@Description:通过车牌号获取车辆任务,该方法未完成
	 *@date May 15, 2013 9:16:29 AM
	 *@author Fengguangping fengguangping@boco.com.cn
	 *@param carNumber
	 *@param latitude
	 *@param longtitude
	 *@throws Exception
	 */
	public List getCarTaskListByCarNumber(String carNumber) throws Exception {
		String sql="select * from pnr_car_approve t1,pnr_car_task t2  where t1.id=t2.car_approve_id  and t1.car_num='"+carNumber+"'";
		return null;
	}
	/**
	 * 
	 *@Description:通过车牌号获取车辆的任务列表;
	 *@date May 15, 2013 9:17:58 AM
	 *@author Fengguangping fengguangping@boco.com.cn
	 *@param carNumber
	 *@return
	 *@throws Exception
	 */
	public void updateCarLocationByCarNumber(String carNumber, String latitude,String longtitude) throws Exception {
		carDao.updateCarLocationByCarNumber(latitude, longtitude, carNumber);
	}
	
	
	public Map getCars(final Integer curPage, final Integer pageSize, final String whereStr) throws Exception {
		return  carDao.getCars(curPage, pageSize, whereStr);
	}
}
