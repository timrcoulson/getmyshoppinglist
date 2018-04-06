package com.getmyshoppinglist.recipes.domain

import com.getmyshoppinglist.recipes.infra.RecipeRow
import play.api.libs.json.{JsValue, Json, Writes}

/**
  * Created by timcoulson on 30/10/2016.
  */
case class Recipe(id: Int, name: String, link: String, serves: Int, img: String, description: String, ingredients: Ingredients, method: Seq[String], prepTime: Int, cookingTime: Int)

object Recipe {
  def fromRow(rows: Seq[(RecipeRow, Ingredient)]): Recipe = {
    val recipeRow = rows.head._1
    new Recipe(
      recipeRow.id,
      recipeRow.name,
      recipeRow.link,
      recipeRow.serves,
      recipeRow.img,
      recipeRow.description,
      new Ingredients(rows.map(_._2)),
      Json.parse(recipeRow.method).validate[Seq[String]].get,
      recipeRow.prepTime,
      recipeRow.cookingTime
    )
  }

  implicit val implicitRecipeWrites = new Writes[Recipe] {
    def writes(recipe: Recipe): JsValue = {
      Json.obj(
        "id" -> recipe.id,
        "name" -> recipe.name,
        "img" -> recipe.img,
        "description" -> recipe.description,
        "link" -> recipe.link,
        "serves" -> recipe.serves,
        "prepTime" -> recipe.prepTime,
        "cookingTime" -> recipe.cookingTime,
        "ingredients" -> Json.toJson(recipe.ingredients),
        "method" -> Json.toJson(recipe.method)
      )
    }
  }
}
