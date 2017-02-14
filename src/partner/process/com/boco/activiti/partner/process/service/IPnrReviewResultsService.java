package com.boco.activiti.partner.process.service;

import java.util.Map;

import org.apache.struts.upload.FormFile;

import com.boco.activiti.partner.process.model.PnrReviewResults;
import com.boco.eoms.deviceManagement.common.service.CommonGenericService;
import com.boco.eoms.partner.netresource.util.ImportResult;

/**
 * 会审结果SERVICE 
 * @author WANGJUN
 *
 */
public interface IPnrReviewResultsService extends
		CommonGenericService<PnrReviewResults> {
	
	/**
	 * 导入会审结果
	 * @param formFile
	 * @param creatorId
	 * @param osPath
	 * @return
	 * @throws Exception
	 */
	public ImportResult importReviewResultsFromFile( FormFile formFile,String creatorId,String osPath) throws Exception;
	
	/**
	 * 根据条件查询会审结果列表
	 * 
	 * @param curPage
	 * @param pageSize
	 * @param whereStr
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map getReviewResultsList(final Integer curPage,
			final Integer pageSize, final String whereStr,final String importStartTime,final String importEndTime);
	
	/**
	 * 删除会审信息
	 * @param whereStr
	 */
	public void deletePnrReviewResults(String whereStr);

}
