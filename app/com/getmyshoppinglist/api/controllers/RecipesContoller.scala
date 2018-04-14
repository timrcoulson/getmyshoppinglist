package com.getmyshoppinglist.api.controllers

import javax.inject._

import akka.actor.ActorSystem
import com.getmyshoppinglist.recipes.domain.{Recipe, RecipeService}
import com.google.inject.Inject
import play.api.libs.json.{JsError, JsSuccess, Json}
import play.api.mvc._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

@Singleton
class RecipesContoller @Inject()(recipeService: RecipeService, actorSystem: ActorSystem) extends InjectedController {
  def search(query: String) = Action.async { implicit req =>
    recipeService.search(query).map(recipes => {
      Ok(Json.obj(
        "data" -> Json.obj(
          "recipes" -> Json.toJson(recipes)
        )
      ))
    })
  }

  def create = Action.async(BodyParsers.parse.json) { implicit req =>
    req.body.validate[Recipe] match {
      case s: JsSuccess[Recipe] =>
        recipeService.add(s.get)
        Future(Ok("Ok!"))
      case e: JsError => Future(BadRequest(e.toString))
    }
  }
}
