package com.boco.eoms.partner.keymanagement.service.impl;

import com.boco.eoms.deviceManagement.common.service.CommonGenericServiceImpl;
import com.boco.eoms.partner.keymanagement.dao.IKeyManagementDao;
import com.boco.eoms.partner.keymanagement.model.KeyBorrowRecord;
import com.boco.eoms.partner.keymanagement.service.IKeyManagementService;

/**
 * Created with IntelliJ IDEA.
 * User: huangpeng
 * Date: 13-6-28
 * Time: 下午2:03
 * To change this template use File | Settings | File Templates.
 */
public class KeyManagementServiceImpl extends CommonGenericServiceImpl<KeyBorrowRecord> implements IKeyManagementService {

    private IKeyManagementDao keyManagementDao;

    public IKeyManagementDao getKeyManagementDao() {
        return keyManagementDao;
    }

    public void setKeyManagementDao(IKeyManagementDao keyManagementDao) {
        this.setCommonGenericDao(keyManagementDao);
        this.keyManagementDao = keyManagementDao;
    }
}
