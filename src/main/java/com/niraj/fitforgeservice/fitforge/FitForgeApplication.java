package com.niraj.fitforgeservice.fitforge;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.crypto.SecretKey;
import java.util.Base64;

@SpringBootApplication
public class FitForgeApplication {

	public static void main(String[] args) {
		SpringApplication.run(FitForgeApplication.class, args);
	}

}
