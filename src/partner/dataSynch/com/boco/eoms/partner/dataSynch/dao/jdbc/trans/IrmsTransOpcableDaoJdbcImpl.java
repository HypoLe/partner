package com.boco.eoms.partner.dataSynch.dao.jdbc.trans;

import java.sql.PreparedStatement;
import java.util.Map;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.partner.dataSynch.dao.AbstractDataSynchDaoJdbc;


/**
 * 类说明：网络资源--传输线路--光缆:光缆DaoJdbcImpl实现类
 * 创建人： zhangkeqi
 * 创建时间：2012-11-16 23:11:36
 */
public class IrmsTransOpcableDaoJdbcImpl extends AbstractDataSynchDaoJdbc {
		/**
		 * 批量插入语句
		 */
		public String getBatchInsertSql(String table){
			return "insert into "+table+"_irms_trans_opcable("+
						"id,"+
						"opcable_name,"+
						"opcable_status,"+
						"related_district,"+
						"project_name,"+
						"business_level,"+
						"property,"+
						"type,"+
						"use_unit,"+
						"completed_date,"+
						"owner,"+
						"remark,"+
						"create_time,"+
						"related_distirct_id,"+
 						"inspect_id,"+
 						"inspect_name"+
					") values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?,?)";
		}
		
		/**
		 * 添加批量插入语句
		 */
		public PreparedStatement addPsBatch(PreparedStatement ps,Map<String,Object> data) throws Exception{
			/*主键*/
			ps.setString(1,StaticMethod.nullObject2String(data.get("id")));
			/*光缆名称，作为业务主键，命名要求唯一。由多根光纤组成（如24芯、48芯），位于相邻局站之间的光缆连接部分，光缆段经过若干光交接点设备，若干光缆段构成光缆线路。[例]重庆江北区华新街-人才市场光缆*/
			ps.setString(2,StaticMethod.nullObject2String(data.get("opcable_name")));
			/*设施当前所处状态，枚举值：工程、在网、空载、退网。*/
			ps.setString(3,StaticMethod.nullObject2String(data.get("opcable_status")));
			/*关联到【空间资源】-【区域】表-【区域名称】*/
			ps.setString(4,StaticMethod.nullObject2String(data.get("related_district")));
			/*光缆对应工程名称*/
			ps.setString(5,StaticMethod.nullObject2String(data.get("project_name")));
			/*省际一干、省内二干、本地骨干、本地汇聚、本地接入、干线本地共用。按照最符合项原则，否则按照承载光路业务的最高级别。*/
			ps.setString(6,StaticMethod.nullObject2String(data.get("business_level")));
			/*自建、合建、共建、附挂附穿、租用、购买、置换*/
			ps.setString(7,StaticMethod.nullObject2String(data.get("property")));
			/*路缆、海缆等等*/
			ps.setString(8,StaticMethod.nullObject2String(data.get("type")));
			/*光缆使用单位*/
			ps.setString(9,StaticMethod.nullObject2String(data.get("use_unit")));
			/*竣工时间*/
			ps.setString(10,StaticMethod.nullObject2String(data.get("completed_date")));
			/*所有权人*/
			ps.setString(11,StaticMethod.nullObject2String(data.get("owner")));
			/*备注*/
			ps.setString(12,StaticMethod.nullObject2String(data.get("remark")));
			/*创建时间*/
			ps.setString(13,StaticMethod.nullObject2String(data.get("create_time")));
			/*所属区域ID*/
			ps.setString(14,StaticMethod.nullObject2String(data.get("related_distirct_id")));
			/*所属巡检点主键id*/
			ps.setString(15,StaticMethod.nullObject2String(data.get("inspect_id")));
			/*所属巡检点名称*/
			ps.setString(16,StaticMethod.nullObject2String(data.get("inspect_name")));
			
			ps.addBatch();
			
			return ps;
		}
}