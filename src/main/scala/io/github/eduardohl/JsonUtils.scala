package io.github.eduardohl

import io.github.eduardohl.SecretManager.Secret
import org.json4s.DefaultFormats
import org.json4s.native.JsonMethods.parse

object JsonUtils {
  implicit def jsonStrToSecrets(jsonStr: String): Seq[Secret] = {
    implicit val formats: DefaultFormats.type = org.json4s.DefaultFormats
    parse(jsonStr).extract[Map[String, String]].map { case (k, v) => Secret(k, v) }.toSeq
  }
}
