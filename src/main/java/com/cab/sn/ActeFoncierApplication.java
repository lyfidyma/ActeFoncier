package com.cab.sn;

import java.util.Date;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cab.sn.metier.CodeDocumentGenerateur;

@SpringBootApplication
public class ActeFoncierApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(ActeFoncierApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("++++++++++"+CodeDocumentGenerateur.genererCodeDocument(new Date(),"Bail","DAGAHOLPA" ));
		System.out.println(new Date());
	}
	
	@Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
