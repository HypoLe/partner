package com.boco.eoms.message.util.nm;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.boco.eoms.common.log.BocoLog;
import com.sxit.cmpp.CMPP;
import com.sxit.cmpp.CMPPException;
import com.sxit.cmpp.CMPPSocket;
import com.sxit.cmpp.SubmitBody;


/**
 * 提供短信发送联接初始化相关公共方法
 * @author JinGuo Li
 */
public class SmsUtil {

  /**
   * 与网关建立Socket联接，并初始化，为取得CMPP做准备
   * @param ipAddr 网关IP地址
   * @param port 网关端口
   * @return CMPPSocket联接
   * @throws CMPPException
   */
  public CMPPSocket createSocket(String ipAddr, int port) throws CMPPException,IOException {

    CMPPSocket socket = new CMPPSocket(ipAddr, port);
    try {
      BocoLog.debug(this,250,"建立Socket连接");
      socket.initialSock();
    }
    catch (CMPPException ce) {
      String result = ce.getMessage() + closeSocket(socket);
      throw new CMPPException(result);
    }
    return socket;
  }

  /**
   * 建立与网关的cmpp连接
   * @param icpId SP服务商ID号，一般为6位数值
   * @param password 登陆密码
   * @param version 短信网关实现的版本号
   * @param socket 由createSocket建立的联接
   * @return CMPP对象
 * @throws CMPPException,IOException 
   */
  public CMPP createConnect(String icpId, String password, int version,
                            CMPPSocket socket) throws CMPPException,IOException {
	socket.initialSock();
    CMPP cmpp = new CMPP(socket);
    if (cmpp != null) {
      BocoLog.debug(this,250,"建立CMPP连接");
      int status =0 ;// cmpp.cmppConnect(icpId, password);
      if (status != 0) {
        String result = getConnectResultInfo(status)
            + closeSocket(socket);
        throw new CMPPException(result + status);
      }
    }
    return cmpp;
  }

  /**
   * 对createConnect返回的错误状态进行中文转换
   * @param status createConnect返回的错误代码
   * @return 错误的中文描述
   */
  private String getConnectResultInfo(int status) {
    String result;
    switch (status) {
      case 1:
        result = "消息结构错";
        break;
      case 2:
        result = "非法源地址";
        break;
      case 3:
        result = "认证错";
        break;
      case 4:
        result = "版本太高";
        break;
      case -1:
        result = "输入流阻塞";
        break;
      case -2:
        result = "I/O错误";
        break;
      default:
        result = "未知错误";
    }
    return result;
  }

  /**
   * 关闭由createSocket建立的Socket联接，关闭后再次使用必须重新建立
   * @param socket 由createSocket建立的Socket联接
   * @return 关闭时返回的错误信息
   */
  public String closeSocket(CMPPSocket socket) {
    String errResult = " Socket连接关闭成功 ";
    if (socket != null) {
      try {
        socket.closeSock();
      }
      catch (IOException ce) {
        errResult = ce.getMessage();
      }
    }
    return errResult;
  }

  /**
   * 初始化消息发送的相关设置，其中出参数以外全部作了默认设置，具体含义见清华深讯提供的java版API文档
   * <pre>
   * ucPkTotal = 1;
   * ucPkNumber = 1;
   * ucRegister = 1;
   * ucMsgLevel = 1;
   * sServiceId = icpCode;
   * ucFeeUserType = 1;
   * sFeeTermId = &quot;&quot;;
   * ucTpPid = 0;
   * ucTpUdhi = 0;
   * ucMsgFmt = 15;
   * sMsgSrc = icpId;
   * sFeeType = &quot;02&quot;;
   * sFeeCode = &quot;10&quot;;
   * sValidTime = &quot;&quot;;
   * sAtTime = &quot;&quot;;
   * sSrcTermId = icpCode;
   * </pre>
   *
   * @param icpId  短信网关SP企业ID
   * @param icpCode 短信网关SP企业代码
   * @param phoneNumber 接受短信的电话号码
   * @param messages 消息内容,最长位160个字符(全是ASCII的情况)，此处未做限制，但提交到网关回返回失败信息
   * @return 初始化后的消息体
   */
  public synchronized SubmitBody initSmsBody(String icpId, String icpCode,
                                             String phoneNumber,
                                             String messages) {
    SubmitBody body = new SubmitBody();
    BocoLog.debug(this,250,"初始化SubmitBody,phoneNumber:"+phoneNumber+",messages:"+messages);
    body.ucPkTotal = 1; //信息总条数
    body.ucPkNumber = 1;
    // 是否要求返回状态确认报告： 0：不需要 1：需要 2：产生SMC话单 （该类型短信仅供网关计费使用，不发送给目的终端)
    body.ucRegister = 1;
    //信息级别
    body.ucMsgLevel = 2;
    ///业务类型，是数字、字母和符号的组合
    body.sServiceId = icpCode;
    // 计费用户类型字段 0：对目的终端MSISDN计费 1：对源终端MSISDN计费 2：对SP计费
    // 3：表示本字段无效，对谁计费参见Fee_terminal_Id字段。
    body.ucFeeUserType = 0;
    //被计费用户的号码（如本字节填空，则表示本字段无效，对谁计费参见Fee_UserType字段，本字段与Fee_UserType字段互斥）
    body.sFeeTermId = "";
    // GSM协议类型 详细是解释请参考GSM03.40中的9.2.3.9
    body.ucTpPid = 0;
    // GSM协议类型 详细是解释请参考GSM03.40中的9.2.3.23,仅使用1位，右对齐
    body.ucTpUdhi = 1;
    //信息格式 0：ASCII串 3：短信写卡操作 4：二进制信息 8：UCS2编码 15：含GB汉字
    body.ucMsgFmt = 8;
    //信息内容来源(SP_Id)
    body.sMsgSrc = icpId;
    // 资费类别 01：对“计费用户号码”免费 02：对“计费用户号码”按条计信息费 03：对“计费用户号码”按包月收取信息费
    // 04：对“计费用户号码”的信息费封顶 05：对“计费用户号码”的收费是由SP实现
    body.sFeeType = "01";
    // 资费代码（以分为单位）
    body.sFeeCode = "0";
    // 存活有效期，格式遵循SMPP3.3协议
    body.sValidTime = "";
    //定时发送时间，格式遵循SMPP3.3协议
    body.sAtTime = "";
    //源号码 SP的服务代码或前缀为服务代码的长号码, 网关将该号码完整的填到SMPP协议Submit_SM消息
    //相应的source_addr字段，该号码最终在用户手机上显示为短消息的主叫号码
    body.sSrcTermId = icpCode;
    //接收短信的MSISDN号码,以英文逗号","分隔开
    //因为2.0协议短信群发的问题，建立此处只填入一个手机号码,如果有多个目的号码,请分条发送
    body.sDstTermId = phoneNumber;
    //信息内容
//    try {
//		body.ucMsgContent = messages.getBytes("UnicodeBigUnmarked");
//	} catch (UnsupportedEncodingException e) {
//		// TODO Auto-generated catch block
//		body.ucMsgContent = (new String("")).getBytes();
//		e.printStackTrace();
//	}

    return body;
  }

  /**
   * 对提交消息到网关的返回代码进行中文转换
   * @param resp 提交消息时返回的信息体
   * @return 转换后的消息
   */
  public static String getSubmitResultInfo(int status) {
    String result;
    switch (status) {
      case 0:
        result = "提交成功";
        break;
      case 1:
        result = "消息结构错";
        break;
      case 2:
        result = "命令字错";
        break;
      case 3:
        result = "消息序号重复";
        break;
      case 4:
        result = "消息长度错";
        break;
      case 5:
        result = "资费代码错";
        break;
      case 6:
        result = "超过最大信息长";
        break;
      case 7:
        result = "业务代码错";
        break;
      case 8:
        result = "流量控制错";
        break;
      case 9:
        result = "本网关不负责服务此计费号码";
        break;
      case 10:
        result = "Src_Id错误";
        break;
      case 11:
        result = "Msg_src错误";
        break;
      case 12:
        result = "Fee_terminal_Id错误";
        break;
      case -1:
        result = "输入流阻塞";
        break;
      case -2:
        result = "输入流或者输出流I/O错误";
        break;
      default:
        result = "未知错误";
    }
    return result;
  }
}
