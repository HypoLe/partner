/**
 * com.boco.eoms.netresource.point start
 */
package com.boco.eoms.netresource.point.util;

import com.boco.eoms.netresource.point.model.Points;
import org.displaytag.decorator.TableDecorator;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.commons.system.dept.service.ITawSystemDeptManager;
import com.boco.eoms.commons.system.dict.service.ITawSystemDictTypeManager;
import com.boco.eoms.commons.system.user.service.ITawSystemUserManager;
import com.boco.eoms.commons.system.role.service.ITawSystemRoleManager;

/**
 * <p>
 * Title:标点信息管理
 * </p>
 * <p>
 * Description:标点信息管理
 * </p>
 * <p>
 * Thu Feb 16 18:22:16 CST 2012
 * </p>
 * 
 * @author 王广平
 * @version 1.0
 * 
 */
public class PointsDecorator extends TableDecorator {
    
   
                
    /**
     * 重写getCity()
     * 
     * @return 通过id返回名称
     */
    public java.lang.String getCity(){
        
        ITawSystemDeptManager deptManager = (ITawSystemDeptManager)ApplicationContextHolder.getInstance().getBean("ItawSystemDeptManager");
        Points points = (Points)getCurrentRowObject();
        String deptId = points.getCity();
        String deptName = "";
        if (null != deptId) {
            String[] deptIdArr = deptId.split(",");
            if (deptIdArr != null) {
                for (int i = 0; i < deptIdArr.length; i++) {
                    deptName = deptName + "," + deptManager.getDeptinfobydeptid(deptIdArr[i],"0").getDeptName();
                }
            }
            if (deptName.length() > 0) {
                deptName = deptName.substring(1);
            }
        }
        
        return deptName;
    }
                                            
}

/**
 * com.boco.eoms.netresource.point end
 */
 
 