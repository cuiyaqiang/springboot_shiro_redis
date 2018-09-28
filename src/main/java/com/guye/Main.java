package com.guye;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.expression.ParseException;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootApplication
@MapperScan("com.guye.dao")     //配置生成dao层的bean
//@EnableWebSocket
//@ImportResource({"classpath:dubbo/dubbo-controller.xml"})
public class Main {

    public static void main(String[] args){
//        Properties properties = PropertiesFactory.createProperties("common","ceshi.properties");
//        System.out.println(properties.getProperty("spring.datasource.username"));
        SpringApplication.run(Main.class,args);
    }

}
