package com.getmyshoppinglist.api.controllers

import org.scalatestplus.play._
import play.api.test.Helpers._
import play.api.test._

/**
  * Add your spec here.
  * You can mock out a whole application including requests, plugins etc.
  * For more information, consult the wiki.
  */
class RecipesControllerSpec extends PlaySpec with OneAppPerTest {

  "Routes" should {
    "retrieve recipes" in {
      route(app, FakeRequest(GET, "/v1/recipes?query=hello")).map(status(_)) mustBe Some(OK)
    }

  }
}
