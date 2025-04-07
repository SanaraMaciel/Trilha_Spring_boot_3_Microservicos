package br.com.alura;

public class ExecutarTarefa implements Runnable{

    private static int CONTADOR = 0;

    @Override
    public void run() {
        CONTADOR++;
        System.out.println("Tarefa numero: " + CONTADOR + " inicializada");
    }
}

