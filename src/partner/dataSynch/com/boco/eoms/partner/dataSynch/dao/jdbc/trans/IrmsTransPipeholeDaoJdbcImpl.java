package com.boco.eoms.partner.dataSynch.dao.jdbc.trans;

import java.sql.PreparedStatement;
import java.util.Map;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.partner.dataSynch.dao.AbstractDataSynchDaoJdbc;


/**
 * 类说明：网络资源--传输线路--管孔:管孔DaoJdbcImpl实现类
 * 创建人： zhangkeqi
 * 创建时间：2012-11-16 23:11:36
 */
public class IrmsTransPipeholeDaoJdbcImpl extends AbstractDataSynchDaoJdbc {
		/**
		 * 批量插入语句
		 */
		public String getBatchInsertSql(String table){
			return "insert into "+table+"_irms_trans_pipehole("+
						"id,"+
						"pipe_hole_name,"+
						"related_pipeline_section,"+
						"cross_section_position,"+
						"status,"+
						"type,"+
						"material,"+
						"use,"+
						"use_unit,"+
						"owner,"+
						"remark,"+
						"create_time,"+
						"related_pipeline_section_id,"+
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
			/*管孔名称，作为业务主键，命名要求唯一。管道段截面的空心部分，可穿放若干条光、电缆或子管孔，一个管道段可以有多个管孔。管孔名称即管孔编号,按照从左到右,从下到上的顺序进行编号 [例] 5*/
			ps.setString(2,StaticMethod.nullObject2String(data.get("pipe_hole_name")));
			/*所属管道段，关联【管道段】表-【管道段名称】。*/
			ps.setString(3,StaticMethod.nullObject2String(data.get("related_pipeline_section")));
			/*标示出管孔在所属管道段截面图中的位置*/
			ps.setString(4,StaticMethod.nullObject2String(data.get("cross_section_position")));
			/*枚举值：未用,占用,预占,已坏.*/
			ps.setString(5,StaticMethod.nullObject2String(data.get("status")));
			/*枚举值：波纹管、梅花管，组合*/
			ps.setString(6,StaticMethod.nullObject2String(data.get("type")));
			/*枚举值：硅管、PVC、钢管等*/
			ps.setString(7,StaticMethod.nullObject2String(data.get("material")));
			/*管孔用途,枚举值：自用,出租,共享*/
			ps.setString(8,StaticMethod.nullObject2String(data.get("use")));
			/*管孔使用单位。例：中国移动。*/
			ps.setString(9,StaticMethod.nullObject2String(data.get("use_unit")));
			/*管孔所有权人。例：中国电信。*/
			ps.setString(10,StaticMethod.nullObject2String(data.get("owner")));
			/*备注*/
			ps.setString(11,StaticMethod.nullObject2String(data.get("remark")));
			/*创建时间*/
			ps.setString(12,StaticMethod.nullObject2String(data.get("create_time")));
			/*所属管道段ID*/
			ps.setString(13,StaticMethod.nullObject2String(data.get("related_pipeline_section_id")));
			/*所属巡检点主键id*/
			ps.setString(14,StaticMethod.nullObject2String(data.get("inspect_id")));
			/*所属巡检点名称*/
			ps.setString(15,StaticMethod.nullObject2String(data.get("inspect_name")));
			
			ps.addBatch();
			
			return ps;
		}
}