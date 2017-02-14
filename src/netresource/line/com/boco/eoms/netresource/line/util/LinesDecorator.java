package com.boco.eoms.netresource.line.util;

import org.displaytag.decorator.TableDecorator;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.commons.system.dept.service.ITawSystemDeptManager;
import com.boco.eoms.netresource.line.model.Lines;

public class LinesDecorator  extends TableDecorator {
	
	/**
     * 重写getCity()
     * 
     * @return 通过id返回名称
     */
    public java.lang.String getCity(){
        
        ITawSystemDeptManager deptManager = (ITawSystemDeptManager)ApplicationContextHolder.getInstance().getBean("ItawSystemDeptManager");
        Lines lines = (Lines)getCurrentRowObject();
        String deptId = lines.getCity();
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
