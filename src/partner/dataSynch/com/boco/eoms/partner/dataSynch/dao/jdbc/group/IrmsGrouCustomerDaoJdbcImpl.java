package com.boco.eoms.partner.dataSynch.dao.jdbc.group;

import java.sql.PreparedStatement;
import java.util.Map;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.partner.dataSynch.dao.AbstractDataSynchDaoJdbc;


/**
 * 类说明：网络资源--集客家客--客户信息表:客户信息表DaoJdbcImpl实现类
 * 创建人： zhangkeqi
 * 创建时间：2012-11-16 23:11:36
 */
public class IrmsGrouCustomerDaoJdbcImpl extends AbstractDataSynchDaoJdbc {
		/**
		 * 批量插入语句
		 */
		public String getBatchInsertSql(String table){
			return "insert into "+table+"_irms_grou_customer("+
						"id,"+
						"customer_no,"+
						"customer_name,"+
						"customer_addr,"+
						"customer_level,"+
						"service_level,"+
						"industry,"+
						"city,"+
						"region,"+
						"linkman,"+
						"link_phone,"+
						"related_manager_cu,"+
						"related_manager_ph,"+
						"group_contact,"+
						"group_phone,"+
						"remark,"+
						"create_time,"+
 						"inspect_id,"+
 						"inspect_name"+
					") values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?,?)";
		}
		
		/**
		 * 添加批量插入语句
		 */
		public PreparedStatement addPsBatch(PreparedStatement ps,Map<String,Object> data) throws Exception{
			/*主键*/
			ps.setString(1,StaticMethod.nullObject2String(data.get("id")));
			/*由BOSS系统提供，用于业务侧与网络侧唯一标示一个客户。可作为T1.1/T1.2/T2.1～T2.5/T3.1关联的对象*/
			ps.setString(2,StaticMethod.nullObject2String(data.get("customer_no")));
			/*填写集团客户名称*/
			ps.setString(3,StaticMethod.nullObject2String(data.get("customer_name")));
			/*填写客户详细地址*/
			ps.setString(4,StaticMethod.nullObject2String(data.get("customer_addr")));
			/*选项：A类/B类/C类/D类*/
			ps.setString(5,StaticMethod.nullObject2String(data.get("customer_level")));
			/*选项：金、银、铜、标准*/
			ps.setString(6,StaticMethod.nullObject2String(data.get("service_level")));
			/*填写客户公司归属的行业名称*/
			ps.setString(7,StaticMethod.nullObject2String(data.get("industry")));
			/*填写对应的城市名称*/
			ps.setString(8,StaticMethod.nullObject2String(data.get("city")));
			/*填写客户所在地理区域（包括正式客户和潜在客户）*/
			ps.setString(9,StaticMethod.nullObject2String(data.get("region")));
			/*字符串（10）*/
			ps.setString(10,StaticMethod.nullObject2String(data.get("linkman")));
			/*字符串（20）*/
			ps.setString(11,StaticMethod.nullObject2String(data.get("link_phone")));
			/**/
			ps.setString(12,StaticMethod.nullObject2String(data.get("related_manager_cu")));
			/**/
			ps.setString(13,StaticMethod.nullObject2String(data.get("related_manager_ph")));
			/**/
			ps.setString(14,StaticMethod.nullObject2String(data.get("group_contact")));
			/**/
			ps.setString(15,StaticMethod.nullObject2String(data.get("group_phone")));
			/*备注*/
			ps.setString(16,StaticMethod.nullObject2String(data.get("remark")));
			/*创建时间*/
			ps.setString(17,StaticMethod.nullObject2String(data.get("create_time")));
			/*所属巡检点主键id*/
			ps.setString(18,StaticMethod.nullObject2String(data.get("inspect_id")));
			/*所属巡检点名称*/
			ps.setString(19,StaticMethod.nullObject2String(data.get("inspect_name")));
			
			ps.addBatch();
			
			return ps;
		}
}