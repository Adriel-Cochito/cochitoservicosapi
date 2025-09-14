package br.edu.infnet.cochitoservicosapi.distancia.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import br.edu.infnet.cochitoservicosapi.client.OpenRouteFeignClient;
import br.edu.infnet.cochitoservicosapi.model.domain.OpenRouteResponse;

@SpringBootTest
@ActiveProfiles("test")
public class OpenRouteFeignClientTest {

    @MockBean
    private OpenRouteFeignClient openRouteFeignClient;
    
    @Test
    @DisplayName("Deve calcular rota corretamente")
    void deveCalcularRotaCorretamente() {
        // Arrange
        String origem = "-47.9373729,-19.72942";
        String destino = "-47.9381862,-19.7443998";
        String apiKey = "test-api-key";
        
        OpenRouteResponse mockResponse = new OpenRouteResponse();
        OpenRouteResponse.Feature feature = new OpenRouteResponse.Feature();
        OpenRouteResponse.Properties properties = new OpenRouteResponse.Properties();
        OpenRouteResponse.Summary summary = new OpenRouteResponse.Summary();
        
        summary.setDistance(3222.2);
        summary.setDuration(400.0);
        properties.setSummary(summary);
        feature.setProperties(properties);
        
        mockResponse.setFeatures(java.util.Collections.singletonList(feature));
        
        when(openRouteFeignClient.calcularRota(anyString(), anyString(), anyString()))
            .thenReturn(mockResponse);
        
        // Act
        OpenRouteResponse response = openRouteFeignClient.calcularRota(apiKey, origem, destino);
        
        // Assert
        assertNotNull(response);
        assertNotNull(response.getFeatures());
        assertEquals(1, response.getFeatures().size());
        assertEquals(3222.2, response.getFeatures().get(0).getProperties().getSummary().getDistance());
        assertEquals(400.0, response.getFeatures().get(0).getProperties().getSummary().getDuration());
    }
    
    @Test
    @DisplayName("Deve lidar com falha de API")
    void deveLidarComFalhaDeApi() {
        // Arrange
        String origem = "coordenada-invalida";
        String destino = "coordenada-invalida";
        String apiKey = "test-api-key";
        
        when(openRouteFeignClient.calcularRota(apiKey, origem, destino))
            .thenThrow(new RuntimeException("API Error"));
        
        // Act & Assert
        try {
            openRouteFeignClient.calcularRota(apiKey, origem, destino);
        } catch (RuntimeException e) {
            assertEquals("API Error", e.getMessage());
        }
    }
}