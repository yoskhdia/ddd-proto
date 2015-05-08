package juan.ddd.proto

import juan.ddd.proto.util.akka.AkkaModule
import juan.ddd.proto.app.{ServicesModule, ApisModule}
import juan.ddd.proto.util.config.ConfigModule
import juan.ddd.proto.external.rest.RestModule
import net.codingwell.scalaguice.ScalaModule

class MainModule extends ScalaModule
{
  override def configure() {
    installCore
    installServices
    installServer
  }

  private def installCore() {
    install(new ConfigModule)
    install(new AkkaModule)
  }

  private def installServices() {
    install(new ServicesModule)
  }

  private def installServer() {
    install(new RestModule)
    install(new ApisModule)
  }
}
