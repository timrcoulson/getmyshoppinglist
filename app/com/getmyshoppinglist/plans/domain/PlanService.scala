package com.getmyshoppinglist.plans.domain

import java.util.UUID
import javax.inject.Inject

import com.getmyshoppinglist.products.domain.ProductService
import com.getmyshoppinglist.recipes.domain.{Recipe, RecipeService}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class PlanService @Inject()(plans: Plans, recipeService: RecipeService, productService: ProductService) {
  def removeRecipe(id: String, recipeId: Int): Future[Plan] = {
    for {
      plan <- plans.find(id)
      products <- productService.getShoppingList(plan.withoutRecipe(recipeId).recipes)
    } yield plan.withoutRecipe(recipeId).withProducts(products)
  }

  def addRecipe(id: String, recipeId: Int): Future[Plan] = {
    for {
      plan <- plans.find(id)
      recipe <- recipeService.find(recipeId, plan.preferences)
      newProducts <- {
        recipe match {
          case Some(r: Recipe) => productService.getShoppingList(r +: plan.recipes)
          case _ => return Future(plan)
        }
      }
    } yield plan.withRecipe(recipe.get).withProducts(newProducts)
  }

  def generate(preferences: Preferences): Future[Plan] = {
    for {
      recipes <- recipeService.withPrefs(preferences)
      products <- productService.getShoppingList(recipes)
    } yield Plan(id = UUID.randomUUID(), preferences = preferences, recipes = recipes, products = products)
  }

  def retrieve(id: String): Future[Plan] = plans.find(id)

  def swapRecipe(id: String, recipeId: Int): Future[Plan] = {
    // Get a new recipe
    val plan = for {
      plan <- plans.find(id)
      allRecipes <- recipeService.all()
    } yield plan.replaceRecipe(recipeId, allRecipes)

    // Get the new products
    for {
      plan <- plan
      newProducts <- productService.getShoppingList(plan.recipes)
    } yield plan.withProducts(newProducts)
  }
}
