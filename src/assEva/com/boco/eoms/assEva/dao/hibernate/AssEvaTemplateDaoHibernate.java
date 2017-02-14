package com.boco.eoms.assEva.dao.hibernate;

import java.util.List;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.commons.system.dict.dao.ID2NameDAO;
import com.boco.eoms.assEva.dao.IAssEvaTemplateDao;
import com.boco.eoms.assEva.model.AssEvaTemplate;
import com.boco.eoms.assEva.util.AssEvaConstants;

public class AssEvaTemplateDaoHibernate extends BaseDaoHibernate implements
		IAssEvaTemplateDao, ID2NameDAO {

	public AssEvaTemplate getTemplate(String id) {
		AssEvaTemplate template = (AssEvaTemplate) getHibernateTemplate().get(
				AssEvaTemplate.class, id);
		if (null == template) {
			// throw new ObjectRetriassEvalFailureException(AssEvaTemplate.class, id);
			template = new AssEvaTemplate();
		}
		return template;
	}

	public void removeTemplate(AssEvaTemplate template) {
		getHibernateTemplate().delete(template);
	}

	public void saveTemplate(AssEvaTemplate template) {
		if (null == template.getId() || "".equals(template.getId())) {
			getHibernateTemplate().save(template);
		} else {
			getHibernateTemplate().merge(template);
		}
	}

	public String id2Name(String id) {
		String templateName = "";
		AssEvaTemplate template = getTemplate(id);
		if (null != template.getId() && !"".equals(template.getId())) {
			if (null != template.getTemplateName()
					&& !"".equals(template.getTemplateName())) {
				templateName = template.getTemplateName();
			} else {
				templateName = AssEvaConstants.NODE_NONAME;
			}
		} else {
			templateName = AssEvaConstants.NODE_NONAME;
		}
		return templateName;
	}
	
	//根据模版中存储的belongNode，找到属于同一节点的所有模版2009-8-7
	public List getTemplateByblnode(String node){
		String hql = "from AssEvaTemplate tem where tem.belongNode = '"+node+"' and deleted = '0' ";
		List list = getHibernateTemplate().find(hql);
		return list;
	}
	
}
