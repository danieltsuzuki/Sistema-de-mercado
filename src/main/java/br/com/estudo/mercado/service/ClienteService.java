package br.com.estudo.mercado.service;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.estudo.mercado.entidade.Cliente;
import br.com.estudo.mercado.repository.ClienteRepository;
import br.com.estudo.mercado.util.Transacional;

public class ClienteService implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private ClienteRepository clienteRepository;
	
	@Transacional
	public void salvar(Cliente cliente) {
		clienteRepository.gravar(cliente);
	}
	
	@Transacional
	public void excluir(Cliente cliente) {
		clienteRepository.remover(cliente);
	}
}
