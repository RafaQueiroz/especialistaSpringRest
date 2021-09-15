package com.algaworks.algafood.jpa;

import java.util.List;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

import com.algaworks.algafood.AlgafoodApiApplication;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;

public class InsercaoCozinhaMain {
	
	public static void main(String[] args) {
		ApplicationContext applicationContext = new SpringApplicationBuilder(AlgafoodApiApplication.class)
				.web(WebApplicationType.NONE)
				.run(args);
		
		CozinhaRepository cadastroCozinha = applicationContext.getBean(CozinhaRepository.class);
		
		Cozinha cozinhaBr = new Cozinha();
		cozinhaBr.setNome("Brasileira");
		
		Cozinha cozinhaTurca = new Cozinha();
		cozinhaTurca.setNome("Turca");
		
		cadastroCozinha.salvar(cozinhaBr);
		cadastroCozinha.salvar(cozinhaTurca);
			
		List<Cozinha> cozinhas = cadastroCozinha.listar();
		
		cozinhas.forEach(cozinha -> {
			System.out.print(cozinha.getNome()+ "\n");
		});
	}

}
