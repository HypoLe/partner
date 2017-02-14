package com.boco.eoms.partner.dataSynch.dao.jdbc.trans;

import java.sql.PreparedStatement;
import java.util.Map;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.partner.dataSynch.dao.AbstractDataSynchDaoJdbc;


/**
 * 类说明：网络资源--传输线路--管道段:管道段DaoJdbcImpl实现类
 * 创建人： zhangkeqi
 * 创建时间：2012-11-16 23:11:36
 */
public class IrmsTransPipesecDaoJdbcImpl extends AbstractDataSynchDaoJdbc {
		/**
		 * 批量插入语句
		 */
		public String getBatchInsertSql(String table){
			return "insert into "+table+"_irms_trans_pipesec("+
						"id,"+
						"pipe_section_name,"+
						"start_well_name,"+
						"end_well_name,"+
						"stuff,"+
						"pore_count,"+
						"sub_pore_count,"+
						"related_pipe,"+
						"related_section,"+
						"property,"+
						"use_unit,"+
						"owner_unit,"+
						"section_length,"+
						"is_construct_share,"+
						"construct_company,"+
						"is_shared,"+
						"shared_company,"+
						"remark,"+
						"create_time,"+
						"start_well_name_id,"+
						"end_well_name_id,"+
						"related_pipe_id,"+
 						"inspect_id,"+
 						"inspect_name"+
					") values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?,?)";
		}
		
		/**
		 * 添加批量插入语句
		 */
		public PreparedStatement addPsBatch(PreparedStatement ps,Map<String,Object> data) throws Exception{
			/*主键*/
			ps.setString(1,StaticMethod.nullObject2String(data.get("id")));
			/*管道段名称，作为业务主键，命名要求唯一。管道段截面的空心部分，可穿放若干条光、电缆或子管孔，一个管道段可以有多个管孔。[例]重庆渝中区中山四路ZS-6号井-ZS-7号井*/
			ps.setString(2,StaticMethod.nullObject2String(data.get("pipe_section_name")));
			/*起始点名称，关联【人手井】表“人手井名称”*/
			ps.setString(3,StaticMethod.nullObject2String(data.get("start_well_name")));
			/*末端点名称，关联【人手井】表“人手井名称”*/
			ps.setString(4,StaticMethod.nullObject2String(data.get("end_well_name")));
			/*枚举值：波纹管、梅花管，组合*/
			ps.setString(5,StaticMethod.nullObject2String(data.get("stuff")));
			/*移动管孔数目。例：3*/
			ps.setString(6,StaticMethod.nullObject2String(data.get("pore_count")));
			/*移动子孔数目。例：3*/
			ps.setString(7,StaticMethod.nullObject2String(data.get("sub_pore_count")));
			/*关联到【管道】表-【管道名称】*/
			ps.setString(8,StaticMethod.nullObject2String(data.get("related_pipe")));
			/*承载光缆段，命名需规范以实现数据关联。*/
			ps.setString(9,StaticMethod.nullObject2String(data.get("related_section")));
			/*枚举值：自建、合建、共建、附挂附穿、租用、购买、置换*/
			ps.setString(10,StaticMethod.nullObject2String(data.get("property")));
			/*管道段的使用单位。例：中国移动。*/
			ps.setString(11,StaticMethod.nullObject2String(data.get("use_unit")));
			/*管道段的所有权人。例：中国电信。*/
			ps.setString(12,StaticMethod.nullObject2String(data.get("owner_unit")));
			/*管道段长度，单位：米。*/
			ps.setString(13,StaticMethod.nullObject2String(data.get("section_length")));
			/*枚举值：【是】或者【否】*/
			ps.setString(14,StaticMethod.nullObject2String(data.get("is_construct_share")));
			/*如共建，逐一列出共建单位，用“，”分割。*/
			ps.setString(15,StaticMethod.nullObject2String(data.get("construct_company")));
			/*枚举值：【是】或者【否】*/
			ps.setString(16,StaticMethod.nullObject2String(data.get("is_shared")));
			/*如共享，逐一列出共享单位，用“，”分割。*/
			ps.setString(17,StaticMethod.nullObject2String(data.get("shared_company")));
			/*备注*/
			ps.setString(18,StaticMethod.nullObject2String(data.get("remark")));
			/*创建时间*/
			ps.setString(19,StaticMethod.nullObject2String(data.get("create_time")));
			/*起始点人手井名称ID*/
			ps.setString(20,StaticMethod.nullObject2String(data.get("start_well_name_id")));
			/*末端点人手井名称ID*/
			ps.setString(21,StaticMethod.nullObject2String(data.get("end_well_name_id")));
			/*所属管道ID*/
			ps.setString(22,StaticMethod.nullObject2String(data.get("related_pipe_id")));
			/*所属巡检点主键id*/
			ps.setString(23,StaticMethod.nullObject2String(data.get("inspect_id")));
			/*所属巡检点名称*/
			ps.setString(24,StaticMethod.nullObject2String(data.get("inspect_name")));
			
			ps.addBatch();
			
			return ps;
		}
}