package Servico.ServicoReservaSimples;

import Servico.ServicoReserva;

public class ServicoReservaSimples implements ServicoReserva {

    public double calcularValorReserva(double valorDiaria,
            int qtdDiarias,
            int qtdPessoas) {

        double total;
        double desconto = 0;

        if (qtdDiarias > 7) {
            desconto = 0.1;
        }

        total = (valorDiaria * qtdDiarias);
        total -= (total * desconto);

        return total;
    }
}
