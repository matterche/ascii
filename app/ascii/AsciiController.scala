package ascii

import javax.inject._
import play.api._
import play.api.mvc._
import scala.concurrent.Future

@Singleton
class AsciiController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def registerImage() = Action.async { request =>
    Future.successful {
      Ok("")
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
