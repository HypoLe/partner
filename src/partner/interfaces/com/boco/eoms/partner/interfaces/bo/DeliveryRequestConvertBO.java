package com.boco.eoms.partner.interfaces.bo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.xml.rpc.ServiceException;
import javax.xml.rpc.holders.StringHolder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.boco.eoms.partner.interfaces.common.init.FtpZipOption;
import com.boco.eoms.partner.interfaces.common.init.MD5Util;
import com.boco.eoms.partner.interfaces.common.init.RequestConfig;
import com.boco.eoms.partner.interfaces.common.init.StaticContext;
import com.boco.eoms.partner.interfaces.dao.JDBCManager;
import com.boco.eoms.partner.interfaces.dao.PartnerConvertDAO;
import com.boco.eoms.partner.interfaces.dto.ExcelStructure;
import com.boco.eoms.partner.interfaces.dto.ExcelStructureFieldMap;
import com.boco.eoms.partner.interfaces.dto.ExcelStructureTable;
import com.boco.eoms.partner.interfaces.services.LoadingRequest.LoadingRequestLocator;
import com.boco.eoms.partner.interfaces.services.LoadingRequest.LoadingRequestPortType;
import com.boco.eoms.partner.serviceArea.model.Grid;

/**
 * @author keyaxin
 * 
 */
public class DeliveryRequestConvertBO{
	private final static Log log = LogFactory.getLog(DeliveryRequestConvertBO.class);
    private LoadingRequestPortType LoadingRequestport;
	private Map classNameMap = null;
	private ExcelStructure eStructure = new ExcelStructure();
	private Connection conn = null;
	private Statement st = null;
	private String localdir = StaticContext.getServletContext().getRealPath("WEB-INF/classes/com/boco/eoms/partner/interfaces/resources/file"); 

	public DeliveryRequestConvertBO() {
	}

	public void initBO() {
		String classdir = StaticContext.getServletContext().getRealPath("WEB-INF/classes/com/boco/eoms/partner/interfaces/resources/partner.properties");
		File file=new File(classdir);
		try {
			RequestConfig.init(file);
			//暂时先屏蔽ESB
//			String esbfile = StaticContext.getServletContext().getRealPath("WEB-INF/classes/com/boco/eoms/partner/interfaces/resources/resesb/serviceproxy.ini");
//			ServiceProxy.getInstance().initialize(esbfile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		eStructure.readExcel();
	}

	private String getCurrentDate(String dataFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat(dataFormat, Locale.SIMPLIFIED_CHINESE);
		Date date = new Date();
		return sdf.format(date);
	}

	private String[] createSQL(String filter) {
		String[] filters = filter.split(";");
		int length = filters.length;
		String[] tablenames = new String[length];
		String[] wheres = new String[length];
		String[] SQL = new String[length];
		String blank = " ";
		for (int i = 0; i < filters.length; i++) {
			tablenames[i] = filters[i].substring(0, filters[i].indexOf(".")).toUpperCase();
			wheres[i] = filters[i].substring(filters[i].indexOf(".") + 1, filters[i].length());
			Map<String, ExcelStructureTable> mapExcel = eStructure.getTableMap();
			ExcelStructureTable est = new ExcelStructureTable();

			StringBuffer sql = new StringBuffer();
			if (mapExcel.containsKey(tablenames[i])) {
				est = mapExcel.get(tablenames[i]);
				if(est != null){
					sql.append("select").append(blank);
					Map<String, ExcelStructureFieldMap> fieldMap = est.getPfieldMap();
					for (Map.Entry<String, ExcelStructureFieldMap> entry1 : fieldMap.entrySet()) {
						ExcelStructureFieldMap esf = entry1.getValue();
						if (!esf.getIrmsFieldName().equals("")) {
							if (!esf.getDatanmsFieldCheck().equals("")) {
								sql.append(esf.getSub_sql()).append(" as ").append(esf.getIrmsFieldName())
										.append(",");
							} else {
								sql.append(esf.getIrmsFieldName()).append(",");
							}
						}
					}
					sql.deleteCharAt(sql.lastIndexOf(","));
					sql.append(blank).append("from").append(blank).append(tablenames[i]).append(blank).append("union_table");
	
//					log.info("wheres[i]:" + wheres[i]);
					if (wheres[i].equals("") || wheres[i].equals("null")) {
						sql.append(" where 1=1");
					} else {
						sql.append(blank).append("where").append(blank);
						sql.append(wheres[i]);
					}
				}
			}
			SQL[i] = sql.toString();
		}
		return SQL;

	}

	private int createXMLFile(Document doc, String filter) {
		int irmsdbosNums = 0;
		String localclassname = "";
		Map<String, String> irmslocalMap = eStructure.localclassidIrmsclassidMap;
		try {
			String[] sql = createSQL(filter);
			log.info("开始创建一个document对象");
			Element root = doc.addElement("resource-data");
			root.addAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
			root.addAttribute("xsi:noNamespaceSchemaLocation", "resource-data.xsd");
			conn = JDBCManager.getDataSource(RequestConfig.getUrl(), RequestConfig.getUsername(),
				     RequestConfig.getPassword()).getConnection();;
			for (int i = 0; i < sql.length; i++) {
				log.info("<<<<<<<<SQL=" + sql[i]);
				if(sql[i] != null && !sql[i].equals("")){
					String tablename = sql[i].substring(sql[i].lastIndexOf("from") + 5, sql[i].lastIndexOf("union_table")).trim();
					Element classname = root.addElement("class");
					localclassname = irmslocalMap.get(tablename) == null ? tablename : irmslocalMap.get(tablename).toString();
					classname.addAttribute("name", localclassname);
					ExcelStructureTable est = new ExcelStructureTable();
					Map<String, ExcelStructureTable> mapExcel = eStructure.getTableMap();
					est = mapExcel.get(tablename);
					// 自定义查询
					ResultSet rs2 = this.getAllObjectByWhere(conn, sql[i], 0, 0);
					int size = appendDocumentByResutSet(classname, rs2, est,localclassname);
					irmsdbosNums = irmsdbosNums + size;
//					log.info("tablename:" + tablename);
				}
			}
			return irmsdbosNums;
		} catch (Exception e) {
			log.error("创建一个document对象失败!" + e.getMessage());
			return irmsdbosNums;
		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void writeFixedLengthString(FileWriter fw, String s, int fixedLength) {
		String blank = " ";
		try {
			fw.write(s);
			int count = fixedLength - s.length();
			for (int l = 0; l < count; l++) {
				fw.write(blank);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 创建多个XML文件(增量/全量)(文件的批量分发)
	 * 
	 * @author keyaxin
	 * @throws Exception
	 */
	public String createDocument(String eventID, String filterMany, String filterOne) throws Exception {
		
		String checkFileName = eventID;
		File file = new File(localdir + File.separatorChar + checkFileName + ".CHK");
		FileWriter fw = new FileWriter(file);
		if (filterMany != null && !filterMany.equals("")) {
			this.createManyFileXml(fw, eventID, filterMany);
		}
		if (filterOne != null && !filterOne.equals("")) {
			this.createOneFileXml(fw, eventID, filterOne);
		}
		fw.close();
		String checkfilename = FtpZipOption.upload2Ftp(file.getPath(),RequestConfig.getftphost(),RequestConfig.getftpport(),
				                                       RequestConfig.getftpusername(),RequestConfig.getftppassword(),
				                                       RequestConfig.getftpdedir());
		if (checkfilename != null) {
			file.delete();
		}
		return checkfilename;
	}

	private void formatOutputXmlDocument(Document doc, String encode, String filepath) {
		OutputFormat format = OutputFormat.createPrettyPrint();
		/** 指定XML编码 */
		format.setEncoding(encode);
		// 输出到流中
		try {
			XMLWriter xmlWriter = new XMLWriter(new FileOutputStream(filepath), format);
			xmlWriter.write(doc);
			xmlWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
//		log.info("生成XML文件成功！！！");
	}

	private void writeCheckFileInfo(FileWriter fw, String zippedFileName, int zippedFileName_digit,
			String zippedFilePath, int zippedFileSize_digit, long annalNumbers, int annalNumbers_digit,
			int createMd5_digit) throws IOException {
		// 写入压缩后的数据文件名
//		log.info("写入压缩后的数据文件名：" + zippedFileName);
		writeFixedLengthString(fw, zippedFileName, zippedFileName_digit);
		// 写入压缩后的数据文件字节大小
		File temp = new File(zippedFilePath);
		long sf = temp.length();
//		log.info("写入数据文件字节大小：" + sf);
		writeFixedLengthString(fw, String.valueOf(sf), zippedFileSize_digit);
		// 写入压缩后的数据文件中生成数据条数
//		log.info("写入数据文件生成数据条数：" + annalNumbers);
		writeFixedLengthString(fw, String.valueOf(annalNumbers), annalNumbers_digit);
		// 写入压缩后的数据文件的MD5
		String md5 = null;
		File md5file = new File(zippedFilePath);
		md5 = MD5Util.getFileMD5String(md5file);
//		log.info("写入数据文件的MD5: " + md5);
		writeFixedLengthString(fw, md5, createMd5_digit);
		fw.write("\n");
	}

	private String getAttributeValueMappingString(String checkFieldValue, String DBValue) {
		int fromIndex = checkFieldValue.indexOf(DBValue);
		if (fromIndex < 0) {
			log.error(DBValue + ":的映射关系在关联表的配置中没有配置");
			return "";
		}
		if (checkFieldValue.indexOf(",", fromIndex) > -1) {
			return checkFieldValue.substring(checkFieldValue.indexOf(DBValue) + DBValue.length() + 1,
					checkFieldValue.indexOf(",", fromIndex)).trim();
		} else {
			return checkFieldValue.substring(checkFieldValue.indexOf(DBValue) + DBValue.length() + 1,
					checkFieldValue.indexOf("]", fromIndex)).trim();
		}

	}

	/**
	 * 生成多个XML文件(全量/增量)
	 * 
	 * @param fw
	 * @param eventID
	 * @param filter
	 * @throws Exception
	 */
	private void createManyFileXml(FileWriter fw, String eventID, String filter) {
		try {
			String[] sql = createSQL(filter);
			int count = 0;
			Map<String, String> irmslocalMap = eStructure.irmsclassidlocalclassidMap;
			String localclassname = "";
			for (int i = 0; i < sql.length; i++) {
				conn = JDBCManager.getDataSource(RequestConfig.getUrl(), RequestConfig.getUsername(),
					     RequestConfig.getPassword()).getConnection();
				int offset = 0;
				final int fetchSize = 10000;
				if(sql[i] != null && !sql[i].equals("")){
					while (true) {
						count++;
						log.info("开始创建多个document对象");
						Document doc = DocumentHelper.createDocument();
						Element root = doc.addElement("resource-data");
						root.addAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
						root.addAttribute("xsi:noNamespaceSchemaLocation", "resource-data.xsd");
						Element classname = root.addElement("class");
						String tablename = sql[i].substring(sql[i].lastIndexOf("from") + 5, sql[i].lastIndexOf("union_table")).trim();
						localclassname = irmslocalMap.get(tablename) == null ? tablename : irmslocalMap.get(tablename).toString();
//						log.info("tablename:" + localclassname);
						log.info("<<<<<<<<SQL=" + sql[i]);
	
						classname.addAttribute("name", localclassname);
	
						ExcelStructureTable est = new ExcelStructureTable();
						Map<String, ExcelStructureTable> mapExcel = eStructure.getTableMap();
						est = mapExcel.get(tablename);
	
						// 自定义查询
						ResultSet rs2 = this.getAllObjectByWhere(conn, sql[i], offset, fetchSize);
	
						int size = 0;
						size = appendDocumentByResutSet(classname, rs2, est,localclassname);
//						log.info("xml生成完毕");
						StringBuffer countth = new StringBuffer(String.valueOf(count));
						while (countth.length() < 3) {
							countth = countth.insert(0, "0");
						}
						String filename = localdir + File.separatorChar + eventID + countth;
						String filepath = filename + ".xml";
						formatOutputXmlDocument(doc, "UTF-8", filepath);
	
						// 压缩文件
						File sourceFile = new File(filepath);
						String zippedFilePath = filename + ".zip";// 文件路径
						String zippedFileName = eventID + countth + ".zip";// 文件名
						log.info("压缩前的数据文件名：" + sourceFile.getName() + " 压缩前的数据文件路径：" + sourceFile.getPath());
						createZip(zippedFilePath, new String[] { sourceFile.getPath() });
						// 写入校验压缩文件的信息
						writeCheckFileInfo(fw, zippedFileName, 40, zippedFilePath, 20, size, 20, 129);
						// 上传压缩文件
						log.info("上传第<" + countth + ">个数据文件。");
	
						String createfilename = FtpZipOption.upload2Ftp(zippedFilePath,RequestConfig.getftphost(),RequestConfig.getftpport(),
                                RequestConfig.getftpusername(),RequestConfig.getftppassword(),
                                RequestConfig.getftpdedir());
						if (createfilename != null) {
							sourceFile.delete();
						}
						if (size == fetchSize) {
//							log.info("第" + count + "次查询综合资源库" + tablename + "获得" + size + "条数据!");
							offset += size;
//							log.info("第" + count + "次查询综合资源库" + tablename + "后偏移设为:" + offset);
						} else {
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("创建多个document对象失败!" + e.getMessage());
		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private String getPageSql(String sql, int offset, int fetchSize) {
		StringBuffer buffer = new StringBuffer();
		buffer.append("select * from (select my_table.*, rownum my_rownum from (");
		buffer.append(sql);
		buffer.append(") my_table where rownum <= ");
		buffer.append(fetchSize);
		buffer.append(") where my_rownum > ");
		buffer.append(offset);
		return buffer.toString();
	}

	public ResultSet getAllObjectByWhere(Connection conn, String sql, int offset, int fetchSize) {
		fetchSize+=offset;
		ResultSet rs = null;
		try {
			if(fetchSize > 0){
				sql = this.getPageSql(sql,offset,fetchSize);
				st = conn.createStatement();
			}else {
				st = conn.createStatement();
			}
			rs = st.executeQuery(sql);
			return rs;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	

	/**
	 * 生成XML
	 * 
	 * @param classname
	 * @param rs2
	 * @param est
	 * @return
	 * @throws Exception
	 */
	private int appendDocumentByResutSet(Element classname, ResultSet rs2, ExcelStructureTable est,String localclassname) throws Exception {
		Map map = est.getFieldMap();
		int result = 0;
		try {
			while (rs2.next()) {
				result++;
				Element attributes = classname.addElement("attributes");
				Element resourceId = attributes.addElement("resourceId");
				String cuid = "";
				String modifyTime = "";
				for (Object key : map.keySet()) {
					ExcelStructureFieldMap field = (ExcelStructureFieldMap) map.get(key);
					if (field.getIrmsFieldName() != null && field.getDatanmsFieldName() != null
							&& !field.getIrmsFieldName().equals("") && !field.getDatanmsFieldName().equals("")) {

						String value = rs2.getString(field.getIrmsFieldName()) == null ? "" : rs2.getString(field
								.getIrmsFieldName()).trim();
						if (field.getIrmsFieldName().equals("ID")) {
							cuid = value;
						} else if (field.getIrmsFieldName().equals("LAST_MODIFY_TIME")) {
							modifyTime = value;
						} else {
							Element attribute = attributes.addElement("attribute");
							attribute.addAttribute("name", field.getDatanmsFieldName());
							setElementValue(attribute, value);
						}
					} else if ((field.getDatanmsFieldName() == null || field.getDatanmsFieldName().equals(""))
							&& field.getDatanmsFieldCheck() != null && !field.getDatanmsFieldCheck().equals("")) {
						String dataReleated = field.getDatanmsFieldCheck();
						if (dataReleated.contains("DEFAULT") && dataReleated.contains("=")) {
							Element attribute = attributes.addElement("attribute");
							attribute.addAttribute("name", field.getIrmsFieldName());
							String value = field.getDatanmsFieldCheck().substring(dataReleated.indexOf("=") + 1);
							setElementValue(attribute, value);
						}
					}
				}
				setElementText(resourceId, cuid);
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("生成DOCUMENT文件失败" + e.getMessage());
		}finally{
			if (rs2 != null) {
				rs2.close();
				rs2 = null;
			}
		}
	}

	/**
	 * 为某个增加值
	 * 
	 * @param attributes
	 * @param value
	 */
	private void setElementValue(Element attribute, String value) {
		attribute.addAttribute("value", value);
	}

	/**
	 * 为某个增加值
	 * 
	 * @param attributes
	 * @param value
	 */
	private void setElementText(Element attribute, String value) {
		if (value != null) {
			attribute.setText(value);
		}
	}

	/**
	 * 生成单个XML文件(增量)
	 * 
	 * @param fw
	 * @param eventID
	 * @param filter
	 * @author keyaxin
	 * @throws Exception
	 */
	private void createOneFileXml(FileWriter fw, String eventID, String filter) throws Exception {
		// 生成xml文件
		Document doc = DocumentHelper.createDocument();
		int fileResult = createXMLFile(doc, filter);
		String filename = localdir + File.separatorChar + eventID;
		String filepath = filename + ".xml";
		formatOutputXmlDocument(doc, "UTF-8", filepath);

		// 压缩文件
		File sourceFile = new File(filepath);
		String zippedFilePath = filename + ".zip";

		String zippedFileName = eventID + ".zip";
//		log.info("压缩前的数据文件名：" + sourceFile.getName() + " 压缩前的数据文件路径：" + sourceFile.getPath());
		createZip(zippedFilePath, new String[] { sourceFile.getPath() });

		// 写入校验压缩文件的信息
		writeCheckFileInfo(fw, zippedFileName, 40, zippedFilePath, 20, fileResult, 20, 129);
		// 上传压缩文件
		String createfilename = FtpZipOption.upload2Ftp(zippedFilePath,RequestConfig.getftphost(),RequestConfig.getftpport(),
                RequestConfig.getftpusername(),RequestConfig.getftppassword(),
                RequestConfig.getftpdedir());
		if (createfilename != null) {
			File f = new File(sourceFile.getPath());
			f.delete();
		}
	}

	/**
	 * 生成消息的批量分发
	 * 
	 * @param eventerID
	 * @param filter
	 * @return
	 * @throws Exception
	 */
	private String createStringXml(String filter) throws Exception {
		String datainfo = "没有你要查找的数据。";
		if(!filter.equals("") && filter != null){
			Document doc = DocumentHelper.createDocument();
			createXMLFile(doc, filter);
			doc.setXMLEncoding("UTF-8");
			datainfo = doc.asXML();
		}
//		log.info("<<<<<<<<" + datainfo);
		return datainfo;
	}

	public Map getClassNameMap() {
		return classNameMap;
	}

	public void setClassNameMap(Map classNameMap) {
		this.classNameMap = classNameMap;
	}

	public void deliveryReady(String eventID, Calendar sendTime, int readyStatusCode, String readyStatusDescription,
			int workMode, int fileFormat, int charSet, int lineSeparator, int fieldSeparator, String fieldNameList,
			String xmlSchema, String dataInfo, String connectionString, String path, boolean isCompressed,
			String deliveryReadyRequestURL, String systemID) {
		// readyStatusCode 1：表示数据已就绪 2：表示数据未就绪
		// readyStatusDescription 具体描述数据就绪失败原因
		// workMode 0：表示基于消息的批量数据分发 1：表示基于文件的批量数据分发
		// fileFormat 0：表示固定文本格式 1：表示可变文本格式，格式定义在本报文中2：表示固定XML格式
		// 3：表示可变XML格式，schema定义在本报文中 4：表示可变XML格式，schema定义在数据文件中
		// charSet 0：GBK 1：UTF-16（Unicode）2：UTF-8
		// lineSeparator 只在文件格式为1有效，行分割符在此处应填为十六进制表示，如回车换行应填为0x0d0x0a
		// fieldSeparator 只在文件格式为1有效，字段分割符在此处应填为十六进制表示，如回车换行应填为0x0d0x0a
		// fieldNameList 只在文件格式为1有效，字段名列表，逗号分割
		// xmlSchema 只在文件格式为3有效，描述XML数据文件的schema
		// dataInfo 只在工作方式为0时有效，放置待分发的数据记录，数据信息的格式同于数据文件的文件格式要求。
		// connectionString
		// 只在工作方式为1时有效，描述数据文件和校验文件存放位置的连接字符串，如ftp://usr:pwd@192.168.0.123:21。
		// path 只在工作方式为1时有效，描述数据文件和校验文件的放置目录，如/home/usr/data。
		// isCompressed 只在工作方式为1时有效，为布尔值，描述数据文件是否需要进行压缩处理。
		DeliveryReadyWebserviceBO.getInstance().deliveryReady(new StringHolder(eventID), sendTime,
				readyStatusCode, readyStatusDescription, workMode, fileFormat,
				charSet, lineSeparator, fieldSeparator, fieldNameList,
				xmlSchema, dataInfo, connectionString, path, isCompressed,
				deliveryReadyRequestURL,systemID);
	}

	public void convert(String eventID, java.lang.String systemID, java.util.Calendar sendTime,
			java.lang.String filter, java.lang.String dataReadyRequestUri) throws Exception {
		String ipaddress = "无";
		String username = "无";
		String portnum = "无";
		String password = "无";
		String dir = "无";
		int workMode = 1;
		String dataInfo = "无";
		boolean isCompressed = true;
		String conn = "无";
		String[] obj = filter.split(";");
		String[] key = new String[obj.length];
		String[] value = new String[obj.length];
		StringBuffer filter1 = new StringBuffer();
		StringBuffer filter2 = new StringBuffer();
		StringBuffer filter3 = new StringBuffer();
		Calendar sendTime1 = new GregorianCalendar();
		int fileFormat = 2;
		this.initBO();
		Connection connection = null;
		connection = JDBCManager.getDataSource(RequestConfig.getUrl(), RequestConfig.getUsername(),
				     RequestConfig.getPassword()).getConnection();
		Map<String, String> int_idTableNameMap = eStructure.localclassidIrmsclassidMap;
		try {
			for (int i = 0; i < obj.length; i++) {
				key[i] = obj[i].substring(0, obj[i].indexOf(".")).toUpperCase();
				value[i] = obj[i].substring(obj[i].indexOf(".") + 1, obj[i].length());
				String tablename = key[i];
				key[i] = int_idTableNameMap.get(key[i]) == null ? "" : int_idTableNameMap.get(key[i]).toString();
				if (value[i].equalsIgnoreCase(" ") || value[i].equalsIgnoreCase("null")) {
					filter1.append(key[i]).append(".").append(value[i]).append(";");
				} else {
					if(!key[i].equals("")){
						String sqlwhere = "select count(*) from " + key[i] + " where "
						+ value[i];
						ResultSet rs2 = this.getAllObjectByWhere(connection, sqlwhere, 0, 0);
						int size = 0;
						if(rs2 != null){
							while(rs2.next()){
								size = rs2.getInt(1);
							}
						}
						if (size < 10 && size > 0 ) {
							filter2.append(key[i]).append(".").append(value[i]).append(";");
						} else if (size >= 20 && size < 5000) {
							filter3.append(key[i]).append(".").append(value[i]).append(";");
						} else {
							filter1.append(key[i]).append(".").append(value[i]).append(";");
						}
					}else{
						this.deliveryReady(eventID, sendTime1, 2, "您输入的过滤条件有问题，表"+tablename+"不存在", workMode, fileFormat, 2, 0, 0,
								"当前文件格式为固定XML格式", "当前文件格式为固定XML格式", "", "", "", isCompressed, dataReadyRequestUri, systemID);
					}
				}
			}
			if (filter1.toString().length() == 0 && filter3.toString().length() == 0) {
				if (filter2.toString().length() > 0) {
					filter2.deleteCharAt(filter2.lastIndexOf(";"));
				}
				dataInfo = this.createStringXml(filter2.toString());
				workMode = 0;
				isCompressed = false;
			} else {
				if (filter1.toString().length() > 0) {
					filter1.deleteCharAt(filter1.lastIndexOf(";"));
				}
				filter3.append(filter2);
				if (filter3.toString().length() > 0) {
					filter3.deleteCharAt(filter3.lastIndexOf(";"));
				}
				String checkfilename = this.createDocument(eventID, filter1.toString(), filter3.toString());

				
				dir = RequestConfig.getftpdedir();
				conn = "ftp://" + RequestConfig.getftpusername()+ ":" 
                + RequestConfig.getftppassword() + "@" 
                + RequestConfig.getftphost() + ":" 
                + RequestConfig.getftpport();
//				log.info("校验文件名:" + checkfilename);
//				log.info("数据文件和校验文件存放位置的连接字符串:" + conn);
//				log.info("描述数据文件和校验文件的放置目录:" + dir);
				workMode = 1;
				dataInfo = "当前文件格式为固定XML格式";
				isCompressed = true;
			}
			this.deliveryReady(eventID, sendTime1, 1, "数据就绪成功", workMode, fileFormat, 2, 0, 0, "当前文件格式为固定XML格式",
					"当前文件格式为固定XML格式", dataInfo, conn, dir, isCompressed, dataReadyRequestUri, systemID);
		} catch (Exception e) {
			this.deliveryReady(eventID, sendTime1, 2, "您输入的过滤条件有问题，数据无法就绪成功", workMode, fileFormat, 2, 0, 0,
					"当前文件格式为固定XML格式", "当前文件格式为固定XML格式", "", "", "", isCompressed, dataReadyRequestUri, systemID);
		}finally {
			if (st != null) {
				try {
					st.close();
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

	}

	/**
	 * 压缩多个文件,需压缩单个文件时，数组个数为1
	 * 
	 * @param compressFileName
	 * @param files
	 * @return
	 */
	public static boolean createZip(String compressFileName, String files[]) {
		boolean flag = false;
		try {
			byte b[] = new byte[512];
			ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(compressFileName));
			for (int i = 0; i < files.length; i++) {
				InputStream in = new FileInputStream(files[i]);
				File file = new File(files[i]);
				String filename = file.getName();
				ZipEntry e = new ZipEntry(filename);
				zout.putNextEntry(e);
				int len = 0;
				while ((len = in.read(b)) != -1) {
					zout.write(b, 0, len);
				}
				zout.closeEntry();
			}
			zout.close();
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	public void getGrid(String updatetype,Grid grid){
		this.initBO();
		String datainfo="";
		Document doc = DocumentHelper.createDocument();
		Element root = doc.addElement("resource-data");
		root.addAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
		root.addAttribute("xsi:noNamespaceSchemaLocation", "resource-data.xsd");
	    PartnerConvertDAO dao = new PartnerConvertDAO();
	    Element notifyId = root.addElement("notifyId");
	    setElementText(notifyId,grid.getId());
		try {
			Map<String,String> areamap = dao.selectProviderMap();
			Element classname = root.addElement("class");
			Element updateType = classname.addElement("updateType");
			setElementText(updateType, updatetype);
			Element resourceKind = classname.addElement("resourceKind");
			setElementText(resourceKind, "GRID");
			Element resourceId = classname.addElement("resourceId");
			setElementText(resourceId, grid.getId());
			Element attributes = classname.addElement("attributes");
			Element attribute1 = attributes.addElement("attribute");
			attribute1.addAttribute("name", "GRID_NAME");
			setElementValue(attribute1, grid.getGridName());
			Element attribute2 = attributes.addElement("attribute");
			attribute2.addAttribute("name", "REGION");
			setElementValue(attribute2,grid.getRegion());
			Element attribute3 = attributes.addElement("attribute");
			attribute3.addAttribute("name", "CITY");
			setElementValue(attribute3,grid.getCity());
			Element attribute4 = attributes.addElement("attribute");
			attribute4.addAttribute("name", "PROVIDER");
			setElementValue(attribute4,areamap.get(grid.getProvider()) == null ? grid.getProvider() : areamap.get(grid.getProvider()).toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		doc.setXMLEncoding("UTF-8");
		datainfo = doc.asXML();
		this.LoadingRequest(datainfo);
		System.out.println(datainfo);
	}
	
	public void LoadingRequest(String datainfo) {
        getLoadingRequestPort();
        try {
        	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH24MMSS",Locale.SIMPLIFIED_CHINESE);
    		Date date = new Date();
    		String checkFileName = "Partner" + sdf.format(date);
            StringHolder eventID = new StringHolder(checkFileName);
            String systemID = "Partner";
            Calendar sendTime = new GregorianCalendar();
            log.info("sendTime is:" + sendTime.getTime().toString());
            /**
             * 0：表示基于消息的批量数据装载
             * 1：表示基于文件的批量数据装载
             */
            int workMode = 0;
            String feedbackUri = "-1";
            int fileFormat = 2;
            int charSet = 0;
            /**
             * 1：全量数据，数据中心装载全量数据前会清空目标表
             * 2：增量数据，数据中心直接将增量数据插入到目标表中
             * 3：更新增量数据，数据中心会根据主键匹配，更新插入或删除目标表数据。
             */
            int loadingFlag = 2;
            String fieldNameList = ",";
            String xmlSchema = "";
            String fieldSeparator = "0x0d0x0a";
            String lineSeparator = "0x0d0x0a";
            LoadingRequestport.loadingRequestRequest(eventID, systemID, sendTime,
                                                     feedbackUri, loadingFlag, workMode, fileFormat, charSet,
                                                     lineSeparator, fieldSeparator, fieldNameList, xmlSchema,
                                                     datainfo);
            log.info("专业网管处理装载反馈返回值为：" + workMode);
            System.out.println("print content: " + datainfo);
        } catch (Exception e) {
            log.debug(e.toString());
            e.printStackTrace();
        }
    }
	
	public void getLoadingRequestPort() {
		
		String url = RequestConfig.getLoadingFeedback();
        LoadingRequestLocator server = new LoadingRequestLocator();
        try {
        	LoadingRequestport = server.getLoadingRequestSOAPport_http(new URL(url));
        } catch (MalformedURLException e) {
            log.error(e.toString());
        } catch (ServiceException e) {
            log.error(e.toString());
        }
    }
	
//	public static void main(String[] args) {	
//		Grid grid = new Grid();
//		grid.setId("8a52834225ed83950125feb5dad4051b");
//		grid.setGridName("网络ＣＣ");
//		grid.setRegion("10102");
//		grid.setCity("1010206");
//		grid.setProvider("福建丰祥通信技术服务公司");
//		DeliveryRequestConvertBO bo = new DeliveryRequestConvertBO();
//		bo.getGrid("1", grid);
//		bo.getGrid("2", grid);
//		bo.getGrid("3", grid);
//	}
}
