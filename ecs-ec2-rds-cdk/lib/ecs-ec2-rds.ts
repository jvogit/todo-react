import * as ec2 from '@aws-cdk/aws-ec2';
import * as cdk from '@aws-cdk/core';
import * as path from 'path';
import { ApplicationLoadBalancerEcsEc2Stack } from './stacks/applicationloadbalancer-ecs-ec2';
import { PostgresRDSStack } from './stacks/postgres-rds-stack';
import { getPostgresUri } from './utils';

interface EcsEc2RdsProps extends cdk.StackProps {
  readonly accessTokenSecret: string;
  readonly refreshTokenSecret: string;
  readonly jwtIssuer: string;
}

export class EcsEc2Rds extends cdk.Stack {

  constructor(scope: cdk.Construct, id: string, props: EcsEc2RdsProps) {
    super(scope, id, props);

    const vpc = new ec2.Vpc(this, "Vpc", {
      natGatewayProvider: ec2.NatProvider.instance({
        instanceType: ec2.InstanceType.of(ec2.InstanceClass.BURSTABLE2, ec2.InstanceSize.MICRO),
      }),
      natGateways: 1,
      maxAzs: 2,
    });

    const postgresRDS = new PostgresRDSStack(this, 'PostgresRds', {
      vpc: vpc,
      instanceIdentifier: "todo-react-v2",
      databaseName: "springReactNextJs"
    });

    const secret = postgresRDS.databaseInstance.secret!;

    const ecsec2 = new ApplicationLoadBalancerEcsEc2Stack(this, 'ApplicationLoadBalancerEcsEc2Rds', {
      vpc: vpc,
      frontendImage: {
        directory: path.join(__dirname, "..", "..", "client")
      },
      backendImage: {
        directory: path.join(__dirname, "..", "..", "server")
      },
      databaseUri: getPostgresUri(secret),
      envVars: {
        ACCESS_TOKEN_SECRET: props.accessTokenSecret,
        REFRESH_TOKEN_SECRET: props.refreshTokenSecret,
        JWT_ISSUER: props.jwtIssuer
      }
    });

    // grant permissions
    postgresRDS.databaseInstance.connections.allowFrom(ecsec2.service, ec2.Port.tcp(5432), "Allow ec2 to connect to postgres within vpc");
  }
}
