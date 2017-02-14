package com.boco.eoms.partner.home.mgr;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import com.boco.eoms.partner.home.model.PublishInfo;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

/**
 * <p>
 * Title:公告操作
 * </p>
 * <p>
 * Description:公告操作
 * </p>
 * <p>
 * Aug 24, 2012 10:53:19 AM
 * </p>
 * 
 * @author LiHaolin
 * @version 1.0
 * 
 */
public interface PublishMgr{
	/**
	 * 保存公告草稿
	 * @param request
	 * @param form
	 */
	public PublishInfo saveDrafts(HttpServletRequest request,ActionForm form);
	/**
	 * 公告列表 ：草稿，待审批，待查看，历史公告
	 * @param userId
	 * @param listType
	 * @param pageIndex
	 * @param pageSize
	 * @param publishInfoSearch  公告信息Search
	 * @return
	 */
	public SearchResult<PublishInfo> getList(HttpServletRequest request,int listType,int pageIndex,int  pageSize,Search publishInfoSearch);
	
	/**
	 * 作废
	 * @param form
	 */
	public void invalid(HttpServletRequest request,ActionForm form);
	/**
	 * 送审
	 * @param form
	 */
	public void toAudit(HttpServletRequest request,ActionForm form);
//	/**
//	 * 新建公告后直接送审
//	 * @param form
//	 */
//	public void directlyToAudit(HttpServletRequest request,ActionForm form);
	/**
	 * 审批通过
	 * @param form
	 */
	public void auditPass(HttpServletRequest request,ActionForm form);
//	/**
//	 * 审批通过，提交下一审批
//	 * @param request
//	 * @param form
//	 */
//	public void auditPassToNextAudit(HttpServletRequest request,ActionForm form);
	/**
	 * 审批驳回
	 * @param form
	 */
	public void auditReject(HttpServletRequest request,ActionForm form);
	/**
	 * 阅知
	 * @param request 
	 * @param form
	 */
	public void read(HttpServletRequest request,ActionForm form);
	/**
	 * 不通过审批 直接发布公告
	 * @param request
	 * @param form
	 */
	public void directlyPublish(HttpServletRequest request,ActionForm form);
	
	/**
	 * 取得最新的一条公告
	 * @param request
	 * @return
	 */
	public List<PublishInfo> getNewInfo(HttpServletRequest request);
}
