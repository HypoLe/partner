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
import com.boco.eoms.partner.netresource.dao.IBsBtConfigDaoJdbc;
import com.boco.eoms.partner.netresource.dao.IEomsDao;
import com.boco.eoms.partner.netresource.dao.IRoomResourceDaoJdbc;
import com.boco.eoms.partner.netresource.dao.RoomResourceDao;
import com.boco.eoms.partner.netresource.model.bs.BsBtQuipment;
import com.boco.eoms.partner.netresource.model.bs.BsBtResource;
import com.boco.eoms.partner.netresource.model.bs.RoomResource;
import com.boco.eoms.partner.netresource.service.BsBtQuipmentMgr;
import com.boco.eoms.partner.netresource.service.RoomResourceMgr;
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
public class RoomResourceMgrImpl extends CommonGenericServiceImpl<RoomResource> implements RoomResourceMgr {

	private RoomResourceDao rerDao;
	private IRoomResourceDaoJdbc rerJdbc;
	
	private PnrResConfigMgr pnrResService;
	private ITawSystemAreaManager  tawArea;
	private ITawSystemDictTypeManager  tawty;
	
	private int flag = 0 ;//标示有错误出现
	private int errorCount=0;
    private String[] errorDatas;//存放错误的数组
	private StringBuilder errMsg;//错误信息
    private int rowNum =1; //行数
	public List<String> nameList  = new ArrayList<String>();
	
	public List<RoomResource> getRerByName(String reName) {
		// TODO Auto-generated method stub
		StringBuffer hql = new StringBuffer();
		hql.append(" from RoomResource p where p.reName='").append(reName).append("'");
		
		List<RoomResource> list =rerDao.findByHql(hql.toString());
		
		return list;
	}
	
	
	/**
	 * 资源导入--室分资源
	 */
	public ImportResult importRerFromFile(FormFile formFile, final String province,final String specialty,final String creatorId,final String osPath)
	throws Exception {

		tawArea =(ITawSystemAreaManager)ApplicationContextHolder
		.getInstance().getBean("ItawSystemAreaManager");
		tawty=(ITawSystemDictTypeManager)ApplicationContextHolder
		.getInstance().getBean("ItawSystemDictTypeManager");
		pnrResService=(PnrResConfigMgr)ApplicationContextHolder.getInstance().getBean("PnrResConfigMgr");
		
		final List<Object> mainList = new ArrayList<Object>();
		final List<Object> errorList = new ArrayList<Object>();
		
		final ExcelImport ei=new ExcelImport(2,1,24);	
		nameList = ei.getExcelNameList(formFile, 1, 2);
		ImportResult result=ei.importFromFile(formFile,1,new DataSaveCallback(){
			public void doSaveRow2Data(HSSFRow row) throws Exception {
				List<Object> mapList=this.fromRow2Object(row);
				if(mapList.size()==1){
					mainList.add(mapList.get(0));
				}else{
					errorCount++;
				}
			}
			
			// SimpleDateFormat dateformat =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
			@SuppressWarnings({ "unchecked", "static-access" })
			@Override
			public List<Object> fromRow2Object(HSSFRow row) throws Exception {
				List<Object> mainList = new ArrayList<Object>();
				//资源信息 
				PnrResConfig pnrResConfig=null;
				RoomResource  roomResource = null;
//				error message
				errorDatas = new String[25];
				 errMsg=new StringBuilder();
				
				int colNum=0;
				try {
					roomResource = new RoomResource();
					String id = UUIDHexGenerator.getInstance().getID();
					roomResource.setId(id);
					colNum++;
					/**室分编号*/
				 	String reNumber = StaticMethod.nullObject2String(row.getCell(1));
				 	roomResource.setReNumber(reNumber);
					colNum++;
					
					/**室分名称*/
					String reName="";
					reName= StaticMethod.nullObject2String(row.getCell(2));
					if("".equals(reName)){
						errMsg.append("室分名称不能为空;");
						flag=1;
						//throw new Exception("室分名称不能为空");
					}else if(ei.isHaveSameName(reName, nameList)){
						errMsg.append("该室分名称在Excel中已重复;");
						flag=3;
					}else{
						
						List<RoomResource> sbsbtlist=getRerByName(reName);
						if(sbsbtlist.size()>0){
							errMsg.append("该室分名称在系统中已存在;");
							flag=2;
							//throw new Exception("该室分名称在系统中已存在");
						}					
					}
					roomResource.setReName(reName);
					colNum++;
					
					//所属地市ID ？？？
					
					String citiesID="";
					String citiesName = StaticMethod.nullObject2String(row.getCell(3));
					if("".equals(citiesName)){
						errMsg.append("地市不能为空;");
						flag=1;
						//throw new Exception("所属地市不能为空");
					}else{
						
						List<TawSystemArea> alist = tawArea.getTawareas(citiesName);
						if(alist.size()>0){
							citiesID=alist.get(0).getAreaid();
						}else{
							errMsg.append("地市-系统中不存在,请联系管理员;");
							flag=2;
							//throw new Exception("所选地市-系统中不存在");
						}
					}
						
					roomResource.setCity(citiesID);
					colNum++;
					//所属区域ID ？？？
					String countryID="";
					String country = StaticMethod.nullObject2String(row.getCell(4));
					if("".equals(country)){
						errMsg.append("地区不能为空;");
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
									
									errMsg.append("地区-系统中不存在,请联系管理员;");

							}
						}else{
							errMsg.append("地区-系统中不存在,请联系管理员;");
							flag=2;
							//throw new Exception("所选区域-系统中不存在");
						}
					}
					roomResource.setCountry(countryID);
				 	colNum++;
				 	//巡检专业
				 	String specialtyId="";
				 	String specialty = StaticMethod.nullObject2String(row.getCell(5));
				 	if("".equals(specialty)){
				 		errMsg.append("巡检专业不能为空;");
				 	}else{
				 		specialty = specialty.trim();
				 		List<TawSystemDictType> speclist = tawty.getTawSystemDictTypeByName(specialty, "11225", 5);
				 		if(speclist.size()>0){
				 			specialtyId=speclist.get(0).getDictId();
				 			roomResource.setSpecialty(specialtyId);
				 		}else{
				 			errMsg.append("巡检专业-系统中不存在,请联系管理员;");
				 		}
				 	}
				 	colNum++;
				 	//资源类别
				 	String resTypeId="";
				 	String resType = StaticMethod.nullObject2String(row.getCell(6));
				 	if(specialtyId.length()>0){
				 		
				 		if("".equals(resType)){
				 			errMsg.append("资源类别不能为空;");
				 		}else{
				 			resType = resType.trim();
				 			List<TawSystemDictType> resTypelist = tawty.getTawSystemDictTypeByName(resType,specialtyId, 7);
				 			if(resTypelist.size()>0){
				 				resTypeId=resTypelist.get(0).getDictId();
				 				roomResource.setResType(resTypeId);
				 			}else{
				 				errMsg.append("资源类别-系统中不存在,请联系管理员;");
				 			}
				 		}
				 	}
				 	colNum++;
				 	//资源级别
				 	String resLevelId="";
				 	String resLevel = StaticMethod.nullObject2String(row.getCell(7));
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
				 		
				 		roomResource.setResLevel(resLevelId);
				 		
				 	}
				 	colNum++;
				 	/**室分地址*/
				 	String address = StaticMethod.nullObject2String(row.getCell(8));
				 	roomResource.setAddress(address);
					colNum++;
					//经度
					Double  longitude=0.0;
					String getlongitude= StaticMethod.nullObject2String(row.getCell(9));
					if("".equals(getlongitude)){
						errMsg.append("经度不能空;");
						
					}else{
						getlongitude=getlongitude.trim();
						if(getlongitude.matches(PnrNetResourceUtil.REG)){
							
							longitude= Double.parseDouble(getlongitude);
							roomResource.setLongitude(longitude);
						}else{
							
							errMsg.append("经度-不正确;");
						}

					}
					roomResource.setLongitude(longitude);
					colNum++;
					//纬度
					Double latitude=0.0;
					String getlatitude= StaticMethod.nullObject2String(row.getCell(10));
					if("".equals(getlatitude)){
						errMsg.append("纬度不能空;");
					}else{
						
						getlatitude=getlatitude.trim();
						if(getlatitude.matches(PnrNetResourceUtil.REG)){
							latitude = Double.parseDouble(getlatitude);
							roomResource.setLatitude(latitude);
						}else{
							errMsg.append("纬度-不正确;");							
						}
					}
					roomResource.setLatitude(latitude);
					colNum++;
					/**2G信源情况*/
					String source2g = StaticMethod.nullObject2String(row.getCell(11));
					roomResource.setSource2g(source2g);
					colNum++;
					/**lac*/
					String lac = StaticMethod.nullObject2String(row.getCell(12));
					roomResource.setLac(lac);
					colNum++;
					/**ci*/
					String ci = StaticMethod.nullObject2String(row.getCell(13));
					roomResource.setCi(ci);
					colNum++;
					/**BCCH*/
					String bcch = StaticMethod.nullObject2String(row.getCell(14));
					roomResource.setBcch(bcch);
					colNum++;
					/**3G信源情况*/
					String source3g = StaticMethod.nullObject2String(row.getCell(15));
					roomResource.setSource3g(source3g);
					colNum++;
					/**PSC*/
					String psc = StaticMethod.nullObject2String(row.getCell(16));
					roomResource.setPsc(psc);
					colNum++;
					/**干放安装位置*/
					String location =StaticMethod.nullObject2String(row.getCell(17)); 
					roomResource.setLocation(location);
					colNum++;
					/**2G干放型号*/
					String model2g =StaticMethod.nullObject2String(row.getCell(18)); 
					roomResource.setModel2g(model2g);
					colNum++;
					/**2G干放数量*/
					String number2g =StaticMethod.nullObject2String(row.getCell(19)); 
					roomResource.setNumber2g(number2g);
					colNum++;
					/**3G干放型号*/
					String model3g =StaticMethod.nullObject2String(row.getCell(20));
					roomResource.setModel3g(model3g);
					colNum++;
					/**3G干放数量*/
					String number3g = StaticMethod.nullObject2String(row.getCell(21));
					roomResource.setNumber3g(number3g);
					colNum++;
					/**业主联系人*/
					String contact = StaticMethod.nullObject2String(row.getCell(22));
					roomResource.setContact(contact);					
					colNum++;
					/**联系电话*/
					String contactTel =ExcelImport.getStringCellValue(row.getCell(23));
					roomResource.setContactTel(contactTel);
					
				
					
//					保存错误信息的集合
					errorDatas[0]=Integer.toString(rowNum);//序号
					errorDatas[1]=reNumber;
					errorDatas[2]=reName;
					errorDatas[3]=citiesName;
					errorDatas[4]=country;
					errorDatas[5]=specialty;
					errorDatas[6]=resType;
					errorDatas[7]=resLevel;
					errorDatas[8]=address;
					errorDatas[9]=getlongitude;	
					errorDatas[10]=getlatitude;
					errorDatas[11]=source2g;
					errorDatas[12]=lac;
					errorDatas[13]=ci;
					errorDatas[14]=bcch;
					errorDatas[15]=source3g;	
					errorDatas[16]=psc;
					errorDatas[17]=location;
					errorDatas[18]=model2g;
					errorDatas[19]=number2g;
					errorDatas[20]=model3g;	
					errorDatas[21]=number3g;
					errorDatas[22]=contact;
					errorDatas[23]=contactTel;
					//区分错误与正确的列表

					if(errMsg.length()==0){						
						mainList.add(roomResource);
						errorDatas=null;
					}else{
						errorDatas[24]=errMsg.toString();
						errorList.add(errorDatas);
						flag=0;
						roomResource=null;
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
				 insertMainSql.append(" insert into pnr_room_resource (id, re_number,re_name,city,country,");//5
				 insertMainSql.append(" address,longitude,latitude ,source_2g,bcch,");//5
				 insertMainSql.append(" source_3g,psc,location , model_2g , number_2g,");//5
				 insertMainSql.append(" model_3g,number_3g,contact,contact_tel,lac,ci,specialty,res_type,res_level)");//9
				 insertMainSql.append(" values(?,?,?,?,?,?,?,?,?,?,");
				 insertMainSql.append("?,?,?,?,?,?,?,?,?,?,?,?,?,?)");//24
				
				stmt1=conn.prepareStatement(insertMainSql.toString());
				for(int i = 0;i<mainlist1.size();i++){
					rerJdbc.importData(mainlist1.get(i),stmt1,1);
							stmt1.executeBatch();
							stmt1.clearBatch();
					
				}
				 
				for(int k=0;k<mainList.size();k++){
					
					PnrResConfig pnrResConfig=null;
					RoomResource sr = (RoomResource) mainList.get(k);
					//资源信息
					pnrResConfig = new PnrResConfig();
					pnrResConfig.setId(UUIDHexGenerator.getInstance().getID());
					pnrResConfig.setResName(sr.getReName());
					//专业 室分
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
					pnrResConfig.setSubResTable("pnr_room_resource");
					pnrResConfig.setSubResId(sr.getId());
					pnrResConfig.setCity(sr.getCity());
					pnrResConfig.setCountry(sr.getCountry());
					//创建人
					pnrResConfig.setCreator(creatorId);
					//创建时间
					SimpleDateFormat dateformat =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date date = new Date();
					pnrResConfig.setCreateTime(dateformat.format(date));
				/*	//业主单位
					pnrResConfig.setCompanyName(sr.getMaintenanceUnit());*/
					//联系人
					pnrResConfig.setContactName(sr.getContact());
					//联系人电话
					pnrResConfig.setPhone(sr.getContactTel());
				/*	//服务区域
					pnrResConfig.setServiceRegion(sr.getBureau());*/
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
				String outfilepath ="shifenziyuan"+creatorId+".xls";
				outfilepath =ExcelImport.outfilePath(osPath,outfilepath);
				int sheet =1 ,rowNum=3;
				String[] names={"序号","室分编号","室分名称*","地市*","地区*","巡检专业*","资源类别*","资源级别*","室分地址","经度*","纬度*","2G信源情况","LAC","CI","BCCH","3G信源情况","PSC","干放安装位置","2G干放型号","2G干放数量","3G干放型号","3G干放数量","业主联系人","联系电话","错误信息"};
				String sheetName="室分资源";
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


	public RoomResourceDao getRerDao() {
		return rerDao;
	}


	public void setRerDao(RoomResourceDao rerDao) {
		this.rerDao = rerDao;
		this.setCommonGenericDao(rerDao);
	}


	public IRoomResourceDaoJdbc getRerJdbc() {
		return rerJdbc;
	}


	public void setRerJdbc(IRoomResourceDaoJdbc rerJdbc) {
		this.rerJdbc = rerJdbc;
	}
	
	
}
