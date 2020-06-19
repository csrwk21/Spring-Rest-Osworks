package com.works.osworks.domain.service;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.works.osworks.api.model.Comentario;
import com.works.osworks.domain.exception.EntidadeNaoEncontradaException;
import com.works.osworks.domain.exception.NegocioExcepetion;
import com.works.osworks.domain.model.Cliente;
import com.works.osworks.domain.model.OrdemServico;
import com.works.osworks.domain.model.StatusOrdemServico;
import com.works.osworks.domain.repository.ClienteRepository;
import com.works.osworks.domain.repository.ComentarioRepository;
import com.works.osworks.domain.repository.OrdemServicoRepository;

@Service
public class GestaoOrdemServicoService {
		
	@Autowired
	private OrdemServicoRepository ordemServicoRepository;
	
	@Autowired
	private ComentarioRepository comentarioRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	public OrdemServico criar(OrdemServico ordemServico) {
		
		Cliente cliente = clienteRepository.findById(ordemServico.getCliente().getId())
				.orElseThrow(() -> new NegocioExcepetion("Cliente nao encontrado"));
		
		ordemServico.setCliente(cliente);
		ordemServico.setStatus(StatusOrdemServico.ABERTA);
		ordemServico.setDataAbertura(OffsetDateTime.now());
		
		return ordemServicoRepository.save(ordemServico);
	}
	
	public void finalizar(Long ordemServicoId) {
		OrdemServico ordemServico = buscar(ordemServicoId);
			
		ordemServico.finalizar();
		
		ordemServicoRepository.save(ordemServico);
	}
	
	public Comentario adicionarComentario(Long ordemServicoId, String descricao) {
		OrdemServico ordemServico = buscar(ordemServicoId);
		
		Comentario comentario = new Comentario();
		
		comentario.setDataEnvio(OffsetDateTime.now());
		comentario.setDescricao(descricao);
		comentario.setOrdemServico(ordemServico);
		
		return comentarioRepository.save(comentario);
	}
	
	private OrdemServico buscar(Long ordemServicoId) {
		return ordemServicoRepository.findById(ordemServicoId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("ordem de servico nao encontrada"));
	}
}

