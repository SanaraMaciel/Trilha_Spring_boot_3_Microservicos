package com.myorg;

import software.amazon.awscdk.*;
import software.amazon.awscdk.services.ec2.*;
import software.amazon.awscdk.services.ec2.InstanceType;
import software.amazon.awscdk.services.rds.*;
import software.constructs.Construct;

import java.util.Collections;

public class AluraRdsStack extends Stack {
    public AluraRdsStack(final Construct scope, final String id, final Vpc vpc) {
        this(scope, id, null, vpc);
    }

    public AluraRdsStack(final Construct scope, final String id, final StackProps props, final Vpc vpc) {
        super(scope, id, props);

        //cria o parametro da senha para indicar qual a senha na hora do deploy na AWS
        CfnParameter senha = CfnParameter.Builder.create(this, "senha")
                .type("String")
                .description("Senha do database pedidos-ms")
                .build();


        //inclui o security group para a senha do banco desta Vpc
        ISecurityGroup iSecurityGroup = SecurityGroup.fromSecurityGroupId(this, id, vpc.getVpcDefaultSecurityGroup());
        iSecurityGroup.addIngressRule(Peer.anyIpv4(), Port.tcp(3306));

        //cria a instancia do banco Database instance
        DatabaseInstance database = DatabaseInstance.Builder
                .create(this, "Rds-pedidos") //id da instancia na aws
                .instanceIdentifier("alura-aws-pedido-db") //identificacao da instancia
                .engine(DatabaseInstanceEngine.mysql(MySqlInstanceEngineProps.builder()
                        .version(MysqlEngineVersion.VER_8_0_35)
                        .build())) //cria a engine
                .vpc(vpc)
                .credentials(Credentials.fromUsername("admin",
                        CredentialsFromUsernameOptions.builder()
                                .password(SecretValue.unsafePlainText(senha.getValueAsString())) //pega o valor da variavel senha acima
                                .build()))
                .instanceType(InstanceType.of(InstanceClass.BURSTABLE3, InstanceSize.MICRO)) //tamanho micro pra ficar grátis
                .multiAz(false) // false por ser o de graca
                .allocatedStorage(10) //tamanho padrao de armazenamento
                .securityGroups(Collections.singletonList(iSecurityGroup)) //config dos grupos de seguranca informados na vpc acima
                .vpcSubnets(SubnetSelection.builder()
                        .subnets(vpc.getPrivateSubnets())
                        .build()) //subredes informadas na vpc para agrpar toas as aplicacoes que estao nessa vpc
                .build();


        //informar pra aplicacao qual e o endpoint do banco de dados o usuario e a senha dele
        //CloudFormation output disponibilizando o valor
        CfnOutput.Builder.create(this, "pedidos-db-endpoint")
                .exportName("pedidos-db-endpoint")
                .value(database.getDbInstanceEndpointAddress())
                .build();

        CfnOutput.Builder.create(this, "pedidos-db-senha")
                .exportName("pedidos-db-senha")
                .value(senha.getValueAsString())
                .build();





    }
}
