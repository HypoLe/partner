package com.boco.eoms.partner.netresource.dao.jdbc;

import java.sql.PreparedStatement;
import java.util.List;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.boco.eoms.partner.netresource.dao.IRoomResourceDaoJdbc;
import com.boco.eoms.partner.netresource.model.bs.RoomResource;

public class RoomResourceDaoJdbcImpl extends JdbcDaoSupport implements IRoomResourceDaoJdbc {
		
	
	public void importData(List<Object> mainList, PreparedStatement st1,int flag)
	throws Exception {
// TODO Auto-generated method stub
		if(flag==1){
		for(int i=0;i<mainList.size();i++){
			RoomResource pnr = (RoomResource) mainList.get(i);
			st1.setString(1, pnr.getId());
			st1.setString(2, pnr.getReNumber());
			st1.setString(3, pnr.getReName());
			st1.setString(4, pnr.getCity());
			st1.setString(5, pnr.getCountry());
			st1.setString(6, pnr.getAddress());
			st1.setDouble(7, pnr.getLongitude());
			st1.setDouble(8, pnr.getLatitude());
			st1.setString(9, pnr.getSource2g());
			st1.setString(10, pnr.getBcch());
			st1.setString(11, pnr.getSource3g());
			st1.setString(12, pnr.getPsc());
			st1.setString(13, pnr.getLocation());
			st1.setString(14, pnr.getModel2g());
			st1.setString(15, pnr.getNumber2g());
			st1.setString(16, pnr.getModel3g());
			st1.setString(17, pnr.getNumber3g());
			st1.setString(18, pnr.getContact());
			st1.setString(19, pnr.getContactTel());
			st1.setString(20, pnr.getLac());
			st1.setString(21, pnr.getCi());
			st1.setString(22, pnr.getSpecialty());
			st1.setString(23, pnr.getResType());
			st1.setString(24, pnr.getResLevel());
			
			st1.addBatch();
		}
		}else if(flag==2){
			
		}
}
}
