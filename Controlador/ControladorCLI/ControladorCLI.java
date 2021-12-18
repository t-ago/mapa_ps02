package Controlador.ControladorCLI;

import Controlador.Demo;

import Controlador.Controlador;
import Controlador.CadastroEntidade.CadastroHospede;
import Controlador.CadastroEntidade.CadastroReserva;
import Controlador.CadastroEntidade.CadastroSuite;
import Entidade.Hospede;
import Entidade.Suite;
import Excecao.CapacidadeDesrespeitadaException;
import Excecao.SemSuitesDisponiveisException;
import Entidade.Reserva;
import Servico.ServicoControlador;
import Servico.ServicoReservaSimples.ServicoReservaSimples;
import Visualizacao.CLI.VisualizacaoCLI;

import java.util.Hashtable;
import java.util.Map;
import java.util.ArrayList;

public class ControladorCLI extends Controlador implements ServicoControlador {

    VisualizacaoCLI c;

    /*
     * public ControladorCLI() {
     * c = new VisualizacaoCLI();
     * suitesEmMemoria = new Hashtable<String, Suite>();
     * reservasEmMemoria = new Hashtable<String, Reserva>();
     * pessoasEmMemoria = new ArrayList<Hospede>();
     * 
     * executarPrograma();
     * }
     */

    public ControladorCLI(String arg) {
        c = new VisualizacaoCLI();

        System.out.println("Digite \"-demo\" " +
                "para inicializar dois quartos predefinidos do tipo Simples e Luxo," +
                " com capacidades 2 e 4, e diárias $100 e $200, respectivamente, ou" +
                " \"-demorand\" para inicializar 3 quartos aleatórios.\n\n" +
                "Se deseja inicializar com a memória vazia, tecle enter.\n\n");
        String opcao = c.obterStringDoUsuario();

        if (opcao.compareToIgnoreCase("-demo") == 0) {
            suitesEmMemoria = new Demo().gerarDemo();
        } else if (opcao.compareToIgnoreCase("-demorand") == 0) {
            suitesEmMemoria = new Demo().gerarDemoRand();
        } else {
            suitesEmMemoria = new Hashtable<String, Suite>();
        }

        if (arg.compareToIgnoreCase("-simples") == 0) {
            servicoReserva = new ServicoReservaSimples();

        }

        reservasEmMemoria = new Hashtable<String, Reserva>();
        pessoasEmMemoria = new ArrayList<Hospede>();
        c.prepararProximaTela();

        executarPrograma();
    }

    public void executarPrograma() {
        Boolean ligaOuDesliga = true;

        while (ligaOuDesliga) {
            c.telaInicial();
            String entradaDoUsuario = c.obterStringDoUsuario();
            c.prepararProximaTela();

            switch (entradaDoUsuario) {
                case "1":
                    registrarNovaReserva();

                    break;
                case "2":
                    registrarNovaSuite();

                    break;
                case "3":
                    exibirSuites();

                    break;
                case "4":
                    exibirReservas();

                    break;
                case "0":
                    ligaOuDesliga = false;

                    break;
                default:
                    c.imprimir("Insira uma opção válida no menu.");
                    c.prepararProximaTela();

                    break;
            }
        }
    }

    private void registrarNovaReserva() {
        Reserva reservaLocal;
        CadastroHospede cadastroHospede = new CadastroHospede(c, c);
        CadastroReserva cadastroReserva = new CadastroReserva(c, c);

        c.imprimir("Registrando o hóspede titular:\n\n");
        Hospede titularLocal = cadastroHospede.novoHospede();

        while (true) {
            try {
                c.prepararProximaTela();
                exibirSuitesDisponiveis();

                reservaLocal = cadastroReserva.novaReserva(titularLocal,
                        suitesEmMemoria,
                        servicoReserva);

                break;
            } catch (CapacidadeDesrespeitadaException cde) {
                Suite suiteDisponivel = null;

                for (Map.Entry<String, Suite> entrada : suitesEmMemoria.entrySet()) {
                    if (entrada.getValue().capacidade > cde.qtdOfensiva) {
                        suiteDisponivel = entrada.getValue();

                        break;
                    }
                }

                try {
                    if (suiteDisponivel != null) {
                        c.mostrarErro(cde);
                        c.imprimir(String.format("\nO quarto %s está disponível " +
                                "e possui capacidade para %d pessoas adultas.",
                                suiteDisponivel.numero,
                                suiteDisponivel.capacidade));

                        continue;
                    } else {
                        throw new SemSuitesDisponiveisException(
                                "Não há suítes disponíveis para essa quantidade de pessoas.");
                    }
                } catch (SemSuitesDisponiveisException ssde) {
                    c.mostrarErro(ssde);
                    c.prepararProximaTela();

                    return;
                }
            } catch (SemSuitesDisponiveisException ssde) {
                c.mostrarErro(ssde);
                c.prepararProximaTela();

                return;
            }
        }

        for (int i = 0; i < reservaLocal.hospedes.size(); i++) {
            pessoasEmMemoria.add(reservaLocal.hospedes.get(i));
        }

        suitesEmMemoria.get(reservaLocal.quarto.numero).estaOcupado = true;
        reservasEmMemoria.put(reservaLocal.quarto.numero, reservaLocal);

        c.prepararProximaTela();
    }

    private void registrarNovaSuite() {
        CadastroSuite cadastroSuite = new CadastroSuite(c, c, suitesEmMemoria);
        Suite suiteLocal = cadastroSuite.novaSuite();

        suitesEmMemoria.put(suiteLocal.numero, suiteLocal);

        c.prepararProximaTela();

    }

    private void exibirSuites() {
        for (Map.Entry<String, Suite> entrada : suitesEmMemoria.entrySet()) {
            System.out.println(entrada.getValue());
        }

        c.prepararProximaTela();
    }

    private void exibirSuitesDisponiveis() throws SemSuitesDisponiveisException {
        System.out.print("\n- Quartos disponíveis: ");

        int n = 0;
        for (Map.Entry<String, Suite> entrada : suitesEmMemoria.entrySet()) {
            if (!entrada.getValue().estaOcupado) {
                System.out.print(entrada.getValue().numero);
                System.out.print(" ");

                n++;
            }
        }
        if (n == 0) {
            throw new SemSuitesDisponiveisException("Não há suítes disponíveis.");
        }

        System.out.println("\n");
    }

    private void exibirReservas() {
        for (Map.Entry<String, Reserva> entrada : reservasEmMemoria.entrySet()) {
            System.out.println(entrada.getValue());

        }

        c.prepararProximaTela();
    }
}
