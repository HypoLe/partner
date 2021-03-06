package com.boco.eoms.filemanager.topic;


import java.sql.Connection;
import java.sql.SQLException;


/**
 * Created by IntelliJ IDEA.
 * User: gaoc
 * Date: 2003-9-3
 * Time: 11:21:11
 * To change this template use Options | File Templates.
 */
public class LbSelectInfoTypeId extends LBDbLogic {

    public LbSelectInfoTypeId() {
        super();
    }

    public LbSelectInfoTypeId(Connection con) {
        super(con);
    }

    //设置sql文
    public void setSql() {
        m_strSql = "SELECT topic_id FROM taw_file_mgr_topic where topic_type=? and topic_type_name=? and par_topic_id= ? and topic_name= ? and topic_memo= ? and topic_path= ? and topic_order= ? and class_id= ?  ";
    }

    //设置返回HashMap的key植
    public void setReturnParam() {
        String[] strArray = {"topic_id"};
        m_strArrayReturn = strArray;
    }

    public int executeProcess(String[] strParamArray) throws SQLException, Exception {
        return super.executeProcess(strParamArray);
    }
}
