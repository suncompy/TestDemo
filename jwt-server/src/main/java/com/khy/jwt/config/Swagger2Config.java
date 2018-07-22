package com.khy.jwt.config;

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

/**
 * swagger2配置类
 *
 * select()函数返回一个ApiSelectorBuilder实例用来控制哪些接口暴露给Swagger来展现.
 *
 * 此处采用指定扫描的包路径来定义，Swagger会扫描该包下所有Controller定义的API，并产生文档内容（除了被@ApiIgnore注解的API）
 *
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {
    /**
     * 创建controller中的API文档
     * @return
     */
    @Bean
    public Docket createRestUserApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("IndexController")
                .apiInfo(createUserApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.khy.jwt.controller"))//api接口包扫描路径
                .paths(PathSelectors.any())//可以根据url路径设置哪些请求加入文档，忽略哪些请求
                .build();

    }

    /**
     * 创建controller2中的API文档
     * @return
     */
    @Bean
    public Docket createRestCusApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("创建controller2中的api")
                .apiInfo(createCusApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.khy.jwt.controller2"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * 创建用户API信息
     * @return
     */
    private ApiInfo createUserApiInfo(){
        return new ApiInfoBuilder()
                .title("处理用户信息相关接口")//设置文档的标题
                .description("以下接口只操作用户信息")//设置文档的描述->1.Overview
                .contact(new Contact("kouhanyao","http://blog.csdn.net/tenghu8888",""))//设置文档的联系方式->1.2 Contact information
                .version("1.0")//设置文档的版本信息-> 1.1 Version information
                .build();
    }

    /**
     * 创建客户API信息
     * @return
     */
    private ApiInfo createCusApiInfo(){
        return new ApiInfoBuilder()
                .title("处理客户信息相关接口")
                .description("以下接口只操作客户信息")
                .contact(new Contact("kouhanyao1","http://baidu.com",""))
                .version("1.0")
                .build();
    }
}
