package com.myorg;

import org.jetbrains.annotations.NotNull;
import software.amazon.awscdk.*;
import software.amazon.awscdk.services.applicationautoscaling.EnableScalingProps;
import software.amazon.awscdk.services.ecr.IRepository;
import software.amazon.awscdk.services.ecr.Repository;
import software.amazon.awscdk.services.ecs.*;
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
                + Fn.importValue("pedidos-db-endpoint") + ":3306/alurafood-pedidos?createDatabaseIfNotExist=true");
        autenticacao.put("SPRING_DATASOURCE_USERNAME", "admin");
        autenticacao.put("SPRING_DATASOURCE_PASSWORD", Fn.importValue("pedidos-db-senha"));

        IRepository iRepository = Repository.fromRepositoryName(this, "repositorio", "img-pedidos-ms");

        // Criando um novo Log Group no CloudWatch
        LogGroup logGroup = LogGroup.Builder.create(this, "AluraServiceLogs")
                .logGroupName("/ecs/AluraService") // Nome do Log Group
                .retention(RetentionDays.ONE_WEEK) // Retenção dos logs por 1 semana
                .removalPolicy(RemovalPolicy.DESTROY) // Apaga os logs quando o stack for destruído
                .build();

        // Create a load-balanced Fargate service and make it public
        ApplicationLoadBalancedFargateService aluraService = ApplicationLoadBalancedFargateService.Builder.create(this, "AluraService")
                .serviceName("alura-service-ola") //customiza o nome que o cluster vai ter
                .cluster(cluster)           // cluster a ser associado - Required
                .cpu(1024)                   // valor definido de cpu que ele ira utilizar - Default is 256
                .desiredCount(3)            // Qtd de instancias para executar a tarefa - Default is 1
                .listenerPort(8080)        //porta que vai ficar escutando o cluster
                .assignPublicIp(true)     // indica que esse cluster possui um ip publico
                .taskImageOptions(
                        ApplicationLoadBalancedTaskImageOptions.builder()
                                //.image(ContainerImage.fromRegistry("sanara2707/pedidos-ms")) //imagem disponivel no meu dockerhub
                                .image(ContainerImage.fromEcrRepository(iRepository)) //pra referenciar a imagem da aws
                                .containerPort(8080) //porta que vai rodar o container
                                .containerName("app_ola")
                                .environment(autenticacao) //informa pro container quais variaveis vamos usar pra rodar a aplicacao
                                .logDriver(LogDriver.awsLogs(AwsLogDriverProps.builder()
                                        .logGroup(LogGroup.Builder.create(this, "PedidosMsLogGroup") //agrupa todos os logs nesse grupo
                                                .logGroupName("PedidosMsLog")
                                                .removalPolicy(RemovalPolicy.DESTROY) //se apagar a stack, apaga o log tbm
                                                .build())
                                        .streamPrefix("PedidosMS")
                                        .build()))
                                .build())
                .memoryLimitMiB(2048)       //  limite de memória --Default is 512
                .publicLoadBalancer(true)   // Default is false
                .build();


        //Auto Scalling
        ScalableTaskCount scalableTarget = aluraService.getService().autoScaleTaskCount(EnableScalingProps.builder()
                .minCapacity(1)
                .maxCapacity(3)
                .build());

        //escalando cpi
        scalableTarget.scaleOnCpuUtilization("CpuScaling", CpuUtilizationScalingProps.builder()
                .targetUtilizationPercent(70)
                .scaleInCooldown(Duration.minutes(3))//qto tempo a cpu tem q estar com 70% para subir outra instancia ou destruir a instancia
                .scaleOutCooldown(Duration.minutes(2)) //a duracao para desligar a instancias se ficar abaixo de 70%
                .build());

        //escalando memoria
        scalableTarget.scaleOnMemoryUtilization("MemoryScaling", MemoryUtilizationScalingProps.builder()
                .targetUtilizationPercent(65)
                .scaleInCooldown(Duration.minutes(3))
                .scaleOutCooldown(Duration.minutes(2))
                .build());
    }

}
