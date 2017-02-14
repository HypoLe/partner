package com.boco.eoms.partner.dataSynch.dao.jdbc.wlan;

import java.sql.PreparedStatement;
import java.util.Map;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.partner.dataSynch.dao.AbstractDataSynchDaoJdbc;


/**
 * 类说明：网络资源--直放站室分及WLAN--直放站:直放站DaoJdbcImpl实现类
 * 创建人： zhangkeqi
 * 创建时间：2012-11-16 23:11:36
 */
public class IrmsWlanRepeaterDaoJdbcImpl extends AbstractDataSynchDaoJdbc {
		/**
		 * 批量插入语句
		 */
		public String getBatchInsertSql(String table){
			return "insert into "+table+"_irms_wlan_repeater("+
						"id,"+
						"label_cn,"+
						"status,"+
						"nodetype,"+
						"related_cell_cuid,"+
						"coverage_type,"+
						"longitude,"+
						"latitude,"+
						"related_vendor,"+
						"signal_receivetype,"+
						"power_type,"+
						"manage_company,"+
						"remark,"+
						"create_time,"+
						"related_cell_cuid_id,"+
 						"inspect_id,"+
 						"inspect_name"+
					") values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?,?)";
		}
		
		/**
		 * 添加批量插入语句
		 */
		public PreparedStatement addPsBatch(PreparedStatement ps,Map<String,Object> data) throws Exception{
			/*主键*/
			ps.setString(1,StaticMethod.nullObject2String(data.get("id")));
			/*参照集团命名规范*/
			ps.setString(2,StaticMethod.nullObject2String(data.get("label_cn")));
			/*枚举值为现网、工程、空载、退网*/
			ps.setString(3,StaticMethod.nullObject2String(data.get("status")));
			/*1：光纤直放站，2：无线直放站3：其它*/
			ps.setString(4,StaticMethod.nullObject2String(data.get("nodetype")));
			/*填写信源小区，关联到【CELL】、【UTRANCELL】表中的网元名称*/
			ps.setString(5,StaticMethod.nullObject2String(data.get("related_cell_cuid")));
			/*1：室外站;2：室内站;3：室内分布;4：室外分布*/
			ps.setString(6,StaticMethod.nullObject2String(data.get("coverage_type")));
			/*小数点后保留5位*/
			ps.setString(7,StaticMethod.nullObject2String(data.get("longitude")));
			/*小数点后保留5位*/
			ps.setString(8,StaticMethod.nullObject2String(data.get("latitude")));
			/*设备提供商名称*/
			ps.setString(9,StaticMethod.nullObject2String(data.get("related_vendor")));
			/*1:选频;2:选带；3：宽频*/
			ps.setString(10,StaticMethod.nullObject2String(data.get("signal_receivetype")));
			/*1:太阳能;2:市电;3:风能*/
			ps.setString(11,StaticMethod.nullObject2String(data.get("power_type")));
			/*代维公司*/
			ps.setString(12,StaticMethod.nullObject2String(data.get("manage_company")));
			/*备注*/
			ps.setString(13,StaticMethod.nullObject2String(data.get("remark")));
			/*创建时间*/
			ps.setString(14,StaticMethod.nullObject2String(data.get("create_time")));
			/*所属小区ID*/
			ps.setString(15,StaticMethod.nullObject2String(data.get("related_cell_cuid_id")));
			/*所属巡检点主键id*/
			ps.setString(16,StaticMethod.nullObject2String(data.get("inspect_id")));
			/*所属巡检点名称*/
			ps.setString(17,StaticMethod.nullObject2String(data.get("inspect_name")));
			
			ps.addBatch();
			
			return ps;
		}
}