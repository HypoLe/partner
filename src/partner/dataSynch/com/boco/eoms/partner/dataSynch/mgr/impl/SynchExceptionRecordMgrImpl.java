package com.boco.eoms.partner.dataSynch.mgr.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.boco.eoms.base.model.PageModel;
import com.boco.eoms.partner.dataSynch.dao.ISynchExceptionRecordDao;
import com.boco.eoms.partner.dataSynch.mgr.ISynchExceptionRecordMgr;
import com.boco.eoms.partner.dataSynch.model.SynchExceptionRecord;
import com.boco.eoms.partner.dataSynch.util.DataSynchConstant;

/** 
 * Description: 
 * Copyright:   Copyright (c)2012
 * Company:     BOCO
 * @author:     LiuChang
 * @version:    1.0
 * Create at:   Mar 28, 2012 6:37:49 PM
 */
public class SynchExceptionRecordMgrImpl implements ISynchExceptionRecordMgr {
	private ISynchExceptionRecordDao synchExceptionRecordDao;

	public void save(SynchExceptionRecord synchExceptionRecord){
		synchExceptionRecordDao.saveObject(synchExceptionRecord);
	}
	
	@SuppressWarnings("unchecked")
	public PageModel findSynchExceptionRecordList(Integer offset,  Integer pageSize,
			SynchExceptionRecord record){
		StringBuffer hql = new StringBuffer("from SynchExceptionRecord where 1=1 ");
		List params = new ArrayList();
		if(StringUtils.isNotEmpty(record.getDataType())){
//			hql.append("and upper(dataType) like '%' || upper(?) || '%' ");
			hql.append("and dataType like '%"+record.getDataType()+"%' ");
			//params.add(record.getDataType());
		}
		String date = record.getCuid();
		
		String dbType = DataSynchConstant.getDBType();
		if("oracle".equals(dbType)) {
			if(StringUtils.isNotEmpty(date)){
				hql.append("and ? < createTime and " +
				"? > createTime");
				params.add(date + " 00:00:00");
				params.add(date + " 23:59:59");
			}
		} else {
			if(StringUtils.isNotEmpty(date)){
				hql.append("and ? < createTime and " +
				"? > createTime");
				params.add(date + " 00:00:00");
				params.add(date + " 23:59:59");
			}
		}
		
		hql.append(" order by createTime desc");
		return synchExceptionRecordDao.searchPaginated(hql.toString(), params.toArray(), offset, pageSize);
	}
	
	public ISynchExceptionRecordDao getSynchExceptionRecordDao() {
		return synchExceptionRecordDao;
	}

	public void setSynchExceptionRecordDao(
			ISynchExceptionRecordDao synchExceptionRecordDao) {
		this.synchExceptionRecordDao = synchExceptionRecordDao;
	}
	
	
}
