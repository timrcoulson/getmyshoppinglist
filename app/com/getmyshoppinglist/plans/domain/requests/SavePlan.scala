package com.getmyshoppinglist.plans.domain.requests

import com.getmyshoppinglist.plans.domain.Plan

case class SavePlan(plan: Plan) extends PlanRequest
