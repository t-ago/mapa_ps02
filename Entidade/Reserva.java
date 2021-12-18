package Entidade;

import java.util.ArrayList;
import java.lang.StringBuilder;

public class Reserva {
        public Suite quarto;
        public ArrayList<Hospede> hospedes;
        public int qtdDiarias;
        public Hospede titularReserva;
        public double valorReserva;

        public Reserva(Suite entradaQuarto,
                        ArrayList<Hospede> entradaHospedes,
                        int entradaQtdDiarias,
                        Hospede entradaTitular,
                        double entradaValorReserva) {
                quarto = entradaQuarto;
                hospedes = entradaHospedes;
                qtdDiarias = entradaQtdDiarias;
                titularReserva = entradaTitular;
                valorReserva = entradaValorReserva;
        }

        @Override
        public String toString() {
                StringBuilder sb = new StringBuilder();

                sb.append("RESERVA: ");
                sb.append(quarto);
                sb.append(String.format("        " +
                                "Hóspede Responsável: %s\n", titularReserva.nome));

                if (hospedes.size() > 1) {
                        sb.append("        " +
                                        "Outros hóspedes: ");

                        for (int i = 0; i < hospedes.size() - 1; i++) {
                                if (i + 1 < hospedes.size() - 1) {
                                        sb.append(String.format("%s, ", hospedes.get(i).nome));
                                } else {
                                        sb.append(String.format("%s\n", hospedes.get(i).nome));
                                }
                        }
                }

                sb.append(String.format("        " +
                                "Quantidade de Diárias: %d\n", qtdDiarias));
                sb.append(String.format("        " +
                                "Valor total: $%.2f", valorReserva));
                sb.append("\n");

                return sb.toString();
        }
}
