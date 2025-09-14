package br.edu.infnet.cochitoservicosapi.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import br.edu.infnet.cochitoservicosapi.model.domain.OpenRouteResponse;

@FeignClient(name = "openRouteClient", url = "${api.openroute.url}")
public interface OpenRouteFeignClient {
    
    @GetMapping("/v2/directions/driving-car")
    OpenRouteResponse calcularRota(
        @RequestParam("api_key") String apiKey,
        @RequestParam("start") String origem,
        @RequestParam("end") String destino
    );
}