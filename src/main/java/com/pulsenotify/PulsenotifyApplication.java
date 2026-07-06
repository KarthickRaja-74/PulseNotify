package com.pulsenotify;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PulsenotifyApplication {

	public static void main(String[] args) {
		SpringApplication.run(PulsenotifyApplication.class, args);
		System.out.println("PulseNotify Started");
	}

}
