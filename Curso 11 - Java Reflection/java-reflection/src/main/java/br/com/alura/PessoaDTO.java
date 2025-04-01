package br.com.alura;

public class PessoaDTO {

    /**
     * removido porque quem faz essa construção é a classe de reflection
     * public PessoaDTO(String nome, String cpf) {
     * this.nome = nome;
     * this.cpf = cpf;
     * }
     */

    private Integer id;
    private String nome;
    private String cpf;

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

}
