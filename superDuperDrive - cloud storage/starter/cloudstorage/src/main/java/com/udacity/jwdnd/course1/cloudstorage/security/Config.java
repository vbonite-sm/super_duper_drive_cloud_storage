package com.udacity.jwdnd.course1.cloudstorage.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class Config extends WebSecurityConfigurerAdapter {

    private final Authenticator authenticator;

    public Config(Authenticator authenticator) {
        this.authenticator = authenticator;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/signup", "/css/**", "/js/**").permitAll().anyRequest().authenticated();

        http.formLogin().loginPage("/login").defaultSuccessUrl("/home", true).permitAll();

        http.logout().logoutSuccessUrl("/login?logout").permitAll();
    }

    @Override
    public void configure(AuthenticationManagerBuilder managerBuilder) {
        managerBuilder.authenticationProvider(this.authenticator);
    }
}
