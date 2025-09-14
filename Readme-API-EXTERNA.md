# Planejamento para Feature 2 - Cálculo de Distância entre CEPs

## Visão Geral

Este documento descreve o planejamento da Feature 02 para o projeto CochitoServicosAPI, que consiste na integração com APIs externas para cálculo de distância entre CEPs. A feature utilizará duas APIs gratuitas:

1. **AwesomeAPI CEP** - Para obtenção de coordenadas geográficas (latitude/longitude) a partir de CEPs
2. **OpenRouteService** - Para cálculo de distância real entre coordenadas, considerando rotas de ruas e estradas

## Estrutura e Classes Necessárias

### Controller
**DistanciaController**
- Recebe requisições HTTP para calcular distância entre CEPs
- Delega processamento para o service
- Retorna o resultado consolidado (DistanciaQueryResult)

### Service
**DistanciaService**
- Orquestra chamadas aos clients
- Processa os resultados e realiza cálculos 
- Preenche o objeto DistanciaQueryResult
- Implementa lógica de cache e fallback

### Feign Clients
**AwesomeCepFeignClient**
- Interface para comunicação com a API AwesomeAPI CEP

**OpenRouteFeignClient**
- Interface para comunicação com a API OpenRouteService

### Modelos de Resposta das APIs
**AwesomeCepResponse**
- Modelo para resposta da API AwesomeAPI CEP
- Contém apenas os campos necessários:
  - cep
  - lat
  - lng
  - address
  - district (bairro)
  - city
  - state

**OpenRouteResponse**
- Modelo para resposta da API OpenRouteService
- Estrutura hierárquica conforme retorno da API

### Objeto de Resultado
**DistanciaQueryResult**
- Objeto consolidado retornado ao controller
- Contém:
  - cepOrigem
  - cepDestino
  - enderecoOrigem
  - bairroOrigem
  - ufOrigem
  - enderecoDestino
  - bairroDestino
  - ufDestino
  - distanciaKm
  - tempoMinutos

## Exemplos de Chamadas das APIs

### 1. Chamada para AwesomeAPI CEP
```bash
curl --location 'https://cep.awesomeapi.com.br/json/38065065'
```

### 2. Chamada para OpenRouteService
```bash
curl --location 'https://api.openrouteservice.org/v2/directions/driving-car?api_key=YOUR_API_KEY&start=-47.9373729%2C-19.72942&end=-47.9381862%2C-19.7443998' \
--header 'Accept: application/json, application/geo+json, application/gpx+xml, img/png'
```

## Exemplos de Respostas das APIs

### 1. Resposta da AwesomeAPI CEP
```json
{
    "cep": "38065065",
    "address_type": "Rua",
    "address_name": "Governador Valadares",
    "address": "Rua Governador Valadares",
    "state": "MG",
    "district": "Fabrício",
    "lat": "-19.7443998",
    "lng": "-47.9381862",
    "city": "Uberaba",
    "city_ibge": "3170107",
    "ddd": "34"
}
```

### 2. Campos Relevantes da Resposta OpenRouteService

Da resposta completa da OpenRouteService, os campos relevantes para nossa aplicação são:

```json
{
    "features": [
        {
            "properties": {
                "summary": {
                    "distance": 3222.2,  // Distância em metros
                    "duration": 400.0    // Duração em segundos
                }
            }
        }
    ]
}
```

Onde:
- `distance`: Distância total da rota em metros (3222.2 = 3,22 km)
- `duration`: Tempo estimado para percorrer a rota em segundos (400.0 = 6,67 minutos)

### 3. Modelo de Retorno DistanciaQueryResult

```json
{
    "cepOrigem": "38067290",
    "cepDestino": "38065065",
    "enderecoOrigem": "Rua República do Haiti",
    "bairroOrigem": "Fabrício",
    "ufOrigem": "MG",
    "enderecoDestino": "Rua Governador Valadares",
    "bairroDestino": "Fabrício",
    "ufDestino": "MG",
    "distanciaKm": 3.22,
    "tempoMinutos": 6.67
}
```

## Fluxo de Execução

1. Cliente chama: `GET /api/distancias/calcular?cepOrigem=38067290&cepDestino=38065065`
2. DistanciaController delega para DistanciaService
3. DistanciaService:
   - Usa AwesomeCepClient para obter coordenadas dos dois CEPs
   - Usa OpenRouteServiceClient para calcular distância entre as coordenadas
   - Processa os resultados (converte unidades, formata dados)
   - Preenche o DistanciaQueryResult com todos os campos requeridos
4. DistanciaController retorna o resultado como resposta HTTP

## Considerações de Implementação

1. **Cache de Resultados**
   - Implementar cache para reduzir chamadas às APIs
   - Armazenar consultas de CEP e distâncias já calculadas

2. **Tratamento de Erros**
   - Implementar fallback para cálculo de Haversine
   - Tratar erros de comunicação com as APIs externas

3. **Controle de Limites**
   - Monitorar número de requisições para respeitar limites da API
   - Usar estratégia de fallback quando necessário

4. **Configuração**
   - Armazenar chave de API e URLs em arquivo de configuração
   - Facilitar alteração de parâmetros sem modificar código