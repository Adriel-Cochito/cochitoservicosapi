package br.edu.infnet.cochitoservicosapi.model.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
public class Funcionario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotBlank(message = "O nome é obrigatório.")
	@Size(min = 3, max = 50, message = "O nome deve estar entre 3 e 50 caracteres.")
	private String nome;

	@NotBlank(message = "O e-mail é obrigatório.")
	@Email(message = "O e-mail está inválido.")
	private String email;
	
	@NotBlank(message = "O telefone é obrigatório")
	@Pattern(regexp = "\\(\\d{2}\\) \\d{4,5}-\\d{4}", 
	         message = "Telefone deve estar no formato (XX) XXXXX-XXXX ou (XX) XXXX-XXXX")
	private String telefone;


	@NotNull(message = "O salário é obrigatório.")
	@Min(value = 0, message = "O salário não pode ser negativo.")
	private double salario;
	private boolean ativo;



	// TODO construtor padrão do vendedor para marcar o vendedor como ativo

	// TODO construtor com os campos de pessoa e os demais campos de vendedor

	@Override
	public String toString() {
		return String.format("Funcionario{%s, matricula=%d, salario=%.2f, ehAtivo=%s, %s", super.toString(),
				salario, ativo ? "ativo" : "inativo");
	}



	public double getSalario() {
		return salario;
	}

	public void setSalario(double salario) {
		this.salario = salario;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getTelefone() {
		return telefone;
	}


	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}
	

}