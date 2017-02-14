
package com.boco.eoms.message.mgr.impl;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.boco.eoms.base.service.impl.BaseManager;
import com.boco.eoms.message.dao.SmsServiceDao;
import com.boco.eoms.message.mgr.ISmsServiceManager;
import com.boco.eoms.message.model.SmsService;
/**
 * 
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Date:2008-5-5 下午03:40:15
 * </p>
 * 
 * @author 孙圣泰
 * @version 3.5.1
 *
 */
public class SmsServiceManagerImpl extends BaseManager implements ISmsServiceManager {
    private SmsServiceDao dao;

    /**
     * Set the Dao for communication with the data layer.
     * @param dao
     */
    public void setSmsServiceDao(SmsServiceDao dao) {
        this.dao = dao;
    }

    /**
     * @see com.boco.eoms.message.mgr.ISmsServiceManager#getSmsServices(com.boco.eoms.message.model.SmsService)
     */
    public List getSmsServices(final SmsService smsService) {
        return dao.getSmsServices(smsService);
    }

    /**
     * @see com.boco.eoms.message.mgr.ISmsServiceManager#getSmsService(String id)
     */
    public SmsService getSmsService(final String id) {
        return dao.getSmsService(new String(id));
    }

    /**
     * @see com.boco.eoms.message.mgr.ISmsServiceManager#saveSmsService(SmsService smsService)
     */
    public void saveSmsService(SmsService smsService) {
        dao.saveSmsService(smsService);
    }

    /**
     * @see com.boco.eoms.message.mgr.ISmsServiceManager#removeSmsService(String id)
     */
    public void removeSmsService(final String id) {
        dao.removeSmsService(new String(id));
    }
    /**
     * 
     */
    public Map getSmsServices(final Integer curPage, final Integer pageSize) {
        return dao.getSmsServices(curPage, pageSize,null);
    }
    public Map getSmsServices(final Integer curPage, final Integer pageSize, final String whereStr) {
        return dao.getSmsServices(curPage, pageSize, whereStr);
    }
    public List getNextLevelServices(String parentid, String delid) {
		return dao.getNextLevelServices(parentid, delid);
	}

	public List getCancelServices(String receiverId) {
		return dao.getCancelServices(receiverId);
	}

	public boolean hasService(String serviceId) {
		return dao.hasService(serviceId);
	}

	public List getAllServices(String userId) {
		return dao.getAllServices(userId);
	}

	public String xSaveXmlString(String xmlString){
		return dao.xSaveXmlString(xmlString);
		
	}

	public void xDeleteByWebService(String id) {
		dao.xDeleteByWebService(id);
		
	}

	public String xGetXmlString(String id) {
		return dao.xGetXmlString(id);
		 
	}


	public String xGetAllServices() {
		return dao.xGetAllServices();
	}

	public List getUsersList(String serviceId) {
		return dao.getUsersList(serviceId);
	}

	public List getCustomServices(String receiverId) {
		return dao.getCustomServices(receiverId);
	}

	public List getServicesBySelStatus(String selstatus) {
		return dao.getServicesBySelStatus(selstatus);
	}

	public List getToDelList(List allServicesList,List wantedList) {
		Iterator it = allServicesList.iterator();
		Iterator iter = wantedList.iterator();
		List returnList = new ArrayList();
		SmsService service = null;
		String serviceId = "";
		while(it.hasNext()) {
			service = (SmsService)it.next();
			returnList.add(service.getId());
		}
		while(iter.hasNext()) {
			serviceId = (String)iter.next();
			int pos = returnList.indexOf(serviceId);
			if(pos != -1) {
				returnList.remove(pos);
			}
		}
		return returnList;
	}
	
}
