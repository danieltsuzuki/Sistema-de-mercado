package br.com.estudo.mercado.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.context.RequestContext;

import br.com.estudo.mercado.Exception.CarrinhoException;
import br.com.estudo.mercado.entidade.Cliente;
import br.com.estudo.mercado.entidade.Produto;
import br.com.estudo.mercado.entidade.Venda;
import br.com.estudo.mercado.repository.ClienteRepository;
import br.com.estudo.mercado.repository.ProdutoRepository;
import br.com.estudo.mercado.repository.VendaRepository;
import br.com.estudo.mercado.service.VendaService;
import br.com.estudo.mercado.util.MensagemJSF;

@Named
@ViewScoped
public class VendaBean implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Inject
	private ClienteRepository clienteRepository;
	
	@Inject
	private ProdutoRepository produtoRepository;
	
	@Inject
	private VendaService vendaService;
	
	@Inject
	private VendaRepository vendaRepository;
	
	@Inject
	private MensagemJSF mensagemJSF;
	
	private List<Cliente> listaDeClientes;
	
	private List<Produto> listaDeProdutos;
	
	private List<Produto> carrinho = new ArrayList<Produto>();
	
	private Produto produto;
	
	private Cliente cliente;
	
	private Venda venda = new Venda();
	
	private BigDecimal total = new BigDecimal("0");
	
	private Long produtoIdSelecionado;
	
	private Long clienteIdSelecionado;
	
	//Métodos
	@PostConstruct
	public void init() {
		listaDeClientes = clienteRepository.listarTodos();
		listaDeProdutos = produtoRepository.listarTodos();
	}

	public void selecionarCliente() {
		
		Cliente clienteSelecionado = clienteRepository.buscarPorId(clienteIdSelecionado);
	
		venda.setCliente(clienteSelecionado);
		
	}
	
	public void selecionarProduto() {
		Produto produtoSelecionado = produtoRepository.buscarPorId(produtoIdSelecionado);
		setCarrinho(produtoSelecionado);
		this.calcularTotal(produtoSelecionado.getPreco());
		
		if (carrinho.size() == 0) {
			mensagemJSF.info("Nenhum produto selecionado");
		}
		System.out.println(carrinho);
	}
	
	
	public void calcularTotal(BigDecimal valor) {
		setTotal(total.add(valor));
	}
	
	public void removerDoCarrinho(Produto produto) {
		setTotal(total.subtract(produto.getPreco()));
		carrinho.remove(produto);
    	mensagemJSF.info("Item removido!");
	}
	
	public String finalizarVenda(){
	    venda.setProdutos(carrinho);
	    
	    if (venda.getCliente() == null) {
	    	throw new CarrinhoException("O cliente é obrigatório");
	    }
	    if (venda.getProdutos().size() == 0) {
	    	throw new CarrinhoException("O carrinho não pode estar vazio");
	    }
	    vendaService.salvar(venda);
//	    mensagemJSF.info("Venda concluída!");
	    
		resetarVenda();
		
		return "resumo?faces-redirect=true";
	}
	
	public void limparCarrinho() {
		resetarVenda();
	    mensagemJSF.info("Carrinho esvaziado!");
	}
	
	public Venda getUltimaVenda() {
		return vendaRepository.ultimoRegistro();
	}
	
	public BigDecimal getUltimaVendaTotal() {
		Venda venda = vendaRepository.ultimoRegistro();
		BigDecimal resumoTotal = new BigDecimal("0");
		
		List<Produto> produtos = venda.getProdutos();
		
		for (Produto produto : produtos) {
			resumoTotal = resumoTotal.add(produto.getPreco());
		}
		
		
		mensagemJSF.info("Venda realizada com sucesso!");
		RequestContext.getCurrentInstance().update("formularioUm:mensagem");
	    
		return resumoTotal;
	}
	
	//APAGA TODOS OS ITENS DA TABELA E ZERA O VALOR DA COMPRA
	public void resetarVenda() {
		carrinho = new ArrayList<Produto>();
		venda = new Venda();
		total = new BigDecimal("0");
	}
	
	
	//GETs e SETs
	public Cliente getCliente() {
		return cliente;
	}
	
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Cliente> getListaDeClientes() {
		return listaDeClientes;
	}
	
	public void setListaDeClientes(List<Cliente> listaDeClientes) {
		this.listaDeClientes = listaDeClientes;
	}

	public Produto getProduto() {
		return produto;
	}


	public void setProduto(Produto produto) {
		this.produto = produto;
	}


	public List<Produto> getCarrinho() {
		return carrinho;
	}

	public void setCarrinho(Produto produto) {
		this.carrinho.add(produto);
	}

	public List<Produto> getListaDeProdutos() {
		return listaDeProdutos;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public Venda getVenda() {
		return venda;
	}

	public Long getProdutoIdSelecionado() {
		return produtoIdSelecionado;
	}

	public void setProdutoIdSelecionado(Long produtoIdSelecionado) {
		this.produtoIdSelecionado = produtoIdSelecionado;
	}

	public Long getClienteIdSelecionado() {
		return clienteIdSelecionado;
	}

	public void setClienteIdSelecionado(Long clienteIdSelecionado) {
		this.clienteIdSelecionado = clienteIdSelecionado;
	}

	
}
