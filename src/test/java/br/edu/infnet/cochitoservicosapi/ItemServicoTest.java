package br.edu.infnet.cochitoservicosapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import br.edu.infnet.cochitoservicosapi.model.domain.ItemServico;
import br.edu.infnet.cochitoservicosapi.model.domain.Servico;

public class ItemServicoTest {
	private Servico servico;

	@BeforeEach
	void setUp() {
		// Servico de exemplo para reutilização nos testes
		servico = new Servico();
		servico.setTitulo("Consultoria em TI");
		servico.setDescricao("Consultoria especializada em tecnologia");
		servico.setPreco(new BigDecimal("150.00"));
	}

	@Test
	@DisplayName("CT001 - RF001.1 - Deve calcular subtotal para um item válido")
	void deveCalcularSubTotal_quandoItemServicoValido() {
		// Dado: um item de serviço com serviço e quantidade válidos
		ItemServico itemServico = new ItemServico();
		itemServico.setServico(servico);
		itemServico.setQuantidade(3); // 3 * 150.00 = 450.00

		BigDecimal subTotalEsperado = new BigDecimal("450.00");

		// Quando: chamar o método calcularSubTotal
		BigDecimal subTotalCalculado = itemServico.calcularSubTotal();

		// Então: o resultado do subTotal será o valor esperado
		assertEquals(subTotalEsperado, subTotalCalculado, "RF001.1 - O subTotal deve ser 450.00 para esse item");

		// Validações adicionais
		assertEquals("Consultoria em TI", servico.getTitulo(), "O título do serviço deve ser 'Consultoria em TI'");
		assertEquals(new BigDecimal("150.00"), servico.getPreco(), "O preço do serviço deve ser 150.00");
		assertEquals(3, itemServico.getQuantidade(), "A quantidade do item deve ser 3");
		assertNotNull(itemServico.getServico());
	}

	@Test
	@DisplayName("CT002 - RF001.2 - Deve retornar zero quando quantidade for zero")
	void deveRetornarZero_quandoQuantidadeForZero() {
		// Dado: um item de serviço com quantidade zero
		ItemServico itemServico = new ItemServico();
		itemServico.setServico(servico);
		itemServico.setQuantidade(0);

		BigDecimal subTotalEsperado = BigDecimal.ZERO;

		// Quando: chamar o método calcularSubTotal
		BigDecimal subTotalCalculado = itemServico.calcularSubTotal();

		// Então: o resultado do subTotal será zero
		assertEquals(subTotalEsperado, subTotalCalculado,
				"RF001.2 - O subTotal deve ser zero quando a quantidade estiver zerada");
	}

	@Test
	@DisplayName("CT003 - RF001.3 - Deve retornar zero quando quantidade for negativa")
	void deveRetornarZero_quandoQuantidadeForNegativa() {
		// Dado: um item de serviço com quantidade negativa
		ItemServico itemServico = new ItemServico();
		itemServico.setServico(servico);
		itemServico.setQuantidade(-2);

		BigDecimal subTotalEsperado = BigDecimal.ZERO;

		// Quando: chamar o método calcularSubTotal
		BigDecimal subTotalCalculado = itemServico.calcularSubTotal();

		// Então: o resultado do subTotal será zero
		assertEquals(subTotalEsperado, subTotalCalculado,
				"RF001.3 - O subTotal deve ser zero quando a quantidade estiver negativa");
	}

	@Test
	@DisplayName("CT004 - RF001.4 - Deve retornar zero quando serviço for nulo")
	void deveRetornarZero_quandoServicoNulo() {
		// Dado: um item de serviço com serviço nulo
		ItemServico itemServico = new ItemServico();
		itemServico.setServico(null);
		itemServico.setQuantidade(5);

		BigDecimal subTotalEsperado = BigDecimal.ZERO;

		// Quando: chamar o método calcularSubTotal
		BigDecimal subTotalCalculado = itemServico.calcularSubTotal();

		// Então: o resultado do subTotal será zero
		assertEquals(subTotalEsperado, subTotalCalculado,
				"RF001.4 - O subTotal deve ser zero quando o serviço estiver nulo");
	}

	@Test
	@DisplayName("CT005 - RF001.5 - Deve retornar zero quando preço do serviço for nulo")
	void deveRetornarZero_quandoPrecoServicoNulo() {
		// Dado: um item de serviço com preço do serviço nulo
		Servico servicoSemPreco = new Servico();
		servicoSemPreco.setTitulo("Serviço sem preço");
		servicoSemPreco.setDescricao("Serviço de teste");
		servicoSemPreco.setPreco(null);

		ItemServico itemServico = new ItemServico();
		itemServico.setServico(servicoSemPreco);
		itemServico.setQuantidade(2);

		BigDecimal subTotalEsperado = BigDecimal.ZERO;

		// Quando: chamar o método calcularSubTotal
		BigDecimal subTotalCalculado = itemServico.calcularSubTotal();

		// Então: o resultado do subTotal será zero
		assertEquals(subTotalEsperado, subTotalCalculado,
				"RF001.5 - O subTotal deve ser zero quando o preço do serviço estiver nulo");
	}
}
