package br.inpe.ccst.eba.plot;

public interface PlotOwnerReader {

	PlotOwner read(String plotOwnerFilepath, String shapefileDirectory);

}
