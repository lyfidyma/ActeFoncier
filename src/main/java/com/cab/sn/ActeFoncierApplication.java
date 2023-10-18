package com.cab.sn;


import java.util.Calendar;
import java.util.Date;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@SpringBootApplication
public class ActeFoncierApplication{

	public static void main(String[] args) {
		SpringApplication.run(ActeFoncierApplication.class, args);
		System.out.println(Calendar.getInstance().get(Calendar.YEAR));
	}

	
	@Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
