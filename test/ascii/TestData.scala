package ascii

import play.api.libs.json.Json

trait TestData {
  private val imageRaw = """{"sha256":"09569552730cb0628e0bc1f5d8039f8e6abefec920be8f88fc8f9fb5b9fcf0b5","size":664,"chunk_size":256}"""
  val imageJson        = Json.parse(imageRaw)
  val image            = imageJson.as[Image]
}

object TestData extends TestData
