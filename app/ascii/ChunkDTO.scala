package ascii

import config.JsonConf
import play.api.libs.json.{Format, Json}

case class ChunkDTO(id: Int, size: Int, data: String)

object ChunkDTO extends JsonConf {
  implicit val imageChunkFormat: Format[ChunkDTO] = Json.format[ChunkDTO]
}
