package br.com.alura.refl;

import br.com.alura.Pessoa;

public class ObjectToJsonTester {

    public static void main(String... x) {
        Pessoa pessoa = new Pessoa(1, "João", "12345");
        ObjectToJson objectToJson = new ObjectToJson();
        System.out.println(objectToJson.transform(pessoa));
    }
}
