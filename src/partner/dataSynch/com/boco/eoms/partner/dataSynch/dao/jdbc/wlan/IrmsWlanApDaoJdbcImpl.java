package com.boco.eoms.partner.dataSynch.dao.jdbc.wlan;

import java.sql.PreparedStatement;
import java.util.Map;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.partner.dataSynch.dao.AbstractDataSynchDaoJdbc;


/**
 * 类说明：网络资源--直放站室分及WLAN--AP:APDaoJdbcImpl实现类
 * 创建人： zhangkeqi
 * 创建时间：2012-11-16 23:11:36
 */
public class IrmsWlanApDaoJdbcImpl extends AbstractDataSynchDaoJdbc {
		/**
		 * 批量插入语句
		 */
		public String getBatchInsertSql(String table){
			return "insert into "+table+"_irms_wlan_ap("+
						"id,"+
						"ap_name,"+
						"related_district,"+
						"related_hotpoint,"+
						"ap_vendor,"+
						"ap_type,"+
						"ap_mac_addr,"+
						"manage_ap_addr,"+
						"stress_pattern,"+
						"ap_location,"+
						"ap_coverage_area,"+
						"is_2g3g_distributo,"+
						"related_hotping_sw,"+
						"hotpot_sw_port,"+
						"soft_version,"+
						"related_back_ac_cu,"+
						"ne_working_state,"+
						"remark,"+
						"create_time,"+
						"related_district_id,"+
						"related_hotpoint_id,"+
						"related_hotping_sw_id,"+
						"related_back_ac_cu_id,"+
 						"inspect_id,"+
 						"inspect_name"+
					") values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?,?)";
		}
		
		/**
		 * 添加批量插入语句
		 */
		public PreparedStatement addPsBatch(PreparedStatement ps,Map<String,Object> data) throws Exception{
			/*主键*/
			ps.setString(1,StaticMethod.nullObject2String(data.get("id")));
			/*建议集团明确“AP名称”的命名规范*/
			ps.setString(2,StaticMethod.nullObject2String(data.get("ap_name")));
			/*关联字段，关联至【空间资源】-【区域】表-【区域名称】*/
			ps.setString(3,StaticMethod.nullObject2String(data.get("related_district")));
			/*专业内关联属性，关联到【热点】表-【热点名称】*/
			ps.setString(4,StaticMethod.nullObject2String(data.get("related_hotpoint")));
			/*华三、思科、大唐等*/
			ps.setString(5,StaticMethod.nullObject2String(data.get("ap_vendor")));
			/*胖AP、瘦AP*/
			ps.setString(6,StaticMethod.nullObject2String(data.get("ap_type")));
			/*48bit硬件地址*/
			ps.setString(7,StaticMethod.nullObject2String(data.get("ap_mac_addr")));
			/*AP管理地址*/
			ps.setString(8,StaticMethod.nullObject2String(data.get("manage_ap_addr")));
			/*空口制式*/
			ps.setString(9,StaticMethod.nullObject2String(data.get("stress_pattern")));
			/*AP安装的位置*/
			ps.setString(10,StaticMethod.nullObject2String(data.get("ap_location")));
			/*AP覆盖的区域*/
			ps.setString(11,StaticMethod.nullObject2String(data.get("ap_coverage_area")));
			/*枚举类型：是、否*/
			ps.setString(12,StaticMethod.nullObject2String(data.get("is_2g3g_distributo")));
			/*关联属性；关联至【WLAN系统】-【交换机】表-【交换机名称】，参考示例：HNZK-WLAN-SW01-MPS3026G*/
			ps.setString(13,StaticMethod.nullObject2String(data.get("related_hotping_sw")));
			/*关联属性，关联至【WLAN系统】-【交换机】表。端口管理需要知道AP接入热点交换机的哪个端口
建议参考示例：HNZZ-WLAN-SW01-MPS3026G]FE-0/1/1*/
			ps.setString(14,StaticMethod.nullObject2String(data.get("hotpot_sw_port")));
			/*软件版本号*/
			ps.setString(15,StaticMethod.nullObject2String(data.get("soft_version")));
			/*专业内关联属性，关联到【AC】表-【AC名称】*/
			ps.setString(16,StaticMethod.nullObject2String(data.get("related_back_ac_cu")));
			/*工程、现网、空载、退网*/
			ps.setString(17,StaticMethod.nullObject2String(data.get("ne_working_state")));
			/*备注*/
			ps.setString(18,StaticMethod.nullObject2String(data.get("remark")));
			/*创建时间*/
			ps.setString(19,StaticMethod.nullObject2String(data.get("create_time")));
			/*地市ID*/
			ps.setString(20,StaticMethod.nullObject2String(data.get("related_district_id")));
			/*所属热点名称ID*/
			ps.setString(21,StaticMethod.nullObject2String(data.get("related_hotpoint_id")));
			/*所属热点交换机名称ID*/
			ps.setString(22,StaticMethod.nullObject2String(data.get("related_hotping_sw_id")));
			/*所属AC名称ID*/
			ps.setString(23,StaticMethod.nullObject2String(data.get("related_back_ac_cu_id")));
			/*所属巡检点主键id*/
			ps.setString(24,StaticMethod.nullObject2String(data.get("inspect_id")));
			/*所属巡检点名称*/
			ps.setString(25,StaticMethod.nullObject2String(data.get("inspect_name")));
			
			ps.addBatch();
			
			return ps;
		}
}