package com.getmyshoppinglist.recipes.domain

import play.api.libs.json.{JsValue, Json, Writes}

/**
  * Created by timcoulson on 09/11/2016.
  */
class Ingredients(val items: Seq[Ingredient] = Seq()) {
  private val normalisedIngredients: Map[String, Ingredient] = items.map(ingredient => (ingredient.name, ingredient)).toMap

  def sub(ingredients: Ingredients): Ingredients = {
    new Ingredients(
      normalisedIngredients.map {
        case (ingredientName, ingredient) => {
          ingredientName -> ingredient.copy(quantity = math.max(0, ingredient.quantity - ingredients.quantity(ingredientName)))
        }
      }
        .values
        .toSeq
    )
  }

  def quantity(ingredientName: String): Int = {
    normalisedIngredients.get(ingredientName).map(i => i.quantity) match {
      case Some(i) => i
      case None => 0
    }
  }
}

object Ingredients {
  implicit val implicitWrites = new Writes[Ingredients] {
    def writes(ingredients: Ingredients): JsValue = Json.toJson(ingredients.items)
  }
}


