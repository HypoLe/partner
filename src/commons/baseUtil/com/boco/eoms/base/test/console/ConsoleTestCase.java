package com.boco.eoms.base.test.console;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.test.AbstractTransactionalSpringContextTests;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Mar 26, 2007 5:29:15 PM
 * </p>
 * 
 * @author 曲静波
 * @version 1.0
 * 
 */
public class ConsoleTestCase extends AbstractTransactionalSpringContextTests {

	protected Logger logger = Logger.getLogger(this.getClass());

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.test.AbstractDependencyInjectionSpringContextTests#getConfigLocations()
	 */
	protected String[] getConfigLocations() {
		return new String[] { "config/applicationContext-all.xml" };
	}

	protected Object getBean(String beanId) {
		return applicationContext.getBean(beanId);
	}

	/**
	 * hibernate 提交
	 */
	protected void flush() {
		// 获取hibernate sessionFactory
		HibernateTemplate hibernateTemplate = new HibernateTemplate(
				(SessionFactory) applicationContext.getBean("sessionFactory"));
		// 强制提交
		hibernateTemplate.flush();
		hibernateTemplate.clear();
	}
}
