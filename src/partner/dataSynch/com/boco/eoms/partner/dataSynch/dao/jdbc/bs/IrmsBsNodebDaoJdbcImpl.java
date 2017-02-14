package com.boco.eoms.partner.dataSynch.dao.jdbc.bs;

import java.sql.PreparedStatement;
import java.util.Map;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.partner.dataSynch.dao.AbstractDataSynchDaoJdbc;


/**
 * 类说明：网络资源--基站设备及配套--NODEB:NODEBDaoJdbcImpl实现类
 * 创建人： zhangkeqi
 * 创建时间：2012-11-16 23:11:36
 */
public class IrmsBsNodebDaoJdbcImpl extends AbstractDataSynchDaoJdbc {
		/**
		 * 批量插入语句
		 */
		public String getBatchInsertSql(String table){
			return "insert into "+table+"_irms_bs_nodeb("+
						"id,"+
						"userlabel_cm,"+
						"label_cn,"+
						"related_rnc,"+
						"trans_type,"+
						"site_category,"+
						"vip_type,"+
						"beehive_type,"+
						"model,"+
						"related_room,"+
						"fail_acc_unit,"+
						"manage_company,"+
						"status,"+
						"related_vendor,"+
						"open_time,"+
						"remark,"+
						"create_time,"+
						"related_rnc_id,"+
						"related_room_id,"+
 						"inspect_id,"+
 						"inspect_name"+
					") values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?,?)";
		}
		
		/**
		 * 添加批量插入语句
		 */
		public PreparedStatement addPsBatch(PreparedStatement ps,Map<String,Object> data) throws Exception{
			/*主键*/
			ps.setString(1,StaticMethod.nullObject2String(data.get("id")));
			/*按照集团要求，OMC中网元名称要与综合资管命名保持一致，此项命名参照集团规范*/
			ps.setString(2,StaticMethod.nullObject2String(data.get("userlabel_cm")));
			/*中文名称*/
			ps.setString(3,StaticMethod.nullObject2String(data.get("label_cn")));
			/*关联属性，关联到【RNC】表网元名称*/
			ps.setString(4,StaticMethod.nullObject2String(data.get("related_rnc")));
			/* 光传输、微波、卫星*/
			ps.setString(5,StaticMethod.nullObject2String(data.get("trans_type")));
			/*A频段、B频段、E频段、F频段、A+B频段*/
			ps.setString(6,StaticMethod.nullObject2String(data.get("site_category")));
			/* 枚举:非VIP,一级VIP,二级VIP,三级VIP,非基站机房,VVIP类型,超级VIP基站*/
			ps.setString(7,StaticMethod.nullObject2String(data.get("vip_type")));
			/* 枚举{宏蜂窝,微蜂窝}*/
			ps.setString(8,StaticMethod.nullObject2String(data.get("beehive_type")));
			/*设备型号*/
			ps.setString(9,StaticMethod.nullObject2String(data.get("model")));
			/*关联属性，关联到【空间资源】-【机房】表-【机房名称】,对于无机房的基站，请在空间资源里加入该基站虚拟机房名称，名称中可通过扩展虚拟字段进行区分。*/
			ps.setString(10,StaticMethod.nullObject2String(data.get("related_room")));
			/*网优中心排障组等*/
			ps.setString(11,StaticMethod.nullObject2String(data.get("fail_acc_unit")));
			/*江苏海讯科技有限公司,京信通信系统(广州)有限公司等等*/
			ps.setString(12,StaticMethod.nullObject2String(data.get("manage_company")));
			/* 枚举值：{现网、工程、空载、退网}*/
			ps.setString(13,StaticMethod.nullObject2String(data.get("status")));
			/*枚举值：诺西、新邮通、阿尔卡特、摩托罗拉、中兴、华为、日海等*/
			ps.setString(14,StaticMethod.nullObject2String(data.get("related_vendor")));
			/*开通时间*/
			ps.setString(15,StaticMethod.nullObject2String(data.get("open_time")));
			/*备注*/
			ps.setString(16,StaticMethod.nullObject2String(data.get("remark")));
			/*创建时间*/
			ps.setString(17,StaticMethod.nullObject2String(data.get("create_time")));
			/*所属RNC ID*/
			ps.setString(18,StaticMethod.nullObject2String(data.get("related_rnc_id")));
			/*所属机房ID*/
			ps.setString(19,StaticMethod.nullObject2String(data.get("related_room_id")));
			/*所属巡检点主键id*/
			ps.setString(20,StaticMethod.nullObject2String(data.get("inspect_id")));
			/*所属巡检点名称*/
			ps.setString(21,StaticMethod.nullObject2String(data.get("inspect_name")));
			
			ps.addBatch();
			
			return ps;
		}
}