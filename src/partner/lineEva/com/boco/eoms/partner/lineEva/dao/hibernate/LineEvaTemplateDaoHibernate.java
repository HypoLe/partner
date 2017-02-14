package com.boco.eoms.partner.lineEva.dao.hibernate;

import java.util.List;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.commons.system.dict.dao.ID2NameDAO;
import com.boco.eoms.partner.lineEva.dao.ILineEvaTemplateDao;
import com.boco.eoms.partner.lineEva.model.LineEvaTemplate;
import com.boco.eoms.partner.lineEva.util.LineEvaConstants;

public class LineEvaTemplateDaoHibernate extends BaseDaoHibernate implements
		ILineEvaTemplateDao, ID2NameDAO {

	public LineEvaTemplate getTemplate(String id) {
		LineEvaTemplate template = (LineEvaTemplate) getHibernateTemplate().get(
				LineEvaTemplate.class, id);
		if (null == template) {
			// throw new ObjectRetrilineEvalFailureException(LineEvaTemplate.class, id);
			template = new LineEvaTemplate();
		}
		return template;
	}

	public void removeTemplate(LineEvaTemplate template) {
		getHibernateTemplate().delete(template);
	}

	public void saveTemplate(LineEvaTemplate template) {
		if (null == template.getId() || "".equals(template.getId())) {
			getHibernateTemplate().save(template);
		} else {
			getHibernateTemplate().merge(template);
		}
	}

	public String id2Name(String id) {
		String templateName = "";
		LineEvaTemplate template = getTemplate(id);
		if (null != template.getId() && !"".equals(template.getId())) {
			if (null != template.getTemplateName()
					&& !"".equals(template.getTemplateName())) {
				templateName = template.getTemplateName();
			} else {
				templateName = LineEvaConstants.NODE_NONAME;
			}
		} else {
			templateName = LineEvaConstants.NODE_NONAME;
		}
		return templateName;
	}
	
	//根据模版中存储的belongNode，找到属于同一节点的所有模版2009-8-7
	public List getTemplateByblnode(String node){
		String hql = "from LineEvaTemplate tem where tem.belongNode = '"+node+"' and deleted = '0' ";
		List list = getHibernateTemplate().find(hql);
		return list;
	}
	
}
