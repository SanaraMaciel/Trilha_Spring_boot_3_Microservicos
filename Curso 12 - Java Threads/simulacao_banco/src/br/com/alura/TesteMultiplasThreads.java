package br.com.alura;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TesteMultiplasThreads {

    public static void main(String[] args) {

        //cria um pull de threads com 10 mil threads
        //ExecutorService executor = Executors.newFixedThreadPool(50000);

        //Criando threads virtuais com o executor -- recurso inserido no java 21
        //não e necessario informar o num de threads porque quem vai gerenciar isso é o jvm
        ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();


        //medir o tempo de execução entre as threads
        Instant inicio = Instant.now();

        try (ExecutorService e = executor){
            for (int i = 1; i<=100000; i++){
                var tarefa = new ExecutarTarefa();
                e.execute(tarefa);
            }
        }

        //medir o tempo de execução entre as threads
        Instant fim = Instant.now();

        //pega a duracao entre o inicio e o fim de execução das threads
        Duration duracao = Duration.between(inicio, fim);
        System.out.println("tempo gasto pelas tarefas: " + duracao.getSeconds());


    }
}
