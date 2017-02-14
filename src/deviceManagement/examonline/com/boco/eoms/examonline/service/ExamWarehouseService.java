package com.boco.eoms.examonline.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.commons.system.dict.model.TawSystemDictType;
import com.boco.eoms.commons.system.dict.service.ID2NameService;
import com.boco.eoms.examonline.dao.ExamWarehouseDao;

public class ExamWarehouseService {
	private ExamWarehouseDao examWarehouseDao;
	
	public ExamWarehouseDao getExamWarehouseDao() {
		return examWarehouseDao;
	}


	public void setExamWarehouseDao(ExamWarehouseDao examWarehouseDao) {
		this.examWarehouseDao = examWarehouseDao;
	}

	
	/**
	 * 查询试题，以专业和题型进行分组统计
	 * @return
	 */
	public List<Map<String,String>> findAllExamCount(){
		//通过ID2NameService的方法获得相应专业的名称
		ID2NameService service = (ID2NameService) ApplicationContextHolder.getInstance().getBean("ID2NameGetServiceCatch"); 
		
		List listResult = examWarehouseDao.findByHql("select count(*),contype,specialty from ExamWarehouse group by specialty,contype order by specialty");
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		Map<String,String> map = null;
		String lastSpecialtyId = "";		//上一次的专业id
		String requestParameters = "";		//跳转参数字符串
		
		for(Object obj:listResult){	
			Object[] object = (Object[])obj;
	//当前专业为下级菜单	
			if(object[2].toString().trim().length()>7){ 
				if(null==map){   //循环第一次map为空
					map = new HashMap<String,String>();
					map.put("specialtyId", object[2].toString().trim().substring(0, 7));
					//获得对应专业号得专业名
					String name = service.id2Name(object[2].toString().trim().substring(0, 7), "ItawSystemDictTypeDao");
					map.put("specialtyName", name);
				}
				
				if(!object[2].toString().trim().substring(0, 7).equals(map.get("specialtyId"))){
					//添加requestParameters（跳转的参数）
					map.put("url", requestParameters);
					requestParameters = "";
					
					list.add(map);
					map = new HashMap<String,String>();
					map.put("specialtyId", object[2].toString().trim().substring(0, 7));
					//获得对应专业号得专业名
					String name = service.id2Name(object[2].toString().trim().substring(0, 7), "ItawSystemDictTypeDao");
					map.put("specialtyName", name);
				}
				
				//将相应题型与数量添加入map中
				String contype = "key"+object[1].toString().trim();
				if(null==map.get(contype)){  //map中不已存在相应题型得数量
					map.put(contype, object[0].toString().trim());
				}else{		 //map中已存在相应题型的数量
					int a=Integer.parseInt(map.get(contype).toString().trim());
					int b=Integer.parseInt(object[0].toString().trim());
					map.put(contype, (a+b)+"");
				}
	//当前专业为一级菜单			
			}else{		
				if(null==map){   //循环第一次map为空
					map = new HashMap<String,String>();
					map.put("specialtyId", object[2].toString().trim());
					//获得对应专业号得专业名
					String name = service.id2Name(object[2].toString().trim(), "ItawSystemDictTypeDao");
					map.put("specialtyName", name);
				}
				
				if(!object[2].toString().trim().equals(map.get("specialtyId"))){
					//添加requestParameters（跳转的参数）
					map.put("url", requestParameters);
					requestParameters = "";
					
					list.add(map);
					map = new HashMap<String,String>();
					map.put("specialtyId", object[2].toString().trim());
					//获得对应专业号得专业名
					String name = service.id2Name(object[2].toString().trim(), "ItawSystemDictTypeDao");
					map.put("specialtyName", name);
				}
	
				String contype = "key"+object[1].toString().trim();
				map.put(contype, object[0].toString().trim());
			}
			
			//获得requestParameters（跳转的参数）
			if(!lastSpecialtyId.equals(object[2].toString().trim())){
				lastSpecialtyId = object[2].toString().trim();
				requestParameters = requestParameters+"&specialtyId="+lastSpecialtyId;
			}
			
		}
		//如果试题库没有试题
		if(map != null){
			map.put("url", requestParameters);
			list.add(map);  //循环结束将最后的map加入list中	
		}
			
		return list;
	}
	
	
	/**
	 * 通过专业和题型查询题目，以难度和公司进行分组统计（逻辑同上方法）
	 * @param hql
	 * @return
	 */
	public List<Map<String,String>> findExamCountBySpecialty(String contype,String[] specialtyIds){
		
		//生成HQL语句
		String hql = "select count(*),manufacturer,difficulty from ExamWarehouse where";
		for(int i=0;i<specialtyIds.length;i++){
			if(0==i){
				hql = hql+" (specialty='"+specialtyIds[i]+"'";
			}else{
				hql = hql+" or specialty='"+specialtyIds[i]+"'";
			}
		}
		hql = hql+") and contype="+contype+" group by difficulty,manufacturer order by difficulty";
		
		//查询出结果，并处理
		List listResult = examWarehouseDao.findByHql(hql);
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		Map<String,String> map = null;
		for(Object obj:listResult)
		{	
			Object[] object = (Object[])obj;
			
			if(null==map){   //循环第一次map为空
				map = new HashMap<String,String>();
				if("1".equals(object[2].toString().trim())){
					map.put("difficultyName", "初级");
				}else if("2".equals(object[2].toString().trim())){
					map.put("difficultyName", "中级");
				}else{
					map.put("difficultyName", "高级");
				}
				map.put("difficulty", object[2].toString().trim());
			}
			
			if(!object[2].toString().trim().equals(map.get("difficulty"))){
				list.add(map);
				map = new HashMap<String,String>();
				if("1".equals(object[2].toString().trim())){
					map.put("difficultyName", "初级");
				}else if("2".equals(object[2].toString().trim())){
					map.put("difficultyName", "中级");
				}else{
					map.put("difficultyName", "高级");
				}
				map.put("difficulty", object[2].toString().trim());
			}

			String specialty = "k"+object[1].toString().trim();
			map.put(specialty, object[0].toString().trim());
		}
		list.add(map);  //循环结束将最后的map加入list中	
		return list;
	}
	
	
	/**
	 * 查询专业信息，产生HQL查询语句条件
	 * @param hql
	 * @return
	 */
	public String selectSpecialty(String specialty){
		String hql = "from TawSystemDictType where parentdictid='"+specialty+"'";
		List resultList = examWarehouseDao.findByHql(hql);
		
		String sql = " or specialty='"+specialty+"'";
		for(Object obj:resultList){
			TawSystemDictType tawSystemDictType = (TawSystemDictType)obj;
			if(0==tawSystemDictType.getLeaf().intValue()){
				sql = sql + selectSpecialty(tawSystemDictType.getDictId().trim());
			}else{
				sql = sql + " or specialty='"+tawSystemDictType.getDictId().trim()+"'";
			}
		}
		return sql;
	}
}
