package br.com.alura;

public class Pessoa {

    public Pessoa(Integer id, String nome, String cpf) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
    }

    private Integer id;
    private String nome;
    private String cpf;

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }
}
