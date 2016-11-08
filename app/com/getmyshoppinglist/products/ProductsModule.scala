package com.getmyshoppinglist.products

import com.getmyshoppinglist.products.domain.Products
import com.getmyshoppinglist.products.infra.SlickProducts
import com.google.inject.AbstractModule

class ProductsModule extends AbstractModule {
  override def configure() = {
    bind(classOf[Products]).to(classOf[SlickProducts])
  }
}

