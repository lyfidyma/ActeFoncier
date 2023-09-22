package com.cab.sn.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	@Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsService() {
        
        return new InMemoryUserDetailsManager(
        		User.withUsername("user").password(passwordEncoder.encode("user")).roles("USER").build(),
        		User.withUsername("admin").password(passwordEncoder.encode("admin")).roles("USER","ADMIN").build()
        		);
    }
	
	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.formLogin().loginPage("/login").permitAll();
		httpSecurity.authorizeHttpRequests().requestMatchers("/webjars/**").permitAll();
		httpSecurity.authorizeHttpRequests().requestMatchers("https://maxcdn.bootstrapcdn.com/**").permitAll();
		httpSecurity.authorizeHttpRequests().anyRequest().authenticated();
	
		return httpSecurity.build();
}

	
}