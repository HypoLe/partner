/*
 * Created on 2008-1-14
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.boco.eoms.commons.statistic.businessplan.util;

import org.displaytag.decorator.TableDecorator;

import com.boco.eoms.commons.statistic.base.util.StatUtil;
import com.boco.eoms.commons.statistic.businessplan.vo.StatDetailVOBusinessplan;

// import com.boco.eoms.sheet.base.model.BaseCollect;
// import com.boco.eoms.sheet.base.model.BaseMain;

/**
 * @author Administrator
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class BusinessplanListDisplayHelper extends TableDecorator {
/*	public String getTitle() {

		StatDetailVOTask vo = (StatDetailVOTask) getCurrentRowObject();
		// String url = (String) this.getPageContext().getAttribute("url");

		return "<a onclick=window.open('" + "http://localhost:8080/"
				+ "tasksheet.pr.prShowQueryInfo.do?detail/isDetail=1&sheetid="
				+ vo.getSheetid() + "'); href='#' target='_blank'>" + vo.getTitle() + "</a>";

		// http://localhost:8080/tasksheet.pr.prShowQueryInfo.do?sheetid=null-null-080721-004-00007&detail/isDetail=1
	}
*/
	/*
	public String getSheetid(){
		StatDetailVOTask vo = (StatDetailVOTask) getCurrentRowObject();
		// String url = (String) this.getPageContext().getAttribute("url");
		String url = UtilMgrLocator.getEOMSAttributes().getLoginEOSOutUrl(); 

		return "<a href='"+"http://localhost:8080/"
				+ "tasksheet.pr.prShowQueryInfo.do?detail/isDetail=1&sheetid="
				+ vo.getSheetid() + "'  target='_blank'>" + vo.getSheetid() + "</a>";
		// http://localhost:8080/tasksheet.pr.prShowQueryInfo.do?sheetid=null-null-080721-004-00007&detail/isDetail=1
		
	}*/

	public String getSenduser() {
		StatDetailVOBusinessplan vo = (StatDetailVOBusinessplan) getCurrentRowObject();
		return StatUtil.id2Name(vo.getSenduserid(), "statBaseUserId2name_v35");
	}

	public String getSenddept() {
		StatDetailVOBusinessplan vo = (StatDetailVOBusinessplan) getCurrentRowObject();
		return StatUtil.id2Name(vo.getSenddeptid(), "statBaseDeptId2name_v35");

	}

	public String getStatus() {
		StatDetailVOBusinessplan vo = (StatDetailVOBusinessplan) getCurrentRowObject();
		return StatUtil.getStatusName(vo.getStatus());

	}
}
