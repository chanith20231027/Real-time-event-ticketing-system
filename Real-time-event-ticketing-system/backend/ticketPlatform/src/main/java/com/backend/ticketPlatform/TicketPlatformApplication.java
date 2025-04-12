package com.backend.ticketPlatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication


@ComponentScan("com.backend.ticketPlatform")
public class TicketPlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(TicketPlatformApplication.class, args);
	}

}