package com.getmyshoppinglist.plans.infra.actors

import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject

import akka.actor._
import akka.pattern.pipe
import com.getmyshoppinglist.plans.domain.requests._
import com.getmyshoppinglist.plans.domain.{Plan, PlanService, Plans, Preferences}

import scala.collection.JavaConverters._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object PlanActor {
  def props = Props[PlanActor]
}

class PlanActor @Inject()(planService: PlanService, plans: Plans) extends Actor {

  val queue = new ConcurrentHashMap[String, Future[Any]]().asScala

  def receive = {
    case AddRecipe(id: String, recipeId: String) => pushToQueue(id, () => planService.addRecipe(id, recipeId).flatMap(plans.save))
    case RemoveRecipe(id: String, recipeId: String) => pushToQueue(id, () => planService.removeRecipe(id, recipeId).flatMap(plans.save))
    case SavePlan(plan: Plan) => pushToQueue(plan.id.toString, () => plans.save(plan))
    case SwapRecipe(id: String, recipeId: String) => pushToQueue(id, () => planService.swapRecipe(id, recipeId).flatMap(plans.save))
    case GeneratePlan(preferences: Preferences) => planService.generate(preferences) pipeTo sender()
    case RetrievePlan(id: String) => pushToQueue(id, () => planService.retrieve(id))
  }

  private def pushToQueue(planId: String, command: () => Future[Plan]) = {
    queue.values.filterNot(_.isCompleted) // Remove plans from the queue that have no mutations in progress.

    val future: Future[Plan] = queue.get(planId) match {
      case Some(futurePlan: Future[Plan]) => futurePlan.flatMap(plan => command())
      case _ => command()
    }
    queue.put(planId, future)
    future pipeTo sender()
  }
}
