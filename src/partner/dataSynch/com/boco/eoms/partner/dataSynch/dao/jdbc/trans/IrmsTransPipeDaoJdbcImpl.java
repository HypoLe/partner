package com.boco.eoms.partner.dataSynch.dao.jdbc.trans;

import java.sql.PreparedStatement;
import java.util.Map;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.partner.dataSynch.dao.AbstractDataSynchDaoJdbc;


/**
 * 类说明：网络资源--传输线路--管道:管道DaoJdbcImpl实现类
 * 创建人： zhangkeqi
 * 创建时间：2012-11-16 23:11:36
 */
public class IrmsTransPipeDaoJdbcImpl extends AbstractDataSynchDaoJdbc {
		/**
		 * 批量插入语句
		 */
		public String getBatchInsertSql(String table){
			return "insert into "+table+"_irms_trans_pipe("+
						"id,"+
						"pipe_name,"+
						"related_area,"+
						"status,"+
						"project_name,"+
						"length,"+
						"complete_time,"+
						"remark,"+
						"create_time,"+
						"related_area_id,"+
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
			/*管道名称，作为业务主键，命名要求唯一。[例]重庆渝中区石桥铺-两路口SL管道 或者 重庆渝中区中山四路ZS管道*/
			ps.setString(2,StaticMethod.nullObject2String(data.get("pipe_name")));
			/*关联到【空间资源】-【区域】表-【区域名称】*/
			ps.setString(3,StaticMethod.nullObject2String(data.get("related_area")));
			/*设备当前所处状态，在下拉框中选择，枚举值：工程、现网、退网、空载。*/
			ps.setString(4,StaticMethod.nullObject2String(data.get("status")));
			/*管道对应的工程名称。例：本地接入网12期。*/
			ps.setString(5,StaticMethod.nullObject2String(data.get("project_name")));
			/*管道长度，单位：米。*/
			ps.setString(6,StaticMethod.nullObject2String(data.get("length")));
			/*竣工时间：yyyy-MM-dd*/
			ps.setString(7,StaticMethod.nullObject2String(data.get("complete_time")));
			/*备注*/
			ps.setString(8,StaticMethod.nullObject2String(data.get("remark")));
			/*创建时间*/
			ps.setString(9,StaticMethod.nullObject2String(data.get("create_time")));
			/*所属区域ID*/
			ps.setString(10,StaticMethod.nullObject2String(data.get("related_area_id")));
			/*所属巡检点主键id*/
			ps.setString(11,StaticMethod.nullObject2String(data.get("inspect_id")));
			/*所属巡检点名称*/
			ps.setString(12,StaticMethod.nullObject2String(data.get("inspect_name")));
			
			ps.addBatch();
			
			return ps;
		}
}