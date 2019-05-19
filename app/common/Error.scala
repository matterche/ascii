package common

import play.api.libs.json.Json.toJson
import play.api.mvc.Result
import play.api.mvc.Results._

sealed trait ServiceError {
  val message: String
  def toResult: Result
}

case class ConflictError(message: String) extends ServiceError {
  override def toResult: Result = Conflict(toJson(ProblemDTO("409", message)))
}

case class EntityNotFoundError(message: String) extends ServiceError {
  override def toResult: Result = NotFound(toJson(ProblemDTO("404", message)))
}
