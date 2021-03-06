
package com.boco.eoms.message.dao;


import java.util.List;
import java.util.Map;

import com.boco.eoms.base.dao.Dao;
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
 * Date:2008-5-5 下午03:37:02
 * </p>
 * 
 * @author 孙圣泰
 * @version 3.5.1
 *
 */
public interface SmsServiceDao extends Dao {

    /**
     * Retrieves all of the smsServices
     */
    public List getSmsServices(SmsService smsService);

    /**
     * Gets smsService's information based on primary key. An
     * ObjectRetrievalFailureException Runtime Exception is thrown if 
     * nothing is found.
     * 
     * @param id the smsService's id
     * @return smsService populated smsService object
     */
    public SmsService getSmsService(final String id);

    /**
     * Saves a smsService's information
     * @param smsService the object to be saved
     */    
    public void saveSmsService(SmsService smsService);

    /**
     * Removes a smsService from the database by id
     * @param id the smsService's id
     */
    public void removeSmsService(final String id);
    /**
     * 根据当前用户ID、验证密码返回服务对象集合
     * @param userId 当前用户ID
     * @param pwd 验证密码
     * @return 服务对象集合
     */
    public List listByOuter(String userId,String pwd);
    /**
     * 根据模块业务ID取得服务集合
     * @param moduleId 模块业务ID
     * @return 服务对象集合
     */
    public List listByModuleId(String moduleId);
    /**
     * 根据删除标志取得服务集合
     * @param deleted 删除标志
     * @return 服务对象集合
     */
    public List listAll(String deleted);
    
    /**
     * ���ڷ�ҳ��ʾ
     * curPage ��ǰҳ��
     * pageSize ÿҳ��ʾ��
     */
    public Map getSmsServices(final Integer curPage, final Integer pageSize);
    public Map getSmsServices(final Integer curPage, final Integer pageSize, final String whereStr);
    public List getNextLevelServices(String parentid, String delid);
    public List getCancelServices(String receiverId);
    public List getCustomServices(String receiverId);
    public boolean hasService(String serviceId);
    public List getAllServices(String userId);
    
    public String xSaveXmlString(String xmlString);
    public void xDeleteByWebService(String id);
    public String xGetXmlString(String id);
    
    public String xGetAllServices();
    
    public List getUsersList(String serviceId);
    /**
     * 根据正反选标志查询所有该类型服务
     * @param selstatus
     * @return
     */
    public List getServicesBySelStatus(String selstatus);
}

