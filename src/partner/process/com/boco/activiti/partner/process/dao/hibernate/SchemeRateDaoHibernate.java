package com.boco.activiti.partner.process.dao.hibernate;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.SQLQuery;

import com.boco.activiti.partner.process.dao.ISchemeRateDao;
import com.boco.activiti.partner.process.model.SchemeRate;
import com.boco.activiti.partner.process.model.SchemeRateRejectModel;
import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.commons.util.xml.XmlManage;

public class SchemeRateDaoHibernate extends BaseDaoHibernate implements ISchemeRateDao{
	/**
	 * 方案合格率统计list查询
	  * @author zhoukeqing 
	  * @title: schemeRateList
	  * @date Jul 26, 2016 3:39:51 PM
	  * @param userid
	  * @param flag
	  * @param county   time
	  * @return Map<String,Object>
	 */
	@Override
	public Map<String,Object> schemeRateList(String county,String startTime,String endTime) {
		String path = new String("/com/boco/activiti/partner/process/config/schemeRate-stat-config.xml");
		String sql = XmlManage.getFile(path).getProperty("byArea");
		if(!"".equals(startTime)&&!"".equals(endTime)){
			sql=sql.replaceAll("@check_time@", 
						" to_char(t.check_time, 'yyyy-mm-dd') >= '"+startTime+"' and to_char(t.check_time, 'yyyy-mm-dd') <= '"+endTime+"'");
			sql=sql.replaceAll("@distributed_interface_time@",
					" to_char(n.distributed_interface_time, 'yyyy-mm-dd') >= '"+startTime+"' and to_char(n.distributed_interface_time, 'yyyy-mm-dd') <= '"+endTime+"'");
		}else{
			sql=sql.replaceAll("@check_time@", 
					" to_char(t.check_time, 'yyyy-mm')=to_char(sysdate, 'yyyy-mm') ");
			sql=sql.replaceAll("@distributed_interface_time@",
				" to_char(n.distributed_interface_time, 'yyyy-mm')=to_char(sysdate, 'yyyy-mm') ");
		}
		System.out.println("方案合格率统计list查询:" + sql);
		SQLQuery query = getSession().createSQLQuery(sql);
		@SuppressWarnings("unchecked")
		List<Object[]> temp = query.list();
		Iterator<Object[]> iterator = temp.iterator();
		List<SchemeRate> list = new ArrayList<SchemeRate>();
		try {
			while (iterator.hasNext()) {
				Object[] obj = (Object[]) iterator.next();
				SchemeRate SchemeRate = new SchemeRate();
				SchemeRate.setCounty((isNotNULL(obj[0])).toString());
				SchemeRate.setInterface_audit_num(Integer.parseInt((isNotNULL(obj[1])).toString()));
				SchemeRate.setInterface_audit_money(Double.parseDouble((isNotNULL(obj[2])).toString()));
				SchemeRate.setInterface_approved_num(Integer.parseInt((isNotNULL(obj[3])).toString()));
				SchemeRate.setInterface_approved_money(Double.parseDouble((isNotNULL(obj[4])).toString()));
				SchemeRate.setInterface_delay_approved_num(Integer.parseInt((isNotNULL(obj[5])).toString()));
				SchemeRate.setInterface_delay_approved_money(Double.parseDouble((isNotNULL(obj[6])).toString()));
				SchemeRate.setInterface_reject_num(Integer.parseInt((isNotNULL(obj[7])).toString()));
				SchemeRate.setInterface_reject_money(Double.parseDouble((isNotNULL(obj[8])).toString()));
				SchemeRate.setInterface_monthapproved_num(Integer.parseInt((isNotNULL(obj[9])).toString()));
				SchemeRate.setInterface_monthapproved_money(Double.parseDouble((isNotNULL(obj[10])).toString()));
				
				SchemeRate.setArtery_audit_num(Integer.parseInt((isNotNULL(obj[11])).toString()));
				SchemeRate.setArtery_audit_money(Double.parseDouble((isNotNULL(obj[12])).toString()));
				SchemeRate.setArtery_approved_num(Integer.parseInt((isNotNULL(obj[13])).toString()));
				SchemeRate.setArtery_approved_money(Double.parseDouble((isNotNULL(obj[14])).toString()));
				SchemeRate.setArtery_delay_approved_num(Integer.parseInt((isNotNULL(obj[15])).toString()));
				SchemeRate.setArtery_delay_approved_money(Double.parseDouble((isNotNULL(obj[16])).toString()));
				SchemeRate.setArtery_reject_num(Integer.parseInt((isNotNULL(obj[17])).toString()));
				SchemeRate.setArtery_reject_money(Double.parseDouble((isNotNULL(obj[18])).toString()));
				SchemeRate.setArtery_monthapproved_num(Integer.parseInt((isNotNULL(obj[19])).toString()));
				SchemeRate.setArtery_monthapproved_money(Double.parseDouble((isNotNULL(obj[20])).toString()));
				
				SchemeRate.setM_artery_audit_num(Integer.parseInt((isNotNULL(obj[21])).toString()));
				SchemeRate.setM_artery_audit_money(Double.parseDouble((isNotNULL(obj[22])).toString()));
				SchemeRate.setM_artery_approved_num(Integer.parseInt((isNotNULL(obj[23])).toString()));
				SchemeRate.setM_artery_approved_money(Double.parseDouble((isNotNULL(obj[24])).toString()));
				SchemeRate.setM_artery_delay_approved_num(Integer.parseInt((isNotNULL(obj[25])).toString()));
				SchemeRate.setM_artery_delay_approved_money(Double.parseDouble((isNotNULL(obj[26])).toString()));
				SchemeRate.setM_artery_reject_num(Integer.parseInt((isNotNULL(obj[27])).toString()));
				SchemeRate.setM_artery_reject_money(Double.parseDouble((isNotNULL(obj[28])).toString()));
				SchemeRate.setM_artery_monthapproved_num(Integer.parseInt((isNotNULL(obj[29])).toString()));
				SchemeRate.setM_artery_monthapproved_money(Double.parseDouble((isNotNULL(obj[30])).toString()));
				
				SchemeRate.setRo_audit_num(Integer.parseInt((isNotNULL(obj[31])).toString()));
				SchemeRate.setRo_audit_money(Double.parseDouble((isNotNULL(obj[32])).toString()));
				SchemeRate.setRo_approved_num(Integer.parseInt((isNotNULL(obj[33])).toString()));
				SchemeRate.setRo_approved_money(Double.parseDouble((isNotNULL(obj[34])).toString()));
				SchemeRate.setRo_delay_approved_num(Integer.parseInt((isNotNULL(obj[35])).toString()));
				SchemeRate.setRo_delay_approved_money(Double.parseDouble((isNotNULL(obj[36])).toString()));
				SchemeRate.setRo_reject_num(Integer.parseInt((isNotNULL(obj[37])).toString()));
				SchemeRate.setRo_reject_money(Double.parseDouble((isNotNULL(obj[38])).toString()));
				SchemeRate.setRo_monthapproved_num(Integer.parseInt((isNotNULL(obj[39])).toString()));
				SchemeRate.setRo_monthapproved_money(Double.parseDouble((isNotNULL(obj[40])).toString()));
				
				sumReportOnline(SchemeRate);
				list.add(SchemeRate);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		sumReport(list);
		System.out.println("result size == " + list.size());
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("size", list.size());
		map.put("schemeRateList", list);
		return map;
	}
	private void sumReport(List<SchemeRate> dataList){
		String county;
		int interface_audit_num=0;
		double interface_audit_money = 0;
		int interface_approved_num=0;
		double interface_approved_money=0;
		int interface_delay_approved_num=0;
		double interface_delay_approved_money=0;
		int interface_reject_num=0;
		double interface_reject_money=0;
		int interface_monthapproved_num=0;
		double interface_monthapproved_money = 0;

		int artery_audit_num = 0;
		double artery_audit_money = 0;
		int artery_approved_num = 0;
		double artery_approved_money = 0;
		int artery_delay_approved_num = 0;
		double artery_delay_approved_money = 0;
		int artery_reject_num = 0;
		double artery_reject_money = 0;
		int artery_monthapproved_num = 0;
		double artery_monthapproved_money = 0;

		int m_artery_audit_num = 0;
		double m_artery_audit_money = 0;
		int m_artery_approved_num = 0;
		double m_artery_approved_money = 0;
		int m_artery_delay_approved_num = 0;
		double m_artery_delay_approved_money = 0;
		int m_artery_reject_num = 0;
		double m_artery_reject_money = 0;
		int m_artery_monthapproved_num = 0;
		double m_artery_monthapproved_money = 0;

		int ro_audit_num = 0;
		double ro_audit_money = 0;
		int ro_approved_num = 0;
		double ro_approved_money = 0;
		int ro_delay_approved_num = 0;
		double ro_delay_approved_money = 0;
		int ro_reject_num = 0;
		double ro_reject_money = 0;
		int ro_monthapproved_num = 0;
		double ro_monthapproved_money = 0;

		int qualified_num=0;
		int reject_num=0;
		String qualified_rate="0.00%";
		DecimalFormat df = new DecimalFormat("#.00");  
		for(int i=0; i<dataList.size(); i++){
			SchemeRate schemeRate=dataList.get(i);
			interface_audit_num+=schemeRate.getInterface_audit_num();
			interface_audit_money +=schemeRate.getInterface_audit_money();
			interface_approved_num +=schemeRate.getInterface_approved_num();
			interface_approved_money+=schemeRate.getInterface_approved_money();
			interface_delay_approved_num+=schemeRate.getInterface_delay_approved_num();
			interface_delay_approved_money+=schemeRate.getInterface_delay_approved_money();
			interface_reject_num+=schemeRate.getInterface_reject_num();
			interface_reject_money+=schemeRate.getInterface_reject_money();
			interface_monthapproved_num+=schemeRate.getInterface_monthapproved_num();
			interface_monthapproved_money+=schemeRate.getInterface_monthapproved_money();
			
			artery_audit_num+=schemeRate.getArtery_audit_num();
			artery_audit_money+=schemeRate.getArtery_audit_money();
			artery_approved_num+=schemeRate.getArtery_approved_num();
			artery_approved_money+=schemeRate.getArtery_approved_money();
			artery_delay_approved_num+=schemeRate.getArtery_delay_approved_num();
			artery_delay_approved_money+=schemeRate.getArtery_delay_approved_money();
			artery_reject_num+=schemeRate.getArtery_reject_num();
			artery_reject_money+=schemeRate.getArtery_reject_money();
			artery_monthapproved_num+=schemeRate.getArtery_monthapproved_num();
			artery_monthapproved_money+=schemeRate.getArtery_monthapproved_money();
			
			m_artery_audit_num+=schemeRate.getM_artery_audit_num();
			m_artery_audit_money+=schemeRate.getM_artery_audit_money();
			m_artery_approved_num+=schemeRate.getM_artery_approved_num();
			m_artery_approved_money+=schemeRate.getM_artery_approved_money();
			m_artery_delay_approved_num+=schemeRate.getM_artery_delay_approved_num();
			m_artery_delay_approved_money+=schemeRate.getM_artery_delay_approved_money();
			m_artery_reject_num+=schemeRate.getM_artery_reject_num();
			m_artery_reject_money+=schemeRate.getM_artery_reject_money();
			m_artery_monthapproved_num+=schemeRate.getM_artery_monthapproved_num();
			m_artery_monthapproved_money+=schemeRate.getM_artery_monthapproved_money();
			
			ro_audit_num+=schemeRate.getRo_audit_num();
			ro_audit_money+=schemeRate.getRo_audit_money();
			ro_approved_num+=schemeRate.getRo_approved_num();
			ro_approved_money+=schemeRate.getRo_approved_money();
			ro_delay_approved_num+=schemeRate.getRo_delay_approved_num();
			ro_delay_approved_money+=schemeRate.getRo_delay_approved_money();
			ro_reject_num+=schemeRate.getRo_reject_num();
			ro_reject_money+=schemeRate.getRo_reject_money();
			ro_monthapproved_num+=schemeRate.getRo_monthapproved_num();
			ro_monthapproved_money+=schemeRate.getRo_monthapproved_money();
		}
		qualified_num=interface_approved_num+interface_delay_approved_num+artery_approved_num+
			artery_delay_approved_num+m_artery_approved_num+m_artery_approved_num+m_artery_delay_approved_num+
			ro_approved_num+ro_delay_approved_num;
		reject_num=interface_reject_num+artery_reject_num+m_artery_reject_num+ro_reject_num;
		qualified_rate=divrate(qualified_num,(qualified_num+reject_num));
		SchemeRate schemeRate=new SchemeRate();
		schemeRate.setCounty("2828");
		schemeRate.setInterface_audit_num(interface_audit_num);
		schemeRate.setInterface_audit_money(Double.parseDouble(df.format(interface_audit_money)));
		schemeRate.setInterface_approved_num(interface_approved_num);
		schemeRate.setInterface_approved_money(Double.parseDouble(df.format(interface_approved_money)));
		schemeRate.setInterface_delay_approved_num(interface_delay_approved_num);
		schemeRate.setInterface_delay_approved_money(Double.parseDouble(df.format(interface_delay_approved_money)));
		schemeRate.setInterface_reject_num(interface_reject_num);
		schemeRate.setInterface_reject_money(Double.parseDouble(df.format(interface_reject_money)));
		schemeRate.setInterface_monthapproved_num(interface_monthapproved_num);
		schemeRate.setInterface_monthapproved_money(Double.parseDouble(df.format(interface_monthapproved_money)));
		
		schemeRate.setArtery_audit_num(artery_audit_num);
		schemeRate.setArtery_audit_money(Double.parseDouble(df.format(artery_audit_money)));
		schemeRate.setArtery_approved_num(artery_approved_num);
		schemeRate.setArtery_approved_money(Double.parseDouble(df.format(artery_approved_money)));
		schemeRate.setArtery_delay_approved_num(artery_delay_approved_num);
		schemeRate.setArtery_delay_approved_money(Double.parseDouble(df.format(artery_delay_approved_money)));
		schemeRate.setArtery_reject_num(artery_reject_num);
		schemeRate.setArtery_reject_money(Double.parseDouble(df.format(artery_reject_money)));
		schemeRate.setArtery_monthapproved_num(artery_monthapproved_num);
		schemeRate.setArtery_monthapproved_money(Double.parseDouble(df.format(artery_monthapproved_money)));
		
		schemeRate.setM_artery_audit_num(m_artery_audit_num);
		schemeRate.setM_artery_audit_money(Double.parseDouble(df.format(m_artery_audit_money)));
		schemeRate.setM_artery_approved_num(m_artery_approved_num);
		schemeRate.setM_artery_approved_money(Double.parseDouble(df.format(m_artery_approved_money)));
		schemeRate.setM_artery_delay_approved_num(m_artery_delay_approved_num);
		schemeRate.setM_artery_delay_approved_money(Double.parseDouble(df.format(m_artery_delay_approved_money)));
		schemeRate.setM_artery_reject_num(m_artery_reject_num);
		schemeRate.setM_artery_reject_money(Double.parseDouble(df.format(m_artery_reject_money)));
		schemeRate.setM_artery_monthapproved_num(m_artery_monthapproved_num);
		schemeRate.setM_artery_monthapproved_money(Double.parseDouble(df.format(m_artery_monthapproved_money)));
		
		schemeRate.setRo_audit_num(ro_audit_num);
		schemeRate.setRo_audit_money(Double.parseDouble(df.format(ro_audit_money)));
		schemeRate.setRo_approved_num(ro_approved_num);
		schemeRate.setRo_approved_money(Double.parseDouble(df.format(ro_approved_money)));
		schemeRate.setRo_delay_approved_num(ro_delay_approved_num);
		schemeRate.setRo_delay_approved_money(Double.parseDouble(df.format(ro_delay_approved_money)));
		schemeRate.setRo_reject_num(ro_reject_num);
		schemeRate.setRo_reject_money(Double.parseDouble(df.format(ro_reject_money)));
		schemeRate.setRo_monthapproved_num(ro_monthapproved_num);
		schemeRate.setRo_monthapproved_money(Double.parseDouble(df.format(ro_monthapproved_money)));
		
		schemeRate.setReject_num(reject_num);
		schemeRate.setQualified_num(qualified_num);
		schemeRate.setQualified_rate(qualified_rate);
		dataList.add(schemeRate);
	}
	private void sumReportOnline(SchemeRate schemeRate){
		int interface_approved_num=schemeRate.getInterface_approved_num();
		int interface_delay_approved_num=schemeRate.getInterface_delay_approved_num();
		int artery_approved_num=schemeRate.getArtery_approved_num();
		int artery_delay_approved_num=schemeRate.getArtery_delay_approved_num();
		int m_artery_approved_num=schemeRate.getM_artery_approved_num();
		int m_artery_delay_approved_num=schemeRate.getM_artery_delay_approved_num();
		int ro_approved_num=schemeRate.getRo_approved_num();
		int ro_delay_approved_num=schemeRate.getRo_delay_approved_num();
		int qualified_num=interface_approved_num+interface_delay_approved_num+artery_approved_num+
				artery_delay_approved_num+m_artery_approved_num+m_artery_approved_num+m_artery_delay_approved_num+
				ro_approved_num+ro_delay_approved_num;
		
		int interface_reject_num=schemeRate.getInterface_reject_num();
		int artery_reject_num=schemeRate.getArtery_reject_num();
		int m_artery_reject_num=schemeRate.getM_artery_reject_num();
		int ro_reject_num=schemeRate.getRo_reject_num();
		int reject_num=interface_reject_num+artery_reject_num+m_artery_reject_num+ro_reject_num;
		
		String qualified_rate=divrate(qualified_num,(qualified_num+reject_num));
		
		schemeRate.setQualified_num(qualified_num);
		schemeRate.setReject_num(reject_num);
		schemeRate.setQualified_rate(qualified_rate);
	}
	/**
	 * Ojbect计算 a/b 
	 * @return 00.00%
	 */
	public static String divrate(int a , int sum){
		if(sum==0)
			return "-";
		float f = Float.parseFloat(String.valueOf(a))/Float.parseFloat(String.valueOf(sum));
		DecimalFormat df = new DecimalFormat("######0.00");
		return df.format(f*100)+"%";
	}
	/**
	 * 方案合格率统计 驳回原因列表钻取list
	  * @author zhoukeqing 
	  * @title: schemeRateRejectList
	  * @date Jul 26, 2016 3:39:51 PM
	  * @param userid
	  * @param flag
	  * @param county   time
	  * @return List<SchemeRateRejectModel>
	 */
	@Override
	public List<SchemeRateRejectModel> schemeRateRejectList(String city,
			String startTime,String endTime, String themeinterface, int pageSize, int firstResult,
			int endResult) {
		String sql ="select temp2.* from (select temp1.*, rownum num from (";
		sql+=" select n.city,n.country,n.themeinterface,n.theme,t.handle_description,n.process_instance_id,t.auditor from pnr_act_transfer_office_handle t  "
				+" left join pnr_act_transfer_office_main n on t.process_instance_id=n.process_instance_id "
				+" where t.link_name in ('provinceLineExamine','provinceManageExamine','provinceLineViceAudit')"
				+" and t.operation='02'  ";
		if(!"".equals(startTime)&&!"".equals(endTime)){
			sql += " and to_char(t.check_time, 'yyyy-mm-dd') >= '"+startTime+"' and to_char(t.check_time, 'yyyy-mm-dd') <= '"+endTime+"'";
		}else{
			sql += " and to_char(t.check_time,'yyyy-mm')=to_char(sysdate, 'yyyy-mm') ";
		}
		if(!"2828".equals(city)){
			sql += " and n.city='"+city+"' ";
		}else{
			sql += " and n.city is not null ";
		}
		if("interface".equals(themeinterface)){
			sql += " and n.themeinterface='interface' ";
		}else if("ro".equals(themeinterface)){
			sql += " and n.themeinterface in ('overhaul','reform')";
		}else if("artery".equals(themeinterface)){
			sql += " and n.themeinterface='arteryPrecheck' and n.key_word not in ('101230806')";
		}else if("m_artery".equals(themeinterface)){
			sql += " and n.themeinterface='arteryPrecheck' and n.key_word in ('101230806')";
		}
		sql+=" ) temp1 where rownum <="+endResult * pageSize+") temp2 where temp2.num >"+firstResult * pageSize;
		System.out.println("方案合格率统计 驳回原因列表钻取list:"+sql);
		SQLQuery query = getSession().createSQLQuery(sql);
		@SuppressWarnings("unchecked")
		List<Object[]> temp = query.list();
		Iterator<Object[]> iterator = temp.iterator();
		List<SchemeRateRejectModel> list = new ArrayList<SchemeRateRejectModel>();
		try {
			while (iterator.hasNext()) {
				Object[] obj = (Object[]) iterator.next();
				SchemeRateRejectModel schemeRateRejectModel = new SchemeRateRejectModel();
				schemeRateRejectModel.setCity((isNotNULL(obj[0])).toString());
				schemeRateRejectModel.setCounty((isNotNULL(obj[1])).toString());
				schemeRateRejectModel.setThemeinterface((isNotNULL(obj[2])).toString());
				schemeRateRejectModel.setTheme((isNotNULL(obj[3])).toString());
				schemeRateRejectModel.setHandleDescription((isNotNULL(obj[4])).toString());
				schemeRateRejectModel.setProcessInstanceId((isNotNULL(obj[5])).toString());
				schemeRateRejectModel.setAuditor((isNotNULL(obj[6])).toString());
				list.add(schemeRateRejectModel);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("方案合格率统计 驳回原因列表钻取list.size()   :"+list.size());
		return list;
	}
	private String isNotNULL(Object obj){
		if(obj != null){
			return obj.toString();
		}
		return "";

	}
	@Override
	public int schemeRateRejectTotal(String city, String startTime,String endTime,
			String themeinterface) {
		String sql=" select count(*) from pnr_act_transfer_office_handle t  "
			+" left join pnr_act_transfer_office_main n on t.process_instance_id=n.process_instance_id "
			+" where t.link_name in ('provinceLineExamine','provinceManageExamine','provinceLineViceAudit')"
			+" and t.operation='02'  ";
		if(!"".equals(startTime)&&!"".equals(endTime)){
			sql += " and to_char(t.check_time, 'yyyy-mm-dd') >= '"+startTime+"' and to_char(t.check_time, 'yyyy-mm-dd') <= '"+endTime+"'";
		}else{
			sql += " and to_char(t.check_time,'yyyy-mm')=to_char(sysdate, 'yyyy-mm') ";
		}
		if(!"2828".equals(city)){
			sql += " and n.city='"+city+"' ";
		}else{
			sql += " and n.city is not null ";
		}
		if("interface".equals(themeinterface)){
			sql += " and n.themeinterface='interface' ";
		}else if("ro".equals(themeinterface)){
			sql += " and n.themeinterface in ('overhaul','reform')";
		}else if("artery".equals(themeinterface)){
			sql += " and n.themeinterface='arteryPrecheck' and n.key_word not in ('101230806')";
		}else if("m_artery".equals(themeinterface)){
			sql += " and n.themeinterface='arteryPrecheck' and n.key_word in ('101230806')";
		}
		System.out.println("方案合格率统计 驳回原因列表钻取  total :"+sql);
		@SuppressWarnings("rawtypes")
		List query = this.getSession().createSQLQuery(sql).list();
		BigDecimal total = null;
		int inttotal = 0;
		try {
			total = (BigDecimal) query.get(0);
			inttotal = total.intValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("方案合格率统计 驳回原因列表钻取  inttotal  :"+inttotal);
		return inttotal;
	}

}
