package br.edu.infnet.cochitoservicosapi.distancia.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import br.edu.infnet.cochitoservicosapi.client.AwesomeCepFeignClient;
import br.edu.infnet.cochitoservicosapi.model.domain.AwesomeCepResponse;

@SpringBootTest
@ActiveProfiles("test")
public class AwesomeCepFeignClientTest {

    @MockBean
    private AwesomeCepFeignClient awesomeCepFeignClient;
    
    @Test
    @DisplayName("Deve retornar informações de CEP corretamente")
    void deveRetornarInformacoesCepCorretamente() {
        // Arrange
        String cep = "38065065";
        AwesomeCepResponse mockResponse = new AwesomeCepResponse();
        mockResponse.setCep(cep);
        mockResponse.setAddress("Rua Governador Valadares");
        mockResponse.setDistrict("Fabrício");
        mockResponse.setState("MG");
        mockResponse.setCity("Uberaba");
        mockResponse.setLat("-19.7443998");
        mockResponse.setLng("-47.9381862");
        
        when(awesomeCepFeignClient.consultarCep(cep)).thenReturn(mockResponse);
        
        // Act
        AwesomeCepResponse response = awesomeCepFeignClient.consultarCep(cep);
        
        // Assert
        assertNotNull(response);
        assertEquals(cep, response.getCep());
        assertEquals("Rua Governador Valadares", response.getAddress());
        assertEquals("Fabrício", response.getDistrict());
        assertEquals("MG", response.getState());
        assertEquals("Uberaba", response.getCity());
        assertEquals("-19.7443998", response.getLat());
        assertEquals("-47.9381862", response.getLng());
    }
    
    @Test
    @DisplayName("Deve lidar com CEP inexistente")
    void deveLidarComCepInexistente() {
        // Arrange
        String cepInexistente = "99999999";
        when(awesomeCepFeignClient.consultarCep(cepInexistente)).thenReturn(null);
        
        // Act
        AwesomeCepResponse response = awesomeCepFeignClient.consultarCep(cepInexistente);
        
        // Assert
        assertEquals(null, response);
    }
}