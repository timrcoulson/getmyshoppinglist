package com.getmyshoppinglist.recipes.domain

import com.getmyshoppinglist.plans.domain.Preferences
import com.getmyshoppinglist.products.domain.ProductService
import com.google.inject.Inject

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.language.postfixOps
import scala.util.Random

/**
  * Created by timcoulson on 09/11/2016.
  */
class RecipeService @Inject()(productService: ProductService, recipes: Recipes) {
  def add(recipe: Recipe): Future[Int] = {
    recipes.add(recipe)
  }

  def search(query: String): Future[Seq[Recipe]] = {
    recipes.all().map(recipes => {
      recipes.filter(recipe => recipe.name.toLowerCase().contains(query.toLowerCase()))
    })
  }

  def withPrefs(preferences: Preferences): Future[Seq[Recipe]] = {
    recipes.all(preferences.people)
      .map(recipes => {
        cheapestRecipes(recipes, preferences.meals)
      })
  }

  def find(id: String, preferences: Preferences): Future[Option[Recipe]] = {
    recipes.find(id, preferences)
  }

  def all(): Future[Seq[Recipe]] = {
    recipes.all()
  }

  private def cheapestRecipes(
                               recipes: Seq[Recipe],
                               number: Int = 5,
                               currentIngredients: Ingredients = new Ingredients(),
                               selectedRecipes: Seq[Recipe] = Seq()
                             ): Seq[Recipe] = {
    if (selectedRecipes.length >= number || selectedRecipes.length == recipes.length) {
      return selectedRecipes
    }
    cheapestRecipes(recipes, number, new Ingredients(), selectedRecipes :+ selectRecipe(selectedRecipes, recipes, currentIngredients))
  }

  private def selectRecipe(selectedRecipes: Seq[Recipe], recipes: Seq[Recipe], currentIngredients: Ingredients): Recipe = {
    if (selectedRecipes.isEmpty) {
      return Random.shuffle(recipes).head
    }
    Await.result(
      Future.sequence(recipes
        .filter(recipe => !selectedRecipes.contains(recipe)) // Ignore recipes we've used.
        .map(recipe => (recipe, productService.costOfProducts(recipe.ingredients.sub(currentIngredients)))) // Get costs
        .map(recipeAndFutureCost => recipeAndFutureCost._2.map(cost => (recipeAndFutureCost._1, cost))) // Wrap recipe and cost in future
      ),
      10 seconds)
      .minBy(_._2)._1
  }
}
