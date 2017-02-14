package com.boco.eoms.message.util.nm;

import java.io.IOException;

import com.sxit.cmpp.CMPP;
import com.sxit.cmpp.CMPPDeliver;
import com.sxit.cmpp.CMPPException;

import com.sxit.cmpp.CMPPSocket;
import com.sxit.cmpp.Common;

public class ReadSubmitStatus {

    private static CMPPSocket socket;

    private static CMPP cmpp;

    private static String hostIp;

    private int port;

    private String userName;

    private String password;

    private String icpCode;

    private String icpId;

    private int version;

    private int checkSleepTime;
    /**
     * 读取相关配置信息
     */
    private void readConfig() {
        ReadConf rc = new ReadConf();
        port = rc.getSmsReadPort();
        hostIp = rc.getSmsHostIp();
        userName = rc.getSmsUserName();
        password = rc.getSmsPassword();
        icpCode = rc.getSmsIcpCode();
        icpId = rc.getSmsIcpId();
        version = rc.getSmsVersion();
        checkSleepTime=rc.getSmsCheckSleepTime();
    }
    /**
     * 读取初始配置并建立与网关的连接（会一直循环连接，直到连接成功为止）
     */
    public ReadSubmitStatus() {
        readConfig();
        initConnect();
    }

    /**
     * 建立与网关的socket及cmpp连接，如果不能连接则会一直循环连接，直到连接成功为止
     */
    private void initConnect() {
        SmsUtil smsUtil = new SmsUtil();
        while (cmpp == null) {
            while (socket == null) {
                try {
                    socket = smsUtil.createSocket(hostIp, port);
                } catch (IOException ce) {
                    socket = null;
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {

                    }
                    ce.printStackTrace();
                }
            }
            try {
                cmpp = smsUtil.createConnect(icpId, password, version, socket);
            } catch (IOException ce) {
                cmpp = null;
                socket = null;
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {

                }
                ce.printStackTrace();
            }
        }
    }
    /*
    public void printSubmitStatus() {

        //循环等待，直到有由网关发过来的消息，然后由CMPPReader进行相应处理
        long beginTime = System.currentTimeMillis();
        while (true) {
            synchronized (socket) {
                long now = System.currentTimeMillis();
                /*int available = 0;
                try {
                    available = socket.getInputStream().available();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                if (available > 0) {*/
    /*
                    CMPPPacketResp resp = null;
                    try {
                        resp = cmpp.readPacket();
                    } catch (CMPPReaderException e) {
                        e.printStackTrace();
                    }
                    if (resp instanceof CMPPDeliver) {

                        CMPPDeliver deliver = (CMPPDeliver) resp;
                        System.out.println("     消息ID:" + Common.bytes8ToLong(deliver.msg_Id));
                        System.out.println("     目的地:" + deliver.dest_Id);
                        System.out.println("       内容:" + deliver.msg_Content);
                        System.out.println("       格式:" + deliver.msg_Fmt);
                        System.out.println("deliver长度:" + deliver.msg_Length);
                        System.out.println("是否状态报告:"
                                + deliver.registered_Delivery);
                        System.out.println("     序列号:" + deliver.sequenceID);
                        System.out.println("   状态报告:" + deliver.report + "\n");
                        if (deliver.report != null) {
                            System.out.println("\n消息ID:"
                                    + Common.bytes8ToLong(deliver.report.msg_Id));
                            System.out.println("报告状态：" + deliver.report.stat);
                            System.out.println("发送时间："
                                    + deliver.report.submit_time);
                            System.out.println("处理时间："
                                    + deliver.report.done_time);
                            System.out.println("目标终端："
                                    + deliver.report.dest_terminal_Id);
                            System.out.println("消息标志："
                                    + deliver.report.smsc_sequence);

                        }
                    }*/
                //}
                //如果checkSleepTime秒之内没有输入流到达，发送链路检测包
                /*else if (now - beginTime > checkSleepTime * 1000) {
                    beginTime = now;
                    int active = cmpp.cmppActiveTest();
                    if (active == -1) {
                        initConnect();
                    }
                } else {
                    //休眠1秒，等待下一个deliver消息的到来
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                }*/
    /*       }
        }
    }*/
/*
    public static void main(String[] args){
       new ReadSubmitStatus().printSubmitStatus();
    }*/
}
