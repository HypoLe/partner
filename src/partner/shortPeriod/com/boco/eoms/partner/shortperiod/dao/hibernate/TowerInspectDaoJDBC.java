package com.boco.eoms.partner.shortperiod.dao.hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.partner.shortperiod.dao.ITowerInspectJDBCDao;
import com.boco.eoms.partner.shortperiod.po.TowerModel;
import com.boco.eoms.partner.shortperiod.po.TowerQueryConditionModel;

public class TowerInspectDaoJDBC extends JdbcDaoSupport implements
		ITowerInspectJDBCDao {
	
	public int getTowerCount(String userId, TowerQueryConditionModel towerQueryConditionModel){
		List<Object> paramList = new ArrayList<Object>();
		String sql = "";
		String selectSql =  "select count(a.id) as total" +
							"  from BACK_TOWER b" + 
							"   join TIETAJICHU a" + 
							"    on a.id = b.tower_id";
		String whereSql = "";
		//地市
		if(towerQueryConditionModel.getRegion() != null && !"".equals(towerQueryConditionModel.getRegion())){
			whereSql += " and b.city = ?";
			paramList.add(towerQueryConditionModel.getRegion());
		}
		
		//区县
		if(towerQueryConditionModel.getCountry()!= null && !"".equals(towerQueryConditionModel.getCountry())){
			whereSql += " and b.country = ?";
			paramList.add(towerQueryConditionModel.getCountry());
		}
		
		//站点名称
		if(towerQueryConditionModel.getResName()!= null && !"".equals(towerQueryConditionModel.getResName())){
			whereSql += " and instr(a.res_name,?)>0 ";
			paramList.add(towerQueryConditionModel.getResName().trim());
		}
		
		//产品业务确认编号
		if(towerQueryConditionModel.getConfirmNum()!= null && !"".equals(towerQueryConditionModel.getConfirmNum())){
			whereSql += " and a.id = ?";
			paramList.add(towerQueryConditionModel.getConfirmNum().trim());
		}
		
		//是否修改过
		if(towerQueryConditionModel.getIsModify() != null && !"".equals(towerQueryConditionModel.getIsModify())){
			if("0".equals(towerQueryConditionModel.getIsModify())){ //未修改过
				whereSql += " and b.last_modify_userid is null";
			}else if("1".equals(towerQueryConditionModel.getIsModify())){ //修改过
				whereSql += " and b.last_modify_userid is not null";
			}
		}
		
		//数据筛选条件
		if(towerQueryConditionModel.getDataFilter()!= null && !"".equals(towerQueryConditionModel.getDataFilter())){
			if("1".equals(towerQueryConditionModel.getDataFilter())){//无机房且有用户数(机房未移交、无机房)
				whereSql += " and　b.a3 in('1030706','1030703')  and b.a21 <>'0'";
			}else if("2".equals(towerQueryConditionModel.getDataFilter())){//非RRU拉远且存在铁塔机房（RRU拉远、是）
				whereSql += " and b.a3 <>'1030705' and b.a6 ='1030101'";
			}else if("3".equals(towerQueryConditionModel.getDataFilter())){//铁塔站址重复数据
				whereSql += " and a.name_id in (select name_id from tower_repeat_data)";
			}else if("4".equals(towerQueryConditionModel.getDataFilter())){//产品类型为其他
				whereSql += " and b.a2 not in(select dictid from taw_system_dicttype where parentdictid='10308')";
			}else if("5".equals(towerQueryConditionModel.getDataFilter())){//机房类型为其他
				whereSql += " and b.a3 not in(select dictid from taw_system_dicttype where parentdictid='10307')";
			}else if("6".equals(towerQueryConditionModel.getDataFilter())){//天线挂高为其他
				whereSql += " and b.a5 not in(select dictid from taw_system_dicttype where parentdictid='10310')";
			}
		}
		
		//产品类型（新）
		if(towerQueryConditionModel.getNewProductType()!= null && !"".equals(towerQueryConditionModel.getNewProductType())){
			whereSql += " and b.a2 = ?";
			paramList.add(towerQueryConditionModel.getNewProductType());
		}
		
		//机房类型（新）
		if(towerQueryConditionModel.getNewRoomType()!= null && !"".equals(towerQueryConditionModel.getNewRoomType())){
			whereSql += " and b.a3 = ?";
			paramList.add(towerQueryConditionModel.getNewRoomType());
		}
		
		//天线挂高（新）
		if(towerQueryConditionModel.getNewAntennaHeight()!= null && !"".equals(towerQueryConditionModel.getNewAntennaHeight())){
			whereSql += " and b.a5 = ?";
			paramList.add(towerQueryConditionModel.getNewAntennaHeight());
		}
		
		//产品类型（旧）
		if(towerQueryConditionModel.getOldProductTypeName()!= null && !"".equals(towerQueryConditionModel.getOldProductTypeName())){
			whereSql += " and a.a2 = ?";
			paramList.add(towerQueryConditionModel.getOldProductTypeName());
		}
		
		//机房类型（旧）
		if(towerQueryConditionModel.getOldRoomTypeName()!= null && !"".equals(towerQueryConditionModel.getOldRoomTypeName())){
			whereSql += " and a.a3 = ?";
			paramList.add(towerQueryConditionModel.getOldRoomTypeName());
		}
		
		//天线挂高（旧）
		if(towerQueryConditionModel.getOldAntennaHeightName()!= null && !"".equals(towerQueryConditionModel.getOldAntennaHeightName())){
			whereSql += " and a.a5 = ?";
			paramList.add(towerQueryConditionModel.getOldAntennaHeightName());
		}
		
		sql += selectSql + whereSql;
		System.out.println("-------------铁塔数="+sql);
		Object[] args = paramList.toArray();
		int size = this.getJdbcTemplate().queryForInt(sql,args);
		return size;
	}

	public List<TowerModel> getTowerList(String userId,TowerQueryConditionModel towerQueryConditionModel,
			int firstResult, int endResult, int pageSize){
		List<Object> paramList = new ArrayList<Object>();
		String sql = "select temp2.* from (select temp1.*, rownum num from (";
		String selectSql =  "select a.id as product_num, -- 产品业务确认单编号,\n" +
							"       a.res_name, --站点名称,\n" + 
							"       a.city, -- 地市,\n" + 
							"       a.country, -- 区县,\n" + 
							"       a.name_id, -- 站址编码,\n" + 
							"       a.name_number, --需求确认单编号,\n" + 
							"       a.a2, --产品类型,\n" + 
							"       a.a3, --机房类型,\n" + 
							"       a.a4, --产品单元数1,\n" + 
							"       a.a5, --对应实际最高天线挂高1,\n" + 
							"       a.a6, --BBU是否放在铁塔公司机房1,\n" + 
							"       a.a8, -- 产品单元数2,\n" + 
							"       a.a9, --  实际最高天线挂高2,\n" + 
							"       a.a10, --BBU是否放在铁塔公司机房2,\n" + 
							"       a.a12, -- 产品单元数3,\n" + 
							"       a.a13, -- 实际最高天线挂高3,\n" + 
							"       a.a14, --  BBU是否放在铁塔公司机房3,\n" + 
							"       a.a16, -- 期末铁塔共享用户数,\n" + 
							"       a.a21, --  期末机房共享用户数,\n" + 
							"       a.a26, --  配套共享用户数,\n" + 
							"       a.a35, --  场地费共享用户数,\n" + 
							"       a.a41, --  电力引入费共享用户数,\n" + 
							"       a.a48,         --维护费共享用户数,\n" + 
							"       b.res_name as new_res_name, --站点名称,\n" + 
							"       b.a2       as new_a2, -- 产品类型,\n" + 
							"       b.a3       as new_a3, --  机房类型,\n" + 
							"       b.a4       as new_a4, --产品单元数1,\n" + 
							"       b.a5       as new_a5, --对应实际最高天线挂高1,\n" + 
							"       b.a6       as new_a6, --BBU是否放在铁塔公司机房1,\n" + 
							"       b.a8       as new_a8, --产品单元数2,\n" + 
							"       b.a9       as new_a9, --实际最高天线挂高2,\n" + 
							"       b.a10      as new_a10, --BBU是否放在铁塔公司机房2,\n" + 
							"       b.a12      as new_a12, --产品单元数3,\n" + 
							"       b.a13      as new_a13, --实际最高天线挂高3,\n" + 
							"       b.a14      as new_a14, --BBU是否放在铁塔公司机房3,\n" + 
							"       b.a16      as new_a16, --期末铁塔共享用户数,\n" + 
							"       b.a21      as new_a21, --期末机房共享用户数,\n" + 
							"       b.a26      as new_a26, --配套共享用户数,\n" + 
							"       b.a31      as new_a31, --维护费共享用户数,\n" + 
							"       b.a36      as new_a36, --场地费共享用户数,\n" + 
							"       b.a41      as new_a41, --电力引入费共享用户数\n" + 
							"      b.a17         as new_a17, --铁塔共享运营商1的起租日期\n" +
							"      b.a18         as new_a18, --铁塔共享运营商1起租后的共享折扣\n" + 
							"      b.a19         as new_a19, --铁塔共享运营商2的起租日期\n" + 
							"      b.a20         as new_a20, -- 铁塔共享运营商2起租后的共享折扣\n" + 
							"      b.a22         as new_a22, --机房共享运营商1的起租日期\n" + 
							"      b.a23         as new_a23, --机房共享运营商1起租后的共享折扣\n" + 
							"      b.a24         as new_a24, --机房共享运营商2的起租日期\n" + 
							"      b.a25         as new_a25, --机房共享运营商2起租后的共享折扣\n" + 
							"      b.a27         as new_a27, --配套共享运营商1的起租日期\n" + 
							"      b.a28         as new_a28, --配套共享运营商1起租后的共享折扣\n" + 
							"      b.a29         as new_a29, --配套共享运营商2的起租日期\n" + 
							"      b.a30         as new_a30, --配套共享运营商2起租后的共享折扣\n" + 
							"      b.a32         as new_a32, --维护费共享运营商1的起租日期\n" + 
							"      b.a33         as new_a33, --维护费共享运营商1起租后的共享折扣\n" + 
							"      b.a34         as new_a34, --维护费共享运营商2的起租日期\n" + 
							"      b.a35         as new_a35, --维护费共享运营商2起租后的共享折扣\n" + 
							"      b.a48         as new_a48, --场地费\n" + 
							"      b.a49         as new_a49, --电力引入费\n" + 
							"      b.a37         as new_a37, --场地费共享运营商1的起租日期\n" + 
							"      b.a38         as new_a38, --场地费共享运营商1起租后的共享折扣\n" + 
							"      b.a39         as new_a39, --场地费共享运营商2的起租日期\n" + 
							"      b.a40         as new_a40, --场地费共享运营商2起租后的共享折扣\n" + 
							"      b.a42         as new_a42, --电力引入费共享运营商1的起租日期\n" + 
							"      b.a43         as new_a43, --电力引入费共享运营商1起租后的共享折扣\n" + 
							"      b.a44         as new_a44, --电力引入费共享运营商2的起租日期\n" + 
							"      b.a45         as new_a45, --电力引入费共享运营商2起租后的共享折扣\n"+
							"      b.tower_remark as towerRemark, --备注\n"+
							"      b.tower_describe as towerDescribe --描述\n"+
							"  from BACK_TOWER b\n" + 
							"   join TIETAJICHU a\n" + 
							"    on a.id = b.tower_id";
		String whereSql = "";
		//地市
		if(towerQueryConditionModel.getRegion() != null && !"".equals(towerQueryConditionModel.getRegion())){
			whereSql += " and b.city = ?";
			paramList.add(towerQueryConditionModel.getRegion());
		}
		
		//区县
		if(towerQueryConditionModel.getCountry()!= null && !"".equals(towerQueryConditionModel.getCountry())){
			whereSql += " and b.country = ?";
			paramList.add(towerQueryConditionModel.getCountry());
		}
		
		//站点名称
		if(towerQueryConditionModel.getResName()!= null && !"".equals(towerQueryConditionModel.getResName())){
			whereSql += " and instr(a.res_name,?)>0 ";
			paramList.add(towerQueryConditionModel.getResName().trim());
		}
		
		//产品业务确认编号
		if(towerQueryConditionModel.getConfirmNum()!= null && !"".equals(towerQueryConditionModel.getConfirmNum())){
			whereSql += " and a.id = ?";
			paramList.add(towerQueryConditionModel.getConfirmNum().trim());
		}
		
		//是否修改过
		if(towerQueryConditionModel.getIsModify() != null && !"".equals(towerQueryConditionModel.getIsModify())){
			if("0".equals(towerQueryConditionModel.getIsModify())){ //未修改过
				whereSql += " and b.last_modify_userid is null";
			}else if("1".equals(towerQueryConditionModel.getIsModify())){ //修改过
				whereSql += " and b.last_modify_userid is not null";
			}
		}
		
		//数据筛选条件
		if(towerQueryConditionModel.getDataFilter()!= null && !"".equals(towerQueryConditionModel.getDataFilter())){
			if("1".equals(towerQueryConditionModel.getDataFilter())){//无机房且有用户数
				whereSql += " and　b.a3 in('1030706','1030703')  and b.a21 <>'0'";
			}else if("2".equals(towerQueryConditionModel.getDataFilter())){//非RRU拉远且存在铁塔机房
				whereSql += " and b.a3 <>'1030705' and b.a6 ='1030101'";
			}else if("3".equals(towerQueryConditionModel.getDataFilter())){//铁塔站址重复数据
				whereSql += " and a.name_id in (select name_id from tower_repeat_data)";
			}else if("4".equals(towerQueryConditionModel.getDataFilter())){//产品类型为其他
				whereSql += " and b.a2 not in(select dictid from taw_system_dicttype where parentdictid='10308')";
			}else if("5".equals(towerQueryConditionModel.getDataFilter())){//机房类型为其他
				whereSql += " and b.a3 not in(select dictid from taw_system_dicttype where parentdictid='10307')";
			}else if("6".equals(towerQueryConditionModel.getDataFilter())){//天线挂高为其他
				whereSql += " and b.a5 not in(select dictid from taw_system_dicttype where parentdictid='10310')";
			}
		}
		
		//产品类型（新）
		if(towerQueryConditionModel.getNewProductType()!= null && !"".equals(towerQueryConditionModel.getNewProductType())){
			whereSql += " and b.a2 = ?";
			paramList.add(towerQueryConditionModel.getNewProductType());
		}
		
		//机房类型（新）
		if(towerQueryConditionModel.getNewRoomType()!= null && !"".equals(towerQueryConditionModel.getNewRoomType())){
			whereSql += " and b.a3 = ?";
			paramList.add(towerQueryConditionModel.getNewRoomType());
		}
		
		//天线挂高（新）
		if(towerQueryConditionModel.getNewAntennaHeight()!= null && !"".equals(towerQueryConditionModel.getNewAntennaHeight())){
			whereSql += " and b.a5 = ?";
			paramList.add(towerQueryConditionModel.getNewAntennaHeight());
		}
		
		//产品类型（旧）
		if(towerQueryConditionModel.getOldProductTypeName()!= null && !"".equals(towerQueryConditionModel.getOldProductTypeName())){
			whereSql += " and a.a2 = ?";
			paramList.add(towerQueryConditionModel.getOldProductTypeName());
		}
		
		//机房类型（旧）
		if(towerQueryConditionModel.getOldRoomTypeName()!= null && !"".equals(towerQueryConditionModel.getOldRoomTypeName())){
			whereSql += " and a.a3 = ?";
			paramList.add(towerQueryConditionModel.getOldRoomTypeName());
		}
		
		//天线挂高（旧）
		if(towerQueryConditionModel.getOldAntennaHeightName()!= null && !"".equals(towerQueryConditionModel.getOldAntennaHeightName())){
			whereSql += " and a.a5 = ?";
			paramList.add(towerQueryConditionModel.getOldAntennaHeightName());
		}
		
		sql += selectSql+ whereSql+ " ) temp1 where rownum <=?) temp2 where temp2.num >? ";
		System.out.println("-------------铁塔列表="+sql);
		paramList.add(endResult * pageSize);
		paramList.add(firstResult * pageSize);
		Object[] args = paramList.toArray();		
		//SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<TowerModel> r = new ArrayList<TowerModel>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql,args);
		for (Map map : list) {
			TowerModel model = new TowerModel();
			if (map.get("product_num") != null && !"".equals(map.get("product_num").toString())) {
				model.setProductNum(map.get("product_num").toString());
			}
			if (map.get("res_name") != null && !"".equals(map.get("res_name").toString())) {
				model.setResName(map.get("res_name").toString());
			}
			if (map.get("city") != null && !"".equals(map.get("city").toString())) {
				model.setCityName(map.get("city").toString());
			}
			if (map.get("country") != null && !"".equals(map.get("country").toString())) {
				model.setCountryName(map.get("country").toString());
			}
			if (map.get("name_id") != null && !"".equals(map.get("name_id").toString())) {
				model.setNameId(map.get("name_id").toString());
			}
			if (map.get("name_number") != null && !"".equals(map.get("name_number").toString())) {
				model.setNameNumber(map.get("name_number").toString());
			}
			
			if (map.get("a2") != null && !"".equals(map.get("a2").toString())) {
				model.setA2(map.get("a2").toString());
			}
			if (map.get("a3") != null && !"".equals(map.get("a3").toString())) {
				model.setA3(map.get("a3").toString());
			}
			if (map.get("a4") != null && !"".equals(map.get("a4").toString())) {
				model.setA4(map.get("a4").toString());
			}
			if (map.get("a5") != null && !"".equals(map.get("a5").toString())) {
				model.setA5(map.get("a5").toString());
			}
			if (map.get("a6") != null && !"".equals(map.get("a6").toString())) {
				model.setA6(map.get("a6").toString());
			}
			if (map.get("a8") != null && !"".equals(map.get("a8").toString())) {
				model.setA8(map.get("a8").toString());
			}
			if (map.get("a9") != null && !"".equals(map.get("a9").toString())) {
				model.setA9(map.get("a9").toString());
			}
			if (map.get("a10") != null && !"".equals(map.get("a10").toString())) {
				model.setA10(map.get("a10").toString());
			}
			if (map.get("a12") != null && !"".equals(map.get("a12").toString())) {
				model.setA12(map.get("a12").toString());
			}
			if (map.get("a13") != null && !"".equals(map.get("a13").toString())) {
				model.setA13(map.get("a13").toString());
			}
			if (map.get("a14") != null && !"".equals(map.get("a14").toString())) {
				model.setA14(map.get("a14").toString());
			}
			if (map.get("a16") != null && !"".equals(map.get("a16").toString())) {
				model.setA16(map.get("a16").toString());
			}
			if (map.get("a21") != null && !"".equals(map.get("a21").toString())) {
				model.setA21(map.get("a21").toString());
			}
			if (map.get("a26") != null && !"".equals(map.get("a26").toString())) {
				model.setA26(map.get("a26").toString());
			}
			if (map.get("a35") != null && !"".equals(map.get("a35").toString())) {
				model.setA35(map.get("a35").toString());
			}
			if (map.get("a41") != null && !"".equals(map.get("a41").toString())) {
				model.setA41(map.get("a41").toString());
			}
			if (map.get("a48") != null && !"".equals(map.get("a48").toString())) {
				model.setA48(map.get("a48").toString());
			}
			
			if (map.get("new_res_name") != null && !"".equals(map.get("new_res_name").toString())) {
				model.setNewResName(map.get("new_res_name").toString());
			}
			
			if (map.get("new_a2") != null && !"".equals(map.get("new_a2").toString())) {
				model.setNewa2(map.get("new_a2").toString());
			}
			if (map.get("new_a3") != null && !"".equals(map.get("new_a3").toString())) {
				model.setNewa3(map.get("new_a3").toString());
			}
			if (map.get("new_a4") != null && !"".equals(map.get("new_a4").toString())) {
				model.setNewa4(map.get("new_a4").toString());
			}
			if (map.get("new_a5") != null && !"".equals(map.get("new_a5").toString())) {
				model.setNewa5(map.get("new_a5").toString());
			}
			if (map.get("new_a6") != null && !"".equals(map.get("new_a6").toString())) {
				model.setNewa6(map.get("new_a6").toString());
			}
			if (map.get("new_a8") != null && !"".equals(map.get("new_a8").toString())) {
				model.setNewa8(map.get("new_a8").toString());
			}
			if (map.get("new_a9") != null && !"".equals(map.get("new_a9").toString())) {
				model.setNewa9(map.get("new_a9").toString());
			}
			if (map.get("new_a10") != null && !"".equals(map.get("new_a10").toString())) {
				model.setNewa10(map.get("new_a10").toString());
			}
			if (map.get("new_a12") != null && !"".equals(map.get("new_a12").toString())) {
				model.setNewa12(map.get("new_a12").toString());
			}
		
			if (map.get("new_a13") != null && !"".equals(map.get("new_a13").toString())) {
				model.setNewa13(map.get("new_a13").toString());
			}
			if (map.get("new_a14") != null && !"".equals(map.get("new_a14").toString())) {
				model.setNewa14(map.get("new_a14").toString());
			}
			if (map.get("new_a16") != null && !"".equals(map.get("new_a16").toString())) {
				model.setNewa16(map.get("new_a16").toString());
			}
			if (map.get("new_a21") != null && !"".equals(map.get("new_a21").toString())) {
				model.setNewa21(map.get("new_a21").toString());
			}
			if (map.get("new_a26") != null && !"".equals(map.get("new_a26").toString())) {
				model.setNewa26(map.get("new_a26").toString());
			}
			if (map.get("new_a31") != null && !"".equals(map.get("new_a31").toString())) {
				model.setNewa31(map.get("new_a31").toString());
			}
			if (map.get("new_a36") != null && !"".equals(map.get("new_a36").toString())) {
				model.setNewa36(map.get("new_a36").toString());
			}
			if (map.get("new_a41") != null && !"".equals(map.get("new_a41").toString())) {
				model.setNewa41(map.get("new_a41").toString());
			}
			if (map.get("new_a17") != null && !"".equals(map.get("new_a17").toString())) {//铁塔共享运营商1的起租日期
				model.setNewa17(map.get("new_a17").toString());
			}
			if (map.get("new_a18") != null && !"".equals(map.get("new_a18").toString())) {//铁塔共享运营商1起租后的共享折扣
				model.setNewa18(map.get("new_a18").toString());
			}
			if (map.get("new_a19") != null && !"".equals(map.get("new_a19").toString())) {//铁塔共享运营商2的起租日期
				model.setNewa19(map.get("new_a19").toString());
			}
			if (map.get("new_a20") != null && !"".equals(map.get("new_a20").toString())) {//铁塔共享运营商2起租后的共享折扣
				model.setNewa20(map.get("new_a20").toString());
			}
			if (map.get("new_a22") != null && !"".equals(map.get("new_a22").toString())) {//机房共享运营商1的起租日期
				model.setNewa22(map.get("new_a22").toString());
			}
			if (map.get("new_a23") != null && !"".equals(map.get("new_a23").toString())) {//机房共享运营商1起租后的共享折扣
				model.setNewa23(map.get("new_a23").toString());
			}
			if (map.get("new_a24") != null && !"".equals(map.get("new_a24").toString())) {//机房共享运营商2的起租日期
				model.setNewa24(map.get("new_a24").toString());
			}
			if (map.get("new_a25") != null && !"".equals(map.get("new_a25").toString())) {//机房共享运营商2起租后的共享折扣
				model.setNewa25(map.get("new_a25").toString());
			}
			if (map.get("new_a27") != null && !"".equals(map.get("new_a27").toString())) {//配套共享运营商1的起租日期
				model.setNewa27(map.get("new_a27").toString());
			}
			if (map.get("new_a28") != null && !"".equals(map.get("new_a28").toString())) {//配套共享运营商1起租后的共享折扣
				model.setNewa28(map.get("new_a28").toString());
			}
			if (map.get("new_a29") != null && !"".equals(map.get("new_a29").toString())) {//配套共享运营商2的起租日期
				model.setNewa29(map.get("new_a29").toString());
			}
			if (map.get("new_a30") != null && !"".equals(map.get("new_a30").toString())) {//配套共享运营商2起租后的共享折扣
				model.setNewa30(map.get("new_a30").toString());
			} 
			if (map.get("new_a32") != null && !"".equals(map.get("new_a32").toString())) {//维护费共享运营商1的起租日期
				model.setNewa32(map.get("new_a32").toString());
			} 
			if (map.get("new_a33") != null && !"".equals(map.get("new_a33").toString())) {//维护费共享运营商1起租后的共享折扣
				model.setNewa33(map.get("new_a33").toString());
			} 
			if (map.get("new_a34") != null && !"".equals(map.get("new_a34").toString())) {//维护费共享运营商2的起租日期
				model.setNewa34(map.get("new_a34").toString());
			}
			if (map.get("new_a35") != null && !"".equals(map.get("new_a35").toString())) {//维护费共享运营商2起租后的共享折扣
				model.setNewa35(map.get("new_a35").toString());
			}
			if (map.get("new_a48") != null && !"".equals(map.get("new_a48").toString())) {//场地费
				model.setNewa48(map.get("new_a48").toString());
			} 
			if (map.get("new_a49") != null && !"".equals(map.get("new_a49").toString())) {//电力引入费
				model.setNewa49(map.get("new_a49").toString());
			} 
			if (map.get("new_a37") != null && !"".equals(map.get("new_a37").toString())) {//场地费共享运营商1的起租日期
				model.setNewa37(map.get("new_a37").toString());
			} 
			if (map.get("new_a38") != null && !"".equals(map.get("new_a38").toString())) {//场地费共享运营商1起租后的共享折扣
				model.setNewa38(map.get("new_a38").toString());
			} 
			if (map.get("new_a39") != null && !"".equals(map.get("new_a39").toString())) {//场地费共享运营商2的起租日期
				model.setNewa39(map.get("new_a39").toString());
			}  
			if (map.get("new_a40") != null && !"".equals(map.get("new_a40").toString())) {//场地费共享运营商2起租后的共享折扣
				model.setNewa40(map.get("new_a40").toString());
			}
			if (map.get("new_a42") != null && !"".equals(map.get("new_a42").toString())) {//电力引入费共享运营商1的起租日期
				model.setNewa42(map.get("new_a42").toString());
			}
			if (map.get("new_a43") != null && !"".equals(map.get("new_a43").toString())) {//电力引入费共享运营商1起租后的共享折扣
				model.setNewa43(map.get("new_a43").toString());
			}
			if (map.get("new_a44") != null && !"".equals(map.get("new_a44").toString())) {//电力引入费共享运营商2的起租日期
				model.setNewa44(map.get("new_a44").toString());
			} 
			if (map.get("new_a45") != null && !"".equals(map.get("new_a45").toString())) {//电力引入费共享运营商2的起租日期
				model.setNewa45(map.get("new_a45").toString());
			}
			if (map.get("towerRemark") != null && !"".equals(map.get("towerRemark").toString())) {//备注
				model.setTowerRemark(map.get("towerRemark").toString());
			} 
			if (map.get("towerDescribe") != null && !"".equals(map.get("towerDescribe").toString())) {//描述
				model.setTowerDescribe(map.get("towerDescribe").toString());
			} 
			r.add(model);
		}
		
		return r;
	
	}
	
	/**
	 * 铁塔统计数(铁塔核查20170210) 
	 * @param userId
	 * @param towerQueryConditionModel
	 * @return
	 */
	public int getTowerNewCount(String areaId,String userId,TowerQueryConditionModel towerQueryConditionModel){
		List<Object> paramList = new ArrayList<Object>();
		String sql = "";
		String selectSql =  "select count(z.product_no) as total\n" +
							"    from zhzy_tower z, BACK_TOWER b\n" + 
							"   where z.product_no = b.tower_id\n" + 
							"     and z.data_flag = '1'  "+
							"     and b.last_modify_userid is null ";
		String whereSql = "";
		
		// 当前登录人的区域权限
		if(areaId != null && !"".equals(areaId)){
//			if((StaticMethod.nullObject2String(areaid)).length()==2){
//				
//			}else
			if((StaticMethod.nullObject2String(areaId)).length()==4){
				whereSql += " and z.city_id = ? ";
				paramList.add(areaId);
			}else if((StaticMethod.nullObject2String(areaId)).length()==6){
				whereSql += " and z.area_id = ? ";
				paramList.add(areaId);
			}
		}
		
		//地市
		if(towerQueryConditionModel.getRegion() != null && !"".equals(towerQueryConditionModel.getRegion())){
			whereSql += " and z.city_id = ?";
			paramList.add(towerQueryConditionModel.getRegion());
		}
		
		//区县
		if(towerQueryConditionModel.getCountry()!= null && !"".equals(towerQueryConditionModel.getCountry())){
			whereSql += " and z.area_id = ?";
			paramList.add(towerQueryConditionModel.getCountry());
		}
		
		//站点名称
		if(towerQueryConditionModel.getResName()!= null && !"".equals(towerQueryConditionModel.getResName())){
			whereSql += " and instr(z.station_name,?)>0 ";
			paramList.add(towerQueryConditionModel.getResName().trim());
		}
		
		//产品业务确认编号
		if(towerQueryConditionModel.getConfirmNum()!= null && !"".equals(towerQueryConditionModel.getConfirmNum())){
			whereSql += " and z.product_no = ?";
			paramList.add(towerQueryConditionModel.getConfirmNum().trim());
		}
//		
//		//是否修改过
//		if(towerQueryConditionModel.getIsModify() != null && !"".equals(towerQueryConditionModel.getIsModify())){
//			if("0".equals(towerQueryConditionModel.getIsModify())){ //未修改过
//				whereSql += " and b.last_modify_userid is null";
//			}else if("1".equals(towerQueryConditionModel.getIsModify())){ //修改过
//				whereSql += " and b.last_modify_userid is not null";
//			}
//		}
//		
//		//数据筛选条件
//		if(towerQueryConditionModel.getDataFilter()!= null && !"".equals(towerQueryConditionModel.getDataFilter())){
//			if("1".equals(towerQueryConditionModel.getDataFilter())){//无机房且有用户数
//				whereSql += " and　b.a3 in('1030706','1030703')  and b.a21 <>'0'";
//			}else if("2".equals(towerQueryConditionModel.getDataFilter())){//非RRU拉远且存在铁塔机房
//				whereSql += " and b.a3 <>'1030705' and b.a6 ='1030101'";
//			}else if("3".equals(towerQueryConditionModel.getDataFilter())){//铁塔站址重复数据
//				whereSql += " and z.station_code in (select name_id from tower_repeat_data)";
//			}else if("4".equals(towerQueryConditionModel.getDataFilter())){//产品类型为其他
//				whereSql += " and b.a2 not in(select dictid from taw_system_dicttype where parentdictid='10308')";
//			}else if("5".equals(towerQueryConditionModel.getDataFilter())){//机房类型为其他
//				whereSql += " and b.a3 not in(select dictid from taw_system_dicttype where parentdictid='10307')";
//			}else if("6".equals(towerQueryConditionModel.getDataFilter())){//天线挂高为其他
//				whereSql += " and b.a5 not in(select dictid from taw_system_dicttype where parentdictid='10310')";
//			}
//		}
//		
		//产品类型（新）
		if(towerQueryConditionModel.getNewProductType()!= null && !"".equals(towerQueryConditionModel.getNewProductType())){
			whereSql += " and b.a2 = ?";
			paramList.add(towerQueryConditionModel.getNewProductType());
		}
		
		//机房类型（新）
		if(towerQueryConditionModel.getNewRoomType()!= null && !"".equals(towerQueryConditionModel.getNewRoomType())){
			whereSql += " and b.a3 = ?";
			paramList.add(towerQueryConditionModel.getNewRoomType());
		}
		
		//天线挂高（新）
		if(towerQueryConditionModel.getNewAntennaHeight()!= null && !"".equals(towerQueryConditionModel.getNewAntennaHeight())){
			whereSql += " and b.a5 = ?";
			paramList.add(towerQueryConditionModel.getNewAntennaHeight());
		}
		
		//产品类型（旧）
		if(towerQueryConditionModel.getOldProductTypeName()!= null && !"".equals(towerQueryConditionModel.getOldProductTypeName())){
			whereSql += " and z.tower_type = ?";
			paramList.add(towerQueryConditionModel.getOldProductTypeName());
		}
		
		//机房类型（旧）
		if(towerQueryConditionModel.getOldRoomTypeName()!= null && !"".equals(towerQueryConditionModel.getOldRoomTypeName())){
			whereSql += " and z.room_type = ?";
			paramList.add(towerQueryConditionModel.getOldRoomTypeName());
		}
		
		//天线挂高（旧）
		if(towerQueryConditionModel.getOldAntennaHeightName()!= null && !"".equals(towerQueryConditionModel.getOldAntennaHeightName())){
			whereSql += " and z.hang_high_1 = ?";
			paramList.add(towerQueryConditionModel.getOldAntennaHeightName());
		}
		
		sql += selectSql + whereSql;
		System.out.println("-------------铁塔数="+sql);
		Object[] args = paramList.toArray();
		int size = this.getJdbcTemplate().queryForInt(sql,args);
		return size;
	}

	/**
	 * 铁塔详情(铁塔核查20170210)
	 * @param userId
	 * @param towerQueryConditionModel
	 * @param firstResult
	 * @param endResult
	 * @param pageSize
	 * @return
	 */
	public List<TowerModel> getTowerNewList(String areaId,String userId,TowerQueryConditionModel towerQueryConditionModel,int firstResult, int endResult, int pageSize){
		List<Object> paramList = new ArrayList<Object>();
		String sql = "";
		if(pageSize != -1){ //查询和导出共用
			 sql = "select temp2.* from (select temp1.*, rownum num from (";
		}
		String selectSql =  "select z.product_no, --产品业务确认单编号\n" +
							"       z.station_name, --站址名称\n" + 
							"       z.city_id, --地市\n" + 
							"       z.area_id, --铁塔所属区县\n" + 
							"       z.station_code, --站址编码\n" + 
							"       z.need_no, --需求确认单编号\n" + 
							"       z.tower_type, --塔型\n" + 
							"       z.room_type, --机房类型\n" + 
							"       z.hang_high_1, --实际最高天线挂高（米）1\n" + 
							"       z.rru_room_1, --RRU拉远时BBU是否放在铁塔公司机房1\n" + 
							"       z.tower_num, --铁塔共享用户数\n" + 
							"       z.room_num, --机房共享用户数\n" + 
							"       z.support_num, --配套共享用户数\n" + 
							"       z.maitain_num, --维护费共享用户数\n" + 
							"       z.field_num, --场地费共享用户数\n" + 
							"       z.power_num, --电力引入费共享用户数\n" + 
							"       z.rru_num, --RRU数量\n" + 
							"       z.antenna_num, --天线数量\n" + 
							"       b.A2           as b_tower_type, --塔型\n" + 
							"       b.A3           as b_room_type, --机房类型\n" + 
							"       b.A5           as b_hang_high1, --实际最高天线挂高（米）1\n" + 
							"       b.A6           as b_rru_room1, --RRU拉远时BBU是否放在铁塔公司机房1\n" + 
							"       b.A16          as b_tower_num, --铁塔共享用户数\n" + 
							"       b.A21          as b_room_num, --机房共享用户数\n" + 
							"       b.A26          as b_support_num, --配套共享用户数\n" + 
							"       b.A31          as b_maitain_num, --维护费共享用户数\n" + 
							"       b.A36          as b_field_num, --场地费共享用户数\n" + 
							"       b.A41          as b_power_num, --电力引入费共享用户数\n" + 
							"       b.rru_num      as b_rru_num, --RRU数量\n" + 
							"       b.antenna_num  as b_antenna_num --天线数量\n" + 
							"  from zhzy_tower z, BACK_TOWER b\n" + 
							" where z.product_no = b.tower_id\n" + 
							"   and z.data_flag = '1' "+
		                    "   and b.last_modify_userid is null ";
		String whereSql = "";
		
		// 当前登录人的区域权限
		if(areaId != null && !"".equals(areaId)){
//			if((StaticMethod.nullObject2String(areaid)).length()==2){
//				
//			}else
			if((StaticMethod.nullObject2String(areaId)).length()==4){
				whereSql += " and z.city_id = ? ";
				paramList.add(areaId);
			}else if((StaticMethod.nullObject2String(areaId)).length()==6){
				whereSql += " and z.area_id = ? ";
				paramList.add(areaId);
			}
		}
		
		//地市
		if(towerQueryConditionModel.getRegion() != null && !"".equals(towerQueryConditionModel.getRegion())){
			whereSql += " and z.city_id = ?";
			paramList.add(towerQueryConditionModel.getRegion());
			System.out.println("------towerQueryConditionModel.getRegion()="+towerQueryConditionModel.getRegion());
		}
		
		//区县
		if(towerQueryConditionModel.getCountry()!= null && !"".equals(towerQueryConditionModel.getCountry())){
			whereSql += " and z.area_id = ?";
			paramList.add(towerQueryConditionModel.getCountry());
			System.out.println("------towerQueryConditionModel.getCountry()="+towerQueryConditionModel.getCountry());
		}
		
		//站点名称
		if(towerQueryConditionModel.getResName()!= null && !"".equals(towerQueryConditionModel.getResName())){
			whereSql += " and instr(z.station_name,?)>0 ";
			paramList.add(towerQueryConditionModel.getResName().trim());
		}
		
		//产品业务确认编号
		if(towerQueryConditionModel.getConfirmNum()!= null && !"".equals(towerQueryConditionModel.getConfirmNum())){
			whereSql += " and z.product_no = ?";
			paramList.add(towerQueryConditionModel.getConfirmNum().trim());
		}
//		
//		//是否修改过
//		if(towerQueryConditionModel.getIsModify() != null && !"".equals(towerQueryConditionModel.getIsModify())){
//			if("0".equals(towerQueryConditionModel.getIsModify())){ //未修改过
//				whereSql += " and b.last_modify_userid is null";
//			}else if("1".equals(towerQueryConditionModel.getIsModify())){ //修改过
//				whereSql += " and b.last_modify_userid is not null";
//			}
//		}
//		
//		//数据筛选条件
//		if(towerQueryConditionModel.getDataFilter()!= null && !"".equals(towerQueryConditionModel.getDataFilter())){
//			if("1".equals(towerQueryConditionModel.getDataFilter())){//无机房且有用户数
//				whereSql += " and　b.a3 in('1030706','1030703')  and b.a21 <>'0'";
//			}else if("2".equals(towerQueryConditionModel.getDataFilter())){//非RRU拉远且存在铁塔机房
//				whereSql += " and b.a3 <>'1030705' and b.a6 ='1030101'";
//			}else if("3".equals(towerQueryConditionModel.getDataFilter())){//铁塔站址重复数据
//				whereSql += " and z.station_code in (select name_id from tower_repeat_data)";
//			}else if("4".equals(towerQueryConditionModel.getDataFilter())){//产品类型为其他
//				whereSql += " and b.a2 not in(select dictid from taw_system_dicttype where parentdictid='10308')";
//			}else if("5".equals(towerQueryConditionModel.getDataFilter())){//机房类型为其他
//				whereSql += " and b.a3 not in(select dictid from taw_system_dicttype where parentdictid='10307')";
//			}else if("6".equals(towerQueryConditionModel.getDataFilter())){//天线挂高为其他
//				whereSql += " and b.a5 not in(select dictid from taw_system_dicttype where parentdictid='10310')";
//			}
//		}
//		
		//产品类型（新）
		if(towerQueryConditionModel.getNewProductType()!= null && !"".equals(towerQueryConditionModel.getNewProductType())){
			whereSql += " and b.a2 = ?";
			paramList.add(towerQueryConditionModel.getNewProductType());
		}
		
		//机房类型（新）
		if(towerQueryConditionModel.getNewRoomType()!= null && !"".equals(towerQueryConditionModel.getNewRoomType())){
			whereSql += " and b.a3 = ?";
			paramList.add(towerQueryConditionModel.getNewRoomType());
		}
		
		//天线挂高（新）
		if(towerQueryConditionModel.getNewAntennaHeight()!= null && !"".equals(towerQueryConditionModel.getNewAntennaHeight())){
			whereSql += " and b.a5 = ?";
			paramList.add(towerQueryConditionModel.getNewAntennaHeight());
		}
		
		//产品类型（旧）
		if(towerQueryConditionModel.getOldProductTypeName()!= null && !"".equals(towerQueryConditionModel.getOldProductTypeName())){
			whereSql += " and z.tower_type = ?";
			paramList.add(towerQueryConditionModel.getOldProductTypeName());
		}
		
		//机房类型（旧）
		if(towerQueryConditionModel.getOldRoomTypeName()!= null && !"".equals(towerQueryConditionModel.getOldRoomTypeName())){
			whereSql += " and z.room_type = ?";
			paramList.add(towerQueryConditionModel.getOldRoomTypeName());
		}
		
		//天线挂高（旧）
		if(towerQueryConditionModel.getOldAntennaHeightName()!= null && !"".equals(towerQueryConditionModel.getOldAntennaHeightName())){
			whereSql += " and z.hang_high_1= ?";
			paramList.add(towerQueryConditionModel.getOldAntennaHeightName());
		}
		
		sql += selectSql+ whereSql;
		
		if(pageSize != -1){ //查询和导出共用
			sql += " ) temp1 where rownum <=?) temp2 where temp2.num >? ";
			paramList.add(endResult * pageSize);
			paramList.add(firstResult * pageSize);
		}
		
		System.out.println("-------------铁塔列表="+sql);
		
		Object[] args = paramList.toArray();		
		//SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<TowerModel> r = new ArrayList<TowerModel>();
		List<Map> list = this.getJdbcTemplate().queryForList(sql,args);
		for (Map map : list) {
			TowerModel model = new TowerModel();
			//产品业务确认单编号
			if (map.get("product_no") != null && !"".equals(map.get("product_no").toString())) {
				model.setProductNum(map.get("product_no").toString());
			}
			//站址名称
			if (map.get("station_name") != null && !"".equals(map.get("station_name").toString())) {
				model.setResName(map.get("station_name").toString());
			}
			//地市ID
			System.out.println("city_id="+map.get("city_id").toString());
			if (map.get("city_id") != null && !"".equals(map.get("city_id").toString())) {
				model.setCityId(map.get("city_id").toString());
			}
			//铁塔所属区县
			System.out.println("area_id="+map.get("city_id").toString());
			if (map.get("area_id") != null && !"".equals(map.get("area_id").toString())) {
				model.setCountyId(map.get("area_id").toString());
			}
			//站址编码
			if (map.get("station_code") != null && !"".equals(map.get("station_code").toString())) {
				model.setStationCode(map.get("station_code").toString());
			}
			//需求确认单编号
			if (map.get("need_no") != null && !"".equals(map.get("need_no").toString())) {
				model.setNeedNo(map.get("need_no").toString());
			}
			//塔型
			if (map.get("tower_type") != null && !"".equals(map.get("tower_type").toString())) {
				model.setTowerType(map.get("tower_type").toString());
			}
			//机房类型
			if (map.get("room_type") != null && !"".equals(map.get("room_type").toString())) {
				model.setRoomType(map.get("room_type").toString());
			}
			//实际最高天线挂高（米）1
			if (map.get("hang_high_1") != null && !"".equals(map.get("hang_high_1").toString())) {
				model.setHangHigh1(map.get("hang_high_1").toString());
			}
			//RRU拉远时BBU是否放在铁塔公司机房1
			if (map.get("rru_room_1") != null && !"".equals(map.get("rru_room_1").toString())) {
				model.setRruRoom1(map.get("rru_room_1").toString());
			}
			//铁塔共享用户数
			if (map.get("tower_num") != null && !"".equals(map.get("tower_num").toString())) {
				model.setTowerNum(map.get("tower_num").toString());
			}
			//机房共享用户数
			if (map.get("room_num") != null && !"".equals(map.get("room_num").toString())) {
				model.setRoomNum(map.get("room_num").toString());
			}
			//配套共享用户数
			if (map.get("support_num") != null && !"".equals(map.get("support_num").toString())) {
				model.setSupportNum(map.get("support_num").toString());
			}
			//维护费共享用户数
			if (map.get("maitain_num") != null && !"".equals(map.get("maitain_num").toString())) {
				model.setMaitainNum(map.get("maitain_num").toString());
			}
			//场地费共享用户数
			if (map.get("field_num") != null && !"".equals(map.get("field_num").toString())) {
				model.setFieldNum(map.get("field_num").toString());
			}
			//电力引入费共享用户数
			if (map.get("power_num") != null && !"".equals(map.get("power_num").toString())) {
				model.setPowerNum(map.get("power_num").toString());
			}
			//RRU数量
			if (map.get("rru_num") != null && !"".equals(map.get("rru_num").toString())) {
				model.setRruNum(map.get("rru_num").toString());
			}
			//天线数量
			if (map.get("antenna_num") != null && !"".equals(map.get("antenna_num").toString())) {
				model.setAntennaNum(map.get("antenna_num").toString());
			}
			
			//塔型(back表)
			if (map.get("b_tower_type") != null && !"".equals(map.get("b_tower_type").toString())) {
				model.setBTowerType(map.get("b_tower_type").toString());
			}
			//机房类型(back表)
			if (map.get("b_room_type") != null && !"".equals(map.get("b_room_type").toString())) {
				model.setBRoomType(map.get("b_room_type").toString());
			}
			//实际最高天线挂高（米）1(back表)
			if (map.get("b_hang_high1") != null && !"".equals(map.get("b_hang_high1").toString())) {
				model.setBHangHigh1(map.get("b_hang_high1").toString());
			}
			//RRU拉远时BBU是否放在铁塔公司机房1(back表)
			if (map.get("b_rru_room1") != null && !"".equals(map.get("b_rru_room1").toString())) {
				model.setBRruRoom1(map.get("b_rru_room1").toString());
			}
			//铁塔共享用户数(back表)
			if (map.get("b_tower_num") != null && !"".equals(map.get("b_tower_num").toString())) {
				model.setBTowerNum(map.get("b_tower_num").toString());
			}
			//机房共享用户数(back表)
			if (map.get("b_room_num") != null && !"".equals(map.get("b_room_num").toString())) {
				model.setBRoomNum(map.get("b_room_num").toString());
			}
			//配套共享用户数(back表)
			if (map.get("b_support_num") != null && !"".equals(map.get("b_support_num").toString())) {
				model.setBSupportNum(map.get("b_support_num").toString());
			}
			//维护费共享用户数(back表)
			if (map.get("b_maitain_num") != null && !"".equals(map.get("b_maitain_num").toString())) {
				model.setBMaitainNum(map.get("b_maitain_num").toString());
			}
			//场地费共享用户数(back表)
			if (map.get("b_field_num") != null && !"".equals(map.get("b_field_num").toString())) {
				model.setBFieldNum(map.get("b_field_num").toString());
			}
			//电力引入费共享用户数(back表)
			if (map.get("b_power_num") != null && !"".equals(map.get("b_power_num").toString())) {
				model.setBPowerNum(map.get("b_power_num").toString());
			}
			//RRU数量(back表)
			if (map.get("b_rru_num") != null && !"".equals(map.get("b_rru_num").toString())) {
				model.setBRruNum(map.get("b_rru_num").toString());
			}
			//天线数量(back表)
			if (map.get("b_antenna_num") != null && !"".equals(map.get("b_antenna_num").toString())) {
				model.setBAntennaNum(map.get("b_antenna_num").toString());
			}
			r.add(model);
		}
		return r;
	}
	
	/**
	 * 判断当前用户是否有修改权限
	 * @param areaId 地市Id
	 * @param userId 用户Id
	 * @return
	 */
	public int getTowerUserIdPowerConfig(String areaId,String userId){
		List<Object> paramList = new ArrayList<Object>();
		String sql ="select count(c.id)" +
					"  from tower_userid_power_config c" + 
					" where c.areaid = ?" + 
					"   and c.userid = ?";
		paramList.add(areaId);
		paramList.add(userId);
		System.out.println("-------------判断当前用户是否有修改权限="+sql);
		Object[] args = paramList.toArray();
		int size = this.getJdbcTemplate().queryForInt(sql,args);
		System.out.println("-------------条数="+size);
		return size;
	}
}
