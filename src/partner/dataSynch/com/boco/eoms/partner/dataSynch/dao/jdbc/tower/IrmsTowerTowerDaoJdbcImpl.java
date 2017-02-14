package com.boco.eoms.partner.dataSynch.dao.jdbc.tower;

import java.sql.PreparedStatement;
import java.util.Map;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.partner.dataSynch.dao.AbstractDataSynchDaoJdbc;


/**
 * 类说明：网络资源--铁塔及天馈--铁塔:铁塔DaoJdbcImpl实现类
 * 创建人： zhangkeqi
 * 创建时间：2012-11-16 23:11:36
 */
public class IrmsTowerTowerDaoJdbcImpl extends AbstractDataSynchDaoJdbc {
		/**
		 * 批量插入语句
		 */
		public String getBatchInsertSql(String table){
			return "insert into "+table+"_irms_tower_tower("+
						"id,"+
						"label_cn,"+
						"tower_platnum,"+
						"use_platnum,"+
						"related_room,"+
						"tower_stature,"+
						"tower_type,"+
						"vendor,"+
						"tower_property_att,"+
						"buildtime,"+
						"tower_height,"+
						"design_bare_weight,"+
						"real_bare_weight,"+
						"manage_company,"+
						"is_construct_share,"+
						"construct_company,"+
						"is_shared,"+
						"shared_company,"+
						"remark,"+
						"create_time,"+
						"related_room_id,"+
 						"inspect_id,"+
 						"inspect_name"+
					") values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?,?)";
		}
		
		/**
		 * 添加批量插入语句
		 */
		public PreparedStatement addPsBatch(PreparedStatement ps,Map<String,Object> data) throws Exception{
			/*主键*/
			ps.setString(1,StaticMethod.nullObject2String(data.get("id")));
			/***机房**塔（空间资源机房名称+铁塔类型）*/
			ps.setString(2,StaticMethod.nullObject2String(data.get("label_cn")));
			/*2*/
			ps.setString(3,StaticMethod.nullObject2String(data.get("tower_platnum")));
			/*1*/
			ps.setString(4,StaticMethod.nullObject2String(data.get("use_platnum")));
			/*关联属性，关联到【空间资源】-【机房】表-【机房名称】,对于无机房的基站，请在空间资源里加入该基站*/
			ps.setString(5,StaticMethod.nullObject2String(data.get("related_room")));
			/*单位:米*/
			ps.setString(6,StaticMethod.nullObject2String(data.get("tower_stature")));
			/*枚举{落地角钢塔,楼顶角钢塔,落地三管塔,楼顶三管塔,落地内爬单管塔,落地外爬单管塔,楼顶单管塔,落地拉线塔,楼顶拉线塔,楼顶井字架,落地景观塔,楼顶景观塔,抱杆,楼顶美化天线,集束天线、其他}*/
			ps.setString(7,StaticMethod.nullObject2String(data.get("tower_type")));
			/*江苏邮政机械厂、浙江电联*/
			ps.setString(8,StaticMethod.nullObject2String(data.get("vendor")));
			/*移动、联通、电信、其他*/
			ps.setString(9,StaticMethod.nullObject2String(data.get("tower_property_att")));
			/*填写格式:yyyy-MM-dd*/
			ps.setString(10,StaticMethod.nullObject2String(data.get("buildtime")));
			/*单位：米，高度包括楼高+塔身高*/
			ps.setString(11,StaticMethod.nullObject2String(data.get("tower_height")));
			/*公斤*/
			ps.setString(12,StaticMethod.nullObject2String(data.get("design_bare_weight")));
			/*公斤*/
			ps.setString(13,StaticMethod.nullObject2String(data.get("real_bare_weight")));
			/*浙江和勤通信工程有限公司*/
			ps.setString(14,StaticMethod.nullObject2String(data.get("manage_company")));
			/*枚举值：【是】或者【否】*/
			ps.setString(15,StaticMethod.nullObject2String(data.get("is_construct_share")));
			/*如共建，逐一列出共建单位，用“，”分割。*/
			ps.setString(16,StaticMethod.nullObject2String(data.get("construct_company")));
			/*枚举值：【是】或者【否】*/
			ps.setString(17,StaticMethod.nullObject2String(data.get("is_shared")));
			/*如共建，逐一列出共建单位，用“，”分割。*/
			ps.setString(18,StaticMethod.nullObject2String(data.get("shared_company")));
			/*备注*/
			ps.setString(19,StaticMethod.nullObject2String(data.get("remark")));
			/*创建时间*/
			ps.setString(20,StaticMethod.nullObject2String(data.get("create_time")));
			/*所属机房ID*/
			ps.setString(21,StaticMethod.nullObject2String(data.get("related_room_id")));
			/*所属巡检点主键id*/
			ps.setString(22,StaticMethod.nullObject2String(data.get("inspect_id")));
			/*所属巡检点名称*/
			ps.setString(23,StaticMethod.nullObject2String(data.get("inspect_name")));
			
			ps.addBatch();
			
			return ps;
		}
}