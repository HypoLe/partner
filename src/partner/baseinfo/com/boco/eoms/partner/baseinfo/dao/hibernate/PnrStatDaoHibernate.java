package com.boco.eoms.partner.baseinfo.dao.hibernate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.commons.system.area.model.TawSystemArea;
import com.boco.eoms.partner.baseinfo.dao.IPnrStatDao;



public class PnrStatDaoHibernate extends BaseDaoHibernate implements IPnrStatDao {

	/**
	 * 得到基本表，包括（大合作伙伴、小合作伙伴、地市、县区）
	 * author:wangjunfeng
	 * 2010-3-30
	 * @param areaStr
	 * @return
	 */
	public List getBasePartnerAndRegion(final String areaStr,final String partnerStr){
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				StringBuffer queryStr = new StringBuffer();
				queryStr.append("from PartnerDept dept ,AreaDeptTree tree , TawSystemArea tsa  ");
				queryStr.append(" where  dept.id = tree.interfaceHeadId  and tree.nodeType='factory' ");
				queryStr.append(" and  tree.areaId = tsa.parentAreaid  ");
				if(!"".equals(areaStr)){
					queryStr.append(" and  tree.areaId in ("+areaStr+") ");
				}
				if(!"".equals(partnerStr)){
					queryStr.append(" and  dept.name in ("+partnerStr+") ");
				}
				
				queryStr.append("order by tree.interfaceHeadId , tree.areaId , tsa.areaid");
				List list = getHibernateTemplate().find(queryStr.toString());
				return list;
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}


	
	/**
	 * 线路 统计报表 
	 * 统计条件：按合作伙伴名称和地市统计。按月统计。
	 */
	public List getLineReportStat(final String endTime) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
													
				StringBuffer queryStr = new StringBuffer();
				queryStr.append("from Line line ,AreaDeptTree tree");
				queryStr.append(" where  line.isDel = '0'  and tree.nodeName = line.provider ");
				
				if(endTime !=null &&!"".equals(endTime)){
					queryStr.append(" and  line.timeNew< to_date('"+endTime+"','yyyy-mm') ");
				}
				
				queryStr.append(" order by tree.areaId, tree.interfaceHeadId");
				
				
				List list = getHibernateTemplate().find(queryStr.toString());
				
				
				
				return list;
			}

				
/*				String queryStr=" select bs.partner,bs.region,bs.regionname,bs.city,bs.cityname,sum(line.lh) from " +
						"(select dept.name as partner ,tree.nodename as spartner ,tree.areaid as region ,tsa1.areaname as regionName , " +
						"tsa.areaid as city ,tsa.areaname as cityName " +
						" from pnr_dept dept,pnr_areadepttree tree ,taw_system_area tsa ,taw_system_area tsa1 " +
						" where dept.id=tree.interface_head_id and tree.node_type='factory' and tree.areaid=tsa.parentareaid  " +
						" and tree.areaid=tsa1.areaid) bs " +
						" left join (select provider,region,city,sum(line_length) as lh from pnr_servicearea_line " +
						" where isdel='0' " + timeStr +
						" group by provider,region ,city) line " +
						" on bs.spartner=line.provider and line.region=bs.region and line.city=bs.city "  + whereStr + 
						" group by bs.partner,bs.region,bs.regionname,bs.city,bs.cityname" +
						" order by bs.partner,bs.region,bs.city ";
				
				SQLQuery countQuery = session.createSQLQuery(queryStr);
				
				return countQuery.list();
			}*/
		};
		return (List) getHibernateTemplate().execute(callback);		
		
	}

	/**
	 * 线路统计
	 * 统计条件：按地市、县区、线路等级统计。按月统计。
	 */
	public List getReportLineStatByLineLevel(final String areaStr,final String gradeStr,final String timeStr) {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {

				String 	queryStr = " select rname,rid,cname,cid,line.grade,sum(line_length) from (" +
						" select  region.areaname as rname,region.areaid as rid,city.areaname as cname,city.areaid as cid " +
						"  from  taw_system_area region , taw_system_area city ,pnr_dept partner2" +
						"  where region.areaid=city.parentareaid " +
						"  and "+ areaStr +         						//region.areaid = '3901' and city.areaid='390101'
						" group by region.areaname,region.areaid,city.areaname,city.areaid " +
						"  )left join  pnr_servicearea_line line on	line.city=cid" +
						"  and   line.isdel=0  "+ gradeStr + 	timeStr +				//and line.grade=1
						" group by rname,rid,cname,cid,grade order by rid,cid,grade";
				
				SQLQuery countQuery = session.createSQLQuery(queryStr);
				return countQuery.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
		
	}
	
	
	/**
	 * 列出所有合伙伙伴
	 */
	public List listLinePartner() {
		
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
//				String hql = "from Line line  ";
//				List list = getHibernateTemplate().find(hql);
//				return list;

				//String queryStr = "select distinct name from pnr_dept where deleted='0' and area_id='"+ parentareaid +"' ";
				String queryStr = "select distinct(d.name)   from pnr_areadepttree t ,pnr_dept d " +
				"where  d.id=t.interface_head_id  ";

				SQLQuery countQuery = session.createSQLQuery(queryStr);
				
				return countQuery.list();

			}
		};
		return (List) getHibernateTemplate().execute(callback);		
		
	}
	

	/**
	 * 列出所有地市
	 */
	public List listLineRegion() {
		
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {

				String queryStr = "select  region from taw_system_area where ordercode=2 ";
				SQLQuery countQuery = session.createSQLQuery(queryStr);
				
				return countQuery.list();

			}
		};
		return (List) getHibernateTemplate().execute(callback);		
		
	}
	/**
	 * 列出所有县区
	 */
	public List listLineCity() {
		
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {

				String queryStr = "select distinct city from pnr_servicearea_line ";
				SQLQuery countQuery = session.createSQLQuery(queryStr);
				
				return countQuery.list();

			}
		};
		return (List) getHibernateTemplate().execute(callback);		
		
	}


	/**
	 * 合作伙伴信息 统计报表 
	 * 统计条件：按地市统计，按月统计合作伙伴数量，按合作伙伴、专业统计维护人员数量
	 */
	public List getReportProvideStat(final String whereStr) {
		
				
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				
				StringBuffer queryStr = new StringBuffer();
				queryStr.append("select rn,pni,pn,service_professional,cou from ");
				queryStr.append("(select region.nodename as rn, region.areaid as ra, partner.nodename as pn,region.node_id as rni from ");
				queryStr.append("(select t.*, t.rowid from pnr_areadepttree t " ); 
				if("".equals(whereStr)){
					queryStr.append("where node_type = 'area'");
				}else{
					queryStr.append("where areaid = '"+whereStr+"'");
				}	
				queryStr.append(") region  ,pnr_areadepttree partner ");
				
				queryStr.append("where region.node_id = partner.parent_node_id) left join (select parent_node_id ,count(parent_node_id) as pni from pnr_areadepttree where node_type='factory' group by parent_node_id) on rni = parent_node_id left join ");
				queryStr.append("(select area_id,dept_name,service_professional,count(decode(deleted,0,1,1)) as coufrom pnr_user where deleted='0' group by (area_id,dept_name,service_professional)) " );
				queryStr.append("on rni=area_id and pn=dept_name order by ra,pn,service_professional");
				SQLQuery countQuery = session.createSQLQuery(queryStr.toString());
				
				
				return countQuery.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
		
	}	

	/**
	 * 车辆统计
	 * 2010-01-19
	 * author:wangjunfeng
	 */
	public List getReportCarStat(final String timeStr,final String whereStr) {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
									
	
/*				String queryStr=" select bs.areaname,bs.areaid,bs.parent_node_id,bs.name,bs.tree_node_id  ,sum(yp.yp_num) as yps ,sum(sp.sp_num) as sps from (" +
						"select  tsa.areaname,tsa.areaid,tree.parent_node_id,dept.name, dept.tree_node_id,tree.nodename,tree.node_id  from pnr_dept dept " +
						" left join taw_system_area tsa on dept.area_id=tsa.parentareaid  " +
						"left join pnr_areadepttree tree on dept.id=tree.interface_head_id and tsa.areaid=tree.areaid and node_type='factory' " +
						" where area_id='"+parentareaid+"' order by areaid,tree_node_id,node_id ) bs " +
						" left join( select region as region_yp,provider,sum(dispose_no) as yp_num from pnr_base_vehicle where isdel='0' " +
						" group by region,provider) yp on bs.areaid = yp.region_yp and bs.nodename = yp.provider " +
						"left join ( select area_id,dept_id,count(id)as sp_num from pnr_partner_car  where deleted='0' " + timeStr +
						" group by dept_id,area_id ) sp on bs.parent_node_id = sp.area_id and  bs.node_id = sp.dept_id " + whereStr +
						" group by  bs.areaname,bs.areaid,bs.parent_node_id,bs.name,bs.tree_node_id " +
						" order by areaid,tree_node_id ";	
*/				
				String queryStr=" select bs.region,bs.regionname,bs.partner,sum(yp.yp_num),sum(sp.sp_num) from " +
						"(select tree.areaid as region ,tsa.areaname as regionName ,dept.name as partner ,tree.nodename as spartner,tree.node_id, " +
						"  tree.parent_node_id as tpregion  " +
						" from pnr_dept dept,pnr_areadepttree tree  ,taw_system_area tsa " +
						" where dept.id=tree.interface_head_id and tree.node_type='factory'  and tree.areaid=tsa.areaid) bs " +
						" left join( " +
						" select region as region_yp,provider,sum(dispose_no) as yp_num from pnr_base_vehicle where isdel='0' " +
						" group by region,provider  ) yp on bs.region = yp.region_yp and bs.spartner = yp.provider " +
						" left join ( " +
						"select area_id,dept_id,count(id)as sp_num from pnr_partner_car  where deleted='0' " + timeStr +
						" group by dept_id,area_id ) sp on bs.tpregion = sp.area_id and  bs.node_id = sp.dept_id " + whereStr +
						"  group by bs.region, bs.regionname, bs.partner" +
						" order by bs.region,bs.regionname ,bs.partner ";
				
				SQLQuery countQuery = session.createSQLQuery(queryStr);
				
				return countQuery.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
		
	}
	

	/**
	 * 仪器仪表统计
	 * 2010-01-19
	 * author:wangjunfeng
	 */
	public List getReportApparatusStat(final String whereStr,final String timeStr) {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
												
/*				String queryStr=" select region,regionname,partner,type,sum(yp_num),sum(sp_num) from " +
						"(select bs.region,bs.regionname,bs.partner,bs.spartner,yp.type,yp.yp_num,sp.sp_num from " +
						"(select tree.areaid as region ,tsa.areaname as regionName ,dept.name as partner ," +
						" tree.nodename as spartner,tree.node_id , tree.parent_node_id tpregion " +
						" from pnr_dept dept,pnr_areadepttree tree  ,taw_system_area tsa " +
						" where dept.id=tree.interface_head_id and tree.node_type='factory' " +
						" and tree.areaid=tsa.areaid ) bs " +
						" left join " +
						"( select region as region_yp ,provider,type,dispose_no as yp_num from pnr_baseinfo_instrumentconfig " +
						"where isdel='0' group by region,provider,type,dispose_no ) yp " +
						" on bs.region = yp.region_yp and  bs.spartner = yp.provider " +
						" left join " +
						"( select area_id as region_sp,dept_id as partner_id,type,count(*) as sp_num from pnr_partner_apparatus where deleted='0' " +
						 timeStr +
						" group by area_id,dept_id,type) sp on bs.tpregion = sp.region_sp and bs.node_id = sp.partner_id  and sp.type=yp.type ) " + 
						 whereStr +
						"group by  region,regionname,partner,type    order by regionname,partner,type  ";
*/				
				
				String queryStr="  select region, regionname, partner, apparatusname, sum(yp_num), sum(sp_num) " +
						"  from (select bs.region, bs.regionname, bs.partner, bs.spartner, sp.apparatusname,yp.yp_num, sp.sp_num" +
						"  from (select tree.areaid as region, tsa.areaname as regionName, dept.name as partner, tree.nodename as spartner," +
						" tree.node_id, tree.parent_node_id tpregion  " +
						" from pnr_dept  dept, pnr_areadepttree tree, taw_system_area  tsa  where dept.id = tree.interface_head_id" +
						" and tree.node_type = 'factory'  and tree.areaid = tsa.areaid) bs " +
						"  left join (select region as region_yp,  provider, dispose_no as yp_num" +
						"  from pnr_baseinfo_instrumentconfig   where isdel = '0'  group by region, provider, dispose_no) yp " +
						" on bs.region = yp.region_yp  and bs.spartner =  yp.provider  left join (" +
						" select area_id as region_sp, dept_id as partner_id, apparatusname, count(*) as sp_num " +
						"  from pnr_partner_apparatus  where deleted = '0' " +  timeStr +
						"  group by area_id, dept_id, apparatusname) sp on bs.tpregion =  sp.region_sp  and bs.node_id =  sp.partner_id ) " +
						whereStr +
						"  group by region, regionname, partner, apparatusname  order by regionname, partner, apparatusname  ";
				SQLQuery countQuery = session.createSQLQuery(queryStr);
				
				return countQuery.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
		
	}

	
	public List getReportApparatusPartnerStat(final String partnerStr,final String whereStr,final String regionStr) {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
									
/*				String queryStr=" select partner,type,state,sum(sp_num) from (" +
						"select bs.partner,bs.yq.type,yq.state,yq.sp_num from(" +
						"select dept.name as partner,tree.nodename as spartner,tree.node_id as spartnerId " +
						"from pnr_dept dept,pnr_areadepttree  tree " +
						"where dept.id=tree.interface_head_id and tree.node_type='factory' ) bs " +
						"left join( " +
						"select dept_id as partner_id,type,state,count(*) as sp_num from pnr_partner_apparatus " +
						"where deleted='0' " + whereStr +
						" group by dept_id,type,state " +
						") yq on bs.spartnerid=yq.partner_id) " +
						 partnerStr +" group by partner,type,state order by partner,type,state";
*/						
				
				String queryStr="  select partner, apparatusname, state, sum(sp_num) " +
						" from (select bs.partner, sp.apparatusname, sp.state, sp.sp_num " +
						" from (select dept.name     as partner, tree.nodename as spartner, tree.node_id  as spartnerId " +
						" from pnr_dept dept, pnr_areadepttree tree  where dept.id = tree.interface_head_id " +
						" and tree.node_type = 'factory'  "+ regionStr+") bs  left join (select dept_id as partner_id,  apparatusname, " +
						" state, count(*) as sp_num  from pnr_partner_apparatus     where deleted = '0'" + whereStr +
						" group by dept_id, apparatusname, state) sp on bs.spartnerid = sp.partner_id) " +
						partnerStr + " group by partner, apparatusname, state  order by partner, apparatusname, state ";
				
				
				SQLQuery countQuery = session.createSQLQuery(queryStr);
				
				return countQuery.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
		
	}
	
	
	/**
	 * 合作伙伴市场份额
	 */
	public List getReportMarketStat(final String regionStr) {
		
				
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				StringBuffer queryStr = new StringBuffer();
				SQLQuery countQuery = null;
				if ("org.hibernate.dialect.OracleDialect".equals(ApplicationContextHolder.getInstance().getHQLDialect())) {
					queryStr.append(" select dt.name,dt.areaid,sum(cs),sum(lh),sum(cp) from (select tree.areaid as areaid,dept.name as name,tree.nodename as nodename from  ");
					queryStr.append(" pnr_dept dept ,pnr_areadepttree tree   ");
					queryStr.append(" where dept.id=tree.interface_head_id and tree.node_type='factory'  "+ regionStr +" ) dt ");
					queryStr.append(" left join (select region,provider,sum(line_length) lh from pnr_servicearea_line t where isdel='0' group by region ,provider) lin  ");
					queryStr.append(" on dt.areaid=lin.region and dt.nodename = lin.provider ");
					queryStr.append(" left join (select region,provider,count(id) cs from pnr_servicearea_site where unconfig='0' group by region,provider) sit  ");
					queryStr.append(" on dt.areaid=sit.region and dt.nodename = sit.provider ");
					queryStr.append(" left join (select region,provider,count(id) cp from pnr_servicearea_point where isdel=0 group by region,provider) poi  ");
					queryStr.append(" on dt.areaid=poi.region and dt.nodename = poi.provider ");
					queryStr.append(" group by dt.name,dt.areaid  order by dt.name,dt.areaid ");
					countQuery = session.createSQLQuery(queryStr.toString()); 
				}
				else if("org.hibernate.dialect.InformixDialect".equals(ApplicationContextHolder.getInstance().getHQLDialect())){
					queryStr.append(" select dept_name as tn,tree_areaid as ta,sum(site_count) as sc ,sum(sum_line_legth) as sll,sum(point_count) as pc from v_pnr_dept_area ");
					queryStr.append(" left join v_pnr_servicearea_line on tree_areaid = line_region and tree_nodename = linr_provider ");
					queryStr.append(" left join v_pnr_servicearea_point on tree_areaid = point_region and tree_nodename = point_provider ");
					queryStr.append(" left join v_pnr_servicearea_site on tree_areaid = site_region and tree_nodename = site_provider ");
					queryStr.append(regionStr);
					queryStr.append(" group by dept_name,tree_areaid  order by dept_name,tree_areaid ");
					countQuery = session.createSQLQuery(queryStr.toString());
					countQuery.addScalar("tn", Hibernate.STRING);
					countQuery.addScalar("ta", Hibernate.STRING);
					countQuery.addScalar("sc", Hibernate.STRING);
					countQuery.addScalar("sll", Hibernate.STRING);
					countQuery.addScalar("pc", Hibernate.STRING);
				}
				return countQuery.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
		
	}	


	
	/**
	 * 合作伙伴使用情况统计
	 */
	public List getUseCaseStat(final String parentareaid,final String  whereStr,final String regionStr) {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
									
/*				String queryStr=" select name,user_id,num,opertime,remoteip from ( " +
						"select d.name,u.user_id  from pnr_user u,pnr_areadepttree t ,pnr_dept d " +
						"where u.tree_node_id = t.node_id and d.id=t.interface_head_id  and t.areaid='"+ parentareaid +"' order by t.interface_head_id " +
						") bs left join( " +
						"  select count(l.id) num,max(l.beginnotetime) lastTime,u.user_id userid " +
						"  from pnr_user u,taw_common_logoperator l,pnr_areadepttree t,pnr_dept d " +
						"  where operid='0001' and u.user_id = l.userid and t.node_id = u.tree_node_id and d.id=t.interface_head_id " +
						"  group by u.user_id,d.id " +
						")tm on bs.user_id=tm.userid " +
						"left join (select  distinct(g.userid) ,g.opertime,logs.remoteip from " +
						"(select l.userid as userid,max(l.beginnotetime) as opertime from taw_common_logoperator l " +
						" where operid='0001' group by l.userid) g,taw_common_logoperator logs " +
						"where g.opertime =logs.beginnotetime and g.userid = logs.userid " +
						") ip on ip.userid=tm.userid " + whereStr ;
*/				
				String queryStr=" select partner,user_id,num,opertime,remoteip from ( " +
						"select dept.name as partner,u.user_id  " +
						"from pnr_dept dept,pnr_areadepttree  tree ,pnr_user u " +
						"where dept.id=tree.interface_head_id and tree.node_type='factory' and u.dept_id=tree.node_id "+ regionStr +" order by partner "+
						") bs left join( " +
						"  select count(l.id) num,max(l.beginnotetime) lastTime,u.user_id userid " +
						"  from pnr_user u,taw_common_logoperator l,pnr_areadepttree t,pnr_dept d " +
						"  where operid='0001' and u.user_id = l.userid and t.node_id = u.tree_node_id and d.id=t.interface_head_id " +
						"  group by u.user_id,d.id " +
						")tm on bs.user_id=tm.userid " +
						"left join (select  distinct(g.userid) ,g.opertime,logs.remoteip from " +
						"(select l.userid as userid,max(l.beginnotetime) as opertime from taw_common_logoperator l " +
						" where operid='0001' group by l.userid) g,taw_common_logoperator logs " +
						"where g.opertime =logs.beginnotetime and g.userid = logs.userid " +
						") ip on ip.userid=tm.userid " + whereStr ;
				
				SQLQuery countQuery = session.createSQLQuery(queryStr);
				
				return countQuery.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
		
	}
	

	/**
	 * 基站统计
	 */
	public List getReportSiteStat(final String region,final String city,final String provider,final Date timeEnd,final String province) {
		
				
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				StringBuffer queryStr = new StringBuffer();
				
				queryStr.append(" select  partner,regionname,cityname,sum(psc),sum(pbps),sum(cou),bs.region,bs.city ,bs.did  from ");
				queryStr.append(" (select dept.name as partner ,tree.nodename as spartner ,tree.areaid as region ,tsa1.areaname as regionName , ");
				queryStr.append(" tsa.areaid as city ,tsa.areaname as cityName ,dept.id as did,tree.node_id tpdept "); 
				queryStr.append(" from pnr_dept dept,pnr_areadepttree tree ,taw_system_area tsa ,taw_system_area tsa1 ");
				queryStr.append(" where dept.id=tree.interface_head_id and tree.node_type='factory' and tree.areaid=tsa.parentareaid  ");
				queryStr.append("  and tree.areaid=tsa1.areaid) bs  "); 
				queryStr.append(" left join (select provider ,region ,city,count(id) psc from pnr_servicearea_site s where isdel='0' and unconfig='0' ");
				if(timeEnd!=null){
					String endTimeString = new SimpleDateFormat("yyyy-MM-dd").format(timeEnd);
					queryStr.append(" and add_time <  to_date('"+endTimeString+"','yyyy-MM-dd HH24:mi:ss')");
				}		
				queryStr.append("  group by provider ,region ,city) pss");
				queryStr.append("  on  bs.city = pss.city and bs.spartner = pss.provider left join  ");
				queryStr.append(" (select provider ,region ,city,sum(dispose_no) pbps  from pnr_baseinfo_personconfig where isdel='0' group by provider ,region ,city) pbp  ");
				queryStr.append("  on bs.city = pbp.city and bs.spartner = pbp.provider left join ");
				queryStr.append("  (select area_name ana,dept_id,dept_name,city,count(pnr_user.id) as cou from pnr_user   left join pnr_baseinfo_resume t  on person_card_no =t.id_card_no where deleted='0'  and state='1' ");
				if(timeEnd!=null){
					String endTimeString = new SimpleDateFormat("yyyy-MM-dd").format(timeEnd);
					queryStr.append(" and dimission_date > to_date('"+endTimeString+"','yyyy-MM-dd HH24:mi:ss')");					
				}		
				queryStr.append("  group by area_name,dept_id,dept_name,city) pu  ");
				queryStr.append("  on bs.city = pu.city and   bs.tpdept = pu.dept_id ");
				queryStr.append(" where 1=1 ");
				if(!"".equals(provider)){
					queryStr.append(provider);
				}
				if(!"".equals(region)){
					queryStr.append(region);
				}
				if(!"".equals(city)){
					queryStr.append(city);
				}
				queryStr.append("  group by partner,regionname,cityname,bs.region,bs.city ,bs.did order by partner,regionname,cityname ");
				SQLQuery countQuery = session.createSQLQuery(queryStr.toString());
//				if(timeEnd!=null){
//					countQuery.setTimestamp(0, timeEnd);
//				}
				return countQuery.list();
			} 
		};
		return (List) getHibernateTemplate().execute(callback);		
		
	}

	
	/**
	 * 基站统计按等级
	 */
	public List getReportSiteStatBylevel(final String region,final String level,final Date timeEnd ,final String province) {
		
				
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				StringBuffer queryStr = new StringBuffer();
				queryStr.append(" select areaname,site_levle,sum(ci),areaid from (select * from taw_system_area,(select distinct site_levle from pnr_servicearea_site  where unconfig='0')) ");
				queryStr.append(" left join (select  region,site_levle sl,count(id) ci from pnr_servicearea_site  where unconfig='0' group by region,site_levle)  ");
				queryStr.append(" on site_levle = sl and areaid=region where  parentareaid='"+province+"' " );
				if(!"".equals(region)){
					queryStr.append(region);
				}
				if(!"".equals(level)){
					queryStr.append(level);
				}
				queryStr.append("group by areaid,areaname,site_levle order by areaid,site_levle ");
//				queryStr.append(" select nodename,site_levle,ci from (select * from (select nodename,areaid ss from pnr_areadepttree where node_type='area' ") ;
//				if(!"".equals(region)){
//					queryStr.append(region);
//				}
//				queryStr.append(" ),(select distinct site_levle from pnr_servicearea_site ");
//				if(!"".equals(level)){
//					queryStr.append(level);
//				}
//				queryStr.append(" )) left join (select  region,site_levle sl,count(id) ci from pnr_servicearea_site group by region,site_levle) ");
//				queryStr.append(" on ss=region and site_levle = sl  order by nodename,site_levle " );
				SQLQuery countQuery = session.createSQLQuery(queryStr.toString());
				if(timeEnd!=null){
					countQuery.setTimestamp(0, timeEnd);
				}
				return countQuery.list();
			} 
		};
		return (List) getHibernateTemplate().execute(callback);		
		
	}

	
	/**
	 * 人员统计按地市 合作伙伴
	 */
	public List getReportPersonStat(final String region,final String professional,final String provider,final Date timeEnd,final String province) {
		
				
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				StringBuffer queryStr = new StringBuffer();
				
				queryStr.append(" select  l.regionName,l.partner,pbp.service_professional,sum(sd),sum(pumgoc),l.region from  ");
				queryStr.append(" ( select tree.areaid as region ,tsa.areaname as regionName ,dept.name as partner , ");
				queryStr.append(" tree.nodename as spartner,tree.node_id,tree.parent_node_id tpregion   ");
				queryStr.append(" from pnr_dept dept,pnr_areadepttree tree  ,taw_system_area tsa ");
				queryStr.append(" where dept.id=tree.interface_head_id and tree.node_type='factory'  and tree.areaid=tsa.areaid) l  ");
				queryStr.append(" left join (select region,provider,service_professional,sum(dispose_no) sd ");
				queryStr.append(" from pnr_baseinfo_personconfig t group by region,provider,service_professional) pbp ");
				queryStr.append(" on l.region = pbp.region and l.spartner=pbp.provider left join  ");
				queryStr.append(" (select area_id,dept_name,dept_id,service_professional,count(pnr_user.id) pumgoc ");
				queryStr.append(" from pnr_user left join pnr_baseinfo_resume t  on person_card_no =t.id_card_no  " );
				
				queryStr.append(" where deleted='0' and state='1'  ");
				if(timeEnd!=null){
					String endTimeString = new SimpleDateFormat("yyyy-MM-dd").format(timeEnd);
					queryStr.append(" and dimission_date > to_date('"+endTimeString+"','yyyy-MM-dd HH24:mi:ss')");
				}				
				queryStr.append(" group by area_id,dept_name,dept_id,service_professional) pnr  ");
				queryStr.append(" on l.tpregion = pnr.area_id and l.node_id=pnr.dept_id and pbp.service_professional = pnr.service_professional where 1=1");
				if(!"".equals(region)){
					queryStr.append(region);
				}
				if(!"".equals(provider)){
					queryStr.append(provider);
				}
				if(!"".equals(professional)){
					queryStr.append(professional);
				}
				queryStr.append(" group by l.regionName,l.partner,pbp.service_professional,l.region  ");
				queryStr.append(" order by  l.regionName,l.partner,pbp.service_professional,l.region ");

				SQLQuery countQuery = session.createSQLQuery(queryStr.toString());
				return countQuery.list();
			} 
		};
		return (List) getHibernateTemplate().execute(callback);		
	}	
	

	/**
	 * 人员统计按 合作伙伴
	 */
	public List getReportPersonStatByPor(final String region,final String city,final String provider,final Date timeEnd,final String province,final String regionStr) {
		
				
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				StringBuffer queryStr = new StringBuffer();
				
				queryStr.append(" select dt.name,sum(pbp.sd),sum(pnr.pumgoc) from   ");
				queryStr.append(" (select tree.areaid as areaid,dept.name as name,tree.nodename as nodename,tree.node_id tnode from ");
				queryStr.append(" pnr_dept dept ,pnr_areadepttree tree ");
				queryStr.append(" where dept.id=tree.interface_head_id and tree.node_type='factory'"+ regionStr +") dt ");
				queryStr.append(" left join (select provider,sum(dispose_no) sd from pnr_baseinfo_personconfig where isdel='0' group by provider) pbp " );
				queryStr.append(" on dt.nodename=pbp.provider left join ");
				queryStr.append(" (select dept_name,dept_id,count(pnr_user.id) pumgoc ");
				queryStr.append(" from pnr_user left join pnr_baseinfo_resume t  on person_card_no =t.id_card_no ");
				queryStr.append(" where deleted='0' and state='1' ");
				if(timeEnd!=null){
					String endTimeString = new SimpleDateFormat("yyyy-MM-dd").format(timeEnd);
					queryStr.append(" and dimission_date > to_date('"+endTimeString+"','yyyy-MM-dd HH24:mi:ss')");
				}	
				queryStr.append(" group by dept_name,dept_id) pnr on dt.tnode=pnr.dept_id ");
				if(!"".equals(provider)){
					queryStr.append(provider);
				}
				queryStr.append(" group by name");

				SQLQuery countQuery = session.createSQLQuery(queryStr.toString());
//				if(timeEnd!=null){
//					countQuery.setTimestamp(0, timeEnd);
//				}
				return countQuery.list();
			} 
		};
		return (List) getHibernateTemplate().execute(callback);		
	}

	public List getPartnerUsersByTimeAndCity(final String time,final String cityStr){
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				StringBuffer queryStr = new StringBuffer();
				queryStr.append("from AreaDeptTree tree,PartnerUser user where 1=1 ");
				queryStr.append(" and  tree.nodeId = user.treeNodeId ");
				if(!"".equals(cityStr)){
					queryStr.append(" and  tree.areaId in ("+cityStr+") ");
				}
				if(!"".equals(time)){
					//Informix数据库
					if ("org.hibernate.dialect.InformixDialect".equals(ApplicationContextHolder.getInstance().getHQLDialect())) {
						queryStr.append(" and tree.createTime< '"+time+"-01 00:00:00' ");
					}
					//Oracle数据库		
					else if("org.hibernate.dialect.OracleDialect".equals(ApplicationContextHolder.getInstance().getHQLDialect())){
						queryStr.append(" and tree.createTime< to_date('"+time+"','yyyy-mm') ");
					}
				}	

				queryStr.append("order by tree.areaId, user.serviceProfessional");
				List list = getHibernateTemplate().find(queryStr.toString());
				return list;
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}
	public List getPartnersByTimeAndCity(final String time,final String cityStr){
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				StringBuffer queryStr = new StringBuffer();
				queryStr.append("from AreaDeptTree tree,PartnerDept dept where 1=1 ");
				queryStr.append(" and dept.id = tree.interfaceHeadId ");
				if(!"".equals(cityStr)){
					queryStr.append(" and  tree.areaId in ("+cityStr+") ");
				}
				if(!"".equals(time)){
					//Informix数据库
					if ("org.hibernate.dialect.InformixDialect".equals(ApplicationContextHolder.getInstance().getHQLDialect())) {
						queryStr.append(" and tree.createTime< '"+time+"-01 00:00:00' ");
					}
					//Oracle数据库		
					else if("org.hibernate.dialect.OracleDialect".equals(ApplicationContextHolder.getInstance().getHQLDialect())){
						queryStr.append(" and  tree.createTime< to_date('"+time+"','yyyy-mm') ");
					}
				}	
				queryStr.append(" and  tree.nodeType = 'factory' ");
				queryStr.append("order by tree.areaId, tree.interfaceHeadId");
				List list = getHibernateTemplate().find(queryStr.toString());
				return list;
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}

//	人力资源视图
	public List getReportPersonViewStat(final String regionStr) {
		
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				StringBuffer queryStr = new StringBuffer();
				
				queryStr.append(" select tp.regionName,tp.partner,pu.service_professional,sum(puc),sum(pubc),sum(pumlc),sum(pumoc),sum(pumglc),sum(pumgoc) ");
				queryStr.append(" from (select tree.parent_node_id  as region ,tsa.areaname as regionName ,dept.name as partner ,  ");
				queryStr.append(" tree.nodename as spartner,tree.node_id  ");
				queryStr.append(" from pnr_dept dept,pnr_areadepttree tree  ,taw_system_area tsa  ");
				queryStr.append(" where dept.id=tree.interface_head_id and tree.node_type='factory'  and tree.areaid=tsa.areaid "+ regionStr +") tp  " );
				queryStr.append(" left join (select area_name,area_id,dept_name,dept_id,service_professional,count(t.id) puc ");
				
				queryStr.append(" from pnr_user  left join pnr_baseinfo_resume t  on person_card_no =t.id_card_no  where deleted ='0' and state='1'  ");
				queryStr.append(" group by area_name,area_id,dept_name,dept_id,service_professional) pu ");
				queryStr.append(" on tp.region= pu.area_id and tp.node_id = pu.dept_id ");
				queryStr.append(" left join (select  area_name,area_id,dept_name,dept_id,service_professional,count(t.id) pubc ");
				queryStr.append(" from pnr_user left join pnr_baseinfo_resume t  on person_card_no =t.id_card_no ");
				queryStr.append(" where isbackBone = '1' and deleted='0' and state='1' group by  area_name,area_id,dept_name,dept_id,service_professional) pub ");
				
				queryStr.append(" on tp.region= pub.area_id and tp.node_id = pub.dept_id and pu.service_professional=pub.service_professional  ");
				queryStr.append(" left join (select area_name,area_id,dept_name,dept_id,service_professional,count(pnr_user.id) pumlc ");
				
				queryStr.append(" from pnr_user left join pnr_baseinfo_resume t  on person_card_no =t.id_card_no ");
				queryStr.append(" where commencement_date > to_date('2009-01-06 00:00:00','yyyy-MM-dd HH24:mi:ss') and deleted='0'  and state='1'  ");
				queryStr.append(" group by area_name,area_id,dept_name,dept_id,service_professional) puml ");
				queryStr.append(" on tp.region= puml.area_id and tp.node_id = puml.dept_id and pu.service_professional=puml.service_professional ");
				queryStr.append(" left join (select  area_name,area_id,dept_name,dept_id,service_professional,count(pnr_user.id) pumoc ");
				queryStr.append(" from pnr_user left join pnr_baseinfo_resume t  on person_card_no =t.id_card_no ");
				queryStr.append(" where dimission_date > to_date('2009-01-06 00:00:00','yyyy-MM-dd HH24:mi:ss') and deleted='0'  and state='1' ");
				queryStr.append(" group by  area_name,area_id,dept_name,dept_id,service_professional) pumo ");
				queryStr.append(" on tp.region= pumo.area_id and tp.node_id = pumo.dept_id and pu.service_professional=pumo.service_professional ");
				queryStr.append(" left join (select area_name,area_id,dept_name,dept_id,service_professional,count(pnr_user.id) pumglc ");
				queryStr.append(" from pnr_user left join pnr_baseinfo_resume t  on person_card_no =t.id_card_no  ");
				queryStr.append(" where commencement_date > to_date('2009-01-06 00:00:00','yyyy-MM-dd HH24:mi:ss')  and  isbackBone = '1' and deleted='0'  and state='1'  ");
				queryStr.append(" group by area_name,area_id,dept_name,dept_id,service_professional) pumgl ");
				queryStr.append(" on tp.region= pumgl.area_id and tp.node_id = pumgl.dept_id and pu.service_professional=pumgl.service_professional  ");
				queryStr.append(" left join (select area_name,area_id,dept_name,dept_id,service_professional,count(pnr_user.id) pumgoc  ");
				queryStr.append(" from pnr_user left join pnr_baseinfo_resume t  on person_card_no =t.id_card_no   ");
				queryStr.append(" where dimission_date >to_date('2009-01-06 00:00:00','yyyy-MM-dd HH24:mi:ss')  and isbackBone = '1' and deleted='0'  and state='1'   ");
				queryStr.append(" group by area_name,area_id,dept_name,dept_id,service_professional) pumgo  ");

				queryStr.append(" on tp.region= pumgo.area_id and tp.node_id = pumgo.dept_id and pu.service_professional=pumgo.service_professional  ");
				queryStr.append(" group by tp.regionName,tp.partner,pu.service_professional order by tp.regionName,tp.partner,pu.service_professional  ");
				SQLQuery countQuery = session.createSQLQuery(queryStr.toString());
//				if(timeEnd!=null){
//					countQuery.setTimestamp(0, timeEnd);
//				}
				return countQuery.list();
			} 
		};
		return (List) getHibernateTemplate().execute(callback);		
	}


	/**
	 * Kpi统计
	 * 2010-01-19
	 * author:benweiwei
	 */
	public List getReportKPImStat(final String province,final String areaStr,final String providerStr,final String gridStr,final String year,final String month,final String table) {
		
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				StringBuffer queryStr = new StringBuffer();
/*				queryStr.append(" select ta,dn,pm.grid_name,pm.workorder_atr,pm.bs_warning,pm.bsw_reduce,pm.dropcall_rate, ");
				queryStr.append(" pm.bsout_rate,pm.mbplot,pm.wireless_in,pm.mostfail_rate,pm.nofai_ramrate from  ");
				queryStr.append(" (select  tsa.areaname tan,tsa.areaid ta,tree.parent_node_id tp,dept.name dn, dept.tree_node_id,tree.nodename,tree.node_id tn from pnr_dept dept ");
				queryStr.append(" left join taw_system_area tsa on dept.area_id=tsa.parentareaid ");
				queryStr.append(" left join pnr_areadepttree tree on dept.id=tree.interface_head_id and tsa.areaid=tree.areaid and node_type='factory'");
				queryStr.append(" where area_id='"+province+"') left join (select * from "+table+" where isdel=0" );
*/				
				SQLQuery countQuery = null;
				if ("org.hibernate.dialect.OracleDialect".equals(ApplicationContextHolder.getInstance().getHQLDialect())) {
					queryStr.append("  select partner.areaid,partner.name,pm.grid_name,pm.workorder_atr," +
							"pm.bs_warning,pm.bsw_reduce,pm.dropcall_rate, " +
							"pm.bsout_rate,pm.mbplot,pm.wireless_in,pm.mostfail_rate,pm.nofai_ramrate from " +
							"(select tree.areaid as areaid,dept.name as name,tree.nodename as nodename from " +
							"pnr_dept dept ,pnr_areadepttree tree  " +
							"where dept.id=tree.interface_head_id and tree.node_type='factory') partner  " );
							
					queryStr.append(" left join (select * from "+table+" where isdel=0" );
	
					if(!"".equals(year)){
						queryStr.append(year);
					}	
					if(!"".equals(month)){
						queryStr.append(month);
					}
				   //queryStr.append(") pm on ta=region and nodename=company_name where 1=1");
					queryStr.append(") pm  on partner.areaid=pm.region and partner.nodename=pm.company_name where 1=1 ");
					if(!"".equals(areaStr)){
						queryStr.append(areaStr);
					}
					if(!"".equals(providerStr)){
						queryStr.append(providerStr);
					}
					if(!"".equals(gridStr)){
						queryStr.append(gridStr);
					}
					queryStr.append(" order by partner.areaid,partner.name,pm.grid_name ");
	
					countQuery = session.createSQLQuery(queryStr.toString());
				}
				else if("org.hibernate.dialect.InformixDialect".equals(ApplicationContextHolder.getInstance().getHQLDialect())){
					queryStr.append("  select partner.tree_areaid,partner.tree_nodename,pm.grid_name,pm.workorder_atr," +
							"pm.bs_warning,pm.bsw_reduce,pm.dropcall_rate, " +
							"pm.bsout_rate,pm.mbplot,pm.wireless_in,pm.mostfail_rate,pm.nofai_ramrate from " +
							" v_pnr_dept_area partner   " );
							
					queryStr.append(" left join "+table +" " );

					queryStr.append(" pm  on partner.tree_areaid=pm.region and partner.tree_nodename=pm.company_name where 1=1 ");
					
					if(!"".equals(year)){
						queryStr.append(year);
					}	
					if(!"".equals(month)){
						queryStr.append(month);
					}
					if(!"".equals(areaStr)){
						queryStr.append(areaStr);
					}
					if(!"".equals(providerStr)){
						queryStr.append(providerStr);
					}
					if(!"".equals(gridStr)){
						queryStr.append(gridStr);
					}
					queryStr.append(" order by partner.tree_areaid,partner.tree_nodename,pm.grid_name ");
	
					countQuery = session.createSQLQuery(queryStr.toString());

				}
				return countQuery.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
		
	}

	public List getPartnersByTime(final String time){
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				StringBuffer queryStr = new StringBuffer();
				queryStr.append("from AreaDeptTree tree,PartnerDept dept where 1=1 ");
				queryStr.append(" and dept.id = tree.interfaceHeadId ");
				queryStr.append(" and  tree.nodeType = 'factory' ");
				queryStr.append(" and  tree.createTime< to_date('"+time+"','yyyy-mm') ");
				queryStr.append("order by tree.interfaceHeadId");
				List list = getHibernateTemplate().find(queryStr.toString());
				return list;
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}
	public List getOilEngineByPartner(final String cityStr){
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				StringBuffer queryStr = new StringBuffer();
				queryStr.append("from AreaDeptTree tree,OilEngineConfigure conf where 1=1 ");
				queryStr.append(" and tree.nodeName = conf.provider ");
				if(!"".equals(cityStr)){
					queryStr.append(" and  tree.areaId in ("+cityStr+") ");
				} 
				queryStr.append("order by tree.areaId, tree.interfaceHeadId");
				List list = getHibernateTemplate().find(queryStr.toString());
				return list;
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}
	public List getOilByPartner(final String endTime,final String cityStr){
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				StringBuffer queryStr = new StringBuffer();
				queryStr.append("from AreaDeptTree tree,TawPartnerOil oil where 1=1 ");
				queryStr.append(" and oil.dept_id = tree.nodeId ");
				if(!"".equals(cityStr)){
					queryStr.append(" and  tree.areaId in ("+cityStr+") ");
				} 
				queryStr.append(" and  tree.createTime< to_date('"+endTime+"','yyyy-mm') ");
				queryStr.append(" order by tree.areaId, tree.interfaceHeadId");
				List list = getHibernateTemplate().find(queryStr.toString());
				return list;
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}
	public List getOilList(final String endTime,final String areaId,final String partnerId,final String power){
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				StringBuffer queryStr = new StringBuffer();
				queryStr.append("from AreaDeptTree tree,TawPartnerOil oil where 1=1 ");
				queryStr.append(" and oil.dept_id = tree.nodeId ");
				if(!"".equals(partnerId)){
					queryStr.append(" and  tree.interfaceHeadId ='"+partnerId+"'");
				}
				if(!"".equals(areaId)){
					queryStr.append(" and  tree.areaId ='"+areaId+"'");
				} 
				if(!"".equals(endTime)){
					queryStr.append(" and  tree.createTime< to_date('"+endTime+"','yyyy-mm') ");
				}
				if(!"".equals(power)){
					queryStr.append(" and  oil.power = '"+power+"' ");
				}
				queryStr.append(" order by tree.areaId, tree.interfaceHeadId");
				
				List list = getHibernateTemplate().find(queryStr.toString());
				return list;
			}
		};
		return (List) getHibernateTemplate().execute(callback);	
	}
	
	public List getOilStatByState(final String time,final String cityStr){
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				StringBuffer queryStr = new StringBuffer();
				queryStr.append("from AreaDeptTree tree,PartnerDept dept where 1=1 ");
				queryStr.append(" and dept.id = tree.interfaceHeadId ");
				if(!"".equals(cityStr)){
					queryStr.append(" and  tree.areaId in ("+cityStr+") ");
				}
				queryStr.append(" and  tree.createTime< to_date('"+time+"','yyyy-mm') ");
				queryStr.append("order by tree.areaId, tree.interfaceHeadId");
				List list = getHibernateTemplate().find(queryStr.toString());
				return list;
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}

	public List getOilNumListByType(final String endTime,final String partnerId,final String kind,final String state,final String regionStr){
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				StringBuffer queryStr = new StringBuffer();
				queryStr.append("select tree.interfaceHeadId,dept.name,oil.kind,oil.state,count(oil.id) ");
				queryStr.append(" from TawPartnerOil oil,AreaDeptTree tree ,PartnerDept dept ");
				queryStr.append(" where 1=1 and oil.dept_id = tree.nodeId and tree.interfaceHeadId =dept.id ");
				queryStr.append(regionStr);
				if(!"".equals(endTime)){
					queryStr.append(" and oil.addTime<to_date('"+endTime+"','yyyy-mm') ");
				}
				if(!"".equals(partnerId)){
					queryStr.append(" and tree.interfaceHeadId ='"+partnerId+"' ");
				}
				if(!"".equals(kind)){
					queryStr.append(" and oil.kind ='"+kind+"' ");
				}
				if(!"".equals(state)){
					queryStr.append(" and oil.state ='"+state+"' ");
				}
				queryStr.append(" group by tree.interfaceHeadId,dept.name,oil.kind,oil.state ");
				queryStr.append(" order by tree.interfaceHeadId,oil.kind,oil.state ");
				List list = getHibernateTemplate().find(queryStr.toString());
				return list;
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}
	
	public List getGridSatisfyStat(final String region,final String provider,final String gridName,final String year,final String month, final List LineListRegion){
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String whereStr="";
				StringBuffer queryStr = new StringBuffer();
				queryStr.append(" from PartnerDept dept,AreaDeptTree tree,Grid grid,GridSatisfaction gsf ");
				queryStr.append(" where gsf.isDel = '0' and dept.id = tree.interfaceHeadId and tree.nodeName = gsf.provider and gsf.grid = grid.gridName  ");
				if(region !=null&&!region.equals("")){
					queryStr.append(" and gsf.region = '"+region+"' ");
				}else{
				    for(int i = 0;LineListRegion.size()>i;i++){
				    	TawSystemArea tawSystemArea = (TawSystemArea)LineListRegion.get(i);
				    	if(i==0){
				    		whereStr+=" and ( gsf.region = '";
				    	}else{
				    		whereStr+=" or gsf.region = '";
				    	}
				    	whereStr+=tawSystemArea.getAreaid();
				    	whereStr+="' ";
				    	if(i==LineListRegion.size()-1){
				    		whereStr+=")";
				    	}
				    }
				    queryStr.append(whereStr);
				}
				
				if(provider !=null&&!provider.equals("")){
					queryStr.append(" and dept.name = '"+provider+"' ");
				}
				if(gridName !=null&&!gridName.equals("")){
					queryStr.append(" and gsf.grid = '"+gridName+"' ");
				}
				if(year !=null&&!year.equals("")){
					queryStr.append(" and gsf.year = '"+year+"' ");
				}
				if(month !=null&&!month.equals("")){
					queryStr.append(" and gsf.month = '"+month+"' ");
					queryStr.append(" order by gsf.year, gsf.month, gsf.region, dept.name, gsf.grid");
				}else{
					queryStr.append(" order by gsf.year, gsf.region, dept.name, gsf.grid");
				}
				Query query = session.createQuery(queryStr.toString());
				List result = query.list();
				return result;
			}
		};
		return (List) getHibernateTemplate().execute(callback);
	}

	public List getPointPie(final String area){
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				StringBuffer queryStr = new StringBuffer();
               queryStr.append("select dept.name,count(point.id)");
				queryStr.append(" from PartnerDept dept,Point point,AreaDeptTree tree ");
				queryStr.append(" where tree.interfaceHeadId = dept.id ");
				queryStr.append(" and point.provider = tree.nodeName ");
				queryStr.append(" and point.region = tree.areaId ");
				queryStr.append(" and dept.deleted=0 ");
				queryStr.append(" and point.isDel=0  ");
				if(area !=null&&!area.equals("")){
					queryStr.append(" and point.region ="+area);
				}
				queryStr.append(" group by tree.interfaceHeadId,dept.name ");
				Query query = session.createQuery(queryStr.toString());
				List result = query.list();
				return result;
			}
		};
		return (List) getHibernateTemplate().execute(callback);
	}
	public List getSitePie(final String area){
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				StringBuffer queryStr = new StringBuffer();
				queryStr.append("select dept.name,count(site.id)");
				queryStr.append(" from PartnerDept dept,Site site,AreaDeptTree tree ");
				queryStr.append(" where tree.interfaceHeadId = dept.id ");
				queryStr.append(" and site.provider = tree.nodeName ");
				queryStr.append(" and site.region = tree.areaId ");
				queryStr.append(" and dept.deleted=0 ");
				queryStr.append(" and site.isDel=0  ");
				if(area !=null&&!area.equals("")){
					queryStr.append(" and site.region ="+area);
				}
				queryStr.append(" group by tree.interfaceHeadId,dept.name ");
				Query query = session.createQuery(queryStr.toString());
				List result = query.list();
				return result;
			}
		};
		return (List) getHibernateTemplate().execute(callback);
	}	
	
	
		
	public List getServiceSpeedStat(final String region,final String provider,final String gridName,final String year,final String month,final List LineListPartner){
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				String whereStr ="";
				StringBuffer queryStr = new StringBuffer();
				queryStr.append(" from PartnerDept dept,AreaDeptTree tree,Grid grid,ServiceSpeed serspd ");
				queryStr.append(" where serspd.isDel = '0' and dept.id = tree.interfaceHeadId and tree.nodeName = serspd.provider and serspd.grid = grid.gridName  ");
				if(region !=null&&!region.equals("")){
					queryStr.append(" and serspd.region = '"+region+"' ");
				}else{
				    for(int i = 0;LineListPartner.size()>i;i++){
				    	TawSystemArea tawSystemArea = (TawSystemArea)LineListPartner.get(i);
				    	if(i==0){
				    		whereStr+=" and ( serspd.region = '";
				    	}else{
				    		whereStr+=" or serspd.region = '";
				    	}
				    	whereStr+=tawSystemArea.getAreaid();
				    	whereStr+="' ";
				    	if(i==LineListPartner.size()-1){
				    		whereStr+=")";
				    	}
				    }
				    queryStr.append(whereStr);
				}
				
				if(provider !=null&&!provider.equals("")){
					queryStr.append(" and dept.name = '"+provider+"' ");
				}
				if(gridName !=null&&!gridName.equals("")){
					queryStr.append(" and serspd.name = '"+gridName+"' ");
				}
				if(year !=null&&!year.equals("")){
					queryStr.append(" and serspd.year = '"+year+"' ");
				}
				if(month !=null&&!month.equals("")){
					queryStr.append(" and serspd.month = '"+month+"' ");
					queryStr.append(" order by serspd.year, serspd.month, serspd.region, dept.name, serspd.grid");
				}else{
					queryStr.append(" order by serspd.year, serspd.region, dept.name, serspd.grid");
				}
				Query query = session.createQuery(queryStr.toString());
				List result = query.list();
				return result;
			}
		};
		return (List) getHibernateTemplate().execute(callback);
	}
	public List getPersonConfig(final String cityStr){
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				StringBuffer queryStr = new StringBuffer();
				queryStr.append("from AreaDeptTree tree,PersonConfig conf where 1=1 ");
				queryStr.append(" and tree.nodeName = conf.provider ");
				if(!"".equals(cityStr)){
					queryStr.append(" and  tree.areaId in ("+cityStr+") ");
				} 
				queryStr.append("order by tree.areaId, tree.interfaceHeadId");
				List list = getHibernateTemplate().find(queryStr.toString());
				return list;
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}
//	得到人员实配数
	public List getPersonByPartner(final String endTime,final String cityStr){
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				StringBuffer queryStr = new StringBuffer();
				queryStr.append("from AreaDeptTree tree,PartnerUser user where 1=1 ");
				queryStr.append(" and user.deptId = tree.nodeId ");
				if(!"".equals(cityStr)){
					queryStr.append(" and  tree.areaId in ("+cityStr+") ");
				} 
				if(!"".equals(endTime)){
					if ("org.hibernate.dialect.InformixDialect".equals(ApplicationContextHolder.getInstance().getHQLDialect())) {
						queryStr.append(" and  tree.createTime< to_date('"+endTime+"','%Y-%M') ");
					}
					else if("org.hibernate.dialect.OracleDialect".equals(ApplicationContextHolder.getInstance().getHQLDialect())){
						queryStr.append(" and  tree.createTime< to_date('"+endTime+"','yyyy-mm') ");
					}
				}					
//					queryStr.append(" and  tree.createTime< to_date('"+endTime+"','yyyy-mm') ");
				queryStr.append(" order by tree.areaId, tree.interfaceHeadId");
				List list = getHibernateTemplate().find(queryStr.toString());
				return list;
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}
	/**
	 * 得到各地域各合作伙伴数量用于显示Chart图表
	 * @param time 时间
	 * @param city 地域
	 */
	public List getPartnerNumforChart(final String time,final String city){
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				StringBuffer queryStr = new StringBuffer();
				queryStr.append("select  dept.name,count(tree.id),tree.interfaceHeadId from AreaDeptTree tree,PartnerDept dept where 1=1 ");
				queryStr.append(" and dept.id = tree.interfaceHeadId ");
				queryStr.append(" and tree.partnerid <> tree.interfaceHeadId ");
				queryStr.append(" and dept.deleted=0 ");
				if(!"".equals(city)){
					queryStr.append(" and  tree.areaId ='"+city+"' ");
				}
				if(!"".equals(time)){
					//Informix数据库
					if ("org.hibernate.dialect.InformixDialect".equals(ApplicationContextHolder.getInstance().getHQLDialect())) {
						queryStr.append(" and tree.createTime< '"+time+"-01 00:00:00' ");
					}
					//Oracle数据库		
					else if("org.hibernate.dialect.OracleDialect".equals(ApplicationContextHolder.getInstance().getHQLDialect())){
						queryStr.append(" and  tree.createTime< to_date('"+time+"','yyyy-mm') ");
					}
				}	
				queryStr.append(" and  tree.nodeType = 'factory' ");
				queryStr.append(" group by tree.interfaceHeadId,dept.name ");
				List list = getHibernateTemplate().find(queryStr.toString());
				return list;
			}
		};
		return (List) getHibernateTemplate().execute(callback);		
	}



	public List getPartnerNumApparatuss(final String city) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				StringBuffer queryStr = new StringBuffer();
				queryStr.append("select dept.name, count(apparatus.id), dept.id from TawApparatus apparatus,PartnerDept dept ");
				queryStr.append("where dept.id = apparatus.bigpartnerid ");
				if(city!=null&&!"".equals(city)){
					queryStr.append("and apparatus.area_id = '"+city+"'");
				}
				queryStr.append("group by dept.id,dept.name");
				List list = getHibernateTemplate().find(queryStr.toString());
				return list;
			}
		};
		return (List) getHibernateTemplate().execute(callback);	
	}



	public List getPartnerNumCars(final String city) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				StringBuffer queryStr = new StringBuffer();
				queryStr.append("select dept.name, count(car.id), dept.id from TawPartnerCar car,PartnerDept dept ");
				queryStr.append("where dept.id = car.bigpartnerid ");
				if(city!=null&&!"".equals(city)){
					queryStr.append("and car.area_id = '"+city+"'");
				}
				queryStr.append("group by dept.id,dept.name");
				List list = getHibernateTemplate().find(queryStr.toString());
				return list;
			}
		};
		return (List) getHibernateTemplate().execute(callback);	
	}



	public List getPartnerNumOils(final String city) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				StringBuffer queryStr = new StringBuffer();
				queryStr.append("select dept.name, count(dept.id), dept.id  from TawPartnerOil oil,PartnerDept dept ");
				queryStr.append("where dept.id = oil.bigpartnerid ");
				if(city!=null&&!"".equals(city)){
					queryStr.append("and oil.area_id = '"+city+"'");
				}
				queryStr.append("group by dept.id,dept.name");
				List list = getHibernateTemplate().find(queryStr.toString());
				return list;
			}
		};
		return (List) getHibernateTemplate().execute(callback);	
	}



	public List getPartnerNumUsers(final String city) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				StringBuffer queryStr = new StringBuffer();
				queryStr.append("select dept.name, count(user.id), dept.id from  PartnerUser user,PartnerDept dept ");
				queryStr.append("where dept.id = user.bigpartnerid ");
				if(city!=null&&!"".equals(city)){
					queryStr.append("and user.areaidtrue = '"+city+"' ");
				}
				queryStr.append("group by dept.id,dept.name");
				List list = getHibernateTemplate().find(queryStr.toString());
				return list;
			}
		};
		return (List) getHibernateTemplate().execute(callback);	
	}
}
