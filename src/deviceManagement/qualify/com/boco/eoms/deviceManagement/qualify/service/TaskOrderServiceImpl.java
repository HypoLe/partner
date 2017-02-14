package com.boco.eoms.deviceManagement.qualify.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import com.boco.eoms.commons.system.dict.dao.ITawSystemDictTypeDao;
import com.boco.eoms.commons.system.dict.model.TawSystemDictType;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.deviceManagement.common.utils.CommonUtils;
import com.boco.eoms.deviceManagement.qualify.dao.TaskOrderDao;
import com.boco.eoms.deviceManagement.qualify.model.TaskOrder;
import com.boco.eoms.deviceManagement.qualify.util.NumberUtil;
import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;


public class TaskOrderServiceImpl implements TaskOrderService {
	
	private TaskOrderDao taskOrderDao;
	private ITawSystemDictTypeDao systemDictTypeDao;

	public void setTaskOrderDao(TaskOrderDao taskOrderDao) {
		this.taskOrderDao = taskOrderDao;
	}

	public void setSystemDictTypeDao(ITawSystemDictTypeDao systemDictTypeDao) {
		this.systemDictTypeDao = systemDictTypeDao;
	}



	public boolean newAdd(TaskOrder taskOrder) {
		taskOrder.setNumber(NumberUtil.generateNumber("HL", "001", new Date(), "seq_taskorder", "t_taskorder_dual"));
		return taskOrderDao.save(taskOrder);
	}
	
	public boolean reply(TaskOrder taskOrder) {
		return taskOrderDao.save(taskOrder);
	}

//	public List<TaskOrder> list(String status, int start, int max) {
//		if(status==null||"".equals(status)){
//			return taskOrderDao.findByStatus(status, start, max);
//		}
//		return taskOrderDao.findAll(start, max);
//	}
	
	public List<TaskOrder> listAll(TawSystemSessionForm form, int start, int max){
		return taskOrderDao.findAll(form, start, max);
	}
	
	public boolean send(TaskOrder taskOrder) {
		if("".equals(taskOrder.getNumber())||taskOrder.getNumber()==null) {
			taskOrder.setNumber(NumberUtil.generateNumber("HL", "001", new Date(), "seq_taskorder", "t_taskorder_dual"));
		}
		return taskOrderDao.save(taskOrder);
	}

	public int count(TawSystemSessionForm form, String topic, String status, String type) {
		return taskOrderDao.count(form, topic, status, type);
	}

	public TaskOrder findById(String id) {
		return taskOrderDao.findById(id);
	}

	public int countAll(TawSystemSessionForm form) {
		return taskOrderDao.countAll(form);
	}

	public boolean end(TaskOrder taskOrder) {
		return taskOrderDao.save(taskOrder);
	}

	public List<TaskOrder> search(TawSystemSessionForm form, String topic, String status, String type, int start, int max) {
		Search search = new Search();
		search.setFirstResult(start);
		search.setMaxResults(max);
		search.addFilterNotIn("status", "已归档","未派发");
		//search.addFilterOr(Filter.equal("nextOperId", form.getUserid()),Filter.and(Filter.equal("nextOperId", form.getDeptid())));
		//search.addFilterOr(Filter.in("nextOperId", form.getUserid(),form.getDeptid()),Filter.and(Filter.in("sendTo2", form.getUserid(),form.getDeptid())));
		search.addFilterOr(Filter.in("nextOperId", form.getUserid(),form.getDeptid()),Filter.and(Filter.in("sendTo2", form.getUserid(),form.getDeptid())));
		if("已派发".equals(status)||"已回复".equals(status))
			search.addFilterEqual("status", status);
		if("101010201".equals(type)||"101010202".equals(type))
			search.addFilterEqual("type", type);
		if(topic !=null && !"".equals(topic.trim()))
			search.addFilterLike("topic", "%"+topic+"%");
		return taskOrderDao.search(search);
	}
	
	public List<TaskOrder> searchHis(String topic, String type, int start, int max) {
		Search search = new Search();
		search.setFirstResult(start);
		search.setMaxResults(max);
		search.addFilterEqual("status", "已归档");
		if(topic !=null && !"".equals(topic.trim()))
			search.addFilterLike("topic", "%"+topic+"%");
		if("101010201".equals(type)||"101010202".equals(type))
			search.addFilterEqual("type", type);
		search.addSort("endTime", true);
		return taskOrderDao.search(search);
	}
	
	public int countHis(String topic, String type) {
		Search search = new Search();
		search.addFilterEqual("status", "已归档");
		if(topic !=null && !"".equals(topic.trim()))
			search.addFilterLike("topic", "%"+topic+"%");
		if("101010201".equals(type)||"101010202".equals(type))
			search.addFilterEqual("type", type);
		return taskOrderDao.count(search);
	}
	
	public List<TaskOrder> listHistory(int start, int max){
		Search search = new Search();
		search.setFirstResult(start);
		search.setMaxResults(max);
		search.addFilterEqual("status", "已归档");
		search.addSort("endTime", true);
		return taskOrderDao.search(search);
	}
	
	public int countHistory() {
		Search search = new Search();
		search.addFilterEqual("status", "已归档");
		return taskOrderDao.count(search);
	}	

	public int countDraft(TawSystemSessionForm form) {
		Search search = new Search();
		search.addFilterEqual("status", "未派发");
		search.addFilterEqual("createUserId", form.getUserid());
		return taskOrderDao.count(search);
	}

	public List<TaskOrder> listDraft(TawSystemSessionForm form, int start,
			int max) {
		Search search = new Search();
		search.setFirstResult(start);
		search.setMaxResults(max);
		search.addFilterEqual("status", "未派发");
		search.addFilterEqual("createUserId", form.getUserid());
		return taskOrderDao.search(search);
	}

	public boolean delete(String id) {
		return taskOrderDao.removeById(id);
	}

	public String importRecord(InputStream is, String fileName,
			String createUserId, String createUserRole, String createUserContact, String createDeptId) throws Exception {
		int count = 0;
		if (!fileName.endsWith(".xls")) { // 检查是否为Excel文件
			throw new Exception(":导入模板文件非法");
		}
		HSSFWorkbook wb = null;
		wb = new HSSFWorkbook(new POIFSFileSystem(is));
		HSSFSheet sheet = wb.getSheetAt(0);
		HSSFRow titleRow = sheet.getRow(0);
		if(!"代维任务主题".equals(titleRow.getCell(0).getRichStringCellValue().getString())
				||!"代维任务类型".equals(titleRow.getCell(1).getRichStringCellValue().getString())
				||!"网络分类".equals(titleRow.getCell(2).getRichStringCellValue().getString())
				||!"代维任务描述".equals(titleRow.getCell(3).getRichStringCellValue().getString())
				||!"备注信息".equals(titleRow.getCell(4).getRichStringCellValue().getString())){
			throw new Exception(":导入模板格式错误");
		}
		List<TawSystemDictType> netGroupList = systemDictTypeDao.getDictSonsByDictid("1010104");
		Map<String, String> netGroupMap = new HashMap<String, String>();
		for(TawSystemDictType dictTypeNetGroup : netGroupList){
			//System.out.println("---dictTypeNetGroup.getDictName()"+dictTypeNetGroup.getDictName());
			//System.out.println("---dictTypeNetGroup.getDictId()"+dictTypeNetGroup.getDictId());
			netGroupMap.put(dictTypeNetGroup.getDictName(), dictTypeNetGroup.getDictId());
		}
		List<TawSystemDictType> typeList = systemDictTypeDao.getDictSonsByDictid("1010102");
		Map<String, String> typeMap = new HashMap<String, String>();
		for(TawSystemDictType dictTypeNetGroup : typeList){
			//System.out.println("---dictTypeNetGroup.getDictName()"+dictTypeNetGroup.getDictName());
			//System.out.println("---dictTypeNetGroup.getDictId()"+dictTypeNetGroup.getDictId());
			typeMap.put(dictTypeNetGroup.getDictName(), dictTypeNetGroup.getDictId());
		}
		List<TaskOrder> list = new ArrayList<TaskOrder>();
		for (int i = 1; i <= sheet.getLastRowNum(); i++) {
			TaskOrder taskOrder = new TaskOrder();
			HSSFRow row = sheet.getRow(i);
			//System.out.println("dsfafdsaf"+ sheet.getLastRowNum());
			if(row.getCell(0) == null || "".equals(row.getCell(0).getRichStringCellValue().getString())) break;
			taskOrder.setTopic(row.getCell(0).getRichStringCellValue().getString());
			System.out.println(typeMap.get(row.getCell(1).getRichStringCellValue().getString()));
			taskOrder.setType(typeMap.get(row.getCell(1).getRichStringCellValue().getString()));
			taskOrder.setNetGroup(netGroupMap.get(row.getCell(2).getRichStringCellValue().getString()));
			taskOrder.setDescription(row.getCell(3).getRichStringCellValue().getString());
			if(row.getCell(4)==null) {
				taskOrder.setRemarks1("");
			} else {
				taskOrder.setRemarks1(row.getCell(4).getRichStringCellValue().getString());
			}			
			taskOrder.setStatus("未派发");
			taskOrder.setCreateUserId(createUserId);
			taskOrder.setCreateUserRole(createUserRole);
			taskOrder.setCreateUserContact(createUserContact);
			taskOrder.setCreateDeptId(createDeptId);
			taskOrder.setCreateTime(CommonUtils.toEomsStandardDate(new Date()));
			taskOrder.setNumber(NumberUtil.generateNumber("HL", "001", new Date(), "seq_taskorder", "t_taskorder_dual"));
			taskOrder.setNextOperId(createUserId);
			list.add(taskOrder);
		}
		for(TaskOrder taskOrder : list) {
			taskOrderDao.save(taskOrder);
			count++;
		}
		return "共导入"+count+"条";
	}

	public JFreeChart createChart(String chartType) {
		JFreeChart chart = null;
		if("type".equals(chartType)) {
			chart = createChartType();
		}
		if("status".equals(chartType)) {
			chart = createChartStatus();
		}
		if("netGroup".equals(chartType)) {
			chart = createChartNetGroup();
		}
		if("efficiency".equals(chartType)) {
			chart = createChartEfficiency();
		}
		return chart;
	}
	
	public JFreeChart createChartType(){
		DefaultPieDataset dataset = new DefaultPieDataset(); 
		Search search1 = new Search();
		search1.addFilterEqual("type", "紧急");
		dataset.setValue("紧急", taskOrderDao.count(search1));
		Search search2 = new Search();
		search1.addFilterEqual("type", "一般");
		dataset.setValue("一般", taskOrderDao.count(search2));
		return ChartFactory.createPieChart("任务工单类型统计", dataset, true, false, false);
	}
	
	
	public JFreeChart createChartStatus() {
		DefaultPieDataset dataset = new DefaultPieDataset(); 
		Search search1 = new Search();
		search1.addFilterEqual("status", "未派发");
		dataset.setValue("未派发", taskOrderDao.count(search1));
		Search search2 = new Search();
		search2.addFilterEqual("status", "已派发");
		dataset.setValue("已派发", taskOrderDao.count(search2));
		Search search3 = new Search();
		search3.addFilterEqual("status", "已回复");
		dataset.setValue("已回复", taskOrderDao.count(search3));
		Search search4 = new Search();
		search4.addFilterEqual("status", "已归档");
		dataset.setValue("已归档", taskOrderDao.count(search4));
		JFreeChart chart = ChartFactory.createPieChart("代维任务工单状态统计", dataset, true, true, false);
		PiePlot piePlot = (PiePlot) chart.getPlot();
		piePlot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0}：{1}\n百分比:{2}"));			
		piePlot.setNoDataMessage("无有效数据");
		return chart;
	}
	
	/*
	public JFreeChart createChartNetGroup() {
		DefaultPieDataset dataset = new DefaultPieDataset(); 
		Search search1 = new Search();
		search1.addFilterEqual("netGroup", "移动");
		dataset.setValue("移动", taskOrderDao.count(search1));
		Search search2 = new Search();
		search1.addFilterEqual("netGroup", "固网");
		dataset.setValue("固网", taskOrderDao.count(search2));
		return ChartFactory.createPieChart("任务网络类型统计", dataset, true, false, false);
	}
	*/
	public JFreeChart createChartNetGroup() {
		DefaultCategoryDataset dataset=new DefaultCategoryDataset(); 
		List<?> netGroups = systemDictTypeDao.getDictSonsByDictid("1010104");
		for(Object netGroup : netGroups){
			TawSystemDictType dictType = (TawSystemDictType) netGroup;
			Search search = new Search();
			search.addFilterEqual("netGroup", dictType.getDictId());
			dataset.setValue(taskOrderDao.count(search), "", dictType.getDictName());
		}
		JFreeChart chart = ChartFactory.createBarChart("代维任务工单网络类型统计", "网络类型", "工单数量", dataset, PlotOrientation.VERTICAL, false, false, false);
		CategoryPlot plot=(CategoryPlot)chart.getPlot();
		CategoryAxis categoryAxis=plot.getDomainAxis();
		categoryAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
		NumberAxis numberAxis=(NumberAxis)plot.getRangeAxis();
		numberAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		return chart;
	}
	
	public JFreeChart createChartEfficiency() {
		DefaultPieDataset dataset = new DefaultPieDataset(); 
		Search search1 = new Search();
		search1.addFilterEqual("delay", "0");
		dataset.setValue("及时归档", taskOrderDao.count(search1));
		Search search2 = new Search();
		search2.addFilterEqual("delay", "1");
		dataset.setValue("延时归档", taskOrderDao.count(search2));
		return ChartFactory.createPieChart("任务工单归档效率统计", dataset, true, false, false);
	}

}
