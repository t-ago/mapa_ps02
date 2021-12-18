package Visualizacao.CLI;

import Entidade.Enum.TipoSuite;
import Visualizacao.Visualizacao;

import java.util.Scanner;

public class VisualizacaoCLI extends Visualizacao {
    private Scanner s;

    public VisualizacaoCLI() {
        s = new Scanner(System.in);
    }

    // ENTRADA

    public String obterStringDoUsuario() {
        return s.nextLine();
    }

    public String obterNomeDoUsuario() {
        String nomeLocal = s.nextLine();

        if (nomeLocal.matches(".*\\d.*")) {
            throw new RuntimeException("Digite um nome válido (Somente letras): ");
        }

        return nomeLocal;
    }

    public String obterEnderecoDoUsuario() {
        return s.nextLine();
    }

    public int obterMaioridadeDoUsuario() {
        int idadeLocal = obterIdadeDoUsuario();

        if (idadeLocal < 18) {
            throw new RuntimeException("Digite uma idade válida (18+): ");
        }

        return idadeLocal;
    }

    public int obterIdadeDoUsuario() {
        int idadeLocal = obterIntPositivoDoUsuario();

        if (idadeLocal < 1 || idadeLocal > 122) {
            throw new RuntimeException("Digite uma idade válida: ");
        }

        return idadeLocal;
    }

    public int obterIntDoUsuario() {
        if (!s.hasNextInt()) {
            consumirLinha();
            throw new RuntimeException("Digite somente números: ");
        }

        return s.nextInt();
    }

    public int obterIntPositivoDoUsuario() {
        int entradaDoUsuario = obterIntDoUsuario();

        if (entradaDoUsuario < 1) {
            throw new RuntimeException("Digite somente números positivos: ");
        }

        return entradaDoUsuario;
    }

    public double obterDoubleDoUsuario() {
        if (!s.hasNextDouble()) {
            consumirLinha();
            throw new RuntimeException("Digite somente números: ");
        }

        return s.nextDouble();
    }

    public double obterDoublePositivoDoUsuario() {
        double entradaDoUsuario = obterDoubleDoUsuario();

        if (entradaDoUsuario < 1) {
            throw new RuntimeException("Digite somente números positivos (Máx. 2 casas decimais): ");
        }

        return entradaDoUsuario;
    }

    public TipoSuite obterTipoSuiteDoUsuario() {
        TipoSuite saida;
        String entradaDoUsuario = obterStringDoUsuario().toLowerCase();

        switch (entradaDoUsuario) {
            case "simples":
                saida = TipoSuite.Simples;
                break;
            case "luxo":
                saida = TipoSuite.Luxo;
                break;
            default:
                throw new RuntimeException("Escolha uma opção válida (Simples/Luxo): ");
        }

        return saida;
    }

    // SAIDA

    public void telaInicial() {
        limparTela();
        System.out.println("SERVIÇO DE RESERVA DE SUÍTES - HOTEL FooBar\n\n" +
                "1 - Nova Reserva (Requer Suíte(s) e Hóspede(s) em Memória)\n" +
                "2 - Nova Suíte\n" +
                "3 - Exibir todas as suítes\n" +
                "4 - Exibir todas as reservas\n" +
                "0 - Sair" +
                "\n\n");
    }

    public void imprimir(String string) {
        System.out.print(string);
    }

    public void prepararProximaTela() {
        System.out.print("\nPressione ENTER para continuar.");
        consumirLinha();
        limparTela();
    }

    public void limparTela() {
        System.out.print("\033[H\033[2J");
        // System.out.flush();
    }

    public void consumirLinha() {
        s.nextLine();
    }

    public void mostrarErro(Exception e) {
        System.out.print("ERRO: " + e.getMessage());
    }

    public void mostrarErro(String s) {
        System.out.print("ERRO: " + s);
    }
}