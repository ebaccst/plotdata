package br.inpe.ccst.eba;

import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import br.inpe.ccst.eba.plot.Spreadsheet;
import br.inpe.ccst.eba.plot.SpreadsheetReader;
import br.inpe.ccst.eba.plot.SpreadsheetWriter;
import br.inpe.ccst.eba.validator.SpreadsheetValidator;

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
	// CommandLineRunner showCommonName(SpreadsheetReader reader) {
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
	// CommandLineRunner validator(SpreadsheetReader reader, SpreadsheetValidator
	// suggestionValidator) {
	// return args -> {
	// System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	// Spreadsheet spreadsheet = reader
	// .get("C:\\Users\\EBA\\Documents\\dev\\git\\plotdata\\src\\test\\resources\\JARAUA_2017_RESUMO.csv");
	//
	// Map<Long, String> errors = suggestionValidator.validate(spreadsheet);
	// System.err.println(errors.size());
	// System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	// };
	// }

	// @Bean
	// CommandLineRunner retrofit(GlobalNamesResolverAPI gnr) {
	// return args -> {
	// System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	// Call<GlobalNamesResponse> call = gnr.resolve("Homo sapiens", "json", false,
	// false, false, false, false);
	// System.err.println(call.request().url().toString());
	//
	// Response<GlobalNamesResponse> execute = call.execute();
	// if (execute.isSuccessful()) {
	// System.err.println("SUCCESS");
	// GlobalNamesResponse response = execute.body();
	// response.getData().forEach(data ->
	// data.getResults().forEach(System.out::println));
	// } else {
	// System.err.println("FAIL");
	// }
	//
	// System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	// };
	// }

	@Bean
	CommandLineRunner retrofit(SpreadsheetReader reader, SpreadsheetWriter spreadsheetWriter,
			@Qualifier("globalNamesResolverValidator") SpreadsheetValidator gnr) {
		return args -> {
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
			Spreadsheet spreadsheet = reader
					.get("C:\\Users\\EBA\\Documents\\dev\\git\\plotdata\\src\\test\\resources\\JARAUA_2017_RESUMO.csv");

			Map<Long, String> errors = gnr.validate(spreadsheet);
			spreadsheetWriter.write(errors,
					"C:\\Users\\EBA\\Documents\\dev\\git\\plotdata\\src\\test\\resources\\errors.csv");
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
}
