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
	private final List<Notificacao> notificacoes;

	public NotificacaoService() {
		this.notificacoes = new ArrayList<>();
	}

	// Cria uma nova notificação baseada em uma ordem de serviço.

	// @param ordemServico A ordem de serviço que originou a notificação
	// @param tipo O tipo da notificação
	// @return A notificação criada
	// @throws IllegalArgumentException se a ordem de serviço for nula, o tipo for
	// nulo ou o funcionário estiver inativo

	public Notificacao criarNotificacao(OrdemServico ordemServico, TipoNotificacao tipo) {
		// Validações de entrada consolidadas
		validarParametrosEntrada(ordemServico, tipo);

		Funcionario funcionario = ordemServico.getFuncionario();
		validarFuncionarioAtivo(funcionario);

		// Criar e configurar a notificação
		Notificacao notificacao = construirNotificacao(ordemServico, tipo, funcionario);

		// Adicionar à lista de notificações
		notificacoes.add(notificacao);

		return notificacao;
	}

	// Marca uma notificação como lida.
	// @param notificacao A notificação a ser marcada como lida
	public void marcarComoLida(Notificacao notificacao) {
		if (notificacao != null) {
			notificacao.marcarComoLida();
		}
	}

	// Conta o número de notificações não lidas para um funcionário específico.

	// @param funcionario O funcionário para contar as notificações
	// @return O número de notificações não lidas
	public int contarNotificacaoNaoLidas(String funcionario) {
		if (funcionario == null) {
			return 0;
		}

		return (int) notificacoes.stream().filter(n -> funcionario.equals(n.getFuncionario()))
				.filter(Notificacao::estaComoNaoLida).count();
	}

	// Métodos privados de validação e construção (REFACTOR)

	// Valida os parâmetros de entrada básicos.
	private void validarParametrosEntrada(OrdemServico ordemServico, TipoNotificacao tipo) {
		if (ordemServico == null) {
			throw new IllegalArgumentException("OrdemServico não pode ser nula");
		}

		if (tipo == null) {
			throw new IllegalArgumentException("TipoNotificacao não pode ser nulo");
		}
	}

	// Valida se o funcionário está ativo.
	private void validarFuncionarioAtivo(Funcionario funcionario) {
		if (funcionario == null || !funcionario.isAtivo()) {
			throw new IllegalArgumentException("Não é possível criar notificação para funcionário inativo");
		}
	}

	// Constrói uma nova instância de notificação com todos os dados necessários.
	private Notificacao construirNotificacao(OrdemServico ordemServico, TipoNotificacao tipo, Funcionario funcionario) {
		Notificacao notificacao = new Notificacao();
		notificacao.setTitulo(gerarTitulo(ordemServico, tipo));
		notificacao.setMensagem(gerarMensagem(ordemServico, tipo));
		notificacao.setTipoNotificacao(tipo);
		notificacao.setFuncionario(funcionario.getNome());
		notificacao.setDataCriacao(LocalDateTime.now());
		return notificacao;
	}

	// Gera o título da notificação baseado na ordem de serviço e tipo. Template
	// Method Pattern para facilitar extensibilidade.
	private String gerarTitulo(OrdemServico ordemServico, TipoNotificacao tipo) {
		Integer idOrdem = ordemServico.getId();

		switch (tipo) {
		case ORDEM_SERVICO_CRIADA:
			return String.format("Nova Ordem de Serviço #%d", idOrdem);
		case ORDEM_SERVICO_ATUALIZADA:
			return String.format("Ordem de Serviço #%d Atualizada", idOrdem);
		case ORDEM_SERVICO_CONCLUIDA:
			return String.format("Ordem de Serviço #%d Concluída", idOrdem);
		default:
			return String.format("Notificação - Ordem #%d", idOrdem);
		}
	}

	// Gera a mensagem da notificação com dados específicos da ordem. Builder
	// pattern para mensagens contextuais flexíveis.
	private String gerarMensagem(OrdemServico ordemServico, TipoNotificacao tipo) {
		NotificacaoMessageBuilder builder = new NotificacaoMessageBuilder()
				.comFuncionario(ordemServico.getFuncionario().getNome()).comOrdem(ordemServico.getId())
				.comStatus(ordemServico.getStatus());

		switch (tipo) {
		case ORDEM_SERVICO_CRIADA:
			return builder.paraOrdemCriada();
		case ORDEM_SERVICO_ATUALIZADA:
			return builder.paraOrdemAtualizada();
		case ORDEM_SERVICO_CONCLUIDA:
			return builder.paraOrdemConcluida();
		default:
			return builder.paraNotificacaoGenerica();
		}
	}

	// Builder interno para construção flexível de mensagens de notificação.
	private static class NotificacaoMessageBuilder {
		private String nomeFuncionario;
		private Integer idOrdem;
		private String status;

		public NotificacaoMessageBuilder comFuncionario(String nome) {
			this.nomeFuncionario = nome;
			return this;
		}

		public NotificacaoMessageBuilder comOrdem(Integer id) {
			this.idOrdem = id;
			return this;
		}

		public NotificacaoMessageBuilder comStatus(String status) {
			this.status = status;
			return this;
		}

		public String paraOrdemCriada() {
			return String.format("Olá %s, uma nova Ordem #%d foi criada para você. Status atual: %s.", nomeFuncionario,
					idOrdem, status);
		}

		public String paraOrdemAtualizada() {
			return String.format("Olá %s, a Ordem #%d foi atualizada. Status atual: %s.", nomeFuncionario, idOrdem,
					status);
		}

		public String paraOrdemConcluida() {
			return String.format("Olá %s, a Ordem #%d foi concluída. Status final: %s.", nomeFuncionario, idOrdem,
					status);
		}

		public String paraNotificacaoGenerica() {
			return String.format("Notificação sobre a Ordem #%d para %s. Status: %s.", idOrdem, nomeFuncionario,
					status);
		}
	}
}