package org.example.util;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//数据库工具类:提供获取数据库连接,释放资源的统一代码
public class DBUtil {
    //静态变量,是类加载的时候初始化,只执行一次
    private static MysqlDataSource ds;

    //一个程序,连接一个数据库,只需要一个连接池,
    // 其中保存了多个数据库连接对象
    //获取连接池,内部使用,不开放
    private static DataSource getDataSource(){
        //TODO 有点问题:多线程环境还有点问题,后序解决
        //ds类加载的时候,初始化为null.
        //方法使用时需判断,若为null就要new,创建及初始化属性
        if(ds==null){
            ds=new MysqlDataSource();
            ds.setURL("jdbc:mysql://127.0.0.1:3306/biaobai");
            ds.setUser("root");
            ds.setPassword("asd9410015");
            ds.setUseSSL(false);//不安全连接,如果不设置不影响,但又警告
            ds.setCharacterEncoding("UTF-8");
        }
        return ds;
    }
    //获取数据库连接对象,开放给外部的jdbc代码使用
    public static Connection getConnection(){
        try{
            return getDataSource().getConnection();
        }catch (SQLException e){
            throw new RuntimeException("获取数据库连接出错," +
                    "可能是url,账号密码写错了",e);

        }

    }

    //查询操作需要释放三个资源
    //更新操作(插入修改删除)只需要释放前两个资源
    public static void close(Connection c, Statement s, ResultSet r){
        try {
            if(r!=null) r.close();
            if(s!=null) s.close();
            if(c!=null) c.close();
        } catch (SQLException e) {
            throw new RuntimeException("释放数据库资源出错",e);
        }
    }

    //提供更新操作方便的释放资源功能
    public static void close(Connection c,Statement s){
        close(c,s,null);
    }

    public static void main(String[] args) {
        System.out.println(getConnection());
    }
}
