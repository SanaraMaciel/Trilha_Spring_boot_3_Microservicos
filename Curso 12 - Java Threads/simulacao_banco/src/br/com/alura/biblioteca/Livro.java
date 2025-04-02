package br.com.alura.biblioteca;

import java.time.LocalDate;

public class Livro {
    private String nome;
    private String autor;
    private LocalDate dataLancamento;
    private boolean reservado;

    public boolean getReservado() {
        return reservado;
    }

    public Livro(String nome, String autor, LocalDate dataLancamento) {
        this.nome = nome;
        this.autor = autor;
        this.dataLancamento = dataLancamento;
    }

    public String getNome() {
        return nome;
    }

    public String getAutor() {
        return autor;
    }

    public LocalDate getDataLancamento() {
        return dataLancamento;
    }

    public void reservar() {
        this.reservado = true;
    }
}