package com.boco.eoms.partner.netresource.dao.jdbc;

import java.sql.PreparedStatement;
import java.util.List;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.boco.eoms.partner.netresource.dao.IKeyCustomerRoomDaoJdbc;
import com.boco.eoms.partner.netresource.model.bs.KeyCustomerRoom;

public class KeyCustomerRoomDaoJdbcImpl extends JdbcDaoSupport implements IKeyCustomerRoomDaoJdbc {
		
	
	public void importData(List<Object> mainList, PreparedStatement st1,int flag)
	throws Exception {
// TODO Auto-generated method stub
		if(flag==1){
		for(int i=0;i<mainList.size();i++){
			KeyCustomerRoom pnr = (KeyCustomerRoom) mainList.get(i);
			st1.setString(1, pnr.getId());
			st1.setString(2, pnr.getKcroomName());
			st1.setString(3, pnr.getCity());
			st1.setString(4, pnr.getCountry());
			st1.setString(5, pnr.getBureau());
			st1.setString(6, pnr.getAccessType());
			st1.setString(7,pnr.getKcroomAddress());
			st1.setString(8, pnr.getAssetsAttributable());
			st1.setString(9, pnr.getContact());
			st1.setString(10, pnr.getContactTel());
			st1.setString(11, pnr.getRemark());
			st1.setString(12, pnr.getEntryPeople());
			st1.setDate(13,new java.sql.Date(pnr.getEntryTime().getTime()));
			st1.setString(14, pnr.getModifyPeople());
			if(pnr.getModifyTime()==null){
				
				st1.setDate(15, null);
			}else{
				
				st1.setDate(15, new java.sql.Date(pnr.getModifyTime().getTime()));
			}
			st1.setDouble(16, pnr.getLength());
			st1.setDouble(17, pnr.getWidth());
			st1.setDouble(18, pnr.getHeight());
			st1.setDouble(19, pnr.getLongitude());
			st1.setDouble(20, pnr.getLatitude());
			st1.setDouble(21, pnr.getUsableArea());
			st1.setDouble(22, pnr.getUsedArea());
			st1.setString(23, pnr.getKcroomVested());
			st1.setString(24, pnr.getIsShare());
			st1.setString(25, pnr.getShareOperators());
			st1.setString(26, pnr.getKcroomCategories());
			st1.setString(27, pnr.getKcroomSmallClass());
			st1.setString(28, pnr.getGroupKcroomTypes());
			st1.setDouble(29, pnr.getGroupRoombear());
			st1.setString(30, pnr.getAlignmentMethod());
			st1.setString(31, pnr.getChutes());
			st1.setString(32, pnr.getFireSystem());
			st1.setString(33, pnr.getIsNoun());
			st1.setString(34, pnr.getIsMonitor());
			if(pnr.getMaturityTime()==null){
				
				st1.setDate(35, null);
			}else{
				
				st1.setDate(35, new java.sql.Date(pnr.getMaturityTime().getTime()));
			}
			st1.setString(36, pnr.getRoomType());
			st1.setString(37, pnr.getMaintenanceMode());
			st1.setString(38, pnr.getMaintenanceUnit());
			st1.setString(39, pnr.getCharter());
			st1.setString(40, pnr.getThreeMainUnit());
			if(pnr.getRenewalTime()==null){
				st1.setDate(41,null);
				
			}else{
				
				st1.setDate(41, new java.sql.Date(pnr.getRenewalTime().getTime()));
			}
			st1.setString(42, pnr.getMaintenanceType());
			st1.setString(43, pnr.getMaintenanceYear());
			st1.setString(44, pnr.getOutDoor());
			st1.setString(45, pnr.getPlace());
			st1.setString(46, pnr.getSpecialty());
			st1.setString(47, pnr.getResType());
			st1.setString(48, pnr.getResLevel());
			
			st1.addBatch();
		}
	}else if(flag==2){
		
	}
	
}

}
