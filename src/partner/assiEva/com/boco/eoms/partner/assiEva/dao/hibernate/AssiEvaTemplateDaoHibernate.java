package com.boco.eoms.partner.assiEva.dao.hibernate;

import java.util.List;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.commons.system.dict.dao.ID2NameDAO;
import com.boco.eoms.partner.assiEva.dao.IAssiEvaTemplateDao;
import com.boco.eoms.partner.assiEva.model.AssiEvaTemplate;
import com.boco.eoms.partner.assiEva.util.AssiEvaConstants;

public class AssiEvaTemplateDaoHibernate extends BaseDaoHibernate implements
		IAssiEvaTemplateDao, ID2NameDAO {

	public AssiEvaTemplate getTemplate(String id) {
		AssiEvaTemplate template = (AssiEvaTemplate) getHibernateTemplate().get(
				AssiEvaTemplate.class, id);
		if (null == template) {
			// throw new ObjectRetriassiEvalFailureException(AssiEvaTemplate.class, id);
			template = new AssiEvaTemplate();
		}
		return template;
	}

	public void removeTemplate(AssiEvaTemplate template) {
		getHibernateTemplate().delete(template);
	}

	public void saveTemplate(AssiEvaTemplate template) {
		if (null == template.getId() || "".equals(template.getId())) {
			getHibernateTemplate().save(template);
		} else {
			getHibernateTemplate().merge(template);
		}
	}

	public String id2Name(String id) {
		String templateName = "";
		AssiEvaTemplate template = getTemplate(id);
		if (null != template.getId() && !"".equals(template.getId())) {
			if (null != template.getTemplateName()
					&& !"".equals(template.getTemplateName())) {
				templateName = template.getTemplateName();
			} else {
				templateName = AssiEvaConstants.NODE_NONAME;
			}
		} else {
			templateName = AssiEvaConstants.NODE_NONAME;
		}
		return templateName;
	}
	
	//根据模版中存储的belongNode，找到属于同一节点的所有模版2009-8-7
	public List getTemplateByblnode(String node){
		String hql = "from AssiEvaTemplate tem where tem.belongNode = '"+node+"' and deleted = '0' ";
		List list = getHibernateTemplate().find(hql);
		return list;
	}
	
}
