package com.boco.activiti.partner.process.model;

/**
 * 抢修工单-故障类型
 * 
 * @author wanghongliang
 *
 */
public class FaultType {
	private String id;
	/**
	 * 地市
	 */
	private String city;
	/**
	 * 线路故障总数（次）
	 */
	private String totalFaultNum;
	/**
	 * 线路故障金额
	 */
	private String totalFaultAmout;
	/**
	 * 车挂数量
	 */
	private String cheguaNum;
	/**
	 * 车挂占比
	 */
	private String cheguaRate;
	/**
	 * 外力数量
	 */
	private String wailiNum;
	/**
	 * 外力占比
	 */
	private String wailiRate;
	/**
	 * 火烧数量
	 */
	private String huoshaoNum;
	/**
	 * 火烧占比
	 */
	private String huoshaoRate;
	/**
	 * 被盗或人为破坏数量
	 */
	private String renweiNum;
	/**
	 * 被盗或人为破坏占比
	 */
	private String renweiRate;
	/**
	 * 自然断纤（纤芯裂化）数量
	 */
	private String duanxianNum;
	/**
	 * 自然断纤（纤芯裂化）占比
	 */
	private String duanxianRate;
	/**
	 * 接头盒进水数量
	 */
	private String jinshuiNum;
	/**
	 * 接头盒进水占比
	 */
	private String jinshuiRate;
	/**
	 * 尾纤及法兰盘数量
	 */
	private String weixianNum;
	/**
	 * 尾纤及法兰盘占比
	 */
	private String weixianRate;
	/**
	 * 鸟啄数量
	 */
	private String niaozhuoNum;
	/**
	 * 鸟啄占比
	 */
	private String niaozhuoRate;
	/**
	 * 鼠咬数量
	 */
	private String shuyaoNum;
	/**
	 * 鼠咬占比
	 */
	private String shuyaoRate;
	/**
	 * 自然灾害数量
	 */
	private String zaihaiNum;
	/**
	 * 自然灾害占比
	 */
	private String zaihaiRate;
	/**
	 * 其他数量
	 */
	private String otherNum;
	/**
	 * 其他占比
	 */
	private String otherRate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getTotalFaultNum() {
		return totalFaultNum;
	}

	public void setTotalFaultNum(String totalFaultNum) {
		this.totalFaultNum = totalFaultNum;
	}

	public String getCheguaNum() {
		return cheguaNum;
	}

	public void setCheguaNum(String cheguaNum) {
		this.cheguaNum = cheguaNum;
	}

	public String getCheguaRate() {
		return cheguaRate;
	}

	public void setCheguaRate(String cheguaRate) {
		this.cheguaRate = cheguaRate;
	}

	public String getWailiNum() {
		return wailiNum;
	}

	public void setWailiNum(String wailiNum) {
		this.wailiNum = wailiNum;
	}

	public String getWailiRate() {
		return wailiRate;
	}

	public void setWailiRate(String wailiRate) {
		this.wailiRate = wailiRate;
	}

	public String getHuoshaoNum() {
		return huoshaoNum;
	}

	public void setHuoshaoNum(String huoshaoNum) {
		this.huoshaoNum = huoshaoNum;
	}

	public String getHuoshaoRate() {
		return huoshaoRate;
	}

	public void setHuoshaoRate(String huoshaoRate) {
		this.huoshaoRate = huoshaoRate;
	}

	public String getRenweiNum() {
		return renweiNum;
	}

	public void setRenweiNum(String renweiNum) {
		this.renweiNum = renweiNum;
	}

	public String getRenweiRate() {
		return renweiRate;
	}

	public void setRenweiRate(String renweiRate) {
		this.renweiRate = renweiRate;
	}

	public String getDuanxianNum() {
		return duanxianNum;
	}

	public void setDuanxianNum(String duanxianNum) {
		this.duanxianNum = duanxianNum;
	}

	public String getDuanxianRate() {
		return duanxianRate;
	}

	public void setDuanxianRate(String duanxianRate) {
		this.duanxianRate = duanxianRate;
	}

	public String getJinshuiNum() {
		return jinshuiNum;
	}

	public void setJinshuiNum(String jinshuiNum) {
		this.jinshuiNum = jinshuiNum;
	}

	public String getJinshuiRate() {
		return jinshuiRate;
	}

	public void setJinshuiRate(String jinshuiRate) {
		this.jinshuiRate = jinshuiRate;
	}

	public String getWeixianNum() {
		return weixianNum;
	}

	public void setWeixianNum(String weixianNum) {
		this.weixianNum = weixianNum;
	}

	public String getWeixianRate() {
		return weixianRate;
	}

	public void setWeixianRate(String weixianRate) {
		this.weixianRate = weixianRate;
	}

	public String getNiaozhuoNum() {
		return niaozhuoNum;
	}

	public void setNiaozhuoNum(String niaozhuoNum) {
		this.niaozhuoNum = niaozhuoNum;
	}

	public String getNiaozhuoRate() {
		return niaozhuoRate;
	}

	public void setNiaozhuoRate(String niaozhuoRate) {
		this.niaozhuoRate = niaozhuoRate;
	}

	public String getShuyaoNum() {
		return shuyaoNum;
	}

	public void setShuyaoNum(String shuyaoNum) {
		this.shuyaoNum = shuyaoNum;
	}

	public String getShuyaoRate() {
		return shuyaoRate;
	}

	public void setShuyaoRate(String shuyaoRate) {
		this.shuyaoRate = shuyaoRate;
	}

	public String getZaihaiNum() {
		return zaihaiNum;
	}

	public void setZaihaiNum(String zaihaiNum) {
		this.zaihaiNum = zaihaiNum;
	}

	public String getZaihaiRate() {
		return zaihaiRate;
	}

	public void setZaihaiRate(String zaihaiRate) {
		this.zaihaiRate = zaihaiRate;
	}

	public String getOtherNum() {
		return otherNum;
	}

	public void setOtherNum(String otherNum) {
		this.otherNum = otherNum;
	}

	public String getOtherRate() {
		return otherRate;
	}

	public void setOtherRate(String otherRate) {
		this.otherRate = otherRate;
	}

	public String getTotalFaultAmout() {
		return totalFaultAmout;
	}

	public void setTotalFaultAmout(String totalFaultAmout) {
		this.totalFaultAmout = totalFaultAmout;
	}

}
