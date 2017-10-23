package br.inpe.ccst.eba.plot;

import java.util.Set;

public interface SpreadsheetReader {
	Set<Spreadsheet> get(String filepath);
}
