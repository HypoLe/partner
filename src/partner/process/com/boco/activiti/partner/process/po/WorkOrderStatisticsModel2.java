package com.boco.activiti.partner.process.po;

/**
 * User: zhuchengxu
 * Date: 13-9-17
 * Time: 下午2:45
 */
public class WorkOrderStatisticsModel2 {
    /**
     * 地市编号
     */
    private String city;
    /**
     * 地市名称
     */
    private String cityName;
    /**
     * 故障工单数
     */
    private int faultSumNum;
    /**
     * 故障工单超时数
     */
    private int faultOvertimeNum;
    /**
     * 故障超时率
     */
    private String faultOvertimeRate;
    /**
     * 发电工单数
     */
    private int generateElectricitySumNum;
    /**
     * 发电工单超时数
     */
    private int generateElectricityOvertimeNum;
    /**
     * 发电超时率
     */
    private String generateElectricityOvertimeRate;
    /**
     * 业务开通工单数
     */
    private int businessOpenedSumNum;
    /**
     * 业务开通工单超时数
     */
    private int businessOpenedOvertimeNum;
    /**
     * 业务开通超时率
     */
    private String businessOpenedOvertimeRate;
    /**
     * 业务协调工单数
     */
    private int operationalCoordinationSumNum;
    /**
     * 业务协调工单超时数
     */
    private int operationalCoordinationOvertimeNum;

    /**
     * 业务协调超时率
     */
    private String operationalCoordinationOvertimeRate;
    /**
     * 缴费工单数
     */
    private int paymentSumNum;
    /**
     * 缴费工单超时数
     */
    private int paymentOvertimeNum;

    /**
     * 缴费超时率
     */
    private String paymentOvertimeRate;
    /**
     * 网络割接工单数
     */
    private int networkCutoverSumNum;
    /**
     * 网络割接工单超时数
     */
    private int networkCutoverOvertimeNum;
    /**
     * 网络割接超时率
     */
    private String networkCutoverOvertimeRate;
    /**
     * 巡检工单数
     */
    private int inspectionSumNum;
    /**
     * 巡检工单超时数
     */
    private int inspectionOvertimeNum;
    /**
     * 巡检超时率
     */
    private String inspectionOvertimeRate;
    /**
     * 其它工单数
     */
    private int  otherSumNum;
    /**
     *其它工单超时数
     */
    private int  otherOvertimeNum;
    /**
     * 其它超时率
     */
    private String otherOvertimeRate;
    /**
     * 工程验收工单数
     */
    private int  projectAcceptanceSumNum;
    /**
     * 工程验收工单超时数
     */
    private int  projectAcceptanceOvertimeNum;
    /**
     * 工程验收超时率
     */
    private String projectAcceptanceOvertimeRate;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getFaultSumNum() {
        return faultSumNum;
    }

    public void setFaultSumNum(int faultSumNum) {
        this.faultSumNum = faultSumNum;
    }

    public String getFaultOvertimeRate() {
        return faultOvertimeRate;
    }

    public void setFaultOvertimeRate(String faultOvertimeRate) {
        this.faultOvertimeRate = faultOvertimeRate;
    }

    public int getGenerateElectricitySumNum() {
        return generateElectricitySumNum;
    }

    public void setGenerateElectricitySumNum(int generateElectricitySumNum) {
        this.generateElectricitySumNum = generateElectricitySumNum;
    }

    public String getGenerateElectricityOvertimeRate() {
        return generateElectricityOvertimeRate;
    }

    public void setGenerateElectricityOvertimeRate(String generateElectricityOvertimeRate) {
        this.generateElectricityOvertimeRate = generateElectricityOvertimeRate;
    }

    public int getBusinessOpenedSumNum() {
        return businessOpenedSumNum;
    }

    public void setBusinessOpenedSumNum(int businessOpenedSumNum) {
        this.businessOpenedSumNum = businessOpenedSumNum;
    }

    public String getBusinessOpenedOvertimeRate() {
        return businessOpenedOvertimeRate;
    }

    public void setBusinessOpenedOvertimeRate(String businessOpenedOvertimeRate) {
        this.businessOpenedOvertimeRate = businessOpenedOvertimeRate;
    }

    public int getOperationalCoordinationSumNum() {
        return operationalCoordinationSumNum;
    }

    public void setOperationalCoordinationSumNum(int operationalCoordinationSumNum) {
        this.operationalCoordinationSumNum = operationalCoordinationSumNum;
    }

    public String getOperationalCoordinationOvertimeRate() {
        return operationalCoordinationOvertimeRate;
    }

    public void setOperationalCoordinationOvertimeRate(String operationalCoordinationOvertimeRate) {
        this.operationalCoordinationOvertimeRate = operationalCoordinationOvertimeRate;
    }

    public int getNetworkCutoverSumNum() {
        return networkCutoverSumNum;
    }

    public void setNetworkCutoverSumNum(int networkCutoverSumNum) {
        this.networkCutoverSumNum = networkCutoverSumNum;
    }

    public String getNetworkCutoverOvertimeRate() {
        return networkCutoverOvertimeRate;
    }

    public void setNetworkCutoverOvertimeRate(String networkCutoverOvertimeRate) {
        this.networkCutoverOvertimeRate = networkCutoverOvertimeRate;
    }

    public int getInspectionSumNum() {
        return inspectionSumNum;
    }

    public void setInspectionSumNum(int inspectionSumNum) {
        this.inspectionSumNum = inspectionSumNum;
    }

    public String getInspectionOvertimeRate() {
        return inspectionOvertimeRate;
    }

    public void setInspectionOvertimeRate(String inspectionOvertimeRate) {
        this.inspectionOvertimeRate = inspectionOvertimeRate;
    }

    public int getProjectAcceptanceSumNum() {
        return projectAcceptanceSumNum;
    }

    public void setProjectAcceptanceSumNum(int projectAcceptanceSumNum) {
        this.projectAcceptanceSumNum = projectAcceptanceSumNum;
    }

    public String getProjectAcceptanceOvertimeRate() {
        return projectAcceptanceOvertimeRate;
    }

    public void setProjectAcceptanceOvertimeRate(String projectAcceptanceOvertimeRate) {
        this.projectAcceptanceOvertimeRate = projectAcceptanceOvertimeRate;
    }

    public int getFaultOvertimeNum() {
        return faultOvertimeNum;
    }

    public void setFaultOvertimeNum(int faultOvertimeNum) {
        this.faultOvertimeNum = faultOvertimeNum;
    }

    public int getGenerateElectricityOvertimeNum() {
        return generateElectricityOvertimeNum;
    }

    public void setGenerateElectricityOvertimeNum(int generateElectricityOvertimeNum) {
        this.generateElectricityOvertimeNum = generateElectricityOvertimeNum;
    }

    public int getBusinessOpenedOvertimeNum() {
        return businessOpenedOvertimeNum;
    }

    public void setBusinessOpenedOvertimeNum(int businessOpenedOvertimeNum) {
        this.businessOpenedOvertimeNum = businessOpenedOvertimeNum;
    }

    public int getOperationalCoordinationOvertimeNum() {
        return operationalCoordinationOvertimeNum;
    }

    public void setOperationalCoordinationOvertimeNum(int operationalCoordinationOvertimeNum) {
        this.operationalCoordinationOvertimeNum = operationalCoordinationOvertimeNum;
    }

    public int getNetworkCutoverOvertimeNum() {
        return networkCutoverOvertimeNum;
    }

    public void setNetworkCutoverOvertimeNum(int networkCutoverOvertimeNum) {
        this.networkCutoverOvertimeNum = networkCutoverOvertimeNum;
    }

    public int getInspectionOvertimeNum() {
        return inspectionOvertimeNum;
    }

    public void setInspectionOvertimeNum(int inspectionOvertimeNum) {
        this.inspectionOvertimeNum = inspectionOvertimeNum;
    }

    public int getProjectAcceptanceOvertimeNum() {
        return projectAcceptanceOvertimeNum;
    }

    public void setProjectAcceptanceOvertimeNum(int projectAcceptanceOvertimeNum) {
        this.projectAcceptanceOvertimeNum = projectAcceptanceOvertimeNum;
    }

    public int getPaymentSumNum() {
        return paymentSumNum;
    }

    public void setPaymentSumNum(int paymentSumNum) {
        this.paymentSumNum = paymentSumNum;
    }

    public int getPaymentOvertimeNum() {
        return paymentOvertimeNum;
    }

    public void setPaymentOvertimeNum(int paymentOvertimeNum) {
        this.paymentOvertimeNum = paymentOvertimeNum;
    }

    public String getPaymentOvertimeRate() {
        return paymentOvertimeRate;
    }

    public void setPaymentOvertimeRate(String paymentOvertimeRate) {
        this.paymentOvertimeRate = paymentOvertimeRate;
    }

    public int getOtherSumNum() {
        return otherSumNum;
    }

    public void setOtherSumNum(int otherSumNum) {
        this.otherSumNum = otherSumNum;
    }

    public int getOtherOvertimeNum() {
        return otherOvertimeNum;
    }

    public void setOtherOvertimeNum(int otherOvertimeNum) {
        this.otherOvertimeNum = otherOvertimeNum;
    }

    public String getOtherOvertimeRate() {
        return otherOvertimeRate;
    }

    public void setOtherOvertimeRate(String otherOvertimeRate) {
        this.otherOvertimeRate = otherOvertimeRate;
    }
}
