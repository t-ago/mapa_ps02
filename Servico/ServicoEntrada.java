package Servico;

import Entidade.Enum.TipoSuite;

public interface ServicoEntrada {
    public String obterStringDoUsuario();

    public String obterNomeDoUsuario();

    public String obterEnderecoDoUsuario();

    public int obterIntDoUsuario();

    public int obterIntPositivoDoUsuario();

    public double obterDoublePositivoDoUsuario();

    public int obterMaioridadeDoUsuario();

    public int obterIdadeDoUsuario();

    public TipoSuite obterTipoSuiteDoUsuario();

    public void prepararProximaTela();
}
