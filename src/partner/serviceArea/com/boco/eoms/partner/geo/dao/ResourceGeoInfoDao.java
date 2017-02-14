package com.boco.eoms.partner.geo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.boco.eoms.partner.geo.model.ResourceGeoInfo;

public class ResourceGeoInfoDao {

	public void insertNewResourceGeoInfo(ResourceGeoInfo info){
		
		String sql =" insert into RESOURCE_GEO_INFO(resource_id, resource_type, longitude, latitude, postion_time, report_time, create_time, postion_status, erro_context "+
		")values(?,?,?,?,?,?,?,?,?)";
		
		Connection conn = ConnectionFactory.getConnection();
		
		PreparedStatement state;
		try {
			state = conn.prepareStatement(sql);
			state.setString(1, info.getResourceId());
			state.setString(2,info.getResourceType());
			state.setDouble(3,Double.parseDouble(info.getLongitude()));
			state.setDouble(4,Double.parseDouble(info.getLatitude()));
			state.setTimestamp(5, info.getPositionTime());
			state.setTimestamp(6, info.getReportTime());
			state.setTimestamp(7, info.getCreateTime());
			state.setString(8, info.getPositionStatus());
			state.setString(9, info.getErroContext());
			state.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
}
