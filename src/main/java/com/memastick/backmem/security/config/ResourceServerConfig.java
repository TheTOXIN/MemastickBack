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
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(RESOURCE_ID);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .mvcMatchers("/test").hasAuthority(RoleType.ADMIN.name())
            .mvcMatchers("/memes/ban/**").hasAuthority(RoleType.ADMIN.name())
            .mvcMatchers("/translator/day").hasAuthority(RoleType.ADMIN.name())
            .mvcMatchers("/translator/admin").hasAuthority(RoleType.ADMIN.name())
            .mvcMatchers("/clear-votes/**").hasAuthority(RoleType.ADMIN.name())
            .mvcMatchers("/security/ban/**").hasAuthority(RoleType.ADMIN.name())
            .mvcMatchers("/memotype/create").hasAuthority(RoleType.ADMIN.name())
            .mvcMatchers("/memotype-set/create").hasAuthority(RoleType.ADMIN.name())
            .mvcMatchers("/meme-coins/transaction/**").hasAuthority(RoleType.ADMIN.name())
            .mvcMatchers("/next-evolve").hasAuthority(RoleType.ADMIN.name())
            .mvcMatchers("/select-evolve").hasAuthority(RoleType.ADMIN.name())
            .mvcMatchers("/send-allowance").hasAuthority(RoleType.ADMIN.name())
            .mvcMatchers("/admin-message").hasAuthority(RoleType.ADMIN.name())
            .mvcMatchers("/migrate").hasAuthority(RoleType.ADMIN.name())
            .mvcMatchers("/invites").hasAuthority(RoleType.ADMIN.name())
            .mvcMatchers("/memes/img/**").anonymous()
            .mvcMatchers("/hello").anonymous()
            .mvcMatchers("/memetick-avatars/download/**").anonymous()
            .mvcMatchers("/invite/registration").anonymous()
            .mvcMatchers("/registration").anonymous()
            .mvcMatchers("/password-reset/send").anonymous()
            .mvcMatchers("/password-reset/take").anonymous()
            .mvcMatchers("/socket/**").anonymous()
            .mvcMatchers("/donate/read").anonymous()
            .mvcMatchers("/donate-ratings/read").anonymous()
            .mvcMatchers("/donate-messages/read").anonymous()
            .mvcMatchers("/memes/random").anonymous()
            .mvcMatchers("/**").authenticated();
    }
}
