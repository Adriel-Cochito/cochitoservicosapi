package br.edu.infnet.cochitoservicosapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.edu.infnet.cochitoservicosapi.model.domain.Funcionario;

public class NotificacaoTest {

	private Funcionario funcionario;

	@BeforeEach
	void setUp() {
		// Funcionário de exemplo para reutilização nos testes
		funcionario = new Funcionario();
		funcionario.setNome("João Silva");
		funcionario.setEmail("joao.silva@cochito.com");
		funcionario.setAtivo(true);
	}

	@Test
	@DisplayName("CT006 - RF002.1 - Deve inicializar notificação com valores padrão")
	void deveInicializarNotificacao_comValoresPadrao() {
		// Dado/Quando: criar uma nova notificação
		Notificacao notificacao = new Notificacao();

		// Então: deve ter valores padrão corretos
		assertFalse(notificacao.isLida(), "RF002.1 - Notificação deve começar como não lida por padrão");
		assertNull(notificacao.getDataLeitura(), "RF002.1 - Data de leitura deve ser nula inicialmente");
		assertNull(notificacao.getTitulo(), "RF002.1 - Título deve ser nulo inicialmente");
		assertNull(notificacao.getMensagem(), "RF002.1 - Mensagem deve ser nula inicialmente");
		assertNull(notificacao.getTipoNotificacao(), "RF002.1 - Tipo deve ser nulo inicialmente");
		assertNull(notificacao.getFuncionario(), "RF002.1 - Funcionário deve ser nulo inicialmente");
	}

	@Test
	@DisplayName("CT007 - RF002.2 - Deve permitir definir e obter dados da notificação")
	void devePermitirDefinirEObter_dadosNotificacao() {
		// Dado: dados válidos para notificação
		String titulo = "Nova Ordem de Serviço";
		String mensagem = "Você foi designado para executar uma nova ordem";
		TipoNotificacao tipo = TipoNotificacao.ORDEM_SERVICO_CRIADA;
		LocalDateTime dataCriacao = LocalDateTime.now();

		// Quando: definir dados na notificação
		Notificacao notificacao = new Notificacao();
		notificacao.setTitulo(titulo);
		notificacao.setMensagem(mensagem);
		notificacao.setTipoNotificacao(tipo);
		notificacao.setFuncionario(funcionario);
		notificacao.setDataCriacao(dataCriacao);

		// Então: deve permitir obter os dados definidos
		assertEquals(titulo, notificacao.getTitulo(), "RF002.2 - Título deve ser o definido");
		assertEquals(mensagem, notificacao.getMensagem(), "RF002.2 - Mensagem deve ser a definida");
		assertEquals(tipo, notificacao.getTipoNotificacao(), "RF002.2 - Tipo deve ser o definido");
		assertEquals(funcionario, notificacao.getFuncionario(), "RF002.2 - Funcionário deve ser o definido");
		assertEquals(dataCriacao, notificacao.getDataCriacao(), "RF002.2 - Data de criação deve ser a definida");
	}

	@Test
	@DisplayName("CT008 - RF002.3 - Deve marcar como lida e registrar data automaticamente")
	void deveMarcarComoLida_eRegistrarDataAutomaticamente() {
		// Dado: uma notificação não lida
		Notificacao notificacao = new Notificacao();
		notificacao.setTitulo("Teste");
		notificacao.setMensagem("Mensagem de teste");
		notificacao.setTipoNotificacao(TipoNotificacao.ORDEM_SERVICO_CRIADA);
		notificacao.setFuncionario(funcionario);
		notificacao.setDataCriacao(LocalDateTime.now());

		// Verificar estado inicial
		assertFalse(notificacao.isLida(), "RF002.3 - Notificação deve começar como não lida");
		assertNull(notificacao.getDataLeitura(), "RF002.3 - Data de leitura deve ser nula inicialmente");

		// Quando: marcar como lida
		notificacao.marcarComoLida();

		// Então: deve estar marcada como lida e registrar data
		assertTrue(notificacao.isLida(), "RF002.3 - Notificação deve estar marcada como lida");
		assertNotNull(notificacao.getDataLeitura(), "RF002.3 - Data de leitura deve estar preenchida automaticamente");
		assertFalse(notificacao.estaComoNaoLida(), "RF002.3 - estaComoNaoLida() deve retornar false");
	}
}
