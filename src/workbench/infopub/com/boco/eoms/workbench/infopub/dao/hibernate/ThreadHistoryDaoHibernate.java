// Decompiled by Jad v1.5.7g. Copyright 2000 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/SiliconValley/Bridge/8617/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi 
// Source File Name:   ThreadHistoryDaoHibernate.java

package com.boco.eoms.workbench.infopub.dao.hibernate;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.common.util.StaticMethod;
import com.boco.eoms.workbench.infopub.dao.ThreadHistoryDao;
import com.boco.eoms.workbench.infopub.model.ThreadCountHistory;
import com.boco.eoms.workbench.infopub.model.ThreadHistory;
import java.util.*;
import org.hibernate.*;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

public class ThreadHistoryDaoHibernate extends BaseDaoHibernate
    implements ThreadHistoryDao
{


    public ThreadHistoryDaoHibernate()
    {
    }

    public Map getThreadHistorys(Integer curPage, Integer pageSize, String whereStr, String s)
    {
        return null;
    }

    public List getThreadHistorys(ThreadHistory threadHistory)
    {
        return getHibernateTemplate().find("from ThreadHistory");
    }

    public ThreadHistory getThreadHistory(String id)
    {
        ThreadHistory threadHistory = (ThreadHistory)getHibernateTemplate().get(com.boco.eoms.workbench.infopub.model.ThreadHistory.class, id);
        return threadHistory;
    }

    public void saveThreadHistory(ThreadHistory threadHistory)
    {
        if(threadHistory.getId() == null || threadHistory.getId().equals("")){
            getHibernateTemplate().save(threadHistory);
        }
        else{
        	getHibernateTemplate().saveOrUpdate(threadHistory);
        }
            
    }

    public void removeThreadHistory(String id)
    {
        ThreadHistory history = getThreadHistory(id);
        if(history != null)
        {
            history.setIsDel("1");
            saveThreadHistory(history);
        }
    }

    public Map getThreadHistorys(final Integer curPage, final Integer pageSize, final String whereStr)
    {
        HibernateCallback callback =  new HibernateCallback() {

            public Object doInHibernate(Session session)
                throws HibernateException
            {
                String queryStr = "from ThreadHistory";
                if(whereStr != null && whereStr.length() > 0)
                    queryStr = queryStr + whereStr;
                String queryCountStr = "select count(*) " + queryStr;
                queryStr = queryStr + " order by readTime desc";
                Integer total = (Integer)session.createQuery(queryCountStr).iterate().next();
                Query query = session.createQuery(queryStr);
                query.setFirstResult(pageSize.intValue() * curPage.intValue());
                query.setMaxResults(pageSize.intValue());
                List result = query.list();
                HashMap map = new HashMap();
                map.put("total", total);
                map.put("result", result);
                return map;
            }

        };
        return (Map)getHibernateTemplate().execute(callback);
    }

    public Map getThreadHistorys(Integer curPage, Integer pageSize)
    {
        return getThreadHistorys(curPage, pageSize, null);
    }

    public Map getThreadCountHistory(final Integer curPage, final Integer pageSize, final String whereStr)
    {
        HibernateCallback callback =  new HibernateCallback() {

            public Object doInHibernate(Session session)
                throws HibernateException
            {
                String queryStr = "";
                queryStr = "select count(*),threadId,userId from ThreadHistory";
                Integer total = null;
                if(whereStr != null && whereStr.length() > 0)
                    queryStr = queryStr + whereStr;
                queryStr = queryStr + " group by userId,threadId";
                Query query = session.createQuery(queryStr);
                query.setFirstResult(pageSize.intValue() * curPage.intValue());
                query.setMaxResults(pageSize.intValue());
                List result = query.list();
                HashMap map = new HashMap();
                List resultCount = new ArrayList();
                for(int i = 0; i < result.size(); i++)
                {
                    Object obj[] = (Object[])result.get(i);
                    ThreadCountHistory threadCountHistory = new ThreadCountHistory();
                    threadCountHistory.setCount(StaticMethod.nullObject2String(obj[0]));
                    threadCountHistory.setThreadId(StaticMethod.nullObject2String(obj[1]));
                    threadCountHistory.setUserId(StaticMethod.nullObject2String(obj[2]));
                    resultCount.add(threadCountHistory);
                }

                total = Integer.valueOf(Integer.toString(result.size()));
                map.put("total", total);
                map.put("result", resultCount);
                return map;
            }

        };
        return (Map)getHibernateTemplate().execute(callback);
    }

    public List getThreadHistorysByThreadId(String threadId)
    {
        return getHibernateTemplate().find("from ThreadHistory threadHistory where threadHistory.threadId='" + threadId + "'");
    }
}
