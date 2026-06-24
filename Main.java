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

    
}
