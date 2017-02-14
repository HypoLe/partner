package com.boco.eoms.partner.inspect.dao.jdbc;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.boco.eoms.partner.inspect.dao.IInspectTemplateBigItemDaoJdbc;

public class InspectTemplateBigItemDaoJdbc extends JdbcDaoSupport implements IInspectTemplateBigItemDaoJdbc {

	/**
	 * 根据当前的id数组删除模板
	 * @param ids
	 */
	public void deleteTemplateItems(String[] ids,String templateId) {
		String id ="";
		for(int i=0;i<ids.length;i++){
			id+=" '"+ids[i]+"' ,";
		}
		id = id.substring(0, id.length()-1);
		String sql ="update pnr_inspect_template_item set status=0 where template_id='"+templateId+"' and id not in("+id+")";
		if(id.length()>0){
			this.getJdbcTemplate().execute(sql);
		}
	}

	/**
	 * 删除模板大项
	 */
	@SuppressWarnings("unchecked")
	public Map<String, String> deleteTemplateBigItems(Map<String, Integer> map,String templateId) {
		String id="";
		Iterator<String> it = map.keySet().iterator();
		while(it.hasNext()){
			id += " '"+it.next()+"' ,";
		}
		id=id.substring(0, id.length()-1);
		String sql = "update pnr_inspect_template_bigitem set status=0 where template_id='"+templateId+"' and id not in("+id+") ";
		this.getJdbcTemplate().execute(sql);
				
		return null;
	}
	
	/**
	 * 查询模板大项
	 */
	public Map<String, String> findTemplateBigItems(Map<String, Integer> map,String templateId) {
		String sql2 = "select id,name,item_num from pnr_inspect_template_bigitem where template_id='"+templateId+"' and status=1";
		List list = this.getJdbcTemplate().queryForList(sql2);
		Map bigMap = new HashMap<String, String>();

		for(int i=0;i<list.size();i++){
			String ids = ((Map<String, String>)list.get(i)).get("id");
			String name = ((Map<String, String>)list.get(i)).get("name");
			bigMap.put(name, ids);
		}
				
		return bigMap;
	}

}
