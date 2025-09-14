package br.edu.infnet.cochitoservicosapi.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.edu.infnet.cochitoservicosapi.model.domain.AwesomeCepResponse;

@FeignClient(name = "awesomeCepClient", url = "${api.awesomeapi.url}")
public interface AwesomeCepFeignClient {
    
    @GetMapping("/json/{cep}")
    AwesomeCepResponse consultarCep(@PathVariable("cep") String cep);
}