/**
 * BulletinService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.huawei.csp.si.service;

public interface BulletinService extends javax.xml.rpc.Service {
    public java.lang.String getBulletinAddress();

    public com.huawei.csp.si.service.Bulletin_PortType getBulletin() throws javax.xml.rpc.ServiceException;

    public com.huawei.csp.si.service.Bulletin_PortType getBulletin(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
