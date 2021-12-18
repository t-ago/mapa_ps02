package Controlador.CadastroEntidade;

import Controlador.Cadastro;
import Entidade.Suite;
import Entidade.Enum.TipoSuite;
import Excecao.SuiteJaExisteException;
import Servico.ServicoEntrada;
import Servico.ServicoSaida;

import java.util.Hashtable;
import java.util.Map;

public class CadastroSuite extends Cadastro {
    private String perguntaTipoSuite = "Tipo (Simples/Luxo): ";
    private String perguntaNumero = "Número: ";
    private String perguntaCapacidade = "Capacidade: ";
    private String perguntaValorDiaria = "Valor da diária: ";

    private Hashtable<String, Suite> suitesEmMemoria;

    public CadastroSuite(ServicoEntrada e,
            ServicoSaida s,
            Hashtable<String, Suite> suitesRegistradas) {
        appEntrada = e;
        appSaida = s;
        suitesEmMemoria = suitesRegistradas;
    }

    public Suite novaSuite() {
        TipoSuite tipoLocal = definirTipoSuite();

        String numeroLocal;
        while (true) {
            try {
                numeroLocal = definirNumeroSuite();

                break;
            } catch (SuiteJaExisteException sjee) {
                appSaida.mostrarErro(sjee);
            }
        }

        int capacidadeLocal = definirCapacidade();
        double valorDiariaLocal = definirValorDiaria();

        return new Suite(tipoLocal,
                numeroLocal,
                capacidadeLocal,
                valorDiariaLocal);
    }

    private TipoSuite definirTipoSuite() {
        TipoSuite tipoLocal;

        appSaida.imprimir(perguntaTipoSuite);
        while (true) {
            try {
                tipoLocal = appEntrada.obterTipoSuiteDoUsuario();
                break;
            } catch (Exception e) {
                appSaida.mostrarErro(e);
            }
        }

        return tipoLocal;
    }

    private String definirNumeroSuite() throws SuiteJaExisteException {
        String numeroLocal;

        appSaida.imprimir(perguntaNumero);
        while (true) {
            try {
                numeroLocal = String.format("%d", appEntrada.obterIntDoUsuario());

                break;
            } catch (Exception e) {
                appSaida.mostrarErro(e);
            }
        }

        for (Map.Entry<String, Suite> entrada : suitesEmMemoria.entrySet()) {
            if (numeroLocal.compareTo(entrada.getKey()) == 0) {
                throw new SuiteJaExisteException("Suite com esse número já existe em memória.\n");
            }
        }

        return numeroLocal;
    }

    private int definirCapacidade() {
        int capacidadeLocal;

        appSaida.imprimir(perguntaCapacidade);
        while (true) {
            try {
                capacidadeLocal = appEntrada.obterIntPositivoDoUsuario();

                break;
            } catch (Exception e) {
                appSaida.mostrarErro(e);
            }
        }

        return capacidadeLocal;
    }

    private double definirValorDiaria() {
        double valorDiariaLocal;

        appSaida.imprimir(perguntaValorDiaria);
        while (true) {
            try {
                valorDiariaLocal = appEntrada.obterDoublePositivoDoUsuario();

                break;
            } catch (Exception e) {
                appSaida.mostrarErro(e);
            }
        }

        return valorDiariaLocal;
    }
}
