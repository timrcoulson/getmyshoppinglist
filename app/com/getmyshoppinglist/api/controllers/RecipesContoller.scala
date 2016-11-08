package com.getmyshoppinglist.api.controllers

import javax.inject._

import akka.actor.ActorSystem
import com.getmyshoppinglist.recipes.domain.RecipeService
import com.google.inject.Inject
import play.api.libs.json.Json
import play.api.mvc._

import scala.concurrent.ExecutionContext.Implicits.global

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
}
