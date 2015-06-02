package juan.ddd.proto.domain.routing

case class RoutingMap(rm: Map[String, Route]) extends Map[String, Route] {
  def +(kv: (String, Route)): RoutingMap = new RoutingMap(rm + kv)

  override def get(key: String): Option[Route] = rm.get(key)

  override def iterator: Iterator[(String, Route)] = rm.iterator

  override def -(key: String): Map[String, Route] = new RoutingMap(rm - key)

  override def +[B1 >: Route](kv: (String, B1)): Map[String, B1] = rm + kv
}

trait Router {

  def nextRoute(request: Request): Option[Route]
}

object Router {
  def apply(routingMap: RoutingMap): Router = {
    SimpleRouter(routingMap)
  }
}

case class SimpleRouter(routingMap: RoutingMap) extends Router {

  override def nextRoute(request: Request): Option[Route] = {
    Some {
      routingMap.head._2
    }
  }
}
