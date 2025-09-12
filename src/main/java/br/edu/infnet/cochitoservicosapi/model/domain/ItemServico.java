package br.edu.infnet.cochitoservicosapi.model.domain;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

public class ItemServico {

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private OrdemServico ordemServico;
	
	private Servico servico;
	private Integer quantidade;
	public OrdemServico getOrdemServico() {
		return ordemServico;
	}
	public void setOrdemServico(OrdemServico ordemServico) {
		this.ordemServico = ordemServico;
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
