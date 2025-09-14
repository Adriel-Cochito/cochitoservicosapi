package br.edu.infnet.cochitoservicosapi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import br.edu.infnet.cochitoservicosapi.client.AwesomeCepFeignClient;
import br.edu.infnet.cochitoservicosapi.client.OpenRouteFeignClient;

@SpringBootTest
class CochitoservicosapiApplicationTests {

    @MockBean
    private AwesomeCepFeignClient awesomeCepFeignClient;
    
    @MockBean
    private OpenRouteFeignClient openRouteFeignClient;
    
	@Test
	void contextLoads() {
	}

}
