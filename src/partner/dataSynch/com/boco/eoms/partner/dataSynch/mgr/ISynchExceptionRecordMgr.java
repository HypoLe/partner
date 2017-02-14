package com.boco.eoms.partner.dataSynch.mgr;

import com.boco.eoms.base.model.PageModel;
import com.boco.eoms.partner.dataSynch.model.SynchExceptionRecord;

/** 
 * Description: 同步异常数据接口
 * Copyright:   Copyright (c)2012
 * Company:     BOCO
 * @author:     LiuChang
 * @version:    1.0
 * Create at:   Mar 28, 2012 6:37:32 PM
 */
public interface ISynchExceptionRecordMgr {
	public void save(SynchExceptionRecord synchExceptionRecord);
	
	/**
	 * 分页查询
	 * @param offset
	 * @param pageSize
	 * @param record
	 * @return
	 */
	public PageModel findSynchExceptionRecordList(Integer offset,  Integer pageSize,SynchExceptionRecord record);
}
