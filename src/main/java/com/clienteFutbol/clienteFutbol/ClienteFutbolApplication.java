package com.clienteFutbol.clienteFutbol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.clienteFutbol"})
public class ClienteFutbolApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClienteFutbolApplication.class, args);
	}

}
