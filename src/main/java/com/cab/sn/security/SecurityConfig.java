package com.cab.sn.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;

import com.cab.sn.metier.UserDetail;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    UserDetail userDetail;

    @Autowired
    private ApplicationContext applicationContext;
    
	/*
	 * @Autowired public void configureInMemoryAuthentication(
	 * AuthenticationManagerBuilder auth) throws Exception {
	 * auth.inMemoryAuthentication() .withUser("admin")
	 * .password(passwordEncoder.encode("admin")) .roles("USER", "ADMIN"); }
	 */
    
    @Autowired
    public void configureUserDetailAuthentication( AuthenticationManagerBuilder auth) throws Exception {
    	auth.inMemoryAuthentication()
        .withUser("admin")
        .password("$2a$10$au1KPeWof3cvsjfKUGpWMOcssSQz2BFVYKzcar3hIOZF0RuJm9f.q")
        .roles("USER", "ADMIN");
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