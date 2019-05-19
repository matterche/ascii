package ascii

import common.{ConflictError, EntityNotFoundError, ServiceError}
import javax.inject.{Inject, Singleton}

@Singleton
class AsciiService @Inject()(imageRepository: ImageRepository) {

  def createImage(imageDto: ImageDTO): Either[ServiceError, Unit] = {
    imageRepository.findImage(imageDto.sha256) match {
      case Some(image) => Left(ConflictError(s"Image ${image.sha256} already exists"))
      case None =>
        val image = Image.from(imageDto)
        Right(imageRepository.createImage(image))
    }
  }

  def uploadChunk(sha256: String, chunkDto: ChunkDTO): Either[ServiceError, Unit] = {

    imageRepository
      .findImage(sha256)
      .map { image =>
        image.findChunk(chunkDto.id) match {
          case Some(chunk) => Left(ConflictError(s"Chunk ${chunk.id} already exists for image ${image.sha256}"))
          case None =>
            val chunk        = Chunk.from(chunkDto)
            val updatedImage = image.insertChunk(chunk)
            imageRepository.updateImage(updatedImage)
            Right(())
        }
      }
      .getOrElse(Left(EntityNotFoundError(s"Could not find image $sha256")))
  }

  def downloadImage(sha256: String): Either[ServiceError, String] = {
    imageRepository
      .findImage(sha256)
      .map(image => Right(image.chunks.map(chunk => chunk.data).mkString("")))
      .getOrElse(Left(EntityNotFoundError(s"Could not find image $sha256")))
  }
}
