package com.boco.eoms.partner.deviceInspect.model;

import com.boco.eoms.partner.netresource.model.EomsModel;

/**
 * 类说明：巡检元任务与网络资源关联
 * 创建人： zhangkeqi
 * 创建时间：2013-01-24
 */
public class PnrInspectTaskLink extends EomsModel{

	/**主键*/
	private Long id;
	/**计划id（InspectPlanMain中的id）*/
	private String planId;
	/**元任务的id（InspectPlanRes中的id）*/
	private Long planResId;
	/**巡检点设备关系id（PnrInspectLink中的id）*/
	private String inspectLinkId;
	
	//以下字段复制PnrInspectLink表中的相关字段
	/**巡检点id（PnrResConfig中的id）*/
	private String inspectId;
	/**巡检点类型与网络资源设备类型映射id（PnrInspectMapping中的id）*/
	private String inspectMappingId;
	/**设备专业名称(与网络资源的专业相对应)*/
	private String deviceSpecialtyName;
	/**设备类型名称*/
	private String deviceTypeName;
	/**网络资源名称*/
	private String netresName;
	/**网络资源表名*/
	private String netresTableName;
	/**网络资源id（网络资源各表中的主键id或立uuid）*/
	private String netResId;
	//所属网络 2013-07-30 用于手机显示
	private String netWork;
    // 新增條形碼   2013-08-13
    private String  resBar;
    // 新增  设备核对
    private String barState; //resBar 持有状态(0现场独有, 1数据独有,2共有,3 现场+数据)
    // 新增  设备核对- 现场特有条码字段     //2013-08-16
    private String  barSite;
    // 新增 现场独有字段状态
    private String  barCodeSite;


	
	
	//以下字段复制InspectPlanRes中的相关字段
	/**代维资源类型 与InspectPlanRes中的resType，且该字段的值为轮循时生成，来源于InspectPlanRes*/
	private String resType;
	private String city;
	private String createTime;
	
	/*==================以下字段用于线路巡检使用 begin=====================*/
	/**线路敷设点标识 1为敷设点 为0为以前的数据 在数据库中增加默认约束 用些字段区分是否线路巡检资源类型*/
	private String tlpInspectFlag;
	
	private String tlpDis;
	private String tlpWire;
	private String tlpSeg;
	private String tlpPAName;
	private String tlpPALo;
	private String tlpPALa;
	private String tlpPZName;
	private String tlpPZLo;
	private String tlpPZLa;
	
	/*光缆点类别*/
	private String tlpType;
	/*光缆点顺序号*/
	private String tlpSortNum;
	/*资源分配情况 1:已分配 0或空或NULL：未分配 (用此来设置必到点)*/
	private String isMustArrive;
	/*映射到代维后的地市*/
	private String tlpCity;		      //地市
	/*映射到代维后的区县*/
	private String tlpCountry;           //区县
	
	
	/**是否已到该点 0：否 1：是*/
	private String isArrived;
	/**巡检轨迹外键 用于签到点 对应表PnrInspectTrack中的主键id*/
	private String inspectTrackId;
	/**签到经度*/
	private String arrivedLo;
	/**签到纬度*/
	private String arrivedLa;
	/**签到时间*/
	private String arrivedTime;
	/*敷设点来源 0:正常情况，即本来就是段之间的点;1:光缆段的起点新增;2：光缆段的终点新增*/
	private String tlpSource;
	/*0签到正常,1不在范围之内,2无坐标有照片,3不在范围之内,但有图片,-1PC上填写*/
	private Integer signStatus ;
	
	/*==================以下字段用于线路巡检使用 end=====================*/

    public String getBarCodeSite(){
        return barCodeSite;
    }

    public void setBarCodeSite(String barCodeSite){
        this.barCodeSite = barCodeSite;
    }

    public String getBarSite(){
        return barSite;
    }
    public void setBarSite(String barSite){
        this.barSite = barSite;
    }


    public String getBarState() {
        return barState;
    }
    public void setBarState(String barState) {
        this.barState = barState;
    }
	public Integer getSignStatus() {
		return signStatus;
	}
	public void setSignStatus(Integer signStatus) {
		this.signStatus = signStatus;
	}
    public String getResBar(){
        return resBar;
    }

    public void setResBar(String resBar){
        this.resBar = resBar;
    }
	public String getTlpSource() {
		return tlpSource;
	}
	public void setTlpSource(String tlpSource) {
		this.tlpSource = tlpSource;
	}
	public String getInspectTrackId() {
		return inspectTrackId;
	}
	public void setInspectTrackId(String inspectTrackId) {
		this.inspectTrackId = inspectTrackId;
	}
	public String getArrivedLo() {
		return arrivedLo;
	}
	public void setArrivedLo(String arrivedLo) {
		this.arrivedLo = arrivedLo;
	}
	public String getArrivedLa() {
		return arrivedLa;
	}
	public void setArrivedLa(String arrivedLa) {
		this.arrivedLa = arrivedLa;
	}
	public String getArrivedTime() {
		return arrivedTime;
	}
	public void setArrivedTime(String arrivedTime) {
		this.arrivedTime = arrivedTime;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getNetResId() {
		return netResId;
	}
	public void setNetResId(String netResId) {
		this.netResId = netResId;
	}
	public String getDeviceSpecialtyName() {
		return deviceSpecialtyName;
	}
	public void setDeviceSpecialtyName(String deviceSpecialtyName) {
		this.deviceSpecialtyName = deviceSpecialtyName;
	}
	public String getDeviceTypeName() {
		return deviceTypeName;
	}
	public void setDeviceTypeName(String deviceTypeName) {
		this.deviceTypeName = deviceTypeName;
	}
	public String getNetresTableName() {
		return netresTableName;
	}
	public void setNetresTableName(String netresTableName) {
		this.netresTableName = netresTableName;
	}
	public String getNetresName() {
		return netresName;
	}
	public void setNetresName(String netresName) {
		this.netresName = netresName;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPlanId() {
		return planId;
	}
	public void setPlanId(String planId) {
		this.planId = planId;
	}
	public Long getPlanResId() {
		return planResId;
	}
	public void setPlanResId(Long planResId) {
		this.planResId = planResId;
	}
	public String getInspectId() {
		return inspectId;
	}
	public void setInspectId(String inspectId) {
		this.inspectId = inspectId;
	}
	public String getInspectLinkId() {
		return inspectLinkId;
	}
	public void setInspectLinkId(String inspectLinkId) {
		this.inspectLinkId = inspectLinkId;
	}
	public String getInspectMappingId() {
		return inspectMappingId;
	}
	public void setInspectMappingId(String inspectMappingId) {
		this.inspectMappingId = inspectMappingId;
	}
	public String getResType() {
		return resType;
	}
	public void setResType(String resType) {
		this.resType = resType;
	}
	public String getTlpDis() {
		return tlpDis;
	}
	public void setTlpDis(String tlpDis) {
		this.tlpDis = tlpDis;
	}
	public String getTlpWire() {
		return tlpWire;
	}
	public void setTlpWire(String tlpWire) {
		this.tlpWire = tlpWire;
	}
	public String getTlpSeg() {
		return tlpSeg;
	}
	public void setTlpSeg(String tlpSeg) {
		this.tlpSeg = tlpSeg;
	}
	public String getTlpPAName() {
		return tlpPAName;
	}
	public void setTlpPAName(String tlpPAName) {
		this.tlpPAName = tlpPAName;
	}
	public String getTlpPALo() {
		return tlpPALo;
	}
	public void setTlpPALo(String tlpPALo) {
		this.tlpPALo = tlpPALo;
	}
	public String getTlpPALa() {
		return tlpPALa;
	}
	public void setTlpPALa(String tlpPALa) {
		this.tlpPALa = tlpPALa;
	}
	public String getTlpPZName() {
		return tlpPZName;
	}
	public void setTlpPZName(String tlpPZName) {
		this.tlpPZName = tlpPZName;
	}
	public String getTlpPZLo() {
		return tlpPZLo;
	}
	public void setTlpPZLo(String tlpPZLo) {
		this.tlpPZLo = tlpPZLo;
	}
	public String getTlpPZLa() {
		return tlpPZLa;
	}
	public void setTlpPZLa(String tlpPZLa) {
		this.tlpPZLa = tlpPZLa;
	}
	public String getTlpType() {
		return tlpType;
	}
	public void setTlpType(String tlpType) {
		this.tlpType = tlpType;
	}
	public String getTlpSortNum() {
		return tlpSortNum;
	}
	public void setTlpSortNum(String tlpSortNum) {
		this.tlpSortNum = tlpSortNum;
	}
	public String getIsMustArrive() {
		return isMustArrive;
	}
	public void setIsMustArrive(String isMustArrive) {
		this.isMustArrive = isMustArrive;
	}
	public String getTlpCity() {
		return tlpCity;
	}
	public void setTlpCity(String tlpCity) {
		this.tlpCity = tlpCity;
	}
	public String getTlpCountry() {
		return tlpCountry;
	}
	public void setTlpCountry(String tlpCountry) {
		this.tlpCountry = tlpCountry;
	}
	public String getIsArrived() {
		return isArrived;
	}
	public void setIsArrived(String isArrived) {
		this.isArrived = isArrived;
	}
	public String getTlpInspectFlag() {
		return tlpInspectFlag;
	}
	public void setTlpInspectFlag(String tlpInspectFlag) {
		this.tlpInspectFlag = tlpInspectFlag;
	}
	public String getNetWork() {
		return netWork;
	}
	public void setNetWork(String netWork) {
		this.netWork = netWork;
	}
	
}
