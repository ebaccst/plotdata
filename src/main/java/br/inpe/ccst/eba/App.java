package br.inpe.ccst.eba;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import br.inpe.ccst.eba.service.CommonNameService;

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

	// @Bean
	// CommandLineRunner repo(FamilyRepository repo) {
	// return args -> {
	// System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	// Family findOne = repo.findOne(1L);
	// System.out.println(findOne.getName());
	//
	// System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	// };
	// }

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
}
