package com.cobona.vici;

import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.bstek.ureport.console.UReportServlet;
import com.cobona.vici.config.properties.ViciProperties;

/**
 * SpringBoot方式启动类
 *
 * @author cobona
 * @Date 2017/5/21 12:06
 */
@SpringBootApplication
@ImportResource("classpath:ureport-console-context.xml")
public class ViciApplication extends WebMvcConfigurerAdapter{

    protected final static Logger logger = LoggerFactory.getLogger(ViciApplication.class);

    @Autowired
    ViciProperties viciProperties;

    /**
     * 增加swagger的支持
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if(viciProperties.getSwaggerOpen()){
            registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
            registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(ViciApplication.class, args);
        logger.info("ViciApplication is success!");
    }
    
   @Bean //开启ureport
    public ServletRegistrationBean buildUReportServlet(){
    	return new ServletRegistrationBean(new UReportServlet(),"/ureport/*");
    }
    
   /* *//** 开启ssl
     * 配置一个TomcatEmbeddedServletContainerFactory bean
     * @return
     *//*
    @Bean
    public EmbeddedServletContainerFactory servletContainer() {

        TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory() {

            @Override
            protected void postProcessContext(Context context) {

                SecurityConstraint securityConstraint = new SecurityConstraint();
                securityConstraint.setUserConstraint("CONFIDENTIAL");
                SecurityCollection collection = new SecurityCollection();
                collection.addPattern("/*");
                securityConstraint.addCollection(collection);
                context.addConstraint(securityConstraint);
            }
        };
        tomcat.addAdditionalTomcatConnectors(initiateHttpConnector());
        return tomcat;
    }

    *//**
     * 让我们的应用支持HTTP是个好想法，但是需要重定向到HTTPS，
     * 但是不能同时在application.properties中同时配置两个connector，
     * 所以要以编程的方式配置HTTP connector，然后重定向到HTTPS connector
     * @return Connector
     *//*
    private Connector initiateHttpConnector() {
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setScheme("http");
        connector.setPort(8080); // http端口
        connector.setSecure(false);
        connector.setRedirectPort(8443); // application.properties中配置的https端口
        return connector;
    }*/
    
}
