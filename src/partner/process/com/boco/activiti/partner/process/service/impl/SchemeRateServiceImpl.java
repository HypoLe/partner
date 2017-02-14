package com.boco.activiti.partner.process.service.impl;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;

import com.boco.activiti.partner.process.dao.ISchemeRateDao;
import com.boco.activiti.partner.process.dao.ISchemeRateJDBCDao;
import com.boco.activiti.partner.process.model.SchemeRate;
import com.boco.activiti.partner.process.model.SchemeRateRejectModel;
import com.boco.activiti.partner.process.po.ChildSceneReportsModel;
import com.boco.activiti.partner.process.service.ISchemeRateService;
import com.boco.eoms.deviceManagement.common.pojo.CommonSearch;
import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.partner.process.util.CommonUtils;
import com.googlecode.genericdao.search.ExampleOptions;
import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.ISearch;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

public class SchemeRateServiceImpl extends CommonGenericServiceImpl<SchemeRate> implements ISchemeRateService{
	private ISchemeRateDao schemeRateDao;
	
	private ISchemeRateJDBCDao schemeRateJDBCDao;
	
	public ISchemeRateJDBCDao getSchemeRateJDBCDao() {
		return schemeRateJDBCDao;
	}
	public void setSchemeRateJDBCDao(ISchemeRateJDBCDao schemeRateJDBCDao) {
		this.schemeRateJDBCDao = schemeRateJDBCDao;
	}
	/**
	 * 方案合格率统计list查询
	  * @author zhoukeqing 
	  * @title: schemeRateList
	  * @date Jul 26, 2016 3:39:51 PM
	  * @param userid
	  * @param flag
	  * @param county   time
	  * @return List<SchemeRate>
	 */
	@Override
	public Map<String,Object> schemeRateList(String county,String startTime,String endTime) {
		Map<String,Object> map=schemeRateDao.schemeRateList(county, startTime,endTime);
		return map;
	}
	/**
	 * 方案合格率统计 驳回原因列表钻取list
	  * @author zhoukeqing 
	  * @title: schemeRateRejectList
	  * @date Jul 26, 2016 3:39:51 PM
	  * @param userid
	  * @param flag
	  * @param county   time
	  * @return List<SchemeRateRejectModel>
	 */
	@Override
	public List<SchemeRateRejectModel> schemeRateRejectList(String city,String startTime,String endTime,String themeinterface,int pageSize,int firstResult,int endResult) {
		List<SchemeRateRejectModel> schemeRateRejectList=schemeRateDao.schemeRateRejectList(city, startTime,endTime,themeinterface,pageSize,firstResult,endResult);
		return schemeRateRejectList;
	}
	/**
	 * 方案合格率统计 驳回原因列表钻取total
	  * @author zhoukeqing 
	  * @title: schemeRateRejectTotal
	  * @date Jul 26, 2016 3:39:51 PM
	  * @param userid
	  * @param flag
	  * @param county   time themeinterface
	  * @return int
	 */
	@Override
	public int schemeRateRejectTotal(String city, String startTime,String endTime,
			String themeinterface) {
		int total=schemeRateDao.schemeRateRejectTotal(city, startTime,endTime,themeinterface);
		return total;
	}

	@Override
	public int count(ISearch search) {
		return 0;
	}

	@Override
	public List excelExportToProcess(Search search, String userId,
			String deptId, String queryFlag, String processKey, String flag,
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SchemeRate find(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SchemeRate[] find(String... ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SchemeRate> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map getDataList(Class<SchemeRate> entryClass, String alias,
			String entrySql, String countSql, int pageIndex, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Filter getFilterFromExample(SchemeRate example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Filter getFilterFromExample(SchemeRate example,
			ExampleOptions options) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SchemeRate getReference(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SchemeRate[] getReferences(String... ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAttached(SchemeRate entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void refresh(SchemeRate... entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean remove(SchemeRate entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void remove(SchemeRate... entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean removeById(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeByIds(String... ids) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean save(SchemeRate entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean[] save(SchemeRate... entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SchemeRate> search(ISearch search) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SearchResult<SchemeRate> searchAndCount(ISearch search) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <RT> SearchResult<RT> searchAndCount(CommonSearch commonSearch) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Search searchPrivFilter(Search search, String userId, String deptId,
			HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SchemeRate searchUnique(ISearch search) {
		// TODO Auto-generated method stub
		return null;
	}

	public ISchemeRateDao getSchemeRateDao() {
		return schemeRateDao;
	}

	public void setSchemeRateDao(ISchemeRateDao schemeRateDao) {
		this.schemeRateDao = schemeRateDao;
	}
	
	/**
	 *   子场景工单统计
	 	 * @author WANGJUN
	 	 * @title: childSceneReports
	 	 * @date Jul 21, 2016 9:43:00 AM
	 	 * @param sheetAcceptLimit
	 	 * @param sheetCompleteLimit
	 	 * @param cityId
	 	 * @return List<TaskModel>
	 */
	public List<ChildSceneReportsModel> childSceneReports(String sheetAcceptLimit,String sheetCompleteLimit,String cityId,String flag){
		 List<ChildSceneReportsModel> list = schemeRateJDBCDao.childSceneReports(sheetAcceptLimit,sheetCompleteLimit,cityId,flag);
		 //市分公司预检预修项目总数（项）
		 double sumTotalNum = 0;
		 //市分公司预检预修项目总金额（元）
		 double sumTotalProjectAmount = 0D;
		 //工费总额
		 double sumTotalWorkAmout = 0D;
		 //材料费总额
		 double sumTotalDataAmout = 0D;
		 
		 //敷设光缆（不含接续）
		 	//数量（千米）
		 	double sumA1 = 0D;
		 	//工费
		 	double sumA2 = 0D;
		 	//材料费
		 	double sumA3 = 0D;
		 
		 //光缆接头接续
		 	//数量（头）
		 	double sumB1 = 0D;
		 	//工费
		 	double sumB2 = 0D;
		 	//材料费
		 	double sumB3 = 0D;
		 
		 //光缆接头接续
		 	//数量（芯）
		 	double sumC1 = 0D;
		 	//工费
		 	double sumC2 = 0D;
		 	//材料费
		 	double sumC3 = 0D;
		 	
		 //拆除光缆
		 	//数量（千米）
		 	double sumD1 = 0D;
		 	//工费
		 	double sumD2 = 0D;
		 	//材料费
		 	double sumD3 = 0D;
		 	
		 	double sumE1=0D;
		 	double sumE2=0D;
		 	double sumE3=0D;
		 	double sumF1=0D;
		 	double sumF2=0D;
		 	double sumF3=0D;
		 	double sumG1=0D;
		 	double sumG2=0D;
		 	double sumG3=0D;
		 	double sumH1=0D;
		 	double sumH2=0D;
		 	double sumH3=0D;
		 	double sumI1=0D;
		 	double sumI2=0D;
		 	double sumI3=0D;
		 	double sumJ1=0D;
		 	double sumJ2=0D;
		 	double sumJ3=0D;
		 	double sumJ4=0D;
		 	double sumK1=0D;
		 	double sumK2=0D;
		 	double sumK3=0D;
		 	double sumL1=0D;
		 	double sumL2=0D;
		 	double sumL3=0D;
		 	double sumM1=0D;
		 	double sumM2=0D;
		 	double sumM3=0D;
		 	double sumN1=0D;
		 	double sumN2=0D;
		 	double sumN3=0D;
		 	double sumO1=0D;
		 	double sumO2=0D;
		 	double sumO3=0D;
		 	double sumP1=0D;
		 	double sumP2=0D;
		 	double sumP3=0D;
		 	double sumQ1=0D;
		 	double sumQ2=0D;
		 	double sumQ3=0D;
		 	double sumR1=0D;
		 	double sumR2=0D;
		 	double sumR3=0D;
		 	double sumS1=0D;
		 	double sumS2=0D;
		 	double sumS3=0D;
		 	double sumT1=0D;
		 	double sumT2=0D;
		 	double sumT3=0D;
		 	double sumU1=0D;
		 	double sumU2=0D;
		 	double sumU3=0D;
		 	double sumV1=0D;
		 	double sumV2=0D;
		 	double sumV3=0D;
		 	double sumW1=0D;
		 	double sumW2=0D;
		 	double sumW3=0D;
		 	double sumX1=0D;
		 	double sumX2=0D;
		 	double sumX3=0D;
		 	double sumY1=0D;
		 	double sumY2=0D;
		 	double sumY3=0D;
		 	double sumZ1=0D;
		 	double sumZ2=0D;
		 	double sumZ3=0D;
		 	double sumAA1=0D;
		 	double sumAA2=0D;
		 	double sumAA3=0D;
		 	double sumBB1=0D;
		 	double sumBB2=0D;
		 	double sumBB3=0D;
		 	double sumCC1=0D;
		 	double sumCC2=0D;
		 	double sumCC3=0D;
		 	double sumDD1=0D;
		 	double sumDD2=0D;
		 	double sumDD3=0D;
		 	double sumEE1=0D;
		 	double sumEE2=0D;
		 	double sumEE3=0D;
		 	double sumFF1=0D;
		 	double sumFF2=0D;
		 	double sumFF3=0D;
		 	double sumGG1=0D;
		 	double sumGG2=0D;
		 	double sumGG3=0D;
		 	double sumHH1=0D;
		 	double sumHH2=0D;
		 	double sumHH3=0D;
		 	double sumII1=0D;
		 	double sumII2=0D;
		 	double sumII3=0D;
		 	double sumJJ1=0D;
		 	double sumJJ2=0D;
		 	double sumJJ3=0D;
		 	double sumKK1=0D;
		 	double sumKK2=0D;
		 	double sumKK3=0D;
		 	
		 for(int i = 0 ;i < list.size();i++){
			 ChildSceneReportsModel model = list.get(i);
			 sumTotalNum+= Double.parseDouble(model.getTotalNum());
			 sumTotalProjectAmount+=Double.parseDouble(model.getTotalProjectAmount());
			 sumTotalWorkAmout+=Double.parseDouble(model.getTotalWorkAmout());
			 sumTotalDataAmout+=Double.parseDouble(model.getTotalDataAmout());
			 
			 model.setWorkRatio(model.getWorkRatio()+"%");
			 model.setDataRatio(model.getDataRatio()+"%");
			 
			 sumA1+=Double.parseDouble(model.getA1());
			 sumA2+=Double.parseDouble(model.getA2());
			 sumA3+=Double.parseDouble(model.getA3());
			 
			 sumB1+=Double.parseDouble(model.getB1());
			 sumB2+=Double.parseDouble(model.getB2());
			 sumB3+=Double.parseDouble(model.getB3());
			 
			 sumC1+=Double.parseDouble(model.getC1());
			 sumC2+=Double.parseDouble(model.getC2());
			 sumC3+=Double.parseDouble(model.getC3());
			 
			 sumD1+=Double.parseDouble(model.getD1());
			 sumD2+=Double.parseDouble(model.getD2());
			 sumD3+=Double.parseDouble(model.getD3());
			 
			 sumE1+=Double.parseDouble(model.getE1());
			 sumE2+=Double.parseDouble(model.getE2());
			 sumE3+=Double.parseDouble(model.getE3());
			 sumF1+=Double.parseDouble(model.getF1());
			 sumF2+=Double.parseDouble(model.getF2());
			 sumF3+=Double.parseDouble(model.getF3());
			 sumG1+=Double.parseDouble(model.getG1());
			 sumG2+=Double.parseDouble(model.getG2());
			 sumG3+=Double.parseDouble(model.getG3());
			 sumH1+=Double.parseDouble(model.getH1());
			 sumH2+=Double.parseDouble(model.getH2());
			 sumH3+=Double.parseDouble(model.getH3());
			 sumI1+=Double.parseDouble(model.getI1());
			 sumI2+=Double.parseDouble(model.getI2());
			 sumI3+=Double.parseDouble(model.getI3());
			 sumJ1+=Double.parseDouble(model.getJ1());
			 sumJ2+=Double.parseDouble(model.getJ2());
			 sumJ3+=Double.parseDouble(model.getJ3());
			 sumJ4+=Double.parseDouble(model.getJ4());
			 sumK1+=Double.parseDouble(model.getK1());
			 sumK2+=Double.parseDouble(model.getK2());
			 sumK3+=Double.parseDouble(model.getK3());
			 sumL1+=Double.parseDouble(model.getL1());
			 sumL2+=Double.parseDouble(model.getL2());
			 sumL3+=Double.parseDouble(model.getL3());
			 sumM1+=Double.parseDouble(model.getM1());
			 sumM2+=Double.parseDouble(model.getM2());
			 sumM3+=Double.parseDouble(model.getM3());
			 sumN1+=Double.parseDouble(model.getN1());
			 sumN2+=Double.parseDouble(model.getN2());
			 sumN3+=Double.parseDouble(model.getN3());
			 sumO1+=Double.parseDouble(model.getO1());
			 sumO2+=Double.parseDouble(model.getO2());
			 sumO3+=Double.parseDouble(model.getO3());
			 sumP1+=Double.parseDouble(model.getP1());
			 sumP2+=Double.parseDouble(model.getP2());
			 sumP3+=Double.parseDouble(model.getP3());
			 sumQ1+=Double.parseDouble(model.getQ1());
			 sumQ2+=Double.parseDouble(model.getQ2());
			 sumQ3+=Double.parseDouble(model.getQ3());
			 sumR1+=Double.parseDouble(model.getR1());
			 sumR2+=Double.parseDouble(model.getR2());
			 sumR3+=Double.parseDouble(model.getR3());
			 sumS1+=Double.parseDouble(model.getS1());
			 sumS2+=Double.parseDouble(model.getS2());
			 sumS3+=Double.parseDouble(model.getS3());
			 sumT1+=Double.parseDouble(model.getT1());
			 sumT2+=Double.parseDouble(model.getT2());
			 sumT3+=Double.parseDouble(model.getT3());
			 sumU1+=Double.parseDouble(model.getU1());
			 sumU2+=Double.parseDouble(model.getU2());
			 sumU3+=Double.parseDouble(model.getU3());
			 sumV1+=Double.parseDouble(model.getV1());
			 sumV2+=Double.parseDouble(model.getV2());
			 sumV3+=Double.parseDouble(model.getV3());
			 sumW1+=Double.parseDouble(model.getW1());
			 sumW2+=Double.parseDouble(model.getW2());
			 sumW3+=Double.parseDouble(model.getW3());
			 sumX1+=Double.parseDouble(model.getX1());
			 sumX2+=Double.parseDouble(model.getX2());
			 sumX3+=Double.parseDouble(model.getX3());
			 sumY1+=Double.parseDouble(model.getY1());
			 sumY2+=Double.parseDouble(model.getY2());
			 sumY3+=Double.parseDouble(model.getY3());
			 sumZ1+=Double.parseDouble(model.getZ1());
			 sumZ2+=Double.parseDouble(model.getZ2());
			 sumZ3+=Double.parseDouble(model.getZ3());
			 sumAA1+=Double.parseDouble(model.getAA1());
			 sumAA2+=Double.parseDouble(model.getAA2());
			 sumAA3+=Double.parseDouble(model.getAA3());
			 sumBB1+=Double.parseDouble(model.getBB1());
			 sumBB2+=Double.parseDouble(model.getBB2());
			 sumBB3+=Double.parseDouble(model.getBB3());
			 sumCC1+=Double.parseDouble(model.getCC1());
			 sumCC2+=Double.parseDouble(model.getCC2());
			 sumCC3+=Double.parseDouble(model.getCC3());
			 sumDD1+=Double.parseDouble(model.getDD1());
			 sumDD2+=Double.parseDouble(model.getDD2());
			 sumDD3+=Double.parseDouble(model.getDD3());
			 sumEE1+=Double.parseDouble(model.getEE1());
			 sumEE2+=Double.parseDouble(model.getEE2());
			 sumEE3+=Double.parseDouble(model.getEE3());
			 sumFF1+=Double.parseDouble(model.getFF1());
			 sumFF2+=Double.parseDouble(model.getFF2());
			 sumFF3+=Double.parseDouble(model.getFF3());
			 sumGG1+=Double.parseDouble(model.getGG1());
			 sumGG2+=Double.parseDouble(model.getGG2());
			 sumGG3+=Double.parseDouble(model.getGG3());
			 sumHH1+=Double.parseDouble(model.getHH1());
			 sumHH2+=Double.parseDouble(model.getHH2());
			 sumHH3+=Double.parseDouble(model.getHH3());
			 sumII1+=Double.parseDouble(model.getII1());
			 sumII2+=Double.parseDouble(model.getII2());
			 sumII3+=Double.parseDouble(model.getII3());
			 sumJJ1+=Double.parseDouble(model.getJJ1());
			 sumJJ2+=Double.parseDouble(model.getJJ2());
			 sumJJ3+=Double.parseDouble(model.getJJ3());
			 sumKK1+=Double.parseDouble(model.getKK1());
			 sumKK2+=Double.parseDouble(model.getKK2());
			 sumKK3+=Double.parseDouble(model.getKK3());
			 
			 model.setA4(model.getA4()+"%");
			 model.setB6(model.getB6()+"%");
			 model.setC6(model.getC6()+"%");
			 model.setD4(model.getD4()+"%");
			 model.setE4(model.getE4()+"%");
			 model.setF4(model.getF4()+"%");
			 model.setG4(model.getG4()+"%");
			 model.setH4(model.getH4()+"%");
			 model.setI4(model.getI4()+"%");
			 model.setJ5(model.getJ5()+"%");
			 model.setK4(model.getK4()+"%");
			 model.setL4(model.getL4()+"%");
			 model.setM4(model.getM4()+"%");
			 model.setN4(model.getN4()+"%");
			 model.setO4(model.getO4()+"%");
			 model.setP4(model.getP4()+"%");
			 model.setQ4(model.getQ4()+"%");
			 model.setR4(model.getR4()+"%");
			 model.setS4(model.getS4()+"%");
			 model.setT4(model.getT4()+"%");
			 model.setU4(model.getU4()+"%");
			 model.setV4(model.getV4()+"%");
			 model.setW4(model.getW4()+"%");
			 model.setX4(model.getX4()+"%");
			 model.setY4(model.getY4()+"%");
			 model.setZ4(model.getZ4()+"%");
			 model.setAA4(model.getAA4()+"%");
			 model.setBB4(model.getBB4()+"%");
			 model.setCC4(model.getCC4()+"%");
			 model.setDD4(model.getDD4()+"%");
			 model.setEE4(model.getEE4()+"%");
			 model.setFF4(model.getFF4()+"%");
			 model.setGG4(model.getGG4()+"%");
			 model.setHH4(model.getHH4()+"%");
			 model.setII4(model.getII4()+"%");
			 model.setJJ4(model.getJJ4()+"%");
			 model.setKK4(model.getKK4()+"%");
			 model.setMM1(model.getMM1()+"%");

			 
		 }
		 
		 //合计
		 ChildSceneReportsModel sumModel = new ChildSceneReportsModel();
		 sumModel.setAreaId("total");
		 sumModel.setAreaName("合计"); 
		 sumModel.setTotalNum(Double.toString(CommonUtils.reservedDecimalPlaces(sumTotalNum, 2)));
		 sumModel.setTotalProjectAmount(Double.toString(CommonUtils.reservedDecimalPlaces(sumTotalProjectAmount, 2)));
		 sumModel.setTotalWorkAmout(Double.toString(CommonUtils.reservedDecimalPlaces(sumTotalWorkAmout, 2)));
		 sumModel.setTotalDataAmout(Double.toString(CommonUtils.reservedDecimalPlaces(sumTotalDataAmout, 2)));
		 sumModel.setWorkRatio(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces(sumTotalWorkAmout/sumTotalProjectAmount*100, 2)+"%");//工费占比
		 sumModel.setDataRatio(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces(sumTotalDataAmout/sumTotalProjectAmount*100, 2)+"%");//材料费占比
		 
		 sumModel.setA1(Double.toString(CommonUtils.reservedDecimalPlaces(sumA1, 2)));
		 sumModel.setA2(Double.toString(CommonUtils.reservedDecimalPlaces(sumA2, 2)));
		 sumModel.setA3(Double.toString(CommonUtils.reservedDecimalPlaces(sumA3, 2)));
		 sumModel.setA4(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumA2+sumA3)/sumTotalProjectAmount*100, 2)+"%");
		 
		 sumModel.setB1(Double.toString(CommonUtils.reservedDecimalPlaces(sumB1, 2)));
		 sumModel.setB2(Double.toString(CommonUtils.reservedDecimalPlaces(sumB2, 2)));
		 sumModel.setB3(Double.toString(CommonUtils.reservedDecimalPlaces(sumB3, 2)));
		 sumModel.setB4(sumB1==0D?"0%":Double.toString(CommonUtils.reservedDecimalPlaces(sumB2/sumB1, 2)));//单位接头工费(元)
		 sumModel.setB5(sumB1==0D?"0%":Double.toString(CommonUtils.reservedDecimalPlaces(sumB3/sumB1, 2)));//单位接头材料费用（元）
		 sumModel.setB6(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumB2+sumB3)/sumTotalProjectAmount*100, 2)+"%");
		 
		 sumModel.setC1(Double.toString(CommonUtils.reservedDecimalPlaces(sumC1, 2)));
		 sumModel.setC2(Double.toString(CommonUtils.reservedDecimalPlaces(sumC2, 2)));
		 sumModel.setC3(Double.toString(CommonUtils.reservedDecimalPlaces(sumC3, 2)));
		 sumModel.setC4(sumC1==0D?"0%":Double.toString(CommonUtils.reservedDecimalPlaces(sumC2/sumC1, 2)));//单位接头工费(元)
		 sumModel.setC5(sumC1==0D?"0%":Double.toString(CommonUtils.reservedDecimalPlaces(sumC3/sumC1, 2)));//单位接头材料费用（元）
		 sumModel.setC6(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumC2+sumC3)/sumTotalProjectAmount*100, 2)+"%");
		 
		 sumModel.setD1(Double.toString(CommonUtils.reservedDecimalPlaces(sumD1, 2)));
		 sumModel.setD2(Double.toString(CommonUtils.reservedDecimalPlaces(sumD2, 2)));
		 sumModel.setD3(Double.toString(CommonUtils.reservedDecimalPlaces(sumD3, 2)));
		 sumModel.setD4(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumD2+sumD3)/sumTotalProjectAmount*100, 2)+"%");
		 
		 
		 
		 sumModel.setE1(Double.toString(CommonUtils.reservedDecimalPlaces(sumE1,2)));
		 sumModel.setE2(Double.toString(CommonUtils.reservedDecimalPlaces(sumE2,2)));
		 sumModel.setE3(Double.toString(CommonUtils.reservedDecimalPlaces(sumE3,2)));
		 sumModel.setE4(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumE2+sumE3)/sumTotalProjectAmount*100, 2)+"%");
		 
		 sumModel.setF1(Double.toString(CommonUtils.reservedDecimalPlaces(sumF1,2)));
		 sumModel.setF2(Double.toString(CommonUtils.reservedDecimalPlaces(sumF2,2)));
		 sumModel.setF3(Double.toString(CommonUtils.reservedDecimalPlaces(sumF3,2)));
		 sumModel.setF4(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumF2+sumF3)/sumTotalProjectAmount*100, 2)+"%");
		 
		 sumModel.setG1(Double.toString(CommonUtils.reservedDecimalPlaces(sumG1,2)));
		 sumModel.setG2(Double.toString(CommonUtils.reservedDecimalPlaces(sumG2,2)));
		 sumModel.setG3(Double.toString(CommonUtils.reservedDecimalPlaces(sumG3,2)));
		 sumModel.setG4(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumG2+sumG3)/sumTotalProjectAmount*100, 2)+"%");
		 
		 sumModel.setH1(Double.toString(CommonUtils.reservedDecimalPlaces(sumH1,2)));
		 sumModel.setH2(Double.toString(CommonUtils.reservedDecimalPlaces(sumH2,2)));
		 sumModel.setH3(Double.toString(CommonUtils.reservedDecimalPlaces(sumH3,2)));
		 sumModel.setH4(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumH2+sumH3)/sumTotalProjectAmount*100, 2)+"%");
		 
		 sumModel.setI1(Double.toString(CommonUtils.reservedDecimalPlaces(sumI1,2)));
		 sumModel.setI2(Double.toString(CommonUtils.reservedDecimalPlaces(sumI2,2)));
		 sumModel.setI3(Double.toString(CommonUtils.reservedDecimalPlaces(sumI3,2)));
		 sumModel.setI4(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumI2+sumI3)/sumTotalProjectAmount*100, 2)+"%");
		 
		 sumModel.setJ1(Double.toString(CommonUtils.reservedDecimalPlaces(sumJ1,2)));
		 sumModel.setJ2(Double.toString(CommonUtils.reservedDecimalPlaces(sumJ2,2)));
		 sumModel.setJ3(Double.toString(CommonUtils.reservedDecimalPlaces(sumJ3,2)));
		 sumModel.setJ4(Double.toString(CommonUtils.reservedDecimalPlaces(sumJ4,2)));
		 sumModel.setJ5(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumJ2+sumJ3+sumJ4)/sumTotalProjectAmount*100, 2)+"%");
		 
		 
		 sumModel.setK1(Double.toString(CommonUtils.reservedDecimalPlaces(sumK1,2)));
		 sumModel.setK2(Double.toString(CommonUtils.reservedDecimalPlaces(sumK2,2)));
		 sumModel.setK3(Double.toString(CommonUtils.reservedDecimalPlaces(sumK3,2)));
		 sumModel.setK4(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumK2+sumK3)/sumTotalProjectAmount*100, 2)+"%");
		 
		 sumModel.setL1(Double.toString(CommonUtils.reservedDecimalPlaces(sumL1,2)));
		 sumModel.setL2(Double.toString(CommonUtils.reservedDecimalPlaces(sumL2,2)));
		 sumModel.setL3(Double.toString(CommonUtils.reservedDecimalPlaces(sumL3,2)));
		 sumModel.setL4(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumL2+sumK3)/sumTotalProjectAmount*100, 2)+"%");
		 
		 
		 sumModel.setM1(Double.toString(CommonUtils.reservedDecimalPlaces(sumM1,2)));
		 sumModel.setM2(Double.toString(CommonUtils.reservedDecimalPlaces(sumM2,2)));
		 sumModel.setM3(Double.toString(CommonUtils.reservedDecimalPlaces(sumM3,2)));
		 sumModel.setM4(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumM2+sumM3)/sumTotalProjectAmount*100, 2)+"%");
		 
		 sumModel.setN1(Double.toString(CommonUtils.reservedDecimalPlaces(sumN1,2)));
		 sumModel.setN2(Double.toString(CommonUtils.reservedDecimalPlaces(sumN2,2)));
		 sumModel.setN3(Double.toString(CommonUtils.reservedDecimalPlaces(sumN3,2)));
		 sumModel.setN4(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumN2+sumN3)/sumTotalProjectAmount*100, 2)+"%");
		 
		 sumModel.setO1(Double.toString(CommonUtils.reservedDecimalPlaces(sumO1,2)));
		 sumModel.setO2(Double.toString(CommonUtils.reservedDecimalPlaces(sumO2,2)));
		 sumModel.setO3(Double.toString(CommonUtils.reservedDecimalPlaces(sumO3,2)));
		 sumModel.setO4(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumO2+sumO3)/sumTotalProjectAmount*100, 2)+"%");
		 
		 sumModel.setP1(Double.toString(CommonUtils.reservedDecimalPlaces(sumP1,2)));
		 sumModel.setP2(Double.toString(CommonUtils.reservedDecimalPlaces(sumP2,2)));
		 sumModel.setP3(Double.toString(CommonUtils.reservedDecimalPlaces(sumP3,2)));
		 sumModel.setP4(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumP2+sumP3)/sumTotalProjectAmount*100, 2)+"%");
		 
		 sumModel.setQ1(Double.toString(CommonUtils.reservedDecimalPlaces(sumQ1,2)));
		 sumModel.setQ2(Double.toString(CommonUtils.reservedDecimalPlaces(sumQ2,2)));
		 sumModel.setQ3(Double.toString(CommonUtils.reservedDecimalPlaces(sumQ3,2)));
		 sumModel.setQ4(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumQ2+sumQ3)/sumTotalProjectAmount*100, 2)+"%");
		 
		 sumModel.setR1(Double.toString(CommonUtils.reservedDecimalPlaces(sumR1,2)));
		 sumModel.setR2(Double.toString(CommonUtils.reservedDecimalPlaces(sumR2,2)));
		 sumModel.setR3(Double.toString(CommonUtils.reservedDecimalPlaces(sumR3,2)));
		 sumModel.setR4(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumR2+sumR3)/sumTotalProjectAmount*100, 2)+"%");
		 
		 sumModel.setS1(Double.toString(CommonUtils.reservedDecimalPlaces(sumS1,2)));
		 sumModel.setS2(Double.toString(CommonUtils.reservedDecimalPlaces(sumS2,2)));
		 sumModel.setS3(Double.toString(CommonUtils.reservedDecimalPlaces(sumS3,2)));
		 sumModel.setS4(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumS2+sumS3)/sumTotalProjectAmount*100, 2)+"%");
		 
		 sumModel.setT1(Double.toString(CommonUtils.reservedDecimalPlaces(sumT1,2)));
		 sumModel.setT2(Double.toString(CommonUtils.reservedDecimalPlaces(sumT2,2)));
		 sumModel.setT3(Double.toString(CommonUtils.reservedDecimalPlaces(sumT3,2)));
		 sumModel.setT4(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumT2+sumT3)/sumTotalProjectAmount*100, 2)+"%");
		 
		 
		 sumModel.setU1(Double.toString(CommonUtils.reservedDecimalPlaces(sumU1,2)));
		 sumModel.setU2(Double.toString(CommonUtils.reservedDecimalPlaces(sumU2,2)));
		 sumModel.setU3(Double.toString(CommonUtils.reservedDecimalPlaces(sumU3,2)));
		 sumModel.setU4(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumU2+sumU3)/sumTotalProjectAmount*100, 2)+"%");
		 
		 sumModel.setV1(Double.toString(CommonUtils.reservedDecimalPlaces(sumV1,2)));
		 sumModel.setV2(Double.toString(CommonUtils.reservedDecimalPlaces(sumV2,2)));
		 sumModel.setV3(Double.toString(CommonUtils.reservedDecimalPlaces(sumV3,2)));
		 sumModel.setV4(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumV2+sumV3)/sumTotalProjectAmount*100, 2)+"%");
		 
		 sumModel.setW1(Double.toString(CommonUtils.reservedDecimalPlaces(sumW1,2)));
		 sumModel.setW2(Double.toString(CommonUtils.reservedDecimalPlaces(sumW2,2)));
		 sumModel.setW3(Double.toString(CommonUtils.reservedDecimalPlaces(sumW3,2)));
		 sumModel.setW4(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumW2+sumW3)/sumTotalProjectAmount*100, 2)+"%");
		 
		 sumModel.setX1(Double.toString(CommonUtils.reservedDecimalPlaces(sumX1,2)));
		 sumModel.setX2(Double.toString(CommonUtils.reservedDecimalPlaces(sumX2,2)));
		 sumModel.setX3(Double.toString(CommonUtils.reservedDecimalPlaces(sumX3,2)));
		 sumModel.setX4(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumX2+sumX3)/sumTotalProjectAmount*100, 2)+"%");
		 
		 sumModel.setY1(Double.toString(CommonUtils.reservedDecimalPlaces(sumY1,2)));
		 sumModel.setY2(Double.toString(CommonUtils.reservedDecimalPlaces(sumY2,2)));
		 sumModel.setY3(Double.toString(CommonUtils.reservedDecimalPlaces(sumY3,2)));
		 sumModel.setY4(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumY2+sumY3)/sumTotalProjectAmount*100, 2)+"%");
		 
		 sumModel.setZ1(Double.toString(CommonUtils.reservedDecimalPlaces(sumZ1,2)));
		 sumModel.setZ2(Double.toString(CommonUtils.reservedDecimalPlaces(sumZ2,2)));
		 sumModel.setZ3(Double.toString(CommonUtils.reservedDecimalPlaces(sumZ3,2)));
		 sumModel.setZ4(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumZ2+sumZ3)/sumTotalProjectAmount*100, 2)+"%");
		 
		 sumModel.setAA1(Double.toString(CommonUtils.reservedDecimalPlaces(sumAA1,2)));
		 sumModel.setAA2(Double.toString(CommonUtils.reservedDecimalPlaces(sumAA2,2)));
		 sumModel.setAA3(Double.toString(CommonUtils.reservedDecimalPlaces(sumAA3,2)));
		 sumModel.setAA4(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumAA2+sumAA3)/sumTotalProjectAmount*100, 2)+"%");
		 
		 sumModel.setBB1(Double.toString(CommonUtils.reservedDecimalPlaces(sumBB1,2)));
		 sumModel.setBB2(Double.toString(CommonUtils.reservedDecimalPlaces(sumBB2,2)));
		 sumModel.setBB3(Double.toString(CommonUtils.reservedDecimalPlaces(sumBB3,2)));
		 sumModel.setBB4(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumBB2+sumBB3)/sumTotalProjectAmount*100, 2)+"%");
		 
		 sumModel.setCC1(Double.toString(CommonUtils.reservedDecimalPlaces(sumCC1,2)));
		 sumModel.setCC2(Double.toString(CommonUtils.reservedDecimalPlaces(sumCC2,2)));
		 sumModel.setCC3(Double.toString(CommonUtils.reservedDecimalPlaces(sumCC3,2)));
		 sumModel.setCC4(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumCC2+sumCC3)/sumTotalProjectAmount*100, 2)+"%");
		 
		 sumModel.setDD1(Double.toString(CommonUtils.reservedDecimalPlaces(sumDD1,2)));
		 sumModel.setDD2(Double.toString(CommonUtils.reservedDecimalPlaces(sumDD2,2)));
		 sumModel.setDD3(Double.toString(CommonUtils.reservedDecimalPlaces(sumDD3,2)));
		 sumModel.setDD4(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumDD2+sumDD3)/sumTotalProjectAmount*100, 2)+"%");
		 
		 sumModel.setEE1(Double.toString(CommonUtils.reservedDecimalPlaces(sumEE1,2)));
		 sumModel.setEE2(Double.toString(CommonUtils.reservedDecimalPlaces(sumEE2,2)));
		 sumModel.setEE3(Double.toString(CommonUtils.reservedDecimalPlaces(sumEE3,2)));
		 sumModel.setEE4(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumEE2+sumEE3)/sumTotalProjectAmount*100, 2)+"%");
		 
		 sumModel.setFF1(Double.toString(CommonUtils.reservedDecimalPlaces(sumFF1,2)));
		 sumModel.setFF2(Double.toString(CommonUtils.reservedDecimalPlaces(sumFF2,2)));
		 sumModel.setFF3(Double.toString(CommonUtils.reservedDecimalPlaces(sumFF3,2)));
		 sumModel.setFF4(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumFF2+sumFF3)/sumTotalProjectAmount*100, 2)+"%");
		 
		 sumModel.setGG1(Double.toString(CommonUtils.reservedDecimalPlaces(sumGG1,2)));
		 sumModel.setGG2(Double.toString(CommonUtils.reservedDecimalPlaces(sumGG2,2)));
		 sumModel.setGG3(Double.toString(CommonUtils.reservedDecimalPlaces(sumGG3,2)));
		 sumModel.setGG4(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumGG2+sumGG3)/sumTotalProjectAmount*100, 2)+"%");
		 
		 sumModel.setHH1(Double.toString(CommonUtils.reservedDecimalPlaces(sumHH1,2)));
		 sumModel.setHH2(Double.toString(CommonUtils.reservedDecimalPlaces(sumHH2,2)));
		 sumModel.setHH3(Double.toString(CommonUtils.reservedDecimalPlaces(sumHH3,2)));
		 sumModel.setHH4(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumHH2+sumHH3)/sumTotalProjectAmount*100, 2)+"%");
		 
		 sumModel.setII1(Double.toString(CommonUtils.reservedDecimalPlaces(sumII1,2)));
		 sumModel.setII2(Double.toString(CommonUtils.reservedDecimalPlaces(sumII2,2)));
		 sumModel.setII3(Double.toString(CommonUtils.reservedDecimalPlaces(sumII3,2)));
		 sumModel.setII4(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumII2+sumII3)/sumTotalProjectAmount*100, 2)+"%");
		 
		 sumModel.setJJ1(Double.toString(CommonUtils.reservedDecimalPlaces(sumJJ1,2)));
		 sumModel.setJJ2(Double.toString(CommonUtils.reservedDecimalPlaces(sumJJ2,2)));
		 sumModel.setJJ3(Double.toString(CommonUtils.reservedDecimalPlaces(sumJJ3,2)));
		 sumModel.setJJ4(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumJJ2+sumJJ3)/sumTotalProjectAmount*100, 2)+"%");
		 
		 sumModel.setKK1(Double.toString(CommonUtils.reservedDecimalPlaces(sumKK1,2)));
		 sumModel.setKK2(Double.toString(CommonUtils.reservedDecimalPlaces(sumKK2,2)));
		 sumModel.setKK3(Double.toString(CommonUtils.reservedDecimalPlaces(sumKK3,2)));
		 sumModel.setKK4(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumKK2+sumKK3)/sumTotalProjectAmount*100, 2)+"%");
		 
		 sumModel.setLL1(Double.toString(CommonUtils.reservedDecimalPlaces(sumHH1+sumII1+sumJJ1+sumKK1,2)));
		 sumModel.setMM1(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumHH2 + sumHH3 + sumII2 + sumII3 + sumJJ2 + sumJJ3 + sumKK2 + sumKK3)/sumTotalProjectAmount*100, 2)+"%");
		 
		 list.add(sumModel);
		
		 //费用占比（工费、材料费/总金额）
		 ChildSceneReportsModel ratioModel = new ChildSceneReportsModel(); 
		 ratioModel.setAreaId("ratio");
		 ratioModel.setAreaName("费用占比（工费、材料费/总金额）");
		 
		 ratioModel.setA2(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumA2)/sumTotalProjectAmount*100, 2)+"%");
		 ratioModel.setA3(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumA3)/sumTotalProjectAmount*100, 2)+"%");
		 
		 ratioModel.setB2(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumB2)/sumTotalProjectAmount*100, 2)+"%");
		 ratioModel.setB3(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumB3)/sumTotalProjectAmount*100, 2)+"%");
		 
		 ratioModel.setC2(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumC2)/sumTotalProjectAmount*100, 2)+"%");
		 ratioModel.setC3(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumC3)/sumTotalProjectAmount*100, 2)+"%");
		 
		 ratioModel.setD2(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumD2)/sumTotalProjectAmount*100, 2)+"%");
		 ratioModel.setD3(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumD3)/sumTotalProjectAmount*100, 2)+"%");
		 
		 ratioModel.setE2(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumE2)/sumTotalProjectAmount*100, 2)+"%");
		 ratioModel.setE3(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumE3)/sumTotalProjectAmount*100, 2)+"%");
		 
		 ratioModel.setF2(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumF2)/sumTotalProjectAmount*100, 2)+"%");
		 ratioModel.setF3(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumF3)/sumTotalProjectAmount*100, 2)+"%");
		 
		 ratioModel.setG2(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumG2)/sumTotalProjectAmount*100, 2)+"%");
		 ratioModel.setG3(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumG3)/sumTotalProjectAmount*100, 2)+"%");

		 ratioModel.setH2(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumH2)/sumTotalProjectAmount*100, 2)+"%");
		 ratioModel.setH3(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumH3)/sumTotalProjectAmount*100, 2)+"%");
		 
		 ratioModel.setI2(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumI2)/sumTotalProjectAmount*100, 2)+"%");
		 ratioModel.setI3(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumI3)/sumTotalProjectAmount*100, 2)+"%");

		 ratioModel.setJ2(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumJ2)/sumTotalProjectAmount*100, 2)+"%");
		 ratioModel.setJ3(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumJ3)/sumTotalProjectAmount*100, 2)+"%");
		 ratioModel.setJ4(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumJ4)/sumTotalProjectAmount*100, 2)+"%");
	
		 ratioModel.setK2(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumK2)/sumTotalProjectAmount*100, 2)+"%");
		 ratioModel.setK3(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumK3)/sumTotalProjectAmount*100, 2)+"%");

		 ratioModel.setL2(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumL2)/sumTotalProjectAmount*100, 2)+"%");
		 ratioModel.setL3(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumL3)/sumTotalProjectAmount*100, 2)+"%");
	
		 ratioModel.setM2(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumM2)/sumTotalProjectAmount*100, 2)+"%");
		 ratioModel.setM3(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumM3)/sumTotalProjectAmount*100, 2)+"%");
	
		 ratioModel.setN2(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumN2)/sumTotalProjectAmount*100, 2)+"%");
		 ratioModel.setN3(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumN3)/sumTotalProjectAmount*100, 2)+"%");
		
		 ratioModel.setO2(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumO2)/sumTotalProjectAmount*100, 2)+"%");
		 ratioModel.setO3(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumO3)/sumTotalProjectAmount*100, 2)+"%");
		 
		 ratioModel.setP2(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumP2)/sumTotalProjectAmount*100, 2)+"%");
		 ratioModel.setP3(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumP3)/sumTotalProjectAmount*100, 2)+"%");

		 ratioModel.setQ2(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumQ2)/sumTotalProjectAmount*100, 2)+"%");
		 ratioModel.setQ3(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumQ3)/sumTotalProjectAmount*100, 2)+"%");
	
		 ratioModel.setR2(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumR2)/sumTotalProjectAmount*100, 2)+"%");
		 ratioModel.setR3(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumR3)/sumTotalProjectAmount*100, 2)+"%");
	
		 ratioModel.setS2(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumS2)/sumTotalProjectAmount*100, 2)+"%");
		 ratioModel.setS3(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumS3)/sumTotalProjectAmount*100, 2)+"%");
	
		 ratioModel.setT2(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumT2)/sumTotalProjectAmount*100, 2)+"%");
		 ratioModel.setT3(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumT3)/sumTotalProjectAmount*100, 2)+"%");
		 
		 ratioModel.setU2(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumU2)/sumTotalProjectAmount*100, 2)+"%");
		 ratioModel.setU3(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumU3)/sumTotalProjectAmount*100, 2)+"%");

		 ratioModel.setV2(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumV2)/sumTotalProjectAmount*100, 2)+"%");
		 ratioModel.setV3(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumV3)/sumTotalProjectAmount*100, 2)+"%");

		 ratioModel.setW2(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumW2)/sumTotalProjectAmount*100, 2)+"%");
		 ratioModel.setW3(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumW3)/sumTotalProjectAmount*100, 2)+"%");
		
		 ratioModel.setX2(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumX2)/sumTotalProjectAmount*100, 2)+"%");
		 ratioModel.setX3(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumX3)/sumTotalProjectAmount*100, 2)+"%");

		 ratioModel.setY2(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumY2)/sumTotalProjectAmount*100, 2)+"%");
		 ratioModel.setY3(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumY3)/sumTotalProjectAmount*100, 2)+"%");
	
		 ratioModel.setZ2(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumZ2)/sumTotalProjectAmount*100, 2)+"%");
		 ratioModel.setZ3(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumZ3)/sumTotalProjectAmount*100, 2)+"%");
	
		 ratioModel.setAA2(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumAA2)/sumTotalProjectAmount*100, 2)+"%");
		 ratioModel.setAA3(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumAA3)/sumTotalProjectAmount*100, 2)+"%");
	
		 ratioModel.setBB2(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumBB2)/sumTotalProjectAmount*100, 2)+"%");
		 ratioModel.setBB3(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumBB3)/sumTotalProjectAmount*100, 2)+"%");
		 
		 ratioModel.setCC2(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumCC2)/sumTotalProjectAmount*100, 2)+"%");
		 ratioModel.setCC3(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumCC3)/sumTotalProjectAmount*100, 2)+"%");
		
		 ratioModel.setDD2(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumDD2)/sumTotalProjectAmount*100, 2)+"%");
		 ratioModel.setDD3(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumDD3)/sumTotalProjectAmount*100, 2)+"%");
		
		 ratioModel.setEE2(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumEE2)/sumTotalProjectAmount*100, 2)+"%");
		 ratioModel.setEE3(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumEE3)/sumTotalProjectAmount*100, 2)+"%");

		 ratioModel.setFF2(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumFF2)/sumTotalProjectAmount*100, 2)+"%");
		 ratioModel.setFF3(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumFF3)/sumTotalProjectAmount*100, 2)+"%");
	
		 ratioModel.setGG2(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumGG2)/sumTotalProjectAmount*100, 2)+"%");
		 ratioModel.setGG3(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumGG3)/sumTotalProjectAmount*100, 2)+"%");

		 ratioModel.setHH2(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumHH2)/sumTotalProjectAmount*100, 2)+"%");
		 ratioModel.setHH3(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumHH3)/sumTotalProjectAmount*100, 2)+"%");
	
		 ratioModel.setII2(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumII2)/sumTotalProjectAmount*100, 2)+"%");
		 ratioModel.setII3(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumII3)/sumTotalProjectAmount*100, 2)+"%");
	
		 ratioModel.setJJ2(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumJJ2)/sumTotalProjectAmount*100, 2)+"%");
		 ratioModel.setJJ3(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumJJ3)/sumTotalProjectAmount*100, 2)+"%");
	
		 ratioModel.setKK2(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumKK2)/sumTotalProjectAmount*100, 2)+"%");
		 ratioModel.setKK3(sumTotalProjectAmount==0D?"0%":CommonUtils.reservedDecimalPlaces((sumKK3)/sumTotalProjectAmount*100, 2)+"%");
		 
		 list.add(ratioModel);
		 
		return list;
	}
	
	/**
	 *   导出 子场景工单统计
	 	 * @author WANGJUN
	 	 * @title: exportChildSceneReports
	 	 * @date Jul 25, 2016 10:13:00 AM
	 	 * @param sheetAcceptLimit
	 	 * @param sheetCompleteLimit
	 	 * @param cityId
	 	 * @param flag
	 	 * @return HSSFWorkbook
	 */
	public HSSFWorkbook exportChildSceneReports(String sheetAcceptLimit,String sheetCompleteLimit,String cityId,String flag){
		HSSFWorkbook wb = new HSSFWorkbook();
		String sheetName = "地市统计";
		if("countyQuery".equals(flag)){
			sheetName = "区县统计";
		}
		HSSFSheet sheet = wb.createSheet(sheetName); 
		//-------------创建表头------------------
		HSSFRow row0 = sheet.createRow((short) 0);
		String[] hearder0_6 = new String[]{"市分公司","市分公司预检预修项目总数（项） ","市分公司预检预修项目总金额（元）","工费总额","材料费总额","工费占比","材料费占比"};
		for(int i= 0;i<hearder0_6.length;i++){
		    HSSFCell cell = row0.createCell((short) i);
		    sheet.addMergedRegion(new Region(0,(short)i,1,(short)i));
		    cell.setCellValue(new HSSFRichTextString(hearder0_6[i])); 
		}
		
		String[] hearder0_7 = new String[]{"敷设光缆（不含接续）","光缆接头接续","光缆成端接续","拆除光缆","光缆整理","光缆断纤、劣化纤处理","光交接箱安装或迁移","光分路器箱（光分纤箱）安装或迁移","障碍物清除","立电杆","扶正电杆","拆电杆","增补拉线","拆除拉线","架设吊线","拆除吊线","安装吊线保护装置","升高吊线","增补挂钩","障碍物清除","管道迁改","管道疏通及整修","管道积水及杂物清理","管道人孔升降、人井上覆的更换","障碍物清除","线路资源清查","充气设备维修","标识安装","标识粉刷","护坡加固","架空交接箱站台维修","交接分线设备粉刷、编号喷涂","障碍物清除","光缆（含光交）_其他费用（手工填写）","杆路_其他费用（手工填写）","管道_其他费用（手工填写）","其他项目_其他费用（手工填写）",""};
		int[] jiange = new int[]{4,6,6,4,4,4,4,4,4,5,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,4,2};
		int start = 7 ;
		for(int i=0 ;i<hearder0_7.length;i++){
			HSSFCell cell0_7 = row0.createCell((short) start);
			sheet.addMergedRegion(new Region(0,(short)start,0,(short)(start+jiange[i]-1)));
			cell0_7.setCellValue(new HSSFRichTextString(hearder0_7[i]));
			start =start+jiange[i];
		}
		
		String[] hearder1_7 = new String[]{"数量（千米）","工费","材料费","费用占比","数量（头）","工费","材料费","单位接头工费(元)","单位接头材料费用（元）","接续费用占比","数量（芯）","工费","材料费","单位成端工费（元）","单位成端材料费（元）","接续费用占比","数量（千米）","工费","材料费","费用占比","数量（档）","工费","材料费","费用占比","数量（头）","工费","材料费","费用占比","数量（个）","工费","材料费","费用占比","数量（个）","工费","材料费","费用占比","数量（次）","工费","材料费","费用占比","数量（棵）","工费","材料费","二次搬运费","费用占比","数量（棵）","工费","材料费","费用占比","数量（棵）","工费","材料费","费用占比","数量（条）","工费","材料费","费用占比","数量（条）","工费","材料费","费用占比","数量（千米）","工费","材料费","费用占比","数量（千米）","工费","材料费","费用占比","数量（米）","工费","材料费","费用占比","数量（杆档）","工费","材料费","费用占比","数量（米）","工费","材料费","费用占比","数量（次）","工费","材料费","费用占比","数量（百米）","工费","材料费","费用占比","数量（米）","工费","材料费","费用占比","数量（次）","工费","材料费","费用占比","数量（次）","工费","材料费","费用占比","数量（次）","工费","材料费","费用占比","数量（人天）","工费","材料费","费用占比","数量（次）","工费","材料费","费用占比","数量（个）","工费","材料费","费用占比","数量（个）","工费","材料费","费用占比","数量","工费","材料费","费用占比","数量（个）","工费","材料费","费用占比","数量（次）","工费","材料费","费用占比","数量（次）","工费","材料费","费用占比","数量（次）","工费","材料费","费用占比","数量（次）","工费","材料费","费用占比","数量（次）","工费","材料费","费用占比","数量（次）","工费","材料费","费用占比","非标准模板项目（次）","非标准模板费用占比"};
		HSSFRow row1 = sheet.createRow((short) 1);
		int j = 7 ;
		for(int i= 0;i<hearder1_7.length;i++){
		    HSSFCell cell = row1.createCell((short) j++);
		    cell.setCellValue(new HSSFRichTextString(hearder1_7[i])); 
		}
		
		int rowNum = 2; //sheet真实数据开始的行数
		List<ChildSceneReportsModel> list = this.childSceneReports(sheetAcceptLimit, sheetCompleteLimit, cityId, flag);
		for(ChildSceneReportsModel model : list){
			int colNum = 0;
			HSSFRow row = sheet.createRow(rowNum);
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getAreaName()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getTotalNum()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getTotalProjectAmount()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getTotalWorkAmout()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getTotalDataAmout()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getWorkRatio()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getDataRatio()));
			
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getA1()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getA2()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getA3()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getA4()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getB1()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getB2()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getB3()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getB4()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getB5()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getB6()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getC1()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getC2()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getC3()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getC4()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getC5()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getC6()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getD1()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getD2()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getD3()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getD4()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getE1()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getE2()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getE3()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getE4()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getF1()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getF2()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getF3()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getF4()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getG1()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getG2()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getG3()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getG4()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getH1()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getH2()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getH3()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getH4()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getI1()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getI2()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getI3()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getI4()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getJ1()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getJ2()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getJ3()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getJ4()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getJ5()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getK1()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getK2()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getK3()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getK4()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getL1()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getL2()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getL3()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getL4()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getM1()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getM2()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getM3()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getM4()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getN1()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getN2()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getN3()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getN4()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getO1()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getO2()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getO3()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getO4()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getP1()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getP2()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getP3()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getP4()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getQ1()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getQ2()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getQ3()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getQ4()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getR1()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getR2()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getR3()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getR4()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getS1()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getS2()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getS3()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getS4()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getT1()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getT2()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getT3()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getT4()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getU1()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getU2()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getU3()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getU4()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getV1()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getV2()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getV3()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getV4()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getW1()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getW2()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getW3()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getW4()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getX1()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getX2()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getX3()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getX4()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getY1()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getY2()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getY3()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getY4()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getZ1()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getZ2()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getZ3()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getZ4()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getAA1()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getAA2()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getAA3()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getAA4()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getBB1()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getBB2()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getBB3()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getBB4()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getCC1()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getCC2()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getCC3()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getCC4()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getDD1()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getDD2()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getDD3()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getDD4()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getEE1()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getEE2()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getEE3()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getEE4()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getFF1()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getFF2()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getFF3()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getFF4()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getGG1()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getGG2()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getGG3()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getGG4()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getHH1()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getHH2()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getHH3()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getHH4()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getII1()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getII2()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getII3()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getII4()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getJJ1()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getJJ2()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getJJ3()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getJJ4()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getKK1()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getKK2()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getKK3()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getKK4()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getLL1()));
			row.createCell((short) colNum++).setCellValue(new HSSFRichTextString(model.getMM1()));
			rowNum++;
		}
		return wb;
	}
	
}
