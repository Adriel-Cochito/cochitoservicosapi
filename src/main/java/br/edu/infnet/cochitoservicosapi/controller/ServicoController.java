package br.edu.infnet.cochitoservicosapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.edu.infnet.cochitoservicosapi.model.domain.DistanciaQueryResult;
import br.edu.infnet.cochitoservicosapi.model.service.DistanciaService;

@RestController
@RequestMapping("/api/servicos")
public class ServicoController {
    
    private final DistanciaService distanciaService;
    
    public ServicoController(DistanciaService distanciaService) {
        this.distanciaService = distanciaService;
    }
    
    @GetMapping("/distancia")
    public ResponseEntity<DistanciaQueryResult> calcularDistanciaEntreCeps(
            @RequestParam String cepOrigem, 
            @RequestParam String cepDestino) {
        
        DistanciaQueryResult resultado = distanciaService.calcularDistancia(cepOrigem, cepDestino);
        
        return ResponseEntity.ok(resultado);
    }
}