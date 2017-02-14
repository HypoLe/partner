package com.boco.eoms.partner.personnel.model;

	/**
 * <p>
 * Title:人员技能信息管理
 * </p>
 * <p>
 * Description:人员技能信息管理
 * </p>
 * <p>
 * Jul 17, 2012 2:50:49 PM
 * </p>
 * 
 * @author LiHaolin
 * @version 1.0
 * 
 */
@SuppressWarnings("serial")
public class SkillInfo {
	/**
	 * 资质信息
	 */
	 private DWInfo dwInfo;
	/**
	 * 证书信息
	 */ 
	 private Certificate certificate;
	/**
	 * 奖励信息
	 */
	 private Reward reward;
	/**
	 * 培训经历信息
	 */	 
	 private PXExperience pxExperience;
	public DWInfo getDwInfo() {
		return dwInfo;
	}
	public void setDwInfo(DWInfo dwInfo) {
		this.dwInfo = dwInfo;
	}
	public Certificate getCertificate() {
		return certificate;
	}
	public void setCertificate(Certificate certificate) {
		this.certificate = certificate;
	}
	public Reward getReward() {
		return reward;
	}
	public void setReward(Reward reward) {
		this.reward = reward;
	}
	public PXExperience getPxExperience() {
		return pxExperience;
	}
	public void setPxExperience(PXExperience pxExperience) {
		this.pxExperience = pxExperience;
	}
	 
	 
	 
	 
}
