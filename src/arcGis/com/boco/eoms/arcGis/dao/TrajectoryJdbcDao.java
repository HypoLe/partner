package com.boco.eoms.arcGis.dao;

import java.util.List;
import java.util.Map;

import com.boco.eoms.arcGis.model.Trajectory;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.deviceManagement.common.service.CommonSpringJdbcServiceImpl;

public class TrajectoryJdbcDao {
	CommonSpringJdbcServiceImpl jdbcService = (CommonSpringJdbcServiceImpl) ApplicationContextHolder.getInstance().getBean("commonSpringJdbcService");
	
	/**
	 * 根据电话号码查找当日轨迹
	 * @param phoneNum
	 * @param beginTime
	 * @return
	 */
	public List<Trajectory> getTrajectoryByPhone(String phoneNum,String beginTime){
		String sql = "select trajectory.inserttime,trajectory.x,trajectory.y from GIS_TRAJECTORY trajectory where trajectory.phonenum ='"+phoneNum+"' "+
					 " and trajectory.inserttime >= to_date('"+beginTime+"','yyyy-mm-dd hh24:mi:ss') " +
					 " order by trajectory.inserttime asc ";
		try{
			List<Object> listo = jdbcService.queryForList(sql);
			if(listo.size()>0){
				for(int i=0;i<listo.size();i++){
					Object[] o = (Object[]) listo.get(i);
					Trajectory trajectory = new Trajectory();
	
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		return null;
	}
}
