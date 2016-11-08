package com.getmyshoppinglist.plans.domain.requests

import com.getmyshoppinglist.plans.domain.Preferences

case class GeneratePlan(preferences: Preferences) extends PlanRequest
