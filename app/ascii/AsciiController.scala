package ascii

import javax.inject._
import play.api._
import play.api.mvc._

import scala.concurrent.Future

@Singleton
class AsciiController @Inject()(cc: ControllerComponents, asciiService: AsciiService) extends AbstractController(cc) {

  private val log = Logger(getClass)

  def registerImage(): Action[ImageDTO] = Action.async(parse.json[ImageDTO]) { request =>
    val imageDto = request.body

    Future.successful {
      asciiService.createImage(imageDto) match {
        case Right(_) => Created("")
        case Left(_)  => InternalServerError("An unexpected error occurred while creating an image")
      }
    }

  }

  def uploadChunk(sha256: String) = Action.async(parse.json[ChunkDTO]) { request =>
    val chunkDto = request.body

    // TODO: validate chunk size
    // TODO: validate if image size too big

    Future.successful {
      asciiService.uploadChunk(sha256, chunkDto) match {
        case Right(_) => Created("")
        case Left(_)  => InternalServerError("An unexpected error occurred while creating a chunk")
      }
    }
  }

  def downloadImage(sha256: String) = Action.async { request =>
    Future.successful {
      asciiService.downloadImage(sha256) match {
        case Right(rawImage) => Ok(rawImage)
        case Left(_)         => InternalServerError("An unexpected error occurred while creating a chunk")
      }

    }
  }
}
