package br.com.alura.refl;

import br.com.alura.Pessoa;
import br.com.alura.PessoaDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

public class TransformGenericTest {


    @Test
    public void shouldTransform() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException {

        TransformGeneric transformator = new TransformGeneric();
        Pessoa pessoa = new Pessoa(1, "João", "1234");

        PessoaDTO transformated = transformator.transform(pessoa, PessoaDTO.class);

        Assertions.assertInstanceOf(PessoaDTO.class, transformated);

        Assertions.assertEquals("João", transformated.getNome());
        //Assertions.assertEquals(32, transformated.getIdade());
        Assertions.assertEquals("04333958210", transformated.getCpf());
    }


}