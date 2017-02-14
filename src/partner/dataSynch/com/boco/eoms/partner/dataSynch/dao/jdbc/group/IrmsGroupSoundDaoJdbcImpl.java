package com.boco.eoms.partner.dataSynch.dao.jdbc.group;

import java.sql.PreparedStatement;
import java.util.Map;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.partner.dataSynch.dao.AbstractDataSynchDaoJdbc;


/**
 * 类说明：网络资源--集客家客--语音专线信息表:语音专线信息表DaoJdbcImpl实现类
 * 创建人： zhangkeqi
 * 创建时间：2012-11-16 23:11:36
 */
public class IrmsGroupSoundDaoJdbcImpl extends AbstractDataSynchDaoJdbc {
		/**
		 * 批量插入语句
		 */
		public String getBatchInsertSql(String table){
			return "insert into "+table+"_irms_group_sound("+
						"id,"+
						"customer_name,"+
						"related_instance,"+
						"code_section,"+
						"incoming_call_permissions,"+
						"signaling_pattern,"+
						"business_bw,"+
						"relay_num,"+
						"tran_access_way,"+
						"customer_bus_addr,"+
						"related_sd_name,"+
						"related_sd_port,"+
						"related_ad_name,"+
						"related_ad_port,"+
						"related_lan_name,"+
						"related_lan_port,"+
						"converged_name,"+
						"converged_port,"+
						"related_or_name,"+
						"related_or_device,"+
						"related_or_device_port,"+
						"related_circuit_name,"+
						"related_oc_name,"+
						"client_bus_app_ip,"+
						"customer_acs_date,"+
						"create_time,"+
						"remark,"+
						"related_instance_id,"+
						"related_sd_name_id,"+
						"related_sd_port_id,"+
						"related_ad_name_id,"+
						"related_ad_port_id,"+
						"related_lan_namea_id,"+
						"related_lan_port_id,"+
						"related_or_device_id,"+
						"related_or_device_port_id,"+
						"related_circuit_name_id,"+
						"related_oc_name_id,"+
						"client_bus_app_ip_id,"+
 						"inspect_id,"+
 						"inspect_name"+
					") values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?,?)";
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
			/*语音接入码*/
			ps.setString(4,StaticMethod.nullObject2String(data.get("code_section")));
			/*选项：本地、省内、省际、国际*/
			ps.setString(5,StaticMethod.nullObject2String(data.get("incoming_call_permissions")));
			/*PRI/NO.7/NO.1/MFC/DTMF*/
			ps.setString(6,StaticMethod.nullObject2String(data.get("signaling_pattern")));
			/*中继接入速率*/
			ps.setString(7,StaticMethod.nullObject2String(data.get("business_bw")));
			/*N×2M*/
			ps.setString(8,StaticMethod.nullObject2String(data.get("relay_num")));
			/*枚举值：SDH/PON/PTN/PDH/微波/裸纤*/
			ps.setString(9,StaticMethod.nullObject2String(data.get("tran_access_way")));
			/*客户语音接入地址*/
			ps.setString(10,StaticMethod.nullObject2String(data.get("customer_bus_addr")));
			/*与【客户端设备信息表】的【客户端客户设备名称】关联*/
			ps.setString(11,StaticMethod.nullObject2String(data.get("related_sd_name")));
			/*与【客户端设备信息表】的【客户端客户设备端口编号】关联*/
			ps.setString(12,StaticMethod.nullObject2String(data.get("related_sd_port")));
			/*SDH、PDH接入时，与“传送网”（外线资源）的【光路】的【末端传输或业务设备名称】关联*/
			ps.setString(13,StaticMethod.nullObject2String(data.get("related_ad_name")));
			/*SDH、PDH接入时，与“传送网”（外线资源）的【光路】的【末端传输或业务端口名称】关联*/
			ps.setString(14,StaticMethod.nullObject2String(data.get("related_ad_port")));
			/*经过互联网接入语音专线时与“数据网”的SW/SR的【面板信息】的【设备名称】关联*/
			ps.setString(15,StaticMethod.nullObject2String(data.get("related_lan_name")));
			/*经过互联网接入语音专线时与“数据网”的SW/SR的【面板信息】的【端口名称】关联*/
			ps.setString(16,StaticMethod.nullObject2String(data.get("related_lan_port")));
			/*汇聚语音交换机（PBX/IP PBX/SIP GW/TDM交换机等）设备名称*/
			ps.setString(17,StaticMethod.nullObject2String(data.get("converged_name")));
			/*汇聚语音交换机（PBX/IP PBX/SIP GW/TDM交换机等）设备的端口名称*/
			ps.setString(18,StaticMethod.nullObject2String(data.get("converged_port")));
			/*如果接入TDM设备，与“核心网资源”专业的《端口》的【所属机房】关联*/
			ps.setString(19,StaticMethod.nullObject2String(data.get("related_or_name")));
			/*如果接入TDM设备,与“核心网资源”专业的【端口】的【网元名称】关联*/
			ps.setString(20,StaticMethod.nullObject2String(data.get("related_or_device")));
			/*如果接入TDM设备，
与“核心网资源”专业的【端口】的【端口名称】关联*/
			ps.setString(21,StaticMethod.nullObject2String(data.get("related_or_device_port")));
			/*与“传送网资源”（内线逻辑资源）的【传输电路】的【电路路名称】关联*/
			ps.setString(22,StaticMethod.nullObject2String(data.get("related_circuit_name")));
			/*与“传送网资源”（外线资源）的【光路】的【光路名称】关联*/
			ps.setString(23,StaticMethod.nullObject2String(data.get("related_oc_name")));
			/*IP语音专线接入时，与“数据网”的【逻辑端口信息】的【IP地址】关联*/
			ps.setString(24,StaticMethod.nullObject2String(data.get("client_bus_app_ip")));
			/*接入客户日期:yyyy-MM-dd*/
			ps.setString(25,StaticMethod.nullObject2String(data.get("customer_acs_date")));
			/*创建时间*/
			ps.setString(26,StaticMethod.nullObject2String(data.get("create_time")));
			/*备注*/
			ps.setString(27,StaticMethod.nullObject2String(data.get("remark")));
			/*产品实例标识ID*/
			ps.setString(28,StaticMethod.nullObject2String(data.get("related_instance_id")));
			/*客户端客户设备名称ID*/
			ps.setString(29,StaticMethod.nullObject2String(data.get("related_sd_name_id")));
			/*客户端客户设备端口编号ID*/
			ps.setString(30,StaticMethod.nullObject2String(data.get("related_sd_port_id")));
			/*移动接入设备名ID*/
			ps.setString(31,StaticMethod.nullObject2String(data.get("related_ad_name_id")));
			/*移动接入设备端口编号ID*/
			ps.setString(32,StaticMethod.nullObject2String(data.get("related_ad_port_id")));
			/*城域网设备名称ID*/
			ps.setString(33,StaticMethod.nullObject2String(data.get("related_lan_namea_id")));
			/*城域网设备端口编号ID*/
			ps.setString(34,StaticMethod.nullObject2String(data.get("related_lan_port_id")));
			/*局端设备名称ID*/
			ps.setString(35,StaticMethod.nullObject2String(data.get("related_or_device_id")));
			/*局端设备端口ID*/
			ps.setString(36,StaticMethod.nullObject2String(data.get("related_or_device_port_id")));
			/*电路名称ID*/
			ps.setString(37,StaticMethod.nullObject2String(data.get("related_circuit_name_id")));
			/*光路名称ID*/
			ps.setString(38,StaticMethod.nullObject2String(data.get("related_oc_name_id")));
			/*客户业务应用IP地址（公网/私网）ID*/
			ps.setString(39,StaticMethod.nullObject2String(data.get("client_bus_app_ip_id")));
			/*所属巡检点主键id*/
			ps.setString(40,StaticMethod.nullObject2String(data.get("inspect_id")));
			/*所属巡检点名称*/
			ps.setString(41,StaticMethod.nullObject2String(data.get("inspect_name")));
			
			ps.addBatch();
			
			return ps;
		}
}