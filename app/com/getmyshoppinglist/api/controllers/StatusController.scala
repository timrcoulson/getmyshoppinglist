
package com.getmyshoppinglist.api.controllers

import javax.inject._

import play.api.libs.json.Json
import play.api.mvc._

@Singleton
class StatusController @Inject()() extends InjectedController {
  def index = Action(parse.json) {
    Ok(Json.obj(
      "data" -> Json.obj(
        "message" -> "Ok!"
      )
    ))
  }
}
