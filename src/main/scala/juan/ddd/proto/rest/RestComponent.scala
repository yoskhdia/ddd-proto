package juan.ddd.proto.rest

import spray.routing.Route

trait RestComponent
{
  def route: Route
}