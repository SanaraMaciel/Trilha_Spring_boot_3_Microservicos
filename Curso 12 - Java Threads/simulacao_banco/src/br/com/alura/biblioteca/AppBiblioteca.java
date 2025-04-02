package br.com.alura.biblioteca;


import java.time.LocalDate;

public class AppBiblioteca {
    public static void main(String[] args) {
        var livro = new Livro("Percy Jackson e os Olimpianos", "Rick Riordan", LocalDate.of(2002, 04, 06));
        var operacao = new OperacaoReserva(livro);

        Thread reservaJoao = new Thread(operacao, "João");
        Thread reservaMaria = new Thread(operacao, "Maria");

        reservaMaria.start();
        reservaJoao.start();

        try {
            reservaMaria.join();
            reservaJoao.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Reservas finalizadas!");
    }

}