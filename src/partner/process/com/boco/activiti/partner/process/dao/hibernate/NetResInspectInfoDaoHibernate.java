package com.boco.activiti.partner.process.dao.hibernate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;

import com.boco.activiti.partner.process.dao.INetResInspectInfoDao;
import com.boco.activiti.partner.process.model.NetResInspect;
import com.boco.activiti.partner.process.model.NetResInspectTurnToSendModel;
import com.boco.activiti.partner.process.model.PnrAndroidPhotoFile;
import com.boco.activiti.partner.process.model.PnrPrecheckPhoto;
import com.boco.activiti.partner.process.po.NetResInspectModel;
import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;

/**
 
 */
public class NetResInspectInfoDaoHibernate extends BaseDaoHibernate implements INetResInspectInfoDao {

	@Override
	public void saveTurnToSendInfo(NetResInspectTurnToSendModel netResInspectTurnToSendModel) {
		try{
			getHibernateTemplate().save(netResInspectTurnToSendModel);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	/**
	 * 	 手机工单代办列表查询
	 	 * @author zhoukeqing
	 	 * @title: searchListsendundo
	 	 * @date Jul 13, 2016 2:42:35 PM
	 	 * @param param
	 	 * @return String
	 */
	public List<NetResInspect> searchListsendundo(String userid,
			int pageSize, int pageIndex) {
		String sql = "select temp2.* from (select temp1.*, rownum num from (";
		sql+=	"select *  from pnr_act_netresinspect_main m "+
				"  where  m.task_def_key in ('siteCheck','defectTreatment') and  m.assignee = '"+userid+"'"+
				"  order by m.reported_date desc";
		sql += ") temp1 where rownum <="
			+pageSize* (pageIndex + 1)  + ") temp2 where temp2.num >" + pageSize * pageIndex;
		System.out.println("手机工单代办列表查询:" + sql);

		SQLQuery query = getSession().createSQLQuery(sql);
		query.addEntity(NetResInspect.class);
		
		List<NetResInspect> list = query.list();
		System.out.println("result size == " + list.size());
		return list;
	}
	/**
	 * 	 手机工单代办列表总数查询
	 	 * @author zhoukeqing
	 	 * @title: searchListsendundoTotal
	 	 * @date Jul 13, 2016 2:42:35 PM
	 	 * @param param
	 	 * @return String
	 */
	@Override
	public int searchListsendundoTotal(String userid) {
		String sql=	"select count(*)  from pnr_act_netresinspect_main m "+
		"  where  m.task_def_key in ('siteCheck','defectTreatment') and  m.assignee = '"+userid+"'";
		System.out.println("工单查询结果集：" + sql);
		
		@SuppressWarnings("rawtypes")
		List query = this.getSession().createSQLQuery(sql).list();
		BigDecimal total = null;
		int inttotal = 0;
		try {
			total = (BigDecimal) query.get(0);
			inttotal = total.intValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return inttotal;
	}
	/**
	 * 	 手机工单详情页面查询
	 	 * @author zhoukeqing
	 	 * @title: serchDetailAndroid
	 	 * @date Jul 13, 2016 2:42:35 PM
	 	 * @param param
	 	 * @return String
	 */
	public NetResInspect serchDetailAndroid(String processInstanceId){
		String sql=" select * from pnr_act_netresinspect_main m where m.process_instance_id='"+processInstanceId+"'";
		SQLQuery query = getSession().createSQLQuery(sql);
		query.addEntity(NetResInspect.class);
		NetResInspect netResInspect=new NetResInspect();
		List<NetResInspect> list = query.list();
		if(list != null && list.size()>0){
			netResInspect = (NetResInspect)list.get(0);
		}
		return netResInspect;
	}
	/**
	 * 	 查询pnr_android_photo
	 	 * @author zhoukeqing
	 	 * @title: serchAndroidPhoto
	 	 * @date Jul 13, 2016 2:42:35 PM
	 	 * @param param
	 	 * @return String
	 */
	@Override
	public List<PnrAndroidPhotoFile> serchAndroidPhoto(String processInstanceId) {
		String sql=" select * from pnr_android_photo p where p.picture_id='"+processInstanceId+"'";
		SQLQuery query = getSession().createSQLQuery(sql);
		query.addEntity(PnrAndroidPhotoFile.class);
		List<PnrAndroidPhotoFile> list = query.list();
		return list;
	}
	@Override
	public void saveFlowPhone(String processInstanceId,String type) {
		String sql=" select * from pnr_android_photo p where p.picture_id='"+processInstanceId+"' and photo_sub_type='"+type+"'";
		SQLQuery query = getSession().createSQLQuery(sql);
		query.addEntity(PnrAndroidPhotoFile.class);
		List<PnrAndroidPhotoFile> list = query.list();
		if(list!=null&&list.size()>0){
			for(int i=0;i<list.size();i++){
				PnrAndroidPhotoFile pnrAndroidPhotoFile=list.get(i);
				String sql1="select * from pnr_act_precheck_photo_ship sp where sp.processinstance_id='"
					+pnrAndroidPhotoFile.getPicture_id()+"' and sp.photo_id='"+pnrAndroidPhotoFile.getId()+"'";
				SQLQuery query1 = getSession().createSQLQuery(sql1);
				query.addEntity(PnrPrecheckPhoto.class);
				List<PnrPrecheckPhoto> list1 = query1.list();
				if(list1==null||list1.size()==0){
					PnrPrecheckPhoto pnrPrecheckPhoto=new PnrPrecheckPhoto();
					pnrPrecheckPhoto.setPhotoId(pnrAndroidPhotoFile.getId());
					pnrPrecheckPhoto.setProcessInstanceId(pnrAndroidPhotoFile.getPicture_id());
					getHibernateTemplate().save(pnrPrecheckPhoto);
				}
				
			}
		}
	}
	/**
	 * 	 将手机拍摄的照片和流程关联  新建工单用
	 	 * @author zhoukeqing
	 	 * @title: saveFlowPhone
	 	 * @date Jul 15, 2016 2:42:35 PM
	 	 * @param param
	 	 * @return String
	 */
	public void savePhoneStarFlow(List<PnrAndroidPhotoFile> list,String processInstanceId){
		if(list!=null&&list.size()>0){
			for(int i=0;i<list.size();i++){
				PnrAndroidPhotoFile pnrAndroidPhotoFile=list.get(i);
				pnrAndroidPhotoFile.setPicture_id(processInstanceId);
				getHibernateTemplate().update(pnrAndroidPhotoFile);
				PnrPrecheckPhoto pnrPrecheckPhoto=new PnrPrecheckPhoto();
				pnrPrecheckPhoto.setPhotoId(pnrAndroidPhotoFile.getId());
				pnrPrecheckPhoto.setProcessInstanceId(pnrAndroidPhotoFile.getPicture_id());
				getHibernateTemplate().save(pnrPrecheckPhoto);
			}
		}
	}
	/**
	 * 	 将手机拍摄的照片和流程关联  派发子流程用
	 	 * @author zhoukeqing
	 	 * @title: saveFlowPhone
	 	 * @date Jul 15, 2016 2:42:35 PM
	 	 * @param param
	 	 * @return String
	 */
	public void savePhoneSubFlow(List<PnrAndroidPhotoFile> list,String processInstanceId){
		if(list!=null&&list.size()>0){
			for(int i=0;i<list.size();i++){
				PnrAndroidPhotoFile pnrAndroidPhotoFile=list.get(i);
				PnrPrecheckPhoto pnrPrecheckPhoto=new PnrPrecheckPhoto();
				pnrPrecheckPhoto.setPhotoId(pnrAndroidPhotoFile.getId());
				pnrPrecheckPhoto.setProcessInstanceId(processInstanceId);
				getHibernateTemplate().save(pnrPrecheckPhoto);
			}
		}
	}


}
