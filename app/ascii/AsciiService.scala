package ascii

import common.{EntityNotFoundError, ServiceError}
import javax.inject.{Inject, Singleton}

@Singleton
class AsciiService @Inject()(imageRepository: ImageRepository) {

  def createImage(imageDto: ImageDTO): Either[ServiceError, Unit] = {
    val image = Image.from(imageDto)
    Right(imageRepository.createImage(image))
  }

  def uploadChunk(sha256: String, chunkDto: ChunkDTO): Either[ServiceError, Unit] = {
    val chunk = Chunk.from(chunkDto)

    imageRepository
      .findImage(sha256)
      .map { image =>
        image.chunks.insert(chunk.id, chunk)
        imageRepository.updateImage(image)
        Right(())
      }
      .getOrElse(Left(EntityNotFoundError(s"Could not find image $sha256")))

  }
}
