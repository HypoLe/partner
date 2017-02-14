/**
 * @(#)DBConnection
 *
 * Copyright (c) 2001 MCS. All Rights Reserved.
 *
 *
 * @author  Yuanlei
 * @version 1.0
 * @date    15Apr2002
 * # 数据库连接包装类，添加时间戳、超时时长、所属连接池、是否租用等属性，增加死连接的验证功能
 */
package mcs.common.pool;

import java.sql.*;
import java.util.*;
import java.io.*;

/**
 * 数据库连接包装类，添加时间戳、超时时长、所属连接池、是否租用等属性，增加死连接的验证功能<br>
 */
public  class DBConnection implements Connection
{
	private DBCPool pool;
	private Connection conn;
	private boolean inuse;
	private long timestamp;

	public DBConnection (Connection conn,DBCPool pool)
	{
		this.conn = conn;
		this.pool = pool;
		this.inuse = false;
		this.timestamp = 0;
	}

	public synchronized boolean lease()
	{
		if (inuse) return false;
		else
		{
			inuse = true;
			timestamp = System.currentTimeMillis();
			return true;
		}
	}
	/**
	 * 验证死连接，返回为false为死连接
	 */
	public boolean validate()
	{
		try
		{
			conn.getMetaData();
		}
		catch (Exception e)
		{
			return false;
		}
		return true;
	}

	public boolean inUse()
	{
		return inuse;
	}

	public long getLastUse()
	{
		return timestamp;
	}

	public void close() throws SQLException
	{
		pool.returnConn(this);
	}

	protected void expireLease()
	{
		inuse = false;
	}

	protected Connection getConnection()
	{
		return conn;
	}

	public PreparedStatement prepareStatement(String sql) throws SQLException
	{
		return conn.prepareStatement(sql);
	}

        public PreparedStatement prepareStatement(String sql,int col) throws SQLException
        {
          return conn.prepareStatement(sql);
        }

        public PreparedStatement prepareStatement(String sql,int[] cols) throws SQLException
        {
          return conn.prepareStatement(sql);
	}

        public PreparedStatement prepareStatement(String sql,String[] sqls) throws SQLException
        {
                return conn.prepareStatement(sql);
	}

        public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency)
        {
          try {
            return conn.prepareStatement(sql);
          }
          catch (SQLException ex) {
            return null;
          }
        }

        public PreparedStatement prepareStatement(String sql, int resultSetType, int resultSetConcurrency,int resultSetHoldability)
        {
          try {
            return conn.prepareStatement(sql);
          }
          catch (SQLException ex) {
            return null;
          }
        }

	public CallableStatement prepareCall(String sql) throws SQLException
	{
		return conn.prepareCall(sql);
	}


	public Statement createStatement() throws SQLException
	{
		return conn.createStatement();
	}


	public Statement createStatement(int resultSetType,
                                 int resultSetConcurrency,
                                 int resultSetHoldability)
                                 throws SQLException
	{
		return null;
	}

	public Statement createStatement(int resultSetType,
                                 int resultSetConcurrency)
                          throws SQLException
       {
       	return null;
       }

	public CallableStatement prepareCall(String sql,
                                     int resultSetType,
                                     int resultSetConcurrency)
                              throws SQLException
	{
		return null;
	}

        public CallableStatement prepareCall(String sql,
            int resultSetType,
            int resultSetConcurrency,
            int resultSetHoldability)
            throws SQLException
        {
          return null;
        }

	public Map getTypeMap()
               throws SQLException
	{
		return null;
	}

	public void setTypeMap(Map map)
                throws SQLException
	{

	}


        public void releaseSavepoint(Savepoint savepoint)
        {
          try {


            conn.releaseSavepoint(savepoint);
          }
          catch (SQLException ex) {
          }
        }


        public Savepoint setSavepoint()
        {
          try {
            return conn.setSavepoint();
          }
          catch (SQLException ex) {
            return null;
          }
        }

        public Savepoint setSavepoint(String name)
        {
          try {
            return conn.setSavepoint(name);
          }
          catch (SQLException ex) {
            return null;
          }
        }

       public int getHoldability()
       {
        try {
          return conn.getHoldability();
        }
        catch (SQLException ex) {
          return 0;
        }
       }

       public void  setHoldability()
       {

       }

       public void  setHoldability(int holdability)
       {

       }



	public String nativeSQL(String sql) throws SQLException
	{
		return conn.nativeSQL(sql);
	}

	public void setAutoCommit(boolean autoCommit) throws SQLException
	{
		conn.setAutoCommit(autoCommit);
	}

	public boolean getAutoCommit() throws SQLException
	{
		return conn.getAutoCommit();
	}

	public void commit() throws SQLException
	{
		conn.commit();
	}

	public void rollback() throws SQLException
	{
		conn.rollback();
	}

        public void rollback(Savepoint sp) throws SQLException
        {
          conn.rollback();
        }

	public boolean isClosed() throws SQLException
	{
		return conn.isClosed();
	}

	public DatabaseMetaData getMetaData() throws SQLException
	{
		return conn.getMetaData();
	}

	public void setReadOnly(boolean readOnly) throws SQLException
	{
		conn.setReadOnly(readOnly);
	}

	public boolean isReadOnly() throws SQLException
	{
		return conn.isReadOnly();
	}

	public void setCatalog(String catalog) throws SQLException
	{
		conn.setCatalog(catalog);
	}

	public String getCatalog() throws SQLException
	{
		return conn.getCatalog();
	}

	public void setTransactionIsolation(int level) throws SQLException
	{
		conn.setTransactionIsolation(level);
	}

	public int getTransactionIsolation() throws SQLException
	{
		return conn.getTransactionIsolation();
	}

	public SQLWarning getWarnings() throws SQLException
	{
		return conn.getWarnings();
	}

	public void clearWarnings() throws SQLException
	{
		conn.clearWarnings();
	}

	public Array createArrayOf(String typeName, Object[] elements) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Blob createBlob() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Clob createClob() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

//	public java.sql.NClob createNClob() throws SQLException {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	public java.sql.SQLXML createSQLXML() throws SQLException {
//		// TODO Auto-generated method stub
//		return null;
//	}

	public Struct createStruct(String typeName, Object[] attributes) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public Properties getClientInfo() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getClientInfo(String name) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean isValid(int timeout) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

//	public void setClientInfo(Properties properties) throws java.sql.SQLClientInfoException {
//		// TODO Auto-generated method stub
		
//	}
//
//	public void setClientInfo(String name, String value) throws java.sql.SQLClientInfoException {
//		// TODO Auto-generated method stub
		
//	}


	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	public <T> T unwrap(Class<T> iface) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

    @Override
    public NClob createNClob() throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public SQLXML createSQLXML() throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setClientInfo(String name, String value) throws SQLClientInfoException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setClientInfo(Properties properties) throws SQLClientInfoException {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
