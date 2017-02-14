package com.boco.eoms.partner.res.mgr.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.boco.eoms.commons.system.dict.exceptions.DictDAOException;
import com.boco.eoms.commons.system.dict.model.TawSystemDictType;
import com.boco.eoms.commons.system.dict.service.ITawSystemDictTypeManager;
import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.partner.res.dao.PnrResToWeekTimeDao;
import com.boco.eoms.partner.res.mgr.PnrResToWeekTimeMgr;
import com.boco.eoms.partner.res.model.PnrResSetWeekTime;
import com.boco.eoms.partner.res.model.PnrResToWeekTime;

/** 
 * 
 * @author:     chenbing 
 * 
 */ 
public class PnrResToWeekTimeMgrImpl extends CommonGenericServiceImpl<PnrResToWeekTime> implements PnrResToWeekTimeMgr {

	private PnrResToWeekTimeDao pnrResToWeekTimeDao;
	private ITawSystemDictTypeManager  tawSystemDictTypeManager;
	
	
	public ITawSystemDictTypeManager getTawSystemDictTypeManager() {
		return tawSystemDictTypeManager;
	}

	public void setTawSystemDictTypeManager(
			ITawSystemDictTypeManager tawSystemDictTypeManager) {
		this.tawSystemDictTypeManager = tawSystemDictTypeManager;

	}

	public void setPnrResToWeekTimeDao(PnrResToWeekTimeDao pnrResToWeekTimeDao) {
		this.pnrResToWeekTimeDao = pnrResToWeekTimeDao;
		this.setCommonGenericDao(pnrResToWeekTimeDao);
	}

	public PnrResToWeekTimeDao getPnrResToWeekTimeDao() {
		return pnrResToWeekTimeDao;
	}

	public List<PnrResToWeekTime> getPnrResToWeekTimeByPnrId(String pnrId) {
		// TODO Auto-generated method stub
		StringBuffer hql = new StringBuffer();
		hql.append(" from PnrResToWeekTime p where p.pnrID='").append(pnrId).append("'");
		
		List<PnrResToWeekTime> list =pnrResToWeekTimeDao.findByHql(hql.toString());
		
		return list;
	}

	public int removeByPnrId(String pnrId) {
		// TODO Auto-generated method stub
		StringBuffer hql = new StringBuffer();
		hql.append("delete from PnrResToWeekTime p where p.pnrID='").append(pnrId).append("'");
		
		int i = pnrResToWeekTimeDao.bulkUpdateByHql(hql.toString());
		
		return i;
	}

/*	public List<PnrResSetWeekTime> getTawSystemDictTypeByDictId(String dictId, int level,String assign) {
		// TODO Auto-generated method stub
		// resType resLevel
		List<PnrResSetWeekTime> slist = new ArrayList<PnrResSetWeekTime>();
		
		if(level==1){
//			巡检专业
			TawSystemDictType first;
			first= tawSystemDictTypeManager.getDictByDictId(dictId);
			if(null!=first||!"".equals(first)){
				
				String  specialty = first.getDictName();
//			资源类别
				List<TawSystemDictType>  childlist= tawSystemDictTypeManager.getDictSonsByDictid(dictId);
//			资源级别
//			资源类别的个数
				int i = childlist.size();
//			资源类别的ID
				String twochildDictId;
//			开始取资源类别
				for(int j=0;j<i;j++){
//				资源类别的ID赋值
					twochildDictId=childlist.get(j).getDictId();
//				此刻这个数组才是 资源级别信息
					List<TawSystemDictType>  twochildlist= tawSystemDictTypeManager.getDictSonsByDictid(twochildDictId);
//				当前资源类别下资源级别的个数
					int k =twochildlist.size();
					if(k>0){
//				当前资源类别的名称
					String resType;
					 resType= childlist.get(j).getDictName();
//				保存的对象
					PnrResSetWeekTime pw;
					String weekname="无";
//					资源级别的ID
					String cdictId ;
//				开始取资源级别
					for(int p=0;p<k;p++){
						pw=new PnrResSetWeekTime();
						cdictId= twochildlist.get(p).getDictId();
						//获取周期
						List<PnrResToWeekTime> pnrResToWeekTime = getPnrResToWeekTimeByPnrId(cdictId);
						if(pnrResToWeekTime.size()>0){
							TawSystemDictType wt=tawSystemDictTypeManager.getDictByDictId(pnrResToWeekTime.get(0).getWeekID());
							if(null!=wt||!"".equals(wt)){								
								weekname=wt.getDictName();	
							}
						}
//					资源级别的名称
						String resLevel = twochildlist.get(p).getDictName();
						if(assign.equals("no")&&weekname.equals("无")){
							
								
								pw.setCdictId(cdictId);
								pw.setWeekname(weekname);
								pw.setResLevel(resLevel);
								pw.setResType(resType);
								pw.setSpecialty(specialty);
								slist.add(pw);				
								
							
							
						}else if(assign.equals("yes")&&!weekname.equals("无")){
							
								
								pw.setCdictId(cdictId);
								pw.setWeekname(weekname);
								pw.setResLevel(resLevel);
								pw.setResType(resType);
								pw.setSpecialty(specialty);
								slist.add(pw);
							
							
						}else if(assign.equals("all")){
								pw.setCdictId(cdictId);
								pw.setWeekname(weekname);
								pw.setResLevel(resLevel);
								pw.setResType(resType);
								pw.setSpecialty(specialty);
								slist.add(pw);
							
						}
						
						weekname="无";
					}
			}
				}
			}
			
		}else if(level==2){
			
			//资源类别
			TawSystemDictType first;
			first= tawSystemDictTypeManager.getDictByDictId(dictId);
			if(null!=first||!"".equals(first)){
				
				String  resType = first.getDictName();
				//巡检专业
				String  parentDictId = first.getParentDictId();
				TawSystemDictType  specialt= tawSystemDictTypeManager.getDictByDictId(parentDictId);
				String specialty = specialt.getDictName();
				
				//资源级别
				List<TawSystemDictType>  childlist= tawSystemDictTypeManager.getDictSonsByDictid(dictId);
				int i = childlist.size();	
				String twochildDictId;
				PnrResSetWeekTime pw;
				String weekname="无";
				String cdictId ;
				for(int j=0;j<i;j++){
					pw=new PnrResSetWeekTime();
					cdictId= childlist.get(j).getDictId();
					//获取周期
					List<PnrResToWeekTime> pnrResToWeekTime = getPnrResToWeekTimeByPnrId(cdictId);
					if(pnrResToWeekTime.size()>0){
						TawSystemDictType wt= tawSystemDictTypeManager.getDictByDictId(pnrResToWeekTime.get(0).getWeekID());
						if(null != wt || !"".equals(wt)){
							
							weekname=wt.getDictName();	
						}
					}
					String resLevel = childlist.get(j).getDictName();
					
					pw.setCdictId(cdictId);
					pw.setWeekname(weekname);
					pw.setResLevel(resLevel);
					pw.setResType(resType);
					pw.setSpecialty(specialty);
					slist.add(pw);			
					
					if(assign.equals("no")&&weekname.equals("无")){
						
						
						pw.setCdictId(cdictId);
						pw.setWeekname(weekname);
						pw.setResLevel(resLevel);
						pw.setResType(resType);
						pw.setSpecialty(specialty);
						slist.add(pw);				
						
					
					
				}else if(assign.equals("yes")&&!weekname.equals("无")){
					
						
						pw.setCdictId(cdictId);
						pw.setWeekname(weekname);
						pw.setResLevel(resLevel);
						pw.setResType(resType);
						pw.setSpecialty(specialty);
						slist.add(pw);
					
					
				}else if(assign.equals("all")){
						pw.setCdictId(cdictId);
						pw.setWeekname(weekname);
						pw.setResLevel(resLevel);
						pw.setResType(resType);
						pw.setSpecialty(specialty);
						slist.add(pw);
					
				}
				}
			}
			
						
		}else if(level==3){
			
			//资源级别
			TawSystemDictType first;
			first= tawSystemDictTypeManager.getDictByDictId(dictId);
			if(null!=first||!"".equals(first)){
			String  resLevel = first.getDictName();
			//资源类别
			String  parentDictId = first.getParentDictId();
			TawSystemDictType  resTyp= tawSystemDictTypeManager.getDictByDictId(parentDictId);
			String resType = resTyp.getDictName();
			//巡检专业
			String  pDictId = resTyp.getParentDictId();
			TawSystemDictType  special= tawSystemDictTypeManager.getDictByDictId(pDictId);
			String specialty = special.getDictName();
			
			String twochildDictId;
			PnrResSetWeekTime pw;
			
				String weekname="无";

				//获取周期
				List<PnrResToWeekTime> pnrResToWeekTime = getPnrResToWeekTimeByPnrId(dictId);
				if(pnrResToWeekTime.size()>0){
					TawSystemDictType wt= tawSystemDictTypeManager.getDictByDictId(pnrResToWeekTime.get(0).getWeekID());
					if(null != wt|| !"".equals(wt)){
						
						weekname=wt.getDictName();	
					}
				}
				
				pw=new PnrResSetWeekTime();
					pw.setCdictId(dictId);
				pw.setWeekname(weekname);
				pw.setResLevel(resLevel);
				pw.setResType(resType);
				pw.setSpecialty(specialty);
				slist.add(pw);
				if(assign.equals("no")&&weekname.equals("无")){
					
					
					pw.setCdictId(dictId);
					pw.setWeekname(weekname);
					pw.setResLevel(resLevel);
					pw.setResType(resType);
					pw.setSpecialty(specialty);
					slist.add(pw);				
					
				
				
			}else if(assign.equals("yes")&&!weekname.equals("无")){
				
					
					pw.setCdictId(dictId);
					pw.setWeekname(weekname);
					pw.setResLevel(resLevel);
					pw.setResType(resType);
					pw.setSpecialty(specialty);
					slist.add(pw);
				
				
			}else if(assign.equals("all")){
					pw.setCdictId(dictId);
					pw.setWeekname(weekname);
					pw.setResLevel(resLevel);
					pw.setResType(resType);
					pw.setSpecialty(specialty);
					slist.add(pw);
				
			}
			}
		}
		return slist;
	}	
*/
	public Map<String,String> getTawSystemDictTypeByParentID(
			String parentId) {
		// TODO Auto-generated method stub
		StringBuffer hql = new StringBuffer();
		Map<String,String> m;
		hql.append(" from TawSystemDictType p where p.parentDictId='").append(parentId).append("'");
		
		List<TawSystemDictType> list =pnrResToWeekTimeDao.findByHql(hql.toString());
		int k = list.size();
		m = new HashMap();
		if(k>0){
			for(int j=0;j<k;j++){
				String ditId = list.get(j).getDictId();
				String dname = list.get(j).getDictName();
				m.put(ditId, dname);
			}
		}
		
		return m;
	}	
	public List<PnrResSetWeekTime> getTawSystemDictTypeByDictId(List<TawSystemDictType> list){
		
		List<PnrResSetWeekTime>  plist = new ArrayList<PnrResSetWeekTime>();
		PnrResSetWeekTime p= null;
		int size = list.size();
		
		for(int i=0;i<size;i++){
			p= new PnrResSetWeekTime();
			
			TawSystemDictType  resTyp= tawSystemDictTypeManager.getDictByDictId(list.get(i).getParentDictId());
			List<PnrResToWeekTime> pnrList= getPnrResToWeekTimeByPnrId(list.get(i).getDictId());

			String resLevel ="";
			String resType ="";
			String specialty ="";
			String weekname ="无";
			
			try {
				resLevel = tawSystemDictTypeManager.id2Name(list.get(i).getDictId());
				resType = tawSystemDictTypeManager.id2Name(list.get(i).getParentDictId());
				specialty = tawSystemDictTypeManager.id2Name(resTyp.getParentDictId());
				if(pnrList.size()>0){
					
					weekname = tawSystemDictTypeManager.id2Name(pnrList.get(0).getWeekID());
				}
				
				p.setCdictId(list.get(i).getDictId());
				p.setResLevel(resLevel);
				p.setResType(resType);
				p.setSpecialty(specialty);
				p.setWeekname(weekname);
				
				plist.add(p);
			} catch (DictDAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return plist;
		
	}
}
