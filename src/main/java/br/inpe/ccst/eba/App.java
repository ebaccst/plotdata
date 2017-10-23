package br.inpe.ccst.eba;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import br.inpe.ccst.eba.service.CommonNameService;
import br.inpe.ccst.eba.service.MeasurementsService;
import br.inpe.ccst.eba.service.TaxonomyService;

@SpringBootApplication
public class App {
	@Bean
	CommandLineRunner commonName(CommonNameService commonNameService) {
		return args -> {
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			System.out.println(commonNameService.getCountOfRecords());
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		};
	}

	@Bean
	CommandLineRunner taxonomy(TaxonomyService taxonomyService) {
		return args -> {
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			System.out.println(taxonomyService.getCountOfRecords());
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		};
	}

	@Bean
	CommandLineRunner measurements(MeasurementsService measurementsService) {
		return args -> {
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			System.out.println(measurementsService.getCountOfRecords());
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
}
