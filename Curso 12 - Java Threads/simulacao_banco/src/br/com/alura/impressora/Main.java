package br.com.alura.impressora;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        var tarefa = new RealizaTarefa();
        var thread1 = new Thread(tarefa);
        System.out.println(thread1.isAlive());
        thread1.start();
        thread1.join();
        System.out.println(thread1.isAlive());
    }
}