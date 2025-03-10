package com.myorg;

import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.ecs.Cluster;
import software.amazon.awscdk.services.ecs.ContainerImage;
import software.amazon.awscdk.services.ecs.patterns.ApplicationLoadBalancedFargateService;
import software.amazon.awscdk.services.ecs.patterns.ApplicationLoadBalancedTaskImageOptions;
import software.constructs.Construct;

public class AluraServiceStack extends Stack {

    public AluraServiceStack(final Construct scope, final String id, final Cluster cluster) {
        this(scope, id, null, cluster);
    }

    public AluraServiceStack(final Construct scope, final String id, final StackProps props, final Cluster cluster) {
        super(scope, id, props);

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
                                .image(ContainerImage.fromRegistry("jacquelineoliveira/ola:1.0"))
                                .containerPort(8080) //porta que vai rodar o container
                                .containerName("app_ola")
                                .build())
                .memoryLimitMiB(1024)       //  limite de memória --Default is 512
                .publicLoadBalancer(true)   // Default is false
                .build();
    }

}
