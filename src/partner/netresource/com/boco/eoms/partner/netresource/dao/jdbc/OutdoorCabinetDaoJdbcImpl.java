package com.boco.eoms.partner.netresource.dao.jdbc;

import java.sql.PreparedStatement;
import java.util.List;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.boco.eoms.partner.netresource.dao.IOutdoorCabinetDaoJdbc;
import com.boco.eoms.partner.netresource.model.bs.OutdoorCabinet;

public class OutdoorCabinetDaoJdbcImpl extends JdbcDaoSupport implements IOutdoorCabinetDaoJdbc {
		
	
	public void importData(List<Object> mainList, PreparedStatement st1,int flag)
	throws Exception {
// TODO Auto-generated method stub
		if(flag==1){
		for(int i=0;i<mainList.size();i++){
			OutdoorCabinet pnr = (OutdoorCabinet) mainList.get(i);
			st1.setString(1, pnr.getId());
			st1.setString(2, pnr.getResIdentifier());
			st1.setString(3, pnr.getDataIdentifier());
			st1.setString(4, pnr.getOutCabinetName());
			st1.setString(5, pnr.getAnotherOCName());
			st1.setString(6, pnr.getCity());
			st1.setString(7, pnr.getCountry());			
			st1.setString(8, pnr.getMaintenanceArea());
			st1.setString(9, pnr.getOcAddress());
			st1.setString(10, pnr.getPlace());
			st1.setDouble(11, pnr.getLongitude());
			st1.setDouble(12,pnr.getLatitude());
			st1.setString(13,pnr.getIsRentSpace());
			st1.setString(14,pnr.getRentalSpaceArea());
			st1.setString(15, pnr.getLessor());
			st1.setString(16, pnr.getLeaseDuration());
			st1.setString(17, pnr.getRemark());
			st1.setString(18, pnr.getSpecialty());
			st1.setString(19, pnr.getResType());
			st1.setString(20, pnr.getResLevel());
			
			st1.addBatch();
		}
		}else if(flag==2){
	
			
	
		}
	}
}
