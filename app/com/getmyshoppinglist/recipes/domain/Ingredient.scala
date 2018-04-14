package com.getmyshoppinglist.recipes.domain

import play.api.libs.json._
import slick.jdbc.GetResult
import play.api.libs.functional.syntax._

/**
  * Created by timcoulson on 07/11/2016.
  */
case class Ingredient(
                       id: String,
                       name: String,
                       aisle: String,
                       keeps: Boolean,
                       exact: Boolean,
                       quantity: Int,
                       unit: String
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
        "unit" -> ingredient.unit
      )
    }
  }


  implicit val implicitFooReads: Reads[Ingredient] = (
    (JsPath \ "id").read[String] and
      (JsPath \ "name").read[String] and
      (JsPath \ "aisle").read[String] and
      (JsPath \ "keeps").read[Boolean] and
      (JsPath \ "exact").read[Boolean] and
      (JsPath \ "quantity").read[Int] and
      (JsPath \ "unit").read[String]
    ) (Ingredient.fromJson _)

  implicit val getIngredient = GetResult(r => Ingredient(r.<<, r.<<, r.<<, r.<<, r.<<, r.<<, r.<<))

  def fromJson(
                id: String,
                name: String,
                aisle: String,
                keeps: Boolean,
                exact: Boolean,
                quantity: Int,
                unit: String
              ): Ingredient = {
    new Ingredient(
      id, name, aisle, keeps, exact, quantity, unit
    )
  }

}
