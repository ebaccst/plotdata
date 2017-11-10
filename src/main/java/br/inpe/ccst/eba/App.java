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
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableTransactionManagement
@SpringBootApplication
public class App implements ApplicationRunner {
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
		if (args.containsOption(ARG_INSERT)) {
			List<String> values = args.getOptionValues(ARG_INSERT);
			if (values.size() == 1 && values.get(0).equalsIgnoreCase("true")) {
				log.info("Inserting spreadsheet in database...");
				PlotOwner plotOwner = plotOwnerReader.read(plotOwnerFilepath, shapefileDirectory);
				dataService.insert(plotOwner, spreadsheet);
				dataService.updateAGB();
			} else {
				throw new IllegalArgumentException(String.format("Argument '%s' must be a boolean, got %s.", ARG_INSERT,
						Arrays.toString(values.toArray())));
			}
		} else {
			log.info("Validating spreadsheet...");
			dataService.validate(spreadsheet);
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
}
