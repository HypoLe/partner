package com.boco.eoms.partner.dataSynch.dao.jdbc.bs;

import java.sql.PreparedStatement;
import java.util.Map;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.partner.dataSynch.dao.AbstractDataSynchDaoJdbc;


/**
 * 类说明：网络资源--基站设备及配套--机楼基站外电:机楼基站外电DaoJdbcImpl实现类
 * 创建人： zhangkeqi
 * 创建时间：2012-11-16 23:11:36
 */
public class IrmsBsJzwdDaoJdbcImpl extends AbstractDataSynchDaoJdbc {
		/**
		 * 批量插入语句
		 */
		public String getBatchInsertSql(String table){
			return "insert into "+table+"_irms_bs_jzwd("+
						"id,"+
						"related_province,"+
						"related_city,"+
						"related_county,"+
						"related_site,"+
						"related_site_name,"+
						"related_site_no,"+
						"property,"+
						"important_level,"+
						"room_addr,"+
						"one_substation,"+
						"two_substation,"+
						"preserver,"+
						"utility_cpacity,"+
						"utility_mode,"+
						"line,"+
						"cables_length,"+
						"elect_kind,"+
						"turn_info,"+
						"height,"+
						"longitude,"+
						"latitude,"+
						"elect_contract,"+
						"substation_tel,"+
						"owner_linkman,"+
						"query_linkman,"+
						"owner_linktel,"+
						"query_linktel,"+
						"power_maint,"+
						"trans_maint,"+
						"environment_maint,"+
						"fire_maint,"+
						"air_condition_main,"+
						"create_time,"+
						"remark,"+
						"related_site_id,"+
 						"inspect_id,"+
 						"inspect_name"+
					") values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?,?)";
		}
		
		/**
		 * 添加批量插入语句
		 */
		public PreparedStatement addPsBatch(PreparedStatement ps,Map<String,Object> data) throws Exception{
			/*主键*/
			ps.setString(1,StaticMethod.nullObject2String(data.get("id")));
			/*填写所属省份*/
			ps.setString(2,StaticMethod.nullObject2String(data.get("related_province")));
			/*填写所属省份地市*/
			ps.setString(3,StaticMethod.nullObject2String(data.get("related_city")));
			/*填写所属省份区县*/
			ps.setString(4,StaticMethod.nullObject2String(data.get("related_county")));
			/*关联到【空间资源】-【站点表】-【站点名称】*/
			ps.setString(5,StaticMethod.nullObject2String(data.get("related_site")));
			/*填写所属机楼（基站）名称*/
			ps.setString(6,StaticMethod.nullObject2String(data.get("related_site_name")));
			/*填写所属机楼（基站）编号*/
			ps.setString(7,StaticMethod.nullObject2String(data.get("related_site_no")));
			/*枚举值：自建、购买、租用、共建、共享*/
			ps.setString(8,StaticMethod.nullObject2String(data.get("property")));
			/*枚举值：通信机楼,综合机楼，非通信机楼，
超级基站，VVIP基站，VIP基站，普通基站*/
			ps.setString(9,StaticMethod.nullObject2String(data.get("important_level")));
			/*机楼、基站的详细地址信息*/
			ps.setString(10,StaticMethod.nullObject2String(data.get("room_addr")));
			/*市电一来自的变电站名称，节点基站不必填*/
			ps.setString(11,StaticMethod.nullObject2String(data.get("one_substation")));
			/*市电二来自的变电站名称*/
			ps.setString(12,StaticMethod.nullObject2String(data.get("two_substation")));
			/*移动公司该设备的负责人*/
			ps.setString(13,StaticMethod.nullObject2String(data.get("preserver")));
			/*向供电部门申请最终确定的容量*/
			ps.setString(14,StaticMethod.nullObject2String(data.get("utility_cpacity")));
			/*枚举值：地埋、架空*/
			ps.setString(15,StaticMethod.nullObject2String(data.get("utility_mode")));
			/*线径大小*/
			ps.setString(16,StaticMethod.nullObject2String(data.get("line")));
			/*线缆长度*/
			ps.setString(17,StaticMethod.nullObject2String(data.get("cables_length")));
			/*枚举值：转供、直供*/
			ps.setString(18,StaticMethod.nullObject2String(data.get("elect_kind")));
			/*转供单位及联系人*/
			ps.setString(19,StaticMethod.nullObject2String(data.get("turn_info")));
			/*机房绝对标高指机房地面以海平面为基准的标高(也称海拔标高)，一般使用珠江高程。如缺乏绝对标高资料，则用相对标高，即以机房室外地面为基准的标高，备注中说明。单位：m*/
			ps.setString(20,StaticMethod.nullObject2String(data.get("height")));
			/*基站机房的经度。填至度、分、秒。格式：XXX：XX:XXE*/
			ps.setString(21,StaticMethod.nullObject2String(data.get("longitude")));
			/*基站机房的纬度。填至度、分、秒。格式：XXX：XX:XXN*/
			ps.setString(22,StaticMethod.nullObject2String(data.get("latitude")));
			/*供电合同编号*/
			ps.setString(23,StaticMethod.nullObject2String(data.get("elect_contract")));
			/*变电站的联系电话*/
			ps.setString(24,StaticMethod.nullObject2String(data.get("substation_tel")));
			/*基站机房的业主联系人。*/
			ps.setString(25,StaticMethod.nullObject2String(data.get("owner_linkman")));
			/*基站机房的保安或者物业管理联系人。*/
			ps.setString(26,StaticMethod.nullObject2String(data.get("query_linkman")));
			/*基站机房的业主联系电话。*/
			ps.setString(27,StaticMethod.nullObject2String(data.get("owner_linktel")));
			/*基站机房的保安或者物业管理联系电话。*/
			ps.setString(28,StaticMethod.nullObject2String(data.get("query_linktel")));
			/*动力代维*/
			ps.setString(29,StaticMethod.nullObject2String(data.get("power_maint")));
			/*传输代维*/
			ps.setString(30,StaticMethod.nullObject2String(data.get("trans_maint")));
			/*环境代维*/
			ps.setString(31,StaticMethod.nullObject2String(data.get("environment_maint")));
			/*消防代维*/
			ps.setString(32,StaticMethod.nullObject2String(data.get("fire_maint")));
			/*空调代维*/
			ps.setString(33,StaticMethod.nullObject2String(data.get("air_condition_main")));
			/*创建时间*/
			ps.setString(34,StaticMethod.nullObject2String(data.get("create_time")));
			/*备注*/
			ps.setString(35,StaticMethod.nullObject2String(data.get("remark")));
			/*站点ID*/
			ps.setString(36,StaticMethod.nullObject2String(data.get("related_site_id")));
			/*所属巡检点主键id*/
			ps.setString(37,StaticMethod.nullObject2String(data.get("inspect_id")));
			/*所属巡检点名称*/
			ps.setString(38,StaticMethod.nullObject2String(data.get("inspect_name")));
			
			ps.addBatch();
			
			return ps;
		}
}