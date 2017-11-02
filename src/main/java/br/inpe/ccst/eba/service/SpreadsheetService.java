package br.inpe.ccst.eba.service;

import java.util.Map;

import br.inpe.ccst.eba.plot.Spreadsheet;

public interface SpreadsheetService {

	Map<Long, String> validate(Spreadsheet input);
	
}
