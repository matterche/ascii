package ascii

import javax.inject.Singleton

import scala.collection.JavaConverters._

trait ImageRepository {

  def createImage(image: Image): Unit

  def updateImage(image: Image): Unit

  def exists(sha256: String): Boolean

  def findImage(sha256: String): Option[Image]

  def deleteAll(): Unit
}

@Singleton
class InMemoryImageRepository extends ImageRepository {

  private val imageRepository = new java.util.concurrent.ConcurrentHashMap[String, Image]().asScala

  override def createImage(image: Image): Unit = imageRepository(image.sha256) = image

  override def updateImage(image: Image): Unit = imageRepository(image.sha256) = image

  override def exists(sha256: String): Boolean = imageRepository.contains(sha256)

  override def deleteAll(): Unit = imageRepository.clear()

  override def findImage(sha256: String): Option[Image] = imageRepository.get(sha256)

}
