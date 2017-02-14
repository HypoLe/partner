package com.boco.eoms.deviceManagement.qualify.service;

import java.io.InputStream;
import java.util.List;


import org.jfree.chart.JFreeChart;

import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.deviceManagement.qualify.model.TaskOrder;

public interface TaskOrderService {
	
	public boolean newAdd(TaskOrder taskOrder);
	
	public boolean send(TaskOrder taskOrder);
	
	public boolean end(TaskOrder taskOrder);
	
	public boolean reply(TaskOrder taskOrder);
	
	public List<TaskOrder> search(TawSystemSessionForm form, String topic, String status, String type, int start, int max);
	
	public List<TaskOrder> listHistory(int start, int max);
	
	//public List<TaskOrder> list(String status, int start, int max);
	
	public List<TaskOrder> listAll(TawSystemSessionForm form, int start, int max);
	
	public List<TaskOrder> listDraft(TawSystemSessionForm form, int start, int max);
	
	public String importRecord(InputStream is, String fileName, String createUserid, String createUserRole, String createUserContact, String createDeptId) throws Exception;
	
	public int count(TawSystemSessionForm form, String topic, String status, String type);
	
	public int countAll(TawSystemSessionForm form);
	
	public int countHistory();
	
	public int countDraft(TawSystemSessionForm form);
	
	public boolean delete(String id);
	
	public TaskOrder findById(String id);
	
	public JFreeChart createChart(String chartType);
	
	public List<TaskOrder> searchHis(String topic, String type, int start, int max);
	
	public int countHis(String topic, String type);

}
