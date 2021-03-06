package com.boco.eoms.partner.netresource.dao.jdbc;

import java.sql.PreparedStatement;
import java.util.List;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.boco.eoms.partner.netresource.dao.IAccessNetworkRoomDaoJdbc;
import com.boco.eoms.partner.netresource.model.bs.AccessNetworkQuipment;
import com.boco.eoms.partner.netresource.model.bs.AccessNetworkRoom;

public class AccessNetworkRoomDaoJdbcImpl extends JdbcDaoSupport implements IAccessNetworkRoomDaoJdbc{
	
		
	public void importData(List<Object> mainList, PreparedStatement st1,int flag)
	throws Exception {
// TODO Auto-generated method stub
if(flag==1){
		for(int i=0;i<mainList.size();i++){
			AccessNetworkRoom pnr = (AccessNetworkRoom) mainList.get(i);
			st1.setString(1, pnr.getId());
			st1.setString(2, pnr.getAnrName());
			st1.setString(3, pnr.getCity());
			st1.setString(4, pnr.getCountry());
			st1.setString(5, pnr.getBureau());
			st1.setString(6, pnr.getTwoAnrName());
			st1.setString(7, pnr.getAnotherAnrName());
			st1.setString(8, pnr.getAnrType());
			st1.setString(9, pnr.getAnrLevel());
			st1.setString(10,pnr.getAnrAddress());
			st1.setString(11,pnr.getFoor());
			st1.setString(12,pnr.getNo());
			st1.setString(13, pnr.getAssetsAttributable());
			st1.setString(14, pnr.getContact());
			st1.setString(15, pnr.getContactTel());
			st1.setString(16, pnr.getIs97bureau());
			st1.setString(17, pnr.getUserName());
			st1.setString(18, pnr.getRemark());
			st1.setString(19, pnr.getEntryPeople());
			st1.setDate(20,new java.sql.Date(pnr.getEntryTime().getTime()));
			st1.setString(21, pnr.getModifyPeople());
			if(pnr.getModifyTime()==null){
				
				st1.setDate(22, null);
			}else{
				
				st1.setDate(22, new java.sql.Date(pnr.getModifyTime().getTime()));
			}
			st1.setDouble(23, pnr.getLength());
			st1.setDouble(24, pnr.getWidth());
			st1.setDouble(25, pnr.getHeight());
			st1.setString(26, pnr.getAnrClassify());
			st1.setDouble(27, pnr.getLongitude());
			st1.setDouble(28, pnr.getLatitude());
			st1.setDouble(29, pnr.getUsableArea());
			st1.setDouble(30, pnr.getUsedArea());
			st1.setString(31, pnr.getAnrVested());
			st1.setString(32, pnr.getIsShare());
			st1.setString(33, pnr.getShareOperators());
			st1.setString(34, pnr.getAnrCategories());
			st1.setString(35, pnr.getAnrSmallClass());
			st1.setString(36, pnr.getGroupAnrTypes());
			st1.setDouble(37, pnr.getGroupRoombear());
			st1.setString(38, pnr.getAlignmentMethod());
			st1.setString(39, pnr.getChutes());
			st1.setString(40, pnr.getFireSystem());
			st1.setString(41, pnr.getIsNoun());
			st1.setString(42, pnr.getIsMonitor());
			if(pnr.getMaturityTime()==null){
				
				st1.setDate(43, null);
			}else{
				
				st1.setDate(43, new java.sql.Date(pnr.getMaturityTime().getTime()));
			}
			st1.setString(44, pnr.getRoomType());
			st1.setString(45, pnr.getMaintenanceMode());
			st1.setString(46, pnr.getMaintenanceUnit());
			st1.setString(47, pnr.getCharter());
			st1.setString(48, pnr.getThreeMainUnit());
			if(pnr.getRenewalTime()==null){
				st1.setDate(49,null);
				
			}else{
				
				st1.setDate(49, new java.sql.Date(pnr.getRenewalTime().getTime()));
			}
			st1.setString(50, pnr.getMaintenanceType());
			st1.setString(51, pnr.getMaintenanceYear());
			st1.setString(52, pnr.getOutDoor());
			st1.setString(53, pnr.getPlace());
			st1.setString(54, pnr.getSpecialty());
			st1.setString(55, pnr.getResType());
			st1.setString(56, pnr.getResLevel());
			
			st1.addBatch();
		
			}
	}else if(flag==2){
		for(int i=0;i<mainList.size();i++){
			AccessNetworkQuipment pnr = (AccessNetworkQuipment) mainList.get(i);
			st1.setString(1, pnr.getId());
			st1.setString(2, pnr.getNetwork());
			st1.setString(3, pnr.getDeviceType());
			st1.setString(4, pnr.getAnqName());
			st1.setString(5, pnr.getAnrName());
			st1.setString(6, pnr.getAnrId());
			st1.setString(7, pnr.getDeviceNumber());
			st1.setString(8, pnr.getInspectName());
			st1.setString(9, pnr.getInspectId());
			st1.addBatch();

		}
	}
	}
}
