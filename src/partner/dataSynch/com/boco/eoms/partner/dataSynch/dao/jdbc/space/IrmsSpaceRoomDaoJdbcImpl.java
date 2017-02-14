package com.boco.eoms.partner.dataSynch.dao.jdbc.space;

import java.sql.PreparedStatement;
import java.util.Map;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.partner.dataSynch.dao.AbstractDataSynchDaoJdbc;


/**
 * 类说明：网络资源--空间资源--机房:机房DaoJdbcImpl实现类
 * 创建人： zhangkeqi
 * 创建时间：2012-11-16 23:11:36
 */
public class IrmsSpaceRoomDaoJdbcImpl extends AbstractDataSynchDaoJdbc {
		/**
		 * 批量插入语句
		 */
		public String getBatchInsertSql(String table){
			return "insert into "+table+"_irms_space_room("+
						"id,"+
						"room_name,"+
						"abbreviation,"+
						"room_alias,"+
						"room_type,"+
						"service_level,"+
						"related_site_name,"+
						"floor_num,"+
						"length,"+
						"width,"+
						"height,"+
						"rack_start_row_num,"+
						"rack_end_row_num,"+
						"rack_start_col_num,"+
						"rack_end_col_num,"+
						"rack_row_direct,"+
						"rack_column_direct,"+
						"is_construct_share,"+
						"build_togther_unit,"+
						"is_shared,"+
						"shared_unit,"+
						"remark,"+
						"create_time,"+
						"related_site_name_id,"+
 						"inspect_id,"+
 						"inspect_name"+
					") values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?,?)";
		}
		
		/**
		 * 添加批量插入语句
		 */
		public PreparedStatement addPsBatch(PreparedStatement ps,Map<String,Object> data) throws Exception{
			/*主键*/
			ps.setString(1,StaticMethod.nullObject2String(data.get("id")));
			/*机房名称*/
			ps.setString(2,StaticMethod.nullObject2String(data.get("room_name")));
			/*机房缩写*/
			ps.setString(3,StaticMethod.nullObject2String(data.get("abbreviation")));
			/*机房别名*/
			ps.setString(4,StaticMethod.nullObject2String(data.get("room_alias")));
			/*机房类型*/
			ps.setString(5,StaticMethod.nullObject2String(data.get("room_type")));
			/*传输业务级别*/
			ps.setString(6,StaticMethod.nullObject2String(data.get("service_level")));
			/*所属站点*/
			ps.setString(7,StaticMethod.nullObject2String(data.get("related_site_name")));
			/*所在楼层*/
			ps.setString(8,StaticMethod.nullObject2String(data.get("floor_num")));
			/*长*/
			ps.setString(9,StaticMethod.nullObject2String(data.get("length")));
			/*宽*/
			ps.setString(10,StaticMethod.nullObject2String(data.get("width")));
			/*高*/
			ps.setString(11,StaticMethod.nullObject2String(data.get("height")));
			/*机架起始行号*/
			ps.setString(12,StaticMethod.nullObject2String(data.get("rack_start_row_num")));
			/*机架终止行号*/
			ps.setString(13,StaticMethod.nullObject2String(data.get("rack_end_row_num")));
			/*机架起始列号*/
			ps.setString(14,StaticMethod.nullObject2String(data.get("rack_start_col_num")));
			/*机架终止列号*/
			ps.setString(15,StaticMethod.nullObject2String(data.get("rack_end_col_num")));
			/*机架行方向 枚举值：从东到西、从西到东、从南到北、从北到南
说明：对于核心生产楼的机房此项必填*/
			ps.setString(16,StaticMethod.nullObject2String(data.get("rack_row_direct")));
			/*机架列方向 机架列方向 枚举值：从东到西、从西到东、从南到北、从北到南
说明：对于核心生产楼的机房此项必填*/
			ps.setString(17,StaticMethod.nullObject2String(data.get("rack_column_direct")));
			/*是否共建 枚举值：【是】或者【否】*/
			ps.setString(18,StaticMethod.nullObject2String(data.get("is_construct_share")));
			/*共建单位*/
			ps.setString(19,StaticMethod.nullObject2String(data.get("build_togther_unit")));
			/*是否共享 枚举值：【是】或者【否】*/
			ps.setString(20,StaticMethod.nullObject2String(data.get("is_shared")));
			/*共享单位*/
			ps.setString(21,StaticMethod.nullObject2String(data.get("shared_unit")));
			/*备注*/
			ps.setString(22,StaticMethod.nullObject2String(data.get("remark")));
			/*创建时间*/
			ps.setString(23,StaticMethod.nullObject2String(data.get("create_time")));
			/*所属站点ID*/
			ps.setString(24,StaticMethod.nullObject2String(data.get("related_site_name_id")));
			/*所属巡检点主键id*/
			ps.setString(25,StaticMethod.nullObject2String(data.get("inspect_id")));
			/*所属巡检点名称*/
			ps.setString(26,StaticMethod.nullObject2String(data.get("inspect_name")));
			
			ps.addBatch();
			
			return ps;
		}
}