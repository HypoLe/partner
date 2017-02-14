package com.boco.eoms.partner.dataSynch.mgr;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;

import com.boco.eoms.base.util.InterfaceDataMonitor;
import com.boco.eoms.common.util.StaticMethod;
import com.boco.eoms.commons.loging.BocoLog;
import com.boco.eoms.partner.dataSynch.dao.AbstractDataSynchDaoJdbc;
import com.boco.eoms.partner.dataSynch.model.CheckFile;
import com.boco.eoms.partner.dataSynch.rule.AbstractDataMappingRule;
import com.boco.eoms.partner.dataSynch.util.DataSynchConfig;
import com.boco.eoms.partner.dataSynch.util.DataSynchConstant;
import com.boco.eoms.partner.dataSynch.util.DataSynchJdbcUtil;
import com.boco.eoms.partner.dataSynch.util.ZipFileUnpackUtils;

/** 
 * Description: 数据同步
 * Copyright:   Copyright (c)2012
 * Company:     BOCO
 * @author:     zhangkeqi
 * @version:    1.0
 * Create at:   Mar 28, 2012 2:48:26 PM
 */
public class AbstractDataSynchXiAnMgr extends AbstractDataSynchMgr{
	/**
	 * 解析数据文件
	 * @param dataFileInputStream 数据文件流
	 * @param dataFileName 数据文件名，如：SDH_jk_group20120515103709006.zip 
	 * @return 解析的总数据条数
	 */
	public int parseDataFile(InputStream dataFileInputStream,Connection conn,String dataFileName)throws Exception{
//		String sql = dataDaoJdbcImpl.getBatchInsertSql("his");
		String sql = dataDaoJdbcImpl.getBatchInsertSql("tmp");
		
		PreparedStatement ps = null;
		int totalCount = 0; //记录总条数
		int counter = 0; //计数器
		
		try {
			ps = conn.prepareStatement(sql);
			XMLInputFactory factory = XMLInputFactory.newInstance();
			XMLStreamReader reader = factory.createXMLStreamReader(dataFileInputStream);
			
			boolean endFlag = false;
			String cuid = "";
			String fieldName = "";
			String fieldValue = "";
			Map<String,Object> dataMap = null;
			while (reader.hasNext()) {
				int event = reader.getEventType();
				switch (event) {
					case XMLStreamConstants.START_ELEMENT:
						String localName = reader.getLocalName();
						if("class".equals(localName)){
							dataMap = new HashMap<String,Object>();
						}else if("resourceId".equals(localName)){
							cuid = reader.getElementText();
							dataMap.put("id", cuid);
						}else if("attribute".equals(localName)){
							for (int i = 0; i < reader.getAttributeCount(); i++) {
								if("name".equals(reader.getAttributeLocalName(i))){
									fieldName = reader.getAttributeValue(i);
								}else if("value".equals(reader.getAttributeLocalName(i))){
									fieldValue = reader.getAttributeValue(i);
								}
							}
							dataMap = dataMappingRule.mapping(fieldName, fieldValue,cuid, 
									dataMap,dataFileName,dataType+deviceType);
//							dataMap.put(fieldName.toLowerCase(), fieldValue);
						}
						break;
					case XMLStreamConstants.END_ELEMENT:
						if("class".equals(reader.getLocalName())){
							//非异常数据
							if(!"Y".equals(dataMap.get(DataSynchConstant.EXCEPTION_KEY))){
								//添加额外数据
								dataMap.put("data_synch_time", StaticMethod.getCurrentDateTime("yyyy-MM-dd HH:mm:ss"));
								ps = dataDaoJdbcImpl.addPsBatch(ps, dataMap);
							}
							totalCount++;
							counter++;
						}
						if("resource-data".equals(reader.getLocalName())){
							endFlag = true;
						}
				}
				
				if(counter == dataSynchConfig.getBatchListSize() || endFlag){
					dataSynchJdbcUtil.batchSaveData(conn,ps);
					if(dataSynchConfig.isTmpTableCountQuery()){
						System.out.println("临时表数量："+dataDaoJdbcImpl.getTotalCountInTmp(conn));
					}
					counter = 0;
				}
				if (reader.hasNext()) {
					event = reader.next();
				} else {
					break;
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(ps != null){
					ps.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
				BocoLog.warn(this.getClass(), e.getMessage());
			}
		}
System.out.println(dataFileName + "解析完成后的数据量:" + totalCount);
		return totalCount;
	}
}
