package juan.ddd.proto.domain.routing

trait Router {

  def nextRoute(request: Request): Option[Route]
}

object Router {
  def apply(): Router = {
    SimpleRouter()
  }
}

case class SimpleRouter() extends Router {

  override def nextRoute(request: Request): Option[Route] = {
    Some(Route(Uri("GET", "/")))
  }
}
