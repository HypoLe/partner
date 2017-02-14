package com.boco.activiti.partner.process.dao.hibernate;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.boco.activiti.partner.process.dao.IRoomAssetsJDBCDao;
import com.boco.activiti.partner.process.po.AssetQueryConditionModel;
import com.boco.activiti.partner.process.po.RoomAssetsModel;

/**
 * 机房资产JDBCDAO实现类
 * @author WANGJUN
 *
 */
public class RoomAssetsDaoJDBC extends JdbcDaoSupport implements
		IRoomAssetsJDBCDao {
	
	/**
	 * 查询资产
	 * @param assetQueryConditionModel
	 * @param firstResult
	 * @param endResult
	 * @param pageSize
	 * @return
	 */
	public List<RoomAssetsModel> queryAssetsList(AssetQueryConditionModel assetQueryConditionModel,
			int firstResult, int endResult, int pageSize){		
		String sql = "select temp2.* from (select temp1.*, rownum num from (";
		String selectSql = "select * from room_demolition_assets a";
		String whereSql = " where 1=1";

		if (assetQueryConditionModel.getAssetNumbers() != null
				&& !assetQueryConditionModel.getAssetNumbers().equals("")) {
			whereSql += " and a.assets_number='"
					+ assetQueryConditionModel.getAssetNumbers()
					+ "'";
		}
		if (assetQueryConditionModel.getAssetName()!= null
				&& !assetQueryConditionModel.getAssetName().equals("")) {
			whereSql += " and a.assets_name like '%"
					+ assetQueryConditionModel.getAssetName()
					+ "%'";
		}
		if (assetQueryConditionModel.getAssetIds() != null
				&& !assetQueryConditionModel.getAssetIds().equals("")) {
			whereSql += " and a.id not in ("
					+ assetQueryConditionModel.getAssetIds()+ ")";
		}

		sql += selectSql + whereSql
				+ ") temp1 where rownum <="
				+ endResult * pageSize + ") temp2 where temp2.num > "
				+ firstResult * pageSize;
		
		System.out.println("--------------机房拆除流程 查询资产 集合sql=" + sql);
		List<RoomAssetsModel> r = new ArrayList<RoomAssetsModel>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql);
		for (Map map : list) {
			RoomAssetsModel model = new RoomAssetsModel();
			if (map.get("id") != null && !"".equals(map.get("id"))) {
				model.setId(map.get("id").toString());
			}
			
			if (map.get("ASSETS_NUMBER") != null && !"".equals(map.get("ASSETS_NUMBER"))) {
				
				model.setAssetNumbers(map.get("ASSETS_NUMBER").toString());
			}
			if (map.get("ASSETS_NAME") != null && !"".equals(map.get("ASSETS_NAME"))) {
				
				model.setAssetName(map.get("ASSETS_NAME").toString());
			}
			if (map.get("ASSESTS_SORT") != null && !"".equals(map.get("ASSESTS_SORT"))) {
				
				model.setAssetTagNumber(map.get("ASSESTS_SORT").toString());
			}
			r.add(model);
		}
		
		return r;
		
	}
	
	@Override
	public String[] getRoomAssetsByProcessInstanceId(
			String processInstanceId) {
		StringBuilder sql = new StringBuilder("select a.id,a.assets_number,a.assets_name, a.assests_sort, a.model_version, a.assets_start_date,");
		sql.append(" a.assets_month_num, a.original_value, a.accumulated_depreciation,a.assets_net,a.assets_devalue,");
		sql.append("  r.exit_direction from room_demolition_assets a, pnr_act_room_assets_relation r");
		sql.append(" where a.id = r.room_assets_id  and r.process_instance_id = '").append(processInstanceId).append("' order by r.order_code");
		
		
		System.out.println("------------------通过流程ID查询所对应的资产sql="+sql.toString());
		List<Map> list = this.getJdbcTemplate().queryForList(sql.toString());
		String[] str = new String[2];
		String newTr ="";
		String assetsIds = "";
		if(list!=null && list.size()>0){
			for(Map map:list){
				String exitDirectionStr="";
				String assetsId = map.get("ID").toString();
				assetsIds+=assetsId+",";
				//资产标签号
				String assetNumbers = map.get("assets_number")==null?"无":map.get("assets_number").toString();
				//资产名称
				String assetName = map.get("assets_name")==null?"无":map.get("assets_name").toString();
				//资产类别
				String assetsSort = map.get("assests_sort")==null?"无":map.get("assests_sort").toString();
				//设备型号
				String modelVersion = map.get("model_version")==null?"无":map.get("model_version").toString();
				//资产启用日期
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String assetsStartTime = map.get("assets_start_date")==null?"无":map.get("assets_start_date").toString();
				try{
					assetsStartTime = sdf.format(sdf.parse(assetsStartTime));
				}catch(Exception e){
					e.printStackTrace();
				}
				//资产使用月数（月）
				String assetsMonthNum = map.get("assets_month_num")==null?"无":map.get("assets_month_num").toString();
				//原值
				String originalValue = map.get("original_value")==null?"无":map.get("original_value").toString();
				//累计折旧
				String accumulatedDepreciation = map.get("accumulated_depreciation")==null?"无":map.get("accumulated_depreciation").toString();
				//资产净值
				String assetsNet = map.get("assets_net")==null?"无":map.get("assets_net").toString();
				//累计减值准备
				String assetsDevalue = map.get("assets_devalue")==null?"无":map.get("assets_devalue").toString();
				//退网使用方向
				String exitDirection = map.get("exit_direction")==null?"":map.get("exit_direction").toString();
				if("1270501".equals(exitDirection)){
					exitDirectionStr="<select onchange='setIdAndExitDirection(this)'><option value='1270501' selected=\"selected\">闲置-可利用</option><option value='1270502'>闲置-待报废</option></select>";
				}else if("1270502".equals(exitDirection)){
					exitDirectionStr="<select onchange='setIdAndExitDirection(this)'><option value='1270501'>闲置-可利用</option><option value='1270502' selected=\"selected\">闲置-待报废</option></select>";
				}
				
				newTr += "<tr>" +
						"<td><input type='hidden' name='hId' value='"+assetsId+"' />"+assetName+"</td>" +
						"<td>"+assetNumbers+"</td>" +
						"<td>"+assetsSort+"</td>" +
						"<td>"+modelVersion+"</td>" +
						"<td>"+assetsStartTime+"</td>" +
						"<td>"+assetsMonthNum+"</td>" +
						"<td>"+originalValue+"</td>" +
						"<td>"+accumulatedDepreciation+"</td>" +
						"<td>"+assetsNet+"</td>" +
						"<td>"+assetsDevalue+"</td>" +
						"<td>"+exitDirectionStr+"</td>" +
						"<td><a href='javascript:;' onclick='delRow(this)'>删除</a><input type='hidden' name='idAndExit' value='"+assetsId+","+exitDirection+"' /></td>" +
						"</tr>";
			}
			str[0] = assetsIds;
			str[1] = newTr;
		}
		
		return str;
	}
	
	public void deleteRoomAssetsByProcessInstanceId(String processInstanceId){
		String delSql = "delete from pnr_act_room_assets_relation where process_instance_id ='"+processInstanceId+"'";
		this.getJdbcTemplate().execute(delSql, new PreparedStatementCallback() {
			public Object doInPreparedStatement(PreparedStatement ps)
					throws SQLException, DataAccessException {
				ps.addBatch();
				return ps.executeBatch();
			}
		});
	
	}

	@Override
	public int queryAssetsListCount(
			AssetQueryConditionModel assetQueryConditionModel, int firstResult,
			int endResult, int pageSize) {
		// TODO Auto-generated method stub
		
		int num=0;
		String sql ="";// "select temp2.* from (select temp1.*, rownum num from (";
		String selectSql = "select count(*) num from room_demolition_assets a";
		String whereSql = " where 1=1";

		if (assetQueryConditionModel.getAssetNumbers() != null
				&& !assetQueryConditionModel.getAssetNumbers().equals("")) {
			whereSql += " and a.assets_number='"
					+ assetQueryConditionModel.getAssetNumbers()
					+ "'";
		}
		if (assetQueryConditionModel.getAssetName()!= null
				&& !assetQueryConditionModel.getAssetName().equals("")) {
			whereSql += " and a.assets_name like '%"
					+ assetQueryConditionModel.getAssetName()
					+ "%'";
		}
		if (assetQueryConditionModel.getAssetIds() != null
				&& !assetQueryConditionModel.getAssetIds().equals("")) {
			whereSql += " and a.id not in ("
					+ assetQueryConditionModel.getAssetIds()+ ")";
		}

		sql += selectSql + whereSql;
			/*	+ ") temp1 where rownum <="
				+ endResult * pageSize + ") temp2 where temp2.num > "
				+ firstResult * pageSize;*/
		
		System.out.println("--------------机房拆除流程 查询资产 集合sql=" + sql);
		List<RoomAssetsModel> r = new ArrayList<RoomAssetsModel>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql);
		for (Map map : list) {
			num = Integer.parseInt(map.get("num").toString());			
		}
		return num;
	}

}
