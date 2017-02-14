package com.boco.eoms.partner.dataSynch.dao.jdbc.trans;

import java.sql.PreparedStatement;
import java.util.Map;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.partner.dataSynch.dao.AbstractDataSynchDaoJdbc;


/**
 * 类说明：网络资源--传输线路--直埋段:直埋段DaoJdbcImpl实现类
 * 创建人： zhangkeqi
 * 创建时间：2012-11-16 23:11:36
 */
public class IrmsTransDirburysecDaoJdbcImpl extends AbstractDataSynchDaoJdbc {
		/**
		 * 批量插入语句
		 */
		public String getBatchInsertSql(String table){
			return "insert into "+table+"_irms_trans_dirburysec("+
						"id,"+
						"dirbury_sec_name,"+
						"related_dirbury,"+
						"related_opcable_sec,"+
						"related_start_point_name,"+
						"related_end_point_name,"+
						"property,"+
						"use_unit,"+
						"owner,"+
						"use,"+
						"length,"+
						"is_construt_shared,"+
						"construct_company,"+
						"is_shared,"+
						"shared_unit,"+
						"remark,"+
						"create_time,"+
						"related_dirbury_id,"+
						"related_start_point_name_id,"+
						"related_end_point_name_id,"+
 						"inspect_id,"+
 						"inspect_name"+
					") values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?,?)";
		}
		
		/**
		 * 添加批量插入语句
		 */
		public PreparedStatement addPsBatch(PreparedStatement ps,Map<String,Object> data) throws Exception{
			/*主键*/
			ps.setString(1,StaticMethod.nullObject2String(data.get("id")));
			/*直埋段名称，作为业务主键，命名要求唯一。管线网络中将光缆或电缆直接埋于地下的部分。[例]重庆彭水-黔江PQ0145号标石-PQ0146号标石*/
			ps.setString(2,StaticMethod.nullObject2String(data.get("dirbury_sec_name")));
			/*关联【直埋】表-【直埋名称】。*/
			ps.setString(3,StaticMethod.nullObject2String(data.get("related_dirbury")));
			/*承载光缆段，命名需规范以实现数据关联。关联【光缆段】表“光缆段名称”。*/
			ps.setString(4,StaticMethod.nullObject2String(data.get("related_opcable_sec")));
			/*起始点名称，命名需规范以实现数据关联。关联【标石】表-【标石名称】。*/
			ps.setString(5,StaticMethod.nullObject2String(data.get("related_start_point_name")));
			/*末端点名称，命名需规范以实现数据关联。关联【标石】表-【标石名称】。*/
			ps.setString(6,StaticMethod.nullObject2String(data.get("related_end_point_name")));
			/*枚举值：自建、合建、共建、附挂附穿、租用、购买、置换*/
			ps.setString(7,StaticMethod.nullObject2String(data.get("property")));
			/*直埋段使用单位。例：中国移动。*/
			ps.setString(8,StaticMethod.nullObject2String(data.get("use_unit")));
			/*直埋段所有权人。例：中国电信。*/
			ps.setString(9,StaticMethod.nullObject2String(data.get("owner")));
			/*直埋段用途，枚举值：自用、出租、共享*/
			ps.setString(10,StaticMethod.nullObject2String(data.get("use")));
			/*线段长度，单位：米。例：123445 */
			ps.setString(11,StaticMethod.nullObject2String(data.get("length")));
			/*枚举值：【是】或者【否】*/
			ps.setString(12,StaticMethod.nullObject2String(data.get("is_construt_shared")));
			/*如共建，逐一列出共建单位，用“，”分割。*/
			ps.setString(13,StaticMethod.nullObject2String(data.get("construct_company")));
			/*枚举值：【是】或者【否】*/
			ps.setString(14,StaticMethod.nullObject2String(data.get("is_shared")));
			/*如共享，逐一列出共享单位，用“，”分割。*/
			ps.setString(15,StaticMethod.nullObject2String(data.get("shared_unit")));
			/*备注*/
			ps.setString(16,StaticMethod.nullObject2String(data.get("remark")));
			/*创建时间*/
			ps.setString(17,StaticMethod.nullObject2String(data.get("create_time")));
			/*所属直埋ID*/
			ps.setString(18,StaticMethod.nullObject2String(data.get("related_dirbury_id")));
			/*起点名称ID*/
			ps.setString(19,StaticMethod.nullObject2String(data.get("related_start_point_name_id")));
			/*末端名称ID*/
			ps.setString(20,StaticMethod.nullObject2String(data.get("related_end_point_name_id")));
			/*所属巡检点主键id*/
			ps.setString(21,StaticMethod.nullObject2String(data.get("inspect_id")));
			/*所属巡检点名称*/
			ps.setString(22,StaticMethod.nullObject2String(data.get("inspect_name")));
			
			ps.addBatch();
			
			return ps;
		}
}