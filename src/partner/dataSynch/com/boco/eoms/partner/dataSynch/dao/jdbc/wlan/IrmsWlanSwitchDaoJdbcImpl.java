package com.boco.eoms.partner.dataSynch.dao.jdbc.wlan;

import java.sql.PreparedStatement;
import java.util.Map;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.partner.dataSynch.dao.AbstractDataSynchDaoJdbc;


/**
 * 类说明：网络资源--直放站室分及WLAN--交换机:交换机DaoJdbcImpl实现类
 * 创建人： zhangkeqi
 * 创建时间：2012-11-16 23:11:36
 */
public class IrmsWlanSwitchDaoJdbcImpl extends AbstractDataSynchDaoJdbc {
		/**
		 * 批量插入语句
		 */
		public String getBatchInsertSql(String table){
			return "insert into "+table+"_irms_wlan_switch("+
						"id,"+
						"related_district,"+
						"label_cn,"+
						"related_vendor_cui,"+
						"model,"+
						"soft_version,"+
						"manage_ip_addr,"+
						"related_up_orig_port_name,"+
						"up_port_type,"+
						"sw_port_count,"+
						"related_hotpot_cui,"+
						"related_room_cuid,"+
						"status,"+
						"net_level,"+
						"related_tsw_dev_cu,"+
						"related_tsw_dev_po,"+
						"max_access_ap_coun,"+
						"max_access_sw_coun,"+
						"setup_time,"+
						"real_access_ap_cou,"+
						"real_aceess_sw_cou,"+
						"remark,"+
						"create_time,"+
						"related_district_id,"+
						"related_hotpot_cui_id,"+
 						"inspect_id,"+
 						"inspect_name"+
					") values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?,?)";
		}
		
		/**
		 * 添加批量插入语句
		 */
		public PreparedStatement addPsBatch(PreparedStatement ps,Map<String,Object> data) throws Exception{
			/*主键*/
			ps.setString(1,StaticMethod.nullObject2String(data.get("id")));
			/*关联属性，关联到【空间资源】-【区域】表-【区域名称】*/
			ps.setString(2,StaticMethod.nullObject2String(data.get("related_district")));
			/*根据中国移动数据网命名规范，如，HNZK-WLAN-SW01-MPS3026G（命名见下述解释）*/
			ps.setString(3,StaticMethod.nullObject2String(data.get("label_cn")));
			/*设备生产厂家。枚举：华三、迈普等*/
			ps.setString(4,StaticMethod.nullObject2String(data.get("related_vendor_cui")));
			/*设备硬件型号*/
			ps.setString(5,StaticMethod.nullObject2String(data.get("model")));
			/*在网的软件版本*/
			ps.setString(6,StaticMethod.nullObject2String(data.get("soft_version")));
			/*登录设备使用的操作维护IP地址。一般指loopback0地址*/
			ps.setString(7,StaticMethod.nullObject2String(data.get("manage_ip_addr")));
			/*关联属性，互连设备可能是数据网、传输网设备，或其他，命名可参考根据中国移动数据网命名规范。参考示例（本端和对端设备端口信息）：[HNZZ-WLAN-SW01-MPS3026G]GE-0/1/1-[HNZZ-MC-CMNET-RT01-HWNE40E]GE-0/1/1。参考示例见右侧插入对象*/
			ps.setString(8,StaticMethod.nullObject2String(data.get("related_up_orig_port_name")));
			/*光口、电口等*/
			ps.setString(9,StaticMethod.nullObject2String(data.get("up_port_type")));
			/*交换机的所有端口数量*/
			ps.setString(10,StaticMethod.nullObject2String(data.get("sw_port_count")));
			/*关联属性，关联至【WLAN系统】-【热点】表-【热点名称】*/
			ps.setString(11,StaticMethod.nullObject2String(data.get("related_hotpot_cui")));
			/*在用户机房，不关联空间资源-机房*/
			ps.setString(12,StaticMethod.nullObject2String(data.get("related_room_cuid")));
			/*工程、现网、空载、退网*/
			ps.setString(13,StaticMethod.nullObject2String(data.get("status")));
			/*汇聚层、接入层*/
			ps.setString(14,StaticMethod.nullObject2String(data.get("net_level")));
			/*若是接入层交换机填写*/
			ps.setString(15,StaticMethod.nullObject2String(data.get("related_tsw_dev_cu")));
			/*若是接入层交换机填写*/
			ps.setString(16,StaticMethod.nullObject2String(data.get("related_tsw_dev_po")));
			/*接入层交换机接入的AP数量*/
			ps.setString(17,StaticMethod.nullObject2String(data.get("max_access_ap_coun")));
			/*三层汇聚交换机可接入的二层交换机数量*/
			ps.setString(18,StaticMethod.nullObject2String(data.get("max_access_sw_coun")));
			/*标准格式：yyyy-MM-dd*/
			ps.setString(19,StaticMethod.nullObject2String(data.get("setup_time")));
			/**/
			ps.setString(20,StaticMethod.nullObject2String(data.get("real_access_ap_cou")));
			/**/
			ps.setString(21,StaticMethod.nullObject2String(data.get("real_aceess_sw_cou")));
			/*备注*/
			ps.setString(22,StaticMethod.nullObject2String(data.get("remark")));
			/*创建时间*/
			ps.setString(23,StaticMethod.nullObject2String(data.get("create_time")));
			/*地市ID*/
			ps.setString(24,StaticMethod.nullObject2String(data.get("related_district_id")));
			/*交换机所属热点ID*/
			ps.setString(25,StaticMethod.nullObject2String(data.get("related_hotpot_cui_id")));
			/*所属巡检点主键id*/
			ps.setString(26,StaticMethod.nullObject2String(data.get("inspect_id")));
			/*所属巡检点名称*/
			ps.setString(27,StaticMethod.nullObject2String(data.get("inspect_name")));
			
			ps.addBatch();
			
			return ps;
		}
}