package com.boco.eoms.partner.dataSynch.dao.jdbc.group;

import java.sql.PreparedStatement;
import java.util.Map;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.partner.dataSynch.dao.AbstractDataSynchDaoJdbc;


/**
 * 类说明：网络资源--集客家客--客户开通业务信息表:客户开通业务信息表DaoJdbcImpl实现类
 * 创建人： zhangkeqi
 * 创建时间：2012-11-16 23:11:36
 */
public class IrmsGroupProductDaoJdbcImpl extends AbstractDataSynchDaoJdbc {
		/**
		 * 批量插入语句
		 */
		public String getBatchInsertSql(String table){
			return "insert into "+table+"_irms_group_product("+
						"id,"+
						"customer_name,"+
						"customer_no,"+
						"prod_type,"+
						"prod_code,"+
						"customer_addressa,"+
						"citya,"+
						"regiona,"+
						"customer_addressz,"+
						"cityz,"+
						"regionz,"+
						"customer_link_man,"+
						"customer_link_phon,"+
						"business_demand,"+
						"open_time,"+
						"busi_end_time,"+
						"service_level,"+
						"create_time,"+
						"remark,"+
						"customer_no_id,"+
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
			/*客户名称*/
			ps.setString(2,StaticMethod.nullObject2String(data.get("customer_name")));
			/*客户编号*/
			ps.setString(3,StaticMethod.nullObject2String(data.get("customer_no")));
			/*业务类型*/
			ps.setString(4,StaticMethod.nullObject2String(data.get("prod_type")));
			/*产品实例标识*/
			ps.setString(5,StaticMethod.nullObject2String(data.get("prod_code")));
			/*业务接入点地址A*/
			ps.setString(6,StaticMethod.nullObject2String(data.get("customer_addressa")));
			/*客户业务接入点A所属地市*/
			ps.setString(7,StaticMethod.nullObject2String(data.get("citya")));
			/*客户业务接入点A所属区县*/
			ps.setString(8,StaticMethod.nullObject2String(data.get("regiona")));
			/*业务接入点地址Z*/
			ps.setString(9,StaticMethod.nullObject2String(data.get("customer_addressz")));
			/*客户业务接入点Z所属地市*/
			ps.setString(10,StaticMethod.nullObject2String(data.get("cityz")));
			/*客户业务接入点Z所属区县*/
			ps.setString(11,StaticMethod.nullObject2String(data.get("regionz")));
			/*客户技术联系人*/
			ps.setString(12,StaticMethod.nullObject2String(data.get("customer_link_man")));
			/*客户技术联系人电话*/
			ps.setString(13,StaticMethod.nullObject2String(data.get("customer_link_phon")));
			/*业务描述*/
			ps.setString(14,StaticMethod.nullObject2String(data.get("business_demand")));
			/*业务开通日期*/
			ps.setString(15,StaticMethod.nullObject2String(data.get("open_time")));
			/*业务服务终止时间*/
			ps.setString(16,StaticMethod.nullObject2String(data.get("busi_end_time")));
			/*业务保障等级*/
			ps.setString(17,StaticMethod.nullObject2String(data.get("service_level")));
			/*创建时间*/
			ps.setString(18,StaticMethod.nullObject2String(data.get("create_time")));
			/*备注*/
			ps.setString(19,StaticMethod.nullObject2String(data.get("remark")));
			/*客户编号ID*/
			ps.setString(20,StaticMethod.nullObject2String(data.get("customer_no_id")));
			/*所属巡检点主键id*/
			ps.setString(21,StaticMethod.nullObject2String(data.get("inspect_id")));
			/*所属巡检点名称*/
			ps.setString(22,StaticMethod.nullObject2String(data.get("inspect_name")));
			
			ps.addBatch();
			
			return ps;
		}
}