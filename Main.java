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

 
}
