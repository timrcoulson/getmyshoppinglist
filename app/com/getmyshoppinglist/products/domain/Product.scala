package com.getmyshoppinglist.products.domain

import play.api.libs.json._
import slick.jdbc.GetResult

case class Product(id: Int, ingredientId: Int, ingredientName: String, url: String, quantity: Int, unit: String, price: Float, aisle: String)

object Product {
  implicit val getProduct = GetResult(r => Product(r.<<, r.<<, r.<<, r.<<, r.<<, r.<<, r.<<, r.<<))

  implicit val writes = new Writes[Product] {
    def writes(product: Product): JsValue = {
      Json.obj(
        "name" -> JsString(product.ingredientName),
        "price" -> JsNumber(BigDecimal(product.price).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble),
        "quantity" -> JsNumber(product.quantity),
        "unit" -> JsString(product.unit),
        "url" -> JsString(product.url)
      )
    }
  }
}

