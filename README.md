# CochitoServicosAPI - Feature 1: Sistema de Notificações

## 🏢 Contexto do Projeto

O **CochitoServicosAPI** é uma aplicação Java Spring Boot desenvolvida para gerenciar serviços prestados por uma empresa de consultoria em TI. O sistema permite o controle completo de ordens de serviço, desde a criação até a execução, envolvendo funcionários especializados e clientes corporativos.

### Arquitetura Base
- **Backend**: Java 17 + Spring Boot 3.x + JPA/Hibernate
- **Banco de Dados**: Configurável (H2 para dev, PostgreSQL para prod)
- **Testes**: JUnit 5 + Mockito
- **Metodologia**: Test-Driven Development (TDD)

### Domínio de Negócio
A Cochito Serviços atua prestando consultoria especializada em:
- Infraestrutura de TI
- Desenvolvimento de software
- Análise de sistemas
- Consultoria em segurança digital

---

## 📊 Status do Projeto

### Progresso TDD - Feature 1:
- 🔴 **RED**: **100%** ✅
- 🟢 **GREEN**: **100%** ✅  
- 🟡 **REFACTOR**: **100%** ✅

**Total de cenários implementados**: 15 testes (CT001 a CT015)

---

## 🏗️ Arquitetura do Sistema

### Sistema Existente (Base Consolidada)
O CochitoServicosAPI já possuía a estrutura principal:

#### Classes Core:
- **`Funcionario.java`** - Funcionários especializados (com controle ativo/inativo)
- **`OrdemServico.java`** - Núcleo do sistema - Gerencia execução dos serviços
- **`Servico.java`** - Catálogo de serviços oferecidos
- **`Cliente.java`** - Clientes corporativos

#### Fluxo Operacional:
```
Cliente solicita → OrdemServico criada → Funcionário atribuído → Serviços executados → Status atualizado
```

#### Arquitetura Atual do Projeto:
```java
// Estrutura expandida implementada
Funcionario ← OrdemServico → ItemServico → Servico
                  ↓              
            Notificacao → TipoNotificacao
```

---

## 🚀 Feature 1: Sistema de Notificações

### Objetivo
Implementar um sistema completo de notificações para comunicar funcionários sobre mudanças de status em suas ordens de serviço, melhorando a comunicação interna e agilidade operacional.

### Classes Implementadas

#### A. Estrutura de Dados:
- **`ItemServico.java`** - ✅ **IMPLEMENTADA** - Item com quantidade e cálculos
- **`Notificacao.java`** - ✅ **IMPLEMENTADA** - Entidade principal de notificação  
- **`TipoNotificacao.java`** - ✅ **IMPLEMENTADA** - Enum para categorização

#### B. Camada de Serviços:
- **`NotificacaoService.java`** - ✅ **IMPLEMENTADA** - Lógica de negócio completa

#### C. Cobertura de Testes:
- **`ItemServicoTest.java`** - ✅ **5 cenários** - Testa cálculos e validações
- **`NotificacaoTest.java`** - ✅ **3 cenários** - Testa entidade básica
- **`NotificacaoServiceTest.java`** - ✅ **7 cenários** - Testa service completo

---

## 📋 Requisitos Funcionais Implementados

### RF001 - Gestão de Cálculos de ItemServico ✅
Sistema responsável pelo cálculo de subtotais dos itens de serviço.

#### Funcionalidades:
- ✅ **RF001.1**: Cálculo de subtotal válido (`quantidade × preço`)
- ✅ **RF001.2**: Tratamento de quantidade zero
- ✅ **RF001.3**: Tratamento de quantidade negativa  
- ✅ **RF001.4**: Tratamento de serviço nulo
- ✅ **RF001.5**: Tratamento de preço nulo

### RF002 - Gestão de Entidade Notificacao ✅
Sistema responsável pelo comportamento básico da entidade Notificacao.

#### Funcionalidades:
- ✅ **RF002.1**: Inicialização com valores padrão (`lida = false`)
- ✅ **RF002.2**: Getters/setters com integridade de dados
- ✅ **RF002.3**: Controle de leitura com data automática

### RF003 - Gestão de Serviços de Notificacao ✅
Sistema responsável pela lógica de negócio das notificações.

#### Funcionalidades:
- ✅ **RF003.1**: Criação baseada em OrdemServico válida
- ✅ **RF003.2**: Validação de funcionário ativo
- ✅ **RF003.3**: Mensagens personalizadas contextuais
- ✅ **RF003.4**: Marcação de leitura via service
- ✅ **RF003.5**: Contagem de não lidas por funcionário
- ✅ **RF003.6**: Validação de OrdemServico não nula
- ✅ **RF003.7**: Validação de TipoNotificacao não nulo

---

## 🔄 Cenários de Teste TDD (Metodologia Completa)

### ItemServicoTest.java - RF001 (5 cenários)

| Cenário | Descrição | Status TDD |
|---------|-----------|------------|
| **CT001** | Cálculo de subtotal válido | 🔴→🟢→🟡 ✅ |
| **CT002** | Quantidade zero retorna zero | 🔴→🟢→🟡 ✅ |
| **CT003** | Quantidade negativa retorna zero | 🔴→🟢→🟡 ✅ |
| **CT004** | Serviço nulo retorna zero | 🔴→🟢→🟡 ✅ |
| **CT005** | Preço nulo retorna zero | 🔴→🟢→🟡 ✅ |

### NotificacaoTest.java - RF002 (3 cenários)

| Cenário | Descrição | Status TDD |
|---------|-----------|------------|
| **CT006** | Inicialização com valores padrão | 🔴→🟢→🟡 ✅ |
| **CT007** | Getters/setters funcionais | 🔴→🟢→🟡 ✅ |
| **CT008** | Marcação como lida automática | 🔴→🟢→🟡 ✅ |

### NotificacaoServiceTest.java - RF003 (7 cenários)

| Cenário | Descrição | Status TDD |
|---------|-----------|------------|
| **CT009** | Criação para ordem válida | 🔴→🟢→🟡 ✅ |
| **CT010** | Falha para funcionário inativo | 🔴→🟢→🟡 ✅ |
| **CT011** | Mensagem personalizada com dados | 🔴→🟢→🟡 ✅ |
| **CT012** | Marcação como lida via service | 🔴→🟢→🟡 ✅ |
| **CT013** | Contagem de não lidas por funcionário | 🔴→🟢→🟡 ✅ |
| **CT014** | Validação OrdemServico nula | 🔴→🟢→🟡 ✅ |
| **CT015** | Validação TipoNotificacao nulo | 🔴→🟢→🟡 ✅ |

---

## ⚡ Melhorias Implementadas no Refactor

### 1. ItemServico.java - Validações Encapsuladas
```java
// ANTES (GREEN)
if (quantidade == null || quantidade <= 0) return BigDecimal.ZERO;
if(servico == null) return BigDecimal.ZERO;
if(servico.getPreco() == null) return BigDecimal.ZERO;

// DEPOIS (REFACTOR)
private boolean isQuantidadeValida() { return quantidade != null && quantidade > 0; }
private boolean isServicoValido() { return servico != null && servico.getPreco() != null; }
```

### 2. NotificacaoService.java - Design Patterns Aplicados

#### Builder Pattern para Mensagens:
```java
private static class NotificacaoMessageBuilder {
    public String paraOrdemCriada() { /* contexto específico */ }
    public String paraOrdemAtualizada() { /* contexto específico */ }
    // ...
}
```

#### Template Method para Títulos:
```java
private String gerarTitulo(OrdemServico ordemServico, TipoNotificacao tipo) {
    switch (tipo) {
        case ORDEM_SERVICO_CRIADA: return "Nova Ordem de Serviço #" + idOrdem;
        // ...
    }
}
```

#### Validações Encapsuladas:
```java
private void validarParametrosEntrada(OrdemServico ordemServico, TipoNotificacao tipo)
private void validarFuncionarioAtivo(Funcionario funcionario)  
private Notificacao construirNotificacao(OrdemServico ordemServico, TipoNotificacao tipo, Funcionario funcionario)
```

### 3. Notificacao.java - Robustez e Utilitários

#### Operação Idempotente:
```java
public void marcarComoLida() {
    if (!this.lida) {  // Só executa se necessário
        this.lida = true;
        this.dataLeitura = LocalDateTime.now();
    }
}
```

#### Métodos Utilitários:
```java
public boolean foiLida() { return this.lida && this.dataLeitura != null; }
public String resumo() { return String.format("Notificacao[tipo=%s, funcionario=%s, lida=%s]", ...); }
```

#### Comparação Adequada:
```java
@Override
public boolean equals(Object obj) { /* implementação robusta */ }
@Override  
public int hashCode() { return Objects.hash(titulo, tipoNotificacao, funcionario, dataCriacao); }
```

---

## 🔗 Integração com Sistema Existente

### Compatibilidade Total
- ✅ **OrdemServico**: Uso do campo `itensServicos` já existente
- ✅ **Funcionario**: Sem alterações - compatibilidade total
- ✅ **Servico**: Sem alterações - compatibilidade total  
- ✅ **Services Existentes**: Zero impacto nas funcionalidades atuais

### Dados Acessados via Relacionamentos
- **Funcionário**: `ordemServico.getFuncionario().getNome()`
- **Status Funcionário**: `ordemServico.getFuncionario().isAtivo()`
- **Serviços da Ordem**: `ordemServico.getItensServicos()`
- **Valores**: `ItemServico.getQuantidade()` e `Servico.getPreco()`
- **Status da Ordem**: `ordemServico.getStatus()`

---

## 🎯 Benefícios Entregues

### Para o Negócio:
- **Comunicação Ágil**: Funcionários notificados automaticamente
- **Rastreabilidade**: Histórico completo de leitura das notificações
- **Gestão Eficiente**: Contadores de notificações não lidas
- **Escalabilidade**: Arquitetura preparada para novos tipos de notificação

### Para Desenvolvimento:
- **Cobertura de Testes**: 100% dos RFs testados
- **Clean Code**: Refatoração seguindo melhores práticas
- **Design Patterns**: Builder, Template Method aplicados
- **Extensibilidade**: Fácil adição de novos tipos de notificação

### Para Qualidade:
- **TDD Rigoroso**: 15 cenários RED→GREEN→REFACTOR
- **Validações Robustas**: Tratamento de todos os casos extremos  
- **Código Defensivo**: Proteção contra NullPointer e dados inválidos
- **Documentação**: JavaDoc completo para manutenibilidade

---

## 🚀 Como Executar

### Pré-requisitos
- Java 17+
- Maven 3.6+
- IDE (IntelliJ IDEA, Eclipse, VS Code)

### Comandos
```bash
# Executar todos os testes
mvn test

# Executar apenas testes da Feature 1
mvn test -Dtest="*ItemServicoTest,*NotificacaoTest,*NotificacaoServiceTest"

# Executar aplicação
mvn spring-boot:run
```

### Verificar Implementação
```bash
# Relatório de cobertura
mvn jacoco:report

# Verificar testes passando
mvn clean test
```

---

## 📈 Roadmap e Próximos Passos

### Visão Geral do Projeto (4 Features Total)
O CochitoServicosAPI está sendo desenvolvido em **4 features incrementais**, cada uma aplicando metodologia TDD rigorosa e expandindo as capacidades do sistema:

- ✅ **Feature 1**: Sistema de Notificações *(COMPLETA)*
- 🔄 **Feature 2**: Módulo com API *(EM PLANEJAMENTO)*  
- 📋 **Feature 3**: *(A SER DEFINIDA)*
- 📋 **Feature 4**: *(A SER DEFINIDA)*

### Feature 2 - Sistema com API Externa (Próxima)
Integração de uma API externa ao projeto usando metodologia TDD. Detalhes técnicos e classes a serem definidos.

### Features 3 e 4 - Roadmap Estratégico
*(A serem definidas conforme evolução do projeto)*

### Melhorias Contínuas (Todas as Features):
- **Performance**: Otimizações baseadas em métricas reais
- **Documentação**: JavaDoc e README sempre atualizados
- **Cobertura de Testes**: Meta de 100% para código crítico
- **Clean Architecture**: Refatoração constante seguindo SOLID

---

## 👥 Equipe

**Desenvolvido com metodologia TDD por:**
- Professor Elberth (Orientação técnica)
- Adriel Cochito (Desenvolvimento principal)

---

## 📄 Licença

Este projeto é desenvolvido para fins acadêmicos como parte do curso de Engenharia de Software com foco em metodologias ágeis e boas práticas de desenvolvimento.

---

**🏆 Feature 1 - Sistema de Notificações: COMPLETA ✅**

*Implementado seguindo rigorosamente TDD (Test-Driven Development) com 15 cenários de teste e refatoração aplicando Design Patterns para código enterprise de alta qualidade.*