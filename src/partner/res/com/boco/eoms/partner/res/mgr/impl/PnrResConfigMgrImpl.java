package com.boco.eoms.partner.res.mgr.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.upload.FormFile;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.util.UUIDHexGenerator;
import com.boco.eoms.commons.system.dict.dao.ID2NameDAO;
import com.boco.eoms.commons.system.dict.exceptions.DictDAOException;
import com.boco.eoms.commons.system.dict.service.ITawSystemDictTypeManager;
import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.partner.baseinfo.util.PartnerCityByUser;
import com.boco.eoms.partner.netresource.dao.IEomsDao;
import com.boco.eoms.partner.res.dao.IPnrResConfigDaoJdbc;
import com.boco.eoms.partner.res.dao.PnrResConfigDao;
import com.boco.eoms.partner.res.dao.other.resource.ResourceSystemDBUtil;
import com.boco.eoms.partner.res.dao.other.resource.model.JkXcwhRoom;
import com.boco.eoms.partner.res.mgr.IPnrResourceSuccessLogService;
import com.boco.eoms.partner.res.mgr.PnrResConfigMgr;
import com.boco.eoms.partner.res.model.PnrResConfig;
import com.boco.eoms.partner.res.model.PnrResConfigStation;
import com.boco.eoms.partner.res.model.PnrResFamilyBroadband;
import com.boco.eoms.partner.res.model.PnrResIron;
import com.boco.eoms.partner.res.model.PnrResJieke;
import com.boco.eoms.partner.res.model.PnrResLine;
import com.boco.eoms.partner.res.model.PnrResRepeaters;
import com.boco.eoms.partner.res.model.PnrResWlan;
import com.boco.eoms.partner.res.model.PnrResourceSuccessLog;
import com.boco.eoms.partner.res.util.excelimport.DataSaveCallback;
import com.boco.eoms.partner.res.util.excelimport.ExcelImport;
import com.boco.eoms.partner.res.util.excelimport.ImportResult;
import com.boco.eoms.partner.resourceInfo.util.XLSCellValidateUtil;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;

/** 
 * Description: 资源主表
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liaojiming 
 * @version:    1.0 
 */ 
public class PnrResConfigMgrImpl extends CommonGenericServiceImpl<PnrResConfig> implements PnrResConfigMgr {
	
	private PnrResConfigDao pnrResConfigDao;
	
	private IPnrResConfigDaoJdbc pnrResConfigDaoJdbc;
		
	public String temPlateFlag="";
	

	/**
	 * 分页查询资源
	 */
	@SuppressWarnings("unchecked")
	public Map getResources(Integer curPage, Integer pageSize, String whereStr) {
		return pnrResConfigDao.getResources(curPage, pageSize, whereStr);
	}

	public PnrResConfigDao getPnrResConfigDao() {
		return pnrResConfigDao;
	}

	public void setPnrResConfigDao(PnrResConfigDao pnrResConfigDao) {
		this.setCommonGenericDao(pnrResConfigDao);
		this.pnrResConfigDao = pnrResConfigDao;
	}

	@SuppressWarnings("unchecked")
	public void updateAllEntity(Class c,String setWhere, String whereStr) {
		pnrResConfigDao.bulkUpdateByHql(whereStr);
	}

	/**
	 * 资源导入
	 */
	public ImportResult importFromFile(FormFile formFile, final String province,final String specialty,final String creatorId)
			throws Exception {
//		Map<List<PnrResConfig>, List<Object>> mainMap = new HashMap<List<PnrResConfig>, List<Object>>();
		final List<Object> mainList = new ArrayList<Object>();
		final List<Object> subList = new ArrayList<Object>();
		final ExcelImport ei=new ExcelImport(2,1,21);
		ImportResult result=ei.importFromFile(formFile, new DataSaveCallback(){
			public void doSaveRow2Data(HSSFRow row) throws Exception {
				List<Object> mapList=this.fromRow2Object(row);
				if(mapList.size()==1){
					mainList.add(mapList.get(0));
				}else{
					mainList.add(mapList.get(0));
					subList.add(mapList.get(1));
				}
			}

			//将每行转化成PnrResCongif对象
			@SuppressWarnings({ "unchecked", "static-access" })
			@Override
			public List<Object> fromRow2Object(HSSFRow row) throws Exception {
				List<Object> mainList = new ArrayList<Object>();
				PnrResConfig pnrResConfig=null;
				int colNum=0;
				try {
					pnrResConfig = new PnrResConfig();
					pnrResConfig.setId(UUIDHexGenerator.getInstance().getID());
					colNum++;
					
					if(StaticMethod.nullObject2String(row.getCell(1).getRichStringCellValue()).equals("")){
						throw new Exception("错误列数是:"+colNum);
					}
					
					pnrResConfig.setResName(row.getCell(1).getRichStringCellValue().toString());
					colNum++;
					//资源专业类型
					pnrResConfig.setSpecialty(specialty);
					colNum++;
					//设置资源类别
					//String resType = name2Id(row.getCell(3).getRichStringCellValue().toString(), specialty);
					String resType =XLSCellValidateUtil.dictNameToDictId(row, 3, specialty, false);
					pnrResConfig.setResType(resType);
					colNum++;
					//设置资源级别
					String resLevel = "";
					if(!"".equals(StaticMethod.nullObject2String(row.getCell(4)))){
						resLevel =XLSCellValidateUtil.dictNameToDictId(row, 4, resType, false);
					}
					pnrResConfig.setResLevel(resLevel);
					colNum++;
					pnrResConfig.setResLongitude(XLSCellValidateUtil.checkNumber(row, 5, false).replace("\n", ""));
					colNum++;
					pnrResConfig.setResLatitude(XLSCellValidateUtil.checkNumber(row, 6, false).replace("\n", ""));
					colNum++;
					//设置地市
					//String province2 = new AreaName2Id().name2Id(row.getCell(7).getRichStringCellValue().toString(), province); 
					String province2 = XLSCellValidateUtil.checkAndGetAreaId(row, 7);
					pnrResConfig.setCity(province2);
					colNum++;
					//设置区县
					pnrResConfig.setCountry(XLSCellValidateUtil.checkAndGetAreaId(row, 8));
					colNum++;
					pnrResConfig.setStreet(StaticMethod.nullObject2String(row.getCell(9))) ;
					colNum++;
					pnrResConfig.setServiceRegion(row.getCell(10).toString());
					colNum++;
					//地理环境
					String environment = XLSCellValidateUtil.dictNameToDictId(row, 11, "11229", true);
					pnrResConfig.setEographicalEnvironment(environment);
					colNum++;
					//区域类型
					String regionType =  XLSCellValidateUtil.dictNameToDictId(row, 12, "11230", true);
					pnrResConfig.setRegionType(regionType);
					colNum++;
					pnrResConfig.setCompanyName(row.getCell(13).toString());
					colNum++;
					if((row.getCell(14).getRichStringCellValue().toString()).trim().equals("")){
						throw new Exception();
					}
					pnrResConfig.setContactName(row.getCell(14).getRichStringCellValue().toString());
					colNum++;
					if((row.getCell(15).getRichStringCellValue().toString()).trim().equals("")){
						throw new Exception();
					}
					String str1 = row.getCell(15).getRichStringCellValue().toString();
					Pattern parttert = Pattern.compile("(^(\\d{3,4}-)?\\d{7,8})$|(1[0-9]{10})$|(\\(\\d{3,4}\\)?\\d{7,8})$");
					Matcher matcher = parttert.matcher(str1);
					if(matcher.matches()){  //电话号码验证
						pnrResConfig.setPhone(str1);
					}else{
						throw new Exception("电话号码验证出错！");
					}
					//设置创建人
					pnrResConfig.setCreator(creatorId);
					//设置资源创建日期，为当前日期
					pnrResConfig.setCreateTime(StaticMethod.getCurrentDateTime());
					
					//表示基站
					if("1122501".equals(specialty)){
						colNum++;
						PnrResConfigStation pnrResConfigStation = new PnrResConfigStation();
						SimpleDateFormat dateformat =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						if((row.getCell(16).getRichStringCellValue().toString()).trim().equals("")){
							throw new Exception();
						}
						pnrResConfigStation.setAddress(row.getCell(16).getRichStringCellValue().toString());
						colNum++;
						String propertyTypeType = row.getCell(17).getRichStringCellValue().toString();
						if("共享".equals(propertyTypeType)){
							pnrResConfigStation.setPropertyType(1);
						}else if("自建".equals(propertyTypeType)){
							pnrResConfigStation.setPropertyType(2);
						}else if("租用".equals(propertyTypeType)){
							pnrResConfigStation.setPropertyType(3);
						}else {
							pnrResConfigStation.setPropertyType(4);
						}
						String tdOpenTime = "";
						colNum++;
						if(!StringUtils.isEmpty(StaticMethod.nullObject2String(row.getCell(18)))){
							tdOpenTime = dateformat.format(dateformat.parse(StaticMethod.nullObject2String(row.getCell(18))));
						}
						pnrResConfigStation.setTDOpenTime( tdOpenTime);
						colNum++;
						if((row.getCell(19).getRichStringCellValue().toString()).trim().equals("")){
							throw new Exception();
						}
						pnrResConfigStation.setNetType(row.getCell(19).getRichStringCellValue().toString());
						colNum++;
						String openTime="";
						if(!StringUtils.isEmpty(StaticMethod.nullObject2String(row.getCell(20)))){
							openTime = dateformat.format(dateformat.parse(StaticMethod.nullObject2String(row.getCell(20))));
						}
						pnrResConfigStation.setOpenTime(openTime);
						colNum++;
						//当是产权类型为共享时，才又共建共享
						if("共享".equals(propertyTypeType)){
							pnrResConfigStation.setSharing(row.getCell(21).getRichStringCellValue().toString());
						}
						colNum++;
						pnrResConfigStation.setAdjustmentMessage(StaticMethod.nullObject2String(row.getCell(22)));
						colNum++;
						pnrResConfig.setRemarks(StaticMethod.nullObject2String(row.getCell(23)));
						String uuid = UUIDHexGenerator.getInstance().getID();   //uuid作为字表的id
						pnrResConfig.setSubResTable("pnr_res_station");
						pnrResConfigStation.setId(uuid);
						pnrResConfig.setSubResId(uuid);
						mainList.add(pnrResConfig);
						mainList.add(pnrResConfigStation);
					}else if("1122502".equals(specialty)){
						//传输线路
						PnrResLine pnrResLine = new PnrResLine();
						colNum++;
						String endLongitude = StaticMethod.nullObject2String(row.getCell(16));
						pnrResLine.setEndLongitude(endLongitude);
						pnrResConfig.setEndLongitude(endLongitude);
						colNum++;
						String endLatitude  = StaticMethod.nullObject2String(row.getCell(17));
						pnrResLine.setEndLatitude(endLatitude);
						pnrResConfig.setEndLatitude(endLatitude);
						colNum++;
						pnrResLine.setTubeSide(StaticMethod.nullObject2String(row.getCell(18)));
						colNum++;
						pnrResLine.setSingleRouting(StaticMethod.nullObject2String(row.getCell(19)));
						colNum++;
						pnrResLine.setLikeRouting(StaticMethod.nullObject2String(row.getCell(20)));
						colNum++;
						pnrResConfig.setRemarks(StaticMethod.nullObject2String(row.getCell(21)));
						String uuid = UUIDHexGenerator.getInstance().getID();   //uuid作为字表的id
						pnrResConfig.setSubResTable("pnr_res_line");
						pnrResLine.setId(uuid);
						pnrResConfig.setSubResId(uuid);
						mainList.add(pnrResConfig);
						mainList.add(pnrResLine);
					}else if("1122503".equals(specialty)){
						//直放站
						SimpleDateFormat dateformat =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						colNum++;
						PnrResRepeaters pnrResRepeaters = new PnrResRepeaters();
						if((row.getCell(16).getRichStringCellValue().toString()).trim().equals("")){
							throw new Exception();
						}
						pnrResRepeaters.setAddress(row.getCell(16).getRichStringCellValue().toString());
						colNum++;
						String propertyTypeType = row.getCell(17).getRichStringCellValue().toString();
						if("共享".equals(propertyTypeType)){
							pnrResRepeaters.setPropertyType(1);
						}else if("自建".equals(propertyTypeType)){
							pnrResRepeaters.setPropertyType(2);
						}else if("租用".equals(propertyTypeType)){
							pnrResRepeaters.setPropertyType(3);
						}else {
							pnrResRepeaters.setPropertyType(4);
						}
						String tdOpenTime = "";
						colNum++;
						if(!StringUtils.isEmpty(StaticMethod.nullObject2String(row.getCell(18)))){
							tdOpenTime = dateformat.format(dateformat.parse(StaticMethod.nullObject2String(row.getCell(18))));
						}
						pnrResRepeaters.setTDOpenTime( tdOpenTime);
						colNum++;
						if((row.getCell(19).getRichStringCellValue().toString()).trim().equals("")){
							throw new Exception();
						}
						pnrResRepeaters.setNetType(row.getCell(19).getRichStringCellValue().toString());
						colNum++;
						String openTime="";
						if(!StringUtils.isEmpty(StaticMethod.nullObject2String(row.getCell(20)))){
							openTime = dateformat.format(dateformat.parse(StaticMethod.nullObject2String(row.getCell(20))));
						}
						pnrResRepeaters.setOpenTime(openTime);
						colNum++;
						//当是产权类型为共享时，才又共建共享
						if("共享".equals(propertyTypeType)){
							pnrResRepeaters.setSharing(row.getCell(21).getRichStringCellValue().toString());
						}
						colNum++;
						pnrResRepeaters.setAdjustmentMessage(StaticMethod.nullObject2String(row.getCell(22)));
						colNum++;
						pnrResConfig.setRemarks(StaticMethod.nullObject2String(row.getCell(23)));
						String uuid = UUIDHexGenerator.getInstance().getID();   //uuid作为字表的id
						pnrResConfig.setSubResTable("pnr_res_repeaters");
						pnrResRepeaters.setId(uuid);
						pnrResConfig.setSubResId(uuid);
						mainList.add(pnrResConfig);
						mainList.add(pnrResRepeaters);
						
					}else if("1122504".equals(specialty)){
						PnrResIron pnrResIron = new PnrResIron();
						colNum++;
						pnrResIron.setIronType(StaticMethod.nullObject2String(row.getCell(16)));
						colNum++;
						pnrResIron.setIndoorNumber(StaticMethod.nullObject2String(row.getCell(17)));
						colNum++;
						pnrResIron.setOutdoorNumber(StaticMethod.nullObject2String(row.getCell(18)));
						colNum++;
						pnrResIron.setPhaseNumber(StaticMethod.nullObject2String(row.getCell(19)));
						colNum++;
						pnrResIron.setPower(row.getCell(20).toString());
						colNum++;
						pnrResConfig.setRemarks(StaticMethod.nullObject2String(row.getCell(21)));
						colNum++;
						String uuid = UUIDHexGenerator.getInstance().getID();   //uuid作为字表的id
						pnrResConfig.setSubResTable("pnr_res_iron");
						pnrResIron.setId(uuid);
						pnrResConfig.setSubResId(uuid);
						mainList.add(pnrResConfig);
						mainList.add(pnrResIron);
					}else if("1122505".equals(specialty)){
						PnrResJieke pnrResJieke = new PnrResJieke();
						colNum++;
						pnrResJieke.setClientNumber(StaticMethod.nullObject2String(row.getCell(16)));
						colNum++;
						pnrResJieke.setClientType(StaticMethod.nullObject2String(row.getCell(17)));
						colNum++;
						if((row.getCell(18).getRichStringCellValue().toString()).trim().equals("")){
							throw new Exception();
						}
						pnrResJieke.setStationName(row.getCell(18).getRichStringCellValue().toString());
						colNum++;
						if((row.getCell(19).getRichStringCellValue().toString()).trim().equals("")){
							throw new Exception();
						}
						pnrResJieke.setStationNumber(row.getCell(19).getRichStringCellValue().toString());
						colNum++;
						pnrResConfig.setRemarks(StaticMethod.nullObject2String(row.getCell(19)));
						String uuid = UUIDHexGenerator.getInstance().getID();   //uuid作为字表的id
						pnrResConfig.setSubResTable("pnr_res_jieke");
						pnrResJieke.setId(uuid);
						pnrResConfig.setSubResId(uuid);
						mainList.add(pnrResConfig);
						mainList.add(pnrResJieke);
					}else if("1122506".equals(specialty)){
						colNum++;
						PnrResWlan pnrResWlan = new PnrResWlan();
						
						SimpleDateFormat dateformat =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						if((row.getCell(16).getRichStringCellValue().toString()).trim().equals("")){
							throw new Exception();
						}
						pnrResWlan.setAddress(row.getCell(16).getRichStringCellValue().toString());
						colNum++;
						String propertyTypeType = row.getCell(17).getRichStringCellValue().toString();
						if("共享".equals(propertyTypeType)){
							pnrResWlan.setPropertyType(1);
						}else if("自建".equals(propertyTypeType)){
							pnrResWlan.setPropertyType(2);
						}else if("租用".equals(propertyTypeType)){
							pnrResWlan.setPropertyType(3);
						}else {
							pnrResWlan.setPropertyType(4);
						}
						String tdOpenTime = "";
						colNum++;
						if(!StringUtils.isEmpty(StaticMethod.nullObject2String(row.getCell(18)))){
							tdOpenTime = dateformat.format(dateformat.parse(StaticMethod.nullObject2String(row.getCell(18))));
						}
						pnrResWlan.setTDOpenTime( tdOpenTime);
						colNum++;
						if((row.getCell(19).getRichStringCellValue().toString()).trim().equals("")){
							throw new Exception();
						}
						pnrResWlan.setNetType(row.getCell(19).getRichStringCellValue().toString());
						colNum++;
						String openTime="";
						if(!StringUtils.isEmpty(StaticMethod.nullObject2String(row.getCell(20)))){
							openTime = dateformat.format(dateformat.parse(StaticMethod.nullObject2String(row.getCell(20))));
						}
						pnrResWlan.setOpenTime(openTime);
						colNum++;
						//当是产权类型为共享时，才又共建共享
						if("共享".equals(propertyTypeType)){
							pnrResWlan.setSharing(row.getCell(21).getRichStringCellValue().toString());
						}
						colNum++;
						pnrResWlan.setAdjustmentMessage(StaticMethod.nullObject2String(row.getCell(22)));
						colNum++;
						pnrResConfig.setRemarks(StaticMethod.nullObject2String(row.getCell(23)));
						String uuid = UUIDHexGenerator.getInstance().getID();   //uuid作为字表的id
						pnrResConfig.setSubResTable("pnr_res_wlan");
						pnrResWlan.setId(uuid);
						pnrResConfig.setSubResId(uuid);
						mainList.add(pnrResConfig);
						mainList.add(pnrResWlan);
					}else if("1122507".equals(specialty)){
						PnrResFamilyBroadband pnrResFamilyBroadband = new PnrResFamilyBroadband();
						colNum++;
						pnrResFamilyBroadband.setClientNumber(StaticMethod.nullObject2String(row.getCell(16)));
						colNum++;
						pnrResFamilyBroadband.setClientType(StaticMethod.nullObject2String(row.getCell(17)));
						colNum++;
						if((row.getCell(18).getRichStringCellValue().toString()).trim().equals("")){
							throw new Exception();
						}
						pnrResFamilyBroadband.setStationName(row.getCell(18).getRichStringCellValue().toString());
						colNum++;
						if((row.getCell(19).getRichStringCellValue().toString()).trim().equals("")){
							throw new Exception();
						}
						pnrResFamilyBroadband.setStationNumber(row.getCell(19).getRichStringCellValue().toString());
						colNum++;
						pnrResConfig.setRemarks(StaticMethod.nullObject2String(row.getCell(19)));
						String uuid = UUIDHexGenerator.getInstance().getID();   //uuid作为字表的id
						pnrResConfig.setSubResTable("pnr_res_family_broadband");
						pnrResFamilyBroadband.setId(uuid);
						pnrResConfig.setSubResId(uuid);
						mainList.add(pnrResConfig);
						mainList.add(pnrResFamilyBroadband);
					}else{//此时用公共模板导入
						pnrResConfig.setRemarks(StaticMethod.nullObject2String(row.getCell(16)));
						temPlateFlag = "yes";
						mainList.add(pnrResConfig);
					}
				} catch (Exception e) {
					e.printStackTrace();
					colNum=colNum+1;
					throw new Exception(",错误列数是:"+colNum+e.getMessage());
				}
				return mainList;
			}
		});
		
		//在这里保存数据
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement stmt1 = null; 
		PreparedStatement stmt2 = null; 
		try {
			IEomsDao ed = (IEomsDao)ApplicationContextHolder.getInstance().getBean("eomsDao");
			//先判断导入的数据是否为空，如果为空值不插入
			if(mainList.size()>0){
				conn=ed.getCon();
				conn.setAutoCommit(false);
				//将这集合里的数据拆分成多个集合,以便一次插入的数据别太多
				List<List<Object>> mainlist1 = Lists.partition(mainList, 100);
				System.out.println("-------------------------mainList大小:"+mainList.size());
				System.out.println("--------------------------mainList1大小:"+mainlist1.size());
				List<List<Object>> sublist1 = Lists.partition(subList, 100);
				String insertMainSql = "insert into pnr_res_config (id, specialty,res_name , res_type,res_level ," +
				" res_longitude,res_latitude,sub_res_table ,sub_res_id,city ,country,  creator,create_time , street , company_name ,contact_name , phone,remarks,service_region,eographical_environment,region_type)"
				+" values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				String  insertSubSql = "";
				String subResTable = ((PnrResConfig) mainList.get(0)).getSubResTable();
				if("pnr_res_station".equals(subResTable)){
					insertSubSql = "insert into  pnr_res_station (id,address ,property_type,sharing ,net_type,g2_open_time,td_open_time ,adjustment_message) "
						+" values(?,?,?,?,?,?,?,?)";
				}else if("pnr_res_line".equals(subResTable)){
					insertSubSql = "insert into  pnr_res_line (id ,end_longitude,  end_latitude ,tube_side,single_routing ,like_routing)"
						+" values(?,?,?,?,?,?)";
				}else if("pnr_res_repeaters".equals(subResTable)){
					insertSubSql = "insert into  pnr_res_repeaters (id,address,property_type ,sharing ,net_type ,g2_open_time ,td_open_time ,adjustment_message )"
						+" values(?,?,?,?,?,?,?,?)";
				}else if("pnr_res_iron".equals(subResTable)){
					insertSubSql = "insert into  pnr_res_iron (id,iron_type ,indoor_number,outdoor_number, phase_number ,power ) "
						+" values(?,?,?,?,?,?)";
				}else if("pnr_res_jieke".equals(subResTable)){
					insertSubSql = "insert into  pnr_res_jieke (id,client_number,client_type,station_name,station_number ) "
						+" values(?,?,?,?,?)";
				}else if("pnr_res_wlan".equals(subResTable)){
					insertSubSql = "insert into  pnr_res_wlan (id,address,property_type ,sharing ,net_type ,g2_open_time ,td_open_time ,adjustment_message )"
						+" values(?,?,?,?,?,?,?,?)";
				}else if("pnr_res_family_broadband".equals(subResTable)){
					insertSubSql = "insert into  pnr_res_family_broadband (id,client_number,client_type,station_name,station_number ) "
						+" values(?,?,?,?,?)";
				}
				stmt1=conn.prepareStatement(insertMainSql);
				stmt2=conn.prepareStatement(insertSubSql);
				for(int i = 0;i<mainlist1.size();i++){
					if("yes".equals(temPlateFlag) || insertSubSql.equals("")){
						System.out.println("使用公共模块导入");
						pnrResConfigDaoJdbc.importData(mainlist1.get(i),new ArrayList<Object>(),stmt1,stmt2);
						if(i==mainlist1.size()-1){
							stmt1.executeBatch();
							stmt1.clearBatch();
						}
					}else{
						pnrResConfigDaoJdbc.importData(mainlist1.get(i),sublist1.get(i),stmt1,stmt2);
						if(i==mainlist1.size()-1){
							stmt1.executeBatch();
							stmt2.executeBatch();
							stmt1.clearBatch();
							stmt2.clearBatch();
						}
					}
				}
				conn.commit();
				conn.setAutoCommit(true);
			}
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
			if (stmt2!= null) {
				try {
					stmt2.close();
				} catch (Exception pe2) {
					pe2.printStackTrace();
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
	
	/**
	 * 资源导出
	 */
	@SuppressWarnings("deprecation")
	public void excelExport(String specialty,List<String> condiion,OutputStream toClient) {
		List<Map<String,Object>> list = pnrResConfigDaoJdbc.excelExport(specialty, condiion);
		HSSFWorkbook  workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet();
		sheet.setColumnWidth((short)0, (short)5000);   //设置列宽
		sheet.setColumnWidth((short)1, (short)5000);
		sheet.setColumnWidth((short)2, (short)5000);
		sheet.setColumnWidth((short)3, (short)5000);
		sheet.setColumnWidth((short)4, (short)5000);
		sheet.setColumnWidth((short)5, (short)5000);
		sheet.setColumnWidth((short)6, (short)5000);
		sheet.setColumnWidth((short)7, (short)5000);
		sheet.setColumnWidth((short)8, (short)5000);
		sheet.setColumnWidth((short)9, (short)5000);
		sheet.setColumnWidth((short)10, (short)5000);
		sheet.setColumnWidth((short)11, (short)5000);
		sheet.setColumnWidth((short)12, (short)5000);
		sheet.setColumnWidth((short)13, (short)5000);
		sheet.setColumnWidth((short)14, (short)5000);
		sheet.setColumnWidth((short)15, (short)5000);
		sheet.setColumnWidth((short)16, (short)5000);
		sheet.setColumnWidth((short)17, (short)5000);
		sheet.setColumnWidth((short)18, (short)5000);
		sheet.setColumnWidth((short)19, (short)5000);
		sheet.setColumnWidth((short)20, (short)5000);
		sheet.setColumnWidth((short)21, (short)5000);
		sheet.setColumnWidth((short)22, (short)5000);
		HSSFRow row = sheet.createRow(( short ) 0 );
		row.setHeight((short)500);    //设置行高
		
		HSSFFont font = workbook.createFont();
		font.setItalic(false); // 是否使用斜体
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setFont(font);
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平居中中
		
		HSSFFont font1 = workbook.createFont();
		font1.setFontHeightInPoints((short) 13); // 字体高度
		font1.setColor(HSSFFont.COLOR_NORMAL); // 字体颜色
		font1.setFontName("黑体"); // 字体
		font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); // 宽度
		font1.setItalic(false); // 是否使用斜体
		
		HSSFCellStyle cellStyle1 = workbook.createCellStyle();  //第一行的样式
		cellStyle1.setFont(font1);
		cellStyle1.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平居中中
		
		HSSFCell cell = row.createCell((short) 0);
		cell.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell.setCellValue("资源名称");
		cell.setCellStyle(cellStyle1);
		
		cell = row.createCell((short) 1);
		cell.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell.setCellValue("资源专业类型");
		cell.setCellStyle(cellStyle1);
		
		cell = row.createCell((short) 2);
		cell.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell.setCellValue("资源类别");
		cell.setCellStyle(cellStyle1);
		
		cell = row.createCell((short) 3);
		cell.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell.setCellValue("资源级别");
		cell.setCellStyle(cellStyle1);
		
		cell = row.createCell((short) 4);
		cell.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell.setCellValue("资源经度");
		cell.setCellStyle(cellStyle1);
		
		cell = row.createCell((short) 5);
		cell.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell.setCellValue("资源纬度");
		cell.setCellStyle(cellStyle1);
		
		cell = row.createCell((short) 6);
		cell.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell.setCellValue("地市");
		cell.setCellStyle(cellStyle1);
		
		cell = row.createCell((short) 7);
		cell.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell.setCellValue("区县");
		cell.setCellStyle(cellStyle1);
		
		cell = row.createCell((short) 8);
		cell.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell.setCellValue("地理环境");
		cell.setCellStyle(cellStyle1);
		
		cell = row.createCell((short) 9);
		cell.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell.setCellValue("区域类型");
		cell.setCellStyle(cellStyle1);
		
		cell = row.createCell((short) 10);
		cell.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell.setCellValue("乡镇/街道");
		cell.setCellStyle(cellStyle1);
		
		cell = row.createCell((short) 11);
		cell.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell.setCellValue("服务区域");
		cell.setCellStyle(cellStyle1);
		
		cell = row.createCell((short) 12);
		cell.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell.setCellValue("业主单位名称");
		cell.setCellStyle(cellStyle1);
		
		cell = row.createCell((short) 13);
		cell.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell.setCellValue("联系人");
		cell.setCellStyle(cellStyle1);
		
		cell = row.createCell((short) 14);
		cell.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell.setCellValue("业主联系电话");
		cell.setCellStyle(cellStyle1);
		
		if("1122501".equals(specialty)){

			cell = row.createCell((short) 15);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue("站址");
			cell.setCellStyle(cellStyle1);
			
			cell = row.createCell((short) 16);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue("产权类型");
			cell.setCellStyle(cellStyle1);
			
			cell = row.createCell((short) 17);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue("TD开通年月");
			cell.setCellStyle(cellStyle1);
			
			cell = row.createCell((short) 18);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue("网络类型");
			cell.setCellStyle(cellStyle1);
			
			cell = row.createCell((short) 19);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue("2G开通年月");
			cell.setCellStyle(cellStyle1);
			
			cell = row.createCell((short) 20);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue("共建共享");
			cell.setCellStyle(cellStyle1);
			
			cell = row.createCell((short) 21);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue("搬迁与配置调整情况");
			cell.setCellStyle(cellStyle1);
			
			cell = row.createCell((short) 22);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue("备注");
			cell.setCellStyle(cellStyle1);
			
			//一下是忘表格里面填数据
			for(int i=0;i<list.size();i++){
				row = sheet.createRow((int) i + 1);
				Map<String, Object> map = list.get(i);
				row.setHeight((short)400);
				mainExcel(cell, row, cellStyle, map);
				
				//开始填入子表的数据
				HSSFCell cell14 = row.createCell((short) 15);
				cell14.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell14.setCellStyle(cellStyle);
				cell14.setCellValue(Strings.nullToEmpty((String)map.get("address")));
				
				HSSFCell cell15 = row.createCell((short) 16);
				cell15.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell15.setCellStyle(cellStyle);
				
				if("1".equals(StaticMethod.nullObject2String(map.get("property_type")))){
					cell15.setCellValue("共享");
				}else if("2".equals(StaticMethod.nullObject2String(map.get("property_type")))){
					cell15.setCellValue("自建");
				}else if("3".equals(StaticMethod.nullObject2String(map.get("property_type")))){
					cell15.setCellValue("租用");
				}else{
					cell15.setCellValue("");
				}
				
				
				
				HSSFCell cell16 = row.createCell((short) 17);
				cell16.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell16.setCellStyle(cellStyle);
				cell16.setCellValue(Strings.nullToEmpty((String)map.get("td_open_time")));
				
				HSSFCell cell17 = row.createCell((short) 18);
				cell17.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell17.setCellStyle(cellStyle);
				cell17.setCellValue(Strings.nullToEmpty((String)map.get("net_type")));
				
				HSSFCell cell18 = row.createCell((short) 19);
				cell18.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell18.setCellStyle(cellStyle);
				cell18.setCellValue(Strings.nullToEmpty((String)map.get("g2_open_time")));
				
				HSSFCell cell19 = row.createCell((short) 20);
				cell19.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell19.setCellStyle(cellStyle);
				cell19.setCellValue(StaticMethod.nullObject2String(map.get("sharing")));
				
				HSSFCell cell20 = row.createCell((short) 21);
				cell20.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell20.setCellStyle(cellStyle);
				cell20.setCellValue(Strings.nullToEmpty((String)map.get("adjustment_message")));
				
				HSSFCell cell21 = row.createCell((short) 22);
				cell21.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell21.setCellStyle(cellStyle);
				cell21.setCellValue(Strings.nullToEmpty((String)map.get("remarks")));
				
			}
		}else if("1122502".equals(specialty)){
			cell = row.createCell((short) 15);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue("终点经度");
			cell.setCellStyle(cellStyle1);
			
			cell = row.createCell((short) 16);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue("终点纬度");
			cell.setCellStyle(cellStyle1);
			
			cell = row.createCell((short) 17);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue("管程/杆程");
			cell.setCellStyle(cellStyle1);
			
			cell = row.createCell((short) 18);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue("单路由光缆皮长");
			cell.setCellStyle(cellStyle1);
			
			cell = row.createCell((short) 19);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue("同路由光缆皮长");
			cell.setCellStyle(cellStyle1);
			
			cell = row.createCell((short) 20);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue("备注");
			cell.setCellStyle(cellStyle1);
			
			//一下是忘表格里面填数据
			for(int i=0;i<list.size();i++){
				row = sheet.createRow((int) i + 1);
				Map<String, Object> map = list.get(i);
				row.setHeight((short)400);
				mainExcel(cell, row, cellStyle, map);
				
				//开始填入子表的数据
				HSSFCell cell14 = row.createCell((short) 15);
				cell14.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell14.setCellStyle(cellStyle);
				cell14.setCellValue(map.get("end_longitude")+"");
				
				HSSFCell cell15 = row.createCell((short) 16);
				cell15.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell15.setCellStyle(cellStyle);
				cell15.setCellValue(map.get("end_latitude")+"");
				
				HSSFCell cell16 = row.createCell((short) 17);
				cell16.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell16.setCellStyle(cellStyle);
				cell16.setCellValue(Strings.nullToEmpty((String)map.get("tube_side")));
				
				HSSFCell cell17 = row.createCell((short) 18);
				cell17.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell17.setCellStyle(cellStyle);
				cell17.setCellValue(StaticMethod.nullObject2String(map.get("single_routing")));
				
				HSSFCell cell18 = row.createCell((short) 19);
				cell18.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell18.setCellStyle(cellStyle);
				cell18.setCellValue(Strings.nullToEmpty((String)map.get("like_routing")));
				
				HSSFCell cell19 = row.createCell((short) 20);
				cell19.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell19.setCellStyle(cellStyle);
				cell19.setCellValue(Strings.nullToEmpty((String)map.get("remarks")));
			}
		}else if("1122503".equals(specialty)){//直放站
			cell = row.createCell((short) 15);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue("站址");
			cell.setCellStyle(cellStyle1);
			
			cell = row.createCell((short) 16);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue("产权类型");
			cell.setCellStyle(cellStyle1);
			
			cell = row.createCell((short) 17);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue("TD开通年月");
			cell.setCellStyle(cellStyle1);
			
			cell = row.createCell((short) 18);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue("网络类型");
			cell.setCellStyle(cellStyle1);
			
			cell = row.createCell((short) 19);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue("2G开通年月");
			cell.setCellStyle(cellStyle1);
			
			cell = row.createCell((short) 20);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue("共建共享");
			cell.setCellStyle(cellStyle1);
			
			cell = row.createCell((short) 21);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue("搬迁与配置调整情况");
			cell.setCellStyle(cellStyle1);
			
			cell = row.createCell((short) 22);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue("备注");
			cell.setCellStyle(cellStyle1);
			
			//一下是忘表格里面填数据
			for(int i=0;i<list.size();i++){
				row = sheet.createRow((int) i + 1);
				Map<String, Object> map = list.get(i);
				row.setHeight((short)400);
				mainExcel(cell, row, cellStyle, map);
				
				//开始填入子表的数据
				HSSFCell cell14 = row.createCell((short) 15);
				cell14.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell14.setCellStyle(cellStyle);
				cell14.setCellValue(Strings.nullToEmpty((String)map.get("address")));
				
				HSSFCell cell15 = row.createCell((short) 16);
				cell15.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell15.setCellStyle(cellStyle);
				
				if("1".equals(StaticMethod.nullObject2String(map.get("property_type")))){
					cell15.setCellValue("共享");
				}else if("2".equals(StaticMethod.nullObject2String(map.get("property_type")))){
					cell15.setCellValue("自建");
				}else if("3".equals(StaticMethod.nullObject2String(map.get("property_type")))){
					cell15.setCellValue("租用");
				}else{
					cell15.setCellValue("");
				}
				
				
				
				HSSFCell cell16 = row.createCell((short) 17);
				cell16.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell16.setCellStyle(cellStyle);
				cell16.setCellValue(Strings.nullToEmpty((String)map.get("td_open_time")));
				
				HSSFCell cell17 = row.createCell((short) 18);
				cell17.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell17.setCellStyle(cellStyle);
				cell17.setCellValue(Strings.nullToEmpty((String)map.get("net_type")));
				
				HSSFCell cell18 = row.createCell((short) 19);
				cell18.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell18.setCellStyle(cellStyle);
				cell18.setCellValue(Strings.nullToEmpty((String)map.get("g2_open_time")));
				
				HSSFCell cell19 = row.createCell((short) 20);
				cell19.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell19.setCellStyle(cellStyle);
				cell19.setCellValue(StaticMethod.nullObject2String(map.get("sharing")));
				
				HSSFCell cell20 = row.createCell((short) 21);
				cell20.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell20.setCellStyle(cellStyle);
				cell20.setCellValue(Strings.nullToEmpty((String)map.get("adjustment_message")));
				
				HSSFCell cell21 = row.createCell((short) 22);
				cell21.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell21.setCellStyle(cellStyle);
				cell21.setCellValue(Strings.nullToEmpty((String)map.get("remarks")));
				
			}
		}else if("1122504".equals(specialty)){//铁搭及天馈
			cell = row.createCell((short) 15);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue("型号");
			cell.setCellStyle(cellStyle1);
			
			cell = row.createCell((short) 16);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue("室内机编号");
			cell.setCellStyle(cellStyle1);
			
			cell = row.createCell((short) 17);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue("室外机编号");
			cell.setCellStyle(cellStyle1);
			
			cell = row.createCell((short) 18);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue("相数");
			cell.setCellStyle(cellStyle1);
			
			cell = row.createCell((short) 19);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue("功率");
			cell.setCellStyle(cellStyle1);
			
			cell = row.createCell((short) 20);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue("备注");
			cell.setCellStyle(cellStyle1);
			
			//一下是忘表格里面填数据
			for(int i=0;i<list.size();i++){
				row = sheet.createRow((int) i + 1);
				Map<String, Object> map = list.get(i);
				row.setHeight((short)400);
				mainExcel(cell, row, cellStyle, map);
				
				//开始填入子表的数据
				HSSFCell cell14 = row.createCell((short) 15);
				cell14.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell14.setCellStyle(cellStyle);
				cell14.setCellValue(StaticMethod.nullObject2String(map.get("iron_type")));
				
				HSSFCell cell15 = row.createCell((short) 16);
				cell15.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell15.setCellStyle(cellStyle);
				cell15.setCellValue(StaticMethod.nullObject2String(map.get("indoor_number")));
				
				HSSFCell cell16 = row.createCell((short) 17);
				cell16.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell16.setCellStyle(cellStyle);
				cell16.setCellValue(Strings.nullToEmpty((String)map.get("outdoor_number")));
				
				HSSFCell cell17 = row.createCell((short) 18);
				cell17.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell17.setCellStyle(cellStyle);
				cell17.setCellValue(StaticMethod.nullObject2String(map.get("phase_number")));
				
				HSSFCell cell18 = row.createCell((short) 19);
				cell18.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell18.setCellStyle(cellStyle);
				cell18.setCellValue(Strings.nullToEmpty((String)map.get("power")));
				
				HSSFCell cell19 = row.createCell((short) 20);
				cell19.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell19.setCellStyle(cellStyle);
				cell19.setCellValue(Strings.nullToEmpty((String)map.get("remarks")));
				
			}
		}else if("1122505".equals(specialty)){//集客家客
			cell = row.createCell((short) 15);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue("客户数");
			cell.setCellStyle(cellStyle1);
			
			cell = row.createCell((short) 16);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue("业务类型");
			cell.setCellStyle(cellStyle1);
			
			cell = row.createCell((short) 17);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue("所属站点名称");
			cell.setCellStyle(cellStyle1);
			
			cell = row.createCell((short) 18);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue("所属站点编号");
			cell.setCellStyle(cellStyle1);
			
			cell = row.createCell((short) 19);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue("备注");
			cell.setCellStyle(cellStyle1);
			
			//一下是忘表格里面填数据
			for(int i=0;i<list.size();i++){
				row = sheet.createRow((int) i + 1);
				Map<String, Object> map = list.get(i);
				row.setHeight((short)400);
				mainExcel(cell, row, cellStyle, map);
				
				//开始填入子表的数据
				HSSFCell cell14 = row.createCell((short) 15);
				cell14.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell14.setCellStyle(cellStyle);
				cell14.setCellValue(StaticMethod.nullObject2String(map.get("client_number")));
				
				HSSFCell cell15 = row.createCell((short) 16);
				cell15.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell15.setCellStyle(cellStyle);
				String client_type = StaticMethod.nullObject2String(map.get("client_type"));
				if("--请选择--".equals(client_type)){
					cell15.setCellValue("");
				}else{
					cell15.setCellValue(StaticMethod.nullObject2String(map.get("client_type")));
				}
				
				HSSFCell cell16 = row.createCell((short) 17);
				cell16.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell16.setCellStyle(cellStyle);
				cell16.setCellValue(Strings.nullToEmpty((String)map.get("station_name")));
				
				HSSFCell cell17 = row.createCell((short) 18);
				cell17.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell17.setCellStyle(cellStyle);
				cell17.setCellValue(StaticMethod.nullObject2String(map.get("station_number")));
				
				HSSFCell cell18 = row.createCell((short) 19);
				cell18.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell18.setCellStyle(cellStyle);
				cell18.setCellValue(Strings.nullToEmpty((String)map.get("remarks")));
				
			}
		}else if("1122506".equals(specialty)){//WLAN

			cell = row.createCell((short) 15);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue("站址");
			cell.setCellStyle(cellStyle1);
			
			cell = row.createCell((short) 16);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue("产权类型");
			cell.setCellStyle(cellStyle1);
			
			cell = row.createCell((short) 17);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue("TD开通年月");
			cell.setCellStyle(cellStyle1);
			
			cell = row.createCell((short) 18);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue("网络类型");
			cell.setCellStyle(cellStyle1);
			
			cell = row.createCell((short) 19);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue("2G开通年月");
			cell.setCellStyle(cellStyle1);
			
			cell = row.createCell((short) 20);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue("共建共享");
			cell.setCellStyle(cellStyle1);
			
			cell = row.createCell((short) 21);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue("搬迁与配置调整情况");
			cell.setCellStyle(cellStyle1);
			
			cell = row.createCell((short) 22);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue("备注");
			cell.setCellStyle(cellStyle1);
			
			//一下是忘表格里面填数据
			for(int i=0;i<list.size();i++){
				row = sheet.createRow((int) i + 1);
				Map<String, Object> map = list.get(i);
				row.setHeight((short)400);
				mainExcel(cell, row, cellStyle, map);
				
				//开始填入子表的数据
				HSSFCell cell14 = row.createCell((short) 15);
				cell14.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell14.setCellStyle(cellStyle);
				cell14.setCellValue(Strings.nullToEmpty((String)map.get("address")));
				
				HSSFCell cell15 = row.createCell((short) 16);
				cell15.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell15.setCellStyle(cellStyle);
				
				if("1".equals(StaticMethod.nullObject2String(map.get("property_type")))){
					cell15.setCellValue("共享");
				}else if("2".equals(StaticMethod.nullObject2String(map.get("property_type")))){
					cell15.setCellValue("自建");
				}else if("3".equals(StaticMethod.nullObject2String(map.get("property_type")))){
					cell15.setCellValue("租用");
				}else{
					cell15.setCellValue("");
				}
				
				
				
				HSSFCell cell16 = row.createCell((short) 17);
				cell16.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell16.setCellStyle(cellStyle);
				cell16.setCellValue(Strings.nullToEmpty((String)map.get("td_open_time")));
				
				HSSFCell cell17 = row.createCell((short) 18);
				cell17.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell17.setCellStyle(cellStyle);
				cell17.setCellValue(Strings.nullToEmpty((String)map.get("net_type")));
				
				HSSFCell cell18 = row.createCell((short) 19);
				cell18.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell18.setCellStyle(cellStyle);
				cell18.setCellValue(Strings.nullToEmpty((String)map.get("g2_open_time")));
				
				HSSFCell cell19 = row.createCell((short) 20);
				cell19.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell19.setCellStyle(cellStyle);
				cell19.setCellValue(StaticMethod.nullObject2String(map.get("sharing")));
				
				HSSFCell cell20 = row.createCell((short) 21);
				cell20.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell20.setCellStyle(cellStyle);
				cell20.setCellValue(Strings.nullToEmpty((String)map.get("adjustment_message")));
				
				HSSFCell cell21 = row.createCell((short) 22);
				cell21.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell21.setCellStyle(cellStyle);
				cell21.setCellValue(Strings.nullToEmpty((String)map.get("remarks")));
				
			}
		}else if("1122507".equals(specialty)){ //家庭宽带
			cell = row.createCell((short) 15);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue("客户数");
			cell.setCellStyle(cellStyle1);
			
			cell = row.createCell((short) 16);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue("业务类型");
			cell.setCellStyle(cellStyle1);
			
			cell = row.createCell((short) 17);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue("所属站点名称");
			cell.setCellStyle(cellStyle1);
			
			cell = row.createCell((short) 18);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue("所属站点编号");
			cell.setCellStyle(cellStyle1);
			
			cell = row.createCell((short) 19);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue("备注");
			cell.setCellStyle(cellStyle1);
			
			//一下是忘表格里面填数据
			for(int i=0;i<list.size();i++){
				row = sheet.createRow((int) i + 1);
				Map<String, Object> map = list.get(i);
				row.setHeight((short)400);
				mainExcel(cell, row, cellStyle, map);
				
				//开始填入子表的数据
				HSSFCell cell14 = row.createCell((short) 15);
				cell14.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell14.setCellStyle(cellStyle);
				cell14.setCellValue(StaticMethod.nullObject2String(map.get("client_number")));
				
				HSSFCell cell15 = row.createCell((short) 16);
				cell15.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell15.setCellStyle(cellStyle);
				String client_type = StaticMethod.nullObject2String(map.get("client_type"));
				if("--请选择--".equals(client_type)){
					cell15.setCellValue("");
				}else{
					cell15.setCellValue(StaticMethod.nullObject2String(map.get("client_type")));
				}
				
				HSSFCell cell16 = row.createCell((short) 17);
				cell16.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell16.setCellStyle(cellStyle);
				cell16.setCellValue(Strings.nullToEmpty((String)map.get("station_name")));
				
				HSSFCell cell17 = row.createCell((short) 18);
				cell17.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell17.setCellStyle(cellStyle);
				cell17.setCellValue(StaticMethod.nullObject2String(map.get("station_number")));
				
				HSSFCell cell18 = row.createCell((short) 19);
				cell18.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell18.setCellStyle(cellStyle);
				cell18.setCellValue(Strings.nullToEmpty((String)map.get("remarks")));
				
			}
		}else{//此时只有主表的数据

			cell = row.createCell((short) 15);
			cell.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell.setCellValue("备注");
			cell.setCellStyle(cellStyle1);
			
			//一下是忘表格里面填数据
			for(int i=0;i<list.size();i++){
				row = sheet.createRow((int) i + 1);
				Map<String, Object> map = list.get(i);
				row.setHeight((short)400);
				mainExcel(cell, row, cellStyle, map);
			}
		}
		
		try {
			//将工作簿也流的方式打印出去
			workbook.write(toClient);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * 设置主表的那一部分
	 */
	@SuppressWarnings("deprecation")
	private void mainExcel(HSSFCell cell,HSSFRow row,HSSFCellStyle cellStyle,Map<String, Object> map){
		
		//地市区县 id2name的dao
		ID2NameDAO id2namedao = (ID2NameDAO) ApplicationContextHolder
		.getInstance().getBean("tawSystemAreaDao");
		
		ITawSystemDictTypeManager dictManager = (ITawSystemDictTypeManager) ApplicationContextHolder
		.getInstance().getBean("ItawSystemDictTypeManager");
		
		HSSFCell cell1 = row.createCell((short) 0);
		cell1.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell1.setCellStyle(cellStyle);
		cell1.setCellValue(Strings.nullToEmpty((String)map.get("res_name")));
		
		try {
			HSSFCell cell2 = row.createCell((short) 1);
			cell2.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell2.setCellStyle(cellStyle);
			cell2.setCellValue(dictManager.id2Name( Strings.nullToEmpty((String)map.get("specialty"))));
		
		
			HSSFCell cell3 = row.createCell((short) 2);
			cell3.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell3.setCellStyle(cellStyle);
			cell3.setCellValue(dictManager.id2Name(Strings.nullToEmpty((String)map.get("res_type"))));
			
			HSSFCell cell4 = row.createCell((short) 3);
			cell4.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell4.setCellStyle(cellStyle);
			cell4.setCellValue(dictManager.id2Name(Strings.nullToEmpty((String)map.get("res_level"))));
		} catch (DictDAOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		HSSFCell cell5 = row.createCell((short) 4);
		cell5.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell5.setCellStyle(cellStyle);
		cell5.setCellValue(StaticMethod.nullObject2String(map.get("res_longitude")));
		
		HSSFCell cell6 = row.createCell((short) 5);
		cell6.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell6.setCellStyle(cellStyle);
		cell6.setCellValue(StaticMethod.nullObject2String(map.get("res_latitude")));
		
		HSSFCell cell7 = row.createCell((short) 6);
		cell7.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell7.setCellStyle(cellStyle);
		try {
			cell7.setCellValue(id2namedao.id2Name(Strings.nullToEmpty((String)map.get("city"))));
		} catch (DictDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		HSSFCell cell8 = row.createCell((short) 7);
		cell8.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell8.setCellStyle(cellStyle);
		try {
			cell8.setCellValue(id2namedao.id2Name(Strings.nullToEmpty((String)map.get("country"))));
			
			HSSFCell cell9 = row.createCell((short) 8);
			cell9.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell9.setCellStyle(cellStyle);
			cell9.setCellValue(dictManager.id2Name( Strings.nullToEmpty((String)map.get("eographical_environment"))));
			
			HSSFCell cell10 = row.createCell((short) 9);
			cell10.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell10.setCellStyle(cellStyle);
			cell10.setCellValue(dictManager.id2Name( Strings.nullToEmpty((String)map.get("region_type"))));
			
		} catch (DictDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		HSSFCell cell9 = row.createCell((short) 10);
		cell9.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell9.setCellStyle(cellStyle);
		cell9.setCellValue(Strings.nullToEmpty((String)map.get("street")));

		
		HSSFCell cell10 = row.createCell((short) 11);
		cell10.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell10.setCellStyle(cellStyle);
		cell10.setCellValue(Strings.nullToEmpty((String)map.get("service_region")));
		
		HSSFCell cell11 = row.createCell((short) 12);
		cell11.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell11.setCellStyle(cellStyle);
		cell11.setCellValue(Strings.nullToEmpty((String)map.get("company_name")));
		
		HSSFCell cell12 = row.createCell((short) 13);
		cell12.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell12.setCellStyle(cellStyle);
		cell12.setCellValue(Strings.nullToEmpty((String)map.get("contact_name")));
		
		HSSFCell cell13 = row.createCell((short) 14);
		cell13.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell13.setCellStyle(cellStyle);
		cell13.setCellValue(Strings.nullToEmpty((String)map.get("phone")));
	}
	
	/**
	 * 查询未分配周期和执行对象的资源数
	 */
	@SuppressWarnings("unchecked")
	public List findUnAssignCycleAndExecuteObject(String whereSql){
		return pnrResConfigDaoJdbc.findUnAssignCycleAndExecuteObject(whereSql);
	}
	public List<PnrResConfig> getPnrResConfigByResId(String subResId) {
		// TODO Auto-generated method stub
		List<PnrResConfig>  list =null;
		StringBuffer sql = new StringBuffer();
		sql.append("select new PnrResConfig(p.id,p.resName,p.subResTable,p.subResId) from PnrResConfig p where p.subResId='").append(subResId).append("'");
		list=pnrResConfigDao.findByHql(sql.toString());
		return list;
	}
	
	public IPnrResConfigDaoJdbc getPnrResConfigDaoJdbc() {
		return pnrResConfigDaoJdbc;
	}

	public void setPnrResConfigDaoJdbc(IPnrResConfigDaoJdbc pnrResConfigDaoJdbc) {
		this.pnrResConfigDaoJdbc = pnrResConfigDaoJdbc;
	}
	
	/**
	 * 查询资源总条数
	 */
	public Map<String,Object> totalNumber(Map<String,String> param){
		int total=this.getTotalNumber(param);
		//返回结果
		Map<String,Object> resultMap=new HashMap<String,Object>();
		resultMap.put("total", total);
		return resultMap;
	}
	
	/**
	 * 同步资源清查数据
	 */
	public Map<String,Object> collectResourceInventoryData(Map<String,String> param){
		//1.读取资源库中的机房数据
		List<JkXcwhRoom> jkXcwhRoomList = this.getResourceInventoryList(param);
		//2.将采集到的全部数据放入临时表中pnr_resource_inventory_temp
		this.copyJkXcwhRoomToPnrInventoryRoomTemp(jkXcwhRoomList);
		
		//3.去重
		List<JkXcwhRoom> pnrInventoryRoomTempList = this.getPnrInventoryRoomTempList(param);
		//4.取出重复的数据
		List<JkXcwhRoom> repetitiveInventoryRoomList=this.getRepetitiveInventoryRoomList(param);
		
		//5.将资源数据保存
		int total=0;//计数器
		total = this.insertResourceInventoryData(pnrInventoryRoomTempList,param);
		//6.将重复数据保存到备份表中
		this.insertRepetitiveInventoryRoom(repetitiveInventoryRoomList);
		
		//返回结果
		Map<String,Object> resultMap=new HashMap<String,Object>();
		resultMap.put("total", total);
		
		return resultMap;
	}
	
	/**
	 * 	 查询资源总条数
	 	 * @author WangJun
	 	 * @title: getTotalNumber
	 	 * @date Nov 24, 2015 3:04:48 PM
	 	 * @param param
	 	 * @return List<JkXcwhRoom>
	 */
	private int getTotalNumber(Map<String,String> param){
		String cityIds = param.get("cityIds");
		Connection connection = null;
		PreparedStatement pstmtTotal = null;
		ResultSet executeQuery=null;
		int total = 0;
		try {
			connection = ResourceSystemDBUtil.openConnection();
			//String sql = "select count(room_id) as total from jk_xcwh_room_temp where city_id in ("+cityIds+")";
			//资源数据库
			String sql = "select count(room_id) as total from zygl.v_s_room_hc_olt where city_id in ("+cityIds+")";
			System.out.println("------查询资源总条数sql="+sql);
		   pstmtTotal = connection.prepareStatement(sql); 
		   executeQuery = pstmtTotal.executeQuery(); 
		   if(executeQuery.next()){
			   total = executeQuery.getInt(1); 
		   }
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			if(executeQuery!=null){
				try {
					executeQuery.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			if(pstmtTotal!=null){
				try {
					pstmtTotal.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return total;
	}
	
	
	/**
	 * 	 从资源库取资源清查的机房数据
	 	 * @author WangJun
	 	 * @title: getResourceInventoryList
	 	 * @date Nov 24, 2015 3:04:48 PM
	 	 * @param param
	 	 * @return List<JkXcwhRoom>
	 */
	private List<JkXcwhRoom> getResourceInventoryList(Map<String,String> param){
		String limitedNumber = param.get("limitedNumber");
		String cityIds = param.get("cityIds");
		Connection connection = null;
		List<JkXcwhRoom> jkXcwhRoomList = null;
		try {
			connection = ResourceSystemDBUtil.openConnection();
//			String sql = 
//				"SELECT ROOM_ID       as roomId," + 
//				"       ROOM_NAME     as roomName," + 
//				"       BUREAU_ID     as bureauId," + 
//				"       BUREAU_NAME   as bureauName," + 
//				"       AREA_ID       as areaId," + 
//				"       AREA_NAME     as areaName," + 
//				"       CITY_ID       as cityId," + 
//				"       CITY_NAME     as cityName," + 
//				"       ROOM_POSITION as roomPosition," + 
//				"       JT_GRADE      as jtGrade," + 
//				"       POSITION_TYPE as positionType," + 
//				"       ROOM_TYPE     as roomType," + 
//				"       JT_CLASS      as jtClass," + 
//				"       ATTR_1        as attrOne" + 
//				"  FROM JK_XCWH_ROOM_TEMP where city_id in ("+cityIds+") and rownum <="+limitedNumber;
			
			
			//资源数据库
			String sql=
				"select room_id       as roomid," +
				"       room_name     as roomname," + 
				"       bureau_id     as bureauid," + 
				"       bureau_name   as bureauname," + 
				"       area_id       as areaid," + 
				"       area_name     as areaname," + 
				"       city_id       as cityid," + 
				"       city_name     as cityname," + 
				"       room_position as roomposition," + 
				"       jt_grade      as jtgrade," + 
				"       position_type as positiontype," + 
				"       room_type     as roomtype," + 
				"       jt_class      as jtclass," + 
				"       attr_1        as attrone" + 
				"  from zygl.v_s_room_hc_olt where city_id in ("+cityIds+") and rownum <="+limitedNumber;
			System.out.println("------在资源库查询的数据sql="+sql);
			jkXcwhRoomList = ResourceSystemDBUtil.queryBeanList(connection, sql,
					JkXcwhRoom.class);
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return jkXcwhRoomList;
	}
	
	/**
	 * 	 把从资源库采集的资源清查的机房的数据，先放到现场维护系统一个临时表里面	
	 	 * @author WangJun
	 	 * @title: copyJkXcwhRoomToPnrInventoryRoomTemp
	 	 * @date Nov 27, 2015 3:46:36 PM
	 	 * @param jkXcwhRoomList void
	 */
	private void copyJkXcwhRoomToPnrInventoryRoomTemp(List<JkXcwhRoom> jkXcwhRoomList){
		if(jkXcwhRoomList.size()>0){
			//先清空临时表里的数据
			this.truncatePnrInventoryRoomTempTable();
			
			int listSize=jkXcwhRoomList.size();
			IEomsDao ed = (IEomsDao)ApplicationContextHolder.getInstance().getBean("eomsDao");
			Connection con =null;
			PreparedStatement ps = null; 
			try {
				con = ed.getCon();
				con.setAutoCommit(false);// 关闭自动提交
				String sql ="insert into pnr_resource_inventory_temp(ROOM_ID,Room_Name,Bureau_ID,Bureau_Name,AREA_ID,AREA_NAME,City_ID,City_Name,room_position,jt_grade,position_type,room_type,jt_class,attr_1) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				ps = con.prepareStatement(sql);
				for (int i = 0; i <listSize; i++) {
					ps.setString(1,jkXcwhRoomList.get(i).getRoomId());
					ps.setString(2,jkXcwhRoomList.get(i).getRoomName());
					ps.setString(3,jkXcwhRoomList.get(i).getBureauId());
					ps.setString(4,jkXcwhRoomList.get(i).getBureauName());
					ps.setString(5,jkXcwhRoomList.get(i).getAreaId());
					ps.setString(6,jkXcwhRoomList.get(i).getAreaName());
					ps.setString(7,jkXcwhRoomList.get(i).getCityId());
					ps.setString(8,jkXcwhRoomList.get(i).getCityName());
					ps.setString(9,jkXcwhRoomList.get(i).getRoomPosition());
					ps.setString(10,jkXcwhRoomList.get(i).getJtGrade());
					ps.setString(11,jkXcwhRoomList.get(i).getPositionType());
					ps.setString(12,jkXcwhRoomList.get(i).getRoomType());
					ps.setString(13,jkXcwhRoomList.get(i).getJtClass());
					ps.setString(14,jkXcwhRoomList.get(i).getAttrOne());
					ps.addBatch();
					if (i% 100 == 0) {
						ps.executeBatch();
						con.commit();
						//total=total+100;
						ps.clearBatch();
					}
				}
				// 最后插入不足100条的数据
				ps.executeBatch();
				//System.out.println("executeBatch2="+executeBatch2);
				con.commit();	
				//total=total+executeBatch2.length;
			} catch (Exception e) {
				e.printStackTrace();
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}finally {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 	 把重复的数据插入到日志表中
	 	 * @author WangJun
	 	 * @title: copyJkXcwhRoomToPnrInventoryRoomTemp
	 	 * @date Nov 27, 2015 3:46:36 PM
	 	 * @param jkXcwhRoomList void
	 */
	private void insertRepetitiveInventoryRoom(List<JkXcwhRoom> jkXcwhRoomList){
		if(jkXcwhRoomList.size()>0){
			int listSize=jkXcwhRoomList.size();
			IEomsDao ed = (IEomsDao)ApplicationContextHolder.getInstance().getBean("eomsDao");
			Connection con =null;
			PreparedStatement ps = null; 
			SimpleDateFormat dateformat =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String nowTime = dateformat.format(new Date());
			try {
				con = ed.getCon();
				con.setAutoCommit(false);// 关闭自动提交
				String sql ="insert into pnr_resource_inventory_log(ROOM_ID,Room_Name,Bureau_ID,Bureau_Name,AREA_ID,AREA_NAME,City_ID,City_Name,room_position,jt_grade,position_type,room_type,jt_class,attr_1,insert_time,remark) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				ps = con.prepareStatement(sql);
				for (int i = 0; i <listSize; i++) {
					ps.setString(1,jkXcwhRoomList.get(i).getRoomId());
					ps.setString(2,jkXcwhRoomList.get(i).getRoomName());
					ps.setString(3,jkXcwhRoomList.get(i).getBureauId());
					ps.setString(4,jkXcwhRoomList.get(i).getBureauName());
					ps.setString(5,jkXcwhRoomList.get(i).getAreaId());
					ps.setString(6,jkXcwhRoomList.get(i).getAreaName());
					ps.setString(7,jkXcwhRoomList.get(i).getCityId());
					ps.setString(8,jkXcwhRoomList.get(i).getCityName());
					ps.setString(9,jkXcwhRoomList.get(i).getRoomPosition());
					ps.setString(10,jkXcwhRoomList.get(i).getJtGrade());
					ps.setString(11,jkXcwhRoomList.get(i).getPositionType());
					ps.setString(12,jkXcwhRoomList.get(i).getRoomType());
					ps.setString(13,jkXcwhRoomList.get(i).getJtClass());
					ps.setString(14,jkXcwhRoomList.get(i).getAttrOne());
					ps.setString(15,nowTime);
					ps.setString(16,"重复");
					ps.addBatch();
					if (i% 100 == 0) {
						ps.executeBatch();
						con.commit();
						//total=total+100;
						ps.clearBatch();
					}
				}
				// 最后插入不足100条的数据
				ps.executeBatch();
				//System.out.println("executeBatch2="+executeBatch2);
				con.commit();	
				//total=total+executeBatch2.length;
			} catch (Exception e) {
				e.printStackTrace();
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}finally {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	/**
	 * 	 查询不重复的机房数据
	 	 * @author WangJun
	 	 * @title: getPnrInventoryRoomTempList
	 	 * @date Dec 7, 2015 3:28:36 PM
	 	 * @param param
	 	 * @return List<JkXcwhRoom>
	 */
	private List<JkXcwhRoom> getPnrInventoryRoomTempList(Map<String,String> param){
		List<JkXcwhRoom> list= new ArrayList<JkXcwhRoom>();
		IEomsDao ed = (IEomsDao)ApplicationContextHolder.getInstance().getBean("eomsDao");
		Connection con=null;
	    PreparedStatement ps=null;
	    ResultSet rs=null;
//	    String sql=
//	    	"select p.room_id," +
//	    	"       p.room_name," + 
//	    	"       p.bureau_id," + 
//	    	"       p.bureau_name," + 
//	    	"       p.area_id," + 
//	    	"       p.area_name," + 
//	    	"       p.city_id," + 
//	    	"       p.city_name," + 
//	    	"       p.room_position," + 
//	    	"       p.jt_grade," + 
//	    	"       p.position_type," + 
//	    	"       p.room_type," + 
//	    	"       p.jt_class," + 
//	    	"       p.attr_1" + 
//	    	"  from pnr_resource_inventory_temp p" + 
//	    	" where not exists (select r.id" + 
//	    	"          from pnr_resource_inventory_room r" + 
//	    	"         where r.id = p.room_id)";
	    
	    String sql=
	    	"select p.room_id," +
	    	"       p.room_name," + 
	    	"       p.bureau_id," + 
	    	"       p.bureau_name," + 
	    	"       p.area_id," + 
	    	"       p.area_name," + 
	    	"       p.city_id," + 
	    	"       p.city_name," + 
	    	"       p.room_position," + 
	    	"       p.jt_grade," + 
	    	"       p.position_type," + 
	    	"       p.room_type," + 
	    	"       p.jt_class," + 
	    	"       p.attr_1," + 
	    	"       zy.city_id as city_id_new," + 
	    	"       zy.city as city_name_new," + 
	    	"       zy.country_id as country_id_new," + 
	    	"       zy.country as country_name_new" + 
	    	"  from pnr_resource_inventory_temp p, zyqc_city zy" + 
	    	" where p.city_id = zy.ny_city_id(+)" + 
	    	"   and p.area_id = zy.ny_country_id(+)" + 
	    	" and not exists (select r.id" + 
	    	"          from pnr_resource_inventory_room r" + 
	    	"         where r.id = p.room_id)";
	    
	    System.out.println("---------去重="+sql);
	    try {
			con = ed.getCon();
			ps=con.prepareStatement(sql);     
			rs=ps.executeQuery();
			while(rs.next()){
				JkXcwhRoom jkXcwhRoom= new JkXcwhRoom();
				jkXcwhRoom.setRoomId(rs.getString("ROOM_ID"));
				jkXcwhRoom.setRoomName(rs.getString("ROOM_NAME"));
				jkXcwhRoom.setBureauId(rs.getString("BUREAU_ID"));
				jkXcwhRoom.setBureauName(rs.getString("BUREAU_NAME"));
				jkXcwhRoom.setAreaId(rs.getString("AREA_ID"));
				jkXcwhRoom.setAreaName(rs.getString("AREA_NAME"));
				jkXcwhRoom.setCityId(rs.getString("CITY_ID"));
				jkXcwhRoom.setCityName(rs.getString("CITY_NAME"));
				jkXcwhRoom.setRoomPosition(rs.getString("ROOM_POSITION"));
				jkXcwhRoom.setJtGrade(rs.getString("JT_GRADE"));
				jkXcwhRoom.setPositionType(rs.getString("POSITION_TYPE"));
				jkXcwhRoom.setRoomType(rs.getString("ROOM_TYPE"));
				jkXcwhRoom.setJtClass(rs.getString("JT_CLASS"));
				jkXcwhRoom.setAttrOne(rs.getString("ATTR_1"));
				jkXcwhRoom.setCityIdNew(rs.getString("city_id_new"));
				jkXcwhRoom.setCityNameNew(rs.getString("city_name_new"));
				jkXcwhRoom.setCountryIdNew(rs.getString("country_id_new"));
				jkXcwhRoom.setCountryNameNew(rs.getString("country_name_new"));
		        list.add(jkXcwhRoom);
		    }
		}  catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return list;
	}
	
	/**
	 * 	 查询重复的机房数据
	 	 * @author WangJun
	 	 * @title: getPnrInventoryRoomTempList
	 	 * @date Dec 7, 2015 3:28:36 PM
	 	 * @param param
	 	 * @return List<JkXcwhRoom>
	 */
	private List<JkXcwhRoom> getRepetitiveInventoryRoomList(Map<String,String> param){
		List<JkXcwhRoom> list= new ArrayList<JkXcwhRoom>();
		IEomsDao ed = (IEomsDao)ApplicationContextHolder.getInstance().getBean("eomsDao");
		Connection con=null;
	    PreparedStatement ps=null;
	    ResultSet rs=null;
	    String sql=
	    	"select p.room_id," +
	    	"       p.room_name," + 
	    	"       p.bureau_id," + 
	    	"       p.bureau_name," + 
	    	"       p.area_id," + 
	    	"       p.area_name," + 
	    	"       p.city_id," + 
	    	"       p.city_name," + 
	    	"       p.room_position," + 
	    	"       p.jt_grade," + 
	    	"       p.position_type," + 
	    	"       p.room_type," + 
	    	"       p.jt_class," + 
	    	"       p.attr_1" + 
	    	"  from pnr_resource_inventory_temp p" + 
	    	" where exists (select r.id" + 
	    	"          from pnr_resource_inventory_room r" + 
	    	"         where r.id = p.room_id)";
	    System.out.println("---------重复的数据="+sql);
	    try {
			con = ed.getCon();
			ps=con.prepareStatement(sql);     
			rs=ps.executeQuery();
			while(rs.next()){
				JkXcwhRoom jkXcwhRoom= new JkXcwhRoom();
				jkXcwhRoom.setRoomId(rs.getString("ROOM_ID"));
				jkXcwhRoom.setRoomName(rs.getString("ROOM_NAME"));
				jkXcwhRoom.setBureauId(rs.getString("BUREAU_ID"));
				jkXcwhRoom.setBureauName(rs.getString("BUREAU_NAME"));
				jkXcwhRoom.setAreaId(rs.getString("AREA_ID"));
				jkXcwhRoom.setAreaName(rs.getString("AREA_NAME"));
				jkXcwhRoom.setCityId(rs.getString("CITY_ID"));
				jkXcwhRoom.setCityName(rs.getString("CITY_NAME"));
				jkXcwhRoom.setRoomPosition(rs.getString("ROOM_POSITION"));
				jkXcwhRoom.setJtGrade(rs.getString("JT_GRADE"));
				jkXcwhRoom.setPositionType(rs.getString("POSITION_TYPE"));
				jkXcwhRoom.setRoomType(rs.getString("ROOM_TYPE"));
				jkXcwhRoom.setJtClass(rs.getString("JT_CLASS"));
				jkXcwhRoom.setAttrOne(rs.getString("ATTR_1"));
		        list.add(jkXcwhRoom);
		    }
		}  catch (Exception e) {
			e.printStackTrace();
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}finally {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return list;
	}
	
	
	/**
	 * 	 往现场维护表中插入资源清查数据
	 	 * @author WangJun
	 	 * @title: insertResourceInventoryData
	 	 * @date Nov 27, 2015 11:08:06 AM
	 	 * @param jkXcwhRoomList
	 	 * @return int
	 */
	private int insertResourceInventoryData(List<JkXcwhRoom> jkXcwhRoomList,Map<String,String> param){
		int total=0;
		if(jkXcwhRoomList.size()>0){
			IEomsDao ed = (IEomsDao)ApplicationContextHolder.getInstance().getBean("eomsDao");
			Connection con =null;
			PreparedStatement ps1 = null; 
			PreparedStatement ps2 = null; 
			String sql1 =
				"insert into pnr_res_config" +
				"  (id," + 
				"   res_name," + 
				"   specialty," + 
				"   res_type," + 
				"   res_level," + 
				"   inspect_cycle," + 
				"   res_longitude," + 
				"   res_latitude," + 
				"   sub_res_table," + 
				"   sub_res_id," + 
				"   city," + 
				"   country," + 
				"   creator," + 
				"   create_time," + 
				"   service_region)" + 
				"values\n" + 
				"  (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

			String sql2 ="insert into pnr_resource_inventory_room(id,Room_Name,Bureau_ID,Bureau_Name,AREA_ID,AREA_NAME,City_ID,City_Name,room_position,jt_grade,position_type,room_type,jt_class,attr_1,city_id_new,city_name_new,country_id_new,country_name_new) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			//巡检周期
			String week="";
			week=PartnerCityByUser.getWeekTimeByPnrId("11225110104");
			if(!"".equals(week)){
				String[]a = week.split(",");
				if(a!=null){
					if(a.length>2){
					      week=a[2];
					}
				}
			}
			//日期格式化
			SimpleDateFormat dateformat =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			try {
				con = ed.getCon();
				con.setAutoCommit(false);// 关闭自动提交
				ps1 = con.prepareStatement(sql1);
				ps2 = con.prepareStatement(sql2);
				for(int i=0;i<jkXcwhRoomList.size();i++){
					ps1.setString(1,PnrResConfigMgrImpl.getUUID());
					ps1.setString(2,jkXcwhRoomList.get(i).getRoomName());
					ps1.setString(3,"1122511");
					ps1.setString(4,"112251101");
					ps1.setString(5,"11225110104");//默认为C类
					ps1.setString(6,week);
					ps1.setDouble(7,new Double("0"));//经度 默认为0
					ps1.setDouble(8,new Double("0"));//纬度 默认为0
					ps1.setString(9,"pnr_resource_inventory_room");
					ps1.setString(10,jkXcwhRoomList.get(i).getRoomId());
					//ps1.setString(11,jkXcwhRoomList.get(i).getCityId());
					//ps1.setString(12,jkXcwhRoomList.get(i).getAreaId());
					ps1.setString(11,jkXcwhRoomList.get(i).getCityIdNew());
					ps1.setString(12,jkXcwhRoomList.get(i).getCountryIdNew());
					ps1.setString(13,param.get("userId"));//创建人
					ps1.setString(14,dateformat.format(new Date()));//创建时间
					ps1.setString(15,jkXcwhRoomList.get(i).getBureauName());
					ps1.addBatch();

					ps2.setString(1,jkXcwhRoomList.get(i).getRoomId());
					ps2.setString(2,jkXcwhRoomList.get(i).getRoomName());
					ps2.setString(3,jkXcwhRoomList.get(i).getBureauId());
					ps2.setString(4,jkXcwhRoomList.get(i).getBureauName());
					ps2.setString(5,jkXcwhRoomList.get(i).getAreaId());
					ps2.setString(6,jkXcwhRoomList.get(i).getAreaName());
					ps2.setString(7,jkXcwhRoomList.get(i).getCityId());
					ps2.setString(8,jkXcwhRoomList.get(i).getCityName());
					ps2.setString(9,jkXcwhRoomList.get(i).getRoomPosition());
					ps2.setString(10,jkXcwhRoomList.get(i).getJtGrade());
					ps2.setString(11,jkXcwhRoomList.get(i).getPositionType());
					ps2.setString(12,jkXcwhRoomList.get(i).getRoomType());
					ps2.setString(13,jkXcwhRoomList.get(i).getJtClass());
					ps2.setString(14,jkXcwhRoomList.get(i).getAttrOne());
					ps2.setString(15,jkXcwhRoomList.get(i).getCityIdNew());
					ps2.setString(16,jkXcwhRoomList.get(i).getCityNameNew());
					ps2.setString(17,jkXcwhRoomList.get(i).getCountryIdNew());
					ps2.setString(18,jkXcwhRoomList.get(i).getCountryNameNew());
					ps2.addBatch();
					
					if (i % 100 == 0) {
						int[] executeBatch = ps1.executeBatch();
						ps2.executeBatch();
						con.commit();
						total=total+executeBatch.length;
						ps1.clearBatch();
						ps2.clearBatch();
					}
				}
				
				// 最后插入不足100条的数据
			    int[] executeBatch2 = ps1.executeBatch();
				ps2.executeBatch();
				System.out.println("executeBatch2="+executeBatch2);
				System.out.println("size="+executeBatch2.length);
				con.commit();	
				total=total+executeBatch2.length;
			}  catch (Exception e) {
				e.printStackTrace();
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}finally {
				try {
					ps1.close();
					ps2.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		//插入采集成功条数记录的日志表中
		PnrResourceSuccessLog successLog=new PnrResourceSuccessLog();
		successLog.setUserId(param.get("userId"));
		successLog.setInsertTime(new Date());
		successLog.setCityNames(param.get("cityNames"));
		successLog.setInsertSuccessNum(Integer.toString(total));
		successLog.setZyglQueryNum(param.get("zyglTotalNumber"));
		this.savePnrResourceSuccessLog(successLog);
		
		return total;
	}
	
	/**
	 * 往插入成功的条数的日志表中保存数据
	 */
	public void savePnrResourceSuccessLog(PnrResourceSuccessLog pnrResourceSuccessLog){
		IPnrResourceSuccessLogService pnrResourceSuccessLogService = (IPnrResourceSuccessLogService) ApplicationContextHolder.getInstance().getBean("pnrResourceSuccessLogService");
		pnrResourceSuccessLogService.save(pnrResourceSuccessLog);
	}
	
	/**
	 * 	 返回uuid
	 	 * @author WangJun
	 	 * @title: getUUID
	 	 * @date Nov 25, 2015 8:51:38 AM
	 	 * @return String
	 */
    public static String getUUID(){  
        String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");  
        return uuid;  
    }
    
    private void truncatePnrInventoryRoomTempTable() {
    	IEomsDao ed = (IEomsDao)ApplicationContextHolder.getInstance().getBean("eomsDao");
		Connection connection = null;
		Statement stmt = null;
		try {
			connection = ed.getCon();
			String sql = "truncate table pnr_resource_inventory_temp";
			stmt = connection.createStatement();
			stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}