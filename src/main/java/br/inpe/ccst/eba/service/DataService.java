package br.inpe.ccst.eba.service;

import br.inpe.ccst.eba.plot.PlotOwner;
import br.inpe.ccst.eba.plot.Spreadsheet;

public interface DataService {
	
	void validate(Spreadsheet spreadsheet);
	
	void insert(PlotOwner plotOwner, Spreadsheet spreadsheet);

}
