package com.gaoap.opf.upm.conf;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

/**
 * 访问地址：http://localhost:9021/swagger-ui/index.html
 *
 * @author gaoyd
 * @version 1.0.0
 * @ClassName Swagger3Config.java
 * @Description TODO
 * @createTime 2021年10月18日 20:40:00
 */
@Configuration
@EnableOpenApi
public class Swagger3Config {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .securitySchemes(Collections.singletonList(HttpAuthenticationScheme.JWT_BEARER_BUILDER
//                        显示用
                        .name("JWT")
                        .build()))
                .securityContexts(Collections.singletonList(SecurityContext.builder()
                        .securityReferences(Collections.singletonList(SecurityReference.builder()
                                .scopes(new AuthorizationScope[0])
                                .reference("JWT")
                                .build()))
                        // 声明作用域
                        .operationSelector(o -> o.requestMappingPattern().matches("/.*"))
                        .build()))
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                // .apis(RequestHandlerSelectors.basePackage("com.gaoap.opf.upm.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Swagger3接口文档")
                .description("更多请咨询服务开发者")
                .contact(new Contact("gaoyd", "http://www.gaoap.cn", "gaoyd@gaoap.com"))
                .version("1.0.0")
                .build();
    }
}
/**
 * @Api：用在请求的类上，表示对类的说明 tags="说明该类的作用，可以在UI界面上看到的注解"
 * value="该参数没什么意义，在UI界面上也看到，所以不需要配置"
 * @ApiOperation：用在请求的方法上，说明方法的用途、作用 value="说明方法的用途、作用"
 * notes="方法的备注说明"
 * @ApiImplicitParams：用在请求的方法上，表示一组参数说明
 * @ApiImplicitParam：用在@ApiImplicitParams注解中，指定一个请求参数的各个方面 name：参数名
 * value：参数的汉字说明、解释
 * required：参数是否必须传
 * paramType：参数放在哪个地方
 * · header --> 请求参数的获取：@RequestHeader
 * · query --> 请求参数的获取：@RequestParam
 * · path（用于restful接口）--> 请求参数的获取：@PathVariable
 * · div（不常用）
 * · form（不常用）
 * dataType：参数类型，默认String，其它值dataType="Integer"
 * defaultValue：参数的默认值
 * @ApiResponses：用在请求的方法上，表示一组响应
 * @ApiResponse：用在@ApiResponses中，一般用于表达一个错误的响应信息 code：数字，例如400
 * message：信息，例如"请求参数没填好"
 * response：抛出异常的类
 * @ApiModel：用于响应类上，表示一个返回响应数据的信息 （这种一般用在post创建的时候，使用@RequestBody这样的场景，
 * 请求参数无法使用@ApiImplicitParam注解进行描述的时候）
 * @ApiModelProperty：用在属性上，描述响应类的属性
 */
