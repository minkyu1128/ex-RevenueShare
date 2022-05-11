package com.example.revenueshare.core.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class SpringDocConfig {

    @Bean
    public OpenAPI openAPI(@Value("${app.url}") String url
                        ,@Value("${app.version}") String appVersion
                        ,@Value("${spring.profiles.active}") String active) {
        Info info = new Info().title("RS API - Active Profile: " + active)
                .version(appVersion)
                .description("유튜브 채널 수익배분 Application API 입니다.")
                .termsOfService("http://swagger.io/terms/")
                .contact(new Contact().name("sandbox").url("https://sandbox.co.kr/").email("minkyu1128@gmail.co.kr"))
                .license(new License().name("Apache License Version 2.0").url("http://www.apache.org/licenses/LICENSE-2.0"));

        /*TODO : 등록시 url 뒤에 url에 ... (curl 실행시)*/
        List<Server> servers = Arrays.asList(new Server().url(url).description("RS api (" + active +")"));


        return new OpenAPI()
                .info(info)
                .servers(servers);
    }




//    @Profile({"local"})
    @Bean
    public GroupedOpenApi baseInfoMngApiDoc() {
        return GroupedOpenApi.builder()
                .group("기초정보관리")
                .pathsToMatch(
                        "/mng/**"
                )
                .build();
    }


}