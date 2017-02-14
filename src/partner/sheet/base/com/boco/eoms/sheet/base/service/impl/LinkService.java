package com.boco.eoms.sheet.base.service.impl;

import java.lang.reflect.Method;
import java.util.List;

import com.boco.eoms.base.service.impl.BaseManager;
import com.boco.eoms.sheet.base.dao.ILinkDAO;
import com.boco.eoms.sheet.base.exception.SheetException;
import com.boco.eoms.sheet.base.model.BaseLink;
import com.boco.eoms.sheet.base.service.ILinkService;
import com.boco.eoms.sheet.base.util.UUIDHexGenerator;

/**
 *  LinkService接口
 */
public abstract class LinkService extends BaseManager implements ILinkService {

    private BaseLink linkObject;

    private ILinkDAO linkDAO;

    public ILinkDAO getLinkDAO() {
        return linkDAO;
    }

    public void setLinkDAO(ILinkDAO linkDAO) {
        this.linkDAO = linkDAO;
    }

    public BaseLink getLinkObject() {
        return linkObject;
    }
    public void setLinkObject(BaseLink linkObject) {
        this.linkObject = linkObject;
    }

    public BaseLink getSingleLinkPO(String id) throws Exception {
        ILinkDAO dao = this.getLinkDAO();
        BaseLink link = dao.loadSinglePO(id, this.getLinkObject());
        return link;
    }
    @SuppressWarnings("unchecked")
	public List getLinksBySql(String sql) throws Exception {
        return null;
    }

    @SuppressWarnings("unchecked")
	public List getLinksByMainId(String mainId) throws Exception {
        ILinkDAO dao = getLinkDAO();
        List list = dao.listAllLinkOfWorkSheet(mainId, this.getLinkObject());
        return list;
    }

    public String addLink(Object obj) throws Exception {
    	System.out.println("Eoms35 linkService addLink");
        Method getterMethod = obj.getClass().getMethod("getId",new Class[] {});
        Object idObject=getterMethod.invoke(obj,new Object[] {});
        String id=idObject.toString();
        if(id==null || id.equals("")){
            id = UUIDHexGenerator.getInstance()
			.getID();
            Method setterMethod = obj.getClass().getMethod("setId",new Class[] {String.class});
            setterMethod.invoke(obj,new Object[] {id});
        }
        linkDAO.saveOrUpdate(obj);
        return id;
    }
    
	@SuppressWarnings("unchecked")
	public List getDealTemplatesByUserIds(String userId, Integer curPage, Integer pageSize, int[] aTotal, String linkName, String codition) throws SheetException{
		return this.linkDAO.getDealTemplatesByUserIds(userId, curPage, pageSize, aTotal, linkName, codition);
	}
	
	
    public String getOperateRoleId(String preLinkId){
        ILinkDAO dao = this.getLinkDAO();
        BaseLink link = dao.loadSinglePO(preLinkId, this.getLinkObject());
        if(link==null)
        	return "";
        String str=link.getOperateRoleId();
        return str;
    
    }
    public String getOperateUserId(String preLinkId){
        ILinkDAO dao = this.getLinkDAO(); 
        BaseLink link = dao.loadSinglePO(preLinkId, this.getLinkObject());
        if(link==null)
        	return "";
        String str=link.getOperateUserId();
        return str;
    
    }
    
    @SuppressWarnings("unchecked")
	public List getLinkOperateByAiid(String aiid, String linkName) {
    	return this.linkDAO.getLinkOperateByAiid(aiid, linkName);
    }
    @SuppressWarnings("unchecked")
	public List getLinksBycondition(String condition, String linkName) {
    	return this.linkDAO.getLinksBycondition(condition, linkName); 
    }
    
    public void removeLink(Object obj) {
    	this.linkDAO.removeLink(obj);
    }
	/**
	 * 清除当前session
	 */
    public void clearObjectOfCurrentSession() {
    	this.linkDAO.clearObjectOfCurrentSession();
    }
	/**
	 * 当有两个相同标识不同实体时执行
	 */
    public void mergeObject(Object obj){
    	this.linkDAO.mergeObject(obj);
    }
}
