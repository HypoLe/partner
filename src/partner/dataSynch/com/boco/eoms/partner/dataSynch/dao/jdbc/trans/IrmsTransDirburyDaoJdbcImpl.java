package com.boco.eoms.partner.dataSynch.dao.jdbc.trans;

import java.sql.PreparedStatement;
import java.util.Map;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.partner.dataSynch.dao.AbstractDataSynchDaoJdbc;


/**
 * 类说明：网络资源--传输线路--直埋:直埋DaoJdbcImpl实现类
 * 创建人： zhangkeqi
 * 创建时间：2012-11-16 23:11:36
 */
public class IrmsTransDirburyDaoJdbcImpl extends AbstractDataSynchDaoJdbc {
		/**
		 * 批量插入语句
		 */
		public String getBatchInsertSql(String table){
			return "insert into "+table+"_irms_trans_dirbury("+
						"id,"+
						"dirbury_name,"+
						"related_district,"+
						"status,"+
						"project_name,"+
						"length,"+
						"completed_date,"+
						"remark,"+
						"create_time,"+
						"related_distirct_id,"+
 						"inspect_id,"+
 						"inspect_name"+
					") values (?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?,?)";
		}
		
		/**
		 * 添加批量插入语句
		 */
		public PreparedStatement addPsBatch(PreparedStatement ps,Map<String,Object> data) throws Exception{
			/*主键*/
			ps.setString(1,StaticMethod.nullObject2String(data.get("id")));
			/*由多个标石组成的路由称为直埋，即标石路由.作为业务主键，命名要求唯一。[例]重庆彭水-黔江PQ标石路由*/
			ps.setString(2,StaticMethod.nullObject2String(data.get("dirbury_name")));
			/*关联到【空间资源】-【区域】表-【区域名称】*/
			ps.setString(3,StaticMethod.nullObject2String(data.get("related_district")));
			/*设备当前所处状态，未知、设计、在建、竣工、废弃、维护*/
			ps.setString(4,StaticMethod.nullObject2String(data.get("status")));
			/*对应工程名称。例：本地接入网12期。*/
			ps.setString(5,StaticMethod.nullObject2String(data.get("project_name")));
			/*直埋的长度。单位：米。例：12345。*/
			ps.setString(6,StaticMethod.nullObject2String(data.get("length")));
			/*竣工时间。例：2004-01-02*/
			ps.setString(7,StaticMethod.nullObject2String(data.get("completed_date")));
			/*备注*/
			ps.setString(8,StaticMethod.nullObject2String(data.get("remark")));
			/*创建时间*/
			ps.setString(9,StaticMethod.nullObject2String(data.get("create_time")));
			/*所属区域ID*/
			ps.setString(10,StaticMethod.nullObject2String(data.get("related_distirct_id")));
			/*所属巡检点主键id*/
			ps.setString(11,StaticMethod.nullObject2String(data.get("inspect_id")));
			/*所属巡检点名称*/
			ps.setString(12,StaticMethod.nullObject2String(data.get("inspect_name")));
			
			ps.addBatch();
			
			return ps;
		}
}