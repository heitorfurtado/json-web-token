package br.com.heitor.jsonwebtoken.security;

import br.com.heitor.jsonwebtoken.auth.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class CustomWebConfig extends WebSecurityConfigurerAdapter {

    private final PasswordConfig passwordConfig;
    private final CustomUserDetailsService userDetailsService;

    @Autowired
    public CustomWebConfig(PasswordConfig passwordConfig, CustomUserDetailsService userDetailsService) {
        this.passwordConfig = passwordConfig;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.
                userDetailsService(userDetailsService).
                passwordEncoder(passwordConfig.passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/h2-console/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests().antMatchers(HttpMethod.GET,"/api/**").permitAll()
                .anyRequest().authenticated()
                .and().httpBasic();
    }
}
