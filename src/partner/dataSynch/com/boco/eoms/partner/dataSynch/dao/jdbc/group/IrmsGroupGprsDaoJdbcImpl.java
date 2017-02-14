package com.boco.eoms.partner.dataSynch.dao.jdbc.group;

import java.sql.PreparedStatement;
import java.util.Map;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.partner.dataSynch.dao.AbstractDataSynchDaoJdbc;


/**
 * 类说明：网络资源--集客家客--GPRS信息表:GPRS信息表DaoJdbcImpl实现类
 * 创建人： zhangkeqi
 * 创建时间：2012-11-16 23:11:36
 */
public class IrmsGroupGprsDaoJdbcImpl extends AbstractDataSynchDaoJdbc {
		/**
		 * 批量插入语句
		 */
		public String getBatchInsertSql(String table){
			return "insert into "+table+"_irms_group_gprs("+
						"id,"+
						"customer_name,"+
						"related_pro_instance_id,"+
						"apn_name,"+
						"related_apn_id,"+
						"business_bw,"+
						"business_open_limi,"+
						"business_hosting_way,"+
						"relative_pro_instance_id,"+
						"ip_allocation_mode,"+
						"use_ip_num,"+
						"is_both_ggsn,"+
						"tran_access_way,"+
						"enterprise_service,"+
						"label_cn1,"+
						"gre_address_name1,"+
						"gre_enterprise_add1,"+
						"gre_name1,"+
						"gre_tunnrl_key1,"+
						"label_cn2,"+
						"gre_address_name2,"+
						"gre_enterprise_add2,"+
						"gre_name2,"+
						"gre_tunnrl_key2,"+
						"apn_address_pool,"+
						"is_use_radius,"+
						"radius_ip_add,"+
						"is_terminal_visits,"+
						"circuit_name,"+
						"optical_circuit_name,"+
						"customer_access_date,"+
						"remark,"+
						"create_time,"+
						"related_pro_instance_id_id,"+
						"related_apn_id_id,"+
						"enterprise_service_id,"+
						"label_cn1_id,"+
						"label_cn2_id,"+
						"apn_address_pool_id,"+
						"circuit_name_id,"+
						"optical_circuit_name_id,"+
 						"inspect_id,"+
 						"inspect_name"+
					") values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?,?)";
		}
		
		/**
		 * 添加批量插入语句
		 */
		public PreparedStatement addPsBatch(PreparedStatement ps,Map<String,Object> data) throws Exception{
			/*主键*/
			ps.setString(1,StaticMethod.nullObject2String(data.get("id")));
			/*客户名称*/
			ps.setString(2,StaticMethod.nullObject2String(data.get("customer_name")));
			/*与【T1.1-客户信息表】的【客户编号】关联*/
			ps.setString(3,StaticMethod.nullObject2String(data.get("related_pro_instance_id")));
			/*APN名称*/
			ps.setString(4,StaticMethod.nullObject2String(data.get("apn_name")));
			/*与“核心网资源”专业的【APN】的【APN ID】关联*/
			ps.setString(5,StaticMethod.nullObject2String(data.get("related_apn_id")));
			/*为企业服务器接入GGSN时开通的专线速率*/
			ps.setString(6,StaticMethod.nullObject2String(data.get("business_bw")));
			/*全国，全省（如果是直辖市范围填写全省），本地*/
			ps.setString(7,StaticMethod.nullObject2String(data.get("business_open_limi")));
			/*选项：
1：移动数据（互联网)专线接入；
2：其它运营商互联网接入
3：移动传输专线接入
若选择2，则关联产品实例标识可为空*/
			ps.setString(8,StaticMethod.nullObject2String(data.get("business_hosting_way")));
			/*若业务承载方式选择为2，此字段可以不填写*/
			ps.setString(9,StaticMethod.nullObject2String(data.get("relative_pro_instance_id")));
			/*描述给客户的IP地址分配方式（静态/动态）*/
			ps.setString(10,StaticMethod.nullObject2String(data.get("ip_allocation_mode")));
			/*格式：设备名称-IP地址数量。例如：ZZGGSN03BNK-254;*/
			ps.setString(11,StaticMethod.nullObject2String(data.get("use_ip_num")));
			/*是/否
若选择是，则第二个GGSN的相关字段需要填写*/
			ps.setString(12,StaticMethod.nullObject2String(data.get("is_both_ggsn")));
			/*枚举值：SDH/PON/PTN/PDH/微波/裸纤*/
			ps.setString(13,StaticMethod.nullObject2String(data.get("tran_access_way")));
			/*若业务承载方式选择为1时，与相关产品实例标识关联的【客户业务应用IP地址】关联；
若业务承载方式选择为2时，手工填写，无跨专业关联*/
			ps.setString(14,StaticMethod.nullObject2String(data.get("enterprise_service")));
			/*与“GPRS”的【GPRS系统（物理资源）－GGSN】的【设备名称】关联*/
			ps.setString(15,StaticMethod.nullObject2String(data.get("label_cn1")));
			/*第一个的GGSN GRE选择的地址*/
			ps.setString(16,StaticMethod.nullObject2String(data.get("gre_address_name1")));
			/*GGSN与客户路由器之间的隧道IP信息*/
			ps.setString(17,StaticMethod.nullObject2String(data.get("gre_enterprise_add1")));
			/*GRE的名称*/
			ps.setString(18,StaticMethod.nullObject2String(data.get("gre_name1")));
			/*隧道key，手工填写*/
			ps.setString(19,StaticMethod.nullObject2String(data.get("gre_tunnrl_key1")));
			/*与“GPRS”的【GPRS系统（物理资源）－GGSN】的【设备名称】关联*/
			ps.setString(20,StaticMethod.nullObject2String(data.get("label_cn2")));
			/*GRE的GGSN本端地址，双GGSN时填写*/
			ps.setString(21,StaticMethod.nullObject2String(data.get("gre_address_name2")));
			/*GGSN企业端地址，根据上面的“GRE的企业端地址”在双GGSN时手工填写*/
			ps.setString(22,StaticMethod.nullObject2String(data.get("gre_enterprise_add2")));
			/*GRE的名称*/
			ps.setString(23,StaticMethod.nullObject2String(data.get("gre_name2")));
			/*隧道key，双GGSN时手工填写*/
			ps.setString(24,StaticMethod.nullObject2String(data.get("gre_tunnrl_key2")));
			/*与“GPRS”的【GPRS系统（逻辑资源）－IP地址信息】的【IP地址】关联*/
			ps.setString(25,StaticMethod.nullObject2String(data.get("apn_address_pool")));
			/*是，否*/
			ps.setString(26,StaticMethod.nullObject2String(data.get("is_use_radius")));
			/*RADIUS服务器地址*/
			ps.setString(27,StaticMethod.nullObject2String(data.get("radius_ip_add")));
			/*是，否*/
			ps.setString(28,StaticMethod.nullObject2String(data.get("is_terminal_visits")));
			/*与“传送网资源”（内线逻辑资源）的【传输电路】的【电路路名称】关联*/
			ps.setString(29,StaticMethod.nullObject2String(data.get("circuit_name")));
			/*与“传送网资源”（外线资源）的【光路】的【光路名称】关联*/
			ps.setString(30,StaticMethod.nullObject2String(data.get("optical_circuit_name")));
			/*光纤接入客户的日期：yyyy-MM-dd*/
			ps.setString(31,StaticMethod.nullObject2String(data.get("customer_access_date")));
			/*备注*/
			ps.setString(32,StaticMethod.nullObject2String(data.get("remark")));
			/*创建时间*/
			ps.setString(33,StaticMethod.nullObject2String(data.get("create_time")));
			/*产品实例标识ID*/
			ps.setString(34,StaticMethod.nullObject2String(data.get("related_pro_instance_id_id")));
			/*APN号码ID*/
			ps.setString(35,StaticMethod.nullObject2String(data.get("related_apn_id_id")));
			/*企业服务器IP地址ID*/
			ps.setString(36,StaticMethod.nullObject2String(data.get("enterprise_service_id")));
			/*第一个GGSN的名称ID*/
			ps.setString(37,StaticMethod.nullObject2String(data.get("label_cn1_id")));
			/*第二个GGSN的名称ID*/
			ps.setString(38,StaticMethod.nullObject2String(data.get("label_cn2_id")));
			/*终端IP地址池ID*/
			ps.setString(39,StaticMethod.nullObject2String(data.get("apn_address_pool_id")));
			/*电路名称ID*/
			ps.setString(40,StaticMethod.nullObject2String(data.get("circuit_name_id")));
			/*光路名称ID*/
			ps.setString(41,StaticMethod.nullObject2String(data.get("optical_circuit_name_id")));
			/*所属巡检点主键id*/
			ps.setString(42,StaticMethod.nullObject2String(data.get("inspect_id")));
			/*所属巡检点名称*/
			ps.setString(43,StaticMethod.nullObject2String(data.get("inspect_name")));
			
			ps.addBatch();
			
			return ps;
		}
}