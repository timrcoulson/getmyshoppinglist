import org.scalatestplus.play._

/**
  * add your integration spec here.
  * An integration test will fire up a whole play application in a real (or headless) browser
  */
class IntegrationSpec extends PlaySpec with OneServerPerTest with OneBrowserPerTest with HtmlUnitFactory {
  "Application" should {

    "work from within a browser" in {

      go to ("http://localhost:" + port + "/status")

      pageSource must include("Ok!")
    }
  }
}
