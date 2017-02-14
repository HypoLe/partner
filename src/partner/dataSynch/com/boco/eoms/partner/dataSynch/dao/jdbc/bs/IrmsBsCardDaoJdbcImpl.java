package com.boco.eoms.partner.dataSynch.dao.jdbc.bs;

import java.sql.PreparedStatement;
import java.util.Map;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.partner.dataSynch.dao.AbstractDataSynchDaoJdbc;


/**
 * 类说明：网络资源--基站设备及配套--板卡:板卡DaoJdbcImpl实现类
 * 创建人： zhangkeqi
 * 创建时间：2012-11-16 23:11:36
 */
public class IrmsBsCardDaoJdbcImpl extends AbstractDataSynchDaoJdbc {
		/**
		 * 批量插入语句
		 */
		public String getBatchInsertSql(String table){
			return "insert into "+table+"_irms_bs_card("+
						"id,"+
						"label_cn,"+
						"related_device_cui,"+
						"related_slot_cuid,"+
						"step,"+
						"project_no,"+
						"device_type,"+
						"function_type,"+
						"card_model,"+
						"property_no,"+
						"serial_no,"+
						"if_spare,"+
						"produce_time,"+
						"create_time,"+
						"remark,"+
						"related_device_cui_id,"+
						"related_slot_cuid_id,"+
 						"inspect_id,"+
 						"inspect_name"+
					") values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?,?)";
		}
		
		/**
		 * 添加批量插入语句
		 */
		public PreparedStatement addPsBatch(PreparedStatement ps,Map<String,Object> data) throws Exception{
			/*主键*/
			ps.setString(1,StaticMethod.nullObject2String(data.get("id")));
			/*按集团名称规范命名（信息包含基站侧载频、传输板和核心机房BSC 、RNC板卡）*/
			ps.setString(2,StaticMethod.nullObject2String(data.get("label_cn")));
			/*具体的所属设备名称，关联到【BSC】、【BTSSITE】、【RNC】、【NODEB】、【直放站】名称*/
			ps.setString(3,StaticMethod.nullObject2String(data.get("related_device_cui")));
			/*关联属性，关联到【机槽】表-【机槽名称】*/
			ps.setString(4,StaticMethod.nullObject2String(data.get("related_slot_cuid")));
			/*仓库,代维,待确认,工程部借用,其它,维修中心,现网*/
			ps.setString(5,StaticMethod.nullObject2String(data.get("step")));
			/*11扩,12扩等*/
			ps.setString(6,StaticMethod.nullObject2String(data.get("project_no")));
			/*手工,枚举值{BTSSITE/NODEB/BSC/RNC}*/
			ps.setString(7,StaticMethod.nullObject2String(data.get("device_type")));
			/*载频、合路器、主控板、腔体*/
			ps.setString(8,StaticMethod.nullObject2String(data.get("function_type")));
			/*3BK00797AA等*/
			ps.setString(9,StaticMethod.nullObject2String(data.get("card_model")));
			/*JS0167499*/
			ps.setString(10,StaticMethod.nullObject2String(data.get("property_no")));
			/*BS0011U05AW*/
			ps.setString(11,StaticMethod.nullObject2String(data.get("serial_no")));
			/*枚举{是,否}*/
			ps.setString(12,StaticMethod.nullObject2String(data.get("if_spare")));
			/*格式为：YYYY-MM-DD*/
			ps.setString(13,StaticMethod.nullObject2String(data.get("produce_time")));
			/*创建时间*/
			ps.setString(14,StaticMethod.nullObject2String(data.get("create_time")));
			/*备注*/
			ps.setString(15,StaticMethod.nullObject2String(data.get("remark")));
			/*所属设备ID*/
			ps.setString(16,StaticMethod.nullObject2String(data.get("related_device_cui_id")));
			/*Column所属槽位ID*/
			ps.setString(17,StaticMethod.nullObject2String(data.get("related_slot_cuid_id")));
			/*所属巡检点主键id*/
			ps.setString(18,StaticMethod.nullObject2String(data.get("inspect_id")));
			/*所属巡检点名称*/
			ps.setString(19,StaticMethod.nullObject2String(data.get("inspect_name")));
			
			ps.addBatch();
			
			return ps;
		}
}