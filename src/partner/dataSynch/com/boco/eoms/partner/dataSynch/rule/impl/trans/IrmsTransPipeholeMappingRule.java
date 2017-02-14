package com.boco.eoms.partner.dataSynch.rule.impl.trans;

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
public class IrmsTransPipeholeMappingRule extends AbstractDataMappingRule{
	/**
	 * 初始化映射规则
	 */
	public void initMappingRule() throws Exception{
		Reader reader = getMappingFileReader("/trans/dataSynch-rule-IrmsTransPipehole.xml");
		AbstractDataMappingRule rule = (IrmsTransPipeholeMappingRule) Unmarshaller.unmarshal(IrmsTransPipeholeMappingRule.class, reader);
		buildRuleMap(rule);
	}
}