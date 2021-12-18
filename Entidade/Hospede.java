package Entidade;

import java.lang.StringBuilder;

public class Hospede {
    public String nome;
    public String endereco;
    public int idade;

    public Hospede(String entradaNome,
            String entradaEndereco,
            int entradaIdade) {

        nome = entradaNome;
        endereco = entradaEndereco;
        idade = entradaIdade;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("Nome: %s\n", nome));
        sb.append(String.format("Endereço: %s\n", endereco));
        sb.append(String.format("Idade: %d\n", idade));

        return sb.toString();
    }
}
