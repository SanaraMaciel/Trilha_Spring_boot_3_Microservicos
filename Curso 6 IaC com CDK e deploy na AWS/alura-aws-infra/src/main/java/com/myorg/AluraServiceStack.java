package com.myorg;

import software.amazon.awscdk.Fn;
import software.amazon.awscdk.RemovalPolicy;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.ecs.AwsLogDriverProps;
import software.amazon.awscdk.services.ecs.Cluster;
import software.amazon.awscdk.services.ecs.ContainerImage;
import software.amazon.awscdk.services.ecs.LogDriver;
import software.amazon.awscdk.services.ecs.patterns.ApplicationLoadBalancedFargateService;
import software.amazon.awscdk.services.ecs.patterns.ApplicationLoadBalancedTaskImageOptions;

import software.amazon.awscdk.services.iam.ManagedPolicy;
import software.amazon.awscdk.services.iam.Role;
import software.amazon.awscdk.services.iam.ServicePrincipal;
import software.amazon.awscdk.services.logs.LogGroup;
import software.amazon.awscdk.services.logs.RetentionDays;
import software.constructs.Construct;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AluraServiceStack extends Stack {

    public AluraServiceStack(final Construct scope, final String id, final Cluster cluster) {
        this(scope, id, null, cluster);
    }

    public AluraServiceStack(final Construct scope, final String id, final StackProps props, final Cluster cluster) {
        super(scope, id, props);

        // Criando a role do ECS para permitir logging no CloudWatch
        Role executionRole = Role.Builder.create(this, "EcsExecutionRole")
                .assumedBy(new ServicePrincipal("ecs-tasks.amazonaws.com"))
                .managedPolicies(List.of(ManagedPolicy.fromAwsManagedPolicyName("service-role/AmazonECSTaskExecutionRolePolicy")))
                .build();

        //cria o mapa de variaveis de ambiente que serao usadas na api
        Map<String, String> autenticacao = new HashMap<>();
        autenticacao.put("SPRING_DATASOURCE_URL", "jdbc:mysql://"
                + Fn.importValue( "pedidos-db-endpoint") + ":3306/alurafood-pedidos?createDatabaseIfNotExist=true");
        autenticacao.put("SPRING_DATASOURCE_USERNAME", "admin");
        autenticacao.put("SPRING_DATASOURCE_PASSWORD", Fn.importValue("pedidos-db-senha"));

        // Criando um novo Log Group no CloudWatch
        LogGroup logGroup = LogGroup.Builder.create(this, "AluraServiceLogs")
                .logGroupName("/ecs/AluraService") // Nome do Log Group
                .retention(RetentionDays.ONE_WEEK) // Retenção dos logs por 1 semana
                .removalPolicy(RemovalPolicy.DESTROY) // Apaga os logs quando o stack for destruído
                .build();

        // Create a load-balanced Fargate service and make it public
        ApplicationLoadBalancedFargateService.Builder.create(this, "AluraService")
                .serviceName("alura-service-ola") //customiza o nome que o cluster vai ter
                .cluster(cluster)           // cluster a ser associado - Required
                .cpu(512)                   // valor definido de cpu que ele ira utilizar - Default is 256
                .desiredCount(1)            // Qtd de instancias para executar a tarefa - Default is 1
                .listenerPort(8080)        //porta que vai ficar escutando o cluster
                .assignPublicIp(true)     // indica que esse cluster possui um ip publico
                .taskImageOptions(
                        ApplicationLoadBalancedTaskImageOptions.builder()
                                .image(ContainerImage.fromRegistry("sanara2707/pedidos-ms")) //imagem disponivel no meu dockerhub
                                .containerPort(8080) //porta que vai rodar o container
                                .containerName("app_ola")
                                .environment(autenticacao) //informa pro container quais variaveis vamos usar pra rodar a aplicacao
                                .logDriver(LogDriver.awsLogs(AwsLogDriverProps.builder()
                                        .logGroup(logGroup)
                                        .streamPrefix("ecs")
                                        .build()))
                                .executionRole(executionRole) // Define a role correta
                                .build())
                .memoryLimitMiB(1024)       //  limite de memória --Default is 512
                .publicLoadBalancer(true)   // Default is false
                .build();
    }

}
