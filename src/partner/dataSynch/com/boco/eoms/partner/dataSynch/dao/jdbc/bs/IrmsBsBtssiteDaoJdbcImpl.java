package com.boco.eoms.partner.dataSynch.dao.jdbc.bs;

import java.sql.PreparedStatement;
import java.util.Map;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.partner.dataSynch.dao.AbstractDataSynchDaoJdbc;


/**
 * 类说明：网络资源--基站设备及配套--BTSSITE:BTSSITEDaoJdbcImpl实现类
 * 创建人： zhangkeqi
 * 创建时间：2012-11-16 23:11:36
 */
public class IrmsBsBtssiteDaoJdbcImpl extends AbstractDataSynchDaoJdbc {
		/**
		 * 批量插入语句
		 */
		public String getBatchInsertSql(String table){
			return "insert into "+table+"_irms_bs_btssite("+
						"id,"+
						"userlabel_cm,"+
						"label_cn,"+
						"related_bsc,"+
						"fre_band,"+
						"transmode,"+
						"btssite_crisis_type,"+
						"vip_type,"+
						"related_room,"+
						"beehive_type,"+
						"btssite_no,"+
						"model,"+
						"fail_acc_unit,"+
						"manage_company,"+
						"status,"+
						"related_vendor,"+
						"soft_version,"+
						"open_time,"+
						"remark,"+
						"create_time,"+
						"related_bsc_id,"+
						"related_room_id,"+
 						"inspect_id,"+
 						"inspect_name"+
					") values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?,?)";
		}
		
		/**
		 * 添加批量插入语句
		 */
		public PreparedStatement addPsBatch(PreparedStatement ps,Map<String,Object> data) throws Exception{
			/*主键*/
			ps.setString(1,StaticMethod.nullObject2String(data.get("id")));
			/*OMC中网元名称按照集团要求，OMC中网元名称要与综合资管命名保持一致，此项命名参照集团规范*/
			ps.setString(2,StaticMethod.nullObject2String(data.get("userlabel_cm")));
			/*中文名称*/
			ps.setString(3,StaticMethod.nullObject2String(data.get("label_cn")));
			/*所属bsc关联属性，关联到【BSC】表网元名称*/
			ps.setString(4,StaticMethod.nullObject2String(data.get("related_bsc")));
			/*1800、900/1800、900，标识清楚即可*/
			ps.setString(5,StaticMethod.nullObject2String(data.get("fre_band")));
			/*光传输、微波、卫星*/
			ps.setString(6,StaticMethod.nullObject2String(data.get("transmode")));
			/*50年一遇防洪基站,冰雪,测试站,常规站,地震,地震＋冰雪,地震＋洪水,地震＋洪水＋冰雪,地震＋洪水＋台风,地震＋洪水＋台风＋冰雪,地震＋台风,地震＋台风＋冰雪,洪水,洪水＋冰雪,洪水＋台风,洪水＋台风＋冰雪,台风,台风＋冰雪,应急通信车,应急站 */
			ps.setString(7,StaticMethod.nullObject2String(data.get("btssite_crisis_type")));
			/*非VIP,一级VIP,二级VIP,三级VIP,非基站机房,VVIP类型,超级VIP基站*/
			ps.setString(8,StaticMethod.nullObject2String(data.get("vip_type")));
			/*所属关联属性，关联到【空间资源】-【机房】表-【机房名称】,对于无机房的基站，请在空间资源里加入该基站虚拟机房名称，名称中可通过扩展虚拟字段进行区分。*/
			ps.setString(9,StaticMethod.nullObject2String(data.get("related_room")));
			/*枚举{宏蜂窝,微蜂窝}*/
			ps.setString(10,StaticMethod.nullObject2String(data.get("beehive_type")));
			/*基站编号*/
			ps.setString(11,StaticMethod.nullObject2String(data.get("btssite_no")));
			/*BTS3900*/
			ps.setString(12,StaticMethod.nullObject2String(data.get("model")));
			/*故障受理单位手工维护，如基站班、网优中心故障处理组等*/
			ps.setString(13,StaticMethod.nullObject2String(data.get("fail_acc_unit")));
			/*江苏海讯科技有限公司,京信通信系统(广州)有限公司等等*/
			ps.setString(14,StaticMethod.nullObject2String(data.get("manage_company")));
			/*{现网、工程、空载、退网}*/
			ps.setString(15,StaticMethod.nullObject2String(data.get("status")));
			/*诺西、新邮通、阿尔卡特、摩托罗拉、中兴、华为、日海等*/
			ps.setString(16,StaticMethod.nullObject2String(data.get("related_vendor")));
			/*手工维护*/
			ps.setString(17,StaticMethod.nullObject2String(data.get("soft_version")));
			/*手工维护*/
			ps.setString(18,StaticMethod.nullObject2String(data.get("open_time")));
			/*备注*/
			ps.setString(19,StaticMethod.nullObject2String(data.get("remark")));
			/*创建时间*/
			ps.setString(20,StaticMethod.nullObject2String(data.get("create_time")));
			/*所属BSC ID*/
			ps.setString(21,StaticMethod.nullObject2String(data.get("related_bsc_id")));
			/*所属机房ID*/
			ps.setString(22,StaticMethod.nullObject2String(data.get("related_room_id")));
			/*所属巡检点主键id*/
			ps.setString(23,StaticMethod.nullObject2String(data.get("inspect_id")));
			/*所属巡检点名称*/
			ps.setString(24,StaticMethod.nullObject2String(data.get("inspect_name")));
			
			ps.addBatch();
			
			return ps;
		}
}