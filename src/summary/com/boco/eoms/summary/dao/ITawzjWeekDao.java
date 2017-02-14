package com.boco.eoms.summary.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.boco.eoms.base.dao.Dao;
import com.boco.eoms.summary.model.TawzjWeek;
import com.boco.eoms.summary.model.TawzjWeekCheck;
import com.boco.eoms.summary.model.TawzjWeekSub;

public interface ITawzjWeekDao extends Dao{
	

    /**
     * Retrieves all of the TawzjWeeks
     */
    public List getTawzjWeeks(TawzjWeek tawzjWeek);

    /**
     * Gets TawzjWeek's information based on primary key. An
     * ObjectRetrievalFailureException Runtime Exception is thrown if 
     * nothing is found.
     * 
     * @param id the TawzjWeek's id
     * @return TawzjWeek populated TawzjWeek object
     */
    public TawzjWeek getTawzjWeek(final String id);

    /**
     * Saves a TawzjWeek's information
     * @param TawzjWeek the object to be saved
     */    
    public String saveTawzjWeek(TawzjWeek tawzjWeek);

    /**
     * Removes a TawzjWeek from the database by id
     * @param id the TawzjWeek's id
     */
    public void removeTawzjWeek(final String id);
    /**
     * 用于分页显示
     * @param curPage the current page number
     * @param pageSize the size number per page
     */    
    public Map getTawzjWeeks(final Integer curPage, final Integer pageSize);
    /**
     * 用于分页显示
     * @param curPage the current page number
     * @param pageSize the size number per page
     * @param whereStr the "where.." conditional statement,must start with "where", can be blank
     */ 
    public Map getTawzjWeeks(final Integer curPage, final Integer pageSize, final String whereStr);
    /**
     * 根据父节点查询下级子节点
     * @param parentId 子节点中parentId字段即父节点id
     */    
    public ArrayList getChildList(String parentId);
    
    
    /**
     * @param weekid
     * @return
     */
    public TawzjWeek getTawzjWeeks(final String weekid,final String userid);
    
    /**
	 * 根据主id得到每天填写的内容
	 * @param tawzjWeekId
	 * @return
	 */
    public List getTawzjWeekSub(final String tawzjWeekId);
    
    public String  saveTawzjWeekSub(final TawzjWeekSub tawzjWeekSub);
    
    public String  saveTawzjWeekCheck(final TawzjWeekCheck tawzjWeekCheck) ;
    
    public List getTawzjWeekCheck(final String tawzjWeekId);
    
    public List getTawzjWeekCheckList(final String userid);
    
    public String  findauditer(final String userid,final String weekid);
}
