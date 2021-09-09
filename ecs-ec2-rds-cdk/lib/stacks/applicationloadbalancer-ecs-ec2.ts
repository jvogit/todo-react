import * as ec2 from '@aws-cdk/aws-ec2';
import * as ecs from '@aws-cdk/aws-ecs';
import { AwsLogDriver, ContainerDefinitionOptions } from '@aws-cdk/aws-ecs';
import * as elb from '@aws-cdk/aws-elasticloadbalancingv2';
import * as cdk from '@aws-cdk/core';
import { CfnOutput } from '@aws-cdk/core';

interface EcsApplicationLoadBalancerStackProps extends cdk.StackProps {
  readonly vpc: ec2.IVpc;
  readonly frontendImage: { directory: string, props?: ecs.AssetImageProps | undefined };
  readonly backendImage: { directory: string, props?: ecs.AssetImageProps | undefined };
  readonly databaseUri: string;
  readonly envVars: { [key: string]: string };
}

export class ApplicationLoadBalancerEcsEc2Stack extends cdk.Stack {
  public loadBalancer: elb.ApplicationLoadBalancer;
  public cluster: ecs.Cluster;
  public service: ecs.Ec2Service;
  public taskDefinition: ecs.Ec2TaskDefinition;

  constructor(scope: cdk.Construct, id: string, props: EcsApplicationLoadBalancerStackProps) {
    super(scope, id, props);
    
    this.loadBalancer = new elb.ApplicationLoadBalancer(scope, "LoadBalancer", {
      vpc: props.vpc,
      internetFacing: true,
    });

    this.cluster = new ecs.Cluster(scope, 'EcsCluster', {
      clusterName: 'ecs-on-ec2-cluster',
      vpc: props.vpc,
      capacity: {
        instanceType: ec2.InstanceType.of(ec2.InstanceClass.BURSTABLE2, ec2.InstanceSize.MICRO),
        maxCapacity: 1,
      },
    });

    this.taskDefinition = new ecs.Ec2TaskDefinition(scope, 'EcsTaskDefinition');

    this.service = new ecs.Ec2Service(scope, 'EcsOnEc2Service', {
      cluster: this.cluster,
      taskDefinition: this.taskDefinition,
    });

    this.createWebContainer(scope, "todo-react-v2-frontend", props.vpc, {
      containerName: "todo-react-v2-frontend",
      image: ecs.ContainerImage.fromAsset(props.frontendImage.directory, props.frontendImage.props),
      memoryLimitMiB: 256,
      environment: {
        "BASE_API_URL": `http://${this.loadBalancer.loadBalancerDnsName}:8080`
      },
      logging: new AwsLogDriver({ streamPrefix: "todo-react-v2-frontend" }),
      portMappings: [
        { containerPort: 3000, hostPort: 80, protocol: ecs.Protocol.TCP }
      ]
    });

    this.createWebContainer(scope, "todo-react-v2-backend", props.vpc, {
      containerName: "todo-react-v2-backend",
      image: ecs.ContainerImage.fromAsset(props.backendImage.directory, props.backendImage.props),
      memoryLimitMiB: 256,
      environment: {
        "DATABASE_URL": props.databaseUri,
        "CORS_ORIGIN": `http://${this.loadBalancer.loadBalancerDnsName}`,
        ...props.envVars
      },
      logging: new AwsLogDriver({ streamPrefix: "todo-react-v2-backend" }),
      portMappings: [
        { containerPort: 8080, hostPort: 8080, protocol: ecs.Protocol.TCP }
      ]
    });

    new CfnOutput(scope, 'LoadBalancerDnsName', { value: this.loadBalancer.loadBalancerDnsName });
  }

  protected createWebContainer(scope: cdk.Construct, name: string, vpc: ec2.IVpc, containerProps: ContainerDefinitionOptions) {
    this.taskDefinition.addContainer(name, containerProps);

    const webPortMapping = containerProps.portMappings![0];

    const target = this.service.loadBalancerTarget({
      containerName: containerProps.containerName!,
      containerPort: webPortMapping.containerPort,
      protocol: webPortMapping.protocol,
    });

    const targetGroup = new elb.ApplicationTargetGroup(scope, `${name}TargetGroup`, {
      vpc: vpc,
      port: webPortMapping.hostPort,
      targetType: elb.TargetType.INSTANCE,
      protocol: elb.ApplicationProtocol.HTTP
    });

    this.loadBalancer.addListener(name, {
      port: webPortMapping.hostPort,
      protocol: elb.ApplicationProtocol.HTTP,
      defaultTargetGroups: [targetGroup]
    });

    target.attachToApplicationTargetGroup(targetGroup);
  }
}