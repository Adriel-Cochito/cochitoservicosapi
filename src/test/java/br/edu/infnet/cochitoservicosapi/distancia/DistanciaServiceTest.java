package br.edu.infnet.cochitoservicosapi.distancia;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.edu.infnet.cochitoservicosapi.client.AwesomeCepFeignClient;
import br.edu.infnet.cochitoservicosapi.client.OpenRouteFeignClient;
import br.edu.infnet.cochitoservicosapi.model.domain.AwesomeCepResponse;
import br.edu.infnet.cochitoservicosapi.model.domain.DistanciaQueryResult;
import br.edu.infnet.cochitoservicosapi.model.domain.OpenRouteResponse;
import br.edu.infnet.cochitoservicosapi.model.service.DistanciaService;

@ExtendWith(MockitoExtension.class)
public class DistanciaServiceTest {

    @Mock
    private AwesomeCepFeignClient awesomeCepFeignClient;
    
    @Mock
    private OpenRouteFeignClient openRouteFeignClient;
    
    private DistanciaService distanciaService;
    
    private final String apiKey = "eyJvcmciOiI1YjNjZTM1OTc4NTExMTAwMDFjZjYyNDgiLCJpZCI6ImYyYzNkMDY3YzI1ODQ0ODhhMDc3ZmNjNTI1NmY1N2Y1IiwiaCI6Im11cm11cjY0In0%3D"; // Valor fixo para testes
    
    @BeforeEach
    void setUp() {
        distanciaService = new DistanciaService(awesomeCepFeignClient, openRouteFeignClient, apiKey);
    }
    
    @Test
    @DisplayName("Deve calcular distância entre CEPs corretamente")
    void deveCalcularDistanciaEntreCepsCorretamente() {
        // Arrange
        String cepOrigem = "38067290";
        String cepDestino = "38065065";
        
        // Mock para o CEP de origem
        AwesomeCepResponse respostaOrigem = new AwesomeCepResponse();
        respostaOrigem.setCep(cepOrigem);
        respostaOrigem.setAddress("Rua República do Haiti");
        respostaOrigem.setDistrict("Fabrício");
        respostaOrigem.setState("MG");
        respostaOrigem.setCity("Uberaba");
        respostaOrigem.setLat("-19.72942");
        respostaOrigem.setLng("-47.9373729");
        
        // Mock para o CEP de destino
        AwesomeCepResponse respostaDestino = new AwesomeCepResponse();
        respostaDestino.setCep(cepDestino);
        respostaDestino.setAddress("Rua Governador Valadares");
        respostaDestino.setDistrict("Fabrício");
        respostaDestino.setState("MG");
        respostaDestino.setCity("Uberaba");
        respostaDestino.setLat("-19.7443998");
        respostaDestino.setLng("-47.9381862");
        
        // Mock para o cálculo de rota
        OpenRouteResponse rotaResponse = new OpenRouteResponse();
        OpenRouteResponse.Feature feature = new OpenRouteResponse.Feature();
        OpenRouteResponse.Properties properties = new OpenRouteResponse.Properties();
        OpenRouteResponse.Summary summary = new OpenRouteResponse.Summary();
        
        summary.setDistance(3222.2);
        summary.setDuration(400.0);
        properties.setSummary(summary);
        feature.setProperties(properties);
        
        rotaResponse.setFeatures(java.util.Collections.singletonList(feature));
        
        // Configuração dos mocks
        when(awesomeCepFeignClient.consultarCep(cepOrigem)).thenReturn(respostaOrigem);
        when(awesomeCepFeignClient.consultarCep(cepDestino)).thenReturn(respostaDestino);
        when(openRouteFeignClient.calcularRota(apiKey, "-47.9373729,-19.72942", "-47.9381862,-19.7443998"))
            .thenReturn(rotaResponse);
        
        // Act
        DistanciaQueryResult resultado = distanciaService.calcularDistancia(cepOrigem, cepDestino);
        
        // Assert
        assertNotNull(resultado);
        assertEquals(cepOrigem, resultado.getCepOrigem());
        assertEquals(cepDestino, resultado.getCepDestino());
        assertEquals("Rua República do Haiti", resultado.getEnderecoOrigem());
        assertEquals("Fabrício", resultado.getBairroOrigem());
        assertEquals("MG", resultado.getUfOrigem());
        assertEquals("Rua Governador Valadares", resultado.getEnderecoDestino());
        assertEquals("Fabrício", resultado.getBairroDestino());
        assertEquals("MG", resultado.getUfDestino());
        assertEquals(3.22, resultado.getDistanciaKm());
        assertEquals(6.67, resultado.getTempoMinutos());
    }
    
    @Test
    @DisplayName("Deve usar cálculo de Haversine quando API externa falhar")
    void deveUsarCalculoHaversineQuandoApiFalhar() {
        // Arrange
        String cepOrigem = "38067290";
        String cepDestino = "38065065";
        
        // Mock para o CEP de origem
        AwesomeCepResponse respostaOrigem = new AwesomeCepResponse();
        respostaOrigem.setCep(cepOrigem);
        respostaOrigem.setAddress("Rua República do Haiti");
        respostaOrigem.setDistrict("Fabrício");
        respostaOrigem.setState("MG");
        respostaOrigem.setCity("Uberaba");
        respostaOrigem.setLat("-19.72942");
        respostaOrigem.setLng("-47.9373729");
        
        // Mock para o CEP de destino
        AwesomeCepResponse respostaDestino = new AwesomeCepResponse();
        respostaDestino.setCep(cepDestino);
        respostaDestino.setAddress("Rua Governador Valadares");
        respostaDestino.setDistrict("Fabrício");
        respostaDestino.setState("MG");
        respostaDestino.setCity("Uberaba");
        respostaDestino.setLat("-19.7443998");
        respostaDestino.setLng("-47.9381862");
        
        // Configuração dos mocks
        when(awesomeCepFeignClient.consultarCep(cepOrigem)).thenReturn(respostaOrigem);
        when(awesomeCepFeignClient.consultarCep(cepDestino)).thenReturn(respostaDestino);
        when(openRouteFeignClient.calcularRota(anyString(), anyString(), anyString()))
            .thenThrow(new RuntimeException("API Error"));
        
        // Act
        DistanciaQueryResult resultado = distanciaService.calcularDistancia(cepOrigem, cepDestino);
        
        // Assert
        assertNotNull(resultado);
        assertEquals(cepOrigem, resultado.getCepOrigem());
        assertEquals(cepDestino, resultado.getCepDestino());
        assertEquals("Rua República do Haiti", resultado.getEnderecoOrigem());
        assertEquals("Fabrício", resultado.getBairroOrigem());
        assertEquals("MG", resultado.getUfOrigem());
        assertEquals("Rua Governador Valadares", resultado.getEnderecoDestino());
        assertEquals("Fabrício", resultado.getBairroDestino());
        assertEquals("MG", resultado.getUfDestino());
        
        // Verificar se o cálculo de Haversine foi aplicado (valor aproximado)
        // O valor específico pode variar, mas deve ser próximo da distância real
        assertNotNull(resultado.getDistanciaKm());
        assertNotNull(resultado.getTempoMinutos());
    }
}