import * as secretsmanager from '@aws-cdk/aws-secretsmanager';

export function getPostgresUri(secret: secretsmanager.ISecret) : string {
    return `postgres://${getFromSecret(secret, 'username')}:${getFromSecret(secret, 'password')}@${getFromSecret(secret, 'host')}:${getFromSecret(secret, 'port')}/${getFromSecret(secret, 'dbname')}`;
}

export function getFromSecret(secret: secretsmanager.ISecret, field: string) : string {
    return secret.secretValueFromJson(field).toString();
}