package br.com.alura.impressora;

public class Principal {

    public static void main(String[] args) throws InterruptedException {
        var impressora1 = new Impressora(1);
        var impressora2 = new Impressora(2);

        var thread1 = new Thread(impressora1);
        var thread2 = new Thread(impressora2);

        thread1.setPriority(1);
        thread2.setPriority(10);

        thread1.start();
        thread2.start();
    }
}