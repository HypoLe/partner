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
import com.boco.eoms.partner.netresource.model.bs.BsBtAp;
import com.boco.eoms.partner.netresource.service.BsBtApMgr;
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
public class BsBtApMgrImpl extends CommonGenericServiceImpl<BsBtAp> implements BsBtApMgr {

	private BsBtApDao  apDao;
	private IBsBtApDaoJdbc apJdbc;
	
	private PnrResConfigMgr pnrResService;
	private ITawSystemAreaManager  tawArea;
	private ITawSystemDictTypeManager  tawty;
	
	private int flag = 0 ;//标示有错误出现
	private int errorCount=0;
    private String[] errorDatas;//存放错误的数组
	private StringBuilder errMsg;//错误信息
    private int rowNum =1; //行数
	public List<String> nameList = new ArrayList<String>();

	public List<BsBtAp> getApName(String apName) {
		// TODO Auto-generated method stub
		StringBuffer hql = new StringBuffer();
		hql.append(" select new BsBtAp(p.id,p.apName) from BsBtAp p where p.apName='").append(apName).append("'");
		
		List<BsBtAp> list =apDao.findByHql(hql.toString());
		
		return list;
	}
	
	
	/**
	 * 资源导入--基站机房资源
	 */
	public ImportResult importBsBtApFromFile(FormFile formFile, final String province,final String specialty,final String creatorId,final String osPath)
	throws Exception {

		tawArea =(ITawSystemAreaManager)ApplicationContextHolder
		.getInstance().getBean("ItawSystemAreaManager");
		tawty=(ITawSystemDictTypeManager)ApplicationContextHolder
		.getInstance().getBean("ItawSystemDictTypeManager");
		pnrResService=(PnrResConfigMgr)ApplicationContextHolder.getInstance().getBean("PnrResConfigMgr");
		
		final List<Object> mainList = new ArrayList<Object>();
		final List<Object> errorList = new ArrayList<Object>();
		
		final ExcelImport ei=new ExcelImport(2,1,48);	
		nameList = ei.getExcelNameList(formFile, 1,1);
		
		ImportResult result=ei.importFromFile(formFile,1,new DataSaveCallback(){
			public void doSaveRow2Data(HSSFRow row) throws Exception {
				List<Object> mapList=this.fromRow2Object(row);
				if(mapList.size()==1){
					mainList.add(mapList.get(0));
				}else{
					errorCount++;
				}
			}
			
			SimpleDateFormat dateformat =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat date2dd    =  new SimpleDateFormat("yyyy-MM-dd");
		
			//将每行转化成SdBsBtResource对象
			@SuppressWarnings({ "unchecked", "static-access" })
			@Override
			public List<Object> fromRow2Object(HSSFRow row) throws Exception {
				List<Object> mainList = new ArrayList<Object>();
				//资源信息 
				PnrResConfig pnrResConfig=null;
				BsBtAp  bsBtAp = null;
				errorDatas = new String[49];
				errMsg=new StringBuilder();
				
				int colNum=0;
				try {
					bsBtAp = new BsBtAp();
					String id = UUIDHexGenerator.getInstance().getID();
					bsBtAp.setId(id);
					colNum++;
					
					//机房位置
					String apName="";
					apName= StaticMethod.nullObject2String(row.getCell(1));
					if("".equals(apName)){
						errMsg.append("位置名称不能为空;");
						//throw new Exception("机房位置不能为空");
					}else if(ei.isHaveSameName(apName, nameList)){
						errMsg.append("该位置名称在Excel表中已重复;");
					}else{
						
						List<BsBtAp> sbsbtlist=getApName(apName);
						if(sbsbtlist.size()>0){
							errMsg.append("该位置名称在系统中已存在;");
							//throw new Exception("该机房位置在系统中已存在");
						}					
					}
					bsBtAp.setApName(apName);
					colNum++;
					
					//所属地市ID ？？？
					
					String citiesID="";
					String citiesName = StaticMethod.nullObject2String(row.getCell(2));
					if("".equals(citiesName)){
						errMsg.append("所属地市不能为空;");

						//throw new Exception("所属地市不能为空");
					}else{
						
						List<TawSystemArea> alist = tawArea.getTawareas(citiesName);
						if(alist.size()>0){
							citiesID=alist.get(0).getAreaid();
						}else{
							errMsg.append("所属地市-系统中不存在,请联系管理员;");

							//throw new Exception("所选地市-系统中不存在");
						}
					}
					bsBtAp.setCity(citiesID);
					colNum++;
					//所属区域ID ？？？
					String countryID="";
					String country = StaticMethod.nullObject2String(row.getCell(3));
					if("".equals(country)){
						errMsg.append("所属区域不能为空;");

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
						//	throw new Exception("所选区域-系统中不存在");
						}
					}
					bsBtAp.setCountry(countryID);
				 	colNum++;
				 	//巡检专业
				 	String specialtyId="";
				 	String specialty = StaticMethod.nullObject2String(row.getCell(4));
				 	if("".equals(specialty)){
				 		errMsg.append("巡检专业不能为空;");
				 	}else{
				 		specialty = specialty.trim();
				 		List<TawSystemDictType> speclist = tawty.getTawSystemDictTypeByName(specialty, "11225", 5);
				 		if(speclist.size()>0){
				 			specialtyId=speclist.get(0).getDictId();
				 			bsBtAp.setSpecialty(specialtyId);
				 		}else{
				 			errMsg.append("巡检专业-系统中不存在,请联系管理员;");
				 		}
				 	}
				 	colNum++;
				 	//资源类别
				 	String resTypeId="";
				 	String resType = StaticMethod.nullObject2String(row.getCell(5));
				 	if(specialtyId.length()>0){
				 		
				 		if("".equals(resType)){
				 			errMsg.append("资源类别不能为空;");
				 		}else{
				 			resType = resType.trim();
				 			List<TawSystemDictType> resTypelist = tawty.getTawSystemDictTypeByName(resType,specialtyId, 7);
				 			if(resTypelist.size()>0){
				 				resTypeId=resTypelist.get(0).getDictId();
				 				bsBtAp.setResType(resTypeId);
				 			}else{
				 				errMsg.append("资源类别-系统中不存在,请联系管理员;");
				 			}
				 		}
				 	}
				 	colNum++;
				 	//资源级别
				 	String resLevelId="";
				 	String resLevel = StaticMethod.nullObject2String(row.getCell(6));
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
				 		
				 		bsBtAp.setResLevel(resLevelId);
				 		
				 	}
				 	colNum++;
					//所在局所
				 	String bureau = StaticMethod.nullObject2String(row.getCell(7));
				 	bsBtAp.setBureau(bureau);
					colNum++;
					//接入点类型
					String accessType = StaticMethod.nullObject2String(row.getCell(8));
					bsBtAp.setAccessType(accessType);
					colNum++;
					//机房位置
					String apAddress = StaticMethod.nullObject2String(row.getCell(9));
					bsBtAp.setApAddress(apAddress);
					colNum++;
					//资产归属
					String assetsAttributable =StaticMethod.nullObject2String(row.getCell(10)); 
					bsBtAp.setAssetsAttributable(assetsAttributable);
					colNum++;
					//联系人
					String contact = StaticMethod.nullObject2String(row.getCell(11));
					bsBtAp.setContact(contact);					
					colNum++;
					//联系电话
					String contactTel =ExcelImport.getStringCellValue(row.getCell(12));
					bsBtAp.setContactTel(contactTel);
					colNum++;
					//备注
					String  remark = StaticMethod.nullObject2String(row.getCell(13));
					bsBtAp.setRemark(remark);
					colNum++;
					//录入人
					String  entryPeople = StaticMethod.nullObject2String(row.getCell(14));
					bsBtAp.setEntryPeople(entryPeople);
					colNum++;
					//录入日期
					Date entryTime;
					String  entryTime2 = StaticMethod.nullObject2String(row.getCell(15));
					if("".equals(entryTime2)){
						Date time = new Date();
						entryTime =time;
					}else{
					entryTime=dateformat.parse(entryTime2);
					}
					bsBtAp.setEntryTime(entryTime);
					colNum++;
					//修改人
					String  modifyPeople = StaticMethod.nullObject2String(row.getCell(16));
					bsBtAp.setModifyPeople(modifyPeople);
					colNum++;
					//修改日期
					Date modifyTime;
					String  modifyTime2 = StaticMethod.nullObject2String(row.getCell(17));
					if("".equals(modifyTime2)){
					modifyTime=null;
					}else{
					modifyTime=dateformat.parse(modifyTime2);
					}
					bsBtAp.setModifyTime(modifyTime);
					colNum++;
					//长
					String getLength=StaticMethod.nullObject2String(row.getCell(18));
					double  length =0;
					if("".equals(getLength)){
					}else{
						length= Double.parseDouble(getLength);
						
					}
					bsBtAp.setLength(length);
					colNum++;
					//宽
					String getWidth=StaticMethod.nullObject2String(row.getCell(19));
					double  width =0;
					if("".equals(getWidth)){
					}else{
						width= Double.parseDouble(getWidth);
						
					}
					bsBtAp.setWidth(width);
					colNum++;
					//高
					String getHeight=StaticMethod.nullObject2String(row.getCell(20));
					double  height =0;
					if("".equals(getHeight)){
					}else{
						height= Double.parseDouble(getHeight);
						
					}
					bsBtAp.setHeight(height);
					colNum++;
					//经度
					Double  longitude=0.0;

					String getlongitude= StaticMethod.nullObject2String(row.getCell(21));
					if("".equals(getlongitude)){
						errMsg.append("GPS_X不能空;");
					
					}else{
						getlongitude=getlongitude.trim();
						if(getlongitude.matches(PnrNetResourceUtil.REG)){
							
							longitude= Double.parseDouble(getlongitude);
							bsBtAp.setLongitude(longitude);
						}else{
							errMsg.append("GPS_X-不正确;");
						}

					}
					colNum++;
					//纬度
					Double latitude=0.0;
					String getlatitude= StaticMethod.nullObject2String(row.getCell(22));
					if("".equals(getlatitude)){
						errMsg.append("GPS_Y不能空;");
					}else{
						
						getlatitude=getlatitude.trim();
						if(getlatitude.matches(PnrNetResourceUtil.REG)){
							latitude = Double.parseDouble(getlatitude);
							bsBtAp.setLatitude(latitude);
						}else{
							errMsg.append("GPS_Y-不正确;");							
						}
					}
					colNum++;
					//集团可用面积
					Double usableArea;
					String getusableArea= StaticMethod.nullObject2String(row.getCell(23));
					if("".equals(getusableArea)){
						usableArea=0.0;
					}else{
						
						usableArea = Double.parseDouble(getusableArea);
					}				
					bsBtAp.setUsableArea(usableArea);
					colNum++;
					//集团已用面积
					Double usedArea;
					String getusedArea= StaticMethod.nullObject2String(row.getCell(24));
					if("".equals(getusedArea)){
						usedArea=0.0;
					}else{
						
						usedArea = Double.parseDouble(getusedArea);
					}		
					bsBtAp.setUsedArea(usedArea);
					colNum++;
					
					//集团机房归属
					String  apVested = StaticMethod.nullObject2String(row.getCell(25));
					bsBtAp.setApVested(apVested);
					colNum++;
					
					//集团是否共享
					String  isShare = StaticMethod.nullObject2String(row.getCell(26));
					bsBtAp.setIsShare(isShare);
					colNum++;
					//集团共享运营商
					String  shareOperators = StaticMethod.nullObject2String(row.getCell(27));
					bsBtAp.setShareOperators(shareOperators);
					colNum++;
					//机房大类
					String  apCategories = StaticMethod.nullObject2String(row.getCell(28));
					bsBtAp.setApCategories(apCategories);
					colNum++;
					//机房小类
					String  apSmallClass = StaticMethod.nullObject2String(row.getCell(29));
					bsBtAp.setApSmallClass(apSmallClass);
					colNum++;
					//集团机房类型					
					String  groupApTypes = StaticMethod.nullObject2String(row.getCell(30));
					bsBtAp.setGroupApTypes(groupApTypes);
					colNum++;
					
					//集团机房承重
					Double groupRoombear;
					String getgroupRoombear= StaticMethod.nullObject2String(row.getCell(31));
					if("".equals(getgroupRoombear)){
						groupRoombear=0.0;
					}else{
						
						groupRoombear = Double.parseDouble(getgroupRoombear);
					}		
					bsBtAp.setGroupRoombear(groupRoombear);
					colNum++;
					
					//集团走线方式
					String  alignmentMethod = StaticMethod.nullObject2String(row.getCell(32));
					bsBtAp.setAlignmentMethod(alignmentMethod);
					colNum++;
					
					//集团单双走线架
					String  chutes = StaticMethod.nullObject2String(row.getCell(33));
					bsBtAp.setChutes(chutes);
					colNum++;
					//集团消防系统
					String  fireSystem = StaticMethod.nullObject2String(row.getCell(34));
					bsBtAp.setFireSystem(fireSystem);
					colNum++;
					//集团有无地板
					String  isNoun = StaticMethod.nullObject2String(row.getCell(35));
					bsBtAp.setIsNoun(isNoun);
					colNum++;
					
					//集团有无监控
					String  isMonitor = StaticMethod.nullObject2String(row.getCell(36));
					bsBtAp.setIsMonitor(isMonitor);
					colNum++;
					
					//集团租用到期时间
					Date maturityTime;
					String  maturityTime2 = StaticMethod.nullObject2String(row.getCell(37));
					if("".equals(maturityTime2)){
					maturityTime=null;
					}else{
						
					maturityTime=date2dd.parse(maturityTime2);
					}
					bsBtAp.setMaturityTime(maturityTime);
					colNum++;
					
					//集团房屋类型
					String  roomType = StaticMethod.nullObject2String(row.getCell(38));
					bsBtAp.setRoomType(roomType);
					colNum++;
					//集团维护方式
					String  maintenanceMode = StaticMethod.nullObject2String(row.getCell(39));
					bsBtAp.setMaintenanceMode(maintenanceMode);
					colNum++;
					//集团维护单位
					String  maintenanceUnit = StaticMethod.nullObject2String(row.getCell(40));
					bsBtAp.setMaintenanceUnit(maintenanceUnit);
					colNum++;
					//集团包机人
					String  charter = StaticMethod.nullObject2String(row.getCell(41));
					bsBtAp.setCharter(charter);
					colNum++;
					//集团第三方维护单位
					String  threeMainUnit = StaticMethod.nullObject2String(row.getCell(42));
					bsBtAp.setThreeMainUnit(threeMainUnit);
					colNum++;
					//集团续保截至日期
					Date renewalTime;
					String  renewalTime2 = StaticMethod.nullObject2String(row.getCell(43));
					if("".equals(renewalTime2)){
					renewalTime=null;
					}else{
					renewalTime=date2dd.parse(renewalTime2);
					}
					bsBtAp.setRenewalTime(renewalTime);
					colNum++;
					//集团维保类型
					String  maintenanceType = StaticMethod.nullObject2String(row.getCell(44));
					bsBtAp.setMaintenanceType(maintenanceType);
					colNum++;
					
					//集团已购买维保累计年限
					String  maintenanceYear = StaticMethod.nullObject2String(row.getCell(45));
					bsBtAp.setMaintenanceYear(maintenanceYear);
					colNum++;
					
					
					//集团室外放置
					String  outDoor = StaticMethod.nullObject2String(row.getCell(46));
					bsBtAp.setOutDoor(outDoor);
					colNum++;
					
					//标准地址
					String  place = StaticMethod.nullObject2String(row.getCell(47));
					bsBtAp.setPlace(place);
					colNum++;
					
					
//					保存错误信息的集合
					errorDatas[0]=Integer.toString(rowNum);//序号
					
					errorDatas[1]=apName;
					errorDatas[2]=citiesName;
					errorDatas[3]=country;
					errorDatas[4]=specialty;
					errorDatas[5]=resType;
					errorDatas[6]=resLevel;
					errorDatas[7]=bureau;

					errorDatas[8]=accessType;	
					errorDatas[9]=apAddress;	
					errorDatas[10]=assetsAttributable;	
					errorDatas[11]=contact;
					errorDatas[12]=contactTel;
					errorDatas[13]=remark;	
					errorDatas[14]=entryPeople;
					errorDatas[15]=entryTime2;
					errorDatas[16]=modifyPeople;
					errorDatas[17]=modifyTime2;
					errorDatas[18]=getLength;	
					errorDatas[19]=getWidth;
					errorDatas[20]=getHeight;
					errorDatas[21]=getlongitude;
					errorDatas[22]=getlatitude;	
					errorDatas[23]=getusableArea;
					errorDatas[24]=getusedArea;

					errorDatas[25]=apVested;
					errorDatas[26]=isShare;
					errorDatas[27]=shareOperators;	

					errorDatas[28]=apCategories;
					errorDatas[29]=apSmallClass;
					errorDatas[30]=groupApTypes;
					errorDatas[31]=getgroupRoombear;
					errorDatas[32]=alignmentMethod;	
					errorDatas[33]=chutes;
					errorDatas[34]=fireSystem;
					errorDatas[35]=isNoun;
					errorDatas[36]=isMonitor;
					errorDatas[37]=maturityTime2;	
					errorDatas[38]=roomType;
					errorDatas[39]=maintenanceMode;
					errorDatas[40]=maintenanceUnit;
					errorDatas[41]=charter;
					errorDatas[42]=threeMainUnit;	
					errorDatas[43]=renewalTime2;
					errorDatas[44]=maintenanceType;
					errorDatas[45]=maintenanceYear;
					errorDatas[46]=outDoor;
					errorDatas[47]=place;	
					//区分错误与正确的列表

					if(errMsg.length()==0){						
						mainList.add(bsBtAp);
						errorDatas=null;
					}else{
						errorDatas[48]=errMsg.toString();
						errorList.add(errorDatas);
						flag=0;
						bsBtAp=null;
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
		rowNum=1;
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
				 insertMainSql.append(" insert into PNR_BSBT_AP (ID, AP_NAME,CITY , country,BUREAU ,");//5
				 insertMainSql.append(" ACCESS_TYPE ,AP_ADDRESS,");//2
				 insertMainSql.append(" ASSETS_ATTRIBUTABLE , CONTACT , CONTACT_TEL ,");//3
				 insertMainSql.append(" REMARK,ENTRY_PEOPLE,ENTRY_TIME,");//3
				 insertMainSql.append(" MODIFY_PEOPLE,MODIFY_TIME,LENGTH,WIDTH,HEIGHT,");//5
				 insertMainSql.append(" LONGITUDE,LATITUDE,USABLE_AREA,USED_AREA,");//4
				 insertMainSql.append(" AP_VESTED,IS_SHARE,SHARE_OPERATORS,AP_CATEGORIES,AP_SMALL_CLASS,");//5
				 insertMainSql.append(" GROUP_AP_TYPES,GROUP_ROOM_BEAR,ALIGNMENT_METHOD,CHUTES,FIRE_SYSTEM,");//5
				 insertMainSql.append(" IS_NOUN,IS_MONITOR,MATURITY_TIME,ROOM_TYPE,MAINTENANCE_MODE,");//5
				 insertMainSql.append(" MAINTENANCE_UNIT,CHARTER,THREE_MAIN_UNIT,RENEWAL_TIME,MAINTENANCE_TYPE,");//5
				 insertMainSql.append(" MAINTENANCE_YEAR,OUT_DOOR,PLACE,SPECIALTY,RES_TYPE,RES_LEVEL)");//6
				 insertMainSql.append(" values(?,?,?,?,?,?,?,?,?,?,");
				 insertMainSql.append("?,?,?,?,?,?,?,?,?,?,");//20
				 insertMainSql.append("?,?,?,?,?,?,?,?,?,?,");
				 insertMainSql.append("?,?,?,?,?,?,?,?,?,?,");//20
				 insertMainSql.append("?,?,?,?,?,?,?,?)");//8
			
				stmt1=conn.prepareStatement(insertMainSql.toString());
				for(int i = 0;i<mainlist1.size();i++){
					apJdbc.importData(mainlist1.get(i),stmt1,1);
							stmt1.executeBatch();
							stmt1.clearBatch();
					
				}
			
				for(int k=0;k<mainList.size();k++){
					
					PnrResConfig pnrResConfig=null;
					BsBtAp sr = (BsBtAp) mainList.get(k);
					//资源信息
					pnrResConfig = new PnrResConfig();
					pnrResConfig.setId(UUIDHexGenerator.getInstance().getID());
					pnrResConfig.setResName(sr.getApName());
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
					pnrResConfig.setSubResTable("pnr_bsbt_ap");
					pnrResConfig.setSubResId(sr.getId());
					pnrResConfig.setCity(sr.getCity());
					pnrResConfig.setCountry(sr.getCountry());
					//创建人
					pnrResConfig.setCreator(creatorId);
					//创建时间
					SimpleDateFormat dateformat =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date date = new Date();
					pnrResConfig.setCreateTime(dateformat.format(date));
					//业主单位
					pnrResConfig.setCompanyName(sr.getMaintenanceUnit());
					//联系人
					pnrResConfig.setContactName(sr.getContact());
					//联系人电话
					pnrResConfig.setPhone(sr.getContactTel());
					//服务区域
					pnrResConfig.setServiceRegion(sr.getBureau());
					//线路巡检标识
					pnrResConfig.setTlInspectFlag("0");
					pnrResService.save(pnrResConfig);
		    	}
				
				conn.commit();
				conn.setAutoCommit(true);
			}
			//处理错误信息
			int errorSize = errorList.size();
			if(errorSize>0){
				//开始将错误的信息导入到excel中 
				String outfilepath ="wlanziyuan"+creatorId+".xls";
				outfilepath =ExcelImport.outfilePath(osPath,outfilepath);

				int sheet =1 ,rowNum=3;
				
				String[] names={"序号","位置名称*","所属地市*","所属区域*","巡检专业*","资源类别*","资源级别*","所在局所","接入点类型","位置信息","资产归属","联系人","联系电话","备注","录入人","录入日期","修改人","修改日期","长","宽","高","GPS_X*","GPS_Y*","集团可用面积","集团已用面积","集团机房归属","集团是否共享","集团共享运营商","机房大类","机房小类","集团机房类型","集团机房承重","集团走线方式","集团单双层走线架","集团消防系统","集团有无地板","集团有无监控","集团租用到期时间","集团房屋类型","集团维护方式","集团维护单位","集团包机人","集团第三方维护单位","集团续保截止日期","集团维保类型","集团已购买维保累计年限","集团室外放置点","标准地址","错误信息"};
				String sheetName="wlan";
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


	public BsBtApDao getApDao() {
		return apDao;
	}


	public void setApDao(BsBtApDao apDao) {
		this.apDao = apDao;
		this.setCommonGenericDao(apDao);
	}


	public IBsBtApDaoJdbc getApJdbc() {
		return apJdbc;
	}


	public void setApJdbc(IBsBtApDaoJdbc apJdbc) {
		this.apJdbc = apJdbc;
	}


	public int isExitByName(String apName) {
		// TODO Auto-generated method stub
		return 0;
	}
	
}
