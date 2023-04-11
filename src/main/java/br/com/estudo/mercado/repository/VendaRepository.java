package br.com.estudo.mercado.repository;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.estudo.mercado.entidade.Venda;

public class VendaRepository implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager manager;
	
	public Venda gravar(Venda venda) {
		return manager.merge(venda);
	}
	
	public Venda ultimoRegistro() {
	    String jpql = "SELECT v FROM Venda v ORDER BY v.id DESC";
	    return manager.createQuery(jpql, Venda.class)
	                 .setMaxResults(1)
	                 .getSingleResult();
	}
	
}
