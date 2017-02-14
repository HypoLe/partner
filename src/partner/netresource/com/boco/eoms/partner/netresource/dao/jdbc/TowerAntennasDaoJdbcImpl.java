package com.boco.eoms.partner.netresource.dao.jdbc;

import java.sql.PreparedStatement;
import java.util.List;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.boco.eoms.partner.netresource.dao.ITowerAntennasDaoJdbc;
import com.boco.eoms.partner.netresource.model.bs.TowerAntennas;

public class TowerAntennasDaoJdbcImpl extends JdbcDaoSupport implements ITowerAntennasDaoJdbc {
		
	
	public void importData(List<Object> mainList, PreparedStatement st1,int flag)
	throws Exception {
// TODO Auto-generated method stub
if(flag==1){
for(int i=0;i<mainList.size();i++){
	TowerAntennas pnr = (TowerAntennas) mainList.get(i);
	st1.setString(1, pnr.getId());
	st1.setString(2, pnr.getAnotherTaName());
	st1.setString(3, pnr.getTaName());
	st1.setString(4, pnr.getCity());
	st1.setString(5, pnr.getCountry());
	st1.setString(6, pnr.getSpecialty());
	st1.setString(7, pnr.getResType());
	st1.setString(8, pnr.getResLevel());
	st1.setString(9, pnr.getEquity());
	st1.setString(10, pnr.getTowerType());
	st1.setString(11, pnr.getAntennaType());
	st1.setString(12, pnr.getTowerHeight());
	st1.setDouble(13,pnr.getLongitude());
	st1.setDouble(14,pnr.getLatitude());
	st1.setDouble(15,pnr.getRruNumber());
	st1.setDouble(16,pnr.getRepeaterNumber());

	st1.addBatch();
}
}else if(flag==2){
		
}
}
}
