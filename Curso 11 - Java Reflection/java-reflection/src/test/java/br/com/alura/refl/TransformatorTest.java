package br.com.alura.refl;

import br.com.alura.Endereco;
import br.com.alura.Pessoa;
import br.com.alura.PessoaDTO;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.*;

public class TransformatorTest {

    Pessoa pessoa = new Pessoa(1, "João", "1234");
    Endereco endereco = new Endereco("Rua 10", 20);

    //Usando o padrão fixture para criar pessoa sem precisar instanciar e outras classes de testes poderem usar
    //Pessoa pessoa = PessoaFixture.buildPessoa();

    @Test
    public void shouldTransform() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Transformator transformator = new Transformator();
        PessoaDTO pessoaDTO = transformator.transform(pessoa);

        Assertions.assertInstanceOf(PessoaDTO.class, pessoaDTO);
        Assertions.assertEquals(pessoa.getId(), pessoaDTO.getId());
        Assertions.assertEquals(pessoa.getNome(), pessoaDTO.getNome());
        Assertions.assertEquals(pessoa.getCpf(), pessoaDTO.getCpf());
    }

    @Test
    public void shouldNotTransform() {
        Assertions.assertThrows(ClassNotFoundException.class, () -> {
            Transformator transformator = new Transformator();
            transformator.transform(endereco);
        });
    }

    @Test
    public void shouldTransformWhenSomeFieldIsNull() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Pessoa pessoaSemCPF = new Pessoa(1,"João",null);
        Transformator transformator = new Transformator();
        PessoaDTO pessoaDTOSemCPF = transformator.transform(pessoaSemCPF);

        Assertions.assertEquals(pessoa.getNome(), pessoaDTOSemCPF.getNome());
        Assertions.assertNull(pessoaDTOSemCPF.getCpf());
    }
}