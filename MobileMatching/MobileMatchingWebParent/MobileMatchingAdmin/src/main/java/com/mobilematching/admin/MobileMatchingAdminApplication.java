package com.mobilematching.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
@SpringBootApplication
@EntityScan(basePackages = "com.mobilematching.common.entity")


public class MobileMatchingAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(MobileMatchingAdminApplication.class, args);
	}

}
