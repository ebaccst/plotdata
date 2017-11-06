package br.inpe.ccst.eba.consumer.impl;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GlobalNamesData {

	@JsonProperty("supplied_name_string")
	private String suppliedName;

	@JsonProperty("is_known_name")
	private Boolean isknownName;

	@JsonProperty("status")
	private String status;

	@JsonProperty("results")
	private List<GlobalNames> results;

}
