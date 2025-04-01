package br.com.alura.refl;

import java.lang.reflect.InvocationTargetException;

/**
 * Classe que transforma um obj em outro informado no momento da chamada assim não é necessário
 * concatenar com o sufixo DTO
 */
public class TransformGeneric {

    public <T, U> U transform(T source, Class target) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException,
            InstantiationException {

        Class<?> sourceClass = source.getClass();
        Class<?> targetClass = target;

        U instanceOfTarget = (U) targetClass.getDeclaredConstructor().newInstance();

        return instanceOfTarget;
    }


}
