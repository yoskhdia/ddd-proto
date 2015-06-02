package juan.ddd.proto.usecase.routing

import javax.inject.Inject

import juan.ddd.proto.contract.routing._
import juan.ddd.proto.domain.routing._

class NextRouteImpl extends NextRoute {
  @Inject var userRepository: UserRepository = _
  @Inject var routingMapFinder: RoutingMapFinder = _

  override def nextRoute(userId: UserId, request: Request): Option[Route] = {
    val router = Router(routingMapFinder.find())
    userRepository.userOfId(userId) match {
      case Some(user) => Option(user.attach(request).askRouteTo(router).getOrElse(null))
      case None => {
        val user = User(userRepository.nextIdentity).attach(request)
        userRepository.save(user)
        Option(user.askRouteTo(router).getOrElse(null))
      }
    }
  }
}
