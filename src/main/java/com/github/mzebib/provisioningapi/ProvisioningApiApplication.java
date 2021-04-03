package com.github.mzebib.provisioningapi;

import com.github.mzebib.provisioningapi.util.ProvConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author mzebib
 */
@SpringBootApplication
@ComponentScan(basePackages = ProvConst.BASE_PACKAGE)
public class ProvisioningApiApplication {

    @Bean
    public Logger logger() {
        return LoggerFactory.getLogger(ProvisioningApiApplication.class);
    }

	public static void main(String[] args) {
		SpringApplication.run(ProvisioningApiApplication.class, args);
	}
}
