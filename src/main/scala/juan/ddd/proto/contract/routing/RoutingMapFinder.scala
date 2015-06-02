package juan.ddd.proto.contract.routing

import juan.ddd.proto.domain.routing.RoutingMap

trait RoutingMapFinder {

  def find(): RoutingMap
}
