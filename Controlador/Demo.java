package Controlador;

import Entidade.Suite;
import Entidade.Enum.TipoSuite;

import java.util.Hashtable;
import java.util.Random;

public class Demo {
    public Hashtable<String, Suite> gerarDemo() {
        Hashtable<String, Suite> demo = new Hashtable<String, Suite>();

        demo.put("101", new Suite(TipoSuite.Simples, "101", 2, 100.00));
        demo.put("201", new Suite(TipoSuite.Luxo, "201", 4, 200.00));

        return demo;
    }

    public Hashtable<String, Suite> gerarDemoRand() {
        Hashtable<String, Suite> demoRand = new Hashtable<String, Suite>();

        Random rd = new Random();
        int qtd = 3;
        
        for (int i = 0; i < qtd; i++) {
            String num = String.format("%d", rd.nextInt(100, 999));

            demoRand.put(num,
                    new Suite(TipoSuite.values()[rd.nextInt(TipoSuite.values().length)],
                            num,
                            rd.nextInt(10),
                            rd.nextDouble(25.00, 999.99)));
        }

        return demoRand;
    }

}
