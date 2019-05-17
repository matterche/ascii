package ascii

import javax.inject._
import play.api._
import play.api.mvc._

import scala.concurrent.Future

@Singleton
class AsciiController @Inject()(cc: ControllerComponents, asciiService: AsciiService) extends AbstractController(cc) {

  private val log = Logger(getClass)

  def registerImage(): Action[Image] = Action.async(parse.json[Image]) { request =>
    val image = request.body

    Future.successful {
      asciiService.createImage(image) match {
        case Right(_) => Created("")
        case Left(_)  => InternalServerError("An unexpected error occurred")
      }
    }

  }

  def uploadChunk(sha256: String) = Action.async { request =>
    Future.successful {
      Ok("")
    }
  }

  def downloadImage(sha256: String) = Action.async { request =>
    Future.successful {
      Ok("")
    }
  }
}
