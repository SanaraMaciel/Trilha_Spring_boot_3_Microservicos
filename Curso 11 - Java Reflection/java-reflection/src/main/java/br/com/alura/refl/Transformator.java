package br.com.alura.refl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;


public class Transformator {

    public <I, O> O transform(I input) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        //pega o tipo da classe que você vai usar para fazer a transformação
        Class<?> source = input.getClass();

        //transforma uma classe em outra adicionando o prefixo DTO
        Class<?> target = Class.forName(source + "DTO");

        //busca o construtor dessa classe
        O targetClass = (O) target.getDeclaredConstructor().newInstance();

        //pega os campos da classe
        Field[] sourceFields = source.getDeclaredFields(); //campos da classe origem
        Field[] targetFields = target.getDeclaredFields(); //campos da classe saida


        //valida se os campos são iguais tanto no nome quanto no tipo ex: Nome ~ String
        //percorrendo todos os campos da classe
        Arrays.stream(sourceFields).forEach(sourceField ->
                Arrays.stream(targetFields).forEach(targetField -> {
                            validate(sourceField, targetField); //funcao que valida os campos
                            try {
                                targetField.set(targetClass, sourceField.get(input)); //seta o valor na classe dto conforme veio no input
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                                throw new RuntimeException(e);
                            }
                        }
                ));


        return targetClass;
    }

    /**
     * valida se o nome e o tipo dos atributos são iguais
     *
     * @param sourceField
     * @param targetField
     */
    private void validate(Field sourceField, Field targetField) {
        if (sourceField.getName().equals(targetField.getName())
                && sourceField.getType().equals(targetField.getType())) {
            //setAccessible - permite acesso temporário a campos privados em tempo de execução
            sourceField.setAccessible(true);
            targetField.setAccessible(true);
        }
    }


}
