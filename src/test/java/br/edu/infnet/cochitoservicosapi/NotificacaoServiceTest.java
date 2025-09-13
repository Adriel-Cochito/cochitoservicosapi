package br.edu.infnet.cochitoservicosapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.edu.infnet.cochitoservicosapi.model.domain.Funcionario;
import br.edu.infnet.cochitoservicosapi.model.domain.ItemServico;
import br.edu.infnet.cochitoservicosapi.model.domain.OrdemServico;
import br.edu.infnet.cochitoservicosapi.model.domain.Servico;

public class NotificacaoServiceTest {
	private NotificacaoService notificacaoService;
	private Funcionario funcionarioAtivo;
	private Funcionario funcionarioInativo;
	private OrdemServico ordemServico;
	private Servico servico;
	private ItemServico itemServico;

	@BeforeEach
	void setUp() {
		notificacaoService = new NotificacaoService();

		// Funcionário ativo para testes
		funcionarioAtivo = new Funcionario();
		funcionarioAtivo.setNome("Carlos Silva");
		funcionarioAtivo.setEmail("carlos.silva@cochito.com");
		funcionarioAtivo.setAtivo(true);

		// Funcionário inativo para testes
		funcionarioInativo = new Funcionario();
		funcionarioInativo.setNome("Maria Santos");
		funcionarioInativo.setEmail("maria.santos@cochito.com");
		funcionarioInativo.setAtivo(false);

		// Serviço para o item
		servico = new Servico();
		servico.setTitulo("Consultoria em Infraestrutura");
		servico.setDescricao("Análise e otimização de infraestrutura de TI");
		servico.setPreco(new BigDecimal("200.00"));

		// Item de serviço
		itemServico = new ItemServico();
		itemServico.setServico(servico);
		itemServico.setQuantidade(2);

		// Ordem de serviço para testes
		ordemServico = new OrdemServico();
		ordemServico.setId(101);
		ordemServico.setFuncionario(funcionarioAtivo);
		ordemServico.setDataCriacao(LocalDateTime.now());
		ordemServico.setDataExecucao(LocalDateTime.now().plusDays(1));
		ordemServico.setStatus("PENDENTE");

		List<ItemServico> itens = new ArrayList<>();
		itens.add(itemServico);
		ordemServico.setItensServicos(itens);
	}

	@Test
	@DisplayName("CT009 - RF003.1 - Deve criar notificação para OrdemServico válida")
	void deveCriarNotificacao_quandoOrdemServicoValida() {
		// Dado: uma ordem de serviço válida com funcionário ativo
		TipoNotificacao tipo = TipoNotificacao.ORDEM_SERVICO_CRIADA;

		// Quando: criar notificação
		Notificacao notificacao = notificacaoService.criarNotificacao(ordemServico, tipo);

		// Então: a notificação deve ser criada corretamente
		assertNotNull(notificacao, "RF003.1 - Notificação deve ser criada");
		assertEquals("Nova Ordem de Serviço #101", notificacao.getTitulo(),
				"RF003.1 - Título deve conter o ID da ordem");
		assertEquals(funcionarioAtivo, notificacao.getFuncionario(), "RF003.1 - Funcionário deve ser o da ordem");
		assertEquals(tipo, notificacao.getTipoNotificacao(), "RF003.1 - Tipo deve ser o informado");
		assertNotNull(notificacao.getDataCriacao(), "RF003.1 - Data de criação deve estar preenchida");
	}

	@Test
	@DisplayName("CT010 - RF003.2 - Deve falhar ao criar notificação para funcionário inativo")
	void deveFalhar_quandoFuncionarioInativo() {
		// Dado: uma ordem de serviço com funcionário inativo
		ordemServico.setFuncionario(funcionarioInativo);
		TipoNotificacao tipo = TipoNotificacao.ORDEM_SERVICO_CRIADA;

		// Quando/Então: deve lançar exceção
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			notificacaoService.criarNotificacao(ordemServico, tipo);
		});

		assertEquals("Não é possível criar notificação para funcionário inativo", exception.getMessage(),
				"RF003.2 - Mensagem de erro deve ser específica");
	}

	@Test
	@DisplayName("CT011 - RF003.3 - Deve gerar mensagem personalizada com dados da ordem")
	void deveGerarMensagemPersonalizada_comDadosOrdem() {
		// Dado: uma ordem de serviço com dados específicos
		TipoNotificacao tipo = TipoNotificacao.ORDEM_SERVICO_CRIADA;

		// Quando: criar notificação
		Notificacao notificacao = notificacaoService.criarNotificacao(ordemServico, tipo);

		// Então: a mensagem deve conter dados específicos da ordem
		String mensagem = notificacao.getMensagem();
		assertTrue(mensagem.contains("Ordem #101"), "RF003.3 - Mensagem deve conter ID da ordem");
		assertTrue(mensagem.contains("Carlos Silva"), "RF003.3 - Mensagem deve conter nome do funcionário");
		assertTrue(mensagem.contains("PENDENTE"), "RF003.3 - Mensagem deve conter status da ordem");
	}

	@Test
	@DisplayName("CT012 - RF003.4 - Deve marcar notificação como lida corretamente")
	void deveMarcarNotificacaoComoLida() {
		// Dado: uma notificação existente
		TipoNotificacao tipo = TipoNotificacao.ORDEM_SERVICO_CRIADA;
		Notificacao notificacao = notificacaoService.criarNotificacao(ordemServico, tipo);

		// Verificar estado inicial
		assertTrue(notificacao.estaComoNaoLida(), "RF003.4 - Notificação deve começar como não lida");

		// Quando: marcar como lida
		notificacaoService.marcarComoLida(notificacao);

		// Então: deve estar marcada como lida
		assertTrue(notificacao.isLida(), "RF003.4 - Notificação deve estar marcada como lida");
		assertNotNull(notificacao.getDataLeitura(), "RF003.4 - Data de leitura deve estar preenchida");
	}

	@Test
	@DisplayName("CT013 - RF003.5 - Deve contar notificações não lidas por funcionário")
	void deveContarNotificacaoNaoLidas_porFuncionario() {
		// Dado: múltiplas notificações para um funcionário
		notificacaoService.criarNotificacao(ordemServico, TipoNotificacao.ORDEM_SERVICO_CRIADA);
		notificacaoService.criarNotificacao(ordemServico, TipoNotificacao.ORDEM_SERVICO_ATUALIZADA);
		notificacaoService.criarNotificacao(ordemServico, TipoNotificacao.ORDEM_SERVICO_CONCLUIDA);

		// Quando: contar notificações não lidas
		int contador = notificacaoService.contarNotificacaoNaoLidas(funcionarioAtivo);

		// Então: deve retornar 3 notificações não lidas
		assertEquals(3, contador, "RF003.5 - Deve haver 3 notificações não lidas para o funcionário");
	}

	@Test
	@DisplayName("CT014 - RF003.6 - Deve falhar com OrdemServico nula")
	void deveFalhar_quandoOrdemServicoNula() {
		// Dado: ordem de serviço nula
		OrdemServico ordemNula = null;
		TipoNotificacao tipo = TipoNotificacao.ORDEM_SERVICO_CRIADA;

		// Quando/Então: deve lançar exceção
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			notificacaoService.criarNotificacao(ordemNula, tipo);
		});

		assertEquals("OrdemServico não pode ser nula", exception.getMessage(),
				"RF003.6 - Mensagem de erro deve ser específica");
	}

	@Test
	@DisplayName("CT015 - RF003.7 - Deve falhar com tipo de notificação nulo")
	void deveFalhar_quandoTipoNotificacaoNulo() {
		// Dado: tipo de notificação nulo
		TipoNotificacao tipoNulo = null;

		// Quando/Então: deve lançar exceção
		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
			notificacaoService.criarNotificacao(ordemServico, tipoNulo);
		});

		assertEquals("TipoNotificacao não pode ser nulo", exception.getMessage(),
				"RF003.7 - Mensagem de erro deve ser específica");
	}
}
