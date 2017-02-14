package com.boco.eoms.partner.resourceInfo.dao.hibernate;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.base.model.PageModel;
import com.boco.eoms.deviceManagement.common.dao.CommonGenericDaoImpl;
import com.boco.eoms.partner.resourceInfo.dao.IApparatusDao;
import com.boco.eoms.partner.resourceInfo.model.Apparatus;

public class ApparatusDaoHibernate extends CommonGenericDaoImpl<Apparatus, String> implements IApparatusDao {


}
