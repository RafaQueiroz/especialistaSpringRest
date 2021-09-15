package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.service.CadastroCidadeService;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private CadastroCidadeService cadastroCidade;

	@RequestMapping
	public ResponseEntity<List<Cidade>> list() {

		List<Cidade> cidades = cidadeRepository.listar();

		return ResponseEntity.ok(cidades);
	}

	@RequestMapping("{cidadeId}")
	public ResponseEntity<Cidade> buscar(@PathVariable Long cidadeId) {

		Cidade cidade = cidadeRepository.buscar(cidadeId);

		if (cidade == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(cidade);
	}


	@PostMapping
	public ResponseEntity<Cidade> salvar(@RequestBody Cidade novaCidade){


		try {
			Cidade cidade = cadastroCidade.salvar(novaCidade);
			return ResponseEntity.ok(cidade);			
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping("{id}")
	public ResponseEntity<Cidade> atualizar(@PathVariable Long id, @RequestBody Cidade cidadeAtualizada){


		try {
			Cidade cidade = cadastroCidade.atualizar(id, cidadeAtualizada);
			return ResponseEntity.ok(cidade);
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		}

	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<?> deletar(@PathVariable Long id){	
		
		try {
			cadastroCidade.deletar(id);
			return ResponseEntity.noContent().build();
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
		
		
	}

}
