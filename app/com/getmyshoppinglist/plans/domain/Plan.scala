package com.getmyshoppinglist.plans.domain

import java.util.UUID

import com.getmyshoppinglist.products.domain.ProductWithQuantity
import com.getmyshoppinglist.recipes.domain.Recipe
import play.api.libs.json.{JsValue, Json, Writes}

import scala.util.Random

case class Plan(
                 id: UUID, preferences: Preferences,
                 recipes: Seq[Recipe],
                 products: Seq[ProductWithQuantity]
               ) {

  lazy val cost = to2dp(products.map(_.price).sum.toDouble)

  lazy val costPerHead = to2dp(cost / (preferences.meals * preferences.people))

  def withoutRecipe(recipeId: Int): Plan = {
    copy(recipes = recipes.filterNot(recipe => recipeId == recipe.id))
  }

  def withRecipe(recipe: Recipe): Plan = copy(recipes = recipe +: recipes)

  def withRecipes(recipes: Seq[Recipe]): Plan = copy(recipes = recipes)

  def withProducts(products: Seq[ProductWithQuantity]): Plan = {
    copy(products = products)
  }

  def replaceRecipe(recipeId: Int, otherRecipes: Seq[Recipe]): Plan = {
    val newRecipes = recipes.map(recipe => {
      lazy val unusedRecipes = otherRecipes.filter(recipe => contains(recipe.id))
      if (recipe.id == recipeId) {
        Random.shuffle(unusedRecipes).head
      } else {
        recipe
      }
    })
    copy(recipes = newRecipes)
  }

  def contains(recipeId: Int): Boolean = {
    recipes.exists(planRecipe => planRecipe.id == recipeId)
  }

  private def to2dp(double: Double): Double = {
    BigDecimal(double).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble
  }
}

object Plan {
  implicit val writes = new Writes[Plan] {
    def writes(plan: Plan): JsValue = {
      Json.obj(
        "id" -> plan.id,
        "recipes" -> Json.toJson(plan.recipes),
        "preferences" -> Json.toJson(plan.preferences),
        "shoppingList" -> Json.toJson(plan.products),
        "cost" -> Json.toJson(price(plan.cost)),
        "costPerHead" -> Json.toJson(price(plan.costPerHead))
      )
    }
  }

  def price(cost: Double): String = "£" + "%.2f".format(cost)
}

