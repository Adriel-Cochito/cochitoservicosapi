# CochitoServicosAPI - Feature 1: Sistema de Notificações

## Projeto Base e Nova Funcionalidade

### Sistema Existente (Disciplina Anterior)
O **CochitoServicosAPI** possui a estrutura consolidada de gestão de serviços:

#### Classes Base Atuais:
- **`Funcionario.java`** - Funcionários que executam os serviços (com controle ativo/inativo)
- **`OrdemServico.java`** - Núcleo do sistema - Controla execução dos serviços
- **`Servico.java`** - Catálogo de serviços oferecidos pela empresa

#### Fluxo Atual:
```
OrdemServico criada → Funcionário atribuído → Serviços executados → Status atualizado
```

---

## Reestruturação Arquitetural Implementada

### Nova Estrutura do Sistema

#### Nova Classe Implementada:
- **`ItemServico.java`** - Item individual com quantidade na ordem

#### Arquitetura Atual do Projeto:
```java
// Estrutura expandida implementada
Funcionario ← OrdemServico → ItemServico → Servico
                  ↓              
            Notificacao → TipoNotificacao
```

---

## Classes da Feature 1 - Sistema de Notificações

### Novas Classes (Desenvolvimento via TDD)

#### A. Estrutura de Dados:
- **`ItemServico.java`** - CRIADA - Item com quantidade e relacionamentos
- **`Notificacao.java`** - A CRIAR - Entidade principal de notificação
- **`TipoNotificacao.java`** - A CRIAR - Enum para tipos de notificação

#### B. Service Principal:
- **`NotificacaoService.java`** - A CRIAR VIA TDD - Lógica de negócio completa

#### C. Classes de Teste:
- **`ItemServicoTest.java`** - Testa cálculos e validações
- **`NotificacaoTest.java`** - Testa entidade básica
- **`NotificacaoServiceTest.java`** - PRINCIPAL TDD - Service completo

---

## Requisitos Funcionais

### RF001 - Gestão de Cálculos de ItemServico
Sistema responsável pelo cálculo de subtotais dos itens de serviço.

#### RF001.1 - Cálculo de Subtotal Válido
- Sistema deve calcular subtotal usando `quantidade × servico.getPreco()`
- Deve retornar valor correto para dados válidos

#### RF001.2 - Tratamento de Quantidade Zero
- Sistema deve retornar BigDecimal.ZERO quando quantidade for zero
- Deve evitar cálculos com valores inválidos

#### RF001.3 - Tratamento de Quantidade Negativa
- Sistema deve retornar BigDecimal.ZERO quando quantidade for negativa
- Deve validar entrada de dados

#### RF001.4 - Tratamento de Serviço Nulo
- Sistema deve retornar BigDecimal.ZERO quando serviço for nulo
- Deve evitar NullPointerException

#### RF001.5 - Tratamento de Preço Nulo
- Sistema deve retornar BigDecimal.ZERO quando preço do serviço for nulo
- Deve validar integridade completa dos dados

### RF002 - Gestão de Entidade Notificacao
Sistema responsável pelo comportamento básico da entidade Notificacao.

#### RF002.1 - Inicialização de Notificacao
- Sistema deve inicializar notificação com valores padrão corretos
- Campo `lida` deve começar como `false`
- Data de leitura deve ser `null` inicialmente

#### RF002.2 - Getters e Setters de Notificacao
- Sistema deve permitir definir e obter todos os dados da notificação
- Deve manter integridade dos dados armazenados

#### RF002.3 - Controle de Leitura de Notificacao
- Sistema deve marcar notificação como lida
- Deve registrar data de leitura automaticamente
- Deve atualizar método `estaComoNaoLida()`

### RF003 - Gestão de Serviços de Notificacao
Sistema responsável pela lógica de negócio das notificações.

#### RF003.1 - Criação de Notificacao para Ordem Válida
- Sistema deve criar notificação baseada em OrdemServico válida
- Deve extrair dados da ordem para criar notificação
- Deve definir título e funcionário corretamente

#### RF003.2 - Validação de Funcionário Ativo
- Sistema deve validar se funcionário está ativo antes de criar notificação
- Deve lançar IllegalArgumentException para funcionário inativo

#### RF003.3 - Geração de Mensagem Personalizada
- Sistema deve gerar mensagem contextual com dados da ordem
- Mensagem deve conter ID da ordem, nome do funcionário e status

#### RF003.4 - Controle de Leitura via Service
- Sistema deve permitir marcar notificação como lida via service
- Deve delegar para método da entidade

#### RF003.5 - Contagem de Notificações Não Lidas
- Sistema deve contar notificações não lidas por funcionário
- Deve manter controle de estado das notificações

#### RF003.6 - Validação de OrdemServico Nula
- Sistema deve validar entrada não nula
- Deve lançar IllegalArgumentException para ordem nula

#### RF003.7 - Validação de TipoNotificacao Nulo
- Sistema deve validar tipo de notificação não nulo
- Deve lançar IllegalArgumentException para tipo nulo

---

## Cenários de Teste TDD

### ItemServicoTest.java - RF001 (5 cenários)

#### CT001 - RF001.1 - "Deve calcular subtotal para um item válido"
- **RED**: Método `calcularSubTotal()` retorna null
- **GREEN**: Implementação: `quantidade × servico.getPreco()`
- **REFACTOR**: Validação de dados e otimização

#### CT002 - RF001.2 - "Deve retornar zero quando quantidade for zero"
- **RED**: Não trata quantidade zero
- **GREEN**: Validação: `if(quantidade <= 0) return BigDecimal.ZERO`
- **REFACTOR**: Método de validação reutilizável

#### CT003 - RF001.3 - "Deve retornar zero quando quantidade for negativa"
- **RED**: Não trata quantidade negativa
- **GREEN**: Usa mesma validação de quantidade
- **REFACTOR**: Consolidar validações

#### CT004 - RF001.4 - "Deve retornar zero quando serviço for nulo"
- **RED**: NullPointerException ao acessar serviço
- **GREEN**: Validação: `if(servico == null) return BigDecimal.ZERO`
- **REFACTOR**: Validação defensiva

#### CT005 - RF001.5 - "Deve retornar zero quando preço do serviço for nulo"
- **RED**: NullPointerException ao acessar preço
- **GREEN**: Validação: `if(servico.getPreco() == null) return BigDecimal.ZERO`
- **REFACTOR**: Validação completa da cadeia

### NotificacaoTest.java - RF002 (3 cenários)

#### CT006 - RF002.1 - "Deve inicializar notificação com valores padrão"
- **RED**: Construtor não inicializa campos
- **GREEN**: Inicialização: `lida = false`, campos nulos
- **REFACTOR**: Construtor otimizado

#### CT007 - RF002.2 - "Deve permitir definir e obter dados da notificação"
- **RED**: Getters/setters não funcionam
- **GREEN**: Implementação básica de getters/setters
- **REFACTOR**: Validações nos setters

#### CT008 - RF002.3 - "Deve marcar como lida e registrar data automaticamente"
- **RED**: Não registra data de leitura
- **GREEN**: No `marcarComoLida()`: `lida = true; dataLeitura = LocalDateTime.now()`
- **REFACTOR**: Lógica condicional otimizada

### NotificacaoServiceTest.java - RF003 (7 cenários)

#### CT009 - RF003.1 - "Deve criar notificação para OrdemServico válida"
- **RED**: Método `criarNotificacao()` não existe
- **GREEN**: Criação básica usando dados da OrdemServico
- **REFACTOR**: Extração de dados otimizada

#### CT010 - RF003.2 - "Deve falhar ao criar notificação para funcionário inativo"
- **RED**: Não valida status do funcionário
- **GREEN**: `if(!ordemServico.getFuncionario().isAtivo()) throw new IllegalArgumentException()`
- **REFACTOR**: Método de validação encapsulado

#### CT011 - RF003.3 - "Deve gerar mensagem personalizada com dados da ordem"
- **RED**: Mensagem genérica
- **GREEN**: Template: `"Ordem {id} - Funcionário {nome} - Status {status}"`
- **REFACTOR**: Builder de mensagens flexível

#### CT012 - RF003.4 - "Deve marcar notificação como lida corretamente"
- **RED**: Método `marcarComoLida()` não existe no service
- **GREEN**: Delega para `notificacao.marcarComoLida()`
- **REFACTOR**: Validações e otimizações

#### CT013 - RF003.5 - "Deve contar notificações não lidas por funcionário"
- **RED**: Método não existe
- **GREEN**: Lista em memória e contador simples
- **REFACTOR**: Estrutura de dados otimizada

#### CT014 - RF003.6 - "Deve falhar com OrdemServico nula"
- **RED**: NullPointerException
- **GREEN**: `if(ordemServico == null) throw new IllegalArgumentException()`
- **REFACTOR**: Validação de entrada robusta

#### CT015 - RF003.7 - "Deve falhar com tipo de notificação nulo"
- **RED**: Enum nulo não é tratado
- **GREEN**: `if(tipo == null) throw new IllegalArgumentException()`
- **REFACTOR**: Validação completa de parâmetros

---

## Integração com Estrutura Atual

### Dados Acessados via Relacionamentos
- **Funcionário**: Via `ordemServico.getFuncionario().getNome()`
- **Status Funcionário**: Via `ordemServico.getFuncionario().isAtivo()`
- **Serviços da Ordem**: Via `ordemServico.getItensServicos()`
- **Valores e Quantidades**: Via `ItemServico.getQuantidade()` e `Servico.getPreco()`
- **Status da Ordem**: Via `ordemServico.getStatus()`

### Compatibilidade com Sistema Existente
- **OrdemServico**: Alteração mínima (campo `itensServicos` já implementado)
- **Funcionario e Servico**: Sem alterações (compatibilidade total)
- **Services Existentes**: Sem impacto nas funcionalidades atuais

---

## Resultados TDD Esperados

### Distribuição de Cenários (Total: 15)
- **ItemServicoTest**: 5 cenários (CT001 a CT005) - RF001
- **NotificacaoTest**: 3 cenários (CT006 a CT008) - RF002
- **NotificacaoServiceTest**: 7 cenários (CT009 a CT015) - RF003

### Cobertura de Funcionalidades
- **RF001**: Cálculos de ItemServico com validações completas
- **RF002**: Entidade Notificacao com inicialização e controles
- **RF003**: Service NotificacaoService com lógica de negócio completa

### Evidências RED-GREEN-REFACTOR
Cada cenário seguirá rigorosamente:
1. **RED** → Teste falhando (método/validação não existe)
2. **GREEN** → Código mínimo para passar
3. **REFACTOR** → Melhorias mantendo testes verdes

---

## Resumo da Feature 1

**Sistema de Notificações** integrado ao CochitoServicosAPI:

1. **ItemServico** com cálculos de subtotal (RF001)
2. **Notificacao** para comunicação com funcionários (RF002)
3. **NotificacaoService** com lógica de negócio via TDD (RF003)
4. **15 cenários de teste** (CT001 a CT015) mapeados 1:1 com RFs
5. **Integração não invasiva** com sistema existente