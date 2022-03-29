package org.example.dao;

import org.example.model.Article;
import org.example.util.DBUtil;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//文章表的CRUD操作
public class ArticleDao {
    public static List<Article> queryByUserId(Integer id) {
        List<Article> articles=new ArrayList<>();
        Connection c=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        try{
            c=DBUtil.getConnection();
            String sql="select id,title,`date`,content,user_id " +
                    "from article where user_id=?";
            ps=c.prepareStatement(sql);
            ps.setInt(1,id);
            rs=ps.executeQuery();
            while(rs.next()){
                //结果集中,每一行数据转换为一个文章对象,再添加到List中
                Article a=new Article();
                Integer aId=rs.getInt("id");
                String title=rs.getString("title");
                java.sql.Date date=rs.getDate("date");
                String content=rs.getString("content");
                a.setId(aId);
                a.setTitle(title);
                long time=date.getTime();
                a.setDate(new java.util.Date(time));
                //日期格式化类:将日期对象转换为设置格式的日期字符串
                DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
                String dateString=df.format(a.getDate());
                a.setDateString(dateString);

                //如果文章内容超过50,截取前50
                a.setContent(content.length()<=50?content:content.substring(0,50));
                a.setUserId(id);

                articles.add(a);
            }
            return articles;
        } catch (SQLException e) {
            throw new RuntimeException("查询文章列表jdbc出错",e);
        } finally {
            DBUtil.close(c,ps,rs);
        }


    }

    //根据文章id,查询一篇文章
    public static Article queryById(int id) {
        Article a=null;
        Connection c=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        try{
            c=DBUtil.getConnection();
            String sql="select id,title,`date`,content,user_id" +
                    "from article where id=?";
            ps=c.prepareStatement(sql);
            ps.setInt(1,id);
            rs=ps.executeQuery();
            while(rs.next()){
                //处理每一行数据
                a=new Article();
                String title=rs.getString("title");
                java.sql.Date date=rs.getDate("date");
                //同上,需要转换日期为年-月-日的字符串
                DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
                String dateString=df.format(new java.util.Date(date.getTime()));
                String content=rs.getString("content");
                Integer userId=rs.getInt("user_id");
                a.setId(id);
                a.setTitle(title);
                a.setDateString(dateString);
                a.setContent(content);
                a.setUserId(userId);

            }
            return a;

        } catch (SQLException e) {
            throw new RuntimeException("获取文章详情jdbc出错",e);
        }finally {
            DBUtil.close(c,ps,rs);
        }
    }



    @Test
    public void testQueryByUserId(){
        List<Article> articles=queryByUserId(1);
        System.out.println(articles);
    }

    @Test
    public void parseDate(){
        java.util.Date d=new java.util.Date();
        //使用日期格式化类,可以将日期和字符串互相转换:构造方法传入的pattern字符串
        //yyyy年 MM月 dd日
        DateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(df.format(d));
    }

    @Test
    public void testQueryById(){
        Article a=queryById(1);
        System.out.println(a);
    }
}
