package com.boco.eoms.summary.displaytag;


import org.displaytag.decorator.TableDecorator;
import com.boco.eoms.summary.model.TawzjMonth;
/**
 * 
 * <p>
 * Title:月工作总结
 * Title:threadList.jsp页面分页显示checkbox
 * <p>
 * Description:
 * </p>
 * <p>
 * Date:2009-6-17 16:39:25
 * </p>
 * 
 * @author 韩璐
 * @version 3.5.1
 * 
 */
public class MonthDisplay extends TableDecorator {
	/**
	 * id属性的checkbox
	 * 
	 * @return 一个带有checkbox的属性
	 */
	public String getId() {		
		TawzjMonth tawzjMonth = (TawzjMonth) getCurrentRowObject();
		return "<input type='checkbox' id='" + tawzjMonth.getId()
				+ "' name='ids' value='" + tawzjMonth.getId() + "'>";
		
	}


}
