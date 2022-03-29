package org.example.listener;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class TemplateEngineListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //创建模板引擎和解析器
        TemplateEngine engine=new TemplateEngine();
        ServletContext sc=sce.getServletContext();
        ServletContextTemplateResolver resolver=new ServletContextTemplateResolver(sc);
        resolver.setCharacterEncoding("utf-8");
        resolver.setPrefix("/WEB-INF/templates/");
        resolver.setSuffix(".html");
        engine.setTemplateResolver(resolver);
        sc.setAttribute("engine",engine);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
