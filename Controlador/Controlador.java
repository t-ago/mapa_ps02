package Controlador;

import Servico.ServicoReserva;
import Entidade.Hospede;
import Entidade.Suite;
import Entidade.Reserva;

import java.util.Hashtable;
import java.util.ArrayList;

public abstract class Controlador {
    protected Hashtable<String, Suite> suitesEmMemoria;
    protected Hashtable<String, Reserva> reservasEmMemoria;
    protected ArrayList<Hospede> pessoasEmMemoria;
    protected ServicoReserva servicoReserva;

}
