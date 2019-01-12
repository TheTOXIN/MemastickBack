package com.memastick.backmem.security.config;

import com.memastick.backmem.security.constant.RoleType;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;


@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private static final String RESOURCE_ID = "memastick-resource";

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(RESOURCE_ID);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/hello").anonymous()
            .antMatchers("/invite/registration").anonymous()
            .antMatchers("/registration").anonymous()
            .antMatchers("/password-reset/send").anonymous()
            .antMatchers("/password-reset/take").anonymous()
            .antMatchers("/**").permitAll()
            .antMatchers("/invites").hasAuthority(RoleType.ADMIN.name())
            .antMatchers("/invite/**").hasAnyAuthority(RoleType.ADMIN.name(), RoleType.USER.name())
            .antMatchers("/**").authenticated();
    }

}
