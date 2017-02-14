package com.boco.eoms.partner.geo.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.boco.eoms.base.dao.jdbc.BaseDaoJdbc;
import com.boco.eoms.partner.geo.model.ResourceInfo;

public class ResourceInfoDao extends BaseDaoJdbc{

	public List<ResourceInfo> getAllResourceInfo(){
		
		List<ResourceInfo> resultList = new ArrayList<ResourceInfo>();
		
		ResourceInfo peopleResource = new ResourceInfo();
		peopleResource.setResourceName("人员");
		peopleResource.setLeaf(false);
		List<ResourceInfo> peopleList = getResourceInfoByType(1);
		peopleResource.setChildren(peopleList);
		
		ResourceInfo carResource = new ResourceInfo();
		carResource.setResourceName("车辆");
		carResource.setLeaf(false);
		List<ResourceInfo> carList = getResourceInfoByType(2);
		carResource.setChildren(carList);
		
		resultList.add(peopleResource);
		resultList.add(carResource);
		return	resultList;
	}
	
	public List<ResourceInfo> getResourceInfoByType(int type){
		
		String sql = "select t.*  from resource_info t where t.parend_id=?";
		List<ResourceInfo> resultList = new ArrayList<ResourceInfo>();
	
		ResultSet rs = null;

		resultList = getJdbcTemplate().query(sql, new Object[]{type},new RowMapper(){
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				ResourceInfo info = new ResourceInfo();
				info.setResourceId(rs.getString("RESOURCE_ID"));
				info.setResourceName(rs.getString("RESOURCE_NAME"));
				info.setResourceType(rs.getString("RESOURCE_TYPE"));
				info.setId(rs.getInt("ID"));
				return info;
			}
		});
		return resultList;
	}
	
}
