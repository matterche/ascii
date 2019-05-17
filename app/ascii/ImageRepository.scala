package ascii

import javax.inject.Singleton

import scala.collection.mutable

trait ImageRepository {
  def createImage(image: Image): Unit

  def exists(sha256: String): Boolean

  def deleteAll(): Unit

}

@Singleton
class InMemoryImageRepository extends ImageRepository {

  private val imageRepository = new mutable.HashMap[String, Image]

  override def createImage(image: Image): Unit = imageRepository(image.sha256) = image

  override def exists(sha256: String): Boolean = imageRepository.contains(sha256)

  override def deleteAll(): Unit = imageRepository.clear()
}
