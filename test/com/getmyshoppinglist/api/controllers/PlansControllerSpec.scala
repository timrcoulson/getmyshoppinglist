package com.getmyshoppinglist.api.controllers

import org.scalatestplus.play._
import play.api.libs.json.Json
import play.api.test.Helpers._
import play.api.test._

/**
  * Add your spec here.
  * You can mock out a whole application including requests, plugins etc.
  * For more information, consult the wiki.
  */
class PlansControllerSpec extends PlaySpec with OneAppPerTest {
  "Routes" should {
    "retrieve status" in {
      val resp = route(app, FakeRequest(PUT, "/v1/plan").withJsonBody(Json.parse("""{ "people": 1, "diet": "VEG", "meals": 5 }""")))
      resp.map(status(_)) mustBe Some(OK)
    }
  }
}
