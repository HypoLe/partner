package com.boco.eoms.testcard.model;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: ����
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class TawTestcardClearNote {

  private String iccid;
  //---------------����-----------------
  private String msisdn;
  private String clearuser;
  private String cleartime;
  //---------------��ϵͳ����--------------
  private String oldstate;
  //---------------������----------------
  private String newstate;
  //---------------���ò���--------------
  private String clearyuanyin;
/**
 * @return claertime
 */
public String getCleartime() {
	return cleartime;
}
/**
 * @param claertime Ҫ���õ� claertime
 */
public void setCleartime(String cleartime) {
	this.cleartime = cleartime;
}
/**
 * @return clearyuanyin
 */
public String getClearyuanyin() {
	return clearyuanyin;
}
/**
 * @param clearyuanyin Ҫ���õ� clearyuanyin
 */
public void setClearyuanyin(String clearyuanyin) {
	this.clearyuanyin = clearyuanyin;
}
/**
 * @return iccid
 */
public String getIccid() {
	return iccid;
}
/**
 * @param iccid Ҫ���õ� iccid
 */
public void setIccid(String iccid) {
	this.iccid = iccid;
}
/**
 * @return newstate
 */
public String getNewstate() {
	return newstate;
}
/**
 * @param newstate Ҫ���õ� newstate
 */
public void setNewstate(String newstate) {
	this.newstate = newstate;
}
/**
 * @return oldstate
 */
public String getOldstate() {
	return oldstate;
}
/**
 * @param oldstate Ҫ���õ� oldstate
 */
public void setOldstate(String oldstate) {
	this.oldstate = oldstate;
}
/**
 * @return clearuser
 */
public String getClearuser() {
	return clearuser;
}
/**
 * @param clearuser Ҫ���õ� clearuser
 */
public void setClearuser(String clearuser) {
	this.clearuser = clearuser;
}
/**
 * @return msisdn
 */
public String getMsisdn() {
	return msisdn;
}
/**
 * @param msisdn Ҫ���õ� msisdn
 */
public void setMsisdn(String msisdn) {
	this.msisdn = msisdn;
}
  
  

}