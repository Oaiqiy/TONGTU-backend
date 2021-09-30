package com.tongtu.tongtu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;


@SpringBootApplication
@EnableConfigurationProperties
@ConfigurationPropertiesScan

public class TongtuApplication {

	public static void main(String[] args) {
		SpringApplication.run(TongtuApplication.class, args);
	}

}
