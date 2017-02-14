package com.boco.eoms.partner.res.mgr;

import java.util.List;
import java.util.Map;

import org.apache.struts.upload.FormFile;

import com.boco.eoms.partner.netresource.service.IEomsService;
import com.boco.eoms.partner.res.dao.IPnrTransLineDao;
import com.boco.eoms.partner.resourceInfo.util.ImportResult;

public interface IPnrTransLineMgr extends IEomsService {

	public IPnrTransLineDao getPnrTransLineDao();
	
	/**
	 * 上传光缆段
	 * @param formFile
	 * @return
	 * @throws Exception 
	 */
	ImportResult importTLFromFile(FormFile file) throws Exception;
	/**
	 * 上传光缆敷设点
	 * @param formFile
	 * @return
	 * @throws Exception 
	 */
	ImportResult importTLPFromFile(FormFile file) throws Exception;
	
	/**
	 * 敷设点关联光缆段
	 * @param formFile
	 * @return
	 * @throws Exception 
	 */
	public void relationTransLine(String... parma ) throws Exception;
	
	/**
	 * 敷设点关联光缆段
	 * @param formFile
	 * @return
	 * @throws Exception 
	 */
	public Map<String, List> getTransPointRate(); 
	
	/**
	 * 必到点设置
	 * @param formFile
	 * @return
	 * @throws Exception 
	 */
	public void setMustArrive(String ids,String... parmas); 
	
	/**
	 * 批量设置到点率
	 * @param formFile
	 * @return
	 * @throws Exception 
	 */
	public void updateAllTransPointRate(String ids,String... params); 

	/**
	 * 添加巡检段的起点和终点到敷设点
	 * @param tlId
	 */
	public void addTL2TPL(String tlId);
	
	/**
	 * 敷设点是否巡检完成
	 * @param tlId
	 */
	public boolean isFinishedTLPInspectItem(String pointId);
	
	//新增了根据地域来查询人到点率或周期 add by lee

	public Map<String, List> getTransPointRate(String areaId);
	
	public String rateCountryId2Name(String countryId);
}
