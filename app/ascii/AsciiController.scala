package ascii

import common.ServiceError
import javax.inject._
import play.api.mvc._

import scala.concurrent.Future

@Singleton
class AsciiController @Inject()(cc: ControllerComponents, asciiService: AsciiService) extends AbstractController(cc) {

  def registerImage(): Action[ImageDTO] = Action.async(parse.json[ImageDTO]) { request =>
    val imageDto = request.body

    Future.successful {
      asciiService.createImage(imageDto) match {
        case Right(_)                  => Created("")
        case Left(error: ServiceError) => error.toResult
      }
    }
  }

  def uploadChunk(sha256: String): Action[ChunkDTO] = Action.async(parse.json[ChunkDTO]) { request =>
    val chunkDto = request.body

    Future.successful {
      asciiService.uploadChunk(sha256, chunkDto) match {
        case Right(_)                  => Created("")
        case Left(error: ServiceError) => error.toResult
      }
    }
  }

  def downloadImage(sha256: String): Action[AnyContent] = Action.async { _ =>
    Future.successful {
      asciiService.downloadImage(sha256) match {
        case Right(rawImage)           => Ok(rawImage)
        case Left(error: ServiceError) => error.toResult
      }
    }
  }

}
