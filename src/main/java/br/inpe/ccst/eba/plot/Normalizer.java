package br.inpe.ccst.eba.plot;

public interface Normalizer {
	String apply(String string);
	
	String apply(String string, boolean joinWords);
}
