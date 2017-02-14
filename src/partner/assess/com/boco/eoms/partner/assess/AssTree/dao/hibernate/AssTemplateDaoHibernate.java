package com.boco.eoms.partner.assess.AssTree.dao.hibernate;

import java.util.List;

import com.boco.eoms.partner.assess.AssTree.dao.IAssTemplateDao;
import com.boco.eoms.partner.assess.AssTree.model.AssTemplate;
import com.boco.eoms.partner.assess.util.AssConstants;
import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.commons.system.dict.dao.ID2NameDAO;
/**
 * <p>
 * Title:考核模板dao操作基类
 * </p>
 * <p>
 * Description:考核模板dao操作基类
 * </p>
 * <p>
 * Date:Nov 26, 2010 5:52:06 PM
 * </p>
 * 
 * @author 戴志刚
 * @version 1.0
 * 
 */
public abstract class AssTemplateDaoHibernate extends BaseDaoHibernate implements
		IAssTemplateDao, ID2NameDAO {

	public AssTemplate getTemplate(String id) {
		AssTemplate template = (AssTemplate) getHibernateTemplate().get(
				AssTemplate.class, id);
		if (null == template) {
			// throw new ObjectRetriasslFailureException(AssTemplate.class, id);
			template = new AssTemplate();
		}
		return template;
	}

	public void removeTemplate(AssTemplate template) {
		getHibernateTemplate().delete(template);
	}

	public void saveTemplate(AssTemplate template) {
		if (null == template.getId() || "".equals(template.getId())) {
			getHibernateTemplate().save(template);
		} else {
			getHibernateTemplate().merge(template);
		}
	}

	public String id2Name(String id) {
		String templateName = "";
		AssTemplate template = getTemplate(id);
		if (null != template.getId() && !"".equals(template.getId())) {
			if (null != template.getTemplateName()
					&& !"".equals(template.getTemplateName())) {
				templateName = template.getTemplateName();
			} else {
				templateName = AssConstants.NODE_NONAME;
			}
		} else {
			templateName = AssConstants.NODE_NONAME;
		}
		return templateName;
	}
	
	//根据模版中存储的belongNode，找到属于同一节点的所有模版2009-8-7
	public List getTemplateByblnode(String node){
		String hql = "from AssTemplate tem where tem.belongNode = '"+node+"' and deleted = '0' ";
		List list = getHibernateTemplate().find(hql);
		return list;
	}
	
}
