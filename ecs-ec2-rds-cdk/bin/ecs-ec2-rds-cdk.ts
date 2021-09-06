#!/usr/bin/env node
import 'source-map-support/register';
import * as cdk from '@aws-cdk/core';
import { EcsEc2Rds } from '../lib/ecs-ec2-rds';

const app = new cdk.App();

const accessTokenSecret = app.node.tryGetContext("accessTokenSecret");
const refreshTokenSecret = app.node.tryGetContext("refreshTokenSecret");
const jwtIssuer = app.node.tryGetContext("jwtIssuer");

new EcsEc2Rds(app, 'EcsEc2RdsCdkStack', {
  env: { account: process.env.CDK_DEFAULT_ACCOUNT, region: process.env.CDK_DEFAULT_REGION },
  accessTokenSecret: accessTokenSecret,
  refreshTokenSecret: refreshTokenSecret,
  jwtIssuer: jwtIssuer
});