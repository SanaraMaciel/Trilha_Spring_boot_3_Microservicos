package com.myorg;

import software.amazon.awscdk.App;
import software.amazon.awscdk.Environment;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.ec2.Vpc;

import java.util.Arrays;

public class AluraAwsInfraApp {
    public static void main(final String[] args) {
        App app = new App();

        //Cluster
        AluraVpcStack vpcStack = new AluraVpcStack(app, "Vpc");
        AluraClusterStack clusterStack = new AluraClusterStack(app, "Cluster", vpcStack.getVpc());
        clusterStack.addDependency(vpcStack); //informa que o cluster depende da criacao primeiramente da vpc

        //RDS para acessar o nosso banco de dados
        AluraRdsStack rdsStack = new AluraRdsStack(app, "Rds", vpcStack.getVpc());
        rdsStack.addDependency(vpcStack);


        //Service
        AluraServiceStack aluraServiceStack = new AluraServiceStack(app, "Service", clusterStack.getCluster());
        aluraServiceStack.addDependency(clusterStack); //para fazer com que o service so inicie apos o cluster

        app.synth();
    }

}

