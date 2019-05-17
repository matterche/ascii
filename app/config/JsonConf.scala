package config

import play.api.libs.json.JsonConfiguration
import play.api.libs.json.JsonNaming.SnakeCase

trait JsonConf {
  // enable snakecase to camelcase conversion
  implicit def config = JsonConfiguration(SnakeCase)
}

object JsonConf extends JsonConf
