package juan.ddd.proto.adapter

import juan.ddd.proto.adapter.echo.EchoApi
import juan.ddd.proto.external.rest.RestComponent
import net.codingwell.scalaguice._

class ApisModule extends ScalaModule
{
  private lazy val apiBinder = ScalaMultibinder.newSetBinder[RestComponent](binder)

  protected[this] def bindApi = {
    apiBinder.addBinding
  }

  def configure
  {
    bindApi.to[EchoApi]
  }
}
