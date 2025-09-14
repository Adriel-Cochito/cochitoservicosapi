package br.edu.infnet.cochitoservicosapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CochitoservicosapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(CochitoservicosapiApplication.class, args);
	}

}
