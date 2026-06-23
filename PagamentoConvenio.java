public class PagamentoConvenio extends Pagamento {

    private Convenio convenio;
    private String especialidadeConsulta;

    public PagamentoConvenio(double valor, String dataPagamento, Convenio convenio, String especialidadeConsulta) {
        super(valor, dataPagamento);
        this.convenio = convenio;
        this.especialidadeConsulta = especialidadeConsulta;
    }

    public Convenio getConvenio() {
        return convenio;
    }

    public boolean coberturaValida() {
        return convenio != null && convenio.coobreEspecialidade(especialidadeConsulta);
    }

    // SOBRESCRITA
    @Override
    public double calcularValorFinal() {
        double percentual = convenio.getPercentualCobertura();
        double abatimento = valor * (percentual / 100.0);
        return valor - abatimento;
    }
}
