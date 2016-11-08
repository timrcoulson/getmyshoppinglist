package com.getmyshoppinglist.recipes

import com.getmyshoppinglist.recipes.domain.Recipes
import com.getmyshoppinglist.recipes.infra.SlickRecipes
import com.google.inject.AbstractModule

/**
  * Created by timcoulson on 03/11/2016.
  */
class RecipesModule extends AbstractModule {

  override def configure() = {
    bind(classOf[Recipes]).to(classOf[SlickRecipes])
  }
}

