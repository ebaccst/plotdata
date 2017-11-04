package br.inpe.ccst.eba;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {
	// @Bean
	// CommandLineRunner commonName(CommonNameService commonNameService) {
	// return args -> {
	// System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	// System.out.println(commonNameService.getCountOfRecords());
	// System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	// };
	// }

	// @Bean
	// CommandLineRunner teste(SpreadsheetReader reader) {
	// return args -> {
	// System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	// Spreadsheet spreadsheet = reader
	// .get("C:\\Users\\EBA\\Documents\\dev\\git\\plotdata\\src\\test\\resources\\JARAUA_2017_RESUMO.csv");
	//
	// spreadsheet.each(rec -> {
	// System.err.println(rec.getCommonName());
	// });
	// System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	// };
	// }

	// @Bean
	// CommandLineRunner teste(SpreadsheetReader reader, SpreadsheetValidator
	// suggestionValidator) {
	// return args -> {
	// System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	// Spreadsheet spreadsheet =
	// reader.get("C:\\Users\\EBA\\Documents\\dev\\git\\plotdata\\src\\test\\resources\\JARAUA_2017_RESUMO.csv");
	//
	// Map<Long, String> errors = suggestionValidator.validate(spreadsheet);
	// System.err.println(errors.size());
	// System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	// };
	// }

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
}
