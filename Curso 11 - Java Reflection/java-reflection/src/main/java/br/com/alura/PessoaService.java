package br.com.alura;

import br.com.alura.refl.Transformator;

import java.lang.reflect.InvocationTargetException;

public class PessoaService {

    public PessoaDTO list() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Pessoa pessoa = new PessoaRepository().list();

        //chama o transformador passando a classe Pessoa como origem
        PessoaDTO pessoaDTO =  new Transformator().transform(pessoa);

        //PessoaDTO pessoaDTO = new PessoaDTO(pessoa.getNome(), pessoa.getCpf());
        return pessoaDTO;
    }
}
