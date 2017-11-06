package br.inpe.ccst.eba.plot;

import java.util.Map;

public interface SpreadsheetWriter {
	void write(Map<Long, String> errors, String filepath);
}
