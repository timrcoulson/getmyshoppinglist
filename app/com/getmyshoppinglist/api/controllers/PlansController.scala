package com.getmyshoppinglist.api.controllers

import javax.inject._

import akka.actor.{ActorRef, ActorSystem}
import akka.pattern.ask
import akka.util.Timeout
import com.getmyshoppinglist.plans.domain.requests._
import com.getmyshoppinglist.plans.domain.{Plan, Preferences}
import com.google.inject.name.Named
import play.api.libs.json._
import play.api.mvc._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.duration._

@Singleton
class PlansController @Inject()(@Named("plans") plansActor: ActorRef, actorSystem: ActorSystem) extends InjectedController {
  implicit val timeout: Timeout = 20.seconds

  def create = Action.async(BodyParsers.parse.json) { implicit req =>
    req.body.validate[Preferences] match {
      case s: JsSuccess[Preferences] =>
        (plansActor ? GeneratePlan(s.get)).mapTo[Plan].map(plan => {
          plansActor ! SavePlan(plan)
          Ok(Json.obj("data" -> plan))
        })
      case e: JsError => Future(BadRequest(e.toString))
    }
  }

  def get(id: String) = Action.async { implicit req => execute(RetrievePlan(id)) }

  private def execute(request: PlanRequest): Future[Result] = (plansActor ? request).mapTo[Plan].map(plan => Ok(Json.obj("data" -> plan)))

  def replaceRecipe(id: String, recipeId: Int) = Action.async { implicit req => execute(SwapRecipe(id, recipeId)) }

  def newRecipe(id: String, recipeId: Int) = Action.async { implicit req => execute(AddRecipe(id, recipeId)) }

  def removeRecipe(id: String, recipeId: Int) = Action.async { implicit req => execute(RemoveRecipe(id, recipeId)) }
}
