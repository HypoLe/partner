package com.boco.eoms.partner.dataSynch.dao.jdbc.trans;

import java.sql.PreparedStatement;
import java.util.Map;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.partner.dataSynch.dao.AbstractDataSynchDaoJdbc;


/**
 * 类说明：网络资源--传输线路--光交接箱:光交接箱DaoJdbcImpl实现类
 * 创建人： zhangkeqi
 * 创建时间：2012-11-16 23:11:36
 */
public class IrmsTransOdfDaoJdbcImpl extends AbstractDataSynchDaoJdbc {
		/**
		 * 批量插入语句
		 */
		public String getBatchInsertSql(String table){
			return "insert into "+table+"_irms_trans_odf("+
						"id,"+
						"odf_name,"+
						"odf_no,"+
						"related_district,"+
						"status,"+
						"vendor,"+
						"position,"+
						"longitude,"+
						"latitude,"+
						"type,"+
						"face_num,"+
						"each_face_row_num,"+
						"each_face_col_num,"+
						"property,"+
						"use,"+
						"use_unit,"+
						"owner,"+
						"capacity,"+
						"remark,"+
						"create_time,"+
						"related_distirct_id,"+
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
			/*光电交接箱名称，作为业务主键，命名要求唯一。在光缆/电缆之间起交接作用的设备，它由多个连接纤芯/电缆的端子组成。[例]重庆新牌坊/GJ001*/
			ps.setString(2,StaticMethod.nullObject2String(data.get("odf_name")));
			/*CQXPF/GJ001*/
			ps.setString(3,StaticMethod.nullObject2String(data.get("odf_no")));
			/*关联到【空间资源】-【区域】表-【区域名称】*/
			ps.setString(4,StaticMethod.nullObject2String(data.get("related_district")));
			/*设备当前所处状态，在下拉框中选择，包括：工程、在网、退网。*/
			ps.setString(5,StaticMethod.nullObject2String(data.get("status")));
			/*设备的供货厂家，在下拉框中选择。*/
			ps.setString(6,StaticMethod.nullObject2String(data.get("vendor")));
			/*光交接箱的具体位置*/
			ps.setString(7,StaticMethod.nullObject2String(data.get("position")));
			/*经度*/
			ps.setString(8,StaticMethod.nullObject2String(data.get("longitude")));
			/*纬度*/
			ps.setString(9,StaticMethod.nullObject2String(data.get("latitude")));
			/*型号*/
			ps.setString(10,StaticMethod.nullObject2String(data.get("type")));
			/*单面、双面*/
			ps.setString(11,StaticMethod.nullObject2String(data.get("face_num")));
			/*每面行数*/
			ps.setString(12,StaticMethod.nullObject2String(data.get("each_face_row_num")));
			/*每面列数*/
			ps.setString(13,StaticMethod.nullObject2String(data.get("each_face_col_num")));
			/*自建、合建、共建、附挂附穿、租用、购买、置换,默认为自建*/
			ps.setString(14,StaticMethod.nullObject2String(data.get("property")));
			/*枚举值：自用、出租、共享*/
			ps.setString(15,StaticMethod.nullObject2String(data.get("use")));
			/*使用单位*/
			ps.setString(16,StaticMethod.nullObject2String(data.get("use_unit")));
			/*所有权人*/
			ps.setString(17,StaticMethod.nullObject2String(data.get("owner")));
			/*容量*/
			ps.setString(18,StaticMethod.nullObject2String(data.get("capacity")));
			/*备注*/
			ps.setString(19,StaticMethod.nullObject2String(data.get("remark")));
			/*创建时间*/
			ps.setString(20,StaticMethod.nullObject2String(data.get("create_time")));
			/*所属区域ID*/
			ps.setString(21,StaticMethod.nullObject2String(data.get("related_distirct_id")));
			/*所属巡检点主键id*/
			ps.setString(22,StaticMethod.nullObject2String(data.get("inspect_id")));
			/*所属巡检点名称*/
			ps.setString(23,StaticMethod.nullObject2String(data.get("inspect_name")));
			
			ps.addBatch();
			
			return ps;
		}
}