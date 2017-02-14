package com.boco.eoms.partner.dataSynch.dao.jdbc.trans;

import java.sql.PreparedStatement;
import java.util.Map;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.partner.dataSynch.dao.AbstractDataSynchDaoJdbc;


/**
 * 类说明：网络资源--传输线路--杆路段:杆路段DaoJdbcImpl实现类
 * 创建人： zhangkeqi
 * 创建时间：2012-11-16 23:11:36
 */
public class IrmsTransPolelinesecDaoJdbcImpl extends AbstractDataSynchDaoJdbc {
		/**
		 * 批量插入语句
		 */
		public String getBatchInsertSql(String table){
			return "insert into "+table+"_irms_trans_polelinesec("+
						"id,"+
						"poleline_section_name,"+
						"related_poleline,"+
						"related_start_pole_name,"+
						"related_end_pole_name,"+
						"property,"+
						"use_unit,"+
						"owner,"+
						"use,"+
						"section_length,"+
						"is_construct_shared,"+
						"construct_company,"+
						"is_shared,"+
						"shared_company,"+
						"remark,"+
						"create_time,"+
						"related_poleline_id,"+
						"related_start_pole_name_id,"+
						"related_end_pole_name_id,"+
 						"inspect_id,"+
 						"inspect_name"+
					") values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?,?)";
		}
		
		/**
		 * 添加批量插入语句
		 */
		public PreparedStatement addPsBatch(PreparedStatement ps,Map<String,Object> data) throws Exception{
			/*主键*/
			ps.setString(1,StaticMethod.nullObject2String(data.get("id")));
			/*杆路段名称，作为业务主键，命名要求唯一。杆路系统中相邻两个电杆之间的部分。[例]重庆沙坪坝区逸园-石板YS杆路YS-23号杆-YS-24号杆*/
			ps.setString(2,StaticMethod.nullObject2String(data.get("poleline_section_name")));
			/*杆路段所属的杆路。关联到【杆路】表-【杆路名称】。*/
			ps.setString(3,StaticMethod.nullObject2String(data.get("related_poleline")));
			/*起始点名称，关联【电杆】表-【电杆名称】。*/
			ps.setString(4,StaticMethod.nullObject2String(data.get("related_start_pole_name")));
			/*末端点名称，关联【电杆】表-【电杆名称】。*/
			ps.setString(5,StaticMethod.nullObject2String(data.get("related_end_pole_name")));
			/*枚举值：自建、合建、共建、附挂附穿、租用、购买、置换*/
			ps.setString(6,StaticMethod.nullObject2String(data.get("property")));
			/*该杆路段的使用单位。例：中国移动。*/
			ps.setString(7,StaticMethod.nullObject2String(data.get("use_unit")));
			/*杆路段的所有权人。例：中国移动。*/
			ps.setString(8,StaticMethod.nullObject2String(data.get("owner")));
			/*杆路段的用途，自用、出租、共享*/
			ps.setString(9,StaticMethod.nullObject2String(data.get("use")));
			/*线段长度，单位：米。例：1234345*/
			ps.setString(10,StaticMethod.nullObject2String(data.get("section_length")));
			/*枚举值：【是】或者【否】*/
			ps.setString(11,StaticMethod.nullObject2String(data.get("is_construct_shared")));
			/*如共建，逐一列出共建单位，用“，”分割。*/
			ps.setString(12,StaticMethod.nullObject2String(data.get("construct_company")));
			/*枚举值：【是】或者【否】*/
			ps.setString(13,StaticMethod.nullObject2String(data.get("is_shared")));
			/*如共享，逐一列出共享单位，用“，”分割。*/
			ps.setString(14,StaticMethod.nullObject2String(data.get("shared_company")));
			/*备注*/
			ps.setString(15,StaticMethod.nullObject2String(data.get("remark")));
			/*创建时间*/
			ps.setString(16,StaticMethod.nullObject2String(data.get("create_time")));
			/*所属杆路ID*/
			ps.setString(17,StaticMethod.nullObject2String(data.get("related_poleline_id")));
			/*起点电杆名称ID*/
			ps.setString(18,StaticMethod.nullObject2String(data.get("related_start_pole_name_id")));
			/*末端电杆名称ID*/
			ps.setString(19,StaticMethod.nullObject2String(data.get("related_end_pole_name_id")));
			/*所属巡检点主键id*/
			ps.setString(20,StaticMethod.nullObject2String(data.get("inspect_id")));
			/*所属巡检点名称*/
			ps.setString(21,StaticMethod.nullObject2String(data.get("inspect_name")));
			
			ps.addBatch();
			
			return ps;
		}
}