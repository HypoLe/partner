package com.boco.eoms.partner.tranEva.dao.hibernate;

import java.util.List;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.commons.system.dict.dao.ID2NameDAO;
import com.boco.eoms.partner.tranEva.dao.ITranEvaTemplateDao;
import com.boco.eoms.partner.tranEva.model.TranEvaTemplate;
import com.boco.eoms.partner.tranEva.util.TranEvaConstants;

public class TranEvaTemplateDaoHibernate extends BaseDaoHibernate implements
		ITranEvaTemplateDao, ID2NameDAO {

	public TranEvaTemplate getTemplate(String id) {
		TranEvaTemplate template = (TranEvaTemplate) getHibernateTemplate().get(
				TranEvaTemplate.class, id);
		if (null == template) {
			// throw new ObjectRetritranEvalFailureException(TranEvaTemplate.class, id);
			template = new TranEvaTemplate();
		}
		return template;
	}

	public void removeTemplate(TranEvaTemplate template) {
		getHibernateTemplate().delete(template);
	}

	public void saveTemplate(TranEvaTemplate template) {
		if (null == template.getId() || "".equals(template.getId())) {
			getHibernateTemplate().save(template);
		} else {
			getHibernateTemplate().merge(template);
		}
	}

	public String id2Name(String id) {
		String templateName = "";
		TranEvaTemplate template = getTemplate(id);
		if (null != template.getId() && !"".equals(template.getId())) {
			if (null != template.getTemplateName()
					&& !"".equals(template.getTemplateName())) {
				templateName = template.getTemplateName();
			} else {
				templateName = TranEvaConstants.NODE_NONAME;
			}
		} else {
			templateName = TranEvaConstants.NODE_NONAME;
		}
		return templateName;
	}
	
	//根据模版中存储的belongNode，找到属于同一节点的所有模版2009-8-7
	public List getTemplateByblnode(String node){
		String hql = "from TranEvaTemplate tem where tem.belongNode = '"+node+"' and deleted = '0' ";
		List list = getHibernateTemplate().find(hql);
		return list;
	}
	
}
