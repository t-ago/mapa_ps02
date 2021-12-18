package Entidade;

import Entidade.Enum.TipoSuite;

import java.lang.StringBuilder;

public class Suite {
    public TipoSuite tipoSuite;
    public String numero;
    public int capacidade;
    public double valorDiaria;
    public Boolean estaOcupado;

    public Suite(TipoSuite entradaTipoSuite,
            String entradaNumero,
            int entradaCapacidade,
            double entradaValorDiaria) {
        tipoSuite = entradaTipoSuite;
        numero = entradaNumero;
        capacidade = entradaCapacidade;
        valorDiaria = entradaValorDiaria;
        estaOcupado = false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("Suíte #%s\n\n", numero));
        sb.append(String.format("   Tipo de Suíte: %s\n", tipoSuite.toString()));
        sb.append(String.format("   Capacidade: %d\n", capacidade));
        sb.append(String.format("   Valor da diária: $%.2f\n", valorDiaria));

        sb.append("   Status: ");
        if (estaOcupado) {
            sb.append("OCUPADO");
        } else {
            sb.append("LIVRE");
        }
        sb.append("\n");

        return sb.toString();
    }
}
