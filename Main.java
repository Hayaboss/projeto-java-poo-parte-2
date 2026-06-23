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
}
