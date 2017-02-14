package com.boco.eoms.partner.dataSynch.rule.impl.bs;

import java.io.Reader;

import org.exolab.castor.xml.Unmarshaller;

import com.boco.eoms.partner.dataSynch.rule.AbstractDataMappingRule;
import com.boco.eoms.partner.dataSynch.util.DataSynchConstant;

/** 
 * Description: 
 * Copyright:   Copyright (c)2012
 * Company:     BoCo
 * @author:     zhangkeqi
 * @version:    1.0
 * Create at:   2012-11-27 20:43:12
 */
public class IrmsBsStorageMappingRule extends AbstractDataMappingRule{
	/**
	 * 初始化映射规则
	 */
	public void initMappingRule() throws Exception{
		Reader reader = getMappingFileReader("/bs/dataSynch-rule-IrmsBsStorage.xml");
		AbstractDataMappingRule rule = (IrmsBsStorageMappingRule) Unmarshaller.unmarshal(IrmsBsStorageMappingRule.class, reader);
		buildRuleMap(rule);
	}
}