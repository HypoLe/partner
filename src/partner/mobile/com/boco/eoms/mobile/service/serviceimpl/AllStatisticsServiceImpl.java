package com.boco.eoms.mobile.service.serviceimpl;
import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.mobile.dao.IAllStatisticsDao;
import com.boco.eoms.mobile.model.AllStatistics;
import com.boco.eoms.mobile.service.AllStatisticsService;
public class AllStatisticsServiceImpl  extends CommonGenericServiceImpl<AllStatistics>  implements AllStatisticsService{
	
	private IAllStatisticsDao IAllStatisticsDao;

	public IAllStatisticsDao getIAllStatisticsDao() {
		return IAllStatisticsDao;
	}

	public void setIAllStatisticsDao(IAllStatisticsDao allStatisticsDao) {
		IAllStatisticsDao = allStatisticsDao;
		this.setCommonGenericDao(IAllStatisticsDao);
	}

	
}
