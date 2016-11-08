package com.getmyshoppinglist.plans

import com.getmyshoppinglist.plans.domain.Plans
import com.getmyshoppinglist.plans.infra.actors.PlanActor
import com.getmyshoppinglist.plans.infra.db.SlickPlans
import com.google.inject.AbstractModule
import play.api.libs.concurrent.AkkaGuiceSupport

class PlansModule extends AbstractModule with AkkaGuiceSupport {
  override def configure() = {
    bind(classOf[Plans]).to(classOf[SlickPlans])
    bindActor[PlanActor]("plans")
  }
}

