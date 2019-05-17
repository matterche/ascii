package common

sealed trait ServiceError {
  val message: String
}

case class ConflictError(message: String) extends ServiceError

case class EntityNotFoundError(message: String) extends ServiceError
