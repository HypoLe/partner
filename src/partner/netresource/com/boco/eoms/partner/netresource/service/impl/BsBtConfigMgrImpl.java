package com.boco.eoms.partner.netresource.service.impl;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
import com.boco.eoms.partner.deviceInspect.model.PnrInspectLink;
import com.boco.eoms.partner.deviceInspect.model.PnrInspectMapping;
import com.boco.eoms.partner.deviceInspect.service.PnrInspectLinkService;
import com.boco.eoms.partner.deviceInspect.service.PnrInspectMappingService;
import com.boco.eoms.partner.netresource.dao.BsBtConfigDao;
import com.boco.eoms.partner.netresource.dao.IBsBtConfigDaoJdbc;
import com.boco.eoms.partner.netresource.dao.IEomsDao;
import com.boco.eoms.partner.netresource.model.bs.AccessNetworkQuipment;
import com.boco.eoms.partner.netresource.model.bs.BsBtQuipment;
import com.boco.eoms.partner.netresource.model.bs.BsBtResource;
import com.boco.eoms.partner.netresource.service.BsBtConfigMgr;
import com.boco.eoms.partner.netresource.service.BsBtQuipmentMgr;
import com.boco.eoms.partner.netresource.util.DataSaveCallback;
import com.boco.eoms.partner.netresource.util.ExcelImport;
import com.boco.eoms.partner.netresource.util.ImportResult;
import com.boco.eoms.partner.netresource.util.PnrNetResourceUtil;
import com.boco.eoms.partner.res.mgr.PnrResConfigMgr;
import com.boco.eoms.partner.res.model.PnrResConfig;
import com.google.common.collect.Lists;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

/** 
 * Description: 
 * Copyright:   Copyright (c)2013
 * Company:     BOCO 
 * @author:     chenbing 
 * @version:    1.0 
 */ 
public class BsBtConfigMgrImpl extends CommonGenericServiceImpl<BsBtResource> implements BsBtConfigMgr {

	private BsBtConfigDao sdBsBtConfigDao;
	private IBsBtConfigDaoJdbc bsBtDaoJdbc;
	
	private BsBtQuipmentMgr bsb;
	private ITawSystemAreaManager  tawArea;
	private ITawSystemDictTypeManager  tawty;
	private PnrResConfigMgr pnrResService;
	private PnrInspectLinkService pnrInspectLinkService;
	private PnrInspectMappingService pnrInspectMappingService;
    private int flag = 0 ;//标示有错误出现
	private int errorCount=0;
    private String[] errorDatas;//存放错误的数组
	private StringBuilder errMsg;//错误信息
    private int rowNum =1; //行数
    public List<String> nameList;
	
	public BsBtConfigDao getSdBsBtConfigDao() {
		return sdBsBtConfigDao;
	}
	public void setSdBsBtConfigDao(BsBtConfigDao sdBsBtConfigDao) {
		this.sdBsBtConfigDao = sdBsBtConfigDao;
		this.setCommonGenericDao(sdBsBtConfigDao);

	}
	public int isExitByName(String bsbtName) {
		// TODO Auto-generated method stub
		return 0;
	}
	public List<BsBtResource> getBsBtByName(String bsbtName) {
		// TODO Auto-generated method stub
		StringBuffer hql = new StringBuffer();
		hql.append(" from BsBtResource p where p.bsbtName='").append(bsbtName).append("'");
		
		List<BsBtResource> list =sdBsBtConfigDao.findByHql(hql.toString());
		
		return list;
	}
	
	/**
	 * 资源导入--基站机房资源--设备
	 */
	
	public ImportResult importBsBtEqFile(FormFile formFile, final String province,final String specialty,final String creatorId,final String osPath)
	throws Exception {
//     赋值
		bsb=(BsBtQuipmentMgr)ApplicationContextHolder
		.getInstance().getBean("bsBtQuipmentService");
		tawty=(ITawSystemDictTypeManager)ApplicationContextHolder
		.getInstance().getBean("ItawSystemDictTypeManager");
		pnrInspectLinkService=(PnrInspectLinkService)ApplicationContextHolder.getInstance().getBean("pnrInspectLinkService");
		pnrInspectMappingService =(PnrInspectMappingService)ApplicationContextHolder.getInstance().getBean("pnrInspectMappingService");
		pnrResService=(PnrResConfigMgr)ApplicationContextHolder.getInstance().getBean("PnrResConfigMgr");
		
		final List<Object> mainList = new ArrayList<Object>();
		final List<Object> errorList = new ArrayList<Object>();//错误保存列表
		
		final ExcelImport ei=new ExcelImport(2,1,12);
		
		ImportResult result=ei.importFromFile(formFile,2,new DataSaveCallback(){
			public void doSaveRow2Data(HSSFRow row) throws Exception {
				List<Object> mapList=this.fromRow2Object(row);
				if(mapList.size()==1){
					mainList.add(mapList.get(0));
				}else{
					errorCount++;
				}
			}
			
			// SimpleDateFormat dateformat =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			SimpleDateFormat datefor =  new SimpleDateFormat("yyyy-MM-dd");
			@SuppressWarnings({ "unchecked", "static-access" })
			@Override
			public List<Object> fromRow2Object(HSSFRow row) throws Exception {
				List<Object> mainList = new ArrayList<Object>();
				  BsBtQuipment bsbtQuipment = null;
				  BsBtResource sbsbt=null;
				  TawSystemDictType tadt=null;
				  errorDatas = new String[13];
				  errMsg=new StringBuilder();
				int colNum=0;
				try {
					bsbtQuipment= new BsBtQuipment();
					
					bsbtQuipment.setId(UUIDHexGenerator.getInstance().getID());

					colNum++;
					//基站机房名称
					String bsbt= StaticMethod.nullObject2String(row.getCell(1));
					if("".equals(bsbt)){
						
						errMsg.append("机房名称不能为空;");
						flag=1;
					//	throw new Exception("机房名称不能为空");
						
					}else{
						
						List<BsBtResource> sbsbtlist=getBsBtByName(bsbt);
						if(sbsbtlist.size()>0){
							sbsbt=sbsbtlist.get(0);
						}else{
							errMsg.append("机房名称-在系统中不存在，请先导入机房信息;");
							flag=2;
							//	throw new Exception("机房名称-在系统中不存在，请先导入机房信息");
							
						}
					}
					if(flag==0&&errMsg.length()==0){
						//通过名字查ID
						bsbtQuipment.setBsbtId(sbsbt.getId());					
						//机房名称
						bsbtQuipment.setBsbtName(sbsbt.getBsbtName());
					}else{
						flag=0;
					}

					colNum++;
					
					//设备编号
					String deviceNumber;
					 deviceNumber = StaticMethod.nullObject2String(row.getCell(2));
					 if("".equals(deviceNumber)){
						 errMsg.append("设备编号不能为空;");
						// throw new Exception("设备编号不能为空");
					 }else {
						 						 
						 List<BsBtQuipment> bqlist= bsb.getQuipmentByName(deviceNumber);
						 if(bqlist.size()>0){
							 errMsg.append("设备编号-系统中已存在;");
							 //throw new Exception("设备编号-系统中已存在");
						 }
					 }
					 bsbtQuipment.setDeviceNumber(deviceNumber);
					 colNum++;
					//生产商
					String manufacturers;
					 manufacturers = StaticMethod.nullObject2String(row.getCell(3));
					/* if("".equals(manufacturers)){
							throw new Exception("生产商不能为空");
							
						}//12101
						
						List<TawSystemDictType>  ddlist2 =tawty.getTawSystemDictTypeByName(manufacturers,"12101",5);
						if(ddlist2.size()>0){
							tadt=ddlist2.get(0);
						}else{
							throw new Exception("生产商-在系统中不存在，请先在\"系统管理\"-\"字典管理\"录入");
						}*/
					
					bsbtQuipment.setManufacturers(manufacturers);
					colNum++;
					//设备类型
					String deviceType = StaticMethod.nullObject2String(row.getCell(4));
				/*	if("".equals(deviceType)){
						throw new Exception("设备类型不能为空");
						
					}//12105
					
					List<TawSystemDictType>  ddlist3 =tawty.getTawSystemDictTypeByName(deviceType,"12105",5);
					if(ddlist3.size()>0){
						tadt=ddlist3.get(0);
					}else{
						throw new Exception("设备类型-在系统中不存在，请先在\"系统管理\"-\"字典管理\"录入");
					}*/
					bsbtQuipment.setDeviceType(deviceType);
//					设备类型
					
					
					colNum++;
					//**容量*/
					String capacity = StaticMethod.nullObject2String(row.getCell(5));
					bsbtQuipment.setCapacity(capacity);
					colNum++;
					//所属网络
					String network = StaticMethod.nullObject2String(row.getCell(6));
					if("".equals(network)){
						 errMsg.append("所属网络不能为空;");
						 flag=1;
						// throw new Exception("所属网络不能为空");
						
					}else{
						
						List<TawSystemDictType>  ddlist4 =tawty.getTawSystemDictTypeByName(network,"1210801",7);
						if(ddlist4.size()>0){
							tadt=ddlist4.get(0);
						}else{
							errMsg.append("所属网络-在系统中不存在，请联系管理员;");
							flag=2;
							// throw new Exception("所属网络-在系统中不存在，请先在\"系统管理\"-\"字典管理\"录入");
						}
					}
					if(flag==0&&errMsg.length()==0){
						
						bsbtQuipment.setNetwork(tadt.getDictId());
					}else{
						flag=0;
					}
					

					colNum++;
					//设备子类---取字典查下
					String deviceSubclass = StaticMethod.nullObject2String(row.getCell(7));
					if("".equals(deviceSubclass)){
						 errMsg.append("设备子类不能为空;");
						 flag=1;
						//throw new Exception("设备子类不能为空");
						
					}else {
						
						
						List<TawSystemDictType>  ddlist =tawty.getTawSystemDictTypeByName(deviceSubclass,"1210801",7);
						if(ddlist.size()>0){
							tadt=ddlist.get(0);
						}else{
							errMsg.append("设备子类-在系统中不存在，请联系管理员;");
							flag=2;
							//throw new Exception("设备子类-在系统中不存在，请先在\"系统管理\"-\"字典管理\"录入");
						}
					}
					if(flag==0&&errMsg.length()==0){
						
						bsbtQuipment.setDeviceSubclass(tadt.getDictId());
					}else{
						flag=0;
					}

					colNum++;
					//资产归属
					String assetsAttributable = StaticMethod.nullObject2String(row.getCell(8));
					bsbtQuipment.setAssetsAttributable(assetsAttributable);
					

					colNum++;
					
					//资产编号
					String assetsNumber = StaticMethod.nullObject2String(row.getCell(9));
					bsbtQuipment.setAssetsNumber(assetsNumber);

					colNum++;
					//使用日期
					Date useTime;
					String  useTime2 = StaticMethod.nullObject2String(row.getCell(10));
					if("".equals(useTime2)){
						useTime=null;
					}else{
						useTime=datefor.parse(useTime2);
					}
					bsbtQuipment.setUseTime(useTime);
					
					

					colNum++;
					//软件版本
					String soft =StaticMethod.nullObject2String(row.getCell(11)); 
					bsbtQuipment.setSoft(soft);
					
					colNum++;
					//巡检资源的ID及名字
					if(errMsg.length()==0){
						
						List<PnrResConfig> plist=pnrResService.getPnrResConfigByResId(sbsbt.getId());
						if(plist.size()>0){							
							bsbtQuipment.setInspectId(plist.get(0).getId());
							bsbtQuipment.setInspectName(plist.get(0).getResName());
						}
					}
					
					
//					保存错误信息的集合
					errorDatas[0]=Integer.toString(rowNum);//序号
					errorDatas[1]=bsbt;
					errorDatas[2]=deviceNumber;
					errorDatas[3]=manufacturers;
					errorDatas[4]=deviceType;
					errorDatas[5]=capacity;
					errorDatas[6]=network;
					errorDatas[7]=deviceSubclass;
					errorDatas[8]=assetsAttributable;
					errorDatas[9]=assetsNumber;
					errorDatas[10]=useTime2;
					errorDatas[11]=soft;
					
					//区分错误与正确的列表
					
					if(errMsg.length()==0){						
						mainList.add(bsbtQuipment);
						errorDatas=null;
					}else{
						errorDatas[12]=errMsg.toString();
						errorList.add(errorDatas);
						flag=0;
						bsbtQuipment=null;
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
		rowNum =1;//初始化
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
				insertMainSql.append(" insert into pnr_bsbt_quipment (ID, BSBT_ID,DEVICE_NUMBER , MANUFACTURERS,DEVICE_TYPE ,");//5
				insertMainSql.append(" CAPACITY,NETWORK,DEVICE_SUBCLASS ,ASSETS_ATTRIBUTABLE,ASSETS_NUMBER,");//5
				insertMainSql.append(" USE_TIME,SOFT,BSBT_NAME,INSPECT_ID,INSPECT_NAME)");//3
				insertMainSql.append(" values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");//15
				
				stmt1=conn.prepareStatement(insertMainSql.toString());
				for(int i = 0;i<mainlist1.size();i++){
					bsBtDaoJdbc.importData(mainlist1.get(i),stmt1,2);
					stmt1.executeBatch();
					stmt1.clearBatch();
					
				}
				conn.commit();
				conn.setAutoCommit(true);
				//将数据导入到巡检点与网络资源中的设备关联表
				insertAssociatedTable(mainList,pnrResService,pnrInspectLinkService);
				
			}
			//处理错误信息
			int errorSize = errorList.size();
			if(errorSize>0){
				//开始将错误的信息导入到excel中 
				String outfilepath ="jizhanshebei"+creatorId+".xls";
				outfilepath =ExcelImport.outfilePath(osPath,outfilepath);

				int sheet =2 ,rowNum=3;
				String[] names={"序号","所属机房*","设备编号*","生产厂商","设备类型","容量","所属网络","设备子类*","资产归属","资产编号","使用日期","软件版本","错误信息"};
				String sheetName="基站机房下设备";
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
	/**
	 * ---设备导入时往"巡检点与网络资源中的设备关联表"导数据
	 */
	public void insertAssociatedTable(List mainList,PnrResConfigMgr pnrResService,PnrInspectLinkService pnrInspectLinkService ) throws Exception{

		
		//将数据导入到巡检点与网络资源中的设备关联表
		PnrInspectLink ps=null;
		BsBtQuipment bq=null;
		PnrInspectMapping pm=null;
		int size=0;
		
		for(int i =0 ;i<mainList.size();i++){
			ps = new PnrInspectLink();
			bq = (BsBtQuipment)mainList.get(i);
			List<PnrInspectMapping> p =pnrInspectMappingService.getPnrInspectMapping(bq.getNetwork(),bq.getDeviceSubclass());
			if(p.size()>0){
				pm = p.get(0);
				//检查是否已同步
				Search search = new Search();
				search.addFilterEqual("netResId", bq.getId());
				SearchResult sr =pnrInspectLinkService.searchAndCount(search);
				
				size = sr.getTotalCount();
			}
			if(pm!=null){
				if(size==0){
				ps.setId(UUIDHexGenerator.getInstance().getID());
				//通过资源ID 去找巡检资源
				List<PnrResConfig> plist=pnrResService.getPnrResConfigByResId(bq.getBsbtId());
				//巡检资源ID
				if(plist.size()>0){					
					ps.setInspectId(plist.get(0).getId());
				}
				//设备所属网络及设备类别 与 专业关联表
				ps.setInspectMappingId(pm.getId());
				//设备ID
				ps.setNetResId(bq.getId());
				//字典值(专业)网络资源分类
				ps.setDeviceSpecialtyName(pm.getDeviceSpecialtyName());
				//所属网络名称
				ps.setDeviceTypeName(pm.getDeviceTypeName());
				//设备表名
				ps.setNetresTableName(pm.getNetresTableName());
//				网络资源名称 --设备的编号
				ps.setNetresName(bq.getDeviceNumber());
//				设备的字段（设备类别）
				ps.setNetresFieldName(pm.getNetresFieldName());
				// 设备类别的字典值
				ps.setNetresFieldValue(pm.getNetresFieldValue());
				
				pnrInspectLinkService.save(ps);
				}
			}
		}
		
	}
	
	/**
	 * 资源导入--基站机房资源
	 */
	public ImportResult importBsBtFromFile(FormFile formFile, final String province,final String specialty,final String creatorId,final String osPath)
	throws Exception {

		tawArea =(ITawSystemAreaManager)ApplicationContextHolder
		.getInstance().getBean("ItawSystemAreaManager");
		tawty=(ITawSystemDictTypeManager)ApplicationContextHolder
		.getInstance().getBean("ItawSystemDictTypeManager");
		pnrResService=(PnrResConfigMgr)ApplicationContextHolder.getInstance().getBean("PnrResConfigMgr");
		
		final List<Object> mainList = new ArrayList<Object>();
		final List<Object> errorList = new ArrayList<Object>();
		nameList = new ArrayList<String>();
		final ExcelImport ei=new ExcelImport(2,1,56);		
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
			SimpleDateFormat date2dd =  new SimpleDateFormat("yyyy-MM-dd");
		
			//将每行转化成SdBsBtResource对象
			@SuppressWarnings({ "unchecked", "static-access" })
			@Override
			public List<Object> fromRow2Object(HSSFRow row) throws Exception {
				List<Object> mainList = new ArrayList<Object>();
				//资源信息 
				PnrResConfig pnrResConfig=null;
				BsBtResource  sdBsBtResource = null;
				//错误信息
				errorDatas = new String[57];
				errMsg=new StringBuilder();
				  
				int colNum=0;
				try {
					sdBsBtResource = new BsBtResource();
					String id = UUIDHexGenerator.getInstance().getID();
					sdBsBtResource.setId(id);
					colNum++;
					
					//基站机房名称
					String bsbtName;
					bsbtName= StaticMethod.nullObject2String(row.getCell(1));
					if("".equals(bsbtName)){
						errMsg.append("机房名称不能为空;");
						flag=1;
						//throw new Exception("机房名称不能为空");
					}else if(ei.isHaveSameName(bsbtName, nameList)){
						errMsg.append("该机房名称在Excel表中重复;");
						flag = 3;
					}else{
						
						List<BsBtResource> sbsbtlist=getBsBtByName(bsbtName);
						if(sbsbtlist.size()>0){
							errMsg.append("该机房名称在系统中已存在;");
							flag=2;
							//throw new Exception("该机房名称在系统中已存在");
						}					
					}
					sdBsBtResource.setBsbtName(bsbtName);
					colNum++;
					
					//所属地市ID ？？？
					
					String citiesID="";
					String citiesName = StaticMethod.nullObject2String(row.getCell(2));
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

					sdBsBtResource.setCity(citiesID);
					}else{
						flag=0;
					}
					colNum++;
					//所属区域ID ？？？
					String countryID="";
					String country = StaticMethod.nullObject2String(row.getCell(3));
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
							if(countryID.equals("")){
								errMsg.append("所属区域-系统中不存在,请联系管理员;");
							}
						}else{
							errMsg.append("所属区域-系统中不存在,请联系管理员;");
							flag=2;
							//throw new Exception("所选区域-系统中不存在");
						}
					}
					if(flag==0&&errMsg.length()==0){

				 	sdBsBtResource.setCountry(countryID);
					}else{
						flag=0;
					}
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
				 			sdBsBtResource.setSpecialty(specialtyId);
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
				 				sdBsBtResource.setResType(resTypeId);
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
				 		
				 		sdBsBtResource.setResLevel(resLevelId);
				 		
				 	}
				 	colNum++;
					//所在局所
				 	String bureau = StaticMethod.nullObject2String(row.getCell(7));
				 	sdBsBtResource.setBureau(bureau);
					colNum++;
				 	//二期机房名
				 	String twoBsbtName = StaticMethod.nullObject2String(row.getCell(8));
				 	sdBsBtResource.setTwoBsbtName(twoBsbtName);
					colNum++;
					//机房别名
					String anotherBsbtName = StaticMethod.nullObject2String(row.getCell(9));
					sdBsBtResource.setAnotherBsbtName(anotherBsbtName);
					colNum++;
					//机房类型
					String bsbtType = StaticMethod.nullObject2String(row.getCell(10));
					sdBsBtResource.setBsbtType(bsbtType);
					colNum++;
					//机房级别
					String bsbtLevel = StaticMethod.nullObject2String(row.getCell(11));
					sdBsBtResource.setBsbtLevel(bsbtLevel);
					colNum++;
					//机房位置
					String bsbtAddress = StaticMethod.nullObject2String(row.getCell(12));
					sdBsBtResource.setBsbtAddress(bsbtAddress);
					colNum++;
					//所在楼层
					String foor =StaticMethod.nullObject2String(row.getCell(13)); 
					sdBsBtResource.setFoor(foor);
					colNum++;
					//序号
					String no =StaticMethod.nullObject2String(row.getCell(14)); 
					sdBsBtResource.setNo(no);
					colNum++;
					//资产归属
					String assetsAttributable =StaticMethod.nullObject2String(row.getCell(15)); 
					sdBsBtResource.setAssetsAttributable(assetsAttributable);
					colNum++;
					//联系人
					String contact = StaticMethod.nullObject2String(row.getCell(16));
					sdBsBtResource.setContact(contact);					
					colNum++;
					//联系电话
					
					String contactTel =ExcelImport.getStringCellValue(row.getCell(17));
					sdBsBtResource.setContactTel(contactTel);
					colNum++;
					//所属97局所
					String is97bureau =StaticMethod.nullObject2String(row.getCell(18));
					sdBsBtResource.setIs97bureau(is97bureau);
					colNum++;
					//用户名称
					String userName = StaticMethod.nullObject2String(row.getCell(19));
					sdBsBtResource.setUserName(userName);
					colNum++;
					//备注
					String  remark = StaticMethod.nullObject2String(row.getCell(20));
					sdBsBtResource.setRemark(remark);
					colNum++;
					//录入人
					String  entryPeople = StaticMethod.nullObject2String(row.getCell(21));
					sdBsBtResource.setEntryPeople(entryPeople);
					colNum++;
					//录入日期
					Date entryTime;
					String  entryTime2 = StaticMethod.nullObject2String(row.getCell(22));
					if("".equals(entryTime2)){
						Date time = new Date();
						entryTime =time;
					}else{
					entryTime=dateformat.parse(entryTime2);
					}
					sdBsBtResource.setEntryTime(entryTime);
					colNum++;
					//修改人
					String  modifyPeople = StaticMethod.nullObject2String(row.getCell(23));
					sdBsBtResource.setModifyPeople(modifyPeople);
					colNum++;
					//修改日期
					Date modifyTime;
					String  modifyTime2 = StaticMethod.nullObject2String(row.getCell(24));
					if("".equals(modifyTime2)){
					modifyTime=null;
					}else{
					modifyTime=dateformat.parse(modifyTime2);
					}
					sdBsBtResource.setModifyTime(modifyTime);
					colNum++;
					//长
					String getLength=StaticMethod.nullObject2String(row.getCell(25));
					double  length =0;
					if("".equals(getLength)){
					}else{
						length= Double.parseDouble(getLength);
						
					}
					sdBsBtResource.setLength(length);
					colNum++;
					//宽
					String getWidth=StaticMethod.nullObject2String(row.getCell(26));
					double  width =0;
					if("".equals(getWidth)){
					}else{
						width= Double.parseDouble(getWidth);
						
					}
					sdBsBtResource.setWidth(width);
					colNum++;
					//高
					String getHeight=StaticMethod.nullObject2String(row.getCell(27));
					double  height =0;
					if("".equals(getHeight)){
					}else{
						height= Double.parseDouble(getHeight);
						
					}
					sdBsBtResource.setHeight(height);
					colNum++;
					//机房分类
					String  bsbtClassify = StaticMethod.nullObject2String(row.getCell(28));
					sdBsBtResource.setBsbtClassify(bsbtClassify);
					colNum++;
					//经度
					Double  longitude=0.0;
					String getlongitude= StaticMethod.nullObject2String(row.getCell(29));
					if("".equals(getlongitude)){
						errMsg.append("GPS_X不能空;");
					}else{
						getlongitude=getlongitude.trim();
						if(getlongitude.matches(PnrNetResourceUtil.REG)){
							
							longitude= Double.parseDouble(getlongitude);
							sdBsBtResource.setLongitude(longitude);
						}else{
							
							errMsg.append("GPS_X-不正确;");
						}
						
					}

					colNum++;
					//纬度
					Double latitude=0.0;
					String getlatitude= StaticMethod.nullObject2String(row.getCell(30));
					if("".equals(getlatitude)){
						errMsg.append("GPS_Y不能空;");
					}else{
						getlatitude=getlatitude.trim();
						if(getlatitude.matches(PnrNetResourceUtil.REG)){
							latitude = Double.parseDouble(getlatitude);
							sdBsBtResource.setLatitude(latitude);
						}else{
							errMsg.append("GPS_Y-不正确;");							
						}
					}
					colNum++;
					//集团可用面积
					Double usableArea;
					String getusableArea= StaticMethod.nullObject2String(row.getCell(31));
					if("".equals(getusableArea)){
						usableArea=0.0;
					}else{
						
						usableArea = Double.parseDouble(getusableArea);
					}				
					sdBsBtResource.setUsableArea(usableArea);
					colNum++;
					//集团已用面积
					Double usedArea;
					String getusedArea= StaticMethod.nullObject2String(row.getCell(32));
					if("".equals(getusedArea)){
						usedArea=0.0;
					}else{
						
						usedArea = Double.parseDouble(getusedArea);
					}		
					sdBsBtResource.setUsedArea(usedArea);
					colNum++;
					
					//集团机房归属
					String  bsbtVested = StaticMethod.nullObject2String(row.getCell(33));
					sdBsBtResource.setBsbtVested(bsbtVested);
					colNum++;
					
					//集团是否共享
					String  isShare = StaticMethod.nullObject2String(row.getCell(34));
					sdBsBtResource.setIsShare(isShare);
					colNum++;
					//集团共享运营商
					String  shareOperators = StaticMethod.nullObject2String(row.getCell(35));
					sdBsBtResource.setShareOperators(shareOperators);
					colNum++;
					//机房大类
					String  bsbtCategories = StaticMethod.nullObject2String(row.getCell(36));
					sdBsBtResource.setBsbtCategories(bsbtCategories);
					colNum++;
					//机房小类
					String  bsbtSmallClass = StaticMethod.nullObject2String(row.getCell(37));
					sdBsBtResource.setBsbtSmallClass(bsbtSmallClass);
					colNum++;
					//集团机房类型					
					String  groupBsbtTypes = StaticMethod.nullObject2String(row.getCell(38));
					sdBsBtResource.setGroupBsbtTypes(groupBsbtTypes);
					colNum++;
					
					//集团机房承重
					Double groupRoombear;
					String getgroupRoombear= StaticMethod.nullObject2String(row.getCell(39));
					if("".equals(getgroupRoombear)){
						groupRoombear=0.0;
					}else{
						
						groupRoombear = Double.parseDouble(getgroupRoombear);
					}		
					sdBsBtResource.setGroupRoombear(groupRoombear);
					colNum++;
					
					//集团走线方式
					String  alignmentMethod = StaticMethod.nullObject2String(row.getCell(40));
					sdBsBtResource.setAlignmentMethod(alignmentMethod);
					colNum++;
					
					//集团单双走线架
					String  chutes = StaticMethod.nullObject2String(row.getCell(41));
					sdBsBtResource.setChutes(chutes);
					colNum++;
					//集团消防系统
					String  fireSystem = StaticMethod.nullObject2String(row.getCell(42));
					sdBsBtResource.setFireSystem(fireSystem);
					colNum++;
					//集团有无地板
					String  isNoun = StaticMethod.nullObject2String(row.getCell(43));
					sdBsBtResource.setIsNoun(isNoun);
					colNum++;
					
					//集团有无监控
					String  isMonitor = StaticMethod.nullObject2String(row.getCell(44));
					sdBsBtResource.setIsMonitor(isMonitor);
					colNum++;
					
					//集团租用到期时间
					Date maturityTime;
					String  maturityTime2 = StaticMethod.nullObject2String(row.getCell(45));
					if("".equals(maturityTime2)){
					maturityTime=null;
					}else{
					maturityTime=date2dd.parse(maturityTime2);
					}
					sdBsBtResource.setMaturityTime(maturityTime);
					colNum++;
					
					//集团房屋类型
					String  roomType = StaticMethod.nullObject2String(row.getCell(46));
					sdBsBtResource.setRoomType(roomType);
					colNum++;
					//集团维护方式
					String  maintenanceMode = StaticMethod.nullObject2String(row.getCell(47));
					sdBsBtResource.setMaintenanceMode(maintenanceMode);
					colNum++;
					//集团维护单位
					String  maintenanceUnit = StaticMethod.nullObject2String(row.getCell(48));
					sdBsBtResource.setMaintenanceUnit(maintenanceUnit);
					colNum++;
					//集团包机人
					String  charter = StaticMethod.nullObject2String(row.getCell(49));
					sdBsBtResource.setCharter(charter);
					colNum++;
					//集团第三方维护单位
					String  threeMainUnit = StaticMethod.nullObject2String(row.getCell(50));
					sdBsBtResource.setThreeMainUnit(threeMainUnit);
					colNum++;
					//集团续保截至日期
					Date renewalTime;
					String  renewalTime2 = StaticMethod.nullObject2String(row.getCell(51));
					if("".equals(renewalTime2)){
					renewalTime=null;
					}else{
					renewalTime=date2dd.parse(renewalTime2);
					}
					sdBsBtResource.setRenewalTime(renewalTime);
					colNum++;
					//集团维保类型
					String  maintenanceType = StaticMethod.nullObject2String(row.getCell(52));
					sdBsBtResource.setMaintenanceType(maintenanceType);
					colNum++;
					
					//集团已购买维保累计年限
					String  maintenanceYear = StaticMethod.nullObject2String(row.getCell(53));
					sdBsBtResource.setMaintenanceYear(maintenanceYear);
					colNum++;
					
					
					//集团室外放置
					String  outDoor = StaticMethod.nullObject2String(row.getCell(54));
					sdBsBtResource.setOutDoor(outDoor);
					colNum++;
					
					//标准地址
					String  place = StaticMethod.nullObject2String(row.getCell(55));
					sdBsBtResource.setPlace(place);
					
//					保存错误信息的集合
					errorDatas[0]=Integer.toString(rowNum);//序号
					errorDatas[1]=bsbtName;
					errorDatas[2]=citiesName;
					errorDatas[3]=country;
					errorDatas[4]=specialty;
					errorDatas[5]=resType;
					errorDatas[6]=resLevel;
					errorDatas[7]=bureau;
					errorDatas[8]=twoBsbtName;
					errorDatas[9]=anotherBsbtName;	
					errorDatas[10]=bsbtType;
					errorDatas[11]=bsbtLevel;
					errorDatas[12]=bsbtAddress;
					errorDatas[13]=foor;
					errorDatas[14]=no;	
					errorDatas[15]=assetsAttributable;	
					errorDatas[16]=contact;
					errorDatas[17]=contactTel;
					errorDatas[18]=is97bureau;
					errorDatas[19]=userName;
					errorDatas[20]=remark;	
					errorDatas[21]=entryPeople;
					errorDatas[22]=entryTime2;
					errorDatas[23]=modifyPeople;
					errorDatas[24]=modifyTime2;
					errorDatas[25]=getLength;	
					errorDatas[26]=getWidth;
					errorDatas[27]=getHeight;
					errorDatas[28]=bsbtClassify;
					errorDatas[29]=getlongitude;
					errorDatas[30]=getlatitude;	
					errorDatas[31]=getusableArea;
					errorDatas[32]=getusedArea;
					errorDatas[33]=bsbtVested;
					errorDatas[34]=isShare;
					errorDatas[35]=shareOperators;	
					errorDatas[36]=bsbtCategories;
					errorDatas[37]=bsbtSmallClass;
					errorDatas[38]=groupBsbtTypes;
					errorDatas[39]=getgroupRoombear;
					errorDatas[40]=alignmentMethod;	
					errorDatas[41]=chutes;
					errorDatas[42]=fireSystem;
					errorDatas[43]=isNoun;
					errorDatas[44]=isMonitor;
					errorDatas[45]=maturityTime2;	
					errorDatas[46]=roomType;
					errorDatas[47]=maintenanceMode;
					errorDatas[48]=maintenanceUnit;
					errorDatas[49]=charter;
					errorDatas[50]=threeMainUnit;	
					errorDatas[51]=renewalTime2;
					errorDatas[52]=maintenanceType;
					errorDatas[53]=maintenanceYear;
					errorDatas[54]=outDoor;
					errorDatas[55]=place;	
					//区分错误与正确的列表

					if(errMsg.length()==0){						
						mainList.add(sdBsBtResource);
						errorDatas=null;
					}else{
						errorDatas[56]=errMsg.toString();
						errorList.add(errorDatas);
						flag=0;
						sdBsBtResource=null;
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
				 insertMainSql.append(" insert into PNR_BSBT_RESOURCE (ID, BSBT_NAME,CITY , country,BUREAU ,");//5
				 insertMainSql.append(" TWO_BSBT_NAME,ANOTHER_BSBT_NAME,BSBT_TYPE ,BSBT_LEVEL,BSBT_ADDRESS,");//5
				 insertMainSql.append(" FOOR,NO,ASSETS_ATTRIBUTABLE , CONTACT , CONTACT_TEL ,");//5
				 insertMainSql.append(" IS97BUREAU,USER_NAME,REMARK,ENTRY_PEOPLE,ENTRY_TIME,");//5
				 insertMainSql.append(" MODIFY_PEOPLE,MODIFY_TIME,LENGTH,WIDTH,HEIGHT,");//5
				 insertMainSql.append(" BSBT_CLASSIFY,LONGITUDE,LATITUDE,USABLE_AREA,USED_AREA,");//5
				 insertMainSql.append(" BSBT_VESTED,IS_SHARE,SHARE_OPERATORS,BSBT_CATEGORIES,BSBT_SMALL_CLASS,");//5
				 insertMainSql.append(" GROUP_BSBT_TYPES,GROUP_ROOM_BEAR,ALIGNMENT_METHOD,CHUTES,FIRE_SYSTEM,");//5
				 insertMainSql.append(" IS_NOUN,IS_MONITOR,MATURITY_TIME,ROOM_TYPE,MAINTENANCE_MODE,");//5
				 insertMainSql.append(" MAINTENANCE_UNIT,CHARTER,THREE_MAIN_UNIT,RENEWAL_TIME,MAINTENANCE_TYPE,");//5
				 insertMainSql.append(" MAINTENANCE_YEAR,OUT_DOOR,PLACE,SPECIALTY,RES_TYPE,RES_LEVEL)");//6
				 insertMainSql.append(" values(?,?,?,?,?,?,?,?,?,?,");
				 insertMainSql.append("?,?,?,?,?,?,?,?,?,?,");//20
				 insertMainSql.append("?,?,?,?,?,?,?,?,?,?,");
				 insertMainSql.append("?,?,?,?,?,?,?,?,?,?,");//20
				 insertMainSql.append("?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");//16
			
				stmt1=conn.prepareStatement(insertMainSql.toString());
				for(int i = 0;i<mainlist1.size();i++){
					bsBtDaoJdbc.importData(mainlist1.get(i),stmt1,1);
							stmt1.executeBatch();
							stmt1.clearBatch();
					
				}
			
				for(int k=0;k<mainList.size();k++){
					
					PnrResConfig pnrResConfig=null;
					BsBtResource sr = (BsBtResource) mainList.get(k);
					//资源信息
					pnrResConfig = new PnrResConfig();
					pnrResConfig.setId(UUIDHexGenerator.getInstance().getID());
					pnrResConfig.setResName(sr.getBsbtName());
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
					pnrResConfig.setSubResTable("pnr_bsbt_resource");
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
				//先保存资源再去提交资源	
				conn.commit();
				conn.setAutoCommit(true);
			}
			
			//处理错误信息
			int errorSize = errorList.size();
			if(errorSize>0){
				//开始将错误的信息导入到excel中 
				String outfilepath ="jizhanjifangziyuan"+creatorId+".xls";
				outfilepath =ExcelImport.outfilePath(osPath,outfilepath);

				int sheet =1 ,rowNum=3;
				String[] names={"序号","机房名称*","所属地市*","所属区域*","巡检专业*","资源类别*","资源级别*","所在局所","二期机房名称","机房别名","机房类型","机房级别","机房位置","所在楼层","序号","资产归属","联系人","联系电话","所属97局所","用户名称","备注","录入人","录入日期","修改人","修改日期","长","宽","高","机房分类","GPS_X*","GPS_Y*","集团可用面积","集团已用面积","集团机房归属","集团是否共享","集团共享运营商","机房大类","机房小类","集团机房类型","集团机房承重","集团走线方式","集团单双层走线架","集团消防系统","集团有无地板","集团有无监控","集团租用到期时间","集团房屋类型","集团维护方式","集团维护单位","集团包机人","集团第三方维护单位","集团续保截止日期","集团维保类型","集团已购买维保累计年限","集团室外放置点","标准地址","错误信息"};
				String sheetName="基站机房资源";
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
	
	public IBsBtConfigDaoJdbc getBsBtDaoJdbc() {
		return bsBtDaoJdbc;
	}
	public void setBsBtDaoJdbc(IBsBtConfigDaoJdbc bsBtDaoJdbc) {
		this.bsBtDaoJdbc = bsBtDaoJdbc;
	}
	
}
