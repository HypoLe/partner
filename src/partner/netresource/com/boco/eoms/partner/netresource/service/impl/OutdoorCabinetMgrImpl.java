package com.boco.eoms.partner.netresource.service.impl;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.struts.upload.FormFile;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.util.UUIDHexGenerator;
import com.boco.eoms.commons.system.area.model.TawSystemArea;
import com.boco.eoms.commons.system.area.service.ITawSystemAreaManager;
import com.boco.eoms.commons.system.dict.model.TawSystemDictType;
import com.boco.eoms.commons.system.dict.service.ITawSystemDictTypeManager;
import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.partner.baseinfo.util.PartnerCityByUser;
import com.boco.eoms.partner.netresource.dao.BsBtApDao;
import com.boco.eoms.partner.netresource.dao.IBsBtApDaoJdbc;
import com.boco.eoms.partner.netresource.dao.IEomsDao;
import com.boco.eoms.partner.netresource.dao.IOutdoorCabinetDaoJdbc;
import com.boco.eoms.partner.netresource.dao.OutdoorCabinetDao;
import com.boco.eoms.partner.netresource.model.bs.BsBtAp;
import com.boco.eoms.partner.netresource.model.bs.OutdoorCabinet;
import com.boco.eoms.partner.netresource.service.OutdoorCabinetMgr;
import com.boco.eoms.partner.netresource.util.DataSaveCallback;
import com.boco.eoms.partner.netresource.util.ExcelImport;
import com.boco.eoms.partner.netresource.util.ImportResult;
import com.boco.eoms.partner.netresource.util.PnrNetResourceUtil;
import com.boco.eoms.partner.res.mgr.PnrResConfigMgr;
import com.boco.eoms.partner.res.model.PnrResConfig;
import com.google.common.collect.Lists;

/** 
 * Description: 
 * Copyright:   Copyright (c)2013
 * Company:     BOCO 
 * @author:     chenbing 
 * @version:    1.0 
 */ 
public class OutdoorCabinetMgrImpl extends CommonGenericServiceImpl<OutdoorCabinet> implements OutdoorCabinetMgr {

	private OutdoorCabinetDao  ocDao;
	private IOutdoorCabinetDaoJdbc ocJdbc;
	
	private PnrResConfigMgr pnrResService;
	private ITawSystemAreaManager  tawArea;
	private ITawSystemDictTypeManager  tawty;
	
	private int flag = 0 ;//标示有错误出现
	private int errorCount=0;
	private String[] errorDatas;//存放错误的数组
    private StringBuilder errMsg;//错误信息
	private int rowNum =1; //行数
	public List<String> nameList = new ArrayList<String>();
	
	public List<OutdoorCabinet> getOCByName(String ocName) {
		// TODO Auto-generated method stub
		StringBuffer hql = new StringBuffer();
		hql.append(" select new OutdoorCabinet(p.id,p.outCabinetName) from OutdoorCabinet p where p.outCabinetName='").append(ocName).append("'");
		
		List<OutdoorCabinet> list =ocDao.findByHql(hql.toString());
		
		return list;
	}
	
	
	/**
	 * 资源导入--室外箱体资源
	 */
	public ImportResult importOCFromFile(FormFile formFile, final String province,final String specialty,final String creatorId,final String osPath)
	throws Exception {
		tawArea =(ITawSystemAreaManager)ApplicationContextHolder
		.getInstance().getBean("ItawSystemAreaManager");
		tawty=(ITawSystemDictTypeManager)ApplicationContextHolder
		.getInstance().getBean("ItawSystemDictTypeManager");
		pnrResService=(PnrResConfigMgr)ApplicationContextHolder.getInstance().getBean("PnrResConfigMgr");
		
		final List<Object> mainList = new ArrayList<Object>();
		final List<Object> errorList = new ArrayList<Object>();
		
		final ExcelImport ei=new ExcelImport(3,1,20);
		nameList = ei.getExcelNameList(formFile, 1,3);
		ImportResult result=ei.importFromFile(formFile,1,new DataSaveCallback(){
			public void doSaveRow2Data(HSSFRow row) throws Exception {
				List<Object> mapList=this.fromRow2Object(row);
				if(mapList.size()==1){
					mainList.add(mapList.get(0));
				}else{
					errorCount++;
				}
			}
			
		
		
			@SuppressWarnings({ "unchecked", "static-access" })
			@Override
			public List<Object> fromRow2Object(HSSFRow row) throws Exception {
				List<Object> mainList = new ArrayList<Object>();
				//资源信息 
				PnrResConfig pnrResConfig=null;
				OutdoorCabinet  outdoorCabinet = null;
				// error message
				 errorDatas = new String[21];
				 errMsg=new StringBuilder();
				
				int colNum=0;
				try {
					outdoorCabinet = new OutdoorCabinet();
					String id = UUIDHexGenerator.getInstance().getID();
					outdoorCabinet.setId(id);
					colNum++;
					
					///**资源类标识*/
					String resIdentifier;
					resIdentifier= StaticMethod.nullObject2String(row.getCell(1));
					if("".equals(resIdentifier)){
						errMsg.append("资源类标识不能为空;");
						flag=1;
						//throw new Exception("资源类标识不能为空");
					}
								
					outdoorCabinet.setResIdentifier(resIdentifier);
					colNum++;
					
					///**数据标识*/
					String dataIdentifier;
					dataIdentifier= StaticMethod.nullObject2String(row.getCell(2));
					if("".equals(dataIdentifier)){
						errMsg.append("数据标识不能为空;");
						flag=1;
						//throw new Exception("数据标识不能为空");
					}
					
					outdoorCabinet.setDataIdentifier(dataIdentifier);
					colNum++;
					
					/**设备放置点名称*/
					
					String outCabinetName = StaticMethod.nullObject2String(row.getCell(3));
					if("".equals(outCabinetName)){
						errMsg.append("设备放置点名称不能为空;");
						flag=1;
						//throw new Exception("设备放置点名称不能为空");
					}else if(ei.isHaveSameName(outCabinetName, nameList)){
						errMsg.append("设备放置点名称在Excel中已重复;");
						flag=3;
					}else{
						List<OutdoorCabinet> glist =getOCByName(outCabinetName);
						if(glist.size()>0){
							errMsg.append("设备放置点名称系统中已存在;");
							flag=2;
						}
					}
					outdoorCabinet.setOutCabinetName(outCabinetName);
					colNum++;
					
					/**机房别名*/					
					String anotherOCName = StaticMethod.nullObject2String(row.getCell(4));
									
					outdoorCabinet.setAnotherOCName(anotherOCName);
					colNum++;

					//所属地市ID ？？？
					
					String citiesID="";
					String citiesName = StaticMethod.nullObject2String(row.getCell(5));
					if("".equals(citiesName)){
						errMsg.append("所属地市不能为空;");
						flag=1;
						//throw new Exception("所属地市不能为空");
					}else{
						
						List<TawSystemArea> alist = tawArea.getTawareas(citiesName);
						if(alist.size()>0){
							citiesID=alist.get(0).getAreaid();
						}else{
							errMsg.append("所属地市-系统中不存在,请联系管理员;");
							flag=2;
							//throw new Exception("所选地市-系统中不存在");
						}
					}
					if(flag==0&&errMsg.length()==0){
					outdoorCabinet.setCity(citiesID);
					}else{
						flag=0;
					}
					colNum++;
					//所属区域ID ？？？
					String countryID="";
					String country = StaticMethod.nullObject2String(row.getCell(6));
					if("".equals(country)){
						errMsg.append("所属区域不能为空;");
						flag=1;
						//throw new Exception("所属区域不能为空");
					}else{
						
						List<TawSystemArea> alist2 = tawArea.getTawareas(country);
						if(alist2.size()>0){
							for(int i=0;i<alist2.size();i++){
								if(alist2.get(i).getParentAreaid().equals(citiesID)){
									countryID=alist2.get(i).getAreaid();
									break;
								}
							}
							
							if("".equals(countryID)){
									errMsg.append("所属区域-系统中不存在,请联系管理员;");
							}
						}else{
							errMsg.append("所属区域-系统中不存在,请联系管理员;");
							flag=2;
							//throw new Exception("所选区域-系统中不存在");
						}
					}
					if(flag==0&&errMsg.length()==0){

					outdoorCabinet.setCountry(countryID);
					}else{
						flag=0;
					}
				 	colNum++;
				 	//巡检专业
				 	String specialtyId="";
				 	String specialty = StaticMethod.nullObject2String(row.getCell(7));
				 	if("".equals(specialty)){
				 		errMsg.append("巡检专业不能为空;");
				 	}else{
				 		specialty = specialty.trim();
				 		List<TawSystemDictType> speclist = tawty.getTawSystemDictTypeByName(specialty, "11225", 5);
				 		if(speclist.size()>0){
				 			specialtyId=speclist.get(0).getDictId();
				 			outdoorCabinet.setSpecialty(specialtyId);
				 		}else{
				 			errMsg.append("巡检专业-系统中不存在,请联系管理员;");
				 		}
				 	}
				 	colNum++;
				 	//资源类别
				 	String resTypeId="";
				 	String resType = StaticMethod.nullObject2String(row.getCell(8));
				 	if(specialtyId.length()>0){
				 		
				 		if("".equals(resType)){
				 			errMsg.append("资源类别不能为空;");
				 		}else{
				 			resType = resType.trim();
				 			List<TawSystemDictType> resTypelist = tawty.getTawSystemDictTypeByName(resType,specialtyId, 7);
				 			if(resTypelist.size()>0){
				 				resTypeId=resTypelist.get(0).getDictId();
				 				outdoorCabinet.setResType(resTypeId);
				 			}else{
				 				errMsg.append("资源类别-系统中不存在,请联系管理员;");
				 			}
				 		}
				 	}
				 	colNum++;
				 	//资源级别
				 	String resLevelId="";
				 	String resLevel = StaticMethod.nullObject2String(row.getCell(9));
				 	if(resTypeId.length()>0){
				 		
				 		if(PnrNetResourceUtil.checkIsNull(resLevel)){
				 			errMsg.append("资源级别不能为空;");
				 		}else{
				 			resLevel=resLevel.trim();
				 			List<TawSystemDictType> resLevellist = tawty.getTawSystemDictTypeByName(resLevel,resTypeId, 9);
				 			if(resLevellist.size()>0){
				 				resLevelId=resLevellist.get(0).getDictId();
				 			}else{
				 				errMsg.append("资源级别-系统中不存在,请联系管理员;");
				 			}
				 		}
				 		
				 		outdoorCabinet.setResLevel(resLevelId);
				 		
				 	}
				 	colNum++;
					/**维护区域*/			
					String maintenanceArea = StaticMethod.nullObject2String(row.getCell(10));
					
					outdoorCabinet.setMaintenanceArea(maintenanceArea);
					colNum++;
				    
					/**放置点地址*/
					String ocAddress = StaticMethod.nullObject2String(row.getCell(11));
					outdoorCabinet.setOcAddress(ocAddress);
					colNum++;
					/**放置点标准地址*/
					String place = StaticMethod.nullObject2String(row.getCell(12));
					outdoorCabinet.setPlace(place);
					colNum++;
					
					//经度
					Double  longitude=0.0;
					String getlongitude= StaticMethod.nullObject2String(row.getCell(13));
					if("".equals(getlongitude)){
						errMsg.append("经度不能空;");
					}else{
						
						getlongitude=getlongitude.trim();
						if(getlongitude.matches(PnrNetResourceUtil.REG)){
							
							longitude= Double.parseDouble(getlongitude);
							outdoorCabinet.setLongitude(longitude);
						}else{
							
							errMsg.append("经度-不正确;");
						}
						
					}					
					colNum++;
					//纬度
					Double latitude=0.0;
					String getlatitude= StaticMethod.nullObject2String(row.getCell(14));
					if("".equals(getlatitude)){
						errMsg.append("纬度不能空;");
					}else{
						
						getlatitude=getlatitude.trim();
						if(getlatitude.matches(PnrNetResourceUtil.REG)){
							latitude = Double.parseDouble(getlatitude);
							outdoorCabinet.setLatitude(latitude);
						}else{
							errMsg.append("纬度-不正确;");							
						}
					}
					colNum++;
					
					/**是否租用空间*/
					String  isRentSpace = StaticMethod.nullObject2String(row.getCell(15));
					outdoorCabinet.setIsRentSpace(isRentSpace);
					colNum++;
					
					/**租用空间面积*/
					String  rentalSpaceArea = StaticMethod.nullObject2String(row.getCell(16));
					outdoorCabinet.setRentalSpaceArea(rentalSpaceArea);
					colNum++;
					
					/**出租方*/
					String  lessor = StaticMethod.nullObject2String(row.getCell(17));
					outdoorCabinet.setLessor(lessor);
					colNum++;
					
					
					/**租用期限*/
					String  leaseDuration = StaticMethod.nullObject2String(row.getCell(18));
					outdoorCabinet.setLeaseDuration(leaseDuration);
					colNum++;
					
					
					//备注
					String  remark = StaticMethod.nullObject2String(row.getCell(19));
					outdoorCabinet.setRemark(remark);
					
					
//					保存错误信息的集合
					errorDatas[0]=Integer.toString(rowNum);//序号
					errorDatas[1]=resIdentifier;
					errorDatas[2]=dataIdentifier;
					errorDatas[3]=outCabinetName;
					errorDatas[4]=anotherOCName;
					errorDatas[5]=citiesName;
					errorDatas[6]=country;
					errorDatas[7]=specialty;
					errorDatas[8]=resType;
					errorDatas[9]=resLevel;
					errorDatas[10]=maintenanceArea;
					errorDatas[11]=ocAddress;
					errorDatas[12]=place;
					errorDatas[13]=getlongitude;
					errorDatas[14]=getlatitude;
					errorDatas[15]=isRentSpace;	
					errorDatas[16]=rentalSpaceArea;
					errorDatas[17]=lessor;
					errorDatas[18]=leaseDuration;
					errorDatas[19]=remark;
					//区分错误与正确的列表

					if(errMsg.length()==0){						
						mainList.add(outdoorCabinet);
						errorDatas=null;
					}else{
						errorDatas[20]=errMsg.toString();
						errorList.add(errorDatas);
						flag=0;
						outdoorCabinet=null;
					}
					
					
				} catch (Exception e) {
					e.printStackTrace();
					colNum=colNum+1;
					throw new Exception(",错误列数是:"+colNum+e.getMessage());
				}
				rowNum++;
				return mainList;
			}
		});
		rowNum=1;//初始化 
		//在这里保存数据
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement stmt1 = null; 
		try {
			IEomsDao ed = (IEomsDao)ApplicationContextHolder.getInstance().getBean("eomsDao");
			//先判断导入的数据是否为空，如果为空值不插入
			if(mainList.size()>0){
				conn=ed.getCon();
				conn.setAutoCommit(false);
				//将这集合里的数据拆分成多个集合,以便一次插入的数据别太多
				List<List<Object>> mainlist1 = Lists.partition(mainList, 100);
				StringBuffer insertMainSql = new StringBuffer();
				 insertMainSql.append(" insert into pnr_outdoor_cabinet(id, res_identifier,data_identifier ,out_cabinet_name,another_oc_name,");//5
				 insertMainSql.append(" city,country,maintenance_area ,oc_address,");//4
				 insertMainSql.append(" place , longitude , latitude ,");//3
				 insertMainSql.append(" is_rent_space,rental_space_area,lessor,");//3
				 insertMainSql.append(" lease_duration,remark,specialty,res_type,res_level)");//5
				 insertMainSql.append(" values(?,?,?,?,?,?,?,?,?,?,");//10
				 insertMainSql.append("?,?,?,?,?,?,?,?,?,?)");//10
			
				stmt1=conn.prepareStatement(insertMainSql.toString());
				for(int i = 0;i<mainlist1.size();i++){
					ocJdbc.importData(mainlist1.get(i),stmt1,1);
							stmt1.executeBatch();
							stmt1.clearBatch();
					
				}
				
				for(int k=0;k<mainList.size();k++){
					
					PnrResConfig pnrResConfig=null;
					OutdoorCabinet sr = (OutdoorCabinet) mainList.get(k);
					//资源信息
					pnrResConfig = new PnrResConfig();
					pnrResConfig.setId(UUIDHexGenerator.getInstance().getID());
					pnrResConfig.setResName(sr.getOutCabinetName());
					//专业 室外箱体
					pnrResConfig.setSpecialty(sr.getSpecialty());
					//类别
					pnrResConfig.setResType(sr.getResType());
					//级别
					pnrResConfig.setResLevel(sr.getResLevel());
					//周期
					
					String week ;
					week=PartnerCityByUser.getWeekTimeByPnrId(sr.getResLevel());
					String[]a = week.split(",");
					week="";
					if(a.length>2){
						week=a[2];
					}
					pnrResConfig.setInspectCycle(week);//巡检周期
					
					pnrResConfig.setResLongitude(Double.toString(sr.getLongitude()));
					pnrResConfig.setResLatitude(Double.toString(sr.getLatitude()));
					pnrResConfig.setSubResTable("pnr_outdoor_cabinet");
					pnrResConfig.setSubResId(sr.getId());
					pnrResConfig.setCity(sr.getCity());
					pnrResConfig.setCountry(sr.getCountry());
					//创建人
					pnrResConfig.setCreator(creatorId);
					//创建时间
					SimpleDateFormat dateformat =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date date = new Date();
					pnrResConfig.setCreateTime(dateformat.format(date));
					/*//业主单位
					pnrResConfig.setCompanyName(sr.getMaintenanceUnit());
					//联系人
					pnrResConfig.setContactName(sr.getContact());
					//联系人电话
					pnrResConfig.setPhone(sr.getContactTel());*/
					//服务区域
					pnrResConfig.setServiceRegion(sr.getMaintenanceArea());
					//线路巡检标识
					pnrResConfig.setTlInspectFlag("0");
					pnrResService.save(pnrResConfig);
		    	}
//				先保存巡检资源，再保存资源
				conn.commit();
				conn.setAutoCommit(true);
			}
			//处理错误信息
			int errorSize = errorList.size();
			if(errorSize>0){
				//开始将错误的信息导入到excel中 
				String outfilepath ="shiwaixiangti"+creatorId+".xls";
				outfilepath =ExcelImport.outfilePath(osPath,outfilepath);

				int sheet =1 ,rowNum=4;
				String[] names={"字段名称","资源类标识*","数据标识*","设备放置点名称*","别名","所属地市*","所属区域*","巡检专业*","资源类别*","资源级别*","所属维护区域*","设备放置点地址","设备放置点标准地址","经度","纬度","是否租用空间","租用空间面积","出租方","租用期限","备注","错误信息"};
				String sheetName="室外箱体";
				ei.errorObjtoExcel( outfilepath, errorList , sheet,names, sheetName, rowNum);
				
			}
			result.setErrorCount(errorCount);//错误的个数
			errorCount=0;///重置
			return result;
		}catch (Exception e) {
			e.printStackTrace();
			if (conn != null) {
				try {
					conn.rollback();
				} catch (Exception cre) {
					cre.printStackTrace();
				}
			}
			result.setResultCode("503");
			String msg = result.getRestultMsg();
			if (!msg.equals("")) {
				msg += ",";
			}
			result.setRestultMsg(e.getMessage());
			throw new RuntimeException(result.getRestultMsg());
		} finally {
			if (stmt1!= null) {
				try {
					stmt1.close();
				} catch (Exception pe1) {
					pe1.printStackTrace();
				}
			}
			
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception ce) {
					ce.printStackTrace();
				}
			}
		}
	
		
		
	}


	public OutdoorCabinetDao getOcDao() {
		return ocDao;
	}

	public void setOcDao(OutdoorCabinetDao ocDao) {
		this.ocDao = ocDao;
		this.setCommonGenericDao(ocDao);
	}


	public IOutdoorCabinetDaoJdbc getOcJdbc() {
		return ocJdbc;
	}


	public void setOcJdbc(IOutdoorCabinetDaoJdbc ocJdbc) {
		this.ocJdbc = ocJdbc;
	}
		
}
