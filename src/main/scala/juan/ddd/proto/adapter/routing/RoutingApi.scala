package juan.ddd.proto.adapter.routing

import javax.inject.Inject

import com.typesafe.scalalogging.slf4j.LazyLogging
import juan.ddd.proto.adapter.RestComponent
import juan.ddd.proto.contract.routing.NextRoute
import juan.ddd.proto.domain.routing.UserId
import org.json4s.{DefaultFormats, Formats}
import spray.httpx.Json4sJacksonSupport
import spray.routing.Directives

class RoutingApi @Inject()(nextRouteUseCase: NextRoute)
  extends RestComponent
  with Directives
  with LazyLogging
  with RoutingApiJsonSupport
{
  def route = {
    path("routing") {
      get {
        parameter('id) { (id: String) =>
          requestInstance { request =>
            complete {
              nextRouteUseCase.nextRoute(UserId(id), toRequest(request))
            }
          }
        }
      }
    }
  }
}

trait RoutingApiJsonSupport extends Json4sJacksonSupport {

  def json4sJacksonFormats: Formats = DefaultFormats

}