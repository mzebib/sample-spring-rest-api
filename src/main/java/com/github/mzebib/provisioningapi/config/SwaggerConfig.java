package com.github.mzebib.provisioningapi.config;

import com.github.mzebib.provisioningapi.util.ProvConst;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.BasicAuth;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author mzebib
 */
@EnableSwagger2
@Configuration
public class SwaggerConfig {

    @Value("${info.app.name}")
    private String appName;
    @Value("${info.app.description}")
    private String appDescription;
    @Value("${info.app.version}")
    private String appVersion;
    @Value("${server.contextPath}")
    private String baseUri;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(paths())
                .build()
                .globalResponseMessage(RequestMethod.GET, responseMessages())
                .globalResponseMessage(RequestMethod.POST, responseMessages())
                .globalResponseMessage(RequestMethod.PUT, responseMessages())
                .globalResponseMessage(RequestMethod.DELETE, responseMessages())
                .apiInfo(metaInfo())
                .securitySchemes(Collections.singletonList(new BasicAuth("basicAuth")));
    }

    private Predicate<String> paths() {
        List<Predicate<String>> basePaths = new ArrayList<>();
        basePaths.add(PathSelectors.regex(baseUri + ".*"));
        basePaths.add(PathSelectors.regex(ProvConst.URI_AUTH + ".*"));
        basePaths.add(PathSelectors.regex(ProvConst.URI_USER + ".*"));
        basePaths.add(PathSelectors.regex(ProvConst.URI_ORG + ".*"));
        basePaths.add(PathSelectors.ant(ProvConst.URI_AUDITEVENTS));
        basePaths.add(PathSelectors.ant(ProvConst.URI_BEANS));
        basePaths.add(PathSelectors.ant(ProvConst.URI_CONFIGPROPS));
        basePaths.add(PathSelectors.ant(ProvConst.URI_ENV));
        basePaths.add(PathSelectors.ant(ProvConst.URI_INFO));
        basePaths.add(PathSelectors.ant(ProvConst.URI_HEALTH));
        basePaths.add(PathSelectors.ant(ProvConst.URI_LOGGERS));
        basePaths.add(PathSelectors.ant(ProvConst.URI_MAPPINGS));
        basePaths.add(PathSelectors.ant(ProvConst.URI_METRICS));
        basePaths.add(PathSelectors.ant(ProvConst.URI_TRACE));

        return Predicates.or(basePaths);
    }

    private ApiInfo metaInfo() {
        return new ApiInfoBuilder().title("Provisioning API")
                .description(appDescription)
                .version(appVersion)
                .build();
    }

    private List<ResponseMessage> responseMessages() {
        return Arrays.asList(
                new ResponseMessageBuilder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .message(HttpStatus.BAD_REQUEST.getReasonPhrase())
                        .build(),
                new ResponseMessageBuilder()
                        .code(HttpStatus.UNAUTHORIZED.value())
                        .message(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                        .build(),
                new ResponseMessageBuilder()
                        .code(HttpStatus.FORBIDDEN.value())
                        .message(HttpStatus.FORBIDDEN.getReasonPhrase())
                        .build(),
                new ResponseMessageBuilder()
                        .code(HttpStatus.CONFLICT.value())
                        .message(HttpStatus.CONFLICT.getReasonPhrase())
                        .build(),
                new ResponseMessageBuilder()
                        .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .message(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                        .build()
        );
    }

}