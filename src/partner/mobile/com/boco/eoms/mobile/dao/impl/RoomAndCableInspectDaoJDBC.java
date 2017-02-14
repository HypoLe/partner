package com.boco.eoms.mobile.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.boco.eoms.mobile.dao.IRoomAndCableInspectJDBCDao;
import com.boco.eoms.mobile.po.CellItem;
import com.boco.eoms.mobile.po.DianganCabel;
import com.boco.eoms.mobile.po.EquipmentRoomItem;
import com.boco.eoms.mobile.po.GjxCableOpticalCableItem;
import com.boco.eoms.mobile.po.LinkEquipmentItem;
import com.boco.eoms.mobile.po.LinkEquipmentOpticalCableItem;
import com.boco.eoms.mobile.po.LinkEquipmentTerminalItem;
import com.boco.eoms.mobile.po.PortOpticalCableItem;
import com.boco.eoms.mobile.po.WellsOpticalCableItem;
import com.boco.eoms.mobile.util.AndroidPropertiesUtils;

/**
 * 
 * @author WANGJUN
 * @ClassName: RoomAndCableInspectDaoJDBC
 * @Version
 * @ModifiedBy
 * @Copyright BOCO
 * @date Sep 29, 2016 9:59:01 AM
 * @description 接入机房和光缆网核查JDBC实现类
 */
public class RoomAndCableInspectDaoJDBC extends JdbcDaoSupport implements
		IRoomAndCableInspectJDBCDao {

	/**
	 * 查询人井对应的光缆、管孔信息
	 * 
	 * @author WANGJUN
	 * @title: getWellsOpticalCableList
	 * @date Sep 29, 2016 10:48:54 AM
	 * @param planResId
	 * @return List<WellsOpticalCableItem>
	 */
	public List<WellsOpticalCableItem> getWellsOpticalCableList(String planResId,int pageIndex,int pageSize) {
		List<Object> paramList = new ArrayList<Object>();
		String sql = "select temp2.* from (select temp1.*, rownum num from (";
		sql += "select id, ofiber_no, hole_no\n"
				+ "  from zhzy_renjing\n"
				+ " where well_id = (select sub_res_id\n"
				+ "                    from Pnr_Res_Config\n"
				+ "                   where id = (select res_cfg_id\n"
				+ "                                 from pnr_inspect_plan_res r\n"
				+ "                                where r.id = ?)) and nvl(operate_type,'add') !='delete' order by hole_no";
		sql += ") temp1 where rownum <=?) temp2 where temp2.num >? ";
		System.out.println("------查询人井对应的光缆、管孔信息sql="+sql);
		paramList.add(planResId);
		paramList.add((pageIndex+1)*pageSize);
		paramList.add(pageIndex*pageSize);
		Object[] args = paramList.toArray();
		List<WellsOpticalCableItem> r = new ArrayList<WellsOpticalCableItem>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql, args);
		for (Map map : list) {
			WellsOpticalCableItem model = new WellsOpticalCableItem();
			if (map.get("id") != null && !"".equals(map.get("id").toString())) {
				model.setId(map.get("id").toString());
			}
			if (map.get("ofiber_no") != null && !"".equals(map.get("ofiber_no").toString())) {
				model.setCableName(map.get("ofiber_no").toString());
			}
			if (map.get("hole_no") != null && !"".equals(map.get("hole_no").toString())) {
				model.setPipeHoleInfor(map.get("hole_no").toString());
			}
			r.add(model);
		}
		return r;
	}
	
	/**
	 * 	 查询人井对应的光缆、管孔信息的总条数
	 	 * @author WANGJUN
	 	 * @title: getWellsOpticalCableCount
	 	 * @date Oct 9, 2016 10:16:00 AM
	 	 * @param planResId
	 	 * @return int
	 */
	public int getWellsOpticalCableCount(String planResId){
		int total = 0;
		List<Object> paramList = new ArrayList<Object>();
		String sql = "select count(id) as total"
				+ "  from zhzy_renjing\n"
				+ " where well_id = (select sub_res_id\n"
				+ "                    from Pnr_Res_Config\n"
				+ "                   where id = (select res_cfg_id\n"
				+ "                                 from pnr_inspect_plan_res r\n"
				+ "                                where r.id = ?)) and nvl(operate_type,'add') !='delete'";
		System.out.println("------查询人井对应的光缆、管孔信息的总条数sql="+sql);
		paramList.add(planResId);
		Object[] args = paramList.toArray();
		try{
			total = this.getJdbcTemplate().queryForInt(sql,args);
		}catch(Exception e){
			e.printStackTrace();
		}
		return total;
	}

	/**
	 *   更新人井对应的光缆信息
	 	 * @author WANGJUN
	 	 * @title: updateWellsOpticalCable
	 	 * @date Sep 29, 2016 3:30:29 PM
	 	 * @param id 光缆id
	 	 * @param cableName 光缆名称
	 	 * @param pipeHoleInfor 管孔信息
	 	 * @param operateType 操作类型
	 */
	public int updateWellsOpticalCable(String id,String cableName,String pipeHoleInfor,String operateType){
		int rows = 0;
		List<Object> paramList = new ArrayList<Object>();
		String sql = "";
		if("delete".equals(operateType)){
			sql = "update zhzy_renjing set operate_type = ?,operate_time = sysdate where id = ?";
			paramList.add(operateType);
			paramList.add(id);
		}else if("alter".equals(operateType)){
			sql ="update zhzy_renjing" +
				 "   set ofiber_no = ?, hole_no = ?, operate_type = ?,operate_time = sysdate " + 
				 " where id = ?";
			paramList.add(cableName);
			paramList.add(pipeHoleInfor);
			paramList.add(operateType);
			paramList.add(id);
		}else{
			return rows;
		}
		Object[] args = paramList.toArray();
		rows  = this.getJdbcTemplate().update(sql,args);
		return rows;
	}
	
	/**
	 * 查询光交箱端子模块
	 * 
	 * @param planResId
	 * @return
	 */
	public List<PortOpticalCableItem> getGjxPortOpticalCableList(String planResId,int pageIndex,int pageSize) {
		List<Object> paramList = new ArrayList<Object>();
		String sql = "select temp2.* from (select temp1.*, rownum num from (";
		sql+=   "select distinct fiber_node_id as gjxid, fmodule_no as module\n" +
				"  from "+AndroidPropertiesUtils.getValue("gjx_duanzi")+" zh, Pnr_Res_Config con, pnr_inspect_plan_res r\n" + 
				" where zh.fiber_node_id = con.sub_res_id\n" + 
				"   and con.id = r.res_cfg_id\n" + 
				"   and r.id = ?\n" + 
				" order by fmodule_no";
		 sql += ") temp1 where rownum <=?) temp2 where temp2.num >? ";
			System.out.println("-----查询光交箱端子模块信息sql="+sql);
			paramList.add(planResId);
			paramList.add((pageIndex+1)*pageSize);
			paramList.add(pageIndex*pageSize);
		Object[] args = paramList.toArray();
		List<PortOpticalCableItem> r = new ArrayList<PortOpticalCableItem>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql, args);
		for (Map map : list) {
			PortOpticalCableItem model = new PortOpticalCableItem();
			if (map.get("gjxid") != null && !"".equals(map.get("gjxid").toString())) {
				model.setGjxId(map.get("gjxid").toString());
			}
			if (map.get("module") != null && !"".equals(map.get("module").toString())) {
				model.setModule(map.get("module").toString());
			}
			r.add(model);
		}
		return r;
	}

	/**
	 * 	 查询光交箱端子的总条数
	 	 * 
	 	 * @param planResId
	 	 * @return int
	 */
	public int getGjxPortOpticalCableCount(String planResId){
		int total = 0;
		List<Object> paramList = new ArrayList<Object>();
		String sql ="select count(distinct fmodule_no) as total\n" +
					"  from "+AndroidPropertiesUtils.getValue("gjx_duanzi")+" zh, Pnr_Res_Config con, pnr_inspect_plan_res r\n" + 
					" where zh.fiber_node_id = con.sub_res_id\n" + 
					"   and con.id = r.res_cfg_id\n" + 
					"   and r.id = ?";
		System.out.println("------查询光交箱端子模块信息的总条数sql="+sql);
		paramList.add(planResId);
		Object[] args = paramList.toArray();
		try{
			total = this.getJdbcTemplate().queryForInt(sql,args);
		}catch(Exception e){
			e.printStackTrace();
		}
		return total;
	}
	
	/**
	 * 更新光交箱端子信息
	 * @param id
	 * @param cableName
	 * @param pipeHoleInfor
	 * @param operateType
	 * @return
	 */
	public int updateGjxPortOpticalCable(String gjxid,String module,String port,String status,String userInfo,String operateType){		
		int rows = 0;
		List<Object> paramList = new ArrayList<Object>();
		String sql = "";
		if("delete".equals(operateType) || "alter".equals(operateType)){
			sql = "insert into tozhzy_guangjiaoxiang(fiber_node_id,fmodule_no,fport_no,fport_status,fcircuit_no,operate_type) values (?,?,?,?,?,?)";
//			sql = "insert into tozhzy_guangjiaoxiang(gjxid,module,port,status,userInfo,operateType) values(?,?,?,?,?)";
			paramList.add(gjxid);
			paramList.add(module);
			paramList.add(port);
			paramList.add(status);
			paramList.add(userInfo);
			paramList.add(operateType);
		}else{
			return rows;
		}
		System.out.println("------更新光交箱端子信息的sql="+sql);
		Object[] args = paramList.toArray();
		rows  = this.getJdbcTemplate().update(sql,args);
		return rows;
	}
	
	
	/**
	 * 查询光交箱经过缆情况信息
	 * 
	 * @param planResId
	 * @return
	 */
	public List<GjxCableOpticalCableItem> getGjxCableOpticalCableList(String planResId,int pageIndex,int pageSize) {
		List<Object> paramList = new ArrayList<Object>();
		String sql = "select temp2.* from (select temp1.*, rownum num from (";
		sql+=
			"select FIBER_NODE_ID as gjxId,\n" +
			"       ofiber_no     as ofiberName,\n" + 
			"       core_sequence as coreSequence,\n" + 
			"       fport_num     as fportNum,\n" + 
			"       core_status   as coreStatus\n" + 
			"  from "+AndroidPropertiesUtils.getValue("gjx_jingguolan")+" zh, Pnr_Res_Config con, pnr_inspect_plan_res r\n" + 
			" where zh.fiber_node_id = con.sub_res_id\n" + 
			"   and con.id = r.res_cfg_id\n" + 
			"   and r.id = ?\n" + 
			"   and not exists (select 1\n" + 
			"          from tozhzy_gjx_Cable a\n" + 
			"         where zh.fiber_node_id = a.fiber_node_id\n" + 
			"           and zh.ofiber_no = a.ofiber_name\n" + 
			"           and zh.core_sequence = a.core_sequence)\n" + 
			" order by ofiber_name, core_sequence";

		
		
		
//		sql +=	 "select FIBER_NODE_ID as gjxId,\n" +
//				 "       ofiber_no as  ofiberName,\n" + 
//				 "       core_sequence as coreSequence,\n" + 
//				 "       fport_num as fportNum,\n" + 
//				 "       core_status as coreStatus" + 
//				 "  from "+AndroidPropertiesUtils.getValue("gjx_jingguolan")+" zh,\n" + 
//				 "       Pnr_Res_Config               con,\n" + 
//				 "       pnr_inspect_plan_res         r\n" + 
//				 " where zh.fiber_node_id = con.sub_res_id\n" + 
//				 "   and con.id = r.res_cfg_id\n" + 
//				 "   and r.id = ?\n" + 
//				 " order by ofiber_name, core_sequence";
		 sql +=  ") temp1 where rownum <=?) temp2 where temp2.num >? ";
			System.out.println("-----查询光交箱经过缆信息sql="+sql);
			paramList.add(planResId);
			paramList.add((pageIndex+1)*pageSize);
			paramList.add(pageIndex*pageSize);
		Object[] args = paramList.toArray();
		List<GjxCableOpticalCableItem> r = new ArrayList<GjxCableOpticalCableItem>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql, args);
		for (Map map : list) {
			GjxCableOpticalCableItem model = new GjxCableOpticalCableItem();
			if (map.get("gjxId") != null && !"".equals(map.get("gjxId").toString())) {
				model.setGjxId(map.get("gjxId").toString());
			}
			if (map.get("ofiberName") != null && !"".equals(map.get("ofiberName").toString())) {
				model.setOfiberName(map.get("ofiberName").toString());
			}
			if (map.get("coreSequence") != null && !"".equals(map.get("coreSequence").toString())) {
				model.setCoreSequence(map.get("coreSequence").toString());
			}
			if (map.get("fportNum") != null && !"".equals(map.get("fportNum").toString())) {
				model.setFportNum(map.get("fportNum").toString());
			}
			if (map.get("coreStatus") != null && !"".equals(map.get("coreStatus").toString())) {
				model.setCoreStatus(map.get("coreStatus").toString());
			}
			r.add(model);
		}
		return r;
	}

	/**
	 * 	 查询光交箱经过缆的总条数
	 	 * 
	 	 * @param planResId
	 	 * @return int
	 */
	public int getGjxCableOpticalCableCount(String planResId){
		int total = 0;
		List<Object> paramList = new ArrayList<Object>();
		String sql ="select count(FIBER_NODE_ID) as total\n" +
					"  from "+AndroidPropertiesUtils.getValue("gjx_jingguolan")+" zh,\n" + 
					"       Pnr_Res_Config               con,\n" + 
					"       pnr_inspect_plan_res         r\n" + 
					" where zh.fiber_node_id = con.sub_res_id\n" + 
					"   and con.id = r.res_cfg_id\n" + 
					"   and r.id = ?\n" + 
					"   and not exists (select 1\n" + 
					"          from tozhzy_gjx_Cable a\n" + 
					"         where zh.fiber_node_id = a.fiber_node_id\n" + 
					"           and zh.ofiber_no = a.ofiber_name\n" + 
					"           and zh.core_sequence = a.core_sequence)\n";
		System.out.println("------查询光交箱经过缆信息的总条数sql="+sql);
		paramList.add(planResId);
		Object[] args = paramList.toArray();
		try{
			total = this.getJdbcTemplate().queryForInt(sql,args);
		}catch(Exception e){
			e.printStackTrace();
		}
		return total;
	}
	
	/**
	 * 更新光交箱经过缆信息
	 * @param id
	 * @param cableName
	 * @param pipeHoleInfor
	 * @param operateType
	 * @return
	 */
	public int updateGjxCableOpticalCable(String resId ,String id,String ofiber_name,String core_sequence,String operateType,String fportNum,String coreStatus){		
		int rows = 0;
		List<Object> paramList = new ArrayList<Object>();
		String sql = "";
		if("delete".equals(operateType) || "alter".equals(operateType)){
			sql = "insert into tozhzy_gjx_Cable(fiber_node_id,ofiber_name,core_sequence,operate_type,fport_num,core_status) values (?,?,?,?,?,?)";
//			sql = "insert into tozhzy_guangjiaoxiang(gjxid,module,port,status,userInfo,operateType) values(?,?,?,?,?)";
			paramList.add(id);
			paramList.add(ofiber_name);
			paramList.add(core_sequence);
			paramList.add(operateType);
			paramList.add(fportNum);
			paramList.add(coreStatus);
		}else if("add".equals(operateType)){
			sql = "insert into tozhzy_gjx_Cable(fiber_node_id,ofiber_name,core_sequence,operate_type,fport_num,core_status) values ((select sub_res_id from Pnr_Res_Config where id = (select res_cfg_id from pnr_inspect_plan_res r where r.id = ?)),?,?,?,?,?)";
//			sql = "insert into tozhzy_guangjiaoxiang(gjxid,module,port,status,userInfo,operateType) values(?,?,?,?,?)";
			paramList.add(resId);
			paramList.add(ofiber_name);
			paramList.add(core_sequence);
			paramList.add(operateType);
			paramList.add(fportNum);
			paramList.add(coreStatus);
		}else{
			return rows;
		}
		System.out.println("------更新光交箱经过缆信息的sql="+sql);
		Object[] args = paramList.toArray();
		rows  = this.getJdbcTemplate().update(sql,args);
		return rows;
	}
	
	/**
	 *   查询接入机房-机房设备 列表
	 	 * @author WANGJUN
	 	 * @title: getAccessRoomEquipmentRoomList
	 	 * @date Oct 13, 2016 9:55:23 AM
	 	 * @param planResId
	 	 * @param pageIndex
	 	 * @param pageSize
	 	 * @return List<WellsOpticalCableItem>
	 */
	public List<EquipmentRoomItem> getAccessRoomEquipmentRoomList(String planResId,int pageIndex,int pageSize){
		List<Object> paramList = new ArrayList<Object>();
		String sql = "select temp2.* from (select temp1.*, rownum num from (";
		sql+=
			"select room_id    as roomId, --机房id\n" +
			"       equip_no   as deviceName, --设备名称\n" + 
			"       net_type   as networkType, --网络类型\n" + 
			"       manu_no    as deviceVender, --设备厂家\n" + 
			"       equip_type as deviceType --设备类型\n" + 
			"  from "+AndroidPropertiesUtils.getValue("zhzy_room_equipment")+" zh, Pnr_Res_Config con, pnr_inspect_plan_res r\n" + 
			" where zh.room_id = con.sub_res_id\n" + 
			"   and con.id = r.res_cfg_id\n" + 
			"   and r.id = ?\n" + 
			"   and not exists (select 1\n" + 
			"          from tozhzy_room_equipment a\n" + 
			"         where zh.room_id = a.room_id\n" + 
			"           and zh.equip_no = a.equ_name)\n" + 
			" order by equip_no";
//		sql +=  "select room_id    as roomId, --机房id\n" +
//				"       equip_no   as deviceName, --设备名称\n" + 
//				"       net_type   as networkType, --网络类型\n" + 
//				"       manu_no    as deviceVender, --设备厂家\n" + 
//				"       equip_type as deviceType --设备类型\n" + 
//				"  from "+AndroidPropertiesUtils.getValue("zhzy_room_equipment")+" zh,\n" + 
//				"       Pnr_Res_Config                 con,\n" + 
//				"       pnr_inspect_plan_res           r\n" + 
//				" where zh.room_id = con.sub_res_id\n" + 
//				"   and con.id = r.res_cfg_id\n" + 
//				"   and r.id = ?\n" + 
//				" order by equip_no";
		sql += ") temp1 where rownum <=?) temp2 where temp2.num >? ";
		System.out.println("------查询接入机房-机房设备 列表sql="+sql);
		paramList.add(planResId);
		paramList.add((pageIndex+1)*pageSize);
		paramList.add(pageIndex*pageSize);
		Object[] args = paramList.toArray();
		List<EquipmentRoomItem> r = new ArrayList<EquipmentRoomItem>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql, args);
		for (Map map : list) {
			EquipmentRoomItem model = new EquipmentRoomItem();
			if (map.get("roomId") != null && !"".equals(map.get("roomId").toString())) {
				model.setRoomId(map.get("roomId").toString());
			}
			if (map.get("deviceName") != null && !"".equals(map.get("deviceName").toString())) {
				model.setDeviceName(map.get("deviceName").toString());
			}
			if (map.get("networkType") != null && !"".equals(map.get("networkType").toString())) {
				model.setNetworkType(map.get("networkType").toString());
			}
			if (map.get("deviceVender") != null && !"".equals(map.get("deviceVender").toString())) {
				model.setDeviceVender(map.get("deviceVender").toString());
			}
			if (map.get("deviceType") != null && !"".equals(map.get("deviceType").toString())) {
				model.setDeviceType(map.get("deviceType").toString());
			}
			r.add(model);
		}
		return r;
	}
	
	/**
	 *   查询接入机房-机房设备 总条数
	 	 * @author WANGJUN
	 	 * @title: getAccessRoomEquipmentRoomCount
	 	 * @date Oct 13, 2016 9:55:32 AM
	 	 * @param planResId
	 	 * @return int
	 */
	public int getAccessRoomEquipmentRoomCount(String planResId){
		int total = 0;
		List<Object> paramList = new ArrayList<Object>();
		String sql ="select count(room_id) as total\n" +
					"  from "+AndroidPropertiesUtils.getValue("zhzy_room_equipment")+" zh,\n" + 
					"       Pnr_Res_Config                 con,\n" + 
					"       pnr_inspect_plan_res           r\n" + 
					" where zh.room_id = con.sub_res_id\n" + 
					"   and con.id = r.res_cfg_id\n" + 
					"   and r.id = ?\n" + 
					"   and not exists (select 1\n" + 
					"          from tozhzy_room_equipment a\n" + 
					"         where zh.room_id = a.room_id\n" + 
					"           and zh.equip_no = a.equ_name)\n";
		System.out.println("------查询接入机房-机房设备 总条数sql="+sql);
		paramList.add(planResId);
		Object[] args = paramList.toArray();
		try{
			total = this.getJdbcTemplate().queryForInt(sql,args);
		}catch(Exception e){
			e.printStackTrace();
		}
		return total;
	}
	
	/**
	 *   接入机房 机房设备 新增、修改、删除
	 	 * @author WANGJUN
	 	 * @title: updateAccessRoomEquipmentRoom
	 	 * @date Oct 14, 2016 9:55:16 AM
	 	 * @param resId
	 	 * @param roomId
	 	 * @param deviceName
	 	 * @param networkType
	 	 * @param deviceVender
	 	 * @param deviceType
	 	 * @param operateType
	 	 * @return int
	 */
	public int updateAccessRoomEquipmentRoom(String resId,String roomId,String deviceName,String networkType,String deviceVender,String deviceType,String operateType){		
		int rows = 0;
		List<Object> paramList = new ArrayList<Object>();
		String sql = "";
		if("delete".equals(operateType) || "alter".equals(operateType)){
			sql ="insert into tozhzy_room_equipment" +
				"  (room_id, equ_name, net_type, vendor, equ_type, operate_type)" + 
				"values" + 
				"  (?, ?, ?, ?, ?, ?)";

			paramList.add(roomId);
			paramList.add(deviceName);
			paramList.add(networkType);
			paramList.add(deviceVender);
			paramList.add(deviceType);
			paramList.add(operateType);
		}else if("add".equals(operateType)){
			sql =
				"insert into tozhzy_room_equipment" +
				"  (room_id, equ_name, net_type, vendor, equ_type, operate_type)" + 
				"values" + 
				"  ((select sub_res_id" + 
				"     from Pnr_Res_Config" + 
				"    where id =" + 
				"          (select res_cfg_id from pnr_inspect_plan_res r where r.id = ?))," + 
				"   ?," + 
				"   ?," + 
				"   ?," + 
				"   ?," + 
				"   ?)";

			paramList.add(resId);
			paramList.add(deviceName);
			paramList.add(networkType);
			paramList.add(deviceVender);
			paramList.add(deviceType);
			paramList.add(operateType);
		}else{
			return rows;
		}
		System.out.println("------接入机房 机房设备 新增、修改、删除的sql="+sql);
		Object[] args = paramList.toArray();
		rows  = this.getJdbcTemplate().update(sql,args);
		return rows;
	}
	
	
	/**
	 *   查询接入机房-链接设备 列表
	 	 * @author WANGJUN
	 	 * @title: getAccessRoomEquipmentRoomList
	 	 * @date Oct 13, 2016 9:55:23 AM
	 	 * @param planResId
	 	 * @param pageIndex
	 	 * @param pageSize
	 	 * @return List<WellsOpticalCableItem>
	 */
	public List<LinkEquipmentItem> getShelfList(String planResId,int pageIndex,int pageSize){
		List<Object> paramList = new ArrayList<Object>();
		String sql = "select temp2.* from (select temp1.*, rownum num from (";
		sql +=  "select room_id    as roomId, --机房id\n" +
				"       shelf_id   as shelfId, --机架id\n" + 
				"       shelf_no   as shelfNo, --机架名称\n" + 
				"       shelf_type as shelfType --机架类型\n" + 
				"  from "+AndroidPropertiesUtils.getValue("zhzy_room_shelf")+" zh,\n" + 
				"       Pnr_Res_Config                 con,\n" + 
				"       pnr_inspect_plan_res           r\n" + 
				" where zh.room_id = con.sub_res_id\n" + 
				"   and con.id = r.res_cfg_id\n" + 
				"   and r.id = ?\n" + 
				" order by shelf_id";
		sql += ") temp1 where rownum <=?) temp2 where temp2.num >? ";
		System.out.println("------查询接入机房-链接设备 列表sql="+sql);
		paramList.add(planResId);
		paramList.add((pageIndex+1)*pageSize);
		paramList.add(pageIndex*pageSize);
		Object[] args = paramList.toArray();
		List<LinkEquipmentItem> r = new ArrayList<LinkEquipmentItem>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql, args);
		for (Map map : list) {
			LinkEquipmentItem model = new LinkEquipmentItem();
			if (map.get("roomId") != null && !"".equals(map.get("roomId").toString())) {
				model.setRoomId(map.get("roomId").toString());
			}
			if (map.get("shelfId") != null && !"".equals(map.get("shelfId").toString())) {
				model.setRackId(map.get("shelfId").toString());
			}
			if (map.get("shelfNo") != null && !"".equals(map.get("shelfNo").toString())) {
				model.setRackName(map.get("shelfNo").toString());
			}
			if (map.get("shelfType") != null && !"".equals(map.get("shelfType").toString())) {
				model.setRackType(map.get("shelfType").toString());
			}
			r.add(model);
		}
		return r;
	}
	
	/**
	 *   查询接入机房-链接设备 总条数
	 	 * @author WANGJUN
	 	 * @title: getAccessRoomEquipmentRoomCount
	 	 * @date Oct 13, 2016 9:55:32 AM
	 	 * @param planResId
	 	 * @return int
	 */
	public int getShelfCount(String planResId){
		int total = 0;
		List<Object> paramList = new ArrayList<Object>();
		String sql ="select count(room_id) as total\n" +
					"  from "+AndroidPropertiesUtils.getValue("zhzy_room_shelf")+" zh,\n" + 
					"       Pnr_Res_Config                 con,\n" + 
					"       pnr_inspect_plan_res           r\n" + 
					" where zh.room_id = con.sub_res_id\n" + 
					"   and con.id = r.res_cfg_id\n" + 
					"   and r.id = ?\n" + 
					" order by shelf_id";
		System.out.println("------查询接入机房-链接设备 总条数sql="+sql);
		paramList.add(planResId);
		Object[] args = paramList.toArray();
		try{
			total = this.getJdbcTemplate().queryForInt(sql,args);
		}catch(Exception e){
			e.printStackTrace();
		}
		return total;
	}
	
	/**
	 *   查询接入机房-链接设备 端子占用列表
	 	 * @author WANGJUN
	 	 * @title: getAccessRoomEquipmentRoomList
	 	 * @date Oct 13, 2016 9:55:23 AM
	 	 * @param planResId
	 	 * @param pageIndex
	 	 * @param pageSize
	 	 * @return List<WellsOpticalCableItem>
	 */
	public List<LinkEquipmentTerminalItem> getRoomPortList(String shelf_id,int pageIndex,int pageSize){
		List<Object> paramList = new ArrayList<Object>();
		String sql = "select temp2.* from (select temp1.*, rownum num from (";
		sql+=
			"select shelf_id         as shelfId, --机架id\n" +
			"       shelf_no         as shelfNo, --机架名称\n" + 
			"       extrenity_no     as portId, --端子ID\n" + 
			"       EXTRENITY_XH     as port, --端子\n" + 
			"       EXTRENITY_STATUS as status, --端子状态\n" + 
			"       FCIRCUIT_NO      as userInfo --占用者信息\n" + 
			"  from "+AndroidPropertiesUtils.getValue("zhzy_room_port")+" zh\n" + 
			" where shelf_id = ?\n" + 
			"   and not exists (select 1\n" + 
			"          from tozhzy_room_port a\n" + 
			"         where zh.shelf_id = a.shelfid\n" + 
			"           and zh. extrenity_no = a.port_id)\n" + 
			" order by EXTRENITY_XH";
//		sql +=  "select shelf_id   as shelfId, --机架id\n" +
//				"       shelf_no   as shelfNo, --机架名称\n" + 
//				"       extrenity_no   as portId, --端子ID\n" + 
//				"       EXTRENITY_XH as port ,--端子\n" + 
//				"       EXTRENITY_STATUS as status ,--端子状态\n" + 
//				"       FCIRCUIT_NO as userInfo --占用者信息\n" + 
//				"  from " +
//				 AndroidPropertiesUtils.getValue("zhzy_room_port") +
//				//"zhzy_room_equipment\n" + 
//				"  where shelf_id = ?\n" + 
//				" order by  EXTRENITY_XH ";
		sql += ") temp1 where rownum <=?) temp2 where temp2.num >? ";
		System.out.println("------查询接入机房-链接设备 端子占用列表sql="+sql);
		paramList.add(shelf_id);
		paramList.add((pageIndex+1)*pageSize);
		paramList.add(pageIndex*pageSize);
		Object[] args = paramList.toArray();
		List<LinkEquipmentTerminalItem> r = new ArrayList<LinkEquipmentTerminalItem>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql, args);
		for (Map map : list) {
			LinkEquipmentTerminalItem model = new LinkEquipmentTerminalItem();
			if (map.get("roomId") != null && !"".equals(map.get("roomId").toString())) {
				model.setRoomId(map.get("roomId").toString());
			}
			if (map.get("shelfId") != null && !"".equals(map.get("shelfId").toString())) {
				model.setRackId(map.get("shelfId").toString());
			}
			
			if (map.get("portId") != null && !"".equals(map.get("portId").toString())) {
				model.setPortId(map.get("portId").toString());
			}
			
			if (map.get("port") != null && !"".equals(map.get("port").toString())) {
				model.setLinkTerminalLabel(map.get("port").toString());
			}
			if (map.get("status") != null && !"".equals(map.get("status").toString())) {
				model.setStatus(map.get("status").toString());
			}
			if (map.get("userInfo") != null && !"".equals(map.get("userInfo").toString())) {
				model.setLinkOccupantInfor(map.get("userInfo").toString());
			}
			r.add(model);
		}
		return r;
	}
	
	/**
	 *   查询接入机房-链接设备 端子占用总数
	 	 * @author WANGJUN
	 	 * @title: getAccessRoomEquipmentRoomCount
	 	 * @date Oct 13, 2016 9:55:32 AM
	 	 * @param planResId
	 	 * @return int
	 */
	public int getRoomPortCount(String planResId){
		int total = 0;
		List<Object> paramList = new ArrayList<Object>();
		String sql ="select count(1)" + 
				"  from " +
				 AndroidPropertiesUtils.getValue("zhzy_room_port") +" zh"+
				//"zhzy_room_equipment\n" + 
				" where shelf_id = ?\n" + 
				"   and not exists (select 1\n" + 
				"          from tozhzy_room_port a\n" + 
				"         where zh.shelf_id = a.shelfid\n" + 
				"           and zh. extrenity_no = a.port_id)\n" ; 
					
		System.out.println("------查询接入机房-链接设备端子占用 总条数sql="+sql);
		paramList.add(planResId);
		Object[] args = paramList.toArray();
		try{
			total = this.getJdbcTemplate().queryForInt(sql,args);
		}catch(Exception e){
			e.printStackTrace();
		}
		return total;
	}
	
	/**
	 * 更新接入机房 链接设备 端子占用 
	 * @param id
	 * @param cableName
	 * @param pipeHoleInfor
	 * @param operateType
	 * @return
	 */
	public int updateRoomShelfPort(String shelfId ,String port,String status,String userInfo,String operateType,String portId){		
		int rows = 0;
		List<Object> paramList = new ArrayList<Object>();
		String sql = "";
		if("delete".equals(operateType) || "alter".equals(operateType)){
			sql = "insert into tozhzy_room_port(shelfId,port,userInfo,operate_type,port_id) values (?,?,?,?,?)";
//			sql = "insert into tozhzy_guangjiaoxiang(gjxid,module,port,status,userInfo,operateType) values(?,?,?,?,?)";
			paramList.add(shelfId);
			paramList.add(port);
			paramList.add(userInfo);
			paramList.add(operateType);
			paramList.add(portId);
		}else{
			return rows;
		}
		System.out.println("------更新光交箱链接设备 端子占用 sql="+sql);
		Object[] args = paramList.toArray();
		rows  = this.getJdbcTemplate().update(sql,args);
		return rows;
	}
	
	
	
	/**
	 *   查询接入机房-链接设备 进出缆列表
	 	 * @author WANGJUN
	 	 * @title: getAccessRoomEquipmentRoomList
	 	 * @date Oct 13, 2016 9:55:23 AM
	 	 * @param planResId
	 	 * @param pageIndex
	 	 * @param pageSize
	 	 * @return List<WellsOpticalCableItem>
	 */
	public List<LinkEquipmentOpticalCableItem> getRoomCabelList(String shelf_id,int pageIndex,int pageSize){
		List<Object> paramList = new ArrayList<Object>();
		String sql = "select temp2.* from (select temp1.*, rownum num from (";
		sql +=  "select " +
				"       extrenity_no     as extrenityNo, --光缆标识\n" + 
				"       ofiber_id        as ofiberid, --光缆id\n" + 
				"       ofiber_no        as ofiberno, --光缆编号\n" + 
				"       extrenity_xh     as extrenityXh, --光缆标号\n" + 
				"       core_num         as corenum, --纤芯数\n" + 
				"       extrenity_status as extrenityStatus --占用状态\n"+
				"  from " +
				 AndroidPropertiesUtils.getValue("zhzy_room_cabel") +" zh"+
				//"zhzy_room_equipment\n" + 
				"  where shelf_id = ?\n" + 	
				"  and not exists (select 1\n" +
				"          from tozhzy_room_cable a\n" + 
				"         where zh.shelf_id = a.shelfid\n" + 
				"           and zh.extrenity_no = a.extrenity_no)"+
				" order by   OFIBER_NO ";
		sql += ") temp1 where rownum <=?) temp2 where temp2.num >? ";
		System.out.println("------查询接入机房-链接设备 进出缆列表sql="+sql);
		paramList.add(shelf_id);
		paramList.add((pageIndex+1)*pageSize);
		paramList.add(pageIndex*pageSize);
		Object[] args = paramList.toArray();
		List<LinkEquipmentOpticalCableItem> r = new ArrayList<LinkEquipmentOpticalCableItem>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql, args);
		for (Map map : list) {
			LinkEquipmentOpticalCableItem model = new LinkEquipmentOpticalCableItem();
			if (map.get("ofiberId") != null && !"".equals(map.get("ofiberId").toString())) {
				model.setLinkCableId(map.get("ofiberId").toString());
			}
			if (map.get("ofiberNo") != null && !"".equals(map.get("ofiberNo").toString())) {
				model.setLinkCableName(map.get("ofiberNo").toString());
			}
			if (map.get("coreNum") != null && !"".equals(map.get("coreNum").toString())) {
				model.setLinkCoreNumber(map.get("coreNum").toString());
			}
			if (map.get("extrenityNo") != null && !"".equals(map.get("extrenityNo").toString())) {//光缆标识
				model.setExtrenityNo(map.get("extrenityNo").toString());
			}
			if (map.get("extrenityXh") != null && !"".equals(map.get("extrenityXh").toString())) {//光缆标号
				model.setExtrenityXh(map.get("extrenityXh").toString());
			}
			if (map.get("extrenityStatus") != null && !"".equals(map.get("extrenityStatus").toString())) {//占用状态
				model.setExtrenityStatus(map.get("extrenityStatus").toString());
			}
			r.add(model);
		}
		return r;
	}
	
	/**
	 *   查询接入机房-链接设备 进出缆列表总数
	 	 * @author WANGJUN
	 	 * @title: getAccessRoomEquipmentRoomCount
	 	 * @date Oct 13, 2016 9:55:32 AM
	 	 * @param planResId
	 	 * @return int
	 */
	public int getRoomCabelCount(String planResId){
		int total = 0;
		List<Object> paramList = new ArrayList<Object>();
		String sql ="select count(1)" + 
				"  from " +
				 AndroidPropertiesUtils.getValue("zhzy_room_cabel") +" zh"+
				//"zhzy_room_equipment\n" + 
				"  where shelf_id = ?\n" +
				"  and not exists (select 1\n" +
				"          from tozhzy_room_cable a\n" + 
				"         where zh.shelf_id = a.shelfid\n" + 
				"           and zh.extrenity_no = a.extrenity_no)";
		System.out.println("------查询接入机房-链接设备进出缆 总条数sql="+sql);
		paramList.add(planResId);
		Object[] args = paramList.toArray();
		try{
			total = this.getJdbcTemplate().queryForInt(sql,args);
		}catch(Exception e){
			e.printStackTrace();
		}
		return total;
	}
	
	/**
	 * 更新接入机房 链接设备 进出缆
	 * @param id
	 * @param cableName
	 * @param pipeHoleInfor
	 * @param operateType
	 * @return
	 */
	public int updateRoomShelfCable(String shelfId ,String ofiberId,String ofiberNo,String num,String operateType,String extrenityNo,String extrenityXh,String extrenityStatus){		
		int rows = 0;
		List<Object> paramList = new ArrayList<Object>();
		String sql = "";
		if("delete".equals(operateType) || "alter".equals(operateType)||"add".equals(operateType)){
			sql = "insert into tozhzy_room_cable(shelfId,ofiberId,ofiberNo,num,operate_type,extrenity_no,extrenity_xh,extrenity_status) values (?,?,?,?,?,?,?,?)";
//			sql = "insert into tozhzy_guangjiaoxiang(gjxid,module,port,status,userInfo,operateType) values(?,?,?,?,?)";
			paramList.add(shelfId);
			paramList.add(ofiberId);
			paramList.add(ofiberNo);
			paramList.add(num);
			paramList.add(operateType);
			paramList.add(extrenityNo);
			paramList.add(extrenityXh);
			paramList.add(extrenityStatus);
		}else{
			return rows;
		}
		System.out.println("------更新链接设备 进出缆sql="+sql);
		Object[] args = paramList.toArray();
		rows  = this.getJdbcTemplate().update(sql,args);
		return rows;
	}
	
	/**
	 *   查询光缆网 电杆 进出缆列表
	 	 * @author WANGJUN
	 	 * @title: getAccessRoomEquipmentRoomList
	 	 * @date Oct 13, 2016 9:55:23 AM
	 	 * @param planResId
	 	 * @param pageIndex
	 	 * @param pageSize
	 	 * @return List<WellsOpticalCableItem>
	 */
	public List<DianganCabel> getDianganCabelList(String shelf_id,int pageIndex,int pageSize){
		List<Object> paramList = new ArrayList<Object>();
		String sql = "select temp2.* from (select temp1.*, rownum num from (";
		sql +=  "select id,bar_id   as barId, --机架id\n" +
				"       ofiber_no   as ofiberNo, --光缆编号\n" + 
				"       CORE_NUM as coreNum  ,--纤芯数\n" + 
				"       ofiber_type as ofiberType  --光缆类型\n" + 
				"  from " +
				 AndroidPropertiesUtils.getValue("zhzy_diangan") +
				//"zhzy_room_equipment\n" + 
				"  where bar_id = \n" + 
				"       (select sub_res_id\n" + 
				"          from Pnr_Res_Config\n" + 
				"         where id =\n" + 
				"               (select res_cfg_id from pnr_inspect_plan_res r where r.id = ?)) and nvl(operate_type, 'add') != 'delete'" +
				" order by OFIBER_NO ";
		sql += ") temp1 where rownum <=?) temp2 where temp2.num >? ";
		System.out.println("------查询光缆网 电杆 进出缆列表 sql="+sql);
		paramList.add(shelf_id);
		paramList.add((pageIndex+1)*pageSize);
		paramList.add(pageIndex*pageSize);
		Object[] args = paramList.toArray();
		List<DianganCabel> r = new ArrayList<DianganCabel>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql, args);
		for (Map map : list) {
			DianganCabel model = new DianganCabel();
			if (map.get("id") != null && !"".equals(map.get("id").toString())) {
				model.setId(map.get("id").toString());
			}
			if (map.get("barId") != null && !"".equals(map.get("barId").toString())) {
				model.setBarId(map.get("barId").toString());
			}
			if (map.get("ofiberNo") != null && !"".equals(map.get("ofiberNo").toString())) {
				model.setOfiberNo(map.get("ofiberNo").toString());
			}
			if (map.get("coreNum") != null && !"".equals(map.get("coreNum").toString())) {
				model.setNum(map.get("coreNum").toString());
			}
			if (map.get("ofiberType") != null && !"".equals(map.get("ofiberType").toString())) {
				model.setOfiberType(map.get("ofiberType").toString());
			}
			r.add(model);
		}
		return r;
	}
	
	/**
	 *      查询光缆网 电杆 进出缆列表总数
	 	 * @author WANGJUN
	 	 * @title: getAccessRoomEquipmentRoomCount
	 	 * @date Oct 13, 2016 9:55:32 AM
	 	 * @param planResId
	 	 * @return int
	 */
	public int getDianganCabelCount(String planResId){
		int total = 0;
		List<Object> paramList = new ArrayList<Object>();
		String sql ="select count(1)" + 
				"  from " +
				 AndroidPropertiesUtils.getValue("zhzy_diangan") +
				//"zhzy_room_equipment\n" + 
				"  where bar_id = \n" +
		"       (select sub_res_id\n" + 
		"          from Pnr_Res_Config\n" + 
		"         where id =\n" + 
		"               (select res_cfg_id from pnr_inspect_plan_res r where r.id = ?)) and nvl(operate_type, 'add') != 'delete'" ;
					
		System.out.println("------ 查询光缆网 电杆 进出缆列表总数sql="+sql);
		paramList.add(planResId);
		Object[] args = paramList.toArray();
		try{
			total = this.getJdbcTemplate().queryForInt(sql,args);
		}catch(Exception e){
			e.printStackTrace();
		}
		return total;
	}
	
	/**
	 * 更新电杆 进出缆
	 * @param id
	 * @param cableName
	 * @param pipeHoleInfor
	 * @param operateType
	 * @return
	 */
	public int updateDianganCable(String barId ,String planId,String ofiberNo,String num,String ofiberType,String operateType,String id){		
		int rows = 0;
		List<Object> paramList = new ArrayList<Object>();
		String sql = "";
		if("delete".equals(operateType)){
			sql = "update zhzy_diangan set operate_type = ?,operate_time = sysdate where id = ?";
			paramList.add(operateType);
			paramList.add(id);
		}else if("alter".equals(operateType)){
			sql ="update zhzy_diangan" +
			 "   set core_num = ?, ofiber_type = ?, operate_type = ?,operate_time = sysdate" + 
			 " where id = ?";
			paramList.add(num);
			paramList.add(ofiberType);
			paramList.add(operateType);
			paramList.add(id);
		}else if("add".equals(operateType)){
			sql = "insert into zhzy_diangan(bar_id,ofiber_no,CORE_NUM,ofiber_type,operate_type,operate_time) values ((select sub_res_id from Pnr_Res_Config where id = (select res_cfg_id from pnr_inspect_plan_res r   where r.id = ?)),?,?,?,?,sysdate)";
			paramList.add(planId);
			paramList.add(ofiberNo);
			paramList.add(num);
			paramList.add(ofiberType);
			paramList.add(operateType);
		}else{
			return rows;
		}

		System.out.println("------更新电杆 进出缆sql="+sql);
		Object[] args = paramList.toArray();
		rows  = this.getJdbcTemplate().update(sql,args);
		return rows;
	}
	
	/**
	 * 	 室分小区数
	 	 * @author WANGJUN
	 	 * @title: getCellCount
	 	 * @date Nov 1, 2016 9:15:57 AM
	 	 * @param planResId
	 	 * @param networkType
	 	 * @return int
	 */
	public int getCellCount(String planResId,String networkType){
		int total = 0;
		List<Object> paramList = new ArrayList<Object>();
		String sql ="select count(ne_code) as total\n" +
					"  from indoor\n" + 
					" where net_type = ?\n" + 
					"   and substr(ne_code, 0, instr(ne_code, '-', 1, 3) - 1) =\n" + 
					"       (select sub_res_id\n" + 
					"          from Pnr_Res_Config\n" + 
					"         where id = (select res_cfg_id\n" + 
					"                       from pnr_inspect_plan_res r\n" + 
					"                      where r.id = ?))\n";
		System.out.println("------室分小区数sql="+sql);
		paramList.add(networkType);
		paramList.add(planResId);
		Object[] args = paramList.toArray();
		try{
			total = this.getJdbcTemplate().queryForInt(sql,args);
		}catch(Exception e){
			e.printStackTrace();
		}
		return total;
	}
	
	/**
	 * 	室分小区集合
	 	 * @author WANGJUN
	 	 * @title: getCellList
	 	 * @date Nov 1, 2016 9:16:07 AM
	 	 * @param planResId
	 	 * @param networkType
	 	 * @param pageIndex
	 	 * @param pageSize
	 	 * @return List<CellItem>
	 */
	public List<CellItem> getCellList(String planResId,String networkType,int pageIndex,int pageSize){
		List<Object> paramList = new ArrayList<Object>();
		String sql = "select temp2.* from (select temp1.*, rownum num from (";
		sql +=  "select indoor_name as siteName, --基站名称,\n" +
				"       substr(ne_code, 0, instr(ne_code, '-', 1, 3) - 1) as siteId, --基站id,\n" + 
				"       ne_code as neCode, --网元编码,\n" + 
				"       cellname -- 小区名称\n" + 
				"  from indoor\n" + 
				" where net_type = ?\n" + 
				"   and substr(ne_code, 0, instr(ne_code, '-', 1, 3) - 1) =\n" + 
				"       (select sub_res_id\n" + 
				"          from Pnr_Res_Config\n" + 
				"         where id = (select res_cfg_id\n" + 
				"                       from pnr_inspect_plan_res r\n" + 
				"                      where r.id = ?))\n" + 
				" order by ne_code";
		sql += ") temp1 where rownum <=?) temp2 where temp2.num >? ";
		System.out.println("------室分小区集合sql="+sql);
		paramList.add(networkType);
		paramList.add(planResId);
		paramList.add((pageIndex+1)*pageSize);
		paramList.add(pageIndex*pageSize);
		Object[] args = paramList.toArray();
		List<CellItem> r = new ArrayList<CellItem>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql, args);
		for (Map map : list) {
			CellItem model = new CellItem();
			if (map.get("siteName") != null && !"".equals(map.get("siteName").toString())) {
				model.setSiteName(map.get("siteName").toString());
			}
			if (map.get("siteId") != null && !"".equals(map.get("siteId").toString())) {
				model.setSiteId(map.get("siteId").toString());
			}
			if (map.get("neCode") != null && !"".equals(map.get("neCode").toString())) {
				model.setNeCode(map.get("neCode").toString());
			}
			if (map.get("cellname") != null && !"".equals(map.get("cellname").toString())) {
				model.setCellName(map.get("cellname").toString());
			}
			r.add(model);
		}
		return r;
	}
	
	/**
	 * 	 室分小区 核查 添加
	 	 * @author WANGJUN
	 	 * @title: updateCell
	 	 * @date Nov 1, 2016 2:53:57 PM
	 	 * @param planResId
	 	 * @param siteName
	 	 * @param cellName
	 	 * @param neCode
	 	 * @param networkType
	 	 * @param isCovered
	 	 * @param cellInfor
	 	 * @param operateType
	 	 * @return int
	 */
	public int updateCell(String planResId,String siteName,String cellName,String neCode,String networkType,String isCovered,String cellInfor,String operateType){		
		int rows = 0;
		List<Object> paramList = new ArrayList<Object>();
		String tableName = AndroidPropertiesUtils.getValue("backup_indoor");
		if(tableName == null || "".equals(tableName)){
			tableName = "backup_indoor";
		}
		String sql ="insert into " +tableName+
					"(plan_res_id," + 
					"   indoor_name," + 
					"   cell_name," + 
					"   ne_code," + 
					"   is_cover," + 
					"   net_type," + 
					"   remark,operate_type)" + 
					" values" + 
					"  (?, ?, ?, ?, ?, ?,?,?)";
		
		paramList.add(planResId);
		paramList.add(siteName);
		paramList.add(cellName);
		paramList.add(neCode);
		paramList.add(isCovered);
		paramList.add(networkType);
		paramList.add(cellInfor);
		paramList.add(operateType);
		
		System.out.println("------室分小区 核查 添加sql="+sql);
		Object[] args = paramList.toArray();
		rows  = this.getJdbcTemplate().update(sql,args);
		return rows;
	}
	
	/**
	 *   查询光交箱端子模块下的端子总数
	 	 * @author WANGJUN
	 	 * @title: getGjxTerminalCount
	 	 * @date Nov 15, 2016 10:41:09 AM
	 	 * @param gjxid
	 	 * @param module
	 	 * @return int
	 */
	public int getGjxTerminalCount(String gjxid,String module){
		int total = 0;
		List<Object> paramList = new ArrayList<Object>();
		String sql ="select count(zh.fmodule_no) as total\n" +
					"  from "+AndroidPropertiesUtils.getValue("gjx_duanzi")+" zh\n" + 
					" where zh.fiber_node_id = ?\n" + 
					"   and zh.fmodule_no = ?\n" + 
					"   and not exists (select 1\n" + 
					"          from tozhzy_guangjiaoxiang a\n" + 
					"         where zh.fiber_node_id = a.fiber_node_id\n" + 
					"           and zh.fmodule_no = a.fmodule_no\n" + 
					"           and zh.fport_no = a.fport_no)";
		
		System.out.println("------查询光交箱端子模块下的端子总数sql="+sql);
		paramList.add(gjxid);
		paramList.add(module);
		Object[] args = paramList.toArray();
		try{
			total = this.getJdbcTemplate().queryForInt(sql,args);
		}catch(Exception e){
			e.printStackTrace();
		}
		return total;
	}
	
	/**
	 *   查询光交箱端子模块下的端子明细
	 	 * @author WANGJUN
	 	 * @title: getGjxTerminalList
	 	 * @date Nov 15, 2016 10:43:06 AM
	 	 * @param gjxid
	 	 * @param modulepageIndex
	 	 * @param pageIndex
	 	 * @param pageSize
	 	 * @return List<PortOpticalCableItem>
	 */
	public List<PortOpticalCableItem> getGjxTerminalList(String gjxid,String module,int pageIndex,int pageSize){
		List<Object> paramList = new ArrayList<Object>();
		String sql = "select temp2.* from (select temp1.*, rownum num from (";
		sql+=   "select FIBER_NODE_ID gjxid,\n" +
				"       fmodule_no    module,\n" + 
				"       fport_no      port,\n" + 
				"       fport_status  status,\n" + 
				"       fcircuit_no   userInfo\n" + 
				"  from "+AndroidPropertiesUtils.getValue("gjx_duanzi")+" zh\n" + 
				" where zh.fiber_node_id = ?\n" + 
				"   and zh.fmodule_no = ?\n" + 
				"   and not exists (select 1\n" + 
				"          from tozhzy_guangjiaoxiang a\n" + 
				"         where zh.fiber_node_id = a.fiber_node_id\n" + 
				"           and zh.fmodule_no = a.fmodule_no\n" + 
				"           and zh.fport_no = a.fport_no)\n" + 
				" order by fmodule_no, fport_no";
		 sql += ") temp1 where rownum <=?) temp2 where temp2.num >? ";
			System.out.println("-----查询光交箱端子模块下的端子明细sql="+sql);
			paramList.add(gjxid);
			paramList.add(module);
			paramList.add((pageIndex+1)*pageSize);
			paramList.add(pageIndex*pageSize);
		Object[] args = paramList.toArray();
		List<PortOpticalCableItem> r = new ArrayList<PortOpticalCableItem>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql, args);
		for (Map map : list) {
			PortOpticalCableItem model = new PortOpticalCableItem();
			if (map.get("gjxid") != null && !"".equals(map.get("gjxid").toString())) {
				model.setGjxId(map.get("gjxid").toString());
			}
			if (map.get("module") != null && !"".equals(map.get("module").toString())) {
				model.setModule(map.get("module").toString());
			}
			if (map.get("port") != null && !"".equals(map.get("port").toString())) {
				model.setPort(map.get("port").toString());
			}
			if (map.get("status") != null && !"".equals(map.get("status").toString())) {
				model.setStatus(map.get("status").toString());
			}
			if (map.get("userInfo") != null && !"".equals(map.get("userInfo").toString())) {
				model.setUserInfo(map.get("userInfo").toString());
			}
			r.add(model);
		}
		return r;
	}
	
	/**
	 *   查询机房设备名称是否存在（资源表）
	 	 * @author WANGJUN
	 	 * @title: getEquipmentRoomByDeviceNameCount
	 	 * @date Nov 16, 2016 9:45:25 AM
	 	 * @param deviceName
	 	 * @return int
	 */
	public int getEquipmentRoomByDeviceNameCount(String resId,String deviceName){
		int total = -1;
		List<Object> paramList = new ArrayList<Object>();
		String sql = 
			"select count(zh.room_id) as total\n" +
			"  from "+AndroidPropertiesUtils.getValue("zhzy_room_equipment")+" zh\n" + 
			" where zh.equip_no = ? and zh.room_id = "+
			" (select sub_res_id\n" +
			"           from Pnr_Res_Config\n" + 
			"            where id =\n" + 
			"                  (select res_cfg_id from pnr_inspect_plan_res r where r.id = ? ))";
		System.out.println("------deviceName="+deviceName);
		System.out.println("------resId="+resId);
		System.out.println("------查询机房设备名称是否存在（资源表）sql="+sql);
		paramList.add(deviceName);
		paramList.add(resId);
		Object[] args = paramList.toArray();
		try{
			total = this.getJdbcTemplate().queryForInt(sql,args);
		}catch(Exception e){
			e.printStackTrace();
		}
		return total;
	}
	/**
	 *   查询机房设备名称是否存在（tozhzy_room_equipment）
	 	 * @author WANGJUN
	 	 * @title: getEquipmentRoomByDeviceNameCount
	 	 * @date Nov 16, 2016 9:45:25 AM
	 	 * @param deviceName
	 	 * @return int
	 */
	public int getToZhzyRoomEquipmentByDeviceNameCount(String resId,String deviceName){
		int total = -1;
		List<Object> paramList = new ArrayList<Object>();
		String sql = 
			"select count(zh.room_id) as total\n" +
			"  from tozhzy_room_equipment zh\n" + 
			" where zh.equ_name = ? and zh.room_id = "+
			" (select sub_res_id\n" +
			"           from Pnr_Res_Config\n" + 
			"            where id =\n" + 
			"                  (select res_cfg_id from pnr_inspect_plan_res r where r.id = ? ))";
		System.out.println("------deviceName="+deviceName);
		System.out.println("------resId="+resId);
		System.out.println("------查询机房设备名称是否存在（tozhzy_room_equipment）sql="+sql);
		paramList.add(deviceName);
		paramList.add(resId);
		Object[] args = paramList.toArray();
		try{
			total = this.getJdbcTemplate().queryForInt(sql,args);
		}catch(Exception e){
			e.printStackTrace();
		}
		return total;
	}
	
	/**
	 *   查询光交接箱进出缆是否存在(资源表)
	 	 * @author WANGJUN
	 	 * @title: getGjxCableOpticalCableBykeyCount
	 	 * @date Nov 17, 2016 10:29:26 AM
	 	 * @param resId
	 	 * @param ofiberName
	 	 * @param coreSequence
	 	 * @return int
	 */
	public int getGjxCableOpticalCableBykeyCount(String resId,String ofiberName,String coreSequence){
		int total = -1;
		List<Object> paramList = new ArrayList<Object>();
		String sql = 
			"select count(zh.fiber_node_id) as total\n" +
			"  from "+AndroidPropertiesUtils.getValue("gjx_jingguolan")+" zh\n" + 
			" where zh.fiber_node_id =\n" + 
			"       (select sub_res_id\n" + 
			"          from Pnr_Res_Config\n" + 
			"         where id =\n" + 
			"               (select res_cfg_id from pnr_inspect_plan_res r where r.id = ?))\n" + 
			"   and zh.ofiber_no = ?\n" + 
			"   and zh.core_sequence = ?";

		//AndroidPropertiesUtils.getValue("gjx_jingguolan")
		
//		String sql = 
//			"select count(zh.room_id) as total\n" +
//			"  from "+AndroidPropertiesUtils.getValue("zhzy_room_equipment")+" zh\n" + 
//			" where zh.equip_no = ? and zh.room_id = "+
//			" (select sub_res_id\n" +
//			"           from Pnr_Res_Config\n" + 
//			"            where id =\n" + 
//			"                  (select res_cfg_id from pnr_inspect_plan_res r where r.id = ? ))";
		System.out.println("------查询光交接箱进出缆是否存在(资源表)sql="+sql);
		System.out.println("------resId="+resId);
		System.out.println("------ofiberName="+ofiberName);
		System.out.println("------coreSequence="+coreSequence);
		paramList.add(resId);
		paramList.add(ofiberName);
		paramList.add(coreSequence);
		Object[] args = paramList.toArray();
		try{
			total = this.getJdbcTemplate().queryForInt(sql,args);
		}catch(Exception e){
			e.printStackTrace();
		}
		return total;
		
		
		
	}
	
	/**
	 *   查询光交接箱进出缆是否存在（tozhzy_gjx_Cable）
	 	 * @author WANGJUN
	 	 * @title: getToZhzyGjxCableBykeyCount
	 	 * @date Nov 17, 2016 10:29:29 AM
	 	 * @param resId
	 	 * @param ofiberName
	 	 * @param coreSequence
	 	 * @return int
	 */
	public int getToZhzyGjxCableBykeyCount(String resId,String ofiberName,String coreSequence){
		int total = -1;
		List<Object> paramList = new ArrayList<Object>();
		String sql = 
			"select count(zh.fiber_node_id) as total\n" +
			"  from tozhzy_gjx_Cable zh\n" + 
			" where zh.fiber_node_id =\n" + 
			"       (select sub_res_id\n" + 
			"          from Pnr_Res_Config\n" + 
			"         where id =\n" + 
			"               (select res_cfg_id from pnr_inspect_plan_res r where r.id = ?))\n" + 
			"   and zh.ofiber_name = ?\n" + 
			"   and zh.core_sequence = ?";


		//tozhzy_gjx_Cable
		
		
//		String sql = 
//			"select count(zh.room_id) as total\n" +
//			"  from tozhzy_room_equipment zh\n" + 
//			" where zh.equ_name = ? and zh.room_id = "+
//			" (select sub_res_id\n" +
//			"           from Pnr_Res_Config\n" + 
//			"            where id =\n" + 
//			"                  (select res_cfg_id from pnr_inspect_plan_res r where r.id = ? ))";
		System.out.println("------查询光交接箱进出缆是否存在（tozhzy_gjx_Cable）sql="+sql);
		System.out.println("------resId="+resId);
		System.out.println("------ofiberName="+ofiberName);
		System.out.println("------coreSequence="+coreSequence);
		paramList.add(resId);
		paramList.add(ofiberName);
		paramList.add(coreSequence);
		Object[] args = paramList.toArray();
		try{
			total = this.getJdbcTemplate().queryForInt(sql,args);
		}catch(Exception e){
			e.printStackTrace();
		}
		return total;
		
	}
}
