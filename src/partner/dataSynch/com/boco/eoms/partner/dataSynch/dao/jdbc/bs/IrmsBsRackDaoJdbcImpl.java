package com.boco.eoms.partner.dataSynch.dao.jdbc.bs;

import java.sql.PreparedStatement;
import java.util.Map;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.partner.dataSynch.dao.AbstractDataSynchDaoJdbc;


/**
 * 类说明：网络资源--基站设备及配套--机柜:机柜DaoJdbcImpl实现类
 * 创建人： zhangkeqi
 * 创建时间：2012-11-16 23:11:36
 */
public class IrmsBsRackDaoJdbcImpl extends AbstractDataSynchDaoJdbc {
		/**
		 * 批量插入语句
		 */
		public String getBatchInsertSql(String table){
			return "insert into "+table+"_irms_bs_rack("+
						"id,"+
						"label_cn,"+
						"related_device_cui,"+
						"related_body,"+
						"vendor,"+
						"rack_type,"+
						"shelf_amount,"+
						"create_time,"+
						"remark,"+
						"related_device_cui_id,"+
						"related_body_id,"+
 						"inspect_id,"+
 						"inspect_name"+
					") values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?,?)";
		}
		
		/**
		 * 添加批量插入语句
		 */
		public PreparedStatement addPsBatch(PreparedStatement ps,Map<String,Object> data) throws Exception{
			/*主键*/
			ps.setString(1,StaticMethod.nullObject2String(data.get("id")));
			/*机柜名称，作为业务主键，命名要求唯一。*/
			ps.setString(2,StaticMethod.nullObject2String(data.get("label_cn")));
			/*关联属性，关联到【BTSSITE】或【NODEB】表网元名称,只对基站无线设备要求填写*/
			ps.setString(3,StaticMethod.nullObject2String(data.get("related_device_cui")));
			/*关联属性，关联到【空间资源】-【机架位置表】表-【机架编号】，要求核心生产楼无线专业设备机柜需要与其关联，基站机柜不需与此项关联，基站已与机房关联*/
			ps.setString(4,StaticMethod.nullObject2String(data.get("related_body")));
			/*枚举值：诺西、新邮通、阿尔卡特、摩托罗拉、中兴、华为、日海等*/
			ps.setString(5,StaticMethod.nullObject2String(data.get("vendor")));
			/*枚举值：BSC、RNC、BTS、NODEB*/
			ps.setString(6,StaticMethod.nullObject2String(data.get("rack_type")));
			/*数字，机框数*/
			ps.setString(7,StaticMethod.nullObject2String(data.get("shelf_amount")));
			/*创建时间*/
			ps.setString(8,StaticMethod.nullObject2String(data.get("create_time")));
			/*备注*/
			ps.setString(9,StaticMethod.nullObject2String(data.get("remark")));
			/*所属基站ID*/
			ps.setString(10,StaticMethod.nullObject2String(data.get("related_device_cui_id")));
			/*所属机架ID*/
			ps.setString(11,StaticMethod.nullObject2String(data.get("related_body_id")));
			/*所属巡检点主键id*/
			ps.setString(12,StaticMethod.nullObject2String(data.get("inspect_id")));
			/*所属巡检点名称*/
			ps.setString(13,StaticMethod.nullObject2String(data.get("inspect_name")));
			
			ps.addBatch();
			
			return ps;
		}
}