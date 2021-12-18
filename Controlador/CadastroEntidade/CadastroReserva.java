package Controlador.CadastroEntidade;

import Controlador.Cadastro;
import Entidade.Hospede;
import Entidade.Reserva;
import Entidade.Suite;
import Excecao.CapacidadeDesrespeitadaException;
import Servico.ServicoEntrada;
import Servico.ServicoReserva;
import Servico.ServicoSaida;

import java.util.ArrayList;
import java.util.Hashtable;

public class CadastroReserva extends Cadastro {

    private String perguntaQuarto = "Digite o número do quarto a ser reservado: ";
    private String perguntaQtdHospedes = "Quantidade de Hóspedes (Incluindo o titular): ";
    private String perguntaQtdDiarias = "Quantidade de Diárias: ";
    private int qtdHospedesIsentos = 0;

    public CadastroReserva(ServicoEntrada e, ServicoSaida s) {
        appEntrada = e;
        appSaida = s;
    }

    public Reserva novaReserva(
            Hospede titular,
            Hashtable<String, Suite> quartos,
            ServicoReserva servicoReserva) throws CapacidadeDesrespeitadaException {

        String quartoLocal = definirQuarto(quartos);
        ArrayList<Hospede> hospedesLocal = definirHospedes(titular,
                quartos.get(quartoLocal));
        int qtdDiariasLocal = definirQtdDiarias();
        double subtotalLocal = definirTotal(servicoReserva,
                quartos.get(quartoLocal).valorDiaria,
                qtdDiariasLocal,
                hospedesLocal.size() - qtdHospedesIsentos);

        return new Reserva(quartos.get(quartoLocal),
                hospedesLocal,
                qtdDiariasLocal,
                titular,
                subtotalLocal);
    }

    private String definirQuarto(Hashtable<String, Suite> quartos) {
        String quartoLocal;

        appSaida.imprimir(perguntaQuarto);
        while (true) {
            quartoLocal = appEntrada.obterStringDoUsuario();

            try {
                if (verificarQuarto(quartos, quartoLocal)) {
                    break;
                }
            } catch (IllegalArgumentException e) {
                appSaida.mostrarErro(e);
            }
        }

        return quartoLocal;
    }

    private Boolean verificarQuarto(Hashtable<String, Suite> quartos,
            String entradaUsuario) throws IllegalArgumentException {
        if (!quartos.containsKey(entradaUsuario)) {
            throw new IllegalArgumentException("Quarto não encontrado no sistema. Tente novamente: ");
        }

        if (quartos.get(entradaUsuario).estaOcupado) {
            throw new IllegalArgumentException("Quarto ocupado. Escolha outro quarto: ");
        }

        return true;
    }

    private ArrayList<Hospede> definirHospedes(Hospede titular, Suite quartoDesejado)
            throws CapacidadeDesrespeitadaException {
        ArrayList<Hospede> hospedesLocal = obterHospedes(titular);
        verificarCapacidadeReserva(hospedesLocal, quartoDesejado);

        return hospedesLocal;
    }

    private ArrayList<Hospede> obterHospedes(Hospede titular) {
        ArrayList<Hospede> hospedesLocal = new ArrayList<Hospede>();

        int qtdHospedesLocal = 1;
        appSaida.imprimir(perguntaQtdHospedes);
        while (true) {
            int qtdHospedesUsuario;

            try {
                qtdHospedesUsuario = appEntrada.obterIntPositivoDoUsuario();

                if (qtdHospedesLocal != qtdHospedesUsuario) {
                    qtdHospedesLocal += qtdHospedesUsuario;

                    CadastroHospede ch = new CadastroHospede(appEntrada, appSaida);

                    for (int i = 0; i < qtdHospedesUsuario - 1; i++) {
                        appEntrada.prepararProximaTela();
                        appSaida.imprimir(String.format("\nRegistrando " +
                                "hóspede adicional %d de %d:\n\n",
                                i + 1,
                                qtdHospedesUsuario - 1));

                        hospedesLocal.add(ch.novoHospede());
                    }

                    appEntrada.prepararProximaTela();
                }
                hospedesLocal.add(titular);

                break;
            } catch (RuntimeException e) {
                appSaida.mostrarErro(e);
            }
        }

        return hospedesLocal;
    }

    private int definirQtdDiarias() {
        int qtdDiariasLocal;

        appSaida.imprimir(perguntaQtdDiarias);
        while (true) {
            try {
                qtdDiariasLocal = appEntrada.obterIntPositivoDoUsuario();
                break;
            } catch (RuntimeException e) {
                appSaida.mostrarErro(e);
            }
        }

        return qtdDiariasLocal;
    }

    private void verificarCapacidadeReserva(ArrayList<Hospede> hospedesLocal,
            Suite quartoDesejado) throws CapacidadeDesrespeitadaException {
        int contadorCapacidade = 0;

        for (int i = 0; i < hospedesLocal.size(); i++) {
            if (hospedesLocal.get(i).idade > 2) {
                contadorCapacidade++;
            } else {
                qtdHospedesIsentos++;
            }
        }

        if (contadorCapacidade > quartoDesejado.capacidade) {
            throw new CapacidadeDesrespeitadaException("Capacidade da suíte excedida." +
                    " Escolha outra suíte e tente novamente.",
                    contadorCapacidade);
        }
    }

    private double definirTotal(ServicoReserva servicoReserva,
            double valorDiaria,
            int qtdDiariasLocal,
            int qtdHospedesLocal) {
        return servicoReserva.calcularValorReserva(valorDiaria,
                qtdDiariasLocal,
                qtdHospedesLocal);
    }
}
