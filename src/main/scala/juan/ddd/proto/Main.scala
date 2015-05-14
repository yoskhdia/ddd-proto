package juan.ddd.proto

import com.google.inject.Guice
import juan.ddd.proto.external.rest.RestServer

object Main extends App
{
  val injector = Guice.createInjector(new MainModule)

  injector.getInstance(classOf[RestServer]).start
}
