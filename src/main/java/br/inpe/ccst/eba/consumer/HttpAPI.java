package br.inpe.ccst.eba.consumer;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public interface HttpAPI {
	default Retrofit retrofit(String url) {
		OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
		return new Retrofit.Builder()
				.addConverterFactory(JacksonConverterFactory.create())
				.baseUrl(url)
				.client(httpClient.build())
				.build();
	}

	GlobalNamesResolverAPI globalNamesResolver();
}
