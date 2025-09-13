package br.edu.infnet.cochitoservicosapi.model.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.edu.infnet.cochitoservicosapi.model.domain.Funcionario;
import br.edu.infnet.cochitoservicosapi.model.domain.Notificacao;
import br.edu.infnet.cochitoservicosapi.model.domain.OrdemServico;
import br.edu.infnet.cochitoservicosapi.model.domain.TipoNotificacao;

public class NotificacaoService {
	
	// Lista em memória para simular um repositório de notificações
    private List<Notificacao> notificacoes;
    
    public NotificacaoService() {
        this.notificacoes = new ArrayList<>();
    }
    

    public Notificacao criarNotificacao(OrdemServico ordemServico, TipoNotificacao tipo) {
        // Validações de entrada
        if (ordemServico == null) {
            throw new IllegalArgumentException("OrdemServico não pode ser nula");
        }
        
        if (tipo == null) {
            throw new IllegalArgumentException("TipoNotificacao não pode ser nulo");
        }
        
        Funcionario funcionario = ordemServico.getFuncionario();
        if (funcionario == null || !funcionario.isAtivo()) {
            throw new IllegalArgumentException("Não é possível criar notificação para funcionário inativo");
        }
        
        // Criar a notificação
        Notificacao notificacao = new Notificacao();
        notificacao.setTitulo(gerarTitulo(ordemServico, tipo));
        notificacao.setMensagem(gerarMensagem(ordemServico, tipo));
        notificacao.setTipoNotificacao(tipo);
        notificacao.setFuncionario(funcionario);
        notificacao.setDataCriacao(LocalDateTime.now());
        
        // Adicionar à lista de notificações
        notificacoes.add(notificacao);
        
        return notificacao;
    }
    

    public void marcarComoLida(Notificacao notificacao) {
        if (notificacao != null) {
            notificacao.marcarComoLida();
        }
    }
    

    public int contarNotificacaoNaoLidas(Funcionario funcionario) {
        if (funcionario == null) {
            return 0;
        }
        
        return (int) notificacoes.stream()
                .filter(n -> n.getFuncionario().equals(funcionario))
                .filter(Notificacao::estaComoNaoLida)
                .count();
    }
    

    private String gerarTitulo(OrdemServico ordemServico, TipoNotificacao tipo) {
        switch (tipo) {
            case ORDEM_SERVICO_CRIADA:
                return "Nova Ordem de Serviço #" + ordemServico.getId();
            case ORDEM_SERVICO_ATUALIZADA:
                return "Ordem de Serviço #" + ordemServico.getId() + " Atualizada";
            case ORDEM_SERVICO_CONCLUIDA:
                return "Ordem de Serviço #" + ordemServico.getId() + " Concluída";
            default:
                return "Notificação - Ordem #" + ordemServico.getId();
        }
    }
    

    private String gerarMensagem(OrdemServico ordemServico, TipoNotificacao tipo) {
        String nomeFuncionario = ordemServico.getFuncionario().getNome();
        String status = ordemServico.getStatus();
        Integer idOrdem = ordemServico.getId();
        
        switch (tipo) {
            case ORDEM_SERVICO_CRIADA:
                return String.format("Olá %s, uma nova Ordem #%d foi criada para você. Status atual: %s.",
                        nomeFuncionario, idOrdem, status);
            case ORDEM_SERVICO_ATUALIZADA:
                return String.format("Olá %s, a Ordem #%d foi atualizada. Status atual: %s.",
                        nomeFuncionario, idOrdem, status);
            case ORDEM_SERVICO_CONCLUIDA:
                return String.format("Olá %s, a Ordem #%d foi concluída. Status final: %s.",
                        nomeFuncionario, idOrdem, status);
            default:
                return String.format("Notificação sobre a Ordem #%d para %s. Status: %s.",
                        idOrdem, nomeFuncionario, status);
        }
    }
}
