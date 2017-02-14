package com.boco.eoms.partner.siteEva.dao.hibernate;

import java.util.List;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.commons.system.dict.dao.ID2NameDAO;
import com.boco.eoms.partner.siteEva.dao.ISiteEvaTemplateDao;
import com.boco.eoms.partner.siteEva.model.SiteEvaTemplate;
import com.boco.eoms.partner.siteEva.util.SiteEvaConstants;

public class SiteEvaTemplateDaoHibernate extends BaseDaoHibernate implements
		ISiteEvaTemplateDao, ID2NameDAO {

	public SiteEvaTemplate getTemplate(String id) {
		SiteEvaTemplate template = (SiteEvaTemplate) getHibernateTemplate().get(
				SiteEvaTemplate.class, id);
		if (null == template) {
			// throw new ObjectRetrisiteEvalFailureException(SiteEvaTemplate.class, id);
			template = new SiteEvaTemplate();
		}
		return template; 
	}

	public void removeTemplate(SiteEvaTemplate template) {
		getHibernateTemplate().delete(template);
	}

	public void saveTemplate(SiteEvaTemplate template) {
		if (null == template.getId() || "".equals(template.getId())) {
			getHibernateTemplate().save(template);
		} else {
			getHibernateTemplate().merge(template);
		}
	}

	public String id2Name(String id) {
		String templateName = "";
		SiteEvaTemplate template = getTemplate(id);
		if (null != template.getId() && !"".equals(template.getId())) {
			if (null != template.getTemplateName()
					&& !"".equals(template.getTemplateName())) {
				templateName = template.getTemplateName();
			} else {
				templateName = SiteEvaConstants.NODE_NONAME;
			}
		} else {
			templateName = SiteEvaConstants.NODE_NONAME;
		}
		return templateName;
	}
	
	//根据模版中存储的belongNode，找到属于同一节点的所有模版2009-8-7
	public List getTemplateByblnode(String node){
		String hql = "from SiteEvaTemplate tem where tem.belongNode = '"+node+"' and deleted = '0' ";
		List list = getHibernateTemplate().find(hql);
		return list;
	}
	
}
