package com.memastick.backmem.security.config;

import com.memastick.backmem.security.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;


@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private static final String GRANT_TYPE_PASSWORD = "password";
    private static final String REFRESH_TOKEN = "refresh_token";
    private static final String SCOPE_READ = "read";
    private static final String SCOPE_WRITE = "write";
    private static final String SCOPE_TRUST = "trust";

    private final PasswordEncoder passwordEncoder;
    private final TokenStore tokenStore;
    private final MyUserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;

    @Value("${oauth.client}") private String oauthClient;
    @Value("${oauth.secret}") private String oauthSecret;
    @Value("${token.access.time}") private int accessTime;
    @Value("${token.refresh.time}") private int refreshTime;

    @Autowired
    public AuthorizationServerConfig(
        PasswordEncoder passwordEncoder,
        TokenStore tokenStore,
        MyUserDetailsService userDetailsService,
        AuthenticationManager authenticationManager
    ) {
        this.passwordEncoder = passwordEncoder;
        this.tokenStore = tokenStore;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
            .withClient(oauthClient)
            .secret(passwordEncoder.encode(oauthClient))
            .authorizedGrantTypes(GRANT_TYPE_PASSWORD, REFRESH_TOKEN)
            .scopes(SCOPE_READ, SCOPE_WRITE, SCOPE_TRUST)
            .accessTokenValiditySeconds(accessTime)
            .refreshTokenValiditySeconds(refreshTime);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
            .tokenStore(this.tokenStore)
            .authenticationManager(this.authenticationManager)
            .userDetailsService(this.userDetailsService);
    }
}
