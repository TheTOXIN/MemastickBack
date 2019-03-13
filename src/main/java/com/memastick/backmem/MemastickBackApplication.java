package com.memastick.backmem;

import com.memastick.backmem.main.service.Initializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableScheduling
@SpringBootApplication
public class MemastickBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(MemastickBackApplication.class, args);
	}

	@EventListener
	public void initConstants(ApplicationReadyEvent event) {
		event.getApplicationContext().getBean(Initializer.class).init();
	}

}
