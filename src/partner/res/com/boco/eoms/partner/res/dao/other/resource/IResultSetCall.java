package com.boco.eoms.partner.res.dao.other.resource;

import java.sql.ResultSet;
import java.sql.SQLException;
 
public interface IResultSetCall<T> {
 
    public T invoke(ResultSet rs) throws SQLException;
 
}
