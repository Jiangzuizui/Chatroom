package com.bit.jdbc;



import java.sql.*;

public class Test {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Connection conn=null;
        Statement s=null;
        ResultSet r=null;
        try {
            //反射的方式,加载一个类(类加载:执行静态变量,静态代码块)
            //数据库驱动包就可以在这种操作下,执行对应的初始化工作(驱动)
            Class.forName("com.mysql.jdbc.Driver");
            //获取数据库连接:Connection接口,需要使用jdbc中的,不要使用mysql中的
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/java43?user=root" +
                    "&password=asd9410015&useUnicode=true&characterEncoding=utf-8&useSSL=false");
            System.out.println(conn);
            //通过连接对象创建操作命令对象Statement
            s = conn.createStatement();
            //查询
            //1.调用Statement操作命令对象的executeQuery(sql)
            //2.返回一个ResultSet结果集对象
            r = s.executeQuery("select id,name,chinese,math,english from exam_result where id>3");
            //处理结果集:结果集可能是多行数据,需要遍历获取,调用next就移动到下一行,
            //返回true代表该行有数据,反之
            while (r.next()) {//一直遍历到最后没有数据
                int id = r.getInt("id");
                String name = r.getString("name");
                double chinese = r.getDouble("chinese");
                double math = r.getDouble("math");
                double english = r.getDouble("english");
                System.out.printf("id=%s,name=%s,chinese=%s,math=%s,english=%s\n",
                        id, name, chinese, math, english);

            }
        }finally {//无论如何都要释放资源
            if(r!=null){
                r.close();
            }
            if(s!=null){
                s.close();
            }
            if(conn!=null){
                conn.close();
            }
        }



    }
}
