package com.boco.eoms.partner.dataSynch.dao.jdbc.group;

import java.sql.PreparedStatement;
import java.util.Map;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.partner.dataSynch.dao.AbstractDataSynchDaoJdbc;


/**
 * 类说明：网络资源--集客家客--彩信息表:彩信息表DaoJdbcImpl实现类
 * 创建人： zhangkeqi
 * 创建时间：2012-11-16 23:11:36
 */
public class IrmsGroupMmsDaoJdbcImpl extends AbstractDataSynchDaoJdbc {
		/**
		 * 批量插入语句
		 */
		public String getBatchInsertSql(String table){
			return "insert into "+table+"_irms_group_mms("+
						"id,"+
						"customer_name,"+
						"related_instance,"+
						"mms_service_num_ty,"+
						"mms_enterprise_cod,"+
						"mms_service_num,"+
						"mms_service_scope,"+
						"service_district,"+
						"protocal_type,"+
						"mms_busi_name,"+
						"mms_busi_code,"+
						"mms_up_url,"+
						"mms_busi_flow_limi,"+
						"tran_access_way,"+
						"business_hosting_way,"+
						"related_product_instance,"+
						"related_ag_room,"+
						"related_ag_name,"+
						"related_gateway_ip,"+
						"related_server_ip,"+
						"service_port,"+
						"login_name,"+
						"related_circuit_name,"+
						"related_oc_name,"+
						"customer_access_date,"+
						"remark,"+
						"create_time,"+
						"related_instance_id,"+
						"related_ag_name_id,"+
						"related_gateway_ip_id,"+
						"related_circuit_name_id,"+
						"related_oc_name_id,"+
 						"inspect_id,"+
 						"inspect_name"+
					") values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?,?)";
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
			/*短信/彩信*/
			ps.setString(4,StaticMethod.nullObject2String(data.get("mms_service_num_ty")));
			/*用于计费唯一标识EC所分配的企业代码*/
			ps.setString(5,StaticMethod.nullObject2String(data.get("mms_enterprise_cod")));
			/*彩信服务号码*/
			ps.setString(6,StaticMethod.nullObject2String(data.get("mms_service_num")));
			/*全国、全省（如果是直辖市范围填写全省）、地市*/
			ps.setString(7,StaticMethod.nullObject2String(data.get("mms_service_scope")));
			/*服务地市名称*/
			ps.setString(8,StaticMethod.nullObject2String(data.get("service_district")));
			/*描述接口的协议类型：SOAP、HTTP*/
			ps.setString(9,StaticMethod.nullObject2String(data.get("protocal_type")));
			/*提供彩信业务的具体业务名称*/
			ps.setString(10,StaticMethod.nullObject2String(data.get("mms_busi_name")));
			/*业务名称对应的代码*/
			ps.setString(11,StaticMethod.nullObject2String(data.get("mms_busi_code")));
			/*用于上行到客户的url*/
			ps.setString(12,StaticMethod.nullObject2String(data.get("mms_up_url")));
			/*1—表示只允许业务上行；
2—表示只允许业务下行；
3—表示上下行均允许*/
			ps.setString(13,StaticMethod.nullObject2String(data.get("mms_busi_flow_limi")));
			/*枚举值：SDH/PON/PTN/PDH/微波/裸纤*/
			ps.setString(14,StaticMethod.nullObject2String(data.get("tran_access_way")));
			/*选项：
1：移动数据（互联网)专线接入；
2：其它运营商互联网接入
3：移动传输专线接入
若选择2，则关联产品实例标识可为空*/
			ps.setString(15,StaticMethod.nullObject2String(data.get("business_hosting_way")));
			/*若业务承载方式选择为2，此字段可以不填写*/
			ps.setString(16,StaticMethod.nullObject2String(data.get("related_product_instance")));
			/*与《短信网关－系统信息》的【所属短信网关ID】的《短信网关－基础信息》的【所在机房】关联*/
			ps.setString(17,StaticMethod.nullObject2String(data.get("related_ag_room")));
			/*与“短彩专业”的【彩信中兴－系统信息】的【业务系统名称】关联*/
			ps.setString(18,StaticMethod.nullObject2String(data.get("related_ag_name")));
			/*与“短彩专业”的【彩信中兴－系统信息】的【公网互联IP地址】关联；*/
			ps.setString(19,StaticMethod.nullObject2String(data.get("related_gateway_ip")));
			/*若业务承载方式选择为1时，与相关产品实例标识关联的【客户业务应用IP地址】关联；
若业务承载方式选择为2时，手工填写，无跨专业关联*/
			ps.setString(20,StaticMethod.nullObject2String(data.get("related_server_ip")));
			/*port number*/
			ps.setString(21,StaticMethod.nullObject2String(data.get("service_port")));
			/*业务登录帐号*/
			ps.setString(22,StaticMethod.nullObject2String(data.get("login_name")));
			/*与“传送网资源”（内线逻辑资源）的【传输电路】的【电路路名称】关联*/
			ps.setString(23,StaticMethod.nullObject2String(data.get("related_circuit_name")));
			/*与“传送网资源”（外线资源）的【光路】的【光路名称】关联*/
			ps.setString(24,StaticMethod.nullObject2String(data.get("related_oc_name")));
			/*光纤接入客户的日期:yyyy-MM-dd*/
			ps.setString(25,StaticMethod.nullObject2String(data.get("customer_access_date")));
			/*备注*/
			ps.setString(26,StaticMethod.nullObject2String(data.get("remark")));
			/*创建时间*/
			ps.setString(27,StaticMethod.nullObject2String(data.get("create_time")));
			/*产品实例标识ID*/
			ps.setString(28,StaticMethod.nullObject2String(data.get("related_instance_id")));
			/*接入网关名称ID*/
			ps.setString(29,StaticMethod.nullObject2String(data.get("related_ag_name_id")));
			/*网关IP地址ID*/
			ps.setString(30,StaticMethod.nullObject2String(data.get("related_gateway_ip_id")));
			/*电路名称ID*/
			ps.setString(31,StaticMethod.nullObject2String(data.get("related_circuit_name_id")));
			/*光路名称ID*/
			ps.setString(32,StaticMethod.nullObject2String(data.get("related_oc_name_id")));
			/*所属巡检点主键id*/
			ps.setString(33,StaticMethod.nullObject2String(data.get("inspect_id")));
			/*所属巡检点名称*/
			ps.setString(34,StaticMethod.nullObject2String(data.get("inspect_name")));
			
			ps.addBatch();
			
			return ps;
		}
}