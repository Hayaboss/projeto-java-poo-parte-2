# VidaPlena — Sistema de Gestão de Clínica Multidisciplinar (AV2)

Sistema de console em Java para gestão de uma clínica multidisciplinar:
cadastro de pacientes e profissionais, agendamento de consultas, registro de
atendimentos e processamento de pagamentos (dinheiro, cartão e convênio).

Este projeto é a evolução (refatoração) da versão AV1, agora aplicando
herança, polimorfismo, interfaces, coleções e tratamento de exceções.

Documentação complementar:
- [Diagrama de classes completo](docs/diagrama-classes.md)
- [Mapa de aplicação dos conceitos](docs/mapa-de-conceitos.md)

## Requisitos

- JDK 11 ou superior.

## Como compilar

```
cd src
javac -encoding UTF-8 -d ../bin *.java
```

## Como executar

```
cd bin
java -Dfile.encoding=UTF-8 Main
```

> Observação: a flag `-Dfile.encoding=UTF-8` evita que acentos apareçam
> como "?" no terminal em alguns sistemas. Em JDKs mais novos (18+) isso
> já costuma vir configurado por padrão.

## Funcionalidades desenvolvidas

- Cadastro de paciente (mínimo e completo, com vínculo a convênio)
- Cadastro de profissional por especialidade (Fisioterapeuta, Psicólogo,
  Nutricionista, Clínico Geral)
- Cadastro de horários disponíveis para profissionais
- Agendamento, cancelamento e remarcação de consultas
- Registro de atendimento com prontuário (observações, diagnóstico e
  procedimentos específicos da especialidade do profissional)
- Processamento de pagamento em dinheiro (5% de desconto), cartão
  (parcelamento de até 6x, com taxa de 2,5% por parcela acima da 3ª) e
  convênio (cobertura percentual por especialidade)
- Relatório unificado de cadastros (polimorfismo sobre `List<Pessoa>`)
- Relatório de pagamentos (polimorfismo sobre `List<Pagamento>`)
- Busca de paciente por CPF e desativação de paciente
- Exportação padronizada de consultas, atendimentos e pagamentos

Três convênios já vêm pré-cadastrados ao iniciar o sistema: **SaúdePlus**
(40%, cobre Fisioterapia e Psicologia), **VidaMais** (30%, cobre Nutrição e
Clínica Geral) e **BemEstar** (50%, cobre todas as especialidades).

## Como realizar as operações do sistema

Ao executar `Main`, um menu numerado é exibido. Basta digitar o número da
opção e seguir as instruções na tela. Resumo de cada opção:

| Opção | Operação | O que é pedido |
|---|---|---|
| 1 | Cadastrar Paciente (Mínimo) | Nome e CPF |
| 2 | Cadastrar Paciente (Completo) | Nome, CPF, telefone, data de nascimento, idade e, opcionalmente, convênio |
| 3 | Cadastrar Profissional | Especialidade (1 a 4), dados gerais e dado específico da especialidade (ex.: sessões previstas, abordagem terapêutica) |
| 4 | Cadastrar Horário Disponível | Nome do profissional, dia da semana e turno |
| 5 | Agendar Consulta | CPF do paciente, nome do profissional, data e horário |
| 6 | Cancelar Consulta | CPF, data e horário da consulta + motivo |
| 7 | Remarcar Consulta | CPF, data e horário da consulta original + nova data/horário |
| 8 | Registrar Atendimento | CPF, data e horário da consulta + observações e diagnóstico |
| 9 | Processar Pagamento | Tipo (dinheiro/cartão/convênio), valor e dados específicos do tipo escolhido |
| 10 | Relatório Unificado de Pessoas | — (lista todos os cadastros com `exibirResumo()`) |
| 11 | Relatório de Pagamentos | — (lista todos os pagamentos com valor original e final) |
| 12 | Buscar Paciente por CPF | CPF |
| 13 | Desativar Paciente | CPF |
| 14 | Exportar Dados | — (exporta consultas, atendimentos e pagamentos) |
| 0 | Sair | — |

Entradas inválidas (texto onde se espera número, CPF já cadastrado, paciente
inativo, horário ocupado, convênio sem cobertura, etc.) nunca encerram o
programa — uma mensagem explicativa é exibida e o menu é exibido novamente.
