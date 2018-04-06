package com.getmyshoppinglist.recipes.infra

import slick.jdbc.GetResult

case class RecipeRow(id: Int, name: String, link: String, serves: Int, img: String, description: String, method: String, prepTime: Int, cookingTime: Int)

object RecipeRow {
  implicit val getRecipe = GetResult(r => RecipeRow(r.<<, r.<<, r.<<,  r.<<, r.<<, r.<<, r.<<, r.<<, r.<<))
}
