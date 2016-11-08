package com.getmyshoppinglist.plans.domain

import play.api.libs.functional.syntax._
import play.api.libs.json._

case class Preferences(people: Int, meals: Int, diet: String) {

}

object Preferences {
  implicit val preferenceReads: Reads[Preferences] = (
    (JsPath \ "people").readNullable[Int] and
      (JsPath \ "meals").readNullable[Int] and
      (JsPath \ "diet").readNullable[String]
    ) (Preferences.fromJson _)

  implicit val writes = new Writes[Preferences] {
    def writes(preferences: Preferences): JsValue = {
      Json.obj(
        "people" -> preferences.people,
        "diet" -> preferences.diet,
        "meals" -> preferences.meals
      )
    }
  }

  def fromJson(people: Option[Int], meals: Option[Int], diet: Option[String]): Preferences =
    new Preferences(people.getOrElse(3), meals.getOrElse(3), diet.getOrElse("None"))
}
