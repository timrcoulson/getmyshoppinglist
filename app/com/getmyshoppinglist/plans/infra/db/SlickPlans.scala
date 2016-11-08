package com.getmyshoppinglist.plans.infra.db

import java.util.UUID

import com.getmyshoppinglist.plans.domain.{Plan, Plans, Preferences}
import com.google.inject.Inject
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import play.libs.Json
import slick.driver.JdbcProfile

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class SlickPlans @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
  extends HasDatabaseConfigProvider[JdbcProfile] with Plans {

  import driver.api._

  override def save(plan: Plan): Future[Plan] = {
    val json = Json.toJson(plan).toString
    val id = plan.id.toString
    db.run(DBIO.seq(
      sqlu"INSERT INTO plans (id, plan, timestamp) VALUES (${id}, ${json}, NOW())"
    )).map(result => plan)
  }

  override def find(id: String): Future[Plan] = db.run(
    sql"""
    SELECT p.plan
    FROM plans p
    WHERE p.id = ${id}
    ORDER BY `order` DESC
    """.as[PlanRow]
  ).map(
    pr =>
      pr.map(pr => new Plan(UUID.randomUUID(), Preferences(1, 2, "Hello"), Seq(), Seq())).head // TODO fix this deserialisation
  )
}

object SlickPlans {

}
