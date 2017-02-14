package com.boco.eoms.deviceManagement.qualify.dao;


import java.util.List;

import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.deviceManagement.qualify.model.TaskOrder;
import com.googlecode.genericdao.dao.hibernate.GenericDAO;

public interface TaskOrderDao extends GenericDAO<TaskOrder, String>{

	
	public List<TaskOrder> findByStatus(String status, int start, int max);
	
	public List<TaskOrder> findByDeadline(String deadline, int start, int max);
	
	
	public TaskOrder findById(String id);
	
	public List<TaskOrder> findAll(TawSystemSessionForm form, int start, int max);
	
	public int count(TawSystemSessionForm form, String topic,String status,String type);
	
	public int countAll(TawSystemSessionForm form);
	
	public String getSequence(String sequence, String seqTable);
	
	//public boolean save(String topic, String type, String netGroup, String deadline, String sendTo, String sendTo2, String description, String remarks1);
}
