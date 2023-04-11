package br.com.estudo.mercado.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.estudo.mercado.entidade.Produto;
import br.com.estudo.mercado.repository.ProdutoRepository;
import br.com.estudo.mercado.service.ProdutoService;
import br.com.estudo.mercado.util.MensagemJSF;

@ViewScoped
@Named
public class ProdutoBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Inject 
	private ProdutoRepository produtoRepository;
	
	@Inject
	private ProdutoService produtoService;
	
	@Inject
	private MensagemJSF mensagemJSF;
	
	private Produto produto = new Produto();
	
	private List<Produto> listaProdutos;

	
	//MÉTODOS
	@PostConstruct
	public void init() {
		this.listaProdutos = produtoRepository.listarTodos();
	}
	
	public void gravar() {
		if (produto.getId() != null) {
			mensagemJSF.info("Produto Atualizado com sucesso!");
		}else {
			mensagemJSF.info("Produto Adicionado com sucesso!");

		}
		produtoService.salvar(produto);
		produto = new Produto();
	}
	
	public void atualizarListaProdutos() {
		listaProdutos = produtoRepository.listarTodos();
	}
	
	public List<Produto> getListarProdutos() {
		return this.produtoRepository.listarTodos();
	}
	
	public void excluir(Produto produto) {
		produtoService.excluir(produto);
		mensagemJSF.info("Produto Removido com sucesso!");
	}
	
	public void editar(Produto produto) {
		this.produto = produto;
	}
	
	
	
	
	
	
	
	
	
	//GETs e SETs
	public Produto getProduto() {
		return produto;
	}

	public List<Produto> getListaProdutos() {
		return listaProdutos;
	}


	public void setListaProdutos(List<Produto> listaProdutos) {
		this.listaProdutos = listaProdutos;
	}

	
}
