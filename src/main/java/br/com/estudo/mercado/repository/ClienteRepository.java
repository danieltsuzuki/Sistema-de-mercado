package br.com.estudo.mercado.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.estudo.mercado.entidade.Cliente;

public class ClienteRepository implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;
	
	public ClienteRepository() {}
	
	public ClienteRepository(EntityManager manager) {
		this.manager = manager;
	}
	
	
	public List<Cliente> listarTodos(){
		String jpql = "From Cliente";
		return manager.createQuery(jpql, Cliente.class).getResultList();
	}
	
	public Cliente buscarPorId(Long id) {
		return manager.find(Cliente.class, id);
	}
	
	public Cliente gravar(Cliente cliente) {
		return manager.merge(cliente);
	}
	
	public void remover(Cliente cliente) {
		cliente = buscarPorId(cliente.getId());
		manager.remove(cliente);
	}
	

}
