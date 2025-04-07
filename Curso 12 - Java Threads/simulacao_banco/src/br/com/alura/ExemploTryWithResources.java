package br.com.alura;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ExemploTryWithResources {
    public static void main(String[] args) {
        // Caminho do arquivo que queremos ler
        String path = "arquivo.txt";

        // Aqui usamos o try-with-resources
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            // Lemos uma linha do arquivo
            String linha = br.readLine();

            // Enquanto houver linhas, imprima-as
            while (linha != null) {
                System.out.println(linha);
                linha = br.readLine();
            }
        } catch (IOException e) {
            // Se houver uma exceção, ela será tratada aqui
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }
        // Não precisamos de um bloco finally para fechar o BufferedReader!
    }
}