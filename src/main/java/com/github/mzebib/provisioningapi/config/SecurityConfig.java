package com.github.mzebib.provisioningapi.config;

import com.github.mzebib.provisioningapi.security.Role;
import com.github.mzebib.provisioningapi.util.ProvConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author mzebib
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${server.contextPath}")
    private String baseUri;
    @Value("${security.user.name}")
    private String adminUser;
    @Value("${security.user.password}")
    private String adminPwd;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(baseUri + "/**").permitAll()
                .antMatchers(ProvConst.URI_AUDITEVENTS).hasRole(Role.ADMIN.name())
                .antMatchers(ProvConst.URI_BEANS).hasRole(Role.ADMIN.name())
                .antMatchers(ProvConst.URI_CONFIGPROPS).hasRole(Role.ADMIN.name())
                .antMatchers(ProvConst.URI_ENV).hasRole(Role.ADMIN.name())
                .antMatchers(ProvConst.URI_INFO).hasRole(Role.ADMIN.name())
                .antMatchers(ProvConst.URI_HEALTH).hasRole(Role.ADMIN.name())
                .antMatchers(ProvConst.URI_LOGGERS).hasRole(Role.ADMIN.name())
                .antMatchers(ProvConst.URI_MAPPINGS).hasRole(Role.ADMIN.name())
                .antMatchers(ProvConst.URI_MAPPINGS).hasRole(Role.ADMIN.name())
                .antMatchers(ProvConst.URI_METRICS).hasRole(Role.ADMIN.name())
                .antMatchers(ProvConst.URI_TRACE).hasRole(Role.ADMIN.name())
                .and().httpBasic()
                .and().csrf().disable();
    }

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser(adminUser).password(adminPwd).roles(Role.ADMIN.name());
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(HttpMethod.POST, ProvConst.URI_AUTH + "/**");
        web.ignoring().antMatchers(HttpMethod.GET, ProvConst.URI_AUTH + "/**");
        web.ignoring().antMatchers(HttpMethod.PUT, ProvConst.URI_AUTH + "/**");
        web.ignoring().antMatchers(
                "/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**"
        );
    }
}