/**
 * ReportTypeType2.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis WSDL2Java emitter.
 */

package com.boco.eoms.gzjhead.interfaces;

public class ReportTypeType2  implements java.io.Serializable, org.apache.axis.encoding.SimpleType {
    private int value;

    public ReportTypeType2() {
    }

    public ReportTypeType2(int value) {
        this.value = value;
    }

    // Simple Types must have a String constructor
    public ReportTypeType2(java.lang.String value) {
        this.value = new Integer(value).intValue();
    }

    // Simple Types must have a toString for serializing the value
    public java.lang.String toString() {
        return new Integer(value).toString();
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ReportTypeType2)) return false;
        ReportTypeType2 other = (ReportTypeType2) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.value == other.getValue();
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        _hashCode += getValue();
        __hashCodeCalc = false;
        return _hashCode;
    }

}
