package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.service.CadastroCozinhaService;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

	@Autowired
	private CozinhaRepository repository;
	
	@Autowired
	private CadastroCozinhaService cadastroCozinha;
	
	@GetMapping
	public ResponseEntity<List<Cozinha>> listar(){
		
		List<Cozinha> cozinhas = repository.listar();
		
		return ResponseEntity.ok(cozinhas);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Cozinha> buscar(@PathVariable Long id) {
		
		Cozinha cozinha = repository.buscar(id);
		
		if( cozinha == null) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(cozinha);
	}
	
	@PostMapping
	public ResponseEntity<Cozinha> salvar(@RequestBody Cozinha cozinha){
		
		Cozinha cozinhaSalva = cadastroCozinha.salvar(cozinha);
		
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(cozinhaSalva);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Cozinha> atualizar(@PathVariable Long id, @RequestBody Cozinha cozinhaAtualizada){
	
		Cozinha cozinha = repository.buscar(id);
		
		if( cozinha == null) {
			return ResponseEntity.notFound().build();
		}
		
		cozinha.setNome(cozinhaAtualizada.getNome());
		cozinha = repository.salvar(cozinha);
		
		return ResponseEntity.ok(cozinha);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Cozinha> remover(@PathVariable Long id){
	
		try {
			cadastroCozinha.excluir(id);
			return ResponseEntity.noContent().build();
			
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
		
	}
	
}
