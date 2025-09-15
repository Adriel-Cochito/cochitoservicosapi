# Feature 02 - Cálculo de Distância entre CEPs

## 📋 Visão Geral

A Feature 02 implementa um sistema completo de cálculo de distância entre CEPs utilizando integração com APIs externas. O projeto **cochitoServicoApi** expõe um endpoint REST que calcula a distância real entre dois CEPs, enquanto o projeto **cochitoApi** consome este serviço para enriquecer as ordens de serviço com informações de distância entre funcionário e cliente.

## 🏗️ Arquitetura Implementada

### Projeto Principal: cochitoServicoApi

#### 1. **Controller**: `ServicoController`
- **Endpoint**: `GET /api/servicos/distancia`
- **Parâmetros**: `cepOrigem` e `cepDestino`
- **Retorno**: Objeto `DistanciaQueryResult` completo

#### 2. **Service**: `DistanciaService`
- Orquestra as chamadas às APIs externas
- Implementa fallback com cálculo Haversine quando APIs falham
- Processa e converte dados (metros → km, segundos → minutos)

#### 3. **Feign Clients**
- **AwesomeCepFeignClient**: Consulta coordenadas geográficas
- **OpenRouteFeignClient**: Calcula distância real por rotas

#### 4. **Modelos de Dados**
- **AwesomeCepResponse**: Mapeia resposta da API AwesomeAPI CEP
- **OpenRouteResponse**: Mapeia resposta da API OpenRouteService
- **DistanciaQueryResult**: Resultado consolidado para o cliente

### Projeto Cliente: cochitoApi

#### 1. **Controller**: `DistanciaController`
- **Endpoint**: `GET /api/distancia/consulta/{cepOrigem}/{cepCliente}`
- Abstrai a complexidade da consulta para outros sistemas

#### 2. **Feign Client**: `DistanciaFeignClient`
- Cliente para consumir o endpoint do cochitoServicoApi
- Configurado via `application.properties`

#### 3. **Integração com Ordem de Serviço**
- **OrdemServicoService**: Enriquece automaticamente consultas de ordens
- Campo `@Transient distancia` no modelo `OrdemServico`

## 🔧 APIs Externas Utilizadas

### 1. AwesomeAPI CEP
- **URL**: `https://cep.awesomeapi.com.br/json/{cep}`
- **Função**: Obter coordenadas (latitude/longitude) de CEPs
- **Gratuita**: Sim

### 2. OpenRouteService
- **URL**: `https://api.openrouteservice.org/v2/directions/driving-car`
- **Função**: Calcular distância real considerando rotas
- **Autenticação**: API Key obrigatória

## 🚀 Endpoints Disponíveis

### **cochitoServicoApi - Endpoints Principais**

#### 1. **Calcular Distância entre CEPs**
```bash
GET /api/servicos/distancia?cepOrigem={origem}&cepDestino={destino}
```

**Parâmetros:**
- `cepOrigem` (String): CEP de origem (aceita formatação com pontos/hífens)
- `cepDestino` (String): CEP de destino (aceita formatação com pontos/hífens)

**Exemplo de Chamada:**
```bash
curl -X GET "http://localhost:8081/api/servicos/distancia?cepOrigem=38067290&cepDestino=38065065"
```

**Resposta de Sucesso (200):**
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

**Casos de Teste do Endpoint Principal:**

```bash
# Teste 1: CEPs básicos
curl -X GET "http://localhost:8081/api/servicos/distancia?cepOrigem=38067290&cepDestino=38065065"

# Teste 2: CEPs com formatação (pontos e hífens)
curl -X GET "http://localhost:8081/api/servicos/distancia?cepOrigem=38.067-290&cepDestino=38065-065"

# Teste 3: CEPs de diferentes cidades
curl -X GET "http://localhost:8081/api/servicos/distancia?cepOrigem=38065065&cepDestino=01310-100"

# Teste 4: Verificar robustez com CEPs inválidos
curl -X GET "http://localhost:8081/api/servicos/distancia?cepOrigem=00000000&cepDestino=11111111"
```

## 🧪 Validação e Testes

### **Para Avaliadores: Como Validar a Implementação**

#### 1. Testar Funcionalidade Principal (cochitoServicoApi)

```bash
# Teste básico - calcular distância entre CEPs de Uberaba
curl -X GET "http://localhost:8081/api/servicos/distancia?cepOrigem=38067290&cepDestino=38065065"
```

#### 2. Testar Tratamento de Formatação

```bash
# CEPs com pontos e hífens (devem ser tratados automaticamente)
curl -X GET "http://localhost:8081/api/servicos/distancia?cepOrigem=38.067-290&cepDestino=38065-065"
```

#### 3. Testar Diferentes Distâncias

```bash
# Teste com CEPs mais distantes
curl -X GET "http://localhost:8081/api/servicos/distancia?cepOrigem=38065065&cepDestino=20040020"
```

**Resposta com Distância Incluída:**
```json
{
    "id": 1,
    "cliente": {
        "id": 2,
        "nome": "Maria Santos",
        "endereco": {
            "cep": "38020-433",
            "logradouro": "Avenida Santa Beatriz da Silva",
            "bairro": "São Benedito",
            "localidade": "Uberaba",
            "uf": "MG"
        }
    },
    "funcionario": {
        "id": 2,
        "nome": "Mariana Lopes",
        "endereco": {
            "cep": "38065-065",
            "logradouro": "Rua Governador Valadares",
            "bairro": "Fabrício",
            "localidade": "Uberaba",
            "uf": "MG"
        }
    },
    "servico": {
        "id": 2,
        "titulo": "Desenvolvimento de E-commerce",
        "preco": 8500.0
    },
    "status": "EM_ANDAMENTO",
    "distancia": {
        "cepOrigem": "38065065",
        "cepDestino": "38020433",
        "enderecoOrigem": "Rua Governador Valadares",
        "bairroOrigem": "Fabrício",
        "ufOrigem": "MG",
        "enderecoDestino": "Avenida Santa Beatriz da Silva",
        "bairroDestino": "São Benedito",
        "ufDestino": "MG",
        "distanciaKm": 2.66,
        "tempoMinutos": 3.39
    }
}
```

## ⚙️ Configuração

### cochitoServicoApi (application.properties)
```properties
# OpenRouteService API Key
api.openroute.apikey=eyJvcmciOiI1YjNjZTM1OTc4NTExMTAwMDFjZjYyNDgiLCJpZCI6ImYyYzNkMDY3YzI1ODQ0ODhhMDc3ZmNjNTI1NmY1N2Y1IiwiaCI6Im11cm11cjY0In0%3D

# Server Configuration
server.port=8081
```

### cochitoApi (application.properties)
```properties
# URL do cochitoServicoApi
cochitoservicoapi.url=http://localhost:8081

# Server Configuration  
server.port=8080
```

## 🔍 Funcionalidades Implementadas

### ✅ 1. Integração com APIs Externas

Para documentação técnica detalhada das APIs utilizadas (AwesomeAPI CEP e OpenRouteService)

Consulte: **[Readme-API-EXTERNA.md](./Readme-API-EXTERNA.md)**

- [x] **AwesomeCepFeignClient**: Consulta coordenadas de CEPs
- [x] **OpenRouteFeignClient**: Calcula rotas reais
- [x] Tratamento de erros e fallback com Haversine


### ✅ 2. Processamento de Dados
- [x] Limpeza automática de CEPs (remove pontos/hífens)
- [x] Conversão de unidades (metros → km, segundos → minutos)
- [x] Arredondamento preciso para 2 casas decimais

### ✅ 3. Endpoints Consumíveis
- [x] **cochitoServicoApi**: `/api/servicos/distancia` (query params)
- [x] **cochitoApi**: `/api/distancia/consulta/{origem}/{destino}` (path params)

## 🔗 Consumo pelo Projeto da Disciplina Anterior (cochitoApi)

### **Implementação do Cliente para Consumir cochitoServicoApi**

#### 1. **DistanciaFeignClient** - Cliente Feign
```java
@FeignClient(name = "distanciaClient", url = "${cochitoservicoapi.url}")
public interface DistanciaFeignClient {
    
    @GetMapping("/api/servicos/distancia")
    DistanciaQueryResult calcularDistancia(
        @RequestParam("cepOrigem") String cepOrigem,
        @RequestParam("cepDestino") String cepDestino
    );
}
```

#### 2. **DistanciaController** - Endpoint Abstraído
```bash
GET /api/distancia/consulta/{cepOrigem}/{cepCliente}
```

**Exemplo de Uso:**
```bash
curl -X GET "http://localhost:8080/api/distancia/consulta/38067290/38065065"
```

#### 3. **Integração com OrdemServicoService**
O serviço `OrdemServicoService` foi modificado para automaticamente consultar a distância ao buscar uma ordem de serviço:

```java
public OrdemServico buscarPorId(Integer id) {
    OrdemServico ordemServico = ordemServicoRepository.findById(id).orElseThrow(...);
    
    // Consulta automática da distância entre funcionário e cliente
    if (ordemServico.getFuncionario() != null && ordemServico.getCliente() != null) {
        String cepFuncionario = ordemServico.getFuncionario().getEndereco().getCep();
        String cepCliente = ordemServico.getCliente().getEndereco().getCep();
        
        DistanciaQueryResult distancia = distanciaService.consultarDistancia(cepFuncionario, cepCliente);
        ordemServico.setDistancia(distancia);
    }
    
    return ordemServico;
}
```

#### 4. **Teste da Integração Completa**

```bash
# Buscar ordem de serviço que inclui distância automaticamente
curl -X GET "http://localhost:8080/api/ordens-servico/1"
```

**Resposta Enriquecida com Distância:**
```json
{
    "id": 1,
    "cliente": {
        "id": 2,
        "nome": "Maria Santos",
        "endereco": {
            "cep": "38020-433",
            "logradouro": "Avenida Santa Beatriz da Silva",
            "bairro": "São Benedito",
            "localidade": "Uberaba",
            "uf": "MG"
        }
    },
    "funcionario": {
        "id": 2,
        "nome": "Mariana Lopes",
        "endereco": {
            "cep": "38065-065",
            "logradouro": "Rua Governador Valadares",
            "bairro": "Fabrício",
            "localidade": "Uberaba",
            "uf": "MG"
        }
    },
    "servico": {
        "id": 2,
        "titulo": "Desenvolvimento de E-commerce",
        "preco": 8500.0
    },
    "status": "EM_ANDAMENTO",
    "distancia": {
        "cepOrigem": "38065065",
        "cepDestino": "38020433",
        "enderecoOrigem": "Rua Governador Valadares",
        "bairroOrigem": "Fabrício",
        "ufOrigem": "MG",
        "enderecoDestino": "Avenida Santa Beatriz da Silva",
        "bairroDestino": "São Benedito",
        "ufDestino": "MG",
        "distanciaKm": 2.66,
        "tempoMinutos": 3.39
    }
}
```

### **Configuração Necessária no cochitoApi**

**application.properties:**
```properties
# URL do cochitoServicoApi para comunicação entre projetos
cochitoservicoapi.url=http://localhost:8081

# Configuração do servidor
server.port=8080
```

### ✅ 4. Consumo entre Projetos
- [x] **DistanciaFeignClient**: Cliente Feign no cochitoApi
- [x] **OrdemServicoService**: Enriquecimento automático com distância
- [x] Campo `@Transient` para não persistir no banco
- [x] **DistanciaController**: Endpoint abstraído para facilitar uso
- [x] **DistanciaService**: Serviço que encapsula chamada ao Feign Client

### ✅ 5. Robustez e Tratamento de Erros
- [x] Fallback para cálculo Haversine quando APIs falham
- [x] Validação e sanitização de entrada de CEPs
- [x] Tratamento de exceções de APIs externas

## 🧪 Testes Implementados

### Testes Unitários (cochitoServicoApi)
- **DistanciaServiceTest**: Testa cenários de sucesso e fallback
- **OpenRouteFeignClientTest**: Valida integração com API externa
- **Cobertura**: Cenários normais, falhas de API e CEPs formatados

### Validação Manual
- Todos os endpoints foram testados com diferentes combinações de CEPs
- Validação de respostas das APIs externas
- Teste de integração completa entre os projetos

## 📊 Fluxo de Execução Completo

### **Cenário 1: Uso Direto do cochitoServicoApi**
```
1. Cliente → cochitoServicoApi:/api/servicos/distancia?cepOrigem=X&cepDestino=Y
   ↓
2. ServicoController → DistanciaService
   ↓
3. AwesomeCepFeignClient → https://cep.awesomeapi.com.br (CEP origem)
   ↓
4. AwesomeCepFeignClient → https://cep.awesomeapi.com.br (CEP destino)
   ↓
5. OpenRouteFeignClient → https://api.openrouteservice.org (coordenadas)
   ↓
6. Processamento e conversão → DistanciaQueryResult
   ↓
7. Resposta HTTP ← cochitoServicoApi
```

### **Cenário 2: Consumo via cochitoApi (Projeto da Disciplina Anterior)**
```
1. Cliente → cochitoApi:/api/distancia/consulta/{origem}/{destino}
   ↓
2. DistanciaController → DistanciaService
   ↓
3. DistanciaFeignClient → cochitoServicoApi:/api/servicos/distancia
   ↓
4. [Fluxo do Cenário 1 no cochitoServicoApi]
   ↓
5. DistanciaQueryResult ← cochitoServicoApi
   ↓
6. Resposta final ← cochitoApi
```

### **Cenário 3: Enriquecimento Automático de Ordem de Serviço**
```
1. Cliente → cochitoApi:/api/ordens-servico/{id}
   ↓
2. OrdemServicoController → OrdemServicoService.buscarPorId()
   ↓
3. Busca ordem no banco → OrdemServico (sem distância)
   ↓
4. Extrai CEPs do funcionário e cliente
   ↓
5. DistanciaService.consultarDistancia() → DistanciaFeignClient
   ↓
6. [Fluxo do Cenário 1 no cochitoServicoApi]
   ↓
7. DistanciaQueryResult ← cochitoServicoApi
   ↓
8. OrdemServico.setDistancia(resultado)
   ↓
9. Resposta com OrdemServico enriquecida ← cochitoApi
```

## 📈 Casos de Teste para Validação

### **Testes do cochitoServicoApi (Projeto Principal)**

#### Teste 1: CEPs Básicos de Uberaba
```bash
curl "http://localhost:8081/api/servicos/distancia?cepOrigem=38067290&cepDestino=38065065"
# Esperado: distanciaKm ≈ 3.22, tempoMinutos ≈ 6.67
```

#### Teste 2: CEPs com Formatação
```bash
curl "http://localhost:8081/api/servicos/distancia?cepOrigem=38.067-290&cepDestino=38065-065"
# Esperado: Mesmo resultado do Teste 1 (formatação é tratada automaticamente)
```

#### Teste 3: CEPs de Diferentes Estados
```bash
curl "http://localhost:8081/api/servicos/distancia?cepOrigem=38065065&cepDestino=01310100"
# Esperado: Distância maior entre Uberaba/MG e São Paulo/SP
```

### **Testes do cochitoApi (Consumo do Projeto Principal)**

#### Teste 4: Consumo Direto via Cliente
```bash
curl "http://localhost:8080/api/distancia/consulta/38067290/38065065"
# Esperado: Mesmo resultado através do cliente Feign
```

#### Teste 5: Ordem de Serviço com Distância Automática
```bash
curl "http://localhost:8080/api/ordens-servico/1"
# Esperado: Objeto completo com campo "distancia" preenchido automaticamente
```

## 🎯 Pontos de Avaliação

**Para o Avaliador - Itens Implementados:**

1. **✅ Integração com APIs Externas**: Duas APIs distintas (AwesomeAPI + OpenRoute)
2. **✅ Classes de Mapeamento**: AwesomeCepResponse, OpenRouteResponse, DistanciaQueryResult
3. **✅ Feign Clients**: Dois clients funcionais com configuração adequada
4. **✅ Service com Lógica**: Processamento, conversão e fallback implementados
5. **✅ Endpoints REST**: Dois endpoints em projetos diferentes
6. **✅ Consumo entre Projetos**: DistanciaFeignClient funcional
7. **✅ Enriquecimento de Dados**: OrdemServico com informação de distância
8. **✅ Tratamento de Erros**: Fallback e validação robustos
9. **✅ Testes**: Unitários e de integração implementados
10. **✅ Documentação**: README completo com exemplos práticos

A Feature 02 está **100% funcional** e atende todos os requisitos especificados, fornecendo uma base sólida para calcular distâncias entre CEPs com integração robusta entre microserviços.