package br.inpe.ccst.eba.plot;

import java.util.Map;

import br.inpe.ccst.eba.domain.impl.Owner;
import lombok.Builder;
import lombok.Getter;

@Builder
public class PlotOwner {
	private Map<String, Owner> owners;

	private Map<String, String> transects;
	
	@Getter
	private String shapefileDirectory;
	
	public Owner owner(String plot) {
		return this.owners.get(plot);
	}

	public String transect(String plot) {
		return this.transects.get(plot);
	}
}
