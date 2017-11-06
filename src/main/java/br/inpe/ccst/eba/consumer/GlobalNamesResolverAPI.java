package br.inpe.ccst.eba.consumer;

import br.inpe.ccst.eba.consumer.impl.GlobalNamesResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GlobalNamesResolverAPI {

	@GET("name_resolvers")
	Call<GlobalNamesResponse> resolve(
			@Query("names") String names,
			@Query("format") String format,
			@Query("resolve_once") Boolean resolveOnce,
			@Query("with_context") Boolean withContext, 
			@Query("best_match_only") Boolean bestMatchOnly, 
			@Query("header_only") Boolean headerOnly, 
			@Query("preferred_data_sources") Boolean preferredDataSources);
}
