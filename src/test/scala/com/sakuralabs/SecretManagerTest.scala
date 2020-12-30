package com.sakuralabs

import com.amazonaws.regions.{Region, Regions}
import monix.execution.Scheduler.Implicits.global
import org.scalatest.funspec.AsyncFunSpec

class SecretManagerTest extends AsyncFunSpec {

  describe("A Secret Manager Client") {
    it("will eventually retrieve secret values") {
      val z = new SecretManager(Regions.US_EAST_1).retrieveSecretValue("")
      z.runToFuture map { _ => assert( true) }
    }
  }
}