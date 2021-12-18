package Servico;

public interface ServicoSaida {
    public void telaInicial();

    public void imprimir(String string);

    public void mostrarErro(Exception e);

    public void mostrarErro(String s);
}
