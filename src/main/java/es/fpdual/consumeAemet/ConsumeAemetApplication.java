package es.fpdual.consumeAemet;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class ConsumeAemetApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(ConsumeAemetApplication.class, args);
	}

}
