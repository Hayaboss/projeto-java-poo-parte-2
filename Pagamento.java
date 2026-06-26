/**
 * Classe abstrata que representa uma forma de pagamento.
 * Implementa Exportavel: todo pagamento pode ser exportado de forma padronizada.
 */
public abstract class Pagamento implements Exportavel {

    protected double valor;
    protected String dataPagamento;

    public Pagamento(double valor, String dataPagamento) {
        setValor(valor);
        this.dataPagamento = dataPagamento;
    }

    // Método abstrato: cada forma de pagamento calcula o valor final de um jeito diferente
    public abstract double calcularValorFinal();

    // Método concreto comum a todas as formas de pagamento (R6)
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

    // SOBRESCRITA possível pelas subclasses, mas já fornece implementação padrão
    @Override
    public String exportarDados() {
        return "Pagamento[tipo=" + getTipo() + ", valorOriginal=" + valor
                + ", valorFinal=" + calcularValorFinal() + ", data=" + dataPagamento + "]";
    }
}
