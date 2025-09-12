package br.edu.infnet.cochitoservicosapi.model.domain;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Servico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "O título é obrigatório.")
    @Size(min = 3, max = 100, message = "Título deve ter entre 3 e 100 caracteres.")
    private String titulo;

    @NotNull(message = "O preço é obrigatório.")
    @Min(value = 0, message = "O preço não pode ser negativo.")
    private BigDecimal preco;

    @NotBlank(message = "A descrição é obrigatória.")
    @Size(min = 10, max = 500, message = "Descrição deve ter entre 10 e 500 caracteres.")
    private String descricao;

    @Override
    public String toString() {
        return String.format("Servico{id=%d, titulo='%s', preco=%.2f, descricao='%s'}", 
                           id, titulo, preco, descricao);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}