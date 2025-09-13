package br.edu.infnet.cochitoservicosapi.model.domain;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ItemServico {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "servico_id")
	private Servico servico;

	private Integer quantidade;

	public BigDecimal calcularSubTotal() {

		if (quantidade == null || quantidade <= 0) {
			return BigDecimal.ZERO;
		}
		
		if(servico == null) {
	        return BigDecimal.ZERO;
	    }
	    
	    BigDecimal preco = servico.getPreco();
	    if(preco == null) {
	        return BigDecimal.ZERO;
	    }

	    return preco.multiply(new BigDecimal(quantidade));
	}

	public Servico getServico() {
		return servico;
	}

	public void setServico(Servico servico) {
		this.servico = servico;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

}
