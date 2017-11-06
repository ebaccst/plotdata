package br.inpe.ccst.eba.consumer.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.inpe.ccst.eba.consumer.GlobalNamesResolverAPI;
import br.inpe.ccst.eba.consumer.HttpAPI;
import retrofit2.Retrofit;

@Configuration("httpAPI")
public class HttpAPIImpl implements HttpAPI {
	@Value("${app.globalnamesresolver.url}")
	private String globalNamesResolverAPI;

	@Override
	@Bean
	public GlobalNamesResolverAPI globalNamesResolver() {
		Retrofit retrofit = this.retrofit(globalNamesResolverAPI);
		return retrofit.create(GlobalNamesResolverAPI.class);
	}
}
