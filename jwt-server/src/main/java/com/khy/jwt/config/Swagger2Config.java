package com.khy.jwt.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

/**
 * swagger2配置类
 * <p>
 * select()函数返回一个ApiSelectorBuilder实例用来控制哪些接口暴露给Swagger来展现.
 * <p>
 * 此处采用指定扫描的包路径来定义，Swagger会扫描该包下所有Controller定义的API，并产生文档内容（除了被@ApiIgnore注解的API）
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    // 设置默认TOKEN，方便测试
    private static final String TOKEN = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJVU0VSX1JPTEUiOiJbXCJBRE1JTlwiLFwiQURNSU4xXCJdIiwiZXhwIjoxNTMyMDg0MDE3LCJ1c2VybmFtZSI6IjEwMDg2IiwidGltZXN0YW1wIjoxNTMyMDgwNDE3NDUxfQ.gLWsqzUQwA1BcXf7tv-KFGU_nN6Umq7LPLefoWH6Jyq4kX9SBLMDsHTfHEA47WlZMrlqkA_9MUbOGrzzLSOYmw";

    /**
     * 创建controller中的API文档
     *
     * @return
     */
    @Bean
    public Docket createRestUserApi() {
        /*ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<Parameter>();
        tokenPar.name("Authorization").description("令牌").defaultValue(TOKEN).modelRef(new ModelRef("string")).parameterType("header").required(false).build();
        pars.add(tokenPar.build());*/
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("IndexController")
                .apiInfo(createUserApiInfo())
                .select()
                //.apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .apis(RequestHandlerSelectors.basePackage("com.khy.jwt.controller"))//api接口包扫描路径
                .paths(PathSelectors.any())//可以根据url路径设置哪些请求加入文档，忽略哪些请求
                .build()
                //.globalOperationParameters(pars)
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());

    }

    /**
     * 创建controller2中的API文档
     *
     * @return
     */
    /*@Bean
    public Docket createRestCusApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("创建controller2中的api")
                .apiInfo(createCusApiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.khy.jwt.controller2"))
                .paths(PathSelectors.any())
                .build();
    }*/

    /**
     * 创建用户API信息
     *
     * @return
     */
    private ApiInfo createUserApiInfo() {
        return new ApiInfoBuilder()
                .title("处理用户信息相关接口")//设置文档的标题
                .description("以下接口只操作用户信息")//设置文档的描述->1.Overview
                .contact(new Contact("kouhanyao", "http://blog.csdn.net/tenghu8888", ""))//设置文档的联系方式->1.2 Contact information
                .version("1.0")//设置文档的版本信息-> 1.1 Version information
                .build();
    }

    /**
     * 创建客户API信息
     *
     * @return
     */
    /*private ApiInfo createCusApiInfo() {
        return new ApiInfoBuilder()
                .title("处理客户信息相关接口")
                .description("以下接口只操作客户信息")
                .contact(new Contact("kouhanyao1", "http://baidu.com", ""))
                .version("1.0")
                .build();
    }*/

    /**
     * 通过Swagger2的securitySchemes配置全局参数：如下列代码所示，securitySchemes的ApiKey中增加一个名
     * 为“Authorization”，type为“header”的参数。
     * @return
     */
    private List<ApiKey> securitySchemes() {
        return newArrayList(
                new ApiKey("Authorization", "Authorization", "header"));
    }

    /**
     * //在Swagger2的securityContexts中通过正则表达式，设置需要使用参数的接口（或者说，是去除掉不需要使用参数的接口），
     * 如下列代码所示，通过PathSelectors.regex("^(?!auth).*$")，所有包含"auth"的接口不需要使用securitySchemes。即不
     * 需要使用上文中设置的名为“Authorization”，type为“header”的参数。
     *
     * @return
     */
    private List<SecurityContext> securityContexts() {
        return newArrayList(
                SecurityContext.builder()
                        .securityReferences(defaultAuth())
                        .forPaths(PathSelectors.regex("^(?!auth).*$"))
                        .build()
        );
    }

    List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return newArrayList(
                new SecurityReference("Authorization", authorizationScopes));
    }
}
