public class PagamentoCartao extends Pagamento {

    private int parcelas;
    private static final int LIMITE_SEM_TAXA = 3;
    private static final int LIMITE_MAXIMO = 6;
    private static final double TAXA_PARCELA_EXTRA = 0.025; 

    public PagamentoCartao(double valor, String dataPagamento, int parcelas) {
        super(valor, dataPagamento);
        setParcelas(parcelas);
    }

    public void setParcelas(int parcelas) {
        if (parcelas < 1 || parcelas > LIMITE_MAXIMO) {
            throw new IllegalArgumentException(
                    "Quantidade de parcelas inválida. Deve ser entre 1 e " + LIMITE_MAXIMO + ".");
        }
        this.parcelas = parcelas;
    }

    public int getParcelas() {
        return parcelas;
    }

    
    private double calcularTaxaAdicional() {
        if (parcelas <= LIMITE_SEM_TAXA) {
            return 0.0;
        }
        int parcelasExtras = parcelas - LIMITE_SEM_TAXA;
        return TAXA_PARCELA_EXTRA * parcelasExtras;
    }


    @Override
    public double calcularValorFinal() {
        double taxa = calcularTaxaAdicional();
        return valor + (valor * taxa);
    }
}
