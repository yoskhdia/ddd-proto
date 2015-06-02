package juan.ddd.proto.adapter.routing

import juan.ddd.proto.contract.routing.RoutingMapFinder
import juan.ddd.proto.domain.routing.{Uri, Route, RoutingMap}

class RoutingMapRepositoryImpl extends RoutingMapFinder {

  override def find(): RoutingMap = {
    val map = Map {
      "" -> Route(Uri("GET", "/"))
    }
    RoutingMap(map)
  }
}
