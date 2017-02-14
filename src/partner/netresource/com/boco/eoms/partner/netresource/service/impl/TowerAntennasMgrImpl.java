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
import com.boco.eoms.partner.netresource.dao.IEomsDao;
import com.boco.eoms.partner.netresource.dao.IOutdoorCabinetDaoJdbc;
import com.boco.eoms.partner.netresource.dao.ITowerAntennasDaoJdbc;
import com.boco.eoms.partner.netresource.dao.OutdoorCabinetDao;
import com.boco.eoms.partner.netresource.dao.TowerAntennasDao;
import com.boco.eoms.partner.netresource.model.bs.OutdoorCabinet;
import com.boco.eoms.partner.netresource.model.bs.TowerAntennas;
import com.boco.eoms.partner.netresource.service.TowerAntennasMgr;
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
public class TowerAntennasMgrImpl extends CommonGenericServiceImpl<TowerAntennas> implements TowerAntennasMgr {

	private TowerAntennasDao  towerAntennasDao;
	private ITowerAntennasDaoJdbc towerAntennasDaoJdbc;
	
	private PnrResConfigMgr pnrResService;
	private ITawSystemAreaManager  tawArea;
	private ITawSystemDictTypeManager  tawty;
	
	private int errorCount=0;
	private String[] errorDatas;//存放错误的数组
    private StringBuilder errMsg;//错误信息
	private int rowNum =1; //行数
	public List<String> nameList = new ArrayList<String>();

	
	public List<TowerAntennas> getTowerAntennasByName(String anName) {
		// TODO Auto-generated method stub
		StringBuffer hql = new StringBuffer();
		hql.append(" select new TowerAntennas(p.id,p.taName) from TowerAntennas p where p.taName='").append(anName).append("'");
		
		List<TowerAntennas> list =towerAntennasDao.findByHql(hql.toString());
		
		return list;
	}
	
	
	/**
	 * 资源导入--铁塔及天馈资源
	 */
	public ImportResult importTowerAntennasFromFile(FormFile formFile, final String province,final String specialty,final String creatorId,final String osPath)
	throws Exception {
		tawArea =(ITawSystemAreaManager)ApplicationContextHolder
		.getInstance().getBean("ItawSystemAreaManager");
		tawty=(ITawSystemDictTypeManager)ApplicationContextHolder
		.getInstance().getBean("ItawSystemDictTypeManager");
		pnrResService=(PnrResConfigMgr)ApplicationContextHolder.getInstance().getBean("PnrResConfigMgr");
		
		final List<Object> mainList = new ArrayList<Object>();
		final List<Object> errorList = new ArrayList<Object>();
		
		final ExcelImport ei=new ExcelImport(2,1,16);		
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
			
		
		
			@SuppressWarnings({ "unchecked", "static-access" })
			@Override
			public List<Object> fromRow2Object(HSSFRow row) throws Exception {
				List<Object> mainList = new ArrayList<Object>();
				//资源信息 
				PnrResConfig pnrResConfig=null;
				TowerAntennas  towerAntennas = null;
				 errorDatas = new String[17];
				 errMsg=new StringBuilder();
				
				int colNum=0;
				try {
					towerAntennas = new TowerAntennas();
					String id = UUIDHexGenerator.getInstance().getID();
					towerAntennas.setId(id);
					colNum++;
					
					/**基站名称（常用名称）*/					
					String anotherTaName = StaticMethod.nullObject2String(row.getCell(1));
					if(!"".equals(anotherTaName)){
						anotherTaName=anotherTaName.trim();
					}
					towerAntennas.setAnotherTaName(anotherTaName);
					colNum++;

					/**资源管理系统中的基站名称*/					
					String taName = StaticMethod.nullObject2String(row.getCell(2));
					if("".equals(taName)){
						errMsg.append("资源管理系统中的基站名称不能为空;");
					}else if(ei.isHaveSameName(taName, nameList)){
						errMsg.append("资源管理系统中的基站名称Excel中已重复;");
					}else{
						taName= taName.trim();
						List<TowerAntennas> glist =getTowerAntennasByName(taName);
						if(glist.size()>0){
							errMsg.append("资源管理系统中的基站名称系统中已存在;");
						}
					}
					towerAntennas.setTaName(taName);
					colNum++;
					
					//所属地市ID					
					String citiesID="";
					String citiesName = StaticMethod.nullObject2String(row.getCell(3));
					if("".equals(citiesName)){
						errMsg.append("所属市不能为空;");
					}else{
						citiesName =citiesName.trim();
						List<TawSystemArea> alist = tawArea.getTawareas(citiesName);
						if(alist.size()>0){
							citiesID=alist.get(0).getAreaid();
							towerAntennas.setCity(citiesID);
						}else{
							errMsg.append("所属市-系统中不存在,请联系管理员;");
						}
					}
					colNum++;
					//所属区域ID
					String countryID="";
					String country = StaticMethod.nullObject2String(row.getCell(4));
					if("".equals(country)){
						errMsg.append("所属区县不能为空;");
					}else{
						country = country.trim();
						List<TawSystemArea> alist2 = tawArea.getTawareas(country);
						if(alist2.size()>0){
							for(int i=0;i<alist2.size();i++){
								if(alist2.get(i).getParentAreaid().equals(citiesID)){
									countryID=alist2.get(i).getAreaid();
									break;
								}
							}
							if("".equals(countryID)){
									errMsg.append("所属区县-系统中不存在,请联系管理员;");
							}
							towerAntennas.setCountry(countryID);
						}else{
							errMsg.append("所属区县-系统中不存在,请联系管理员;");
						}
					}

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
				 			towerAntennas.setSpecialty(specialtyId);
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
				 				towerAntennas.setResType(resTypeId);
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
				 		
				 		towerAntennas.setResLevel(resLevelId);
				 		
				 	}
				 	colNum++;
					/**产权*/			
					String equity = StaticMethod.nullObject2String(row.getCell(8));					
					towerAntennas.setEquity(equity);
					colNum++;
				    
					/**铁塔类型*/
					String towerType = StaticMethod.nullObject2String(row.getCell(9));
					towerAntennas.setTowerType(towerType);
					colNum++;
					/**天馈线类型*/
					String antennaType = StaticMethod.nullObject2String(row.getCell(10));
					towerAntennas.setAntennaType(antennaType);
					colNum++;
					/**铁塔高度（米）*/
					String towerHeight = StaticMethod.nullObject2String(row.getCell(11));
					towerAntennas.setTowerHeight(towerHeight);
					colNum++;
					
					//经度
					Double  longitude=0.0;
					String getlongitude= StaticMethod.nullObject2String(row.getCell(12));
					if(PnrNetResourceUtil.checkIsNull(getlongitude)){
						errMsg.append("经度不能空;");
					}else{
						getlongitude=getlongitude.trim();
						if(getlongitude.matches(PnrNetResourceUtil.REG)){
							
							longitude= Double.parseDouble(getlongitude);
							towerAntennas.setLongitude(longitude);
						}else{
							
							errMsg.append("经度-不正确;");
						}
					}
					
					colNum++;
					//纬度
					Double latitude=0.0;
					String getlatitude= StaticMethod.nullObject2String(row.getCell(13));
					if(PnrNetResourceUtil.checkIsNull(getlatitude)){
						errMsg.append("纬度不能空;");						
					}else{

						getlatitude=getlatitude.trim();
						if(getlatitude.matches(PnrNetResourceUtil.REG)){
							latitude = Double.parseDouble(getlatitude);
							towerAntennas.setLatitude(latitude);
						}else{
							errMsg.append("纬度-不正确;");							
						}
					}

					colNum++;
					
					/**塔上RRU数量（套）*/
					String  rruNumber = StaticMethod.nullObject2String(row.getCell(14));
					if(!PnrNetResourceUtil.checkIsNull(rruNumber)){
						
						if(PnrNetResourceUtil.checkIsNumber(rruNumber)) {
							towerAntennas.setRruNumber(Double.parseDouble(rruNumber));
						}else{
							errMsg.append("塔上RRU数量(套)-必须为数字;");
						}
						
					}
					colNum++;
					
					/**塔上直放站数量（套）*/
					String  repeaterNumber = StaticMethod.nullObject2String(row.getCell(15));
					
					if(!PnrNetResourceUtil.checkIsNull(repeaterNumber)){
						
						if(PnrNetResourceUtil.checkIsNumber(repeaterNumber)) {
							towerAntennas.setRepeaterNumber(Double.parseDouble(repeaterNumber));
						}else{
							errMsg.append("塔上直放站数量(套)-必须为数字;");
						}
						
					}
					
//					保存错误信息的集合
					errorDatas[0]=Integer.toString(rowNum);//序号
					errorDatas[1]=anotherTaName;
					errorDatas[2]=taName;
					errorDatas[3]=citiesName;
					errorDatas[4]=country;
					errorDatas[5]=specialty;
					errorDatas[6]=resType;
					errorDatas[7]=resLevel;
					errorDatas[8]=equity;
					errorDatas[9]=towerType;
					errorDatas[10]=antennaType;
					errorDatas[11]=towerHeight;
					errorDatas[12]=getlongitude;
					errorDatas[13]=getlatitude;
					errorDatas[14]=rruNumber;	
					errorDatas[15]=repeaterNumber;
					
					//区分错误与正确的列表

					if(errMsg.length()==0){						
						mainList.add(towerAntennas);
						errorDatas=null;
					}else{
						errorDatas[16]=errMsg.toString();
						errorList.add(errorDatas);
						towerAntennas=null;
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
				 insertMainSql.append(" insert into pnr_tower_antennas(id,another_ta_name,ta_name ,city,country,");//5
				 insertMainSql.append(" specialty,res_type,res_level ,equity,");//4
				 insertMainSql.append(" tower_type , antenna_type , tower_height ,");//3
				 insertMainSql.append(" longitude,latitude,rru_number,repeater_number)");//4
				 insertMainSql.append(" values(?,?,?,?,?,?,?,?,?,?,");//10
				 insertMainSql.append("?,?,?,?,?,?)");//6
			
				stmt1=conn.prepareStatement(insertMainSql.toString());
				for(int i = 0;i<mainlist1.size();i++){
					towerAntennasDaoJdbc.importData(mainlist1.get(i),stmt1,1);
							stmt1.executeBatch();
							stmt1.clearBatch();
					
				}
				
				for(int k=0;k<mainList.size();k++){
					
					PnrResConfig pnrResConfig=null;
					TowerAntennas sr = (TowerAntennas) mainList.get(k);
					//资源信息
					pnrResConfig = new PnrResConfig();
					pnrResConfig.setId(UUIDHexGenerator.getInstance().getID());
					pnrResConfig.setResName(sr.getTaName());
					//专业 
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
					pnrResConfig.setSubResTable("pnr_tower_antennas");
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
//					pnrResConfig.setServiceRegion(sr.getMaintenanceArea());
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
				String outfilepath ="tietatiankui"+creatorId+".xls";
				outfilepath =ExcelImport.outfilePath(osPath,outfilepath);

				int sheet =1 ,rowNum=3;
				String[] names={"序号","基站名称(常用名称)","资源管理系统中的基站名称*","所属市*","所属区县*","巡检专业*","资源类别*","资源级别*","产权","铁塔类型","天馈线类型","铁塔高度(米)","经度*","纬度*","塔上RRU数量(套)","塔上直放站数量(套)","错误信息"};
				String sheetName="铁塔天馈";
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


	public TowerAntennasDao getTowerAntennasDao() {
		return towerAntennasDao;
	}


	public void setTowerAntennasDao(TowerAntennasDao towerAntennasDao) {
		this.towerAntennasDao = towerAntennasDao;
		this.setCommonGenericDao(towerAntennasDao);
	}


	public ITowerAntennasDaoJdbc getTowerAntennasDaoJdbc() {
		return towerAntennasDaoJdbc;
	}


	public void setTowerAntennasDaoJdbc(ITowerAntennasDaoJdbc towerAntennasDaoJdbc) {
		this.towerAntennasDaoJdbc = towerAntennasDaoJdbc;
	}


		
}
