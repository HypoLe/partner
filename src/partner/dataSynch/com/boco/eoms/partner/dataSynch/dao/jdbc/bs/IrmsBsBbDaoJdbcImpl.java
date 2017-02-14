package com.boco.eoms.partner.dataSynch.dao.jdbc.bs;

import java.sql.PreparedStatement;
import java.util.Map;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.partner.dataSynch.dao.AbstractDataSynchDaoJdbc;


/**
 * 类说明：网络资源--基站设备及配套--电源恒温箱:电源恒温箱DaoJdbcImpl实现类
 * 创建人： zhangkeqi
 * 创建时间：2012-11-16 23:11:36
 */
public class IrmsBsBbDaoJdbcImpl extends AbstractDataSynchDaoJdbc {
		/**
		 * 批量插入语句
		 */
		public String getBatchInsertSql(String table){
			return "insert into "+table+"_irms_bs_bb("+
						"id,"+
						"related_room,"+
						"device_type,"+
						"device_subclass,"+
						"label_dev,"+
						"device_sequence,"+
						"equipmentcode,"+
						"model,"+
						"related_vendor,"+
						"trademark,"+
						"supplier,"+
						"manage_company,"+
						"start_time,"+
						"end_time,"+
						"status,"+
						"rating_cooling_var,"+
						"preserver,"+
						"create_time,"+
						"remark,"+
						"related_room_id,"+
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
			/*关联属性，关联到【空间资源】-【机房】表-【机房名称】*/
			ps.setString(2,StaticMethod.nullObject2String(data.get("related_room")));
			/*枚举值：高压配电、低压配电、发电机组、开关电源系统、UPS系统 、列头柜、专用空调、中央空调、普通空调、节能设备、动环监控采集、图像系统、LSC系统、CSC系统、交流配电系统、开关电源系统、变换设备、UPS系统、普通空调、节能设备、新能源设备、极早期烟感、动环监控采集、防雷接地系统、车载油机、小油机、车载电池*/
			ps.setString(3,StaticMethod.nullObject2String(data.get("device_type")));
			/*枚举值：电源恒温箱*/
			ps.setString(4,StaticMethod.nullObject2String(data.get("device_subclass")));
			/*符合设备命名规范*/
			ps.setString(5,StaticMethod.nullObject2String(data.get("label_dev")));
			/*符合设备编码规范*/
			ps.setString(6,StaticMethod.nullObject2String(data.get("device_sequence")));
			/*设备的资产编号，可选择填写*/
			ps.setString(7,StaticMethod.nullObject2String(data.get("equipmentcode")));
			/*不带品牌说明的设备产品型号规格*/
			ps.setString(8,StaticMethod.nullObject2String(data.get("model")));
			/*设备的生产厂商*/
			ps.setString(9,StaticMethod.nullObject2String(data.get("related_vendor")));
			/*品牌的简称.如：许继、ABB*/
			ps.setString(10,StaticMethod.nullObject2String(data.get("trademark")));
			/*设备供货商*/
			ps.setString(11,StaticMethod.nullObject2String(data.get("supplier")));
			/*设备代维公司*/
			ps.setString(12,StaticMethod.nullObject2String(data.get("manage_company")));
			/*格式为：YYYY-MM-DD*/
			ps.setString(13,StaticMethod.nullObject2String(data.get("start_time")));
			/*格式为：YYYY-MM-DD*/
			ps.setString(14,StaticMethod.nullObject2String(data.get("end_time")));
			/*枚举值：工程，现网，空载，退网*/
			ps.setString(15,StaticMethod.nullObject2String(data.get("status")));
			/*额定制冷量（KW)*/
			ps.setString(16,StaticMethod.nullObject2String(data.get("rating_cooling_var")));
			/*移动公司该设备的负责人*/
			ps.setString(17,StaticMethod.nullObject2String(data.get("preserver")));
			/*创建时间*/
			ps.setString(18,StaticMethod.nullObject2String(data.get("create_time")));
			/*备注*/
			ps.setString(19,StaticMethod.nullObject2String(data.get("remark")));
			/*机房（基站）名称ID*/
			ps.setString(20,StaticMethod.nullObject2String(data.get("related_room_id")));
			/*所属巡检点主键id*/
			ps.setString(21,StaticMethod.nullObject2String(data.get("inspect_id")));
			/*所属巡检点名称*/
			ps.setString(22,StaticMethod.nullObject2String(data.get("inspect_name")));
			
			ps.addBatch();
			
			return ps;
		}
}