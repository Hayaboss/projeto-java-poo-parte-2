public class PagamentoDinheiro extends Pagamento {

    private static final double DESCONTO = 0.05; // 5% de desconto

    public PagamentoDinheiro(double valor, String dataPagamento) {
        super(valor, dataPagamento);
    }

    // SOBRESCRITA
    @Override
    public double calcularValorFinal() {
        return valor - (valor * DESCONTO);
    }
}
