package br.com.alura.impressora;

public class RealizaTarefa implements Runnable{

    @Override
    public void run() {
        System.out.println("Olá, mundo! ");
    }
}