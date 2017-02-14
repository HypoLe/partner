/**
 * 
 */
package com.boco.eoms.partner.interfaces.bo;
/**
 * @author new
 * 
 */
public class DeliveryRequestProcessThread implements Runnable {

	DeliveryRequestPara para = null;

	/**
	 * @return the para
	 */
	public DeliveryRequestPara getPara() {
		return para;
	}

	/**
	 * @param para
	 *            the para to set
	 */
	public void setPara(DeliveryRequestPara para) {
		this.para = para;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		// TODO Auto-generated method stub
		DeliveryRequestConvertBO bo = new DeliveryRequestConvertBO();
		try {
			bo.convert(para.getEventID(), para.getSystemID(), para
					.getSendTime(), para.getFilter(), para
					.getDataReadyRequestUri());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
