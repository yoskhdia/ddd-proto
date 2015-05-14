package juan.ddd.proto.contract.routing

import juan.ddd.proto.domain.routing._

trait NextRoute {

  def nextRoute(userId: UserId, request: Request): Option[Route]
}
