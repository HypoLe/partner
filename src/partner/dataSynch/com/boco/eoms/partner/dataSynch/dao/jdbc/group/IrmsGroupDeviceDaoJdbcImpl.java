package com.boco.eoms.partner.dataSynch.dao.jdbc.group;

import java.sql.PreparedStatement;
import java.util.Map;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.partner.dataSynch.dao.AbstractDataSynchDaoJdbc;


/**
 * 类说明：网络资源--集客家客--客户端设备信息表:客户端设备信息表DaoJdbcImpl实现类
 * 创建人： zhangkeqi
 * 创建时间：2012-11-16 23:11:36
 */
public class IrmsGroupDeviceDaoJdbcImpl extends AbstractDataSynchDaoJdbc {
		/**
		 * 批量插入语句
		 */
		public String getBatchInsertSql(String table){
			return "insert into "+table+"_irms_group_device("+
						"id,"+
						"device_name,"+
						"device_serial_num,"+
						"device_city,"+
						"device_county,"+
						"device_town,"+
						"device_room,"+
						"device_vendor,"+
						"device_model,"+
						"device_status,"+
						"device_property_unit,"+
						"device_use_unit,"+
						"device_manage_unit,"+
						"device_ip,"+
						"device_version,"+
						"device_access_net_date,"+
						"device_fixed_asset_id,"+
						"related_iinstance,"+
						"client_device_type,"+
						"client_device_port_type,"+
						"client_device_port_id,"+
						"device_port_status,"+
						"inerconnect_ip,"+
						"client_device_vlan,"+
						"client_device_mac,"+
						"pointa_longitude,"+
						"pointa_latitude,"+
						"create_time,"+
						"remark,"+
						"device_room_id,"+
						"related_instance_id,"+
 						"inspect_id,"+
 						"inspect_name"+
					") values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?,?)";
		}
		
		/**
		 * 添加批量插入语句
		 */
		public PreparedStatement addPsBatch(PreparedStatement ps,Map<String,Object> data) throws Exception{
			/*主键*/
			ps.setString(1,StaticMethod.nullObject2String(data.get("id")));
			/*开通设备的设备资源命名*/
			ps.setString(2,StaticMethod.nullObject2String(data.get("device_name")));
			/*开通设备的设备序列号*/
			ps.setString(3,StaticMethod.nullObject2String(data.get("device_serial_num")));
			/*设备所属地市*/
			ps.setString(4,StaticMethod.nullObject2String(data.get("device_city")));
			/*设备所属区县*/
			ps.setString(5,StaticMethod.nullObject2String(data.get("device_county")));
			/*设备所属乡镇街道*/
			ps.setString(6,StaticMethod.nullObject2String(data.get("device_town")));
			/*设备所属机房*/
			ps.setString(7,StaticMethod.nullObject2String(data.get("device_room")));
			/*设备厂家名称*/
			ps.setString(8,StaticMethod.nullObject2String(data.get("device_vendor")));
			/*设备规则型号*/
			ps.setString(9,StaticMethod.nullObject2String(data.get("device_model")));
			/*设备状态*/
			ps.setString(10,StaticMethod.nullObject2String(data.get("device_status")));
			/*设备产权单位*/
			ps.setString(11,StaticMethod.nullObject2String(data.get("device_property_unit")));
			/*设备使用单位*/
			ps.setString(12,StaticMethod.nullObject2String(data.get("device_use_unit")));
			/*设备维护单位*/
			ps.setString(13,StaticMethod.nullObject2String(data.get("device_manage_unit")));
			/*设备IP地址*/
			ps.setString(14,StaticMethod.nullObject2String(data.get("device_ip")));
			/*设备版本信息*/
			ps.setString(15,StaticMethod.nullObject2String(data.get("device_version")));
			/*设备入网时间*/
			ps.setString(16,StaticMethod.nullObject2String(data.get("device_access_net_date")));
			/*设备固定资产编号*/
			ps.setString(17,StaticMethod.nullObject2String(data.get("device_fixed_asset_id")));
			/*与【T1.1-客户信息表】的【客户编号】关联*/
			ps.setString(18,StaticMethod.nullObject2String(data.get("related_iinstance")));
			/*IAD/AG/PBX/IP PBX/协转/光收/光端机*/
			ps.setString(19,StaticMethod.nullObject2String(data.get("client_device_type")));
			/*TDM/POS/ETH*/
			ps.setString(20,StaticMethod.nullObject2String(data.get("client_device_port_type")));
			/*1/2/3/4*/
			ps.setString(21,StaticMethod.nullObject2String(data.get("client_device_port_id")));
			/*预占/空闲*/
			ps.setString(22,StaticMethod.nullObject2String(data.get("device_port_status")));
			/*当设备类型为数通类型时为必填项，如果地址没有分配，或该设备无需分配地址，则不填。*/
			ps.setString(23,StaticMethod.nullObject2String(data.get("inerconnect_ip")));
			/*当设备类型为数通类型时填写，若设备无需配置此项，则无需填写*/
			ps.setString(24,StaticMethod.nullObject2String(data.get("client_device_vlan")));
			/*当设备类型为数通类型时填写，若设备无需配置此项，则无需填写*/
			ps.setString(25,StaticMethod.nullObject2String(data.get("client_device_mac")));
			/*细化到小数点后6位数*/
			ps.setString(26,StaticMethod.nullObject2String(data.get("pointa_longitude")));
			/*细化到小数点后6位数*/
			ps.setString(27,StaticMethod.nullObject2String(data.get("pointa_latitude")));
			/*创建时间*/
			ps.setString(28,StaticMethod.nullObject2String(data.get("create_time")));
			/*备注*/
			ps.setString(29,StaticMethod.nullObject2String(data.get("remark")));
			/*所属机房ID*/
			ps.setString(30,StaticMethod.nullObject2String(data.get("device_room_id")));
			/*产品实例标识ID*/
			ps.setString(31,StaticMethod.nullObject2String(data.get("related_instance_id")));
			/*所属巡检点主键id*/
			ps.setString(32,StaticMethod.nullObject2String(data.get("inspect_id")));
			/*所属巡检点名称*/
			ps.setString(33,StaticMethod.nullObject2String(data.get("inspect_name")));
			
			ps.addBatch();
			
			return ps;
		}
}