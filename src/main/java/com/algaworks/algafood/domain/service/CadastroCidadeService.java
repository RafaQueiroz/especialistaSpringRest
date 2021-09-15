package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@Service
public class CadastroCidadeService {

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private EstadoRepository estadoRepository;

	public Cidade salvar(Cidade cidade) {

		Long estadoId = cidade.getEstado().getId();
		Estado estado = estadoRepository.buscar(estadoId);
		if (estado == null) {
			throw new EntidadeNaoEncontradaException(String.format("Nenhum estado com id %d foi encontrado", estadoId));
		}

		cidade.setEstado(estado);

		return cidadeRepository.salvar(cidade);
	}

	public Cidade atualizar(Long id, Cidade cidadeAtualizada) {

		Cidade cidade = cidadeRepository.buscar(id);

		if(cidade == null) {
			throw new EntidadeNaoEncontradaException(String.format("Nenhuma cidade com id %d foi encontrada.",id, null));
		}

		Long estadoId = cidadeAtualizada.getEstado().getId();
		Estado estado = estadoRepository.buscar(estadoId);
		if (estado == null) {
			throw new EntidadeNaoEncontradaException(String.format("Nenhum estado com id %d foi encontrado", estadoId));
		}

		cidade.setEstado(estado);
		cidade.setNome(cidadeAtualizada.getNome());

		return cidadeRepository.salvar(cidade);
	}

	public void deletar(Long id) {
		Cidade cidade = cidadeRepository.buscar(id);

		if(cidade == null) {
			throw new EntidadeNaoEncontradaException(String.format("Nenhuma cidade com id %d foi encontrada.",id, null));
		}
		
		try {
			cidadeRepository.remover(cidade);
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeEmUsoException("A cidade não pode ser removido pois está em uso.");
		}

	}

}
