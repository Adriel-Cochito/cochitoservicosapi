package br.edu.infnet.cochitoservicosapi.model.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;

@Entity
public class OrdemServico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "O funcionário é obrigatório.")
    @ManyToOne
    @JoinColumn(name = "funcionario_id")
    private Funcionario funcionario;

    @NotNull(message = "O serviço é obrigatório.")
    @ManyToOne
    @JoinColumn(name = "servico_id")
    private Servico servico;

    @NotNull(message = "A data de criação é obrigatória.")
    @PastOrPresent(message = "A data de criação não pode ser futura.")
    private LocalDateTime dataCriacao;

    @Future(message = "A data de execução deve ser futura.")
    private LocalDateTime dataExecucao;

    @NotNull(message = "O status é obrigatório.")
    @Pattern(regexp = "PENDENTE|EM_ANDAMENTO|CONCLUIDO|CANCELADO", 
             message = "Status deve ser: PENDENTE, EM_ANDAMENTO, CONCLUIDO ou CANCELADO")
    private String status;


    @Override
    public String toString() {
        return String.format("OrdemServico{id=%d, cliente=%s, funcionario=%s, servico=%s, status='%s', dataCriacao=%s}", 
                           id, 
                           funcionario != null ? funcionario.getNome() : "null",
                           servico != null ? servico.getTitulo() : "null", 
                           status, dataCriacao);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataExecucao() {
        return dataExecucao;
    }

    public void setDataExecucao(LocalDateTime dataExecucao) {
        this.dataExecucao = dataExecucao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}