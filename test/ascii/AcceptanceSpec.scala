package ascii

import akka.stream.Materializer
import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.test.Helpers._
import play.api.test._

import scala.concurrent.Future

class AcceptanceSpec extends PlaySpec with GuiceOneAppPerTest with Injecting {
  implicit lazy val materializer: Materializer = app.materializer

  class TestScope() extends TestData {
    val imageRepository = inject[ImageRepository]
    imageRepository.deleteAll()
  }

  "AsciiController GET" should {

    "register a new image" in new TestScope {
      val result = route(
        app,
        FakeRequest(POST, "/image").withBody(imageJson)
      ).getOrElse(Future.failed(new Exception("failed to call test route")))

      status(result) mustBe CREATED

      imageRepository.exists(image.sha256) mustBe true
    }

    "return 409 if image already exists" in {
      pending
//      status(result) mustBe CONFLICT
    }

    "register a new image chunk" in {
      pending
//      status(result) mustBe CREATED
    }

    "download an image" in {
      pending
      // contentType(home) mustBe Some(...)
//      contentAsString(result) must include("")
    }
  }
}
