package com.boco.eoms.partner.dataSynch.rule;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.boco.eoms.common.util.StaticMethod;
import com.boco.eoms.commons.system.area.dao.TawSystemAreaDaoJdbc;
import com.boco.eoms.partner.dataSynch.mgr.ISynchExceptionRecordMgr;
import com.boco.eoms.partner.dataSynch.model.SynchExceptionRecord;
import com.boco.eoms.partner.dataSynch.model.SynchMappingConfig;
import com.boco.eoms.partner.dataSynch.util.DataParserUtils;
import com.boco.eoms.partner.dataSynch.util.DataSynchConstant;
import com.boco.eoms.partner.dataSynch.util.DataSynchDictUtil;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;

/** 
 * Description: 数据映射规则基类
 * Copyright:   Copyright (c)2012
 * Company:     BOCO
 * @author:     LiuChang
 * @version:    1.0
 * Create at:   Apr 6, 2012 10:30:31 AM
 */
public abstract class AbstractDataMappingRule {
	protected SynchMappingConfig[] config; //采用castor解析xml后放置解析后的对象数组
	protected Map<String,SynchMappingConfig> ruleMap = Maps.newHashMap();;
	private ISynchExceptionRecordMgr synchExceptionRecordMgr;
	protected TawSystemAreaDaoJdbc tawSystemAreaDaoJdbc;
	protected Map<String,String> areaMap; //地域<key综合资源地域名，value本地地域ID>
	protected Map<String,Map<String,String>> dictMap = new HashMap<String,Map<String,String>>();

	/**
	 * 按公用映射规则进行映射
	 * @param fieldName
	 * @param fieldValue
	 * @param cuid
	 * @param fileName
	 * @param dataMap
	 * @return
	 */
	public Map<String,Object> mapping(String fieldName,String fieldValue,String cuid,
			Map<String,Object> dataMap,String fileName,String dataType){
		SynchExceptionRecord record = null;
		SynchMappingConfig cfg = getSynchMappingConfig(fieldName);
		
		//cfg为null表明对该字段无映射规则，所以直接入dataMap然后返回
		if(cfg == null) {
			dataMap.put(fieldName, fieldValue);
			return dataMap;
		}
		
		//如果是必填项
		if("Y".equals(cfg.getRequired())){
			if(StringUtils.isEmpty(fieldValue) || "NIL".equals(fieldValue)){
				saveException(record,dataMap,cuid,fieldName,"必填字段为空",fileName,dataType);
			}
		}
//		//如果需要按公共规则进行映射
//		if("Y".equals(cfg.getMapping())){
//			//地域映射
//			if("REGION".equals(cfg.getMappingType()) || "CITY".equals(cfg.getMappingType())){
//				String value = "";
//				//_5 _7是sql中拼接的areaid长度，用来区分地市名和区县名一样的情况
//				if("REGION".equals(cfg.getMappingType())){ //地市
//					value = areaMap.get(fieldValue.trim()+"_5");
//				}else{//区县
//					value = areaMap.get(fieldValue.trim()+"_7");
//				}
//				if(!Strings.isNullOrEmpty(value)){
//					fieldValue = value;
//				}else{
//					saveException(record,dataMap,cuid,fieldName,"地域无法正确映射",fileName,dataType);
//				}
//			}
//		}
		
		dataMap.put(fieldName, fieldValue);
		
		if("Y".equals(cfg.getMapping()) && !"".equals(fieldValue)){
			if("DICT".equals(cfg.getMappingType())) {
				name2IdMapping(cfg,fieldName,fieldValue,cuid,dataMap,fileName,dataType);
			}
		}
		
		//按私有规则进行映射
		mappingSelf(cfg,fieldName,fieldValue,cuid,dataMap,fileName,dataType);
		
		return dataMap;
	}
	
	
	/**
	 * 按私有规则进行映射
	 * @since Apr 27, 2012
	 * @author zhangkeqi
	 */
	public void mappingSelf(SynchMappingConfig cfg,String fieldName,String fieldValue,
			String cuid,Map<String,Object> dataMap,String fileName,String dataType){
	}
	
	
	private void name2IdMapping(SynchMappingConfig cfg,String fieldName,String fieldValue,
			String cuid,Map<String,Object> dataMap,String fileName,String dataType) {
//		System.out.println(fieldName);
		Map<String,String> initDictMap = dictMap.get(fieldName);
		String dictId = initDictMap.get(fieldValue);
		
		String newDictId = "";
		String newDictIdNum = "";
		String parentDictId = cfg.getInitDictId();
		if("".equals(dictId) || dictId == null) {
			newDictId = DataSynchDictUtil.getNewDictId(parentDictId);
			newDictIdNum = newDictId.substring(newDictId.length()-2, newDictId.length());
			if("99".equals(newDictIdNum)) {
				SynchExceptionRecord record = null;
				saveException(record,dataMap,cuid,fieldName,"字典"+parentDictId+"下的字典项以达到最大值,"+"无法新增字典值！",fileName,dataType);
			} else {
				newDictId = DataSynchDictUtil.dynamicAddDictType(parentDictId,fieldValue);
				//更新map
				initDictMap.put(fieldValue, newDictId);
				fieldValue = newDictId;
			}
		} else {
			fieldValue = dictId;
		}
		
		dataMap.put(fieldName, fieldValue);
	}
	
	/**
	 * 在数据中加入异常标记
	 * @param dataMap
	 */
	private void markExcpetion(Map<String,Object> dataMap){
		Object obj = dataMap.get(DataSynchConstant.EXCEPTION_KEY);
		String mark = obj == null? "" : obj.toString();
		//如果没有标记为异常，则加入异常标记
		if(!"Y".equals(mark)){
			dataMap.put(DataSynchConstant.EXCEPTION_KEY, "Y");
		}
	}
	
	/**
	 * 保存异常数据信息
	 */
	public void saveException(SynchExceptionRecord record,Map<String,Object> dataMap,String cuid,
			String fieldName,String reason,String fileName,String dataType){
		record = new SynchExceptionRecord();
		record.setCuid(cuid);
		record.setExceptionField(fieldName);
		record.setExceptionReason(reason);
		record.setCreateTime(StaticMethod.getCurrentDateTime("yyyy-MM-dd HH:mm:ss"));
		record.setDataType(dataType);
		record.setExceptionFileName(fileName);
		synchExceptionRecordMgr.save(record);
		markExcpetion(dataMap);
	}
	
	/**
	 * 根据字段名获取映射配置
	 * @param fieldName
	 * @return
	 */
	private SynchMappingConfig getSynchMappingConfig(String fieldName){
		return ruleMap.get(fieldName);
	}
	
	/**
	 * 获取映射文件全路径
	 * @param mappingFileName 映射文件名
	 * @return
	 */
	private String getMappingFilePath(String mappingFileName){
		return DataParserUtils.getAbstractPath(DataParserUtils.CLASS_PATH_FLAG 
				+ DataSynchConstant.BASE_PATH + mappingFileName);
	}
	
	/**
	 * 获取映射文件Reader
	 * @param mappingFileName
	 * @return
	 * @throws Exception
	 */
	protected Reader getMappingFileReader(String mappingFileName) throws Exception{
		String filePath =getMappingFilePath(mappingFileName);
		//字符集与xml的encoding都是UTF-8，可以避免乱码
		Reader reader = new InputStreamReader(new FileInputStream(filePath),"UTF-8");
		return reader;
	}
	

	/**
	 * 构造ruleMap
	 * @param rule
	 * @return
	 */
	protected void buildRuleMap(AbstractDataMappingRule rule) {
		for (SynchMappingConfig cfg : rule.config) {
			ruleMap.put(cfg.getFieldName(), cfg);
		}
	}

	/**
	 * 构造dictMap
	 */
	public void buildDictMap() {
		String initDictId = "";
		Map<String,String> initDictMap = null;
		
		SynchMappingConfig cfg = null;
		for(String key : ruleMap.keySet()) {
			cfg = ruleMap.get(key);
			//初始化需要字典映射的字段对应的字典Map
			if("Y".equals(cfg.getMapping())){
				if("DICT".equals(cfg.getMappingType())) {
					initDictId = cfg.getInitDictId();
					initDictMap = DataSynchDictUtil.getDictMap(initDictId);
					dictMap.put(cfg.getFieldName(), initDictMap);
				}
			}
		}
		
//		for (SynchMappingConfig cfg : this.config) {
//			//初始化需要字典映射的字段对应的字典Map
//			if("Y".equals(cfg.getMapping())){
//				if("DICT".equals(cfg.getMappingType())) {
//					initDictId = cfg.getInitDictId();
//					initDictMap = DataSynchDictUtil.getDictMap(initDictId);
//					dictMap.put(cfg.getFieldName(), initDictMap);
//				}
//			}
//		}
	}
	
	public SynchMappingConfig[] getConfig() {
		return config;
	}
	/**
	 * 必须要有setter方法，否则castor解析后不能设置解析后的类
	 * @param config
	 */
	public void setConfig(SynchMappingConfig[] config) {
		this.config = config;
	}
	
	
	public ISynchExceptionRecordMgr getSynchExceptionRecordMgr() {
		return synchExceptionRecordMgr;
	}

	public void setSynchExceptionRecordMgr(
			ISynchExceptionRecordMgr synchExceptionRecordMgr) {
		this.synchExceptionRecordMgr = synchExceptionRecordMgr;
	}
	public TawSystemAreaDaoJdbc getTawSystemAreaDaoJdbc() {
		return tawSystemAreaDaoJdbc;
	}

	public void setTawSystemAreaDaoJdbc(TawSystemAreaDaoJdbc tawSystemAreaDaoJdbc) {
		this.tawSystemAreaDaoJdbc = tawSystemAreaDaoJdbc;
	}
	
	
	
	
}
