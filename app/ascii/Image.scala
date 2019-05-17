package ascii

import config.JsonConf
import play.api.libs.json.{Format, Json}

case class Image(sha256: String, size: Int, chunkSize: Int)

object Image extends JsonConf {
  implicit val imageFormat: Format[Image] = Json.format[Image]
}
