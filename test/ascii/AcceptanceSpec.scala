package ascii

import akka.stream.Materializer
import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.libs.json.{JsValue, Json}
import play.api.test.Helpers._
import play.api.test._

import scala.concurrent.Future

class AcceptanceSpec extends PlaySpec with GuiceOneAppPerTest with Injecting {
  implicit lazy val materializer: Materializer = app.materializer

  class TestScope() extends TestData {
    val imageRepository = inject[ImageRepository]
    imageRepository.deleteAll() // TODO: is this necessary?

    val imageJson = Json.parse(imageRaw)
    val imageDto  = imageJson.as[ImageDTO]
    val image     = Image.from(imageDto)

    val chunk0Json = Json.parse(chunk0Raw)
    val chunk0Dto  = chunk0Json.as[ChunkDTO]
    val chunk0     = Chunk.from(chunk0Dto)

    val chunk1Json = Json.parse(chunk1Raw)
    val chunk1Dto  = chunk1Json.as[ChunkDTO]
    val chunk1     = Chunk.from(chunk1Dto)
  }

  "AsciiController" when {

    "registering new images" should {

      "register a new image" in new TestScope {
        val result = registerImage(imageJson)

        status(result) mustBe CREATED

        imageRepository.exists(imageDto.sha256) mustBe true
      }

      "return 409 if image already exists" in new TestScope {
        imageRepository.createImage(image)
        status(registerImage(imageJson)) mustBe CONFLICT
      }

      "return 400 for malformed request" in new TestScope {
        val malformedPayload = Json.parse("""{"not":"valid"}""")
        status(registerImage(malformedPayload)) mustBe BAD_REQUEST
      }

      "return 415 for unsupported payload format" in new TestScope {
        val result = route(
          app,
          FakeRequest(POST, "/image").withBody("unsupported media")
        ).getOrElse(Future.failed(new Exception("failed to call test route")))

        status(result) mustBe UNSUPPORTED_MEDIA_TYPE
      }
    }

    "uploading new image chunks" should {

      "upload a new image chunk" in new TestScope {
        imageRepository.createImage(image)

        val result = uploadChunk(image, chunk0Json)

        status(result) mustBe CREATED

        image.chunks(chunk0.id) mustBe chunk0
      }

      "upload several chunks for the same image in order" in new TestScope {
        imageRepository.createImage(image)

        status(uploadChunk(image, chunk0Json)) mustBe CREATED
        status(uploadChunk(image, chunk1Json)) mustBe CREATED

        image.chunks mustBe Seq(chunk0, chunk1)
      }

      "upload several chunks for the same image out of order" in new TestScope {
        imageRepository.createImage(image)

        status(uploadChunk(image, chunk1Json)) mustBe CREATED
        status(uploadChunk(image, chunk0Json)) mustBe CREATED

        image.chunks mustBe Seq(chunk0, chunk1)
      }

      "return 400 for malformed request" in new TestScope {
        val malformedPayload = Json.parse("""{"not":"valid"}""")
        status(uploadChunk(image, malformedPayload)) mustBe BAD_REQUEST
      }

      "return 415 for unsupported payload format" in new TestScope {
        val result = route(
          app,
          FakeRequest(POST, s"/image/${image.sha256}/chunks").withBody("unsupported media")
        ).getOrElse(Future.failed(new Exception("failed to call test route")))

        status(result) mustBe UNSUPPORTED_MEDIA_TYPE
      }

    }

    "downloading images" should {
      "download an image" in new TestScope {
        imageRepository.createImage(image)
        image.insertChunk(chunk0)
        image.insertChunk(chunk1)

        val result = route(
          app,
          FakeRequest(GET, s"/image/${image.sha256}")
        ).getOrElse(Future.failed(new Exception("failed to call test route")))

        status(result) mustBe OK

        contentType(result) mustBe Some(TEXT)
        contentAsString(result) mustBe chunk0.data + chunk1.data
      }
    }

  }

  private def registerImage(imageJson: JsValue) = {
    route(
      app,
      FakeRequest(POST, "/image").withBody(imageJson)
    ).getOrElse(Future.failed(new Exception("failed to call test route")))
  }

  private def uploadChunk(image: Image, chunkPayload: JsValue) = {
    route(
      app,
      FakeRequest(POST, s"/image/${image.sha256}/chunks").withBody(chunkPayload)
    ).getOrElse(Future.failed(new Exception("failed to call test route")))
  }
}
