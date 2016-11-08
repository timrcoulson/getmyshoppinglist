package com.getmyshoppinglist.recipes.domain

import com.getmyshoppinglist.plans.domain.Preferences

import scala.concurrent.Future

/**
  * Created by timcoulson on 30/10/2016.
  */
trait Recipes {
  def find(id: Int, preferences: Preferences): Future[Option[Recipe]]

  def getRecipes(recipeIds: Set[Int]): Future[List[Recipe]]

  def all(heads: Int = 1): Future[List[Recipe]]

  def withPrefs(meals: Int): Future[List[Recipe]]
}
