package com.getmyshoppinglist.plans.domain

import scala.concurrent.Future

trait Plans {
  def save(plan: Plan): Future[Plan]

  def find(id: String): Future[Plan]
}
