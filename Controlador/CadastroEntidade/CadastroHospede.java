package Controlador.CadastroEntidade;

import Controlador.Cadastro;
import Entidade.Hospede;
import Servico.ServicoEntrada;
import Servico.ServicoSaida;

public class CadastroHospede extends Cadastro {
    private String perguntaNome = "Nome: ";
    private String perguntaEndereco = "Endereço: ";
    private String perguntaIdade = "Idade: ";

    public CadastroHospede(ServicoEntrada e, ServicoSaida s) {
        appEntrada = e;
        appSaida = s;
    }

    public Hospede novoHospede() {
        String nomeLocal = definirNome();
        String enderecoLocal = definirEndereco();
        int idadeLocal = definirIdade();

        return new Hospede(nomeLocal, enderecoLocal, idadeLocal);
    }

    private String definirNome() {
        String nomeLocal;

        appSaida.imprimir(perguntaNome);
        while (true) {
            try {
                nomeLocal = appEntrada.obterNomeDoUsuario();
                break;
            } catch (Exception e) {
                appSaida.mostrarErro(e);
            }
        }

        return nomeLocal;
    }

    private String definirEndereco() {
        String enderecoLocal;

        appSaida.imprimir(perguntaEndereco);
        while (true) {
            try {
                enderecoLocal = appEntrada.obterEnderecoDoUsuario();
                break;
            } catch (Exception e) {
                appSaida.mostrarErro(e);
            }
        }

        return enderecoLocal;
    }

    private int definirIdade() {
        int idadeLocal;

        appSaida.imprimir(perguntaIdade);
        while (true) {
            try {
                idadeLocal = appEntrada.obterIdadeDoUsuario();
                break;
            } catch (Exception e) {
                appSaida.mostrarErro(e);
                ;
            }
        }

        return idadeLocal;
    }
}
