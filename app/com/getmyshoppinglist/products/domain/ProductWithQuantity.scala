package com.getmyshoppinglist.products.domain

import play.api.libs.json.{JsValue, Json, Writes}

case class ProductWithQuantity(quantityRequired: Int, product: Product) {
  lazy val price = quantityRequired * product.price
}

object ProductWithQuantity {
  def get(product: Product, quantityRequired: Int): ProductWithQuantity = {
    new ProductWithQuantity(roundUp(quantityRequired / product.quantity), product)
  }

  implicit val writes = new Writes[ProductWithQuantity] {
    def writes(productWithQuantity: ProductWithQuantity): JsValue = {
      val product = productWithQuantity.product
      Json.obj(
        "name" -> product.ingredientName,
        "price" -> prettyPrice(product.price),
        "number" -> productWithQuantity.quantityRequired,
        "aisle" -> prettyAisle(product.aisle),
        "quantity" -> product.quantity,
        "subtotal" -> prettyPrice(productWithQuantity.price),
        "unit" -> product.unit,
        "url" -> product.url
      )
    }

    def prettyPrice(price: Float): String = {
      BigDecimal(price).setScale(2, BigDecimal.RoundingMode.HALF_UP).toDouble.formatted("£%.2f")
    }

    def prettyAisle(aisle: String): String = {
      aisle match {
        case "BAKED" => "Baked goods"
        case "BAKING" => "Baking"
        case "CONDIMENTS" => "Condiments"
        case "DAIRY" => "Dairy"
        case "DRY_GOODS" => "Dry goods"
        case "FROZEN" => "Frozen"
        case "FRUIT" => "Fruit"
        case "HERBS_SPICES" => "Herbs & Spices"
        case "MEAT" => "Meat"
        case "TINNED" => "Tinned goods"
        case "VEGETABLE" => "Vegetables"
        case _ => "Unknown aisle"
      }
    }
  }

  private def roundUp(d: Double) = math.max(1, math.ceil(d).toInt)
}
