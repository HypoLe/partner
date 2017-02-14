//---------------------------------------------------------
// Application: E_OMS
// Author     : Jerry
// File       : SizeCacheManager.java
//
// Copyright 2003 boco
// Generated at Thu Mar 27 10:15:57 CST 2003
// using Karapan Sapi Struts Generator
// Visit http://www.javanovic.com
//---------------------------------------------------------

package com.boco.eoms.common.controller;

import com.boco.eoms.common.util.CacheManager;

public class SizeCacheManager {
  private static CacheManager cacheManager = new CacheManager(CacheManager.LRU);

  public static Object getCache(String identifier) {
    return cacheManager.getCache(identifier);
  }

  public static void putCache(Object object, String id, int minutesToLive) {
    cacheManager.putCache(object, id, minutesToLive);
  }
}