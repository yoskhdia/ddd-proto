package juan.ddd.proto.usecase

import juan.ddd.proto.contract.routing.NextRoute
import juan.ddd.proto.usecase.routing.NextRouteImpl
import net.codingwell.scalaguice.ScalaModule

class UseCaseModule extends ScalaModule {
  def configure: Unit = {
    bind[NextRoute].to[NextRouteImpl]
  }
}
