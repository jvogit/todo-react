# ecs-on-ec2

Deploy AWS ECS cluster on EC2 behind an Application Load Balancer with Amazon RDS running postgres engine thru power of CDK. Currently deploys the
`server` docker image.

# deployment
- Set up AWS CLI and proper credentials
- Set up cdk CLI
- Run `npm run build`
- Synthesize `cdk synth`
- Bootstrap resources `cdk bootstrap aws://{account-id}/{region}`
- `cdk deploy --all --context accessTokenSecret=secretsecrethere --context refreshTokenSecret=secretsecrettoken --context jwtIssuer=springreactnextjs`
- To destroy `cdk destroy --all`

## Useful commands

 * `npm run build`   compile typescript to js
 * `npm run watch`   watch for changes and compile
 * `npm run test`    perform the jest unit tests
 * `cdk deploy`      deploy this stack to your default AWS account/region
 * `cdk diff`        compare deployed stack with current state
 * `cdk synth`       emits the synthesized CloudFormation template
