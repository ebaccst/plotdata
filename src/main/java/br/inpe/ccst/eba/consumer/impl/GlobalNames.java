package br.inpe.ccst.eba.consumer.impl;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@NoArgsConstructor
@Slf4j
@JsonIgnoreProperties(ignoreUnknown = true)
public class GlobalNames {
	@JsonProperty("canonical_form")
	private String canonicalForm;

	@JsonProperty("classification_path")
	private String classificationPath;

	@JsonProperty("classification_path_ids")
	private String classificationPathIds;

	@JsonProperty("classification_path_ranks")
	private String classificationPathRanks;

	@JsonProperty("data_source_id")
	private String dataSourceId;

	@JsonProperty("data_source_title")
	private String dataSourceTitle;

	@JsonProperty("edit_distance")
	private String editDistance;

	@JsonProperty("gni_uuid")
	private String gniUuid;

	@JsonProperty("imported_at")
	private String importedAt;

	@JsonProperty("local_id")
	private String localId;

	@JsonProperty("match_type")
	private String matchType;

	@JsonProperty("match_value")
	private String matchValue;

	@JsonProperty("name_string")
	private String nameString;

	@JsonProperty("prescore")
	private String preScore;

	@JsonProperty("score")
	private String score;

	@JsonProperty("taxon_id")
	private String taxonId;

	@JsonProperty("url")
	private String url;

	@Override
	public String toString() {
		try {
			return new ObjectMapper()
					.writer()
					.withDefaultPrettyPrinter()
					.writeValueAsString(this);
		} catch (JsonProcessingException e) {
			log.error(e.getMessage());
			return super.toString();
		}
	}
}
