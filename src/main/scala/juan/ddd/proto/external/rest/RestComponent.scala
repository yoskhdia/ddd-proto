package juan.ddd.proto.external.rest

import spray.routing.Route

trait RestComponent
{
  def route: Route
}