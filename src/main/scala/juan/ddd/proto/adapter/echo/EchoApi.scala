package juan.ddd.proto.adapter.echo

import juan.ddd.proto.adapter.RestComponent
import spray.routing.Directives

import javax.inject.Inject
import com.typesafe.scalalogging.slf4j.LazyLogging

class EchoApi @Inject()
  extends RestComponent
  with Directives
  with LazyLogging
{
  def route = {
    path("echo") {
      get {
        parameter('msg) { (message) =>
          complete(message)
        }
      }
    }
  }
}