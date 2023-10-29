package com.cab.sn.security;


import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import com.cab.sn.metier.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableWebSecurity
public class SecurityConfig
{
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserDetail userDetail;
    
    @Autowired
    public void configureInMemoryAuthentication( AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
        .withUser("admin")
        .password(passwordEncoder.encode("admin"))
        .roles("USER", "ADMIN");
    }
    
    @Autowired
    public void configureUserDetailAuthentication( AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetail);
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity httpSecurity) throws Exception {
        httpSecurity.formLogin(formLogin -> (formLogin
        		.loginPage("/login")
        		.defaultSuccessUrl("/index", true))
        		.permitAll())
        		.logout(logout -> logout.invalidateHttpSession(true)
        		.clearAuthentication(true).logoutRequestMatcher((RequestMatcher)new AntPathRequestMatcher("/logout"))
        		.logoutSuccessUrl("/login?logout")
        		.permitAll());
        httpSecurity.anonymous((anonymous) ->
			anonymous.disable());
        httpSecurity.authorizeHttpRequests(authorizeRequests -> (authorizeRequests
        		.requestMatchers("https://maxcdn.bootstrapcdn.com/**" ))
        		.permitAll());
        httpSecurity.authorizeHttpRequests(authorizeRequests -> (authorizeRequests
        		.requestMatchers("/img/**")).permitAll());
        httpSecurity.authorizeHttpRequests(authorizeRequests -> (authorizeRequests
        		.requestMatchers("/user/**" )).hasRole("USER"));
        httpSecurity.authorizeHttpRequests(authorizeRequests -> (authorizeRequests
        		.requestMatchers("/admin/**" )).hasRole("ADMIN"));
        httpSecurity.authorizeHttpRequests(authorizeRequests -> (authorizeRequests
        		.anyRequest()).authenticated()).csrf(AbstractHttpConfigurer::disable);
        httpSecurity
        .exceptionHandling(Customizer.withDefaults());
        
        return httpSecurity.build();
    }
}