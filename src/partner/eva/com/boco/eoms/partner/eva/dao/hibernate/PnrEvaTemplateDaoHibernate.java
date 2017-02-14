package com.boco.eoms.partner.eva.dao.hibernate;

import java.util.List;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.commons.system.dict.dao.ID2NameDAO;
import com.boco.eoms.partner.eva.dao.IPnrEvaTemplateDao;
import com.boco.eoms.partner.eva.model.PnrEvaTemplate;
import com.boco.eoms.partner.eva.util.PnrEvaConstants;

public class PnrEvaTemplateDaoHibernate extends BaseDaoHibernate implements
		IPnrEvaTemplateDao, ID2NameDAO {


	public PnrEvaTemplate getTemplate(String id) {
		PnrEvaTemplate template = (PnrEvaTemplate) getHibernateTemplate().get(
				PnrEvaTemplate.class, id);
		if (null == template) {
			// throw new ObjectRetrievalFailureException(EvaTemplate.class, id);
			template = new PnrEvaTemplate();
		}
		return template;
	}

	public void removeTemplate(PnrEvaTemplate template) {
		getHibernateTemplate().delete(template);
	}

	public void saveTemplate(PnrEvaTemplate template) {
		if (null == template.getId() || "".equals(template.getId())) {
			getHibernateTemplate().save(template);
		} else {
			getHibernateTemplate().merge(template);
		}
	}

	public String id2Name(String id) {
		String templateName = "";
		PnrEvaTemplate template = getTemplate(id);
		if (null != template.getId() && !"".equals(template.getId())) {
			if (null != template.getTemplateName()
					&& !"".equals(template.getTemplateName())) {
				templateName = template.getTemplateName();
			} else {
				templateName = PnrEvaConstants.NODE_NONAME;
			}
		} else {
			templateName = PnrEvaConstants.NODE_NONAME;
		}
		return templateName;
	}
	
	//根据模版中存储的belongNode，找到属于同一节点的所有模版2009-8-7
	public List getTemplateByblnode(String node){
		String hql = "from PnrEvaTemplate tem where tem.belongNode = '"+node+"' and deleted = '0' ";
		List list = getHibernateTemplate().find(hql);
		return list;
	}
	
	public List getTemplateByCondition(String where) {	
		return getHibernateTemplate().find("from PnrEvaTemplate where 1=1 " + where);
	}

	public List getActiveTemplateByExecuteType(String templateType,String executeType){
//		String hql = "select tem from PnrEvaTemplate tem,PnrEvaTree tree where tem.id=tree.objectId and tem.activated='1' and tem.executeType='"+executeType+"' and tree.parentNodeId ='"+templateType+"' and tem.deleted = '0' ";
		String hql = "select tem from PnrEvaTemplate tem,PnrEvaTree tree where tem.id=tree.objectId and tem.activated='1' and tem.executeType='"+executeType+"' and tree.nodeId like'"+templateType+"%' and tem.deleted = '0' order by tree.nodeId";
		List list = getHibernateTemplate().find(hql);
		return list;
	}
	public List getNextTemplateByExecuteType(String parentNodeId,String executeType){
		String hql = "select tem from PnrEvaTemplate tem,PnrEvaTree tree where tem.id=tree.objectId and tem.activated='1' and tem.executeType='"+executeType+"' and tree.parentNodeId = '"+parentNodeId+"' and tem.deleted = '0' order by tree.nodeId";
		List list = getHibernateTemplate().find(hql);
		return list;
	}
}
