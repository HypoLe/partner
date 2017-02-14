package com.boco.eoms.partner.deviceAssess.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.boco.eoms.base.dao.jdbc.BaseDaoJdbc;
import com.boco.eoms.partner.deviceAssess.dao.QueryEomsSheetJdbc;
import com.boco.eoms.partner.deviceAssess.model.InsideDispose;

public class QueryEomsSheetJDBC  extends BaseDaoJdbc implements QueryEomsSheetJdbc{

	public List getFaultSheetList(){
		 
		String sql =" select main.sheetid,main.sendtime,main.endtime,main.title,main.mainFaultResponseLevel,main.toDeptId,main.mainEquipmentFactory,main.mainNetSortOne,"+
					" main.mainNetSortTwo,main.mainEquipmentModel,main.mainNetName,main.mainFaultGenerantTime,main.mainAlarmSolveDate,main.holdStatisfied "+
					" from commonfault_main main "+	
					" where  main.deleted ='0'  and main.endtime >'2010-10-18 00:00:00' "	+
					" and main.endtime <'2010-10-18 08:00:00' and main.mainequipmentfactory = '101010302'";
						
		List list = new ArrayList();
		
		Connection conn = ConnectionFactory.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		InsideDispose insideDispose = null;
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();			
			
			while (rs.next()) {
				insideDispose = new InsideDispose();
				insideDispose.setSheetNum(rs.getString(1));
				insideDispose.setCreateTime(rs.getDate(2));
				insideDispose.setPigeonholeTime(rs.getDate(3));
				insideDispose.setAffairName(rs.getString(4));
				insideDispose.setAffairLevel(rs.getString(5));
				insideDispose.setProvince("黑龙江");
				insideDispose.setCity(rs.getString(6));
				insideDispose.setFactory(rs.getString(7));
				insideDispose.setSpeciality(rs.getString(8));
				insideDispose.setEquipmentType(rs.getString(10));
				insideDispose.setEquipmentName(rs.getString(11));
				insideDispose.setBeginTime(rs.getDate(12));
				insideDispose.setRemoveTime(rs.getDate(13));
				list.add(insideDispose);
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		}  finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
				if (rs != null) {
					rs.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
		return list;
	}
	
}
