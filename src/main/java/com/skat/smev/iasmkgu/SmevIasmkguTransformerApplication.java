package com.skat.smev.iasmkgu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class SmevIasmkguTransformerApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(SmevIasmkguTransformerApplication.class, args);
	}

//	@Override
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//		return application.sources(SmevSnilsTransformerApplication.class);
//	}
//
//	public static void main(String[] args) throws Exception {
//		SpringApplication.run(SmevSnilsTransformerApplication.class, args);
//	}
}
