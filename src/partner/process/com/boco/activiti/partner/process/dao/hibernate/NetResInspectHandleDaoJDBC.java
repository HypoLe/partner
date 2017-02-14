package com.boco.activiti.partner.process.dao.hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.boco.activiti.partner.process.dao.INetResInspectHandleJDBCDao;
import com.boco.activiti.partner.process.model.RoomDemolitionHandle;

public class NetResInspectHandleDaoJDBC extends JdbcDaoSupport implements
	INetResInspectHandleJDBCDao {

	/**
	 * 查询单条回复信息
	 * @param tempMap 查询条件
	 * @return
	 */
	public RoomDemolitionHandle getOneTransferHandle(
			Map<String, String> tempMap) {
		String sql = "select handle.id,handle.handle_description, handle.do_person from (select * from PNR_ACT_RoomDemolition_HANDLE h where h.process_instance_id = '"+tempMap.get("processInstanceId")+"' and h.link_name = '"+tempMap.get("linkName")+"' order by h.check_time desc) handle where rownum = 1";
		
		System.out.println("--------------查询单条回复信息 sql="+sql);
	
		List<RoomDemolitionHandle> r = new ArrayList<RoomDemolitionHandle>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql);
		for (Map map : list) {
			RoomDemolitionHandle model = new RoomDemolitionHandle();
			if(map.get("id")!=null && !"".equals(map.get("id"))){
				model.setId(map.get("id").toString());
			}
			if(map.get("handle_description")!=null && !"".equals(map.get("handle_description"))){
				model.setHandleDescription(map.get("handle_description").toString());
			}
			if(map.get("do_person")!=null && !"".equals(map.get("do_person"))){
				model.setDoPerson(map.get("do_person").toString());
			}
			
			r.add(model);
		}
		return r.get(0);
	}
}
