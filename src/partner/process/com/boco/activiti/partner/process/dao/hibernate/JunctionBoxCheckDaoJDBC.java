package com.boco.activiti.partner.process.dao.hibernate;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.boco.activiti.partner.process.dao.IJunctionBoxCheckJDBCDao;
import com.boco.activiti.partner.process.po.JunctionBoxModel;

public class JunctionBoxCheckDaoJDBC extends JdbcDaoSupport implements
		IJunctionBoxCheckJDBCDao {

	/**
	 * 根据交接箱ID查询核查表中的数据条数
	 * 
	 * @param fiberNodeId
	 *            交接箱ID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int judgeJunctionBoxIsCheckCompleted(String fiberNodeId) {
		String sql = "select * from JK_FIBER_NODE_FILL f where f.fiber_node_id = '"
				+ fiberNodeId + "'";
		List<Map> list = this.getJdbcTemplate().queryForList(sql);
		return list.size();
	}

	/**
	 * 通过交接箱ID获取交接箱详情
	 * 
	 * @param fiberNodeId
	 * @return
	 */
	public JunctionBoxModel findJunctionBoxDetail(String fiberNodeId) {
		JunctionBoxModel junctionBoxModel = null;
		String sql = "select * from jk_xcwh_fiber_node n where n.fiber_node_id = '"
				+ fiberNodeId + "'";
		List<Map> list = this.getJdbcTemplate().queryForList(sql);
		int size = list.size();		
		if (size > 0) {
			Map map = list.get(0);
			junctionBoxModel=new JunctionBoxModel();
			if (map.get("fiber_node_id") != null
					&& !"".equals(map.get("fiber_node_id"))) {
				junctionBoxModel.setFiberNodeId(map.get("fiber_node_id")
						.toString());
			}
			if (map.get("fiber_node_no") != null
					&& !"".equals(map.get("fiber_node_no"))) {
				junctionBoxModel.setFiberNodeNo(map.get("fiber_node_no")
						.toString());
			}
			if (map.get("bureau_id") != null
					&& !"".equals(map.get("bureau_id"))) {
				junctionBoxModel.setBureauId(map.get("bureau_id")
						.toString());
			}
			if (map.get("bureau_name") != null
					&& !"".equals(map.get("bureau_name"))) {
				junctionBoxModel.setBureauName(map.get("bureau_name")
						.toString());
			}
			if (map.get("area_id") != null
					&& !"".equals(map.get("area_id"))) {
				junctionBoxModel.setAreaId(map.get("area_id")
						.toString());
			}
			if (map.get("area_name") != null
					&& !"".equals(map.get("area_name"))) {
				junctionBoxModel.setAreaName(map.get("area_name")
						.toString());
			}
			if (map.get("city_id") != null
					&& !"".equals(map.get("city_id"))) {
				junctionBoxModel.setCityId(map.get("city_id")
						.toString());
			}
			if (map.get("city_name") != null
					&& !"".equals(map.get("city_name"))) {
				junctionBoxModel.setCityName(map.get("city_name")
						.toString());
			}
			if (map.get("memo") != null
					&& !"".equals(map.get("memo"))) {
				junctionBoxModel.setMemo(map.get("memo")
						.toString());
			}
			if (map.get("count_num") != null
					&& !"".equals(map.get("count_num"))) {
				junctionBoxModel.setCountNum(map.get("count_num")
						.toString());
			}
			
		}
		
		return junctionBoxModel;
	}

}
