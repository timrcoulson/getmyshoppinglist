package com.getmyshoppinglist.products.domain

import com.getmyshoppinglist.recipes.domain.{Ingredients, Recipe}
import com.google.inject.Inject

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class ProductService @Inject()(products: Products) {
  def all(): Future[Seq[Product]] = {
    products.all()
  }

  def getShoppingList(recipes: Seq[Recipe]): Future[Seq[ProductWithQuantity]] = {
    Future.sequence(
      recipes.flatMap(recipe => recipe.ingredients.items)
        .groupBy(ingredient => ingredient.name)
        .values.map(ingredients => (ingredients.head.name, ingredients.map(_.quantity).sum))
        .map(nameAndQuantity => getCheapestProduct(nameAndQuantity._1, nameAndQuantity._2))
        .toSeq
    ).map(products => products.flatten)
  }

  def costOfProducts(ingredients: Ingredients): Future[Float] = {
    Future.sequence(
      ingredients.items.map(ingredient => {
        getCheapestProduct(ingredient.name, ingredient.quantity)
      })
    ).map(products => products.map {
      case Some(p) => p.price
      case None => 0F
    }.sum)
  }

  def getCheapestProduct(ingredient: String = "Spaghetti", quantityRequired: Int = 1): Future[Option[ProductWithQuantity]] = {
    products
      .findByName(ingredient)
      .map(products => {
        products
          .map(product => ProductWithQuantity.get(product, quantityRequired)) match {
          case Seq() => None
          case products => Option(products.minBy(_.price))
        }
      })
  }
}
