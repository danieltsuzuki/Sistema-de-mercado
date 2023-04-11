package br.com.estudo.mercado.service;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.estudo.mercado.entidade.Produto;
import br.com.estudo.mercado.repository.ProdutoRepository;
import br.com.estudo.mercado.util.Transacional;

public class ProdutoService implements Serializable{

	private static final long serialVersionUID = 1L;

	@Inject
	private ProdutoRepository produtoRepository;

	@Transacional
	public void salvar(Produto produto) {
		produtoRepository.guardar(produto);
	}
	
	@Transacional
	public void excluir(Produto produto) {
		produtoRepository.remover(produto);
	}
}
