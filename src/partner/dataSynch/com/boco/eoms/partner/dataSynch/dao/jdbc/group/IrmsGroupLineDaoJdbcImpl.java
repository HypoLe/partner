package com.boco.eoms.partner.dataSynch.dao.jdbc.group;

import java.sql.PreparedStatement;
import java.util.Map;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.partner.dataSynch.dao.AbstractDataSynchDaoJdbc;


/**
 * 类说明：网络资源--集客家客--数据(互联网)专线信息表:数据(互联网)专线信息表DaoJdbcImpl实现类
 * 创建人： zhangkeqi
 * 创建时间：2012-11-16 23:11:36
 */
public class IrmsGroupLineDaoJdbcImpl extends AbstractDataSynchDaoJdbc {
		/**
		 * 批量插入语句
		 */
		public String getBatchInsertSql(String table){
			return "insert into "+table+"_irms_group_line("+
						"id,"+
						"customer_name,"+
						"related_instance,"+
						"customer_busi_addr,"+
						"business_bw,"+
						"tran_access_way,"+
						"related_cd_name,"+
						"related_cd_port,"+
						"related_ad_name,"+
						"related_ad_port_no,"+
						"related_ad_vlan,"+
						"related_ad_slan,"+
						"related_ad_clan,"+
						"related_osr_name,"+
						"related_osd_name,"+
						"related_osd_port,"+
						"related_client_ip,"+
						"related_connect_ip,"+
						"related_circuit_name,"+
						"related_oc_name,"+
						"customer_access_date,"+
						"remark,"+
						"create_time,"+
						"related_instance_id,"+
						"related_cd_name_id,"+
						"related_cd_port_id,"+
						"related_ad_name_id,"+
						"related_ad_port_no_id,"+
						"related_ad_vlan_id,"+
						"related_ad_slan_id,"+
						"related_ad_clan_id,"+
						"related_osd_name_id,"+
						"related_osd_port_id,"+
						"related_client_ip_id,"+
						"related_connect_ip_id,"+
						"related_circuit_name_id,"+
						"related_oc_name_id,"+
 						"inspect_id,"+
 						"inspect_name"+
					") values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?,?)";
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
			/*客户接入互联网的地址。*/
			ps.setString(4,StaticMethod.nullObject2String(data.get("customer_busi_addr")));
			/*客户要求的速率*/
			ps.setString(5,StaticMethod.nullObject2String(data.get("business_bw")));
			/*枚举值：SDH/PON/PTN/PDH/微波/裸纤*/
			ps.setString(6,StaticMethod.nullObject2String(data.get("tran_access_way")));
			/*与【客户端设备信息表】的【客户端客户设备名称】关联*/
			ps.setString(7,StaticMethod.nullObject2String(data.get("related_cd_name")));
			/*与【客户端设备信息表】的【客户端客户设备端口编号】关联*/
			ps.setString(8,StaticMethod.nullObject2String(data.get("related_cd_port")));
			/*SDH、PDH接入时，与“传送网”（外线资源）的【光路】的【末端传输或业务设备名称】关联
采用PON接入时，接入点设备一般是ONU（FTTB）/olt（FTTH），与“传送网（内线逻辑资源）”的【ONU】或【OLT】的【设备名称】关联*/
			ps.setString(9,StaticMethod.nullObject2String(data.get("related_ad_name")));
			/*SDH、PDH接入时，与“传送网”（外线资源）的【光路】的【末端传输或业务端口名称】关联
采用PON接入时，接入点设备一般是ONU（FTTB）/olt（FTTH），与“传送网（内线逻辑资源）”的【ONU】或【OLT】的【端口名称】关联*/
			ps.setString(10,StaticMethod.nullObject2String(data.get("related_ad_port_no")));
			/*与“数据网”的【VLAN信息－单层】的【Vlan ID】关联*/
			ps.setString(11,StaticMethod.nullObject2String(data.get("related_ad_vlan")));
			/*与“数据网”的【vlan信息－双层】的【外层vlan ID关联】*/
			ps.setString(12,StaticMethod.nullObject2String(data.get("related_ad_slan")));
			/*与“数据网”的【vlan信息－双层】的【内层vlan ID关联】*/
			ps.setString(13,StaticMethod.nullObject2String(data.get("related_ad_clan")));
			/*互联网用途的业务接入时，根据局端设备SW或SR等的名称，与“数据网”的【基础信息】的【机房】关联
GPRS用途的业务接入时，与“GPRS”的【GPRS系统（物理资源）－GGSN】的【设备所在机房】关联
短信和彩信等其它业务用途的接入时，与相关设备网元的【基础信息】的【机房】关联*/
			ps.setString(14,StaticMethod.nullObject2String(data.get("related_osr_name")));
			/*互联网用途的业务接入时，与“数据网”的SW或SR等的【面板信息】的【设备名称】关联
GPRS用途的业务接入时，与“GPRS”的【GPRS系统（物理资源）－端口】的【所在设备】关联
短信和彩信等其它业务用途的接入时，与相关设备网元的【面板信息】的【设备名称】关联*/
			ps.setString(15,StaticMethod.nullObject2String(data.get("related_osd_name")));
			/*互联网用途的业务接入时，与“数据网”的SW或SR等【面板信息】的【端口名称】关联
GPRS用途的业务接入时，与“GPRS”的【GPRS系统（物理资源）－端口】的【端口名称】关联
短信和彩信用途的业务接入时，与“短彩信网”的【端口名称】关联*/
			ps.setString(16,StaticMethod.nullObject2String(data.get("related_osd_port")));
			/*互联网用途的业务接入时，与“数据网”的【逻辑端口信息】的【IP地址】关联
GPRS用途的业务接入时，与“GPRS”的【GPRS系统（逻辑资源）－IP地址信息】的【IP地址】关联
短信和彩信等其它业务用途的接入时，与相关设备网元的【逻辑端口信息】的【IP地址】关联*/
			ps.setString(17,StaticMethod.nullObject2String(data.get("related_client_ip")));
			/*互联网用途的业务接入时，与“数据网”的【逻辑端口信息】的【IP地址】关联
GPRS用途的业务接入时，与“GPRS”的【GPRS系统（逻辑资源）－IP地址信息】的【IP地址】关联
短信和彩信等其它业务用途的接入时，与相关设备网元的【逻辑端口信息】的【IP地址】关联*/
			ps.setString(18,StaticMethod.nullObject2String(data.get("related_connect_ip")));
			/*与“传送网资源”（内线逻辑资源）的【传输电路】的【电路路名称】关联*/
			ps.setString(19,StaticMethod.nullObject2String(data.get("related_circuit_name")));
			/*与“传送网资源”（外线资源）的【光路】的【光路名称】关联*/
			ps.setString(20,StaticMethod.nullObject2String(data.get("related_oc_name")));
			/*光纤接入客户的日期*/
			ps.setString(21,StaticMethod.nullObject2String(data.get("customer_access_date")));
			/*备注*/
			ps.setString(22,StaticMethod.nullObject2String(data.get("remark")));
			/*创建时间*/
			ps.setString(23,StaticMethod.nullObject2String(data.get("create_time")));
			/**/
			ps.setString(24,StaticMethod.nullObject2String(data.get("related_instance_id")));
			/**/
			ps.setString(25,StaticMethod.nullObject2String(data.get("related_cd_name_id")));
			/*客户端客户设备端口编号ID*/
			ps.setString(26,StaticMethod.nullObject2String(data.get("related_cd_port_id")));
			/*移动接入设备名称ID*/
			ps.setString(27,StaticMethod.nullObject2String(data.get("related_ad_name_id")));
			/*移动接入设备端口编号ID*/
			ps.setString(28,StaticMethod.nullObject2String(data.get("related_ad_port_no_id")));
			/*接入点设备VLAN编号ID*/
			ps.setString(29,StaticMethod.nullObject2String(data.get("related_ad_vlan_id")));
			/*接入点设备SLAN编号ID*/
			ps.setString(30,StaticMethod.nullObject2String(data.get("related_ad_slan_id")));
			/*接入点设备CLAN编号ID*/
			ps.setString(31,StaticMethod.nullObject2String(data.get("related_ad_clan_id")));
			/*局端设备名称ID*/
			ps.setString(32,StaticMethod.nullObject2String(data.get("related_osd_name_id")));
			/*局端设备端口ID*/
			ps.setString(33,StaticMethod.nullObject2String(data.get("related_osd_port_id")));
			/*客户终端IP地址（公网/私网）ID*/
			ps.setString(34,StaticMethod.nullObject2String(data.get("related_client_ip_id")));
			/*设备互联IP地址ID*/
			ps.setString(35,StaticMethod.nullObject2String(data.get("related_connect_ip_id")));
			/*电路名称ID*/
			ps.setString(36,StaticMethod.nullObject2String(data.get("related_circuit_name_id")));
			/*光路名称ID*/
			ps.setString(37,StaticMethod.nullObject2String(data.get("related_oc_name_id")));
			/*所属巡检点主键id*/
			ps.setString(38,StaticMethod.nullObject2String(data.get("inspect_id")));
			/*所属巡检点名称*/
			ps.setString(39,StaticMethod.nullObject2String(data.get("inspect_name")));
			
			ps.addBatch();
			
			return ps;
		}
}