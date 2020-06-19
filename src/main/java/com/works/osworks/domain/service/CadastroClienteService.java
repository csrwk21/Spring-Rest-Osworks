package com.works.osworks.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.works.osworks.domain.exception.NegocioExcepetion;
import com.works.osworks.domain.model.Cliente;
import com.works.osworks.domain.repository.ClienteRepository;

@Service
public class CadastroClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	public Cliente savarCliente(Cliente cliente) {
		
		Cliente clieteExistente = clienteRepository.findByEmail(cliente.getEmail());
		
		if(clieteExistente != null && !clieteExistente.equals(cliente)) {
			throw  new NegocioExcepetion("ja existe um cliente com esse email.");
		}
		
		return clienteRepository.save(cliente);
	}
	
	public void excluir(Long clientId) {
		clienteRepository.deleteById(clientId);
	}

}
