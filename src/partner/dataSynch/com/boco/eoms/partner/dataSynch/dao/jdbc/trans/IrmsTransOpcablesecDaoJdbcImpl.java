package com.boco.eoms.partner.dataSynch.dao.jdbc.trans;

import java.sql.PreparedStatement;
import java.util.Map;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.partner.dataSynch.dao.AbstractDataSynchDaoJdbc;


/**
 * 类说明：网络资源--传输线路--光缆段:光缆段DaoJdbcImpl实现类
 * 创建人： zhangkeqi
 * 创建时间：2012-11-16 23:11:36
 */
public class IrmsTransOpcablesecDaoJdbcImpl extends AbstractDataSynchDaoJdbc {
		/**
		 * 批量插入语句
		 */
		public String getBatchInsertSql(String table){
			return "insert into "+table+"_irms_trans_opcablesec("+
						"id,"+
						"opcablesection_name,"+
						"related_opcable,"+
						"start_point_name,"+
						"end_point_name,"+
						"opcable_connect_facilities,"+
						"opcable_core_num,"+
						"opcable_type,"+
						"vendor,"+
						"property,"+
						"use_unit,"+
						"owner,"+
						"use,"+
						"length,"+
						"remark,"+
						"create_time,"+
						"related_opcable_id,"+
 						"inspect_id,"+
 						"inspect_name"+
					") values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?,?)";
		}
		
		/**
		 * 添加批量插入语句
		 */
		public PreparedStatement addPsBatch(PreparedStatement ps,Map<String,Object> data) throws Exception{
			/*主键*/
			ps.setString(1,StaticMethod.nullObject2String(data.get("id")));
			/*光缆段名称，作为业务主键，命名要求唯一。由多根光纤组成（如24芯、48芯），位于相邻局站之间的光缆连接部分，光缆段经过若干光交接点设备，若干光缆段构成光缆线路。[例]YXH-1号井-接头盒-SF-10号井-接头盒*/
			ps.setString(2,StaticMethod.nullObject2String(data.get("opcablesection_name")));
			/*关联到【光缆表】-【光缆名称】*/
			ps.setString(3,StaticMethod.nullObject2String(data.get("related_opcable")));
			/*始端名称，命名需规范以实现数据关联。*/
			ps.setString(4,StaticMethod.nullObject2String(data.get("start_point_name")));
			/*末端名称，命名需规范以实现数据关联。*/
			ps.setString(5,StaticMethod.nullObject2String(data.get("end_point_name")));
			/*描述此光缆段从一局站出到另外一局站入顺序经过了哪些外线设施，以逗号进行分隔。注意命名规范。例如：引上1,管道段2,引上3,杆路段4,直埋5,挂墙6。*/
			ps.setString(6,StaticMethod.nullObject2String(data.get("opcable_connect_facilities")));
			/*纤芯数*/
			ps.setString(7,StaticMethod.nullObject2String(data.get("opcable_core_num")));
			/*如：GYTS-48、GYTA-24等。*/
			ps.setString(8,StaticMethod.nullObject2String(data.get("opcable_type")));
			/*光缆生产厂家*/
			ps.setString(9,StaticMethod.nullObject2String(data.get("vendor")));
			/*自建、合建、共建、附挂附穿、租用、购买、置换*/
			ps.setString(10,StaticMethod.nullObject2String(data.get("property")));
			/*使用单位*/
			ps.setString(11,StaticMethod.nullObject2String(data.get("use_unit")));
			/*所有权人*/
			ps.setString(12,StaticMethod.nullObject2String(data.get("owner")));
			/*枚举值：自用、出租、共享。*/
			ps.setString(13,StaticMethod.nullObject2String(data.get("use")));
			/*皮长，单位公里。例如：500。*/
			ps.setString(14,StaticMethod.nullObject2String(data.get("length")));
			/*备注*/
			ps.setString(15,StaticMethod.nullObject2String(data.get("remark")));
			/*创建时间*/
			ps.setString(16,StaticMethod.nullObject2String(data.get("create_time")));
			/*所属光缆ID*/
			ps.setString(17,StaticMethod.nullObject2String(data.get("related_opcable_id")));
			/*所属巡检点主键id*/
			ps.setString(18,StaticMethod.nullObject2String(data.get("inspect_id")));
			/*所属巡检点名称*/
			ps.setString(19,StaticMethod.nullObject2String(data.get("inspect_name")));
			
			ps.addBatch();
			
			return ps;
		}
}