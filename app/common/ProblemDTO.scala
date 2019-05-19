package common

import play.api.libs.json.{Format, Json}

case class ProblemDTO(code: String, message: String)

object ProblemDTO {
  implicit val problemFormat: Format[ProblemDTO] = Json.format[ProblemDTO]

}
