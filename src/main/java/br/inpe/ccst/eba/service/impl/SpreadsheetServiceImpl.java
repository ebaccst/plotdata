package br.inpe.ccst.eba.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.inpe.ccst.eba.plot.Spreadsheet;
import br.inpe.ccst.eba.service.SpreadsheetService;
import br.inpe.ccst.eba.validator.SpreadsheetValidator;

@Service("spreadsheetService")
public class SpreadsheetServiceImpl implements SpreadsheetService {
	
	@Autowired
	private SpreadsheetValidator suggestionValidator;
	

	@Override
	public Map<Long, String> validate(Spreadsheet input) {
		return this.suggestionValidator.validate(input);
	}

}
