import java.util.List;
import java.util.Scanner;

 
public class Main {
   
    private static Scanner scanner = new Scanner(System.in);
    private static ClinicaServico servico = new ClinicaServico();

    public static void main(String[] args) {
        seedConvenios();
        int opcao;
        do {
            exibirMenu();
            opcao = lerInteiro("Escolha uma opção: ");
            try {
                executarOpcao(opcao);
            } catch (PacienteNaoEncontradoException | ProfissionalNaoEncontradoException
                    | PacienteInativoException | HorarioIndisponivelException
                    | OperacaoInvalidaException | ConsultaNaoEncontradaException
                    | PagamentoInvalidoException | ConvenioNaoCobreException e) {    
            
                System.out.println("Erro: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("Dados inválidos: " + e.getMessage());
            }
        } while (opcao != 0);

        System.out.println("Sistema finalizado. Até logo!");
        scanner.close();
    }
    private static void seedConvenios() {
        Convenio saudePlus = new Convenio("SaúdePlus", 40.0);
        saudePlus.adicionarEspecialidadeCobertura("Fisioterapia");
        saudePlus.adicionarEspecialidadeCobertura("Psicologia");
        servico.cadastrarConvenio(saudePlus);

        Convenio vidaMais = new Convenio("VidaMais", 30.0);
        vidaMais.adicionarEspecialidadeCobertura("Nutrição");
        vidaMais.adicionarEspecialidadeCobertura("Clínica Geral");
        servico.cadastrarConvenio(vidaMais);

        Convenio bemEstar = new Convenio("BemEstar", 50.0);
        bemEstar.adicionarEspecialidadeCobertura("Fisioterapia");
        bemEstar.adicionarEspecialidadeCobertura("Nutrição");
        bemEstar.adicionarEspecialidadeCobertura("Psicologia");
        bemEstar.adicionarEspecialidadeCobertura("Clínica Geral");
        servico.cadastrarConvenio(bemEstar);
    }

    private static void exibirMenu() {
        System.out.println();
        System.out.println("===== VidaPlena - Sistema de Gestão de Clínica =====");
        System.out.println("1  - Cadastrar Paciente (Mínimo)");
        System.out.println("2  - Cadastrar Paciente (Completo)");
        System.out.println("3  - Cadastrar Profissional");
        System.out.println("4  - Cadastrar Horário Disponível para Profissional");
        System.out.println("5  - Agendar Consulta");
        System.out.println("6  - Cancelar Consulta");
        System.out.println("7  - Remarcar Consulta");
        System.out.println("8  - Registrar Atendimento");
        System.out.println("9  - Processar Pagamento");
        System.out.println("10 - Relatório Unificado de Pessoas");
        System.out.println("11 - Relatório de Pagamentos");
        System.out.println("12 - Buscar Paciente por CPF");
        System.out.println("13 - Desativar Paciente");
        System.out.println("14 - Exportar Dados");
        System.out.println("0  - Sair");
    }

    private static void executarOpcao(int opcao) throws PacienteNaoEncontradoException,
            ProfissionalNaoEncontradoException, PacienteInativoException, HorarioIndisponivelException,
            OperacaoInvalidaException, ConsultaNaoEncontradaException, PagamentoInvalidoException,
            ConvenioNaoCobreException {

        switch (opcao) {
            case 1:
                cadastrarPacienteMinimo();
                break;
            case 2:
                cadastrarPacienteCompleto();
                break;
            case 3:
                cadastrarProfissional();
                break;
            case 4:
                cadastrarHorario();
                break;
            case 5:
                agendarConsulta();
                break;
            case 6:
                cancelarConsulta();
                break;
            case 7:
                remarcarConsulta();
                break;
            case 8:
                registrarAtendimento();
                break;
            case 9:
                processarPagamento();
                break;
            case 10:
                servico.emitirRelatorioUnificadoDePessoas();
                break;
            case 11:
                servico.emitirRelatorioDePagamentos();
                break;
            case 12:
                buscarPaciente();
                break;
            case 13:
                desativarPaciente();
                break;
            case 14:
                exportarDados();
                break;
            case 0:
                break;
            default:
                System.out.println("Opção inválida.");
        }
    }
 
    private static void cadastrarPacienteMinimo() {
        System.out.print("Nome do paciente: ");
        String nome = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        servico.cadastrarPacienteMinimo(nome, cpf);
        System.out.println("Paciente cadastrado com sucesso.");
    }

    private static void cadastrarPacienteCompleto() {
        System.out.print("Nome do paciente: ");
        String nome = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        System.out.print("Data de nascimento (dd/mm/aaaa): ");
        String dataNascimento = scanner.nextLine();

        int idade = lerInteiro("Idade: ");

        Convenio convenio = null;
        System.out.print("Possui convênio? (s/n): ");
        String possuiConvenio = scanner.nextLine();
        if (possuiConvenio.equalsIgnoreCase("s")) {
            convenio = selecionarConvenio();
        }

        servico.cadastrarPacienteCompleto(nome, cpf, telefone, dataNascimento, idade, convenio);
        System.out.println("Paciente cadastrado com sucesso.");
    }

    private static Convenio selecionarConvenio() {
        List<Convenio> convenios = servico.getConvenios();
        if (convenios.isEmpty()) {
            System.out.println("Nenhum convênio cadastrado.");
            return null;
        }
        System.out.println("Convênios disponíveis:");
        for (int i = 0; i < convenios.size(); i++) {
            System.out.println((i + 1) + " - " + convenios.get(i).getNome());
        }
        int escolha = lerInteiro("Escolha o convênio: ");
        if (escolha < 1 || escolha > convenios.size()) {
            System.out.println("Opção inválida, paciente cadastrado sem convênio.");
            return null;
        }
        return convenios.get(escolha - 1);
    }

    private static void cadastrarProfissional() {
        System.out.println("Especialidades: 1-Fisioterapeuta 2-Psicólogo 3-Nutricionista 4-Clínico Geral");
        int especialidade = lerInteiro("Escolha a especialidade: ");

        System.out.print("Nome do profissional: ");
        String nome = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        System.out.print("Data de nascimento (dd/mm/aaaa): ");
        String dataNascimento = scanner.nextLine();
        System.out.print("Registro profissional: ");
        String registro = scanner.nextLine();
        double valorConsulta = lerDouble("Valor da consulta: R$ ");

        Profissional profissional;
        switch (especialidade) {
            case 1:
                int sessoes = lerInteiro("Quantidade de sessões previstas: ");
                profissional = new Fisioterapeuta(nome, cpf, telefone, dataNascimento, registro, valorConsulta, sessoes);
                break;
            case 2:
                System.out.print("Abordagem terapêutica: ");
                String abordagem = scanner.nextLine();
                profissional = new Psicologo(nome, cpf, telefone, dataNascimento, registro, valorConsulta, abordagem);
                break;
            case 3:
                System.out.print("Plano alimentar padrão: ");
                String plano = scanner.nextLine();
                profissional = new Nutricionista(nome, cpf, telefone, dataNascimento, registro, valorConsulta, plano);
                break;
            case 4:
                profissional = new ClinicoGeral(nome, cpf, telefone, dataNascimento, registro, valorConsulta);
                break;
            default:
                System.out.println("Especialidade inválida.");
                return;
        }
        servico.cadastrarProfissional(profissional);
        System.out.println("Profissional cadastrado com sucesso.");
    }

    private static void cadastrarHorario() throws ProfissionalNaoEncontradoException {
        System.out.print("Nome do profissional: ");
        String nome = scanner.nextLine();
        System.out.print("Dia da semana: ");
        String dia = scanner.nextLine();
        System.out.print("Turno (manhã/tarde): ");
        String turno = scanner.nextLine();

        Profissional profissional = servico.buscarProfissionalPorNome(nome);
        profissional.adicionarHorario(dia, turno);
        System.out.println("Horário cadastrado com sucesso.");
    }

    private static void agendarConsulta() throws PacienteNaoEncontradoException, PacienteInativoException,
            ProfissionalNaoEncontradoException, HorarioIndisponivelException {
        System.out.print("CPF do paciente: ");
        String cpf = scanner.nextLine();
        System.out.print("Nome do profissional: ");
        String nomeProfissional = scanner.nextLine();
        System.out.print("Data (dd/mm/aaaa): ");
        String data = scanner.nextLine();
        System.out.print("Horário: ");
        String horario = scanner.nextLine();

        servico.agendarConsulta(cpf, nomeProfissional, data, horario);
        System.out.println("Consulta agendada com sucesso.");
    }

    private static void cancelarConsulta() throws ConsultaNaoEncontradaException, OperacaoInvalidaException {
        Consulta consulta = localizarConsulta();
        System.out.print("Motivo do cancelamento: ");
        String motivo = scanner.nextLine();
        servico.cancelarConsulta(consulta, motivo);
        System.out.println("Consulta cancelada com sucesso.");
    }

    private static void remarcarConsulta() throws ConsultaNaoEncontradaException, OperacaoInvalidaException,
            HorarioIndisponivelException {
        Consulta consulta = localizarConsulta();
        System.out.print("Nova data: ");
        String novaData = scanner.nextLine();
        System.out.print("Novo horário: ");
        String novoHorario = scanner.nextLine();
        servico.remarcarConsulta(consulta, novaData, novoHorario);
        System.out.println("Consulta remarcada com sucesso.");
    }

    private static void registrarAtendimento() throws ConsultaNaoEncontradaException, OperacaoInvalidaException {
        Consulta consulta = localizarConsulta();
        System.out.print("Observações clínicas: ");
        String observacoes = scanner.nextLine();
        System.out.print("Diagnóstico: ");
        String diagnostico = scanner.nextLine();
        servico.registrarAtendimento(consulta, "registrado via sistema", observacoes, diagnostico);
        System.out.println("Atendimento registrado com sucesso.");
    }

    private static Consulta localizarConsulta() throws ConsultaNaoEncontradaException {
        System.out.print("CPF do paciente: ");
        String cpf = scanner.nextLine();
        System.out.print("Data da consulta: ");
        String data = scanner.nextLine();
        System.out.print("Horário da consulta: ");
        String horario = scanner.nextLine();
        return servico.buscarConsulta(cpf, data, horario);
    }

    private static void processarPagamento() throws PagamentoInvalidoException, ConvenioNaoCobreException,
            PacienteNaoEncontradoException {
        try {
            System.out.println("Tipo de pagamento: 1-Dinheiro 2-Cartão 3-Convênio");
            int tipo = lerInteiro("Escolha o tipo: ");
            double valor = lerDouble("Valor da consulta: R$ ");
            System.out.print("Data do pagamento: ");
            String data = scanner.nextLine();

            switch (tipo) {
                case 1:
                    servico.processarPagamentoDinheiro(valor, data);
                    break;
                case 2:
                    int parcelas = lerInteiro("Número de parcelas (1 a 6): ");
                    servico.processarPagamentoCartao(valor, data, parcelas);
                    break;
                case 3:
                    System.out.print("CPF do paciente: ");
                    String cpf = scanner.nextLine();
                    System.out.print("Especialidade da consulta: ");
                    String especialidade = scanner.nextLine();
                    Paciente paciente = servico.buscarPacientePorCpf(cpf);
                    servico.processarPagamentoConvenio(valor, data, paciente, especialidade);
                    break;
                default:
                    throw new PagamentoInvalidoException("Tipo de pagamento não reconhecido.");
            }
            System.out.println("Pagamento processado com sucesso.");
        } finally {
            System.out.println("--- Operação de pagamento finalizada ---");
        }
    }

    private static void buscarPaciente() throws PacienteNaoEncontradoException {
        System.out.print("CPF do paciente: ");
        String cpf = scanner.nextLine();
        Paciente paciente = servico.buscarPacientePorCpf(cpf);
        paciente.exibirResumo();
    }

    private static void desativarPaciente() throws PacienteNaoEncontradoException {
        System.out.print("CPF do paciente: ");
        String cpf = scanner.nextLine();
        servico.desativarPaciente(cpf);
        System.out.println("Paciente desativado com sucesso.");
    }

   
    private static void exportarDados() {
        try {
            List<String> exportacoes = servico.exportarTodosOsDados();
            System.out.println("===== Exportação de Dados =====");
            for (String linha : exportacoes) {
                System.out.println(linha);
            }
        } finally {
            System.out.println("--- Exportação finalizada ---");
        }
    }

    private static int lerInteiro(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String entrada = scanner.nextLine();
            try {
                return Integer.parseInt(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido. Por favor, digite um número inteiro.");
            }
        }
    }

    private static double lerDouble(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String entrada = scanner.nextLine();
            try {
                return Double.parseDouble(entrada);
            } catch (NumberFormatException e) {
                System.out.println("Valor inválido. Por favor, digite um número (use ponto para decimais).");
            }
        }
    }
}
