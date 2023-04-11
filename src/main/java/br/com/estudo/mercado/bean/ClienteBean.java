package br.com.estudo.mercado.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.estudo.mercado.entidade.Cliente;
import br.com.estudo.mercado.repository.ClienteRepository;
import br.com.estudo.mercado.service.ClienteService;
import br.com.estudo.mercado.util.MensagemJSF;

@ViewScoped
@Named
public class ClienteBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject 
	private ClienteRepository clienteRepository;
	
	@Inject
	private ClienteService clienteService;
	
	@Inject
	private MensagemJSF mensagemJSF;
	
	private Cliente cliente = new Cliente();
	
	private List<Cliente> listaClientes;

	
	//MÉTODOS
	@PostConstruct
	public void init() {
		this.listaClientes = clienteRepository.listarTodos();
	}
	
	public void gravar() {
		if (cliente.getId() != null) {
			mensagemJSF.info("Cliente Atualizado com sucesso!");
		}else {
			mensagemJSF.info("Cliente Adicionado com sucesso!");

		}
		clienteService.salvar(cliente);
		cliente = new Cliente();
		
	}
	
	public void atualizarListaClientes() {
		listaClientes = clienteRepository.listarTodos();
	}
	
	public List<Cliente> getListarClientes() {
		return this.clienteRepository.listarTodos();
	}
	
	public void excluir(Cliente cliente) {
		clienteService.excluir(cliente);
		mensagemJSF.info("Cliente Removido com sucesso!");
	}
	
	public void editar(Cliente cliente) {
		this.cliente = cliente;
	}
	
	
	
	
	
	
	
	
	//GETs e SETs
	public Cliente getCliente() {
		return cliente;
	}
	
	

	public List<Cliente> getListaCliente() {
		return listaClientes;
	}


	public void setListaClientes(List<Cliente> listaClientes) {
		this.listaClientes = listaClientes;
	}
}
