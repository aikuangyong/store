package com.store.utils;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import com.store.model.config.ApplicationEntity;
import com.store.model.config.ColumnModel;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@ConfigurationProperties(prefix = "spring.datasource")
public class DatabaseUtil {

    @Autowired
    private ApplicationEntity applicationEntity;

    private final static Logger LOGGER = Logger.getLogger(DatabaseUtil.class);

    private String username = null;
    private String password = null;
    private String driverclassname = null;

    public void setDriverclassname(String driverclassname) {
        this.driverclassname = driverclassname;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    private String url = null;

    private static final String DRIVER = "com.mysql.jdbc.Driver";

    private static final String SQL = "SELECT * FROM ";// 数据库操作

    public static void main(String[] args) {
//        Pattern pattern = Pattern.compile("(?<=ns\\=\\[).*?(?=\\])");
//        Matcher matcher = pattern.matcher("rows=[Rowcolumns=[1, c], Rowcolumns=[2, java], Rowcolumns=[3, ruby], Rowcolumns=[4, go], Rowcolumns=[5, python], Rowcolumns=[6, lua], Rowcolumns=[7, csharp], Rowcolumns=[8, ajax], Rowcolumns=[9, jsp]]");
//        while(matcher.find()){
//            System.out.println(matcher.group());
//        }
        Pattern pattern = Pattern.compile("(?<=\\[type\\=).*?(?=\\])");
        Matcher matcher = pattern.matcher("[type=input]");
        while(matcher.find()){
            System.out.println(matcher.group());
        }
    }
    /**
     * 获取数据库连接
     *
     * @return
     */
    public Connection getConnection() {
        Connection conn = null;
        try {

            Class.forName(driverclassname);
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            LOGGER.error("get connection failure", e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * 关闭数据库连接
     *
     * @param conn
     */
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                LOGGER.error("close connection failure", e);
            }
        }
    }

    /**
     * 获取数据库下的所有表名
     */
    public List<String> getTableNames() {
        List<String> tableNames = new ArrayList<>();
        Connection conn = getConnection();
        ResultSet rs = null;
        try {
            //获取数据库的元数据
            DatabaseMetaData db = conn.getMetaData();
            //从元数据中获取到所有的表名
            rs = db.getTables(null, null, null, new String[]{"TABLE"});
            while (rs.next()) {
                tableNames.add(rs.getString(3));
            }
        } catch (SQLException e) {
            LOGGER.error("getTableNames failure", e);
        } finally {
            try {
                rs.close();
                closeConnection(conn);
            } catch (SQLException e) {
                LOGGER.error("close ResultSet failure", e);
            }
        }
        return tableNames;
    }

    public List<ColumnModel> getColumnInfo(String tableName) {
        Set<String> hiddenColumn = new HashSet<>();
        hiddenColumn.add("valid");
        hiddenColumn.add("createtime");
        List<ColumnModel> dataList = new ArrayList<>();
        List<String> a = new ArrayList<>();
        //与数据库的连接
        Connection conn = getConnection();
        PreparedStatement pStemt = null;
        String tableSql = SQL + tableName;
        ResultSet rs = null;
        try {
            pStemt = conn.prepareStatement(tableSql);
            rs = pStemt.executeQuery("show full columns from " + tableName);
            String splitChar = ";";
            while (rs.next()) {
                ColumnModel columnModel = new ColumnModel();
                String field = rs.getString("Field");
                columnModel.setColumnName(field);
                columnModel.setProperty(rs.getString("Field").toLowerCase());
                columnModel.setJavaType(type2javaType(rs.getString("Type")));
                columnModel.setColumnType(type2jdbcType(rs.getString("Type")));
                columnModel.setIsRequired("NO".equals(rs.getString("null"))?"1":"0");
                columnModel.setHidden(StringUtils.isEmpty(rs.getString("Key"))?"1":"0");
                if(hiddenColumn.contains(field.toLowerCase())){
                    columnModel.setHidden("0");
                }
                String comment = rs.getString("Comment");
                String htmlType = "input";
                Pattern pattern = Pattern.compile("(?<=\\;type\\=).*");
                Matcher matcher = pattern.matcher(comment);
                while(matcher.find()){
                    htmlType = matcher.group();
                }
                columnModel.setHtmlType(htmlType);
                columnModel.setComment(comment.split(splitChar)[0]);
                columnModel.setIsKey(rs.getString("Key"));
                dataList.add(columnModel);
            }
        } catch (SQLException e) {
            LOGGER.error("getColumnNames failure", e);
        } finally {
            if (pStemt != null) {
                try {
                    pStemt.close();
                } catch (SQLException e) {
                    LOGGER.error("getColumnNames close pstem and connection failure", e);
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            closeConnection(conn);
        }
        return dataList;
    }

    public String type2javaType(String jdbcType) {
        if (jdbcType.toLowerCase().contains("varchar")) {
            return "String";
        } else if (jdbcType.toLowerCase().contains("bigint")) {
            return "Long";
        } else if (jdbcType.toLowerCase().contains("date")) {
            return "Date";
        } else if (jdbcType.toLowerCase().contains("decimal")) {
            return "Double";
        } else if (jdbcType.toLowerCase().contains("int")) {
            return "Integer";
        } else {
            return "String";
        }
    }

    public String type2jdbcType(String jdbcType) {
        if (jdbcType.toLowerCase().contains("varchar")) {
            return "VARCHAR";
        } else if (jdbcType.toLowerCase().contains("bigint")) {
            return "BIGINT";
        } else if (jdbcType.toLowerCase().contains("date")) {
            return "DATE";
        } else if (jdbcType.toLowerCase().contains("decimal")) {
            return "DECIMAL";
        } else if (jdbcType.toLowerCase().contains("int")) {
            return "INT";
        } else if (jdbcType.toLowerCase().contains("text")) {
            return "TEXT";
        } else {
            return "VARCHAR";
        }
    }

    /**
     * 获取表中所有字段名称
     *
     * @param tableName 表名
     * @return
     */
    public List<String> getColumnNames(String tableName) {
        List<String> columnNames = new ArrayList<>();
        //与数据库的连接
        Connection conn = getConnection();
        PreparedStatement pStemt = null;
        String tableSql = SQL + tableName;
        try {
            pStemt = conn.prepareStatement(tableSql);
            //结果集元数据
            ResultSetMetaData rsmd = pStemt.getMetaData();
            //表列数
            int size = rsmd.getColumnCount();
            for (int i = 0; i < size; i++) {
                rsmd.getColumnTypeName(i + 1);
                columnNames.add(rsmd.getColumnName(i + 1));
            }
        } catch (SQLException e) {
            LOGGER.error("getColumnNames failure", e);
        } finally {
            if (pStemt != null) {
                try {
                    pStemt.close();
                    closeConnection(conn);
                } catch (SQLException e) {
                    LOGGER.error("getColumnNames close pstem and connection failure", e);
                }
            }
        }
        return columnNames;
    }

    /**
     * 获取表中所有字段类型
     *
     * @param tableName
     * @return
     */
    public List<String> getColumnTypes(String tableName) {
        List<String> columnTypes = new ArrayList<>();
        //与数据库的连接
        Connection conn = getConnection();
        PreparedStatement pStemt = null;
        String tableSql = SQL + tableName;
        try {
            pStemt = conn.prepareStatement(tableSql);
            //结果集元数据
            ResultSetMetaData rsmd = pStemt.getMetaData();
            //表列数
            int size = rsmd.getColumnCount();
            for (int i = 0; i < size; i++) {
                columnTypes.add(rsmd.getColumnTypeName(i + 1));
            }
        } catch (SQLException e) {
            LOGGER.error("getColumnTypes failure", e);
        } finally {
            if (pStemt != null) {
                try {
                    pStemt.close();
                    closeConnection(conn);
                } catch (SQLException e) {
                    LOGGER.error("getColumnTypes close pstem and connection failure", e);
                }
            }
        }
        return columnTypes;
    }

    /**
     * 获取表中字段的所有注释
     *
     * @param tableName
     * @return
     */
    public List<String> getColumnComments(String tableName) {
        List<String> columnTypes = new ArrayList<>();
        //与数据库的连接
        Connection conn = getConnection();
        PreparedStatement pStemt = null;
        String tableSql = SQL + tableName;
        List<String> columnComments = new ArrayList<>();//列名注释集合
        ResultSet rs = null;
        try {
            pStemt = conn.prepareStatement(tableSql);
            rs = pStemt.executeQuery("show full columns from " + tableName);
            while (rs.next()) {
                columnComments.add(rs.getString("Comment"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    closeConnection(conn);
                } catch (SQLException e) {
                    LOGGER.error("getColumnComments close ResultSet and connection failure", e);
                }
            }
        }
        return columnComments;
    }
//    public static void main(String[] args) {
//        List<String> tableNames = getTableNames();
//        System.out.println("tableNames:" + tableNames);
//        for (String tableName : tableNames) {
//            System.out.println("ColumnNames:" + getColumnNames(tableName));
//            System.out.println("ColumnTypes:" + getColumnTypes(tableName));
//            System.out.println("ColumnComments:" + getColumnComments(tableName));
//        }
//    }
}