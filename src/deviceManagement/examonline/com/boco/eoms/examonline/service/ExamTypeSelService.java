package com.boco.eoms.examonline.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.beanutils.BeanUtils;

import com.boco.eoms.common.util.StaticMethod;
import com.boco.eoms.examonline.dao.ExamTypeSelDao;
import com.boco.eoms.examonline.model.ExamTypeSel;
import com.boco.eoms.examonline.model.ExamWarehouse;

public class ExamTypeSelService {
	public static int staticIssueExam = 2;
	private ExamTypeSelDao examTypeSelDao;

	public void setExamTypeSelDao(ExamTypeSelDao examTypeSelDao) {
		this.examTypeSelDao = examTypeSelDao;
	}

	public void addTypeSel(ExamTypeSel examTypeSel) {
		examTypeSelDao.save(examTypeSel);
	}
	/**
	 * 查询试题参数TypeSel
	 * 
	 * @param typesel_fk
	 * @return
	 */
	public List<ExamTypeSel> getExamTypeSelList(String typesel_fk) {
		String hql = "from ExamTypeSel e where e.typesel_fk='" + typesel_fk+"'";
		return examTypeSelDao.getExamTypeSelList(hql);
	}
	/**
	 * 根据examtypesel从数据库中取题
	 * 
	 * @param typesel_fk
	 *            生成examtypesel时的typesel_fk
	 * @return
	 */
	public List<ExamWarehouse> getExamWarehouseList(String typesel_fk) {
//		List<ExamTypeSel> examTypeSelList =getExamTypeSelList(typesel_fk);
//		List<ExamWarehouse> examWareHouseList = new ArrayList<ExamWarehouse>();
//		String hql = null;
//		if (!examTypeSelList.isEmpty()) {
//			for (int i = 0; i < examTypeSelList.size(); i++) {
//				ExamTypeSel examTypeSel = examTypeSelList.get(i);
//
//				int manufacturer = examTypeSel.getManufacturer();
//				String specialty = examTypeSel.getSpecialty();
//				int difficulty = examTypeSel.getDifficulty();
//				int radioCount = examTypeSel.getRadioCount();
//				int multipleCount = examTypeSel.getMultipleCount();
//				int judgeCount = examTypeSel.getJudgeCount();
//				int qaCount = examTypeSel.getQaCount();
//				int essayCount = examTypeSel.getEssayCount();
//				int[] count={radioCount,multipleCount,judgeCount,qaCount,essayCount};
//				for (int j = 1; j < 6; j++) {
//					if (count[j-1] != 0) {
//					hql = "from ExamWarehouse e where e.contype="+j+" and e.specialty="+ specialty + " and e.difficulty=" + difficulty+ " and e.manufacturer=" + manufacturer;
//					List templist = examTypeSelDao.getExamList(hql);
//					for(int k=0;k<count[j-1];k++){
//					  examWareHouseList.add((ExamWarehouse) templist.get(SubjectRandom(templist.size())));
//			           templist.remove(SubjectRandom(templist.size()));
//					}
//					}
//				}
//			}
//		}
		return null;
	}
	 /**
	   * 取出一个小于count的随机数
	   * @param count int
	   * @return int
	   */
	  public int SubjectRandom(int count) {
	    Random random = new Random();
	    int subId = Math.abs(random.nextInt()) % count;
	    return subId;
	  }
	  public List<ExamWarehouse> getExamWarehouseList(List<ExamTypeSel> examTypeSelList,String tag) {
			List<ExamWarehouse> examWareHouseList = new ArrayList<ExamWarehouse>();
			String hql = null;
			if (!examTypeSelList.isEmpty()) {
				for (int i = 0; i < examTypeSelList.size(); i++) {
					 ExamTypeSel examTypeSel = (ExamTypeSel)examTypeSelList.get(i);
					String typeSelId=StaticMethod.nullObject2String(examTypeSel.getId());
					int manufacturer = StaticMethod.nullObject2int(examTypeSel.getManufacturer());
					String specialty = StaticMethod.nullObject2String(examTypeSel.getSpecialty());
					int difficulty = StaticMethod.nullObject2int(examTypeSel.getDifficulty());
					int radioCount = StaticMethod.nullObject2int(examTypeSel.getRadioCount());
					int multipleCount = StaticMethod.nullObject2int(examTypeSel.getMultipleCount());
					int judgeCount = StaticMethod.nullObject2int(examTypeSel.getJudgeCount());
					int qaCount = StaticMethod.nullObject2int(examTypeSel.getQaCount());
					int essayCount = StaticMethod.nullObject2int(examTypeSel.getEssayCount());
					int[] count={radioCount,multipleCount,judgeCount,qaCount,essayCount};
					int[] score={StaticMethod.nullObject2int(examTypeSel.getRadioScore()),
							StaticMethod.nullObject2int(examTypeSel.getMultipleScore()),
							StaticMethod.nullObject2int(examTypeSel.getJudgeScore()),
							StaticMethod.nullObject2int(examTypeSel.getQaScore()),
							StaticMethod.nullObject2int(examTypeSel.getEssayScore())};
					for (int j = 1; j < 6; j++) {
						if (count[j-1] != 0) {
							hql = "from ExamWarehouse e where e.contype="+j+" and e.specialty="+ specialty + " and e.difficulty=" + difficulty+ " and e.manufacturer=" + manufacturer;
							List templist = examTypeSelDao.getExamList(hql);
							for(int k=0;k<count[j-1];k++){
								int f = SubjectRandom(templist.size());
								ExamWarehouse tempExamWarehouse = (ExamWarehouse) templist.get(f);
//	ExamWarehouse examWarehouse = tempExamWarehouse;
								//hibernate的缓存会影响到前面添加的数据，通过new来产生新的对象，前把值付给它
								ExamWarehouse examWarehouse = new ExamWarehouse();
								try {
									BeanUtils.copyProperties(examWarehouse, tempExamWarehouse);
								} catch (Exception e) {
									e.printStackTrace();
								} 
								
								examWarehouse.setTypeSelId(typeSelId);
								 if ("examTag".equals(tag)) {
						                examWarehouse.setValue(score[(j - 1)]);
						              }
							   examWareHouseList.add(examWarehouse);
					           templist.remove(f);
							}
						}
					}
				}
			}
			return examWareHouseList;
		}
	/**
	 * 制定试题
	 */
	public void generateExam(){
		
	}
}
