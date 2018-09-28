package com.guye.config.swagger2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2Configuration {
    
    @Bean
    public Docket accessToken() {
    	//可以添加多个header或参数
        /*ParameterBuilder aParameterBuilder = new ParameterBuilder();
        aParameterBuilder
                .parameterType("header") //参数类型支持header, cookie, body, query etc
                .name("token") //参数名
                .defaultValue("token") //默认值
                .description("header中token字段测试")
                .modelRef(new ModelRef("string"))//指定参数值的类型
                .required(false).build(); //非必需，这里是全局配置，然而在登陆的时候是不用验证的
        List<Parameter> aParameters = new ArrayList<Parameter>();
        aParameters.add(aParameterBuilder.build());*/
        return new Docket(DocumentationType.SWAGGER_2)
//                .produces(Sets.newHashSet("application/json"))
//                .consumes(Sets.newHashSet("application/json"))
//                .protocols(Sets.newHashSet("http", "https"))

//                .forCodeGeneration(true)
                .groupName("api")// 定义组
                .select() // 选择那些路径和 api 会生成 document
                .apis(RequestHandlerSelectors.basePackage("com.guye.controller")) // 拦截的包路径
//                .apis(RequestHandlerSelectors.any()) // 拦截的包路径
                .paths(PathSelectors.regex("/*/.*"))// 拦截的接口路径
//                .paths(PathSelectors.any())// 拦截的接口路径
                .build()
                .apiInfo(apiInfo()); // 配置说明; // 创建

    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()//
                .title("Spring Boot 之 Web 篇")// 标题
                .description("spring boot Web 相关内容")// 描述
                .termsOfServiceUrl("http://www.baidu.com")//
                .contact(new Contact("moonlightL", "http://www.baidu.com", "445847261@qq.com"))// 联系
                .version("1.0")// 版本
                .build();
    }
}
