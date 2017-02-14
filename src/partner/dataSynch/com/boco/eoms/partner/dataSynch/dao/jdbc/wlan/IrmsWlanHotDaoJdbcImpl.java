package com.boco.eoms.partner.dataSynch.dao.jdbc.wlan;

import java.sql.PreparedStatement;
import java.util.Map;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.partner.dataSynch.dao.AbstractDataSynchDaoJdbc;


/**
 * 类说明：网络资源--直放站室分及WLAN--热点:热点DaoJdbcImpl实现类
 * 创建人： zhangkeqi
 * 创建时间：2012-11-16 23:11:36
 */
public class IrmsWlanHotDaoJdbcImpl extends AbstractDataSynchDaoJdbc {
		/**
		 * 批量插入语句
		 */
		public String getBatchInsertSql(String table){
			return "insert into "+table+"_irms_wlan_hot("+
						"id,"+
						"label_cn,"+
						"related_district,"+
						"nas_id,"+
						"hotpoint_type,"+
						"longitude,"+
						"latitude,"+
						"trans_type,"+
						"related_vendor,"+
						"ap_count,"+
						"address,"+
						"wlan_coverage_area,"+
						"remark,"+
						"create_time,"+
						"related_district_id,"+
 						"inspect_id,"+
 						"inspect_name"+
					") values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?,?)";
		}
		
		/**
		 * 添加批量插入语句
		 */
		public PreparedStatement addPsBatch(PreparedStatement ps,Map<String,Object> data) throws Exception{
			/*主键*/
			ps.setString(1,StaticMethod.nullObject2String(data.get("id")));
			/*手工填写，供AP填写时关联所用*/
			ps.setString(2,StaticMethod.nullObject2String(data.get("label_cn")));
			/*关联属性，关联至【空间资源】-【区域】表-【区域名称】*/
			ps.setString(3,StaticMethod.nullObject2String(data.get("related_district")));
			/*根据中国移动WLAN规范，热点与NAS-ID有明确对应关系，所以此字段不能去掉。若个别省公司一个AC一个NAS-ID，可以填写AC的NAS-ID。*/
			ps.setString(4,StaticMethod.nullObject2String(data.get("nas_id")));
			/*集团客户、高等院校等*/
			ps.setString(5,StaticMethod.nullObject2String(data.get("hotpoint_type")));
			/*热点地理位置经度*/
			ps.setString(6,StaticMethod.nullObject2String(data.get("longitude")));
			/*热点地理位置纬度*/
			ps.setString(7,StaticMethod.nullObject2String(data.get("latitude")));
			/*MSTP、SDH、PON、裸纤等*/
			ps.setString(8,StaticMethod.nullObject2String(data.get("trans_type")));
			/*覆盖该热点AP厂家*/
			ps.setString(9,StaticMethod.nullObject2String(data.get("related_vendor")));
			/*AP数量*/
			ps.setString(10,StaticMethod.nullObject2String(data.get("ap_count")));
			/*是集团WLAN月度报表明确要求的内容*/
			ps.setString(11,StaticMethod.nullObject2String(data.get("address")));
			/*是集团WLAN月度报表明确要求的内容*/
			ps.setString(12,StaticMethod.nullObject2String(data.get("wlan_coverage_area")));
			/*备注*/
			ps.setString(13,StaticMethod.nullObject2String(data.get("remark")));
			/*创建时间*/
			ps.setString(14,StaticMethod.nullObject2String(data.get("create_time")));
			/*地市ID*/
			ps.setString(15,StaticMethod.nullObject2String(data.get("related_district_id")));
			/*所属巡检点主键id*/
			ps.setString(16,StaticMethod.nullObject2String(data.get("inspect_id")));
			/*所属巡检点名称*/
			ps.setString(17,StaticMethod.nullObject2String(data.get("inspect_name")));
			
			ps.addBatch();
			
			return ps;
		}
}