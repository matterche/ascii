package ascii

import akka.stream.Materializer
import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.test._
import play.api.test.Helpers._

class AcceptanceSpec extends PlaySpec with GuiceOneAppPerTest {
  implicit lazy val materializer: Materializer = app.materializer

  "AsciiController GET" should {

    "register a new image" in {
      val controller = new AsciiController(stubControllerComponents())
      val result     = controller.registerImage().apply(FakeRequest(POST, "/image").withBody(""))

      status(result) mustBe OK
      // contentType(home) mustBe Some(...)
      contentAsString(result) must include("")
    }

    "register a new image chunk" in {
      val controller = new AsciiController(stubControllerComponents())
      val result     = controller.registerImage().apply(FakeRequest(POST, "/image").withBody(""))

      status(result) mustBe OK
      // contentType(home) mustBe Some(...)
      contentAsString(result) must include("")
    }

    "download an image" in {
      val controller = new AsciiController(stubControllerComponents())
      val result     = controller.registerImage().apply(FakeRequest(POST, "/image").withBody(""))

      status(result) mustBe OK
      // contentType(home) mustBe Some(...)
      contentAsString(result) must include("")
    }
  }
}
