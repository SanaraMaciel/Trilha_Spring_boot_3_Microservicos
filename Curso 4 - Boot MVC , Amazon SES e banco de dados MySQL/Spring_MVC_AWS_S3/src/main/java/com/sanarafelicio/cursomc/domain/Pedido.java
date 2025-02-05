package com.sanarafelicio.cursomc.domain;

import java.io.Serializable;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Pedido implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)	
	private Integer id;
	
	//anotação para formatar a data
	@JsonFormat(pattern="dd/MM/yyyy HH:mm")
	private Date instante;
	
	//Associação de pedido com pagamento
	//ligar o pagamento com o pedido @OneToOne
	//permite que o pagamento seja serializado pelo pedido @JsonManagedReference
	@OneToOne(cascade=CascadeType.ALL,mappedBy="pedido")
	private Pagamento pagamento; 
	
	//Associação de cliente com o pedido
	@ManyToOne
	@JoinColumn(name="cliente_id")
	private Cliente cliente;
	
	//Associação de pedido com o endereço
	@ManyToOne
	@JoinColumn(name="endereco_de_entrega_id")
	private Endereco enderecoDeEntrega;
	
	//Associação de Pedido com ItemPedido cria um conjunto para o pedido saber quais produtos ele tem e nao tem item repetido
	//o mapeamento esta id.pedido pq o itemPedido tem um obj id que é um tipo de itemPedidoPK
	@OneToMany(mappedBy="id.pedido")
	private Set<ItemPedido> itens = new HashSet<>();
	
	//constructors
	public Pedido() {
		
	}

	public Pedido(Integer id, Date instante, Cliente cliente, Endereco enderecoDeEntrega) {
		super();
		this.id = id;
		this.instante = instante;
		this.cliente = cliente;
		this.enderecoDeEntrega = enderecoDeEntrega;
	}

	//método para calcular o total do pedido com base no subTotal de itemPedido
	public double getValorTotal() {
		double soma = 0;
		for(ItemPedido ip : itens) {
			soma = soma + ip.getSubTotal();
		}
		return soma;
		
		// ou usar lambda no lugar do for como abaixo: 
		// itens.stream().mapToDouble(x -> x.getSubtotal()).sum();
		
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getInstante() {
		return instante;
	}

	public void setInstante(Date instante) {
		this.instante = instante;
	}

	public Pagamento getPagamento() {
		return pagamento;
	}

	public void setPagamento(Pagamento pagamento) {
		this.pagamento = pagamento;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Endereco getEnderecoDeEntrega() {
		return enderecoDeEntrega;
	}

	public void setEnderecoDeEntrega(Endereco enderecoDeEntrega) {
		this.enderecoDeEntrega = enderecoDeEntrega;
	}

	public Set<ItemPedido> getItens() {
		return itens;
	}

	public void setItens(Set<ItemPedido> itens) {
		this.itens = itens;
	}
	
	//hashCode and equals
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pedido other = (Pedido) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	//método To String 	
	@Override
	public String toString() {
		
		//para formatar em dinheiro getCurency istance uma instancia de dinheiro
		NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));	
		
		//Formatando a data para padrão brasileiro
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");		
		
		StringBuilder builder = new StringBuilder();
		builder.append("Pedido Número:");
		builder.append(getId());
		builder.append(", Instante:");
		builder.append(sdf.format(getInstante()));
		builder.append(", Cliente:");
		builder.append(getCliente().getNome());
		builder.append(", Situação do Pagamento: ");
		builder.append(getPagamento().getEstado().getDescricao());
		builder.append("\nDetalhes:\n");		
		for(ItemPedido ip : getItens()) {
			builder.append(ip.toString());
		}
		builder.append("Valor Total:");
		builder.append(nf.format(getValorTotal()));
		
		
		
		
		return builder.toString();
	}
	
	
	
	
	

}
