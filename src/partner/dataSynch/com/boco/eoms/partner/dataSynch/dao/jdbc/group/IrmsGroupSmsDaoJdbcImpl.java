package com.boco.eoms.partner.dataSynch.dao.jdbc.group;

import java.sql.PreparedStatement;
import java.util.Map;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.partner.dataSynch.dao.AbstractDataSynchDaoJdbc;


/**
 * 类说明：网络资源--集客家客--短信信息表:短信信息表DaoJdbcImpl实现类
 * 创建人： zhangkeqi
 * 创建时间：2012-11-16 23:11:36
 */
public class IrmsGroupSmsDaoJdbcImpl extends AbstractDataSynchDaoJdbc {
		/**
		 * 批量插入语句
		 */
		public String getBatchInsertSql(String table){
			return "insert into "+table+"_irms_group_sms("+
						"id,"+
						"customer_name,"+
						"related_instance,"+
						"sms_enterprise_cod,"+
						"sms_service_num_type,"+
						"sms_service_num,"+
						"sms_busi_code,"+
						"sms_service_scope,"+
						"service_district,"+
						"sms_protocol_type,"+
						"tran_access_way,"+
						"business_hosting_way,"+
						"product_instance_id,"+
						"access_gateway_room,"+
						"access_gateway,"+
						"gateway_ip,"+
						"enterprise_server_ip,"+
						"service_port_no,"+
						"gateway_login_name,"+
						"related_circuit_name,"+
						"related_oc_name,"+
						"customer_access_date,"+
						"create_time,"+
						"remark,"+
						"related_instance_id,"+
						"access_gateway_id,"+
						"gateway_ip_id,"+
						"related_circuit_name_id,"+
						"related_oc_name_id,"+
 						"inspect_id,"+
 						"inspect_name"+
					") values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?,?)";
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
			ps.setString(3,StaticMethod.nullObject2String(data.get("related_instance")));
			/*用于计费唯一标识EC所分配的企业代码*/
			ps.setString(4,StaticMethod.nullObject2String(data.get("sms_enterprise_cod")));
			/*短信/彩信/短彩信/WAP*/
			ps.setString(5,StaticMethod.nullObject2String(data.get("sms_service_num_type")));
			/*短信服务号码*/
			ps.setString(6,StaticMethod.nullObject2String(data.get("sms_service_num")));
			/*短信规则为：字符+数字，十位字符长度*/
			ps.setString(7,StaticMethod.nullObject2String(data.get("sms_busi_code")));
			/*全国、全省（如果是直辖市范围填写全省）、地市*/
			ps.setString(8,StaticMethod.nullObject2String(data.get("sms_service_scope")));
			/*服务地市*/
			ps.setString(9,StaticMethod.nullObject2String(data.get("service_district")));
			/*描述接口的协议类型：CMPP2.0、CMPP3.0、CMPPE*/
			ps.setString(10,StaticMethod.nullObject2String(data.get("sms_protocol_type")));
			/*枚举值：SDH/PON/PTN/PDH/微波/裸纤*/
			ps.setString(11,StaticMethod.nullObject2String(data.get("tran_access_way")));
			/*选项：
1：移动数据（互联网)专线接入；
2：其它运营商互联网接入
3：移动传输专线接入
若选择2，则关联产品实例标识可为空*/
			ps.setString(12,StaticMethod.nullObject2String(data.get("business_hosting_way")));
			/*若业务承载方式选择为2，此字段可以不填写*/
			ps.setString(13,StaticMethod.nullObject2String(data.get("product_instance_id")));
			/*与《短信网关－系统信息》的【所属短信网关ID】的《短信网关－基础信息》的【所在机房】关联*/
			ps.setString(14,StaticMethod.nullObject2String(data.get("access_gateway_room")));
			/*与“短彩专业”的【短信网关－系统信息】的【业务系统名称】关联*/
			ps.setString(15,StaticMethod.nullObject2String(data.get("access_gateway")));
			/*互联网接入时，与“短彩专业”的【短信网关－系统信息】的【公网互联IP地址】关联；
传输专线接入时，与“短彩专业”的【短信网关－系统信息】的【私网互联IP地址】关联；*/
			ps.setString(16,StaticMethod.nullObject2String(data.get("gateway_ip")));
			/*若业务承载方式选择为1时，与相关产品实例标识关联的【客户业务应用IP地址】关联；
若业务承载方式选择为2时，手工填写，无跨专业关联*/
			ps.setString(17,StaticMethod.nullObject2String(data.get("enterprise_server_ip")));
			/*提供服务端口号*/
			ps.setString(18,StaticMethod.nullObject2String(data.get("service_port_no")));
			/*登录网关用户名*/
			ps.setString(19,StaticMethod.nullObject2String(data.get("gateway_login_name")));
			/*与“传送网资源”（内线逻辑资源）的【传输电路】的【电路路名称】关联*/
			ps.setString(20,StaticMethod.nullObject2String(data.get("related_circuit_name")));
			/*与“传送网资源”（外线资源）的【光路】的【光路名称】关联*/
			ps.setString(21,StaticMethod.nullObject2String(data.get("related_oc_name")));
			/*光纤接入客户的日期*/
			ps.setString(22,StaticMethod.nullObject2String(data.get("customer_access_date")));
			/*创建时间*/
			ps.setString(23,StaticMethod.nullObject2String(data.get("create_time")));
			/*备注*/
			ps.setString(24,StaticMethod.nullObject2String(data.get("remark")));
			/*产品实例标识ID*/
			ps.setString(25,StaticMethod.nullObject2String(data.get("related_instance_id")));
			/*接入网关名称ID*/
			ps.setString(26,StaticMethod.nullObject2String(data.get("access_gateway_id")));
			/*网关IP地址ID*/
			ps.setString(27,StaticMethod.nullObject2String(data.get("gateway_ip_id")));
			/*电路名称ID*/
			ps.setString(28,StaticMethod.nullObject2String(data.get("related_circuit_name_id")));
			/*光路名称ID*/
			ps.setString(29,StaticMethod.nullObject2String(data.get("related_oc_name_id")));
			/*所属巡检点主键id*/
			ps.setString(30,StaticMethod.nullObject2String(data.get("inspect_id")));
			/*所属巡检点名称*/
			ps.setString(31,StaticMethod.nullObject2String(data.get("inspect_name")));
			
			ps.addBatch();
			
			return ps;
		}
}