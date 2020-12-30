package com.sakuralabs

import org.json4s.DefaultFormats
import org.json4s.native.JsonMethods.parse

object JsonUtils {
  def jsonStrToMap(jsonStr: String): Map[String, String] = {
    implicit val formats: DefaultFormats.type = org.json4s.DefaultFormats
    parse(jsonStr).extract[Map[String, String]]
  }
}
