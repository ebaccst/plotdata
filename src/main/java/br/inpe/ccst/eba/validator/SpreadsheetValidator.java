package br.inpe.ccst.eba.validator;

import java.util.Map;

import br.inpe.ccst.eba.plot.Spreadsheet;

public interface SpreadsheetValidator {

	Map<Long, String> validate(Spreadsheet input);

}
