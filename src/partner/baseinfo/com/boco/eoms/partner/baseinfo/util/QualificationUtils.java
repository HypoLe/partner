package com.boco.eoms.partner.baseinfo.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.deviceManagement.common.utils.CommonUtils;
import com.boco.eoms.message.util.CommonUtil;
import com.boco.eoms.partner.baseinfo.mgr.IPnrQualificationMgr;
import com.boco.eoms.partner.baseinfo.model.PnrQualification;
import com.huawei.insa2.comm.PSocketConnection;

/**  
 * @Title: QualificationUtils.java
 * @Package com.boco.eoms.partner.baseinfo.util
 * @Description: 代维资质工具类将获取的参数封装为Qualification实体
 * @author fengguangping fengguangping@boco.com.cn
 * @date Mar 21, 2013  9:37:24 AM  
 */
public class QualificationUtils {
	public List<PnrQualification> newQualificationObject(HttpServletRequest request){
		List<PnrQualification> pnrQualificationList=new ArrayList<PnrQualification>();
		String[] qualifyNames=request.getParameterValues("qualifyNames");
		String[] qualifyLevels=request.getParameterValues("qualifyLevels");
		String[] issueAuthors=request.getParameterValues("issueAuthors");
		String[] outOfDates=request.getParameterValues("outOfDates");
		String[] qualifyIds=request.getParameterValues("qualifyId");
		IPnrQualificationMgr pnrQualificationMgr=(IPnrQualificationMgr)ApplicationContextHolder.getInstance().getBean("pnrQualificationMgr");
		if (qualifyNames!=null) {
			for (int i = 0; i < qualifyNames.length; i++) {
				PnrQualification p=new PnrQualification();
				if (!"".equals(qualifyIds[i])) {//区分是新增还是删除
					p=pnrQualificationMgr.find(qualifyIds[i]);
				}
				p.setQualifyName(qualifyNames[i]);
				p.setQualifyLevel(qualifyLevels[i]);
				p.setIssueAuthority(issueAuthors[i]);
				p.setOutOfDate(outOfDates[i]);
				p.setIsDeleted("0");
				String sysno=StaticMethod.null2String(p.getSysno());
				if("".equals(sysno)){
					String sys=pnrQualificationMgr.createPnrQualificationSystemNo("");
					p.setSysno(sys);
				}
				p.setAddTime(CommonUtils.toEomsStandardDate(new Date()));
				pnrQualificationList.add(p);
			}
		}
		return pnrQualificationList;
	}
	public boolean removePnrQualificationObject(String id){
		boolean flag=false;
		
		return flag;
	}
}
