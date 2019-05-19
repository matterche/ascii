package common

import javax.inject.Singleton
import play.api.Logger
import play.api.http.HttpErrorHandler
import play.api.libs.json.Json
import play.api.mvc.Results.{InternalServerError, Status}
import play.api.mvc.{RequestHeader, Result}

import scala.concurrent.Future

@Singleton
class ErrorHandler extends HttpErrorHandler {

  private val log = Logger(getClass)

  override def onClientError(request: RequestHeader, statusCode: Int, message: String): Future[Result] = {
    Future.successful {
      log.warn(s"Service error occurred: " + message)
      Status(statusCode)(Json.toJson(ProblemDTO(s"$statusCode", message)))
    }
  }

  override def onServerError(request: RequestHeader, exception: Throwable): Future[Result] = {
    Future.successful {
      log.error(s"Unexpected error occurred: ", exception)
      InternalServerError(Json.toJson(ProblemDTO("500", "Unexpected error occurred" + exception.getLocalizedMessage)))
    }
  }
}
