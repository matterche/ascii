package ascii

import akka.stream.Materializer
import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.libs.json.Json
import play.api.test.Helpers._
import play.api.test._

import scala.concurrent.Future

class AcceptanceSpec extends PlaySpec with GuiceOneAppPerTest with Injecting {
  implicit lazy val materializer: Materializer = app.materializer

  class TestScope() extends TestData {
    val imageRepository = inject[ImageRepository]
    imageRepository.deleteAll()

    val imageJson = Json.parse(imageRaw)
    val imageDto  = imageJson.as[ImageDTO]
    val image     = Image.from(imageDto)

    val chunk0Json = Json.parse(chunk0Raw)
    val chunk0Dto  = chunk0Json.as[ChunkDTO]
    val chunk0     = Chunk.from(chunk0Dto)

  }

  "AsciiController GET" should {

    "register a new image" in new TestScope {
      val result = route(
        app,
        FakeRequest(POST, "/image").withBody(imageJson)
      ).getOrElse(Future.failed(new Exception("failed to call test route")))

      status(result) mustBe CREATED

      imageRepository.exists(imageDto.sha256) mustBe true
    }

    "return 409 if image already exists" in {
      pending
//      status(result) mustBe CONFLICT
    }

    "upload a new image chunk" in new TestScope {
      imageRepository.createImage(image)

      val result = route(
        app,
        FakeRequest(POST, s"/image/${image.sha256}/chunks").withBody(chunk0Json)
      ).getOrElse(Future.failed(new Exception("failed to call test route")))

      status(result) mustBe CREATED

      image.chunks(chunk0.id) mustBe chunk0
    }

    "download an image" in {
      pending
      // contentType(home) mustBe Some(...)
//      contentAsString(result) must include("")
    }
  }
}
