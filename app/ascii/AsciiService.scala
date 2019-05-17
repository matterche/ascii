package ascii

import common.ServiceError
import javax.inject.{Inject, Singleton}

@Singleton
class AsciiService @Inject()(imageRepository: ImageRepository) {
  def createImage(image: Image): Either[ServiceError, Unit] = {
    Right(imageRepository.createImage(image))
  }

}
