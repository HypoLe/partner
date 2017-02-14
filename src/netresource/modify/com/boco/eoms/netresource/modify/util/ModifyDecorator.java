/**
 * com.boco.eoms.netresource.modify start
 */
package com.boco.eoms.netresource.modify.util;

import com.boco.eoms.netresource.modify.model.Modify;
import org.displaytag.decorator.TableDecorator;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.commons.system.dept.service.ITawSystemDeptManager;
import com.boco.eoms.commons.system.dict.service.ITawSystemDictTypeManager;
import com.boco.eoms.commons.system.user.service.ITawSystemUserManager;
import com.boco.eoms.commons.system.role.service.ITawSystemRoleManager;

/**
 * <p>
 * Title:资源变更管理
 * </p>
 * <p>
 * Description:资源变更管理
 * </p>
 * <p>
 * Tue Feb 21 11:40:03 CST 2012
 * </p>
 * 
 * @author 王广平
 * @version 1.0
 * 
 */
public class ModifyDecorator extends TableDecorator {
    
   
                        
    /**
     * 重写getApproveUser()
     * 
     * @return 通过id返回名称
     */
    public java.lang.String getApproveUser(){
        
        ITawSystemUserManager userManager = (ITawSystemUserManager)ApplicationContextHolder.getInstance().getBean("itawSystemUserManager");
        Modify modify = (Modify)getCurrentRowObject();
        String userId = modify.getApproveUser();
        String userName = "";
        if (null != userId) {
            String[] userIdArr = userId.split(",");
            if (userIdArr != null) {
                for (int i = 0; i < userIdArr.length; i++) {
                    userName = userName + "," + userManager.getUserByuserid(userIdArr[i]).getUsername();
                }
            }
            if (userName.length() > 0) {
                userName = userName.substring(1);
            }
        }
        
        return userName;
    }
                                
}

/**
 * com.boco.eoms.netresource.modify end
 */
 
 