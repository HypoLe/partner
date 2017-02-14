package com.boco.eoms.partner.dataSynch.dao.jdbc.space;

import java.sql.PreparedStatement;
import java.util.Map;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.partner.dataSynch.dao.AbstractDataSynchDaoJdbc;


/**
 * 类说明：网络资源--空间资源--区域:区域DaoJdbcImpl实现类
 * 创建人： zhangkeqi
 * 创建时间：2012-11-16 23:11:36
 */
public class IrmsSpaceAreaDaoJdbcImpl extends AbstractDataSynchDaoJdbc {
		/**
		 * 批量插入语句
		 */
		public String getBatchInsertSql(String table){
			return "insert into "+table+"_irms_space_area("+
						"id,"+
						"area_name,"+
						"abbreviation,"+
						"spellabbreviation,"+
						"area_alias,"+
						"area_code,"+
						"related_space,"+
						"area_type,"+
						"is_capital,"+
						"remark,"+
						"create_time,"+
						"related_space_id,"+
 						"inspect_id,"+
 						"inspect_name"+
					") values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?,?)";
		}
		
		/**
		 * 添加批量插入语句
		 */
		public PreparedStatement addPsBatch(PreparedStatement ps,Map<String,Object> data) throws Exception{
			/*主键*/
			ps.setString(1,StaticMethod.nullObject2String(data.get("id")));
			/*区域名称*/
			ps.setString(2,StaticMethod.nullObject2String(data.get("area_name")));
			/*区域名称中文缩写*/
			ps.setString(3,StaticMethod.nullObject2String(data.get("abbreviation")));
			/*拼音缩写*/
			ps.setString(4,StaticMethod.nullObject2String(data.get("spellabbreviation")));
			/*别名*/
			ps.setString(5,StaticMethod.nullObject2String(data.get("area_alias")));
			/*长途区号*/
			ps.setString(6,StaticMethod.nullObject2String(data.get("area_code")));
			/*所属区域名称*/
			ps.setString(7,StaticMethod.nullObject2String(data.get("related_space")));
			/*地区类型  枚举值：全国、省、市、区县、乡镇*/
			ps.setString(8,StaticMethod.nullObject2String(data.get("area_type")));
			/*是否省会  枚举值：是、否*/
			ps.setString(9,StaticMethod.nullObject2String(data.get("is_capital")));
			/*备注*/
			ps.setString(10,StaticMethod.nullObject2String(data.get("remark")));
			/*创建时间*/
			ps.setString(11,StaticMethod.nullObject2String(data.get("create_time")));
			/*所属区域ID*/
			ps.setString(12,StaticMethod.nullObject2String(data.get("related_space_id")));
			/*所属巡检点主键id*/
			ps.setString(13,StaticMethod.nullObject2String(data.get("inspect_id")));
			/*所属巡检点名称*/
			ps.setString(14,StaticMethod.nullObject2String(data.get("inspect_name")));
			
			ps.addBatch();
			
			return ps;
		}
}