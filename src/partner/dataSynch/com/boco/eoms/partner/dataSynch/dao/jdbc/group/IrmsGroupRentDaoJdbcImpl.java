package com.boco.eoms.partner.dataSynch.dao.jdbc.group;

import java.sql.PreparedStatement;
import java.util.Map;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.partner.dataSynch.dao.AbstractDataSynchDaoJdbc;


/**
 * 类说明：网络资源--集客家客--出租专线信息表:出租专线信息表DaoJdbcImpl实现类
 * 创建人： zhangkeqi
 * 创建时间：2012-11-16 23:11:36
 */
public class IrmsGroupRentDaoJdbcImpl extends AbstractDataSynchDaoJdbc {
		/**
		 * 批量插入语句
		 */
		public String getBatchInsertSql(String table){
			return "insert into "+table+"_irms_group_rent("+
						"id,"+
						"customer_name,"+
						"pointa_customer_name,"+
						"related_instance,"+
						"tran_access_way,"+
						"business_bw,"+
						"pointa_customer_addr,"+
						"related_pointa,"+
						"related_pointa_port,"+
						"pointa_ddfodf_port,"+
						"related_ar_device,"+
						"related_ar_device_port,"+
						"related_ard_odf_port,"+
						"related_circuit_name,"+
						"related_circuit_type,"+
						"related_circuit_level,"+
						"circuit_requirement,"+
						"circuit_protect_way,"+
						"office_redirection,"+
						"pointz_customer_name,"+
						"pointz_customer_addr,"+
						"related_pointz_name,"+
						"related_pointz_port,"+
						"pointz_ddfodf_port,"+
						"related_pointz_ar_name,"+
						"related_pointz_ar_port,"+
						"related_pointz_arp_odf,"+
						"related_oc_name,"+
						"remark,"+
						"create_time,"+
						"related_instance_id,"+
						"related_pointa_id,"+
						"related_pointa_port_id,"+
						"related_ar_device_id,"+
						"related_ar_device_port_id,"+
						"related_ard_odf_port_id,"+
						"related_circuit_name_id,"+
						"related_circuit_type_id,"+
						"related_circuit_level_id,"+
						"related_pointz_name_id,"+
						"related_pointz_port_id,"+
						"related_pointz_ar_name_id,"+
						"related_pointz_arpt_id,"+
						"related_pointz_arp_odf_id,"+
						"related_oc_name_id,"+
 						"inspect_id,"+
 						"inspect_name"+
					") values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?,?)";
		}
		
		/**
		 * 添加批量插入语句
		 */
		public PreparedStatement addPsBatch(PreparedStatement ps,Map<String,Object> data) throws Exception{
			/*主键*/
			ps.setString(1,StaticMethod.nullObject2String(data.get("id")));
			/*签订合同的客户名称*/
			ps.setString(2,StaticMethod.nullObject2String(data.get("customer_name")));
			/*开通电路的集团客户名称*/
			ps.setString(3,StaticMethod.nullObject2String(data.get("pointa_customer_name")));
			/*与【T1.1-客户信息表】的【客户编号】关联*/
			ps.setString(4,StaticMethod.nullObject2String(data.get("related_instance")));
			/*枚举值：SDH/PON/PTN/PDH/微波/裸纤*/
			ps.setString(5,StaticMethod.nullObject2String(data.get("tran_access_way")));
			/*客户申请的速率*/
			ps.setString(6,StaticMethod.nullObject2String(data.get("business_bw")));
			/*A端传输接入的地址信息*/
			ps.setString(7,StaticMethod.nullObject2String(data.get("pointa_customer_addr")));
			/*与【客户端设备资源录入模板-来自集团总部】的【客户端客户设备端口类型】关联*/
			ps.setString(8,StaticMethod.nullObject2String(data.get("related_pointa")));
			/*与【客户端设备资源录入模板-来自集团总部】的【客户端客户设备端口编号】关联*/
			ps.setString(9,StaticMethod.nullObject2String(data.get("related_pointa_port")));
			/*A端客户设备ODF架端口*/
			ps.setString(10,StaticMethod.nullObject2String(data.get("pointa_ddfodf_port")));
			/*SDH、PDH、裸光纤接入时，与“传送网资源”（内线逻辑资源）的【传输网元】的【传输网元名称】关联*/
			ps.setString(11,StaticMethod.nullObject2String(data.get("related_ar_device")));
			/*SDH、PDH、裸光纤接入时，与“传送网资源”（内线物理资源）的【光口】的【传输端口名称】关联*/
			ps.setString(12,StaticMethod.nullObject2String(data.get("related_ar_device_port")));
			/*SDH、PDH、裸光纤接入时，与“传送网资源”（内线物理资源）的【光口】的【传输侧ODF端子名称】关联*/
			ps.setString(13,StaticMethod.nullObject2String(data.get("related_ard_odf_port")));
			/*与“传送网资源”（内线逻辑资源）的【传输电路】的【本端传输网元端口】关联*/
			ps.setString(14,StaticMethod.nullObject2String(data.get("related_circuit_name")));
			/*与“传送网资源”（内线逻辑资源）的【传输电路】的【本端传输网元端口】关联*/
			ps.setString(15,StaticMethod.nullObject2String(data.get("related_circuit_type")));
			/*与“传送网资源”（内线逻辑资源）的【传输电路】的【电路级别】关联*/
			ps.setString(16,StaticMethod.nullObject2String(data.get("related_circuit_level")));
			/*根据电路的要求情况分为：新增、停闭、调整*/
			ps.setString(17,StaticMethod.nullObject2String(data.get("circuit_requirement")));
			/*描述该电路的保护方式（单链、双路由、双路由双节点、环保护）*/
			ps.setString(18,StaticMethod.nullObject2String(data.get("circuit_protect_way")));
			/*业务使用方局向，可以为业务设备或者业务站点*/
			ps.setString(19,StaticMethod.nullObject2String(data.get("office_redirection")));
			/*开通电路的集团客户名称*/
			ps.setString(20,StaticMethod.nullObject2String(data.get("pointz_customer_name")));
			/*Z端传输接入的地址信息（如果是传输专线，则Z端必须填写，如果只是负责接入语音或互联网，则Z端地址可以不填）*/
			ps.setString(21,StaticMethod.nullObject2String(data.get("pointz_customer_addr")));
			/*与【客户端设备资源录入模板-来自集团总部】的【客户端客户设备端口类型】关联*/
			ps.setString(22,StaticMethod.nullObject2String(data.get("related_pointz_name")));
			/*与【客户端设备资源录入模板-来自集团总部】的【客户端客户设备端口编号】关联*/
			ps.setString(23,StaticMethod.nullObject2String(data.get("related_pointz_port")));
			/*Z端客户设备ODF架端口*/
			ps.setString(24,StaticMethod.nullObject2String(data.get("pointz_ddfodf_port")));
			/*SDH、PDH、裸光纤接入时，与“传送网资源”（内线逻辑资源）的【传输网元】的【传输网元名称】关联*/
			ps.setString(25,StaticMethod.nullObject2String(data.get("related_pointz_ar_name")));
			/*SDH、PDH、裸光纤接入时，与“传送网资源”（内线物理资源）的【光口】的【传输端口名称】关联*/
			ps.setString(26,StaticMethod.nullObject2String(data.get("related_pointz_ar_port")));
			/*SDH、PDH、裸光纤接入时，与“传送网资源”（内线物理资源）的【光口】的【传输侧ODF端子名称】关联*/
			ps.setString(27,StaticMethod.nullObject2String(data.get("related_pointz_arp_odf")));
			/*与“传送网资源”（外线资源）的【光路】的【光路名称】关联*/
			ps.setString(28,StaticMethod.nullObject2String(data.get("related_oc_name")));
			/*备注*/
			ps.setString(29,StaticMethod.nullObject2String(data.get("remark")));
			/*创建时间*/
			ps.setString(30,StaticMethod.nullObject2String(data.get("create_time")));
			/*产品实例标识ID*/
			ps.setString(31,StaticMethod.nullObject2String(data.get("related_instance_id")));
			/*A 端业务设备名称ID*/
			ps.setString(32,StaticMethod.nullObject2String(data.get("related_pointa_id")));
			/*A 端业务设备端口ID*/
			ps.setString(33,StaticMethod.nullObject2String(data.get("related_pointa_port_id")));
			/*A端接入机房设备名称ID*/
			ps.setString(34,StaticMethod.nullObject2String(data.get("related_ar_device_id")));
			/*A端接入机房设备端口ID*/
			ps.setString(35,StaticMethod.nullObject2String(data.get("related_ar_device_port_id")));
			/*A端接入机房设备ODF端口ID*/
			ps.setString(36,StaticMethod.nullObject2String(data.get("related_ard_odf_port_id")));
			/*电路名称ID*/
			ps.setString(37,StaticMethod.nullObject2String(data.get("related_circuit_name_id")));
			/*电路接口类型ID*/
			ps.setString(38,StaticMethod.nullObject2String(data.get("related_circuit_type_id")));
			/*电路级别ID*/
			ps.setString(39,StaticMethod.nullObject2String(data.get("related_circuit_level_id")));
			/*Z端业务设备名称ID*/
			ps.setString(40,StaticMethod.nullObject2String(data.get("related_pointz_name_id")));
			/*Z端业务设备端口ID*/
			ps.setString(41,StaticMethod.nullObject2String(data.get("related_pointz_port_id")));
			/*Z端接入机房设备名称ID*/
			ps.setString(42,StaticMethod.nullObject2String(data.get("related_pointz_ar_name_id")));
			/*Z端接入机房设备端口ID*/
			ps.setString(43,StaticMethod.nullObject2String(data.get("related_pointz_arpt_id")));
			/*Z端接入机房设备ODF端口ID*/
			ps.setString(44,StaticMethod.nullObject2String(data.get("related_pointz_arp_odf_id")));
			/*光路名称ID*/
			ps.setString(45,StaticMethod.nullObject2String(data.get("related_oc_name_id")));
			/*所属巡检点主键id*/
			ps.setString(46,StaticMethod.nullObject2String(data.get("inspect_id")));
			/*所属巡检点名称*/
			ps.setString(47,StaticMethod.nullObject2String(data.get("inspect_name")));
			
			ps.addBatch();
			
			return ps;
		}
}