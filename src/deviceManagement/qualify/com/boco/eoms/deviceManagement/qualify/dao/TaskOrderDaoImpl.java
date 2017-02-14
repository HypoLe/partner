package com.boco.eoms.deviceManagement.qualify.dao;


import java.math.BigInteger;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.deviceManagement.qualify.model.TaskOrder;
import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;
import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;


public class TaskOrderDaoImpl extends GenericDAOImpl<TaskOrder, String> implements
		TaskOrderDao {

	public List<TaskOrder> findByDeadline(String deadline, int start, int max) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<TaskOrder> findByStatus(String status, int start, int max) {
		Search search = new Search();
		search.setFirstResult(start);
		search.setMaxResults(max);
		search.addFilterEqual("status", status);
		return search(search);
	}	
 /*
	public List<TaskOrder> findAll(TawSystemSessionForm form, int start, int max) {
		Search search = new Search();
		search.setFirstResult(start);
		search.setMaxResults(max);
		search.addFilterNotEqual("status", "已归档");
		System.out.println();
		List<String> deptidList = new ArrayList<String>();
		String deptId = form.getDeptid();
		for(int i=0; i<deptId.length(); i++) {
			deptidList.add(deptId.substring(0,i));
		}
		for(int i=1; i<=deptId.length(); i++) {
			System.out.println("所获得的部门ID:"+deptId.substring(0,i));
			deptidList.add(deptId.substring(0,i));
		}
		search.addFilterOr(Filter.equal("nextOperId", form.getUserid()),Filter.and(Filter.in("nextOperId", deptidList)));
		//search.addFilterEqual("nextOperId", form.getUserid()).addFilterILike("nextOperId", form.getDeptid()+"%");
		search.addSort("createTime", true);
		return search(search);
	}	
*/	
	
	public List<TaskOrder> findAll(TawSystemSessionForm form, int start, int max) {
		Search search = new Search();
		search.setFirstResult(start);
		search.setMaxResults(max);
		search.addFilterNotEqual("status", "已归档");
		//search.addFilterOr(Filter.equal("nextOperId", form.getUserid()),Filter.and(Filter.equal("nextOperId", form.getDeptid())));
		//search.addFilterEqual("nextOperId", form.getUserid()).addFilterILike("nextOperId", form.getDeptid()+"%");
		search.addFilterOr(Filter.in("nextOperId", form.getUserid(),form.getDeptid()),Filter.and(Filter.in("sendTo2", form.getUserid(),form.getDeptid())));
		search.addSort("createTime", true);
		return search(search);
	}	

	public TaskOrder findById(String id) {
		Search search = new Search();
		search.addFilterEqual("id", id);
		return searchUnique(search);
	}

	public int count(TawSystemSessionForm form, String topic, String status, String type) {	
		Search search = new Search();
		search.addFilterNotIn("status", "已归档","未派发");
		//search.addFilterOr(Filter.equal("nextOperId", form.getUserid()),Filter.and(Filter.equal("nextOperId", form.getDeptid())));
		search.addFilterOr(Filter.in("nextOperId", form.getUserid(),form.getDeptid()),Filter.and(Filter.in("sendTo2", form.getUserid(),form.getDeptid())));
		if("未派发".equals(status)||"已派发".equals(status)||"已回复".equals(status))
			search.addFilterEqual("status", status);
		if("101010201".equals(type)||"101010202".equals(type))
			search.addFilterEqual("type", type);
		if(topic !=null && !"".equals(topic.trim()))
			search.addFilterLike("topic", "%"+topic+"%");
		return count(search);
	}	
	
	public int countAll(TawSystemSessionForm form) {
		Search search = new Search();
		//search.addFilterOr(Filter.equal("nextOperId", form.getUserid()),Filter.and(Filter.equal("nextOperId", form.getDeptid())));
		search.addFilterOr(Filter.in("nextOperId", form.getUserid(),form.getDeptid()),Filter.and(Filter.in("sendTo2", form.getUserid(),form.getDeptid())));
		search.addFilterNotEqual("status", "已归档");
		return count(search);
	}	
	
	public String getSequence(String sequence, String seqTable) {
		Session session = getSession();
		SQLQuery query = session.createSQLQuery("select "+sequence+".nextval "+"from "+seqTable);
		BigInteger bigInteger = (BigInteger) query.uniqueResult();
		//query.addEntity(Integer.class);
		return bigInteger.toString();

	}

}
