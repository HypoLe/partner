package com.boco.eoms.partner.res.mgr.impl;

import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.struts.upload.FormFile;

import com.boco.eoms.common.util.StaticMethod;
import com.boco.eoms.deviceManagement.common.utils.CommonSqlHelper;
import com.boco.eoms.partner.deviceInspect.model.PnrInspectMapping;
import com.boco.eoms.partner.deviceInspect.util.TransLineAreaMappingUtil;
import com.boco.eoms.partner.netresource.service.impl.EomsServiceImpl;
import com.boco.eoms.partner.process.util.XLSModel;
import com.boco.eoms.partner.res.dao.IPnrTransLineDao;
import com.boco.eoms.partner.res.mgr.IPnrTransLineMgr;
import com.boco.eoms.partner.res.model.PnrResConfig;
import com.boco.eoms.partner.res.model.PnrTransLinePoint;
import com.boco.eoms.partner.res.util.ResourceConstants;
import com.boco.eoms.partner.resourceInfo.util.CSVFileImport;
import com.boco.eoms.partner.resourceInfo.util.CSVFileImport2;
import com.boco.eoms.partner.resourceInfo.util.ImportResult;

public class PnrTransLineMgrImpl extends EomsServiceImpl implements
		IPnrTransLineMgr {
	IPnrTransLineDao pnrTransLineDao;
	
	
	public IPnrTransLineDao getPnrTransLineDao() {
		return pnrTransLineDao;
	}

	public void setPnrTransLineDao(IPnrTransLineDao pnrTransLineDao) {
		this.pnrTransLineDao = pnrTransLineDao;
		super.setEomsDao(pnrTransLineDao);
	}

	/**
	 * 上传光缆段
	 */
	public ImportResult importTLFromFile(FormFile file) throws Exception {
		CSVFileImport2 csvImport = new CSVFileImport2() {
			@Override
			public String getBachSql() throws Exception {
				String sql = "INSERT INTO pnr_res_config(" +
								"id, specialty, res_name, res_type,city, country, " +
								"create_time, tl_inspect_flag, tl_dis, tl_wire, tl_system_level, " +
								"tl_seg, tl_lay_type, tl_fiber_count, tl_pa_name, tl_pa_lo, tl_pa_la, " +
								"tl_pz_name, tl_pz_lo, tl_pz_la, tl_length,execute_record)"+
							"VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				return sql;
			}
			@Override
			public void loadStaticSource() throws Exception {
				//装载地市传输线路与代维系统的区域映射map
				TransLineAreaMappingUtil.initMap();
			}

			@Override
			public PreparedStatement validateAndPrepareStatement(String[] values, HttpServletRequest request, PreparedStatement ps) throws Exception {
				ps.setString(1, CommonSqlHelper.generateUUID());
				ps.setString(2, "1122502");
				ps.setString(3, values[3]);
				ps.setString(4,"112250201");
				String[] city_country = TransLineAreaMappingUtil.MappingMap.get(values[0])==null?new String[]{"",""}:(String[])(TransLineAreaMappingUtil.MappingMap.get(values[0]));
				ps.setString(5,city_country[0]);
				ps.setString(6,city_country[1]);
				ps.setString(7,StaticMethod.getCurrentDateTime());
				ps.setString(8,ResourceConstants.TlInspectFlag_YES);
				ps.setString(9,values[0]);
				ps.setString(10,values[1]);
				ps.setString(11,values[2]);
				ps.setString(12,values[3]);
				ps.setString(13,values[4]);
				ps.setString(14,values[6]);
				ps.setString(15,values[7]);
				ps.setString(16,values[8]);
				ps.setString(17,values[9]);
				ps.setString(18,values[10]);
				ps.setString(19,values[11]);
				ps.setString(20,values[12]);
				ps.setString(21,values[5]);
				ps.setInt(22, 0);

				ps.addBatch();
				return ps;
			}
		};
		
		ImportResult result=csvImport.xlsFileValidate(file, null);
		return result;
	}
	/**
	 * 上传光缆敷设点
	 */
	public ImportResult importTLPFromFile(FormFile file) throws Exception {
		//装载地市传输线路与代维系统的区域映射map
		TransLineAreaMappingUtil.initMap();
		
		//以下是查询固定的为线路巡检制定的巡检资源与网络资源映射的数据，只取一条，若有多个设置，设置无效。
		PnrInspectMapping inspectMapping = (PnrInspectMapping)pnrTransLineDao.getSession().createQuery("FROM PnrInspectMapping p WHERE p.deviceSpecialty='trans' and p.deviceType='PnrTransLinePoint'").list().get(0);
		String mappingId = "";
		if(null != inspectMapping) {
			mappingId = inspectMapping.getId();
		}
		final String inspectMappingId = mappingId;
		
		CSVFileImport2 csvImport = new CSVFileImport2() {
			@Override
			public String getBachSql() throws Exception {
				String sql = "INSERT INTO pnr_trans_line_point" +
							"(id, tlp_dis, tlp_wire, tlp_seg, tlp_pa_name, tlp_pa_lo, tlp_pa_la, " +
							"tlp_pz_name, tlp_pz_lo, tlp_pz_la," +
							"city, country, inspect_mapping_id, tlp_source)" +
							"VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				return sql;
			}
			@Override
			public void loadStaticSource() throws Exception {
				//装载地市传输线路与代维系统的区域映射map
				TransLineAreaMappingUtil.initMap();
			}

			@Override
			public PreparedStatement validateAndPrepareStatement(String[] values, HttpServletRequest request, PreparedStatement ps) throws Exception {
				ps.setString(1, CommonSqlHelper.generateUUID());
				ps.setString(2, values[0]);
				ps.setString(3, values[1]);
				ps.setString(4,values[2]);
				ps.setString(5,values[3]);
				ps.setString(6,values[4]);
				ps.setString(7,values[5]);
				ps.setString(8,values[6]);
				ps.setString(9,values[7]);
				ps.setString(10,values[8]);
				String[] city_country = TransLineAreaMappingUtil.MappingMap.get(values[0])==null?new String[]{"",""}:(String[])(TransLineAreaMappingUtil.MappingMap.get(values[0]));
				ps.setString(11,city_country[0]);
				ps.setString(12,city_country[1]);
				ps.setString(13,inspectMappingId);
				ps.setString(14,ResourceConstants.TLP_SOURCE_0);

				ps.addBatch();
				return ps;
			}
		};
		
		ImportResult result=csvImport.xlsFileValidate(file, null);
		return result;
	}
	
	public void relationTransLine(String... parma) throws Exception {
		String sql = " update pnr_trans_line_point set res_cfg_id='"+parma[0]+"' where tlp_wire='"+parma[1]+"' and tlp_seg='"+parma[2]+"'";
		pnrTransLineDao.getJdbcTemplate().execute(sql);
	}

	@SuppressWarnings("unchecked")
	public Map<String, List> getTransPointRate() {
		List list1 = pnrTransLineDao.getSession().createQuery(" from PnrLocationCycle").list();
		List list2 = pnrTransLineDao.getSession().createQuery(" from PnrArrivedRate").list();
		Map<String, List> map = new HashMap<String, List>();
		map.put("list1", list1);
		map.put("list2", list2);
		return map;
	}
	public Map<String, List> getTransPointRate(String areaId) {
		List list1 = pnrTransLineDao.getSession().createQuery(" from PnrLocationCycle  where country like '"+areaId+"%' ").list();
		List list2 = pnrTransLineDao.getSession().createQuery(" from PnrArrivedRate where country like '"+areaId+"%' ").list();//黑龙江要求所配的要做权限,按地域
		Map<String, List> map = new HashMap<String, List>();
		map.put("list1", list1);
		map.put("list2", list2);
		return map;
	}
	public void setMustArrive(String ids,String... params) {
		String  sql = "update pnr_trans_line_point set is_must_arrive='"+params[0]+"' where id in ("+ids+")";
		pnrTransLineDao.getJdbcTemplate().execute(sql);
	}

	public void updateAllTransPointRate(String ids,String... params) {
		String  sql = "update pnr_res_config set arrived_rate_id = '"+params[0]+"' ,tl_arrived_rate= '"+params[1]+"' " +
		" where id in ("+ids+")";
		pnrTransLineDao.getJdbcTemplate().execute(sql);
	}
	
	/**
	 * 添加巡检段的起点和终点到敷设点
	 * @param tlId
	 */
	public void addTL2TPL(String tlId) {
		PnrResConfig resCfg = (PnrResConfig)pnrTransLineDao.findByHql("FROM PnrResConfig p WHERE p.id='"+tlId+"'").get(0);
		Map pa = null,pz = null;
		if(resCfg != null) {
			String subQuery1 = "select p.tlp_pz_name from pnr_trans_line_point p where p.tlp_wire='"+resCfg.getTlWire()+"' and tlp_seg='"+resCfg.getTlSeg()+"'";
			String subQuery2 = "select p.tlp_pa_name from pnr_trans_line_point p where p.tlp_wire='"+resCfg.getTlWire()+"' and tlp_seg='"+resCfg.getTlSeg()+"'";
			pa = pnrTransLineDao.getJdbcTemplate().queryForMap("select * from pnr_trans_line_point a where a.tlp_pa_name not in ("+subQuery1+") and a.tlp_wire='"+resCfg.getTlWire()+"' and a.tlp_seg='"+resCfg.getTlSeg()+"'");
			pz = pnrTransLineDao.getJdbcTemplate().queryForMap("select * from pnr_trans_line_point a where a.tlp_pz_name not in ("+subQuery2+") and a.tlp_wire='"+resCfg.getTlWire()+"' and a.tlp_seg='"+resCfg.getTlSeg()+"'");
		}
		
		if(pa == null || pz == null) {
			return;
		}
		
		PnrInspectMapping inspectMapping = (PnrInspectMapping)pnrTransLineDao.getSession().createQuery("FROM PnrInspectMapping p WHERE p.deviceSpecialty='trans' and p.deviceType='PnrTransLinePoint'").list().get(0);
		String mappingId = "";
		if(null != inspectMapping) {
			mappingId = inspectMapping.getId();
		}
		
		PnrTransLinePoint newPA = new PnrTransLinePoint();
		
		newPA.setTlpDis("");
		newPA.setTlpWire(resCfg.getTlWire());
		newPA.setTlpSeg(resCfg.getTlSeg());
		newPA.setTlpPAName(resCfg.getTlPAName());
		newPA.setTlpPALo(resCfg.getTlPALo());
		newPA.setTlpPALa(resCfg.getTlPALa());
		newPA.setIsMustArrive("1");
		newPA.setTlpPZName(pa.get("tlp_pa_name").toString());
		newPA.setTlpPZLo(pa.get("tlp_pa_lo").toString());
		newPA.setTlpPZLa(pa.get("tlp_pa_la").toString());
		
		newPA.setTlpSource(ResourceConstants.TLP_SOURCE_1);
		newPA.setInspectMappingId(mappingId);
		pnrTransLineDao.getHibernateTemplate().saveOrUpdate(newPA);
		
		PnrTransLinePoint newPZ = new PnrTransLinePoint();
		
		newPZ.setTlpDis("");
		newPZ.setTlpWire(resCfg.getTlWire());
		newPZ.setTlpSeg(resCfg.getTlSeg());
		newPZ.setTlpPAName(resCfg.getTlPZName());
		newPZ.setTlpPALo(resCfg.getTlPZLo());
		newPZ.setTlpPALa(resCfg.getTlPZLo());
		newPZ.setIsMustArrive("1");
		newPZ.setTlpPZName("-");
		newPZ.setTlpPZLo("-");
		newPZ.setTlpPZLa("-");
		
		newPZ.setTlpSource(ResourceConstants.TLP_SOURCE_2);
		newPZ.setInspectMappingId(mappingId);
		pnrTransLineDao.getHibernateTemplate().saveOrUpdate(newPZ);
	}

	public boolean isFinishedTLPInspectItem(String pointId) {
		String sql1 = "select count(*) from pnr_inspect_plan_item where inspect_task_link_id='"+pointId+"'";
		String sql2 = "select count(*) from pnr_inspect_plan_item where inspect_task_link_id='"+pointId+"' and  save_time is not null and (picture_upload_flag=1 or picture_num=0 )";
		int total = pnrTransLineDao.getJdbcTemplate().queryForInt(sql1);
		int finshTotal = pnrTransLineDao.getJdbcTemplate().queryForInt(sql2);
		if(total<=finshTotal){
			return true;
		}else{
			return false;
		}
	}
	public String rateCountryId2Name(String countryId){
		
		String sql = "select areaname from taw_system_area where areaid='"+countryId+"'";
		List returnList = pnrTransLineDao.getJdbcTemplate().queryForList(sql);
		if(null !=returnList&&!returnList.isEmpty()){
			return ((ListOrderedMap)returnList.get(0)).get("areaname")+"";
		}
		return "";
	}
}
