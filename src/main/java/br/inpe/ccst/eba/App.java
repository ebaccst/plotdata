package br.inpe.ccst.eba;

import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import br.inpe.ccst.eba.plot.PlotOwner;
import br.inpe.ccst.eba.plot.PlotOwnerReader;
import br.inpe.ccst.eba.plot.Spreadsheet;
import br.inpe.ccst.eba.plot.SpreadsheetReader;
import br.inpe.ccst.eba.service.DataService;

@EnableTransactionManagement
@SpringBootApplication
public class App implements ApplicationRunner {
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

	// @Bean
	// CommandLineRunner retrofit(SpreadsheetReader reader, SpreadsheetWriter
	// spreadsheetWriter,
	// @Qualifier("globalNamesResolverValidator") SpreadsheetValidator gnr) {
	// return args -> {
	// System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	// Spreadsheet spreadsheet = reader
	// .read("C:\\Users\\EBA\\Documents\\dev\\git\\plotdata\\src\\test\\resources\\JARAUA_2017_RESUMO.csv");
	//
	// Map<Long, String> errors = gnr.validate(spreadsheet);
	// spreadsheetWriter.write(errors,
	// "C:\\Users\\EBA\\Documents\\dev\\git\\plotdata\\src\\test\\resources\\errors.csv");
	// System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	// };
	// }

	// @Bean
	// CommandLineRunner insert(SpreadsheetReader spreadsheetReader, PlotOwnerReader
	// plotOwnerReader,
	// DataService dataService) {
	// return args -> {
	// System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	// final String spreadsheetFilepath =
	// "C:\\Users\\EBA\\Documents\\dev\\git\\plotdata\\src\\test\\resources\\JARAUA_2017_RESUMO.csv";
	// final String plotOwnerFilepath =
	// "C:\\Users\\EBA\\Documents\\data\\RESUMO_DADOS_PARCEIROS\\responsaveis_parcelas.csv";
	// final String shapefileDirectory =
	// "C:\\Users\\EBA\\Documents\\data\\RESUMO_DADOS_PARCEIROS\\shp\\epsg_5880";
	//
	// Spreadsheet spreadsheet = spreadsheetReader.read(spreadsheetFilepath);
	// PlotOwner plotOwner = plotOwnerReader.read(plotOwnerFilepath,
	// shapefileDirectory);
	//
	// // dataService.validate(spreadsheet);
	// dataService.insert(plotOwner, spreadsheet);
	// System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	// };
	// }

	private static final String ARG_INSERT = "insert";
	private static final String ARG_SPREADSHEET = "spreadsheet";
	private static final String ARG_OWNER = "owner";
	private static final String ARG_SHAPEFILES = "shapefiles";

	@Autowired
	private SpreadsheetReader spreadsheetReader;

	@Autowired
	private PlotOwnerReader plotOwnerReader;

	@Autowired
	private DataService dataService;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		final Set<String> optionNames = args.getOptionNames();
		if (optionNames.isEmpty() || !(optionNames.contains(ARG_SPREADSHEET) && optionNames.contains(ARG_OWNER)
				&& optionNames.contains(ARG_SHAPEFILES))) {
			throw new IllegalArgumentException(String.format("Mandatory arguments: '--%s', '--%s' and '--%s'.",
					ARG_SPREADSHEET, ARG_OWNER, ARG_SHAPEFILES));
		}

		String spreadsheetFilepath = args.getOptionValues(ARG_SPREADSHEET).get(0);
		if (!Paths.get(spreadsheetFilepath).toFile().isFile()) {
			throw new FileNotFoundException(
					String.format("Argument '%s' must be a csv file with plot measurements.", ARG_SPREADSHEET));
		}

		String plotOwnerFilepath = args.getOptionValues(ARG_OWNER).get(0);
		if (!Paths.get(plotOwnerFilepath).toFile().isFile()) {
			throw new FileNotFoundException(
					String.format("Argument '%s' must be a csv file with owner information.", ARG_OWNER));
		}

		String shapefileDirectory = args.getOptionValues(ARG_SHAPEFILES).get(0);
		if (!Paths.get(shapefileDirectory).toFile().isDirectory()) {
			throw new FileNotFoundException(
					String.format("Argument '%s' must be a directory with plot shapefiels.", ARG_SHAPEFILES));
		}

		Spreadsheet spreadsheet = spreadsheetReader.read(spreadsheetFilepath);
		dataService.validate(spreadsheet);

		if (args.containsOption(ARG_INSERT)) {
			List<String> values = args.getOptionValues(ARG_INSERT);
			if (values.size() == 1 && values.get(0).equalsIgnoreCase("true")) {
				PlotOwner plotOwner = plotOwnerReader.read(plotOwnerFilepath, shapefileDirectory);
				dataService.insert(plotOwner, spreadsheet);
			} else {
				throw new IllegalArgumentException(String.format("Argument '%s' must be a boolean, got %s.", ARG_INSERT,
						Arrays.toString(values.toArray())));
			}
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
}
