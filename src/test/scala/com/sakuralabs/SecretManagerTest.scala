package com.sakuralabs

import com.amazonaws.services.secretsmanager.{AWSSecretsManager, AWSSecretsManagerClientBuilder}
import com.amazonaws.services.secretsmanager.model.{CreateSecretRequest, ResourceNotFoundException}
import com.dimafeng.testcontainers
import com.dimafeng.testcontainers.{ForAllTestContainer, LocalStackContainer}
import monix.eval.Task
import monix.execution.Scheduler.Implicits.global
import org.scalatest.funspec.AsyncFunSpec
import org.scalatest.matchers.should.Matchers.convertToAnyShouldWrapper
import org.testcontainers.containers.localstack.LocalStackContainer.Service

class SecretManagerTest extends AsyncFunSpec with ForAllTestContainer {

  override val container: LocalStackContainer = testcontainers.LocalStackContainer(services = Seq(Service.SECRETSMANAGER))

  val buildSecretsManager: Task[AWSSecretsManager] = Task(AWSSecretsManagerClientBuilder
    .standard
    .withEndpointConfiguration(container.endpointConfiguration(Service.SECRETSMANAGER))
    .withCredentials(container.defaultCredentialsProvider)
    .build)

  describe("A Secret Manager Client") {
    it("will eventually retrieve secret values") {
      val existingSecret = new CreateSecretRequest()
        .withName("mysecret")
        .withSecretString("""{"baba":"yaga"}""")

      val task = for {
        awsSM <- buildSecretsManager
        _ = awsSM.createSecret(existingSecret)
        result <- new SecretManager(buildSecretsManager).retrieveSecretValue("mysecret")
      } yield {
        result.head.key shouldBe "baba"
        result.head.value shouldBe "yaga"
      }

      task.runToFuture
    }

    it("will fail when the secrets don't exist") {
      val task = new SecretManager(buildSecretsManager).retrieveSecretValue("nonexistingsecret")
      recoverToSucceededIf[ResourceNotFoundException](task.runToFuture)
    }
  }
}