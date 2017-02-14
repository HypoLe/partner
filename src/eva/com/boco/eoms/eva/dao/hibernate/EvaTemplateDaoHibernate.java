package com.boco.eoms.eva.dao.hibernate;

import java.util.List;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.commons.system.dict.dao.ID2NameDAO;
import com.boco.eoms.eva.dao.IEvaTemplateDao;
import com.boco.eoms.eva.model.EvaTemplate;
import com.boco.eoms.eva.util.EvaConstants;

public class EvaTemplateDaoHibernate extends BaseDaoHibernate implements
		IEvaTemplateDao, ID2NameDAO {

	public EvaTemplate getTemplate(String id) {
		EvaTemplate template = (EvaTemplate) getHibernateTemplate().get(
				EvaTemplate.class, id);
		if (null == template) {
			// throw new ObjectRetrievalFailureException(EvaTemplate.class, id);
			template = new EvaTemplate();
		}
		return template;
	}

	public void removeTemplate(EvaTemplate template) {
		getHibernateTemplate().delete(template);
	}

	public void saveTemplate(EvaTemplate template) {
		if (null == template.getId() || "".equals(template.getId())) {
			getHibernateTemplate().save(template);
		} else {
			getHibernateTemplate().merge(template);
		}
	}

	public String id2Name(String id) {
		String templateName = "";
		EvaTemplate template = getTemplate(id);
		if (null != template.getId() && !"".equals(template.getId())) {
			if (null != template.getTemplateName()
					&& !"".equals(template.getTemplateName())) {
				templateName = template.getTemplateName();
			} else {
				templateName = EvaConstants.NODE_NONAME;
			}
		} else {
			templateName = EvaConstants.NODE_NONAME;
		}
		return templateName;
	}
	
	//根据模版中存储的belongNode，找到属于同一节点的所有模版2009-8-7
	public List getTemplateByblnode(String node,String del){
		String hql = "from EvaTemplate tem where tem.belongNode = '"+node+"'";
		if(!"".equals(del)){
			hql+=" and deleted = '"+del+"' ";
		}
		List list = getHibernateTemplate().find(hql);
		return list;
	}

	public List getTemplates(String where){
		String hql = "from EvaTemplate tem where tem.deleted = '0'";
		if(!"".equals(where)){
			hql += where;
		}
		List list = getHibernateTemplate().find(hql);
		return list;
	}	
}
