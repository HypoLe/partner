package com.boco.eoms.message.util.nm;

import com.boco.eoms.message.util.MsgHelp;

/**
 *
 * 读取boco.xml文件中有关短信相关设置，如boco.xml中没有设置就返回默认值
 * <pre>
 *  SMS_HOST = "192.168.8.11";
 *  SMS_SEND_PORT = 7890;
 *  SMS_READ_PORT = 7910;
 *  USER_NAME = "905172";
 *  PASSWORD = "905172";
 *  ICP_CODE = "06141";
 *  ICP_ID = "905172";
 *  SMS_VERSION = 2
 *  SMS_SLEEP_TIME = 3 秒
 *  SMS_CHECK_SLEEP_TIME = 30 秒
 *  SMS_SWITCH = OFF
 * </pre>
 *
 * @author JinGuo Li
 */
public class ReadConf {

    private final String SMS_HOST = MsgHelp.getSingleProperty("//message/msg/sms_host_ip");//"211.138.89.72";
    private final int SMS_SEND_PORT = new Integer(MsgHelp.getSingleProperty("//message/msg/sms_port")).intValue();
    private final int SMS_READ_PORT = new Integer(MsgHelp.getSingleProperty("//message/msg/sms_read_port")).intValue();
    private final String USER_NAME = MsgHelp.getSingleProperty("//message/msg/sms_user");
    private final String PASSWORD = MsgHelp.getSingleProperty("//message/msg/sms_pass");
  //  private final String ICP_CODE = "06141";
    private final String ICP_CODE = MsgHelp.getSingleProperty("//message/msg/sms_icp_code");
    private final String ICP_ID = MsgHelp.getSingleProperty("//message/msg/sms_icp_id");
    private final int SMS_VERSION = 2;
    private final int SMS_SLEEP_TIME = 3; //短信发送间隔时间，单位为秒
    private final int SMS_CHECK_SLEEP_TIME = 30;//链路检查间隔时间，单位为秒
    private final String SMS_OFF="ON"; //是否打开短信功能

    /**
     * 从boco.xml文件中sms_switch节点取得短信开关情况，打开短信功能必须把此节点值设为ON
     * @return 如果boco.xml没有设置，返回默认值
     */
    public boolean isOn(){
      return true;
    }
    /**
     * 取得boco.xml中的sms_host_ip接点所设置的短信网关IP地址
     * @return 如果boco.xml没有设置，返回默认值
     */
    public String getSmsHostIp() {
        return SMS_HOST;
    }

    /**
     * 取得boco.xml中的sms_send_port接点所设置的短信网关发送端口地址
     * @return 如果boco.xml没有设置，返回默认值
     */
    public int getSmsSendPort() {
        return SMS_SEND_PORT;
    }

    /**
     * 取得boco.xml中的sms_read_port接点所设置的短信网关读取状态端口地址
     * @return 如果boco.xml没有设置，返回默认值
     */
    public int getSmsReadPort() {
        return SMS_READ_PORT;
    }

    /**
     * 取得boco.xml中的sms_user接点所设置的短信网关用户名
     * @return 如果boco.xml没有设置，返回默认值
     */
    public String getSmsUserName() {
        return USER_NAME;
    }

    /**
     * 取得boco.xml中的sms_pass接点所设置的短信网关用户密码
     * @return 如果boco.xml没有设置，返回默认值
     */
    public String getSmsPassword() {
        return PASSWORD;
    }

    /**
     * 取得boco.xml中的sms_icp_id接点所设置的短信网关SP企业ID
     * @return 如果boco.xml没有设置，返回默认值
     */
    public String getSmsIcpId() {
        return ICP_ID;
    }

    /**
     * 取得boco.xml中的sms_icp_code接点所设置的短信网关SP企业代码
     * @return 如果boco.xml没有设置，返回默认值
     */
    public String getSmsIcpCode() {
        return ICP_CODE;
    }

    /**
     * 取得boco.xml中的sms_version接点所设置的短信网关实现的CMPP版本
     * @return 如果boco.xml没有设置，返回默认值
     */
    public int getSmsVersion() {
        return SMS_VERSION;
    }

    /**
     * 取得boco.xml中的sms_sleep_time接点所设置的短信发送间隔时间，以秒为单位
     * @return 如果boco.xml没有设置，返回默认值
     * @return
     */
    public int getSmsSleepTime() {
        return SMS_SLEEP_TIME;
    }
    /**
     * 取得boco.xml中的sms_check_sleep_time接点所设置的链路检测间隔时间，以秒为单位
     * @return 如果boco.xml没有设置，返回默认值
     * @return
     */
    public int getSmsCheckSleepTime() {
        return SMS_CHECK_SLEEP_TIME;
    }
}
