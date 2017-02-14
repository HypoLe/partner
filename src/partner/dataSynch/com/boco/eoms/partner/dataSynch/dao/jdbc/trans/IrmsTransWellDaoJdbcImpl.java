package com.boco.eoms.partner.dataSynch.dao.jdbc.trans;

import java.sql.PreparedStatement;
import java.util.Map;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.partner.dataSynch.dao.AbstractDataSynchDaoJdbc;


/**
 * 类说明：网络资源--传输线路--人手井:人手井DaoJdbcImpl实现类
 * 创建人： zhangkeqi
 * 创建时间：2012-11-16 23:11:36
 */
public class IrmsTransWellDaoJdbcImpl extends AbstractDataSynchDaoJdbc {
		/**
		 * 批量插入语句
		 */
		public String getBatchInsertSql(String table){
			return "insert into "+table+"_irms_trans_well("+
						"id,"+
						"name,"+
						"related_area,"+
						"well_mode,"+
						"well_type,"+
						"ownership,"+
						"purpose,"+
						"well_real_type,"+
						"longitude,"+
						"latitude,"+
						"use_unit,"+
						"owner_unit,"+
						"remark,"+
						"create_time,"+
						"related_area_id,"+
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
			/*人手井名称，作为业务主键，命名要求唯一。[例]重庆渝中区石桥铺-两路口管道SL-10号井*/
			ps.setString(2,StaticMethod.nullObject2String(data.get("name")));
			/*关联到【空间资源】-【区域】表-【区域名称】*/
			ps.setString(3,StaticMethod.nullObject2String(data.get("related_area")));
			/*枚举值：局前、直通、三通、四通等,默认为直通*/
			ps.setString(4,StaticMethod.nullObject2String(data.get("well_mode")));
			/*枚举值：引上、单井、双井等*/
			ps.setString(5,StaticMethod.nullObject2String(data.get("well_type")));
			/*枚举值：自建、合建、共建、附挂附穿、租用、购买、置换*/
			ps.setString(6,StaticMethod.nullObject2String(data.get("ownership")));
			/*枚举值：自用、出租、共享,默认为自用*/
			ps.setString(7,StaticMethod.nullObject2String(data.get("purpose")));
			/*枚举值：人井、手井*/
			ps.setString(8,StaticMethod.nullObject2String(data.get("well_real_type")));
			/*经度。例：123.123456*/
			ps.setString(9,StaticMethod.nullObject2String(data.get("longitude")));
			/*纬度。例：34.123456*/
			ps.setString(10,StaticMethod.nullObject2String(data.get("latitude")));
			/*使用单位。例：中国移动。*/
			ps.setString(11,StaticMethod.nullObject2String(data.get("use_unit")));
			/*所有权人名称。例：中国电信。*/
			ps.setString(12,StaticMethod.nullObject2String(data.get("owner_unit")));
			/*备注*/
			ps.setString(13,StaticMethod.nullObject2String(data.get("remark")));
			/*创建时间*/
			ps.setString(14,StaticMethod.nullObject2String(data.get("create_time")));
			/*所属区域ID*/
			ps.setString(15,StaticMethod.nullObject2String(data.get("related_area_id")));
			/*所属巡检点主键id*/
			ps.setString(16,StaticMethod.nullObject2String(data.get("inspect_id")));
			/*所属巡检点名称*/
			ps.setString(17,StaticMethod.nullObject2String(data.get("inspect_name")));
			
			ps.addBatch();
			
			return ps;
		}
}