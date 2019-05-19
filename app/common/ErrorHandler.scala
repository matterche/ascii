package common

import javax.inject.Singleton
import play.api.http.HttpErrorHandler
import play.api.libs.json.Json
import play.api.mvc.Results.{InternalServerError, Status}
import play.api.mvc.{RequestHeader, Result}

import scala.concurrent.Future

@Singleton
class ErrorHandler extends HttpErrorHandler {

  override def onClientError(request: RequestHeader, statusCode: Int, message: String): Future[Result] = {
    Future.successful {
      Status(statusCode)(Json.toJson(ProblemDTO(s"$statusCode", message)))
    }
  }

  override def onServerError(request: RequestHeader, exception: Throwable): Future[Result] = {
    Future.successful {
      InternalServerError(Json.toJson(ProblemDTO("500", "Unexpected error occurred" + exception.getLocalizedMessage)))
    }
  }
}
