package com.boco.eoms.partner.dataSynch.dao.jdbc.wlan;

import java.sql.PreparedStatement;
import java.util.Map;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.partner.dataSynch.dao.AbstractDataSynchDaoJdbc;


/**
 * 类说明：网络资源--直放站室分及WLAN--AC:ACDaoJdbcImpl实现类
 * 创建人： zhangkeqi
 * 创建时间：2012-11-16 23:11:36
 */
public class IrmsWlanAcDaoJdbcImpl extends AbstractDataSynchDaoJdbc {
		/**
		 * 批量插入语句
		 */
		public String getBatchInsertSql(String table){
			return "insert into "+table+"_irms_wlan_ac("+
						"id,"+
						"related_district,"+
						"ac_name,"+
						"related_vendor,"+
						"hard_mode,"+
						"soft_version,"+
						"manage_ip_addr,"+
						"up_orig_dev_name,"+
						"up_dateinterface_s,"+
						"label_no,"+
						"insert_power_locat,"+
						"ne_working_state,"+
						"max_manage_ap_coun,"+
						"ac_name2,"+
						"nas_ip,"+
						"setup_time,"+
						"ap_count,"+
						"remark,"+
						"create_time,"+
						"related_district_id,"+
						"label_no_id,"+
 						"inspect_id,"+
 						"inspect_name"+
					") values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?,?)";
		}
		
		/**
		 * 添加批量插入语句
		 */
		public PreparedStatement addPsBatch(PreparedStatement ps,Map<String,Object> data) throws Exception{
			/*主键*/
			ps.setString(1,StaticMethod.nullObject2String(data.get("id")));
			/*地市*/
			ps.setString(2,StaticMethod.nullObject2String(data.get("related_district")));
			/*AC名称*/
			ps.setString(3,StaticMethod.nullObject2String(data.get("ac_name")));
			/*设备厂家*/
			ps.setString(4,StaticMethod.nullObject2String(data.get("related_vendor")));
			/*硬件型号*/
			ps.setString(5,StaticMethod.nullObject2String(data.get("hard_mode")));
			/*AC软件版本*/
			ps.setString(6,StaticMethod.nullObject2String(data.get("soft_version")));
			/*管理IP地址*/
			ps.setString(7,StaticMethod.nullObject2String(data.get("manage_ip_addr")));
			/*上行互联设备端口*/
			ps.setString(8,StaticMethod.nullObject2String(data.get("up_orig_dev_name")));
			/*上行数据接口线速转发速率总和*/
			ps.setString(9,StaticMethod.nullObject2String(data.get("up_dateinterface_s")));
			/*机柜编号*/
			ps.setString(10,StaticMethod.nullObject2String(data.get("label_no")));
			/*接入电源柜位置*/
			ps.setString(11,StaticMethod.nullObject2String(data.get("insert_power_locat")));
			/*工程状态*/
			ps.setString(12,StaticMethod.nullObject2String(data.get("ne_working_state")));
			/*设备最大管理AP数量*/
			ps.setString(13,StaticMethod.nullObject2String(data.get("max_manage_ap_coun")));
			/*AC_NAME*/
			ps.setString(14,StaticMethod.nullObject2String(data.get("ac_name2")));
			/*AC NAS_IP*/
			ps.setString(15,StaticMethod.nullObject2String(data.get("nas_ip")));
			/*入网时间*/
			ps.setString(16,StaticMethod.nullObject2String(data.get("setup_time")));
			/*实际管理AP数量*/
			ps.setString(17,StaticMethod.nullObject2String(data.get("ap_count")));
			/*备注*/
			ps.setString(18,StaticMethod.nullObject2String(data.get("remark")));
			/*创建时间*/
			ps.setString(19,StaticMethod.nullObject2String(data.get("create_time")));
			/*地市ID*/
			ps.setString(20,StaticMethod.nullObject2String(data.get("related_district_id")));
			/*机柜编号ID*/
			ps.setString(21,StaticMethod.nullObject2String(data.get("label_no_id")));
			/*所属巡检点主键id*/
			ps.setString(22,StaticMethod.nullObject2String(data.get("inspect_id")));
			/*所属巡检点名称*/
			ps.setString(23,StaticMethod.nullObject2String(data.get("inspect_name")));
			
			ps.addBatch();
			
			return ps;
		}
}