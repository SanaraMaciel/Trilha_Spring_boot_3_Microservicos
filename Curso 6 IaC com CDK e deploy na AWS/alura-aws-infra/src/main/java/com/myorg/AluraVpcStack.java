package com.myorg;

import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.ec2.Vpc;
import software.amazon.awscdk.services.ecs.Cluster;
import software.constructs.Construct;

public class AluraVpcStack extends Stack {

    private Vpc vpc;

    public AluraVpcStack(final Construct scope, final String id) {
        this(scope, id, null);
    }

    public AluraVpcStack(final Construct scope, final String id, final StackProps props) {
        super(scope, id, props);

        vpc = Vpc.Builder.create(this, "AluraVpc")
                .maxAzs(3)  // quantidade maxima de zonas de disponibilidade
                .build();

        Cluster cluster = Cluster.Builder.create(this, "MyCluster")
                .vpc(vpc).build();

    }

    public Vpc getVpc() {
        return vpc;
    }
}
