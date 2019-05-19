package ascii

import common.ServiceError
import javax.inject._
import play.api.Logger
import play.api.mvc._

import scala.concurrent.Future

@Singleton
class AsciiController @Inject()(cc: ControllerComponents, asciiService: AsciiService) extends AbstractController(cc) {

  private val log = Logger(getClass)

  def registerImage(): Action[ImageDTO] = Action.async(parse.json[ImageDTO]) { request =>
    val imageDto = request.body

    Future.successful {
      asciiService.registerImage(imageDto) match {
        case Right(_) => Created("")
        case Left(error: ServiceError) =>
          log.warn(s"Service error while registering an image occurred: " + error.message)
          error.toResult
      }
    }
  }

  def uploadChunk(sha256: String): Action[ChunkDTO] = Action.async(parse.json[ChunkDTO]) { request =>
    val chunkDto = request.body

    Future.successful {
      asciiService.uploadChunk(sha256, chunkDto) match {
        case Right(_) => Created("")
        case Left(error: ServiceError) =>
          log.warn(s"Service error while upload an image chunk occurred: " + error.message)
          error.toResult
      }
    }
  }

  def downloadImage(sha256: String): Action[AnyContent] = Action.async { _ =>
    Future.successful {
      asciiService.downloadImage(sha256) match {
        case Right(rawImage) => Ok(rawImage)
        case Left(error: ServiceError) =>
          log.warn(s"Service error while downloading an image occurred: " + error.message)
          error.toResult
      }
    }
  }

}
