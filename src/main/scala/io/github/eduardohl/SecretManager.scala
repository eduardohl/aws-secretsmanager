package io.github.eduardohl

import cats.effect.Resource
import com.amazonaws.regions.Regions
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest
import com.amazonaws.services.secretsmanager.{AWSSecretsManager, AWSSecretsManagerClientBuilder}
import io.github.eduardohl.JsonUtils.jsonStrToSecrets
import io.github.eduardohl.SecretManager.{Secret, client, getSecretValueRequest}
import monix.eval.Task

object SecretManager {

  case class Secret(key: String, value: String)

  def apply: SecretManager = {
    val buildSecretsManager = Task(AWSSecretsManagerClientBuilder
      .standard
      .build)
    new SecretManager(buildSecretsManager)
  }

  def apply(region: Regions): SecretManager = {
    val buildSecretsManager = Task(AWSSecretsManagerClientBuilder
      .standard
      .withRegion(region)
      .build)
    new SecretManager(buildSecretsManager)
  }

  def client(buildSecretsManager: Task[AWSSecretsManager]): Resource[Task, AWSSecretsManager] = Resource.make {
    buildSecretsManager
  } { rel =>
    Task(rel.shutdown())
  }

  def getSecretValueRequest(secretName: String): Task[GetSecretValueRequest] = Task {
    new GetSecretValueRequest().withSecretId(secretName)
  }
}

class SecretManager(buildSecretsManager: Task[AWSSecretsManager]) {

  def retrieveSecretValue(secretName: String): Task[Seq[Secret]] = {
    for {
      request <- getSecretValueRequest(secretName)
      result <- client(buildSecretsManager).use(c => Task(c.getSecretValue(request)))
    } yield result.getSecretString
  }
}
