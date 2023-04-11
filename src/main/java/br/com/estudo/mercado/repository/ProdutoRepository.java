package br.com.estudo.mercado.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.estudo.mercado.entidade.Produto;

public class ProdutoRepository implements Serializable{

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;
	
	public ProdutoRepository() {}
	
	public ProdutoRepository(EntityManager manager) {
		this.manager = manager;
	}
	
	public Produto guardar(Produto produto) {
		return manager.merge(produto);
	}
	
	public Produto buscarPorId(Long id) {
		return manager.find(Produto.class, id);
	}
	
	public void remover(Produto produto) {
		produto = buscarPorId(produto.getId());
		manager.remove(produto);
	}
	
	public List<Produto> listarTodos(){
		String jpql = "From Produto";
		return manager.createQuery(jpql, Produto.class).getResultList();
	}

}
