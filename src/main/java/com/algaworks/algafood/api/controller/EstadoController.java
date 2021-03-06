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
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import com.algaworks.algafood.domain.service.CadastroEstadoRepository;

@RestController
@RequestMapping("/estados")
public class EstadoController {

	@Autowired
	private EstadoRepository repository;

	@Autowired
	private CadastroEstadoRepository cadastroEstado;

	@GetMapping
	public ResponseEntity<List<Estado>> listar() {
		return ResponseEntity.ok(repository.listar());
	}

	@GetMapping("{estadoId}")
	public ResponseEntity<Estado> buscar(@PathVariable Long estadoId) {

		Estado estado = repository.buscar(estadoId);

		if (estado == null) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(estado);
	}

	@PostMapping
	public ResponseEntity<Estado> salvar(@RequestBody Estado estado) {

		Estado novoEstado = cadastroEstado.salvar(estado);

		return ResponseEntity.status(HttpStatus.CREATED).body(novoEstado);
	}

	@PutMapping("{estadoId}")
	public ResponseEntity<Estado> atualizar(@PathVariable Long estadoId, @RequestBody Estado estadoAtualizado) {

		try {
			Estado estado = cadastroEstado.atualizar(estadoId, estadoAtualizado);
			return ResponseEntity.ok(estado);
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		}

	}

	@DeleteMapping("{estadoId}")
	public ResponseEntity<?> deletar(@PathVariable Long estadoId) {

		try {
			cadastroEstado.deletar(estadoId);
			return ResponseEntity.noContent().build();
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.notFound().build();
		} catch (EntidadeEmUsoException e) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}

	}

}
