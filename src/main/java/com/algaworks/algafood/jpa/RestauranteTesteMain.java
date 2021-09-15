package com.algaworks.algafood.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;

public class RestauranteTesteMain {

	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE).run(args);

		RestauranteRepository repository = applicationContext.getBean(RestauranteRepository.class);
		List<Restaurante> restaurantes = repository.listar();

		restaurantes.forEach(restaurante -> {
			System.out.printf("+ %s(%s) - frete: R$ %.2f \n", 
					restaurante.getNome(), restaurante.getCozinha().getNome(), restaurante.getTaxaFrete());
		});
	}

}
