package com.boco.eoms.commons.sheet.special.dao.hibernate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.commons.sheet.special.model.TawSheetSpecial;
import com.boco.eoms.commons.sheet.special.dao.TawSheetSpecialDao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Query;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 * 
 * @author panlong 下午05:38:59
 */
public class TawSheetSpecialDaoHibernate extends BaseDaoHibernate implements
		TawSheetSpecialDao {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.boco.eoms.commons.sheet.special.dao.TawSheetSpecialDao#listSpecialsByType(java.lang.String)
	 */
	public List listSpecialsByType(final String specialType) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from TawSheetSpecial tawSheetSpecial where tawSheetSpecial.specialtype:=specialtype";

				Query query = session.createQuery(queryStr);
				query.setString("specialtype", specialType);

				List result = query.list();

				return result;
			}
		};
		return (List) getHibernateTemplate().execute(callback);
	}

	/**
	 * @see com.boco.eoms.commons.sheet.special.dao.TawSheetSpecialDao#getTawSheetSpecials(com.boco.eoms.commons.sheet.special.model.TawSheetSpecial)
	 */
	public List getTawSheetSpecials(final TawSheetSpecial tawSheetSpecial) {
		return getHibernateTemplate().find("from TawSheetSpecial");

		/*
		 * Remove the line above and uncomment this code block if you want to
		 * use Hibernate's Query by Example API. if (tawSheetSpecial == null) {
		 * return getHibernateTemplate().find("from TawSheetSpecial"); } else { //
		 * filter on properties set in the tawSheetSpecial HibernateCallback
		 * callback = new HibernateCallback() { public Object
		 * doInHibernate(Session session) throws HibernateException { Example ex =
		 * Example.create(tawSheetSpecial).ignoreCase().enableLike(MatchMode.ANYWHERE);
		 * return session.createCriteria(TawSheetSpecial.class).add(ex).list(); } };
		 * return (List) getHibernateTemplate().execute(callback); }
		 */
	}

	/**
	 * @see com.boco.eoms.commons.sheet.special.dao.TawSheetSpecialDao#getTawSheetSpecial(Integer
	 *      id)
	 */
	public TawSheetSpecial getTawSheetSpecial(final Integer id) {
		TawSheetSpecial tawSheetSpecial = (TawSheetSpecial) getHibernateTemplate()
				.get(TawSheetSpecial.class, id);
		if (tawSheetSpecial == null) {
			throw new ObjectRetrievalFailureException(TawSheetSpecial.class, id);
		}

		return tawSheetSpecial;
	}

	/**
	 * @see com.boco.eoms.commons.sheet.special.dao.TawSheetSpecialDao#saveTawSheetSpecial(TawSheetSpecial
	 *      tawSheetSpecial)
	 */
	public void saveTawSheetSpecial(final TawSheetSpecial tawSheetSpecial) {
		if ((tawSheetSpecial.getId() == null)
				|| (tawSheetSpecial.getId().equals("")))
			getHibernateTemplate().save(tawSheetSpecial);
		else
			getHibernateTemplate().saveOrUpdate(tawSheetSpecial);
	}

	/**
	 * @see com.boco.eoms.commons.sheet.special.dao.TawSheetSpecialDao#removeTawSheetSpecial(Integer
	 *      id)
	 */
	public void removeTawSheetSpecial(final Integer id) {
		getHibernateTemplate().delete(id);
	}

	/**
	 * ���ڷ�ҳ��ʾ curPage ��ǰҳ�� pageSize ÿҳ��ʾ�� whereStr
	 * where�������䣬������"where"��ͷ,����Ϊ��
	 */
	public Map getTawSheetSpecials(final Integer curPage,
			final Integer pageSize, final String whereStr) {
		// filter on properties set in the tawSheetSpecial
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String queryStr = "from TawSheetSpecial";
				if (whereStr != null && whereStr.length() > 0)
					queryStr += whereStr;
				String queryCountStr = "select count(*) " + queryStr;

				int total = ((Integer) session.createQuery(queryCountStr)
						.iterate().next()).intValue();
				Query query = session.createQuery(queryStr);
				query
						.setFirstResult(pageSize.intValue()
								* (curPage.intValue()));
				query.setMaxResults(pageSize.intValue());
				List result = query.list();
				HashMap map = new HashMap();
				map.put("total", new Integer(total));
				map.put("result", result);
				return map;
			}
		};
		return (Map) getHibernateTemplate().execute(callback);
	}

	public Map getTawSheetSpecials(final Integer curPage, final Integer pageSize) {
		return this.getTawSheetSpecials(curPage, pageSize, null);
	}

	/**
	 * 根据专业ID查询专业信息
	 * 
	 * @param areaid
	 * @return
	 */
	public TawSheetSpecial getSpecialByspecialId(String specialid) {
		String hql = " from TawSheetSpecial spe where spe.speid='" + specialid
				+ "'";
		return (TawSheetSpecial) getHibernateTemplate().find(hql).get(0);
	}

	/**
	 * 查询某专业的下一级专业信息
	 * 
	 * @param specialid
	 * @return
	 */
	public List getSonspecialByspecialId(String specialid) {
		String hql = " from TawSheetSpecial spe where spe.parspeid='"
				+ specialid + "'";
		return (ArrayList) getHibernateTemplate().find(hql);
	}

	/**
	 * 查询同级专业信息
	 * 
	 * @param parentspecialid
	 * @param ordercode
	 * @return
	 */
	public List getSameLevelspecial(String parentspecialid, Integer ordercode) {
		String hql = " from TawSheetSpecial spe where spe.parspeid='"
				+ parentspecialid + "' and spe.ordercode='" + ordercode + "'";
		return (ArrayList) getHibernateTemplate().find(hql);
	}

	/**
	 * 查询某专业是否存在
	 * 
	 * @param specialname
	 * @return
	 */
	public boolean isExitspecialName(String specialid) {
		String hql = " from TawSheetSpecial spe where spe.speid='" + specialid
				+ "'";
		List list = (ArrayList) getHibernateTemplate().find(hql);
		if (list != null && list.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 查询某专业的所有子专业信息
	 * 
	 * @param specialid
	 * @return
	 */
	public List getAllSonspecialByspecialid(String specialid) {
		String hql = " from TawSheetSpecial spe where spe.speid like '"
				+ specialid + "%' and spe.speid !='" + specialid + "'";
		return (ArrayList) getHibernateTemplate().find(hql);
	}

}
