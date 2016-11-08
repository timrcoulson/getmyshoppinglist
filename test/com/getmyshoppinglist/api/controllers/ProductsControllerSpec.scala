package com.getmyshoppinglist.api.controllers

import org.scalatestplus.play._
import play.api.test.Helpers._
import play.api.test._

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 * For more information, consult the wiki.
 */
class ProductsControllerSpec extends PlaySpec with OneAppPerTest {

  "Routes" should {
    "retrieve products" in  {
      route(app, FakeRequest(GET, "/v1/products")).map(status(_)) mustBe Some(OK)
    }

  }
}
