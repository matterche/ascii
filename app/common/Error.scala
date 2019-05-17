package common

sealed trait ServiceError {
  val message: String
}

case class ConflictError(message: String) extends ServiceError