import * as cdk from '@aws-cdk/core';
import * as ec2 from '@aws-cdk/aws-ec2';
import * as rds from '@aws-cdk/aws-rds';
import { IVpc } from '@aws-cdk/aws-ec2';

interface PostgresRdsStackProps extends cdk.StackProps {
  readonly instanceIdentifier: string;
  readonly vpc : IVpc;
  readonly databaseName: string;
}

export class PostgresRDSStack extends cdk.Stack {
  public readonly databaseInstance : rds.DatabaseInstance;

  constructor(scope: cdk.Construct, id: string, props: PostgresRdsStackProps) {
    super(scope, id, props);
    
    this.databaseInstance = new rds.DatabaseInstance(scope, 'PostgresRDS', {
      engine: rds.DatabaseInstanceEngine.postgres({ version: rds.PostgresEngineVersion.VER_12_7 }),
      instanceIdentifier: props.instanceIdentifier,
      instanceType: ec2.InstanceType.of(ec2.InstanceClass.BURSTABLE2, ec2.InstanceSize.MICRO),
      vpcSubnets: {
        subnetType: ec2.SubnetType.PRIVATE,
      },
      // save money. live better.
      // should probably change this in production!
      backupRetention: cdk.Duration.days(0),
      vpc: props.vpc,
      allocatedStorage: 5,
      databaseName: props.databaseName,
    });
  }
}
