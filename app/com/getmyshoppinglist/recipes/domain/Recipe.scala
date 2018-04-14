package com.getmyshoppinglist.recipes.domain

import com.getmyshoppinglist.recipes.infra.RecipeRow
import play.api.libs.functional.syntax._
import play.api.libs.json._

/**
  * Created by timcoulson on 30/10/2016.
  */
case class Recipe(id: String, name: String, link: String, serves: Int, img: String, description: String, ingredients: Ingredients, method: Seq[String], prepTime: Int, cookingTime: Int)

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

  def fromJson(
                id: String,
                name: String,
                img: String,
                description: String,
                link: String,
                serves: Int,
                prepTime: Int,
                cookingTime: Int,
                ingredients: Seq[Ingredient],
                method: Seq[String]
              ): Recipe = {

    val ingredientsObj = new Ingredients(ingredients);
    new Recipe(
      id, name, link, serves, img, description, ingredientsObj, method, prepTime, cookingTime
    )
  }

  implicit val recipeReads: Reads[Recipe] = (
    (JsPath \ "id").read[String] and
      (JsPath \ "name").read[String] and
      (JsPath \ "img").read[String] and
      (JsPath \ "description").read[String] and
      (JsPath \ "link").read[String] and
      (JsPath \ "serves").read[Int] and
      (JsPath \ "prepTime").read[Int] and
      (JsPath \ "cookingTime").read[Int] and
      (JsPath \ "ingredients").read[Seq[Ingredient]] and
      (JsPath \ "method").read[Seq[String]]
    ) (Recipe.fromJson _)
}

