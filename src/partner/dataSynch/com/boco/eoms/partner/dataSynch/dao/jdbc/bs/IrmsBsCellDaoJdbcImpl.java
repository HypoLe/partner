package com.boco.eoms.partner.dataSynch.dao.jdbc.bs;

import java.sql.PreparedStatement;
import java.util.Map;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.partner.dataSynch.dao.AbstractDataSynchDaoJdbc;


/**
 * 类说明：网络资源--基站设备及配套--CELL:CELLDaoJdbcImpl实现类
 * 创建人： zhangkeqi
 * 创建时间：2012-11-16 23:11:36
 */
public class IrmsBsCellDaoJdbcImpl extends AbstractDataSynchDaoJdbc {
		/**
		 * 批量插入语句
		 */
		public String getBatchInsertSql(String table){
			return "insert into "+table+"_irms_bs_cell("+
						"id,"+
						"userlabel_cm,"+
						"label_cn,"+
						"ci,"+
						"lac,"+
						"gsmdcsindicator,"+
						"related_btssite_cu,"+
						"cell_type,"+
						"status,"+
						"boundcell_type,"+
						"if_repeator,"+
						"is_enegprs,"+
						"cover_filed,"+
						"trx_count,"+
						"open_time,"+
						"remark,"+
						"create_time,"+
						"related_btssite_cu_id,"+
 						"inspect_id,"+
 						"inspect_name"+
					") values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?,?)";
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
			/*小区码CI*/
			ps.setString(4,StaticMethod.nullObject2String(data.get("ci")));
			/*位置区码LAC*/
			ps.setString(5,StaticMethod.nullObject2String(data.get("lac")));
			/*1800、900/1800、900，标识清楚即可*/
			ps.setString(6,StaticMethod.nullObject2String(data.get("gsmdcsindicator")));
			/*关联属性，关联到【BTSSITE】表网元名称*/
			ps.setString(7,StaticMethod.nullObject2String(data.get("related_btssite_cu")));
			/*枚举{宏蜂窝,微蜂窝}*/
			ps.setString(8,StaticMethod.nullObject2String(data.get("cell_type")));
			/*枚举{现网、工程、空载、退网}*/
			ps.setString(9,StaticMethod.nullObject2String(data.get("status")));
			/*枚举{省内,省际}*/
			ps.setString(10,StaticMethod.nullObject2String(data.get("boundcell_type")));
			/* 枚举{是,否}*/
			ps.setString(11,StaticMethod.nullObject2String(data.get("if_repeator")));
			/*支持EGPRS*/
			ps.setString(12,StaticMethod.nullObject2String(data.get("is_enegprs")));
			/*车站,大中专院校,党政机关,高级写字楼,高速公路,会展中心,机场,居民小区,旅游景点,其它,商业区,体育场馆,铁路,星级酒店,重点企业 供参考*/
			ps.setString(13,StaticMethod.nullObject2String(data.get("cover_filed")));
			/*TRX数量*/
			ps.setString(14,StaticMethod.nullObject2String(data.get("trx_count")));
			/*开通时间*/
			ps.setString(15,StaticMethod.nullObject2String(data.get("open_time")));
			/*备注*/
			ps.setString(16,StaticMethod.nullObject2String(data.get("remark")));
			/*创建时间*/
			ps.setString(17,StaticMethod.nullObject2String(data.get("create_time")));
			/*所属BTS ID*/
			ps.setString(18,StaticMethod.nullObject2String(data.get("related_btssite_cu_id")));
			/*所属巡检点主键id*/
			ps.setString(19,StaticMethod.nullObject2String(data.get("inspect_id")));
			/*所属巡检点名称*/
			ps.setString(20,StaticMethod.nullObject2String(data.get("inspect_name")));
			
			ps.addBatch();
			
			return ps;
		}
}