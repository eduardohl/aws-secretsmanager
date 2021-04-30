![Scala CI](https://github.com/eduardohl/aws-secretsmanager/workflows/Scala%20CI/badge.svg?branch=master)

## Description
This is a simple Scala wrapper intended to assist using the AWS Secrets Manager API.
The wrapper is by no means exhaustive, and implements essentially just a way to retrieve secret key values.

## Configuration
Import it from Maven Central by adding to dependencies
```
libraryDependencies ++= Seq(
  "io.github.eduardohl" %% "aws-secretsmanager" % "0.0.9"
)
```

## Usage
This code will return a Task[Secret] from which the resulting key values can be extracted
```
SecretManager(Regions.AP_EAST_1).retrieveSecretValue("mysecret")
```

## Credentials
This lib uses the aws sdk, so the credentials can be set using one of the secured ways below:
- From environment variables (AWS_ACCESS_KEY_ID (or AWS_ACCESS_KEY) and AWS_SECRET_KEY (or AWS_SECRET_ACCESS_KEY)
- From Java system properties (aws.accessKeyId and aws.secretKey)
- From a custom Credentials Provider using:

```  
val buildSecretsManager: Task[AWSSecretsManager] = Task(AWSSecretsManagerClientBuilder
         .standard
         .withRegion(region)
         .withCredentials(mycredentials)
         .build)
         
new SecretManager(buildSecretsManager).retrieveSecretValue("nonexistingsecret")
```