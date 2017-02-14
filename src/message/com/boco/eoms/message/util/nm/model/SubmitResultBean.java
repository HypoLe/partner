package com.boco.eoms.message.util.nm.model;

/**
 * 短信发送时相关状态
 * @author JinGuo Li
 */
public class SubmitResultBean {

    private long msgId;
    private String resultStatus;
    private String sendTime;
    private String phoneNumber;
    private String messages;

    /**
     * 初始化各属性值
     * @param msgId 消息ID
     * @param sendTime 发送时间,格式为YYYY-MM-DD HH:MM:SS
     * @param phoneNumber 接收号码
     * @param messages 消息内容
     * @param resultStatus 发送状态描述
     */
    public SubmitResultBean(long msgId,String sendTime,String phoneNumber,String messages,String resultStatus){
        setMsgId(msgId);
        setSendTime(sendTime);
        setPhoneNumber(phoneNumber);
        setMessages(messages);
        setResultStatus(resultStatus);
    }
    /**
     * @return 返回 messages。
     */
    public String getMessages() {
        return messages;
    }
    /**
     * @return 返回 msgId。
     */
    public long getMsgId() {
        return msgId;
    }
    /**
     * @return 返回 phoneNumber。
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }
    /**
     * @return 返回 resultStatus。
     */
    public String getResultStatus() {
        return resultStatus;
    }
    /**
     * @return 返回 sendTime。
     */
    public String getSendTime() {
        return sendTime;
    }
    /**
     * @param messages 要设置的 messages。
     */
    private void setMessages(String messages) {
        this.messages = messages;
    }
    /**
     * @param msgId 要设置的 msgId。
     */
    private void setMsgId(long msgId) {
        this.msgId = msgId;
    }
    /**
     * @param phoneNumber 要设置的 phoneNumber。
     */
    private void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    /**
     * @param resultStatus 要设置的 resultStatus。
     */
    private void setResultStatus(String resultStatus) {
        this.resultStatus = resultStatus;
    }
    /**
     * @param sendTime 要设置的 sendTime。
     */
    private void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }
}
