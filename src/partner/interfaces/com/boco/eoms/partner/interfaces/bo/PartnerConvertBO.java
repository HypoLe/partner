/**
 * 
 */
package com.boco.eoms.partner.interfaces.bo;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.partner.baseinfo.mgr.AreaDeptTreeMgr;
import com.boco.eoms.partner.baseinfo.model.AreaDeptTree;
import com.boco.eoms.partner.interfaces.common.init.FtpZipOption;
import com.boco.eoms.partner.interfaces.common.init.MD5Util;
import com.boco.eoms.partner.interfaces.common.init.StaticContext;
import com.boco.eoms.partner.interfaces.dao.PartnerConvertDAO;
import com.boco.eoms.partner.interfaces.dto.ExcelStructure;
import com.boco.eoms.partner.interfaces.dto.ExcelStructureFieldMap;
import com.boco.eoms.partner.interfaces.dto.ExcelStructureTable;
/**
 * @author mooker
 * 
 */
public class PartnerConvertBO {

	private static Log log = LogFactory.getLog(PartnerConvertBO.class);
	
	public static ExcelStructure eStructure = new ExcelStructure();
    private PartnerConvertDAO dao = new PartnerConvertDAO();
	private String localdir = StaticContext.getServletContext().getRealPath("WEB-INF/classes/com/boco/eoms/partner/interfaces/resources/file"); 
	String avaifile;

	/**
	 * 
	 */
	public PartnerConvertBO() {
		
	}

	public void initBO() {
		eStructure.readExcel();
	}

	public void ConvertXML(String systemID, String eventid, String feedbackuri,int loadingFlag) {
		try {
			TaskStatus taskStatus = new TaskStatus();
			taskStatus.setEventID(eventid);
			taskStatus.setSystemID(systemID);
			// 步骤一：解析数据文件
			Map<String,Map<String,String>> xmlMap =InitConvertXML(eventid, loadingFlag,systemID);
			if(xmlMap!=null){
				log.info("ConvertXML++++++++begin");
				int success = dao.insertResult(xmlMap);
				log.info("ConvertXML++++++++end");
				if(success>0){
					taskStatus.setStatusCode(TaskStatus.noCompleted);
					dao.setTaskStatus(systemID + "_" + eventid, taskStatus);
					this.feedBack(eventid,TaskStatus.completed,"装载任务成功完成",feedbackuri);
				}else{
					taskStatus.setStatusCode(TaskStatus.sourceDataError);
					dao.setTaskStatus(systemID + "_" + eventid, taskStatus);
					this.feedBack(eventid,TaskStatus.sourceDataError,"数据提供方提供数据有误导致装载任务失败",feedbackuri);
				}
			}else{
				taskStatus.setStatusCode(TaskStatus.sourceDataError);
				dao.setTaskStatus(systemID + "_" + eventid, taskStatus);
				this.feedBack(eventid,TaskStatus.sourceDataError,"数据提供方提供数据有误导致装载任务失败",feedbackuri);
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.feedBack(eventid,TaskStatus.sourceDataError,"数据提供方提供数据有误导致装载任务失败"+e.getMessage(),feedbackuri);
		}
	}
	
	private void feedBack(String eventid,int statusCode,String invorkResult,String feedbackuri){
		if (feedbackuri.trim().equals("-1")) {
			log.info("数据提供方不提供装载反馈接口");
			LoadingQueryBO dbo = new LoadingQueryBO();
			try {
				 dbo.setEventid(eventid);
				 dbo.setStatuscode(new Long(statusCode));
				 dbo.setStatusdescription(invorkResult);
				 dbo.setCuid("T_LOADINGQUERY-" + eventid);
				 dao.insertObjectTask(dbo);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			this.lFeedback(eventid, statusCode, invorkResult, feedbackuri);
		}
	}

	/**
	 * 解析数据文件
	 * 
	 * @param eventid
	 * @param loadingFlag
	 * @return
	 * @throws Exception
	 */
	public Map InitConvertXML(String eventid, int loadingFlag,String systemID)
			throws Exception {
		log.info("准备开始解析专业网管上传到综合资源的数据文件，首先校验数据文件");
		avaifile = "";
	    Map<String, String> int_idTableNameMap = eStructure.localclassidIrmsclassidMap;
	    Map<String, String> fntopartnerMap = eStructure.fntopartnerMap;
		Map<String,Map<String,String>> xmlMap = null;
	    String datafile="";
	    String irmsclassname = "";
		String depid="";
		String station="";
		String depid_value="";
		String station_value="";
		String name="";
		String name_value="";
		String area_id="";
		String areaname="";
		String areaname_value="";
		String interfaceHeadId="";
		String interfaceHeadId_value="";
		// 校验文件
		try {
			FtpZipOption ftpOption = new FtpZipOption();
			String sourceChkName = eventid + ".CHK";
			ftpOption.downloadFtpFile(localdir,sourceChkName);

			boolean filenop = checkFile(eventid);
			if (filenop) {
				Map<String,String> areamap = dao.selectAreaMap();
				Map<String,String> deptmap = dao.selectProviderNameMap();
				Map<String,String> stationmap = dao.selectstationNameMap();
				xmlMap = new HashMap<String,Map<String,String>>();
				log.info("经过校验后，可以用来同步的数据文件包括：" + avaifile);
				String[] compfile = avaifile.split("><");
				for (int fileno = 0; fileno < compfile.length; fileno++) {
					String tempcompfile = localdir
							+ File.separatorChar + compfile[fileno];
					String dir = localdir
							+ File.separatorChar + eventid;

					unZip(tempcompfile, dir, 1);
				}
				File newfile = new File(localdir+ File.separatorChar + eventid);
				File[] filelist = newfile.listFiles();
				String[] realcompfile = new String[] {};
				String norealcompfile = "";
				for (int newf = 0; newf < filelist.length; newf++) {
					norealcompfile += filelist[newf].getName() + ",";
				}
				realcompfile = norealcompfile.split(",");
				for (int i = 0; i < compfile.length; i++) {
					datafile = "开始分析第" + i + "个文件：" + realcompfile[i];
					log.info(datafile);
					String filecom =localdir
							+ File.separatorChar + eventid + File.separatorChar
							+ realcompfile[i];
					File file = new File(filecom);

					if (file.exists()) {

						log.info("读取专业网管数据文件[全量数据]：" + filecom);
						SAXReader saxReader = new SAXReader();

						Document document = saxReader.read(file);
						Element root = document.getRootElement();
						int xml=1;
						// 遍历根结点（resource-data）的所有孩子节点（class节点）
						for (Iterator iter = root.elementIterator(); iter
								.hasNext();) {
							Element element = (Element) iter.next();
							// 获取class节点的name属性的值
							Attribute nameAttr = element.attribute("name");
							String classname = nameAttr.getText().toUpperCase();
						    irmsclassname = int_idTableNameMap.get(classname) == null ? classname : int_idTableNameMap.get(classname).toString();
							ExcelStructureTable eTable = (ExcelStructureTable) eStructure.getTableMap().get(irmsclassname);
							if (eTable != null) {
								for (Iterator sub_iter = element.elementIterator(); sub_iter.hasNext();) {
									log.info("开始解析第[" +xml+ "]条");
									Element sub_element = (Element) sub_iter.next();
									Map<String,String> resultMap = new HashMap<String,String>();
									String int_id = "";
									StringBuffer objsql = new StringBuffer();
									StringBuffer valuesql = new StringBuffer();
									objsql.append("insert into ").append(irmsclassname).append("(");
									valuesql.append(" values ('");
									for (Iterator subiter = sub_element.elementIterator(); subiter.hasNext();) {
										Element subelement = (Element) subiter.next();
										if (subelement.getName().toUpperCase().equals("RESOURCEID")) {
											String resourceId = subelement.getTextTrim().toUpperCase();
											int_id = resourceId.substring(resourceId.lastIndexOf("-")+1,resourceId.length());
											objsql.append("ID").append(",");
											valuesql.append(int_id).append("','");
										} else {
											Attribute sub_key_Attr = subelement.attribute("name");
											Attribute sub_val_Attr = subelement.attribute("value");
											String sub_attrName = sub_key_Attr.getValue().toUpperCase().trim();
											String sub_attrValue = sub_val_Attr.getValue().trim();
											if(sub_attrName.equals("REGION")){
												area_id=sub_attrValue;
											}
											
											if(!sub_attrName.equals("ID")){
												ExcelStructureFieldMap eField = eTable.getFieldMap().get(sub_attrName);
												if(eField != null){
													if(sub_attrValue != null && !sub_attrValue.equals("") && !sub_attrValue.equals("null")){
														String type = eField.getIrmsFieldType();
														if(type.toUpperCase().equals("DATE")){
															objsql.append(eField.getIrmsFieldName()).append(",");
															valuesql = valuesql.deleteCharAt(valuesql.lastIndexOf("'"));
															valuesql.append("to_date('").append(sub_attrValue).append("','YYYY-MM-DD HH24:MI:SS')").append(",'");
														}else if(!eField.getDatanmsFieldCheck().equals("") && irmsclassname.toUpperCase().equals("PNR_DEPT")){
															areaname = eField.getIrmsFieldName();
															areaname_value = areamap.get(sub_attrValue) == null ? sub_attrValue : areamap.get(sub_attrValue).toString();
														}else if(!eField.getDatanmsFieldCheck().equals("") && !irmsclassname.toUpperCase().equals("PNR_DEPT")){
															String value = fntopartnerMap.get(sub_attrValue) == null ? sub_attrValue : fntopartnerMap.get(sub_attrValue).toString();
															objsql.append(eField.getIrmsFieldName()).append(",");
															valuesql.append(value).append("','");
														}else{
															if(eField.getIrmsFieldName().toUpperCase().equals("DEPT_NAME") || eField.getIrmsFieldName().toUpperCase().equals("DEPT_ID")){
																depid = eField.getIrmsFieldName();
																depid_value = deptmap.get(sub_attrValue) == null ? sub_attrValue : deptmap.get(sub_attrValue).toString();
															}else if(eField.getIrmsFieldName().toUpperCase().equals("STATION") || eField.getIrmsFieldName().toUpperCase().equals("STORAGE")){
																station = eField.getIrmsFieldName();
																station_value = stationmap.get(sub_attrValue) == null ? sub_attrValue : stationmap.get(sub_attrValue).toString();
															}else if(eField.getIrmsFieldName().toUpperCase().equals("NAME")&&(!irmsclassname.toUpperCase().equals("PNR_USER"))){
																name = eField.getIrmsFieldName();
																name_value = sub_attrValue;
															}else if(eField.getIrmsFieldName().toUpperCase().equals("INTERFACE_HEAD_ID")){
																interfaceHeadId = eField.getIrmsFieldName();
																interfaceHeadId_value = sub_attrValue;
																objsql.append(eField.getIrmsFieldName()).append(",");
																valuesql.append(sub_attrValue).append("','");
															}else if(eField.getIrmsFieldName().toUpperCase().equals("AREA_ID")){
//																areaname = eField.getIrmsFieldName();
//																areaname_value = sub_attrValue;
																continue;
															}else{
																objsql.append(eField.getIrmsFieldName()).append(",");
																valuesql.append(sub_attrValue).append("','");
															}
														}
													}else{
														continue;
													}
												}
											}else{
												continue;
											}
										}
									}
									if(objsql.toString().length() > 0 && valuesql.toString().length() >0){
										String sql = "";
										if(irmsclassname.toUpperCase().equals("PNR_USER")){//人员
											sql = "select tree3.id,tree3.node_id tree_node_id,tree2.node_id DEPT_ID,tree1.node_id AREA_ID,station.region region,station.city city" +
													" from pnr_areadepttree tree1,pnr_areadepttree tree2," +
													"pnr_areadepttree tree3,pnr_baseinfo_station station  " +
													"where tree2.parent_node_id=tree1.node_id " +
													"and tree3.parent_node_id=tree2.node_id  and tree2.nodename='"+depid_value+"' " +
													"and station.station_name='"+station_value+"' and tree3.node_type = 'user' ";
											AreaDep ad = dao.selectAreaDep(sql);
											objsql.append("TREE_NODE_ID").append(",").append("STATION")
											      .append(",").append("DEPT_ID").append(",").append("AREA_ID").append(",").append("CITY").append(",");
											valuesql.append(ad.getTreenodid()).append("','")
											        .append(station_value).append("','").append(ad.getDepid()).append("','")
											        .append(ad.getAreaid()).append("','").append(ad.getCity()).append("','");
										}else if(irmsclassname.toUpperCase().equals("PNR_PARTNER_APPARATUS")){//仪器仪表
											sql = "select tree3.id,tree3.node_id tree_node_id,tree2.node_id DEPT_ID,tree1.node_id AREA_ID,station.region region,station.city city" +
													"  from pnr_areadepttree tree1," +
													"pnr_areadepttree tree2,pnr_areadepttree tree3,pnr_baseinfo_station station  " +
													"where tree2.parent_node_id=tree1.node_id " +
													"and tree3.parent_node_id=tree2.node_id  and tree2.nodename='"+depid_value+"' " +
													"and station.station_name='"+station_value+"' and tree3.node_type = 'instrument' ";
											AreaDep ad = dao.selectAreaDep(sql);
											objsql.append("STORAGE") .append(",").append("DEPT_ID").append(",").append("AREA_ID").append(",");
											valuesql.append(station_value).append("','").append(ad.getDepid()).append("','").append(ad.getAreaid()).append("','");
										}else if(irmsclassname.toUpperCase().equals("PNR_PARTNER_OIL")){//油机
											sql = "select tree3.id,tree3.node_id tree_node_id,tree2.node_id DEPT_ID,tree1.node_id AREA_ID,station.region region,station.city city" +
													"  from pnr_areadepttree tree1,pnr_areadepttree tree2" +
												   ",pnr_areadepttree tree3,pnr_baseinfo_station station  " +
												   "where   tree2.parent_node_id=tree1.node_id " +
												   "and tree3.parent_node_id=tree2.node_id  and tree2.nodename='"+depid_value+"' " +
												   "and station.station_name='"+station_value+"' and tree3.node_type = 'oil' ";
											AreaDep ad = dao.selectAreaDep(sql);
											objsql.append("STATION").append(",").append("DEPT_ID").append(",").append("AREA_ID").append(",");
											valuesql.append(station_value).append("','").append(ad.getDepid()).append("','").append(ad.getAreaid()).append("','");
										}else if(irmsclassname.toUpperCase().equals("PNR_PARTNER_CAR")){//车辆
											sql = "select tree3.id,tree3.node_id tree_node_id,tree2.node_id DEPT_ID,tree1.node_id AREA_ID,station.region region,station.city city" +
													"  from pnr_areadepttree tree1,pnr_areadepttree tree2," +
													"pnr_areadepttree tree3,pnr_baseinfo_station station  " +
													"where  tree2.parent_node_id=tree1.node_id " +
													"and tree3.parent_node_id=tree2.node_id  and tree2.nodename='"+depid_value+"' " +
													"and station.station_name='"+station_value+"' and tree3.node_type = 'car' ";
											AreaDep ad = dao.selectAreaDep(sql);
											objsql.append("STATION").append(",").append("DEPT_ID").append(",").append("AREA_ID").append(",");
											valuesql.append(station_value).append("','").append(ad.getDepid()).append("','").append(ad.getAreaid()).append("','");
										}else if(irmsclassname.toUpperCase().equals("PNR_DEPT")){
											objsql.append("CREATE_TIME").append(",");
											valuesql = valuesql.deleteCharAt(valuesql.lastIndexOf("'"));
											valuesql.append("to_date('").append(StaticMethod.getCurrentDateTime()).append("','YYYY-MM-DD HH24:MI:SS')").append(",'");
																
									
											if(name_value.equals("") || name_value==null || name_value.equals("") || name_value==null){
												objsql.append("NAME").append(",").append("AREA_NAME").append(",");
												valuesql.append(name_value).append("','").append(areaname_value).append("','");
											}else{
												AreaDeptTreeMgr adtm = (AreaDeptTreeMgr)ApplicationContextHolder.getInstance().getBean("areaDeptTreeMgr");
												AreaDeptTree adt = adtm.addDeptTree("1",name_value,area_id,interfaceHeadId_value);
												objsql.append("TREE_ID").append(",").append("TREE_NODE_ID").append(",").append("NAME").append(",").append("AREA_NAME").append(",").append("AREA_ID").append(",");
												valuesql.append(adt.getId()).append("','").append(adt.getNodeId()).append("','").append(name_value).append("','").append(areaname_value).append("','").append(area_id).append("','");
											}
										}
										String s2 = valuesql.deleteCharAt(valuesql.deleteCharAt(valuesql.lastIndexOf(",")).lastIndexOf("'")).append(")").toString();
										String s1= objsql.deleteCharAt(objsql.lastIndexOf(",")).append(")").append(s2).toString();
										//log.info(irmsclassname + " 插入的sql = " + s1);
										resultMap.put(irmsclassname, s1);
										xml++;
									}
									if(xmlMap.get(int_id)==null){
										xmlMap.put(int_id, resultMap);
									}
								}
							} else {
								log.error("FJ_EDISDateSchema.xls没有定义["
										+ classname + "]表名");
							}
						}
					}
				}
			}
		} catch (Exception e) {
			throw new Exception("解析数据文件失败！" + e.getMessage() + "  " + datafile);
		}

		return xmlMap;
	}
	
	public void lFeedback(String eventid, int endstatuscode,
			String endstatusdescription, String url) {
		PartnerWebserviceBO bo = new PartnerWebserviceBO();
		bo.feedback(eventid, endstatuscode, endstatusdescription, url);
	}

//	 <接口单元编码><YYYYMMDDHH24MISS><重试号（二位）>
	public boolean checkFile(String checkfilename) throws Exception {
		boolean fileOK = true;
		// 判断校验文件是否遵守命名规范
		try {
			// 定义指向专业网管本次上传文件的地址（说明：根据接口文档上传文件的后缀名为“.CHK”）
			String filePath = localdir + File.separatorChar+ checkfilename
					+ ".CHK";
			log.info("filePath:" + filePath);
			File file = new File(filePath);
			FileReader fr;
			fr = new FileReader(file);
			BufferedReader in = new BufferedReader(fr);
			String line = "";
			String avai = "";
			while ((line = in.readLine()) != null) {
				// 循环里逐行读取到字符串line
				// str依次存储（长度）：接口数据文件名称（40） 文件的大小（20） 文件中包含的记录数（20） 文件的md5（129）
				String[] str = new String[4];
				str[0] = line.substring(0, line.indexOf(" "));
				// kyx 修改，数据文件下载
				if (str[0].length() > 0) {
					FtpZipOption ftpOption = new FtpZipOption();
					int filesize = ftpOption.downloadFtpFile(localdir, str[0]);
					log.info("下载本地文件大小：" + filesize);
				}
				line = line.substring(40);
				str[1] = line.substring(0, line.indexOf(" "));
				line = line.substring(20);
				str[2] = line.substring(0, line.indexOf(" "));
				line = line.substring(20);
				if (line.indexOf(" ") != -1) {
					str[3] = line.substring(0, line.indexOf(" "));
				} else {
					str[3] = line;
				}
				log.info("文件名：" + str[0] + " 文件长度：" + str[1] + " 记录条数："
						+ str[2] + " MD5：" + str[3]);
				boolean isok = checkoutFile(str[0], Integer.parseInt(str[1]),
						Integer.parseInt(str[2]), str[3]);
				if (isok) {
					log.info("校验文件" + str[0] + "成功与否：" + isok);
					avai += str[0] + "><";
				} else {
					throw new Exception("校验文件失败，文件格式不符合规则！");
				}
			}
			avaifile = avai;
//			log.info("avaifile = " + avaifile);
		} catch (Exception e) {
			throw new Exception("校验文件失败，失败原因" + e.getMessage());
		}
		return fileOK;
	}

	private boolean checkoutFile(String filename, int filelength,
			int recordnum, String md5code) throws Exception {
		boolean b = false;
		try {
			String filedir = localdir + File.separatorChar;
			File filedire = new File(filedir);
			File[] filelist = filedire.listFiles();
			// 第一步：判断校验文件中记录的数据文件是否存在
			log.info("正在判断的文件名" + filename);
			for (int j = 0; j < filelist.length; j++) {
				if (filelist[j].getName().toUpperCase().indexOf(
						filename.toUpperCase()) == -1) {
					continue;
				} else {
					log.info("校验文件中记录的数据文件" + filename + "存在");
					// 第二步：判断校验文件中记录的数据文件大小是否正确
					String tempfile = filedir + filename;
					// log.info("tempfile is " + tempfile);
					File temp = new File(tempfile);
					int tempfilelength = (int) temp.length();
					if (tempfilelength == filelength) {
						log.info("校验文件中记录的数据文件[" + filename + "]大小与数据文件实际大小"
								+ tempfilelength + "一致！");
						// 第三步：判断校验文件中记录的数据文件的MD5是否正确
						MD5Util mdbo = new MD5Util();
						boolean checkmd5 = mdbo.checkFileMD5(tempfile, md5code);
						if (checkmd5){
							return true;
						}else{
							throw new Exception("校验文件中记录的数据文件[" + filename + "]MD5与数据文件实际MD5不一致！");
						}
					} else {
						throw new Exception("校验文件中记录的数据文件[" + filename
								+ "]大小与数据文件实际大小" + tempfilelength + "不一致！");
					}
				}
			}
		} catch (Exception e) {
			throw new Exception("校验文件失败,失败原因：" + e.getMessage());
		}
		return b;
	}
	
	/**
	 * 解析数据消息
	 * 
	 * @param systemID
	 * @param eventid
	 * @param datainfo
	 * @param feedbackuri
	 * @param loadingFlag
	 * @throws Exception
	 */
	public void ConvertMES(String systemID, String eventid, String datainfo,
			String feedbackuri, int loadingFlag) {
		// 步骤一：解析数据信息
		try {
			log.info("准备开始解析专业网管["+systemID +"]上传的消息数据 datainfo:" + datainfo);
			convertString(datainfo, loadingFlag,systemID);
			// 步骤三：根据数据提供方提供装载反馈接口与否，调用相应流程
			this.feedBack(eventid, 1, "装载任务成功", feedbackuri);
		} catch (Exception e) {
			e.printStackTrace();
			this.feedBack(eventid, 3, "数据提供方提供数据有误导致装载任务失败", feedbackuri);
		}

	}

	/**
	 * 解析数据消息的字符串
	 * 
	 * @throws Exception
	 */
	public void convertString(String datainfo, int loadingFlag,String systemID)
			throws Exception {
		try {
			PartnerConvertDAO dao = new PartnerConvertDAO();
			log.info("将XML格式的字符串转换为Document");
			Document document = DocumentHelper.parseText(datainfo);
			List list = document.selectNodes("//resource-data//class");
			Element root = document.getRootElement();
			for (Iterator iter = root.elementIterator(); iter.hasNext();) {
				Element element = (Element) iter.next();
				if(element.getName().toUpperCase().equals("NOTIFYID") && (systemID.equals("") || systemID == null)){
					systemID = element.getTextTrim().toUpperCase().toString();
					break;
				}
			}
			Map<String,Map<String,String>> map = this.loadingXmlToBean(list);
			dao.updateResult(map);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("解析数据消息的字符串异常！" + e.getMessage());
		}
	}
	
	/**
	 * 根据XML节点中的元素的值加载某个对象
	 * 
	 * @param list
	 * @param irmsConvertDAO
	 * @param controlInfo
	 * @throws Exception
	 */
	public Map<String,Map<String,String>> loadingXmlToBean(List list) throws Exception {
		String className = "";
		String irmsclassname = "";
		String depid="";
		String station="";
		String depid_value="";
		String station_value="";
		String name="";
		String name_value="";
		String areaname="";
		String areaname_value="";
		String area_id="";
		String interfaceHeadId="";
		String interfaceHeadId_value="";
		Map<String, String> int_idTableNameMap = eStructure.localclassidIrmsclassidMap;
		 Map<String, String> fntopartnerMap = eStructure.fntopartnerMap;
		Map<String,Map<String,String>> xmlMap = new HashMap<String,Map<String,String>>();
		Map<String,String> areamap = dao.selectAreaMap();
		Map<String,String> deptmap = dao.selectProviderNameMap();
		Map<String,String> stationmap = dao.selectstationNameMap();
		try {
			Iterator iteratorClass = list.iterator();
			while (iteratorClass.hasNext()) {
				Element element = (Element) iteratorClass.next();
				Iterator iterator = element.elementIterator();
				Map<String,String> resultMap = new HashMap<String,String>();
				String int_id = "";
				String updateType = "";
				String resourceId = "";
				String classTempName = "";
				ExcelStructureTable eTable = null;
				StringBuffer addsql = new StringBuffer();
				StringBuffer addvaluesql = new StringBuffer();
				StringBuffer delsql = new StringBuffer();
				StringBuffer updsql = new StringBuffer();
				while (iterator.hasNext()) {
					Element childElement = (Element) iterator.next();
					if (childElement.getName().toUpperCase().equalsIgnoreCase("RESOURCEID")) {
						resourceId = childElement.getTextTrim().toUpperCase();
						int_id = resourceId.substring(resourceId.lastIndexOf("-")+1,resourceId.length());
					} else if (childElement.getName().toUpperCase().equalsIgnoreCase("UPDATETYPE")) {
						updateType = childElement.getTextTrim();
					} else if (childElement.getName().toUpperCase().equalsIgnoreCase("RESOURCEKIND")) {
						String resourceKind = childElement.getTextTrim();
						className = resourceKind.toUpperCase();
					    irmsclassname = int_idTableNameMap.get(className) == null ? className : int_idTableNameMap.get(className).toString();
						eTable = (ExcelStructureTable) eStructure.getTableMap().get(irmsclassname);
						if (eTable == null) {
							log.info("EXCEL中找不到对应的网元类型。" + classTempName);
							continue;
						} else{
							addsql.append("insert into ").append(irmsclassname).append("(");
							addvaluesql.append(" values ('");
							delsql.append("delete ").append(irmsclassname).append(" t where t.id = '");
							updsql.append("update ").append(irmsclassname).append(" t set ");
						}
					} else if (childElement.getName().toUpperCase().equals("ATTRIBUTES")) {
						Iterator iterator2 = childElement.elementIterator();
						int i=0;
						while (iterator2.hasNext()) {
							Element childAttribute = (Element) iterator2.next();
							Attribute sub_key_Attr = childAttribute.attribute("name");
							Attribute sub_val_Attr = childAttribute.attribute("value");
							String sub_attrName = sub_key_Attr.getValue().toUpperCase().trim();
							String sub_attrValue = sub_val_Attr.getValue().trim();
							if(sub_attrName.equals("REGION")){
								area_id=sub_attrValue;
							}
							//area_id=sub_attrValue;
							if(!sub_attrName.equals("ID")){
								ExcelStructureFieldMap eField = eTable.getFieldMap().get(sub_attrName);
								if(eField != null){
									if(sub_attrValue != null && !sub_attrValue.equals("") && !sub_attrValue.equals("null")){
										String type = eField.getIrmsFieldType();
										if(updateType.equals("1")){
											if(type.toUpperCase().equals("DATE")){
												addsql.append(eField.getIrmsFieldName()).append(",");
												addvaluesql = addvaluesql.deleteCharAt(addvaluesql.lastIndexOf("'"));
												addvaluesql.append("to_date('").append(sub_attrValue).append("','YYYY-MM-DD HH24:MI:SS')").append(",'");
											}else if(!eField.getDatanmsFieldCheck().equals("") && irmsclassname.toUpperCase().equals("PNR_DEPT")){
												areaname = eField.getIrmsFieldName();
												areaname_value = areamap.get(sub_attrValue) == null ? sub_attrValue : areamap.get(sub_attrValue).toString();
											}else if(!eField.getDatanmsFieldCheck().equals("") && !irmsclassname.toUpperCase().equals("PNR_DEPT")){
												String value = fntopartnerMap.get(sub_attrValue) == null ? sub_attrValue : fntopartnerMap.get(sub_attrValue).toString();
												addsql.append(eField.getIrmsFieldName()).append(",");
												addvaluesql.append(value).append("','");
											}else{
												if(eField.getIrmsFieldName().toUpperCase().equals("DEPT_NAME") || eField.getIrmsFieldName().toUpperCase().equals("DEPT_ID")){
													depid = eField.getIrmsFieldName();
													depid_value = deptmap.get(sub_attrValue) == null ? sub_attrValue : deptmap.get(sub_attrValue).toString();
												}else if(eField.getIrmsFieldName().toUpperCase().equals("STATION") || eField.getIrmsFieldName().toUpperCase().equals("STORAGE")){
													station = eField.getIrmsFieldName();
													station_value = stationmap.get(sub_attrValue) == null ? sub_attrValue : stationmap.get(sub_attrValue).toString();
												}else if(eField.getIrmsFieldName().toUpperCase().equals("NAME")&&(!irmsclassname.toUpperCase().equals("PNR_USER"))){
													name = eField.getIrmsFieldName();
													name_value = sub_attrValue;
												}else if(eField.getIrmsFieldName().toUpperCase().equals("INTERFACE_HEAD_ID")){
													interfaceHeadId = eField.getIrmsFieldName();
													interfaceHeadId_value = sub_attrValue;
													addsql.append(eField.getIrmsFieldName()).append(",");
													addvaluesql.append(sub_attrValue).append("','");
												}else if(eField.getIrmsFieldName().toUpperCase().equals("AREA_ID")){
//													areaname = eField.getIrmsFieldName();
//													areaname_value = sub_attrValue;
													continue;
												}else{
													addsql.append(eField.getIrmsFieldName()).append(",");
													addvaluesql.append(sub_attrValue).append("','");
												}
											}
										}else if(updateType.equals("2")){
											if(i==0){
												delsql.append(int_id).append("',");
												i++;
											}else if(!eField.getDatanmsFieldCheck().equals("") && irmsclassname.toUpperCase().equals("PNR_DEPT")){
												areaname = eField.getIrmsFieldName();
												areaname_value = areamap.get(sub_attrValue) == null ? sub_attrValue : areamap.get(sub_attrValue).toString();
											}else if(eField.getIrmsFieldName().toUpperCase().equals("NAME")){
												name = eField.getIrmsFieldName();
												name_value = sub_attrValue;
											}else if(eField.getIrmsFieldName().toUpperCase().equals("INTERFACE_HEAD_ID")){
												interfaceHeadId = eField.getIrmsFieldName();
												interfaceHeadId_value = sub_attrValue;
												System.out.println("+++++++++++++++++"+interfaceHeadId);
												addsql.append(eField.getIrmsFieldName()).append(",");
												addvaluesql.append(sub_attrValue).append("','");
											}else{
												continue;
											}
										}else if(updateType.equals("3")){
											if(type.toUpperCase().equals("DATE")){
												updsql.append(eField.getIrmsFieldName()).append(" = ")
												.append("to_date('").append(sub_attrValue).append("','YYYY-MM-DD HH24:MI:SS')").append(",");
											}else if(!eField.getDatanmsFieldCheck().equals("") && irmsclassname.toUpperCase().equals("PNR_DEPT")){
												areaname = eField.getIrmsFieldName();
												areaname_value = areamap.get(sub_attrValue) == null ? sub_attrValue : areamap.get(sub_attrValue).toString();
											}else if(!eField.getDatanmsFieldCheck().equals("") && !irmsclassname.toUpperCase().equals("PNR_DEPT")){
												String value = fntopartnerMap.get(sub_attrValue) == null ? sub_attrValue : fntopartnerMap.get(sub_attrValue).toString();
												updsql.append(eField.getIrmsFieldName()).append(" = '").append(value).append("',");
											}else{
												if(eField.getIrmsFieldName().toUpperCase().equals("DEPT_NAME") || eField.getIrmsFieldName().toUpperCase().equals("DEPT_ID")){
													depid = eField.getIrmsFieldName();
													depid_value = deptmap.get(sub_attrValue) == null ? sub_attrValue : deptmap.get(sub_attrValue).toString();
												}else if(eField.getIrmsFieldName().toUpperCase().equals("STATION") || eField.getIrmsFieldName().toUpperCase().equals("STORAGE")){
													station = eField.getIrmsFieldName();
													station_value = stationmap.get(sub_attrValue) == null ? sub_attrValue : stationmap.get(sub_attrValue).toString();
												}else if(eField.getIrmsFieldName().toUpperCase().equals("NAME")){
													name = eField.getIrmsFieldName();
													name_value = sub_attrValue;
												}else if(eField.getIrmsFieldName().toUpperCase().equals("INTERFACE_HEAD_ID")){
													interfaceHeadId = eField.getIrmsFieldName();
													interfaceHeadId_value = sub_attrValue;
													updsql.append(eField.getIrmsFieldName()).append(" = '").append(sub_attrValue).append("',");
												}else if(eField.getIrmsFieldName().toUpperCase().equals("AREA_ID")){
//													areaname = eField.getIrmsFieldName();
//													areaname_value = sub_attrValue;
													continue;
												}else{
													updsql.append(eField.getIrmsFieldName()).append(" = '").append(sub_attrValue).append("',");
												}
											}
										}
									}else{
										continue;
									}
								}
							}else{
								continue;
							}
						}
					}
				}
				if(addsql.lastIndexOf(",") > 0){
					addsql.append("ID").append(",");
					addvaluesql.append(int_id).append("','");
					String sql = "";
					if(irmsclassname.toUpperCase().equals("PNR_USER")){
						sql = "select tree3.id,tree3.node_id tree_node_id,tree2.node_id DEPT_ID,tree1.node_id AREA_ID,station.region region,station.city city" +
								" from pnr_areadepttree tree1,pnr_areadepttree tree2," +
								"pnr_areadepttree tree3,pnr_baseinfo_station station  " +
								"where tree2.parent_node_id=tree1.node_id " +
								"and tree3.parent_node_id=tree2.node_id  and tree2.nodename='"+depid_value+"' " +
								"and station.station_name='"+station_value+"' and tree3.node_type = 'user' ";
						AreaDep ad = dao.selectAreaDep(sql);
						addsql.append("TREE_NODE_ID").append(",").append("STATION")
						      .append(",").append("DEPT_ID").append(",").append("AREA_ID").append(",").append("CITY").append(",");
						addvaluesql.append(ad.getTreenodid()).append("','")
						        .append(station_value).append("','").append(ad.getDepid()).append("','")
						        .append(ad.getAreaid()).append("','").append(ad.getCity()).append("','");
					}else if(irmsclassname.toUpperCase().equals("PNR_PARTNER_APPARATUS")){
						sql = "select tree3.id,tree3.node_id tree_node_id,tree2.node_id DEPT_ID,tree1.node_id AREA_ID,station.region region,station.city city" +
								"  from pnr_areadepttree tree1," +
								"pnr_areadepttree tree2,pnr_areadepttree tree3,pnr_baseinfo_station station  " +
								"where and tree2.parent_node_id=tree1.node_id " +
								"and tree3.parent_node_id=tree2.node_id  and tree2.nodename='"+depid_value+"' " +
								"and station.station_name='"+station_value+"' and tree3.node_type = 'instrument' ";
						AreaDep ad = dao.selectAreaDep(sql);
						addsql.append("STORAGE") .append(",").append("DEPT_ID").append(",").append("AREA_ID").append(",");
						addvaluesql.append(station_value).append("','").append(ad.getDepid()).append("','").append(ad.getAreaid()).append("','");
					}else if(irmsclassname.toUpperCase().equals("PNR_PARTNER_OIL")){
						sql = "select tree3.id,tree3.node_id tree_node_id,tree2.node_id DEPT_ID,tree1.node_id AREA_ID,station.region region,station.city city" +
								"  from pnr_areadepttree tree1,pnr_areadepttree tree2" +
							   ",pnr_areadepttree tree3,pnr_baseinfo_station station  " +
							   "where tree2.parent_node_id=tree1.node_id " +
							   "and tree3.parent_node_id=tree2.node_id  and tree2.nodename='"+depid_value+"' " +
							   "and station.station_name='"+station_value+"' and tree3.node_type = 'instrument' ";
						AreaDep ad = dao.selectAreaDep(sql);
						addsql.append("STATION").append(",").append("DEPT_ID").append(",").append("AREA_ID").append(",");
						addvaluesql.append(station_value).append("','").append(ad.getDepid()).append("','").append(ad.getAreaid()).append("','");
					}else if(irmsclassname.toUpperCase().equals("PNR_PARTNER_CAR")){
						sql = "select tree3.id,tree3.node_id tree_node_id,tree2.node_id DEPT_ID,tree1.node_id AREA_ID,station.region region,station.city city" +
								"  from pnr_areadepttree tree1,pnr_areadepttree tree2," +
								"pnr_areadepttree tree3,pnr_baseinfo_station station  " +
								"where tree2.parent_node_id=tree1.node_id " +
								"and tree3.parent_node_id=tree2.node_id  and tree2.nodename='"+depid_value+"' " +
								"and station.station_name='"+station_value+"' and tree3.node_type = 'car' ";
						AreaDep ad = dao.selectAreaDep(sql);
						addsql.append("STATION").append(",").append("DEPT_ID").append(",").append("AREA_ID").append(",");
						addvaluesql.append(station_value).append("','").append(ad.getDepid()).append("','").append(ad.getAreaid()).append("','");
					}else if(irmsclassname.toUpperCase().equals("PNR_DEPT")){
						if(name_value.equals("") || name_value==null || name_value.equals("") || name_value==null){
							addsql.append("NAME").append(",").append("AREA_NAME").append(",");
							addvaluesql.append(name_value).append("','").append(areaname_value).append("','");
						}else{
							AreaDeptTreeMgr adtm = (AreaDeptTreeMgr)ApplicationContextHolder.getInstance().getBean("areaDeptTreeMgr");
							AreaDeptTree adt = adtm.addDeptTree("1",name_value,area_id,interfaceHeadId_value);
							addsql.append("TREE_ID").append(",").append("TREE_NODE_ID").append(",").append("NAME").append(",").append("AREA_NAME").append(",");
							addvaluesql.append(adt.getId()).append("','").append(adt.getNodeId()).append("','").append(name_value).append("','").append(areaname_value).append("','");
						}
					}
					String s2 = addvaluesql.deleteCharAt(addvaluesql.deleteCharAt(addvaluesql.lastIndexOf(",")).lastIndexOf("'")).append(")").toString();
					String s1= addsql.deleteCharAt(addsql.lastIndexOf(",")).append(")").append(s2).toString();
					resultMap.put(irmsclassname, s1);
				}
				if(updsql.lastIndexOf(",") > 0){
					String sql = "";
					if(irmsclassname.toUpperCase().equals("PNR_USER")){
						sql = "select tree3.id,tree3.node_id tree_node_id,tree2.node_id DEPT_ID,tree1.node_id AREA_ID,station.region region,station.city city" +
								" from pnr_areadepttree tree1,pnr_areadepttree tree2," +
								"pnr_areadepttree tree3,pnr_baseinfo_station station  " +
								"where tree2.parent_node_id=tree1.node_id " +
								"and tree3.parent_node_id=tree2.node_id  and tree2.nodename='"+depid_value+"' " +
								"and station.station_name='"+station_value+"' and tree3.node_type = 'user' ";
						AreaDep ad = dao.selectAreaDep(sql);
						updsql.append("TREE_NODE_ID").append("='").append(ad.getTreenodid()).append("',")
						      .append("STATION").append("='").append(station_value).append("',")
						      .append("DEPT_ID").append("='").append(ad.getDepid()).append("',")
						      .append("AREA_ID").append("='").append(ad.getAreaid()).append("',")
						      .append("CITY").append("='").append(ad.getCity()).append("',");
					}else if(irmsclassname.toUpperCase().equals("PNR_PARTNER_APPARATUS")){
						sql = "select tree3.id,tree3.node_id tree_node_id,tree2.node_id DEPT_ID,tree1.node_id AREA_ID,station.region region,station.city city" +
								"  from pnr_areadepttree tree1," +
								"pnr_areadepttree tree2,pnr_areadepttree tree3,pnr_baseinfo_station station  " +
								"where tree2.parent_node_id=tree1.node_id " +
								"and tree3.parent_node_id=tree2.node_id  and tree2.nodename='"+depid_value+"' " +
								"and station.station_name='"+station_value+"' and tree3.node_type = 'instrument' ";
						AreaDep ad = dao.selectAreaDep(sql);
						updsql.append("STORAGE").append("='").append(station_value).append("',")
					      .append("DEPT_ID").append("='").append(ad.getDepid()).append("',")
					      .append("AREA_ID").append("='").append(ad.getAreaid()).append("',");
					}else if(irmsclassname.toUpperCase().equals("PNR_PARTNER_OIL")){
						sql = "select tree3.id,tree3.node_id tree_node_id,tree2.node_id DEPT_ID,tree1.node_id AREA_ID,station.region region,station.city city" +
								"  from pnr_areadepttree tree1,pnr_areadepttree tree2" +
							   ",pnr_areadepttree tree3,pnr_baseinfo_station station  " +
							   "where tree2.parent_node_id=tree1.node_id " +
							   "and tree3.parent_node_id=tree2.node_id  and tree2.nodename='"+depid_value+"' " +
							   "and station.station_name='"+station_value+"' and tree3.node_type = 'instrument' ";
						AreaDep ad = dao.selectAreaDep(sql);
						updsql.append("STATION").append("='").append(station_value).append("',")
					      .append("DEPT_ID").append("='").append(ad.getDepid()).append("',")
					      .append("AREA_ID").append("='").append(ad.getAreaid()).append("',");
					}else if(irmsclassname.toUpperCase().equals("PNR_PARTNER_CAR")){
						sql = "select select tree3.id,tree3.node_id tree_node_id,tree2.node_id DEPT_ID,tree1.node_id AREA_ID,station.region region,station.city city" +
								"  from pnr_areadepttree tree1,pnr_areadepttree tree2," +
								"pnr_areadepttree tree3,pnr_baseinfo_station station  " +
								"where tree2.parent_node_id=tree1.node_id " +
								"and tree3.parent_node_id=tree2.node_id  and tree2.nodename='"+depid_value+"' " +
								"and station.station_name='"+station_value+"' and tree3.node_type = 'car' ";
						AreaDep ad = dao.selectAreaDep(sql);
						updsql.append("STATION").append("='").append(station_value).append("',")
					          .append("DEPT_ID").append("='").append(ad.getDepid()).append("',")
					          .append("AREA_ID").append("='").append(ad.getAreaid()).append("',");
					}else if(irmsclassname.toUpperCase().equals("PNR_DEPT")){
						if(name_value.equals("") || name_value==null || name_value.equals("") || name_value==null){
							updsql.append("NAME").append("='").append(name_value).append("',")
						          .append("AREA_NAME").append("='").append(areaname_value).append("',");
						}else{
						//暂时还没实现
//						 	AreaDeptTreeMgr adtm = (AreaDeptTreeMgr)ApplicationContextHolder.getInstance().getBean("areaDeptTreeMgr");
//							AreaDeptTree adt = adtm.addDeptTree("3",name_value,areaname_value);
//							updsql.append("TREE_ID").append("='").append(adt.getId()).append("',")
//					      	      .append("TREE_NODE_ID").append("='").append(adt.getNodeId()).append("',")
							updsql.append("NAME").append("='").append(name_value).append("',")
								  .append("AREA_NAME").append("='").append(areaname_value).append("',");
						}
					}
					resultMap.put(irmsclassname, updsql.deleteCharAt(updsql.lastIndexOf(",")).append(" where id='").append(int_id).append("'").toString());
				}
				if(delsql.lastIndexOf(",") > 0){
					if(irmsclassname.toUpperCase().equals("PNR_DEPT")){
						if(!name_value.equals("") && !name_value.equals("")){
							AreaDeptTreeMgr adtm = (AreaDeptTreeMgr)ApplicationContextHolder.getInstance().getBean("areaDeptTreeMgr");
							AreaDeptTree adt = adtm.addDeptTree("2",name_value,area_id,interfaceHeadId_value);
						}
					}
					resultMap.put(irmsclassname,delsql.deleteCharAt(delsql.indexOf(",")).toString());
				}
				if(xmlMap.get(int_id+";"+updateType)==null){
					xmlMap.put(int_id+";"+updateType, resultMap);
				}
			}
		} catch (Exception e) {
			throw new Exception("解析数据文件失败.失败原因:"+e.getMessage());
		}
		return xmlMap;
	}

	public static boolean unZip(String zipFile, String outFilePath, int mode)
			throws Exception {
		boolean flag = false;
		try {
			File file = new File(zipFile);
			String fileName = file.getName();
			if (mode == 1) {
				outFilePath += File.separator; // 文件当前路径下
			} else {
				outFilePath += File.separator
						+ fileName.substring(0, fileName.length() - 4)
						+ File.separator;
			}
			File tmpFileDir = new File(outFilePath);
			tmpFileDir.mkdirs();

			ZipFile zf = new ZipFile(zipFile);
			FileOutputStream fos;

			byte[] buf = new byte[1024];
			for (Enumeration em = zf.entries(); em.hasMoreElements();) {
				ZipEntry ze = (ZipEntry) em.nextElement();
				if (ze.isDirectory()) {
					continue;
				}
				DataInputStream dis = new DataInputStream(zf.getInputStream(ze));
				String currentFileName = ze.getName();
				int dex = currentFileName.lastIndexOf('/');
				String currentoutFilePath = outFilePath;
				if (dex > 0) {
					currentoutFilePath += currentFileName.substring(0, dex)
							+ File.separator;
					File currentFileDir = new File(currentoutFilePath);
					currentFileDir.mkdirs();
				}
				fos = new FileOutputStream(outFilePath + ze.getName());
				int readLen = 0;
				while ((readLen = dis.read(buf, 0, 1024)) > 0) {
					fos.write(buf, 0, readLen);
				}
				dis.close();
				fos.close();
			}
			flag = true;
		} catch (Exception e) {
			throw new Exception("解压文件失败！失败原因:" + e.getMessage());
		}
		return flag;
	}

	public String UPoper() {
		String info = "";
//		MQMessageOutput put = new MQMessageOutput();
//		info = put.GetMessageToMQ();
		return info;
	}

	public static void main(String args[]) {
//		StringBuffer changeAttribute = new StringBuffer();
//		changeAttribute.append("234");
//		changeAttribute.deleteCharAt(changeAttribute.length() - 1);
		PartnerConvertBO bo = new PartnerConvertBO();
		try {
			SAXReader saxReader = new SAXReader();
			File file = new File("D:\\my.xml");
			Document document = saxReader.read(file);
			String dataInfo = document.asXML();
			bo.initBO();
			bo.convertString(dataInfo,2,"");
//			bo.initBO();
//			String pathdir ="d:\\logs\\partner.properties";// StaticContext.getServletContext().getRealPath("WEB-INF/classes/resources/partner.properties");
//			File file=new File(pathdir);
//			try {
//				RequestConfig.init(file);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			bo.ConvertXML("boco", "NBRMS20091229020016", "", 1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
