package com.sakuralabs

import cats.effect.Resource
import com.amazonaws.regions.Regions
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest
import com.amazonaws.services.secretsmanager.{AWSSecretsManager, AWSSecretsManagerClientBuilder}
import com.sakuralabs.SecretManager.{client, getSecretValueRequest}
import monix.eval.Task

object SecretManager {

  def client(region: String): Resource[Task, AWSSecretsManager] = Resource.make {
    Task(AWSSecretsManagerClientBuilder.standard.withRegion(region).build)
  } { rel =>
    Task(rel.shutdown())
  }

  def getSecretValueRequest(secretName: String): Task[GetSecretValueRequest] = Task {
    new GetSecretValueRequest().withSecretId(secretName)
  }
}

class SecretManager(region: Regions) {

  def retrieveSecretValue(secretName: String): Task[Unit] = {
    for {
      request <- getSecretValueRequest(secretName)
      result <- client(region.getName).use(c => Task(c.getSecretValue(request)))
    } yield JsonUtils.jsonStrToMap(result.getSecretString)
  }
}
