package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@Service
public class CadastroEstadoRepository {

	@Autowired
	private EstadoRepository estadoRespository;

	public Estado salvar(Estado estado) {
		return estadoRespository.salvar(estado);
	}

	public Estado atualizar(Long estadoId, Estado estadoAtualizado) {

		Estado estado = estadoRespository.buscar(estadoId);

		if (estado == null) {
			throw new EntidadeNaoEncontradaException(String.format("Não existe nenhum estado com o id", estadoId));
		}

		estado.setNome(estadoAtualizado.getNome());

		return estadoRespository.salvar(estado);
	}

	public void deletar(Long estadoId) {

		try {
			estadoRespository.remover(estadoId);
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(String.format("Não existe nenhum estado com o id", estadoId));
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("O Estado de id %d, não pode ser removido pois está sendo utilizado.", estadoId));
		}

	}

}
