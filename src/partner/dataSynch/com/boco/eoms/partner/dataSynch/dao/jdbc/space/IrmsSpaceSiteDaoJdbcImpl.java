package com.boco.eoms.partner.dataSynch.dao.jdbc.space;

import java.sql.PreparedStatement;
import java.util.Map;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.partner.dataSynch.dao.AbstractDataSynchDaoJdbc;


/**
 * 类说明：网络资源--空间资源--站点:站点DaoJdbcImpl实现类
 * 创建人： zhangkeqi
 * 创建时间：2012-11-16 23:11:36
 */
public class IrmsSpaceSiteDaoJdbcImpl extends AbstractDataSynchDaoJdbc {
		/**
		 * 批量插入语句
		 */
		public String getBatchInsertSql(String table){
			return "insert into "+table+"_irms_space_site("+
						"id,"+
						"site_name,"+
						"spellabbreviation,"+
						"site_name_alias,"+
						"sitecoding,"+
						"site_type,"+
						"location,"+
						"related_space,"+
						"longitude,"+
						"latitude,"+
						"service_level,"+
						"floor_maxnum,"+
						"remark,"+
						"create_time,"+
						"related_space_id,"+
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
			/*站点名称*/
			ps.setString(2,StaticMethod.nullObject2String(data.get("site_name")));
			/*站点名称拼音缩写*/
			ps.setString(3,StaticMethod.nullObject2String(data.get("spellabbreviation")));
			/*站点名称别名*/
			ps.setString(4,StaticMethod.nullObject2String(data.get("site_name_alias")));
			/*站点编号*/
			ps.setString(5,StaticMethod.nullObject2String(data.get("sitecoding")));
			/*站点类型  枚举值：核心生产楼、汇聚站点、接入站点、用户站点、其他*/
			ps.setString(6,StaticMethod.nullObject2String(data.get("site_type")));
			/*站点地址*/
			ps.setString(7,StaticMethod.nullObject2String(data.get("location")));
			/*所属区域*/
			ps.setString(8,StaticMethod.nullObject2String(data.get("related_space")));
			/*经度*/
			ps.setString(9,StaticMethod.nullObject2String(data.get("longitude")));
			/*纬度*/
			ps.setString(10,StaticMethod.nullObject2String(data.get("latitude")));
			/*传输等级*/
			ps.setString(11,StaticMethod.nullObject2String(data.get("service_level")));
			/*楼层数*/
			ps.setString(12,StaticMethod.nullObject2String(data.get("floor_maxnum")));
			/*备注*/
			ps.setString(13,StaticMethod.nullObject2String(data.get("remark")));
			/*创建时间*/
			ps.setString(14,StaticMethod.nullObject2String(data.get("create_time")));
			/*所属区域ID*/
			ps.setString(15,StaticMethod.nullObject2String(data.get("related_space_id")));
			/*所属巡检点主键id*/
			ps.setString(16,StaticMethod.nullObject2String(data.get("inspect_id")));
			/*所属巡检点名称*/
			ps.setString(17,StaticMethod.nullObject2String(data.get("inspect_name")));
			
			ps.addBatch();
			
			return ps;
		}
}