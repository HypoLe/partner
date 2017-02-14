package com.boco.eoms.partner.dataSynch.dao.jdbc.trans;

import java.sql.PreparedStatement;
import java.util.Map;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.partner.dataSynch.dao.AbstractDataSynchDaoJdbc;


/**
 * 类说明：网络资源--传输线路--电杆:电杆DaoJdbcImpl实现类
 * 创建人： zhangkeqi
 * 创建时间：2012-11-16 23:11:36
 */
public class IrmsTransPolesDaoJdbcImpl extends AbstractDataSynchDaoJdbc {
		/**
		 * 批量插入语句
		 */
		public String getBatchInsertSql(String table){
			return "insert into "+table+"_irms_trans_poles("+
						"id,"+
						"pole_name,"+
						"related_distirct,"+
						"pole_height,"+
						"pole_property,"+
						"pole_use,"+
						"pole_use_unit,"+
						"pole_owership,"+
						"pole_longitude,"+
						"pole_latitude,"+
						"remark,"+
						"create_time,"+
						"related_distirct_id,"+
 						"inspect_id,"+
 						"inspect_name"+
					") values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?,?)";
		}
		
		/**
		 * 添加批量插入语句
		 */
		public PreparedStatement addPsBatch(PreparedStatement ps,Map<String,Object> data) throws Exception{
			/*主键*/
			ps.setString(1,StaticMethod.nullObject2String(data.get("id")));
			/*电杆名称，作为业务主键，命名要求唯一。[例]重庆沙坪坝区逸园-石板杆路YS-57号杆*/
			ps.setString(2,StaticMethod.nullObject2String(data.get("pole_name")));
			/*关联到【空间资源】-【区域】表-【区域名称】*/
			ps.setString(3,StaticMethod.nullObject2String(data.get("related_distirct")));
			/*枚举：七米杆、八米杆、九米杆。*/
			ps.setString(4,StaticMethod.nullObject2String(data.get("pole_height")));
			/*枚举值：自建、合建、共建、附挂附穿、租用、购买、置换*/
			ps.setString(5,StaticMethod.nullObject2String(data.get("pole_property")));
			/*电杆的用途，自用、出租、共享*/
			ps.setString(6,StaticMethod.nullObject2String(data.get("pole_use")));
			/*使用单位。例：中国移动。*/
			ps.setString(7,StaticMethod.nullObject2String(data.get("pole_use_unit")));
			/*电杆所有权人。例：中国电信。*/
			ps.setString(8,StaticMethod.nullObject2String(data.get("pole_owership")));
			/*经度。例：123.123456*/
			ps.setString(9,StaticMethod.nullObject2String(data.get("pole_longitude")));
			/*纬度。例：34.123456*/
			ps.setString(10,StaticMethod.nullObject2String(data.get("pole_latitude")));
			/*备注*/
			ps.setString(11,StaticMethod.nullObject2String(data.get("remark")));
			/*创建时间*/
			ps.setString(12,StaticMethod.nullObject2String(data.get("create_time")));
			/*所属区域ID*/
			ps.setString(13,StaticMethod.nullObject2String(data.get("related_distirct_id")));
			/*所属巡检点主键id*/
			ps.setString(14,StaticMethod.nullObject2String(data.get("inspect_id")));
			/*所属巡检点名称*/
			ps.setString(15,StaticMethod.nullObject2String(data.get("inspect_name")));
			
			ps.addBatch();
			
			return ps;
		}
}