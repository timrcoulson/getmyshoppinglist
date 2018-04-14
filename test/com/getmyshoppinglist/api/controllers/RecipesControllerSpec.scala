package com.getmyshoppinglist.api.controllers

import com.getmyshoppinglist.recipes.domain.{Ingredient, Ingredients, Recipe, RecipeService}
import org.scalatestplus.play._
import play.api.libs.json.Json
import play.api.test.Helpers._
import play.api.test._

import scala.concurrent.Await
import scala.concurrent.duration._

/**
  * Add your spec here.
  * You can mock out a whole application including requests, plugins etc.
  * For more information, consult the wiki.
  */
class RecipesControllerSpec extends PlaySpec with OneAppPerTest {
  "Routes" should {
    "create recipe" in {
      val resp = route(app, FakeRequest(POST, "/v1/recipes").withJsonBody(Json.parse(
        """
          {
            "id": "1c86dd0d-035e-47c8-850d-55ce29eb3b97",
            "name": "Chili Con Carne",
            "img": "http://www.google.com/image.png",
            "description": "Some description",
            "link": "http://www.google.com/",
            "serves": 5,
            "prepTime": 50,
            "cookingTime": 50,
            "ingredients": [],
            "method": []
          }
        """.stripMargin)))
      resp.map(status(_)) mustBe Some(OK)
    }
  }

  "Routes" should {
    "retrieve recipes" in {
      val recipeService = app.injector.instanceOf(classOf[RecipeService])
      val future = recipeService.add(new Recipe("1c86dd0d-035e-47c8-850d-55ce29eb3b98", "Chili Con Carne", "http://www.google.com/", 5,  "http://www.google.com/image.png", "Some description",
        new Ingredients(Seq(new Ingredient("1c86dd0d-035e-47c8-850d-55ce29eb3b93", "Mince", "MEAT", true, true, 1, "kg"))), Seq("Method"), 1, 1)
      )

      Await.result(future, 5000 millis)

      route(app, FakeRequest(GET, "/v1/recipes?query=a")).map(contentAsString(_).contains("Chili Con Carne")) mustBe Some(true)
    }
  }
}

