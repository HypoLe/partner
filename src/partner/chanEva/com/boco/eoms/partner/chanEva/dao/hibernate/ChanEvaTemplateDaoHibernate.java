package com.boco.eoms.partner.chanEva.dao.hibernate;

import java.util.List;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.commons.system.dict.dao.ID2NameDAO;
import com.boco.eoms.partner.chanEva.dao.IChanEvaTemplateDao;
import com.boco.eoms.partner.chanEva.model.ChanEvaTemplate;
import com.boco.eoms.partner.chanEva.util.ChanEvaConstants;

public class ChanEvaTemplateDaoHibernate extends BaseDaoHibernate implements
		IChanEvaTemplateDao, ID2NameDAO {

	public ChanEvaTemplate getTemplate(String id) {
		ChanEvaTemplate template = (ChanEvaTemplate) getHibernateTemplate().get(
				ChanEvaTemplate.class, id);
		if (null == template) {
			// throw new ObjectRetrichanEvalFailureException(ChanEvaTemplate.class, id);
			template = new ChanEvaTemplate();
		}
		return template;
	}

	public void removeTemplate(ChanEvaTemplate template) {
		getHibernateTemplate().delete(template);
	}

	public void saveTemplate(ChanEvaTemplate template) {
		if (null == template.getId() || "".equals(template.getId())) {
			getHibernateTemplate().save(template);
		} else {
			getHibernateTemplate().merge(template);
		}
	}

	public String id2Name(String id) {
		String templateName = "";
		ChanEvaTemplate template = getTemplate(id);
		if (null != template.getId() && !"".equals(template.getId())) {
			if (null != template.getTemplateName()
					&& !"".equals(template.getTemplateName())) {
				templateName = template.getTemplateName();
			} else {
				templateName = ChanEvaConstants.NODE_NONAME;
			}
		} else {
			templateName = ChanEvaConstants.NODE_NONAME;
		}
		return templateName;
	}
	
	//根据模版中存储的belongNode，找到属于同一节点的所有模版2009-8-7
	public List getTemplateByblnode(String node){
		String hql = "from ChanEvaTemplate tem where tem.belongNode = '"+node+"' and deleted = '0' ";
		List list = getHibernateTemplate().find(hql);
		return list;
	}
	
}
