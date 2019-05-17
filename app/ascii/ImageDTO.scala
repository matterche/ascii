package ascii

import config.JsonConf
import play.api.libs.json.{Format, Json}

case class ImageDTO(sha256: String, size: Int, chunkSize: Int)

object ImageDTO extends JsonConf {
  implicit val imageFormat: Format[ImageDTO] = Json.format[ImageDTO]
}
