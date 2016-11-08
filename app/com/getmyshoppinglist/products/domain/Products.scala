package com.getmyshoppinglist.products.domain

import scala.concurrent.Future

trait Products {
  def findByName(name: String): Future[Seq[Product]]

  def all(): Future[Seq[Product]]
}
