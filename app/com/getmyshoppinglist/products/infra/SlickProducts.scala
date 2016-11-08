package com.getmyshoppinglist.products.infra

import com.getmyshoppinglist.products.domain.{Product, Products}
import com.google.inject.Inject
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.driver.JdbcProfile

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class SlickProducts @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
  extends HasDatabaseConfigProvider[JdbcProfile] with Products {

  import driver.api._

  override def findByName(name: String): Future[Seq[Product]] = {
    all().map(products => {
      products.filter(product => product.ingredientName == name)
    })
  }

  // Keep all the products in memory
  override def all(): Future[Seq[Product]] = {
    if (SlickProducts.all == null) {
      SlickProducts.all = db.run(
        sql"""
      SELECT
      p.id, p.ingredient_id, p.name, p.url, p.quantity, p.unit, p.price, ps.type
      FROM products p
      JOIN product_sources ps on p.name = ps.name
      """.as[Product]
      )
    }
    SlickProducts.all
  }
}

object SlickProducts {
  var all: Future[Seq[Product]] = _
}
