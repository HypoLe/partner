package com.boco.eoms.partner.dataSynch.dao.jdbc.trans;

import java.sql.PreparedStatement;
import java.util.Map;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.partner.dataSynch.dao.AbstractDataSynchDaoJdbc;


/**
 * 类说明：网络资源--传输线路--光电接头盒:光电接头盒DaoJdbcImpl实现类
 * 创建人： zhangkeqi
 * 创建时间：2012-11-16 23:11:36
 */
public class IrmsTransOpconboxDaoJdbcImpl extends AbstractDataSynchDaoJdbc {
		/**
		 * 批量插入语句
		 */
		public String getBatchInsertSql(String table){
			return "insert into "+table+"_irms_trans_opconbox("+
						"id,"+
						"box_name,"+
						"box_type,"+
						"related_district,"+
						"box_object,"+
						"vendor,"+
						"status,"+
						"connect_way,"+
						"capacity,"+
						"remark,"+
						"create_time,"+
						"related_distirct_id,"+
						"box_object_id,"+
 						"inspect_id,"+
 						"inspect_name"+
					") values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?,?)";
		}
		
		/**
		 * 添加批量插入语句
		 */
		public PreparedStatement addPsBatch(PreparedStatement ps,Map<String,Object> data) throws Exception{
			/*主键*/
			ps.setString(1,StaticMethod.nullObject2String(data.get("id")));
			/*光电接头盒名称，作为业务主键，命名要求唯一。用于对配线光缆/电缆进行终接、保护和调度管理的设备，具有固定、熔接、余留盘绕、配线调度等功能。[例]重庆沙坪坝区逸园-石板光缆YS-35号杆/GZT1   重庆沙坪坝区逸园-石板光缆YS-102号杆/GT1    重庆沙坪坝区逸园基站/GZDH001*/
			ps.setString(2,StaticMethod.nullObject2String(data.get("box_name")));
			/*光电接头盒、光终端盒*/
			ps.setString(3,StaticMethod.nullObject2String(data.get("box_type")));
			/*关联到【空间资源】-【区域】表-【区域名称】*/
			ps.setString(4,StaticMethod.nullObject2String(data.get("related_district")));
			/*可以为机房、人手井、电杆。关联到【空间资源】-【机房】表，或者【人手井】表-【人手井名称】、或者【电杆】-【电杆名称】。*/
			ps.setString(5,StaticMethod.nullObject2String(data.get("box_object")));
			/*设备的供货厂家*/
			ps.setString(6,StaticMethod.nullObject2String(data.get("vendor")));
			/*设备当前所处状态，在下拉框中选择，包括：工程、在网、退网。*/
			ps.setString(7,StaticMethod.nullObject2String(data.get("status")));
			/*直通、分歧*/
			ps.setString(8,StaticMethod.nullObject2String(data.get("connect_way")));
			/*容量*/
			ps.setString(9,StaticMethod.nullObject2String(data.get("capacity")));
			/*备注*/
			ps.setString(10,StaticMethod.nullObject2String(data.get("remark")));
			/*创建时间*/
			ps.setString(11,StaticMethod.nullObject2String(data.get("create_time")));
			/*所属区域ID*/
			ps.setString(12,StaticMethod.nullObject2String(data.get("related_distirct_id")));
			/*所属对象ID*/
			ps.setString(13,StaticMethod.nullObject2String(data.get("box_object_id")));
			/*所属巡检点主键id*/
			ps.setString(14,StaticMethod.nullObject2String(data.get("inspect_id")));
			/*所属巡检点名称*/
			ps.setString(15,StaticMethod.nullObject2String(data.get("inspect_name")));
			
			ps.addBatch();
			
			return ps;
		}
}