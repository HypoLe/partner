/**
 * RemoteUser.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.boco.eoms.interfaces.securityeoms;

public class RemoteUser  implements java.io.Serializable {
    private java.lang.String fax;

    private java.lang.String mail;

    private java.lang.String mobile;

    private java.lang.String name;
    /**
     * 邮编
     */
    private java.lang.String post;

    private java.lang.String pwd;

    private java.lang.String sex;

    private java.lang.String telephone;

    private java.lang.String uid;

    public RemoteUser() {
    }

    public RemoteUser(
           java.lang.String fax,
           java.lang.String mail,
           java.lang.String mobile,
           java.lang.String name,
           java.lang.String post,
           java.lang.String pwd,
           java.lang.String sex,
           java.lang.String telephone,
           java.lang.String uid) {
           this.fax = fax;
           this.mail = mail;
           this.mobile = mobile;
           this.name = name;
           this.post = post;
           this.pwd = pwd;
           this.sex = sex;
           this.telephone = telephone;
           this.uid = uid;
    }


    /**
     * Gets the fax value for this RemoteUser.
     * 
     * @return fax
     */
    public java.lang.String getFax() {
        return fax;
    }


    /**
     * Sets the fax value for this RemoteUser.
     * 
     * @param fax
     */
    public void setFax(java.lang.String fax) {
        this.fax = fax;
    }


    /**
     * Gets the mail value for this RemoteUser.
     * 
     * @return mail
     */
    public java.lang.String getMail() {
        return mail;
    }


    /**
     * Sets the mail value for this RemoteUser.
     * 
     * @param mail
     */
    public void setMail(java.lang.String mail) {
        this.mail = mail;
    }


    /**
     * Gets the mobile value for this RemoteUser.
     * 
     * @return mobile
     */
    public java.lang.String getMobile() {
        return mobile;
    }


    /**
     * Sets the mobile value for this RemoteUser.
     * 
     * @param mobile
     */
    public void setMobile(java.lang.String mobile) {
        this.mobile = mobile;
    }


    /**
     * Gets the name value for this RemoteUser.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this RemoteUser.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the post value for this RemoteUser.
     * 
     * @return post
     */
    public java.lang.String getPost() {
        return post;
    }


    /**
     * Sets the post value for this RemoteUser.
     * 
     * @param post
     */
    public void setPost(java.lang.String post) {
        this.post = post;
    }


    /**
     * Gets the pwd value for this RemoteUser.
     * 
     * @return pwd
     */
    public java.lang.String getPwd() {
        return pwd;
    }


    /**
     * Sets the pwd value for this RemoteUser.
     * 
     * @param pwd
     */
    public void setPwd(java.lang.String pwd) {
        this.pwd = pwd;
    }


    /**
     * Gets the sex value for this RemoteUser.
     * 
     * @return sex
     */
    public java.lang.String getSex() {
        return sex;
    }


    /**
     * Sets the sex value for this RemoteUser.
     * 
     * @param sex
     */
    public void setSex(java.lang.String sex) {
        this.sex = sex;
    }


    /**
     * Gets the telephone value for this RemoteUser.
     * 
     * @return telephone
     */
    public java.lang.String getTelephone() {
        return telephone;
    }


    /**
     * Sets the telephone value for this RemoteUser.
     * 
     * @param telephone
     */
    public void setTelephone(java.lang.String telephone) {
        this.telephone = telephone;
    }


    /**
     * Gets the uid value for this RemoteUser.
     * 
     * @return uid
     */
    public java.lang.String getUid() {
        return uid;
    }


    /**
     * Sets the uid value for this RemoteUser.
     * 
     * @param uid
     */
    public void setUid(java.lang.String uid) {
        this.uid = uid;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RemoteUser)) return false;
        RemoteUser other = (RemoteUser) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.fax==null && other.getFax()==null) || 
             (this.fax!=null &&
              this.fax.equals(other.getFax()))) &&
            ((this.mail==null && other.getMail()==null) || 
             (this.mail!=null &&
              this.mail.equals(other.getMail()))) &&
            ((this.mobile==null && other.getMobile()==null) || 
             (this.mobile!=null &&
              this.mobile.equals(other.getMobile()))) &&
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            ((this.post==null && other.getPost()==null) || 
             (this.post!=null &&
              this.post.equals(other.getPost()))) &&
            ((this.pwd==null && other.getPwd()==null) || 
             (this.pwd!=null &&
              this.pwd.equals(other.getPwd()))) &&
            ((this.sex==null && other.getSex()==null) || 
             (this.sex!=null &&
              this.sex.equals(other.getSex()))) &&
            ((this.telephone==null && other.getTelephone()==null) || 
             (this.telephone!=null &&
              this.telephone.equals(other.getTelephone()))) &&
            ((this.uid==null && other.getUid()==null) || 
             (this.uid!=null &&
              this.uid.equals(other.getUid())));
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
        if (getFax() != null) {
            _hashCode += getFax().hashCode();
        }
        if (getMail() != null) {
            _hashCode += getMail().hashCode();
        }
        if (getMobile() != null) {
            _hashCode += getMobile().hashCode();
        }
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        if (getPost() != null) {
            _hashCode += getPost().hashCode();
        }
        if (getPwd() != null) {
            _hashCode += getPwd().hashCode();
        }
        if (getSex() != null) {
            _hashCode += getSex().hashCode();
        }
        if (getTelephone() != null) {
            _hashCode += getTelephone().hashCode();
        }
        if (getUid() != null) {
            _hashCode += getUid().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }


}
