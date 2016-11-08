package com.getmyshoppinglist.recipes.infra

import com.getmyshoppinglist.plans.domain.Preferences
import com.getmyshoppinglist.recipes.domain.{Ingredient, Recipe, Recipes}
import com.google.inject.Inject
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.driver.JdbcProfile
import slick.jdbc.SQLActionBuilder

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/**
  * Created by timcoulson on 03/11/2016.
  */

class SlickRecipes @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
  extends HasDatabaseConfigProvider[JdbcProfile] with Recipes {

  import driver.api._

  def getRecipes(recipeIds: Set[Int]): Future[List[Recipe]] = {
    all().map(recipes => {
      recipes.filter(recipe => recipeIds.contains(recipe.id)).toList
    })
  }

  override def all(heads: Int = 1): Future[List[Recipe]] = {
    mapToRecipe(baseQuery(heads.toString))
  }

  private def mapToRecipe(query: SQLActionBuilder): Future[List[Recipe]] = {
    db.run(query.as[(RecipeRow, Ingredient)])
      .map(i => i
        .groupBy(_._1.id.toString())
        .mapValues(rows => Recipe.fromRow(rows))
        .values
        .toList
      )
  }

  private def baseQuery(heads: String = "1", extra: String = ""): SQLActionBuilder = {
    sql"""
      SELECT
      r.id, r.name, r.link, r.serves, r.alt_cost_per_head, r.img, r.description, r.method, r.prep_time, r.cooking_time,
      i.id, i.name, i.aisle, i.keeps, i.exact, i.url, (ri.quantity * #$heads / r.serves), ri.unit, ri.scales
      FROM recipes r
      JOIN recipes_ingredients ri ON ri.recipe_id = r.id
      JOIN ingredients i ON ri.ingredient_id = i.id
      WHERE r.deleted_at is null
      AND r.active = 1
      """
  }

  override def withPrefs(meals: Int): Future[List[Recipe]] = {
    all().map(recipes => {
      recipes.toList.take(meals)
    })
  }

  override def find(id: Int, preferences: Preferences): Future[Option[Recipe]] = {
    all().map(recipes => {
      recipes.find(recipe => recipe.id == id)
    })
  }
}
