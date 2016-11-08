
package com.getmyshoppinglist.api.controllers

import javax.inject._

import akka.actor.ActorSystem
import com.getmyshoppinglist.products.domain.{Product, ProductService}
import play.api.libs.json._
import play.api.mvc._

import scala.concurrent.ExecutionContext.Implicits.global

class ProductsController @Inject()(productService: ProductService, actorSystem: ActorSystem) extends InjectedController {
  def index(): Action[AnyContent] = Action.async { implicit req =>
    productService.all().map(products => {
      val prod = products :+ new Product(1, 2, "", "test", 1, "sd", 1F, "hello")
      Ok(Json.obj(
        "data" -> Json.obj("products" -> Json.toJson(products)))
      )
    })
  }
}
