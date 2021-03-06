package com.boco.eoms.commons.sheet.special.webapp.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import com.boco.eoms.base.webapp.form.BaseForm;

/**
 * Generated by XDoclet/actionform. This class can be further processed with XDoclet/webdoclet/strutsconfigxml and XDoclet/webdoclet/strutsvalidationxml.
 *
 * @struts.form name="tawSheetSpecialForm" 
 */
public class TawSheetSpecialForm
    extends    BaseForm
    implements java.io.Serializable
{

    protected String remark;

    protected String id;

    protected String leaf;

    protected String ordercode;

    protected String parspeid;

    protected String refuserid;

    protected String specialname;

    protected String specialtype;

    protected String speid;

    protected String style;
    
    protected String newuserid;

    /** Default empty constructor. */
    public TawSheetSpecialForm() {}

    public String getRemark()
    {
        return this.remark;
    }
   /**
    */

    public void setRemark( String remark )
    {
        this.remark = remark;
    }

    public String getId()
    {
        return this.id;
    }
   /**
    */

    public void setId( String id )
    {
        this.id = id;
    }

    public String getLeaf()
    {
        return this.leaf;
    }
   /**
    */

    public void setLeaf( String leaf )
    {
        this.leaf = leaf;
    }

    public String getOrdercode()
    {
        return this.ordercode;
    }
   /**
    */

    public void setOrdercode( String ordercode )
    {
        this.ordercode = ordercode;
    }

    public String getParspeid()
    {
        return this.parspeid;
    }
   /**
    */

    public void setParspeid( String parspeid )
    {
        this.parspeid = parspeid;
    }

    public String getRefuserid()
    {
        return this.refuserid;
    }
   /**
    */

    public void setRefuserid( String refuserid )
    {
        this.refuserid = refuserid;
    }

    public String getSpecialname()
    {
        return this.specialname;
    }
   /**
    */

    public void setSpecialname( String specialname )
    {
        this.specialname = specialname;
    }

    public String getSpecialtype()
    {
        return this.specialtype;
    }
   /**
    */

    public void setSpecialtype( String specialtype )
    {
        this.specialtype = specialtype;
    }

    public String getSpeid()
    {
        return this.speid;
    }
   /**
    */

    public void setSpeid( String speid )
    {
        this.speid = speid;
    }

    public String getStyle()
    {
        return this.style;
    }
   /**
    */

    public void setStyle( String style )
    {
        this.style = style;
    }

        /* To add non XDoclet-generated methods, create a file named
           xdoclet-TawSheetSpecialForm.java 
           containing the additional code and place it in your metadata/web directory.
        */
    /**
     * @see org.apache.struts.action.ActionForm#reset(org.apache.struts.action.ActionMapping,
     *                                                javax.servlet.http.HttpServletRequest)
     */
    public void reset(ActionMapping mapping, HttpServletRequest request) {
        // reset any boolean data types to false

    }

	public String getNewuserid() {
		return newuserid;
	}

	public void setNewuserid(String newuserid) {
		this.newuserid = newuserid;
	}

}
