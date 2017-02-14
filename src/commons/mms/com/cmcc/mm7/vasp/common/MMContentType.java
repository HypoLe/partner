

package com.cmcc.mm7.vasp.common;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;

public class MMContentType implements Serializable, Cloneable
{
  private String strPrimaryType;
  private String strSubType;
  private boolean bLock;
  private Hashtable ParmeterList;


  public MMContentType()
  {
    strPrimaryType = "";
    strSubType = "";
    bLock = false;
    ParmeterList = new Hashtable();
  }

  public MMContentType(String type)
  {
    strPrimaryType = "";
    strSubType = "";
    bLock = false;
    ParmeterList = new Hashtable();

   int index = type.indexOf("/");
    if(index>0)
    {
      String strPraType = type.substring(0,index);
      String strSubType = type.substring(index+1);
      setPrimaryType(strPraType);
      if(strSubType.indexOf(";")>0)
        strSubType = strSubType.substring(0,strSubType.indexOf(";"));
      setSubType(strSubType);
    }
    else
      System.err.println("MMContentType@@@@@@@="+type);
  }

  public void setParameter(String name,String value)
  {
    ParmeterList.put(name,value);
  }

  public String getParameter(String name)
  {
    return((String)ParmeterList.get(name));
  }

  public Hashtable getParameterList()
  {
    return(ParmeterList);
  }

  public String getPrimaryType()
  {
    return(this.strPrimaryType);
  }

  public void setPrimaryType(String primaryType)
  {
    this.strPrimaryType = primaryType;
  }

  public MMContentType lock()
  {
    bLock = true;
    return this;
  }

  public boolean isLock()
  {
    return(bLock);
  }

  public void setSubType(String subType)
  {
    this.strSubType = subType;
  }

  public String getSubType()
  {
    return(this.strSubType);
  }

  public boolean match(MMContentType type)
  {
    String strPrimaryType = type.getPrimaryType().trim();
    String strSubType = type.getSubType().trim();
    if(strPrimaryType.equals(strSubType))
      return true;
    else
      return false;
  }

  public boolean match(String type)
  {
    int index = type.indexOf("/");
    if(index>0)
    {
      String strParType = type.substring(0,index);
      String strSubType = type.substring(index+1);
      if(strParType.trim().equals(strPrimaryType))
      {
        if(strSubType.trim().equals(strSubType))
          return true;
        else
          return false;
      }
      else
        return false;
    }
    else{
      System.err.println("MMContentType#########type="+type);
      return (false);
    }
  }

  public String toString()
  {
    StringBuffer sb = new StringBuffer();
    sb.append("strPrimaryType=" + strPrimaryType+"\n");
    sb.append("strSubType=" + strSubType+"\n");
    sb.append("bLock="+bLock+"\n");
    System.out.println("ParmeterList="+ParmeterList);
    if(ParmeterList != null)
    {
      Enumeration strParmeterList = ParmeterList.elements();
      int i = ParmeterList.size()-1;
      while(strParmeterList.hasMoreElements())
      {
        sb.append("ParmeterList["+i+"]="+(String)strParmeterList.nextElement()+"\n");
        i--;
      }
    }
    return sb.toString();
  }
}