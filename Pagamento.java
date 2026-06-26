
public abstract class Pagamento implements Exportavel {

    protected double valor;
    protected String dataPagamento;

    public Pagamento(double valor, String dataPagamento) {
        setValor(valor);
        this.dataPagamento = dataPagamento;
    }

   
    public abstract double calcularValorFinal();

 
    public String getTipo() {
        return this.getClass().getSimpleName();
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        if (valor < 0) {
            throw new IllegalArgumentException("Valor do pagamento não pode ser negativo.");
        }
        this.valor = valor;
    }

    public String getDataPagamento() {
        return dataPagamento;
    }

    @Override
    public String exportarDados() {
        return "Pagamento[tipo=" + getTipo() + ", valorOriginal=" + valor
                + ", valorFinal=" + calcularValorFinal() + ", data=" + dataPagamento + "]";
    }
}
