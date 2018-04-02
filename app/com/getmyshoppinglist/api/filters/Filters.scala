package com.getmyshoppinglist.api.filters

import javax.inject._

import play.api._
import play.api.http.DefaultHttpFilters
import play.filters.cors.CORSFilter

/**
  * This class configures filters that run on every request. This
  * class is queried by Play to get a list of filters.
  *
  * Play will automatically use filters from any class called
  * `com.food.api.middleware.filters.Filters` that is placed the root package. You can load filters
  * from a different class by adding a `play.http.filters` setting to
  * the `application.conf` configuration file.
  *
  * @param env Basic environment settings for the current application.
  *            each response.
  */
@Singleton
class Filters @Inject()(
                         env: Environment,
                         corsFilter: CORSFilter
                       ) extends DefaultHttpFilters(corsFilter) {

}
