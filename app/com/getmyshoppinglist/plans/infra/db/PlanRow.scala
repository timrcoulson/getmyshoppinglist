package com.getmyshoppinglist.plans.infra.db

import slick.jdbc.GetResult

case class PlanRow(json: String)

object PlanRow {
  implicit val getRecipe = GetResult(r => PlanRow(r.<<))
}
