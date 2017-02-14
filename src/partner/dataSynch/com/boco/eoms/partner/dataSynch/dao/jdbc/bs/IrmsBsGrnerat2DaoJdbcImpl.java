package com.boco.eoms.partner.dataSynch.dao.jdbc.bs;

import java.sql.PreparedStatement;
import java.util.Map;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.partner.dataSynch.dao.AbstractDataSynchDaoJdbc;


/**
 * 类说明：网络资源--基站设备及配套--开关电源:开关电源DaoJdbcImpl实现类
 * 创建人： zhangkeqi
 * 创建时间：2012-11-16 23:11:36
 */
public class IrmsBsGrnerat2DaoJdbcImpl extends AbstractDataSynchDaoJdbc {
		/**
		 * 批量插入语句
		 */
		public String getBatchInsertSql(String table){
			return "insert into "+table+"_irms_bs_grnerat2("+
						"id,"+
						"related_room,"+
						"device_type,"+
						"device_subclass,"+
						"label_dev,"+
						"device_sequence,"+
						"sys_no,"+
						"equipmentcode,"+
						"model,"+
						"related_vendor,"+
						"trademark,"+
						"supplier,"+
						"manage_company,"+
						"start_time,"+
						"end_time,"+
						"status,"+
						"exp_rating_voltage,"+
						"smps_type,"+
						"monitor_model,"+
						"comm_model_num,"+
						"comm_model,"+
						"comm_model_voltage,"+
						"comm_model_exp_cap,"+
						"battery_capability,"+
						"battery_eleccap_gr,"+
						"is_under_second,"+
						"preserver,"+
						"is_construct_share,"+
						"construct_company,"+
						"is_shared,"+
						"shared_company,"+
						"create_time,"+
						"remark,"+
						"related_room_id,"+
 						"inspect_id,"+
 						"inspect_name"+
					") values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?,?)";
		}
		
		/**
		 * 添加批量插入语句
		 */
		public PreparedStatement addPsBatch(PreparedStatement ps,Map<String,Object> data) throws Exception{
			/*主键*/
			ps.setString(1,StaticMethod.nullObject2String(data.get("id")));
			/*关联属性，关联到【空间资源】-【机房】表-【机房名称】*/
			ps.setString(2,StaticMethod.nullObject2String(data.get("related_room")));
			/*枚举值：高压配电、低压配电、发电机组、开关电源系统、UPS系统 、列头柜、专用空调、中央空调、普通空调、节能设备、动环监控采集、图像系统、LSC系统、CSC系统、交流配电系统、开关电源系统、变换设备、UPS系统、普通空调、节能设备、新能源设备、极早期烟感、动环监控采集、防雷接地系统、车载油机、小油机、车载电池、其他*/
			ps.setString(3,StaticMethod.nullObject2String(data.get("device_type")));
			/*枚举值：整流器柜、其他*/
			ps.setString(4,StaticMethod.nullObject2String(data.get("device_subclass")));
			/*符合设备命名规范*/
			ps.setString(5,StaticMethod.nullObject2String(data.get("label_dev")));
			/*符合设备编码规范*/
			ps.setString(6,StaticMethod.nullObject2String(data.get("device_sequence")));
			/*开关电源的系统编号*/
			ps.setString(7,StaticMethod.nullObject2String(data.get("sys_no")));
			/*设备的资产编号，可选择填写*/
			ps.setString(8,StaticMethod.nullObject2String(data.get("equipmentcode")));
			/*不带品牌说明的设备产品型号规格*/
			ps.setString(9,StaticMethod.nullObject2String(data.get("model")));
			/*设备的生产厂商*/
			ps.setString(10,StaticMethod.nullObject2String(data.get("related_vendor")));
			/*品牌的简称.如：许继、ABB*/
			ps.setString(11,StaticMethod.nullObject2String(data.get("trademark")));
			/*设备供货商*/
			ps.setString(12,StaticMethod.nullObject2String(data.get("supplier")));
			/*设备代维公司*/
			ps.setString(13,StaticMethod.nullObject2String(data.get("manage_company")));
			/*格式为：YYYY-MM-DD*/
			ps.setString(14,StaticMethod.nullObject2String(data.get("start_time")));
			/*格式为：YYYY-MM-DD*/
			ps.setString(15,StaticMethod.nullObject2String(data.get("end_time")));
			/*枚举值：工程，现网，空载，退网*/
			ps.setString(16,StaticMethod.nullObject2String(data.get("status")));
			/*枚举值：-48,-24*/
			ps.setString(17,StaticMethod.nullObject2String(data.get("exp_rating_voltage")));
			/*枚举值：分列式，组合式*/
			ps.setString(18,StaticMethod.nullObject2String(data.get("smps_type")));
			/*填写监控模块型号*/
			ps.setString(19,StaticMethod.nullObject2String(data.get("monitor_model")));
			/*单位：块*/
			ps.setString(20,StaticMethod.nullObject2String(data.get("comm_model_num")));
			/*填写整流模块型号*/
			ps.setString(21,StaticMethod.nullObject2String(data.get("comm_model")));
			/*枚举值：380,220*/
			ps.setString(22,StaticMethod.nullObject2String(data.get("comm_model_voltage")));
			/*单个整流模块额定输出容量，单位：A*/
			ps.setString(23,StaticMethod.nullObject2String(data.get("comm_model_exp_cap")));
			/*单组电池组单个熔丝的容量及并联个数*/
			ps.setString(24,StaticMethod.nullObject2String(data.get("battery_capability")));
			/*用于连接电池组的熔丝组数*/
			ps.setString(25,StaticMethod.nullObject2String(data.get("battery_eleccap_gr")));
			/*开关电源系统是否具有二次下电功能*/
			ps.setString(26,StaticMethod.nullObject2String(data.get("is_under_second")));
			/*移动公司该设备的负责人*/
			ps.setString(27,StaticMethod.nullObject2String(data.get("preserver")));
			/*枚举值：【是】或者【否】*/
			ps.setString(28,StaticMethod.nullObject2String(data.get("is_construct_share")));
			/*如共建，逐一列出共建单位，用“，”分割。*/
			ps.setString(29,StaticMethod.nullObject2String(data.get("construct_company")));
			/*枚举值：【是】或者【否】*/
			ps.setString(30,StaticMethod.nullObject2String(data.get("is_shared")));
			/*如共建，逐一列出共享单位，用“，”分割。*/
			ps.setString(31,StaticMethod.nullObject2String(data.get("shared_company")));
			/*创建时间*/
			ps.setString(32,StaticMethod.nullObject2String(data.get("create_time")));
			/*备注*/
			ps.setString(33,StaticMethod.nullObject2String(data.get("remark")));
			/*机房（基站）名称ID*/
			ps.setString(34,StaticMethod.nullObject2String(data.get("related_room_id")));
			/*所属巡检点主键id*/
			ps.setString(35,StaticMethod.nullObject2String(data.get("inspect_id")));
			/*所属巡检点名称*/
			ps.setString(36,StaticMethod.nullObject2String(data.get("inspect_name")));
			
			ps.addBatch();
			
			return ps;
		}
}