package com.whut.resume.util;


import org.apache.commons.lang.StringUtils;

import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

/**
 * @author AC <chuhan@133.cn>
 * @describe
 * @date 2021/4/27 16:40
 */
public class sqlUtil {
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/resume?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASS = "23451";
    private static Connection connection;
    private static Statement statement;
    public static void open() throws ClassNotFoundException, SQLException {
        Class.forName(JDBC_DRIVER);
        connection=DriverManager.getConnection(DB_URL,USER,PASS);
        statement=connection.createStatement();
    }

    public static void close() throws Exception{
        statement.close();
        connection.close();
    }

    /***
     * 将类中信息写入数据库中指定的表
     * @param object
     * @param tableName
     * @throws IllegalAccessException
     */
    public static boolean addInfo(Object object,String tableName,Integer id) throws Exception{
        Map<String,String> attrAndValue=classUtil.getAttributesAndValue(object);
        List<String> fields=classUtil.getFields(object);
        StringJoiner stringJoiner=new StringJoiner("\",\"","\"","\"");
        //获取所有值
        for(String field:fields){
            stringJoiner.add(attrAndValue.get(field));
        }
        String sql="insert into "+tableName+" values ("+id+","+stringJoiner+");";
        return statement.executeUpdate(sql)<=0?false:true;
    }

    public static boolean addInfo(String object,String tableName,Integer id) throws Exception{
        String objectStr="\""+object+"\"";
        String sql="insert into "+tableName+" values ("+id+","+objectStr+");";
        return statement.executeUpdate(sql)<=0?false:true;
    }

    /***
     * 插入基本信息后查询新插入行的id
     * @return
     */
    public static int getID() throws Exception{
        String sql="SELECT LAST_INSERT_ID();";
        statement.execute(sql);
        ResultSet resultSet=statement.getResultSet();
        resultSet.next();
        return resultSet.getInt(1);
    }
}
