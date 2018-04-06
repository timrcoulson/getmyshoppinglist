package com.getmyshoppinglist.recipes.domain

import play.api.libs.json.{JsValue, Json, Writes}
import slick.jdbc.GetResult

/**
  * Created by timcoulson on 07/11/2016.
  */
case class Ingredient(
                       id: Int,
                       name: String,
                       aisle: String,
                       keeps: Boolean,
                       exact: Boolean,
                       quantity: Int,
                       unit: String,
                       scales: String
                     )

object Ingredient {
  implicit val implicitFooWrites = new Writes[Ingredient] {
    def writes(ingredient: Ingredient): JsValue = {
      Json.obj(
        "id" -> ingredient.id,
        "name" -> ingredient.name,
        "aisle" -> ingredient.aisle,
        "keeps" -> ingredient.keeps,
        "exact" -> ingredient.exact,
        "quantity" -> ingredient.quantity,
        "unit" -> ingredient.unit,
        "scales" -> ingredient.scales
      )
    }
  }

  implicit val getIngredient = GetResult(r => Ingredient(r.<<, r.<<, r.<<, r.<<, r.<<, r.<<, r.<<, r.<<))
}
