package br.edu.infnet.cochitoservicosapi;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.edu.infnet.cochitoservicosapi.model.domain.Notificacao;
import br.edu.infnet.cochitoservicosapi.model.domain.TipoNotificacao;

public class NotificacaoTest {

	private Notificacao notificacao;
	private LocalDateTime dataCriacao;
	private String tituloTeste;
	private String mensagemTeste;
	private String funcionarioTeste;

	@BeforeEach
	void setUp() {
		notificacao = new Notificacao();
		dataCriacao = LocalDateTime.now();
		tituloTeste = "Notificação de Teste";
		mensagemTeste = "Esta é uma mensagem de teste";
		funcionarioTeste = "João Silva";

		notificacao.setTitulo(tituloTeste);
		notificacao.setMensagem(mensagemTeste);
		notificacao.setTipoNotificacao(TipoNotificacao.ORDEM_SERVICO_CRIADA);
		notificacao.setFuncionario(funcionarioTeste);
		notificacao.setDataCriacao(dataCriacao);
	}

	@Test
	@DisplayName("Deve inicializar notificação com valores padrão")
	void deveInicializarNotificacao_comValoresPadrao() {
		// Dado/Quando: criar uma nova notificação sem configurar valores
		Notificacao novaNotificacao = new Notificacao();

		// Então: deve ter valores padrão corretos
		assertFalse(novaNotificacao.isLida(), "Notificação deve começar como não lida por padrão");
		assertNull(novaNotificacao.getDataLeitura(), "Data de leitura deve ser nula inicialmente");
		assertNull(novaNotificacao.getTitulo(), "Título deve ser nulo inicialmente");
		assertNull(novaNotificacao.getMensagem(), "Mensagem deve ser nula inicialmente");
		assertNull(novaNotificacao.getTipoNotificacao(), "Tipo deve ser nulo inicialmente");
		assertNull(novaNotificacao.getFuncionario(), "Funcionário deve ser nulo inicialmente");
	}

	@Test
	@DisplayName("Deve permitir definir e obter dados da notificação")
	void devePermitirDefinirEObter_dadosNotificacao() {
		// Dado: dados já configurados no setUp

		// Então: deve permitir obter os dados definidos
		assertEquals(tituloTeste, notificacao.getTitulo(), "Título deve ser o definido");
		assertEquals(mensagemTeste, notificacao.getMensagem(), "Mensagem deve ser a definida");
		assertEquals(TipoNotificacao.ORDEM_SERVICO_CRIADA, notificacao.getTipoNotificacao(),
				"Tipo deve ser o definido");
		assertEquals(funcionarioTeste, notificacao.getFuncionario(), "Funcionário deve ser o definido");
		assertEquals(dataCriacao, notificacao.getDataCriacao(), "Data de criação deve ser a definida");
	}

	@Test
	@DisplayName("Deve marcar como lida e registrar data automaticamente")
	void deveMarcarComoLida_eRegistrarDataAutomaticamente() {
		// Verificar estado inicial
		assertFalse(notificacao.isLida(), "Notificação deve começar como não lida");
		assertNull(notificacao.getDataLeitura(), "Data de leitura deve ser nula inicialmente");
		assertTrue(notificacao.estaComoNaoLida(), "estaComoNaoLida() deve retornar true inicialmente");
		assertFalse(notificacao.foiLida(), "foiLida() deve retornar false inicialmente");

		// Quando: marcar como lida
		notificacao.marcarComoLida();

		// Então: deve estar marcada como lida e registrar data
		assertTrue(notificacao.isLida(), "Notificação deve estar marcada como lida");
		assertNotNull(notificacao.getDataLeitura(), "Data de leitura deve estar preenchida automaticamente");
		assertFalse(notificacao.estaComoNaoLida(), "estaComoNaoLida() deve retornar false");
		assertTrue(notificacao.foiLida(), "foiLida() deve retornar true");
	}

	@Test
	@DisplayName("Deve chamar marcarComoLida múltiplas vezes sem efeitos colaterais")
	void deveSerIdempotente_aoMarcarComoLida() {
		// Quando: marcar como lida
		notificacao.marcarComoLida();
		LocalDateTime primeiraDataLeitura = notificacao.getDataLeitura();

		// Pequena pausa para garantir que o timestamp seria diferente
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
		}

		// Quando: marcar como lida novamente
		notificacao.marcarComoLida();

		// Então: data de leitura não deve mudar
		assertEquals(primeiraDataLeitura, notificacao.getDataLeitura(),
				"Data de leitura não deve mudar ao chamar marcarComoLida múltiplas vezes");
	}

	@Test
	@DisplayName("Deve testar método foiLida em vários cenários")
	void deveRetornarEstadoCorreto_quandoUsarFoiLida() {
		// Cenário 1: Por padrão, não foi lida
		Notificacao notificacao1 = new Notificacao();
		notificacao1.setTitulo(tituloTeste);
		notificacao1.setMensagem(mensagemTeste);
		notificacao1.setTipoNotificacao(TipoNotificacao.ORDEM_SERVICO_CRIADA);
		notificacao1.setFuncionario(funcionarioTeste);
		notificacao1.setDataCriacao(dataCriacao);
		assertFalse(notificacao1.foiLida(), "Inicialmente, foiLida() deve retornar false");

		// Cenário 2: Quando lida mas sem data de leitura (situação anômala)
		Notificacao notificacao2 = new Notificacao();
		notificacao2.setTitulo(tituloTeste);
		notificacao2.setMensagem(mensagemTeste);
		notificacao2.setTipoNotificacao(TipoNotificacao.ORDEM_SERVICO_CRIADA);
		notificacao2.setFuncionario(funcionarioTeste);
		notificacao2.setDataCriacao(dataCriacao);
		notificacao2.setLida(true);
		notificacao2.setDataLeitura(null);
		assertFalse(notificacao2.foiLida(), "Com lida=true mas dataLeitura=null, foiLida() deve retornar false");

		// Cenário 3: Quando marcada como lida normalmente
		Notificacao notificacao3 = new Notificacao();
		notificacao3.setTitulo(tituloTeste);
		notificacao3.setMensagem(mensagemTeste);
		notificacao3.setTipoNotificacao(TipoNotificacao.ORDEM_SERVICO_CRIADA);
		notificacao3.setFuncionario(funcionarioTeste);
		notificacao3.setDataCriacao(dataCriacao);
		notificacao3.marcarComoLida();
		assertTrue(notificacao3.foiLida(), "Após marcarComoLida(), foiLida() deve retornar true");
	}

	@Test
	@DisplayName("Deve gerar resumo correto da notificação")
	void deveGerarResumoCorreto() {
		// Cenário normal
		String resumoEsperado = "Notificacao[tipo=ORDEM_SERVICO_CRIADA, funcionario=João Silva, lida=false]";
		assertEquals(resumoEsperado, notificacao.resumo(), "Resumo deve conter informações corretas");

		// Cenário com funcionário nulo
		notificacao.setFuncionario(null);
		String resumoEsperadoSemFuncionario = "Notificacao[tipo=ORDEM_SERVICO_CRIADA, funcionario=N/A, lida=false]";
		assertEquals(resumoEsperadoSemFuncionario, notificacao.resumo(),
				"Resumo deve tratar funcionário nulo como N/A");
	}

	@Test
	@DisplayName("Deve testar equals e hashCode corretamente")
	void deveCompararNotificacoesCorretamente() {
		// Criar uma notificação igual
		Notificacao notificacaoIgual = new Notificacao();
		notificacaoIgual.setTitulo(tituloTeste);
		notificacaoIgual.setTipoNotificacao(TipoNotificacao.ORDEM_SERVICO_CRIADA);
		notificacaoIgual.setFuncionario(funcionarioTeste);
		notificacaoIgual.setDataCriacao(dataCriacao);

		// Criar uma notificação diferente
		Notificacao notificacaoDiferente = new Notificacao();
		notificacaoDiferente.setTitulo("Outro título");
		notificacaoDiferente.setTipoNotificacao(TipoNotificacao.ORDEM_SERVICO_CONCLUIDA);
		notificacaoDiferente.setFuncionario("Outro funcionário");
		notificacaoDiferente.setDataCriacao(LocalDateTime.now().plusHours(1));

		// Testar equals
		assertEquals(notificacao, notificacao, "Uma notificação deve ser igual a si mesma");
		assertEquals(notificacao, notificacaoIgual, "Notificações com mesmos valores críticos devem ser iguais");
		assertNotEquals(notificacao, notificacaoDiferente, "Notificações diferentes não devem ser iguais");
		assertNotEquals(notificacao, null, "Uma notificação não deve ser igual a null");
		assertNotEquals(notificacao, "Uma string", "Uma notificação não deve ser igual a objetos de outro tipo");

		// Testar hashCode
		assertEquals(notificacao.hashCode(), notificacaoIgual.hashCode(), "Objetos iguais devem ter o mesmo hashCode");
	}

	@Test
	@DisplayName("Deve definir e obter dataLeitura corretamente")
	void deveManipularDataLeitura() {
		// Dado: data de leitura específica
		LocalDateTime dataLeitura = LocalDateTime.now().minusHours(1);

		// Quando: definir a data de leitura manualmente
		notificacao.setDataLeitura(dataLeitura);

		// Então: deve retornar a data definida
		assertEquals(dataLeitura, notificacao.getDataLeitura(),
				"getData() deve retornar a data definida com setDataLeitura()");
	}

	@Test
	@DisplayName("Deve definir e obter estado de leitura manualmente")
	void deveManipularEstadoLeitura() {
		// Quando: definir como lida manualmente (sem chamar marcarComoLida)
		notificacao.setLida(true);

		// Então: isLida deve retornar true, mas dataLeitura permanece null
		assertTrue(notificacao.isLida(), "isLida() deve retornar true após setLida(true)");
		assertNull(notificacao.getDataLeitura(), "setLida(true) não deve alterar dataLeitura automaticamente");
	}
}