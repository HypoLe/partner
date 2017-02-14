package com.boco.eoms.partner.dataSynch.dao.jdbc.tower;

import java.sql.PreparedStatement;
import java.util.Map;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.partner.dataSynch.dao.AbstractDataSynchDaoJdbc;


/**
 * 类说明：网络资源--铁塔及天馈--天线:天线DaoJdbcImpl实现类
 * 创建人： zhangkeqi
 * 创建时间：2012-11-16 23:11:36
 */
public class IrmsTowerAntDaoJdbcImpl extends AbstractDataSynchDaoJdbc {
		/**
		 * 批量插入语句
		 */
		public String getBatchInsertSql(String table){
			return "insert into "+table+"_irms_tower_ant("+
						"id,"+
						"label_cn,"+
						"sum_angle,"+
						"related_cellid,"+
						"vendor,"+
						"high_cube,"+
						"ant_type,"+
						"maxazimuth_value,"+
						"adjust_angle_ele,"+
						"adjust_angle_mac,"+
						"install_time,"+
						"tian_mian,"+
						"remark,"+
						"create_time,"+
						"related_cellid_id,"+
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
			/***小区（小区网元命名）天线*/
			ps.setString(2,StaticMethod.nullObject2String(data.get("label_cn")));
			/*自动计算（电下倾+机械下倾）*/
			ps.setString(3,StaticMethod.nullObject2String(data.get("sum_angle")));
			/*关联属性，关联到【CELL】、【UTRANCELL】表网元名称，存在一根天线关联多个小区情况，用逗号分隔*/
			ps.setString(4,StaticMethod.nullObject2String(data.get("related_cellid")));
			/*国信,京信,广东通宇等*/
			ps.setString(5,StaticMethod.nullObject2String(data.get("vendor")));
			/*相对于地面的高度*/
			ps.setString(6,StaticMethod.nullObject2String(data.get("high_cube")));
			/*枚举{单频单极化、单频双极化,单频双极化电调,双频双极化,双频双极化电调}省公司根据需求自行改变*/
			ps.setString(7,StaticMethod.nullObject2String(data.get("ant_type")));
			/*方位角*/
			ps.setString(8,StaticMethod.nullObject2String(data.get("maxazimuth_value")));
			/*电调下倾角*/
			ps.setString(9,StaticMethod.nullObject2String(data.get("adjust_angle_ele")));
			/*机械下倾角*/
			ps.setString(10,StaticMethod.nullObject2String(data.get("adjust_angle_mac")));
			/*安装时间:yyyy-mm-dd*/
			ps.setString(11,StaticMethod.nullObject2String(data.get("install_time")));
			/*统一填天线所在平台，如1代表第一平台，2代表第2平台；桅杆如果不能区分天面情况填0*/
			ps.setString(12,StaticMethod.nullObject2String(data.get("tian_mian")));
			/*备注*/
			ps.setString(13,StaticMethod.nullObject2String(data.get("remark")));
			/*创建时间*/
			ps.setString(14,StaticMethod.nullObject2String(data.get("create_time")));
			/*所属小区ID*/
			ps.setString(15,StaticMethod.nullObject2String(data.get("related_cellid_id")));
			/*所属巡检点主键id*/
			ps.setString(16,StaticMethod.nullObject2String(data.get("inspect_id")));
			/*所属巡检点名称*/
			ps.setString(17,StaticMethod.nullObject2String(data.get("inspect_name")));
			
			ps.addBatch();
			
			return ps;
		}
}