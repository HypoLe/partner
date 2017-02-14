package com.boco.eoms.commons.ui.taglib;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts.taglib.TagUtils;
import org.jdom.Element;

import utils.PartnerPrivUtils;

import com.boco.eoms.base.Constants;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.commons.loging.BocoLog;
import com.boco.eoms.commons.system.area.model.TawSystemArea;
import com.boco.eoms.commons.system.area.service.ITawSystemAreaManager;
import com.boco.eoms.commons.system.area.service.impl.TawSystemAreaManagerImpl;
import com.boco.eoms.commons.system.dict.model.TawSystemDictType;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.commons.ui.util.JSONUtil;

/**  
 *<p>
 * @Title:区域联动下拉框
 *</p>
 *<p>
 * @Description: XXX
 *</p>
 * @author fengguangping fengguangping@boco.com.cn
 * @date May 23, 2013 10:14:56 AM
 * @version V1.0  
 */
public class AreaComboBoxTag extends TagSupport{
	// 元素的id
	protected String id = null;

	// 元素的name
	protected String name = null;

	// 元素的下级联动元素的id
	protected String sub = null;

	// 元素初始化的字典id
	protected String initAreaId = null;

	// 指定从自定义的action地址取数据，而不通过字典表

	// ds值为此action的链接地址
	protected String ds = null;

	// 元素的class
	protected String styleClass = null;

	// 默认选中的选项值

	protected String defaultValue = null;

	// formBean名称
	protected String form = null;

	// 是否可以多选

	protected String multiple = null;

	// 显示行数
	protected String size = null;

	// onchange事件监听，其中的JS将在doCombo之后执行
	protected String onchange = null;

	// TODO 加入自定义的html属性

	protected String attributes = null;
	
	//利用alt属性作为Ext框架接口
	protected String alt = null;
	
	//增加checkbox 中不显示的option 
	protected String hiddenOption = null;
	//是否有权限:为true和false:为true时有权限,false或者不填表示没有权限,
	//该项值和initAreaId是互斥条件,当该值为true时initAreaId将失效;
	protected String hasRight=null;
	
	//后续扩展
	protected String level=null;
	//下级等待上级选择时的下拉框的text
	protected String waitMsg=null;
	//上级选择框的text
	protected String selectMsg=null;
	//在未选择上级时是否让下级disable,该值为true或者false,为true时下级disable,false或者不填表示没有权限;
	protected String subControl=null;
	
	public int doEndTag(){
		try {
			String selectedValue = null;
			ResourceBundle bundle = ResourceBundle	.getBundle(Constants.BUNDLE_KEY);
			String selectText =null;
			String waitText =null; 
			if (selectMsg!=null) {
				selectText=selectMsg;
			}else {
				selectText=bundle.getString("comboBox.select");
			}
			if (waitMsg!=null) {
				waitText=waitMsg;
			}else {
				waitText=bundle.getString("comboBox.wait");
			}
			Element defaultOp = new Element("option");
			defaultOp.addContent(selectText);
			defaultOp.setAttribute("value", "");
			
			Element disableOp = new Element("option");
			disableOp.addContent(waitText);
			disableOp.setAttribute("value", "");
			
			
			Element rootElement = new Element("select");
			rootElement.setAttribute("id", id);
			rootElement.setAttribute("name", name);
			if (ds != null) {
				rootElement.setAttribute("ds", ds);
			}else {
				rootElement.setAttribute("ds", "/area/tawSystemAreas.do?method=getSonAreaByAreaId&areaId=");
			}
			if (styleClass != null) {
				rootElement.setAttribute("class", styleClass);
			}
			if (multiple != null) {
				rootElement.setAttribute("multiple", multiple);
			}
			if (size != null) {
				rootElement.setAttribute("size", size);
			}
			if (alt != null) {
				rootElement.setAttribute("alt", alt);
			}
			if (level != null) {
				rootElement.setAttribute("alt", level);
			}
			// 设置subid属性和onchage事件
			if (sub !=null && !"".equals(sub)) {
				rootElement.setAttribute("subid", sub);
				rootElement.setAttribute("onchange",
						"javascript:eoms.ComboBox.doCombo(this,'" + sub+"');"+StaticMethod.null2String(onchange));
			}
			else if(onchange != null){
				rootElement.setAttribute("onchange", "javascript:"+StaticMethod.null2String(onchange));
			}

			if (form != null) {
				// 是否能直接获取form名称，而不用设置？
				selectedValue = (String) TagUtils.getInstance().lookup(pageContext, form, name, "request");
			}
			HttpServletRequest request=(HttpServletRequest)pageContext.getRequest();
			ITawSystemAreaManager tawSystemAreaManager=(ITawSystemAreaManager)ApplicationContextHolder
			.getInstance().getBean("ItawSystemAreaManager");
			TawSystemSessionForm sessionForm=(TawSystemSessionForm)request.getSession().getAttribute("sessionform");
			List cityList=new ArrayList();
			String rootAreaid=null;
			if (hasRight!=null&&hasRight.equals("true")) {//如果要求控制权限则获取登录人员的areaid
				Map map=PartnerPrivUtils.userIsPersonnel((HttpServletRequest)pageContext.getRequest());
				String userid=map.get("isPersonnel").toString();
				if (userid.equals("admin")) {
					rootAreaid="00";//因为StaticMethod.getUserCityAreaList()方法会默认获取rootAreaId所以只要该值长度为2就可以进入那个方法
				}else{
					rootAreaid=map.get("areaId").toString();
				}
			}else if(initAreaId!=null) {
				rootAreaid=initAreaId;
			}
			if (rootAreaid!=null) {
				if (rootAreaid.length()==2) {
					cityList=StaticMethod.getUserCityAreaList();
				}else if (rootAreaid.length()>2) {
					TawSystemArea tawSystemArea=new TawSystemArea();
					tawSystemArea=tawSystemAreaManager.getAreaByAreaId(rootAreaid.substring(0,4));
					cityList.add(tawSystemArea);
				}
				if(cityList.size()>0){
					if (multiple == null){
						rootElement.addContent(defaultOp);
					}
					String itemName = null;
					String itemId = null;
					// 将list转为option元素插入select元素
					for (Iterator it = cityList.iterator(); it.hasNext();) {
						TawSystemArea item = (TawSystemArea) it.next();
						itemName = StaticMethod.null2String(item.getAreaname());
						itemId = StaticMethod.null2String(item.getAreaid());
						if(hiddenOption!=null && hiddenOption.contains(itemId)){
							continue;
						}
						Element option = new Element("option").addContent(itemName);
						option.setAttribute("value", itemId);
						if (form != null && itemId.equals(selectedValue)) {
							option.setAttribute("selected", "true");
						} else if (itemId.equals(defaultValue)) {
							option.setAttribute("selected", "true");
						}
						rootElement.addContent(option);
					}
				}
			}else{
				rootElement.addContent(disableOp);
				if (subControl!=null&&subControl.equals("true")) {
					rootElement.setAttribute("disabled", "true");
				}
			}
			pageContext.getOut().println(JSONUtil.getStrElement(rootElement));
		} catch (IOException e) {
			BocoLog.error(this, e.getMessage());
		} catch (JspException e) {
			BocoLog.error(this, e.getMessage());
		}catch (Exception e) {
			BocoLog.error(this, e.getMessage());
		}
		return EVAL_PAGE;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSub() {
		return sub;
	}
	public void setSub(String sub) {
		this.sub = sub;
	}
	public String getInitAreaId() {
		return initAreaId;
	}
	public void setInitAreaId(String initAreaId) {
		this.initAreaId = initAreaId;
	}
	public String getDs() {
		return ds;
	}
	public void setDs(String ds) {
		this.ds = ds;
	}
	public String getStyleClass() {
		return styleClass;
	}
	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}
	public String getDefaultValue() {
		return defaultValue;
	}
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	public String getForm() {
		return form;
	}
	public void setForm(String form) {
		this.form = form;
	}
	public String getMultiple() {
		return multiple;
	}
	public void setMultiple(String multiple) {
		this.multiple = multiple;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getOnchange() {
		return onchange;
	}
	public void setOnchange(String onchange) {
		this.onchange = onchange;
	}
	public String getAttributes() {
		return attributes;
	}
	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}
	public String getAlt() {
		return alt;
	}
	public void setAlt(String alt) {
		this.alt = alt;
	}
	public String getHiddenOption() {
		return hiddenOption;
	}
	public void setHiddenOption(String hiddenOption) {
		this.hiddenOption = hiddenOption;
	}
	public String getHasRight() {
		return hasRight;
	}
	public void setHasRight(String hasRight) {
		this.hasRight = hasRight;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getWaitMsg() {
		return waitMsg;
	}
	public void setWaitMsg(String waitMsg) {
		this.waitMsg = waitMsg;
	}
	public String getSelectMsg() {
		return selectMsg;
	}
	public void setSelectMsg(String selectMsg) {
		this.selectMsg = selectMsg;
	}
	public String getSubControl() {
		return subControl;
	}
	public void setSubControl(String subControl) {
		this.subControl = subControl;
	}
	
}


