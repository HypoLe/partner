
package com.boco.eoms.summary.service.impl;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.boco.eoms.base.service.impl.BaseManager;
import com.boco.eoms.summary.dao.ITawzjWeekDao;
import com.boco.eoms.summary.model.TawzjWeek;
import com.boco.eoms.summary.model.TawzjWeekCheck;
import com.boco.eoms.summary.model.TawzjWeekSub;
import com.boco.eoms.summary.service.ITawzjWeekManager;

public class TawzjWeekManagerImpl extends BaseManager implements ITawzjWeekManager {
    private ITawzjWeekDao dao;

    /**
     * Set the Dao for communication with the data layer.
     * @param dao
     */
    public void setTawzjWeekDao(ITawzjWeekDao dao) {
        this.dao = dao;
    }

    /**
     * @see com.boco.eoms.TawzjWeek.service.ITawzjWeekManager#getTawzjWeeks(com.boco.eoms.TawzjWeek.model.TawzjWeek)
     */
    public List getTawzjWeeks(final TawzjWeek TawzjWeek) {
        return dao.getTawzjWeeks(TawzjWeek);
    }

    /**
     * @see com.boco.eoms.TawzjWeek.service.ITawzjWeekManager#getTawzjWeek(String id)
     */
    public TawzjWeek getTawzjWeek(final String id) {
        return dao.getTawzjWeek(new String(id));
    }

    /**
     * @see com.boco.eoms.TawzjWeek.service.ITawzjWeekManager#saveTawzjWeek(TawzjWeek TawzjWeek)
     */
    public String saveTawzjWeek(TawzjWeek TawzjWeek) {
        return dao.saveTawzjWeek(TawzjWeek);
    }

    /**
     * @see com.boco.eoms.TawzjWeek.service.ITawzjWeekManager#removeTawzjWeek(String id)
     */
    public void removeTawzjWeek(final String id) {
        dao.removeTawzjWeek(new String(id));
    }
    /**
     * @see com.boco.eoms.TawzjWeek.service.ITawzjWeekManager#getTawzjWeeks(final Integer curPage, final Integer pageSize)
     */
    public Map getTawzjWeeks(final Integer curPage, final Integer pageSize) {
        return dao.getTawzjWeeks(curPage, pageSize,null);
    }
    /**
     * @see com.boco.eoms.TawzjWeek.service.ITawzjWeekManager#getTawzjWeeks(final Integer curPage, final Integer pageSize, final String whereStr)
     */    
    public Map getTawzjWeeks(final Integer curPage, final Integer pageSize, final String whereStr) {
        return dao.getTawzjWeeks(curPage, pageSize, whereStr);
    }
    /**
     * @see com.boco.eoms.TawzjWeek.service.ITawzjWeekManager#getChildList(String parentId)
     */     
    public List getChildList(String parentId) {		
		return dao.getChildList(parentId);
	}
    /**
     * @see com.boco.eoms.TawzjWeek.service.ITawzjWeekManager#xGetChildNodes(String parentId)
     */  	
	public JSONArray xGetChildNodes(String parentId) {
		JSONArray json = new JSONArray();
		List list = new ArrayList();	
		list = this.getChildList(parentId);

		/*for (Iterator rowIt = list.iterator(); rowIt.hasNext();) {
			TawzjWeek obj = (TawzjWeek) rowIt.next();
			JSONObject jitem = new JSONObject();
			jitem.put("id", obj.getId());
			jitem.put("text", obj.getName());
			jitem.put("name", obj.getName());
			jitem.put("allowChild", true);
			jitem.put("allowDelete", true);
			if(obj.getLeaf().equals("1")){
				jitem.put("leaf", true);
			}
			json.put(jitem);
		}*/
		return json;
	}	 
	
	 public TawzjWeek getTawzjWeeks(final String weekid,final String userid){
		 return dao.getTawzjWeeks(weekid,userid);
	 }
	 /**
		 * 根据主id得到每天填写的内容
		 * @param tawzjWeekId
		 * @return
		 */
	 public List getTawzjWeekSub(final String tawzjWeekId){
		 return dao.getTawzjWeekSub(tawzjWeekId);
	 }
	 
	 /* (non-Javadoc)
	 * @see com.boco.eoms.summary.service.ITawzjWeekManager#saveTawzjWeekSub(com.boco.eoms.summary.model.TawzjWeekSub)
	 */
	public String  saveTawzjWeekSub(final TawzjWeekSub tawzjWeekSub){
		 return dao.saveTawzjWeekSub(tawzjWeekSub);
	 }
	 
	 /* (non-Javadoc)
	 * @see com.boco.eoms.summary.service.ITawzjWeekManager#saveTawzjWeekCheck(com.boco.eoms.summary.model.TawzjWeekCheck)
	 */
	public String  saveTawzjWeekCheck(final TawzjWeekCheck tawzjWeekCheck){
		 return dao.saveTawzjWeekCheck(tawzjWeekCheck);
	 }
	 
	 /**
	 * @param tawzjWeekId
	 * @return
	 */
	public List getTawzjWeekCheck(final String tawzjWeekId){
		 return dao.getTawzjWeekCheck(tawzjWeekId);
	 }
	
	public List getTawzjWeekCheckList(final String userid){
		return dao.getTawzjWeekCheckList(userid);
	}
	
	  public String  findauditer(final String userid,final String weekid){
		  return dao.findauditer(userid,weekid);
	  }
}
