public class PagamentoDinheiro extends Pagamento {

    private static final double DESCONTO = 0.05; 

    public PagamentoDinheiro(double valor, String dataPagamento) {
        super(valor, dataPagamento);
    }

  
    @Override
    public double calcularValorFinal() {
        return valor - (valor * DESCONTO);
    }
}
