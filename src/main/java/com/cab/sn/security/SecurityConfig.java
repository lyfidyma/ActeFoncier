package com.cab.sn.security;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


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
		httpSecurity
			.formLogin(formLogin->formLogin
				.loginPage("/login")
				.defaultSuccessUrl("/index", true)
				.permitAll())
		
	        .logout((logout)->logout 
	        		.invalidateHttpSession(true)
	        		.clearAuthentication(true)
	        		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
	        		.logoutSuccessUrl("/login?logout")
	        		.permitAll());
			
		httpSecurity.anonymous().disable();
		//httpSecurity.authorizeHttpRequests(authorizeRequests->authorizeRequests.requestMatchers("/webjars/**").permitAll());
		httpSecurity.authorizeHttpRequests(authorizeRequests->authorizeRequests.requestMatchers("https://maxcdn.bootstrapcdn.com/**").permitAll());
		httpSecurity.authorizeHttpRequests(authorizeRequests->authorizeRequests.requestMatchers("/img/**").permitAll());
		//httpSecurity.authorizeHttpRequests(authorizeRequests->authorizeRequests.anyRequest().authenticated());
		httpSecurity.authorizeHttpRequests(authorizeRequests->authorizeRequests.requestMatchers("/user/**").hasRole("USER"));
		httpSecurity.authorizeHttpRequests(authorizeRequests->authorizeRequests.requestMatchers("/admin/**").hasRole("ADMIN"));
		httpSecurity.authorizeHttpRequests(authorizeRequests->authorizeRequests
				.anyRequest()			
				.authenticated())
				.csrf(AbstractHttpConfigurer::disable);
		httpSecurity.exceptionHandling(Customizer.withDefaults())
		.exceptionHandling();
		
	
		return httpSecurity.build();
}

	
}
