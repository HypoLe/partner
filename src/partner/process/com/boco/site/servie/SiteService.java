package com.boco.site.servie;

/**
 * Created with IntelliJ IDEA.
 * User: zhuchengxu
 * Date: 13-12-2
 * Time: 上午11:16
 * To change this template use File | Settings | File Templates.
 */
public interface SiteService {
    public void saveSiteFile(String userId, String cname ,String imgname, String imgpath,
                             String dongitude,
                             String dimension,
                             String position);
}
